/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica6;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Usuario
 */
public class RobotR extends Thread {
    
    private Thread tct;
    
    // Representación de los silos en los que descarga el robot
    private Semaphore SiloM1;
    private Semaphore SiloM2;
    
    public RobotR(Semaphore SiloM1, Semaphore SiloM2) {
        this.SiloM1 = SiloM1;
        this.SiloM2 = SiloM2;
    }
    
    @Override
    public void run() {
        
        tct = Thread.currentThread();
        Random r = new Random(System.currentTimeMillis());
        int num_materiaM1, num_materiaM2;
        System.out.println("Hola, soy el RobotR ("+tct.getId()+"), comienzo.");
        
        try {
            // Repite infinatemente hasta que le interrumpan
            while (true) {
            
                // Introducir entre 3 y 5 kg en SiloM1
                num_materiaM1 = r.nextInt(3)+3;
                for (int i = 0; i < num_materiaM1; i++) {
                    SiloM1.release();
                    System.out.println("AÑADIDO 1 DE M1");
                }
                // Notifico de la cantidad introducida
                System.out.println("Soy el RobotR ("+tct.getId()+"), he añadido "+num_materiaM1+"Kg de materia en el SiloM1.");
                
                // Introducir entre 3 y 5 kg en SiloM2
                num_materiaM2 = r.nextInt(3)+3;
                for (int i = 0; i < num_materiaM2; i++) {
                    SiloM2.release();
                    System.out.println("AÑADIDO 1 DE M2");
                }
                // Notifico de la cantidad introducida
                System.out.println("Soy el RobotR ("+tct.getId()+"), he añadido "+num_materiaM2+"Kg de materia en el SiloM2.");
                
                // Esperar 4 segundos
                Thread.sleep(4000);
            }
        } catch (InterruptedException ex) {
            // Cuando el hilo es interrumpido, termina
            System.out.println("Soy el RobotR ("+tct.getId()+"), me han interrumpido, finalizo.");
        }
    }
}
