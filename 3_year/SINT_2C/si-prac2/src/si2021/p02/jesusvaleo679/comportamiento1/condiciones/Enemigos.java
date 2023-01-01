package si2021.p02.jesusvaleo679.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p02.jesusvaleo679.motor.Condicion;

public class Enemigos implements Condicion {

	@Override
	public boolean seCumple(StateObservation so) {
		
		int enemigos = 0;
		
		// NPCS
		ArrayList<Observation> npcs[] = so.getNPCPositions();
		for (ArrayList<Observation> arr : npcs) {
			for (Observation ob : arr) {
				if(ob.category == 3 && ob.itype == 15) {
					enemigos++;
				}
			}
		}
		
		return (enemigos == 0);
	}

	
}
