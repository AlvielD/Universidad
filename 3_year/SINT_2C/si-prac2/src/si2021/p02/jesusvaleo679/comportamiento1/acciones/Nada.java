package si2021.p02.jesusvaleo679.comportamiento1.acciones;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p02.jesusvaleo679.motor.Accion;

public class Nada implements Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		return ACTIONS.ACTION_NIL;
	}
}
