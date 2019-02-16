#include<stdio.h>

char* skipSpaces(char* buffer);

int main(int argc, char**argv)
{
    int i;
    for(i = 1; i < argc; i++)
    {
        printf("string: \"%s\"\nfirst non whitespace: \"%s\"\n", argv[i], skipSpaces(argv[i]));
    }
}

char* skipSpaces(char* buffer)
{
    int i = 0;

    while(1)
    {
        switch(buffer[i])
        {
            case '\t':
            case '\n':
            case ' ':
                i++;
                break;
            case '/':
                if(buffer[i+1] == '*')
                {
                    i+=4;
                    while(buffer[i] != '\0' && 
                        (buffer[i-2] != '*' || buffer[i-1] != '/'))
                    {
                        i++;
                    }
                }
                else
                    return buffer + i;
                break;
            default:
                return buffer + i;
        }
    }
}

