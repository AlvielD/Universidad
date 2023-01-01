package bnf2cnf.ast.Symbols;

public class TSymbol implements Symbol {
	
	private String name;
	
	public TSymbol(String name) {
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
		TSymbol copia = new TSymbol(this.toString());
		
		return copia;
	}
}
