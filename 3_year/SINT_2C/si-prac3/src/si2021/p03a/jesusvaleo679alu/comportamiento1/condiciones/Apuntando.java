package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;

public class Apuntando extends Logica {

	public Apuntando(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
		// Debemos comprobar si se esta apuntando hacia abajo
		if(so.getAvatarOrientation().y == 1.00) {
			return nodoSI;
		}
		return nodoNO;
	}

}
