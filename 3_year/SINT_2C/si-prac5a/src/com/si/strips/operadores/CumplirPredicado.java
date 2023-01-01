package com.si.strips.operadores;

import com.si.busqueda.Accion;
import com.si.strips.EstadoSTRIPS;
import com.si.strips.IApilable;
import com.si.strips.Predicado;

public class CumplirPredicado extends Accion {

	private final Predicado predicado;
	
	public CumplirPredicado(IApilable cima) {
		super(EstadoSTRIPS.CUMPLIR_PREDICADO);
		this.predicado = (Predicado) cima;
	}

	@Override
	public String toString() {
		return "Se cumple y se quita el predicado: " + predicado.toString();
	}

}
