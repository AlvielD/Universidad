package com.si.strips.operadores;

import java.util.ArrayList;

import com.si.busqueda.Accion;
import com.si.strips.EstadoSTRIPS;
import com.si.strips.IApilable;
import com.si.strips.Predicado;
import com.si.strips.PredicadoSet;

public class AgregarPredicadosAPila extends Accion {

	private final ArrayList<Predicado> predicados;
	
	public AgregarPredicadosAPila(IApilable cima) {
		super(EstadoSTRIPS.AGREGAR_PREDICADOS_A_PILA);
		this.predicados = ((PredicadoSet) cima).convertirLista();
	}
	
	public ArrayList<Predicado> getPredicados() {
		return this.predicados;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Se agregan todas las combinaciones posibles de los predicados del conjunto siguiente a la pila:\n{ ");
		for (int i = 0; i < this.predicados.size(); i++) {
			sb.append(this.predicados.get(i).toString());
			if (i != this.predicados.size() - 1) sb.append(", ");
		}
		sb.append(" }");
		return sb.toString();
	}

}
