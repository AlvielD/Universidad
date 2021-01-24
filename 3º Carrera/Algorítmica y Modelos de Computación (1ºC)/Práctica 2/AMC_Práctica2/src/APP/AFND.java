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
public class AFND implements Cloneable, Proceso {

    private final Object estadoInicial;       // Estado inicial del AFND
    private Object[] estadosFinales;    // Indica cuales son los estados Finales
    private List<TransicionesAFND> transiciones; // Indica la lista de transiciones del AFND
    private List<TransicionesLambda> transicioneslambda; // Indica la lista de transiciones lambda del AFND

    /**
     * Constructor usado para la carga de ficheros
     *
     * @param f fichero cargado en la aplicación
     */
    public AFND(FicheroCargado f) throws Exception {
        estadoInicial = f.getEstadoInicial();
        estadosFinales = f.getEstadosFin();
        transiciones = f.getTransicionesAFND();
        transicioneslambda = f.getTransicionesLambda();
    }

    /**
     * Agrega una transición a la lista de transiciones del autómata
     *
     * @param e1 estado origen de la transición
     * @param simbolo simbolo de la transición
     * @param e2 estado destino de la transición
     */
    public void agregarTransicion(Object e1, Object simbolo, Object[] e2) {
        transiciones.add(new TransicionesAFND(e1, simbolo, e2));
    }

    /**
     * Agrega una transición lambda a la lista de transiciones lambda del
     * autómata
     *
     * @param e1 estado origen de la transición
     * @param e2 estado destino de la transición
     */
    public void agregarTransicionLambda(Object e1, Object[] e2) {
        transicioneslambda.add(new TransicionesLambda(e1, e2));
    }

    /**
     * Dado un estado y un símbolo devuelve los estados destinos de la
     * transición o null en caso de que no exista dicha transicion.
     *
     * @param estado estado origen de la transicion
     * @param simbolo simbolo de la transicion
     * @return array con los estados destinos de la transicion
     */
    private Object[] transicion(Object estado, Object simbolo) {

        Object[] estadoDestino = null;
        boolean encontrada = false;
        int i = 0;

        /*for (int i = 0; i < transiciones.size(); i++) {
            if (String.valueOf(transiciones.get(i).getEstadoO()).equals(String.valueOf(estado)) && String.valueOf(transiciones.get(i).getSimbolo()).equals(String.valueOf(simbolo))) {
                estadoDestino = transiciones.get(i).getEstadoD();
            }
        }*/
        // Recorremos la lista de transiciónes buscando la coincida con los parámetros pasados (MÁS EFICIENTE QUE EL COMENTADO JUSTO ARRIBA)
        while (!encontrada && i < transiciones.size()) {
            if (String.valueOf(transiciones.get(i).getEstadoO()).equals(String.valueOf(estado)) && String.valueOf(transiciones.get(i).getSimbolo()).equals(String.valueOf(simbolo))) {
                estadoDestino = transiciones.get(i).getEstadoD();
            }
            i++;
        }

        return estadoDestino;
    }

    /**
     * Dado un macroestado y un símbolo devuelve los estados destinos de la
     * transición o null en caso de que no exista dicha transicion.
     *
     * @param macroestado conjunto de estados que forman el macroestado de la
     * transición
     * @param simbolo simbolo de la transicion
     * @return array con los estados destinos de la transicion
     */
    public Object[] transicion(Object[] macroestado, Object simbolo) {

        ArrayList<Object> estadoDestino = new ArrayList<Object>();
        Object[] aux;   // Array auxiliar para almacenar los estados destino de aplicar la transición al macroestado[i]

        /*for (int i = 0; i < transiciones.size(); i++) {
            for (int j = 0; j < macroestado.length; j++) {
                if (String.valueOf(transiciones.get(i).getEstadoO()).equals(String.valueOf(macroestado[j])) && String.valueOf(transiciones.get(i).getSimbolo()).equals(String.valueOf(simbolo))) {
                    for (int k = 0; k < transiciones.get(i).getEstadoD().length; k++) {
                        estadoDestino.add(transiciones.get(i).getEstadoD()[k]);
                    }
                }
            }
        }*/
        // Aplicamos el método "transición a cada estado de nuestro macroestado (MÁS EFICIENTE QUE EL COMENTADO JUSTO ARRIBA, HACE USO DEL MÉTODO ANTERIOR)
        for (int i = 0; i < macroestado.length; i++) {
            aux = transicion(macroestado[i], simbolo);  // Sacamos el array de estados destinos de aplicar la transición al macroestado i
            if (aux != null) {                          // Solo se añaden los estados destino si existen
                for (int j = 0; j < aux.length; j++) {
                    if (!estadoDestino.contains(aux[j])) {
                        estadoDestino.add(aux[j]);
                    }
                }
            }
        }

        return estadoDestino.toArray();
    }

    /**
     * Dado un estado y un símbolo devuelve los estados destinos de la
     * transición lambda o un array vacío en caso de que no exista dicha
     * transicion.
     *
     * @param estado estado origen de la transicion
     * @return array con los estados destinos de la transicion
     */
    public Object[] transicionlambda(Object[] estado) {

        ArrayList<Object> estadoDestino = new ArrayList<Object>();
        try {        // Recorremos la lista de transiciónes buscando la coincida con los parámetros pasados
            for (int i = 0; i < transicioneslambda.size(); i++) {
                for (int j = 0; j < estado.length; j++) {
                    if (String.valueOf(transicioneslambda.get(i).getEstadoO()).equals(String.valueOf(estado[j]))) {
                        for (int k = 0; k < transicioneslambda.get(i).getEstadoD().length; k++) {
                            estadoDestino.add(transicioneslambda.get(i).getEstadoD()[k]);

                            Object[] aux = transicionlambda(estadoDestino.toArray());
                            for (int l = 0; l < aux.length; l++) {
                                if (!estadoDestino.contains(aux[l])) {
                                    estadoDestino.add(aux[l]);

                                }

                            }
                        }
                    }
                }
            }
        } catch (StackOverflowError ex) {

        }

        return estadoDestino.toArray();
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
            if (((String) (estadosFinales[i])).equals(String.valueOf(estado))) {
                existe = true;
            }
            i++;
        }

