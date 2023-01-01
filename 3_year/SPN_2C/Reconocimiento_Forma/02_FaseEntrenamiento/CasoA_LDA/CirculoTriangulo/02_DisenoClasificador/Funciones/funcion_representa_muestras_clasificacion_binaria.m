function funcion_representa_muestras_clasificacion_binaria(X, Y)

    valoresY = unique(Y);
    datosClas1 = X(Y==valoresY(1), :);
    datosClas2 = X(Y==valoresY(2), :);
    
    [~, numAtributos] = size(X);
    
    if numAtributos == 3
        
        plot3(datosClas1(:, 1), datosClas1(:, 2), datosClas1(:, 3), '*r');
        hold on;
        plot3(datosClas2(:, 1), datosClas2(:, 2), datosClas2(:, 3), '*b');
        
    else

        plot(datosClas1(:, 1), datosClas1(:, 2), '*r');
        hold on;
        plot(datosClas2(:, 1), datosClas2(:, 2), '*b');
        
    end

end

