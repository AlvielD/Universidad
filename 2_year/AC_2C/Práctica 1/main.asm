;PROGRAMA PRINCIPAL

data segment

	contR dw 0
	RETARDO equ 200
	color db 0

ends

assume cs:code, ds:data

code segment
	
start:

;----------------------------------------------------------
	MOV AL, 13h		;Interrupción para el modo gráfico
	MOV AH, 0
	INT 10h
;----------------------------------------------------------

	;MOV AH, 0
	;INT 16h

	MOV color, 00h
	
Dibuja:

	INC color
	MOV AX, 0A000h
	MOV ES, AX
	MOV AL, color		;Formato de color
	
	MOV SI, 63680d	;Posición del primer pixel del dibujo
	
;----------------------------------------------------------

;DIBUJO
;----------------------------------------------------------
pintaArriba1:

;-------Bucle de retardo----------
	bucleRelleno1:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno1
	
	MOV contR, 0
;---------------------------------

	MOV ES:[SI], AL
	SUB SI, 320
	CMP SI, 0
	
JNE pintaArriba1

pintaDerecha1:

;-------Bucle de retardo----------
	bucleRelleno2:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno2
	
	MOV contR, 0
;---------------------------------
	
	MOV ES:[SI], AL
	INC SI
	CMP SI, 319
	
JNE pintaDerecha1

pintaAbajo1:

;-------Bucle de retardo----------
	bucleRelleno3:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno3
	
	MOV contR, 0
;---------------------------------

	MOV ES:[SI], AL
	ADD SI, 320
	CMP SI, 63999

JNE pintaAbajo1

pintaIzquierda1:

;-------Bucle de retardo----------
	bucleRelleno4:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno4
	
	MOV contR, 0
;---------------------------------

	MOV ES:[SI], AL
	SUB SI, 1
	CMP SI, 63877
	
JNE pintaIzquierda1

pintaArriba2:

;-------Bucle de retardo----------
	bucleRelleno5:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno5
	
	MOV contR, 0
;---------------------------------

	MOV ES:[SI], AL
	SUB SI, 320
	CMP SI, 38917
	
JNE pintaArriba2

pintaDerecha2:

;-------Bucle de retardo----------
	bucleRelleno6:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno6
	
	MOV contR, 0
;---------------------------------
	
	MOV ES:[SI], AL
	INC SI
	CMP SI, 38963
	
JNE pintaDerecha2

pintaAbajo2:

;-------Bucle de retardo----------
	bucleRelleno7:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno7
	
	MOV contR, 0
;---------------------------------

	MOV ES:[SI], AL
	ADD SI, 320
	CMP SI, 48883
	
JNE pintaAbajo2

pintaIzquierda2:

;-------Bucle de retardo----------
	bucleRelleno8:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno8
	
	MOV contR, 0
;---------------------------------
	
	MOV ES:[SI], AL
	SUB SI, 1
	CMP SI, 48866
	
JNE pintaIzquierda2

pintaArriba3:

;-------Bucle de retardo----------
	bucleRelleno9:
		
		INC contR
		CMP contR, RETARDO
		
	JNE bucleRelleno9
	
	MOV contR, 0
;---------------------------------

	MOV ES:[SI], AL
	SUB SI, 320
	CMP SI, 44706
	
JNE pintaArriba3
;----------------------------------------------------------

JMP Dibuja

findeprograma:

MOV AX, 4c00h
INT 21h

ends

end start