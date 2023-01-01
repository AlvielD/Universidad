package com.si.strips;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.si.util.Permutaciones;

public class PredicadoSet implements IApilable {

	private Set<Predicado> predicados;
	
	public PredicadoSet() {
		this.predicados = new HashSet<>();
	}
	
	// Constructor de copia
	public PredicadoSet(final Set<Predicado> ps) {
		this();
		
		Iterator<Predicado> it = ps.iterator();
		while (it.hasNext())
			this.predicados.add(new Predicado(it.next()));
	}
	
	public ArrayList<Predicado> convertirLista() {
		//return new ArrayList<>(this.predicados);
		ArrayList<Predicado> pl = new ArrayList<>();
		Iterator<Predicado> it = this.predicados.iterator();
		while (it.hasNext())
			pl.add(it.next());
		return pl;
	}
	
	public void agregarPredicado(final Predicado p) {
		predicados.add(p);
	}
	
	public void agregarTodos(final PredicadoSet p) {
		predicados.addAll(p.predicados);
	}
	
	public void eliminarPredicado(final Predicado p) {
		predicados.remove(p);
	}
	
	public void eliminarTodos(final PredicadoSet p) {
		predicados.removeAll(p.predicados);
	}
	
	public boolean tiene(final Predicado p) {
		Iterator<Predicado> it = this.predicados.iterator();
		
		while (it.hasNext())
			if (it.next().equals(p))
				return true;
		
		return false;
	}
	
	public boolean tieneTodos(final PredicadoSet p) {
		Iterator<Predicado> it = this.predicados.iterator();
		int buscados = p.convertirLista().size();
		int encontrados = 0;
		
		while (it.hasNext()) {
			Iterator<Predicado> it2 = p.predicados.iterator();
			Predicado actual = it.next();
			while (it2.hasNext())
				if (actual.equals(it2.next()))
					encontrados++;
 		}
		
		return encontrados == buscados;
	}
	
	@Override
	public String getNombre() {
		return "";
	}
	
	@Override
	public ArrayList<Parametro> getParametros() {
		return new ArrayList<>();
	}
	
	@Override
	public boolean estaInstanciado() {
		for (Predicado p : convertirLista())
			if (!p.estaInstanciado())
				return false;
		
		return true;
	}
	
	public ArrayList<ArrayList<Predicado>> getCombinacionesPosibles() {
		// Todas las combinaciones posibles de la lista de predicados
		ArrayList<ArrayList<Predicado>> combinaciones = new ArrayList<>();
		int numElementos = this.predicados.size();
		
		// Obtenemos los elementos del conjunto en un ArrayList
		ArrayList<Predicado> pred = convertirLista();
		
		// Las permutaciones te da los numeros desde el 1 hasta el numElementos,
		// por tanto, hay que restar 1 al numero que se obtenga de permutaciones[i][j]
		int[][] permutaciones = Permutaciones.generarPermutaciones(numElementos);
		
		for (int i = 0; i < permutaciones.length; i++) {
			ArrayList<Predicado> comb = new ArrayList<>();
			for (int j = 0; j < permutaciones[i].length; j++)
				comb.add(pred.get(permutaciones[i][j] - 1));
			combinaciones.add(comb);
		}
		
		return combinaciones;
	}

	@Override
	public int getTipo() {
		return PREDICADO_SET;
	}
	
	@Override
	public void reemplazarParametros(Map<String, Parametro> tabla) {
		for (Predicado p : this.predicados)
			p.reemplazarParametros(tabla);
	}
	
	@Override
	public Object clone() {
		ArrayList<Predicado> pred = convertirLista();
		
		PredicadoSet nuevo = new PredicadoSet();
		for (Predicado p : pred) nuevo.agregarPredicado((Predicado) p.clone());
		
		return nuevo;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof PredicadoSet) {
			PredicadoSet op = (PredicadoSet) o;
			ArrayList<Predicado> pl = op.convertirLista();
			ArrayList<Predicado> pred = convertirLista();
			if (pl.size() != pred.size()) return false;
			
			for (int i = 0; i < pred.size(); i++)
				if (!pred.get(i).equals(pl.get(i)))
					return false;
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		Iterator<Predicado> it = this.predicados.iterator();
		int i = 0;
		while (it.hasNext()) {
			Predicado p = it.next();
			sb.append(p.toString());
			if (i != this.predicados.size() - 1) sb.append(", ");
			i++;
		}
		sb.append(" ]");
		return sb.toString();
	}

}
