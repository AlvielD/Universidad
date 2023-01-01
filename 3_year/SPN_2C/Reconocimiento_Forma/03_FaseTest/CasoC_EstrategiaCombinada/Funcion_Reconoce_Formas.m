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
    % CLASIFICADOR KNN PARA CIRCULOS-TRIANGULOS VS CUADRADOS
    
    load('../../02_FaseEntrenamiento/CasoC_EstrategiaCombinada/01_SeleccionDescriptores/Datos/espacioCcas_cir-trian_cuad.mat');
    load('../../02_FaseEntrenamiento/CasoC_EstrategiaCombinada/01_SeleccionDescriptores/Datos/datosProblema_cir-trian_cuad.mat');
    XTest_circtrian_cuad = X(:, espacioCcas);
    
    espacioCcas_circtrian_cuad = espacioCcas;
    nombresProblema_circtrian_cuad = nombresProblemaOI;
    valorJ_circtrian_cuad = valorJ;
    XoI_circtrian_cuad = XoI;
    YoI_circtrian_cuad = YoI;
    
    YTest = funcion_knn(XTest_circtrian_cuad, XoI_circtrian_cuad(:, espacioCcas_circtrian_cuad), YoI, 3, 'Mahalanobis');
    
    clear('espacioCcas', 'XoI', 'YoI', 'nombresProblemaOI', 'valorJ');
    
    % CLASIFICADOR CIRCULOS VS TRIANGULOS
    
    load('../../02_FaseEntrenamiento/CasoC_EstrategiaCombinada/01_SeleccionDescriptores/Datos/espacioCcas_cir_trian.mat');
    load('../../02_FaseEntrenamiento/CasoC_EstrategiaCombinada/01_SeleccionDescriptores/Datos/datosProblema_cir_trian.mat');
    load('../../02_FaseEntrenamiento/CasoC_EstrategiaCombinada/02_DisenoClasificador/Datos/LDA_Circ_Trian.mat');
    XTest_circ_trian = X(:, espacioCcas);
    
    espacioCcas_circ_trian = espacioCcas;
    nombresProblema_circ_trian = nombresProblemaOI;
    valorJ_circ_trian = valorJ;
    XoI_circ_trian = XoI;
    YoI_circ_trian = YoI;
    
    d_circ_trian = d12;
    coefs_circ_trian = coefs_d12;
    
    clear('espacioCcas', 'XoI', 'YoI', 'nombresProblemaOI', 'valorJ', 'd12', 'coefs_d12');
    
    % Aplicamos cada clasificador
    for i=1:numMuestras
            
        % Reglas de decisión
        if YTest(i) == 1 % Circulo-Triangulo
            
            % Circulo vs Triangulo
            x1 = X(i, espacioCcas_circ_trian(1));
            x2 = X(i, espacioCcas_circ_trian(2));
            x3 = X(i, espacioCcas_circ_trian(3));
            valor_d_circ_trian = eval(d_circ_trian);
            
            if valor_d_circ_trian < 0 % Triangulo
            
                YTest(i) = 3;
                
            end
            
        end
        
        % Representación de los resultados
      
        if YTest(i) == 1
            reconocimiento = ['Objeto reconocido: ' nombresProblema_circ_trian.clases{1}];
            color = [255, 0, 0];
        elseif YTest(i) == 2
            reconocimiento = ['Objeto reconocido: ' nombresProblema_circtrian_cuad.clases{2}];
            color = [0, 255, 0];
        elseif YTest(i) == 3
            reconocimiento = ['Objeto reconocido: ' nombresProblema_circ_trian.clases{2}];
            color = [0, 0, 255];
        end
        
        
        figure;
        subplot(1,3,1), imshow(funcion_visualiza(I, (IEtiq==i), color, false));
        title(reconocimiento);
        
        subplot(1,3,2), funcion_representa_datos(XoI_circtrian_cuad, YoI_circtrian_cuad, espacioCcas_circtrian_cuad, nombresProblema_circtrian_cuad),
        hold on, plot3(X(i, espacioCcas_circtrian_cuad(1)), X(i, espacioCcas_circtrian_cuad(2)), X(i, espacioCcas_circtrian_cuad(3)), '*y');
        
        subplot(1,3,3), Funcion_representa_datos_frontera(XoI_circ_trian, YoI_circ_trian, nombresProblema_circ_trian, coefs_circ_trian),
        hold on, plot3(X(i, espacioCcas_circ_trian(1)), X(i, espacioCcas_circ_trian(2)), X(i, espacioCcas_circ_trian(3)), '*y');
        
    end
end

