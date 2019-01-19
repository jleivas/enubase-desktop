/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.view.notifications.OptionPane;
import cl.softdirex.enubase.view.os.general.panels.VMessage;
import cl.softdirex.enubase.view.os.general.panels.VUsuarios;
import static cl.softdirex.enubase.view.os.other.ContentAdminMac.principalAdminMac;
import static cl.softdirex.enubase.view.os.windows.ContentAdmin.principalAdmin;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author home
 */
public class Boton {
    private int ancho = 1300;
    private int anchoMac = 1500;
    private int alto = 650;
    private int locat = 5;
    
    public void index() throws SQLException, ClassNotFoundException {
//        if(isWin()){
//            openView(new VCrearFicha());
//        }else{
//            openView(new VCrearFichaMac());
//        }
//        
    }
    
    public void ventas(int cboOption) throws SQLException, ClassNotFoundException {
//        if(isWin()){
//            openView(new VCrearFicha());
//        }else{
//            openView(new VCrearFichaMac());
//        }
//        
    }
    
    public void items() throws SQLException, ClassNotFoundException {
//        if(GV.tipoUserIventario()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VCristales());
//            }else{
//                openView(new VCristalesMac());
//            }
//        }else{
//            accesDenied();
//        }
    }
    
    public void clientes() throws SQLException, ClassNotFoundException{
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VClientes());
//            }else{
//                openView(new VClientesMac());
//            }
    }
    
    
    public void descuentos() throws SQLException, ClassNotFoundException{
//         if(GV.tipoUserAdmin()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VDescuentos());
//            }else{
//                openView(new VDescuentosMac());
//            }
//         }else{
//             accesDenied();
//         }   
    }
    
    public void oficinas() throws SQLException, ClassNotFoundException {
//        if(GV.tipoUserAdmin()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VOficinas());
//            }else{
//                openView(new VOficinasMac());
//            }
//        }else{
//            accesDenied();
//        }
    }
    
    public void mensajes() {
//        CursorUtils.cursorWAIT();
//        openView(new VMessages());
    }
    
    public void proveedores() throws SQLException, ClassNotFoundException{
//        CursorUtils.cursorWAIT();
//        if(isWin()){
//            openView(new VInstituciones());
//        }else{
//            openView(new VInstitucionesMac());
//        }
    }
    
    public void venta() throws SQLException, ClassNotFoundException {
//        CursorUtils.cursorWAIT();
//        if(isWin()){
//            openView(new VFicha());
//        }else{
//            openView(new VFichaMac());
//        }
    }
    
    public void inventarios() throws SQLException, ClassNotFoundException {
//        if(GV.tipoUserIventario()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VInventarios());
//            }else{
//                openView(new VInventariosMac());
//            }
//        }else{
//            accesDenied();
//        }
    }
//    
//    public void lentes() throws SQLException, ClassNotFoundException {
//        if(GV.tipoUserIventario()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VLentes());
//            }else{
//                openView(new VLentesMac());
//            }
//        }else{
//            accesDenied();
//        }
//    }
//    

//    
    
//    
    public void registroBajas() {
//        if(GV.tipoUserAdmin()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VRegistroBajas());
//            }else{
//                openView(new VRegistroBajasMac());
//            }
//        }else{
//            accesDenied();
//        }
    }
//    
    public void tipoPagos() throws SQLException, ClassNotFoundException{
//        if(GV.tipoUserAdmin()){
//            CursorUtils.cursorWAIT();
//            if(isWin()){
//                openView(new VTipoPagos());
//            }else{
//                openView(new VTipoPagosMac());
//            }
//        }else{
//            accesDenied();
//        }
    }
//    
    public void usuarios() throws SQLException, ClassNotFoundException {
        if(GV.tipoUserAdmin()){
            CursorUtils.cursorWAIT();
//            if(isWin()){
                openView(new VUsuarios());
//            }else{
//                openView(new VUsuariosMac());
//            }
        }else{
            accesDenied();
        }
    }
//    
    public void mensajeInfo(String title, String message) {
        openView(new VMessage(title, message));
    }
    
    private void openView(JPanel p1){
        if(isWin()){
            try{
                p1.setSize(principalAdmin.getWidth(), principalAdmin.getHeight());
                p1.setLocation(locat, locat);
                principalAdmin.removeAll();
                principalAdmin.add(p1,BorderLayout.CENTER);
                principalAdmin.revalidate();
                principalAdmin.repaint();
            }catch(Exception ex){
                OptionPane.showMsg("Error inesperado", "No se ha podido abrir la ventana solicitada, \n"
                        + "se enviará un reporte para solucionar este problema,\n"
                        + "póngase en contacto con su proveedor de software.", JOptionPane.ERROR_MESSAGE);
            }
            principalAdmin.setCursor(Cursor.getDefaultCursor());
        }else{
            try{
                p1.setSize(principalAdminMac.getWidth(), principalAdminMac.getHeight());
                p1.setLocation(locat, locat);
                principalAdminMac.removeAll();
                principalAdminMac.add(p1,BorderLayout.CENTER);
                principalAdminMac.revalidate();
                principalAdminMac.repaint();
            }catch(Exception ex){
                OptionPane.showMsg("Error inesperado", "No se ha podido abrir la ventana solicitada, \n"
                        + "se enviará un reporte para solucionar este problema,\n"
                        + "póngase en contacto con su proveedor de software.", JOptionPane.ERROR_MESSAGE);
            }
            principalAdminMac.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void accesDenied() {
        OptionPane.showMsg("Acceso denegado", "No tienes permiso suficiente para acceder a estas opciones.", 2);
        CursorUtils.cursorDF();
    }
//
//    public void salesReport() {
//        OptionPane.showMsg("crear ventana de reporte", "reporte de ventas", 3);
//    }
//    
    private boolean isWin(){
        return GlobalValuesVariables.getIsWindows();
    }
}
