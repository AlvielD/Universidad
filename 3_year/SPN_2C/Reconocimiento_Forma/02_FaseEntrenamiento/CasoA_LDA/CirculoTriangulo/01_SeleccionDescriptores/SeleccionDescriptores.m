%% CARGAMOS LOS DATOS DEL PROBLEMA
clc, clear, close all;

addpath('Funciones')
load('../../../../01_GeneracionDatos/Datos/conjuntoDatosEstandarizados.mat')
load('../../../../01_GeneracionDatos/Datos/nombresProblema.mat')

X = Z;
clear('Z');

[numObjetos, numDescs] = size(X);
valoresY = unique(Y);
numClases = length(valoresY);

%% DETERMINACIÓN DEL ESPACIO DE CARACTER�?STICAS
% Determinamos el mejor espacio de características para cada problema
% planteado

%% CIRCULOS VS TRIANGULOS
% Extraemos las filas de interés de nuestros datos
FoI = Y==1 | Y==3;
YoI = Y(FoI);
XoI = X(FoI, :);

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Circulos', 'Triangulos'};
nombresProblemaOI.colores = {'*r', '*b'};

% Extraemos el espacio de características
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Actualizamos los datos de inter�s de nuestro problema
XoI = XoI(:, espacioCcas);

% Guardamos los datos
save('./Datos/espacioCcas_circ_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');
save('./Datos/datosProblema.mat', 'XoI', 'YoI', 'nombresProblemaOI');