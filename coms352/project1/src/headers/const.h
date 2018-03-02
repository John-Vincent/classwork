#ifndef CONST_H
#define CONST_H

#define MAXLINE 80
#define READ 0
#define WRITE 1
#define eq_exit(c) c[0] == 'e' && c[1] == 'x' && c[2] == 'i' && c[3] == 't' && (c[4] == '\0' || c[4] == ' ')
#define eq_cd(c) c[0] == 'c' && c[1] == 'd' && c[2] == ' '
#define eq_hist(c) c[0] == '!'

#endif
