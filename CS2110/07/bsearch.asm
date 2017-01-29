;;===============================
;;Name: Noam Lerner
;;===============================

; Main
; Do not edit this function!

;@plugin filename=lc3_udiv vector=x80

.orig x3000

	LD R6, STACK	; Initialize stack pointer

	LD R0, ARR_PTR	; \ Load parameters
	AND R1, R1, 0	; |
	LD R2, ARR_LEN	; |
	ADD R2, R2, -1	; |
	LD R3, X	; /

	ADD R6, R6, -4	; \ Call BSEARCH
	STR R0, R6, 0	; |
	STR R1, R6, 1	; |
	STR R2, R6, 2	; |
	STR R3, R6, 3	; |
	JSR BSEARCH	; /

	LDR R0, R6, 0	; \ Pop return value and args off the stack
	ADD R6, R6, 5	; /

	ST R0, ANSWER

	HALT

STACK   .fill xF000 ; Bottom of the stack + 1
ARR_PTR .fill x6000 ; Pointer to the array of elements
ARR_LEN .fill 16
X       .fill 99    ; What to search for
ANSWER  .fill -999  ; Do NOT write to this label from the subroutine!


; To call UDIV, use TRAP x80
; Preconditions:
;    R0 = X
;    R1 = Y
; Postconditions:
;    R0 = X / Y
;    R1 = X % Y

BSEARCH
			LDR R0, R6, 1		; low
			LDR R1, R6, 2		; high
			STR R7, R6, #-2		; store ret add
			STR R5, R6, #-3		; store fp
			ADD R6, R6, #-3		; incremenet pointer
			ADD R5, R6, #-1 	; R5 = new frame pointer
			AND R3, R4, 0 		; mid = 0
			STR R3, R6, -1		; push r4
			ADD R6, R6, -1		; incremet pointer
			NOT R1, R1
			ADD R1, R1, 1 		; high -> -high
			ADD R2, R0, R1 		; low - high
			BRnz GBOUNDS
			AND R0, R0, 0
			ADD R0, R0, -1
			STR R0, R5, 3
			BR RETURN
GBOUNDS		NOT R1, R1
			ADD R1, R1, 1		; -high --> high
			ADD R0, R0, R1 		; low + high
			AND R1, R1, 0
			ADD R1, R1, 2
			TRAP x80			; (low + high)/2
			STR R0, R5, 0		; re-store mid
			LDR R3, R5, 4 		; array pointer
			ADD R1, R3, R0 		; pointer to array[mid]
			LDR R1, R1, 0 		; array[mid]
			LDR R2, R5, 7		; X
			NOT R1, R1
			ADD R1, R1, 1 		; array[mid] --> -array[mid]
			ADD R1, R1, R2 		; x - array[mid]
			BRn ARRGR
			BRp XGR
			STR R0, R5, 3 		; array[mid] = x, return mid
			BR RETURN
XGR 		ADD R6, R6, -4		; increment pointer
			ADD R0, R0, 1		; mid++
			STR R0, R6, 1 		; arg1 = mid+1
			LDR R0, R5, 6		; get high
			STR R0, R6, 2 		; arg2 = high
			BR RECURSE
ARRGR 		ADD R6, R6, -4		; increment pointer
			ADD R0, R0, -1		; mid--
			STR R0, R6, 2 		; arg2 = mid-1
			LDR R0, R5, 5		; get low
			STR R0, R6, 1 		; arg1 = low
RECURSE		STR R3, R6, 0 		; arg0 = array pointer
			STR R2, R6, 3 		; arg3 = X
			JSR BSEARCH
			LDR R0, R6, 0		; get return value
			STR R0, R5, 3		; store return value
			BR RETURN
			LDR R0, R6, #0 			; get return value
			STR R0, R5, #3 			; store return value
RETURN  	LDR R7, R5, #2 			; return address --> r7
			ADD R6, R5,	#3			; reset pointer
			LDR R5, R5, #1 			; R5 is old frame pointer
			RET

.end

.orig x6000

	.fill -45
	.fill -42
	.fill -30
	.fill -2
	.fill 6
	.fill 15
	.fill 16
	.fill 28
	.fill 51
	.fill 78
	.fill 99
	.fill 178
	.fill 200
	.fill 299
	.fill 491
	.fill 5103

.end

