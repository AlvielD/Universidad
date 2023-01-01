package si2021.p02.jesusvaleo679.motor;

import core.game.StateObservation;
import ontology.Types.ACTIONS;

public interface Accion {

	/**
	 * Devuelve la acción a realizar en el próximo tick.
	 * @param so Percepción en el tick actual.
	 * @return La acción que se hará en el próximo tick.
	 */
	public ACTIONS getAccion(final StateObservation so);

}
