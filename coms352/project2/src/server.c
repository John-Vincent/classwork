#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <sys/signal.h>
#include <sys/stat.h>
#include <unistd.h>
#include "../headers/network.h"
#include "../headers/utils.h"

#define PORT 1337
#define BUFF_SIZE 1024

/**
 * this is the starting point for new threads spawned by the server
 * this function reads the request, parses it, then reads and writes the requested file if the request is correct
 * @param  request                 this is a pointer to the integer that represents the socket for this client connection
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:14:48+000
 */
void *handle_request(void *request);

/**
 * this is the function that is called when the program closes, it closes the socket for the server before the program exits
 * @param  signal                  [description]
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:16:10+000
 */
void close_down(int signal);

int sockfd;

/**
 * this is the main function for the program it opens the socket and
 * then loops to accept request and spawn threads
 * @param  argc                    [description]
 * @param  argv                    [description]
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:16:51+000
 */
int main(int argc, char** argv){
  int ans, *tmpd;
  pthread_t thread;
  signal(SIGINT, close_down);
  sockfd = open_socket(PORT);
  if(sockfd == -1){
    return -1;
  }
  while(1){
    if((tmpd = malloc(sizeof(*tmpd))) == NULL){
      perror("failed to allocated space");
      close_down(1);
    }
    if((*tmpd = accept_request(sockfd)) < 0){
      perror("failed getting packet");
      return -1;
    }
    ans = pthread_create(&thread, NULL, &handle_request, tmpd);
    if(ans != 0){
      perror("could not make a thread");
      return -1;
    }
  }

}

void *handle_request(void *socket){
  char *request, *response;
  struct stat statistics;
  char *line, file[BUFF_SIZE], rtype[10];
  struct tm tm;
  int sockd = *((int*) socket), mod = 0, size;
  FILE *fd;
  request = get_request(sockd);
  printf("%s", request);
  response = malloc(BUFF_SIZE);
  if(response == NULL){
    perror("failed to allocate space for response");
    exit(-1);
  }
  file[0] = '.';
  line = strtok(request, "\r\n");
  sscanf(line, "%s %s", rtype, file+1);
  while((line = strtok(NULL, "\n")) != NULL){
    if(sscanf(line, "If-Modified-Since: %d/%d/%d %d:%d:%d", &(tm.tm_mon), &(tm.tm_mday), &(tm.tm_year), &(tm.tm_hour), &(tm.tm_min), &(tm.tm_sec)) == 6){
      mod = 1;
      break;
    }
  }
  if((fd = fopen(file, "r")) == NULL){
    sprintf(response, "HTTP/1.1 404 Not Found\r\nDate: %s\r\n\r\n", get_time("0:0:0"));
    write(sockd, response, strlen(response));
  } else{
    printf("serving file %s\n", file);
    if(stat(file, &statistics) != 0){
      perror("couldn't get file stats");
    }
    if(!mod || statistics.st_mtim.tv_sec > mktime(&tm)){
      if(strncmp(rtype, "GET", 3) == 0){
        fseek(fd, 0, SEEK_END);
        sprintf(response, "HTTP/1.1 200 Ok\r\nDate: %s\r\nContent-Length: %ld\r\n\r\n", get_time("0:0:0"), ftell(fd));
        fseek(fd, 0, SEEK_SET);
        write(sockd, response, strlen(response));
        size = fread(response, 1, BUFF_SIZE, fd);
        while(size != 0){
          write(sockd, response, size);
          size = fread(response, 1, BUFF_SIZE, fd);
        }
      }
      else{
        sprintf(response, "HTTP/1.1 200 Ok\r\nDate: %s\r\n\r\n", get_time("0:0:0"));
        write(sockd, response, strlen(response));
      }
    } else{
      sprintf(response, "HTTP/1.1 304 Not Modified\r\nDate: %s\r\n\r\n", get_time("0:0:0"));
      write(sockd, response, strlen(response));
    }
    fclose(fd);
  }
  close(sockd);
  free(request);
  free(socket);
  return NULL;
}


void close_down(int sigtype){
  close(sockfd);
  printf("Listen terminated\n");
  exit(0);
}
