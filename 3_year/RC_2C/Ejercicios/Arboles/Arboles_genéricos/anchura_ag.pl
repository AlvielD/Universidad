% anchura(+Ag, -Lista)
% es cierto si Lista unifica con la lista
% que contiene a todas las etiquetas de Ag
% resultado de recorrer el árbol en anchura.
%
%          1
%        / | \
% 		2  3  4
%	   / \ |   \
%     5  6 7    8
%
% Recorrido en anchura: 1,2,3,4,5,6,7,8
% a(1, [a(2, [a(5, []), a(6, [])]), a(3, [a(7, [])]), a(4, [a(8, [])])])
%

% Árbol de prueba
arbol1(a(1, [a(2, [a(5, []), a(6, [])]), a(3, [a(7, [])]), a(4, [a(8, [])])])).

anchura(a(E,ListaH), [E|R]) :-
	anchura_lista(ListaH, R).
	
% anchura_lista(+ListaHijos, -Lista)
% es cierto si Lista unifica con la lista de
% etiquetas que aparecen en la lista de árboles
% hijos ListaHijos recorridos en achura

anchura_lista([], []).

anchura_lista([Cab|Resto], R) :-
	raices_hijos([Cab|Resto], Raices, Hijos),
	anchura_lista(Hijos, Rhijos),
	append(Raices, Rhijos, R).

% raices_hijos(+Lista, -ListaRaices, -ListaHijos).	
% es cierto si ListaRaices unifica con las raíces
% y ListaHijos con los hijos, correspondientemente,
% de la lista de árboles Lista.

raices_hijos([],[],[]).
	
raices_hijos([a(E,ListaH)|Resto], [E|Raices], Hijos) :-
	raices_hijos(Resto, Raices, HijosL),
	append(ListaH, HijosL, Hijos).
