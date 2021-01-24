/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica1;

/**
 * Implementación de la interfaz ICola.
 * @author Álvaro Esteban Muñoz
 */
public class Cola implements ICola {
    
    private int head;
    private int tail;
    private final int capacidad;
    private int numelementos;
    private Object datos[];
    
    /**
     * Constructor de la clase. Inicializa la cola y los atributos de la clase.
     * @param capacidad que será la cantidad de elemntos con que se inicializa el vector.
     */
    public Cola(int capacidad) {
        
     //INICIALIZACIÓN DE LOS ATRIBUTOS DE LA CLASE//
     
        this.capacidad = capacidad;
        this.datos = new Object[this.capacidad];
        
        //Inicializo cada posición del vector???
        /*
        for (int i = 0; i < this.capacidad; i++) {
            datos[i] = new Object;
        }
        */
        
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
    public void Acola(Object elemento) throws Exception {
        
        //1) Comprobamos que la cola no está llena
        if (numelementos == capacidad) {
            //2.a) Se lanza una execpción
            throw new Exception("ERROR: Capacidad máxima de la cola alcanzada.");
        }
        
        //2) Acolamos el elemento en datos[tail]
        datos[tail] = elemento;
        
        //3) Controlamos el tail
        if (tail == capacidad-1) {
            tail = 0;
        } else {
            tail++;
        }
        
        //4) Incrementamos el número de elementos
        numelementos++;
    }
    
    /**
     * Extrae el primer elemento de la cola si existe.
     * @return elemento que se extrae.
     * @throws java.lang.Exception si la cola está vacía.
     */
    @Override
    public Object Desacola() throws Exception {
        
        Object retElem = new Object();
        
        //1. Comprobamos si la cola está vacía
        if (numelementos == 0) {
            throw new Exception("ERROR: No existe ningún elemento en la cola.");
        }
        
        //2. Almacenamos el elemento que nos indica head en un Objeto que devolveremos
        retElem = datos[head];
        
        //3. Controlamos el head
        if (head == capacidad-1) {
            head = 0;
        } else {
            head++;
        }
        
        //4. Reducimos el número de elementos
        numelementos--;
        
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
            throw new Exception("ERROR: No existe ningún elemento en la cola.");
        }
        
        //2. Devolvemos el elementos
        return datos[head];
    }
    
}
