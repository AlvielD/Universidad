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
public class C_NotaSimple extends Thread {
    
    private final Monitor mon;
    
    public C_NotaSimple(Monitor mon) {
        this.mon = mon;
    }
    
    @Override
    public void run() {
        
        System.out.println("Hola, soy el C_NotaSimple (" + Thread.currentThread().getId() + ")");
        // Protocolo de entrada para clientes de nota simple
        mon.entraNS();
        
        // Sección Crítica
        System.out.println("C_NotaSimple (" + Thread.currentThread().getId() + "): Estoy siendo atendido.");
        try {
            Thread.sleep(3000); // 3 segundos que se tarda en el proceso
        } catch (InterruptedException ex) {
            Logger.getLogger(C_NotaSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Protocolo de salida para clientes de registro
        mon.saleNS();
        System.out.println("C_NotaSimple (" + Thread.currentThread().getId() + "): Acabo.");
    }
}
