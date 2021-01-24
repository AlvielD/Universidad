/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica7;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class Escritor implements Runnable{
    
    private monitor mon;
    
    public Escritor(monitor mon) {
        this.mon = mon;
    }
    
    @Override
    public void run() {
        try {
            // Protocolo de entrada
            mon.entraescribir();
            
            // Escribir
            Thread.sleep(3000);
            
            // Protocolo de salida
            mon.saleescribir();
        } catch (InterruptedException ex) {
            Logger.getLogger(Escritor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
