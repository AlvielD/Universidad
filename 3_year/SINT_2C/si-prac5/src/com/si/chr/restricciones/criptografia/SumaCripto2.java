package com.si.chr.restricciones.criptografia;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;

public class SumaCripto2 implements Restriccion {

	@Override
	public boolean seCumple(HashMap<String, Instancia> instancias) {
		int n = (instancias.get("N") != null) ? instancias.get("N").getValor() : -1;
		int r = (instancias.get("R") != null) ? instancias.get("R").getValor() : -1;
		int e = (instancias.get("E") != null) ? instancias.get("E").getValor() : -1;
		int c1 = (instancias.get("C1") != null) ? instancias.get("C1").getValor() : -1;
		int c2 = (instancias.get("C2") != null) ? instancias.get("C2").getValor() : -1;
		
		return (n == -1) || (r == -1) || (e == -1) || (c1 == -1) || (c2 == -1) || ((c1 + n + r) == (e + 10 * c2));
	}

}
