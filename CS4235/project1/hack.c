#include <stdio.h>
#include <string.h>

void grant_access(){
    printf ("\n Root privileges given to the user \n");
}
void deny_access(){
    printf ("\n Wrong Password \n");
}

void login(){
    char buff[8];

    printf("\n Enter the password : \n");
    gets(buff);
    printf("%s\n",buff );

    if(strcmp(buff, ""))
    {
        deny_access();
    }
    else
    {
        grant_access();
    }
}

int main(int argc, char *argv[]){
    login();
    return 0;
}
