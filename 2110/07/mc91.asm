;;===============================
;; Name: Noam Lerner
;;===============================

.orig x3000

	LD R6, STACK
	LD R0, N
	ADD R6, R6, #-1 		; Increment pointer
		STR R0, R6, #0			; push
	JSR MC91 				; call MC91 
	LDR R0, R6, #0
	ST  R0, ANSWER
	HALT

STACK	.fill xF000
N	.fill 99       ;input n
ANSWER	.blkw 1    ;save your answer here
HUND 	.fill #-100

MC91
		ADD R6, R6, #-1 		; Increment pointer
		STR R0, R6, #0			; push 				; clear spot for return val
		ADD R0, R7, #0 			; ready return address for push
		ADD R6, R6, #-1 		; Increment pointer
		STR R0, R6, #0			; push 				; push return address
		ADD R0, R5, #0 			; ready old frame pointer for push
		ADD R6, R6, #-1 		; Increment pointer
		STR R0, R6, #0			; push 				; push old frame pointer
		ADD R5, R6, #-1 		; R5 = new frame pointer
		LDR R0, R5, #4			; arg --> R0
		LD R1, HUND 			; R1 = -100
		ADD R1, R1, R0 			; R0 - 100
		BRnz RECURSE			; R0 <= 100 recurse
		ADD R0, R0, #-10 		; R0 = R0 -10
		STR R0, R5, #3			; Store return value
		ADD R6, R5, #3
		LDR R7, R5, #2			; get return address
		LDR R5, R5, #1			; restore frame pointer
		RET 					; return
RECURSE ADD R0, R0, #11 		; R0 = R0 +11
		ADD R6, R6, #-1 		; Increment pointer
		STR R0, R6, #0			; push 				; store arg
		JSR MC91 				; call MC91
		LDR R0, R6, #0 			; get return value
		ADD R6, R6, #-1 		; Increment pointer
		STR R0, R6, #0			; push 				; push arg
		JSR MC91 				; call mc91 again
		LDR R0, R6, #0 			; load return value
		STR R0, R5, #3			; store return value
		LDR R7, R5, #2 			; return address --> r7
		ADD R6, R5,	#3			; reset pointer
		LDR R5, R5, #1 			; R5 is old frame pointer
		RET
.end


