package bnf2cnf.chomskyGenerator;

import java.util.ArrayList;

import bnf2cnf.ast.Definicion;
import bnf2cnf.ast.Gramatica;
import bnf2cnf.ast.Regla;
import bnf2cnf.ast.Symbols.NTSymbol;
import bnf2cnf.ast.Symbols.Symbol;
import bnf2cnf.ast.Symbols.TSymbol;

public abstract class chomskyGenerator {
	
	private static Gramatica g_cnf;	// GramÃ¡tica en forma normal de Chomsky

	public static Gramatica generarCNF(Gramatica G) {
		
		// Debemos hacer una copia de la gramÃ¡tica, no modificarla
		g_cnf = G.clone();
		
		chomskyGenerator.traducePrimerPaso();	// Primer paso del algoritmo
		chomskyGenerator.traduceSegundoPaso();	// Segundo paso del algoritmo
		chomskyGenerator.traduceTercerPaso();	// Tercer paso del algoritmo
		chomskyGenerator.traduceCuartoPaso();	// Cuarto paso del algoritmo
		
		return g_cnf;
	}
	
	/**
	 * PRIMER PASO: ELIMINAR LAS REGLAS LAMBDA
	 * Eliminar las reglas A â†’ Î» y para cada regla en la que aparezca el sÃ­mbolo A se generan dos reglas, una con A y otra sin A.
	 */
	private static void traducePrimerPaso() {
		
		// Extraemos las definiciones de la gramÃ¡tica
		ArrayList<Definicion> defs_cnf = g_cnf.getDefiniciones();
		
		// Guardaremos los NTSymbols que deberemos tener en cuenta para aÃ±adir las nuevas definiciones
		ArrayList<NTSymbol> simbolosModificados = new ArrayList<NTSymbol>();
				
		// Para cada definiciÃ³n de la gramÃ¡tica, buscamos si tiene regla lambda
		for (Definicion d : defs_cnf) {
			
			ArrayList<Regla> reglas = d.getListaReglas();	// Extraemos su lista de reglas
			int i = 0;										// Ã�ndice del bÃºsqueda de la regla lambda en la definiciÃ³n actual
			boolean encontrada = false;						// Flag --> Â¿Hemos encontrado la regla lambda de esta definiciÃ³n?
					
			// Buscamos si la definiciÃ³n contiene una regla lambda en su lista de reglas
			while (i<reglas.size() && !encontrada) {
							
				Regla reglaActual = reglas.get(i);			// Obtenemos la regla actual
						
				if (reglaActual.getSimbolos().isEmpty()) {	// Si la regla tiene un array de sÃ­mbolos vacÃ­os == Es una regla lambda
								
					// Guardaremos el NTSymbol de la regla en un arrayList para cambiar aquellas reglas que lo contengan mÃ¡s adelante.
					simbolosModificados.add(d.getSimbolo());
								
					reglas.remove(i);	// Borramos la regla lambda
					encontrada = true;	// Regla encontrada, no seguimos buscando
				}
				i++;
			}
		}
				
		// Para cada definiciÃ³n miramos si hemos eliminado una regla lambda
		for (Definicion d : defs_cnf) {
			
			ArrayList<Regla> reglasActual = d.getListaReglas();	// Extraemos las reglas de la definiciÃ³n actual
			
			// Para cada regla de la definiciÃ³n...
			for (int i=0; i<reglasActual.size(); i++) {
							
				ArrayList<Symbol> simbolosActual = reglasActual.get(i).getSimbolos();	// Extraemos los sÃ­mbolos de la regla actual
				
				// Para cada sÃ­mbolo de la regla...
				for (Symbol sActual : simbolosActual) {
							
					// Si nuestro sActual coincide con alguno del array de sÃ­mbolos modificados, crearemos una nueva regla igual que
					// la regla actual pero eliminando el sActual.
					for (NTSymbol sModifActual : simbolosModificados) {
						
						// ComparaciÃ³n sÃ­mbolo actual == sÃ­mbolo modificado actual
						if(sActual.equals(sModifActual)) {
							
							Regla regModif = reglasActual.get(i).clone();	// Clonamos la regla actual
							regModif.borraSimb(sActual);					// Pero borramos el sÃ­mbolo actual
							reglasActual.add(regModif);						// Lo aÃ±adimos a la lista de reglas de la definiciÃ³n actual
							
						}
					}	
				}
			}
		}
	}
	
