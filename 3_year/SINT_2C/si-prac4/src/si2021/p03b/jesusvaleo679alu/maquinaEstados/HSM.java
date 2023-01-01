package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;
import java.util.Iterator;

import core.game.StateObservation;

public class HSM extends HSMBase {

	protected ArrayList<EstadoBase> estados;
	protected EstadoBase estadoInicial;
	protected EstadoBase estadoActual;
	
	public HSM() {
		super();
		estados = new ArrayList<>();
		this.estadoInicial = null;
		this.estadoActual = null;
	}
	
	public void agregarEstado(final EstadoBase estado) {
		this.estados.add(estado);
	}
	
	public void setEstadoInicial(final EstadoBase estado) {
		this.estadoInicial = estado;
	}
	
	public void setEstadoActual(final EstadoBase estado) {
		this.estadoActual = estado;
	}
	
	public String getEstadoActual() {
		if (estadoActual != null)
			return estadoActual.getNombre();
		else return "";
	}
	
	@Override
	public ArrayList<EstadoBase> getEstados() {
		if (estadoActual != null) {
			if (estadoActual instanceof Estado)
				return ((Estado)estadoActual).getEstados();
			else return ((SSM) estadoActual).getEstados();
		}
		else return new ArrayList<>();
	}
	
	@Override
	public ResultadoActualizacion actualizar(StateObservation so) {
		if (estadoActual == null) {
			estadoActual = estadoInicial;
			resultado.setAcciones(new ArrayList<>());
			if (estadoActual.getAccionInicial() != null)
				resultado.agregarAccion(estadoActual.getAccionInicial());
		}
		
		Transicion transicionElegida = null;
		Iterator<Transicion> ti = estadoActual.getTransiciones().iterator();
		while (transicionElegida == null && ti.hasNext()) {
			Transicion t = ti.next();
			if (t.estaActiva(so))
				transicionElegida = t;
		}
		
		if (transicionElegida != null) {
			resultado.resetear();
			resultado.setAcciones(new ArrayList<>());
			resultado.setTransicion(transicionElegida);
			resultado.setNivel(transicionElegida.getNivel());
		} else {
			if (estadoActual instanceof Estado)
				resultado = ((Estado)estadoActual).actualizar(so);
			else resultado = ((SSM) estadoActual).actualizar(so);
		}
		
		if (resultado.getTransicion() != null) {
			if (resultado.getNivel() == 0) {
				EstadoBase estadoDestino = resultado.getTransicion().getEstadoDestino();
				if (estadoActual.getAccionFinal() != null)
					resultado.agregarAccion(estadoActual.getAccionFinal());
				if (resultado.getTransicion().getAccion() != null)
					resultado.agregarAccion(resultado.getTransicion().getAccion());
				if (estadoDestino.getAccionInicial() != null)
					resultado.agregarAccion(estadoDestino.getAccionInicial());
				
				estadoActual = estadoDestino;
				
				if (getAccion() != null) resultado.agregarAccion(getAccion());
				
				resultado.setTransicion(null);
			} else if (resultado.getNivel() > 0) {
				if (estadoActual.getAccionFinal() != null)
					resultado.agregarAccion(estadoActual.getAccionFinal());
				estadoActual = null;
				resultado.setNivel(resultado.getNivel()-1);
			} else {
				HSMBase estadoDestino = (HSMBase) resultado.getTransicion().getEstadoDestino();
				SSM maquinaDestino = (SSM) estadoDestino.getPadre();
				if (resultado.getTransicion().getAccion() != null)
					resultado.agregarAccion(resultado.getTransicion().getAccion());
				resultado.agregarAcciones(
						maquinaDestino.actualizarAbajo(estadoDestino, resultado.getNivel()-1));
				resultado.setTransicion(null);
			}
		} else {
			if (estadoActual.getAccion() != null)
				resultado.agregarAccion(estadoActual.getAccion());
		}
		
		return resultado;
	}
	
	protected ArrayList<Accion> actualizarAbajo(HSMBase estado, int nivel) {
		ArrayList<Accion> acciones = new ArrayList<>();
		
		if (nivel > 0) {
			HSM p = (HSM) padre;
			acciones.addAll(p.actualizarAbajo(this, nivel-1));
		}
		
		if (estadoActual != null && estadoActual.getAccionFinal() != null)
			acciones.add(estadoActual.getAccionFinal());
		
		estadoActual = (Estado) estado;
		if (estadoActual.getAccionInicial() != null)
			acciones.add(estadoActual.getAccionInicial());
		
		return acciones;
	}

}
