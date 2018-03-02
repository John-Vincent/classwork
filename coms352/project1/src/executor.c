#include "headers/command.h"
#include "headers/const.h"
#include "headers/executor.h"
#include <unistd.h>
#include <sys/wait.h>
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

/**
 * this function will do the logic for chaning the cwd
 * of the shell, and will be executed by the main thread
 * as long as there are no pipes in the command after(which is consistant with linux terminal).
 * @param  string                  the string argument for changing the directory.
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T00:15:08+000
 */
void change_dir(char *string);

/**
 * gets a command from the history of commands
 * @param  s                       string for querying the history
 * @return                         a command if the requested history exist null otherwise
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T13:29:04+000
 */
command_t *retrieve_history(char *s);

/**
 * splits the command with pipes into an array of commands
 * and creates the pipes needed to communicate between the programs
 * @param  command                 the command that has piped subcommands
 * @param  num_piped               pointer to int that will contain number of commands
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T14:44:24+000
 */
command_t *get_piped_commands(command_t command, int *num_piped);

/**
 * this will fork a process split the command string if & is a the end of the command
 * the parent will wait until the process completes, and will also close any in_pipe.
 * @param  command_t               the command to be run
 * @param  in_pipe                 the pipe the should be used as std_in
 * @param  out_pipe                the pipe that should be used as std_out
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T15:26:58+000
 */
void execute_command(command_t command, int *in_pipe, int *out_pipe);

/**
 * this takes a command string and splits it into an array of tokens that can be
 * passed to the execvp function
 * @param  copy                    command string, will be modified.
 * @param  tokens                  pointer to int that will be filled with number of tokens present.
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T15:47:00+000
 */
char **tokenize_command(char *copy, int *tokens);

/**
 * prints the last 10 commands in the history
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T19:28:31+000
 */
void print_history(void);

char prev_dir[100];

int command_num = 0;

history_t *last = NULL;

//pointer to allocated memory that each fork will need to free to prevent leaks
//in child processes.
command_t *to_free;

//pointer that will be set to the piped commands array so forked processes can free it before closing.
command_t *to_free2;

