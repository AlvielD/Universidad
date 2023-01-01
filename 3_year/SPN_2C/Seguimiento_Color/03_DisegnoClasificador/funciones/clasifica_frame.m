function Ib = clasifica_frame(I, centroide, radio)

    % Separamos las componentes de color de la imagen
    R = double(I(:,:,1));
    G = double(I(:,:,2));
    B = double(I(:,:,3));
    
    % Extraemos las coordenadas del centroide de la esfera
    Rc = centroide(1);
    Gc = centroide(2);
    Bc = centroide(3);
    
    % Calculamos la matriz distancia desde el centroide de la esfera
    % determinado por las coordenadas C = (Rc, Gc, Bc)
    MD = sqrt( (R - Rc).^2 + (G - Gc).^2 + (B - Bc).^2 );
    
    % Calculamos la imagen binaria que nos indica que puntos se encuentran
    % dentro de la esfera (aquellos que se encuentren a una distancia menor
    % que el radio)
    Ib = MD < radio;

end