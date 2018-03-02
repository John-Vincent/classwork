#ifndef INPUT_H
#define INPUT_H
#include "./command.h"

/**
 * get the user input and test if it meets meets the requirements for the program, errors are reported in
 * this function if there is a issue with input. also sets c to be the number of commands read.
 * @return a pointer to a command_t array, NULL otherwise
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-01T16:50:47+000
 */
command_t *get_input(int *c);


#endif
