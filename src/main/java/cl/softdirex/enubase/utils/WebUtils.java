/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.view.notifications.OptionPane;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class WebUtils {
    private static String className = "NetWrk";
    
    private static boolean IS_ONLINE = true;
    
    public static void checkIfOnline(){
        String dirWeb = "www.google.com";
        int puerto = 80;
        boolean isOnline=false;
        try{
            Socket s = new Socket(dirWeb, puerto);
            if(s.isConnected()){
                isOnline = true;
            }
        }catch(IOException e){
            isOnline = false;
        }
        setIsOnline(isOnline);
    }
    
    public static void setIsOnline(boolean value){
        IS_ONLINE = value;
    }
    
    public static boolean isOnline(){
        return IS_ONLINE;
    }
    
    public static void goToPayPage(){
        String url=PropertiesUtils.getPayPageUrl();
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
            Send send = new Send();
            send.sendReportMail(GlobalValuesVariables.getCompanyName()+" está intentando pagar", GlobalValuesVariables.getCompanyName()+" está intentando acceder a la sección de pago webpay,\n"
                    + "compruebe si el pago fué efectuado para actualizar el plan: \""+GlobalValuesVariables.getLicenceCode()+"\", Estado:"+GV.licenciaEstado());
        } catch (IOException ex) {
            Logger.getLogger(WebUtils.class.getName()).log(Level.SEVERE, null, ex);
            OptionPane.showMsg("No se puede abrir enlace", "Ocurrió un error inesperado al intentar abrir el enlace de pago\n"
                    + "Póngase en contacto con su proveedor de software.", 3);
        }
    }
}
