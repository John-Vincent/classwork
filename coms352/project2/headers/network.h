#ifndef NETWORK_H
#define NETWORK_H

/**
 * the function opens a port and sends a request to the server from the client
 * @param  host                    the hostname
 * @param  port                    the port number
 * @param  request                 the request string to be sent
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:35:38+000
 */
int send_request(char *host, int port, char *request);

/**
 * this opens a port for the server program and binds it to the port numebr
 * @param  PORT                    the port number to be used
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:37:00+000
 */
int open_socket(int PORT);

/**
 * this function calls the accept function for the server and returns the client socket number
 * @param  sockfd                  the integer representing the server socket
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:37:51+000
 */
int accept_request(int sockfd);

/**
 * gets the request string from the socket and returns the string
 * @param  sockfd                  the client socket number
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:39:11+000
 */
char *get_request(int sockfd);

#endif
