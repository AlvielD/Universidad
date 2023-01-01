%-----------------------------------------------------
% ordena_insercion(+Lista, -ListaR).
% es cierto cuando ListaR unifica con una lista que
% contiene los mismos elementos que Lista ordenados
% de menor a mayor.
%-----------------------------------------------------

ordena_insercion([], []).

ordena_insercion([E1|Resto], ListaR) :-
	ordena_insercion(Resto, R),
	inserta(E1, R, ListaR).
	
%-----------------------------------------------------
% inserta_en_list_ord(+Elem, +Lista, -ListaR).
% es cierto cuando ListaR unifica con una lista
% que contiene los elementos de la lista ordenada
% Lista, con el elemento Elem insertado de forma
% ordenada.
%-----------------------------------------------------

inserta(Elem, [], [Elem]).

% Si la lista está ordenada se aplica esta regla
inserta(Elem, [Cab|Resto], [Elem,Cab|Resto]) :-
	Elem =< Cab.

% Si la lista no está ordenada se aplica esta regla
inserta(Elem, )