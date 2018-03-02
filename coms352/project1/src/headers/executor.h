#ifndef CHILD_H
#define CHILD_H
#include "./command.h"
/**
 * This function will create the child process(es) to run the input command(s)
 * it will also detect pipes and concurrency (&) inputs and pipe/wait accordingly
 * @param  command                 the command to be run.
 * @param  heap                    the command array that is allocated on the heap so any sub  process can free it before closing.
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-01T17:57:24+000
 */
int run_command(command_t command, command_t *heap);

/**
 * frees the history linked list nodes
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-03-02T01:31:52+000
 */
void free_history();

#endif
