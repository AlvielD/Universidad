package com.si.chr.restricciones.criptografia;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;

public class SumaCripto1 implements Restriccion {

	@Override
	public boolean seCumple(HashMap<String, Instancia> instancias) {
		int e = (instancias.get("E") != null) ? instancias.get("E").getValor() : -1;
		int d = (instancias.get("D") != null) ? instancias.get("D").getValor() : -1;
		int y = (instancias.get("Y") != null) ? instancias.get("Y").getValor() : -1;
		int c1 = (instancias.get("C1") != null) ? instancias.get("C1").getValor() : -1;
		
		return (e == -1) || (d == -1) || (y == -1) || (c1 == -1) || ((e + d) == (y + 10 * c1));
	}

}
