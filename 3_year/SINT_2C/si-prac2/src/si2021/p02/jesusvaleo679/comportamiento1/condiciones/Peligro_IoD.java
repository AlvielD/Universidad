package si2021.p02.jesusvaleo679.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p02.jesusvaleo679.comportamiento1.Comportamiento1;
import si2021.p02.jesusvaleo679.motor.Condicion;
import tools.Vector2d;

public class Peligro_IoD implements Condicion {

	private static int FACTOR = Comportamiento1.FACTOR;
	
	@Override
	public boolean seCumple(StateObservation so) {
		Vector2d posJugador = so.getAvatarPosition();
		Vector2d zona[] = {new Vector2d(posJugador.x - so.getBlockSize(), posJugador.y + so.getBlockSize()),
				new Vector2d(posJugador.x + so.getBlockSize(), posJugador.y + so.getBlockSize())};
		
		ArrayList<Observation> proyectiles = new ArrayList<>();
		for (ArrayList<Observation> arr : so.getMovablePositions()) {
			for (Observation o : arr) {
				if (o.category == 6 && o.itype == 8)
					proyectiles.add(o);
			}
		}
		
		for (Observation o : proyectiles) {
			if ((o.position.x+FACTOR >= zona[0].x && o.position.x-FACTOR <= zona[0].x + so.getBlockSize())
					|| (o.position.x+FACTOR >= zona[1].x && o.position.x-FACTOR <= zona[1].x + so.getBlockSize())) {
				return true;
			}
		}
		
		return false;
	}

}
