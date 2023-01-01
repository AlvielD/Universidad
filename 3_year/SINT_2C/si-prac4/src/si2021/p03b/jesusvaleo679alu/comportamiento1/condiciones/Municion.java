package si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.HashMap;

import core.game.StateObservation;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Condicion;

public class Municion extends Condicion {

	@Override
	public boolean seCumple(StateObservation so) {
		HashMap<Integer, Integer> recursos = so.getAvatarResources();
		
		if (recursos == null || recursos.isEmpty()
				|| !recursos.containsKey(20)  || recursos.get(20) == 0)
			return false;
		
		return true;
	}

}
