/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *      NOTAS SOBRE LA PRÁCTICA:
 * 
 * SiloM1 y SiloM2 PODRÍAN SER SEMÁFOROS
 * SiloM1.acquire(1);  =   wait();
 * SiloM1.release();   =   signal();
 */

package pcd_práctica6;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class Generador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Creamos e inicializamos los silos
        Semaphore SiloM1 = new Semaphore(0);
        Semaphore SiloM2 = new Semaphore(0);
        
        // CREAR Y LANZAR HILOS DE LOS ROBOT CONSUMIDORES Y EL ROBOT REPONEDOR
        RobotA RA = new RobotA(SiloM1, SiloM2);
        RobotB runnableRB = new RobotB(SiloM1, SiloM2);
        Thread RB = new Thread(runnableRB);
        RobotR RR = new RobotR(SiloM1, SiloM2);
        
        RA.start();
        RB.start();
        RR.start();
        
        // ESPERAR A QUE FINALICEN LOS ROBOT CONSUMIDORES
        try {
            RA.join();
            RB.join();
        } catch (InterruptedException ex) {
            Logger.getLogger(Generador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // INTERRUMPIR AL ROBOT REPONEDOR <objeto>.interrupt();
        RR.interrupt();
    }
    
}
