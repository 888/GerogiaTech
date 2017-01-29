;;===============================
;;Name: Noam Lerner
;;===============================

.orig x3000

	LD	R3,A 		; Store A 
	LD	R4,B  		; Store B
	NOT R3,R3		; A=!A
	NOT R4,R4		; B=!B
	AND R7,R4,R3 	; answer = A & B
	NOT R7,R7		; answer = !answer
	ST 	R7,ANSWER 	; Set answer

	
A       .fill 6
B       .fill 13
ANSWER  .fill 0		; This answer should contain A | B when finished.
.end
