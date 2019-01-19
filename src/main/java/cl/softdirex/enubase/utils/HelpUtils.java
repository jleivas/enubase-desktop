/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.view.notifications.OptionPane;
import java.util.Date;

/**
 *
 * @author sdx
 */
public class HelpUtils {
    private static String helpUserPassAccessDenied(){
        return "Si usted no ha modificado su contraseña, es probable que haya sido\n"
                + "reseteada por el sistema, esto ocurre cuando:\n"
                + "a) Su usuario fue registrado en otro equipo y aún no se ha sincronizado\n"
                + "   la base de datos local.\n"
                + "b) Un administrador actualizó sus datos en este equipo.\n"
                + "c) Un administrador actualizó sus datos en otro equipo\n"
                + "   y el sistema local fué sincronizado.\n"
                + "Pruebe con la siguiente clave: "+ getResetPass() + "\n"
                + "Si no tiene éxito, contáctese con su proveedor de software";
    }
    
    public static String getResetPass() {
        return GlobalValuesVariables.getProjectName()+GV.dateToString(new Date(), "yyyy");
    }

    public static void showKeySuggestion() {
        OptionPane.showMsg("Sugerencia de clave", HelpUtils.helpUserPassAccessDenied(), 1);
    }
}
