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
	 * Comprueba si existe una definici�n que derive de forma aislada en el s�mbolo terminal pasado por par�metro
	 * @param sDerivado s�mbolo terminal del que comprobaremos si existe una definici�n que derive de forma aislada
	 * @return la definci�n que deriva de forma aislada en el s�mbolo terminal pasado por par�metro o null en caso de no existir.
	 */
	public Definicion existeDerivacionAislada(TSymbol sDerivado) {
		
		int i=0;			 					// Indice del bucle
		boolean existeDerivacion = false;		// Flag --> �Existe ya una definici�n que derive en el s�mbolo sustituido de forma aislada?
		
		Definicion defAislada = null;			// Variable que guardar� la definici�n aislada en caso de existir o null en caso contrario
		
		// Buscamos si existe alguna definici�n que derive ya en sDerivado
		while (i<definiciones.size() && existeDerivacion == false) {
			
			ArrayList<Regla> reglasActual = definiciones.get(i).getListaReglas();	// Extraemos las reglas de la definici�n actual
			
			int j = 0;	// �ndice del bucle
			
			while (j<reglasActual.size() && existeDerivacion == false) {	// Para cada regla...
				
				Regla reglaActual = reglasActual.get(j);						// Extraemos la regla actual
				int nTerminales = reglaActual.getNumTerminales();				// Obtenemos el n�mero de s�mbolos terminales de la regla
				
				// No tenemos que recorrer el array de s�mbolos porque es una regla aislada, solo deriva en un s�mbolo
				if (nTerminales == 1 && reglaActual.getSimbolos().size() == 1) {	// Entonces la regla deriva de forma aislada en un s�mbolo terminal
					// Comprobamos que el s�mbolo en el que se deriva es el que nos han pasado por par�metro
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
	 * @param simboloDerivacion s�mbolo a ser derivado
	 * @return la lista de reglas derivadas del s�mbolo pasado por par�metro
	 */
	public ArrayList<Regla> derivaRegla(Symbol simboloDerivacion) {
		
		ArrayList<Regla> reglasDerivadas = null;

		boolean derivacionEncontrada = false;			// Flag --> �Hemos encontrado derivaci�n?
		boolean reglaActualizada = true;				// Flag --> �Hemos actualizado la regla en la �ltima vuelta?
		
		while (!derivacionEncontrada && reglaActualizada) {
		
			reglaActualizada = false;				// Marcamos la regla como no actualizada
			int i = 0;								// �ndice del bucle
			
			// Buscamos en la gram�tica una definici�n cuyo NT sea el s�mbolo que nos han pasado por par�metro reglaDerivacion.getSimbolos.get(0)
			while (i<definiciones.size() && !derivacionEncontrada) {		// Para cada definici�n de la gram�tica
				
				Definicion defActual = definiciones.get(i);		// Extraemos la definici�n actual
				
				if(defActual.getSimbolo().equals(simboloDerivacion)) {	// Estamos ante la definici�n que contiene la reglaDerivada
					
					reglasDerivadas = defActual.getListaReglas();		// Devolveremos las reglas de la definici�n actual
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
		
		// Creamos la gram�tica copiada
		Gramatica copia = new Gramatica(DefsCopia);
		
		return copia;
	}
	
	@Override
	public String toString() {
		
		String G;
		
		G = "Gram�tica: \n\n ";
		
		for (Definicion definicion : definiciones) {
			G = G + definicion.toString();
		}
		
		return G;
	}

}
