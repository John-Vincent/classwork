#include "headers/const.h"
#include "headers/command.h"
#include "headers/input.h"
#include "headers/executor.h"
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>

/**
 * main program loop, handles calling all the functions that make to program work, in a loop that detects when to exit.
 * also handles exit and cd commands
 * @return 0, there shouldn't be a case where the program ends in error, all input errors should be handled.
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-01T17:14:16+000
 */
int main(void)
{
  int shouldrun = 1, length, i;
  command_t *input;
  command_t command;

  while(shouldrun)
  {
    printf("osh>");
    fflush(stdout);
    input = get_input(&length);
    //printf("%d commands detected\n", length);
    if(input != NULL)
    {
      for(i = 0; i < length; i++)
      {
        command = input[i];
        shouldrun = run_command(command, input);
      }
      //frees the command array and the string underneath all the commands, this is safe since the potentally running
      //child processes that are using the commands to execute have a copy on their own heap, since heap memory is not shared.
      free(input);
    }
  }
  free_history();
  return 0;
}
