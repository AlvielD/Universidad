clc, clear, close all

addpath('Funciones')
load('../01_SeleccionDescriptores/Datos/espacioCcas_cuad_trian.mat');
load('../01_SeleccionDescriptores/Datos/datosProblema.mat');

XoI = XoI(:, espacioCcas);
nombresProblemaOI.descriptores = nombresProblemaOI.descriptores(espacioCcas);
nombresProblemaOI.clases = {'Cuadrado', 'Triangulo'};
nombresProblemaOI.colores = {'*g', '*b'};

[d1 d2 d12 coefs_d12] = funcion_calcula_funciones_decision_LDA_clasificacion_binaria(XoI, YoI);

save('./Datos/LDA_Cuad_Trian.mat', 'coefs_d12', 'd12', 'XoI', 'YoI', 'nombresProblemaOI', 'espacioCcas');



