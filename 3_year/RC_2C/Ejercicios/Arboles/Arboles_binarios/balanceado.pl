% ---------------------------------------------------
%  balanceado(+Arbol)
%  es cierto si Arbol está balanceado.
%  Para todo nodo la diferencia entre la altura
%  del sub-arbol de la derecha y el sub-arbol
%  de la izquierda es como máximo uno.
% ---------------------------------------------------

balanceado(nil).
% balanceado(a(_, nil, nil)).

balanceado(a(_, SubAI, SubAD)) :-
	altura(SubAI, HI),
	altura(SubAD, HD),
	H is abs(HI - HD),
	H =< 1,
	balanceado(SubAI),
	balanceado(SubAD).


% ---------------------------------------------------
%  altura(+Arbol, -H)
%  es cierto si H unifica con la altura del arbol
%  Arbol.
% ---------------------------------------------------

altura(nil, 0).
altura(a(_, nil, nil), 1).

altura(a(_, SubAI, SubAD), H) :-
	altura(SubAI, HI),
	altura(SubAD, HD),
	HI > HD,
	H is HI+1.
	
altura(a(_, SubAI, SubAD), H) :-
	altura(SubAI, HI),
	altura(SubAD, HD),
	HD >= HI,
	H is HD+1.
	