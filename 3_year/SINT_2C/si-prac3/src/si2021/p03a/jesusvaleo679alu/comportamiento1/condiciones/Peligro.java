package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;
import tools.Vector2d;

public class Peligro extends Logica {
	
	public static int FACTOR = 4;

	public Peligro(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
		Vector2d posJugador = so.getAvatarPosition();
		Vector2d zona[] = {new Vector2d(posJugador.x, posJugador.y + so.getBlockSize()),
				new Vector2d(posJugador.x - so.getBlockSize(), posJugador.y + so.getBlockSize()),
				new Vector2d(posJugador.x + so.getBlockSize(), posJugador.y + so.getBlockSize())};
		
		ArrayList<Observation> proyectiles = new ArrayList<>();
		for (ArrayList<Observation> arr : so.getMovablePositions()) {
			for (Observation o : arr) {
				if (o.category == 6 && o.itype == 8)
					proyectiles.add(o);
			}
		}
		
		for (Observation o : proyectiles) {
			for (Vector2d v : zona)
				if (o.position.x+FACTOR >= v.x && o.position.x-FACTOR <= v.x + so.getBlockSize())
					return nodoSI;
		}
		
		return nodoNO;
	}

}
