/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica6;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class RobotB implements Runnable {

    private static final int NUM_SACOS = 10;    // Número de sacos que debe mezlcar el robot
    private Thread tct;
    
    // Representación de los silos de los que toma la materia el robot
    private Semaphore SiloM1;
    private Semaphore SiloM2;

    public RobotB(Semaphore SiloM1, Semaphore SiloM2) {
        this.SiloM1 = SiloM1;
        this.SiloM2 = SiloM2;
    }
    
    @Override
    public void run() {

        tct = Thread.currentThread();
        Random r = new Random(System.currentTimeMillis());
        System.out.println("Hola, soy el RobotB ("+tct.getId()+"), comienzo.");
            
        try { 
            
            for (int i = 0; i < NUM_SACOS; i++) {
                
                // Coger 1 Uds de M1
                for (int j = 0; j < 1; j++) {
                    // Coger 1 Uds de M1
                    SiloM1.acquire(1);
                }
                System.out.println("Soy el RobotB (" + tct.getId() + "), he retirado la materia del silo M1.");

                // Coger 3 Uds de M2
                for (int j = 0; j < 3; j++) {
                    // Coger 1 Uds de M2
                    SiloM2.acquire(1);
                }
                System.out.println("Soy el RobotB (" + tct.getId() + "), he retirado la materia del silo M2.");

                // Fabricar saco de mezcla
                Thread.sleep(r.nextInt(3)+1);   
            }
            System.out.println("Soy el RobotB (" + tct.getId() + "), he fabricado todos mis sacos, finalizo.");
        } catch (InterruptedException ex) {
            Logger.getLogger(RobotB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
