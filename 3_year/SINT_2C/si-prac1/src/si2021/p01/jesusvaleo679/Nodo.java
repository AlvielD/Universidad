package si2021.p01.jesusvaleo679;

public class Nodo {
	
	// Posición (x,y) del nodo
	private double posX, posY;
	// Valores de g, h y f (siendo f = g + h)
	// Estos son necesarios para el pathfinding.
	private double g, h, f;
	// Establece si el nodo es atravesable por el
	// jugador o no.
	private boolean sePuedeCruzar;
	// Nodo desde el cual se llega este nodo.
	private Nodo padre;

	/**
	 * Crea un nuevo nodo. Inicializa los valores por
	 * defecto y establece su posición con los parámetros
	 * dados.
	 * @param posX Posición en el eje X del nodo.
	 * @param posY Posición en el eje Y del nodo.
	 */
	public Nodo(final double posX, final double posY) {
		super();
		this.posX = posX;
		this.posY = posY;
		this.sePuedeCruzar = true;
		this.padre = null;
	}
	
	/**
	 * Calcula el valor heurístico del nodo. Para ello
	 * calcula la distancia euclídea desde el nodo de origen
	 * hasta el de destino.
	 * @param nodoDestino Nodo de destino.
	 */
	public void calcularHeuristica(Nodo nodoDestino) {
		// Distancia Euclídea para la heurística
		this.h = Math.sqrt(Math.pow((nodoDestino.posX - this.posX), 2)
				+ Math.pow((nodoDestino.posY - this.posY), 2));
		
		// Distancia en valor absoluto
		/*this.h = Math.abs(nodoDestino.getPosX() - posX)
				+ Math.abs(nodoDestino.getPosY() - getPosY());*/
	}
	
	/**
	 * Actualiza la información del nodo con su nodo padre y coste.
	 * @param nodoActual Nodo desde el que se llega (nodo padre).
	 * @param coste Nuevo coste del nodo.
	 */
	public void setInformacion(Nodo nodoActual, double coste) {
		g = nodoActual.g + coste;
		padre = nodoActual;
		calcularCosteF();
	}
	
	/**
	 * Comprueba si existe una ruta alternativa desde el origen hasta el nodo
	 * que se está comprobando actualmente que tenga menor coste que el que
	 * se tiene en este momento.
	 * @param nodoActual Nodo que se está comprobando.
	 * @param coste Coste actual del camino hacia el nodo.
	 * @return true si existe un camino alternativo.
	 */
	public boolean comprobarCaminoAlternativo(Nodo nodoActual, double coste) {
		double costeG = nodoActual.g + coste;
		if (costeG < g) {
			setInformacion(nodoActual, coste);
			return true;
		}
		return false;
	}
	
	// Para comparar nodos
	@Override
	public boolean equals(Object o) {
		Nodo nodo = (Nodo) o;
		return this.posX == nodo.posX && this.posY == nodo.posY;
	}
	
	// Para sacar un nodo por consola
	@Override
	public String toString() {
		return "Nodo [X=" + posX + ", Y=" + posY + "]\n";
	}
	
	private void calcularCosteF() {
		f = g + h;
	}

	// Getters y setters
	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

	public double getG() {
		return g;
	}

	public void setG(double g) {
		this.g = g;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double getF() {
		return f;
	}

	public void setF(double f) {
		this.f = f;
	}

	public boolean getSePuedeCruzar() {
		return sePuedeCruzar;
	}

	public void setSePuedeCruzar(boolean sePuedeCruzar) {
		this.sePuedeCruzar = sePuedeCruzar;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}
}
