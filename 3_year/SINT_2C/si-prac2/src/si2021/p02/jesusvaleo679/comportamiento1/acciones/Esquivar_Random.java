package si2021.p02.jesusvaleo679.comportamiento1.acciones;

import java.util.Random;

import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p02.jesusvaleo679.motor.Accion;

public class Esquivar_Random implements Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		Random r = new Random(System.nanoTime());
		if (r.nextInt(10) < 5) return ACTIONS.ACTION_RIGHT;
		else return ACTIONS.ACTION_LEFT;
	}

}
