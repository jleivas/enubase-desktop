/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.view.notifications.panels.content;

import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.utils.GV;
import cl.softdirex.enubase.utils.GlobalValuesVariables;
import cl.softdirex.enubase.utils.Icons;
import cl.softdirex.enubase.utils.StEntities;
import cl.softdirex.enubase.utils.WebUtils;
import cl.softdirex.enubase.view.notifications.OptionPane;
import cl.softdirex.enubase.view.notifications.panels.input.OpanelSelectAdminToSendReporteItems;

/**
 *
 * @author sdx
 */
public class OpanelReporteItems extends javax.swing.JPanel {
    private static User stUser = StEntities.USER;
    /**
     * Creates new form OpanelSelectDate
     */
    public OpanelReporteItems() {
        initComponents();
        loadData();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSendReport = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtReporte = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));

        btnSendReport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/icons8_Forward_Message_50px.png"))); // NOI18N
        btnSendReport.setToolTipText("Enviar reporte");
        btnSendReport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSendReportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSendReportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSendReportMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnSendReportMousePressed(evt);
            }
        });

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/btn_Ok_50px.png"))); // NOI18N
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnGuardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnGuardarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnGuardarMousePressed(evt);
            }
        });

        txtReporte.setColumns(20);
        txtReporte.setRows(5);
        jScrollPane1.setViewportView(txtReporte);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 497, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSendReport)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnSendReport)
                    .addComponent(btnGuardar))
                .addGap(21, 21, 21))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendReportMouseClicked
        if(WebUtils.isOnline()){
            OptionPane.showOptionPanel(new OpanelSelectAdminToSendReporteItems(), "Enviar reporte de inventario");
        }else{
            OptionPane.showMsg("No se puede enviar reporte", "El sistema no ha detectado ninguna conexión a internet",2);
        }
    }//GEN-LAST:event_btnSendReportMouseClicked

    private void btnSendReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendReportMouseEntered
        btnSendReport.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getEnteredIcon(btnSendReport.getIcon().toString()))));
    }//GEN-LAST:event_btnSendReportMouseEntered

    private void btnSendReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendReportMouseExited
        btnSendReport.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getExitedIcon(btnSendReport.getIcon().toString()))));
    }//GEN-LAST:event_btnSendReportMouseExited

    private void btnSendReportMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendReportMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSendReportMousePressed

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        OptionPane.closeOptionPanel();
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void btnGuardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseEntered
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getEnteredIcon(btnGuardar.getIcon().toString()))));
    }//GEN-LAST:event_btnGuardarMouseEntered

    private void btnGuardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseExited
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource(Icons.getExitedIcon(btnGuardar.getIcon().toString()))));
    }//GEN-LAST:event_btnGuardarMouseExited

    private void btnGuardarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel btnGuardar;
    private javax.swing.JLabel btnSendReport;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtReporte;
    // End of variables declaration//GEN-END:variables

    private void loadData() {
        int compra = GlobalValuesVariables.ITEMS_COMPRA;
        int venta = GlobalValuesVariables.ITEMS_VENTA;
        String reporte = 
                "REPORTE DE INVENTARIO\n"
                + "-----------------------\n"
                + "Stock total de productos: "+GlobalValuesVariables.ITEMS_STOCK+"\n\n"
                + "Items con stock bajo: "+GlobalValuesVariables.ITEMS_STOCK_BAJO+"\n"
                + "Items con stock en cero: "+GlobalValuesVariables.ITEMS_STOCK_CERO+"\n\n";
        String reporteAdmin = 
                "REPORTE MONETARIO (Valores netos)\n"
                + "-----------------------\n"
                + "En inventario: "+GV.strToPrice(compra)+"\n"
                + "Monto de venta: "+GV.strToPrice(venta)+"\n"
                + "Retorno :"+GV.strToPrice((venta-compra));
        if(GV.tipoUserSuperAdmin()){
            txtReporte.setText(reporte+reporteAdmin);
        }else{
            txtReporte.setText(reporte);
        }
    }

    
}
