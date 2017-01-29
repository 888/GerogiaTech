;;===============================
;;Name: Noam Lerner
;;===============================

.orig x3000

MAIN
LD R6, STACK ;initialize stack pointer
LD R0, N
ADD R6, R6, #-1 		; Increment pointer
STR R0, R6, #0	
LD R0, M
ADD R6, R6, #-1 		; Increment pointer
STR R0, R6, #0	
JSR GCD
LDR R0, R6, #0
ST  R0, ANSWER
HALT

STACK .fill xF000
N .fill 2688	; input n
M .fill 768		; input m
ANSWER .fill 0  ; save your answer here

GCD
			STR R7, R6, #-2			; store ret add
			STR R5, R6, #-3			; store fp
			ADD R6, R6, #-3			; incremenet pointer
			ADD R5, R6, #-1 		; R5 = new frame pointer
			LDR R1, R5, #4			; m-->r1		
			LDR R2, R5, #5 			; n --> r2
			NOT R1, R1				; negate r1
			ADD R1, R1, 1			; twos comp neg
			ADD R0, R1, R2 			;  n-m
			BRn RECURSEM
			BRp RECURSEN
			STR R2, R5, #3 	 		; return value = n
RETURN  	LDR R7, R5, #2 			; return address --> r7
			ADD R6, R5,	#3			; reset pointer
			LDR R5, R5, #1 			; R5 is old frame pointer
			RET
RECURSEM	NOT R0, R0 	
			ADD R0, R0, 1 			; n-m --> m-n
			STR R2, R6, #-1			; push n
			STR R0, R6, #-2 		; push m-n
			ADD R6, R6, #-2 		; pointer
			JSR GCD
			LDR R0, R6, #0 			; get return value
			STR R0, R5, #3 			; store return value
			BR RETURN
RECURSEN 	NOT R1, R1 	
			ADD R1, R1, 1 			; -m -> m
			STR R0, R6, #-1 		; push n-m
			STR R1, R6, #-2 		; push m
			ADD R6, R6, #-2 		; pointer
			JSR GCD
			LDR R0, R6, #0 			; get return value
			STR R0, R5, #3 			; store return value
			BR RETURN

.end
