/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.view.notifications.panels.input;

import cl.softdirex.enubase.utils.Boton;
import cl.softdirex.enubase.utils.CursorUtils;
import cl.softdirex.enubase.utils.GlobalValuesVariables;
import cl.softdirex.enubase.utils.Icons;
import cl.softdirex.enubase.view.notifications.OptionPane;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class OpanelSelectDate extends javax.swing.JPanel {
    Boton boton = new Boton();
    /**
     * Creates new form OpanelSelectDate
     */
    public OpanelSelectDate() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtFecha1 = new com.toedter.calendar.JDateChooser();
        btnSyncronize18 = new javax.swing.JLabel();
        btnSyncronize19 = new javax.swing.JLabel();
        txtFecha2 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JLabel();
        btnLoad = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        btnSyncronize18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Calendar_25px.png"))); // NOI18N
        btnSyncronize18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSyncronize18MouseClicked(evt);
            }
        });

        btnSyncronize19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Calendar_25px.png"))); // NOI18N
        btnSyncronize19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSyncronize19MouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel1.setText("Desde");

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel2.setText("Hasta");

        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Cancel_50px.png"))); // NOI18N
        btnCancel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelMousePressed(evt);
            }
        });

        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Filter_50px.png"))); // NOI18N
        btnLoad.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLoadMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoadMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoadMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnLoadMousePressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancel)
                .addGap(18, 18, 18)
                .addComponent(btnLoad)
                .addGap(87, 87, 87))
            .addGroup(layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSyncronize18)
                        .addGap(5, 5, 5)
                        .addComponent(txtFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1))
                .addGap(74, 74, 74)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSyncronize19)
                        .addGap(5, 5, 5)
                        .addComponent(txtFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2))
                .addContainerGap(95, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSyncronize19, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSyncronize18, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFecha1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnLoad))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSyncronize18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSyncronize18MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSyncronize18MouseClicked

    private void btnSyncronize19MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSyncronize19MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSyncronize19MouseClicked

    private void btnCancelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseClicked
        
        OptionPane.closeOptionPanel();
    }//GEN-LAST:event_btnCancelMouseClicked

    private void btnCancelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseEntered
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getEnteredIcon(btnCancel.getIcon().toString()))));
    }//GEN-LAST:event_btnCancelMouseEntered

    private void btnCancelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMouseExited
        btnCancel.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getExitedIcon(btnCancel.getIcon().toString()))));
    }//GEN-LAST:event_btnCancelMouseExited

    private void btnCancelMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelMousePressed

    private void btnLoadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseClicked
        try {
            CursorUtils.cursorWAIT(this);
            if(txtFecha1.getDate() == null){
                if(txtFecha2.getDate() == null){
                    GlobalValuesVariables.setDateFrom(new Date());
                    GlobalValuesVariables.setDateTo(new Date());
                }else{
                    GlobalValuesVariables.setDateFrom(txtFecha2.getDate());
                    GlobalValuesVariables.setDateTo(txtFecha2.getDate());
                }
            }else{
                if(txtFecha2.getDate() == null){
                    GlobalValuesVariables.setDateFrom(txtFecha1.getDate());
                    GlobalValuesVariables.setDateTo(txtFecha1.getDate());
                }else{
                    GlobalValuesVariables.setDateFrom(txtFecha1.getDate());
                    GlobalValuesVariables.setDateTo(txtFecha2.getDate());
                }
            }
            OptionPane.closeOptionPanel();
            CursorUtils.cursorDF(this);
            boton.ventas(GlobalValuesVariables.getCboVentasFilter());
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OpanelSelectDate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnLoadMouseClicked

    private void btnLoadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseEntered
        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getEnteredIcon(btnLoad.getIcon().toString()))));
    }//GEN-LAST:event_btnLoadMouseEntered

    private void btnLoadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMouseExited
        btnLoad.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getExitedIcon(btnLoad.getIcon().toString()))));
    }//GEN-LAST:event_btnLoadMouseExited

    private void btnLoadMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLoadMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnCancel;
    private javax.swing.JLabel btnLoad;
    private javax.swing.JLabel btnSyncronize18;
    private javax.swing.JLabel btnSyncronize19;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private com.toedter.calendar.JDateChooser txtFecha1;
    private com.toedter.calendar.JDateChooser txtFecha2;
    // End of variables declaration//GEN-END:variables

}
