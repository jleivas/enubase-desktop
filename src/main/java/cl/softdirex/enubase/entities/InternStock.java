/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.entities;

import cl.softdirex.enubase.entities.abstractclasses.SyncIntId;


/**
 *
 * @author jlleivas
 */
public class InternStock extends SyncIntId{
    private String idItem;
    private int stock;

    public InternStock() {
    }

    public InternStock(int id, String idItem, int stock, int estado) {
        setId(id);
        setIdItem(idItem);
        setStock(stock);
        setEstado(estado);
    }

    public void setIdItem(String idItem) {
        this.idItem = idItem;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getIdItem() {
        return idItem;
    }

    public int getStock() {
        return stock;
    }
    
    
}
