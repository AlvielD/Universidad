package com.si.chr;

import com.si.busqueda.Accion;

public class Instancia extends Accion {

	private VarDom variable;
	private int valor;
	
	public Instancia(final VarDom variable, final int valor) {
		super(-1);
		this.variable = variable;
		this.valor = valor;
	}
	
	public VarDom getVariable() {
		return this.variable;
	}
	
	public int getValor() {
		return this.valor;
	}

	@Override
	public String toString() {
		StringBuilder devolver = new StringBuilder();
		devolver.append(variable.getNombre());
		devolver.append(" -> ");
		if (ProblemaCHR.valores == null) devolver.append(valor);
		else devolver.append(ProblemaCHR.valores.get(valor));
		return devolver.toString();
	}
	
	@Override
	public Object clone() {
		VarDom nuevaVariable = (VarDom) variable.clone();
		return new Instancia(nuevaVariable, valor);
	}
	
}
