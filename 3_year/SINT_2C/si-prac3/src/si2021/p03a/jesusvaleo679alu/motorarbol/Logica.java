package si2021.p03a.jesusvaleo679alu.motorarbol;

public abstract class Logica extends Condicion {

	protected Nodo nodoSI, nodoNO;
	
	public Logica(final Nodo nodoSI, final Nodo nodoNO) {
		this.nodoSI = nodoSI;
		this.nodoNO = nodoNO;
	}
	
}
