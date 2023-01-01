function Funcion_Reconoce_Formas(Nombre)
    %% CARGAMOS LOS DATOS DE TEST
    ruta = ['../../ImagenesPractica5/Test/' Nombre];
    I = imread(ruta);
    
    clear('Nombre', 'ruta');
        
    %% DESCRIBIMOS LOS OBJETOS MATEMÃ?TICAMENTE
    
    % BINARIZAR CON METODOLOGÃ?A DE SELECCIÃ“N AUTOMÃ?TICA DE UMBRAL (OTSU)
    umbral = graythresh(I);
    Ibin = I < 255*umbral;

    % ELIMINAR POSIBLES COMPONENTES CONECTADAS RUIDOSAS:
    IbinFilt = funcion_elimina_regiones_ruidosas(Ibin);
      
    if sum(IbinFilt(:)>0)
        
        % ETIQUETAR.
        [IEtiq, N] = bwlabel(IbinFilt);
            
        % CALCULAR TODOS LOS DESCRIPORES DE CADA AGRUPACIÃ“N CONEXA
        XImagen = funcion_calcula_descriptores_imagen(IEtiq, N);
            
    else
         
        XImagen = [];
            
    end
        
    %% ESTANDARIZAMOS LOS DATOS GENERADOS
    [numMuestras, numDescriptores] = size(XImagen);
    
    % Cargamos los datos de estandarizaciÃ³n
    load('./Datos/datosEstandarizacion.mat');
    
    % Matriz de datos estandarizados
    Z = zeros(size(XImagen));

    % Establecemos los valores del nÃºmero de Euler de forma artificial.
    medias(end) = 0;
    desv(end) = 1;

    for i=1:numDescriptores-1

        Z(:, i) = ( ( XImagen(:,i) - medias(i) ) / desv(i) + eps );

    end
    
    X = Z;
    clear('Z', 'XImagen', 'Ibin', 'IbinFilt');
    
    %% APLICAMOS EL CLASIFICADOR PARA EL RECONOCIMIENTO DE CADA OBJETO
    
    load('../../02_FaseEntrenamiento/CasoB_KNN/01_SeleccionDescriptores/Datos/espacioCcas_circ_cuad_trian.mat');
    load('../../02_FaseEntrenamiento/CasoB_KNN/01_SeleccionDescriptores/Datos/datosProblema.mat');
    XTest = X(:, espacioCcas);
    
    YTest = funcion_knn(XTest, XoI(:, espacioCcas), YoI, 3, 'Mahalanobis');
    
    % Aplicamos cada clasificador
    for i=1:numMuestras
            
        % Reglas de decisión
        if YTest(i) == 1 % Circulo
            
            reconocimiento = ['Objeto reconocido: ' nombresProblemaOI.clases{1}];
            color = [255, 0, 0];
            
        elseif YTest(i) == 2 % Cuadrado
            
            reconocimiento = ['Objeto reconocido: ' nombresProblemaOI.clases{2}];
            color = [0, 255, 0];
            
        elseif YTest(i) == 3 % Triangulo
            
            reconocimiento = ['Objeto reconocido: ' nombresProblemaOI.clases{3}];
            color = [0, 0, 255];
        end
        
        % Representación de los resultados
        figure;
        subplot(1,2,1), imshow(funcion_visualiza(I, (IEtiq==i), color, false));
        title(reconocimiento);
        
        subplot(1,2,2), funcion_representa_datos(XoI, YoI, espacioCcas, nombresProblemaOI),
        hold on, plot3(X(i, espacioCcas(1)), X(i, espacioCcas(2)), X(i, espacioCcas(3)), '*y');
        
    end
        
end

