package si2021.p03b.jesusvaleo679alu;

import java.util.ArrayList;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import si2021.p03b.jesusvaleo679alu.comportamiento1.Comportamiento1;
import si2021.p03b.jesusvaleo679alu.maquinaEstados.Accion;
import tools.ElapsedCpuTimer;

public class AgenteHSM extends AbstractPlayer {
	
	private int contador, ultimoNumAcciones;
	private Comportamiento1 comportamiento;
	private ArrayList<Accion> acciones;
	
	public AgenteHSM(StateObservation so, ElapsedCpuTimer et) {
		// NACE
		contador = ultimoNumAcciones = 0;
		
		comportamiento = new Comportamiento1();
		acciones = new ArrayList<>();
	}

	@Override
	public ACTIONS act(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		// PENSAR
		ACTIONS accion = ACTIONS.ACTION_NIL;
		
		ArrayList<Accion> nuevasAcciones = comportamiento.actualizar(so).getAcciones();
		acciones.addAll(nuevasAcciones);
		if (nuevasAcciones.size() > 1) contador += ultimoNumAcciones;
		ultimoNumAcciones = nuevasAcciones.size();
		
		if (acciones.size() > 0) {
			if (contador == acciones.size()) contador = acciones.size() - 1;
			accion = acciones.get(contador++).getAccion(so);
		}
		
		return accion; // ACTUAR
	}

}
