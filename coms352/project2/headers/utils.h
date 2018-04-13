#ifndef UTILS_H
#define UTILS_H

/**
 * gets the time in string format base on the given offset string
 * @param  offset                  the offset string in format %d:%d:%d
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:40:14+000
 */
char *get_time(char* offset);

/**
 * gets a line from the socket so that the messages coming through can be read one line at a time
 * @param  line                    the character pointer that should be set to the newly allocated c string
 * @param  sock                    the socket to read the line from
 * @return
 * @author Collin Vincent collinvincent96@gmail.com
 * @date   2018-04-13T21:40:50+000
 */
long get_line(char **line, int sock);

#endif
