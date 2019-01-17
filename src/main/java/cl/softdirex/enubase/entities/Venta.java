/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.entities;

import cl.softdirex.enubase.entities.abstractclasses.SyncCodClass;
import java.util.Date;
import java.util.List;

/**
 *
 * @author sdx
 */
public class Venta extends SyncCodClass{
    public List<Detalle> detalles;
    private User vendedor;
    private Cliente cliente;
    private Date fecha;
    private Date fechaEntrega;
    private String lugarEntrega;
    private String horaEntrega;
    private String observacion;
    private int valorTotal;
    private int descuento;
    private int saldo;
    private Despacho despacho;

    public Venta() {
    }

    public Venta(String cod,User vendedor, Cliente cliente, 
            Date fecha, Date fechaEntrega, String lugarEntrega, String horaEntrega, 
            String observacion, int valorTotal, int descuento, int saldo, Despacho despacho,
            List<Detalle> detalles, int estado, Date lastUpdate, int lastHour) {
        setCod(cod);
        setDetalles(detalles);
        setVendedor(vendedor);
        setCliente(cliente);
        setFecha(fecha);
        setFechaEntrega(fechaEntrega);
        setLugarEntrega(lugarEntrega);
        setHoraEntrega(horaEntrega);
        setObservacion(observacion);
        setValorTotal(valorTotal);
        setDescuento(descuento);
        setSaldo(saldo);
        setDespacho(despacho);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public User getVendedor() {
        return vendedor;
    }

    public Cliente getCliente() {
        return cliente;
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

    public Despacho getDespacho() {
        return despacho;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public void setVendedor(User vendedor) {
        this.vendedor = vendedor;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public void setDespacho(Despacho despacho) {
        this.despacho = despacho;
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha1 = new java.sql.Date(getFecha().getTime());
        java.sql.Date sqlfecha2 = new java.sql.Date(getFechaEntrega().getTime());
        java.sql.Date sqlfecha3 = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"',"+getVendedor().getId()+",'"+getCliente().getCod()+"','"+sqlfecha1+"','"
                +sqlfecha2+"','"+getLugarEntrega()+"','"+getHoraEntrega()+"','"+getObservacion()+"',"+getValorTotal()
                +","+getDescuento()+","+getSaldo()+","+getEstado()+",'"+sqlfecha3+"',"+getLastHour()+")";
    }
}
