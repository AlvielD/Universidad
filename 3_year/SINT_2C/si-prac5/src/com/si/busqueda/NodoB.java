package com.si.busqueda;

import java.util.ArrayList;
import java.util.List;

public class NodoB {

	private Estado estado;
	private NodoB padre;
	private Accion accion;
	private int coste;
	
	public NodoB(final Estado estado) {
		this.estado = estado;
		this.padre = null;
		this.accion = null;
		this.coste = 0;
	}
	
	public void setPadre(final NodoB padre) {
		this.padre = padre;
	}
	
	public void setAccion(final Accion accion) {
		this.accion = accion;
	}
	
	public boolean pruebaMeta() {
		return estado.esFinal();
	}
	
	public int getCoste() {
		return coste;
	}

	public List<Accion> recuperarCamino() {
		ArrayList<Accion> acciones = new ArrayList<>();
		
		if (padre != null)
			acciones.addAll(padre.recuperarCamino());
		
		acciones.add(accion);
		
		return acciones;
	}

	public Lista getSucesores() {
		Lista sucesores = new Lista();
		
		for (Accion a : estado.getAccionesPosibles()) {
			Estado ee = estado.getSucesor(a);
			if (ee != null) {
				NodoB siguiente = new NodoB(ee);
				siguiente.setAccion(a);
				siguiente.setPadre(this);
				siguiente.coste = this.coste + a.getCoste();
				sucesores.add(siguiente);
			}
		}
		
		return sucesores;
	}
	
}
