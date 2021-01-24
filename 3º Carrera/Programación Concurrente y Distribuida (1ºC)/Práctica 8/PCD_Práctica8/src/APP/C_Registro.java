/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class C_Registro implements Runnable {

    private final Monitor mon;
    
    public C_Registro(Monitor mon) {
        this.mon = mon;
    }
    
    @Override
    public void run() {
        
        System.out.println("Hola, soy el C_Registro (" + Thread.currentThread().getId() + ")");
        // Protocolo de entrada para clientes de nota simple
        mon.entraReg();
        
        // Sección Crítica
        System.out.println("C_Registro (" + Thread.currentThread().getId() + "): Estoy siendo atendido.");
        try {
            Thread.sleep(6000); // Tiempo que se emplea en el proceso
        } catch (InterruptedException ex) {
            Logger.getLogger(C_Registro.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Protocolo de salida para clientes de registro
        mon.saleReg();
        System.out.println("C_Registro (" + Thread.currentThread().getId() + "): Acabo.");
    }
    
}
