/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IRemoto;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Usuario
 */
public interface IRemoto extends Remote {

    //******************************//
    // Definición de los protocolos //
    //******************************//
    /**
     * Protocolo de entrada para la realización de una nota simple
     * @throws java.rmi.RemoteException
     */
    public void entraNS() throws RemoteException;

    /**
     * Protocolo de entrada para un registro
     * @throws java.rmi.RemoteException
     */
    public void entraReg() throws RemoteException;

    /**
     * Protocolo de salida para la realización de una nota simple
     * @throws java.rmi.RemoteException
     */
    public void saleNS() throws RemoteException;

    /**
     * Protocolo de salida para un registro
     * @throws java.rmi.RemoteException
     */
    public void saleReg() throws RemoteException;
    
}
