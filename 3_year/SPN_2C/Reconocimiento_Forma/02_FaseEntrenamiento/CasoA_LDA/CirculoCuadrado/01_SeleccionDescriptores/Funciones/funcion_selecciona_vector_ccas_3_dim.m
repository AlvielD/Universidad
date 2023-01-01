function [espacioCcas,JespacioCcas] = funcion_selecciona_vector_ccas_3_dim(XoI,YoI,numDescriptoresOI)
    
    [~, numDescs] = size(XoI);
    outputs = YoI';

    %% RANKING INDIVIDUAL
    
    % Realizamos un ranking individual de cada descriptor y nos quedamos
    % con los mejores (No contamos el número de Euler)
    valoresJ = zeros(numDescs-1, 1);
    for i=1:numDescs-1
       
        inputs = XoI(:, i)';
        valoresJ(i) = indiceJ(inputs, outputs);
        
    end
    
    % Ordenamos los valores
    [~, indices] = sort(valoresJ, 'descend');
    
    % Nos quedamos con los mejores
    mejoresDescs = indices(1:numDescriptoresOI);
    
    %% RANKING CONJUNTO
    
    % Realizamos un ranking de las mejores combinaciones de 3 descriptores
    % de los 9 seleccionados en el apartado anterior.
    permutaciones = combnk(1:numDescriptoresOI, 3);
    numPermutaciones = size(permutaciones, 1);
    
    valoresJ = zeros(numPermutaciones, 1);
    for i=1:numPermutaciones
       
        descsOI = mejoresDescs(permutaciones(i, :));
        inputs = XoI(:, descsOI)';
        valoresJ(i) = indiceJ(inputs, outputs);
        
    end
    
    [valoresJord, indices] = sort(valoresJ, 'descend');
    
    espacioCcas = mejoresDescs(permutaciones(indices(1), :))';
    JespacioCcas = valoresJord(1);
    
end

