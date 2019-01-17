/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.bd;

import cl.softdirex.enubase.utils.BDUtils;
import cl.softdirex.enubase.utils.StVars;
import cl.softdirex.enubase.view.notifications.Notification;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author sdx
 */
public class RmBd {
    private static Connection conn = null;
    private static String className = "RmBd";
    
    public static Connection obtener() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        String detail ="Es probable que su conexión a internet sea intermitente\n"
                + "por favor intente nuevamente, si el problema persiste\n"
                + "póngase en contacto con su proveedor de software.";
        try{
        conn = DriverManager.getConnection("jdbc:mysql://"+BDUtils.getRemoteBdUrl()+"/"+BDUtils.getRemoteBdName(),BDUtils.getRemoteBdUser(),BDUtils.getRemoteBdPass());
        }catch(Exception ex){
            Notification.showMsg("Error en Base de datos remota", "No se pudo obtener la conexion:\n"+detail+"\nbd.RmBd::obtener(): ERROR BD.\n\nCatch: "+ex.getMessage(), 3);
            cerrar();
            BDUtils.setSincronizar(false);
        }
        if(conn == null){
            Notification.showMsg("Error en Base de datos remota", "No se pudo obtener la conexion:\n"+detail+"\nbd.RmBd::obtener(): ERROR BD.", 3);
            cerrar();
            BDUtils.setSincronizar(false);
        }
        return conn;
    }
    
    public static void cerrar() throws SQLException
    {
        if(conn!=null)
            conn.close();
    }
    
    public static boolean isOpen(){
        return (conn!=null)?true:false;
    }
}
