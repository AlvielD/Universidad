clc, clear, close all

addpath('Funciones')
load('../01_SeleccionDescriptores/Datos/espacioCcas_cir_trian.mat');
load('../01_SeleccionDescriptores/Datos/datosProblema_cir_trian.mat');

XoI = XoI(:, espacioCcas);
nombresProblemaOI.descriptores = nombresProblemaOI.descriptores(espacioCcas);
nombresProblemaOI.clases = {'Circulo', 'Triangulo'};
nombresProblemaOI.colores = {'*r', '*b'};

[d1 d2 d12 coefs_d12] = funcion_calcula_funciones_decision_LDA_clasificacion_binaria(XoI, YoI);

save('./Datos/LDA_Circ_Trian.mat', 'coefs_d12', 'd12', 'XoI', 'YoI', 'nombresProblemaOI', 'espacioCcas');
