% -------------------------------------------------
% lista_hojas_gen(+Arbol, -Lista)
% es cierto si Lista unifica con una lista que
% contiene las etiquetas de las hojas del árbol
% genérico Arbol
% -------------------------------------------------

% Caso base
lista_hojas_gen(a(E, []), [E]).

lista_hojas_gen(a(_, Hijos), R) :-
	Hijos \= [],
	lista_hojas_gen_lista(Hijos, R).
	
% ------------------------------------------------------
% lista_hojas_gen_lista(+ListaHijos, -ListaHojas)
% es cierto si ListaHojas unifica con la lista de hojas
% de la lista de hijos ListaHijos
% ------------------------------------------------------

lista_hojas_gen_lista([Cab|Resto], R) :-
	lista_hojas_gen(Cab, L1),
	lista_hojas_gen_lista(Resto, L2),
	append(L1, L2, R).