%------------------------------------------------------
% mas_veces(+Lista, -Elem, -Num)
% es cierto cuando Elem unifica con el elemento
% que se repite más veces en la lista Lista
% y Num unifica con el número de veces que se
% repite dicho elemento.
%------------------------------------------------------

% 1. Ordenar la lista

% 2. Comprimir la lista

% 3. Ordenar la lista atendiendo al valor
%    de la tupla que indica el número de veces
%    que se repite el elemento.
%    Otra opción sería buscar el mayor N

% 4. Acceder al último elemento de la lista
%    leer el elemento y número de veces que se
%    repite.


mas_veces(Lista, Elem, Num) :- msort(Lista, R_ord),
	comprime(R_ord, R_comp),
	mayor(R_comp, Elem, Num).
	
%------------------------------------------------------
% mayor(+Lista, -Elem, -Num)
% es cierto cuando Elem unifica con el elemento
% que se repite más veces en la lista Lista
% y Num unifica con el número de veces que se
% repite dicho elemento.
%------------------------------------------------------

mayor([],_,0).

mayor([(_,N1)|Resto], C2, N2) :- mayor(Resto, C2, N2),
	N2 > N1.
	
mayor([(C1,N1)|Resto], C1, N1) :- mayor(Resto, C2, N2),
	N2 =< N1.
	
%------------------------------------------------------
% comprime(+Lista, -Resultado)
% es cierto si Resultado unifica con una lista
% de la siguiente forma:
%
% comprime([a,a,a,b,b,c,d,d], R)
% R = [(a,3),(b,2),(c,1),(d,2)]
%------------------------------------------------------

comprime([], []).
comprime([A], [(A,1)]).

% Si el siguiente elemento es el mismo
comprime([Cab,Cab|Resto], [(Cab,N2)|L]) :- 
	comprime([Cab|Resto], [(Cab,N1)|L]),
	N2 is N1 + 1.
	
% Si el siguiente elemento es distinto
comprime([Cab1,Cab2|Resto], [(Cab1,1)|L]) :-
	Cab1 \= Cab2,
	comprime([Cab2|Resto], L).