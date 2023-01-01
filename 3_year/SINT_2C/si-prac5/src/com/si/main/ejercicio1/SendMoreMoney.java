package com.si.main.ejercicio1;

import java.util.ArrayList;
import java.util.List;

import com.si.busqueda.Accion;
import com.si.chr.ProblemaCHR;
import com.si.chr.Restriccion;
import com.si.chr.VarDom;
import com.si.chr.restricciones.Distinto;
import com.si.chr.restricciones.criptografia.SumaCripto1;
import com.si.chr.restricciones.criptografia.SumaCripto2;
import com.si.chr.restricciones.criptografia.SumaCripto3;
import com.si.chr.restricciones.criptografia.SumaCripto4;

public class SendMoreMoney {
	
	public static void main(String[] args) {
		
		VarDom s = new VarDom("S", 0, 9);
		VarDom e = new VarDom("E", 0, 9);
		VarDom n = new VarDom("N", 0, 9);
		VarDom d = new VarDom("D", 0, 9);
		VarDom m = new VarDom("M", 0, 9);
		VarDom o = new VarDom("O", 0, 9);
		VarDom r = new VarDom("R", 0, 9);
		VarDom y = new VarDom("Y", 0, 9);
		
		VarDom c1 = new VarDom("C1", 0, 1);
		VarDom c2 = new VarDom("C2", 0, 1);
		VarDom c3 = new VarDom("C3", 0, 1);
		
		ArrayList<VarDom> variables = new ArrayList<>();
		
		variables.add(c1); variables.add(c2); variables.add(c3);
		
		variables.add(s); variables.add(e); variables.add(n);
		variables.add(d); variables.add(m); variables.add(o);
		variables.add(r); variables.add(y);
		
		ArrayList<Restriccion> restricciones = new ArrayList<>();
		
		// Restriccion all disjoint entre s,e,n,d,m,o,r,y
		for (int i = 3; i < variables.size(); i++)
			for (int j = i + 1; j < variables.size(); j++)
				restricciones.add(new Distinto(variables.get(i), variables.get(j)));
		
		// Restriccion suma
		restricciones.add(new SumaCripto1());
		restricciones.add(new SumaCripto2());
		restricciones.add(new SumaCripto3());
		restricciones.add(new SumaCripto4());
		
		ProblemaCHR sendMoreMoney = new ProblemaCHR(variables, restricciones, null);
		
		System.out.println("-----------------------------------------");
		System.out.println("PROBLEMA DE SEND MORE MONEY (CRIPTOGRFÍA)");
		System.out.println("-----------------------------------------");
		
		long ant = System.currentTimeMillis();
		List<Accion> acciones = sendMoreMoney.resolver();
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
