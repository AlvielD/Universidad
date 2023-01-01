package Persistence;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;
import java.sql.Types;
import javax.swing.JOptionPane;

/**
 * Contains the methods to deal with the database querys
 * @author Álvaro Esteban Muñoz
 */
public class ExpertManager {
    
    // Creating an object of class "OracleConnection"
    OracleConnection conexion = null;
    
    // Creating a PreparedStatement as an atribute for ExpertManager to use it in several methods
    PreparedStatement ps = null;
    
    /**
     * Implements operations over Expert table
     * @param c  OracleConnection
     */    
    public ExpertManager(OracleConnection c) {
        conexion = c;
    }
    
    /**
     * REturnn a list with all the experts with a nationality
     * @param country
     * @throws SQLException 
     * @return ArrayList(expert)
     */
    public ArrayList<Expert> expertListByCountry(String country) throws SQLException {
        
        ArrayList<Expert> result = new ArrayList<>(); 
        
        try {
            Statement stmt = conexion.conn.createStatement();
            String sql = "SELECT CODEXPERT, NAME, COUNTRY, SEX, SPECIALISM FROM EXPERT WHERE COUNTRY LIKE '"
                    + country + "'";
 
            ResultSet auxSet = stmt.executeQuery(sql);
            while (auxSet.next()) 
                result.add(new Expert(auxSet.getString(1), auxSet.getString(2), auxSet.getString(3), auxSet.getNString(4), auxSet.getString(5)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }

        return result;
    }
    
    /**
     * Check if the expert exists
     * @param codExpert
     * @return if the expert exists
     */
    public boolean expertExists (String codExpert) {
        
        boolean exists = false;
        
        try {
            String sql = "SELECT COUNT (*) FROM EXPERT WHERE CODEXPERT=?";
            PreparedStatement stmt = conexion.conn.prepareStatement(sql);

            stmt.setString(1, codExpert);

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
     * Insert a new Expert
     * @param exp
     * @return if the expert has been inserted succesfully 
     */
    public boolean insertExpert(Expert exp) {
        
        boolean succed;
        
        try { 
            Statement stmt = conexion.conn.createStatement();
            String sql = "INSERT INTO EXPERT (CODEXPERT,NAME,COUNTRY,SEX,SPECIALISM) " + "VALUES "
                    + "('" + exp.getCodEXPERT()
                    + "' ,'" + exp.getName()
                    + "' ,'" + exp.getCountry()
                    + "' ,'" + exp.getSex()
                    + "' ,'" + exp.getSpecialism() + "')";

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
     * Removes an expert from the database
     * @param codExp
     * @return if the expert has been removed succesfully 
     */
    public boolean removeExpert(String codExp) {
        
        boolean succed;
        
        try { 
            Statement stmt = conexion.conn.createStatement();
            String sql = "DELETE FROM EXPERT WHERE CODEXPERT = '" + codExp + "'";

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
     * @return an arrayList with all the experts in the database 
     */
    public ArrayList<Expert> expertList() {
        
        ArrayList<Expert> result = new ArrayList<>();  
        
        try {
            Statement stmt = conexion.conn.createStatement();
            String sql = "SELECT * FROM EXPERT";

            ResultSet auxSet = stmt.executeQuery(sql);
            while (auxSet.next()) 
                result.add(new Expert(auxSet.getString(1), auxSet.getString(2), auxSet.getString(3), auxSet.getNString(4), auxSet.getString(5)));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }

        return result;
    }

    /**
     * 
     * @return an ArrayList with all the countries of the database 
     */
    public ArrayList<String> countriesList() {

        ArrayList<String> result = new ArrayList<>();
        
        try {
            Statement stmt = conexion.conn.createStatement();
            String sql = "SELECT DISTINCT COUNTRY FROM EXPERT";
            
            ResultSet auxSet = stmt.executeQuery(sql);

            while (auxSet.next()) 
                result.add(auxSet.getString(1));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
        
        return result;
    }
    
    /**
     * 
     * @param selected_gender
     * @return the number of experts with the selected gender within the database
     */
    public int sExpert(char selected_gender) {
        
        int num_gender = -1;
        
        try {
            CallableStatement stmt = null;  // Creation of the CallableStatement object
            String sql = "{ ? = call fExperts(?) }";
            stmt = conexion.conn.prepareCall(sql);
            stmt.registerOutParameter(1, Types.INTEGER);    // Registration of the output
            stmt.setString(2, String.valueOf(selected_gender));   // Indication of the other parameters
            stmt.execute();     // Executation of the function on the DB
            num_gender = stmt.getInt(1);    // Store the result on a variable
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
        
        return num_gender;
    }
}