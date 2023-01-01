;RUTINA DE ATENCIÓN A LA INTERRUPCIÓN PARA NUESTRO BENCHMARK SINTÉTICO
.MODEL SMALL
.CODE
	org 100h
	
Programa_Int:
	JMP  Reside
	;Declaración de variables
	cont db 0
	
Rutina_Servicio	PROC
	CLI
	; Salvar el contenido de los registros implicados en la rutina
	
	INC cont
	CMP cont, 18
	JB Finalizar ;Salta si es inferior
		
		MOV AX, 4Ch 
		INT 21h
	
	Finalizar:
	
	;Recupera el valor de los registros implicados en la rutina
	STI
	IRET
ENDP
Reside:   ;etiqueta para determinar la dirección siguiente a la última de la rutina que debe quedar residente

	MOV DX, offset Rutina_Servicio
	MOV AX, 0
	MOV ES, AX
	MOV  SI, 1Ch*4
	CLI
	MOV ES:[SI], DX
	MOV ES:[SI+2], CS
	STI
	MOV DX, offset Reside
	INT 27h
	
END Programa_Int