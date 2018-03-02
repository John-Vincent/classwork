#ifndef COMMAND_H
#define COMMAND_H
#include "const.h"
/**
 * this is a struct that holds a string and an int
 * the string is a user entered command and the int is the length of the string not including the null terminator.
 */
typedef struct command
{
  char string[MAXLINE+1];
  int length;
} command_t;

#endif
