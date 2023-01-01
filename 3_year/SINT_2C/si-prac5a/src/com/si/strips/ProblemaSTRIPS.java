package com.si.strips;

import java.util.ArrayList;
import java.util.List;

import com.si.busqueda.Accion;
import com.si.busqueda.Busqueda;
import com.si.util.Logger;

public class ProblemaSTRIPS {

	public static int LIMITE_EXPLORACION = 50;
	private EstadoSTRIPS estadoInicial;
	
	public ProblemaSTRIPS(final PredicadoSet predicadosIni,
			final PredicadoSet objetivo,
			final ArrayList<Operador> operadores,
			Logger logger,
			final int limiteExploracion) {
		this.estadoInicial = new EstadoSTRIPS(predicadosIni, objetivo, operadores, logger);
		logger.logInit(predicadosIni, objetivo, operadores);
		LIMITE_EXPLORACION = limiteExploracion;
	}
	
	public ProblemaSTRIPS(final PredicadoSet predicadosIni,
			final PredicadoSet objetivo,
			final ArrayList<Operador> operadores,
			Logger logger) {
		this.estadoInicial = new EstadoSTRIPS(predicadosIni, objetivo, operadores, logger);
		logger.logInit(predicadosIni, objetivo, operadores);
	}
	
	public List<Accion> resolver() {
		return Busqueda.buscarProfundidad(estadoInicial);
	}
	
}
