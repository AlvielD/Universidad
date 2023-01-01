package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;

import core.game.StateObservation;

public abstract class HSMBase {

	protected ResultadoActualizacion resultado;
	protected HSMBase padre;
	
	public HSMBase() {
		resultado = new ResultadoActualizacion();
		padre = null;
	}
	
	public ResultadoActualizacion actualizar(StateObservation so) {
		resultado.setAcciones(new ArrayList<>());
		resultado.setTransicion(null);
		resultado.setNivel(0);
		return resultado;
	}
	
	public void setPadre(HSMBase padre) {
		this.padre = padre;
	}
	
	public HSMBase getPadre() {
		return this.padre;
	}
	
	public Accion getAccion() {
		return null;
	}
	
	public abstract ArrayList<EstadoBase> getEstados();
	
	public ArrayList<Accion> getAcciones() {
		return resultado.getAcciones();
	}
	
}
