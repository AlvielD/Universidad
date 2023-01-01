package com.si.strips;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import com.si.busqueda.Accion;
import com.si.busqueda.Estado;
import com.si.strips.operadores.AgregarOperadorAPila;
import com.si.strips.operadores.AgregarPredicadosAPila;
import com.si.strips.operadores.AplicarOperador;
import com.si.strips.operadores.CumplirPredicado;
import com.si.strips.operadores.CumplirPredicadoSet;
import com.si.util.Logger;

public class EstadoSTRIPS implements Estado {
	
	// Constantes para aplicar
	public final static int APLICAR_OPERADOR_USUARIO = -1;
	public final static int CUMPLIR_PREDICADO = -2;
	public final static int CUMPLIR_PREDICADOSET = -3;
	public final static int AGREGAR_PREDICADOS_A_PILA = -4;
	public final static int AGREGAR_OPERADOR_USUARIO_A_PILA = -5;
	
	private PredicadoSet predicados;
	private Stack<IApilable> pila;
	
	private final ArrayList<Operador> operadoresUsuario;
	private ArrayList<Operador> plan;
	
	private Logger logger;
	
	public EstadoSTRIPS(final PredicadoSet predicados,
			final PredicadoSet objetivo,
			final ArrayList<Operador> operadoresUsuario,
			Logger logger) {
		this.predicados = predicados;
		this.pila = new Stack<>();
		if (objetivo != null) this.pila.push(objetivo);
		this.operadoresUsuario = operadoresUsuario;
		this.plan = new ArrayList<>();
		this.logger = logger;
	}

	@Override
	public boolean esFinal() {
		boolean fin = pila.isEmpty();
		
		if (fin) {
			logger.logMsg("\nEl algoritmo ha acabado su ejecución.");
			logger.logPlanFinal(this.plan);
		}
		
		return fin;
	}

	@Override
	public Estado getSucesor(Accion accion) {
		if (this.pila.size() == ProblemaSTRIPS.LIMITE_EXPLORACION) return null;
		
		EstadoSTRIPS nuevo = this.copy();
		
		switch (accion.getTipo()) {
		case APLICAR_OPERADOR_USUARIO: // Aplicar Operador
		{
			Operador operador = ((AplicarOperador) accion).getOperadorSTRIPS();
			
			if (nuevo.predicados.tieneTodos(operador.getPrecondiciones())) {
				logger.logMsg("Se puede aplicar el operador. Se aplican sus postcondiciones.");
				
				Iterator<Predicado> it = operador.getPostcondiciones().convertirLista().iterator();
				while (it.hasNext()) {
					Predicado p = it.next();
					if (p.estaNegado())
						nuevo.predicados.eliminarPredicado(p.getContrario());
					else
						nuevo.predicados.agregarPredicado(new Predicado(p));
				}
				
				Operador np = (Operador) nuevo.pila.pop();
				plan.add((Operador) np.clone());
				
				while (nuevo.pila.peek() instanceof Operador) {
					logger.logMsg("Condiciones alcanzadas. Se elimina de la pila el operador sobrante: " + nuevo.pila.peek().toString());
					nuevo.pila.pop();
				}
			} else {
				nuevo.pila.push(operador.getPrecondiciones());
				logger.logMsg("No se puede aplicar el operador. Se introducen sus precondiciones en la pila.");
			}
		}
			break;
		case CUMPLIR_PREDICADO: // Cumplir Predicado
		case CUMPLIR_PREDICADOSET: // Cumplir PredicadoSet
			if(!nuevo.pila.isEmpty()) nuevo.pila.pop();
			break;
		case AGREGAR_PREDICADOS_A_PILA: // Agregar Predicados de una conjunción por separado
		{
			ArrayList<Predicado> pred = ((AgregarPredicadosAPila) accion).getPredicados();
			for (Predicado p : pred)
				nuevo.pila.push(new Predicado(p));
		}
			break;
		case AGREGAR_OPERADOR_USUARIO_A_PILA: // Agregar un Operador a la pila
		{
			Operador op = ((AgregarOperadorAPila) accion).getOperador();
			nuevo.pila.push(op);
			logger.logAgregarOperador(op);
		}
			break;
		default:
			break;
		}
		
		return nuevo;
	}

