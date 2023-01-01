package com.si.chr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VarDom {
	
	private String variable;
	private ArrayList<Integer> dominio;
	
	public VarDom(final String variable, final int inicio, final int fin) {
		this.variable = new String(variable);
		this.dominio = new ArrayList<>();
		for (int i = inicio; i <= fin; i++)
			this.dominio.add(i);
	}
	
	public VarDom(final String variable, final List<Integer> dominio) {
		this.variable = new String(variable);
		this.dominio = new ArrayList<>();
		this.dominio.addAll(dominio);
	}
	
	public String getNombre() {
		return this.variable;
	}
	
	public List<Integer> getDominio() {
		return this.dominio;
	}
	
	@Override
	public Object clone() {
		ArrayList<Integer> nuevoDom = new ArrayList<>(this.dominio.size());
		nuevoDom = (ArrayList<Integer>) this.dominio.stream().collect(Collectors.toList());
		return new VarDom(new String(this.variable), nuevoDom);
	}
	
}
