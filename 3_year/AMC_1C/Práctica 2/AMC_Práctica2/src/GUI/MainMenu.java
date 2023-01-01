/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import APP.AFD;
import APP.AFND;
import APP.FicheroCargado;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Aburo Senpai
 */
public class MainMenu extends JFrame implements ActionListener {

    protected JButton boton1;
    protected JButton boton2;
    protected JMenuItem item1;
    protected JMenuItem item2;
    protected JRadioButtonMenuItem item3;
    protected JRadioButtonMenuItem item4;
    protected JMenuItem item5;
    protected JMenuItem item6;
    protected ButtonGroup menuopciones;
    protected ButtonGroup menuopciones2;
    protected JRadioButton opcion1;
    protected JRadioButton opcion2;
    protected FicheroCargado fichero;
    protected AFD automataafd;
    protected AFND automataafnd;
    protected JFrame introducir;
    protected JFrame instrucciones;
    protected JTextArea escribir;
    protected JTextArea transiciones;
    protected JTextField transicion;
    protected JLabel fichActual = new JLabel();
    protected JPanel contenedor = new JPanel();

    /**
     *
     */
    public MainMenu() {

        setSize(400, 400);
        setResizable(false);
        setTitle("Práctica 2 AMC");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(null);
        JMenuBar mBar = new JMenuBar();
        JMenu menu1 = new JMenu("Archivo");
        JMenu menu2 = new JMenu("Simulación");
        JMenu menu3 = new JMenu("Ver");
        JMenu menu4 = new JMenu("Ayuda");

        transicion = new JTextField();
        transicion.setVisible(false);
        transicion.setLocation(135, 245);
        transicion.setSize(120, 25);

        transiciones = new JTextArea();
        transiciones.setSize(60, 40);
        transiciones.setRows(4);
        transiciones.setColumns(10);

        contenedor.setSize(120, 80);
        contenedor.setLocation(135, 240);
        contenedor.setVisible(true);
        contenedor.add(transiciones);
        contenedor.add(new JScrollPane(transiciones));

        item1 = new JMenuItem("Importar");
        item1.setVisible(true);
        item2 = new JMenuItem("Introducir");
        item2.setVisible(true);
        item3 = new JRadioButtonMenuItem("Simular paso a paso");
        item3.setVisible(true);
        item4 = new JRadioButtonMenuItem("Simular de una vez");
        item4.setVisible(true);
        item5 = new JMenuItem("Ver fichero cargado");
        item5.setVisible(true);
        item6 = new JMenuItem("Ver Javadoc");
        item6.setVisible(true);

        mBar.setVisible(true);
        mBar.setSize(getSize().width, 23);
        menu1.setVisible(true);

        menu1.add(item1);
        menu1.add(item2);
        menu2.add(item4);
        menu2.add(item3);
        menu3.add(item5);
        menu4.add(item6);

        mBar.add(menu1);
        mBar.add(menu2);
        mBar.add(menu3);
        mBar.add(menu4);

        item1.addActionListener(this);
        item2.addActionListener(this);
        item3.addActionListener(this);
        item4.addActionListener(this);
        item5.addActionListener(this);
        item6.addActionListener(this);

        menuopciones2 = new ButtonGroup();

        menuopciones2.add(item3);
        menuopciones2.add(item4);

        menuopciones2.setSelected(item4.getModel(), true);

        boton1 = new JButton("Simular");
        boton1.setSize(100, 50);
        boton1.setLocation(145, 175);
        boton1.addActionListener(this);
        boton1.setVisible(true);

        opcion1 = new JRadioButton("AFD");
        opcion2 = new JRadioButton("AFND");
        menuopciones = new ButtonGroup();

        menuopciones.add(opcion1);
        menuopciones.add(opcion2);
        menuopciones.setSelected(opcion1.getModel(), true);

        opcion1.setVisible(true);
        opcion2.setVisible(true);
        opcion1.setBounds(10, 10, 50, 15);
        opcion2.setBounds(10, 10, 60, 15);

        opcion1.setLocation(260, 183);
        opcion2.setLocation(260, 203);

        
        
        fichActual.setVisible(true);
        fichActual.setSize(400, 20);
        fichActual.setLocation(0, 235);
        fichActual.setVerticalTextPosition((int) CENTER_ALIGNMENT);
        fichActual.setVerticalAlignment((int) CENTER_ALIGNMENT);
        
        JLabel imagen = new JLabel();
        imagen.setIcon(new ImageIcon("src/IMG/Icono.png"));
        imagen.setVisible(true);
        
        JPanel panel=new JPanel();
        panel.setSize(200,150);
        panel.setLocation(95,30);
        panel.setVisible(true);
        panel.add(imagen);
        
        add(panel);
        add(transicion);
        add(contenedor);
        add(fichActual);
        add(opcion1);
        add(opcion2);
        add(boton1);
        add(mBar);
        setVisible(true);

    }

