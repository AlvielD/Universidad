package si2021.p01.jesusvaleo679;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import core.game.Observation;
import core.game.StateObservation;
import tools.Vector2d;

public class AEstrella {

	private static int COSTE_BASICO = 10;
	
	private int blockSize;
	
	private Nodo[][] nodos;
	private PriorityQueue<Nodo> abiertos;
	private Set<Nodo> cerrados;
	private Nodo nodoOrigen;
	private Nodo nodoDestino;
	
	public AEstrella(StateObservation percepcion, Vector2d origen, Vector2d destino) {
                
        // Array que contiene las posiciones intransitables
		ArrayList<Observation> intransitables[] = percepcion.getImmovablePositions();
		
        // Obtiene el tamaño de un bloque
		this.blockSize = percepcion.getBlockSize(); // blockSize = tamaño de una casilla/bloque transitable
		
        // Obtiene el tamaño del mapa en casillas/bloques (Dividimos el tamaño entre el tamaño del bloque)
		int numX = percepcion.getWorldDimension().width / percepcion.getBlockSize();
		int numY = percepcion.getWorldDimension().height / percepcion.getBlockSize();
		
        // Creamos un array bidimensional con tantos nodos como casillas/bloques tenga el mapa
		nodos = new Nodo[numX][numY];
		
        // Recorre el array bidimensional inicializando cada nodo con su posición en el mapa
		for (int i = 0; i < numX; i++)
			for (int j = 0; j < numY; j++)
				nodos[i][j] = new Nodo(i*percepcion.getBlockSize(), j*percepcion.getBlockSize());
				
		
        // Si hay bloques que no se pueden atravesar, se cambia el parámetro del nodo correspondiente
		// para indicar que el jugador no puede pasar por ellos.
		//
		// * En el juego 50, los bloques (árboles) no atravesables tienen category = 4, itype = 0
		if (intransitables != null) {
			for (int i = 0; i < intransitables.length; i++) {
				for (int j = 0; j < intransitables[i].size(); j++) {
					// Posición (x,y) del nodo correspondiente al bloque no atravesable
					int x = (int) (intransitables[i].get(j).position.x / percepcion.getBlockSize());
					int y = (int) (intransitables[i].get(j).position.y / percepcion.getBlockSize());
					// Se comprueba que sea del tipo de bloque no atravesable antes de marcarlo como tal
					if (intransitables[i].get(j).category == 4 && intransitables[i].get(j).itype == 0)
						nodos[x][y].setSePuedeCruzar(false);
 				}
			}
		}
		
        // Obtiene a partir de la posición de inicio el nodo de inicio
		int origenX = (int) (origen.x / percepcion.getBlockSize());
		int origenY = (int) (origen.y / percepcion.getBlockSize());
		nodoOrigen = nodos[origenX][origenY];
		
		// Obtiene a partir de la posición de destino el nodo final
		int destX = (int) (destino.x / percepcion.getBlockSize());
		int destY = (int) (destino.y / percepcion.getBlockSize());
		nodoDestino = nodos[destX][destY];
		
		// Se calcula la heurística de los nodos con el nodo de destino
		for (int i = 0; i < numX; i++)
			for (int j = 0; j < numY; j++)
				nodos[i][j].calcularHeuristica(nodoDestino);
		
        // Creamos el conjunto de nodos abiertos
        // Se sobrecarga el método compare de la PriorityQueue para que
		// el orden de salida de los nodos que se obtengan sea en base
		// a el valor F. Saldré siempre primero aquel nodo con menor F.
		abiertos = new PriorityQueue<Nodo>(new Comparator<Nodo>() {
			@Override
			public int compare(Nodo nodo0, Nodo nodo1) {
				return Double.compare(nodo0.getF(), nodo1.getF());
			}
		});
		
        // Lista de nodos recorridos
		cerrados = new HashSet<>();
	}
	
	/**
	 * Encuentra el camino (si lo hay) desde el nodo inicial, que será
	 * el del personaje, hasta el destino, que es la meta.
	 * @return Un ArrayList con los nodos a recorrer ordenados en la secuencia hasta la meta.
	 */
	public List<Nodo> encontrarCamino() {
		abiertos.add(nodoOrigen);
		while (!abiertos.isEmpty()) {
			Nodo actual = abiertos.poll();
			cerrados.add(actual);
			if (nodoDestino.equals(actual))
				return obtenerCamino(actual);
			else
				agregarAdyacentes(actual);
		}
		
		return new ArrayList<Nodo>();
	}
	
