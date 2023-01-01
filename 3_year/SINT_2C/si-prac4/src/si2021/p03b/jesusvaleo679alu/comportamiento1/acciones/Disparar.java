package si2021.p03b.jesusvaleo679alu.comportamiento1.acciones;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Accion;

public class Disparar extends Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		if (so.getAvatarOrientation().y != 1.00)
			return ACTIONS.ACTION_UP;
		
		return ACTIONS.ACTION_USE;
	}

}