	@Override
	public ArrayList<Accion> getAccionesPosibles() {
		logger.logEstado(this.pila);
		
		// Operaciones posibles a partir de la pila
		ArrayList<Accion> acciones = new ArrayList<>();
		IApilable cima = this.pila.peek();
		logger.logCimaPila(cima);
		
		switch (cima.getTipo()) {
		case IApilable.OPERADOR:
			acciones.add(new AplicarOperador(cima));
			logger.logIntentarOperador((Operador) cima);
			break;
		case IApilable.PREDICADO:
		{
			Predicado predicado = (Predicado) cima;
			logger.logPredicadoInstanciado(predicado);
			
			if (!predicado.estaInstanciado()) {
				Map<String, Parametro> reemplazo = this.buscarInstanciacion(predicado);
				
				Iterator<IApilable> it = this.pila.iterator();
				while(it.hasNext())
					it.next().reemplazarParametros(reemplazo);
			}
			
			if (this.predicados.tiene((Predicado) cima)) {
				// Si se cumple el predicado se quita de la pila
				CumplirPredicado cp = new CumplirPredicado(cima);
				acciones.add(cp);
				logger.logMsg(cp.toString());
			} else {
				// si no se cumple, se busca en la lista de operadores del usuario aquellos
				// que agreguen el predicado y para cada operador, genero una accion nueva.
				logger.logMsg("Buscando algún operador que agregue el predicado anterior...");
				ArrayList<Operador> ops = buscarOperadoresPredicado((Predicado) cima);
				
				for(Operador o : ops) acciones.add(new AgregarOperadorAPila(o));
				
				logger.logOperadoresPosibles(ops);
			}
		}
			break;
		case IApilable.PREDICADO_SET:
			if (this.predicados.tieneTodos((PredicadoSet)cima)) {
				// Si todos los predicados del conjunto se cumplen se quitan todos de la pila
				CumplirPredicadoSet cps = new CumplirPredicadoSet(cima);
				acciones.add(cps);
				logger.logMsg(cps.toString());
			} else {
				// Si no, se agregan por separado todas las combinaciones posibles
				// de los predicados del conjunto a la pila

				logger.logMsg("No se cumple la conjunción. Se agrega una posible combinación de sus elementos.");
				PredicadoSet conjunto = (PredicadoSet) cima;
				for (ArrayList<Predicado> p : conjunto.getCombinacionesPosibles()) {
					PredicadoSet predicadoSet = new PredicadoSet();
					for (Predicado p2 : p) predicadoSet.agregarPredicado(p2);
					acciones.add(new AgregarPredicadosAPila(predicadoSet));
				}
			}
			break;
		default:
			break;
		}
		
		return acciones;
	}
	
	private ArrayList<Operador> buscarOperadoresPredicado(final Predicado predicado) {
		ArrayList<Operador> operadores = new ArrayList<>();
		
		String pbNombre = predicado.getNombre();
		boolean pbNegado = predicado.estaNegado();
		ArrayList<Parametro> pbParams = predicado.getParametros();
		
		for (Operador o : this.operadoresUsuario) {
			logger.logAnalizandoOperador(o);
			
			for (Predicado p : o.getPostcondiciones().convertirLista()) {
				
				if (p.getNombre().equals(pbNombre) && p.estaNegado() == pbNegado) {
					Operador operador = (Operador) o.clone();
					
					Map<String, Parametro> reemplazo = new HashMap<>();
					ArrayList<Parametro> params = p.getParametros();
					
					for (int i = 0; i < params.size(); i++)
						reemplazo.put(params.get(i).getNombre(), pbParams.get(i));
					
					if (!operador.estaInstanciado()){
						operador.reemplazarParametros(reemplazo);
						
						
						if (!operador.estaInstanciado()) {
							for (Predicado pre : operador.getPrecondiciones().convertirLista()) {
								Map<String, Parametro> rep = buscarInstanciacion(pre);
								operador.reemplazarParametros(rep);
							}
						}
					}
					
					if (operador.estaInstanciado()) {
						operadores.add(operador);
						logger.logAnaOpPostcondicion(p, true);
					} else {
						logger.logAnaOpPostcondicion(p, false);
					}
				} else {
					logger.logAnaOpPostcondicion(p, false);
				}
				
			}
		}
		
		return operadores;
	}
	
	private Map<String, Parametro> buscarInstanciacion(final IApilable apilableBuscado) {
		Map<String, Parametro> reemplazo = new HashMap<>();
		
		String pbNombre = apilableBuscado.getNombre();
		ArrayList<Parametro> pbParams = apilableBuscado.getParametros();
		
		for (Predicado predicadoEstado : this.predicados.convertirLista()) {
			
			if (predicadoEstado.getNombre().equals(pbNombre)) {
				boolean descartar = false;
				
				ArrayList<Parametro> paramsPredEstado = predicadoEstado.getParametros();
				int n = paramsPredEstado.size();
				
				for (int i = 0; i < n && !descartar; i++) {
					for (int j = 0; j < pbParams.size(); j++) {
						Parametro p = pbParams.get(j);
						
						if (p.estaInstanciado() && !paramsPredEstado.get(i).getNombre().equals(p.getNombre()))
							descartar = true;
						else
							reemplazo.put(p.getNombre(), paramsPredEstado.get(i));
					}
				}
			}
		}
		
		return reemplazo;
	}

	private EstadoSTRIPS copy() {
		EstadoSTRIPS nuevo =
				new EstadoSTRIPS(null, null, this.operadoresUsuario, this.logger);
		
		nuevo.predicados = (PredicadoSet) this.predicados.clone();
		
		ArrayList<IApilable> pl = new ArrayList<>(pila);
		Stack<IApilable> nuevaPila = new Stack<>();
		int n = pl.size() - 1;
		for (int i = 0; i <= n; i++) nuevaPila.push((IApilable) pl.get(i).clone());
		nuevo.pila = nuevaPila;
		
		nuevo.plan = this.plan;
		
		return nuevo;
	}
	
}
