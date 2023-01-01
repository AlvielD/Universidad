function X = funcion_calcula_Hu_objetos_imagen(IEtiq, N)

    X = [];
    
    for i=1:N
        
        Ibin = IEtiq==i;
        
        mHu = Funcion_Calcula_Hu(Ibin);
        X(i, :) = mHu';
    
    end

end

