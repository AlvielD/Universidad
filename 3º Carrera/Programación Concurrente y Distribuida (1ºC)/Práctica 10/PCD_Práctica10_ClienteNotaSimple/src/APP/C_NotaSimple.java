/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import IRemoto.IRemoto;
import java.rmi.Naming;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class C_NotaSimple {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        
        IRemoto mon = (IRemoto)Naming.lookup("rmi://localhost:2000/MonitorRemoto");
        
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
