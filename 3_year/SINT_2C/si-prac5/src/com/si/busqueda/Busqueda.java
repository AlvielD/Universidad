package com.si.busqueda;

import java.util.List;

public class Busqueda {
	
	public static List<Accion> buscar (Estado inicial) { 
		Lista abiertos = new Lista(); 
		Lista cerrados = new Lista();
		
		abiertos.add(new NodoB(inicial)); 
		while (!abiertos.isEmpty()) {
			NodoB actual = abiertos.remove(0); 
			cerrados.add(actual);
			
			if (actual.pruebaMeta()) { 
				return actual.recuperarCamino(); 
			} else { 
				Lista sucesores = actual.getSucesores(); 
				sucesores = quitarRepetidos(sucesores, cerrados);
				abiertos.insertarOrdenado(sucesores);
			}
		}
		
		return null;
	}
	
	private static Lista quitarRepetidos(Lista sucesores, Lista cerrados) {
		Lista devolver = new Lista();
		
		for (NodoB suc : sucesores)
			if (!cerrados.contains(suc))
				devolver.add(suc);
		
		return devolver;
	}
	
}
