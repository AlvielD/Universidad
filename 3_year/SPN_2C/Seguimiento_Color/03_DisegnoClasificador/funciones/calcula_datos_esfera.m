function datos_esfera = calcula_datos_esfera(XColor, XFondo)
    
    % Trasponemos nuestras nubes de puntos para hacer los c�lculos
    NPColor = XColor';
    NPFondo = XFondo';

    % Calculamos el centroide de la esfera
    centroide_esfera = mean(XColor);
    
    % Sacamos las distancias del centroide de la esfera a la nube de puntos
    % del color de seguimiento. Nuestro radio será el punto más alejado.
    P = centroide_esfera';
    Pamp = repmat(P, 1, size(XColor, 1));
    
    Distancia = sqrt(sum((Pamp - NPColor).^2));
    
    radio1 = max(Distancia);
    
    % Sacamos las distancias del centroide de la esfera a la nube de puntos
    % de color de fondo. Nuestro radio será el punto más cercano.
    Pamp = repmat(P, 1, size(XFondo, 1));
    
    Distancia = sqrt(sum((Pamp - NPFondo).^2));
    
    radio2 = min(Distancia);
    
    % Establecemos el tercer radio como una media de los dos anteriores.
    radio12 = (radio1+radio2)/2;
    
    datos_esfera = [centroide_esfera, radio1, radio2, radio12];

end