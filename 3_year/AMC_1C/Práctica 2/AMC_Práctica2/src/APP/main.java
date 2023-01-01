/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import GUI.MainMenu;

/**
 *
 * @author AburoSenpai y Alvi
 */
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        // Lanzamos el menú
        MainMenu a = new MainMenu();
        
        // Pruebas de clone
        /*
        FicheroCargado f = new FicheroCargado("pruebaClone.txt");
        AFND automata = new AFND(f);
        AFND automataCopia = (AFND)automata.clone();
        Object[] estadosDestinos = {"q3"};
        
        System.out.println("Transiciones:");
        System.out.println(automata.verTransiciones());
        System.out.println(automataCopia.verTransiciones());
        
        automata.agregarTransicion("q3", 1, estadosDestinos);
        automata.agregarTransicionLambda("q0", estadosDestinos);
        System.out.println(automata.verTransiciones());
        System.out.println(automataCopia.verTransiciones());
        */
    }    
}
