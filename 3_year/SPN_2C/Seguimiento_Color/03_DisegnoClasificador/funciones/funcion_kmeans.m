function idx = funcion_kmeans(XColor, K)

    % Se eligen K observaciones para que actúen como semillas que definirán
    % las K clases.
    semillas = randsample(size(XColor, 1), K);
    centroides = zeros(3, K);
    for i=1:K
        centroides(:, i) = XColor(semillas(i), :)';
    end
    
    
    idx_anterior = funcion_calcula_agrupacion(XColor, centroides);
    iguales = false;
    
    while(iguales == false)
        centroides = funcion_calcula_centroides(XColor, idx_anterior);
        idx = funcion_calcula_agrupacion(XColor, centroides);
        iguales = funcion_compara_matrices(idx_anterior, idx);
        idx_anterior = idx;
    end
    
end

function idx = funcion_calcula_agrupacion(XColor, centroides)

    NumDatos = size(XColor, 1);
    NumCentroides = size(centroides, 2);
    Distancia = zeros(NumCentroides, NumDatos);

    % Tantas veces como columnas en centroides (Columna = Centroide)
    NP = XColor';
    for i=1:NumCentroides
        P = centroides(:, i);
        
        Pamp = repmat(P, 1, NumDatos);
        
        Distancia(i, :) = sqrt(sum((Pamp - NP).^2));
    end
    
    [~, idx] = min(Distancia);
    idx = idx';

end

function centroides = funcion_calcula_centroides(XColor, idx)

    Clases = unique(idx);
    centroides = zeros(size(XColor, 2), length(Clases));
    
    for i=1:length(Clases)
       NubeActual = XColor((idx == i), :);
       centroides(:, i) = mean(NubeActual);
    end

end

function varLogica = funcion_compara_matrices(m1,m2)
    
    % Comprobamos dimensiones
    
    [numFilas_m1 , numColumas_m1] = size(m1);
    [numFilas_m2 , numColumas_m2] = size(m2);
    
    if (numFilas_m1 ~= numFilas_m2) || (numColumas_m1 ~= numColumas_m2)
        
        varLogica = []; % por devolver algo en este caso
        
    else % sólo entra si las matrices  son iguales

        m1 = double(m1); % para asegurar que hacemos la diferencia en el  formato adecuado
        m2 = double(m2);

        dif = m1-m2;

        vMin = min(dif(:));
        vMax = max(dif(:));

        if vMin==vMax && vMin==0
            varLogica = true;
        else
            varLogica = false;         
        end
        
    end


end