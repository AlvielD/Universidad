function funcion_representa_datos(X, Y)
    
    valoresY = unique(Y);   % Valores posibles del vector Y
    color = {'*b', '*m', '*g', '*r'};   % Celda de colores disponibles en la grafica

    for i=1:length(valoresY)
        FoI = Y == valoresY(i);

        % Extraemos las componentes de interes
        RoI = X(FoI, 1);
        GoI = X(FoI, 2);
        BoI = X(FoI, 3);

        % Representamos
        plot3(RoI, GoI, BoI, color{i});
        hold on;
    end

    % Ajustes de la gráfica
    title('Representacion de los datos');   % Titulo

    axis([0, 255, 0, 255, 0, 255]);         % Rango de cada eje

    % Descripción de cada eje
    xlabel('Componente roja');
    ylabel('Componente verde');
    zlabel('Componente azul');

end