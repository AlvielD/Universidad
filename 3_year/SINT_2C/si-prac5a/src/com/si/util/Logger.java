package com.si.util;

import java.util.ArrayList;
import java.util.Stack;

import com.si.strips.IApilable;
import com.si.strips.Operador;
import com.si.strips.Predicado;
import com.si.strips.PredicadoSet;

public class Logger {

	private StringBuilder sb;
	
	public Logger() {
		this.sb = new StringBuilder();
	}
	
	public void logInit(final PredicadoSet estadoInicial, final PredicadoSet objetivos, final ArrayList<Operador> operadores) {
		sb.append("\n\n");
		sb.append(" ▒█▀▀█ ▒█▀▀█ ▒█▀▀▀█ ▒█▀▀█ ▒█░░░ ▒█▀▀▀ ▒█▀▄▀█ ░█▀▀█   ▒█▀▀▄ ▒█▀▀▀   ▒█▀▀█ ▒█░░░ ▒█▀▀▀█ ▒█▀▀█ ▒█░▒█ ▒█▀▀▀ ▒█▀▀▀█ \n");
		sb.append(" ▒█▄▄█ ▒█▄▄▀ ▒█░░▒█ ▒█▀▀▄ ▒█░░░ ▒█▀▀▀ ▒█▒█▒█ ▒█▄▄█   ▒█░▒█ ▒█▀▀▀   ▒█▀▀▄ ▒█░░░ ▒█░░▒█ ▒█░▒█ ▒█░▒█ ▒█▀▀▀ ░▀▀▀▄▄ \n");
		sb.append(" ▒█░░░ ▒█░▒█ ▒█▄▄▄█ ▒█▄▄█ ▒█▄▄█ ▒█▄▄▄ ▒█░░▒█ ▒█░▒█   ▒█▄▄▀ ▒█▄▄▄   ▒█▄▄█ ▒█▄▄█ ▒█▄▄▄█ ░▀▀█▄ ░▀▄▄▀ ▒█▄▄▄ ▒█▄▄▄█ \n");
		sb.append("\n\t\t\t\t\t\t\t\t\tÁlvaro Esteban Muñoz, Jesús Valeo Fernández (c) 2021.\n");
		
		sb.append("\n\n\n");
		sb.append("[#] Estado Inicial: ").append(estadoInicial.toString()).append("\n");
		sb.append("[#] Objetivos: ").append(objetivos.toString()).append("\n");
		sb.append("[+] Operadores posibles: { "); 
		for (int i = 0; i < operadores.size(); i++) {
			sb.append(operadores.get(i).toString());
			if (i != operadores.size() - 1) sb.append(", ");
		}
		sb.append(" } \n\n\n");
		sb.append("Iniciado el algoritmo STRIPS:\n");
	}
	
	public void logEstado(final Stack<IApilable> pila) {
		sb.append("Estado actual de la pila: \n");
		
		ArrayList<IApilable> pl = new ArrayList<>(pila);
		int i = pl.size() - 1;
		while (i >= 0) {
			sb.append("\t");
			IApilable elem = pl.get(i);
			switch (elem.getTipo()) {
			case IApilable.PREDICADO:
				sb.append(((Predicado) elem).toString());
				break;
			case IApilable.OPERADOR:
				sb.append(((Operador) elem).toString());
				break;
			case IApilable.PREDICADO_SET:
				sb.append(((PredicadoSet) elem).toString());
				break;
			}
			sb.append("\n");
			i--;
		}
	}
	
	public void logCimaPila(final IApilable cima) {
		sb.append("Analizando la cima de la pila: ");
		switch (cima.getTipo()) {
		case IApilable.OPERADOR:
			sb.append(((Operador) cima).toString()).append(" (Operador)");
			break;
		case IApilable.PREDICADO:
			sb.append(((Predicado) cima).toString()).append(" (Predicado)");
			break;
		case IApilable.PREDICADO_SET:
			sb.append(((PredicadoSet) cima).toString()).append(" (Conjunción de Predicados)");
			break;
		}
		sb.append("\n");
	}
	
	public void logIntentarOperador(final Operador op) {
		sb.append("Se intenta aplicar el operador ").append(op.toString()).append(".\n");
	}
	
	public void logPredicadoInstanciado(final Predicado pred) {
		boolean instanciado = pred.estaInstanciado();
		sb.append("El predicado ").append(pred.toString());
		String m = (instanciado) ? " ya " : " no ";
		sb.append(m).append("está instanciado");
		m = (instanciado) ? "." : " todavía.";
		sb.append(m).append("\n");
	}
	
	public void logAnalizandoOperador(final Operador op) {
		sb.append("\tAnalizando el operador ").append(op.toString()).append(":\n");
	}
	
	public void logAnaOpPostcondicion(final Predicado pred, final boolean valido) {
		sb.append("\t\tLa postcondición ").append(pred.toString());
		if (!valido) sb.append(" no ");
		else sb.append(" ");
		sb.append("es válida.\n");
	}
	
	public void logOperadoresPosibles(final ArrayList<Operador> operadores) {
		sb.append("Operadores posibles encontrados: ");
		for (int i = 0; i < operadores.size(); i++) {
			sb.append(operadores.get(i).toString());
			if (i != operadores.size() - 1) sb.append(", ");
		}
		sb.append("\n");
	}
	
	public void logAgregarOperador(final Operador op) {
		sb.append("Se introduce el operador: ").append(op.toString()).append(" en la pila.\n");
	}
	
	public void logMsg(final String msg) {
		sb.append(msg).append("\n");
	}
	
	public void logPlanFinal(final ArrayList<Operador> plan) {
		sb.append("\nPlan para llegar al objetivo: [");
		for (int i = 0; i < plan.size(); i++) {
			sb.append(plan.get(i).toString());
			if (i != plan.size() - 1) sb.append(", ");
		}
		sb.append("]\n");
	}
	
	public String getTexto() {
		return this.sb.toString();
	}
	
}
