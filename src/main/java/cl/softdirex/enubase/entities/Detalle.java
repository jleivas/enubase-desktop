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
 * @author sdx
 */
public class Detalle extends SyncCodClass{
    private String idVenta;
    private String idItem;
    private int cantidad;
    private int precioUnitario;

    public Detalle() {
    }

    public Detalle(String cod,String idVenta, String idItem, int cantidad, int precioUnitario,
            int estado,Date lastUpdate,int lastHour) {
        setCod(cod);
        setIdVenta(idVenta);
        setIdItem(idItem);
        setCantidad(cantidad);
        setPrecioUnitario(precioUnitario);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    public String getIdVenta() {
        return idVenta;
    }

    public String getIdItem() {
        return idItem;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getPrecioUnitario() {
        return precioUnitario;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecioUnitario(int precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"','"+getIdVenta()+"','"+getIdItem()+"',"+getCantidad()+","+getPrecioUnitario()
                + ","+getEstado()+",'"+sqlfecha+"',"+getLastHour()+")";
    }
}