        return existe;
    }

    /**
     * @param macroestado conjunto de estados a comprobar
     * @return true si "estado es un estado final
     */
    public boolean esFinal(Object[] macroestado) {

        boolean fin = false;

        for (int i = 0; i < macroestado.length; i++) {
            if (esFinal(macroestado[i])) {
                fin = true;
            }
        }

        return fin;
    }

    /**
     *
     * @param macroestado conjunto de estados a los que aplicarle la
     * lambda_clausura
     * @return un array con la lambda clausura del macroestado pasado
     */
    private Object[] lambda_clausura(Object[] macroestado) {

        ArrayList<Object> lambda_clausura = new ArrayList<Object>();
        Object[] aux = transicionlambda(macroestado);;

        for (int i = 0; i < macroestado.length; i++) {
            if (!lambda_clausura.contains(macroestado[i])) {
                lambda_clausura.add(macroestado[i]);
            }
        }
        for (int i = 0; i < aux.length; i++) {
            if (!lambda_clausura.contains(aux[i])) {
                lambda_clausura.add(aux[i]);
            }
        }

        return lambda_clausura.toArray();
    }

    /**
     * @param cadena cadena a comprobar
     * @return true si la cadena pertenece al lenguaje descrito en el AFD.
     */
    @Override
    public boolean reconocer(String cadena) {

        char[] simbolo = cadena.toCharArray();
        Object[] macroestado = {estadoInicial};
        //Object[] macroestado = lambda_clausura(estado);

        for (int i = 0; i < simbolo.length; i++) {
            macroestado = lambda_clausura(macroestado);
            macroestado = transicion(macroestado, simbolo[i]);
        }
        macroestado = lambda_clausura(macroestado);

        return esFinal(macroestado);
    }

    public String reconocer_por_pasos(String cadena) {

        char[] simbolo = cadena.toCharArray();
        Object[] macroestado = {estadoInicial};
        String resultado = "Empiezo en '" + estadoInicial + "'\n";
        //Object[] macroestado = lambda_clausura(estado);

        for (int i = 0; i < simbolo.length; i++) {

            if (!transicioneslambda.isEmpty()) {
                resultado += "Estoy en [";
                for (int j = 0; j < macroestado.length; j++) {
                    resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
                }
                macroestado = lambda_clausura(macroestado);
                resultado += "] --> recibo <λ> --> voy hacia [";
                for (int j = 0; j < macroestado.length; j++) {
                    resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
                }
                resultado += "]\n";
            }

            resultado += "Estoy en [";
            for (int j = 0; j < macroestado.length; j++) {
                resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
            }
            macroestado = transicion(macroestado, simbolo[i]);
            resultado += "] --> recibo <" + simbolo[i] + "> --> voy hacia [";
            for (int j = 0; j < macroestado.length; j++) {
                resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
            }
            resultado += "]\n";
        }
        if (!transicioneslambda.isEmpty()) {
            resultado += "Estoy en [";
            for (int j = 0; j < macroestado.length; j++) {
                resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
            }
            macroestado = lambda_clausura(macroestado);
            resultado += "] --> recibo <λ> --> voy hacia [";
            for (int j = 0; j < macroestado.length; j++) {
                resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
            }
            resultado += "]\n";
        }

        if (esFinal(macroestado)) {
            resultado += "Acabo en [";
            for (int j = 0; j < macroestado.length; j++) {
                resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
            }
            resultado += "'] y soy estado final";
        } else {
            resultado += "Acabo en [";
            for (int j = 0; j < macroestado.length; j++) {
                resultado += "'" + macroestado[j] + "', "; // ['q0', 'q1', 'q2']
            }
            resultado += "'] y NO soy estado final";
        }

        return resultado;
    }

    /**
     *
     * @return
     */
    public static AFND pedir() {
        return null;
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

        cadena += "\nTransiciones Lambda:";
        for (int i = 0; i < transicioneslambda.size(); i++) {
            cadena += "\n" + transicioneslambda.get(i).verTransicion();
        }

        return cadena;
    }

    /**
     *
     * @return
     */
    @Override
    public AFND clone() {

        AFND obj = null;
        List<TransicionesAFND> transicionesCopia = new ArrayList<>();
        List<TransicionesLambda> transicionesLCopia = new ArrayList<>();
        try {
            obj = (AFND) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("No se puede duplicar");
        }

        obj.estadosFinales = this.estadosFinales.clone();

        // Como el arrayList no tiene método clone, tendremos que copiar el array posición a posición
        for (int i = 0; i < this.transiciones.size(); i++) {
            transicionesCopia.add((TransicionesAFND) this.transiciones.get(i).clone());
        }
        obj.transiciones = transicionesCopia;

        // Como las transiciones lambda se almacenan en un arrayLista, repetimos lo del paso anterior
        for (int i = 0; i < this.transicioneslambda.size(); i++) {
            transicionesLCopia.add((TransicionesLambda) this.transicioneslambda.get(i).clone());
        }
        obj.transicioneslambda = transicionesLCopia;

        return obj;
    }
}
