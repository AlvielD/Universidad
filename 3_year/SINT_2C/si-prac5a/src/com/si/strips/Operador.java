package com.si.strips;

import java.util.ArrayList;
import java.util.Map;

import com.si.busqueda.Accion;

public class Operador extends Accion implements IApilable {

	private final String nombre;
	private ArrayList<Parametro> parametros; 
	private PredicadoSet precondiciones;
	private PredicadoSet postcondiciones;
	
	public Operador(String nombre) {
		super(-1);
		this.nombre = nombre;
		this.parametros = new ArrayList<>();
		this.precondiciones = new PredicadoSet();
		this.postcondiciones = new PredicadoSet();
	}

	@Override
	public String getNombre() {
		return nombre;
	}
	
	@Override
	public ArrayList<Parametro> getParametros() {
		return parametros;
	}
	
	public void agregarParametro(final Parametro p) {
		this.parametros.add(p);
	}
	
	public PredicadoSet getPrecondiciones() {
		return precondiciones;
	}
	
	public void agregarPrecondicion(final Predicado p) {
		this.precondiciones.agregarPredicado(p);
	}

	public PredicadoSet getPostcondiciones() {
		return postcondiciones;
	}
	
	public void agregarPostcondicion(final Predicado p) {
		this.postcondiciones.agregarPredicado(p);
	}
	
	@Override
	public int getTipo() {
		return OPERADOR;
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
		Predicado.reemplazarParametrosDeLista(tabla, this.parametros);
		this.precondiciones.reemplazarParametros(tabla);
		this.postcondiciones.reemplazarParametros(tabla);
	}
	
	@Override
	public Object clone() {
		String nuevoNombre = new String(this.nombre);
		
		ArrayList<Parametro> nuevosParametros = new ArrayList<>();
		
		if (!this.parametros.isEmpty()) {
			for (Parametro p : this.parametros) {
				nuevosParametros.add(new Parametro(new String(p.getNombre()),
						p.estaInstanciado()));
			}
		}
		
		PredicadoSet nuevasPrecondiciones = (PredicadoSet) this.precondiciones.clone();
		PredicadoSet nuevasPostcondiciones = (PredicadoSet) this.postcondiciones.clone();
		
		Operador op = new Operador(nuevoNombre);
		
		op.parametros = nuevosParametros;
		op.precondiciones = nuevasPrecondiciones;
		op.postcondiciones = nuevasPostcondiciones;
		
		return op;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Operador) {
			Operador op = (Operador) o;
			
			for (int i = 0; i < this.parametros.size(); i++)
				if (!this.parametros.get(i).equals(op.parametros.get(i)))
					return false;
			
			return ((this.precondiciones.equals(op.precondiciones))
					&& (this.postcondiciones.equals(op.postcondiciones))
					&& (this.nombre.equals(op.nombre)));
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
        hash = hash * 17 + this.nombre.hashCode();
        hash = hash * 31 + this.parametros.hashCode();
        hash = hash * 13 + this.precondiciones.hashCode();
        hash = hash * 23 + this.postcondiciones.hashCode();
        return hash;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.nombre).append("(");
		for (int i = 0; i < this.parametros.size(); i++) {
			sb.append(this.parametros.get(i));
			if (i != this.parametros.size() - 1) sb.append(", ");
		}
		sb.append(")");
		return sb.toString();
	}
	
}
