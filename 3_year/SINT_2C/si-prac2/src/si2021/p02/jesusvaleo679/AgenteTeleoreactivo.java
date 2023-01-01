package si2021.p02.jesusvaleo679;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import si2021.p02.jesusvaleo679.comportamiento1.Comportamiento1;
import si2021.p02.jesusvaleo679.motor.Motor;
import tools.ElapsedCpuTimer;

public class AgenteTeleoreactivo extends AbstractPlayer {
	
	private Motor comp;
	
	public AgenteTeleoreactivo(StateObservation percepcion, ElapsedCpuTimer et) {
		// NACE
		
		comp = new Comportamiento1();
	}

	@Override
	public ACTIONS act(StateObservation percepcion, ElapsedCpuTimer elapsedTimer) {
		// PENSAR
		
		//System.out.println("Posicion Jugador: " + percepcion.getAvatarPosition());
		ACTIONS accion = comp.decidir(percepcion);
		
		return accion; // ACTUAR
	}

}
