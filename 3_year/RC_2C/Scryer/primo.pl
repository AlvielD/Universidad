:- module(lista_divisores, [primo/1, primosEntrexy/3])

lista_divisores(_, 1, [1]).

lista_divisores(X, Y, [Y|R]) :- 
	Y > 1,
	Ind is Y - 1,
	lista_divisores(X, Ind, R),
	0 is X mod Y.
	
lista_divisores(X, Y, R):-
	Y > 1,
	Ind is Y-1,
	lista_divisores(X, Ind, R),
	Z is X mod Y, Z \== 0.


primo(X) :- lista_divisores(X, X, [X, 1]).

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

