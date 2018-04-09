#include <sys/types.h>
#include <sys/socket.h>
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <errno.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdlib.h>
#define BUF_SIZE 1024

int send_request(char *host, int port, char *request, char **response){
  struct hostent *he;
  struct sockaddr_in addr;
  int sock, i, size;
  char *buf;

  if((buf = malloc(BUF_SIZE)) <0){
    perror("error allocating space for response");
    return -1;
  }

  //open socket
  if ((sock = socket(AF_INET, SOCK_STREAM, 0)) < 0){
    printf("Socket creation error \n");
    return -1;
  }


  if ((he = gethostbyname(host)) == NULL){
      herror("gethostbyname");
      return -1;
  }

  if(inet_pton(AF_INET, he->h_addr, &addr.sin_addr) <= 0){
    perror("cannot convert host address");
    return -1;
  }

  addr.sin_port = (unsigned short) port;

  if(connect(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0){
    perror("failed to connect to server");
  }

  send(sock, request, strlen(request), 0);
  i = 0;
  while((size = read(sock, buf + i*BUF_SIZE, BUF_SIZE)) > 0){
    i++;
    if((buf = realloc(buf, (i+1)*BUF_SIZE)) < 0){
      perror("error reallocating space for response");
      return -1;
    }
  }

  if(size < 0){
    perror("error reading response");
    return -1;
  }

  *response = buf;

  close(sock);

  return 0;

}
