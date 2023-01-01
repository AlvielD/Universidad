package com.si.main.operadores;

import java.util.ArrayList;

import com.si.strips.Operador;
import com.si.strips.Parametro;
import com.si.strips.Predicado;

public class Bajar extends Operador {

	public Bajar() {
		// Nombre del operador
		super("BAJAR");
		
		// Parametros
		this.agregarParametro(new Parametro("X", false));
		
		// Precondiciones
		ArrayList<String> param = new ArrayList<>();
		param.add("X");
		this.agregarPrecondicion(new Predicado("AGARRADO", false, param, false));
		
		// Lista de adicion
		param = new ArrayList<>(param);
		this.agregarPostcondicion(new Predicado("DESPEJADO", false, param, false));
		
		param = new ArrayList<>(param);
		this.agregarPostcondicion(new Predicado("SOBRELAMESA", false, param, false));
		
		this.agregarPostcondicion(new Predicado("BRAZOLIBRE", false, new ArrayList<>(), false));
		
		// Lista de sustraccion
		param = new ArrayList<>(param);
		this.agregarPostcondicion(new Predicado("AGARRADO", true, param, false));
	}

}
