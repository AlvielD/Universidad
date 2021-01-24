.MODEL SMALL
.CODE
	org 100h

Programa_Int:
	JMP  Reside
	;Declaracion de variables
	cont db 0
	MSJ db '00:00'

Rutina_Servicio PROC
	CLI ;Clear biestable Interrump
	;Salvar el contenido de los registros implicados en la rutina
	;GUARDAMOS LOS CONTENIDOS DE LOS REGISTROS EN LA PILA DE DATOS
	PUSH AX
	PUSH BX
	PUSH CX
	PUSH DX
	
	INC cont
	CMP cont, 18	;18,2 interrupciones implican 1 seg en el procesador por lo tanto solo se incrementará el reloj cada 18 interrupciones
	JB salir
	MOV cont, 0
		
	;Reloj formato 00:00
	;0B800:[0], '0'
	;0B800:[1], ATRIBUTO

	MOV AX, 0B800h
	MOV ES, AX
	MOV AH, 00001111b	;Movemos el formato al registro AH
	MOV SI, 0 ;Para asegurarnos que SI está inicializado a 0
	MOV DI, 150	;(Creo que estoy indicando en que posición de la pantalla quiero mostrar el reloj)
	
	ShowClock:
	
		MOV AL, MSJ[SI]
		MOV ES:[DI], AX
		INC SI		;Este salta de 1 en 1 que son las casillas del mensaje (Reloj)
		ADD DI, 2	;Este debe saltar de 2 en 2 para no mostrar los formatos
	
		CMP SI, 5	;Compara el número de veces incrementado SI con 5 para leer todo el mensaje
		
	JNE ShowClock
	
	INC MSJ[4]
	
	;Ahora controlamos el reloj
		CMP MSJ[4], '9'
		JBE Salir	;Salta si es menor o igual
		MOV MSJ[4], '0'
		INC MSJ[3]
		
		CMP MSJ[3], '5'
		JBE Salir	;Salta si es menor
		MOV MSJ[3], '0'
		INC MSJ[1]
		
		CMP MSJ[1], '9'
		JBE Salir	;Salta si NO es igual
		MOV MSJ[1], '0'
		INC MSJ[0]
	Salir:

	;Recupera el valor de los registros implicados en la rutina
	;Retomamos los valores que hay en los registros
	POP DX
	POP CX
	POP BX
	POP AX
	
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