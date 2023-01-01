function XImagen = funcion_calcula_descriptores_imagen(IEtiq, N)

    XImagen = zeros(N, 23);
    
    stats = regionprops(IEtiq, 'Perimeter', 'Area', 'Eccentricity', 'Solidity', 'Extent', 'EulerNumber');
    Perimetros = vertcat(stats.Perimeter);
    Areas = vertcat(stats.Area);
    
    Compacticidad = (Perimetros.^2)./Areas;
    
    XImagen(:, 1) = Compacticidad;
    XImagen(:, 2) = vertcat(stats.Eccentricity);
    XImagen(:, 3) = vertcat(stats.Solidity);
    XImagen(:, 4) = vertcat(stats.Extent);
    
    % Calculo de la extensión invariante a la rotación
    ExtentInv = [];
    for i=1:N
       
        % Sacamos el bounding box del objeto
        IbinObjeto = IEtiq==i;
        ExtentInvObjeto = Funcion_Calcula_Extent(IbinObjeto);
        ExtentInv = [ExtentInv; ExtentInvObjeto];
        
    end
    XImagen(:, 5) = ExtentInv;
    
    % Calculo de los momentos de Hu
    XHu = funcion_calcula_Hu_objetos_imagen(IEtiq, N);
    XImagen(:, 6:12) = XHu;
    
    % Calculo de los descriptores de Fourier
    XDF = funcion_calcula_DF_objetos_imagen(IEtiq, N);
    XImagen(:, 13:22) = XDF;
    
    XImagen(:, 23) = vertcat(stats.EulerNumber);

end

