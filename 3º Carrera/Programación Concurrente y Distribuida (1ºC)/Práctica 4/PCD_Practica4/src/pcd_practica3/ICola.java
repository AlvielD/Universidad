/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica3;

/**
 * Cola circular creada mediante la utilización de un vector.
 * @author Álvaro Esteban Muñoz
 */
public interface ICola {

    /**
     * Devuelve el número de elementos que hay en la cola.
     * @return el número de elementos que hay en la cola.
     */
    int GetNum();
    
    /**
     * Añade el elemnto a la cola si no está llena.
     * @param elemento que se acola.
     * @throws java.lang.Exception si la cola está llena.
     */
    void Acola(Object elemento) throws Exception;

    /**
     * Extrae el primer elemento de la cola si existe.
     * @return elemento que se extrae.
     * @throws java.lang.Exception si la cola está vacía.
     */
    Object Desacola() throws Exception;

    /**
     * Devuelve el primer elemento de la cola sin extraerlo.
     * @return elemento que está el primero de la cola.
     * @throws java.lang.Exception si la cola está vacía.
     */
    Object Primero() throws Exception;
    
}
