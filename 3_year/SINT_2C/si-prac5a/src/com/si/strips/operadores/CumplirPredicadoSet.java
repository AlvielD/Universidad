package com.si.strips.operadores;

import com.si.busqueda.Accion;
import com.si.strips.EstadoSTRIPS;
import com.si.strips.IApilable;
import com.si.strips.PredicadoSet;

public class CumplirPredicadoSet extends Accion {

	private final PredicadoSet predicados;
	
	public CumplirPredicadoSet(IApilable cima) {
		super(EstadoSTRIPS.CUMPLIR_PREDICADOSET);
		this.predicados = (PredicadoSet) cima;
	}
	
	@Override
	public String toString() {
		return "Se cumple y se quita la conjunción de predicados: \n"
				+ predicados.toString();
	}

}
