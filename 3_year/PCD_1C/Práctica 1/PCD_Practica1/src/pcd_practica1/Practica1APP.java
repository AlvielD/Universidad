/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica1;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aeste
 */
public class Practica1APP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        /*
        Cola c = new Cola(4);
        Object aux = new Object();
        
        for (int i = 0; i < 4; i++) {
            
            try {
                c.Acola(i);
                System.out.println("Elemento acolado: "+ i);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        
        System.out.println("Número de elementos: " + c.GetNum());
        
        while (c.GetNum() != 0) {
                    
            try {
                aux = c.Desacola(); //c.Primero() resulta en un bucle infinito.
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("Número de elementos: " + c.GetNum());
            System.out.println("Elemento desacolado: " + aux);
        }
        */
        
        Cola c = new Cola(2);
        Object aux = new Object();
        
        //Pedir números aleatorios
        
            Random r = new Random();
            r.setSeed(System.nanoTime());
            
        //************************
        
        for (int i = 0; i < 10; i++) {
            int n = r.nextInt(4);
            System.out.println("Número aleatorio resultante: " + n);
            
            try {
                if (n == 0) {
                    aux = c.Desacola();
                    System.out.println("Elemento desacolado: " + aux);
                } else {
                    c.Acola(i);
                    System.out.println("Elemento acolado: " + i);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        
    }
    
}
