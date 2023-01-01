package bnf2cnf.ast.Symbols;

public class NTSymbol implements Symbol {
	
	private String name;
	
	public NTSymbol(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public boolean equals(Symbol simbolo) {
		return ( this.getName().equals(simbolo.getName()) );
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public Symbol clone() {
		NTSymbol copia = new NTSymbol(this.toString());
		
		return copia;
	}

}
