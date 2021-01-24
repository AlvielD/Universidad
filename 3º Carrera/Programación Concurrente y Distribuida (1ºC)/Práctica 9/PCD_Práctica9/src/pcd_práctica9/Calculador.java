/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica9;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class Calculador implements Callable<Integer>{

    private int ini;    // Valor inicial de la clave
    private String hashClave;   // Hash de la clave a romper
    private Canvas c;   // Canvas de la aplicación
    
    public Calculador(int ini, String hashClave, Canvas c) {
        this.ini = ini;
        this.hashClave = hashClave;
        this.c = c;
    }

    @Override
    public Integer call() throws Exception {
        
        int i = 0;
        int respuesta = -1;    // Clave averiguada
        boolean encontrada = false;
        
        System.out.println("Calculador (" + Thread.currentThread().getId() + "): Empiezo a calcular");
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            
            while (i < 100000 && !encontrada && !Supervisor.parada) {
                // Sacamos el hash
                String clave = String.valueOf((ini*100000) + i);
                c.setContador(ini, clave);
                //c.repaint();
                byte[] hash = md.digest(clave.getBytes());  // Sacamos el hash de la clave que vamos a probar
                
                // Transformamos los bytes de dos en dos a carácter hexadecimal
                StringBuilder hex = new StringBuilder();
                for (int j = 0; j < hash.length; j++) {
                
                    if ((0xff & hash[j]) < 0x10)
                        hex.append("0").append(Integer.toHexString((0xFF & hash[j])).toUpperCase());
                    else
                        hex.append(Integer.toHexString(0xFF & hash[j]).toUpperCase());
                }
                
                // Si el hash de la clave coincide con la prueba, BINGO!
                if (hashClave.equals(hex.toString())) {
                    encontrada = true;
                    Supervisor.parada = true;
                    respuesta = Integer.parseInt(clave);
                }
                
                i++;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Calculador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
    }
}
