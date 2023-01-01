function X = funcion_calcula_DF_objetos_imagen(IEtiq, N)

    X = [];
    
    for i=1:N
        
        Ibin = IEtiq==i;
        
        mFourier = Funcion_Calcula_DF(Ibin, 10);
        X(i, :) = mFourier';
    
    end

end

