package si2021.p03a.jesusvaleo679alu.comportamiento1.acciones;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p03a.jesusvaleo679alu.motorarbol.Accion;

public class MoverDerecha extends Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		return ACTIONS.ACTION_RIGHT;
	}

}
