function YTest = funcion_knn(XTest, XTrain, YTrain, k, Distancia)

    [numMuestras, ~] = size(XTest);
    [numPuntosNube, ~] = size(XTrain);
    codifY = unique(YTrain);
    numClases = length(codifY);
    
    YTest = zeros(numMuestras, 1);
    
    for i=1:numMuestras
       
        % Extraemos la muestra del set de test
        Xi = XTest(i, :)';
        
        if strcmp(Distancia, 'Euclidea')
            
            % Distancia de un punto a una nube de puntos
            Pamp = repmat(Xi, 1, size(XTrain, 1));
            
            vectorDistancia = sqrt(sum((Pamp - XTrain').^2));
            
        elseif strcmp(Distancia, 'Mahalanobis')
            
            vectorDistancia = [];
            medias = zeros(3, numClases);
            matricesCov = zeros(3, 3, numClases);
            
            for j=1:numClases
               
                datosClase = XTrain(YTrain == codifY(j), :);
                medias(:, j) = mean(datosClase)';
                matricesCov(:, :, j) = cov(datosClase);
                
            end
            
            for j=1:numPuntosNube
                
                media = medias(:, YTrain(j));
                matCov = matricesCov(:, :, YTrain(j));
                
                vectorDistancia = [vectorDistancia, ((Xi - media)' * pinv(matCov) * (Xi - media))];
                
            end
                
        else
            
            fprintf('Distancia no permitida. Valores permitidos: Euclidea, Mahalanobis.\n');
            
        end
        
        % Ordenamos el vector distancias
        [~, indices] = sort(vectorDistancia, 'ascend');
        
        % Sacamos las k instancias m√°s cercanas
        instanciasCercanas = indices(1:k);
        
        % Extraemos los valores en YTrain de esas instancias
        Yvotos = YTrain(instanciasCercanas);
        
        numVotosClase = zeros(numClases, 1);
        
        % Contamos cual aparece m√°s
        for k=1:numClases
           
            numVotosClase(k) = sum(Yvotos == codifY(k));
            
        end
        
        % Ordenamos los votos de mayor a menor
        [~, indVotos] = sort(numVotosClase, 'descend');
        
        % Si los dos primeros puestos est·n igualados, escogemos el que
        % tenga la instancia m·s cercana
        if numVotosClase(indVotos(1)) == numVotosClase(indVotos(2))
           
            YTest(i) = YTrain(indices(1));
        else
            % Lo almacenamos en el vector de predicciones
             YTest(i) = codifY(indVotos(1));
        end
        
    end

end