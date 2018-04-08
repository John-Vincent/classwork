#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include "../headers/utils.h"

int parse_args(int argc, char** argv, char** address, char** time_interval);

int parse_url(char *address, char* hostname, char* filepath, int* port);

char *generate_request(char* filepath, char* hostname, char* time_interval, int header);

int main(int argc, char** argv){
  char *address = NULL, *hostname, *filepath;
  char *time_interval = NULL;
  char *request = NULL;
  int header, port = 80, tmp;

  header = parse_args(argc, argv, &address, &time_interval);
  if(header == -1){
    perror("error parsing arguments");
    return -1;
  } else if(header == -2){
    printf("no url found");
    return -1;
  }

  tmp = strlen(address);
  if((hostname = malloc(tmp)) == NULL || (filepath = malloc(tmp)) == NULL){
    perror("cannot allocate memory");
    return -1;
  }

  if(parse_url(address, hostname, filepath, &port)){
    printf("url is ill formatted: %s\n", address);
    return -1;
  }

  if(time_interval){
    if((time_interval = get_time(time_interval)) == NULL){
      perror("eror making time string");
      return -1;
    }
  }

  request = generate_request(filepath, hostname, time_interval, header);


  free(time_interval);
  free(request);
}


int parse_args(int argc, char** argv, char** address, char** time_interval){
  int i = 1;
  int head = -2;
  for(; i < argc; i++){
    if(!strcmp(argv[i], "-h")){
      head = 1;
    } else if(!strcmp(argv[i], "-d")){
      *time_interval = argv[i+1];
      i++;
    } else if(*address == NULL){
      *address = argv[i];
      head = 0;
    } else{
      printf("invalid argument: %s", argv[i]);
      return -1;
    }
  }
  return head;
}

char *generate_request(char* filepath, char* hostname, char* time_interval, int header){
  char* request = NULL;
  int size = 26;
  if(header){
    size++;
  }
  if(time_interval){
    size +=21;
    size += strlen(time_interval);
  }
  size += strlen(filepath);
  size += strlen(hostname);
  if((request = malloc(size)) == NULL){
    return request;
  }
  if(header){
    request[0] = 'H';
    request[1] = 'E';
    request[2] = 'A';
    request[3] = 'D';
    request[4] = ' ';
    size = 5;
  }else{
    request[0] = 'G';
    request[1] = 'E';
    request[2] = 'T';
    request[3] = ' ';
    size = 4;
  }
  if(!time_interval){
    sprintf(request+size, "%s HTTP/1.1\r\nHOST: %s\r\n\r\n", filepath, hostname);
  } else{
    sprintf(request+size, "%s HTTP/1.1\r\nHOST: %s\r\nIf-Modified-Since: %s\r\n\r\n", filepath, hostname, time_interval);
  }
  return request;
}

int parse_url(char *address, char* hostname, char* filepath, int* port){
  filepath[0] = '/';
  filepath[1] = '\0';
  if(sscanf(address, "http://%[^:]:%d%s", hostname, port, filepath) < 2){
    if(sscanf(address, "http://%[^/]%s", hostname, filepath) < 1){
      return -1;
    }
  }
  return 0;
}
