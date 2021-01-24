data segment 

cadena db 5,0,0,0,0,0,0
peso db 8,4,2,1    
mensaje1 db "Introduce un numero en binario: $"  
mensaje2 db "El resultado en binario con signo es: $"
mensaje3 db "El resultado en hexadecimal es: $"
binsig db 0
hex db 0

ends

stack segment
    dw   128  dup(0)
ends

code segment
start:   
          
;------------------------------------------------------------------

mov ax, seg cadena ;Inicializamos el segmento de datos
mov ds, ax

;------------------------------------------------------------------
;Mensaje para pedir pantalla los numeros en binario    

   mov ah, 09h    ; Funcion para imprimir mensaje por pantalla
   lea dx, mensaje1; Cargar la direccion del mensaje en el registro dx
   int 21h        ; Ejecutar la interrupcion 21h que llama al DOS
   
;------------------------------------------------------------------

     
mov dx, offset cadena; Pedimos la cadena por teclado
mov ah, 0Ah    ;Interrupcion para pedir por pantalla
int 21h  
     
;------------------------------------------------------------------

;Limpiamos la pantalla
mov al, 03h
mov ah, 00h
int 10h                                                            

;------------------------------------------------------------------ 
     
;BINARIO CON SIGNO:

;Primero restamos 48 al valor de la cadena
;para pasar de ascii a binario

sub cadena[2], 48d
sub cadena[3], 48d
sub cadena[4], 48d
sub cadena[5], 48d

;Ahora multiplicamos cada digito de la
;cadena por su peso excepto el 2, que sera
;el bit de signo     
     
mov al, cadena[3]
mul peso[1]

mov bx, ax
     
mov al, cadena[4]
mul peso[2]
     
add bx, ax
     
mov al, cadena[5]
mul peso[3]
     
add bx, ax
     
mov binsig, bl

;------------------------------------------------------------------

mov ah, 09h         ; Funcion para imprimir mensaje por pantalla
lea dx, mensaje2    ; Cargar la direccion del mensaje en el registro dx
int 21h             ; Ejecutar la interrupcion 21h que llama al DOS

;------------------------------------------------------------------ 

;Comparamos el valor de cadena[2] con 1
;para comprobar el signo del numero

;mov al, cadena[2]
cmp cadena[2], 1
je Negativo:

;------------------------------------------------------------------

;SI ES POSITIVO, no realizara el salto y
;el codigo se seguira ejecutando por aqui

;Movemos a ES la direccion de la memoria
;de video
 
mov ax, 0B800h
mov es, ax

mov al, '+'
mov ah, 00000111b
mov es:[76], ax

mov al, binsig
add al, 48
mov ah, 00000111b
mov es:[78], ax

jmp continuacion:
 

;Esta ultima instruccion hara al codigo
;saltarse la parte negativa para no
;ejecutarla   

;------------------------------------------------------------------

;SI ES NEGATIVO,el codigo seguira por
;aqui

Negativo:

;Movemos a ES la direccion de la memoria
;de video

mov ax, 0B800h
mov es, ax

mov al, '-'
mov ah, 00000111b
mov es:[76], ax

mov al, binsig
add al, 48
mov ah, 00000111b
mov es:[78], ax 

;Una vez mostrado el binario con signo
;el codigo seguira por aqui

continuacion:
;------------------------------------------------------------------
;Interrupcion para pedir una tecla para continuar
 
espacio:

mov ah, 00h
int 16h

cmp al, 32
jne espacio:

;------------------------------------------------------------------

;Limpiamos la pantalla
mov al, 03h
mov ah, 00h
int 10h                                                            

;------------------------------------------------------------------

;Iniciamos el registro a 0 para evitar problemas        
mov ax, 0

;Movemos el valor de binario con signo al
;registro al para sumarle el valor del bit
;de signo y una vez hecho esto, compararemos
;el valor con el 10 para saber si debemos
;o no pasar el valor a una letra

mov al, cadena[2]
mul peso[0]
;Se le suma a bl, que es el registro que contiene a bl
;(Por lo tanto no hace falta moverlo)
add bl, al
mov hex, bl
cmp bx, 10
jae letras:

;------------------------------------------------------------------

;El codigo seguira por aqui si el numero es
;inferior a 10 pues no sera una letra

mov ah, 09h    ; Funcion para imprimir mensaje por pantalla
lea dx, mensaje3; Cargar la direccion del mensaje en el registro dx
int 21h        ; Ejecutar la interrupcion 21h que llama al DOS
   

;Movemos a ES la direccion de la memoria
;de video
 
mov cx, 0B800h
mov es, cx

add bl, 48
mov cl, bl
mov ch, 00000111b
mov es:[64], cx

jmp findeprograma:

;------------------------------------------------------------------

;El codigo seguira por aqui si el numero es >= 10
;pues entonces sera una letra

letras:

mov ah, 09h    ; Funcion para imprimir mensaje por pantalla
lea dx, mensaje3; Cargar la direccion del mensaje en el registro dx
int 21h        ; Ejecutar la interrupcion 21h que llama al DOS 

mov cx, 0B800h
mov es, cx

add bl, 55
mov cl, bl
mov ch, 00000111b
mov es:[64], cx


;------------------------------------------------------------------ 


findeprograma: 

mov ax, 4c00h
int 21h  

ends

end start