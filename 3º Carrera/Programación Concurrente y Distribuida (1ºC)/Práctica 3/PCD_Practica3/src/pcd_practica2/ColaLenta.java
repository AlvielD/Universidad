/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica2;

/**
 * Implementación de la interfaz ICola.
 * @author Álvaro Esteban Muñoz
 */
public class ColaLenta implements ICola {
    
    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object[] datos;
    private CanvasCola cc;
    
    /**
     * Constructor de la clase. Inicializa la cola y los atributos de la clase.
     * @param capacidad que será la cantidad de elemntos con que se inicializa el vector.
     */
    public ColaLenta(int capacidad, CanvasCola cc) {
        
     //INICIALIZACIÓN DE LOS ATRIBUTOS DE LA CLASE//
     
        this.capacidad = capacidad;
        this.datos = new Object[this.capacidad];
        this.cc = cc;
        this.numelementos = 0;
        
        //Punteros del vector
        this.head = 0;
        this.tail = 0;
    }
    
    /**
     * Devuelve el número de elementos que hay en la cola.
     * @return el número de elementos que hay en la cola.
     */
    @Override
    public int GetNum() {
        return this.numelementos;
    }
    
    /**
     * Añade el elemnto a la cola si no está llena.
     * @param elemento que se acola.
     * @throws java.lang.Exception si la cola está llena.
     */
    @Override
    public synchronized void Acola(Object elemento) throws Exception {
        
        //1) Comprobamos que la cola no está llena
        Thread.sleep(100);
        if (numelementos == capacidad) {
            Thread.sleep(100);
            //2.a) Se lanza una execpción
            cc.avisa("ERROR: Capacidad máxima de la cola alcanzada.");
            throw new Exception("ERROR: Capacidad máxima de la cola alcanzada.");
        }
        
        Thread.sleep(100);
        //2) Acolamos el elemento en datos[tail]
        datos[tail] = elemento;
        
        Thread.sleep(100);
        //3) Controlamos el tail
        if (tail == capacidad-1) {
            Thread.sleep(100);
            tail = 0;
        } else {
            Thread.sleep(100);
            tail++;
        }
        
        Thread.sleep(100);
        //4) Incrementamos el número de elementos
        numelementos++;
        
        cc.representa(head, tail, numelementos, datos);
    }
    
    /**
     * Extrae el primer elemento de la cola si existe.
     * @return elemento que se extrae.
     * @throws java.lang.Exception si la cola está vacía.
     */
    @Override
    public synchronized Object Desacola() throws Exception {
        
        Thread.sleep(100);
        Object retElem = new Object();
        
        Thread.sleep(100);
        //1. Comprobamos si la cola está vacía
        if (numelementos == 0) {
            Thread.sleep(100);
            cc.avisa("ERROR: No existe ningún elemento en la cola.");
            throw new Exception("ERROR: No existe ningún elemento en la cola.");
        }
        
        Thread.sleep(100);
        //2. Almacenamos el elemento que nos indica head en un Objeto que devolveremos
        retElem = datos[head];
        
        Thread.sleep(100);
        //3. Controlamos el head
        if (head == capacidad-1) {
            Thread.sleep(100);
            head = 0;
        } else {
            Thread.sleep(100);
            head++;
        }
        
        //4. Reducimos el número de elementos
        numelementos--;
        
        cc.representa(head, tail, numelementos, datos);
        
        return retElem;  
    }
    
    /**
     * Devuelve el primer elemento de la cola sin extraerlo.
     * @return elemento que está el primero de la cola.
     * @throws java.lang.Exception si la cola está vacía.
     */
    @Override
    public Object Primero() throws Exception {
        
        //1. Comprobamos si la cola está vacía
        if (numelementos == 0) {
            cc.avisa("ERROR: No existe ningún elemento en la cola.");
            throw new Exception("ERROR: No existe ningún elemento en la cola.");
        }

        //2. Devolvemos el elementos
        return datos[head];
    }
    
}
