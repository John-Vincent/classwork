#include <string.h>
#include <stdio.h>
#include <time.h>
#include <stdlib.h>

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

int print_file(char *file, char *payload){
  
}
