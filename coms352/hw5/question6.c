#include <stdio.h>
#include <pthread.h>


extern int world(void);
extern int hello(void);
extern int exlamation(void);
void *thread_start(void *arg);

typedef struct argument{
  pthread_t follow;
  int (*fun)(void);
} argument_t;


int main( int arc, char **argv){
  void *garb;
  pthread_t first, second, third;
  argument_t f, s, t;
  f.follow = 0;
  f.fun = &hello;
  pthread_create(&first, NULL, &thread_start, &f);
  s.follow = first;
  s.fun = &world;
  pthread_create(&second, NULL, &thread_start, &s);
  t.follow = second;
  t.fun = &exlamation;
  pthread_create(&third, NULL, &thread_start, &t);

  pthread_join(third, &garb);

  printf("\n");

  return 0;
}

void *thread_start(void *arg){
  void *g;
  argument_t *a = (argument_t *) arg;

  if(a->follow){
    pthread_join(a->follow, &g);
  }

  a->fun();
  return g;
}

int world(void){
  printf("world");
  return 0;
}


int hello(void){
  printf("hello ");
  return 0;
}

int exlamation(void){
  printf("!");
  return 0;
}
