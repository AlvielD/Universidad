package com.si.busqueda;

import java.util.ArrayList;

public interface Estado {

	public boolean esFinal();

	public Estado getSucesor(Accion accion);
	
	public ArrayList<Accion> getAccionesPosibles();

}
