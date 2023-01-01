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

%% DETERMINACI�N DEL ESPACIO DE CARACTER�STICAS
% Determinamos el mejor espacio de caracter�sticas para cada problema
% planteado

%% CIRCULOS VS CUADRADOS VS TRIANGULOS
% Extraemos las filas de inter�s de nuestros datos
XoI = X;
YoI = Y;

% Establecemos los nombres del problema
nombresProblemaOI = nombresProblema;

% Extraemos el espacio de caracter�sticas
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_circ_cuad_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');
save('./Datos/datosProblema.mat', 'XoI', 'YoI');