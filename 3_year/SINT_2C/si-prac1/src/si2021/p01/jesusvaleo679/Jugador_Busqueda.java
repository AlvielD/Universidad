package si2021.p01.jesusvaleo679;

import java.util.List;

import core.game.StateObservation;
import core.player.AbstractPlayer;
import ontology.Types.ACTIONS;
import tools.ElapsedCpuTimer;
import tools.Vector2d;

public class Jugador_Busqueda extends AbstractPlayer {
	
	private List<Nodo> camino;
	private Vector2d posActual;
	private int contador;
	
	public Jugador_Busqueda(StateObservation so, ElapsedCpuTimer et) {
		// NACE
		contador = 1;
		
		Vector2d meta = so.getPortalsPositions()[0].get(0).position;
		Vector2d posJugador = so.getAvatarPosition();
		
		AEstrella busqueda = new AEstrella(so, posJugador, meta);
		camino = busqueda.encontrarCamino();
		
		/*
		 * El jugador siempre empieza con el máximo de vida pero en el primer
		 * tick pierde 1. A partir de ahí, cada 15 ticks pierde 1 de vida.
		 * Para ajustar cuando tiene que ir a por vida antes que a la meta,
		 * se divide la distancia del camino entre 5. Si ese resultado es mayor
		 * a 12 (los 60 ticks que aguanta con la vida inicial), se debe ir a por
		 * vida antes de ir a por la meta.
		 */
		if (camino.size() / 5 > 12) {
			Vector2d vida = so.getResourcesPositions(posJugador)[0].get(1).position;
			AEstrella b1 = new AEstrella(so, posJugador, vida);
			camino = b1.encontrarCamino();
			AEstrella b2 = new AEstrella(so, new Vector2d(camino.get(camino.size()-1).getPosX(),
					camino.get(camino.size()-1).getPosY()), meta);
			List<Nodo> camino2 = b2.encontrarCamino();
			camino2.remove(0);
			camino.addAll(camino2);
		}
		
		posActual = posJugador;
	}

	@Override
	public ACTIONS act(StateObservation so, ElapsedCpuTimer elapsedTimer) {
		// PENSAR
		System.out.println(so.getBlockSize());
		if (contador >= camino.size())
			return ACTIONS.ACTION_NIL;

		ACTIONS accion;
		if (posActual.x - camino.get(contador).getPosX() > 0)
			accion = ACTIONS.ACTION_LEFT;
		else if (posActual.x - camino.get(contador).getPosX() < 0)
			accion = ACTIONS.ACTION_RIGHT;
		else if (posActual.y - camino.get(contador).getPosY() > 0)
			accion = ACTIONS.ACTION_UP;
		else accion = ACTIONS.ACTION_DOWN;
		
		posActual = new Vector2d(camino.get(contador).getPosX(),
				camino.get(contador).getPosY());
		
		contador++;
		return accion; // ACTUAR
	}

}
