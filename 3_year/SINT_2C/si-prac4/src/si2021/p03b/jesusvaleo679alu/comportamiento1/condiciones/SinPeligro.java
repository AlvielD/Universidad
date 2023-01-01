package si2021.p03b.jesusvaleo679alu.comportamiento1.condiciones;

import core.game.StateObservation;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Condicion;

public class SinPeligro extends Condicion {

	@Override
	public boolean seCumple(StateObservation so) {
		PosiblePeligro p = new PosiblePeligro();
		return !(p.seCumple(so));
	}

}
