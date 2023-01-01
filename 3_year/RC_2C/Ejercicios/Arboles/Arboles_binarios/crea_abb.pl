% --------------------------------------------------------
% crea_abb(+Lista, -Arbol) es cierto cuando Arbol unifica
% con un árbol binario de búsqueda con los elementos de
% Lista
% --------------------------------------------------------

% Suponemos que la lista está ordenada, en caso de no estarla
% tendríamos que llamar a msort.

% Caso base
crea_abb([], nil).
crea_abb(Lista, a(Et, SubAI, SubAD)) :-
	length(Lista, Tam),
	Mitad is Tam div 2,
	length(L1, Mitad),
	append(L1, [Et|L2], Lista),
	crea_abb(L1, SubAI),
	crea_abb(L2, SubAD).
	
% Utilizamos la reversibilidad de los predicados length y append
% para dividir la lista por la mitad.
