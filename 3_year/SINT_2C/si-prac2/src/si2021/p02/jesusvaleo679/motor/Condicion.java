package si2021.p02.jesusvaleo679.motor;

import core.game.StateObservation;

public interface Condicion {
	
	/**
	 * Comprueba que se cumpla la condici�n.
	 * @param so Percepci�n en el tick actual.
	 * @return true si se cumple la condici�n.
	 */
	public boolean seCumple(final StateObservation so);
	
}
