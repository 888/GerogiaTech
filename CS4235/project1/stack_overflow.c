#include <stdio.h>
#include <string.h>

void allow_access(){
    printf ("\n Root privileges given to the user \n");
}

void deny_access(){
    printf ("\nIncorrect Password\n"); 
}
void login(pass){
    char buff[10];

    printf("\n Enter the password : \n");

    gets(buff);
    
    if(strcmp(buff, pass))
    {
        deny_access();
    }
    else
    {
        allow_access();
    }

}

int main(void)
{
    int pass = 0;
    login("password");

    
    return 0;
}
