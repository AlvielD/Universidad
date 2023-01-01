package com.si.chr.restricciones;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;

public class SumaCripto implements Restriccion {

	@Override
	public boolean seCumple(HashMap<String, Instancia> instancias) {
		
		int s = (instancias.get("S") != null) ? instancias.get("S").getValor() : -1;
		int e = (instancias.get("E") != null) ? instancias.get("E").getValor() : -1;
		int n = (instancias.get("N") != null) ? instancias.get("N").getValor() : -1;
		int d = (instancias.get("D") != null) ? instancias.get("D").getValor() : -1;
		int m = (instancias.get("M") != null) ? instancias.get("M").getValor() : -1;
		int o = (instancias.get("O") != null) ? instancias.get("O").getValor() : -1;
		int r = (instancias.get("R") != null) ? instancias.get("R").getValor() : -1;
		int y = (instancias.get("Y") != null) ? instancias.get("Y").getValor() : -1;
		
		if ( s == -1 || e == -1 || n == -1 || d == -1 || m == -1 || o == -1 || r == -1 || y == -1 )
			return true;
		else
			return (1000*(s + m) + 100*(e + o) + 10*(n + r) + d + e == 10000*m + 1000*o + 100*n + 10*e + y);
	}

}
