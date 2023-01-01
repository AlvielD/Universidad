% ------------------------------------------
%	lista_hojas(+Arbol_binario, ?Lista_hojas)
%	es cierto cuando Lista_hojas unifica con 
%   una lista que contiene las etiquetas 
%   de las hojas del árbol Arbol_binario
% ------------------------------------------

lista_hojas(nil, []).
lista_hojas(a(A, nil, nil), [A]).

lista_hojas(a(_, SubAI, SubAD), L) :-
	\+ hoja(a(_, SubAI, SubAD))
	lista_hojas(SubAI, LI),
	lista_hojas(SubAD, LD),
	append(LI, LD, L).
	
% hoja(+A)
% cierto si A es una hoja.

hoja(a(_, nil, nil)).

