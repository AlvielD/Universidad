% ----------------------------------------------
% crea_ag(+Lista, -ArbolG) es cierto si ArbolG
% unifica con un arbol generico cuyas etiquetas
% corresponden con la lista de valores pasada
% ----------------------------------------------


% crea_ag([Cab|Resto], a(Cab, Resto)).

% Caso base para 1 elemento
crea_ag([E], a(E, [])).

% Caso base para 2 elemento
crea_ag([E1, E2], a(E1, [a(E2, [])])).

% Caso para 3 o más elementos
crea_ag([Cab|Lista], a(Cab, [SubAI, SubAD])) :-
	length(Lista, Tam),
	Tam >= 2,
	Mitad is Tam div 2,
	length(L1, Mitad),
	append(L1, L2, Lista),
	crea_ag(L1, SubAI),
	crea_ag(L2, SubAD).
	
% Utilizamos la reversibilidad de los predicados length y append
% para dividir la lista por la mitad.