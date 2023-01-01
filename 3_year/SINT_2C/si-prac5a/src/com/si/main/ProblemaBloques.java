package com.si.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import com.si.busqueda.Accion;
import com.si.main.operadores.Agarrar;
import com.si.main.operadores.Apilar;
import com.si.main.operadores.Bajar;
import com.si.main.operadores.Desapilar;
import com.si.strips.Operador;
import com.si.strips.PredicadoSet;
import com.si.strips.ProblemaSTRIPS;
import com.si.util.Logger;

public class ProblemaBloques {

	public static void main(String[] args) {
		
		Logger logger = new Logger();
		
		FicheroCargado f = new FicheroCargado("DefinicionProblema.txt");
		
		try {
			PredicadoSet EstadoInicial = f.getEstadoInicial();
			PredicadoSet Objetivo = f.getEstadoFinal();
			
			ArrayList<Operador> operadores = new ArrayList<>();
			operadores.add(new Agarrar());
			operadores.add(new Apilar());
			operadores.add(new Bajar());
			operadores.add(new Desapilar());
			
			ProblemaSTRIPS problema = new ProblemaSTRIPS(EstadoInicial, Objetivo, operadores, logger);
			
			List<Accion> acciones = problema.resolver();
			
			if (acciones != null) {
				System.out.println("SE HA ENCONTRADO SOLUCIÓN. SE HA IMPRIMIDO UN ARCHIVO QUE INCLUYE LA INFORMACIÓN.");
			} else System.out.println("NO SE ENCONTRÓ SOLUCIÓN AL PROBLEMA");
			
			FileOutputStream outputfile =  new FileOutputStream(new File("Salida.txt"));
			PrintStream stream = new PrintStream(outputfile, false, "UTF-8");
			stream.print(logger.getTexto());
			stream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
