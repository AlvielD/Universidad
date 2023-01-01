package si2021.p02.jesusvaleo679.comportamiento1.acciones;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p02.jesusvaleo679.motor.Accion;

public class Disparar implements Accion {

	public Disparar() {
		super();
	}

	@Override
	public ACTIONS getAccion(StateObservation so) {
		return ACTIONS.ACTION_USE;
	}

}
