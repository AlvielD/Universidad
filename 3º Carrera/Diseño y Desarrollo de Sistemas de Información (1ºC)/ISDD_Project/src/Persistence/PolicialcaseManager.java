package Persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Contains the methods to deal with the database querys
 * @author Álvaro Esteban Muñoz
 */
public class PolicialcaseManager {
    
    // Creating an object of class "OracleConnection"
    OracleConnection conexion = null;
    
    // Creating a PreparedStatement as an atribute for ExpertManager to use it in several methods
    PreparedStatement ps = null;
    
    /**
     * Implemements operations over the table PolicialCASE
     * @param c connection with Oracle
     */
    public PolicialcaseManager(OracleConnection c) {
        conexion = c;
    }
	 
    /**
     * Check if the Case exists in the POLICIALCASE table
     * @param codCase id  of Policialcase to find
     * @return if the case exists or not 
     */
    public boolean existCase(String codCase) {
        
        boolean exists = false;
        
        try {
            String sql = "SELECT COUNT (*) FROM POLICIALCASE WHERE CODCASO=?";
            PreparedStatement stmt = conexion.conn.prepareStatement(sql);

            stmt.setString(1, codCase);

            ResultSet auxSet = stmt.executeQuery();
            auxSet.next();

            if (auxSet.getInt(1) != 0)
                exists = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
        
        return exists;
    }
    
    /**
     * Insert a Policialcase into PolicialCase table
     * @param pc Policialcase to insertar
     * @return if the case has been inserted succesfully 
     */
    public boolean insertCase (Policialcase pc) {
        
        boolean succed;
        
        try {
            Statement stmt = conexion.conn.createStatement();
            String sql = "INSERT INTO POLICIALCASE (CODCASO,NAME,STARTDATE,ENDDATE) VALUES "
                    + "('" + pc.getCodCaso()
                    + "' ,'" + pc.getNombre()
                    + "' ,'" + pc.getFechaInicio()
                    + "' ,'" + pc.getFechaFin() + "')";
        
            stmt.execute(sql);
            stmt.close();
            succed = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
            succed = false;
        }
        
        return succed;
    }

    /**
     * 
     * @return an ArrayList of the cases in the database 
     */
    public ArrayList<Policialcase> casesList() {
             
        ArrayList<Policialcase> result = new ArrayList<>();
        
        try {
            Statement stmt = conexion.conn.createStatement();
            String sql = "SELECT * FROM POLICIALCASE";  

            ResultSet auxSet = stmt.executeQuery(sql);

            while (auxSet.next()) 
                result.add(new Policialcase(auxSet.getString(1), auxSet.getString(2), auxSet.getString(3), auxSet.getNString(4)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }

        return result;
    }

    /**
     * Removes a case from the database
     * @param codCaso
     * @return if the case has been removed succesfully
     */
    public boolean borraCaso(String codCaso){
        
        boolean succed;
        
        try { 
            Statement stmt = conexion.conn.createStatement();
            String sql = "DELETE FROM POLICIALCASE WHERE CODCASO = '" + codCaso + "'";

            stmt.execute(sql);
            stmt.close();
            succed = true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
            succed = false;
        } 
        
        return succed;  
    }
}

	 


