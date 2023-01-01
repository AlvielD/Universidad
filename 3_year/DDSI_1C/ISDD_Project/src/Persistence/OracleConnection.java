package Persistence;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Contains the methods to control the connection and disconnection to the database
 * @author Álvaro Esteban Muñoz
 */
public class OracleConnection {
    
    protected Connection conn;
    
    public OracleConnection() throws Exception {
        this.conn = null;
        try {	        
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@172.17.20.75:1521:rabida", "ISDD_014", "ISDD_014");
        }
	catch (ClassNotFoundException | SQLException e)  {
            throw e;    
	}      
    }    

    /**
     * Disconnects from the database
     * @throws Exception 
     */
    public void disconnect() throws Exception {
        try {
            conn.close();        
        } 
        catch (Exception e) {
            throw e;
        }       
    }

    /**  
     *  Starts transaction
     */
    public void startTransaction()  {
        
        try {
            conn.setAutoCommit(false);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
    }
    
    /** 
     *  End the transaction with commit
     */			
    public void endTransaccionCommit() {
        try {
            conn.commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
    }
    
    /**  
     *  End the transaction with rollback 
     */			
    public void endfTransaccionRollback() {
        try {
            conn.rollback();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
    }
}
