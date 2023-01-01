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
public interface Proceso {
    public abstract boolean esFinal(Object estado);
    public abstract boolean reconocer(String cadena);
    @Override
    public abstract String toString();  // muestra las transiciones y estados finales
}
