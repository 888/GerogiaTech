#include <stdio.h>
#include <string.h>
#include <stdlib.h>
	
int main(int argc, char **argv) {
	char *text = (char *)malloc(4);
	char *command = (char *) malloc(200);

	printf("%s\n",text );

	strcpy(command, "ls");

	strcpy(text, argv[1]);
	system(command);
	return 0;
}