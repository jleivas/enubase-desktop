/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.sync.entities;

import cl.softdirex.enubase.bd.LcBd;
import cl.softdirex.enubase.entities.Detalle;
import cl.softdirex.enubase.entities.HistorialPago;
import cl.softdirex.enubase.entities.Item;
import cl.softdirex.enubase.entities.Venta;
import cl.softdirex.enubase.entities.dto.InternStockDetail;
import cl.softdirex.enubase.utils.GV;
import cl.softdirex.enubase.utils.GlobalValuesVariables;
import cl.softdirex.enubase.view.notifications.OptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jlleivas
 */
public class LocalInventario {
    private static String className="LocalInventario";
    private static List<InternStockDetail> stockTemporalRebajado = new ArrayList<>();
    private static final String SQL_SYNC = "sincronizar";
    
    public static boolean insert(String idItem, int cantidad){
        try {
            int id = getMaxId();
            PreparedStatement insert = LcBd.obtener().prepareStatement(
                    "INSERT INTO intern_stock VALUES("
                            + id + ",'"
                            + idItem + "',"
                            + cantidad + ","
                            + 1 + ")"
            );
            if (insert.executeUpdate() != 0) {
                LcBd.cerrar();
                return true;
            }
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException ex) {
            Logger.getLogger(LocalInventario.class.getName()).log(Level.SEVERE, null, ex);
            OptionPane.showMsg("Error de excepcion", className + "\n"
                    + ex.getMessage(),3);
        }
        return false;
    }
    
