function indices_atip = funcion_detecta_outliers_clase_interes(X, Y, posClaseInteres)
    % Separamos los datos de interés del resto
    valoresY = unique(Y);

    DatosInteres = double(X(Y == valoresY(posClaseInteres), :));

    % Extraemos el rango de valores normales
    factor_desv = 3;
    MinNormales = mean(DatosInteres)-factor_desv*std(DatosInteres);
    MaxNormales = mean(DatosInteres)+factor_desv*std(DatosInteres);

    % Catalogación de los valores atípicos por componente
    atipR = (DatosInteres(:, 1) > MaxNormales(1)) | (DatosInteres(:, 1) < MinNormales(1));
    atipG = (DatosInteres(:, 2) > MaxNormales(2)) | (DatosInteres(:, 2) < MinNormales(2));
    atipB = (DatosInteres(:, 3) > MaxNormales(3)) | (DatosInteres(:, 3) < MinNormales(3));

    % Posición de los valores atípicos por componente
    pos_atipR = find(atipR);
    pos_atipG = find(atipG);
    pos_atipB = find(atipB);

    % Índices de los valores atípicos
    indices_atip = unique([pos_atipR; pos_atipG; pos_atipB]);
end