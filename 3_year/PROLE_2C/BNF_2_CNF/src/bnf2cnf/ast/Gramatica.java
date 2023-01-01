package bnf2cnf.ast;

import java.util.ArrayList;
import java.util.Iterator;

import bnf2cnf.ast.Symbols.Symbol;
import bnf2cnf.ast.Symbols.TSymbol;

public class Gramatica {
	
	private ArrayList<Definicion> definiciones;

	public Gramatica(ArrayList<Definicion> Definiciones) {
		this.definiciones = Definiciones;
	}
	
	public ArrayList<Definicion> getDefiniciones() {
		return definiciones;
	}
	
	/**
	 * Comprueba si existe una definición que derive de forma aislada en el símbolo terminal pasado por parámetro
	 * @param sDerivado símbolo terminal del que comprobaremos si existe una definición que derive de forma aislada
	 * @return la definción que deriva de forma aislada en el símbolo terminal pasado por parámetro o null en caso de no existir.
	 */
	public Definicion existeDerivacionAislada(TSymbol sDerivado) {
		
		int i=0;			 					// Indice del bucle
		boolean existeDerivacion = false;		// Flag --> ¿Existe ya una definición que derive en el símbolo sustituido de forma aislada?
		
		Definicion defAislada = null;			// Variable que guardará la definición aislada en caso de existir o null en caso contrario
		
		// Buscamos si existe alguna definición que derive ya en sDerivado
		while (i<definiciones.size() && existeDerivacion == false) {
			
			ArrayList<Regla> reglasActual = definiciones.get(i).getListaReglas();	// Extraemos las reglas de la definición actual
			
			int j = 0;	// Índice del bucle
			
			while (j<reglasActual.size() && existeDerivacion == false) {	// Para cada regla...
				
				Regla reglaActual = reglasActual.get(j);						// Extraemos la regla actual
				int nTerminales = reglaActual.getNumTerminales();				// Obtenemos el número de símbolos terminales de la regla
				
				// No tenemos que recorrer el array de símbolos porque es una regla aislada, solo deriva en un símbolo
				if (nTerminales == 1 && reglaActual.getSimbolos().size() == 1) {	// Entonces la regla deriva de forma aislada en un símbolo terminal
					// Comprobamos que el símbolo en el que se deriva es el que nos han pasado por parámetro
					if (reglaActual.getSimbolos().get(0).equals(sDerivado)) { 
						existeDerivacion = true;				// Se ha encontrado derivacion existente
						defAislada = definiciones.get(i);		// Obtenemos el NT de la derivacion
					}
				}
				j++;
			}
			i++;
		}
		
		return defAislada;
	}
	
	/**
	 * Deriva una regla hasta obtener todas sus posibles derivaciones
	 * @param simboloDerivacion símbolo a ser derivado
	 * @return la lista de reglas derivadas del símbolo pasado por parámetro
	 */
	public ArrayList<Regla> derivaRegla(Symbol simboloDerivacion) {
		
		ArrayList<Regla> reglasDerivadas = null;

		boolean derivacionEncontrada = false;			// Flag --> ¿Hemos encontrado derivación?
		boolean reglaActualizada = true;				// Flag --> ¿Hemos actualizado la regla en la última vuelta?
		
		while (!derivacionEncontrada && reglaActualizada) {
		
			reglaActualizada = false;				// Marcamos la regla como no actualizada
			int i = 0;								// Índice del bucle
			
			// Buscamos en la gramática una definición cuyo NT sea el símbolo que nos han pasado por parámetro reglaDerivacion.getSimbolos.get(0)
			while (i<definiciones.size() && !derivacionEncontrada) {		// Para cada definición de la gramática
				
				Definicion defActual = definiciones.get(i);		// Extraemos la definición actual
				
				if(defActual.getSimbolo().equals(simboloDerivacion)) {	// Estamos ante la definición que contiene la reglaDerivada
					
					reglasDerivadas = defActual.getListaReglas();		// Devolveremos las reglas de la definición actual
				}
				i++;
			}
		}
		
		return reglasDerivadas;
	}
	
	@Override
	public Gramatica clone() {
		
		// Creamos e inicializamos el array de definiciones copia
		ArrayList<Definicion> DefsCopia = new ArrayList<Definicion>();
		for (Definicion definicion : definiciones) DefsCopia.add(definicion.clone());
		
		// Creamos la gramática copiada
		Gramatica copia = new Gramatica(DefsCopia);
		
		return copia;
	}
	
	@Override
	public String toString() {
		
		String G;
		
		G = "Gramática: \n\n ";
		
		for (Definicion definicion : definiciones) {
			G = G + definicion.toString();
		}
		
		return G;
	}

}
