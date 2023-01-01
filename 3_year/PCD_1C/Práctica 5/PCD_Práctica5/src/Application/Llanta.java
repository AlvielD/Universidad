/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Graficos.TCanvas;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class Llanta implements Runnable {

    private Tanque t;
    
    public Llanta(Tanque t) {
        this.t = t;
    }
    
    @Override
    public void run() {
        
        Random r = new Random(System.nanoTime());
        System.out.println("Hola, soy el hilo " + Thread.currentThread().getId() + " y soy una llanta.");

        try {
            t.entraLlanta();// PROTOCOLO DE ENTRADA
            
            // SE CROMA EN EL TANQUE --> Duerme entre 2 y 3 segundos
            Thread.sleep((r.nextInt(2)+2)*1000);
            
            t.saleLlanta();// PROTOCOLO DE SALIDA
        } catch (InterruptedException ex) {
            Logger.getLogger(Llanta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
