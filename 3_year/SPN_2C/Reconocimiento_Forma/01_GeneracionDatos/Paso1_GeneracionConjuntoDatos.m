%% PROGRAMACIÓN GENERACIÓN CONJUNTO DE DATOS X-Y

%% LECTURA AUTOMATIZADA DE LAS IMÁGENES DE ENTRENAMIENTO DISPONIBLES

% LECTURA AUTOMATIZADA DE IMAGENES
addpath('ImagenesPractica5/Entrenamiento')
addpath('Funciones')

nombres{1} = 'Circulo';
nombres{2} = 'Cuadrado';
nombres{3} = 'Triangulo';


%% 1.- GENERACIÓN CONJUNTO DE DATOS X-Y

numClases = 3;
numImagenesPorClase = 2;

X = []; 
Y = [];

for i=1:numClases
    for j=1:numImagenesPorClase
        
        nombreImagen = [nombres{i} num2str(j, '%02d') '.jpg'];
        I = imread(nombreImagen);
        
        % 1.1- BINARIZAR CON METODOLOGÍA DE SELECCIÓN AUTOMÁTICA DE UMBRAL (OTSU)
        umbral = graythresh(I);
        Ibin = I < 255*umbral;

        % 1.2.- ELIMINAR POSIBLES COMPONENTES CONECTADAS RUIDOSAS:
        IbinFilt = funcion_elimina_regiones_ruidosas(Ibin);
        
        if sum(IbinFilt(:)>0)
        
            % 1.3.- ETIQUETAR.
            [IEtiq, N] = bwlabel(IbinFilt);
            
            % 1.4.- CALCULAR TODOS LOS DESCRIPORES DE CADA AGRUPACIÓN CONEXA
            XImagen = funcion_calcula_descriptores_imagen(IEtiq, N);
            
            % 1.5.- GENERAR Yimagen
            YImagen = i*ones(N, 1);
            
        else
            
            XImagen = [];
            YImagen = [];
            
        end
        
        X = [X; XImagen];
        Y = [Y; YImagen];
        
    end
end


%% 2.- GENERACIÓN VARIABLE TIPO STRUCT nombresProblema


nombreDescriptores = {'Compacticidad', 'Excentricidad', 'Solidez', ...
    'Extension', 'Extension Invariante Rotacion', 'Hu Numero 1', ...
    'Hu Numero 2', 'Hu Numero 3', 'Hu Numero 4', 'Hu Numero 5', ...
    'Hu Numero 6', 'Hu Numero 7', 'DF Numero 1', 'DF Numero 2', ...
    'DF Numero 3', 'DF Numero 4', 'DF Numero 5', 'DF Numero 6', ...
    'DF Numero 7', 'DF Numero 8', 'DF Numero 9', 'DF Numero 10', ...
    'Numero de Euler'};

% nombreClases:
nombreClases{1} = 'Circulo';
nombreClases{2} = 'Cuadrado';
nombreClases{3} = 'Triangulo';

% simboloClases: simbolos y colores para representar las muestras de cada clase
coloresClases{1} = '*r';
coloresClases{2} = '*g';
coloresClases{3} = '*b';


% ------------------------------------
nombresProblema = [];
nombresProblema.descriptores = nombreDescriptores;
nombresProblema.clases = nombreClases;
nombresProblema.colores = coloresClases;


%% 3.- GUARDAR CONJUNTO DE DATOS Y NOMBRES DEL PROBLEMA
save('./Datos/conjuntoDatos.mat', 'X', 'Y');
save('./Datos/nombresProblema.mat', 'nombresProblema');

rmpath('Funciones')
rmpath('ImagenesPractica5/Entrenamiento')
