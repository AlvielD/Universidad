/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pcd_practica2;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;

/**
 *
 * @author aeste
 */
public class CanvasCola extends Canvas {
    
    // Atributos esenciales
    private int cuadranteHead;
    private int cuadranteTail;
    private int capacidad;
    private int numelementos;
    private Object[] datos;
    private String info;
    
    // Atributos adicionales
    private Font fuenteMensajes;
    private ColaLenta c;
    private String[] cuadrante;
    
    // Constantes
    private static final int[] xHeadCuad1 = {363, 366, 380};
    private static final int[] yHeadCuad1 = {244, 261, 247};
    private static final int[] xHeadCuad2 = {437, 434, 420};
    private static final int[] yHeadCuad2 = {244, 261, 247};
    private static final int[] xHeadCuad3 = {437, 434, 420};
    private static final int[] yHeadCuad3 = {318, 301, 315};
    private static final int[] xHeadCuad4 = {363, 366, 380};
    private static final int[] yHeadCuad4 = {318, 301, 315}; 
    
    private static final int[] xTailCuad1 = {287, 284, 270};
    private static final int[] yTailCuad1 = {168, 151, 165};    
    private static final int[] xTailCuad2 = {513, 516, 530};
    private static final int[] yTailCuad2 = {168, 151, 165};   
    private static final int[] xTailCuad3 = {513, 516, 530};
    private static final int[] yTailCuad3 = {394, 411, 397};
    private static final int[] xTailCuad4 = {287, 284, 270};
    private static final int[] yTailCuad4 = {394, 411, 397}; 
    
    public CanvasCola(int ancho, int alto, Color col, int capacidad) {
        setSize(ancho, alto);
        setBackground(col);
        
        this.c = c;
        this.capacidad = capacidad;
        fuenteMensajes = new Font("Arial", Font.BOLD+Font.ITALIC, 20);        
        info = "";
        cuadrante = new String[4];
        
        for (int i = 0; i < 4; i++)
            cuadrante[i] = "";
    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    @Override
    public void paint(Graphics g) {     
        //Crear una imagen y luego pegarla en el objeto graphics para mejorar la representación
        Image im = createImage(this.getWidth(), this.getHeight());
        Graphics ig = im.getGraphics();
        
        // Dibujamos la representación actualizada de nuestra práctica
        //TO-DO
        ig.drawOval(250, 131, 300, 300);
        ig.drawOval(325, 206, 150, 150);
        
        // Dibujamos las líneas que dividen la cola en sectores
        ig.drawLine(250, 281, 325, 281);
        ig.drawLine(475, 281, 550, 281);
        ig.drawLine(400, 131, 400, 206);
        ig.drawLine(400, 356, 400, 431);
        
        ig.setFont(fuenteMensajes);
        ig.setColor(Color.WHITE);
        ig.drawString(info, 200, 460);
        
        // Dibujamos los datos de la cola
        ig.drawString(cuadrante[0], 310, 215);
        ig.drawString(cuadrante[1], 480, 215);
        ig.drawString(cuadrante[2], 480, 360);
        ig.drawString(cuadrante[3], 310, 360);
        
        // Dibujamos los punteros de Head y tail
        ig.setColor(Color.GREEN);
        switch(cuadranteHead) {
            case 0: {
                ig.fillPolygon(xHeadCuad1, yHeadCuad1, 3);
            }
            break;
            case 1: {
                ig.fillPolygon(xHeadCuad2, yHeadCuad2, 3);
            }
            break;
            case 2: {
                ig.fillPolygon(xHeadCuad3, yHeadCuad3, 3);
            }
            break;
            case 3: {
                ig.fillPolygon(xHeadCuad4, yHeadCuad4, 3);
            }
            break;
        }
        
        ig.setColor(Color.YELLOW);
        switch(cuadranteTail) {
            case 0: {
                ig.fillPolygon(xTailCuad1, yTailCuad1, 3);
            }
            break;
            case 1: {
                ig.fillPolygon(xTailCuad2, yTailCuad2, 3);
            }
            break;
            case 2: {
                ig.fillPolygon(xTailCuad3, yTailCuad3, 3);
            }
            break;
            case 3: {
                ig.fillPolygon(xTailCuad4, yTailCuad4, 3);
            }
            break;
        }
        
        g.drawImage(im, 0, 0, null);  
    }
    
    public void avisa(String mensaje) {
        
        info = mensaje;
        repaint();
    }
    
    public void representa(int head, int tail, int numelementos, Object[] datos) throws Exception {
        
        info = "";
        cuadranteHead = head;
        cuadranteTail = tail;
        this.numelementos = numelementos;

        int i = 0;
        int j = head;
        while (i < this.capacidad) {            
            
            if(i >= this.numelementos || datos[j] == null)
                cuadrante[j] = "";
            else
                cuadrante[j] = String.format("%.2f", datos[j]);
            
            // Actualizamos los índices
            if (j == this.capacidad-1)
                j = 0;
            else
                j++;
            
            i++;
        }
        
        repaint();
    }
}
