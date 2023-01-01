%------------------------------------------------------
% comprime(+Lista, -Resultado)
% es cierto si Resultado unifica con una lista
% de la siguiente forma:
%
% comprime([a,a,a,b,b,c,d,d], R)
% R = [(a,3),(b,2),(c,1),(d,2)]
%------------------------------------------------------

comprime([], []).
comprime([A], [(A,1)]).

% Si el siguiente elemento es el mismo
comprime([Cab,Cab|Resto], [(Cab,N2)|L]) :- 
	comprime([Cab|Resto], [(Cab,N1)|L]),
	N2 is N1 + 1.
	
% Si el siguiente elemento es distinto
comprime([Cab1,Cab2|Resto], [(Cab1,1)|L]) :-
	Cab1 \= Cab2,
	comprime([Cab2|Resto], L).