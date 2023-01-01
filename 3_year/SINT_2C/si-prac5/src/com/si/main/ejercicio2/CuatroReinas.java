package com.si.main.ejercicio2;

import java.util.ArrayList;
import java.util.List;

import com.si.busqueda.Accion;
import com.si.chr.ProblemaCHR;
import com.si.chr.Restriccion;
import com.si.chr.VarDom;
import com.si.chr.restricciones.DistanciaReina;
import com.si.chr.restricciones.Distinto;

public class CuatroReinas {

	public static void main(String[] args) {
		// N-Reinas --> 4-Reinas
		VarDom x1 = new VarDom("X1", 1, 4);
		VarDom x2 = new VarDom("X2", 1, 4);
		VarDom x3 = new VarDom("X3", 1, 4);
		VarDom x4 = new VarDom("X4", 1, 4);
		
		ArrayList<VarDom> variables = new ArrayList<>();
		
		variables.add(x1); variables.add(x2);
		variables.add(x3); variables.add(x4);
		
		ArrayList<Restriccion> restricciones = new ArrayList<>();
		
		// Restriccion all-disjoint - Evitar misma fila
		for (int i = 0; i < variables.size(); i++)
			for (int j = i + 1; j < variables.size(); j++)
				restricciones.add(new Distinto(variables.get(i), variables.get(j)));
		
		// Comprobar diagonales
		restricciones.add(new DistanciaReina(x1, x2, 1));
		restricciones.add(new DistanciaReina(x1, x3, 2));
		restricciones.add(new DistanciaReina(x1, x4, 3));
		restricciones.add(new DistanciaReina(x2, x3, 1));
		restricciones.add(new DistanciaReina(x2, x4, 2));
		restricciones.add(new DistanciaReina(x3, x4, 1));
		
		ProblemaCHR cuatroReinas = new ProblemaCHR(variables, restricciones, null);
		
		System.out.println("--------------------");
		System.out.println("PROBLEMA DE 4-REINAS");
		System.out.println("--------------------");
		
		long ant = System.currentTimeMillis();
		List<Accion> acciones = cuatroReinas.resolver();
		System.out.println("Problema resuelto en: " + (System.currentTimeMillis() - ant) + "ms");
		
		if (acciones != null) {
			System.out.println("LISTA DE ACCIONES:");
			for (Accion a : acciones) {
				if (a != null)
					System.out.println(a.toString());
			}
		} else System.out.println("NO SE ENCONTRÓ SOLUCIÓN AL PROBLEMA");
	}
	
}
