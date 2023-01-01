package com.si.strips;

import java.util.ArrayList;
import java.util.Map;

public class Predicado implements IApilable {

	protected final String nombre;
	protected ArrayList<Parametro> parametros;
	protected final boolean negado;
	
	public Predicado(final String nombre, final boolean negado) {
		this.nombre = nombre;
		this.negado = negado;
		this.parametros = new ArrayList<>();
	}
	
	public Predicado(final String nombre, final boolean negado,
			final ArrayList<String> params, final boolean instanciados) {
		this(nombre, negado);
		for (String s : params)
			this.parametros.add(new Parametro(s, instanciados));
	}
	
	// Constructor de copia
	public Predicado(final Predicado p) {
		this.nombre = p.nombre;
		this.negado = p.negado;
		this.parametros = new ArrayList<>(p.parametros);
	}
	
	public int getTipo() {
		return PREDICADO;
	}
	
	public void agregarParametro(final Parametro p) {
		this.parametros.add(p);
	}
	
	@Override
	public String getNombre() {
		return this.nombre;
	}
	
	@Override
	public ArrayList<Parametro> getParametros() {
		return this.parametros;
	}
	
	public boolean estaNegado() {
		return this.negado;
	}
	
	public Predicado getContrario() {
		Predicado contrario = new Predicado(this.nombre, !this.negado);
		contrario.parametros = new ArrayList<>(this.parametros);
		return contrario;
	}
	
	@Override
	public boolean estaInstanciado() {
		for (Parametro p : this.parametros)
			if (!p.estaInstanciado())
				return false;
		
		return true;
	}
	
	@Override
	public void reemplazarParametros(Map<String, Parametro> tabla) {
		reemplazarParametrosDeLista(tabla, this.parametros);
	}
	
	public static void reemplazarParametrosDeLista(Map<String, Parametro> tabla,
			ArrayList<Parametro> lista) {
		for (int i = 0; i < lista.size(); ++i) {
			Parametro original = lista.get(i);
			
			if (!original.estaInstanciado()) {
				Parametro reemplazar = tabla.get(original.getNombre());
				
				if (reemplazar != null) {
					lista.add(i, reemplazar);
					lista.remove(i + 1);
				}
			}
		}
	}
	
	@Override
	public Object clone() {
		String nuevoNombre = new String(this.nombre);
		
		if (this.parametros.isEmpty()) {
			return new Predicado(nuevoNombre, this.negado);
		} else {
			ArrayList<String> nuevosParametros = new ArrayList<>();
			for (Parametro p : this.parametros) nuevosParametros.add(new String(p.getNombre()));
			
			boolean instanciado = this.parametros.get(0).estaInstanciado();
			
			return new Predicado(nuevoNombre, this.negado, nuevosParametros, instanciado);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Predicado) {
			Predicado po = (Predicado) o;
			
			if (this.parametros.size() != po.parametros.size()) return false;
			
			for (int i = 0; i < this.parametros.size(); i++)
				if (!this.parametros.get(i).equals(po.parametros.get(i)))
					return false;
			
			return (po.nombre.equals(this.nombre) && this.negado == po.negado);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(((this.negado) ? '\u00ac' : "")).append(" ")
			.append(this.nombre).append("(");
		for (int i = 0; i < this.parametros.size(); i++) {
			sb.append(this.parametros.get(i));
			if (i != this.parametros.size() - 1) sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}
	
}