    /**
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == boton1) {

            // Hilo h1 = new Hilo();
            // h1.start();
            Thread h1 = new Thread() {
                @Override
                public void run() {
                    try {
                        if (fichero != null) {
                            String[] cadenas;
                            String texto;
                            String mostrarcadenas;

                            texto = transiciones.getText();
                            cadenas = texto.split("\n");

                            if (menuopciones2.isSelected(item4.getModel())) {

                                if (menuopciones.isSelected(opcion1.getModel())) {
                                    automataafd = new AFD(fichero);
                                    JFrame simulacion = new JFrame("Simulación");
                                    JTextPane simular = new JTextPane();

                                    mostrarcadenas = "AUTÓMATA CARGADO:\n" + automataafd.toString() + "\n\nRESULTADO/S SIMULACION:\n\n";
                                    for (int i = 0; i < cadenas.length; i++) {
                                        mostrarcadenas += '"' + cadenas[i] + '"' + " --> " + String.valueOf(automataafd.reconocer(cadenas[i])).toUpperCase() + "\n";
                                    }

                                    simular.setText(mostrarcadenas);

                                    simular.setEditable(false);
                                    simulacion.setSize(400, 450);
                                    simulacion.setResizable(false);
                                    simulacion.setVisible(true);
                                    simulacion.add(simular);
                                    simulacion.setLocation(getLocation().x - 385, getLocation().y);
                                    simulacion.add(new JScrollPane(simular));

                                } else {
                                    automataafnd = new AFND(fichero);
                                    JFrame simulacion = new JFrame("Simulación");
                                    JTextPane simular = new JTextPane();

                                    mostrarcadenas = "AUTÓMATA CARGADO:\n" + automataafnd.toString() + "\n\nRESULTADO/S SIMULACION:\n\n";
                                    for (int i = 0; i < cadenas.length; i++) {
                                        mostrarcadenas += '"' + cadenas[i] + '"' + " --> " + String.valueOf(automataafnd.reconocer(cadenas[i])).toUpperCase() + "\n";
                                    }

                                    simular.setText(mostrarcadenas);

                                    simular.setEditable(false);
                                    simulacion.setSize(400, 450);
                                    simulacion.setResizable(false);
                                    simulacion.setVisible(true);
                                    simulacion.add(simular);
                                    simulacion.setLocation(getLocation().x - 385, getLocation().y);
                                    simulacion.add(new JScrollPane(simular));
                                }
                            } else {

                                if (menuopciones.isSelected(opcion1.getModel())) {
                                    automataafd = new AFD(fichero);
                                    JFrame simulacion = new JFrame("Simulación paso a paso");
                                    JTextPane simular = new JTextPane();

                                    mostrarcadenas = "AUTÓMATA CARGADO:\n" + automataafd.toString() + "\n\nRESULTADO/S SIMULACION:\n\n";
                                    simular.setText(mostrarcadenas);

                                    String total = automataafd.reconocer_por_pasos(transicion.getText());
                                    cadenas = total.split("\n");

                                    simular.setEditable(false);
                                    simulacion.setSize(400, 450);
                                    simulacion.setResizable(false);
                                    simulacion.setVisible(true);
                                    simulacion.add(simular);
                                    simulacion.setLocation(getLocation().x - 385, getLocation().y);
                                    simulacion.add(new JScrollPane(simular));

                                    for (int i = 0; i < cadenas.length; i++) {
                                        mostrarcadenas += cadenas[i] + "\n";
                                        Thread.sleep(2000);
                                        simular.setText(mostrarcadenas);
                                    }

                                } else {
                                    automataafnd = new AFND(fichero);
                                    JFrame simulacion = new JFrame("Simulación");
                                    JTextPane simular = new JTextPane();

                                    mostrarcadenas = "AUTÓMATA CARGADO:\n" + automataafnd.toString() + "\n\nRESULTADO/S SIMULACION:\n\n";
                                    simular.setText(mostrarcadenas);

                                    String total = automataafnd.reconocer_por_pasos(transicion.getText());
                                    cadenas = total.split("\n");

                                    simular.setEditable(false);
                                    simulacion.setSize(400, 450);
                                    simulacion.setResizable(false);
                                    simulacion.setVisible(true);
                                    simulacion.add(simular);
                                    simulacion.setLocation(getLocation().x - 385, getLocation().y);
                                    simulacion.add(new JScrollPane(simular));

                                    for (int i = 0; i < cadenas.length; i++) {
                                        mostrarcadenas += cadenas[i] + "\n";
                                        Thread.sleep(2000);
                                        simular.setText(mostrarcadenas);
                                    }
                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No se ha cargado ningun automata", "Alerta", 2);

                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Error en la trata del fichero", "Alerta", 2);
                    }
                }
            };
            h1.start();

        }
        if (e.getSource() == boton2) {
            boton2ActionListener();
        }
        if (e.getSource() == item1) {
            item1ActionListener();
        }
        if (e.getSource() == item2) {
            item2ActionListener();
        }
        if (e.getSource() == item3) {
            item3ActionListener();
        }
        if (e.getSource() == item4) {
            item4ActionListener();
        }
        if (e.getSource() == item5) {
            item5ActionListener();
        }
        if (e.getSource() == item6) {
            item6ActionListener();
        }

    }

    /**
     *
     */
    public void boton2ActionListener() {

        String nombreFich = JOptionPane.showInputDialog(escribir, "Introduzca nombre para el fichero a generar: ", "Generar fichero", 1);
        if (nombreFich == null || nombreFich.equals("")) {
            JOptionPane.showMessageDialog(escribir, "No se ha generado ningun Fichero, escriba un nombre", "Alerta", 2);
        } else {
            fichero = FicheroCargado.generarFichero(nombreFich, escribir.getText());
            introducir.dispose();
            instrucciones.dispose();
            JOptionPane.showMessageDialog(this, "Fichero generado con exito", "Alerta", 1);
            fichActual.setText("Fichero Cargado: " + nombreFich + ".txt");

        }
    }

