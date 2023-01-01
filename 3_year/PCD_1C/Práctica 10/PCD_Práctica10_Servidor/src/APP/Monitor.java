/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import IRemoto.IRemoto;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class Monitor extends UnicastRemoteObject implements IRemoto {
    
    private final ReentrantLock l;
    private final Condition notaSimple;
    private final Condition registro;
    
    private int ofiLibre = 3;
    private boolean regiLibre = true;
    
    private int colaReg;    // Clientes esperando por un registro
    
    private Canvas c;
    
    /**
     * Inicialización del monitor
     * @param c canvas de la aplicación
     */
    public Monitor(Canvas c) throws RemoteException {
        l = new ReentrantLock();
        registro = l.newCondition(); 
        notaSimple = l.newCondition();
        this.c = c;
    }
    
    //******************************//
    // Definición de los protocolos //
    //******************************//
    /**
     * Protocolo de entrada para la realización de una nota simple
     */
    @Override
    public void entraNS() throws RemoteException {
        try {
        l.lock();
            // Si no hay oficiales libres y el registrado está ocupado, me espero.
            if (ofiLibre == 0 && !regiLibre) {
                System.out.println("C_NotaSimple (" + Thread.currentThread().getId() + "): No puedo pasar.");
                c.esperaNS(Thread.currentThread());   // Actualizar el canvas
                c.repaint();
                notaSimple.await();
                c.pasaNS(Thread.currentThread());     // Actualizar el canvas
                c.repaint();
                System.out.println("C_NotaSimple (" + Thread.currentThread().getId() + "): Ya puedo pasar.");
            }
            
            // Si hay oficiales libres, ocupo uno.
            if (ofiLibre > 0)
                ofiLibre--;
            else    // En caso contrario, ocupo al registrador
                regiLibre = false;
            
            // Actualizamos el canvas
            c.atiendeNS(Thread.currentThread());
            c.repaint();
        } catch (InterruptedException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {l.unlock();}
    }
    
    /**
     * Protocolo de salida para la realización de una nota simple
     */
    @Override
    public void saleNS() throws RemoteException {
        try {
        l.lock();
            ofiLibre++; // Dejamos un oficial libre
            
            if (regiLibre && colaReg > 0) {
                System.out.println("C_NotaSimple (" + Thread.currentThread().getId() + "): Aviso a registro.");
                registro.signal();  // Avisamos a los que esperan por un registro
            }
            else {
                System.out.println("C_NotaSimple (" + Thread.currentThread().getId() + "): Aviso a nota simple.");
                notaSimple.signal(); // Avisamos a los que esperan por un oficial
            }
            
            c.notaS_atendido(Thread.currentThread());
            c.repaint();
        } finally {l.unlock();}
    }
    
    /**
     * Protocolo de entrada para un registro
     */
    @Override
    public void entraReg() throws RemoteException {
        try {
        l.lock();
            // Si el registrador no está libre o no hay oficiales libres, me espero.
            if (!regiLibre || ofiLibre == 0) {
                System.out.println("C_Registro (" + Thread.currentThread().getId() + "): No puedo pasar.");
                colaReg++;  // Añadimos un cliente a la cola de espera por un registro
                c.esperaReg(Thread.currentThread());  // Actualizar el canvas
                c.repaint();
                registro.await();
                colaReg--;  // Ya no estoy en la cola de los que quieres registrarse
                c.pasaReg(Thread.currentThread());    // Actualizar el canvas
                c.repaint();
                System.out.println("C_Registro (" + Thread.currentThread().getId() + "): Ya puedo pasar.");
            }
            
            ofiLibre--; // Ocupo a un oficial
            regiLibre = false;  // Ocupo al registrador
            
            // Actualizamos el canvas
            c.atiendeReg(Thread.currentThread());
            c.repaint();
        } catch (InterruptedException ex) {
            Logger.getLogger(Monitor.class.getName()).log(Level.SEVERE, null, ex);
        } finally {l.unlock();}
    }
    
    /**
     * Protocolo de salida para un registro
     */
    @Override
    public void saleReg() throws RemoteException {
        try {
        l.lock();
            // Dejo mis recursos libres
            ofiLibre++;
            regiLibre = true;

            // Si hay gente esperando por un registro
            if (colaReg > 0) {
                System.out.println("C_Registro (" + Thread.currentThread().getId() + "): Aviso a registro.");
                registro.signal();  // Aviso a los que esperan por un registro
            }
            else {
                // En caso contrario, aviso a un cliente de nota simple
                System.out.println("C_Registro (" + Thread.currentThread().getId() + "): Aviso a nota simple.");
                notaSimple.signal();
            }
            
            c.reg_atendido(Thread.currentThread());
            c.repaint();
        } finally {l.unlock();}
    }
}
