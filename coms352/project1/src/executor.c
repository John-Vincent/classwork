#include "headers/command.h"
#include "headers/const.h"
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

char *prev_dir = NULL;

typedef struct history{
  char command[MAXLINE + 1];
  struct history *next;
} history_t;

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
  //add this command to the history linked list.
  history_t *temp = malloc(sizeof(*temp));
  temp->next = last;
  strcpy(temp->command, command.string);
  last = temp;
  //check for commands that should be run in the main process
  if(eq_exit(command.string) && strchr(command.string, '|') == NULL)
  {
    return 0;
  } else if(command.length > 3 && eq_cd(command.string) && strchr(command.string, '|') == NULL)
  {
    change_dir(command.string);
  } else
  {
    //fork and run;
  }
  return 1;
}


void change_dir(char *string)
{
  char *arg;
  if(prev_dir)
    free(prev_dir);
  prev_dir = getcwd(prev_dir, 0);
  arg = string + 3;
  //printf("%s\n", string);
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
