/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica2;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class UsaCola {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        //Creamos la cola de 10 elementos.
        ColaLenta c = new ColaLenta(10);
        
        //Creamos un hilo Prodcutor y lo lanzamos
        Productor h1 = new Productor(c);
        h1.start();
        
        //Esperamos a que finalice el hilo de clase Productor.
        try {
            
            h1.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(UsaCola.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Creamos dos runnables y dos hilos a los que le pasaremos dichos runnables.
        Consumidor r1 = new Consumidor(c);
        Consumidor r2 = new Consumidor(c);
        
        Thread h2 = new Thread(r1);
        Thread h3 = new Thread(r2);
        
        //Lanzamos los dos hilos.
        h2.start();
        h3.start();
        
        //Esperamos a que finalicen dichos hilos
        try {
            h2.join();
            h3.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(UsaCola.class.getName()).log(Level.SEVERE, null, ex);
        }     
    }
    
}
