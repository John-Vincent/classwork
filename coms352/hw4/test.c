#include <stdio.h>
#include <unistd.h>

int program1();
int program2();

int main(int argc, char** argv){
  int i;
  for(i = 1; i < argc; i++){
    if(argv[i][0] == '1'){
      program1();
    } else if( argv[i][0]=='2'){
      program2();
    }
  }

  return 0;
}

int program1(){
  int i;
  for(i=0; i<2;i++){
    fork();
    printf("%d,%d\n", getppid(), getpid());
  }
  return 0;
}

int program2(){
  int i;
  for(i=0;i<2;i++)
    fork();
  printf("%d,%d\n", getppid(), getpid());
  return 0;
}
