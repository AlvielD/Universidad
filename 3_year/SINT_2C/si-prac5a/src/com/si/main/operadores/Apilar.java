package com.si.main.operadores;

import java.util.ArrayList;

import com.si.strips.Operador;
import com.si.strips.Parametro;
import com.si.strips.Predicado;

public class Apilar extends Operador {

	public Apilar() {
		// Nombre del operador
		super("APILAR");
		
		// Parametros
		this.agregarParametro(new Parametro("X", false));
		this.agregarParametro(new Parametro("Y", false));
		
		// Precondiciones
		ArrayList<String> param = new ArrayList<>();
		param.add("Y");
		this.agregarPrecondicion(new Predicado("DESPEJADO", false, param, false));
		
		param = new ArrayList<>();
		param.add("X");
		this.agregarPrecondicion(new Predicado("AGARRADO", false, param, false));
		
		// Lista de adicion
		this.agregarPostcondicion(new Predicado("BRAZOLIBRE", false, new ArrayList<>(), false));
		
		param = new ArrayList<>();
		param.add("X"); param.add("Y");
		this.agregarPostcondicion(new Predicado("SOBRE", false, param, false));
		
		param = new ArrayList<>();
		param.add("X");
		this.agregarPostcondicion(new Predicado("DESPEJADO", false, param, false));
		
		// Lista de sustraccion
		param = new ArrayList<>();
		param.add("Y");
		this.agregarPostcondicion(new Predicado("DESPEJADO", true, param, false));
		
		param = new ArrayList<>();
		param.add("X");
		this.agregarPostcondicion(new Predicado("AGARRADO", true, param, false));
	}

}
