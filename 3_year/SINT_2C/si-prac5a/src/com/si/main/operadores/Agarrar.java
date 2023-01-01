package com.si.main.operadores;

import java.util.ArrayList;

import com.si.strips.Operador;
import com.si.strips.Parametro;
import com.si.strips.Predicado;

public class Agarrar extends Operador {

	public Agarrar() {
		// Nombre del operador
		super("AGARRAR");
		
		// Parametros
		this.agregarParametro(new Parametro("X", false));
		
		// Precondiciones
		ArrayList<String> param = new ArrayList<>();
		param.add("X");
		this.agregarPrecondicion(new Predicado("DESPEJADO", false, param, false));
		
		param = new ArrayList<>(param);
		this.agregarPrecondicion(new Predicado("SOBRELAMESA", false, param, false));
		
		this.agregarPrecondicion(new Predicado("BRAZOLIBRE", false, new ArrayList<>(), false));
		
		// Lista de adicion
		param = new ArrayList<>(param);
		this.agregarPostcondicion(new Predicado("AGARRADO", false, param, false));
		
		// Lista de sustraccion
		param = new ArrayList<>(param);
		this.agregarPostcondicion(new Predicado("DESPEJADO", true, param, false));
		
		param = new ArrayList<>(param);
		this.agregarPostcondicion(new Predicado("SOBRELAMESA", true, param, false));
		
		this.agregarPostcondicion(new Predicado("BRAZOLIBRE", true, new ArrayList<>(), false));
	}

}
