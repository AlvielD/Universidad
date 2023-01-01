/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica2;

/**
 *
 * @author aeste
 */
public class Consumidor implements Runnable {
    private ColaLenta cola;
    
    public Consumidor(ColaLenta c) {
        cola = c;
    }
    
    @Override
    public void run() {
        
        Thread tct = Thread.currentThread();
        Object auxO = null;
        
        for (int i = 0; i < 10; i++) {
            
            try {
                auxO = cola.Desacola();
                System.out.println("Hilo " + tct.getId() + ": Extraído el elemento --> " + auxO);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            
            
        }
    }
    
}
