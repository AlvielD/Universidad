% -----------------------------------------------
% cuenta_internos(+Arbol, -N)
% es cierto si N unifica con el número de nodos
% internos de árbol.
% -----------------------------------------------

arbol1(a(a, a(b, a(d, nil, nil), a(e, nil, a(f, nil, nil))), a(c, nil, a(g, nil, nil)))).

% Caso base
cuenta_internos(nil, 0).

% Si el nodo no es una hoja, se cuenta su raíz.
cuenta_internos(a(_, SubAI, SubAD), N) :-
	\+ hoja(a(_, SubAI, SubAD)),
	cuenta_internos(SubAI, NI),
	cuenta_internos(SubAD, ND),
	N is NI+ND+1.

% Si el nodo es una hoja, no se cuenta su raíz	
cuenta_internos(A, 0) :-
	hoja(A).


% -----------------------------------------------	
% hoja(+Arbol) es cierto si Arbol es un nodo hoja
% -----------------------------------------------

hoja(a(_, nil, nil)).
	
