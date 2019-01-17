/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.entities;

import cl.softdirex.enubase.entities.abstractclasses.SyncStringId;
import java.util.Date;

/**
 *
 * @author home
 */
public class Item extends SyncStringId{
    //debe incluir el id del inventario en el idString solo por bd remota, no se debe mostrar despues de -
    private String foto;
    private String marca;
    private int clasificacion;
    private String descripcion;
    private int precioRef;
    private int precioAct;
    private int stock;
    private int stockMin;
    private int inventario;

    public Item() {
    }
    
    /**
     * 
     * @param cod
     * @param foto
     * @param marca
     * @param clasificacion
     * @param descripcion
     * @param precioRef
     * @param precioAct
     * @param stock
     * @param stockMin
     * @param inventario
     * @param estado
     * @param lastUpdate
     * @param lastHour 
     */
    public Item(String cod, String foto, String marca, int clasificacion, String descripcion, int precioRef, int precioAct, int stock, int stockMin,int inventario, int estado, Date lastUpdate, int lastHour) {
        setMarca(marca);
        setFoto(foto);
        setClasificacion(clasificacion);
        setDescripcion(descripcion);
        setPrecioRef(precioRef);
        setPrecioAct(precioAct);
        setStock(stock);
        setStockMin(stockMin);
        setInventario(inventario);
        setCod(cod);
        setEstado(estado);
        setLastUpdate(lastUpdate);
        setLastHour(lastHour);
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getFoto() {
        return foto;
    }

    @Override
    public void setCod(String cod) {
        super.setCod(cod);
    }
    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    public int getInventario() {
        return inventario;
    }

    public void setMarca(String marca) {
        this.marca = getStr(marca);
    }

    public void setClasificacion(int clasificacion) {
        this.clasificacion = clasificacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = getStr(descripcion);
    }

    public void setPrecioRef(int precioRef) {
        this.precioRef = precioRef;
    }

    public void setPrecioAct(int precioAct) {
        this.precioAct = precioAct;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setStockMin(int stockMin) {
        this.stockMin = stockMin;
    }

    public String getMarca() {
        return getStr(marca);
    }
    
    public int getClasificacion() {
        return clasificacion;
    }

    public String getDescripcion() {
        return getStr(descripcion);
    }

    public int getPrecioRef() {
        return precioRef;
    }

    public int getPrecioAct() {
        return precioAct;
    }

    public int getStock() {
        return stock;
    }

    public int getStockMin() {
        return stockMin;
    }

    @Override
    public String toString() {
        return "\n -cod: "+getCod()+
                " - marca:"+this.marca+
                " - Stock:"+getStock()+
                " - Stock minimo:"+getStockMin()+
                " - Estado:"+getEstado()+
                " - lastUpdate:"+getLastUpdate()+
                " - LastHour:"+getLastHour();
    }
    
    public String getSqlInsertStatement(){
        java.sql.Date sqlfecha1 = new java.sql.Date(getLastUpdate().getTime());
        return "('"+getCod()+"','"+getFoto()+"','"+getMarca()+"',"+getClasificacion()
                +",'"+getDescripcion()+"',"+getPrecioRef()+","+getPrecioAct()
                + ","+getStock()+","+getStockMin()+","+getInventario()+","+getEstado()
                +",'"+sqlfecha1+"',"+getLastHour()+")";
    }
}