int run_command(command_t command, command_t *heap)
{
  command_t *piped_commands;
  int num_piped, i;
  int pipe1[2], pipe2[2], *out_pipe = NULL, *in_pipe = NULL;

  to_free = heap;
  to_free2 = NULL;
  //check for commands that should be run in the main process
  if(eq_hist(command.string) && strchr(command.string, '|') == NULL)
  {
    piped_commands = retrieve_history(command.string);
    if(!piped_commands)
      return 1;
    command = *piped_commands;
  }

  if(eq_exit(command.string) && strchr(command.string, '|') == NULL)
  {
    return 0;
  }
  else if(command.length > 3 && eq_cd(command.string) && strchr(command.string, '|') == NULL)
  {
    add_to_history(command);
    change_dir(command.string);
  } else
  {
    add_to_history(command);
    if(strchr(command.string, '|'))
    {
      piped_commands = get_piped_commands(command, &num_piped);
      //if there was no real pipe and the pipe character detected was just in a quote
      if(num_piped == 1)
      {
        execute_command(command, NULL, NULL);
        return 1;
      }
      if(piped_commands == NULL)
        return 1;
      to_free2 = piped_commands;
      //execute commands will needed pipes as arguments
      for( i = 0; i < num_piped; i++)
      {
        in_pipe = out_pipe;
        if(out_pipe == pipe1)
          out_pipe = pipe2;
        else
          out_pipe = pipe1;
        if(i == num_piped - 1)
        {
          out_pipe = NULL;
        }
        else
        {
          if(pipe(out_pipe))
          {
            printf("%s", strerror(errno));
            close(in_pipe[READ]);
            close(in_pipe[WRITE]);
            return 1;
          }
        }
        execute_command(piped_commands[i], in_pipe, out_pipe);
      }
      free(piped_commands);
      return 1;
    }
    else
    {
      execute_command(command, NULL, NULL);
    }
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

void execute_command(command_t command, int *in_pipe, int *out_pipe)
{
  command_t *temp;
  char **split_command;
  char copy[MAXLINE+1];
  int pid, tokens, status;

  //will only execute if call to history is within pipe command to prevent using a really long command and then adding a recall of it to another reall long command
  //which would cause and overflow if i just placed the old command string within the piped chain command in the history.
  if(eq_hist(command.string))
  {
    temp = retrieve_history(command.string);
    if(!temp)
      return;
    command = *temp;
  }

  strcpy(copy, command.string);

  split_command = tokenize_command(copy, &tokens);

  pid = fork();
  if(pid == 0)
  {
    if(in_pipe)
    {
      if(dup2(in_pipe[READ], STDIN_FILENO) == -1)
        perror("dup2");
      close(in_pipe[WRITE]);
      close(in_pipe[READ]);
    }
    if(out_pipe)
    {
      if(dup2(out_pipe[WRITE], STDOUT_FILENO) == -1)
        perror("dup2");
      close(out_pipe[READ]);
      close(out_pipe[WRITE]);
    }
    free(to_free);
    if(!strcmp(split_command[0], "history"))
    {
      print_history();
      free(to_free2);
      free_history();
      free(split_command);
      exit(1);
    }
    if(execvp(split_command[0], split_command))
    {
      perror("execvp");
      fprintf(stderr, "on command \"%s\"\n", command.string);
      free(to_free2);
      free_history();
      free(split_command);
      exit(1);
    }
  }
  else
  {
    if(in_pipe)
    {
      close(in_pipe[WRITE]);
      close(in_pipe[READ]);
    }
    if(*(split_command[tokens-1]) != '&')
    {
      waitpid(pid, &status, 0);
    }
    free(split_command);
  }
}

void print_history()
{
  int i, j;
  history_t *cur = last;
  j = command_num < 10 ? command_num : 10;
  for(i = 0; i < j ; i++)
  {
    printf("%d: %s\n", command_num-i, cur->command.string);
    cur = cur->next;
  }
}

command_t *get_piped_commands(command_t command, int *num_piped)
{
  command_t *ans = NULL;
  char *cur = command.string;
  int i, s, quote = 0;

  *num_piped = 1;
  //get number of commands deliminated by pipes
  while(*cur != '\0')
  {
    if(*cur == '|' && !quote)
      (*num_piped)++;
    else if(*cur == '"')
      quote = !quote;
    cur++;
  }
  if(*num_piped == 1)
    return ans;
  ans = malloc(*num_piped *sizeof(*ans));
  if(!ans)
  {
    *num_piped = 0;
    printf("failed to allocate space for piped commands");
    return ans;
  }
  cur = command.string;
  s = 0;
  i = 0;
  ans[0].length = 0;
  //create an array of sub commands that contain no pipes.
  quote = 0;
  while(*cur != '\0')
  {
    if(*cur == '|' && !quote)
    {
      if(ans[i].string[s-1] == ' ')
        ans[i].string[s-1] = '\0';
      i++;
      s = 0;
      ans[i].length = 0;
    }
    else
    {
      if(s != 0 || *cur != ' ')
      {
        ans[i].string[s++] = *cur;
        ans[i].length++;
      }
      if(*cur == '"')
        quote = !quote;
    }
    cur++;
  }
  return ans;
}

char **tokenize_command(char *copy, int *tokens)
{
  char **ans = NULL;
  char *cur = copy;
  int quote = 0, i;
  *tokens = 1;

  while(*cur != '\0')
  {
    if(!quote && *cur == ' ')
      (*tokens)++;
    else if(*cur == '"')
      quote = !quote;
    cur++;
  }
  ans = malloc(*tokens * sizeof(*ans) + 1);
  cur = copy;
  ans[0] = cur;
  i = 1;
  while(*cur != '\0')
  {
    if(!quote && *cur == ' ')
    {
      ans[i] = cur+1;
      *cur = '\0';
      i++;
    }
    else if(*cur == '"')
      quote = !quote;
    cur++;
  }
  ans[*tokens] = NULL;
  return ans;
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

command_t *retrieve_history(char *s)
{
  int num, hist_num;
  history_t *hist;
  command_t *ans = NULL;
  if(s[0] == '!' && s[1] == '!')
  {
    if(last)
    {
      return &(last->command);
    }
    else
    {
      printf("no commands in history\n");
      return ans;
    }
  }
  else
  {
    num = sscanf(s, "!%d", &hist_num);
    if(num == 0)
      return NULL;
    num = command_num;
    hist = last;
    while(hist_num < num){
      num--;
      hist = hist->next;
    }
    if(hist_num == num)
      return &(hist->command);
    printf("No such command in history.\n");
    return ans;
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
