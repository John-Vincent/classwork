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
