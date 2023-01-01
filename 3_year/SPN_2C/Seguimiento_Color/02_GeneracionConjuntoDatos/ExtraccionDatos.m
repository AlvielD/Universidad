clc, clear, close all;

%% LECTURA DE LOS DATOS DE LA FASE ANTERIOR

% Imagenes de calibracion
load('../01_GeneracionMaterial/datos/imagenes.mat');
% Directorio con funciones
addpath('funciones');


%% VISUALIZAMOS LAS IMAGENES

[N, M, NumComp, NumImg] = size(imagenes);

for i=1:NumImg
    imshow(imagenes(:,:,:,i)), title(['Imagen ' num2str(i)]);
    pause();
end


%% EXTRACCION DE LOS DATOS

% Datos Color
DatosColor = [];

for i=2:NumImg
    % Extraemos los canales de color de la imagen
    I = imagenes(:,:,:,i);
    R = I(:,:,1);
    G = I(:,:,2);
    B = I(:,:,3);
    
    % Extraemos los pixeles de interes de la imagen
    Ib = roipoly(I);
	
	numPix = sum(Ib(:));	% Pixeles totales sacados de la imÃ¡gen binaria
	DatosColor = [DatosColor; i*ones(numPix, 1) R(Ib), G(Ib), B(Ib)];
end

% Datos Fondo
DatosFondo = [];

for i=1:NumImg
    % Extraemos los canales de color de la imagen
    I = imagenes(:,:,:,i);
    R = I(:,:,1);
    G = I(:,:,2);
    B = I(:,:,3);
    
    % Extraemos los pixeles de interes de la imagen
    Ib = roipoly(I);
	
	numPix = sum(Ib(:));	% Pixeles totales sacados de la imÃ¡gen binaria
	DatosFondo = [DatosFondo; i*ones(numPix, 1) R(Ib), G(Ib), B(Ib)];
end

% Guardamos los datos generados
save('./datos/ConjuntoDatos_SinClasificar', 'DatosColor', 'DatosFondo');


%% GENERACION DE NUESTROS PRIMEROS CONJUNTOS DE DATOS

% Leemos los datos que hemos extraido anteriormente
%load('../02_GeneracionConjuntoDatos/datos/ConjuntoDatos_SinClasificar');

% Generamos el conjunto de Datos X
X = double([DatosColor(:, 2:4); DatosFondo(:, 2:4)]);

% Generamos el conjunto de Datos Y
NumMuestrasColor = size(DatosColor, 1);
NumMuestrasFondo = size(DatosFondo, 1);

Y = [ones(NumMuestrasColor, 1); zeros(NumMuestrasFondo, 1)];

save('./datos/ConjuntoDatos_Original', 'X', 'Y');


%% REPRESENTACION DE LOS DATOS

% Cargamos los conjuntos de datos generados en la etapa anterior
load('../02_GeneracionConjuntoDatos/datos/ConjuntoDatos_Original');

% Representamos los datos en una grafica
funcion_representa_datos(X, Y);


%% ELIMINACIÓN DE VALORES ATÍPICOS

% Cargamos los datos de la fase anterior
load('../02_GeneracionConjuntoDatos/datos/ConjuntoDatos_Original');

% Detectamos las posiciones de los valores atípicos
posClaseInteres = 2;
indices_atip = funcion_detecta_outliers_clase_interes(X, Y, posClaseInteres);

% Eliminamos los valores atípicos
X(indices_atip, :) = [];
Y(indices_atip) = [];

% Guardamos los datos actualizados
save('./datos/ConjuntoDatos_SinOutliers', 'X', 'Y');


%% REPRESENTAMOS LOS CONJUNTOS DE DATOS  Y LOS VALORES ATÍPICOS
funcion_representa_datos(X, Y);

% Retiramos el directorio de funciones del path
rmpath('funciones');

