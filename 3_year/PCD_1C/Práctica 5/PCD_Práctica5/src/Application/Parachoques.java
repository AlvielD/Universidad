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
public class Parachoques extends Thread {
    
    private Tanque t;
    
    public Parachoques(Tanque t) {
        this.t = t;
    }
    
    @Override
    public void run() {

        System.out.println("Hola, soy el hilo " + Thread.currentThread().getId() + " y soy un parachoques.");
        
        try {
            // PROTOCOLO DE ENTRADA
            t.entraPC();
            
            // SE CROMA EN EL TANQUE --> Duerme 4 segundos
            Thread.sleep(4000);
            
            // PROTOCOLO DE SALIDA
            t.salePC();
        } catch (InterruptedException ex) {
            Logger.getLogger(Parachoques.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
