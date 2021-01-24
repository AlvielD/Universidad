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
public class Productor extends Thread {
    private ColaLenta cola;
    
    public Productor(ColaLenta c) {
        cola = c;
    }
    
    public void run() {
        
        //Almacenamos el hilo actual en una variable.
        Thread tct = Thread.currentThread();
        Random r = new Random();
        r.setSeed(System.nanoTime());
        
        try{
            for (int i = 0; i < 15; i++) {

                float n = r.nextFloat()*10;
                Thread.sleep((r.nextInt(3)+1)*1000);    // Duerme el hilo un número aleatorio de segundos (1s-3s)
                cola.Acola(n);
            }
        } catch (Exception ex) {
            System.out.println("Hilo " + tct.getId() + " no puedo acolar, finalizo");
            // El hilo finaliza
        }
    }
}
