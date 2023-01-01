package si2021.p02.jesusvaleo679.comportamiento1.condiciones;

import core.game.StateObservation;
import si2021.p02.jesusvaleo679.motor.Condicion;

public class Predeterminada implements Condicion {

	@Override
	public boolean seCumple(StateObservation so) {
		return true;
	}

}
