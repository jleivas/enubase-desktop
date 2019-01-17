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
public class HistorialPago extends SyncCodClass{
    
    private Date fecha;
    private int abono;
    private int idTipoPago;
    private String idVenta;
    

    public HistorialPago() {
    }

    public HistorialPago(String cod, Date fecha, int abono, 
            int tipoPago, String idVenta,int estado, Date lastUpdate, int lastHour) {
        setCod(cod);
        setFecha(fecha);
        setAbono(abono);
        setIdVenta(idVenta);
        setIdTipoPago(tipoPago);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    

    public void setIdTipoPago(int idTipoPago) {
        this.idTipoPago = idTipoPago;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = getStr(idVenta);
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setAbono(int abono) {
        this.abono = abono;
    }

    public Date getFecha() {
        return fecha;
    }

    public int getAbono() {
        return abono;
    }

    public int getIdTipoPago() {
        return idTipoPago;
    }  
    
    public String getIdVenta() {
        return idVenta;
    }
    
    @Override
    public String toString() {
        return "Despacho{" + "cod=" + getCod()
                + ", fecha=" + fecha 
                + ", abono=" + abono 
                + ", idTipoPago=" + idTipoPago 
                + ", idVenta=" + idVenta 
                + ", estado=" + getEstado() 
                + ", lastUpdate=" + getLastUpdate() + '}';
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha1 = new java.sql.Date(getFecha().getTime());
        java.sql.Date sqlfecha2 = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"','"+sqlfecha1+"',"+getAbono()+","+getIdTipoPago()+",'"+getIdVenta()
                + "',"+getEstado()+",'"+sqlfecha2+"',"+getLastHour()+")";
    }
}
