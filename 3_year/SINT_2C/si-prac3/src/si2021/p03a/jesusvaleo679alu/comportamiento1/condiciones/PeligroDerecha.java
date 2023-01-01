package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;
import tools.Vector2d;

public class PeligroDerecha extends Logica {

	public PeligroDerecha(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
		Vector2d posJugador = so.getAvatarPosition();
		Vector2d zona = new Vector2d(posJugador.x + so.getBlockSize(),
				posJugador.y + so.getBlockSize());
		
		ArrayList<Observation> proyectiles = new ArrayList<>();
		for (ArrayList<Observation> arr : so.getMovablePositions()) {
			for (Observation o : arr) {
				if (o.category == 6 && o.itype == 8)
					proyectiles.add(o);
			}
		}
		
		for (Observation o : proyectiles) {
			if (o.position.x+Peligro.FACTOR >= zona.x
					&& o.position.x-Peligro.FACTOR <= zona.x + so.getBlockSize()) {
				return nodoSI;
			}
		}
		
		return nodoNO;
	}

}