    public static Item getItem(String id){
        try {
            Object object = GV.searchInList(id, listarItems(id), new Item());
            return (object!=null)?(Item)object:null;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(LocalInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * Retorna una lista de String con los id de los items cuyos stocks no han sido actualizados
     * @return 
     */
    public static ArrayList<Object> listarItemsForSync(){
        ArrayList<Object> lista = new ArrayList<>();
        updateStockTemporal();
        for (InternStockDetail isd : stockTemporalRebajado) {
            try {
                Object item = selectToSync(isd.getCod());
                if(item != null){
                    lista.add(item);
                }
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
                Logger.getLogger(LocalInventario.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }
    
    private static Object selectToSync(String idParam) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        ArrayList<Object> lista = new ArrayList<>();
        updateStockTemporal();
        String sql = "SELECT itm_id, itm_foto, proveedor_pro_id, itm_tipo, itm_clasificacion, itm_descripcion, itm_precio_ref, itm_precio_act, itm_stock, itm_stock_min, inventario_inv_id, itm_estado, itm_last_update, itm_last_hour,"
                + "(Select SUM(stock) from intern_stock WHERE id_item = itm_id AND estado = 1) as stock_menos FROM item WHERE itm_id ='" + idParam + "'";

        PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
        ResultSet datos = consulta.executeQuery();
        while (datos.next()) {
            String idItem = datos.getString("itm_id");
            int stock = datos.getInt("itm_stock")-datos.getInt("stock_menos");
            Date lastUpdate = new Date();
            int lastHour = GV.strToNumber(GV.dateToString(lastUpdate, "hhmmss"));
            lista.add(new Item(
                        idItem,
                        datos.getString("itm_foto"),
                        datos.getString("proveedor_pro_id"),
                        datos.getInt("itm_tipo"),
                        datos.getInt("itm_clasificacion"),
                        datos.getString("itm_descripcion"),
                        datos.getInt("itm_precio_ref"),
                        datos.getInt("itm_precio_act"),
                        stock,
                        datos.getInt("itm_stock_min"),
                        datos.getInt("inventario_inv_id"),
                        datos.getInt("itm_estado"),
                        lastUpdate,
                        lastHour
                )
            );
        }
        LcBd.cerrar();
        
        return (lista.size() > 0)?lista.get(0):null;
    }
    
    public static ArrayList<Object> listarItems(String idParam) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        ArrayList<Object> lista = new ArrayList<>();
        boolean sincronizar = (idParam.equals(SQL_SYNC));
        updateStockTemporal();
        String sql = "SELECT * FROM item WHERE itm_id ='" + idParam + "' AND inventario_inv_id = "+GlobalValuesVariables.getInventaryChooser();
        if (idParam.equals("0") || idParam.equals(GlobalValuesVariables.getSqlLowStock())) {
            sql = "SELECT * FROM item WHERE itm_estado=1 AND inventario_inv_id = "+GlobalValuesVariables.getInventaryChooser();
        }
        if (idParam.equals("-1")) {
            sql = "SELECT * FROM item WHERE itm_estado=0 AND inventario_inv_id = "+GlobalValuesVariables.getInventaryChooser();
        }
        if (idParam.equals("-2")) {
            sql = "SELECT * FROM item WHERE inventario_inv_id = "+GlobalValuesVariables.getInventaryChooser();
        }
        if (idParam.equals("st")) {
            sql = "SELECT * FROM item WHERE (itm_estado=1 AND itm_stock > 0) AND inventario_inv_id = "+GlobalValuesVariables.getInventaryChooser();
        }
        if (sincronizar) {
            sql = "SELECT * FROM item";
        }

        PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
        ResultSet datos = consulta.executeQuery();
        while (datos.next()) {
            String idItem = datos.getString("itm_id");
            int currentStock = datos.getInt("itm_stock");
            int stock = currentStock-getLocalStock(idItem);
            Date lastUpdate = datos.getDate("itm_last_update");
            int lastHour = datos.getInt("itm_last_hour");
            if(currentStock != stock && sincronizar){
                lastUpdate = new Date();
                lastHour = GV.strToNumber(GV.dateToString(lastUpdate, "hhmmss"));
                lista.add(new Item(
                        idItem,
                        datos.getString("itm_foto"),
                        datos.getString("proveedor_pro_id"),
                        datos.getInt("itm_tipo"),
                        datos.getInt("itm_clasificacion"),
                        datos.getString("itm_descripcion"),
                        datos.getInt("itm_precio_ref"),
                        datos.getInt("itm_precio_act"),
                        stock,
                        datos.getInt("itm_stock_min"),
                        datos.getInt("inventario_inv_id"),
                        datos.getInt("itm_estado"),
                        lastUpdate,
                        lastHour
                    )
            )   ;
            }
            if(!sincronizar){
                if(idParam.equals("st")){
                    if(stock > 0){
                        lista.add(new Item(
                            idItem,
                            datos.getString("itm_foto"),
                            datos.getString("proveedor_pro_id"),
                            datos.getInt("itm_tipo"),
                            datos.getInt("itm_clasificacion"),
                            datos.getString("itm_descripcion"),
                            datos.getInt("itm_precio_ref"),
                            datos.getInt("itm_precio_act"),
                            stock,
                            datos.getInt("itm_stock_min"),
                            datos.getInt("inventario_inv_id"),
                            datos.getInt("itm_estado"),
                            lastUpdate,
                            lastHour
                            )
                        );
                    }
                }else{
                    lista.add(new Item(
                            idItem,
                            datos.getString("itm_foto"),
                            datos.getString("proveedor_pro_id"),
                            datos.getInt("itm_tipo"),
                            datos.getInt("itm_clasificacion"),
                            datos.getString("itm_descripcion"),
                            datos.getInt("itm_precio_ref"),
                            datos.getInt("itm_precio_act"),
                            stock,
                            datos.getInt("itm_stock_min"),
                            datos.getInt("inventario_inv_id"),
                            datos.getInt("itm_estado"),
                            lastUpdate,
                            lastHour
                        )
                    );
                }
            }   
        }
        LcBd.cerrar();
        return lista;
    }
    
    public static boolean deleteAllRegistry(String idItem) {
        try{
            String sql = "UPDATE intern_stock set estado = 0 WHERE id_item = '" + idItem+"' AND estado = 1";
            if(idItem.equals("-2")){
                sql = "UPDATE intern_stock set estado = 0 WHERE estado = 1";
            }
            PreparedStatement insert = LcBd.obtener().prepareStatement(sql);
            insert.executeUpdate();
            LcBd.cerrar();
        }catch( ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex){
            OptionPane.showMsg("Error inesperado", "Ocurrió un error al intentar borrar los registros temporales del stock\n"
                    + "en :"+className, 3);
            updateStockTemporal();
            return false;
        }
        updateStockTemporal();
        return true;
    }

    public static int getMaxId() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        
        int id = 0;
        String sql = "SELECT MAX(id) as suma FROM intern_stock";
        PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
            ResultSet datos = consulta.executeQuery();
            while (datos.next()) {
                id = datos.getInt("suma");
            }
            LcBd.cerrar();
        return id + 1;
    }
    
    /**
     * retorna la cantidad total de registros de stock que no han sido eliminados
     * No se actualiza la lista estática porue este método sólo se llama desde listar items
     * para evitar problemas de conexion con la base de datos
     * @param idItem
     * @return 
     */
    private static int getLocalStock(String idItem){
        return searchInList(idItem).getCantidad();
    }
    
    /**
     * actualiza lista de stock retorna la cantidad total de registros de stock que no han sido eliminados
     * @param idItem
     * @return 
     */
    public static int stockDescontado(String idItem){
        updateStockTemporal();
        return searchInList(idItem).getCantidad();
    }
    
    private static InternStockDetail searchInList(String idItem) {
        Optional<InternStockDetail> objectFound = stockTemporalRebajado.stream()
            .filter(p -> p.getCod().equals(idItem))
            .findFirst();
        return objectFound.isPresent() ? objectFound.get() : new InternStockDetail();
    }
    
    private static void updateStockTemporal(){
        stockTemporalRebajado = listarStocksTemporales();
    }
    
    public static List<InternStockDetail> listarStocksTemporales(){
        
        List<InternStockDetail> listaStock = new ArrayList<>();
        try{
            String sql = "SELECT id_item, SUM(stock) as cantidad FROM intern_stock WHERE estado = 1 GROUP BY id_item";
            PreparedStatement consulta = LcBd.obtener().prepareStatement(sql);
                ResultSet datos = consulta.executeQuery();
                while (datos.next()) {
                    listaStock.add(new InternStockDetail(datos.getString("id_item"), datos.getInt("cantidad")));
                }
                LcBd.cerrar();
        }catch(Exception e){
            return new ArrayList<InternStockDetail>();
        }
        return listaStock;
    }
    
    public static boolean addObject(Object objectParam) {
        
        try{
            if(objectParam == null){
                return false;
            }else{
                PreparedStatement insert = LcBd.obtener().prepareStatement(
                        sqlInsert(objectParam)
                );
                if(insert.executeUpdate() != 0) {
                    LcBd.cerrar();
                    return true;
                }
                return false;
            }
        }catch( InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException | NullPointerException ex){
            Logger.getLogger(Local.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private static String sqlInsert(Object objectParam){
        return GV.LOCAL_SYNC.sqlInsert(objectParam);
    }
    
    public static boolean addVenta(Venta venta, List<Detalle> articulos, HistorialPago hp){
        try {
            PreparedStatement insert = LcBd.obtener().prepareStatement(
                    sqlInsert(hp)
            );
            if(insert.executeUpdate() == 0) {
                return false;
            }
            String sqlInsert="INSERT INTO detalle VALUES";
            String sql="";
            for (Detalle det : articulos) {
                java.sql.Date lastUpdate = new java.sql.Date(det.getLastUpdate().getTime());
                String values = "('"+det.getCod()+"','"+det.getIdVenta()+"','"+det.getIdItem()+"',"
                        + det.getCantidad()+","+det.getPrecioUnitario()+","
                        + det.getEstado()+",'"+lastUpdate+"',"+det.getLastHour()+")";
                sql=(sql.isEmpty())?values:sql + "," + values;
            }
            if(!sql.isEmpty()){
                sqlInsert = sqlInsert + sql;
                insert = LcBd.obtener().prepareStatement(sqlInsert);
                if(insert.executeUpdate() == 0) {
                    return false;
                }
            }   
            insert = LcBd.obtener().prepareStatement(
                    sqlInsert(venta)
            );
            if(insert.executeUpdate() == 0) {
                return false;
            }
            LcBd.cerrar();
            return true;
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(LocalInventario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
