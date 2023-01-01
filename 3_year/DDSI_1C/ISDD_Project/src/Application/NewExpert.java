/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Application;

import Persistence.Expert;
import Persistence.ExpertManager;
import Persistence.OracleConnection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Contains the view and all the methods to add and remove experts from the DB
 * @author Álvaro Esteban Muñoz
 */
public class NewExpert extends javax.swing.JFrame {

    private final OracleConnection c;   // The conecction to the DB
    private ExpertManager em = null;    // The manager to the DB
    protected final DefaultTableModel tmodel = new DefaultTableModel(); // The table of experts to be shown
    
    /**
     * Creates new form AddExpertsMenu
     */
    public NewExpert(OracleConnection c) {
        
        initComponents();
        this.c = c;
        em = new ExpertManager(c);
        this.setTitle("Experts");    
        
        // Inicialize the table with the values of the DB
        setColumnNames();
        
        try{
            // Inicialize the tuples
            viewExperts();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            this.dispose();
        }
    }
    
    /**
     * Set the name of the columns of the table
     */
    private void setColumnNames() {
        ExpertsTable.setModel(tmodel);
        String[] columns = {"Code", "Name", "Country", "Sex", "Specialism"};
        tmodel.setColumnIdentifiers(columns);
                
        ExpertsTable.getTableHeader().setResizingAllowed(false);
        
        ExpertsTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        ExpertsTable.getColumnModel().getColumn(1).setPreferredWidth(140);
        ExpertsTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        ExpertsTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        ExpertsTable.getColumnModel().getColumn(4).setPreferredWidth(122);
    }
    
    /**
     * Takes the experts of the database and passes it to the table
     * @throws SQLException 
     */
    private void viewExperts() throws SQLException {
        ArrayList<Expert> listExp = em.expertList();
        fillExpertsTable(listExp);
    }
    
    /**
     * Fills the table with the experts
     * @param listExp 
     */
    private void fillExpertsTable (ArrayList<Expert> listExp) {
        Object[] columna = new Object[5];
        int numRegistros = listExp.size();
        int numRows = tmodel.getRowCount();
        
        // Removes all the tuples of the table
        for (int i = numRows-1; i >= 0; i--) {
           tmodel.removeRow(i);
        }
        
        // Fills the table with the passed arrayList
        for (int i = 0; i < numRegistros; i++) {
            columna[0] = listExp.get(i).getCodEXPERT();
            columna[1] = listExp.get(i).getName();
            columna[2] = listExp.get(i).getCountry();
            columna[3] = listExp.get(i).getSex();
            columna[4] = listExp.get(i).getSpecialism();
            tmodel.addRow(columna);
        } 
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        ExpertsTable = new javax.swing.JTable();
        CodeField = new javax.swing.JTextField();
        NameField = new javax.swing.JTextField();
        CountryField = new javax.swing.JTextField();
        SexField = new javax.swing.JTextField();
        SpecialismField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        NewButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        RefreshButton = new javax.swing.JButton();
        SaveButton = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentHidden(java.awt.event.ComponentEvent evt) {
                formComponentHidden(evt);
            }
        });

        ExpertsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CodExpert", "Name", "Country", "Sex", "Specialism"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(ExpertsTable);

        CodeField.setToolTipText("");

        jLabel1.setText("CodExpert");

        jLabel2.setText("Name");

        jLabel3.setText("Country");

        jLabel4.setText("Sex");

        jLabel6.setText("Specialism");

        NewButton.setText("New");
        NewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewButtonActionPerformed(evt);
            }
        });

        DeleteButton.setText("Delete");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        RefreshButton.setText("Refresh");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NameField)
                                    .addComponent(CodeField)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(NewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(RefreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(SaveButton))
                                    .addComponent(SpecialismField)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(SexField)
                                    .addComponent(CountryField))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(CodeField)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(NameField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CountryField)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(SexField, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SpecialismField)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(NewButton)
                    .addComponent(DeleteButton)
                    .addComponent(RefreshButton)
                    .addComponent(SaveButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Performs the action of adding an expert to the table
     * @param evt 
     */
    private void NewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewButtonActionPerformed
        
        if (CodeField.getText().equals("") ||
            NameField.getText().equals("") ||
            CountryField.getText().equals("") ||
            SexField.getText().equals("") ||
            SpecialismField.getText().equals("")) {
            
            JOptionPane.showMessageDialog(null ,"ERROR: All fields must be filled");
        } else {
            
            // Create an auxiliar expert
            Object[] newRow = new Object[5];
            Expert auxEx = new Expert();
            
            newRow[0] = CodeField.getText();
            newRow[1] = NameField.getText();
            newRow[2] = CountryField.getText();
            newRow[3] = SexField.getText();
            newRow[4] = SpecialismField.getText();

            tmodel.addRow(newRow);
            
            auxEx.setCodEXPERT(CodeField.getText());
            auxEx.setName(NameField.getText());
            auxEx.setCountry(CountryField.getText());
            auxEx.setSex(SexField.getText());
            auxEx.setSpecialism(SpecialismField.getText());
            
            em.insertExpert(auxEx);
        }
    }//GEN-LAST:event_NewButtonActionPerformed

    /**
     * Performs the action of saving all the changes made to the table to the DB
     * @param evt 
     */
    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        c.endTransaccionCommit();
    }//GEN-LAST:event_SaveButtonActionPerformed

    /**
     * Refresh the data of the table
     * @param evt 
     */
    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        try {
            // If the changes aren't saved, we rollback
            c.endfTransaccionRollback();
            viewExperts();
        } catch (SQLException ex) {
            Logger.getLogger(NewExpert.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.dispose();
        }
    }//GEN-LAST:event_RefreshButtonActionPerformed

    /**
     * If the window is close, then we rollback all the changes
     * @param evt 
     */
    private void formComponentHidden(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentHidden
        c.endfTransaccionRollback();

    }//GEN-LAST:event_formComponentHidden

    /**
     * Remove an Expert from the table
     * @param evt 
     */
    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        
        boolean succed=false;
        
        // Remove the expert from the database
        succed = em.removeExpert((String)tmodel.getValueAt(ExpertsTable.getSelectedRow(), 0));
            
        // Remove from the table
        tmodel.removeRow(ExpertsTable.getSelectedRow());
        
        if (!succed) {
            JOptionPane.showMessageDialog(null, "ERROR: The expert was not deleted correctly.");
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CodeField;
    private javax.swing.JTextField CountryField;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JTable ExpertsTable;
    private javax.swing.JTextField NameField;
    private javax.swing.JButton NewButton;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JButton SaveButton;
    private javax.swing.JTextField SexField;
    private javax.swing.JTextField SpecialismField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}