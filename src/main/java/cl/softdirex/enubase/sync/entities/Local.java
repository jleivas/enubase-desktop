/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.sync.entities;

import cl.softdirex.enubase.bd.LcBd;
import cl.softdirex.enubase.entities.Cliente;
import cl.softdirex.enubase.entities.Descuento;
import cl.softdirex.enubase.entities.Despacho;
import cl.softdirex.enubase.entities.Detalle;
import cl.softdirex.enubase.entities.Equipo;
import cl.softdirex.enubase.entities.HistorialPago;
import cl.softdirex.enubase.entities.InternMail;
import cl.softdirex.enubase.entities.Inventario;
import cl.softdirex.enubase.entities.Item;
import cl.softdirex.enubase.entities.Oficina;
import cl.softdirex.enubase.entities.Proveedor;
import cl.softdirex.enubase.entities.RegistroBaja;
import cl.softdirex.enubase.entities.TipoPago;
import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.entities.Venta;
import cl.softdirex.enubase.entities.VentaDTO;
import cl.softdirex.enubase.entities.abstractclasses.SyncCodClass;
import cl.softdirex.enubase.entities.abstractclasses.SyncIntId;
import cl.softdirex.enubase.entities.abstractclasses.SyncIntIdValidaName;
import cl.softdirex.enubase.entities.abstractclasses.SyncStringId;
import cl.softdirex.enubase.sync.InterfaceSync;
import cl.softdirex.enubase.utils.BDUtils;
import cl.softdirex.enubase.utils.GV;
import cl.softdirex.enubase.utils.VarUtils;
import cl.softdirex.enubase.view.notifications.Notification;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class Local implements InterfaceSync {
    private static String className = Local.className;

    @Override
    public boolean add(Object objectParam) {
        try{
            if(objectParam == null)
                return false;
            if(objectParam instanceof Venta){
                Venta object = (Venta)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT ven_id FROM venta WHERE ven_id='" + object.getCod() + "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Venta: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof VentaDTO){
                VentaDTO object = (VentaDTO)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT ven_id FROM venta WHERE ven_id='" + object.getCod() + "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Venta: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Cliente){
                Cliente object = (Cliente)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT cli_rut FROM cliente WHERE cli_rut='" + object.getCod() + "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Cliente: " + object.getNombre()+ "\nId: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Descuento){
                Descuento object = (Descuento)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT des_id FROM descuento WHERE des_id=" + object.getId());
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();

                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Descuento: " + object.getNombre() + "\nId: " + object.getId() + "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Detalle){
                Detalle object = (Detalle)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT det_id FROM detalle WHERE det_id='" + object.getCod() + "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "detalle: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Despacho){
                Despacho object = (Despacho)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT dsp_id FROM despacho WHERE dsp_id='" + object.getCod() + "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "despacho: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Equipo){
                Equipo object = (Equipo)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT eq_id FROM equipo WHERE eq_id=" + object.getId()+ "");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Equipo: " + object.getNombre()+ "\nId: " + object.getId()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof HistorialPago){
                HistorialPago object = (HistorialPago)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT hp_id FROM historial_pago WHERE hp_id='" + object.getCod()+ "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Historial de pago: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Inventario){
                Inventario object = (Inventario)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT inv_id FROM inventario WHERE inv_id="+object.getId()+"");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    LcBd.cerrar();
                    return update(object);
                }  
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlInsert(object)
                               );
                if(insert.executeUpdate()!=0){
                    LcBd.cerrar();

                    return true;
                }
            }
            if(objectParam instanceof Item){
                Item object = (Item)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT itm_id FROM item WHERE itm_id= '" + object.getCod()+ "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Item: " + object.getDescripcion()+ "\nId: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof InternMail){
                InternMail object = (InternMail)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT msg_id FROM message WHERE msg_id=" + object.getId()+ "");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Mensaje: " + object.getAsunto()+ "\nId: " + object.getId() + "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Oficina){
                Oficina object = (Oficina)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT of_id FROM oficina WHERE of_id=" + object.getId() + "");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();

                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Oficina: " + object.getNombre()+ "\nId: " + object.getId()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof Proveedor){
                Proveedor object = (Proveedor)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT pro_id FROM proveedor WHERE pro_id='" + object.getCod()+ "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();

                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Proveedor: " + object.getNombre()+ "\nId: " + object.getCod()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof RegistroBaja){
                RegistroBaja object = (RegistroBaja)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT rb_id FROM registro_bajas WHERE rb_id='" + object.getCod()+ "'");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();

                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Registro de Bajas: " + object.getCod()+ "\n\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof TipoPago){
                TipoPago object = (TipoPago)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT tp_id FROM tipo_pago WHERE tp_id=" + object.getId()+ "");
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();

                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Tipo de Pago: " + object.getNombre()+ "\nId: " + object.getId()+ "\nNo se pudo insertar.", 3);
                return false;
            }
            if(objectParam instanceof User){
                User object = (User)objectParam;
                if (object != null) {
                    PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT us_id FROM usuario WHERE us_id=" + object.getId());
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        LcBd.cerrar();
                        return update(object);
                    }
                    PreparedStatement insert = LcBd.obtener().prepareStatement(
                            sqlInsert(object)
                    );
                    if (insert.executeUpdate() != 0) {
                        LcBd.cerrar();
                        //Notification.showMsg("Operación realizada correctamente", "Usuario: "+object.getUsername()+"\nId: "+object.getId()+"\nAgregado correctamente.", 1);
                        return true;
                    }
                }
                Notification.showMsg("Error inseperado en la operación", "Usuario: " + object.getUsername() + "\nId: " + object.getId() + "\nNo se pudo insertar.", 3);
                return false;
            }else{
                Notification.showMsg("Error inseperado en la operación", "El objeto no se pudo insertar.\n\n"+className+" no soporta el tipo de registro enviado.", 3);
                return false;
            }
        }catch( InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | NullPointerException ex){
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    @Override
    public boolean update(Object objectParam) {
        
        Date dsp_fecha = new Date();
        int hour = 0;
        try{
            if(objectParam == null)
                return false;
            if(objectParam instanceof Venta){
                Venta object = (Venta)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM venta WHERE ven_id='" + object.getCod() + "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("ven_last_update");
                        hour = datos.getInt("ven_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof VentaDTO){
                VentaDTO object = (VentaDTO)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM venta WHERE ven_id='" + object.getCod() + "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("ven_last_update");
                        hour = datos.getInt("ven_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Cliente){
                Cliente object = (Cliente)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM cliente WHERE cli_rut='" + object.getCod() + "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("cli_last_update");
                        hour = datos.getInt("cli_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Descuento){
                Descuento object = (Descuento)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM descuento WHERE des_id=" + object.getId());
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("des_last_update");
                        hour = datos.getInt("des_last_hour");
                    } catch (Exception e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.", 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Detalle){
                Detalle object = (Detalle)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM detalle WHERE det_id='" + object.getCod()+"'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("des_last_update");
                        hour = datos.getInt("des_last_hour");
                    } catch (Exception e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.", 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Despacho){
                Despacho object = (Despacho)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM despacho WHERE dsp_id='" + object.getCod() + "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("dsp_last_update");
                        hour = datos.getInt("dsp_last_hour");
                    } catch (Exception e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.", 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Equipo){
                Equipo object = (Equipo)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM equipo WHERE eq_id=" + object.getId()+ "");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("eq_last_update");
                        hour = datos.getInt("eq_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof HistorialPago){
                HistorialPago object = (HistorialPago)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM historial_pago WHERE hp_id='" + object.getCod()+ "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("hp_last_update");
                        hour = datos.getInt("hp_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Inventario){
                Inventario object = (Inventario)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM inventario WHERE inv_id=" + object.getId()+ "");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("inv_last_update");
                        hour = datos.getInt("inv_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Item){
                Item object = (Item)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM item WHERE itm_id='" + object.getCod()+ "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("itm_last_update");
                        hour = datos.getInt("itm_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof InternMail){
                InternMail object = (InternMail)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM message WHERE msg_id=" + object.getId()+ "");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("msg_last_update");
                        hour = datos.getInt("msg_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Oficina){
                Oficina object = (Oficina)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM oficina WHERE of_id=" + object.getId() + "");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("of_last_update");
                        hour = datos.getInt("of_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof Proveedor){
                Proveedor object = (Proveedor)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM proveedor WHERE pro_id='" + object.getCod()+ "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("pro_last_update");
                        hour = datos.getInt("pro_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof RegistroBaja){
                RegistroBaja object = (RegistroBaja)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM registro_bajas WHERE rb_id='" + object.getCod()+ "'");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("rb_last_update");
                        hour = datos.getInt("rb_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof TipoPago){
                TipoPago object = (TipoPago)objectParam;
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM tipo_pago WHERE tp_id=" + object.getId()+ "");
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("tp_last_update");
                        hour = datos.getInt("tp_last_hour");
                    } catch (SQLException e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.\nDetalle: " + e.getMessage(), 3);
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        LcBd.cerrar();
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
            if(objectParam instanceof User){
                User object = (User)objectParam;
                if (object == null) {
                    return false;
                }
                PreparedStatement consulta = LcBd.obtener().prepareStatement("SELECT * FROM usuario WHERE us_id=" + object.getId());
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    
                    try {
                        dsp_fecha = datos.getDate("us_last_update");
                        hour = datos.getInt("us_last_hour");
                    } catch (Exception e) {
                        Notification.showMsg("Error al convertir fecha", "Se cayó al intentar convertir la fecha.", 3);
                    }
                    if (dsp_fecha == null) {
                        dsp_fecha = new Date();
                    }
                    if (!GV.objectIsNew(object.getLastUpdate(),object.getLastHour(), dsp_fecha,hour)) {
                        return false;
                    }
                }
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlUpdate(object));
                insert.executeUpdate();
                LcBd.cerrar();
                return true;
            }
        }catch(InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | NullPointerException ex){
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public int getIdEquipo(){
        int id = 0;
        String sql = "SELECT eq_id  FROM equipo WHERE eq_nombre = '"+VarUtils.getEquipo()+"'";
        PreparedStatement consulta;
        try {
            consulta = LcBd.obtener().prepareStatement(sql);
            ResultSet datos = consulta.executeQuery();
            while (datos.next()) {
                id = datos.getInt("eq_id");
            }
            LcBd.cerrar();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
    @Override
    public int getMaxId(Object type) {
        
        int id = 0;
        try{
            String sql = "";
            if(type instanceof Venta || type instanceof VentaDTO){
                sql = "SELECT COUNT(ven_id) as id FROM venta WHERE ven_id LIKE '%-"+getIdEquipo()+"'";
            }
            if(type instanceof Descuento){
                sql = "SELECT COUNT(des_id) as id FROM descuento";
            }
            if(type instanceof Detalle){
                sql = "SELECT COUNT(det_id) as id FROM detalle";
            }
            if(type instanceof Despacho){
                sql = "SELECT COUNT(dsp_id) as id FROM despacho WHERE dsp_id LIKE '%-"+getIdEquipo()+"'";
            }
            if(type instanceof Equipo){
                sql = "SELECT COUNT(eq_id) as id FROM equipo";
            }
             if(type instanceof HistorialPago){
                sql = "SELECT COUNT(hp_id) as id FROM historial_pago WHERE hp_id LIKE '%-"+getIdEquipo()+"'";
            }
            if(type instanceof InternMail){
                sql = "SELECT COUNT(msg_id) as id FROM message";
            }
            if(type instanceof Inventario){
                sql = "SELECT COUNT(inv_id) as id FROM inventario";
            }
            if (type instanceof Oficina) {
                sql = "SELECT COUNT(of_id) as id FROM oficina";
            }
            if (type instanceof RegistroBaja) {
                sql = "SELECT COUNT(rb_id) as id FROM registro_bajas WHERE rb_id LIKE '%-"+getIdEquipo()+"'";
            }
            if (type instanceof TipoPago) {
                sql = "SELECT COUNT(tp_id) as id FROM tipo_pago";
            }
            if(type instanceof User){
                sql = "SELECT COUNT(us_id) as id FROM usuario";
            }
            if(sql.length()>2){
                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    id = datos.getInt("id");
                }
                LcBd.cerrar();
            }
        } catch (NullPointerException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
            return GV.strToNumber(GV.dateToString(new Date(), "yyyymmddhhss"));
        }
        return id + 1;
    }
    /**
     * Para listar las ventas de debe castear a la clase ResF,
     * si se desean obtener las ventas con todos sus datos, el idParam debe contener un Where en sql
     * de las ventas que se desean obtener y se debe transformas a idParamToVentaList de GlobalValues
     * @param idParam listar ventas = idVenta: enviar original, 
     * id_user: GC.convertVentaIdParamToUser(idParam),
     * rut_cliente: GC.convertVentaIdParamToClient(idParam)
     * @param type listar ventas= de tipo ResF para obtener ventas
     * @return lista de objetos ResF para varios elementos y Venta para un solo elemento
     */
    @Override
    public ArrayList<Object> listar(String idParam, Object type) {
        //Falta ordenar y agregar clases
        
        ArrayList<Object> lista = new ArrayList<>();
        idParam = idParam.trim();
        try {
            if(type instanceof Venta){
                if(!VarUtils.ventaIdParamIsVentaList(idParam) && !VarUtils.ventaIdParamIsIdVenta(idParam)){
                    return listar(idParam,new VentaDTO());
                }
                String sql = getSqlVenta()+" WHERE venta.ven_id='" + idParam + "'";
                sql = (idParam.equals("-2"))?getSqlVenta():sql;
                if(VarUtils.ventaIdParamIsVentaList(idParam)){
                    sql=getSqlVenta()+VarUtils.cleanIdParam(idParam);
                }
                
                    PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                    ResultSet datos = consulta.executeQuery();
                    while (datos.next()) {
                        /*------------------------CLIENTE-------------------*/
                        String rutCl;
                        try{rutCl = datos.getString("cliente_cli_rut");}catch (Exception e){rutCl = "";}
                        String nomCl;
                        try{nomCl = datos.getString("cli_nombre");}catch (Exception e){nomCl = "";}
                        String tel1Cl;
                        try{tel1Cl = datos.getString("cli_telefono1");}catch (Exception e){tel1Cl = "";}
                        String tel2Cl ;
                        try{tel2Cl = datos.getString("cli_telefono2");}catch (Exception e){tel2Cl = "";}
                        String emCl;
                        try{emCl = datos.getString("cli_email");}catch (Exception e){emCl="";}
                        String dirCl;
                        try{dirCl = datos.getString("cli_direccion");}catch (Exception e){dirCl="";}
                        String comCl;
                        try{comCl = datos.getString("cli_comuna");}catch (Exception e){comCl="";}
                        String ciuCl;
                        try{ciuCl = datos.getString("cli_ciudad");}catch (Exception e){ciuCl="";}
                        int sexCl;
                        try{sexCl = datos.getInt("cli_sexo");}catch (Exception e){sexCl=0;}
                        Date nacCl;
                        try{nacCl = datos.getDate("cli_nacimiento");}catch (Exception e){nacCl=new Date();}
                        int estaCl;
                        try{estaCl = datos.getInt("cli_estado");}catch (Exception e){estaCl=0;}
                        Date lastUpdCl;
                        try{lastUpdCl = datos.getDate("cli_last_update");}catch (Exception e){lastUpdCl=null;}
                        int lastHouCl;
                        try{lastHouCl = datos.getInt("cli_last_hour");}catch (Exception e){lastHouCl=0;}
                        
                        Cliente cliente = new Cliente(rutCl, nomCl, 
                                tel1Cl, tel2Cl, emCl, dirCl, comCl, ciuCl,
                                sexCl, nacCl, estaCl, lastUpdCl, lastHouCl);
                        /*--------------------------------------------------*/
                        /*----------------------DESPACHO--------------------*/
                        String codDs;
                        try{codDs = datos.getString("despacho_dsp_id");}catch (Exception e){codDs="";}
                        String rutDs;
                        try{rutDs = datos.getString("dsp_rut");}catch (Exception e){rutDs="";}
                        String nomDs;
                        try{nomDs = datos.getString("dsp_nombre");}catch (Exception e){nomDs="";}
                        Date fechDs;
                        try{fechDs = datos.getDate("dsp_fecha");}catch (Exception e){fechDs=null;}
                        String idFichDs;
                        try{idFichDs = datos.getString("ven_id");}catch (Exception e){idFichDs="";}
                        int estaDs;
                        try{estaDs = datos.getInt("dsp_estado");}catch (Exception e){estaDs=0;}
                        Date lastUpdDs;
                        try{lastUpdDs = datos.getDate("dsp_last_update");}catch (Exception e){lastUpdDs=null;}
                        int lastHouDs;
                        try{lastHouDs = datos.getInt("dsp_last_hour");}catch (Exception e){lastHouDs=0;}
                        
                        Despacho despacho = new Despacho(codDs, rutDs, nomDs, 
                                fechDs, idFichDs, estaDs, lastUpdDs, lastHouDs);
                        /*--------------------------------------------------*/
                        /*----------------------USER------------------------*/
                        int idUs;
                        try{idUs = datos.getInt("usuario_us_id");}catch (Exception e){idUs=0;}
                        String nomUs;
                        try{nomUs = datos.getString("us_nombre");}catch (Exception e){nomUs="";}
                        String usernUs;
                        try{usernUs = datos.getString("us_username");}catch (Exception e){usernUs="";}
                        String emUs;
                        try{emUs = datos.getString("us_email");}catch (Exception e){emUs="";}
                        String passUs;
                        try{passUs = datos.getString("us_pass");}catch (Exception e){passUs="";}
                        int tipUs;
                        try{tipUs = datos.getInt("us_tipo");}catch (Exception e){tipUs=0;}
                        int estaUs;
                        try{estaUs = datos.getInt("us_estado");}catch (Exception e){estaUs=0;}
                        Date lastUpdUs;
                        try{lastUpdUs = datos.getDate("us_last_update");}catch (Exception e){lastUpdUs=null;}
                        int lastHouUs;
                        try{lastHouUs = datos.getInt("us_last_hour");}catch (Exception e){lastHouUs=0;}
                        
                        User usuario = new User(idUs, nomUs, usernUs, emUs, 
                                passUs, tipUs, estaUs, lastUpdUs, lastHouUs);
                        /*--------------------------------------------------*/
                        lista.add(new Venta(
                                  datos.getString("ven_id")
                                , usuario
                                , cliente
                                , datos.getDate("ven_fecha")
                                , datos.getDate("ven_fecha_entrega")
                                , datos.getString("ven_lugar_entrega")
                                , datos.getString("ven_hora_entrega")
                                , datos.getString("ven_obs")
                                , datos.getInt("ven_valor_total")
                                , datos.getInt("ven_descuento")
                                , datos.getInt("ven_saldo")
                                , despacho
                                , datos.getInt("ven_estado")
                                , datos.getDate("ven_last_update")
                                , datos.getInt("ven_last_hour")
                                )
                        );
                    }
                    LcBd.cerrar();
                    return lista;
                    
                
            }
            if(type instanceof VentaDTO){
                String sql = "SELECT * FROM venta";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new VentaDTO(
                        datos.getString("ven_id"),
                        datos.getInt("usuario_us_id"),
                        datos.getString("cliente_cli_rut"),
                        datos.getDate("ven_fecha"),
                        datos.getDate("ven_fecha_entrega"),
                        datos.getString("ven_lugar_entrega"),
                        datos.getString("ven_hora_entrega"),
                        datos.getString("ven_obs"),
                        datos.getInt("ven_valor_total"),
                        datos.getInt("ven_descuento"),
                        datos.getInt("ven_saldo"),
                        datos.getInt("ven_estado"),
                        datos.getDate("ven_last_update"),
                        datos.getInt("ven_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Cliente){
                String sql = "SELECT * FROM cliente WHERE cli_rut='" + idParam + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM cliente WHERE cli_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM cliente WHERE cli_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM cliente";
                }
                if(idParam.equals("morosos")){
                    sql=" SELECT ven_id, "
                + "(SELECT cliente.cli_rut from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_rut, "
                + "(SELECT cliente.cli_nombre from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_nombre, "
                + "(SELECT cliente.cli_telefono1 from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_telefono1, "
                + "(SELECT cliente.cli_telefono2 from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_telefono2, "
                + "(SELECT cliente.cli_email from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_email, "
                + "(SELECT cliente.cli_direccion from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_direccion, "
                + "(SELECT cliente.cli_comuna from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_comuna, "
                + "(SELECT cliente.cli_ciudad from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_ciudad, "
                + "(SELECT cliente.cli_sexo from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_sexo, "
                + "(SELECT cliente.cli_nacimiento from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_nacimiento, "
                + "(SELECT cliente.cli_estado from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_estado, "
                + "(SELECT cliente.cli_last_update from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_last_update, "
                + "(SELECT cliente.cli_last_hour from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_last_hour "
                + "from venta where ven_saldo > 0 AND ven_fecha_entrega < '"+GV.dateToString(new Date(), "yyyy-mm-dd")+"'";
                }
                if(idParam.equals("retirar")){
                    sql=" SELECT ven_id, "
                + "(SELECT cliente.cli_rut from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_rut, "
                + "(SELECT cliente.cli_nombre from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_nombre, "
                + "(SELECT cliente.cli_telefono1 from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_telefono1, "
                + "(SELECT cliente.cli_telefono2 from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_telefono2, "
                + "(SELECT cliente.cli_email from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_email, "
                + "(SELECT cliente.cli_direccion from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_direccion, "
                + "(SELECT cliente.cli_comuna from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_comuna, "
                + "(SELECT cliente.cli_ciudad from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_ciudad, "
                + "(SELECT cliente.cli_sexo from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_sexo, "
                + "(SELECT cliente.cli_nacimiento from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_nacimiento, "
                + "(SELECT cliente.cli_estado from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_estado, "
                + "(SELECT cliente.cli_last_update from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_last_update, "
                + "(SELECT cliente.cli_last_hour from cliente where cliente.cli_rut=venta.cliente_cli_rut) as cli_last_hour "
                + "from venta where ven_estado="+VarUtils.estadoVentaPaid()+" AND (ven_fecha_entrega < '"+GV.dateToString(new Date(), "yyyy-mm-dd")+"' OR ven_fecha_entrega = '"+GV.dateToString(new Date(), "yyyy-mm-dd")+"')";
                }
                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    if(!GV.getStr(datos.getString("cli_rut")).isEmpty()){
                        lista.add(new Cliente(
                            datos.getString("cli_rut"),
                            datos.getString("cli_nombre"),
                            datos.getString("cli_telefono1"),
                            datos.getString("cli_telefono2"),
                            datos.getString("cli_email"),
                            datos.getString("cli_direccion"),
                            datos.getString("cli_comuna"),
                            datos.getString("cli_ciudad"),
                            datos.getInt("cli_sexo"),
                            datos.getDate("cli_nacimiento"),
                            datos.getInt("cli_estado"),
                            datos.getDate("cli_last_update"),
                            datos.getInt("cli_last_hour")
                            )
                        );
                    }
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Descuento){
                String sql = "SELECT * FROM descuento WHERE des_nombre='" + idParam + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM descuento WHERE des_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM descuento WHERE des_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM descuento";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Descuento(
                        datos.getInt("des_id"),
                        datos.getString("des_nombre"),
                        datos.getString("des_descripcion"),
                        datos.getInt("des_porc"),
                        datos.getInt("des_monto"),
                        datos.getInt("des_estado"),
                        datos.getDate("des_last_update"),
                        datos.getInt("des_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Detalle){
                String sql = "SELECT * FROM detalle WHERE det_id='" + idParam + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM detalle WHERE det_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM detalle WHERE det_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM detalle";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Detalle(
                        datos.getString("det_id"),
                        datos.getString("venta_ven_id"),
                        datos.getString("item_itm_id"),
                        datos.getInt("det_cantidad"),
                        datos.getInt("det_precio_unit"),
                        datos.getInt("det_estado"),
                        datos.getDate("det_last_update"),
                        datos.getInt("det_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Despacho){
                String sql = "SELECT * FROM despacho WHERE venta_ven_id='" + idParam + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM despacho WHERE dsp_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM despacho WHERE dsp_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM despacho";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Despacho(
                        datos.getString("dsp_id"),
                        datos.getString("dsp_rut"),
                        datos.getString("dsp_nombre"),
                        datos.getDate("dsp_fecha"),
                        datos.getString("venta_ven_id"),
                        datos.getInt("dsp_estado"),
                        datos.getDate("dsp_last_update"),
                        datos.getInt("dsp_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof Equipo){
                
                String sql = "SELECT * FROM equipo WHERE eq_id =" + idParam + "";
                if(!GV.isNumeric(idParam)){
                    sql = "SELECT * FROM equipo WHERE eq_nombre ='" + idParam + "'";
                }
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM equipo WHERE eq_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM equipo WHERE eq_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM equipo";
                }
                if(idParam.contains("_")){
                    sql = "SELECT * FROM equipo WHERE eq_nombre LIKE '" + idParam.substring(0, idParam.indexOf("_")) + "%'";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Equipo(
                        datos.getInt("eq_id"),
                        datos.getString("eq_nombre"),
                        datos.getString("eq_licencia"),
                        datos.getString("eq_bd"),
                        datos.getString("eq_bd_user"),
                        datos.getString("eq_bd_pass"),
                        datos.getString("eq_bd_url"),
                        datos.getInt("eq_estado"),
                        datos.getDate("eq_last_update"),
                        datos.getInt("eq_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof HistorialPago){
                String idVenta = null;
                if(!idParam.isEmpty() && idParam.contains("<") && idParam.substring(idParam.indexOf("<")+1).contains(">")){
                    idVenta = idParam.substring(idParam.indexOf("<")+1,idParam.indexOf(">")).trim();
                }
                String sql = "SELECT * FROM historial_pago WHERE hp_id ='" + idParam + "'";
                if(idVenta != null && GV.getStr(idVenta).isEmpty()){
                    idVenta = null;
                    idParam = "0";
                }
                sql = (idVenta != null) ? "SELECT * FROM historial_pago WHERE venta_ven_id ='" + idVenta + "'":sql;
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM historial_pago WHERE hp_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM historial_pago WHERE hp_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM historial_pago";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new HistorialPago(
                        datos.getString("hp_id"),
                        datos.getDate("hp_fecha"),
                        datos.getInt("hp_abono"),
                        datos.getInt("tipo_pago_tp_id"),
                        datos.getString("venta_ven_id"),
                        datos.getInt("hp_estado"),
                        datos.getDate("hp_last_update"),
                        datos.getInt("hp_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof Inventario){
                String sql = "SELECT * FROM inventario WHERE LOWER(inv_nombre) = '" + idParam.toLowerCase() + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM inventario WHERE inv_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM inventario WHERE inv_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM inventario";
                }
                if(idParam.startsWith("BY_ID/")){
                    sql = "SELECT * FROM inventario WHERE inv_id = " + idParam.replaceAll("BY_ID/", "");
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Inventario(
                        datos.getInt("inv_id"),
                        datos.getString("inv_nombre"),
                        datos.getString("inv_descripcion"),
                        datos.getInt("inv_estado"),
                        datos.getDate("inv_last_update"),
                        datos.getInt("inv_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof Item){
                String sql = "SELECT * FROM item WHERE itm_id ='" + idParam + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM item WHERE itm_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM item WHERE itm_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM item";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Item(
                        datos.getString("itm_id"),
                        datos.getString("itm_foto"),
                        datos.getString("itm_marca"),
                        datos.getInt("itm_clasificacion"),
                        datos.getString("itm_descripcion"),
                        datos.getInt("itm_precio_ref"),
                        datos.getInt("itm_precio_act"),
                        datos.getInt("itm_stock"),
                        datos.getInt("itm_stock_min"),
                        datos.getInt("inventario_inv_id"),
                        datos.getInt("itm_estado"),
                        datos.getDate("itm_last_update"),
                        datos.getInt("itm_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof InternMail){
                String sql = "SELECT * FROM message WHERE msg_id =" + idParam + "";
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM message";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    User rem = (User)getElement(null, datos.getInt("us_id_remitente"), new User());
                    User des = (User)getElement(null, datos.getInt("us_id_destinatario"), new User());
                    lista.add(new InternMail(
                        datos.getInt("msg_id"),
                        rem,
                        des,
                        datos.getString("msg_asunto"),
                        datos.getString("msg_content"),
                        datos.getDate("msg_fecha"),
                        datos.getString("msg_hora"),
                        datos.getInt("msg_estado"),
                        datos.getDate("msg_last_update"),
                        datos.getInt("msg_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof Oficina) {
                String sql = "SELECT * FROM oficina WHERE LOWER(of_nombre)='" + idParam.toLowerCase() + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM oficina WHERE of_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM oficina WHERE of_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM oficina";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Oficina(
                            datos.getInt("of_id"),
                             datos.getString("of_nombre"),
                             datos.getString("of_direccion"),
                             datos.getString("of_ciudad"),
                             datos.getString("of_telefono1"),
                             datos.getString("of_telefono2"),
                             datos.getString("of_email"),
                             datos.getString("of_web"),
                             datos.getInt("of_estado"),
                             datos.getDate("of_last_update"),
                             datos.getInt("of_last_hour")
                    )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof Proveedor) {
                String sql = "SELECT * FROM proveedor WHERE pro_id='" + idParam.toUpperCase() + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM proveedor WHERE pro_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM proveedor WHERE pro_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM proveedor";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Proveedor(
                            datos.getString("pro_id"),
                            datos.getString("pro_nombre"),
                            datos.getString("pro_telefono"),
                            datos.getString("pro_mail"),
                            datos.getString("pro_web"),
                            datos.getString("pro_direccion"),
                            datos.getString("pro_comuna"),
                            datos.getString("pro_ciudad"),
                            datos.getInt("pro_estado"),
                            datos.getDate("pro_last_update"),
                            datos.getInt("pro_last_hour")
                    )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof RegistroBaja) {
                String sql = "SELECT * FROM registro_bajas WHERE rb_id='" + idParam + "'";
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM registro_bajas WHERE rb_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM registro_bajas WHERE rb_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM registro_bajas";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new RegistroBaja(
                            datos.getString("rb_id"),
                            datos.getDate("rb_fecha"),
                            datos.getString("item_itm_id"),
                            datos.getInt("rb_cantidad"),
                            datos.getString("rb_obs"),
                            datos.getInt("rb_estado"),
                            datos.getDate("rb_last_update"),
                            datos.getInt("rb_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof TipoPago) {
                String sql = "SELECT * FROM tipo_pago WHERE tp_nombre='" + idParam + "'";
                sql = (GV.isNumeric(idParam))? "SELECT * FROM tipo_pago WHERE tp_id=" + idParam:sql;
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM tipo_pago WHERE tp_estado=1 AND tp_id <> 1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM tipo_pago WHERE tp_estado=0 AND tp_id <> 1";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM tipo_pago";
                }

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new TipoPago(
                            datos.getInt("tp_id"),
                            datos.getString("tp_nombre"),
                            datos.getInt("tp_estado"),
                            datos.getDate("tp_last_update"),
                            datos.getInt("tp_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof User){
                String sql = "SELECT * FROM usuario WHERE us_username='" + idParam + "'";
                if(!idParam.equals("0") && GV.isNumeric(idParam)){
                    sql = "SELECT * FROM usuario WHERE us_id=" + idParam + "";
                }
                if (idParam.equals("0")) {
                    sql = "SELECT * FROM usuario WHERE us_estado=1";
                }
                if (idParam.equals("-1")) {
                    sql = "SELECT * FROM usuario WHERE us_estado=0";
                }
                if (idParam.equals("-2")) {
                    sql = "SELECT * FROM usuario";
                }
                boolean isOpenBD = (LcBd.isOpen())?true:false;
                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new User(
                        datos.getInt("us_id"),
                        datos.getString("us_nombre"),
                        datos.getString("us_username"),
                        datos.getString("us_email"),
                        datos.getString("us_pass"),
                        datos.getInt("us_tipo"),
                        datos.getInt("us_estado"),
                        datos.getDate("us_last_update"),
                        datos.getInt("us_last_hour")
                        )
                    );
                }
                /*
                Consulta si la conexion estaba abierta antes de rescatar los datos
                para no cerrarla por que se está usando en listar mensajes donde se
                están rescatando estos usuarios
                */
                if(!isOpenBD){
                   LcBd.cerrar();
                }
                
                return lista;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
            Notification.showMsg("Error de conexión", "El sistema está teniendo errores al conectarse a la base de datos "
                    + ""+className+",\n pruebe cerrando todos los procesos y vuelva a intentar, \nde lo contrario reinicie el equipo."
                            + "\n\nDetalle: "+ex, 3);
        }
        
        return lista;
    }
    /**
     * Retorna una lista de objetos comparndo por la ultima fecha de actualización con
     * paramDate y tipo de objeto con type
     * @param paramDate
     * @param type
     * @return 
     */
    @Override
    public ArrayList<Object> listar(Date paramDate, Object type) {
        
        java.sql.Date param = new java.sql.Date(paramDate.getTime());
        ArrayList<Object> lista = new ArrayList<>();
        try {
            LcBd.cerrar();
            if(type instanceof Venta || type instanceof VentaDTO){
                String sql = "SELECT * FROM venta WHERE ven_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new VentaDTO(
                        datos.getString("ven_id"),
                        datos.getInt("usuario_us_id"),
                        datos.getString("cliente_cli_rut"),
                        datos.getDate("ven_fecha"),
                        datos.getDate("ven_fecha_entrega"),
                        datos.getString("ven_lugar_entrega"),
                        datos.getString("ven_hora_entrega"),
                        datos.getString("ven_obs"),
                        datos.getInt("ven_valor_total"),
                        datos.getInt("ven_descuento"),
                        datos.getInt("ven_saldo"),
                        datos.getInt("ven_estado"),
                        datos.getDate("ven_last_update"),
                        datos.getInt("ven_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Cliente){
                String sql = "SELECT * FROM cliente WHERE cli_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Cliente(
                        datos.getString("cli_rut"),
                        datos.getString("cli_nombre"),
                        datos.getString("cli_telefono1"),
                        datos.getString("cli_telefono2"),
                        datos.getString("cli_email"),
                        datos.getString("cli_direccion"),
                        datos.getString("cli_comuna"),
                        datos.getString("cli_ciudad"),
                        datos.getInt("cli_sexo"),
                        datos.getDate("cli_nacimiento"),
                        datos.getInt("cli_estado"),
                        datos.getDate("cli_last_update"),
                        datos.getInt("cli_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Descuento){
                String sql = "SELECT * FROM descuento WHERE des_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Descuento(
                        datos.getInt("des_id"),
                        datos.getString("des_nombre"),
                        datos.getString("des_descripcion"),
                        datos.getInt("des_porc"),
                        datos.getInt("des_monto"),
                        datos.getInt("des_estado"),
                        datos.getDate("des_last_update"),
                        datos.getInt("des_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Detalle){
                String sql = "SELECT * FROM detalle WHERE det_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Detalle(
                        datos.getString("det_id"),
                        datos.getString("venta_ven_id"),
                        datos.getString("item_itm_id"),
                        datos.getInt("det_cantidad"),
                        datos.getInt("det_precio_unit"),
                        datos.getInt("det_estado"),
                        datos.getDate("det_last_update"),
                        datos.getInt("det_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Despacho){
                String sql = "SELECT * FROM despacho WHERE dsp_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Despacho(
                        datos.getString("dsp_id"),
                        datos.getString("dsp_rut"),
                        datos.getString("dsp_nombre"),
                        datos.getDate("dsp_fecha"),
                        datos.getString("venta_ven_id"),
                        datos.getInt("dsp_estado"),
                        datos.getDate("dsp_last_update"),
                        datos.getInt("dsp_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Equipo){
                String sql = "SELECT * FROM equipo WHERE eq_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Equipo(
                        datos.getInt("eq_id"),
                        datos.getString("eq_nombre"),
                        datos.getString("eq_licencia"),
                        datos.getString("eq_bd"),
                        datos.getString("eq_bd_user"),
                        datos.getString("eq_bd_pass"),
                        datos.getString("eq_bd_url"),
                        datos.getInt("eq_estado"),
                        datos.getDate("eq_last_update"),
                        datos.getInt("eq_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof HistorialPago){
                String sql = "SELECT * FROM historial_pago WHERE hp_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new HistorialPago(
                        datos.getString("hp_id"),
                        datos.getDate("hp_fecha"),
                        datos.getInt("hp_abono"),
                        datos.getInt("tipo_pago_tp_id"),
                        datos.getString("venta_ven_id"),
                        datos.getInt("hp_estado"),
                        datos.getDate("hp_last_update"),
                        datos.getInt("hp_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Inventario){
                String sql = "SELECT * FROM inventario WHERE inv_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Inventario(
                        datos.getInt("inv_id"),
                        datos.getString("inv_nombre"),
                        datos.getString("inv_descripcion"),
                        datos.getInt("inv_estado"),
                        datos.getDate("inv_last_update"),
                        datos.getInt("inv_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Item){
                String sql = "SELECT * FROM item WHERE itm_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Item(
                        datos.getString("itm_id"),
                        datos.getString("itm_foto"),
                        datos.getString("itm_marca"),
                        datos.getInt("itm_clasificacion"),
                        datos.getString("itm_descripcion"),
                        datos.getInt("itm_precio_ref"),
                        datos.getInt("itm_precio_act"),
                        datos.getInt("itm_stock"),
                        datos.getInt("itm_stock_min"),
                        datos.getInt("inventario_inv_id"),
                        datos.getInt("itm_estado"),
                        datos.getDate("itm_last_update"),
                        datos.getInt("itm_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof InternMail){
                String sql = "SELECT * FROM message WHERE msg_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                int us_id_remitente = 0;
                int us_id_destinatario = 0;
                while (datos.next()) {
                    us_id_remitente = datos.getInt("us_id_remitente");
                    us_id_destinatario = datos.getInt("us_id_destinatario");
                    User rem = (User)getElement(null, us_id_remitente , new User());
                    User des = (User)getElement(null, us_id_destinatario , new User());
                    lista.add(new InternMail(
                        datos.getInt("msg_id"),
                        rem,
                        des,
                        datos.getString("msg_asunto"),
                        datos.getString("msg_content"),
                        datos.getDate("msg_fecha"),
                        datos.getString("msg_hora"),
                        datos.getInt("msg_estado"),
                        datos.getDate("msg_last_update"),
                        datos.getInt("msg_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof Oficina) {
                String sql = "SELECT * FROM oficina WHERE of_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Oficina(
                        datos.getInt("of_id"),
                        datos.getString("of_nombre"),
                        datos.getString("of_direccion"),
                        datos.getString("of_ciudad"),
                        datos.getString("of_telefono1"),
                        datos.getString("of_telefono2"),
                        datos.getString("of_email"),
                        datos.getString("of_web"),
                        datos.getInt("of_estado"),
                        datos.getDate("of_last_update"),
                        datos.getInt("of_last_hour")
                    )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof Proveedor){
                String sql = "SELECT * FROM proveedor WHERE pro_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new Proveedor(
                            datos.getString("pro_id"),
                            datos.getString("pro_nombre"),
                            datos.getString("pro_telefono"),
                            datos.getString("pro_mail"),
                            datos.getString("pro_web"),
                            datos.getString("pro_direccion"),
                            datos.getString("pro_comuna"),
                            datos.getString("pro_ciudad"),
                            datos.getInt("pro_estado"),
                            datos.getDate("pro_last_update"),
                            datos.getInt("pro_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof RegistroBaja) {
                String sql = "SELECT * FROM registro_bajas WHERE rb_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new RegistroBaja(
                            datos.getString("rb_id"),
                            datos.getDate("rb_fecha"),
                            datos.getString("lente_len_id"),
                            datos.getInt("rb_cantidad"),
                            datos.getString("rb_obs"),
                            datos.getInt("rb_estado"),
                            datos.getDate("rb_last_update"),
                            datos.getInt("rb_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if (type instanceof TipoPago) {
                String sql = "SELECT * FROM tipo_pago WHERE tp_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new TipoPago(
                            datos.getInt("tp_id"),
                            datos.getString("tp_nombre"),
                            datos.getInt("tp_estado"),
                            datos.getDate("tp_last_update"),
                            datos.getInt("tp_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
            if(type instanceof User){
                String sql = "SELECT * FROM usuario WHERE us_last_update >='" + param + "'";

                PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    lista.add(new User(
                        datos.getInt("us_id"),
                        datos.getString("us_nombre"),
                        datos.getString("us_username"),
                        datos.getString("us_email"),
                        datos.getString("us_pass"),
                        datos.getInt("us_tipo"),
                        datos.getInt("us_estado"),
                        datos.getDate("us_last_update"),
                        datos.getInt("us_last_hour")
                        )
                    );
                }
                LcBd.cerrar();
                return lista;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
            Notification.showMsg("Error al conectar con base de datos "+className, ""+ex, 3);
        }
        return lista;
    }
    /**
     * @param cod 
     * @param id
     * @param type
     * Tipo de clase que se desea retornar
     * @return Retorna la clase de tipo Object, luego sólo se debe parsear.
     */
    @Override
    public Object getElement(String cod,int id, Object type) {
        
        if(cod == null && id == 0){
            return null;
        }
        try{
            cod = (cod == null)?""+id:cod;
            if(type instanceof Equipo){
                if(cod != null){
                    if(cod.contains("_") && !cod.startsWith("_") && cod.length() > 2){
                        for (Object object : listar(cod, type)) {
                            if (((Equipo) object).getNombre().contains(cod.substring(0, cod.indexOf("_")))) {
                                return object;
                            }
                        }
                    }else{
                        for (Object object : listar(cod, type)) {
                            if (((Equipo) object).getNombre().contains(cod)) {
                                return object;
                            }
                        }
                    }
                }else{
                    for (Object object : listar(""+id, type)) {//id debe ser el rut del doctor
                        if (((Equipo) object).getId() == id) {
                            return object;
                        }
                    }
                }
                return null;
            }
            if(type instanceof Inventario){
                if(GV.isNumeric(cod.replaceAll("BY_ID/", ""))){
                    for (Object object : listar(cod, type)) {
                        if (((Inventario) object).getId() == GV.strToNumber(cod)) {
                            return object;
                        }
                    }
                }else{
                    for (Object object : listar(cod, type)) {
                        if (((Inventario) object).getNombre().toLowerCase().equals(cod.toLowerCase())) {
                            return object;
                        }
                    }
                }
                return null;
            }
            if (type instanceof Oficina) {
                for (Object object : listar(cod, type)) {//id debe ser el id de la venta
                    if (((Oficina) object).getNombre().toLowerCase().equals(cod.toLowerCase())) {
                        return object;
                    }
                }
                return null;
            }
            if (type instanceof TipoPago) {
                if(cod == null){
                    for (Object object : listar(""+id, type)) {//cod es el nombre de la entidad
                        if (((TipoPago) object).getId() == id) {
                            return object;
                        }
                    }
                }else{
                    for (Object object : listar(cod, type)) {//cod es el nombre de la entidad
                        if (((TipoPago) object).getNombre().trim().toLowerCase().equals(cod.toLowerCase())) {
                            return object;
                        }
                    }
                }   
                return null;
            }
            if(type instanceof User){
                if(cod == null){
                    for (Object object : listar(""+id, type)) {
                        if (((User) object).getId() == id) {
                            return object;
                        }
                    }
                }else{
                    for (Object object : listar(cod, type)) {
                        if (((User) object).getUsername().trim().toLowerCase().equals(cod.toLowerCase())) {
                            return object;
                        }
                    }
                }
                return null;
            }
            return GV.searchInList(cod, listar(cod, type), type);
        }catch(Exception ex){
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
        }
        Notification.showMsg("Instancia no encontrada", "No se encuentra la instancia, se retornará un valor vacío,"
                + "\nUbicación:"+ className, 3);
        return null;
    }
    /**
     * Compara por los siguientes atributos:
     * Cliente=>rut,
     * Cristal=>nombre,
     * Descuento=>nombre,
     * Doctor=>rut,
     * Lente=>cod,
     * Oficina=>nombre,
     * RegistroBaja=>cod,
     * User=>username,
     * Institucion=>id,
     * TipoPago=>nombre
     * @param object
     * @return 
     */
    @Override
    public boolean exist(Object object) {
        
        if (object instanceof SyncCodClass) {
            
            return (getElement(((SyncCodClass) object).getCod(),0,object) != null);
        }
        if (object instanceof SyncIntIdValidaName) {
            
            return (getElement(((SyncIntIdValidaName) object).getNombre(),0,object) != null);
        }
        if (object instanceof SyncIntId) {
            
            return (getElement(null,((SyncIntId) object).getId(),object) != null);
        }
        if (object instanceof SyncStringId) {
            
            return (getElement(((SyncStringId) object).getCod(),0,object) != null);
        }
        return false;
    }
    
    public String sqlInsert(Object objectParam){
        return BDUtils.getSqlInsert(objectParam);
    }

    private String sqlUpdate(Object objectParam) {
        return BDUtils.getSqlUpdate(objectParam);
    }
    
    public ArrayList<InternMail> mensajes(int remitente, int destinatario, int estado) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        ArrayList<InternMail> lista = new ArrayList<>();
        String sql = "";
        if(remitente > 0){
            sql = "SELECT msg_id,us_id_remitente,us_id_destinatario,msg_asunto, msg_content,msg_fecha,msg_hora, msg_estado, msg_last_update, msg_last_hour,"
                    + "(SELECT us_username FROM usuario WHERE us_id = us_id_remitente) as rem,"
                    + "(SELECT us_username FROM usuario WHERE us_id = us_id_destinatario) as des "
                    + "FROM message WHERE us_id_remitente = "+remitente+" AND msg_estado > 0";
            if(estado > 0)
                sql = "SELECT msg_id,us_id_remitente,us_id_destinatario,msg_asunto, msg_content,msg_fecha,msg_hora, msg_estado, msg_last_update, msg_last_hour,"
                        + ",(SELECT us_username FROM usuario WHERE us_id = us_id_remitente) as rem,"
                        + "(SELECT us_username FROM usuario WHERE us_id = us_id_destinatario) as des "
                        + "FROM message WHERE us_id_remitente = " + remitente + " AND msg_estado = "+estado;
        }else{
            sql = "SELECT msg_id,us_id_remitente,us_id_destinatario,msg_asunto, msg_content,msg_fecha,msg_hora, msg_estado, msg_last_update, msg_last_hour,"
                    + "(SELECT us_username FROM usuario WHERE us_id = us_id_remitente) as rem,"
                    + "(SELECT us_username FROM usuario WHERE us_id = us_id_destinatario) as des "
                    + "FROM message WHERE us_id_destinatario = " + destinatario + " AND msg_estado > 0";
            if(estado > 0)
                sql = "SELECT msg_id,us_id_remitente,us_id_destinatario,msg_asunto, msg_content,msg_fecha,msg_hora, msg_estado, msg_last_update, msg_last_hour,"
                        + "(SELECT us_username FROM usuario WHERE us_id = us_id_remitente) as rem,"
                        + "(SELECT us_username FROM usuario WHERE us_id = us_id_destinatario) as des "
                        + "FROM message WHERE us_id_destinatario = " + destinatario + " AND msg_estado = "+estado;
        }
        PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
        ResultSet datos = consulta.executeQuery();
        while (datos.next()) {
            User rem = (User)getElement(datos.getString("rem"), 0, new User());
            User des = (User)getElement(datos.getString("des"), 0, new User());
            lista.add(new InternMail(
                datos.getInt("msg_id"),
                rem,
                des,
                datos.getString("msg_asunto"),
                datos.getString("msg_content"),
                datos.getDate("msg_fecha"),
                datos.getString("msg_hora"),
                datos.getInt("msg_estado"),
                datos.getDate("msg_last_update"),
                datos.getInt("msg_last_hour")
                )
            );
        }
        LcBd.cerrar();
        return lista;
    }

    private String getSqlVenta() {
        return "SELECT " +
                "ven_id, ven_fecha, ven_fecha_entrega, ven_lugar_entrega, ven_hora_entrega," +
                "ven_obs, ven_valor_total, ven_descuento, ven_saldo, cliente_cli_rut, usuario_us_id, ven_estado," +
                "ven_last_update, ven_last_hour," +
                "cliente.cli_nombre," +
                "cliente.cli_telefono1," +
                "cliente.cli_telefono2," +
                "cliente.cli_email," +
                "cliente.cli_direccion," +
                "cliente.cli_comuna," +
                "cliente.cli_ciudad," +
                "cliente.cli_sexo," +
                "cliente.cli_nacimiento," +
                "cliente.cli_estado," +
                "cliente.cli_last_update," +
                "cliente.cli_last_hour," +
                "despacho.dsp_rut," +
                "despacho.dsp_nombre," +
                "despacho.dsp_fecha," +
                "despacho.dsp_estado," +
                "despacho.dsp_last_update," +
                "despacho.dsp_last_hour," +
                "usuario.us_nombre," +
                "usuario.us_username," +
                "usuario.us_email," +
                "usuario.us_pass," +
                "usuario.us_tipo," +
                "usuario.us_estado," +
                "usuario.us_last_update," +
                "usuario.us_last_hour," +
                " FROM venta" +
                " LEFT JOIN cliente ON cliente.cli_rut = venta.cliente_cli_rut" +
                " LEFT JOIN despacho ON despacho.venta_ven_id = venta.ven_id" +
                " LEFT JOIN usuario ON usuario.us_id = venta.usuario_us_id";
    }
    
    public boolean sincronizar(String externBdSqlSync){
        try {
            PreparedStatement insert = LcBd.obtener().prepareStatement(
                    externBdSqlSync
            );
            if (insert.executeUpdate() != 0) {
                LcBd.cerrar();
                return true;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Remote.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * modifica el objeto previa validacion de lastUpdate y lastHour
     * @param object
     * @return 
     */
    public boolean updateFromDao(Object object){
        PreparedStatement update;
        try {
            update = LcBd.obtener().prepareStatement(
                    sqlUpdate(object));
            update.executeUpdate();
            LcBd.cerrar();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Remote.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
    
    public boolean insertFromDao(String sql){
        PreparedStatement insert;
        try {
            insert = LcBd.obtener().prepareStatement(sql);
            insert.executeUpdate();
            LcBd.cerrar();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
            Notification.showMsg(ex.getMessage()+"\nNo se pudo ejecutar la consulta", sql, 3);
            Notification.closeInfoPanel();
            return false;
        }
        return true;
    }
}
