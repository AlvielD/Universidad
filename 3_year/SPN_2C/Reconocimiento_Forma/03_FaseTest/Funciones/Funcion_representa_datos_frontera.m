function Funcion_representa_datos_frontera(X,Y,nombresProblema,coefs)

    valoresY = unique(Y);
    datosClas1 = X(Y==valoresY(1), :);
    datosClas2 = X(Y==valoresY(2), :);
        
    plot3(datosClas1(:, 1), datosClas1(:, 2), datosClas1(:, 3), nombresProblema.colores{1});
    hold on;
    plot3(datosClas2(:, 1), datosClas2(:, 2), datosClas2(:, 3), nombresProblema.colores{2});
    
    % REPRESENTAMOS LA FRONTERA
    A = coefs(1); B = coefs(2); C = coefs(3); D = coefs(4);

    x1min = min(X(:, 1)); x1max = max(X(:, 1));
    x2min = min(X(:, 2)); x2max = max(X(:, 2));
    paso1 = (x1max - x1min)/100;
    paso2 = (x2max - x2min)/100;

    [x1Plano, x2Plano] = meshgrid(x1min:paso1:x1max, x2min:paso2:x2max);

    x3Plano = -(A*x1Plano + B*x2Plano + D)/(C+eps);

    surf(x1Plano, x2Plano, x3Plano);
    
    legend(nombresProblema.clases);

end