	/**
	 * Obtiene una lista con los nodos que van desde el nodo inicial
	 * hasta el nodo actual siguiendo el camino mínimo para ello.
	 * @param actual El nodo desde el que se quiere obtener el camino.
	 * @return Un ArrayList de nodos, el camino hasta el nodo actual desde el origen.
	 */
	private List<Nodo> obtenerCamino(Nodo actual) {
		List<Nodo> camino = new ArrayList<Nodo>();
		camino.add(actual);
		Nodo padre;
		while ((padre = actual.getPadre()) != null) {
			camino.add(0, padre);
			actual = padre;
		}
		return camino;
	}
	
	/**
	 * Obtiene los nodos adyacentes a un nodo actual.
	 * @param actual Nodo actual.
	 */
	private void agregarAdyacentes(Nodo actual) {
		agregarAdyacentesArriba(actual);
		agregarAdyacentesMedio(actual);
		agregarAdyacentesAbajo(actual);
	}

	/**
	 * Obtiene los nodos adyacentes superiores a un nodo actual,
	 * es decir, aquellos que están arriba del mismo (misma columna).
	 * @param actual Nodo actual.
	 */
	private void agregarAdyacentesArriba(Nodo actual) {
		int fila = (int) (actual.getPosX() / blockSize);
		int col = (int) (actual.getPosY() / blockSize);
		int filaArriba = fila - 1;
		
		if (filaArriba >= 0) {
			comprobarNodo(actual, col, filaArriba, COSTE_BASICO);
		}
	}
	
	/**
	 * Obtiene los nodos adyacentes de la misma fila a un nodo actual,
	 * es decir, aquellos que están a sus lados.
	 * @param actual Nodo actual.
	 */
	private void agregarAdyacentesMedio(Nodo actual) {
		int fila = (int) (actual.getPosX() / blockSize);
		int col = (int) (actual.getPosY() / blockSize);
		int filaMedio = fila;
		
		if (col - 1 >= 0)
			comprobarNodo(actual, col - 1, filaMedio, COSTE_BASICO);
		
		if (col + 1 < nodos[0].length)
			comprobarNodo(actual, col + 1, filaMedio, COSTE_BASICO);
	}
	
	/**
	 * Obtiene los nodos adyacentes inferiores a un nodo actual,
	 * es decir, aquellos que están debajo del mismo (misma columna).
	 * @param actual Nodo actual.
	 */
	private void agregarAdyacentesAbajo(Nodo actual) {
		int fila = (int) (actual.getPosX() / blockSize);
		int col = (int) (actual.getPosY() / blockSize);
		int filaAbajo = fila + 1;
		
		if (filaAbajo < nodos.length) {
			comprobarNodo(actual, col, filaAbajo, COSTE_BASICO);
		}
	}
	
	/**
	 * Comprueba un nodo. Si es transitable y no se ha visitado previamente,
	 * se comprueban varias cosas. Si no estaba en la lista de abiertos, se
	 * actualiza sus datos y se añade. Si ya estaba, se comprueba si existe
	 * un camino alternativo para llegar a él de menor coste, y, en caso
	 * afirmativo, se actualizan los datos con este nuevo coste.
	 * @param actual
	 * @param col
	 * @param fila
	 * @param coste
	 */
	private void comprobarNodo(Nodo actual, int col, int fila, double coste) {
		Nodo nodoAdyacente = nodos[fila][col];
		if (nodoAdyacente.getSePuedeCruzar() && !cerrados.contains(nodoAdyacente)) {
			if (!abiertos.contains(nodoAdyacente)) {
				nodoAdyacente.setInformacion(actual, coste);
				abiertos.add(nodoAdyacente);
			} else {
				boolean actualizado = nodoAdyacente.comprobarCaminoAlternativo(actual, coste);
				if (actualizado) {
					// Actualizar posicion en la lista de abiertos
					// que se ordena por el coste F
					abiertos.remove(nodoAdyacente);
					abiertos.add(nodoAdyacente);
				}
			}
		}
	}
	
}
