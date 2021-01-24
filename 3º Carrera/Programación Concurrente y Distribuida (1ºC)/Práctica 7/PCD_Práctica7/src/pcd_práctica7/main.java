/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            // Creamos el monitor
            monitor mon = new monitor();
            
            // Creamos los hilos
            Lector l1 = new Lector(mon);
            Lector l2 = new Lector(mon);
            Lector l3 = new Lector(mon);
            Lector l4 = new Lector(mon);
            Lector l5 = new Lector(mon);
            
            Escritor re1 = new Escritor(mon);
            Escritor re2 = new Escritor(mon);
            Thread e1 = new Thread(re1);
            Thread e2 = new Thread(re2);
            
            // Lanzamos los hilos
            e1.start();
            l1.start();
            l2.start();
            l3.start();
            e2.start();
            Thread.sleep(7000);
            l4.start();
            l5.start();
            
            
            // Esperamos a que finalicen los hilos
            l1.join();
            l2.join();
            e1.join();
            l3.join();
            l4.join();
            l5.join();
            e2.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
