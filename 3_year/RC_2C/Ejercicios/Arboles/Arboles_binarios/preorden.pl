% ---------------------------------------------
% preorden(+Arbol, -R) es cierto si R unifica
% con una lista que contiene el recorrido
% preorden de Arbol.
% ---------------------------------------------

preorden(nil, []).

preorden(a(E, SubAI, SubAD), Lista) :-
	preorden(SubAI, ListaI),
	preorden(SubAD, ListaD),
	append([E|ListaI], ListaD, Lista).