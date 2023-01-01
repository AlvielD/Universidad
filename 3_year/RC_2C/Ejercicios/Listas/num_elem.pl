num_elem([], 0).
num_elem([_|Resto], N2) :- num_elem(Resto, N),
	N2 is N + 1.