function funcion_representa_datos(X, Y)
    
    valoresY = unique(Y);   % Valores posibles del vector Y
    color = {'*r', '*b'};   % Celda de colores disponibles en la gráfica

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
    title('Representación de los datos');   % Título

    axis([0, 255, 0, 255, 0, 255]);         % Rango de cada eje

    % Descripción de cada eje
    xlabel('Componente roja');
    ylabel('Componente verde');
    zlabel('Componente azul');

    legend('Datos de fondo', 'Datos del color de seguimiento');% Leyenda

end