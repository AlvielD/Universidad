package com.si.chr.restricciones.criptografia;

import java.util.HashMap;

import com.si.chr.Instancia;
import com.si.chr.Restriccion;

public class SumaCripto4 implements Restriccion {

	@Override
	public boolean seCumple(HashMap<String, Instancia> instancias) {
		int s = (instancias.get("S") != null) ? instancias.get("S").getValor() : -1;
		int m = (instancias.get("M") != null) ? instancias.get("M").getValor() : -1;
		int o = (instancias.get("O") != null) ? instancias.get("O").getValor() : -1;
		int c3 = (instancias.get("C3") != null) ? instancias.get("C3").getValor() : -1;
		
		return (s == -1) || (m == -1) || (o == -1) || (c3 == -1) || ((c3 + s + m) == (10 * m + o));
	}

}
