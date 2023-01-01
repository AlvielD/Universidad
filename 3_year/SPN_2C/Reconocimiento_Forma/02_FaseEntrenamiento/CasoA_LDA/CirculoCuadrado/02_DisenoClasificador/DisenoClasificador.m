clc, clear, close all

addpath('Funciones')
load('../01_SeleccionDescriptores/Datos/espacioCcas_circ_cuad.mat');
load('../01_SeleccionDescriptores/Datos/datosProblema.mat');

XoI = XoI(:, espacioCcas);
nombresProblemaOI.descriptores = nombresProblemaOI.descriptores(espacioCcas);
nombresProblemaOI.clases = {'Circulo', 'Cuadrado'};
nombresProblemaOI.colores = {'*r', '*g'};

[d1 d2 d12 coefs_d12] = funcion_calcula_funciones_decision_LDA_clasificacion_binaria(XoI, YoI);

save('./Datos/LDA_Circ_Cuad.mat', 'coefs_d12', 'd12', 'XoI', 'YoI', 'nombresProblemaOI', 'espacioCcas');



