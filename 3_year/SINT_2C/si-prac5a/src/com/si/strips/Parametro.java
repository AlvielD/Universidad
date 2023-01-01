package com.si.strips;

public class Parametro {

	private final String nombre;
	private final boolean instanciado;
	
	public Parametro(final String nombre, final boolean instanciado) {
		this.nombre = nombre;
		this.instanciado = instanciado;
	}
	
	// Constructor de copia
	public Parametro(Parametro original) {
		this.nombre = original.nombre;
		this.instanciado = original.instanciado;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public boolean estaInstanciado() {
		return this.instanciado;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Parametro) {
			Parametro p = (Parametro) o;
			return (this.nombre.equals(p.nombre))
					&& (this.instanciado == p.instanciado);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return this.nombre.hashCode() + (this.instanciado ? 0 : 1);
	}
	
	@Override
	public String toString() {
		if (this.instanciado)
			return this.nombre;
		
		return "[" + this.nombre + "]";
	}
}
