package si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones;

import si2021.p03b.jesusvaleo679alu.maquinaEstados.Condicion;
import core.game.StateObservation;

public class SinMunicion extends Condicion {

	@Override
	public boolean seCumple(StateObservation so) {
		Municion m = new Municion();
		return !(m.seCumple(so));
	}
}
