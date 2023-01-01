% cuenta_nodos(+Arbol, -N)
% es cierto si N unifica con el número de nodos
% de Arbol

cuenta_nodos(nil, 0). % Caso base
cuenta_nodos(a(_, SubAI, SubAD), N) :- 
	cuenta_nodos(SubAI, NI),
	cuenta_nodos(SubAD, ND),
    N is NI+ND+1.