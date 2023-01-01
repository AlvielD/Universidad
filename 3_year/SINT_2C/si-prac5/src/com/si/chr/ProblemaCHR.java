package com.si.chr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.si.busqueda.Accion;
import com.si.busqueda.Busqueda;

public class ProblemaCHR {

	private ArrayList<VarDom> variables;
	private ArrayList<Restriccion> restricciones;
	public static ArrayList<String> valores;
	
	public ProblemaCHR(final ArrayList<VarDom> variables,
			final ArrayList<Restriccion> restricciones,
			final ArrayList<String> valores) {
		this.variables = variables;
		this.restricciones = restricciones;
		ProblemaCHR.valores = valores;
	}
	
	public List<Accion> resolver() {
		EstadoCHR inicial = new EstadoCHR(variables, new HashMap<>(), restricciones);
		return Busqueda.buscar(inicial);
	}
	
}
