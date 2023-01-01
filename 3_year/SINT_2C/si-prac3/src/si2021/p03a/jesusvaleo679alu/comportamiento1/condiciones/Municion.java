package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.HashMap;

import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;

public class Municion extends Logica {

	public Municion(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
		HashMap<Integer, Integer> recursos = so.getAvatarResources();
		
		// Si no hay municion, se devuelve true
		if (recursos == null || recursos.isEmpty()
				|| !recursos.containsKey(20)  || recursos.get(20) == 0)
			return nodoNO;
		
		return nodoSI;
	}
	
	

}
