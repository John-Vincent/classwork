#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include "../headers/utils.h"
#include "../headers/network.h"
#define BUF_SIZE 1024

int parse_args(int argc, char** argv, char** address, char** time_interval);

int parse_url(char *address, char* hostname, char* filepath, int* port);

char *generate_request(char* filepath, char* hostname, char* time_interval, int header);

int read_write_message(int sock);

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
    printf("no url found\r");
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

  if(time_interval){
    free(time_interval);
  }

  if((tmp = send_request(hostname, port, request)) == -1){
    return -1;
  }

  if(read_write_message(tmp)){
    return -1;
  }

  if(request){
    free(request);
  }
  if(hostname){
    free(hostname);
  }
  if(filepath){
    free(filepath);
  }
  return 0;
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


int read_write_message(int sock){
  char *buff;
  char *line = NULL;
  int size, chunked;
  size_t n = 0;
  long chunk_size;
  FILE *file;

  file = fopen("response.txt", "w+");

  if(file == NULL){
    perror("could not open/create response file");
    close(sock);
    fclose(file);
    return -1;
  }

  //reads http header
  while(1){
    //need to write my own get_line
    size = get_line(&line, sock);
    if(size < 0){
      perror("failed to read line");
      close(sock);
      fclose(file);
      return -1;
    }
    if(fwrite(line, 1, strlen(line), file) == -1){
      perror("file write error");
    }
    if(sscanf(line, "Content-Length: %d", &size)){
      chunked = 0;
    } else if(strcmp("Transfer-Encoding: chunked", line) == 0){
      chunked = 1;
    } else if(strcmp("\r\n", line) == 0 || strcmp("\n", line) == 0){
      break;
    }
    free(line);
    line = NULL;
    n = 0;
  }

  //reads chunked body
  if(chunked){
    chunk_size = 1;
    while(chunk_size != 0){
      free(line);
      line = NULL;
      n = 0;
      if((size = get_line(&line, sock)) == -1){
        perror("failed to read line");
        break;
      }
      chunk_size = strtol(line, NULL, 16);
      if(chunk_size != 0){
        if((buff = malloc(chunk_size + 3)) == NULL){
          perror("error allocating space for buffer");
        }
        n = 0;
        while((size = read(sock, buff + n, chunk_size - n)) != chunk_size - n){
          if(size == -1){
            perror("failed to read from socket");
            close(sock);
            fclose(file);
            return -1;
          }
          size += n;
        }
        if(fwrite(buff, 1, chunk_size, file) == 0 && ferror(file) == -1){
          perror("file write error");
          break;
        }
        free(buff);
      }
    }
  } else{ //reads normal body with specified content-length
    if((buff = malloc(size + 3)) == NULL){
      perror("error allocating space for buffer");
    }
    size = read(sock, buff, size);
    if(size < 0){
      perror("error reading response");
      return -1;
    }
    printf("Bytes recieved %d\n", size);
    if(fwrite(buff, 1, size, file) == 0 && ferror(file) == -1){
      perror("file write error");
    }
    free(buff);
  }

  close(sock);
  fclose(file);

  return 0;
}
