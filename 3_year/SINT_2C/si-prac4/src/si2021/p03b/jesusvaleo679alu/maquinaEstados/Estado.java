package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;

public class Estado extends HSMBase implements EstadoBase {

	protected Accion accion, accionInicial, accionFinal;
	protected ArrayList<Transicion> transiciones;
	protected String nombre;
	
	public Estado(String nombre, Accion accion,
			Accion accionInicial, Accion accionFinal) {
		super();
		this.nombre = nombre;
		this.accion = accion;
		this.accionInicial = accionInicial;
		this.accionFinal = accionFinal;
		this.transiciones = new ArrayList<>();
	}
	
	@Override
	public String getNombre() {
		return this.nombre;
	}

	@Override
	public Accion getAccion() {
		return this.accion;
	}
	
	@Override
	public Accion getAccionInicial() {
		return this.accionInicial;
	}
	
	@Override
	public Accion getAccionFinal() {
		return this.accionFinal;
	}
	
	public void agregarTransicion(final Transicion transicion) {
		this.transiciones.add(transicion);
	}
	
	public ArrayList<Transicion> getTransiciones() {
		return this.transiciones;
	}
	
	@Override
	public ArrayList<EstadoBase> getEstados() {
		ArrayList<EstadoBase> estados = new ArrayList<>();
		estados.add(this);
		return estados;
	}
	
}