	/**
	 * SEGUNDO PASO: AISLAR LOS SÃ�MBOLOS TERMINALES
	 * Para cada sÃ­mbolo terminal a que aparezca en alguna regla acompaÃ±ado de mÃ¡s sÃ­mbolos se crea un nuevo sÃ­mbolo no terminal A,
	 * se aÃ±ade la regla A â†’ a y se sustituyen las apariciones del terminal a por el no terminal A.
	 */
	private static void traduceSegundoPaso() {
		
		NTSymbol NTDefAux;
			
		// Array de sÃ­mbolos auxiliares de los que crearemos reglas aisladas
		ArrayList<TSymbol> auxSymbols2 = new ArrayList<TSymbol>();
		ArrayList<NTSymbol> auxDerivs = new ArrayList<NTSymbol>();
		
		ArrayList<Definicion> defs_cnf = g_cnf.getDefiniciones();	// Extraemos la definiciones de la gramÃ¡tica
		
		
		// Recorreremos todas las definiciones buscando aquellas reglas que contengan sÃ­mbolos terminales aislados, 
		for (int i=0; i<defs_cnf.size(); i++) {		// Para cada definiciÃ³n...
					
			ArrayList<Regla> reglasActual = defs_cnf.get(i).getListaReglas();	// Extraemos la lista de reglas de la definiciÃ³n actual
			
			for (int j=0; j<reglasActual.size(); j++) {	// Para cada regla...
							
				Regla reglaActual = reglasActual.get(j);						// Extraemos la regla actual
				ArrayList<Symbol> simbolosActual = reglaActual.getSimbolos();	// Extraemos el array de sÃ­mbolos de la regla actual
				int contador = reglaActual.getNumTerminales();					// Contamos los sÃ­mbolos terminales en la lista de sÃ­mbolos
				
				// Si la regla contiene un sÃ­mbolo terminal --> (contador > 0 && simbolosActual.size() > 1)
				if (contador > 0 && simbolosActual.size() > 1) {
					
					int k = 0;						// Ã�ndice del bucle
					int encontrado = 0;				// NÃºmero de sÃ­mbolos terminales encontrados
					
					// Mientras no hayamos sustituido todos los sÃ­mbolos terminales de la regla, iteramos
					while (k<simbolosActual.size() && encontrado<contador) {
						
						// Si el sÃ­mbolo es terminal, lo hemos encontrado
						if (simbolosActual.get(k) instanceof TSymbol) {
							encontrado++;
							reglaActual.sustituyeSimb(g_cnf, (TSymbol) simbolosActual.get(k));
						}
						
						k++;
					}
				}
				
			}
		}
	}
	
	/**
	 * TERCER PASO: EXPANDIR LAS REGLAS CON UN ÃšNICO SÃ�MBOLOS
	 * Sustituir las reglas de tipo A â†’ B (es decir, las reglas con un Ãºnico sÃ­mbolo no terminal en la parte derecha). Para ello,
	 * por cada regla B â†’ a se crea una nueva regla A â†’ a y por cada regla  B â†’ C D E  se crea una regla A â†’ C D E.
	 */
	private static void traduceTercerPaso() {
		
		ArrayList<Definicion> defs_cnf = g_cnf.getDefiniciones();	// Extraemos las definiciones de la grÃ¡matica
		boolean reglasActualizadas = true;							// Flag --> Â¿Hemos actualizado las reglas en la Ãºltima vuelta del bucle?
		
		while (reglasActualizadas) {
		
			reglasActualizadas = false;		// Marcamos las reglas como no actualizadas
			
			// Recorremos la grÃ¡matica buscando aquellas reglas que tengan un Ãºnico sÃ­mbolo que ademÃ¡s sea no terminal
			for (Definicion definicion : defs_cnf) {
				
				ArrayList<Regla> reglasActual = definicion.getListaReglas();
				
				for (int i=0; i<reglasActual.size(); i++) {	// Para cada regla de la definiciÃ³n...
					
					Regla reglaActual = reglasActual.get(i);
					
					if (reglaActual.getNumTerminales() == 0 && reglaActual.getSimbolos().size() == 1) {
						// Estamos ante una regla con un Ãºnico sÃ­mbolo que ademÃ¡s es no terminal, tendremos que derivar la regla 
						// hasta alcanzar un sÃ­mbolo terminal
						
						// Obtenemos la derivaciÃ³n de la regla
						ArrayList<Regla> reglasDerivadas = g_cnf.derivaRegla(reglaActual.getSimbolos().get(0));
						
						reglasActual.remove(i);				// Borramos la regla actual
						
						// AÃ±adimos las reglas una vez derivadas
						for(int j=0; j<reglasDerivadas.size(); j++) reglasActual.add(reglasDerivadas.get(j));
						
						reglasActualizadas = true;			// Marcamos que hemos actualizado las reglas
					}
				}
			}
		}
	}
	
