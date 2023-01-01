package com.si.chr.restricciones.criptografia;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;

public class SumaCripto3 implements Restriccion {

	@Override
	public boolean seCumple(HashMap<String, Instancia> instancias) {
		int e = (instancias.get("E") != null) ? instancias.get("E").getValor() : -1;
		int o = (instancias.get("O") != null) ? instancias.get("O").getValor() : -1;
		int n = (instancias.get("N") != null) ? instancias.get("N").getValor() : -1;
		int c2 = (instancias.get("C2") != null) ? instancias.get("C2").getValor() : -1;
		int c3 = (instancias.get("C3") != null) ? instancias.get("C3").getValor() : -1;
		
		return (e == -1) || (o == -1) || (n == -1) || (c2 == -1) || (c3 == -1) || ((c2 + e + o) == (n + 10 * c3));
	}

}
