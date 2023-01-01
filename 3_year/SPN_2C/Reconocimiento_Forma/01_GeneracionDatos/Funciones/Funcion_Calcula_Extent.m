function Extent = Funcion_Calcula_Extent(IbinObjeto)

    Extensiones = [];

    Icentrada = Funcion_Centra_Objeto(IbinObjeto);
    
    for i=0:5:355
       
        Irotada = imrotate(Icentrada, i);
        stats = regionprops(Irotada, 'Extent');
        Extensiones = [Extensiones; stats.Extent];
        
    end
    
    Extent = max(Extensiones);

end

