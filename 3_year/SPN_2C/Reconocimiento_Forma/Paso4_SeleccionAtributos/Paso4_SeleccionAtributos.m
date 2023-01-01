%% CARGAMOS LOS DATOS DEL PROBLEMA
clc, clear, close all;

addpath('Funciones')
load('../Fase3_FaseTest/Datos/conjuntoDatosEstandarizados.mat')
load('../Fase3_FaseTest/Datos/nombresProblema.mat')

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
funcion_representa_datos(X, Y, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_circ_cuad_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');


%% CIRCULOS VS CUADRADOS
% Extraemos las filas de inter�s de nuestros datos
FoI = Y==1 | Y==2;
YoI = Y(FoI);
XoI = X(FoI, :);

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Circulos', 'Cuadrados'};
nombresProblemaOI.colores = {'*r', '*b'};

% Extraemos el espacio de caracter�sticas
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_circ_cuad.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');


%% CIRCULOS VS TRIANGULOS
% Extraemos las filas de inter�s de nuestros datos
FoI = Y==1 | Y==3;
YoI = Y(FoI);
XoI = X(FoI, :);

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Circulos', 'Triangulos'};
nombresProblemaOI.colores = {'*r', '*b'};

% Extraemos el espacio de caracter�sticas
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_circ_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');


%% CUADRADOS VS TRIANGULOS
% Extraemos las filas de inter�s de nuestros datos
FoI = Y==2 | Y==3;
YoI = Y(FoI);
XoI = X(FoI, :);

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Cuadrados', 'Triangulos'};
nombresProblemaOI.colores = {'*r', '*b'};

% Extraemos el espacio de caracter�sticas
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_cuad_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');


%% CIRCULOS-TRIANGULOS VS CUADRADOS
% Extraemos las filas de inter�s de nuestros datos
YoI = Y;
YoI(Y==3) = 1;
XoI = X;

% Establecemos los nombres del problema
nombresProblemaOI.descriptores = nombresProblema.descriptores;
nombresProblemaOI.clases = {'Circulos-Triangulos', 'Cuadrados'};
nombresProblemaOI.colores = {'*r', '*b'};

% Extraemos el espacio de caracter�sticas
[espacioCcas, valorJ] = funcion_selecciona_vector_ccas_3_dim(XoI, YoI, 9);

% Representamos los datos
funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI);

% Guardamos los datos
save('./Datos/espacioCcas_Cir-cuad_trian.mat', 'espacioCcas', 'valorJ', 'nombresProblemaOI');
