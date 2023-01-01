function indices_atip = funcion_detecta_outliers_clase_interes(X, Y, posClaseInteres)
    % Separamos los datos de inter�s del resto
    valoresY = unique(Y);

    DatosInteres = double(X(Y == valoresY(posClaseInteres), :));

    % Extraemos el rango de valores normales
    factor_desv = 3;
    MinNormales = mean(DatosInteres)-factor_desv*std(DatosInteres);
    MaxNormales = mean(DatosInteres)+factor_desv*std(DatosInteres);

    % Catalogaci�n de los valores at�picos por componente
    atipR = (DatosInteres(:, 1) > MaxNormales(1)) | (DatosInteres(:, 1) < MinNormales(1));
    atipG = (DatosInteres(:, 2) > MaxNormales(2)) | (DatosInteres(:, 2) < MinNormales(2));
    atipB = (DatosInteres(:, 3) > MaxNormales(3)) | (DatosInteres(:, 3) < MinNormales(3));

    % Posici�n de los valores at�picos por componente
    pos_atipR = find(atipR);
    pos_atipG = find(atipG);
    pos_atipB = find(atipB);

    % �ndices de los valores at�picos
    indices_atip = unique([pos_atipR; pos_atipG; pos_atipB]);
end