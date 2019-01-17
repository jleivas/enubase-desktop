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
 * @author home
 */
public class RegistroBaja extends SyncCodClass{
    private Date fecha;
    private String idItem;
    private int cantidad;
    private String obs;

    public RegistroBaja() {
    }

    public RegistroBaja(String cod, Date fecha, String idItem, int cantidad,
            String obs, int estado, Date lastUpdate, int lastHour) {
        setCod(cod);
        setFecha(fecha);
        setIdItem(idItem);
        setCantidad(cantidad);
        setObs(obs);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setIdItem(String idItem) {
        this.idItem = getStr(idItem);
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setObs(String obs) {
        this.obs = getStr(obs);
    }

    public Date getFecha() {
        return fecha;
    }

    public String getIdItem() {
        return getStr(idItem);
    }

    public int getCantidad() {
        return cantidad;
    }

    public String getObs() {
        return getStr(obs);
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha1 = new java.sql.Date(getFecha().getTime());
        java.sql.Date sqlfecha2 = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"','"+sqlfecha1+"','"+getIdItem()+"',"+getCantidad()+",'"+getObs()
                + "',"+getEstado()+",'"+sqlfecha2+"',"+getLastHour()+")";
    }
}
