package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;
import tools.Vector2d;

public class PosiblePeligro extends Logica {

	public PosiblePeligro(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
		Vector2d posJugador = so.getAvatarPosition();
		Vector2d zona[] = {new Vector2d(posJugador.x - 2*so.getBlockSize(), posJugador.y + so.getBlockSize()),
				new Vector2d(posJugador.x + so.getBlockSize()/2, posJugador.y + so.getBlockSize())};
		
		ArrayList<Observation> proyectiles = new ArrayList<>();
		for (ArrayList<Observation> arr : so.getMovablePositions()) {
			for (Observation o : arr) {
				if (o.category == 6 && o.itype == 8)
					proyectiles.add(o);
			}
		}
		
		for (Observation o : proyectiles) {
			if ((o.position.x+Peligro.FACTOR >= zona[0].x && o.position.x-Peligro.FACTOR <= zona[0].x + 3/2*so.getBlockSize())
					|| (o.position.x+Peligro.FACTOR >= zona[1].x && o.position.x-Peligro.FACTOR <= zona[1].x + 3/2*so.getBlockSize())) {
				return nodoSI;
			}
		}
		
		return nodoNO;
	}

}
