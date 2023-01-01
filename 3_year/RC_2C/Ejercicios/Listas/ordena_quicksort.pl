% -----------------------------------------------------
% divide(+Elem, +Lista, -Menores, -Mayores)
% es cierto cuando Menores unifica con una lista que
% contiene los elemenentos de Lista que son menores
% o iguales que Elem y Mayores unifica con una lista
% que contiene los elementos de Lista que son
% mayores que Elem.
% ------------------------------------------------------

% CASO BASE
divide(_, [], [], []).

% MENOR QUE EL PIVOTE
divide(E, [Cab|Resto], [Cab|Menores], Mayores) :- Cab =< E,
	divide(E, Resto, Menores, Mayores).
	
% MAYOR QUE EL PIVOTE
divide(E, [Cab|Resto], Menores, [Cab|Mayores]) :- Cab > E,
	divide(E, Resto, Menores, Mayores).

% --------------------------------------------------	
% contiene los mismos elementos que Lista ordenados
% de menor a mayor.
% --------------------------------------------------

ordena_quick([], []).
ordena_quick([Cab|Resto], R) :-
	divide(Cab, Resto, Menores, Mayores),
	ordena_quick(Menores, Menores2),
	ordena_quick(Mayores, Mayores2),
	append(Menores2, [Cab|Mayores2], R).