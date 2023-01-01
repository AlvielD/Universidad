package si2021.p02.jesusvaleo679.motor;

import java.util.ArrayList;
import java.util.Iterator;

import core.game.StateObservation;
import ontology.Types.ACTIONS;;

public class Regla {

	private ArrayList<Condicion> condiciones;
	private Accion accion;
	
	public Regla() {
		condiciones = new ArrayList<>();
	}
	
	/**
	 * Comprueba que se cumplan las condiciones de la regla.
	 * @param so Percepción del tick actual.
	 * @return true si se cumplen todas las condiciones.
	 */
	public boolean seCumple(final StateObservation so) {
		Iterator<Condicion> it = condiciones.iterator();
		while (it.hasNext())
			if (!it.next().seCumple(so)) return false;
		return true;
	}
	
	/**
	 * Establece la acción a realizar si se dan todas las
	 * condiciones de la regla.
	 * @param accion Acción nueva a realizar.
	 */
	public void setAccion(final Accion accion) {
		this.accion = accion;
	}
	
	/**
	 * Devuelve la acción a realizar en el tick siguiente.
	 * @param so Percepción en el tick actual.
	 * @return La acción que se hará en el próximo tick.
	 */
	public ACTIONS getAccion(final StateObservation so) {
		return this.accion.getAccion(so);
	}
	
	/**
	 * Agrega una nueva condición a la lista de condicones
	 * de la regla.
	 * @param condicion Nueva condición a agregar.
	 */
	public void agregarCondicion(final Condicion condicion) {
		condiciones.add(condicion);
	}

}
