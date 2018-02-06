#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <unistd.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#define SIZE 1000
#define NAME "temp"

int main(int argc, char** args){
  int start, fd;
  char* map;

  if(argc < 2){
    scanf("%d", &start);
  } else{
    start = atoi(args[1]);
  }

  if(start <= 0){
    printf("number must be positive: %d is not positive\n", start);
    exit(1);
  }

  fd = shm_open(NAME, O_RDWR|O_CREAT, 0333);
  if(fd < 0){
    printf("failed to open shared memory object:\n%s\n", strerror(errno));
    exit(-1);
  }

  if(ftruncate(fd, (off_t)SIZE) < 0){
    printf("failed to set shared memory size:\n%s\n%d\n", strerror(errno), fd);
    shm_unlink(NAME);
    exit(-1);
  }

  map = mmap(NULL, SIZE, PROT_READ|PROT_WRITE, MAP_SHARED, fd, 0);
  if(map == MAP_FAILED){
    printf("failed to mmap:\n%s\n", strerror(errno));
    shm_unlink(NAME);
    exit(-1);
  }

  if(fork() == 0){
    char *copy = map;
    while(start != 1){
      copy += sprintf(copy, "%d, ", start);
      if(start % 2 == 0){
        start /= 2;
      } else{
        start = start * 3 + 1;
      }
    }
    sprintf( copy, "%d\n", start);
  } else{
    wait(NULL);
    printf("%s", map);
    munmap(map, SIZE);
    shm_unlink(NAME);
  }

  return 0;
}
