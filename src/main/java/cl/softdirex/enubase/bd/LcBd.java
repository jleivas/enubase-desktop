/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.bd;

import cl.softdirex.enubase.dao.Dao;
import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.utils.BDUtils;
import cl.softdirex.enubase.utils.GV;
import cl.softdirex.enubase.utils.GlobalValuesVariables;
import cl.softdirex.enubase.view.notifications.OptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author sdx
 */
public class LcBd{
    private static Connection conn = null;
    private static String className= "LcBd";
    private static String[] T = BDUtils.tableNamesDB();
    private static String[] C = BDUtils.tablesDB();
    
    public static Connection crear() {
        if(T.length == C.length){
            for(int i=0;i<T.length;i++){
                System.out.println(T[i]+"=>"+C[i]);
                if(!createTable(T[i], C[i])){
                    System.out.println("La base de datos ya existe");
                    return conn;
                }
            }
        }
        Dao load = new Dao();
        try {
            load.addOnInit(new User(1, "Admin", "admin", "", GV.enC("admin"), 1, 1, null, 0));
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LcBd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
    
    public static Connection deleteAll() {
        if(T.length > 0){
            for(int i=0;i<T.length;i++){
                dropTable(T[i]);
            }
        }
        return conn;
    }
    
    public static Connection truncateAll() {
        if(T.length > 0){
            for(int i=0;i<T.length;i++){
                truncateTable(T[i]);
            }
        }
        return conn;
    }
        
    public static Connection obtener() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
        
        conn = DriverManager.getConnection("jdbc:derby:"+BDUtils.getLocalBdUrl()+BDUtils.getLocalBdName());
        if(conn == null)
            OptionPane.showMsg("Error en Base de datos local", "No se pudo obtener la conexion:\nbd.RmBd::obtener(): ERROR BD.", 3);
        return conn;
    }
    
    public static boolean isOpen(){
        return (conn!=null)?true:false;
    }
    
    public static void cerrar() throws SQLException
    {
        if(conn!=null)
            conn.close();
    }
    
    private static void truncateTable(String tableName){
        try{
            //obtenemos el driver de para mysql
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            cerrar();
            conn = DriverManager.getConnection("jdbc:derby:"+BDUtils.getLocalBdUrl()+BDUtils.getLocalBdName()+";create=true");
            if (conn!=null){
               String creartabla="TRUNCATE TABLE "+tableName;//(tableName.toLowerCase().equals("lente"))?"drop table lente":
                System.out.println(creartabla);
               try {
                    PreparedStatement pstm = conn.prepareStatement(creartabla);
                    pstm.execute();
                    pstm.close();
                    cerrar();
                } catch (SQLException ex) {
                    System.out.println("\"Error al borrar datos detabla "+tableName+", "+ex.getLocalizedMessage());
//                    OptionPane.showMsg("Error al crear tabla "+tableName, ex.getLocalizedMessage(),3);
                }
            }
        }catch(SQLException | ClassNotFoundException | ExceptionInInitializerError e){
         OptionPane.showMsg("Error al borrar datos de tabla "+tableName,e.getMessage() ,  3);
        }
    }
    
    private static void dropTable(String tableName){
        try{
            //obtenemos el driver de para mysql
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            cerrar();
            conn = DriverManager.getConnection("jdbc:derby:"+BDUtils.getLocalBdUrl()+BDUtils.getLocalBdName()+";create=true");
            if (conn!=null){
               String creartabla="DROP TABLE "+tableName;//(tableName.toLowerCase().equals("lente"))?"drop table lente":
                System.out.println(creartabla);
               try {
                    PreparedStatement pstm = conn.prepareStatement(creartabla);
                    pstm.execute();
                    pstm.close();
                    cerrar();
                } catch (SQLException ex) {
                    System.out.println("\"Error al borrar tabla "+tableName+", "+ex.getLocalizedMessage());
//                    OptionPane.showMsg("Error al crear tabla "+tableName, ex.getLocalizedMessage(),3);
                }
            }
        }catch(SQLException | ClassNotFoundException | ExceptionInInitializerError e){
         OptionPane.showMsg("Error al borrar tabla "+tableName,e.getMessage() ,  3);
        }
    }
    
    private static boolean createTable(String tableName, String columns){
        try{
            //obtenemos el driver de para mysql
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            columns = (columns.startsWith("(")) ? columns.trim():"("+columns.trim();
            columns = (columns.endsWith("))")) ? columns.trim():columns.trim()+")";
            //obtenemos la conexión
            //"jdbc:derby:.\\DB\\Derby.DB;create=true"
            cerrar();
            conn = DriverManager.getConnection("jdbc:derby:"+BDUtils.getLocalBdUrl()+BDUtils.getLocalBdName()+";create=true");
            if (conn!=null){
               String creartabla="create table "+tableName+columns;//(tableName.toLowerCase().equals("lente"))?"drop table lente":
               
               try {
                    PreparedStatement pstm = conn.prepareStatement(creartabla);
                    pstm.execute();
                    pstm.close();
                    System.out.println(creartabla);
                    cerrar();
                } catch (SQLException ex) {
                    System.out.println(ex+"\n"+ex.getSQLState());
                    return false;                }
            }
        }catch(SQLException | ClassNotFoundException | ExceptionInInitializerError e){
            JOptionPane.showMessageDialog(null,"Ha intentado abrir el programa mas de una vez\n¡"+GlobalValuesVariables.getProjectName()+" ya se encuentra en ejecución!"
                    +"\n\nEl sistema se cerrará",GlobalValuesVariables.getProjectName()+" ya se encuentra en ejecución", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return true;
    }
    
    public static Object [][] select(String table, String fields, String where){
      int registros = 0;      
      String colname[] = fields.split(",");

      //Consultas SQL
      String q ="SELECT " + fields + " FROM " + table;
      String q2 = "SELECT count(*) as total FROM " + table;
      if(where!=null)
      {
          q+= " WHERE " + where;
          q2+= " WHERE " + where;
      }
       try{
         PreparedStatement pstm = obtener().prepareStatement(q2);
         ResultSet res = pstm.executeQuery();
         res.next();
         registros = res.getInt("total");
         res.close();
      }catch(SQLException e){
         OptionPane.showMsg("Error en LCBD", e.getLocalizedMessage(),3);
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(LcBd.class.getName()).log(Level.SEVERE, null, ex);
            OptionPane.showMsg("Error en LCBD", "Detalle:\n"+ex.getLocalizedMessage(),3);
        }
    
    //se crea una matriz con tantas filas y columnas que necesite
    Object[][] data = new String[registros][fields.split(",").length];
    //realizamos la consulta sql y llenamos los datos en la matriz "Object"
      try{
         PreparedStatement pstm = obtener().prepareStatement(q);
         ResultSet res = pstm.executeQuery();
         int i = 0;
         while(res.next()){
            for(int j=0; j<=fields.split(",").length-1;j++){
                data[i][j] = res.getString( colname[j].trim() );
            }
            i++;         }
         res.close();
          }catch(SQLException e){
         OptionPane.showMsg("Error en LCBD al obtener datos", ""+e.getLocalizedMessage(),3);
    }   catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            OptionPane.showMsg("Error en LCBD al obtener datos", "Detalle:\n"+ex.getLocalizedMessage(),3);
        }
    return data;
 }
}
