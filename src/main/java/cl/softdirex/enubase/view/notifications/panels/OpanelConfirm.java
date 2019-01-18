/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.view.notifications.panels;

import cl.softdirex.enubase.view.notifications.Notification;
import cl.softdirex.enubase.utils.PanelUtils;
import javax.swing.JOptionPane;

/**
 *
 * @author sdx
 */
public class OpanelConfirm extends javax.swing.JPanel {

    public static int status=0;
    /**
     * Creates new form OpanelSelectDate
     */
    public OpanelConfirm() {
        initComponents();
        switch (PanelUtils.getMsgStatus()){
            case JOptionPane.INFORMATION_MESSAGE:
                imgIconMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.iconInfo())));
                break;
            case JOptionPane.WARNING_MESSAGE:
                imgIconMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.iconWarn())));
                break;
            case JOptionPane.ERROR_MESSAGE:
                imgIconMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.iconError())));
                break;
            case JOptionPane.ERROR:
                imgIconMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.iconError())));
                break;
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

        imgIconMessage = new javax.swing.JLabel();
        btnCancelar = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblMessage = new javax.swing.JTextArea();
        btnAceptar = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        imgIconMessage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/softdirex/enubase/view/icons/info_50px.png"))); // NOI18N
        imgIconMessage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgIconMessageMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                imgIconMessageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                imgIconMessageMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgIconMessageMousePressed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/softdirex/enubase/view/icons/cancel_50px.png"))); // NOI18N
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancelarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnCancelarMousePressed(evt);
            }
        });

        lblTitle.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        lblTitle.setText("Titulo del mensaje");

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        lblMessage.setEditable(false);
        lblMessage.setColumns(20);
        lblMessage.setRows(5);
        jScrollPane1.setViewportView(lblMessage);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cl/softdirex/enubase/view/icons/accept_50px.png"))); // NOI18N
        btnAceptar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnAceptarMousePressed(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnAceptarMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnAceptarMouseEntered(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imgIconMessage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 436, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addGap(18, 18, 18))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(393, Short.MAX_VALUE)
                    .addComponent(btnAceptar)
                    .addGap(79, 79, 79)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTitle)
                    .addComponent(imgIconMessage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(173, Short.MAX_VALUE)
                    .addComponent(btnAceptar)
                    .addGap(8, 8, 8)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void imgIconMessageMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgIconMessageMouseClicked
        
    }//GEN-LAST:event_imgIconMessageMouseClicked

    private void imgIconMessageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgIconMessageMouseEntered
        
    }//GEN-LAST:event_imgIconMessageMouseEntered

    private void imgIconMessageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgIconMessageMouseExited
        
    }//GEN-LAST:event_imgIconMessageMouseExited

    private void imgIconMessageMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_imgIconMessageMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_imgIconMessageMousePressed

    private void btnCancelarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseClicked
        Notification.setConfirm(false);
        Notification.closeInfoPanel();
    }//GEN-LAST:event_btnCancelarMouseClicked

    private void btnCancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseEntered
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.getEnteredIcon(btnCancelar.getIcon().toString()))));
    }//GEN-LAST:event_btnCancelarMouseEntered

    private void btnCancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMouseExited
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.getExitedIcon(btnCancelar.getIcon().toString()))));
    }//GEN-LAST:event_btnCancelarMouseExited

    private void btnCancelarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCancelarMousePressed

    private void btnAceptarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseClicked
        Notification.setConfirm(true);
        Notification.closeInfoPanel();
    }//GEN-LAST:event_btnAceptarMouseClicked

    private void btnAceptarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseEntered
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.getEnteredIcon(btnAceptar.getIcon().toString()))));
    }//GEN-LAST:event_btnAceptarMouseEntered

    private void btnAceptarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMouseExited
        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource(PanelUtils.getExitedIcon(btnAceptar.getIcon().toString()))));
    }//GEN-LAST:event_btnAceptarMouseExited

    private void btnAceptarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel btnAceptar;
    private javax.swing.JLabel btnCancelar;
    private javax.swing.JLabel imgIconMessage;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JTextArea lblMessage;
    public static javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables

    

    
}
