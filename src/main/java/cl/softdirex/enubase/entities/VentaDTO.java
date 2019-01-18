/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.entities;

import cl.softdirex.enubase.entities.abstractclasses.SyncCodClass;
import cl.softdirex.enubase.utils.GV;
import java.util.Date;

/**
 *
 * @author sdx
 */
public class VentaDTO extends SyncCodClass{
    private int idVendedor;
    private String rutCliente;
    private Date fecha;
    private Date fechaEntrega;
    private String lugarEntrega;
    private String horaEntrega;
    private String observacion;
    private int valorTotal;
    private int descuento;
    private int saldo;

    public VentaDTO() {
    }
    
    public VentaDTO(Venta venta){
        setCod(venta.getCod());
        int idVendedor = (venta.getVendedor()!=null)?venta.getVendedor().getId():0;
        setIdVendedor(idVendedor);
        String rutCliente = (venta.getCliente()!=null)?(GV.getStr(venta.getCliente().getCod())):"";
        setRutCliente(rutCliente);
        setFecha(venta.getFecha());
        setFechaEntrega(venta.getFechaEntrega());
        setLugarEntrega(venta.getLugarEntrega());
        setHoraEntrega(venta.getHoraEntrega());
        setObservacion(venta.getObservacion());
        setValorTotal(venta.getValorTotal());
        setDescuento(venta.getDescuento());
        setSaldo(venta.getSaldo());
        setEstado(venta.getEstado());
        setLastUpdate(venta.getLastUpdate());
        setLastHour(venta.getLastHour());
    }

    public VentaDTO(String cod,int idVendedor, String rutCliente, 
            Date fecha, Date fechaEntrega, String lugarEntrega, String horaEntrega, 
            String observacion, int valorTotal, int descuento, int saldo,
            int estado, Date lastUpdate, int lastHour) {
        setCod(cod);
        setIdVendedor(idVendedor);
        setRutCliente(rutCliente);
        setFecha(fecha);
        setFechaEntrega(fechaEntrega);
        setLugarEntrega(lugarEntrega);
        setHoraEntrega(horaEntrega);
        setObservacion(observacion);
        setValorTotal(valorTotal);
        setDescuento(descuento);
        setSaldo(saldo);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public String getRutCliente() {
        return rutCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public String getLugarEntrega() {
        return lugarEntrega;
    }

    public String getHoraEntrega() {
        return horaEntrega;
    }

    public String getObservacion() {
        return observacion;
    }

    public int getValorTotal() {
        return valorTotal;
    }

    public int getDescuento() {
        return descuento;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public void setRutCliente(String rutCliente) {
        this.rutCliente = rutCliente;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setLugarEntrega(String lugarEntrega) {
        this.lugarEntrega = lugarEntrega;
    }

    public void setHoraEntrega(String horaEntrega) {
        this.horaEntrega = horaEntrega;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setValorTotal(int valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha1 = new java.sql.Date(getFecha().getTime());
        java.sql.Date sqlfecha2 = new java.sql.Date(getFechaEntrega().getTime());
        java.sql.Date sqlfecha3 = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"',"+getIdVendedor()+",'"+getRutCliente()+"','"+sqlfecha1+"','"
                +sqlfecha2+"','"+getLugarEntrega()+"','"+getHoraEntrega()+"','"+getObservacion()+"',"+getValorTotal()
                +","+getDescuento()+","+getSaldo()+","+getEstado()+",'"+sqlfecha3+"',"+getLastHour()+")";
    }
}
