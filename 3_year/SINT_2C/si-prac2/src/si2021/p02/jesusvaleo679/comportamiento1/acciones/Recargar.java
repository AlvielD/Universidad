package si2021.p02.jesusvaleo679.comportamiento1.acciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import ontology.Types.ACTIONS;
import si2021.p02.jesusvaleo679.motor.Accion;
import tools.Vector2d;

public class Recargar implements Accion {

	public Recargar() {
		super();
	}

	@Override
	public ACTIONS getAccion(StateObservation so) {
		
		Vector2d posJugador = so.getAvatarPosition();
		
		ArrayList<Observation>[] movables = so.getMovablePositions(posJugador);
		Observation ammo = null;
		
		// Obtenemos el paquete de munición más cercano.
		int im = 0, jm;
		while (ammo == null && im < movables.length) {
			jm = 0;
			while (ammo == null && jm < movables[im].size()) {
				if (movables[im].get(jm).category ==  6
						&& movables[im].get(jm).itype == 19)
					ammo = movables[im].get(jm);
				jm++;
			}
			im++;
		}
		
		/*
		 * Calculamos el vector de diferencia entre la posición del
		 * jugador y la del paquete de munición más cercano.
		 * 
		 * Si la diferencia en valor absoluto entre las coordenadas
		 * x e y del vector es positiva, el jugador se moverá horizontalmente
		 * en caso contrario, lo hará verticalmente.
		 * 
		 * Si el vector diferencia tiene la componente x positiva, el paquete
		 * de munición está a su izquierda, por lo que se moverá en esa dirección,
		 * si es negativa, está a su derecha y se mueve hacia esa otra dirección.
		 * 
		 * Para calcular la dirección vertical ocurre lo mismo. Si el vector tiene
		 * componente y positiva, el paquete está encima del jugador y se mueve
		 * hacia arriba. Si es negativa, está abajo y se mueve en esa dirección.
		 */
		if (ammo != null) {
			Vector2d dif = new Vector2d(posJugador.x - ammo.position.x,
					posJugador.y - ammo.position.y);
			
			if (Math.abs(dif.x) - Math.abs(dif.y) > 0) {
				if (dif.x > 0) return ACTIONS.ACTION_LEFT;
				else return ACTIONS.ACTION_RIGHT;
			} else {
				if (dif.y > 0) return ACTIONS.ACTION_UP;
				else return ACTIONS.ACTION_DOWN;
			}
		}

		return ACTIONS.ACTION_NIL;
	}

}
