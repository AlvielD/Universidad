package com.si.chr.restricciones;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;
import com.si.chr.VarDom;

public class Distinto implements Restriccion {

	private VarDom A, B;
	
	public Distinto(VarDom A, VarDom B) {
		this.A = A;
		this.B = B;
	}

	@Override
	public boolean seCumple(final HashMap<String, Instancia> instancias) {
		Instancia valorA = instancias.get(A.getNombre());
		Instancia valorB = instancias.get(B.getNombre());
		
		return (valorA == null) || (valorB == null) || (valorA.getValor() != valorB.getValor());
	}
}
