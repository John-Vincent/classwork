#include "headers/const.h"
#include "headers/command.h"
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>

/**
 * takes the c string input with length end and creates a command_t array
 * where each element in the array is a different user entered command.
 * also test for "empty" commands like "   ;ls ." which would be invalid because the first command
 * has to actually command to it.
 * sets length to the number of commands found in input.
 * also trims spaces off the front and end of command strings.
 * @param  input                   user entered string.
 * @param  end                     length of the input string
 * @param  length                  pointer to an integer which will be set to the number of commands entered.
 * @return                         an array of type command_t where each element is a user command
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-01T19:19:17+000
 */
command_t *split_commands(char *input, int end, int *length);


command_t *get_input(int *length){
  long unsigned chars = 0, read;
  char *input = NULL;
  command_t *ans;

  read = getline(&input, &chars, stdin);
  if(read == -1)
  {
    printf("%s\n", strerror(errno));
    if(input != NULL){
      free(input);
    }
    return NULL;
  } else if(read > MAXLINE + 2){
    printf("lines can only be %d characters long: %ld characters detected\n%s", MAXLINE, read, input);
    free(input);
    return NULL;
  }
  ans = split_commands(input, (int)read, length);
  free(input);
  return ans;
}


command_t *split_commands(char *input, int end, int *length)
{
  command_t *commands;
  int i, c, test = 0, start = -1, r, space = 0, quote = 0;
  *length = 1;

  for(i = 0; i < end; i++){
    if(!test && (input[i] == '&' || input[i] == ';' || input[i] == '|')){
      printf("unexpected character: '%c' at character: %d", input[i], i);
      return NULL;
    }
    if(input[i] != ' ' && input[i] != ';' && input[i] != '&' && input[i] != '|'){
      test = 1;
    }
    if(input[i] == ';'){
      test = 0;
      (*length)++;
    }
  }
  commands = (command_t *)malloc(sizeof(*commands) * (*length));
  c = 0;
  for(i = 0; i < end; i++)
  {
    //if there are multiple spaces in a row disregard all spaces after the first
    //unless its inside of a quote.
    if(space && input[i] == ' ' && !quote)
    {
      start++;
      continue;
    }
    if(start == -1 && input[i] != ' ')
    {
      start = i;
      commands[c].length = 0;
    }
    if(start != -1 && input[i] != ';' && input[i] != '\n')
    {
      commands[c].string[i-start] = input[i];
      commands[c].length++;
    }
    if(start != -1 && (input[i] == ';' || input[i] == '\n'))
    {
      commands[c].string[i-start] = '\0';
      r = 1;
      while(commands[c].string[i-start-r] == ' ' || commands[c].string[i-start-r] == '\n' || commands[c].string[i-start-r] == '\r')
      {
        commands[c].string[i-start-r] = '\0';
        commands[c].length--;
        r++;
      }
      start = -1;
      c++;
    }
    //stores if the last character was a space for the next loop iteration
    if(input[i] == ' ')
    {
      space = 1;
    } else
    {
      space = 0;
    }
    if(input[i] == '"')
    {
      quote = !quote;
    }
  }
  i = 0;
  while(i < *length && commands[i].length == 0)
    i++;
  if(i == *length)
  {
    free(commands);
    commands = NULL;
  }
  return commands;
}
