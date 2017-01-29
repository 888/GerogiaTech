;;===============================
;;Name: Noam Lerner
;;===============================

.orig x3000
		LD		R1, ARRAY 	; point to first element in counter
		AND		R2,R2,#0	; int k = 0

LOOP0	AND		R3,R3,#0	; int isSorted =0
		ADD 	R3,R3,#1 	;isSorted++	
		AND		R4,R4,#0	; int i = 0;
		ADD 	R4,R4,#1 	;i++
LOOP1	ADD		R5,R1,R4	; address of arr[i]
		LDR		R6,R5,#0	; Load arr[i]
		LDR		R7,R5,#-1	; Load arr[i-1]
		NOT 	R0,R7		; Flip arr[i]
		ADD 	R0,R0,#1	; Add 1 to it (twos neg compliment)	
		ADD 	R0,R0,R6	; Add arr[i-1] - arr[i]
		BRnz	BRLOOP 		; if arr[i] > arr[i-1] end loop iteration
		STR 	R6,R5,#-1	; put arr[i] in arr[i-1] slot
		STR		R7,R5,#0	; put arr[i-1] in arr[i] slot
		AND		R3,R3,#0	;isSorted = 0
BRLOOP	ADD 	R4,R4,#1 	;i++
		LD 		R0,LENGTH 	;LOAD LENGTH
		NOT 	R5,R0 		;NOT LENGTH (to negate sooon)
		ADD 	R5,R5,#1 	;-LENGTH
		ADD 	R6,R5,R2 	;k-LENGTH
		ADD 	R7,R6,R4 	;i +k-LENGTH = i-(LENGTH-k)
		BRn 	LOOP1 		;if i < (length-k) loop
		ADD		R3,R3,#0	;Check isSorted
		BRp 	DONE		; if isSorted,stop 
		ADD 	R2,R2,#1 	; k++	
		ADD 	R6,R5,R2 	;k-Length
		BRn		LOOP0		;if k < length, loop
DONE 	HALT

ARRAY   .fill x6000
LENGTH  .fill 12
.end

; This array should be sorted when finished.
.orig x6000
.fill -28
.fill -50
.fill -7
.fill -2
.fill -42
.fill -4
.fill -15
.fill -8
.fill -34
.fill 101
.fill -5
.fill 250
.end
