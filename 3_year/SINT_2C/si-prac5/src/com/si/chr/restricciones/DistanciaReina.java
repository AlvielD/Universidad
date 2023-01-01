package com.si.chr.restricciones;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;
import com.si.chr.VarDom;

public class DistanciaReina implements Restriccion {

	private final VarDom A, B;
	private final int valor;
	
	public DistanciaReina(final VarDom A, final VarDom B, final int valor) {
		this.A = A;
		this.B = B;
		this.valor = valor;
	}
	
	@Override
	public boolean seCumple(HashMap<String, Instancia> instancias) {
		int var1 = (instancias.get(A.getNombre()) != null) ? instancias.get(A.getNombre()).getValor() : -1;
        int var2 = (instancias.get(B.getNombre()) != null) ? instancias.get(B.getNombre()).getValor() : -1;

        return (var1 == -1) || (var2 == -1) || Math.abs(var1-var2) != valor;
	}

}
