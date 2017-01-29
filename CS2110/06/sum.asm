;;===============================
;;Name: Noam Lerner
;;===============================

.orig x3000

		AND		R3,R3,#0	; Clear sum (R3)
		LD		R4,LENGTH 	; set counter (R4)
		LD		R5,ARRAY 	; Point to first element in array (R5)
		LDR 	R6,R5,#0	; Load element into R6

LOOP	ADD		R3,R3,R6	; Add item to sum
		ADD 	R5,R5,#1	; Point to next item
		LDR 	R6,R5,#0	; Load element into R6
		ADD 	R4,R4,#-1	; Decrement counter
		BRp		LOOP		; if counter is positive, loop
		ADD 	R3,R3,#0	; check if negative
		BRzp	SET 		; If positive or zero, return 
		NOT 	R3,R3		; if Negative, not r3 (first step in abs)
		ADD 	R3,R3,#1	; Add 1 to it (twos neg compliment)		
SET		ST 	R7,ANSWER 		; Set answer

		HALT
		
ARRAY   .fill x6000
LENGTH  .fill 10
ANSWER	.fill 0		; The answer should have the abs(sum) when finished.
.end

.orig x6000
.fill 8
.fill 9
.fill 7
.fill 0
.fill -3
.fill 11
.fill 9
.fill -9
.fill 2
.fill 9
.end
