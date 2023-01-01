function funcion_representa_datos(X, Y, espacioCcas, nombresProblema)

    % Separamos los campos de la variables de nombres en variables
    % diferentes
    descriptores = nombresProblema.descriptores;
    clases = nombresProblema.clases;
    colores = nombresProblema.colores;

    % Extraemos los valores usados para codificar las clases y cuantas hay
    valoresClases = unique(Y);
    numClases = length(valoresClases);
    
    figure;
    hold on;
    for i=1:numClases
       
        Xi = X(Y==valoresClases(i), espacioCcas);
        if length(espacioCcas) == 2
            plot(Xi(:, 1), Xi(:, 2), colores{i});
        else
            plot3(Xi(:, 1), Xi(:, 2), Xi(:, 3), colores{i});
        end
        
    end
    
    legend(clases);
    xlabel(descriptores{espacioCcas(1)});
    ylabel(descriptores{espacioCcas(2)});
    
    if length(espacioCcas) == 3
        zlabel(descriptores{espacioCcas(3)});
    end    

end

