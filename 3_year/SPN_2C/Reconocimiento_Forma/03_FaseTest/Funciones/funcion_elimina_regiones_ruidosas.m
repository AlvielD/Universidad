function IbinFilt = funcion_elimina_regiones_ruidosas(Ibin)

    [N, M] = size(Ibin);
    Ibin2 = bwareaopen(Ibin, round(0.001*N*M));
    
    if sum(Ibin2>2)
        IEtiq = bwlabel(Ibin2);
        
        stats = regionprops(IEtiq, 'Area');
        areas = vertcat(stats.Area);
    
        numPix = round(max(areas)/5);
    
        IbinFilt = bwareaopen(Ibin2, numPix);
    else
        IbinFilt = Ibin2;
    end

end