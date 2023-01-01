package si2021.p02.jesusvaleo679.comportamiento1.condiciones;

import core.game.StateObservation;
import si2021.p02.jesusvaleo679.motor.Condicion;

public class NoApunta implements Condicion{

	@Override
	public boolean seCumple(StateObservation so) {
		
		// Debemos comprobar si se está apuntando hacia abajo
		if(so.getAvatarOrientation().y != 1.00) {
			return true;
		}
		return false;
	}
}
