package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;

import core.game.StateObservation;

public class SSM extends HSM implements EstadoBase {

	protected ArrayList<Transicion> transiciones;
	protected Accion accionInicial, accionFinal;
	protected String nombre;
	
	public SSM(String nombre) {
		super();
		this.nombre = nombre;
		transiciones = new ArrayList<>();
		accionInicial = accionFinal = null;
	}
	
	@Override
	public String getNombre() {
		return this.nombre;
	}
	
	@Override
	public ResultadoActualizacion actualizar(StateObservation so) {
		return super.actualizar(so);
	}
	
	@Override
	public ArrayList<EstadoBase> getEstados() {
		ArrayList<EstadoBase> e = new ArrayList<>();
		e.add(this);
		if (estadoActual != null)
			e.addAll(((HSMBase) estadoActual).getEstados());
		return e;
	}
	
	@Override
	public Accion getAccion() {
		if (estadoActual != null && estadoActual.getAccion() != null)
			return estadoActual.getAccion();
		else return super.getAccion();
	}

	@Override
	public Accion getAccionInicial() {
		return accionInicial;
	}

	@Override
	public Accion getAccionFinal() {
		return accionFinal;
	}

	@Override
	public ArrayList<Transicion> getTransiciones() {
		return transiciones;
	}

	public void setAccionInicial(Accion accionInicial) {
		this.accionInicial = accionInicial;
	}

	public void setAccionFinal(Accion accionFinal) {
		this.accionFinal = accionFinal;
	}
	
	public void agregarTransicion(Transicion transicion) {
		this.transiciones.add(transicion);
	}
	
}
