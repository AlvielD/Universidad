/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica2;

import java.util.Random;

/**
 *
 * @author aeste
 */
public class Productor extends Thread {
    private ColaLenta cola;
    
    public Productor(ColaLenta c) {
        cola = c;
    }
    
    public void run() {
        
        //Almacenamos el hilo actual en una variable.
        Thread tct = Thread.currentThread();
        
        System.out.println("Hilo productor: " + tct.getId());
        
        Random r = new Random();
        r.setSeed(System.nanoTime());
        
        for (int i = 0; i < 10; i++) {
            
            float n = r.nextFloat()*10;
            System.out.println("Número aleatorio a acolar: " + n);
            
            try {
                cola.Acola(n);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
