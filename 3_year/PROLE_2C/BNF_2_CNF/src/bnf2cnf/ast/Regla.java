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
	 * Cuenta el n�mero de s�mbolos terminales que hay en una regla
	 * @return el n�mero de s�mbolos terminales que tiene la regla
	 */
	public int getNumTerminales() {
		int nTerminales = 0;
		
		// Recorremos la lista de s�mbolos, contando los que sean terminales
		for (Symbol sActual : simbolos)
			if (sActual instanceof TSymbol) nTerminales++;		// Si el s�mbolo es terminal, lo contamos
		
		return nTerminales;
	}
	
	/**
	 * Borra de la lista de s�mbolos que componen la regla el s�mbolo pasado por par�metro
	 * @param sActual S�mbolo a ser eliminado de la lista de s�mbolos de la regla
	 */
	public void borraSimb(Symbol sActual) {
		for (int i=0; i<simbolos.size(); i++) {
			if (simbolos.get(i).equals(sActual)) simbolos.remove(i);	
		}
	}
	
	/**
	 * Sustituye el s�mbolo terminal suministrado por par�metro por un s�mbolo no terminal
	 * @param G
	 * @param sustituido s�mbolo terminal que ser� sustituido en la regla
	 * @return el s�mbolo no terminal que sustituye al suministrado por par�metro
	 */
	public void sustituyeSimb(Gramatica G, TSymbol sustituido) {
		
		NTSymbol sustituto = null;				// S�mbolo no terminal que ser� encargado de sustituir al s�mbolo terminal suministrado por par�metro
		boolean creadoNuevo = false;			// Flag --> �Se ha creado una nueva definici�n?
		
		ArrayList<Definicion> defs = G.getDefiniciones();
		
		// Comprobamos que no exista ya una definici�n que derive de forma aislada en sustituido
		Definicion derivacion = G.existeDerivacionAislada(sustituido);
		if (derivacion != null) {
			// Existe una defici�n que deriva en sustituido de forma aislada, usaremos su NT como sustituto
			sustituto = derivacion.getSimbolo();
		} else {
			// No existe una definici�n que deriva en sustituido de forma asilada, creamos un s�mbolo no terminal nuevo
			sustituto = Regla.creaSimbolo();
			
			// Creamos la definici�n aislada nueva
			ArrayList<Symbol> simbolosDerivacion = new ArrayList<Symbol>();	// S�mbolos de la derivaci�n aislada
			simbolosDerivacion.add(sustituido);								// A�adimos el sustituido
			
			Regla reglaDerivacion = new Regla(simbolosDerivacion);			// Creamos la regla a partir del array de s�mbolos
			ArrayList<Regla> reglasDerivacion = new ArrayList<Regla>();		// Creamos el array de reglas
			reglasDerivacion.add(reglaDerivacion);							// A�adimos la regla al array
			
			derivacion = new Definicion(sustituto, reglasDerivacion);		// Creamos la nuev definici�n
			
			creadoNuevo = true;
		}
		
		// Buscamos al sustituido en la lista de s�mbolos de la regla y lo cambiamos por sustituto
		for (int i = 0; i<simbolos.size(); i++) {
			if (simbolos.get(i).equals(sustituido)) {
				simbolos.remove(i);
				simbolos.add(i, sustituto);
			}
		}
		
		if (creadoNuevo == true) {
			// Si se ha creado una nueva definici�n, se a�ade a la gram�tica
			defs.add(derivacion);
		}
	}
	
	/**
	 * Crea un s�mbolo no terminal auxiliar nuevo con el nombre "Aux" + contador
	 * @return El s�mbolo no terminal nuevo creado
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
		// Array de s�mbolos copia
		ArrayList<Symbol> copiaSimbolos = new ArrayList<Symbol>();
		
		// Agregamos a nuestro array de copia, una copia de cada s�mbolos
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