    /**
     *
     */
    public void item1ActionListener() {

        JFileChooser contenedor = new JFileChooser();
        contenedor.setDialogTitle("Importar");
        contenedor.setVisible(true);
        int seleccion = contenedor.showOpenDialog(this);
        if (seleccion == JFileChooser.APPROVE_OPTION) {
            fichero = new FicheroCargado(contenedor.getSelectedFile().getAbsolutePath());
            JOptionPane.showMessageDialog(this, "Se ha cargado correctamente!!");

        } else {
            JOptionPane.showMessageDialog(this, "Importacion de fichero cancelada");
        }

    }

    /**
     *
     */
    public void item2ActionListener() {
        introducir = new JFrame("Introducir");
        instrucciones = new JFrame("Instrucciones");
        //JTextPane escribir = new JTextPane();
        escribir = new JTextArea();
        JTextPane instruccion = new JTextPane();
        JPanel contenedor = new JPanel();
        JPanel contenedorb = new JPanel();
        boton2 = new JButton("Cargar");

        escribir.setText("ESTADOS: \nINICIAL: \nFINALES: \nTRANSICIONES: \nTRANSICIONES LAMBDA:(Borrar si va a ser AFD) \nFIN");
        escribir.setSize(400, 300);
        escribir.setRows(18);
        escribir.setColumns(33);

        instruccion.setText("Intrucciones: \n\nDebera escribir por orden: \n\nESTADOS:' A B C D'\n\nDejar un espacio antes de escribir los estados y separar los estados por espacios.\n\nINICIAL:' A'\n\nLo mismo que en los ESTADOS:\n\nFINALES:' A C'\n\nLo mismo que en los ESTADOS:\n\nTRANSICIONES:\n' A 1 B C'\n\nDebera dejar un salto de linea y escribir las transiciones despues de un espacio.\n\nTRANSICIONES LAMBDA: \n' a b c'\n\nEn el caso de que hubiera, siguen las mismas reglas que las TRANSICIONES:\n\nFIN\n\nPondremos FIN para finalizar.");

        instruccion.setEditable(false);

        boton2.setSize(100, 100);
        boton2.setVisible(true);
        boton2.addActionListener(this);

        contenedorb.setSize(100, 100);
        contenedorb.setLocation(145, 300);
        contenedorb.setVisible(true);
        contenedorb.add(boton2);

        contenedor.setSize(400, 300);
        contenedor.setVisible(true);
        contenedor.add(escribir);
        contenedor.add(new JScrollPane(escribir));
        contenedor.setLocation(-8, 0);

        instrucciones.setSize(400, 560);
        instrucciones.setResizable(false);
        instrucciones.setVisible(true);
        instrucciones.add(instruccion);
        instrucciones.setLocation(getLocation().x - 385, getLocation().y);

        introducir.setSize(400, 400);
        introducir.setResizable(false);
        introducir.setLocation(getLocation().x + 385, getLocation().y);
        introducir.setVisible(true);
        introducir.setLayout(null);
        introducir.add(contenedor);
        introducir.add(contenedorb);

    }

    /**
     *
     */
    public void item3ActionListener() { //paso a paso
        contenedor.setVisible(false);
        transicion.setText("");
        transicion.setVisible(true);
    }

    public void item4ActionListener() {//total
        transicion.setVisible(false);
        transiciones.setText("");
        contenedor.setVisible(true);

    }

    public void item5ActionListener() {
        if (fichero != null) {
            JFrame visualizacion = new JFrame("Visualización");
            JTextPane visual = new JTextPane();

            visual.setText(fichero.LeeFichero());

            visual.setEditable(false);
            visualizacion.setSize(400, 450);
            visualizacion.setResizable(false);
            visualizacion.setVisible(true);
            visualizacion.add(visual);
            visualizacion.setLocation(getLocation().x - 385, getLocation().y);
            visualizacion.add(new JScrollPane(visual));
        } else {
            JOptionPane.showInternalMessageDialog(null, "No se ha cargado ningun automata", "Alerta", 2);
        }

    }

    public void item6ActionListener() {
        try {
            File objetofile = new File("dist/javadoc/index.html");
            Desktop.getDesktop().open(objetofile);
        } catch (IOException e) {
            System.out.println("No se ha podido abrir el navegador");
        }
    }

}
