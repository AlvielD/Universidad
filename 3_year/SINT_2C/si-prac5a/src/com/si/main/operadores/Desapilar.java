package com.si.main.operadores;

import java.util.ArrayList;

import com.si.strips.Operador;
import com.si.strips.Parametro;
import com.si.strips.Predicado;

public class Desapilar extends Operador {

	public Desapilar() {
		// Nombre del operador
		super("DESAPILAR");
		
		// Parametros
		this.agregarParametro(new Parametro("X", false));
		this.agregarParametro(new Parametro("Y", false));
		
		// Precondiciones
		ArrayList<String> param = new ArrayList<>();
		param.add("X"); param.add("Y");
		this.agregarPrecondicion(new Predicado("SOBRE", false, param, false));
		
		param = new ArrayList<>();
		param.add("X");
		this.agregarPrecondicion(new Predicado("DESPEJADO", false, param, false));
		
		this.agregarPrecondicion(new Predicado("BRAZOLIBRE", false, new ArrayList<>(), false));
		
		// Lista de adicion
		param = new ArrayList<>();
		param.add("X");
		this.agregarPostcondicion(new Predicado("AGARRADO", false, param, false));
		
		param = new ArrayList<>();
		param.add("Y");
		this.agregarPostcondicion(new Predicado("DESPEJADO", false, param, false));
		
		// Lista de sustraccion
		param = new ArrayList<>();
		param.add("X"); param.add("Y");
		this.agregarPostcondicion(new Predicado("SOBRE", true, param, false));
		
		param = new ArrayList<>();
		param.add("X");
		this.agregarPostcondicion(new Predicado("DESPEJADO", true, param, false));
		
		this.agregarPostcondicion(new Predicado("BRAZOLIBRE", true, new ArrayList<>(), false));
	}

}
