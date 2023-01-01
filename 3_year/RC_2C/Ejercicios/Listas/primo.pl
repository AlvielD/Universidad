%------------------------------------------------
% lista_divisores(+X, +Y, -ListaR).
% es cierto cuando ListaR unifica con una lista
% que contiene a los números cuyo resto
% de la división entera de X entre Z es igual a 0
% para valores de Z entre 1 e Y.
% lista_divisores(10, 10, R)
% R = [1,2,5]
%-------------------------------------------------

% Caso base
lista_divisores(_, 1, [1]).

% SI EL ELEMENTO ES DIVISOR
lista_divisores(X, Y, [Y|R]) :- 
	Y > 1,
	Ind is Y - 1,
	lista_divisores(X, Ind, R),
	0 is X mod Y.
	
% SI EL ELEMNTO NO ES DIVISOR
lista_divisores(X, Y, R):-
	Y > 1,
	Ind is Y-1,
	lista_divisores(X, Ind, R),
	Z is X mod Y, Z \== 0.

%---------------------------------------------------
% primo(+X)
% es cierto si X unifica con un número primo.
%---------------------------------------------------

primo(X) :- lista_divisores(1, X, [X, 1]).

%---------------------------------------------------
% primosEntrexy(+X, +Y, -ListaR)
% es cierto cuando ListaR unifica con una lista
% que contiene a los primos que van desde X hasta
% Y ambos incluidos en orden ascendente.
%---------------------------------------------------

primosEntrexy(X, X, []).

primosEntrexy(X, Y, [X|R]) :- 
	X<Y,
	Aux is X+1,
	primo(X),
	primosEntrexy(Aux, Y, R).
 
primosEntrexy(X,Y, R):- 
	X<Y,
	Aux is X+1,
	\+ primo(X),
	primosEntrexy(Aux,Y, R).

