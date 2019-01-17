/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.entities;

import cl.softdirex.enubase.entities.abstractclasses.SyncCodClass;
import java.util.Date;

/**
 *
 * @author Lenovo G470
 */
public class Despacho extends SyncCodClass{
    
    private String rut;
    private String nombre;
    private Date fecha;
    private String idVenta;

    public Despacho() {
    }

    public Despacho(String cod, String rut, String nombre, Date fecha, 
            String idVenta, int estado, Date lastUpdate, int lastHour) {
        setCod(cod);
        setRut(rut);
        setNombre(nombre);
        setFecha(fecha);
        setIdVenta(idVenta);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    public void setRut(String rut) {
        this.rut = getStr(rut);
    }

    public void setNombre(String nombre) {
        this.nombre = getStr(nombre);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = getStr(idVenta);
    }

    public String getRut() {
        return rut;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getIdVenta() {
        return idVenta;
    }

    @Override
    public String toString() {
        return "Despacho{" + "cod=" + getCod()
                + ", rut=" + rut 
                + ", nombre=" + nombre 
                + ", fecha=" + fecha 
                + ", idVenta=" + idVenta + '}';
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha1 = new java.sql.Date(getFecha().getTime());
        java.sql.Date sqlfecha2 = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"','"+getRut()+"','"+getNombre()+"','"+sqlfecha1+"','"+getIdVenta()
                + "',"+getEstado()+",'"+sqlfecha2+"',"+getLastHour()+")";
    }
}
