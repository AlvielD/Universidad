package si2021.p03a.jesusvaleo679alu.motorarbol;

import core.game.StateObservation;
import ontology.Types.ACTIONS;

public abstract class Accion extends Nodo {

	@Override
	public Accion decidir(StateObservation so) {
		return this;
	}
	
	public abstract ACTIONS getAccion(StateObservation so);
	
}
