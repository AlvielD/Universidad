function centroides = funcion_calcula_centroides(IEtiq, N)
	
	centroides = double(zeros(N, 2));
	%areas = funcion_calcula_areas(IEtiq, N)
	
	for i=1:N
		
		Ib = (IEtiq == i);
		[f, c] = find(Ib);
		
		x = mean(c);
		y = mean(f);
		
		centroides(i, 1) = x;
		centroides(i, 2) = y;
		
	end

end