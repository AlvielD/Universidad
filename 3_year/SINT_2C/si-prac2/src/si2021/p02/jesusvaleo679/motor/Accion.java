package si2021.p02.jesusvaleo679.motor;

import core.game.StateObservation;
import ontology.Types.ACTIONS;

public interface Accion {

	/**
	 * Devuelve la acci�n a realizar en el pr�ximo tick.
	 * @param so Percepci�n en el tick actual.
	 * @return La acci�n que se har� en el pr�ximo tick.
	 */
	public ACTIONS getAccion(final StateObservation so);

}
