package com.si.strips.operadores;

import com.si.busqueda.Accion;
import com.si.strips.EstadoSTRIPS;
import com.si.strips.IApilable;
import com.si.strips.Operador;

public class AgregarOperadorAPila extends Accion {

	private final Operador operador;
	
	public AgregarOperadorAPila(IApilable cima) {
		super(EstadoSTRIPS.AGREGAR_OPERADOR_USUARIO_A_PILA);
		this.operador = ((Operador) cima);
	}
	
	public Operador getOperador() {
		return this.operador;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Se agrega el operador siguiente a la pila: ");
		sb.append(this.operador.toString());
		return sb.toString();
	}

}
