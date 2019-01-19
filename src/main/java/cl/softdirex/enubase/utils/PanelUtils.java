/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.view.notifications.Notification;
import cl.softdirex.enubase.view.notifications.panels.MPanel;
import cl.softdirex.enubase.view.notifications.panels.OPanel;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author sdx
 */
public class PanelUtils {
    /* Joption Pane del sistema */
    public static MPanel INFOPANEL = new MPanel();
    public static OPanel OPTIONPANEL = new OPanel();
    public static String PANELTITLE ="";
    public static int MSG_STATUS=JOptionPane.ABORT;
    public static final String ICON_INFO = "/icons/info_50px.png";
    public static final String ICON_WARN = "/icons/alert_50px.png";
    public static final String ICON_ERROR = "/icons/error_50px.png";
    public static final String ICON_LOGO = "/images/icon.png";
    
    public static String getEnteredIcon(String stIcon) {
        stIcon = stIcon.substring(stIcon.indexOf("/icons"));
        return stIcon.replaceAll(".png", "_1.png");
    }

    public static String getEnteredIconIfActive(String stIcon) {
        stIcon = stIcon.substring(stIcon.indexOf("/icons"));
        return stIcon.replaceAll(".png", "_1.png");//borrar
    }

    public static String getExitedIcon(String img) {
        img = img.substring(img.indexOf("/icons"));
        return img.replaceAll("_1.png", ".png").replaceAll("_2.png", ".png");
    }

    public static String getEnteredIconIfConnected(String stIcon) {
        stIcon = stIcon.substring(stIcon.indexOf("/icons"));
        if(NetWrk.isOnline())
            return stIcon.replaceAll(".png", "_1.png");
        else
            return stIcon.replaceAll(".png", "_2.png");
    }
    
    public static int getMsgStatus(){
        return MSG_STATUS;
    }
    
    public static String iconInfo(){
        return ICON_INFO;
    }
    
    public static String iconWarn(){
        return ICON_WARN;
    }
    
    public static String iconError(){
        return ICON_ERROR;
    }
    
    public static void setMsgStatus(int statusMsg){
        switch(statusMsg){
            case 1:
                MSG_STATUS = (MSG_STATUS!=JOptionPane.WARNING_MESSAGE && MSG_STATUS!=JOptionPane.ERROR_MESSAGE)?JOptionPane.INFORMATION_MESSAGE:MSG_STATUS;
                break;
            case 2: 
                MSG_STATUS = (MSG_STATUS!=JOptionPane.ERROR_MESSAGE)?JOptionPane.WARNING_MESSAGE:MSG_STATUS;
                break;
            case 3:
                MSG_STATUS = JOptionPane.ERROR_MESSAGE;
                break;
            default:
                MSG_STATUS = JOptionPane.ABORT;
                break;
        }
    }
    
    public static OPanel opanel(){
        return OPTIONPANEL;
    }
    
    public static MPanel mpanel(){
        return INFOPANEL;
    }
    
    public static void licenciaShowMessageLicenceStatus(){
        String pagar = "Para evitar este mensaje:\nEntre al sistema y en el menú superior diríjase a \"Herramientas\", seleccione \"Renovar Licencia\"\n"
                + "y renueve su licencia con el medio de pago que más le acomode.\n"
                + "Si usted ya había efectuado el pago correspondiente, póngase en contacto con su proveedor.";
        int expDias = GV.fechaDiferencia(GV.stringToDate(StVars.getExpDate())); 
        if(expDias <= 5){
            if(expDias > 1){
                Notification.showMsg("Renueve su licencia", "Su licencia expirará dentro de "+expDias+" días."+pagar, 2);
            }
            if(expDias == 1){
                Notification.showMsg("Renueve su licencia", "Su licencia expirará mañana.\n\n"+pagar, 2);
            }
            if(expDias == 0){
                JOptionPane.showMessageDialog(null,"Su licencia expirará hoy.\n\n"+pagar,"Renueve su licencia", JOptionPane.WARNING_MESSAGE);
            }
            if(expDias < 0){
                JOptionPane.showMessageDialog(null,"Su licencia ha caducado."+pagar,"Renueve su licencia", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
