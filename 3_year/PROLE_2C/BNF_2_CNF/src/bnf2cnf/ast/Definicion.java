package bnf2cnf.ast;

import java.util.ArrayList;

import bnf2cnf.ast.Symbols.NTSymbol;

public class Definicion {
	
	private NTSymbol NT;
	private ArrayList<Regla> ListaReglas;
	
	public Definicion(NTSymbol NT, ArrayList<Regla> ListaReglas) {
		this.NT = NT;
		this.ListaReglas = ListaReglas;
	}
	
	public NTSymbol getSimbolo() {
		return NT;
	}
	
	public ArrayList<Regla> getListaReglas() {
		return ListaReglas;
	}
	
	@Override
	public Definicion clone() {
		// Copia de la parte izquierda de la definición
		NTSymbol NTCopia = (NTSymbol) this.getSimbolo().clone();
		
		// Copia de la parte derecha de la definición
		ArrayList<Regla> LRCopia = new ArrayList<Regla>();
		for (Regla regla : ListaReglas) LRCopia.add(regla.clone());
		
		// Creamos la copia de la definición con sus parametros una vez clonados
		Definicion copia = new Definicion(NTCopia, LRCopia);
		
		return copia;
	}
	
	@Override
	public String toString() {
		
		String D = NT.toString() + " ::= " + ListaReglas.get(0).toString();
		
		for (int i=1; i<ListaReglas.size(); i++) {
			D = D + "\n | " + ListaReglas.get(i).toString();
		}
		D = D + "\n ; \n\n";
		
		return D;
	}

}
