% -----------------------------------
% miembro(+Arbol, +X) es cierto si X
% pertenece a Arbol
% -----------------------------------

arbol1(a(3, a(2, a(1, nil, nil), a(4, nil, nil)), a(4, nil, a(1, nil, nil)))).
arbol2(a(5, a(3, a(2, nil, nil), a(2, nil, nil)), a(6, a(0, nil, nil), a(3, nil, nil)))).

% caso base
miembro(Elem,a(Elem,_,_)).

miembro(Elem,a(ElemArbol,AI,_)) :-
  Elem \= ElemArbol,
  miembro(Elem,AI).

miembro(Elem,a(ElemArbol,_,AD)) :-
  Elem \= ElemArbol,
  miembro(Elem,AD).
