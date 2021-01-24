/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author aeste
 */
public class Canvas extends java.awt.Canvas{
    
    // Imágenes a dibujar en el canvas
    private BufferedImage imgCliente = null;
    private BufferedImage imgRecepcionista = null;
    private BufferedImage imgAtendiendo = null;
    private BufferedImage imgRegistrador = null;
    private BufferedImage imgOficial = null;
    
    private int colaReg;
    private int colaNS;
    private int atendiendoReg;
    private int atendiendoNS;
    private ArrayList<Thread> hilosEsperandoReg;
    private ArrayList<Thread> hilosEsperandoNS;
    private ArrayList<Thread> hilosAtendiendoReg;
    private ArrayList<Thread> hilosAtendiendoNS;
    
    public Canvas(int ANCHO, int ALTO) {
        
        colaReg = 0;
        colaNS = 0;
        atendiendoReg = 0;
        atendiendoNS = 0;
        
        hilosEsperandoReg = new ArrayList<Thread>();
        hilosEsperandoNS = new ArrayList<Thread>();
        hilosAtendiendoReg = new ArrayList<Thread>();
        hilosAtendiendoNS = new ArrayList<Thread>();
        
        setSize(ANCHO, ALTO);
        setVisible(true);
        
        setBackground(Color.yellow);
        
        // Inicialización de las imágenes que serán dibujadas en el canvas
        try {
            imgCliente = ImageIO.read(new File("src/Images/ImagenCliente.png"));
            imgRecepcionista = ImageIO.read(new File("src/Images/ImagenRecep.png"));
            imgAtendiendo = ImageIO.read(new File("src/Images/ImagenAtendiendo.png"));
            imgRegistrador = ImageIO.read(new File("src/Images/Registrador.png"));
            imgOficial = ImageIO.read(new File("src/Images/Oficial.png"));            
        } catch (IOException ex) {
            Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void paint(Graphics g) {
        
        g.drawString("Notas Simples", 560, 130);
        g.drawString("Registros", 560, 380);
        g.drawImage(imgRecepcionista, 560, 140, null);
        g.drawImage(imgRecepcionista, 560, 390, null);
        
        for (int i = 0; i < colaNS; i++) {
            g.drawImage(imgCliente, 500-(i*45), 150, null);
            g.drawString(String.valueOf(hilosEsperandoNS.get(i).getId()), 510-(i*45), 220);
        }
        for (int i = 0; i < colaReg; i++) {
            g.drawImage(imgCliente, 500-(i*45), 400, null);
            g.drawString(String.valueOf(hilosEsperandoReg.get(i).getId()), 510-(i*45), 480);
        }   
        
        for (int i = 0; i < atendiendoNS; i++) {
            g.drawImage(imgAtendiendo, 660+(i*35), 150, null);
            g.drawImage(imgOficial, 660+(i*35), 50, null);
            g.drawString("Oficial", 655+(i*35), 135);
            g.drawString(String.valueOf(hilosAtendiendoNS.get(i).getId()), 665+(i*35), 230);
        }
        for (int i = 0; i < atendiendoReg; i++) {
            g.drawImage(imgAtendiendo, 660+(i*35), 400, null);
            g.drawImage(imgOficial, 660+(i*35), 300, null);
            g.drawImage(imgRegistrador, 695+(i*35), 300, null);
            g.drawString("Oficial", 655+(i*35), 385);
            g.drawString("Registrador", 700, 385);
            g.drawString(String.valueOf(hilosAtendiendoReg.get(i).getId()), 665+(i*35), 480);
        }
    }
    
    public void esperaNS(Thread h) {
        colaNS++;
        hilosEsperandoNS.add(h);
    }
    
    public void pasaNS(Thread h) {
        colaNS--;
        hilosEsperandoNS.remove(h);
    }
    
    public void esperaReg(Thread h) {
        colaReg++;
        hilosEsperandoReg.add(h);
    }
    
    public void pasaReg(Thread h) {
        colaReg--;
        hilosEsperandoReg.remove(h);
    }
    
    public void atiendeNS(Thread h) {
        atendiendoNS++;
        hilosAtendiendoNS.add(h);
    }
    
    public void notaS_atendido(Thread h) {
        atendiendoNS--;
        hilosAtendiendoNS.remove(h);
    }
    
    public void atiendeReg(Thread h) {
        atendiendoReg++;
        hilosAtendiendoReg.add(h);
    }
    
    public void reg_atendido(Thread h) {
        atendiendoReg--;
        hilosAtendiendoReg.remove(h);
    }
}
