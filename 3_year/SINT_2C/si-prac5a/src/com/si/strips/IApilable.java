package com.si.strips;

import java.util.ArrayList;
import java.util.Map;

public interface IApilable {
	
	public static final int PREDICADO = 1;
	public static final int PREDICADO_SET = 2;
	public static final int OPERADOR = 3;
	
	public int getTipo();
	
	public boolean estaInstanciado();
	
	public String getNombre();
	
	public ArrayList<Parametro> getParametros();
	
	public void reemplazarParametros(Map<String, Parametro> tabla);
	
	public Object clone();
	
}
