package com.si.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import com.si.strips.Predicado;
import com.si.strips.PredicadoSet;

public class FicheroCargado {

    private final File fichero;	// Variable que guardará el fichero lógico

    /**
     * Constructor que inicializa el valor del parÃ¡metro fichero al fichero del
     * que se quieren cargar los datos.
     *
     * @param fileRoute ruta del fichero que se pretende cargar.
     */
    public FicheroCargado(String fileRoute) {
        fichero = new File(fileRoute);
    }

    /**
     * MÃ©todo que mostrarÃ¡ el contenido del fichero por pantalla (solo para
     * testear).
     */
    public String LeeFichero() {

        Scanner s = null;
        String line = "";
        String texto = "";

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el EscÃ¡ner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            //Al final del fichero hay una lÃ­nea que pone EOF
            line = s.nextLine();

            while (s.hasNextLine()) {
                texto += line + "\n";
                line = s.nextLine();
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showInternalMessageDialog(null, "No se ha cargado bien el fichero", "Alerta", 2);
        }
        return texto;
    }

    /**
     *
     * @return 
     */
    public PredicadoSet getEstadoInicial() throws Exception {

        Scanner s = null;
        String line = "";
        String split[];
        PredicadoSet predicados = new PredicadoSet();
        
        String parametros;
        String[] splitParams = null;

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el EscÃ¡ner
            s = new Scanner(fichero);

            // Saltamos en el fichero hasta llegar a la línea que nos dice el estado inicial
            while (!line.contains("Estado Inicial:")) {
            	line = s.nextLine();
            }

            line = s.nextLine();	// Nos saltamos la línea "ESTADO INICIAL"
            while (!line.contains("Estado Final:")) {
            	// Dividimos el nombre y los parámetros
                split = line.split("\\(");
                
                if (split.length > 1) {
	                // Dividimos el conjunto de parámetros por la coma
	                parametros = split[1];
	                splitParams = parametros.split(",");
	                
	                // Añadimos los parámetros al array
	                // El último carácter es un ")" tendremos que evitarlo
	                ArrayList<String> listaParametros = new ArrayList<String>();
	                
	                int i;
	                for(i=0; i<splitParams.length - 1; i++) listaParametros.add(splitParams[i]);
	                listaParametros.add(String.valueOf(splitParams[i].charAt(0)));
	                
	                // Agregamos el predicado al predicadoSet
	                predicados.agregarPredicado(new Predicado(split[0], false, listaParametros, true));
	                
                } else {
                	predicados.agregarPredicado(new Predicado(split[0], false, new ArrayList<String>(), true));
                }
                
                // Pasamos a la siguiente línea
                line = s.nextLine();
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero.");
        }

        return predicados;
    }
    
    /**
    *
    * @return 
    */
   public PredicadoSet getEstadoFinal() throws Exception {

	   boolean parada = false;
       Scanner s = null;
       String line = "";
       String split[];
       PredicadoSet predicados = new PredicadoSet();
       
       String parametros;
       String[] splitParams = null;

       //Lectura del fichero completo
       try {
           //Cargamos el fichero en el EscÃ¡ner
           s = new Scanner(fichero);

           // Saltamos en el fichero hasta llegar a la línea que nos dice el estado inicial
           while (!line.contains("Estado Final:")) {
           	line = s.nextLine();
           }

           line = s.nextLine();	// Nos saltamos la línea "ESTADO INICIAL"
           while (!parada) {
        	   try {
	        	   // Dividimos el nombre y los parámetros
	               split = line.split("\\(");
	               
	               if (split.length > 1) {
	            	   
		               // Dividimos el conjunto de parámetros por la coma
		               parametros = split[1];
		               splitParams = parametros.split(",");
		               
		               // Añadimos los parámetros al array
		               // El último carácter es un ")" tendremos que evitarlo
		               ArrayList<String> listaParametros = new ArrayList<String>();
		               
		               int i;
		               for(i=0; i<splitParams.length - 1; i++) listaParametros.add(splitParams[i]);
		               listaParametros.add(String.valueOf(splitParams[i].charAt(0)));
		               
		               // Agregamos el predicado al predicadoSet
		               predicados.agregarPredicado(new Predicado(split[0], false, listaParametros, true));
	               
	               } else {
	            	   predicados.agregarPredicado(new Predicado(split[0], false, new ArrayList<String>(), true));
	               }
	               
	               // Pasamos a la siguiente línea
	               line = s.nextLine();
	               
        	   } catch(NoSuchElementException ex) {
        		   parada = true;
        	   }
           }
           
       } catch (FileNotFoundException e) {
           System.out.println("Error en la lectura del fichero.");
       }

       return predicados;
   }

    /**
     * Genera un fichero a partir de un nombre estupendo :)
     *
     * @param texto
     */
    public static FicheroCargado generarFichero(String nombre, String texto) {

        try {
            // Inicializamos el ficheor a escribir
            FileWriter fichero_escritura = new FileWriter(nombre + ".txt");

            fichero_escritura.write(texto);
            fichero_escritura.close();
        } catch (IOException ex) {
            Logger.getLogger(FicheroCargado.class.getName()).log(Level.SEVERE, null, ex);
        }

        FicheroCargado fich = new FicheroCargado(nombre + ".txt");

        return fich;
    }
}
