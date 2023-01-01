% ---------------------------------------------
% inorden(+Arbol, -R) es cierto si R unifica
% con una lista que contiene el recorrido
% inorden de Arbol.
% ---------------------------------------------

inorden(nil, []).

inorden(a(E, SubAI, SubAD), Lista) :-
	inorden(SubAI, ListaI),
	inorden(SubAD, ListaD),
	append(ListaI, [E|ListaD], Lista).