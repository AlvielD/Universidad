%% REPRESENTACI�N Y AN�LISIS CUALITATIVO INICIAL DEL CONJUNTO DE DATOS X-Y


%% CARGAR CONJUNTO DE DATOS Y VARIABLES DEL PROBLEMA
clear, clc, close all

load('./Datos/conjuntoDatos.mat');
load('./Datos/nombresProblema.mat');
addpath('Funciones');

[numObjetos, numDescriptores] = size(X);

descriptores = nombresProblema.descriptores;
clases = nombresProblema.clases;
colores = nombresProblema.colores;

codifClases = unique(Y);
numClases = length(clases);

%% REPRESENTAR LOS DATOS EN ESPACIOS DE CARACTERISTICAS DOS A DOS


% Cada gr�fica en una ventana tipo figure. Utilizar la funci�n:

% funcion_representa_datos(X,Y, espacioCcas, nombresProblema)
for i=1:2:numDescriptores-1
   
    funcion_representa_datos(X, Y, [i, i+1], nombresProblema);
    
end

%% ---------------------------------------------------------------
%% REPRESENTACI�N HISTOGRAMA Y DIAGRAMA DE CAJAS DE CADA DESCRIPTOR
%% ---------------------------------------------------------------

% Para cada descriptor, abrir dos ventanas tipo figure
% una para representar histogramas y otra para diagramas de caja

% En cada una de ellas se representan los datos del descriptor para las 
% distintas clases del problema en gr�ficas independientes

% Ejemplo de programaci�n
for j=1:numDescriptores
     
     % Valores m�ximo y m�nimos para representar en la misma escala
     vMin = min(X(:,j)); 
     vMax = max(X(:,j));
     
     hFigure = figure; hold on
     bpFigure = figure; hold on
     
     for i=1:numClases
     
         Xij = X(Y==codifClases(i),j); % datos de la clase i del descriptor j 
         numDatosClase = size(Xij,1);
 
         figure(hFigure)
         subplot(numClases,1,i), hist(Xij),
         xlabel(descriptores{j})
         ylabel('Histograma')
         axis([ vMin vMax 0 numObjetos/4]) % inf para escala autom�tica del eje y
         title(clases{i})
         
         figure(bpFigure)
         subplot(1,numClases,i), boxplot(Xij)
         xlabel('Diagrama de Caja')
         ylabel(descriptores{j})
         axis([ 0 2 vMin vMax ])
         title(clases{i})
    end
end


%% ---------------------------------------------------------------
%% OBTENER CONCLUSIONES DE LA EFICIENCIA DE CADA DESCRIPTOR - AN�LISIS CUALITATIVO
%% ---------------------------------------------------------------

% Por cada descriptor, asignar una categor�a seg�n la siguiente escala:

% Escala de adecuaci�n del descriptor: no adecuado, adecuado, muy adecuado 



rmpath('Funciones')