function Io = funcion_visualiza(Ii, Ib, Color, flagRepresenta)
	
	[~, ~, m] = size(Ii);
	
	if m == 3
		R = Ii(:,:,1);
		G = Ii(:,:,2);
		B = Ii(:,:,3);
	else
		R = Ii;
		G = Ii;
		B = Ii;
	end
	
	R(Ib) = Color(1);
	G(Ib) = Color(2);
	B(Ib) = Color(3);
	
	Io = cat(3,R,G,B);
	
	if (nargin == 4)
		if (flagRepresenta)
			figure;
			imshow(Io);
		end
	end
end