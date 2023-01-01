package si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03b.jesusvaleo679alu.comportamiento1.Comportamiento1;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Condicion;
import tools.Vector2d;

public class TieneBlanco extends Condicion {

	private static int FACTOR = Comportamiento1.FACTOR;

	@Override
	public boolean seCumple(StateObservation so) {
		ArrayList<Observation> enemigos = new ArrayList<>();
		
		ArrayList<Observation> npcs[] = so.getNPCPositions();
		for (ArrayList<Observation> arr : npcs) {
			for (Observation ob : arr) {
				if(ob.category == 3 && ob.itype == 15) {
					enemigos.add(ob);
				}
			}
		}
		
		Vector2d posJugador = so.getAvatarPosition();
		
		for (Observation o : enemigos) {
			Vector2d posEnem = o.position;
			
			if ((posEnem.x >= posJugador.x - 2*so.getBlockSize() - FACTOR && posEnem.x <= posJugador.x - so.getBlockSize() + FACTOR)
					|| (posEnem.x >= posJugador.x + 2*so.getBlockSize() + FACTOR && posEnem.x <= posJugador.x + 3*so.getBlockSize() - FACTOR))
				return true;
		}
		
		return false;
	}

}
