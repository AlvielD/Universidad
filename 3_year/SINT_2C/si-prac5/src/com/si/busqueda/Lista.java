package com.si.busqueda;

import java.util.ArrayList;
import java.util.Comparator;

public class Lista extends ArrayList<NodoB> {

	private static final long serialVersionUID = 1L;
	
	public void insertarOrdenado(final Lista lista) {
		Lista ordenado = new Lista();
		ordenado.addAll(0, this);
		ordenado.addAll(lista);
		ordenado.sort(new Comparator<NodoB>() {
			@Override
			public int compare(NodoB n1, NodoB n2) {
				if (n1.getCoste() < n2.getCoste())
					return -1;
				else if (n1.getCoste() == n2.getCoste())
					return 0;
				else return 1;
			}
		});
		this.clear();
		this.addAll(0, ordenado);
	}
}
