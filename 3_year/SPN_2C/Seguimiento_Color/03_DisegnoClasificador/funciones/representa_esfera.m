function representa_esfera(centroide, radio)
    
    [R,G,B] = sphere(100);
    % Matrices de puntos de una esfera centrada en el origen de radio
    % unidad
    x = radio * R(:) + centroide(1);
    y = radio * G(:) + centroide(2);
    z = radio * B(:) + centroide(3);
    plot3(x,y,z, '.y')
    
end