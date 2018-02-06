#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>


int main(int argc, char** args){
  int start;
  if(argc < 2){
    scanf("%d", &start);
  } else{
    start = atoi(args[1]);
  }

  if(start <= 0){
    printf("number must be positive: %d is not positive\n", start);
    exit(1);
  }

  if(fork() == 0){
    while(start != 1){
      printf("%d, ", start);
      if(start % 2 == 0){
        start /= 2;
      } else{
        start = start * 3 + 1;
      }
    }
    printf("%d\n", start);
  } else{
    wait(NULL);
  }

  return 0;
}
