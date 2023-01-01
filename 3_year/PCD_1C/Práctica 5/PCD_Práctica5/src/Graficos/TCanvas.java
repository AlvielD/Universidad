/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Usuario
 */
public class TCanvas extends Canvas{
    
    // Imágenes a dibujar en el canvas
    private BufferedImage imgLlanta = null;
    private BufferedImage imgParachoques = null;
    
    // Nos indicarán que posiciones de la pila de piezas están ocupadas
    private int num_llantas;
    private int num_pchoques;
    private int num_llantasTanque;
    private int num_pchoquesTanque;
    
    /**
     * 
     * @param ANCHO ancho del canvas (px)
     * @param ALTO alto del canvas (px)
     */
    public TCanvas(int ANCHO, int ALTO) {
        
        // Inicialización del canvas
        this.setSize(ANCHO, ALTO);
        this.setBackground(Color.orange);
        
        // Inicialización de las imágenes que serán dibujadas en el canvas
        try {
            imgLlanta = ImageIO.read(new File("src/Images/llanta.jpg"));
            imgParachoques = ImageIO.read(new File("src/Images/parachoques.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(TCanvas.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        num_llantas = 0;
        num_pchoques = 0;
        num_llantasTanque = 0;
        num_pchoquesTanque = 0;
    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    /**
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {

        // Dibujo del tanque y las pilas de piezas
        g.setColor(Color.GRAY);
        g.fillRect(350, 450, 500, 300); // Tanque
        g.setColor(Color.lightGray);
        g.fillRect(50, 50, 200, 650);   // Pila de llantas
        g.fillRect(950, 50, 200, 650);  // Pila de parachoques
        
        // Dibujo las llantas en la pila de llantas
        for (int i = 0; i < num_llantas; i++) {
            if (i%2==0) {
                g.drawImage(imgLlanta, 85, 60+(65*(i/2)), null);   
            } else {
                g.drawImage(imgLlanta, 150, 60+(65*(i/2)), null);
            }
        }
        
        // Dibujo los parachoques en la pila de parachoques
        for (int i = 0; i < num_pchoques; i++) {
            g.drawImage(imgParachoques, 985, 60+(65*i), null);
        }
        
        // Dibujo las llantas en el tanque
        for (int i = 0; i < num_llantasTanque; i++) {
            g.drawImage(imgLlanta, 360+(65*i), 460, null);
        }
        
        // Dibujo los parachoques en el tanque
        for (int i = 0; i < num_pchoquesTanque; i++) {
            g.drawImage(imgParachoques, 360+(130*i), 525, null);
        }
    }
    
    /**
     * Actualiza la información referente a la pila de llantas
     */
    public void apilaLlanta() {
        num_llantas++;
    }
    
    /**
     * Actualiza la información referente a la pila de de parachoques
     */
    public void apilaPC() {
        num_pchoques++;
    }
    
    /**
     * Actualiza la información referente a la pila de llantas
     */
    public void desapilaLlanta() {
        num_llantas--;
    }
    
    /**
     * Actualiza la información referente a la pila de de parachoques
     */
    public void desapilaPC() {
        num_pchoques--;
    }

    /**
     * Actualiza la información referente a la pila de llantas
     */
    public void cromaLlanta() {
        num_llantasTanque++;
    }
    
    /**
     * Actualiza la información referente a la pila de de parachoques
     */
    public void cromaPC() {
        num_pchoquesTanque++;
    }
    
    /**
     * Actualiza la información referente a la pila de llantas
     */
    public void desCromaLlanta() {
        num_llantasTanque--;
    }
    
    /**
     * Actualiza la información referente a la pila de de parachoques
     */
    public void desCromaPC() {
        num_pchoquesTanque--;
    }
}
