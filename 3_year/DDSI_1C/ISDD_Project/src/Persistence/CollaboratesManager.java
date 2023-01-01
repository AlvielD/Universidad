package Persistence;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import oracle.jdbc.internal.OracleTypes;

/**
 * Contains the methods to deal with the database querys
 * @author Álvaro Esteban Muñoz
 */
public class CollaboratesManager {
    
    // Creating an object of class "OracleConnection"
    OracleConnection conexion = null;
    
    // Creating a PreparedStatement as an atribute for ExpertManager to use it in several methods
    PreparedStatement ps = null;
    
    /**
     * Implemements operations over the table PolicialCASE
     * @param c 
     */
    public CollaboratesManager(OracleConnection c) {
        conexion = c;
    }
    
    /**
     * Check if the collaboration exists in table COLLABORATES
     * @param codExpert
     * @param codCaso
     * @return 
     */
    public boolean existCollaboration(String codExpert, String codCaso) {
        
        boolean exists = false;
        
        try {
            String sql = "SELECT COUNT (*) FROM COLLABORATES WHERE EXISTS (SELECT CODEXPERT, CODCASO FROM COLLABORATES WHERE CODEXPERT=? AND CODCASO=?)";
            PreparedStatement stmt = conexion.conn.prepareStatement(sql);

            stmt.setString(1, codExpert);
            stmt.setString(2, codCaso);

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
     * Insert a new collaboration
     * @param col
     * @return 
     */
    public boolean insertCollaboration(Collaborates col) {
        
        boolean succed;
        
        try {
            conexion.startTransaction();
            Statement stmt = conexion.conn.createStatement();
            String sql = "INSERT INTO COLLABORATES (CODEXPERT,CODCASO,DATEC,DESCRIPCION) " + "VALUES "
                    + "('" + col.getCodExperto()
                    + "' ,'" + col.getCodCaso()
                    + "' ,'" + col.getFecha()
                    + "' ,'" + col.getDescripcionColaboracion() + "')";

            succed = stmt.execute(sql);
            stmt.close();
            succed = true;
            conexion.endTransaccionCommit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
            succed = false;
            conexion.endfTransaccionRollback();
        }
        
        return succed;       
    }
    
    /**
     * Delete a collaboration from the database
     * @param ce
     * @param cc
     * @return 
     */
    public boolean deleteCollaboration(String ce, String cc) {
        
        boolean succed;
        
        try {
            conexion.startTransaction();
            Statement stmt = conexion.conn.createStatement();
            String sql = "DELETE FROM COLLABORATES WHERE "
                    + "CODEXPERT = '" + ce
                    + "' AND CODCASO = '" + cc
                    + "'";

            succed = stmt.execute(sql);
            stmt.close();
            succed = true;
            conexion.endTransaccionCommit();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
            succed = false;
            conexion.endfTransaccionRollback();
        }
        
        return succed;  
    }
    
    /**
     * Gets the description of the collaborations
     * @return a resultSet with the description of the collaborations 
     */
    public ResultSet getDescExp() {
        
        ResultSet auxSet = null;
        
        try {
            String sql = "SELECT DISTINCT NAME, DESCRIPCION FROM COLLABORATES INNER JOIN EXPERT ON EXPERT.CODEXPERT=COLLABORATES.CODEXPERT";
            Statement stmt = conexion.conn.createStatement();
            auxSet = stmt.executeQuery(sql);

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was a connection error");
        }
        
        return auxSet; 
    }
    
    /**
     * List the collaborators of a case given
     * @param codCase the code of the case
     * @return a resultset with the experts that collaborates on that case
     */
    public ResultSet listCollaboratorsByCase (String codCase) {
        
        String sql = "{call pColaboratesCase(?, ?)}";
        ResultSet rs = null;
        
        try {
            CallableStatement call = conexion.conn.prepareCall(sql);
            call.setString(1, codCase);
            call.registerOutParameter(2, OracleTypes.CURSOR);
            call.executeUpdate();
            rs = (ResultSet)call.getObject(2);
        } catch (Exception ex) {
            Logger.getLogger(CollaboratesManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
}
