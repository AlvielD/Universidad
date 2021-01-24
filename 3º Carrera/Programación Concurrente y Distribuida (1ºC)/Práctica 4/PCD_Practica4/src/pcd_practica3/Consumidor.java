/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica3;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class Consumidor implements Runnable {
    private ColaLenta cola;
    
    public Consumidor(ColaLenta c) {
        cola = c;
    }
    
    @Override
    public void run() {
        
        Thread tct = Thread.currentThread();
        Object auxO = null;
        Random r = new Random();
        r.setSeed(System.nanoTime());
        
        try{
            for (int i = 0; i < 15; i++) {
                Thread.sleep((r.nextInt(3)+1)*1000);    // Duerme el hilo un número aleatorio de segundos (1s-3s)
                auxO = cola.Desacola();
            }
            
            // Debo notificar a los productores para que finalicen, como???
            System.out.println("Hilo consumidor: " + tct.getId() + " He terminado de desacolar");
            for (int i = 0; i < 15; i++) {
                System.out.println("Notifico a los procesos: " + (i+1));
                synchronized(cola) {
                    Thread.sleep(500);
                    cola.notifyAll();
                }
            }
        } catch (Exception ex) {
            System.out.println("Hilo " + tct.getId() + " no puedo desacolar, finalizo");
            // El hilo finaliza
        }
    }
}
