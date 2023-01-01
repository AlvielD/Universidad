%% CARGAMOS LOS DATOS DEL PROBLEMA
clc, clear, close all;

addpath('Funciones')
load('../../../01_GeneracionDatos/Datos/conjuntoDatosEstandarizados.mat')
load('../../../01_GeneracionDatos/Datos/nombresProblema.mat')

X = Z;
clear('Z');

[numObjetos, numDescs] = size(X);
valoresY = unique(Y);
numClases = length(valoresY);

%% DETERMINACIÃ“N DEL ESPACIO DE CARACTERÃ?STICAS
% Determinamos el mejor espacio de caracterÃ­sticas para cada problema
% planteado

%% CIRCULOS-TRIANGULOS VS CUADRADOS
% Extraemos las filas de interÃ©s de nuestros datos
YoI = Y;
YoI(Y==3) = 1;
XoI = X;

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Circulo-Triangulo', 'Cuadrado'};
nombresProblemaOI.colores = {'*m', '*g'};

% Extraemos el espacio de caracterÃ­sticas
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_cir-trian_cuad.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');
save('./Datos/datosProblema_cir-trian_cuad.mat', 'XoI', 'YoI');

%% CIRCULOS VS TRIANGULOS
% Extraemos las filas de interés de nuestros datos
FoI = Y==1 | Y==3;
YoI = Y(FoI);
XoI = X(FoI, :);

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Circulo', 'Triangulo'};
nombresProblemaOI.colores = {'*r', '*b'};

% Extraemos el espacio de características
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_cir_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');
save('./Datos/datosProblema_cir_trian.mat', 'XoI', 'YoI');
