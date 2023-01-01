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

class TransicionesLambda implements Cloneable{
    
    private final Object estado_origen;
    private Object[] estado_destino;
  
    public TransicionesLambda(Object e1, Object[] e2) {
        estado_origen = e1;
        estado_destino = e2;
    }
    
    public Object getEstadoO() {
        return estado_origen;
    }
    
    public Object[] getEstadoD() {
        return estado_destino;
    }
    
    public String verTransicion() {
        
        String transicion;
        
        transicion = estado_origen + " --> λ --> ";
        
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
        
        TransicionesLambda obj = null;
        try {
            obj = (TransicionesLambda)super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("No se puede duplicar");
        }
        
        obj.estado_destino = this.estado_destino.clone();
        
        return obj;
    }
}