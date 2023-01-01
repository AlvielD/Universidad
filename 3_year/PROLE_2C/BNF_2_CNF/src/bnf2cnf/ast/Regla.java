package bnf2cnf.ast;

import java.util.ArrayList;

import bnf2cnf.ast.Symbols.NTSymbol;
import bnf2cnf.ast.Symbols.Symbol;
import bnf2cnf.ast.Symbols.TSymbol;

public class Regla implements Cloneable {
	
	private ArrayList<Symbol> simbolos;
	private static int contador = 1;
	
	public Regla(ArrayList<Symbol> Simbolos) {
		this.simbolos = Simbolos;
	}
	
	public ArrayList<Symbol> getSimbolos() {
		return simbolos;
	}
	
	/**
	 * Cuenta el número de símbolos terminales que hay en una regla
	 * @return el número de símbolos terminales que tiene la regla
	 */
	public int getNumTerminales() {
		int nTerminales = 0;
		
		// Recorremos la lista de símbolos, contando los que sean terminales
		for (Symbol sActual : simbolos)
			if (sActual instanceof TSymbol) nTerminales++;		// Si el símbolo es terminal, lo contamos
		
		return nTerminales;
	}
	
	/**
	 * Borra de la lista de símbolos que componen la regla el símbolo pasado por parámetro
	 * @param sActual Símbolo a ser eliminado de la lista de símbolos de la regla
	 */
	public void borraSimb(Symbol sActual) {
		for (int i=0; i<simbolos.size(); i++) {
			if (simbolos.get(i).equals(sActual)) simbolos.remove(i);	
		}
	}
	
	/**
	 * Sustituye el símbolo terminal suministrado por parámetro por un símbolo no terminal
	 * @param G
	 * @param sustituido símbolo terminal que será sustituido en la regla
	 * @return el símbolo no terminal que sustituye al suministrado por parámetro
	 */
	public void sustituyeSimb(Gramatica G, TSymbol sustituido) {
		
		NTSymbol sustituto = null;				// Símbolo no terminal que será encargado de sustituir al símbolo terminal suministrado por parámetro
		boolean creadoNuevo = false;			// Flag --> ¿Se ha creado una nueva definición?
		
		ArrayList<Definicion> defs = G.getDefiniciones();
		
		// Comprobamos que no exista ya una definición que derive de forma aislada en sustituido
		Definicion derivacion = G.existeDerivacionAislada(sustituido);
		if (derivacion != null) {
			// Existe una defición que deriva en sustituido de forma aislada, usaremos su NT como sustituto
			sustituto = derivacion.getSimbolo();
		} else {
			// No existe una definición que deriva en sustituido de forma asilada, creamos un símbolo no terminal nuevo
			sustituto = Regla.creaSimbolo();
			
			// Creamos la definición aislada nueva
			ArrayList<Symbol> simbolosDerivacion = new ArrayList<Symbol>();	// Símbolos de la derivación aislada
			simbolosDerivacion.add(sustituido);								// Añadimos el sustituido
			
			Regla reglaDerivacion = new Regla(simbolosDerivacion);			// Creamos la regla a partir del array de símbolos
			ArrayList<Regla> reglasDerivacion = new ArrayList<Regla>();		// Creamos el array de reglas
			reglasDerivacion.add(reglaDerivacion);							// Añadimos la regla al array
			
			derivacion = new Definicion(sustituto, reglasDerivacion);		// Creamos la nuev definición
			
			creadoNuevo = true;
		}
		
		// Buscamos al sustituido en la lista de símbolos de la regla y lo cambiamos por sustituto
		for (int i = 0; i<simbolos.size(); i++) {
			if (simbolos.get(i).equals(sustituido)) {
				simbolos.remove(i);
				simbolos.add(i, sustituto);
			}
		}
		
		if (creadoNuevo == true) {
			// Si se ha creado una nueva definición, se añade a la gramática
			defs.add(derivacion);
		}
	}
	
	/**
	 * Crea un símbolo no terminal auxiliar nuevo con el nombre "Aux" + contador
	 * @return El símbolo no terminal nuevo creado
	 */
	public static NTSymbol creaSimbolo() {
		
		String nombreSimb = "Aux" + Regla.getContador();
		Regla.setContador(Regla.getContador()+1);
		
		NTSymbol auxSymbol = new NTSymbol(nombreSimb);
		
		return auxSymbol;
	}
	
	public static int getContador() {
		return contador;
	}

	public static void setContador(int contador) {
		Regla.contador = contador;
	}
	
	@Override
	public Regla clone() {
		// Array de símbolos copia
		ArrayList<Symbol> copiaSimbolos = new ArrayList<Symbol>();
		
		// Agregamos a nuestro array de copia, una copia de cada símbolos
		for (int i=0; i<simbolos.size(); i++) {
			copiaSimbolos.add(simbolos.get(i).clone());
		}
		
		// Creamos una regla copia y la inicializamos con su lista de simbolos copia
		Regla copia = new Regla(copiaSimbolos);
		
		return copia;
	}
	
	@Override
	public String toString() {
		
		String R = "";
		
		for (Symbol symbol : simbolos) {
			R = R + symbol.toString() + " ";
		}
		
		return R;	
	}

}
