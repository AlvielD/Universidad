package si2021.p02.jesusvaleo679.comportamiento1.acciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p02.jesusvaleo679.motor.Accion;

public class PorDefecto implements Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		/**
		 * Calcula la posici�n media de los tanques con respecto
		 * a la del jugador. Si esta es negativa, la mayor�a de
		 * los tanques est�n a la derecha y se mueve hacia all�,
		 * si es positiva, se deber�a mover a la izquierda por
		 * la raz�n inversa.
		 */
		ArrayList<Observation> enemigos = new ArrayList<>();
		
		ArrayList<Observation> npcs[] = so.getNPCPositions();
		for (ArrayList<Observation> arr : npcs) {
			for (Observation ob : arr) {
				if(ob.category == 3 && ob.itype == 15) {
					enemigos.add(ob);
				}
			}
		}
		
		if (enemigos != null && enemigos.size() > 0) {
			double media = 0;
			for (Observation e : enemigos) media += e.position.x;
			media = media / enemigos.size();
			
			if (so.getAvatarPosition().x - media < 0)
				return ACTIONS.ACTION_RIGHT;
		}
		
		return ACTIONS.ACTION_LEFT;
	}

}
