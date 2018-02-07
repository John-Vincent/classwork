#include <stdio.h>
#include <unistd.h>
#include <sys/types.h>
#include <string.h>
#include <stdlib.h>

#define WRITE 1
#define READ 0
#define LENGTH 50

int get_message(char *message, int argc, char **argv);
void child_task(int* tp, int* tc, char* message);
void parent_task(int* tp, int* tc, char* message);

int main(int argc, char **argv){
  int tp[2], tc[2];
  pid_t childpid;
  char message[LENGTH];
  message[0] = '\0';

  if(get_message(message, argc, argv)){
    return -1;
  }

  pipe(tp);
  pipe(tc);

  if((childpid = fork()) == 0){
    child_task(tp, tc, message);
  } else if(childpid != -1){
    parent_task(tp, tc, message);
  }
  return 0;
}

void child_task(int* tp, int* tc, char* message){
  int i;
  //close the other processes sides of the pipe
  close(tp[READ]);
  close(tc[WRITE]);

  read(tc[READ], message, LENGTH);
  printf("Process: %d - received string \"%s\"\n", getpid(), message);
  //inverse message
  for(i = 0; i < LENGTH; i++){
    if(message[i] == '\0')
      break;
    if(message[i] > 64 && message[i] < 91){
      message[i] += 32;
    } else if(message[i] > 96 && message[i] < 123){
      message[i] -= 32;
    }
  }
  write(tp[WRITE], message, LENGTH);

  //close the pipes
  close(tp[WRITE]);
  close(tc[READ]);
}

void parent_task(int* tp, int* tc, char* message){
  //close the other processes sides of the pipe
  close(tc[READ]);
  close(tp[WRITE]);
  //send message to child
  write(tc[WRITE], message, LENGTH);
  read(tp[READ], message, LENGTH);
  printf("Process: %d - received string \"%s\"\n", getpid(), message);

  //close the pipes
  close(tc[WRITE]);
  close(tp[READ]);
}

/**
 * pulls the message from the arguments or from scanf if there are no arguments
 * @param  message                 the charater pointer that will be filled with the message
 * @param  argc                    the number of arguments
 * @param  argv                    the array of string arguments
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-02-06T22:52:19+000
 */
int get_message(char *message, int argc, char **argv){
  int length = 0, i;

  if(argc < 2){
    printf("type a message to invert case:\n");
    while(!fgets(message, LENGTH, stdin));
    i = strlen(message) - 1;
    if(message[i] == '\n'){
      message[i] = '\0';
    }
  } else{
    for(i = 1; i < argc; i++){
      length += strlen(argv[i]);
      //if its not the last word add a space
      if(i < argc-1){
        length++;
      }
      if(length > 50){
        printf("input string must be less than 50 characters");
        return -1;
      }
      strcat(message, argv[i]);
      //add space
      if(i < argc-1){
        message[length-1] = ' ';
      }
    }
  }
  return 0;
}
