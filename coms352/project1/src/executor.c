#include "headers/command.h"
#include "headers/const.h"
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

/**
 * struct that will story the history of the commands entered into the shell
 * functions as a linked list
 */
typedef struct history{
  command_t command;
  struct history *next;
} history_t;

/**
 * adds a given command to the history linked list
 * @param  command                 the command to be added to the linked list.
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T02:06:16+000
 */
void add_to_history(command_t command);

char prev_dir[100];

int command_num = 0;

history_t *last = NULL;

/**
 * this function will do the logic for chaning the cwd
 * of the shell, and will be executed by the main thread
 * as long as there are no pipes in the command after(which is consistant with linux terminal).
 * @param  string                  the string argument for changing the directory.
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T00:15:08+000
 */
void change_dir(char *string);

int run_command(command_t command, command_t *heap)
{
  //check for commands that should be run in the main process
  if(eq_exit(command.string) && strchr(command.string, '|') == NULL)
  {
    return 0;
  } else if(command.length > 3 && eq_cd(command.string) && strchr(command.string, '|') == NULL)
  {
    add_to_history(command);
    change_dir(command.string);
  } else
  {
    add_to_history(command);
    //fork and run;
  }
  return 1;
}


void change_dir(char *string)
{
  char *arg, temp[100];
  if(!getcwd(temp, 100)){
    printf("failed to get current directory: %s", strerror(errno));
  }
  arg = string + 3;
  //printf("%s\n", temp);
  if(string[3] == '.' && string[4] == '.')
  {
    string[1] = '.';
    string[2] = '/';
    arg-=2;
  } else if(string[3] == '-')
  {
    if(prev_dir == NULL){
      printf("There is no previous directory\n");
      return;
    }
    string[3] = '.';
    if(chdir(prev_dir))
    {
      printf("%s\n", strerror(errno));
    }
  } else if(string[3] == '~')
  {
    arg = getenv("HOME");
    if(arg == NULL){
      printf("Could not find home directory, please update environment variable $HOME\n");
      return;
    }
    string[3] = '.';
    if(chdir(arg))
    {
      printf("%s\n", strerror(errno));
    }
    arg = string + 3;
  }
  if(chdir(arg))
  {
    printf("%s\n", strerror(errno));
  }
  strcpy(prev_dir, temp);
}

void add_to_history(command_t command)
{
  history_t *temp = malloc(sizeof(*temp));
  temp->next = last;
  strcpy(temp->command.string, command.string);
  temp->command.length = command.length;
  last = temp;
  command_num++;
}

void free_history()
{
  history_t *cur=last, *next;
  while(cur){
    next = cur->next;
    free(cur);
    cur = next;
  }
}
