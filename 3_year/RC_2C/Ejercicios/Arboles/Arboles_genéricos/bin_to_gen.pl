% -------------------------------------------------
% bin_to_gen(+ArbolBin, -ArbolGen)
% es cierto si ArbolGen unifica con un árbol
% genérico que tiene la misma estructura que el
% árbol binario ArbolBin.
% -------------------------------------------------

bin_to_gen(a(E, nil, nil), a(E, [])).

bin_to_gen(a(E, SubAI, SubAD), a(E, [SubAIG, SubADG])) :-
	SubAI \= nil,
	SubAD \= nil,
	bin_to_gen(SubAI, SubAIG),
	bin_to_gen(SubAD, SubADG).
	
bin_to_gen(a(E, SubAI, nil), a(E, [SubAIG])) :-
	SubAI \= nil,
	bin_to_gen(SubAI, SubAIG).
	
bin_to_gen(a(E, nil, SubAD), a(E, [SubADG])) :-
	SubAD \= nil,
	bin_to_gen(SubAD, SubADG).