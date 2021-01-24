/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author aeste
 */
public class monitor {
        
    private final ReentrantLock l;
    private final Condition cL;
    private final Condition cE;
    private boolean escribiendo;
    private int nl;
    private int nlEsperando;
    
    /**
     * Este monitor funciona con desbloquear y espera urgente?
     */
    public monitor() {
        l = new ReentrantLock();
        cL = l.newCondition();
        cE = l.newCondition();
    }
    
    public void entraleer() throws InterruptedException {

        try {
        l.lock();
            System.out.println("Lector " + Thread.currentThread().getId() + ": Voy a leer");
            if (escribiendo) {
                System.out.println("Lector " + Thread.currentThread().getId() + ": No puedo leer, me bloqueo");
                nlEsperando++;
                cL.await();
                nlEsperando--;
                System.out.println("Lector " + Thread.currentThread().getId() + ": Ya puedo leer, me desbloqueo");
            }
            nl+=1;
            cL.signal();
        }finally{l.unlock();}
    }
    
    public void saleleer() {
        
        try {
        l.lock();
            System.out.println("Lector " + Thread.currentThread().getId() + ": He terminado de leer");
            nl-=1;
            if (nl == 0) cE.signal();
        }finally{l.unlock();}
    }
    
    public void entraescribir() throws InterruptedException {
        
        try{
        l.lock();
            System.out.println("Escritor " + Thread.currentThread().getId() + ": Voy a escribir");
            if (nl != 0 || escribiendo) {
                System.out.println("Escritor " + Thread.currentThread().getId() + ": No puedo escribir, me bloqueo");
                cE.await();
                System.out.println("Escritor " + Thread.currentThread().getId() + ": Ya puedo escribir, me desbloqueo");
            }
            escribiendo = true;
        } finally {l.unlock();}
    }
    
    public void saleescribir() {
        
        try {
        l.lock();
            System.out.println("Escritor " + Thread.currentThread().getId() + ": He terminado de escribir");
            escribiendo = false;
            if (nlEsperando == 0) cE.signal();
            else cL.signal();
        } finally {l.unlock();}
    }
}
