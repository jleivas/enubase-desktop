/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.dao.Dao;
import cl.softdirex.enubase.entities.Oficina;
import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.entities.Venta;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class StEntities {
    public static User USER;
    public static Oficina OFICINA;
    public static Venta OPEN_VENTA= new Venta();
    
    public static void setOficina(Oficina oficina){
        OFICINA = oficina;
    }
    
    public static boolean setOficina(String nombre){
        Dao load = new Dao();
        try {
            Oficina temp = (Oficina)load.get(GV.getStr(nombre), 0, new Oficina());
            if(temp != null){
                setOficina(temp);
                return true;
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(StEntities.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static String getNombreOficina() {
        if(OFICINA != null){
            if(!OFICINA.getNombre().isEmpty()){
                return OFICINA.getNombre();
            }
        }
        return "Ninguna";
    }
    
    public static String getLblNombreOficina() {
        if(OFICINA != null){
            if(!OFICINA.getNombre().isEmpty()){
                return "Asignada a este equipo: "+OFICINA.getNombre();
            }
        }
        return "Asignada a este equipo: Ninguna";
    }

    static int getTipoUsuario() {
        if(USER != null){
            return USER.getTipo();
        }
        return 0;
    }
    
    public static void setSessionUser(User user){
        USER = user;
    }
    
    public static User getSessionUser(){
        return USER;
    }

    public static String getOficinaWeb() {
        if(OFICINA != null){
            if(!OFICINA.getWeb().isEmpty()){
                return OFICINA.getWeb();
            }
        }
        return "Sin dirección web";
    }

    public static String getOficinaAddress() {
        if(OFICINA != null){
            if(!OFICINA.getDireccion().isEmpty()){
                return OFICINA.getDireccion();
            }
        }
        return "no registrada";
    }

    public static String getOficinaCity() {
       if(OFICINA != null){
            if(!OFICINA.getCiudad().isEmpty()){
                return OFICINA.getCiudad();
            }
        }
        return "no registrada";
    }

    public static String getOficinaMail() {
        if(OFICINA != null){
            if(!OFICINA.getEmail().isEmpty()){
                return OFICINA.getEmail();
            }
        }
        return "no registrado";
    }

    public static String getOficinaPhone1() {
        if(OFICINA != null){
            if(!OFICINA.getTelefono1().isEmpty()){
                return OFICINA.getTelefono1();
            }
        }
        return "no registrado";
    }

    public static String getOficinaPhone2() {
        if(OFICINA != null){
            if(!OFICINA.getTelefono2().isEmpty()){
                return OFICINA.getTelefono2();
            }
        }
        return "no registrado";
    }

    public static void setOpenVenta(Venta venta) {
        OPEN_VENTA = venta;
    }
}
