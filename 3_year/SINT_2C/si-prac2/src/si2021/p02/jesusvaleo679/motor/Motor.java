package si2021.p02.jesusvaleo679.motor;

import java.util.ArrayList;
import java.util.Iterator;

import core.game.StateObservation;
import ontology.Types.ACTIONS;

public class Motor {

	private ArrayList<Regla> reglas;
	
	public Motor() {
		reglas = new ArrayList<>();
	}
	
	/**
	 * Agrega una regla a la lista de reglas del motor.
	 * @param regla La nueva regla a incluir.
	 */
	public void agregarRegla(final Regla regla) {
		this.reglas.add(regla);
	}
	
	/**
	 * Decide que acción realizar en base a las reglas de la
	 * lista del motor. La lista debe estar ordenada por
	 * prioridad a la hora de introducirse.
	 * @param so Percepción en el tick actual.
	 * @return Una acción a realizar en el siguiente tick.
	 */
	public ACTIONS decidir(StateObservation so) {
		Iterator<Regla> it = reglas.iterator();
		while(it.hasNext()) {
			Regla actual = it.next();
			if (actual.seCumple(so))
				return actual.getAccion(so);
		}
				
		return ACTIONS.ACTION_NIL;
	}
}
