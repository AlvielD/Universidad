%% LECTURA DE LOS DATOS DEL PROBLEMA
clc, clear, close all

addpath('Funciones')
load('./Datos/conjuntoDatos.mat')
load('./Datos/nombresProblema.mat')

[numObjetos, numDescriptores] = size(X); 
codifClases = unique(Y);
numClases = length(codifClases);

%% ESTANDARIZAMOS LOS DATOS

% Matriz de datos estandarizados
Z = zeros(size(X));

% Estimadores de los atributos
medias = mean(X);
desv = std(X);

% Establecemos los valores del número de Euler de forma artificial.
medias(end) = 0;
desv(end) = 1;

for i=1:numDescriptores-1
   
    Z(:, i) = ( ( X(:,i) - medias(i) ) / desv(i) + eps );
    
end

%% GUARDAMOS LOS DATOS
save('./Datos/conjuntoDatosEstandarizados.mat', 'Z', 'Y');
save('./Datos/datosEstandarizacion.mat', 'medias', 'desv');
