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
    
    % Cargamos y renombramos los datos de cada clasificador
    load('../../02_FaseEntrenamiento/CasoA_LDA/CirculoCuadrado/02_DisenoClasificador/Datos/LDA_Circ_Cuad');
    
    d_circ_cuad = d12;
    coefs_circ_cuad = coefs_d12;
    nomProbCircCuad = nombresProblemaOI;
    X_circ_cuad = XoI;
    Y_circ_cuad = YoI;
    espacioCcas_circ_cuad = espacioCcas;
    
    load('../../02_FaseEntrenamiento/CasoA_LDA/CirculoTriangulo/02_DisenoClasificador/Datos/LDA_Circ_Trian');
    
    d_circ_trian = d12;
    coefs_circ_trian = coefs_d12;
    nomProbCircTrian = nombresProblemaOI;
    X_circ_trian = XoI;
    Y_circ_trian = YoI;
    espacioCcas_circ_trian = espacioCcas;
    
    load('../../02_FaseEntrenamiento/CasoA_LDA/CuadradoTriangulo/02_DisenoClasificador/Datos/LDA_Cuad_Trian');
    
    d_cuad_trian = d12;
    coefs_cuad_trian = coefs_d12;
    nomProbCuadTrian = nombresProblemaOI;
    X_cuad_trian = XoI;
    Y_cuad_trian = YoI;
    espacioCcas_cuad_trian = espacioCcas;
    
    clear('d12', 'coefsd12', 'nombresProblemaOI', 'XoI', 'YoI', 'espacioCcas');
    
    % Aplicamos cada clasificador
    for i=1:numMuestras
        
        % Circulo vs Cuadrado
        x1 = X(i, espacioCcas_circ_cuad(1));
        x2 = X(i, espacioCcas_circ_cuad(2));
        x3 = X(i, espacioCcas_circ_cuad(3));
        valor_d_circ_cuad = eval(d_circ_cuad);
        
        % Circulo vs Triangulo
        x1 = X(i, espacioCcas_circ_trian(1));
        x2 = X(i, espacioCcas_circ_trian(2));
        x3 = X(i, espacioCcas_circ_trian(3));
        valor_d_circ_trian = eval(d_circ_trian);
        
        % Cuadrado vs Triangulo
        x1 = X(i, espacioCcas_cuad_trian(1));
        x2 = X(i, espacioCcas_cuad_trian(2));
        x3 = X(i, espacioCcas_cuad_trian(3));
        valor_d_cuad_trian = eval(d_cuad_trian);
            
        % Reglas de decisión
        if valor_d_circ_cuad > 0 && valor_d_circ_trian > 0 % Circulo
            
            reconocimiento = ['Objeto reconocido: ' nomProbCircCuad.clases{1}];
            color = [255, 0, 0];
            
        elseif valor_d_circ_cuad < 0 && valor_d_cuad_trian > 0 % Cuadrado
            
            reconocimiento = ['Objeto reconocido: ' nomProbCircCuad.clases{2}];
            color = [0, 255, 0];
            
        elseif valor_d_circ_trian < 0 && valor_d_cuad_trian < 0 % Triangulo
            
            reconocimiento = ['Objeto reconocido: ' nomProbCuadTrian.clases{2}];
            color = [0, 0, 255];
        end
        
        % Representación de los resultados
        figure;
        subplot(2,2,1), imshow(funcion_visualiza(I, (IEtiq==i), color, false));
        title(reconocimiento);
        
        subplot(2,2,2), Funcion_representa_datos_frontera(X_circ_cuad, Y_circ_cuad, nomProbCircCuad, coefs_circ_cuad),
        hold on, plot3(X(i, espacioCcas_circ_cuad(1)), X(i, espacioCcas_circ_cuad(2)), X(i, espacioCcas_circ_cuad(3)), '*y');
        
        subplot(2,2,3), Funcion_representa_datos_frontera(X_circ_trian, Y_circ_trian, nomProbCircTrian, coefs_circ_trian),
        hold on, plot3(X(i, espacioCcas_circ_trian(1)), X(i, espacioCcas_circ_trian(2)), X(i, espacioCcas_circ_trian(3)), '*y');
        
        subplot(2,2,4), Funcion_representa_datos_frontera(X_cuad_trian, Y_cuad_trian, nomProbCuadTrian, coefs_cuad_trian),
        hold on, plot3(X(i, espacioCcas_cuad_trian(1)), X(i, espacioCcas_cuad_trian(2)), X(i, espacioCcas_cuad_trian(3)), '*y');
        
    end
        
end

