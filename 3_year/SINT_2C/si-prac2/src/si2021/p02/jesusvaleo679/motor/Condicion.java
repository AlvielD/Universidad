package si2021.p02.jesusvaleo679.motor;

import core.game.StateObservation;

public interface Condicion {
	
	/**
	 * Comprueba que se cumpla la condición.
	 * @param so Percepción en el tick actual.
	 * @return true si se cumple la condición.
	 */
	public boolean seCumple(final StateObservation so);
	
}
