/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author AburoSenpai y Alvi
 */
public class FicheroCargado {

    private final File fichero;

    /**
     * Constructor que inicializa el valor del parámetro fichero al fichero del
     * que se quieren cargar los datos.
     *
     * @param fileRoute ruta del fichero que se pretende cargar.
     */
    public FicheroCargado(String fileRoute) {
        fichero = new File(fileRoute);
    }

    /**
     * Método que mostrará el contenido del fichero por pantalla (solo para
     * testear).
     */
    public String LeeFichero() {

        Scanner s = null;
        String line = "";
        String texto = "";

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el Escáner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            //Al final del fichero hay una línea que pone EOF
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
     * @return un array con los estados finales del autómata
     */
    public Object[] getEstadosFin() throws Exception {

        Scanner s = null;
        String line = "";
        String split[];
        Object[] estadosFinales = null;

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el Escáner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            line = s.nextLine();

            while (!line.contains("FINALES")) {
                line = s.nextLine();
            }

            split = line.split(" ");
            estadosFinales = new Object[split.length - 1];
            for (int i = 1; i < split.length; i++) {    // Nos saltamos la posición 0 que contiene "FINALES:"
                estadosFinales[i - 1] = split[i];
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero.");
        } 

        return estadosFinales;
    }

    /**
     *
     * @return un arrayList con las transiciones del autómata contenido en el
     * fichero
     */
    public ArrayList<TransicionesAFD> getTransicionesAFD() throws Exception{

        Scanner s = null;
        String line = "";
        String split[];
        ArrayList<TransicionesAFD> trans = new ArrayList<TransicionesAFD>();
        Object e1, e2;
        Object simbolo;

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el Escáner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            //Al final del fichero hay una línea que pone EOF
            line = s.nextLine();

            while (!line.contains("TRANSICIONES:")) {
                line = s.nextLine();
            }

            line = s.nextLine();    // Saltamos una línea

            while (s.hasNextLine()) {
                split = line.split(" ");
                e1 = split[1];    // Saltamos la posición 0 del split
                simbolo = split[2];
                e2 = split[3];

                trans.add(new TransicionesAFD(e1, simbolo, e2));    // Añadimos al arrayList
                line = s.nextLine();                                // Saltamos a la siguiente linea
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero.");
        } 

        return trans;
    }

    /**
     *
     * @return un arrayList con las transiciones del autómata contenido en el
     * fichero
     */
    public ArrayList<TransicionesAFND> getTransicionesAFND() throws Exception {

        Scanner s = null;
        String line = "";
        String split[];
        ArrayList<TransicionesAFND> trans = new ArrayList<TransicionesAFND>();
        Object e1, simbolo;
        Object[] e2;

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el Escáner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            //Al final del fichero hay una línea que pone EOF
            line = s.nextLine();

            while (!line.contains("TRANSICIONES:")) {
                line = s.nextLine();
            }

            line = s.nextLine();    // Saltamos una línea

            while (!line.contains("TRANSICIONES LAMBDA:")) {

                split = line.split(" ");
                e1 = split[1];    // Saltamos la posición 0 del split
                simbolo = split[2];
                e2 = new Object[split.length - 3];

                for (int i = 0; i < e2.length; i++) {
                    e2[i] = split[i + 3];
                }

                trans.add(new TransicionesAFND(e1, simbolo, e2));   // Añadimos al arrayList
                line = s.nextLine();                                // Saltamos a la siguiente linea
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero.");
        } 

        return trans;
    }

    /**
     *
     * @return un arrayList con las transiciones del autómata contenido en el
     * fichero
     */
    public ArrayList<TransicionesLambda> getTransicionesLambda() throws Exception {

        Scanner s = null;
        String line = "";
        String split[];
        ArrayList<TransicionesLambda> trans = new ArrayList<TransicionesLambda>();
        Object e1;
        Object[] e2;

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el Escáner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            //Al final del fichero hay una línea que pone EOF
            line = s.nextLine();

            while (!line.contains("TRANSICIONES LAMBDA:")) {
                line = s.nextLine();
            }

            line = s.nextLine();    // Saltamos una línea

            while (s.hasNextLine()) {

                split = line.split(" ");
                e1 = split[1];    // Saltamos la posición 0 del split
                e2 = new Object[split.length - 2];

                for (int i = 0; i < e2.length; i++) {
                    e2[i] = split[i + 2];
                }

                trans.add(new TransicionesLambda(e1, e2));   // Añadimos al arrayList
                line = s.nextLine();                         // Saltamos a la siguiente linea
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero.");
        }

        return trans;
    }

    /**
     *
     * @return el estado inicial del autómata
     */
    public Object getEstadoInicial() throws Exception {

        Scanner s = null;
        String line = "";
        String split[];
        Object estadoInicial = null;

        //Lectura del fichero completo
        try {
            //Cargamos el fichero en el Escáner
            s = new Scanner(fichero);

            //Mientras no se alcance el final del fichero, sigue leyendo.
            line = s.nextLine();

            while (!line.contains("INICIAL")) {
                line = s.nextLine();
            }

            split = line.split(" ");
            estadoInicial = split[1];
        } catch (FileNotFoundException e) {
            System.out.println("Error en la lectura del fichero.");
        }

        return estadoInicial;
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
