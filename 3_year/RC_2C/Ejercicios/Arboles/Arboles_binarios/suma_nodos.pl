% ----------------------------------------------
% suma_nodos(+Arbol, -N) es cierto si N unifica
% con todas las etiquetas de Arbol.
% ----------------------------------------------

% Caso base
suma_nodos(nil, 0).

suma_nodos(a(Elem, SubAI, SubAD), N) :-
	suma_nodos(SubAI, NI),
	suma_nodos(SubAD, ND),
	N is NI+ND+Elem.
	