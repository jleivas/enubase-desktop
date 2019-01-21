/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this objectlate file, choose Tools | Templates
 * and open the objectlate in the editor.
 */
package cl.softdirex.enubase.dao;

import cl.softdirex.enubase.entities.Cliente;
import cl.softdirex.enubase.entities.Descuento;
import cl.softdirex.enubase.entities.Despacho;
import cl.softdirex.enubase.entities.Detalle;
import cl.softdirex.enubase.entities.Equipo;
import cl.softdirex.enubase.entities.HistorialPago;
import cl.softdirex.enubase.entities.InternMail;
import cl.softdirex.enubase.entities.Inventario;
import cl.softdirex.enubase.entities.Item;
import cl.softdirex.enubase.entities.Oficina;
import cl.softdirex.enubase.entities.Proveedor;
import cl.softdirex.enubase.entities.RegistroBaja;
import cl.softdirex.enubase.entities.TipoPago;
import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.entities.Venta;
import cl.softdirex.enubase.entities.VentaDTO;
import cl.softdirex.enubase.entities.abstractclasses.SyncClass;
import cl.softdirex.enubase.entities.abstractclasses.SyncCodClass;
import cl.softdirex.enubase.entities.abstractclasses.SyncIntId;
import cl.softdirex.enubase.entities.abstractclasses.SyncIntIdValidaName;
import cl.softdirex.enubase.entities.abstractclasses.SyncStringId;
import cl.softdirex.enubase.sync.Sync;
import cl.softdirex.enubase.sync.entities.LocalInventario;
import cl.softdirex.enubase.utils.GV;
import cl.softdirex.enubase.utils.WebUtils;
import cl.softdirex.enubase.utils.Send;
import cl.softdirex.enubase.utils.StEntities;
import cl.softdirex.enubase.utils.GlobalValuesVariables;
import cl.softdirex.enubase.view.notifications.OptionPane;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class Dao{
    private static String className="Dao";

    private Send mailSend = new Send();
    /**
     * Sólo crea datos, si ya existen no los puede agregar.
     * Útil solo para registros independientes de la base de datos remota
     * @param object
     * @return 
     */
    public boolean sendMessage (InternMail msg) throws InstantiationException, IllegalAccessException{
        msg.setEstado(1);
        String mail = msg.getDestinatario().getEmail();
        if(GV.getStr(mail).isEmpty()){
            mailSend.sendMessageMail(msg.getAsunto(), mail);
        }
        return add(msg);
    }
    
    public boolean addOnInit(Object object) throws InstantiationException, IllegalAccessException {
        if(object == null){
            return false;//ultima modificacion sin verificar en todos los casos de uso
        }
        if(object instanceof SyncClass){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));//solo se actualizan lastuodates para crear objetos
        }
        if(object instanceof SyncCodClass){
            if(!(object instanceof Venta)){
                ((SyncCodClass) object).setCod(getCurrentCod(object));
            }
        }
        if(object instanceof SyncIntId)//se pueden agregar solo si tienen conexion a internet
            ((SyncIntId)object).setId(GV.LOCAL_SYNC.getMaxId(object));

        if(GV.LOCAL_SYNC.exist(object)){
            if(object instanceof SyncIntId){
                if(!GV.isCurrentDate(((SyncIntId)object).getLastUpdate())){
                    OptionPane.showMsg("No se puede crear nuevo registro", "El nombre ya se encuentra utilizado,\n"
                        + "Para poder ingresar un nuevo registro debes cambiar el nombre.", 2);
                }
            }else{
                return update(object);
            }
        }else{
            try {
                return Sync.add(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return false;
    }
    
   /**
    * Agrega registros a la base de datos, si ya existen los actualiza, útil para sincronización de bases de datos.
    * 
    * Comprueba si existe antes de agregar, en User comprueba si ya existe un username igual,
    * en cristal, descuento y oficina compara si ya existe un nombre igual (en estos casos no busca por id).
    * @param object
    * @return
    * @throws InstantiationException
    * @throws IllegalAccessException 
    */
    public boolean add(Object object) throws InstantiationException, IllegalAccessException {
        if(object == null){
            return false;//ultima modificacion sin verificar en todos los casos de uso
        }
        if(object instanceof SyncClass){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));//solo se actualizan lastuodates para crear objetos
        }
        if(object instanceof SyncCodClass){
            if(!(object instanceof Venta)){
                ((SyncCodClass) object).setCod(getCurrentCod(object));
            }
        }
        if(WebUtils.isOnline()){
            if(object instanceof SyncIntId)//se pueden agregar solo si tienen conexion a internet
                if(object instanceof User){
                    if(((User)object).getId() == 1 || ((User)object).getId() == 2){
                        ((SyncIntId)object).setId(((SyncIntId)object).getId());
                    }else{
                        ((SyncIntId)object).setId(GV.REMOTE_SYNC.getMaxId(object));
                    }
                }else{
                    ((SyncIntId)object).setId(GV.REMOTE_SYNC.getMaxId(object));
                }
                
            if(GV.REMOTE_SYNC.exist(object)){
                if(object instanceof SyncIntId){
                    if(!GV.isCurrentDate(((SyncIntId)object).getLastUpdate())){
                        OptionPane.showMsg("No se puede crear nuevo registro", "El nombre ya se encuentra utilizado,\n"
                            + "Para poder ingresar un nuevo registro debes cambiar el nombre.", 2);
                    }
                }else{
                    return update(object);
                }
            }else{
                try {
                    if(object instanceof InternMail){
                        return (Sync.addLocalSync(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object) &&
                                Sync.addRemoteSync(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object));
                    }else{
                        return Sync.add(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object);
                    }
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }else{
            if(object instanceof SyncStringId){
                if(!GV.LOCAL_SYNC.exist(object)){
                    return update(object);
                }else{
                    try {
                        return Sync.add(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }else{
                OptionPane.showMsg("No se puede crear nuevo registro", "Para poder ingresar un nuevo registro debes tener acceso a internet."+object.toString(), 2);
            }
        }
        return false;
    }

    public boolean update(Object object) {
        
        if(object instanceof SyncClass){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));
        }
        try {
            return Sync.add(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean updateFromUI(Object object){
        if(validaEntity(object)){
            if(GV.licenciaLocal()){
                OptionPane.showMsg("NO CREATED", "function ist created!", 3);
                return false;
            }else{
                updateRemote(object);
                return true;
            }
        }
        return false;
    }
    
    public boolean decreaseStock(String idItem, int cantidad)  {
        try{
            Item temp = (Item) get(idItem, 0, new Item());
            int newStock = 0;
            if(temp != null){
                newStock = temp.getStock() - cantidad;
                if(newStock >= 0){
                    /**
                     * Se inserta un registro temporal con las cantidades a reducir
                     */
                    if(LocalInventario.insert(idItem,cantidad)){
                        return true;
                    }
                }else{
                    OptionPane.showMsg("No se pudo reducir el stock", "La cantidad a reducir es mayor que el stock disponible", 2);
                }
            }
            return false;
        }catch(IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex){
            return false;
        }
    }
    
    public boolean increaseStock(String idItem, int cantidad)  {
        if(GV.getStr(idItem).isEmpty()){
            return false;
        }
        if(cantidad < 0){
            return false;
        }
        try{
            Item temp = (Item) get(idItem, 0, new Item());
            int newStock = 0;
            if(temp != null){
                newStock = temp.getStock() + cantidad;
                if(newStock >= 0){
                    return LocalInventario.insert(idItem, (cantidad * -1));
                }else{
                    OptionPane.showMsg("No se pudo modificar el stock", "El nuevo stock no es disponible,\n"
                            + "debe ser mayor que cero", 2);
                }
            }
            return false;
        }catch(IllegalAccessException | InstantiationException | SQLException | ClassNotFoundException ex){
            return false;
        }
    }
    
    
    public boolean restoreOrDeleteFromUI(Object object){
        if(!validaPrivilegiosParaEstados(object)){
            if(((SyncClass)object).getEstado() == 0){
                OptionPane.showMsg("No tienes privilegios suficientes", "No tienes permiso para restaurar el registro", 2);
                return false;
            }else{
                OptionPane.showMsg("No tienes privilegios suficientes", "No tienes permiso para eliminar el registro", 2);
                return false;
            }
        }
        if(object instanceof Inventario){
            if(((Inventario)object).getNombre().equals(GlobalValuesVariables.getInventarioName())){
                OptionPane.showMsg("No se puede eliminar", "Este inventario se encuentra en uso, no se puede eliminar.",3);
                return false;
            }
        }
        if(object instanceof User){
            if(((User)object).getId() == StEntities.USER.getId()){
                OptionPane.showMsg("No es posible realizar esta operación", "No puedes eliminar tu propio usuario.", 2);
                return false;
            }
        }
        if(GV.licenciaLocal()){
            //System.out.println("no function");
            return false;
        }else{
            restoreOrDeleteRemote(object);
            return true;
        }
    }
    
    public boolean delete(String cod,int id, Object type) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        Object temp =  GV.LOCAL_SYNC.getElement(cod,id, type);
        if(temp != null){
            if(type instanceof SyncClass){
                if(type instanceof Venta){
                    if(GV.fechaPasada(((Venta)temp).getFecha())){
                        OptionPane.showMsg("No se puede eliminar", "Esta opción no se encuentra disponible,\n"
                                + "solo se pueden anular ventas generadas hoy.", 2);
                        return false;
                    }
                    ((SyncClass)temp).setEstado(((((SyncClass)temp).getEstado())*-1));
                    String idDetail = null;
                    for (Detalle det : ((Venta)temp).getDetalles()) {
                        increaseStock(det.getCod(), det.getCantidad());
                    }
                }else{
                    ((SyncClass)temp).setEstado(0);
                }
                ((SyncClass)temp).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
                ((SyncClass)temp).setLastHour(GV.hourToInt(new Date()));
                
                if(WebUtils.isOnline()){
                    GV.REMOTE_SYNC.update(temp);
                }
                return GV.LOCAL_SYNC.update(temp);
            }else{
                OptionPane.showMsg("No se puede eliminar", "El registro no tiene tipo válido.", 2);
                return false;
            }
        }else{
            OptionPane.showMsg("No se puede eliminar", "El registro no existe.", 2);
            return false;
        } 
    }

    public boolean restore(String cod,int id,Object type) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        Object temp =  GV.LOCAL_SYNC.getElement(cod,id, type);
        if(temp != null){
            if(type instanceof SyncClass){
                if(type instanceof Venta){
                    OptionPane.showMsg("No se puede restaurar", "Esta opción aún no se encuentra disponible.", 2);
                    return false;
                    //((SyncClass)temp).setEstado(((((SyncClass)temp).getEstado())*-1));
                }else{
                    ((SyncClass)temp).setEstado(1);
                }
                ((SyncClass)temp).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
                ((SyncClass)temp).setLastHour(GV.hourToInt(new Date()));
                
                if(WebUtils.isOnline()){
                    GV.REMOTE_SYNC.update(temp);
                }
                return GV.LOCAL_SYNC.update(temp);
            }else{
                OptionPane.showMsg("No se puede restaurar", "El registro no tiene tipo válido.", 2);
                return false;
            }
        }else{
            OptionPane.showMsg("No se puede restaurar", "El registro no existe.", 2);
            return false;
        } 
    }
    
    /**
     * Retorna un elemento de la base de datos local
     * @param cod
     * @param id
     * @param type
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    public Object get(String cod,int id, Object type) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        if(type instanceof Item){
            Inventario inv = (Inventario)get(GlobalValuesVariables.getInventarioName(), 0, new Inventario());
            if(inv != null){
                GlobalValuesVariables.setInventaryChooser(inv.getId());
            }
            Object item =  LocalInventario.getItem(cod);
            GlobalValuesVariables.setInventaryChooser(0);
            return item;
        }
        return GV.LOCAL_SYNC.getElement(cod,id,type);
    }
    
    public Item getItem(String idItem, String inventarioName){
        Inventario inv = new Inventario();
        try {
            inv = (Inventario)get(inventarioName, 0, new Inventario());
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
        GlobalValuesVariables.setInventaryChooser(inv.getId());
        Item item = LocalInventario.getItem(idItem);
        GlobalValuesVariables.setInventaryChooser(0);
        return item;
    }

        public static void sincronize(Object type) {
        
        if(type instanceof Venta){
            type = new VentaDTO();
        }
        boolean esItem = (type instanceof Item);
            //System.out.println(esItem);
        if(GV.isCurrentDate(GlobalValuesVariables.getLastUpdate())){//validar plan de licencia
            return;//solo hace una actualizacion por día.
        }
        try {
            if(WebUtils.isOnline()){
                if(type instanceof Venta){
                    type = new VentaDTO();
                }
                ArrayList<Object> remoteObjectList= GV.REMOTE_SYNC.listar("-2",type);
                ArrayList<Object> localObjectList= GV.LOCAL_SYNC.listar("-2",type);
                /*LISTA1 SE DEBE CARGAR CON UN RETRASO DE DOS MESES PARA RESCATAR ULTIMOS REGISTROS SUBIDOS*/
                ArrayList<Object> lista1= GV.REMOTE_SYNC.listar(GV.dateSumaResta(GlobalValuesVariables.getLastUpdate(), -2, "MONTHS"),type);
                int size1 = lista1.size();
                ArrayList<Object> lista2= GV.LOCAL_SYNC.listar(GlobalValuesVariables.getLastUpdate(),type);
                
                int size2 = lista2.size();
                if(size1 > 0){
                    for (Object object : lista1) {
                        Object local;
                        //System.out.println("lista1");
                        if(object instanceof SyncIntId){
                            //System.out.println("INT");
                            local = GV.searchInList(""+((SyncIntId)object).getId(), localObjectList, type);
                        }else if(object instanceof SyncStringId){
                            //System.out.println("STRING");
                            local = GV.searchInList(((SyncStringId)object).getCod(), localObjectList, type);
                        }else{
                            //System.out.println("XXX");
                            return;
                        }
                        GlobalValuesVariables.calcularSubPorcentaje(size1+size2);
                        if(local == null){
                            /*CREAR SQL PARA INSERTAR TODOS LOS REGISTROS EN UNA SOLA CONSULTA*/
                            //System.out.println("INSERT");
                            GV.LOCAL_SYNC.add(object);
                        }else{
                            /*VALIDACION DE USUARIO ADMIN SI NO CONTIENE EMAIL*/
                            if(object instanceof User){
                                if(((User)object).getUsername().equals("admin")){
                                    if(GV.getStr(((User)object).getEmail()).isEmpty()){
                                        Object remoto = GV.searchInList(""+((SyncIntId)object).getId(), remoteObjectList, type);
                                        /*SE OBTIENE EMAIL EXISTENTE SI ES QUE SE ENCUENTRA REGISTRADO*/
                                        if(remoto != null){
                                            ((User)object).setEmail(((User)remoto).getEmail());
                                            if(((User)object).getPass().equals(GV.enC("admin"))){
                                                ((User)object).setPass(((User)remoto).getPass());
                                            }
                                            ((User)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
                                            ((User)object).setLastHour(GV.hourToInt(new Date()));
                                        }
                                    }
                                }
                            }
                            /*VALIDAR SI YA ESTÁ INSERTADO PARA UPDATEAR*/
                            if(object instanceof SyncClass){
                                /*VALIDAR SI LA FECHA DEL OBJETO LOCAL ES NUEVA O IGUAL*/
                                if(GV.localIsNewOrEqual(((SyncClass)object).getLastUpdate(), ((SyncClass)local).getLastUpdate())){
                                    /*VALIDAR SI LA FECHA ES LA MISMA*/
                                    if(GV.dateToString(((SyncClass)object).getLastUpdate(), "ddmmyyyy").equals(GV.dateToString(((SyncClass)local).getLastUpdate(), "ddmmyyyy"))){
                                        /*VALIDAR SI LA HORA ES MAS RECIENTE*/
                                        if(((SyncClass)object).getLastHour() > ((SyncClass)local).getLastHour()){
                                            GV.LOCAL_SYNC.update(object);
                                            //System.out.println("UPD1");
                                        }
                                    }else{
                                        GV.LOCAL_SYNC.update(object);
                                        //System.out.println("UPD2");
                                    }
                                }
                            }
                        }
                    }
                }
                if(size2 > 0){
                    String sql2 = "";
                    for (Object object : lista2) {
                        //System.out.println("lista2");
                        GlobalValuesVariables.calcularSubPorcentaje(size1+size2);
                        if(!WebUtils.isOnline()){
                            GV.stopSincronizacion();
                        }
                        if(GV.sincronizacionIsStopped()){
                            return;
                        }
                        Object remote;
                        if(object instanceof SyncIntId){
                            //System.out.println("INT");
                            remote = GV.searchInList(""+((SyncIntId)object).getId(), 
                                    remoteObjectList, type);
                        }else if(object instanceof SyncStringId){
                            //System.out.println("STRING");
                            remote = GV.searchInList(((SyncStringId)object).getCod(), 
                                    remoteObjectList, type);
                        }else{
                            //System.out.println("XXX");
                            return;
                        }
                        if(remote == null){
                            /*CREAR SQL PARA INSERTAR TODOS LOS REGISTROS EN UNA SOLA CONSULTA*/
                            //System.out.println("INSERT");
                            sql2 = getSqlRemoteInsert(sql2, object);
                        }else{
                            /*VALIDAR SI YA ESTÁ INSERTADO PARA UPDATEAR*/
                            if(object instanceof SyncClass){
                                /*VALIDAR SI LA FECHA DEL OBJETO LOCAL ES NUEVA O IGUAL*/
                                if(GV.localIsNewOrEqual(((SyncClass)object).getLastUpdate(), 
                                        ((SyncClass)remote).getLastUpdate())){
                                    /*VALIDAR SI LA FECHA ES LA MISMA*/
                                    if(GV.dateToString(((SyncClass)object).getLastUpdate(), 
                                            "ddmmyyyy").equals(GV.dateToString(
                                                    ((SyncClass)remote).getLastUpdate(), 
                                                    "ddmmyyyy")))
                                    {
                                        /*VALIDAR SI LA HORA ES MAS RECIENTE*/
                                        if(((SyncClass)object).getLastHour() > 
                                                ((SyncClass)remote).getLastHour())
                                        {
                                            GV.REMOTE_SYNC.updateFromDao(object);
                                            //System.out.println("UPD1");
                                        }
                                    }else{
                                        GV.REMOTE_SYNC.updateFromDao(object);
                                        //System.out.println("UPD2");
                                    }
                                }
                            }
                        }
                    }//TERMINA DE RECORRER LA LISTA LOCAL
                    if(!sql2.isEmpty()){
                        //System.out.println("EXE INS");
                        GV.REMOTE_SYNC.insertFromDao(sql2);
                    }
                }
                if(esItem){
                    /**
                     * actualizar stock
                     */
                    //se obtiene una lista recien descargada procesada con los stocks actualizados
                    lista2 = LocalInventario.listarItemsForSync();
                    int tam1 = lista2.size();
                    for (Object object : lista2) {
                        GlobalValuesVariables.calcularSubPorcentaje(tam1);
                        //System.out.println("items");
                        Sync.addRemoteSync(GV.LOCAL_SYNC, GV.REMOTE_SYNC, object);
                        GV.LOCAL_SYNC.updateFromDao(object);
                    }
                    LocalInventario.deleteAllRegistry("-2");
                }
            } 
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Dao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList<Object> listar(String param, Object type){
        if(type instanceof Item){
            try {
                return LocalInventario.listarItems(param);
            } catch (ClassNotFoundException | InstantiationException | 
                    IllegalAccessException | SQLException ex) {
                return new ArrayList<>();
            }
        }
        return GV.LOCAL_SYNC.listar(param, type);
    }
    
    /**
     * 
     * @param remitente 0 si no se necesita
     * @param destinatario 0 si no se necesita
     * @param estado 0: todos, 1: no leidos, 2 leidos
     * @return 
     */
    public ArrayList<InternMail> mensajes(int remitente, int destinatario, int estado) 
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, 
            SQLException
    {
        return GV.LOCAL_SYNC.mensajes(remitente, destinatario, estado);
    }

    /**
     * Retorna el id actual de las entidades Armazon, Despacho, Venta, HistorialPago y 
     * RegistroBaja
     * @param type tipo de clase a consultar
     * @return 
     */
    public String getCurrentCod(Object type){
        if(type instanceof SyncCodClass){
            return GV.LOCAL_SYNC.getMaxId(type)+"-"+GV.LOCAL_SYNC.getIdEquipo();
        }else{
            OptionPane.showMsg("Instancia de datos errónea", 
                    "El tipo de datos ingresado no es válido para obtener el identificador.", 
                    3);
            return null;
        }
        
    }

    public boolean createVenta(Venta venta, HistorialPago hp) {
        setLastUpdates(hp);
        setLastUpdates(venta.getCliente());
        setLastUpdates(venta);
        
        int maxIdDetalle = GV.LOCAL_SYNC.getMaxId(new Detalle());
        for (Detalle det : venta.getDetalles()) {
            det.setCod(maxIdDetalle+ "-" + GlobalValuesVariables.getIdEquipo());
            if(LocalInventario.addObject(det)){
                String idItem = det.getIdItem();
                if(!idItem.isEmpty()){
                    decreaseStock(idItem, det.getCantidad());
                }
            }
            maxIdDetalle++;
        }
        
        if(venta.getCliente() != null){
            if(!GV.getStr(venta.getCliente().getCod()).isEmpty()){
                GV.LOCAL_SYNC.add(venta.getCliente());
            }  
        }
        LocalInventario.addObject(venta);
        if(hp != null){
            if(hp.getAbono() > 0){
                hp.setCod(getCurrentCod(hp));
                LocalInventario.addObject(hp);
            }
        }
        return true;
    }
    
    private void setLastUpdates(SyncClass object){
        Date lastDate = new Date();
        if(object != null){
            (object).setLastUpdate(lastDate);//actualizamos la ultima fecha de modificacion
            (object).setLastHour(GV.hourToInt(lastDate));
        }
    }

    public boolean usernameYaExiste(String username) {
        if(GlobalValuesVariables.getLicenciaTipoPlan() == GlobalValuesVariables.licenciaTipoFree() ||
           GlobalValuesVariables.getLicenciaTipoPlan() == GlobalValuesVariables.licenciaTipoLocal()){
            return (GV.LOCAL_SYNC.getElement(username, 0, new User())!=null);
        }else{
            return (GV.REMOTE_SYNC.getElement(username, 0, new User())!=null);
        }
    }

    
    public boolean addFromUI(Object object){
        if(validaEntity(object)){
            if(GV.licenciaLocal()){
                addLocal(object);
            }else{
                addRemote(object);
            }
            return true;
        }
        return false;
    }
    
    private void addLocal(Object object) {
        if(object instanceof SyncIntId){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));//solo se actualizan lastuodates para crear objetos
            if(object instanceof SyncIntIdValidaName){
                addSyncIntIdValidaNameLocal(object);
            }else{
                addSyncIntIdLocal(object);
            }
        }
        if(object instanceof SyncStringId){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));//solo se actualizan lastuodates para crear objetos
            addSyncStringIdLocal(object);
        }
        OptionPane.showMsg("No se puede insertar registro", 
                "La entidad enviada no tiene un formato válido\n"
                + "\n"
                + "Detalle: " + object.getClass().getName(), 2);
    }

    private void addRemote(Object object) {
        if(object instanceof SyncCodClass)
        {
            if(!(object instanceof Venta))
            {
                ((SyncCodClass) object).setCod(getCurrentCod(object));
            }
        }
        if(object instanceof SyncIntId)
        {
            if(!WebUtils.isOnline())
            {
                OptionPane.showMsg("No se puede crear nuevo registro", 
                        "Para poder ingresar un nuevo registro debes tener acceso "
                                + "a internet.", 2);
                return;
            }
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));//solo se actualizan lastuodates para crear objetos
            ((SyncIntId)object).setId(GV.LOCAL_SYNC.getMaxId(object));
            if(((SyncIntId)object).getId() < 0)
            {
                OptionPane.showMsg("Error de conexión", 
                        "No se pudo obtener conexión desde la base de datos remota.", 2);
                return;
            }
            if(object instanceof SyncIntIdValidaName)
            {
                addSyncIntIdValidaNameRemote(object);
                return;
            }else{
                addSyncIntIdRemote(object);
                return;
            }
        }
        if(object instanceof SyncStringId){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));//solo se actualizan lastuodates para crear objetos
            addSyncStringIdRemote(object);
            return;
        }
        OptionPane.showMsg("No se puede insertar registro", 
                "La entidad enviada no tiene un formato válido\n"
                + "\n"
                + "Detalle: " + object.getClass().getName(), 2);
    }

    private void addSyncIntIdValidaNameLocal(Object object) {
        SyncIntIdValidaName entity = (SyncIntIdValidaName) GV.LOCAL_SYNC.getElement
        (((SyncIntIdValidaName)object).getNombre(), 0, object);
        if(entity == null){
            if(GV.LOCAL_SYNC.add(object)){
                msgEntityAdded();
            }
            msgEntityNotAdded();
        }else{
            msgInvalidName(entity.getEstado());
        }
    }

    private void addSyncIntIdLocal(Object object) {
        GV.LOCAL_SYNC.add(object);
    }
    
    private void addSyncIntIdRemote(Object object) {
        if(object instanceof User){
            User entity = (User) GV.REMOTE_SYNC.getElement(((User)object)
                    .getUsername(), 0, object);
            if(entity == null){
                if(GV.REMOTE_SYNC.add(object)){
                    GV.LOCAL_SYNC.add(object);
                    msgEntityAdded();
                    return;
                }
                msgEntityNotAdded();
                return;
            }else{
                if(entity.getEstado() == 0){
                    OptionPane.showMsg("No se puede agregar el registro", 
                            "Ya existe un usuario con el username ingresado "
                                    + "pero se encuentra anulado.", 2);
                    return;
                }else{
                    OptionPane.showMsg("No se puede agregar el registro", 
                            "Ya existe un usuario con el username ingresado.", 2);
                    return;
                }
            }
        }else{
            if(GV.REMOTE_SYNC.add(object)){
                GV.LOCAL_SYNC.add(object);
                msgEntityAdded();
                return;
            }
            msgEntityNotAdded();
            return;
        } 
    }
    
    private void msgInvalidName(int status){
        if(status == 0){
            OptionPane.showMsg("Imposible registrar elemento", "Ya existe un registro con el nombre ingresado pero se encuentra elimidado,\n"
                + "debes cambiar el nombre para poder continuar o restaurar el elemento eliminado.", 2);
        }else{
            OptionPane.showMsg("Imposible registrar elemento", "Ya existe un registro con el nombre ingresado,\n"
                + "debes cambiar el nombre para poder continuar.", 2);
        }
    }
    
    private void msgEntityAdded(){
        OptionPane.showMsg("Proceso finalizado", 
                "El registro a sido guardado exitosamente.", 1);
    }
    
    private void msgEntityNotAdded(){
        OptionPane.showMsg("Proceso finalizado", "El registro no se ha podido guardar\n"
                + "ha ocurrido un error inesperado durante la operación.", 2);
    }
    
    private void msgEntityUpdated() {
        OptionPane.showMsg("Proceso finalizado", 
                "El registro a sido modificado exitosamente.", 1);
    }

    private void msgEntityNotUpdated() {
        OptionPane.showMsg("Proceso finalizado", 
                "El registro no se ha podido modificar\n"
                + "ha ocurrido un error inesperado durante la operación.", 2);
    }

    private void addSyncStringIdLocal(Object object) {
        SyncStringId entity = (SyncStringId)GV.LOCAL_SYNC.getElement
        (((SyncStringId)object).getCod(), 0, object);
        if(entity == null){
            GV.LOCAL_SYNC.add(object);
        }else{
            if(entity.getEstado() == 0){
                if(OptionPane.getConfirmation("El registro ya existe", 
                        "Los datos ingresados corresponden a un registro anulado.\n"
                        + "Los datos se actualizarán y el registro se restaurará "
                                + "si confirmas los cambios.\n"
                        + "¿Deseas actualizar los datos?", 2)){
                    GV.LOCAL_SYNC.update(object);
                }
            }else{
                if(OptionPane.getConfirmation("El registro ya existe", 
                        "No se ha podido guardar el registro a menos que \n"
                        + "confirmes los cambios.\n"
                        + "¿Deseas actualizar los datos?", 2)){
                    GV.LOCAL_SYNC.update(object);
                }
            }  
        }
    }
    
    private void addSyncStringIdRemote(Object object) {
        SyncStringId entity = (SyncStringId)GV.LOCAL_SYNC.getElement
        (((SyncStringId)object).getCod(), 0, object);
        if(entity == null){
            GV.LOCAL_SYNC.add(object);
            msgEntityAdded();
        }else{
            if(entity.getEstado() == 0){
                if(OptionPane.getConfirmation("El registro ya existe", 
                        "Los datos ingresados corresponden a un registro anulado.\n"
                        + "Los datos se actualizarán y el registro se restaurará "
                                + "si confirmas los cambios.\n"
                        + "¿Deseas actualizar los datos?", 2)){
                    GV.LOCAL_SYNC.update(object);
                    msgEntityAdded();
                }
            }else{
                if(OptionPane.getConfirmation("El registro ya existe", 
                        "No se ha podido guardar el registro a menos que \n"
                        + "confirmes los cambios.\n"
                        + "¿Deseas actualizar los datos?", 2)){
                    GV.LOCAL_SYNC.update(object);
                    msgEntityAdded();
                }
            }  
        }
    }

    private void addSyncIntIdValidaNameRemote(Object object) {
        SyncIntIdValidaName entity = (SyncIntIdValidaName) GV.LOCAL_SYNC
                .getElement(((SyncIntIdValidaName)object).getNombre(), 0, object);
        if(entity == null){
            if(GV.REMOTE_SYNC.add(object)){
                GV.LOCAL_SYNC.add(object);
                msgEntityAdded();
            }else{
                msgEntityNotAdded();
            }
        }else{
            msgInvalidName(entity.getEstado());
        }
    }

    private boolean validaEntity(Object object) {
        if(object == null){
            return false;
        }
        if(object instanceof SyncIntIdValidaName){
            SyncIntIdValidaName obj = (SyncIntIdValidaName)object;
            if(obj.getStr(obj.getNombre()).length() <= 3){
                OptionPane.showMsg("Nombre incorrecto", "El registro debe tener "
                        + "un nombre válido.\n"
                        + "Información a considerar:\n"
                        + "- El campo nombre no debe estar vacío.\n"
                        + "- El nombre debe tener más de tres caracteres.\n"
                        + "- El nombre no debe contener caracteres especiales.", 2);
                return false;
            }
        }
        if(object instanceof Cliente){
            Cliente obj = (Cliente)object;
            if(obj.getStr(obj.getNombre()).length() <= 3){
                OptionPane.showMsg("Nombre incorrecto", "El registro debe tener "
                        + "un nombre válido.\n"
                        + "Información a considerar:\n"
                        + "- El campo nombre no debe estar vacío.\n"
                        + "- El nombre debe tener más de tres caracteres.\n"
                        + "- El nombre no debe contener caracteres especiales.", 2);
                return false;
            }
            if(obj.getNacimiento() == null){
                OptionPane.showMsg("Fecha no ingresada", 
                        "El cliente debe tener una fecha de nacimiento válida.", 2);
                return false;
            }
            if(obj.getTelefono1().isEmpty() && obj.getTelefono2()
                    .isEmpty() && obj.getEmail().isEmpty())
            {
                OptionPane.showMsg("Faltan datos de contacto", 
                        "El cliente debe tener al menos un registro de contacto.\n"
                    + "Ingrese un teléfono o correo electrónico.", 2);
                return false;
            }
            return true;
        }
        if(object instanceof Descuento){
            Descuento obj = (Descuento)object;
            if(obj.getMonto() == 0 && obj.getPorcetange() == 0){
                OptionPane.showMsg("Descuento inválido", "No ha agregado ningún "
                        + "tipo de descuento.\n"
                        + "Seleccione el tipo de descuento y aplique un monto válido.", 2);
                return false;
            }
            return true;
        }
        if(object instanceof Detalle){
            Detalle obj = (Detalle)object;
            if(obj.getCantidad() == 0){
                OptionPane.showMsg("Item agregado inválido", "No ha agregado una "
                        + "cantidad válida para este item.\n"
                        + "Asigne cantidad al producto.", 2);
                return false;
            }
            return true;
        }
        if(object instanceof Inventario){
            return true;
        }
        if(object instanceof Item){
            Item obj = (Item)object;
            if(obj.getCod().isEmpty()){
                OptionPane.showMsg("Faltan datos", "El producto debe tener un código válido.", 
                        2);
                return false;
            }
            if(obj.getClasificacion() == 0){
                OptionPane.showMsg("Faltan datos", 
                        "El producto debe tener una clasificación asignada.", 2);
                return false;
            }
            if(obj.getTipo()== 0){
                OptionPane.showMsg("Faltan datos", 
                        "El producto debe tener un tipo de unidad asignado.", 2);
                return false;
            }
            if(obj.getDescripcion().isEmpty()){
                OptionPane.showMsg("Faltan datos", 
                        "El producto no contiene una descripcion del producto.", 2);
                return false;
            }
            if(obj.getInventario() == 0){
                OptionPane.showMsg("Faltan datos", 
                        "El producto debe tener un inventario asignado.", 2);
                return false;
            }
            if(obj.getIdProveedor().isEmpty()){
                if(!OptionPane.getConfirmation("Faltan datos","Falta agregar un proveedor válido.\n\n"
                        + "¿Desea registrar el producto de todas formas?\n"
                        + "Si confirma los cambios, no se asignará un proveedor a este item." , 2)){
                    return false;
                }
            }
            if(obj.getPrecioAct() < 0 || obj.getPrecioRef() < 0){
                OptionPane.showMsg("Error de datos", 
                        "Uno de los precios ingresados no son válido.", 2);
                return false;
            }
            if(obj.getStock() < 0 || obj.getStockMin() < 0){
                OptionPane.showMsg("Error de datos", 
                        "Uo de los stocks ingresados no son válido.", 2);
                return false;
            }
            return true;
        }
        if(object instanceof Oficina){
            Oficina obj = (Oficina)object;
            if(obj.getTelefono1().isEmpty() && 
                    obj.getTelefono2().isEmpty() && obj.getEmail().isEmpty()){
                OptionPane.showMsg("Faltan datos de contacto", "El nuevo registro debe tener al menos un registro de contacto.\n"
                        + "Ingrese un teléfono o correo electrónico.", 2);
                return false;
            }
            return true;
        }
        if(object instanceof RegistroBaja){
            return true;
        }
        if(object instanceof TipoPago){
            return true;
        }
        if(object instanceof Proveedor){
            Proveedor obj = (Proveedor)object;
            if(GV.getStr(obj.getNombre()).isEmpty()){
                OptionPane.showMsg("Validaciónd de datos", 
                        "No se pudo agregar proveedor, debe ingresar un nombre válido,"
                    + "\nlos registros deben tener como mínimo 3 carácteres.", 2);
                return false;
            }
            if(!GV.validaRut(obj.getCod())){
                OptionPane.showMsg("Validaciónd de datos", 
                        "No se pudo agregar proveedor, debe ingresar un rut válido,", 2);
                return false;
            }
            return true;
        }
        if(object instanceof User){
            User obj = (User)object;
            if(obj.getNombre().length() <= 3){
                OptionPane.showMsg("Agregar usuario", 
                        "No se pudo agregar usuario, debe ingresar un nombre válido,"
                    + "\nlos registros deben tener como mínimo 3 carácteres.", 2);
                return false;
            }
            if(obj.getUsername().length() <= 3){
                OptionPane.showMsg("Agregar usuario", 
                        "No se pudo agregar usuario, debe ingresar un username válido,"
                    + "\nlos registros deben tener como mínimo 3 carácteres.", 2);
                return false;
            }
            if(!GV.tipoUserSuperAdmin() && obj.getTipo() == 1){
                OptionPane.showMsg("Agregar usuario", "No se pudo agregar usuario, "
                        + "debe ingresar un tipo de usuario distinto,"
                        + "\nno tienes permisos suficientes para crear un usuario de tipo "
                        + "\"Jefatura\".", 2);
                return false;
            }
            if(obj.getTipo() == 0){
                OptionPane.showMsg("Agregar usuario", "No se pudo agregar usuario, "
                        + "debe ingresar un tipo de usuario válido.", 2);
                return false;
            }
            return true;
        }
        OptionPane.showMsg("Entidad no validada", "No se ha cumplido con las validaciones "
                + "en esta entidad.", 3);
        return false;
    }

    private void updateRemote(Object object) {
        if(object instanceof SyncClass){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));
            if(object instanceof SyncIntId){
                if(!WebUtils.isOnline()){
                    OptionPane.showMsg("No se puede modificar el registro",
                            "Para poder modificar estos datos debes tener acceso "
                                    + "a internet.", 2);
                    return;
                }
                if(object instanceof SyncIntIdValidaName){
                    updateSyncIntIdValidaNameRemote(object);
                    return;
                }else{
                    updateSyncIntIdRemote(object);
                    return;
                }
            }
            if(object instanceof SyncStringId){
                updateSyncStringIdRemote(object);
                return;
            }
        }
        OptionPane.showMsg("No se puede modificar registro", 
                "La entidad enviada no tiene un formato válido\n"
                + "\n"
                + "Detalle: " + object.getClass().getName(), 2);
    }

    private void updateSyncIntIdValidaNameRemote(Object object) {
        SyncIntIdValidaName entity = (SyncIntIdValidaName)GV.REMOTE_SYNC
                .getElement(((SyncIntIdValidaName)object).getNombre(), 0, object);
        if(entity == null){
            if(GV.REMOTE_SYNC.update(object)){
                GV.LOCAL_SYNC.update(object);
                msgEntityUpdated();
                return;
            }
            msgEntityNotUpdated();
            return;
        }else{
            if(entity.getId() == ((SyncIntIdValidaName)object).getId()){
                if(GV.REMOTE_SYNC.update(object)){
                    GV.LOCAL_SYNC.update(object);
                    msgEntityUpdated();
                    return;
                }
                msgEntityNotUpdated();
            }else{
                msgInvalidName(entity.getEstado());
            }
        }
    }

    private void updateSyncIntIdRemote(Object object) {
        if(object instanceof User){
            User entity = (User)GV.REMOTE_SYNC.getElement(
                    ((User)object).getUsername(), 0, new User()
            );
            if(entity == null){
                if(GV.REMOTE_SYNC.update(object)){
                    GV.LOCAL_SYNC.update(object);
                    msgEntityUpdated();
                    return;
                }
                msgEntityNotUpdated();
                return;
            }else{
                if(entity.getId() == ((User)object).getId()){
                    if(GV.REMOTE_SYNC.update(object)){
                        GV.LOCAL_SYNC.update(object);
                        msgEntityUpdated();
                        return;
                    }
                    msgEntityNotUpdated();
                    return;
                }else{
                    if(entity.getEstado() == 0){
                        OptionPane.showMsg("No se puede modificar el registro", 
                                "Ya existe un usuario con el username ingresado "
                                        + "pero se encuentra anulado.", 2);
                        return;
                    }else{
                        OptionPane.showMsg("No se puede modificar el registro", 
                                "Ya existe un usuario con el username ingresado.", 2);
                        return;
                    }
                }
            }
        }
    }

    private void updateSyncStringIdRemote(Object object) {
        if(GV.LOCAL_SYNC.update(object)){
            msgEntityUpdated();
            return;
        }
        msgEntityNotUpdated();
    }

    private void restoreOrDeleteRemote(Object object) {
        if(object instanceof SyncClass){
            ((SyncClass)object).setLastUpdate(new Date());//actualizamos la ultima fecha de modificacion
            ((SyncClass)object).setLastHour(GV.hourToInt(new Date()));
            int estado = ((SyncClass)object).getEstado();
            if(object instanceof Venta){
                ((SyncClass)object).setEstado(estado*-1);
            }else{
                if(estado > 0){
                    ((SyncClass)object).setEstado(0);
                }else{
                    ((SyncClass)object).setEstado(1);
                }
            }
            if(object instanceof SyncIntId){
                if(!WebUtils.isOnline()){
                    if(((SyncClass)object).getEstado() < 1){
                        OptionPane.showMsg("No se puede eliminar el registro", 
                                "Para poder eliminar este elemento debes tener acceso "
                                        + "a internet.", 2);
                    }else{
                        OptionPane.showMsg("No se puede restaurar el registro", 
                                "Para poder restaurar este elemento debes tener acceso "
                                        + "a internet.", 2);
                    }
                    return;
                }
                restoreOrDeleteSyncIntIdRemote(object);
                return;
            }
            if(object instanceof SyncStringId){
                restoreOrDeleteSyncStringIdRemote(object);
                return;
            }
        }
        OptionPane.showMsg("No se puede eliminar registro", 
                "La entidad enviada no tiene un formato válido\n"
                + "\n"
                + "Detalle: " + object.getClass().getName(), 2);
    }

    private void restoreOrDeleteSyncIntIdRemote(Object object) {
        if(GV.REMOTE_SYNC.update(object)){
            GV.LOCAL_SYNC.update(object);
            msgEntityRestoreOrDeleted(((SyncClass)object).getEstado());
            return;
        }
        msgEntityNotRestoreOrDeleted(((SyncClass)object).getEstado());
    }
    
    

    private void restoreOrDeleteSyncStringIdRemote(Object object) {
        if(GV.LOCAL_SYNC.update(object)){
            msgEntityRestoreOrDeleted(((SyncClass)object).getEstado());
            return;
        }
        msgEntityNotRestoreOrDeleted(((SyncClass)object).getEstado());
    }

    private void msgEntityRestoreOrDeleted(int estado) {
        if(estado == 0){
            OptionPane.showMsg("Registro eliminado", 
                    "La operación se ejecutó exitosamente", 1);
        }else{
            OptionPane.showMsg("Registro restaurado", 
                    "La operación se ejecutó exitosamente", 1);
        }
    }

    private void msgEntityNotRestoreOrDeleted(int estado) {
        if(estado == 0){
            OptionPane.showMsg("Registro no fue eliminado", 
                    "La operación no se pudo ejecutar correctamente", 2);
        }else{
            OptionPane.showMsg("Registro no fue restaurado", 
                    "La operación no se pudo ejecutar correctamente", 2);
        }
    }

    private boolean validaPrivilegiosParaEstados(Object object) {
        if(object instanceof Cliente){
            return GV.tipoUserIventario();
        }
        if(object instanceof Descuento)
            return GV.tipoUserAdmin();
        if(object instanceof Despacho)
            return true;
        if(object instanceof Detalle)
            return true;
        if(object instanceof Inventario)
            return GV.tipoUserIventario();
        if(object instanceof Item)
            return GV.tipoUserIventario();
        if(object instanceof Oficina)
            return GV.tipoUserSuperAdmin();
        if(object instanceof Proveedor)
            return GV.tipoUserSuperAdmin();
        if(object instanceof TipoPago)
            return GV.tipoUserSuperAdmin();
        if(object instanceof User)
            return GV.tipoUserAdmin();
        if(object instanceof Venta)
            return true;
        OptionPane.showMsg("No valid entitie", 
                "isnt possible to validate the user type", 3);
        return false;
    }
    
    public static String getSqlRemoteInsert(String sql, Object object){
        if(object instanceof SyncClass){
            if(sql.startsWith("INSERT")){
                sql = sql + "," + getValuesSqlStatement(object);
            }else{
                sql = getInsertSqlStatement(object) + getValuesSqlStatement(object);
            }
        }
        return sql;
    }
    
    private static String getValuesSqlStatement(Object object){
        if(object instanceof Cliente){
            return ((Cliente)object).getSqlInsertStatement();
        }
        if(object instanceof Descuento){
            return ((Descuento)object).getSqlInsertStatement();
        }
        if(object instanceof Despacho){
            return ((Despacho)object).getSqlInsertStatement();
        }
        if(object instanceof Detalle){
            return ((Detalle)object).getSqlInsertStatement();
        }
        if(object instanceof Equipo){
            return ((Equipo)object).getSqlInsertStatement();
        }
        if(object instanceof HistorialPago){
            return ((HistorialPago)object).getSqlInsertStatement();
        }
        if(object instanceof InternMail){
            return ((InternMail)object).getSqlInsertStatement();
        }
        if(object instanceof Inventario){
            return ((Inventario)object).getSqlInsertStatement();
        }
        if(object instanceof Item){
            return ((Item)object).getSqlInsertStatement();
        }
        if(object instanceof Oficina){
            return ((Oficina)object).getSqlInsertStatement();
        }
        if(object instanceof Proveedor){
            return ((Proveedor)object).getSqlInsertStatement();
        }
        if(object instanceof RegistroBaja){
            return ((RegistroBaja)object).getSqlInsertStatement();
        }
        if(object instanceof TipoPago){
            return ((TipoPago)object).getSqlInsertStatement();
        }
        if(object instanceof User){
            return ((User)object).getSqlInsertStatement();
        }
        if(object instanceof VentaDTO){
            return ((VentaDTO)object).getSqlInsertStatement();
        }
        return "";
    }
    
    private static String getInsertSqlStatement(Object object){
        if(object instanceof Cliente){
            return "INSERT INTO cliente VALUES ";
        }
        if(object instanceof Descuento){
            return "INSERT INTO descuento VALUES ";
        }
        if(object instanceof Despacho){
            return "INSERT INTO despacho VALUES ";
        }
        if(object instanceof Detalle){
            return "INSERT INTO detalle VALUES ";
        }
        if(object instanceof Equipo){
            return "INSERT INTO equipo VALUES ";
        }
        if(object instanceof HistorialPago){
            return "INSERT INTO historial_pago VALUES ";
        }
        if(object instanceof InternMail){
            return "INSERT INTO message VALUES ";
        }
        if(object instanceof Inventario){
            return "INSERT INTO inventario VALUES ";
        }
        if(object instanceof Item){
            return "INSERT INTO item VALUES ";
        }
        if(object instanceof Oficina){
            return "INSERT INTO oficina VALUES ";
        }
        if(object instanceof Proveedor){
            return "INSERT INTO proveedor VALUES ";
        }
        if(object instanceof RegistroBaja){
            return "INSERT INTO registro_bajas VALUES ";
        }
        if(object instanceof TipoPago){
            return "INSERT INTO tipo_pago VALUES ";
        }
        if(object instanceof User){
            return "INSERT INTO usuario VALUES ";
        }
        if(object instanceof VentaDTO){
            return "INSERT INTO venta VALUES ";
        }
        return "";
    }
}
