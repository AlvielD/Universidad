/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APP;

/**
 *
 * @author AburoSenpai y Alvi
 */
class TransicionesAFND implements Cloneable{
    
    private final Object estado_origen;
    private Object[] estado_destino;
    private final Object simbolo;
    
    /**
     * Crea una transición a partir de los parámetros pasados
     * @param e1 estado origen de la transición
     * @param simbolo input necesaria para cambiar al estado destino
     * @param e2 estado destino de la transición
     */
    public TransicionesAFND(Object e1, Object simbolo, Object[] e2) {
        estado_origen = e1;
        estado_destino = e2;
        this.simbolo = simbolo;
    }
    
    /**
     * 
     * @return el estado origen de la transición
     */
    public Object getEstadoO() {
        return estado_origen;
    }
    
    /**
     * 
     * @return el estado destino de la transición
     */
    public Object[] getEstadoD() {
        return estado_destino;
    }

    /**
     * 
     * @return el input necesario para pasar de e1 a e2
     */
    public Object getSimbolo() {
        return simbolo;
    }
    
    /**
     * 
     * @return el objeto en forma de String
     */
    public String verTransicion() {
        
        String transicion;
        
        transicion = estado_origen + " --> " + simbolo + " --> ";
        
        for (int i = 0; i < estado_destino.length; i++) {
            transicion += ((estado_destino[i]) + " "); 
        }
        
        return transicion;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Object clone() {
        
        TransicionesAFND obj = null;
        try {
            obj = (TransicionesAFND)super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("No se puede duplicar");
        }
        
        obj.estado_destino = this.estado_destino.clone();
        
        return obj;
    }
}