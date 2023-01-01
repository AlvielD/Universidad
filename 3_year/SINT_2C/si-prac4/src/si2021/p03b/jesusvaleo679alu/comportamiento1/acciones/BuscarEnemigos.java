package si2021.p03b.jesusvaleo679alu.comportamiento1.acciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Accion;

public class BuscarEnemigos extends Accion {

	@Override
	public ACTIONS getAccion(StateObservation so) {
		/**
		 * Calcula la posicion del primer tanque de la
		 * lista de enemigos y lo sigue
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
			Observation enemigo = enemigos.get(0);
			
			if (enemigo.position.x > so.getAvatarPosition().x - 2*so.getBlockSize())
				return ACTIONS.ACTION_RIGHT;
			
			return ACTIONS.ACTION_LEFT;
		}
		
		return ACTIONS.ACTION_UP;
	}

}
