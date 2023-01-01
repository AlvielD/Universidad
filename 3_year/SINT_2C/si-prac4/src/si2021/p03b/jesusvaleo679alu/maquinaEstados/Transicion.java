package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;

import core.game.StateObservation;

public class Transicion {
	
	private int nivel;
	private EstadoBase estadoDestino;
	private ArrayList<Condicion> condiciones;
	private Accion accion;
	
	public Transicion(final int nivel, final EstadoBase estadoDestino,
			final ArrayList<Condicion> condiciones, final Accion accion) {
		this.nivel = nivel;
		this.estadoDestino = estadoDestino;
		this.condiciones = condiciones;
		this.accion = accion;
	}

	public boolean estaActiva(StateObservation so) {
		for(Condicion c : condiciones)
			if (!c.seCumple(so)) return false;
		return true;
	}
	
	public EstadoBase getEstadoDestino() {
		return estadoDestino;
	}
	
	public Accion getAccion() {
		return accion;
	}
	
	public int getNivel() {
		return nivel;
	}
	
}
