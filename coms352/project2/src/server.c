#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include "../headers/network.h"
#include "../headers/utils.h"

#define PORT 1337
#define BUFF_SIZE 1024

pthread_mutex_t lock;

void handle_request(void *request, int);

int main(int argc, char** argv){
  int sockd, ans;
  pthread_t *thread;

  sockd = open_socket(PORT);
  while(1){
    if(accept_request(sockd) == NULL){
      perror("failed getting packet");
      return -1;
    }
    ans = pthread_create(thread, NULL, handle_request, &sockd);
    if(ans != 0){
      perror("could not make a thread");
      return -1;
    }
  }

}

void handle_request(void *socket){
  char *request, *response;
  char *line, file[BUFF_SIZE], rtype[10], date[30];
  int sockd = *((int*) socket);
  FILE *fd;
  pthread_mutex_lock(&lock);
  request = get_request(sockd);
  pthread_mutex_unlock(&lock);
  response = malloc(BUFF_SIZE);
  if(response == NULL){
    perror("failed to allocate space for response");
    exit(-1);
  }
  file[0] = '.';
  line = strtok(request, "\r\n");
  sscanf(line, "%s %s", &rtype, &(file+1));
  while((line = strtok(NULL, "\n")) != NULL){
    sscanf(line, "If-Modified-Since: %s", &date);
  }
  if((fd = fopen(file)) == NULL){
    strcpy(response, "HTTP/1.1 404 Not Found\r\n\r\n");
    send_response(response);
  }

  free(request);
  exit(0);
}
