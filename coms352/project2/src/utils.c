#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>
#include <errno.h>
#include <unistd.h>
#define BUFF_SIZE 1024

char *get_time(char* offset){
  int dayo = 0, houro = 0, minuteo = 0;
  char *ans = malloc(20);
  if(ans == NULL){
    return NULL;
  }
  time_t cur_time = time(NULL);
  if(offset)
    sscanf(offset, "%d:%d:%d", &dayo, &houro, &minuteo);

  cur_time -= (dayo * 60 * 60 * 24 + houro * 60 * 60 + minuteo * 60);

  struct tm tm = *localtime(&cur_time);

  sprintf(ans, "%d/%d/%d %d:%d:%d", tm.tm_mon, tm.tm_mday, tm.tm_year, tm.tm_hour, tm.tm_min, tm.tm_sec);

  return ans;
}

long get_line(char **line, int sock){
  long count = 0;
  long size = 0;
  char *ans;
  int it = 2;
  ans = malloc(BUFF_SIZE);
  if(ans == NULL){
    perror("malloc failed");
    return -1;
  }
  ans[0] = ' ';
  while(ans[count] != '\n'){
    size = read(sock, ans + count, 1);
    if(size != 1){
      perror("read error");
      return -1;
    }
    ans[count+1] = '\0';
    if(ans[count] == '\n'){
      break;
    } else if(count+1 == BUFF_SIZE){
      ans = realloc(ans, BUFF_SIZE * it);
      if(ans == NULL){
        perror("error reallocating");
      }
      it++;
    }
    count++;
  }

  *line = ans;

  return count+1;
}