	/**
	 * CUARTO PASO: TROCEAR LAS REGLAS CON MÃ�S DE DOS SÃ�MBOLOS
	 * Por cada regla con mÃ¡s de dos sÃ­mbolos no terminales a la derecha, A â†’ B C D ..., se crean nuevos sÃ­mbolos terminales N1, N2, ... 
	 * y  se sustituye la regla por A â†’ B N1,  N1 â†’ C N2, N2 â†’ D...
	 */
	private static void traduceCuartoPaso() {
		
		ArrayList<Definicion> defs_cnf = g_cnf.getDefiniciones();	// Extraemos las definiciones de la gramÃ¡tica
		
		// Buscamos todas las definiciones que contengan reglas con mÃ¡s de dos sÃ­mbolos
		for (int j=0; j<defs_cnf.size(); j++) {	// Para cada definiciÃ³n de la gramÃ¡tica...
			
			ArrayList<Regla> reglasActual = defs_cnf.get(j).getListaReglas();	// Extraemos el array de reglas de la definiciÃ³n actual
			
			for (Regla regla : reglasActual) {	// Para cada regla de la definiciÃ³n actual...
				
				//System.out.println("Una regla troceada despuÃ©s...\n" + g_cnf.toString());
				if(regla.getSimbolos().size() > 2) {
					
					// La regla tiene mÃ¡s de dos sÃ­mbolos y debe ser troceada.
					NTSymbol simboloNuevaDef = Regla.creaSimbolo();					// Creamos el sÃ­mbolo NT que derivarÃ¡ en la lista de sÃ­mbolos sobrantes
					ArrayList<Symbol> simbolosSobrantes = new ArrayList<Symbol>();	// Creamos el array de sÃ­mbolos que deberÃ¡n ser apartados
					
					// Pasamos cada sÃ­mbolo con Ã­ndice mayor o igual que 1 (1, 2, 3, 4...) a un array aparte.
					// AÃ±adimos el sÃ­mbolo al array de sÃ­mbolos sobrantes
					// Como estamos modificando el tamaÃ±o del array, tendremos que guardar el tamaÃ±o inicial
					int numSimbolos = regla.getSimbolos().size();
					
					for(int i=1; i<numSimbolos; i++) {
						simbolosSobrantes.add(regla.getSimbolos().get(1));
						regla.getSimbolos().remove(1);
					}
					
					// Sustituimos los sÃ­mbolos a partir de la segunda posiciÃ³n por el sÃ­mbolo nuevo
					regla.getSimbolos().add(1, simboloNuevaDef);
					
					Regla reglaSimbolosSobrantes = new Regla(simbolosSobrantes);// Creamos la regla de la definiciÃ³n troceada
					ArrayList<Regla> reglasDefinicion = new ArrayList<Regla>();	// Creamos el array de reglas de la definiciÃ³n troceada
					reglasDefinicion.add(reglaSimbolosSobrantes);				// AÃ±adimos la regla en el array
					
					Definicion defTroceada = new Definicion(simboloNuevaDef, reglasDefinicion);	// Creamos la definiciÃ³n que serÃ¡ aÃ±adida a la gramÃ¡tica
					
					defs_cnf.add(defTroceada);
				}
				
			}
			
		}
		
	}
}
