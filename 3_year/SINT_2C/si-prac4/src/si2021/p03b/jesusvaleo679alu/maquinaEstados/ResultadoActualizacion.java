package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;

public class ResultadoActualizacion {
	
	public ResultadoActualizacion() {
		resetear();
	}
	
	public void resetear() {
		acciones = new ArrayList<>();
		nivel = 0;
		transicion = null;
	}
	
	private ArrayList<Accion> acciones;
	private Transicion transicion;
	
	// Nivel = 0 --> HSM
	// Nivel = 1 --> SSM
	private int nivel;

	public ArrayList<Accion> getAcciones() {
		return acciones;
	}

	public void setAcciones(ArrayList<Accion> acciones) {
		this.acciones = acciones;
	}
	
	public void agregarAccion(Accion accion) {
		this.acciones.add(accion);
	}
	
	public void agregarAcciones(ArrayList<Accion> acciones) {
		this.acciones.addAll(acciones);
	}

	public Transicion getTransicion() {
		return transicion;
	}

	public void setTransicion(Transicion transicion) {
		this.transicion = transicion;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}
	
}
