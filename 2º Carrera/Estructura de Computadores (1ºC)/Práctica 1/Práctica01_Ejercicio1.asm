data segment 

cadena db 0,1,0,1
peso db 8,4,2,1
valor_decimal db 0

ends

stack segment
    dw   128  dup(0)
ends

code segment
start:

     mov ax, seg cadena ;Inicializamos el segmento de datos
     mov ds, ax
     
     ;valor decimal = cadena[0]*peso[0] + cadena[1]*cadena[1] + 
     ;cadena[2]*peso[2] + cadena[3]*peso[3]  
     
     mov al, cadena[0]
     mul peso[0]    ; AX = cadena[0]*peso[0]
     
     mov bx, ax ; BX = cadena[0]*peso[0]
     
     mov al, cadena[1]
     mul peso[1]
     
     add bx, ax ; BX = cadena[0]*peso[0] + cadena[1]*peso[1]
     
     mov al, cadena[2]
     mul peso[2]
     
     add bx, ax
     
     mov al, cadena[3]
     mul peso[3]
     
     add bx, ax ;Bingo!  
     
     mov valor_decimal, bl

mov ax, 4c00h
int 21h  

ends

end start
             