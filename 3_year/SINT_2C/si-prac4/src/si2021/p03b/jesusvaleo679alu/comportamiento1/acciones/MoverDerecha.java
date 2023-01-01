package si2021.p03b.jesusvaleo679alu.comportamiento1.acciones;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Accion;

public class MoverDerecha extends Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		return ACTIONS.ACTION_RIGHT;
	}

}
