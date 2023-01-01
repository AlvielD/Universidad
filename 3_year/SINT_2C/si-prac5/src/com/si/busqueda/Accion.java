package com.si.busqueda;

public abstract class Accion {

	private int tipo;
	
	public Accion(final int tipo) {
		this.tipo = tipo;
	}
	
	public int getCoste() {
		return 1;
	}
	
	public int getTipo() {
		return tipo;
	}
	
	@Override
	public abstract String toString();

}
