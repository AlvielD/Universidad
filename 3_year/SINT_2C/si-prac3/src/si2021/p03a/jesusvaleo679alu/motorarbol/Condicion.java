package si2021.p03a.jesusvaleo679alu.motorarbol;

import core.game.StateObservation;

public abstract class Condicion extends Nodo {
	
	@Override
	public Accion decidir(StateObservation so) {
		return eligeRama(so).decidir(so);
	}
	
	public abstract Nodo eligeRama(StateObservation so);
	
}
