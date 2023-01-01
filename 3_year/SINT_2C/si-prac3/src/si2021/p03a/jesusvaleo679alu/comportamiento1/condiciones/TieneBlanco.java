package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;
import tools.Vector2d;

public class TieneBlanco extends Logica {

	public TieneBlanco(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
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
		
		for (int i = 0; i < enemigos.size(); i++) {
			Vector2d posEnem = enemigos.get(i).position;
			
			if ((posEnem.x >= posJugador.x - so.getBlockSize() && posEnem.x <= posJugador.x)
					|| (posEnem.x >= posJugador.x + so.getBlockSize() && posEnem.x <= posJugador.x + 2*so.getBlockSize()))
				return nodoSI;
		}
		
		return nodoNO;
	}

}
