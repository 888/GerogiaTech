		ADD $a0, $zero, $zero
		ADDI $t0, $zero, 10
LOOP1:	ADDI $a0, $a0, 1
		BNE $a0, $t0, LOOP1
		STR $zero, 0($zero)
LOOP2:	LDR $a1, 0($zero)
		ADDI $a1, $a1, 1
		STR $a1, 0($zero)
		BNE $a1, $t0, LOOP2
		HALT