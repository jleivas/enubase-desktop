/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.view.notifications.panels.input;

import cl.softdirex.enubase.dao.Dao;
import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.utils.Boton;
import cl.softdirex.enubase.utils.CursorUtils;
import cl.softdirex.enubase.utils.GlobalValuesVariables;
import cl.softdirex.enubase.utils.Icons;
import cl.softdirex.enubase.view.notifications.OptionPane;
import com.mxrck.autocompleter.TextAutoCompleter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class OpanelSelectUser extends javax.swing.JPanel {
    Boton boton = new Boton();
    /**
     * Creates new form OpanelSelectDate
     */
    public OpanelSelectUser() {
        initComponents();
        try {
            autocompletar();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(OpanelSelectUser.class.getName()).log(Level.SEVERE, null, ex);
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

        jLabel1 = new javax.swing.JLabel();
        btnCancel = new javax.swing.JLabel();
        btnLoad = new javax.swing.JLabel();
        txtNombreUsuario = new javax.swing.JTextField();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione un usuario");

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(351, Short.MAX_VALUE)
                        .addComponent(btnCancel)
                        .addGap(18, 18, 18)
                        .addComponent(btnLoad))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombreUsuario, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(87, 87, 87))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txtNombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel)
                    .addComponent(btnLoad))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

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
        String idUser = txtNombreUsuario.getText();
        CursorUtils.cursorWAIT(this);
        if(idUser.contains("<")){
            idUser = idUser.substring(idUser.indexOf("<")+1);
            idUser = idUser.replaceAll(">", "");
            GlobalValuesVariables.setIdUserSelected(idUser);
            OptionPane.closeOptionPanel();
            try {
                boton.ventas(GlobalValuesVariables.getCboVentasFilter());
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(OpanelSelectUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            CursorUtils.cursorDF(this);
            OptionPane.showMsg("El vendedor no existe", "Debe seleccionar un vendedor de la lista desplegable", 2);
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

    private void autocompletar() throws SQLException, ClassNotFoundException {
        Dao load = new Dao();
        TextAutoCompleter textAutoCompleter2 = new TextAutoCompleter(txtNombreUsuario);
        for (Object temp : load.listar("-2", new User())) {
            if(((User)temp).getTipo() != 7){
                textAutoCompleter2.addItem(((User)temp).getNombre()+" <"+((User)temp).getId()+">");
                textAutoCompleter2.setMode(0);
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnCancel;
    private javax.swing.JLabel btnLoad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtNombreUsuario;
    // End of variables declaration//GEN-END:variables

}
