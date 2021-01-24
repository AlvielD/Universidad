/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica2;

import java.awt.Color;

/**
 *
 * @author aeste
 */
public class ColaFrame extends java.awt.Frame {

    /**
     * Creates new form ColaFrame
     */
    public ColaFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String args[]) throws InterruptedException {
        
        // Objetos y variables del apartado gráfico
        int ancho = 800, alto = 600;
        Color col = Color.PINK;
        
        ColaFrame cf = new ColaFrame();
        cf.setSize(ancho, alto);
        cf.setTitle("Práctica 3 - Álvaro Esteban Muñoz");
        cf.setLocationRelativeTo(null);
        CanvasCola cc = new CanvasCola(ancho, alto, col, 4);
        cf.add(cc);
        cf.setVisible(true);
        
        // Objetos y variables del apartado de concurrencia
        ColaLenta c = new ColaLenta(4, cc);
        Productor h1 = new Productor(c);
        Consumidor r1 = new Consumidor(c);
        Thread h2 = new Thread(r1);
        
        // Lanzamos los hilos
        h1.start();     // Productor
        h2.start();     // Consumidor
        
        Thread.sleep(3000);     // Esperamos 3 segundos
        
        h1.join();      // Esperamos a que finalice el hilo productor
        h2.join();      // Esperamos que finalice el hilo consumidor
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
