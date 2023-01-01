package com.si.chr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.si.busqueda.Accion;
import com.si.busqueda.Estado;

public class EstadoCHR implements Estado {
	
	private ArrayList<VarDom> pendientes;
	private HashMap<String, Instancia> instancias;
	
	private final ArrayList<Restriccion> restricciones;

	public EstadoCHR(final ArrayList<VarDom> pendientes,
			HashMap<String, Instancia> instancias, final ArrayList<Restriccion> restricciones) {
		this.pendientes = pendientes;
		this.instancias = instancias;
		this.restricciones = restricciones;
	}
	
	@Override
	public boolean esFinal() {
		return pendientes.isEmpty();
	}

	@Override
	public Estado getSucesor(Accion accion) {
		Instancia instancia = (Instancia) accion;
		EstadoCHR nuevo = this.copy();
		
		nuevo.instancias.put(instancia.getVariable().getNombre(), instancia);
		
		if (nuevo.esValido()) 
			return nuevo;
		
		return null;
	}

	private boolean esValido() {
		for (Restriccion r : restricciones)
			if (!r.seCumple(instancias)) return false;
		return true;
	}

	@Override
	public ArrayList<Accion> getAccionesPosibles() {
		ArrayList<Accion> acciones = new ArrayList<>();
		VarDom var = pendientes.remove(0);
		
		for (int i : var.getDominio())
			acciones.add(new Instancia(var, i));
		
		return acciones;
	}
	
	@SuppressWarnings("unchecked")
	public EstadoCHR copy() {
		ArrayList<VarDom> nuevoPendientes = new ArrayList<>();
		nuevoPendientes = (ArrayList<VarDom>) this.pendientes.clone();
		Set<Entry<String, Instancia>> entradas = instancias.entrySet();
		HashMap<String,Instancia> nuevoInstancias = new HashMap<>();
		for (Entry<String,Instancia> e : entradas) {
			String nn = new String(e.getKey());
			Object ni = (Instancia) e.getValue().clone();
			nuevoInstancias.put(nn, (Instancia) ni);
		}
		return new EstadoCHR(nuevoPendientes, nuevoInstancias, this.restricciones);
	}

}
