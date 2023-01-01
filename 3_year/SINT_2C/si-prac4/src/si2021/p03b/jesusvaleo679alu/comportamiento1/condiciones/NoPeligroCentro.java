package si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03b.jesusvaleo679alu.comportamiento1.Comportamiento1;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Condicion;
import tools.Vector2d;

public class NoPeligroCentro extends Condicion {

	private static int FACTOR = Comportamiento1.FACTOR;

	@Override
	public boolean seCumple(StateObservation so) {
		Vector2d posJugador = so.getAvatarPosition();
		Vector2d iniZona = new Vector2d(posJugador.x, posJugador.y + so.getBlockSize());
		Vector2d finZona = new Vector2d(posJugador.x, posJugador.y + 2*so.getBlockSize());
		
		ArrayList<Observation> proyectiles = new ArrayList<>();
		for (ArrayList<Observation> arr : so.getMovablePositions()) {
			for (Observation o : arr) {
				if (o.category == 6 && o.itype == 8)
					proyectiles.add(o);
			}
		}
		
		for (Observation o : proyectiles) {
			Vector2d pos = o.position;
			if (pos.x >= iniZona.x - FACTOR && pos.y >= iniZona.y - FACTOR
					&& pos.x <= finZona.x + FACTOR && pos.y <= finZona.y + FACTOR)
				return false;
		}
		
		return true;
	}

}
