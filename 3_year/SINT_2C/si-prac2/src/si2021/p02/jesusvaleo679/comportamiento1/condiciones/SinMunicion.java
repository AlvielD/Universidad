package si2021.p02.jesusvaleo679.comportamiento1.condiciones;

import java.util.HashMap;

import core.game.StateObservation;
import si2021.p02.jesusvaleo679.motor.Condicion;

public class SinMunicion implements Condicion {

	@Override
	public boolean seCumple(StateObservation so) {
		HashMap<Integer, Integer> recursos = so.getAvatarResources();
		
		// Si no hay munición, se devuelve true
		if (recursos == null || recursos.isEmpty()
				|| !recursos.containsKey(20)  || recursos.get(20) == 0)
			return true;
		
		return false;
	}

}
