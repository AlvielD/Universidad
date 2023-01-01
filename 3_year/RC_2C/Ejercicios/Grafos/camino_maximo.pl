% Grafo del ejercicio
grafo1(grafo([a,b,c,d,e], [arista(a,b),
					arista(a,e),
                    arista(b,c),
					arista(b,d),
					arista(b,e),
					arista(c,d),
					arista(c,e),
					arista(d,e)])).

/*
  conectado(+A,+B, +G).
  es cierto si los vertices A y B están conectados
  mediante una arista en el grafo G. Si el grafo 
  es dirigido la conexión está solo entre A y B. En
  el caso de los grafos no dirigidos la conexión va
  desde A hata B y desde B hasta A.
*/

% Incluir estos dos predicados permite grafos no dirigidos
% si eliminamos el segundo predicado hablariamos de grafos dirigidos
conectado(A,B, grafo(_,Aristas)) :- member(arista(A,B), Aristas).
conectado(A,B, grafo(_,Aristas)) :- member(arista(B,A), Aristas).

/*
camino(+grafo(V,A), +Ini, +Fin, +Visitados, -Camino, -Peso).
  es cierto si Camino unifica con una lista de 
  vertices o aristas que representa el camino que
  va desde Ini hasta Fin en el grafo(V,A) y donde
  no se repiten los vertices o aristas de que están
  en la lista Visitados. El Peso unifica con coste total
  del camino  
*/  

% Camino más corto posible (Caso base)
camino(_, Ini, Ini, _, []).

camino(grafo(_,A), Ini, Fin, Visitados, [arista(Ini,TMP)|Camino]):-
  conectado(Ini, TMP, grafo(_,A)),
  \+ member(arista(Ini, TMP), Visitados),
  camino(grafo(_,A), TMP, Fin, [arista(Ini,TMP),
								arista(TMP,Ini)|Visitados], Camino).

camino_max(grafo(V,A), Camino) :-
	member(P,V),
	camino(grafo(_,A), P, _, [], Camino),
	length(Camino, 8).
	
% Podemos obtener el número de soluciones con bagof y length
% grafo1(G), bagof(C, camino_max(G, C), R), length(R, N).