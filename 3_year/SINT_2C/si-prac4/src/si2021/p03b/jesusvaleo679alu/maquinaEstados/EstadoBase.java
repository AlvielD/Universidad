package si2021.p03b.jesusvaleo679alu.maquinaEstados;

import java.util.ArrayList;

public interface EstadoBase {

	public Accion getAccion();
	public Accion getAccionInicial();
	public Accion getAccionFinal();
	
	public ArrayList<Transicion> getTransiciones();
	
	public String getNombre();
	
}
