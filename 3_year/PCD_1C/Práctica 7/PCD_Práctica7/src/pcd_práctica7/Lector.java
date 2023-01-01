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
public class Lector extends Thread {
    
    private monitor mon;
    
    public Lector(monitor mon) {
        this.mon = mon;
    }
    
    @Override
    public void run() {
        try {
            // Protocolo de entrada
            mon.entraleer();
            
            // Leer
            Thread.sleep(3000);
            
            // Protocolo de salida
            mon.saleleer();
        } catch (InterruptedException ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
