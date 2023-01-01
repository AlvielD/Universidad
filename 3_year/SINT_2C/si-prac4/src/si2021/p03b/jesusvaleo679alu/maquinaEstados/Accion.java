package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import core.game.StateObservation;
import ontology.Types.ACTIONS;

public abstract class Accion {

	public abstract ACTIONS getAccion(StateObservation so);
	
}
