% permuta(+Lista, -ListaR).
% es cierto cuando ListaR unifica con una lista
% que contiene los elementos de Lista en orden
% distinto. Este predicado genera todas las
% listas posibles por backtraking.
%
% permuta([1,2,3], R)
% R = [1,2,3];
% R = [1,3,2];
% R = [2,1,3];
% R = [2,3,1];
% R = [3,1,2];
% R = [3,2,1];


permuta([], []).
permuta([Cab|Resto], L1) :- permuta(Resto, L2), insertar(Cab, L2, L1).

% Es cierto cuando R unifica con una lista donde sus componentes son los mismos, pero añadiendo E
	
insertar(E, L, [E|L]).
insertar(E, [Cab|Resto], [Cab|R]) :- insertar(E, Resto, R).