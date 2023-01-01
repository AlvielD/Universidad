package si2021.p03a.jesusvaleo679alu.comportamiento1.condiciones;

import java.util.ArrayList;

import core.game.Observation;
import core.game.StateObservation;
import si2021.p03a.jesusvaleo679alu.motorarbol.Logica;
import si2021.p03a.jesusvaleo679alu.motorarbol.Nodo;

public class Enemigos extends Logica {

	public Enemigos(Nodo nodoSI, Nodo nodoNO) {
		super(nodoSI, nodoNO);
	}

	@Override
	public Nodo eligeRama(StateObservation so) {
		
		int enemigos = 0;
		
		// NPCS
		ArrayList<Observation> npcs[] = so.getNPCPositions();
		for (ArrayList<Observation> arr : npcs) {
			for (Observation ob : arr) {
				if(ob.category == 3 && ob.itype == 15) {
					enemigos++;
				}
			}
		}
		
		// Si no hay enemigos se devuelve el nodoNO
		if (enemigos == 0)
			return nodoNO;
		
		// En cualquier otro caso se devuelve el nodoSI
		return nodoSI;
	}

}
