% ---------------------------------------------
% postorden(+Arbol, -R) es cierto si R unifica
% con una lista que contiene el recorrido
% postorden de Arbol.
% ---------------------------------------------

postorden(nil, []).

postorden(a(E, SubAI, SubAD), Lista) :-
	postorden(SubAI, ListaI),
	postorden(SubAD, ListaD),
	append(ListaI, ListaD, ListaAux),
	append(ListaAux, [E], Lista).