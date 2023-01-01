clc, clear, close all;

%% LEEMOS LOS DATOS DE LA FASE ANTERIOR Y AÑADIMOS DIRECTORIOS IMPORTANTES

% CARGAMOS LOS DATOS DEL CLASIFICADOR GENERADOS EN LA ETAPA ANTERIOR
load('../03_DisegnoClasificador/datos/datos_multiples_esferas.mat');
% DIRECTORIO DE FUNCIONES
addpath('funciones');


%% ELECCIÓN DE UN CRITERIO DE RADIO

% CARGAMOS LAS IMAGENES DE CALIBRACIÓN Y VEMOS QUE RADIO SE AJUSTA MÁS A
% LOS DATOS
load('../01_GeneracionMaterial/datos/imagenes.mat');

% EXTRAEMOS LOS DATOS DE LAS ESFERAS
centroides = datos_multiples_esferas(:, 1:3);
radios = datos_multiples_esferas(:, 4:end);
numAgrupaciones = size(radios, 2);

% Vamos a probar nuestro clasificador formado por multiples esferas sobre
% las imagenes de calibración y elegiremos el criterio de radio que más se
% ajuste a los datos.
numImg = size(imagenes, 4);
for i=1:numImg % Para cada imagen
    figure;
    ImgActual = imagenes(:, :, :, i);
    subplot(2,2,1), imshow(ImgActual), title(['Imagen ' num2str(i)]);
    for j=1:numAgrupaciones % Para cada criterio de radio
        
        Ib_anterior = clasifica_frame(ImgActual, centroides(1, :), radios(1, j));
        for k=2:numAgrupaciones % Para cada esfera
            Ib = clasifica_frame(ImgActual, centroides(k, :), radios(k, j));
            Ib = Ib_anterior | Ib;
            Ib_anterior = Ib;
        end
        
        Io = funcion_visualiza(ImgActual, Ib, [255, 0, 0], false);
        subplot(2,2,j+1), imshow(Io), title(['Radio ' num2str(j)]);
        hold on;
    end
    pause();
end
close all;

% Como podemos observar en las imagenes, el radio que mejor se ajusta a los
% datos es el que no deja pasar ni un solo dato de ruido.
radios = radios(:, 2);
datosClasificadorMultiEsferas = [centroides, radios];

save('./datos/datosClasificadorMultiEsferas', 'datosClasificadorMultiEsferas');


%% CALIBRACIÓN DE PARÁMETROS DE CONECTIVIDAD
%load('./datos/datosClasificadorMultiEsferas.mat');
load('../01_GeneracionMaterial/datos/imagenes.mat');
numImgs = size(imagenes, 4);

% Observamos las imagenes para saber cual es la que tiene el objeto en la
% posición más alejada.
for i=1:numImgs
   imshow(imagenes(:,:,:,i));
   title(['Imagen de Calibración número ' num2str(i)]);
   pause;
end

% Parece que la imagen 13 podría ser la más alejada
ImgInteres = 12;    % Imagen del objeto más alejado
I_objeto_alejado = imagenes(:,:,:,ImgInteres);

% Visualizamos la imágen para asegurarnos
imshow(I_objeto_alejado);


% SACAMOS EL NUMERO DE PIXELES DEL OBJETO EN LA POSICION MAS ALEJADA
Ib = roipoly(I_objeto_alejado);
numPixReferencia = sum(Ib(:));

% USAREMOS UN PORCENTAJE DE ESOS PIXELES DE REFERENCIA COMO UMBRAL
numPixAnalisis = round([0.25, 0.5, 0.75]*numPixReferencia);

color = [255, 0, 0];

% COMPROBAMOS COMO FUNCIONA EN LAS IMAGENES YA CLASIFICADAS POR EL
% CLASIFICADOR
for i=1:numImgs
    
   figure;
   I = imagenes(:,:,:,i);
   
   Ib_deteccion_multiEsferas = clasifica_frame_multiples_esferas(I, datosClasificadorMultiEsferas);
   
   Io = funcion_visualiza(I, Ib_deteccion_multiEsferas, color, false);
   subplot(2,2,1), imshow(Io), title(['Imagen de Calibración número: ' num2str(i)]);
   
   for j=1:length(numPixAnalisis)
       
       Ib = logical(funcion_filtra_objetos(Ib_deteccion_multiEsferas, numPixAnalisis(j)));
       Io = funcion_visualiza(I, Ib, color);
       subplot(2,2,j+1);
       imshow(Io);
       title(['numPix = ' num2str(numPixAnalisis(j))]);
       
   end
   pause;
   close;
   
end

% Podemos ver que en todos los criterios se elimina el ruido a pesar de que
% se pierde el objeto, usaremos el umbral que menos nos perjudique.

numPix = numPixAnalisis(3);
save('./datos/numPix', 'numPix');

%% GUARDAMOS LAS VARIABLES JUNTAS
%load('./datos/datosClasificadorMultiEsferas.mat');
%load('./datos/numPix');
save('./datos/parametrosClasificador', 'datosClasificadorMultiEsferas', 'numPix');

rmpath('funciones');
