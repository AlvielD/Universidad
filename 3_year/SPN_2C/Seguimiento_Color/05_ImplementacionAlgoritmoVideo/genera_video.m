clc, clear, close all;

%% VARIABLES REQUERIDAS

% Video entrada
VideoEntrada = VideoReader('../01_GeneracionMaterial/datos/VideoEntrada.avi');
% Clasificador
load('../04_AjusteClasificador/datos/parametrosClasificador.mat');
% Directorio con funciones
addpath('funciones');


%% GENERACION DE LA SECUENCIA DE VIDEO FINAL

% Visualizamos el video de entrada
implay('../01_GeneracionMaterial/datos/VideoEntrada.avi');

% Establecemos la configuración del vídeo de salida igual que las del video
% de entrada.
VideoSalida = VideoWriter('VideoSalida.avi', 'Uncompressed AVI');
VideoSalida.FrameRate = VideoEntrada.FrameRate;
nframes = VideoEntrada.Duration*VideoEntrada.FrameRate;

% Vamos a escribir en el archivo .avi
open(VideoSalida);
VideoEntrada.CurrentTime = 0;

for i=1:nframes
    
    
    I = readFrame(VideoEntrada);
    
    Ib = clasifica_frame_multiples_esferas(I, datosClasificadorMultiEsferas);
    
    Ib = funcion_filtra_objetos(Ib, numPix);
    
    [IEtiq, N] = funcion_etiquetar(Ib);
    
    centroides = round(funcion_calcula_centroides(IEtiq, N));

    for j=1:size(centroides, 1)

        centroide = [centroides(j, 2), centroides(j, 1)];
        marcador = zeros(3, 3, 3);
        marcador(:,:,1) = 255;
        I(centroide(1)-1:centroide(1)+1, centroide(2)-1:centroide(2)+1, :) = marcador;

    end
    
    writeVideo(VideoSalida, I);
    
end

close(VideoSalida);
% Cerramos el archivo .avi

% Visualizamos el video de salida
implay('./VideoSalida.avi');

% Retiramos el directorio de funciones del path
rmpath('funciones');

