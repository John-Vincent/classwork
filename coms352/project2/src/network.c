#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#include "../headers/utils.h"

#define BUFF_SIZE 1024

int send_request(char *host, int port, char *request){
  struct hostent *he;
  struct sockaddr_in addr;
  int sock;

  if ((he = gethostbyname(host)) == NULL){
      herror("gethostbyname");
      return -1;
  }

  //open socket
  if ((sock = socket(he->h_addrtype, SOCK_STREAM, 0)) < 0){
    printf("Socket creation error \n");
    return -1;
  }

  memcpy(&addr.sin_addr, he->h_addr_list[0], he->h_length);
  addr.sin_port = htons(port);
  addr.sin_family = he->h_addrtype;

  if(connect(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0){
    perror("failed to connect to server");
    return -1;
  }

  send(sock, request, strlen(request), 0);

  return sock;

}

int open_socket(int PORT){
  int sockfd;
  struct sockaddr_in add;
  if((sockfd = socket(AF_INET, SOCK_STREAM, 0)) < 0){
    perror("could not create socket");
    return -1;
  }

  memset(&add, 0, sizeof(add));
  add.sin_family = AF_INET;
  add.sin_addr.s_addr = htonl(INADDR_ANY);
  add.sin_port = htons(PORT);

  if(bind(sockfd, (struct sockaddr *) &add, sizeof(add)) < 0){
    perror("could not bind to port");
    return -1;
  }
  printf("bound to port %d\n", PORT);
  listen(sockfd, 5);
  return sockfd;
}

int accept_request(int sockfd){
  struct sockaddr client;
  int cd;
  socklen_t len;

  if((cd = accept(sockfd, &client, &len)) < 0){
    perror("failed to accept connection");
    return -1;
  }
  return cd;
}

char *get_request(int sockfd){
  char *message, *line;
  long length, total;
  int i = 1;
  total = 0;
  message = malloc(BUFF_SIZE);
  if(message == NULL){
    return NULL;
  }
  length = get_line(&line, sockfd);
  while(strcmp(line, "\r\n") != 0){
    if(total + length > BUFF_SIZE *i + 1){
      i++;
      message = realloc(message, BUFF_SIZE * i);
    }
    strcpy(message+total, line);
    total += length;
    free(line);
    length = get_line(&line, sockfd);
  }
  free(line);
  return message;
}
