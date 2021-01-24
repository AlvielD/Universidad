/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_práctica9;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Usuario
 */
public class Canvas extends java.awt.Canvas{
    
    private String contadores[];
    private Font tipografía;
    
    /**
     * 
     */
    public Canvas() {
        
        setSize(800, 600);
        
        tipografía = new Font(Font.SERIF, 1, 24);
        
        contadores = new String[Supervisor.TAREAS];
        for (int i = 0; i < contadores.length; i++) {
            contadores[i] = "000000";
        }
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
        
        Image im = createImage(this.getWidth(), this.getHeight());
        Graphics ig = im.getGraphics();
        
        ig.setColor(Color.pink);
        ig.fillRect(50, 50, 400, 580);
        
        ig.setFont(tipografía);
        ig.setColor(Color.MAGENTA);
        for (int i = 0; i < Supervisor.TAREAS; i++) {
            ig.drawString(contadores[i], 100, 100+(i*50));
        }
        
        g.drawImage(im, 0, 0, null); 
    }
    
    /**
     * 
     * @param index
     * @param valor 
     */
    public void setContador(int index, String valor) {
        contadores[index] = valor;
        repaint();
    }
}
