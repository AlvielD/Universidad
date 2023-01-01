/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica9;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Supervisor extends Thread{
    
    protected static final int TAREAS = 10; //Tareas en las que se ha descompuesto el problema

    private int nHilos; // Número de hilos de la threa pool
    private String hashClave;   // Hash de la clave a averiguar
    private Canvas c;    // frame de la aplicación
    protected static boolean parada;
    
    /**
     * Constructor del hilo
     * @param nHilos número de hilos que tendrá la thread pool creada por la clase
     */
    public Supervisor(int nHilos, String hashClave, Canvas c) {
        this.nHilos = nHilos;
        this.hashClave = hashClave;
        this.c = c;
        parada = false;
    }
    
    @Override
    public void run() {
        try {
            
            Future<Integer>[] claves = new Future[TAREAS];  // Claves obtenidas de ejecutar cada tarea (-1 si no se averigua)
            Calculador[] tareas = new Calculador[TAREAS]; // Array de tareas a ejecutar
            ExecutorService thp = Executors.newFixedThreadPool(nHilos); // Creamos la thread pool

            for (int i = 0; i < TAREAS; i++) {
                tareas[i] = new Calculador(i, hashClave, c);   // Creamos el callable de la tarea
                claves[i] = thp.submit(tareas[i]);  // Añadimos la tarea a la thread pool
            }
            System.out.println("Todos los hilos generados, calculando...");

            for (int i = 0; i < TAREAS; i++) {
                if (claves[i].get() != -1) {
                    thp.shutdownNow();  // Como ya hemos encontrado la clave, paramos la thread pool
                    System.out.println("Calculador " + i + ": La clave es --> " + claves[i].get());
                } else
                    System.out.println("Calculador " + i + ": No he conseguido la clave");
            }
        } catch (InterruptedException | ExecutionException ex) {
            Logger.getLogger(Supervisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
