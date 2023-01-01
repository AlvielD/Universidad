clc, clear, close all;

%% LEEMOS LOS DATOS DE LA FASE ANTERIOR Y AÑADIMOS DIRECTORIOS IMPORTANTES

% CARGAMOS EL CONJUNTO DE DATOS DE GENERADO EN LA ETAPA ANTERIOR
load('../02_GeneracionConjuntoDatos/datos/ConjuntoDatos_SinOutliers.mat');
% DIRECTORIO DE FUNCIONES
addpath('funciones');


%% DISEÑO DEL CLASIFICADOR FORMADO POR UNA ESFERA.

% CLASES DEL PROBLEMA
valoresY = unique(Y);


% SEPARAMOS LAS CLASES EN DOS NUBES DE PUNTOS DIFERENTES
FFondo = Y == valoresY(1);
FColor = Y == valoresY(2);

XFondo = X(FFondo, :);
XColor = X(FColor, :);

clear('FFondo', 'FColor');


% Usaremos un clasificador esférico, hemos diseñado una función que se
% encarga de calcular los datos de 3 esferas, cada una con un criterío de
% radio diferente.
%
%   radios(1) --> No pierde ni un solo dato de la nube de puntos de la
%   clase de interes
%
%   radios(2) --> Evita que se cuelen datos de la nube de puntos de la
%   clase contraria
%
%   radios(3) --> Compromiso entre los dos radios anteriores.

datos_esfera = calcula_datos_esfera(XColor, XFondo);

centroide = datos_esfera(1:3);
radios = datos_esfera(4:end);

% Representamos las 3 esferas para ver cual se ajusta mejor a nuestros
% datos.
for i=1:length(radios)
    figure; funcion_representa_datos(X, Y); hold on;
    representa_esfera(centroide, radios(i));
    title(['Radio = ' num2str(radios(i))]);
end
pause();


% Para que sea más sencillo vamos a probar directamente el clasificador
% sobre las imagenes de calibración y ver cual se ajusta mejor.

% CARGAMOS LAS IMAGENES DE CALIBRACION
load('../01_GeneracionMaterial/datos/imagenes.mat');
numImg = size(imagenes, 4);

for i=1:numImg
    figure;
    ImgActual = imagenes(:, :, :, i);
    subplot(2,2,1), imshow(ImgActual), title(['Imagen ' num2str(i)]);
    for j=1:length(radios) 
        Ib = clasifica_frame(ImgActual, centroide, radios(j));
        Io = funcion_visualiza(ImgActual, Ib, [255, 0, 0], false);
        subplot(2,2,j+1), imshow(Io), title(['Radio ' num2str(j)]);
    end
    pause;
end
close all;

% A pesar de que el objeto se pierde bastante, el radio que mejor resultado
% nos da es el que no mete ruido de fondo.


%% DISEÑO DEL CLASIFICADOR FORMADO POR VARIAS ESFERAS

% Para ajustar aún más el clasificador al problema, dividiremos nuestra
% nubes de puntos de interés en K agrupaciones (3 en este caso) de un
% tamaño balanceado y calcularemos una esfera por cada agrupación.
numAgrupaciones = 3;
idx = funcion_kmeans(XColor, numAgrupaciones); % idx indica a que agrupación pertenece cada fila de XColor

for i=1:length(unique(idx))
    hold on;
    funcion_representa_datos(XColor, idx);
end


% Sacamos los datos de las distintas esferas centradas en cada cluster de
% puntos.
datos_multiples_esferas = zeros(numAgrupaciones, 6);
for i=1:numAgrupaciones
    datos_multiples_esferas(i, :) = calcula_datos_esfera(XColor(idx == i, :), XFondo);
end


% EXTRAEMOS LOS DATOS DE LAS ESFERAS
centroides = datos_multiples_esferas(:, 1:3);
radios = datos_multiples_esferas(:, 4:end);


% REPRESENTAMOS PARA VER QUE RADIO SE AJUSTA MÁS A LOS DATOS
for i=1:numAgrupaciones
    figure;
    funcion_representa_datos_interes(X, Y, 1);
    hold on;
    funcion_representa_datos(XColor, idx);
    for j=1:numAgrupaciones
       representa_esfera(centroides(j, :), radios(j, i));
       hold on;
    end
end
pause();


% GUARDAMOS LOS DATOS DE LAS MULTIPLES ESFERAS
save('./datos/datos_multiples_esferas', 'datos_multiples_esferas');


% RETIRAMOS LA CARPETA DE FUNCIONES DEL PATH
rmpath('funciones');
