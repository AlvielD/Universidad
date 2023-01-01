function [IEtiq, N] = funcion_etiquetar(Ib)
	
	% Inicialización de valor 1 a una única etiqueta
	%-----------------------------------------------
	Ib = double(Ib);
	N = 0;
	[f, c] = size(Ib);
	for i=1:f
		for j=1:c
			if (Ib(i,j) > 0)
				N = N+1;
				Ib(i,j) = N;
			end
		end
	end
	%-----------------------------------------------
	
	% Etiquetado
	%-----------------------------------------------
	cambio = true;
	IEtiq = double(zeros(f+2, c+2));
	IEtiq(2:end-1, 2:end-1) = Ib;
	while(cambio == true)
	
		% Iteración de arriba-abajo
		%-----------------------------------------------
		cambio = false;
		for i=2:f+1
			for j=2:c+1
				if (IEtiq(i,j) ~= 0)
					V = [IEtiq(i, j-1), IEtiq(i-1, j)];
					V = V(V ~= 0);
					M = min(V);
					if IEtiq(i,j) ~= M
						cambio = true;
						IEtiq(i,j) = M;
					end
				end
			end
		end
		%-----------------------------------------------
		
		% Iteración de abajo-arriba
		%-----------------------------------------------
		for i=f+1:-1:2
			for j=c+1:-1:2
				if (IEtiq(i,j) ~= 0)
					V = [IEtiq(i, j+1), IEtiq(i+1, j)];
					V = V(V ~= 0);
					M = min(V);
					if IEtiq(i,j) ~= M
						cambio = true;
						IEtiq(i,j) = M;
					end
				end
			end
		end
		%-----------------------------------------------
	end
	%-----------------------------------------------
	
	Ib = IEtiq(2:end-1, 2:end-1);
	valores = unique(Ib);
	valores = valores(valores ~= 0);
	for i=1:length(valores)
		Iaux = Ib == valores(i);
		Ib(Iaux) = i;
	end

	N = length(valores);
	IEtiq = Ib;
end