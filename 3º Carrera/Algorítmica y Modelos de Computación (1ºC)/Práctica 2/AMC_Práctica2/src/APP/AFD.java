/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package APP;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author AburoSenpai y Alvi
 */
public class AFD implements Cloneable, Proceso{
    
    //*** ELEMENTOS DE UN AUTÓMATA FINITO DETERMINISTA ***//
    // Conjunto finito de estados
        // Estado inicial = 0
        // Conjunto finito de estados finales
    // Conjunto finito de simbolos
    // Conjunto finito de transiciones
    
    private final Object estadoInicial;            // Estado inicial del AFD
    private Object[] estadosFinales;   // Array de estados finales del AFD
    private List<TransicionesAFD> transiciones; // Lista de transiciones de AFD
    
    /**
     * Constructor usado para crear el autómata a partir de inputs
     * @param finales 
     */
    public AFD(Object[] finales) {
        estadoInicial = 0;
        estadosFinales = finales;
        transiciones = new ArrayList<TransicionesAFD>();
    }
    
    /**
     * Constructor usado para la carga de ficheros
     * @param f fichero cargado con los datos del autómata
     */
    public AFD(FicheroCargado f) throws Exception {
        estadoInicial = f.getEstadoInicial();
        estadosFinales = f.getEstadosFin();
        transiciones = f.getTransicionesAFD();   
    }
    
    /**
     * Añade la transicion indicada a la lista de transiciones.
     * @param e1 estado origen de la transicion
     * @param simbolo simbolo de la transicion
     * @param e2 estado destino de la transicion
     */
    public void agregarTransicion(Object e1, Object simbolo, Object e2) {
        transiciones.add(new TransicionesAFD(e1, simbolo, e2));
    }
    
    /**
     * Dado un estado y un símbolo devuelve el estado destino de la transición
     * o -1 en caso de que no exista dicha transicion.
     * @param estado estado origen de la transicion
     * @param simbolo simbolo de la transicion
     * @return estado destino de la transicion
     */
    public Object transicion(Object estado, Object simbolo) {
        
        Object estadoDestino = -1;
        
        // Recorremos la lista de transiciónes buscando la coincida con los parámetros pasados
        for (int i = 0; i < transiciones.size(); i++) {
            if (String.valueOf(transiciones.get(i).getEstadoO()).equals(String.valueOf(estado)) && String.valueOf(transiciones.get(i).getSimbolo()).equals(String.valueOf(simbolo)))
                estadoDestino = transiciones.get(i).getEstadoD();
        }
        
        return estadoDestino; 
    }
    
    /**
     * @param estado estado a comprobar
     * @return true si "estado es un estado final
     */
    @Override
    public boolean esFinal(Object estado) {
        
        boolean existe = false;
        int i = 0;  // Índice del bucle de búsqueda
        
        // Buscamos en la lista de estados finales el estado pasado como parámetro
        while (i < estadosFinales.length && existe == false) {
            if (((String)(estadosFinales[i])).equals(String.valueOf(estado))) {
                existe = true;
            }
            i++;
        }
        
        return existe; 
    }
    
    /**
     * @param cadena cadena a comprobar
     * @return true si la cadena pertenece al lenguaje descrito en el AFD.
     */
    @Override
    public boolean reconocer(String cadena) {
        
        Object estado_actual = estadoInicial;  // Empezamos en el estado inicial
        char[] simbolos;
        simbolos = cadena.toCharArray();
        
        // Aplica, para cada simbolo de la cadena, su transicion correspondiente
        for (int i = 0; i < simbolos.length; i++) {
            estado_actual = transicion(estado_actual, simbolos[i]);
        }
        
        // Si estado_actual es un estado final, la cadena se aprueba.
        return esFinal(estado_actual);  
    }
    
    public String reconocer_por_pasos(String cadena) {
                
        Object estado_actual = estadoInicial;  // Empezamos en el estado inicial
        char[] simbolos;
        String resultado = "Empiezo en '" + estadoInicial + "'\n";
        simbolos = cadena.toCharArray();
        
        // Aplica, para cada simbolo de la cadena, su transicion correspondiente
        for (int i = 0; i < simbolos.length; i++) {
            resultado += "Estoy en '" + estado_actual + "' --> recibo <" + simbolos[i] + "> --> voy hacia '"+ transicion(estado_actual, simbolos[i])+"'\n";
            estado_actual = transicion(estado_actual, simbolos[i]);
        }
        
        if (esFinal(estado_actual)) {
            resultado += "Acabo en '" + estado_actual + "' y soy estado final";
        } else
            resultado += "Acabo en '" + estado_actual + "' y NO soy estado final";

        return resultado;
    }
    
    /**
     * Muestra por pantalla las transiciones del autómnata
     */
    @Override
    public String toString() {
        
        String cadena;
        cadena = "Estado Inicial: " + estadoInicial + "\nEstados finales:";
        
        for (int i = 0; i < estadosFinales.length; i++) {
            cadena += " " + estadosFinales[i];
        }
        
        cadena += "\nTransiciones:";
        for (int i = 0; i < transiciones.size(); i++) {
            cadena += "\n" + transiciones.get(i).verTransicion();  
        }
        
        return cadena;
    }
    
    /**
     *
     * @return
     */
    @Override
    public Object clone() {
        
        AFD obj = null;
        List<TransicionesAFD> transicionesCopia = new ArrayList<>();
        try {
            obj = (AFD)super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("No se puede duplicar");
        }
        
        obj.estadosFinales = this.estadosFinales.clone();
        
        // Como el arrayList no tiene método clone, tendremos que copiar el array posición a posición
        for (int i = 0; i < this.transiciones.size(); i++) {
            transicionesCopia.add((TransicionesAFD)this.transiciones.get(i).clone());
        }
        obj.transiciones = transicionesCopia;
        
        return obj;
    }
}
