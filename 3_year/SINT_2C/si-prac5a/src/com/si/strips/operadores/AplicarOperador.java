package com.si.strips.operadores;

import com.si.busqueda.Accion;
import com.si.strips.EstadoSTRIPS;
import com.si.strips.IApilable;
import com.si.strips.Operador;

public class AplicarOperador extends Accion {

	private final Operador operador;
	
	public AplicarOperador(IApilable cima) {
		super(EstadoSTRIPS.APLICAR_OPERADOR_USUARIO);
		this.operador = (Operador) cima;
	}
	
	public Operador getOperadorSTRIPS() {
		return this.operador;
	}

	@Override
	public String toString() {
		return "Aplicar el operador: " + operador.toString();
	}

}
