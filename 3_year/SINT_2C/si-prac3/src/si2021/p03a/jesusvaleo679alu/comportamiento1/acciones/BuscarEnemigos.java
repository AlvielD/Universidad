package si2021.p03a.jesusvaleo679alu.comportamiento1.acciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p03a.jesusvaleo679alu.motorarbol.Accion;

public class BuscarEnemigos extends Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		/**
		 * Calcula en que direccion estan la mayoria de los
		 * tanques con respecto al jugador. Si es a la derecha
		 * se mueve en esa direccion, sino se mueve a la izquierda.
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
			int i = 0, d = 0;
			for (Observation e : enemigos)
				if (e.position.x <= so.getAvatarPosition().x)
					i++;
				else d++;
			
			if (d > i) return ACTIONS.ACTION_RIGHT;
		}
		
		return ACTIONS.ACTION_LEFT;
	}

}
