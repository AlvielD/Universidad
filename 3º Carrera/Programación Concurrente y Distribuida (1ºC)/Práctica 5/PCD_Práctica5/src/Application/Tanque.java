/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Graficos.TCanvas;

/**
 *
 * @author aeste
 */
public class Tanque {
    
    private int llantas = 0, PC = 0;
    private final TCanvas canvas;
    
    public Tanque(TCanvas canvas) {
        this.canvas = canvas;
    }
    
    public synchronized void entraLlanta() throws InterruptedException {
        while(llantas==5 || PC==2 || (PC==1 && llantas>4)) {
            System.out.println("Hilo " + Thread.currentThread().getId() + ": No puedo cromarme, duermo.");
            wait();
        }
        // Manejo del canvas
        canvas.desapilaLlanta();
        canvas.cromaLlanta();
        canvas.repaint();
        System.out.println("Hilo " + Thread.currentThread().getId() + ": Entro a cromarme");

        llantas++;
    }
    
    public synchronized void saleLlanta() {
        // Manejo del canvas
        canvas.desCromaLlanta();
        canvas.repaint();
        System.out.println("Hilo " + Thread.currentThread().getId() + ": Salgo de cromarme");
        
        llantas--;
        notifyAll();
    }
    
    public synchronized void entraPC() throws InterruptedException {
        while(PC==2 || llantas>3 || (PC==1 && llantas>0)) {
            System.out.println("Hilo " + Thread.currentThread().getId() + ": No puedo cromarme, duermo.");
            wait();
        }
        // Manejo del canvas
        canvas.desapilaPC();
        canvas.cromaPC();
        canvas.repaint();
        System.out.println("Hilo " + Thread.currentThread().getId() + ": Entro a cromarme");
        
        PC++;
    }
    
    public synchronized void salePC() {
        // Manejo del canvas
        canvas.desCromaPC();
        canvas.repaint();
        System.out.println("Hilo " + Thread.currentThread().getId() + ": Salgo de cromarme");
        
        PC--;
        notifyAll();
    }  
}
