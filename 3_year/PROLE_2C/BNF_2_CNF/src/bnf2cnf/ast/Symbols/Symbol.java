package bnf2cnf.ast.Symbols;

public interface Symbol {
	
	public boolean equals(Symbol simbolo);
	public Symbol clone();
	public String getName();
	
	@Override
	public String toString();
}
