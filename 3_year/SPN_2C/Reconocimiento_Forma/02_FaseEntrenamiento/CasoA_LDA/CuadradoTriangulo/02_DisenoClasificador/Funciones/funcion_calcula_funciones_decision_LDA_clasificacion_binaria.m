function [d1, d2, d12, coefs_d12] = funcion_calcula_funciones_decision_LDA_clasificacion_binaria(X, Y)

    [numDatos, numAtributos] = size(X);
    numDatosClase = zeros(1, 2);
    
    codifY = unique(Y);
    
    numDatosClase(1) = sum(Y==codifY(1));
    numDatosClase(2) = sum(Y==codifY(2));

    % Separamos los datos según la clase

    datosClas1 = X(Y == codifY(1), :);
    datosClas2 = X(Y == codifY(2), :);
    
    x1 = sym('x1', 'real');
    x2 = sym('x2', 'real');
    Xsym = [x1; x2];
    
    if numAtributos == 3
        
        x3 = sym('x3', 'real');
        Xsym = [Xsym; x3];
        
    end
    
    % Sacamos sus medias
    media1 = mean(datosClas1)';
    media2 = mean(datosClas2)';
    
    % Sacamos las matrices de covarianzas
    matCov1 = cov(datosClas1);
    matCov2 = cov(datosClas2);

    % Estimamos una única matriz de covarianzas
    matCov = ((numDatosClase(1)-1)*matCov1 + (numDatosClase(2)-1)*matCov2)/(numDatos-2);
    
    % Probabilidad a priori
    piK = [numDatosClase(1)/numDatos, numDatosClase(2)/numDatos];
    
    % Funciones de decisión LDA
    d1 = expand((-0.5 * (Xsym - media1)' * pinv(matCov) * (Xsym - media1)) + log(piK(1)));
    d2 = expand((-0.5 * (Xsym - media2)' * pinv(matCov) * (Xsym - media2)) + log(piK(2)));

    % Función discriminante:
    d12 = d1 - d2;
    
    % Frontera de decisión y calculo de coeficientes
    if numAtributos == 2
        x1 = 0; x2 = 0; C = eval(d12);
        x1 = 1; x2 = 0; A = eval(d12)-C;
        x1 = 0; x2 = 1; B = eval(d12)-C;
        
        coefs_d12 = [A B C];
    else
        x1 = 0; x2 = 0; x3 = 0; D = eval(d12);
        x1 = 1; x2 = 0; x3 = 0; A = eval(d12)-D;
        x1 = 0; x2 = 1; x3 = 0; B = eval(d12)-D;
        x1 = 0; x2 = 0; x3 = 1; C = eval(d12)-D;
        
        coefs_d12 = [A B C D];
    end
        
end