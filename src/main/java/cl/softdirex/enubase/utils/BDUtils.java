/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.bd.LcBd;
import cl.softdirex.enubase.dao.Dao;
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
import cl.softdirex.enubase.sync.entities.Migrar;
import cl.softdirex.enubase.sync.entities.Remote;
import cl.softdirex.enubase.view.notifications.Notification;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.write.WriteException;

/**
 *
 * @author sdx
 */
public class BDUtils {
    /* Bases de datos*/
    public static String BD_URL_REMOTE;
    public static String BD_NAME_REMOTE;
    public static String BD_USER_REMOTE;
    public static String BD_PASS_REMOTE;
    public static String BD_URL_LOCAL = "."+File.separator+"DB"+File.separator;//"localhost:1527"
    public static String BD_NAME_LOCAL = "Derby.DB";//"odm4"
    public static String BD_USER_LOCAL = "odm4";
    public static String BD_PASS_LOCAL = "odm4";
    
    private static Migrar mig = new Migrar();
    private static Remote rem = new Remote();
    
    private static boolean SINCRONIZAR = false;
    private static boolean error = false;
    
    private static List<Object> LISTA_VENTAS = new ArrayList<>();
    
    /*ORDEN DE INSERCION EN BASE DE DATOS*/
    private static int cli = 1;
    private static int des = 2;
    private static int det = 3;
    private static int dsp = 4;
    private static int eq = 5;
    private static int hp = 6;
    private static int inv = 7;
    private static int itm = 8;
    private static int msg = 9;
    private static int of = 10;
    private static int pro =11;
    private static int rb = 12;
    private static int tp = 13;
    private static int usu = 14;
    private static int ven = 15;
    
    private static String CLIENTE = "CLI_RUT VARCHAR(25) not null primary key," +
" CLI_NOMBRE VARCHAR(45)," +
" CLI_TELEFONO1 VARCHAR(25)," +
" CLI_TELEFONO2 VARCHAR(25)," +
" CLI_EMAIL VARCHAR(45)," +
" CLI_DIRECCION VARCHAR(45)," +
" CLI_COMUNA VARCHAR(45)," +
" CLI_CIUDAD VARCHAR(45)," +
" CLI_SEXO INTEGER," +
" CLI_NACIMIENTO DATE," +
" CLI_ESTADO INTEGER," +
" CLI_LAST_UPDATE DATE," +
" CLI_LAST_HOUR INTEGER";
    private static String DESCUENTO = "DES_ID INTEGER not null primary key," +
" DES_NOMBRE VARCHAR(45)," +
" DES_DESCRIPCION LONG VARCHAR," +
" DES_PORC INTEGER," +
" DES_MONTO INTEGER," +
" DES_ESTADO INTEGER," +
" DES_LAST_UPDATE DATE," +
" DES_LAST_HOUR INTEGER";
    private static String VENTA = "VEN_ID VARCHAR(25) not null primary key," +
" USUARIO_US_ID INTEGER," +
" CLIENTE_CLI_RUT VARCHAR(25)," +
" VEN_FECHA DATE," +
" VEN_FECHA_ENTREGA DATE," +
" VEN_LUGAR_ENTREGA VARCHAR(45)," +
" VEN_HORA_ENTREGA VARCHAR(15)," +
" VEN_OBS LONG VARCHAR," +
" VEN_VALOR_TOTAL INTEGER," +
" VEN_DESCUENTO INTEGER," +
" VEN_SALDO INTEGER," +
" VEN_ESTADO INTEGER," +
" VEN_LAST_UPDATE DATE," +
" VEN_LAST_HOUR INTEGER";
    private static String DETALLE = "DET_ID VARCHAR(25) not null primary key," +
" VENTA_VEN_ID VARCHAR(25)," +
" ITEM_ITM_ID VARCHAR(45)," +
" DET_CANTIDAD INTEGER," +
" DET_PRECIO_UNIT INTEGER," +
" DET_ESTADO INTEGER," +
" DET_LAST_UPDATE DATE," +
" DET_LAST_HOUR INTEGER";
    private static String DESPACHO = "DSP_ID VARCHAR(25) not null primary key," +
" DSP_RUT VARCHAR(25)," +
" DSP_NOMBRE VARCHAR(45)," +
" DSP_FECHA DATE," +
" VENTA_VEN_ID VARCHAR(25)," +
" DSP_ESTADO INTEGER," +
" DSP_LAST_UPDATE DATE," +
" DSP_LAST_HOUR INTEGER";
    private static String EQUIPO = "EQ_ID INTEGER not null primary key," +
" EQ_NOMBRE VARCHAR(45)," +
" EQ_LICENCIA VARCHAR(45)," +
" EQ_BD VARCHAR(100)," +
" EQ_BD_USER VARCHAR(100)," +
" EQ_BD_PASS VARCHAR(100)," +
" EQ_BD_URL VARCHAR(100)," +
" EQ_ESTADO INTEGER," +
" EQ_LAST_UPDATE DATE," +
" EQ_LAST_HOUR INTEGER";
    private static String HISTORIAL_PAGO = "HP_ID VARCHAR(25)," +
" HP_FECHA DATE," +
" HP_ABONO INTEGER," +
" TIPO_PAGO_TP_ID INTEGER," +
" VENTA_VEN_ID VARCHAR(25)," +
" HP_ESTADO INTEGER," +
" HP_LAST_UPDATE DATE," +
" HP_LAST_HOUR INTEGER";
    private static String PROVEEDOR = "PRO_ID VARCHAR(25) not null primary key," +
" PRO_NOMBRE VARCHAR(45)," +
" PRO_TELEFONO VARCHAR(25)," +
" PRO_MAIL VARCHAR(45)," +
" PRO_WEB VARCHAR(45)," +
" PRO_DIRECCION VARCHAR(45)," +
" PRO_COMUNA VARCHAR(45)," +
" PRO_CIUDAD VARCHAR(45)," +
" PRO_ESTADO INTEGER," +
" PRO_LAST_UPDATE DATE," +
" PRO_LAST_HOUR INTEGER";
    private static String INTERN_STOCK = "ID INTEGER not null primary key," +
" ID_ITEM VARCHAR(100) not null," +
" STOCK INTEGER," +
" ESTADO INTEGER";
    private static String INVENTARIO = "INV_ID INTEGER not null primary key," +
" INV_NOMBRE VARCHAR(45)," +
" INV_DESCRIPCION LONG VARCHAR," +
" INV_ESTADO INTEGER," +
" INV_LAST_UPDATE DATE," +
" INV_LAST_HOUR INTEGER";
    private static String ITEM = "ITM_ID VARCHAR(100) not null primary key," +
" ITM_FOTO VARCHAR(25)," +
" ITM_MARCA VARCHAR(45)," +
" ITM_CLASIFICACION INTEGER," +
" ITM_DESCRIPCION LONG VARCHAR," +
" ITM_PRECIO_REF INTEGER," +
" ITM_PRECIO_ACT INTEGER," +
" ITM_STOCK INTEGER," +
" ITM_STOCK_MIN INTEGER," +
" INVENTARIO_INV_ID INTEGER," +
" ITM_ESTADO INTEGER," +
" ITM_LAST_UPDATE DATE," +
" ITM_LAST_HOUR INTEGER";
   private static String MESSAGE = "MSG_ID INTEGER not null primary key," +
" US_ID_REMITENTE INTEGER," +
" US_ID_DESTINATARIO INTEGER," +
" MSG_ASUNTO VARCHAR(45)," +
" MSG_CONTENT LONG VARCHAR," +
" MSG_FECHA DATE," +
" MSG_HORA VARCHAR(25)," +
" MSG_ESTADO INTEGER," +
" MSG_LAST_UPDATE DATE," +
" MSG_LAST_HOUR INTEGER";
   private static String OFICINA = "OF_ID INTEGER not null primary key," +
" OF_NOMBRE VARCHAR(45)," +
" OF_DIRECCION VARCHAR(45)," +
" OF_CIUDAD VARCHAR(45)," +
" OF_TELEFONO1 VARCHAR(25)," +
" OF_TELEFONO2 VARCHAR(25)," +
" OF_EMAIL VARCHAR(45)," +
" OF_WEB VARCHAR(100)," +
" OF_ESTADO INTEGER," +
" OF_LAST_UPDATE DATE," +
" OF_LAST_HOUR INTEGER";
    private static String REGISTRO_BAJAS = "RB_ID VARCHAR(25) not null primary key," +
" RB_FECHA DATE," +
" ITEM_ITM_ID VARCHAR(100)," +
" RB_CANTIDAD INTEGER," +
" RB_OBS LONG VARCHAR," +
" RB_ESTADO INTEGER," +
" RB_LAST_UPDATE DATE," +
" RB_LAST_HOUR INTEGER";
    private static String TIPO_PAGO = "TP_ID INTEGER not null primary key," +
" TP_NOMBRE VARCHAR(45)," +
" TP_ESTADO INTEGER," +
" TP_LAST_UPDATE DATE," +
" TP_LAST_HOUR INTEGER";
    private static String USUARIO = "US_ID INTEGER not null primary key," +
" US_NOMBRE VARCHAR(45)," +
" US_USERNAME VARCHAR(45)," +
" US_EMAIL VARCHAR(45)," +
" US_PASS VARCHAR(100)," +
" US_TIPO INTEGER," +
" US_ESTADO INTEGER," +
" US_LAST_UPDATE DATE," +
" US_LAST_HOUR INTEGER";
    private static String COL_VENTA = "VEN_ID," +
" USUARIO_US_ID," +
" CLIENTE_CLI_RUT," +   
" VEN_FECHA," +
" VEN_FECHA_ENTREGA," +
" VEN_LUGAR_ENTREGA," +
" VEN_HORA_ENTREGA," +
" VEN_OBS," +
" VEN_VALOR_TOTAL," +
" VEN_DESCUENTO," +
" VEN_SALDO," +
" VEN_ESTADO," +
" VEN_LAST_UPDATE," +
" VEN_LAST_HOUR";
    private static String COL_CLIENTE = "CLI_RUT," +
" CLI_NOMBRE," +
" CLI_TELEFONO1," +
" CLI_TELEFONO2," +
" CLI_EMAIL," +
" CLI_DIRECCION," +
" CLI_COMUNA," +
" CLI_CIUDAD," +
" CLI_SEXO," +
" CLI_NACIMIENTO," +
" CLI_ESTADO," +
" CLI_LAST_UPDATE," +
" CLI_LAST_HOUR";
    private static String COL_DESCUENTO = "DES_ID," +
" DES_NOMBRE," +
" DES_DESCRIPCION," +
" DES_PORC," +
" DES_MONTO," +
" DES_ESTADO," +
" DES_LAST_UPDATE," +
" DES_LAST_HOUR";
    private static String COL_DESPACHO = "DSP_ID," +
" DSP_RUT," +
" DSP_NOMBRE," +
" DSP_FECHA," +
" FICHA_FCH_ID," +
" DSP_ESTADO," +
" DSP_LAST_UPDATE," +
" DSP_LAST_HOUR";
    private static String COL_DETALLE = "DET_ID," +
" VENTA_VEN_ID," +
" ITEM_ITM_ID," +
" DET_CANTIDAD," +
" DET_PRECIO_UNIT," +
" DET_ESTADO," +
" DET_LAST_UPDATE," +
" DET_LAST_HOUR";
    private static String COL_EQUIPO = "EQ_ID," +
" EQ_NOMBRE," +
" EQ_LICENCIA," +
" EQ_BD," +
" EQ_BD_USER," +
" EQ_BD_PASS," +
" EQ_BD_PASS," +
" EQ_ESTADO," +
" EQ_LAST_UPDATE," +
" EQ_LAST_HOUR";
    private static String COL_HISTORIAL_PAGO = "HP_ID," +
" HP_FECHA," +
" HP_ABONO," +
" TIPO_PAGO_TP_ID," +
" VENTA_VEN_ID," +
" HP_ESTADO," +
" HP_LAST_UPDATE," +
" HP_LAST_HOUR";
    private static String COL_PROVEEDOR = "PRO_ID," +
" PRO_NOMBRE," +
" PRO_TELEFONO," +
" PRO_EMAIL," +
" PRO_WEB," +
" PRO_DIRECCION," +
" PRO_COMUNA," +
" PRO_CIUDAD," +
" PRO_ESTADO," +
" PRO_LAST_UPDATE," +
" PRO_LAST_HOUR";
    private static String COL_INTERN_STOCK = "ID," +
" ID_ITEM," +
" STOCK," +
" ESTADO";
    private static String COL_INVENTARIO = "INV_ID," +
" INV_NOMBRE," +
" INV_DESCRIPCION," +
" INV_ESTADO," +
" INV_LAST_UPDATE," +
" INV_LAST_HOUR";
    private static String COL_ITEM = "ITM_ID," +
" ITM_FOTO," +
" ITM_MARCA," +
" ITM_CLASIFICACION," +
" ITM_DESCRIPCION," +
" ITM_PRECIO_REF," +
" ITM_PRECIO_ACT," +
" ITM_STOCK," +
" ITM_STOCK_MIN," +
" INVENTARIO_INV_ID," +
" ITM_ESTADO," +
" ITM_LAST_UPDATE," +
" ITM_LAST_HOUR";
   private static String COL_MESSAGE = "MSG_ID," +
" US_ID_REMITENTE," +
" US_ID_DESTINATARIO," +
" MSG_ASUNTO," +
" MSG_CONTENT," +
" MSG_FECHA," +
" MSG_HORA," +
" MSG_ESTADO," +
" MSG_LAST_UPDATE," +
" MSG_LAST_HOUR";
   private static String COL_OFICINA = "OF_ID," +
" OF_NOMBRE," +
" OF_DIRECCION," +
" OF_CIUDAD," +
" OF_TELEFONO1," +
" OF_TELEFONO2," +
" OF_EMAIL," +
" OF_WEB," +
" OF_ESTADO," +
" OF_LAST_UPDATE," +
" OF_LAST_HOUR";
    private static String COL_REGISTRO_BAJAS = "RB_ID," +
" RB_FECHA," +
" ITEM_ITM_ID," +
" RB_CANTIDAD," +
" RB_OBS," +
" RB_ESTADO," +
" RB_LAST_UPDATE," +
" RB_LAST_HOUR";
    private static String COL_TIPO_PAGO = "TP_ID," +
" TP_NOMBRE," +
" TP_ESTADO," +
" TP_LAST_UPDATE," +
" TP_LAST_HOUR";
    private static String COL_USUARIO = "US_ID," +
" US_NOMBRE," +
" US_USERNAME," +
" US_EMAIL," +
" US_PASS," +
" US_TIPO," +
" US_ESTADO," +
" US_LAST_UPDATE," +
" US_LAST_HOUR";
    
    public static Connection initDB(){
        return LcBd.crear();
    }
    
    /**
     * Elimina todos los datos y las tablas de la base de datos
     * @return 
     */
    public static Connection dropDB(){
        return LcBd.deleteAll();
    }
    
    public static void sincronizarTodo(){
        if(GV.syncEnabled()){
            //si la ultima fecha de actualizacion corresponde al dia actual
            //restamos un dia a LastUpdate para validar actualización
            StVars.resetAllPorcentaje();
            if(GV.isCurrentDate(StVars.getLastUpdate())){
                StVars.setLastUpdate(GV.dateSumaResta(StVars.getLastUpdate(), -1, "DAYS"));
            }
            if(sincronizar(allEntitiesForRemoteSync())){
                StVars.setLastUpdate(new Date());
                StVars.setSyncCount(StVars.getSyncCount()+1);
            }
        }else{
            Notification.showMsg("No se puede procesar la solicitud", 
                    "Se ha agotado el limite de sincronizaciones por día", 2);
        } 
    }
    
    public static List<Object> allEntitiesForRemoteSync(){
        List<Object> entities = new ArrayList<>();
        entities.add(new RegistroBaja());
        entities.add(new Item());
        entities.add(new TipoPago());
        entities.add(new Venta());
        entities.add(new Detalle());
        entities.add(new User());
        entities.add(new HistorialPago());
        entities.add(new Inventario());
        entities.add(new Cliente());
        entities.add(new Oficina());
        entities.add(new Descuento());
        entities.add(new Despacho());
        entities.add(new Equipo());
        entities.add(new Proveedor());
        entities.add(new InternMail());
        
        return entities;
    }
    
    public static boolean sincronizar(List<Object> listaObjetos){
        setSincronizar(true);
        StVars.calcularPorcentaje(listaObjetos.size(),"Preparando la sincronización");
        for (Object type : listaObjetos) {
            if(type instanceof Venta){
                type = new VentaDTO();
            }
            if(!sincronizeObject(type)){
                error = true;
                break;
            }
            StVars.calcularPorcentaje(listaObjetos.size(), "Sincronizando entidades [Tipo de datos:"+GV.getClassName(type).trim()+"]...");
        }
        StVars.resetAllPorcentaje();
        setSincronizar(false);
        if(error){
            Notification.showMsg("La sincrconización se ha suspendido", "No se sincronizaron los datos por uno de estos motivos:\n"
                    + "-Se ha cancelado manualmente\n"
                    + "-Ocurrió un error de datos en la red, compruebe su conexion a internet", 2);
            return false;
        }
        return true;
    }
    
    public static boolean sincronizeObject(Object object){
        if(NetWrk.isOnline()){
            if(!GV.sincronizacionIsStopped()){
                
                Dao.sincronize(object);
                
                return true;
            }
        }
        StVars.setReport("No se pudo sincronizar la base de datos...");
        return false;
    }
    
    /**
     * leimina todos los datos de las tablas
     * @return 
     */
    public static Connection truncateDB(){
        return LcBd.truncateAll();
    }
    
    public static String getLocalBdUser() {
        return BD_USER_LOCAL;
    }

    public static String getLocalBdPass() {
        return BD_PASS_LOCAL;
    }

    public static String getLocalBdUrl() {
        return BD_URL_LOCAL;
    }

    public static String getLocalBdName() {
        return BD_NAME_LOCAL;
    }
    
    public static String getRemoteBdUser() {
        return BD_USER_REMOTE;
    }

    public static String getRemoteBdPass() {
        return BD_PASS_REMOTE;
    }

    public static String getRemoteBdUrl() {
        return BD_URL_REMOTE;
    }

    public static String getRemoteBdName() {
        return BD_NAME_REMOTE;
    }
    public static String[] tableNamesDB(){
        String[] names = {"CLIENTE","DESCUENTO","DETALLE","DESPACHO",
                           "EQUIPO","HISTORIAL_PAGO","INVENTARIO","ITEM",
                           "MESSAGE","OFICINA","PROVEEDOR","INTERN_STOCK",
                           "REGISTRO_BAJAS","TIPO_PAGO","USUARIO","VENTA"};
        return names;
    }
    public static String[] tablesDB(){
        String[] tables = {CLIENTE,DESCUENTO,DETALLE,DESPACHO,
                           EQUIPO,HISTORIAL_PAGO,INVENTARIO,ITEM,
                           MESSAGE,OFICINA,PROVEEDOR,INTERN_STOCK,
                           REGISTRO_BAJAS,TIPO_PAGO,USUARIO,VENTA};
        if(tableNamesDB().length != tables.length){
            Notification.showMsg("Error al generar tablas", "Los objetos Array no coinciden\nDetalle: BDUtils:tablesBD()", 3);
        }
        return tables;
    }
    public static String[] tableDataDB(){
        String[] tables = {COL_CLIENTE,COL_DESCUENTO,COL_DETALLE,COL_DESPACHO,
                           COL_EQUIPO,COL_HISTORIAL_PAGO,COL_INVENTARIO,COL_ITEM,
                           COL_MESSAGE,COL_OFICINA,COL_PROVEEDOR,COL_INTERN_STOCK,
                           COL_REGISTRO_BAJAS,COL_TIPO_PAGO,COL_USUARIO,COL_VENTA};
        if(tableNamesDB().length != tables.length){
            Notification.showMsg("Error al generar tablas", "Los objetos Array no coinciden\nDetalle: BDUtils:tablesBD()", 3);
        }
        return tables;
    }
    
    private static void generarBackup(List<Object> listaObjetos){
        try {
            LcBd.cerrar();
            if(NetWrk.isOnline() && LcBd.obtener() != null){
                ZipUtils.zipperBackup();
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {
            Logger.getLogger(BDUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private static Object[][] listaObjetos(Object type){
        String[] T = tableNamesDB();
        String[] D = tableDataDB();
        int index = getIndex(type);
        if(T.length == D.length){
            return LcBd.select(T[index], D[index], null);
        }
        return null;
    }
    
    public static String nameObjeto(Object type){
        String[] T = tableNamesDB();
        String[] D = tableDataDB();
        int index = getIndex(type);
        if(T.length == D.length){
            return T[index];
        }
        return null;
    }
    
    private static int getIndex(Object type){
        if(type instanceof Cliente){
            return cli-1;
        }
        if(type instanceof Descuento){
            return des-1;
        }
        if(type instanceof Detalle){
            return det-1;
        }
        if(type instanceof Despacho){
            return dsp-1;
        }
        if(type instanceof Equipo){
            return eq-1;
        }
        if(type instanceof HistorialPago){
            return hp-1;
        }if(type instanceof Inventario){
            return inv-1;
        }if(type instanceof Item){
            return itm-1;
        }if(type instanceof InternMail){
            return msg-1;
        }if(type instanceof Oficina){
            return of-1;
        }if(type instanceof Proveedor){
            return pro-1;
        }if(type instanceof RegistroBaja){
            return rb-1;
        }if(type instanceof TipoPago){
            return tp-1;
        }if(type instanceof User){
            return usu-1;
        }if(type instanceof Venta){
            return ven-1;
        }
        return 0;
    }

    public static void listarVentassByDate(Date date1, Date date2) {
        LISTA_VENTAS = listarAllVentas(date1, date2, null, null, null);
    }
    
    public static void listarVentasByClient(String rut) {
        LISTA_VENTAS = listarAllVentas(null, null, null, rut, null);
    }
    
    public static void listarVentasByUser(String user) {
        LISTA_VENTAS = listarAllVentas(null, null, user, null, null);
    }
    
    public static void listarVentasByUserAndDate(String user, Date date1, Date date2) {
        LISTA_VENTAS = listarAllVentas(date1, date2, user, null, null);
    }
    
    public static Object openVentaByCod(String cod){
        List<Object> lista = listarAllVentas(null, null, null, null, cod);
        if(lista.size() > 0){
            return lista.get(0);
        }
        return null;
    }
    
    
    /**
     * retorna una lista de objetos que deben ser parseados a ResF
     * @return 
     */
    public static List<Object> getVentas(){
        return LISTA_VENTAS;
    }

    public static void setSincronizar(boolean value) {
        SINCRONIZAR = value;
    }

    public static boolean sincronizacion() {
        return SINCRONIZAR;
    }
    
    /**
     * Retorna una lista de entidades tipo ficha con todos sus datos sin incluir las anuladas, si userId y clientCod son null,
     * buscara por fecha, 
     * de lo contratrio vlidara un idVenta
     * @param dateFrom
     * @param dateTo
     * @param idUser
     * @param codClient
     * @param idVenta 
     * @return 
     */
    public static List<Object> listarVentas(Date dateTo, Date dateFrom,String idUser, String codClient, String idVenta){
        Dao load = new Dao();
        String idParam = GV.getWhereFromVentas(dateTo, dateFrom, idUser, codClient, idVenta);
        idParam = StVars.convertVentaIdToVentaList(idParam);
        return load.listar(idParam, new Venta());
    }
    
    /**
     * Retorna una lista de entidades tipo ficha con todos sus datos incluyendo las anuladas, si userId y clientCod son null,
     * buscara por fecha, 
     * de lo contratrio vlidara un idVenta
     * @param dateFrom
     * @param dateTo
     * @param idUser
     * @param codClient
     * @param idVenta 
     * @return 
     */
    public static List<Object> listarAllVentas(Date dateTo, Date dateFrom,String idUser, String codClient, String idVenta){
        Dao load = new Dao();
        String idParam = GV.getWhereFromAllVentas(dateTo, dateFrom, idUser, codClient, idVenta);
        idParam = StVars.convertVentaIdToVentaList(idParam);
        return load.listar(idParam, new Venta());
    }
    
    public static void resetAllDataSource(){
//        if(GV.tipoUserSuperAdmin() && 
//                Notification.getConfirmation("Confirmación crítica", 
//                "¿Estas seguro que deseas borrar todos los datos?", 2)){
            XmlUtils.cargarRegistroLocal();
//            backUpLocalBd();
            GV.setLastUpdate(GV.stringToDate("01-01-2001"));
            BDUtils.dropDB();
            BDUtils.initDB();
            XmlUtils.cargarRegistroLocal();
            BDUtils.sincronizarTodo();
//        }
    }
    
    public static void migrarAllOldData(){
        //todas las fichas tendran id con guion uno, no se deberá utilizar mas
//        migrarClientes();
//        migrarCristales();
//        migrarDescuentos();
//        migrarDespachos();
//        migrarDoctores();
//        migrarVentas();
//        migrarHistorialPago();
//        migrarInstitucion();
//        migrarLentes();
//        migrarRegistroBajas();
//        migrarUsers();
    }
    
    private static void migrarUsers() {
        List<Object> lista = mig.listar("-2", new User());
        List<Object> lista2 = new ArrayList<>();
        for (Object object : lista) {
            User user = (User)object;
                if(user.getTipo()== 2){
                    user.setTipo(4);
                }
                if(user.getTipo() == 1){
                    user.setTipo(2);
                }
                user.setId(user.getId()+2);
                lista2.add(user);
        }
        String [][] stList = (String[][]) transformList(lista2, new User());
        createExcel(stList, new User());
    }
    
    private static void migrarClientes() {
        List<Object> lista = mig.listar("-2", new Cliente());
        List<Object> lista2 = new ArrayList<>();
        int cont = 0;
        for (Object object : lista) {
            Cliente cli = (Cliente)object;
            cli.setComuna(cli.getComuna().replaceAll("ñ", "n"));
            cli.setCiudad(cli.getCiudad().replaceAll("ñ", "n"));
            cli.setDireccion(cli.getDireccion().replaceAll("ñ", "n"));
            if(cli.getCiudad().equals("Chañarañ")){
                cli.setCiudad("Chanaral");
            }
            cli.setNombre(cli.getNombre().replaceAll("ñ", "n"));
            lista2.add(cli);
            cont++;
        }
        String [][] stList = (String[][]) transformList(lista2, new Cliente());
        createExcel(stList, new Cliente());
    }
    
    
    private static void migrarDescuentos() {
        int maxId = 1;
        List<Object> lista = mig.listar("-2", new Descuento());
        List<Object> lista2 = new ArrayList<>();
        for (Object object : lista) {
            Descuento des = (Descuento)object;
                des.setId(maxId);
                lista2.add(des);
                maxId++;
        }
        String [][] stList = (String[][]) transformList(lista2, new Descuento());
        createExcel(stList, new Descuento());
    }
    

    private static void migrarDespachos() {
        List<Object> lista = mig.listar("-2", new Despacho());
        List<Object> lista2 = new ArrayList<>();
        for (Object object : lista) {
            Despacho temp = (Despacho)object;
            temp.setNombre(temp.getNombre().replaceAll("ñ", "n").replaceAll("Ñ", "N"));
            lista2.add(temp);
        }
        String [][] stList = (String[][]) transformList(lista2, new Despacho());
        createExcel(stList, new Despacho());
    }

    private static void migrarHistorialPago() {
        List<Object> lista = mig.listar("-2", new HistorialPago());
        for (Object object : lista) {
            HistorialPago temp = (HistorialPago)object;
            if(temp.getIdTipoPago() == 0){
                temp.setIdTipoPago(2);
            }else{
                temp.setIdTipoPago(temp.getIdTipoPago()+1);
            }
        }
        String [][] stList = (String[][]) transformList(lista, new HistorialPago());
        createExcel(stList, new HistorialPago());
    }

    private static void migrarRegistroBajas() {
        List<Object> lista = mig.listar("-2", new RegistroBaja());
        String [][] stList = (String[][]) transformList(lista, new RegistroBaja());
        createExcel(stList, new RegistroBaja());
    }
    
    private static boolean createExcel(Object[][] res, Object type){
        if(res == null || res.length < 1){
            return false;
        }
        if(res.length > 0){
            try {
                XlsUtils.generarExcelRespaldo((String [][])res, BDUtils.nameObjeto(type));
            } catch (WriteException ex) {
                Logger.getLogger(BDUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true;
        }
        return false;
    }
    
    public static Object[][] transformList(List<Object> lista, Object type){
        if(type instanceof VentaDTO){
            int columnas = 18;
            String[][] stList = new String[lista.size()][columnas];
            int i=0;
            for (Object object : lista) {
                VentaDTO temp = (VentaDTO)object;
                stList[i][0]=""+temp.getCod();
                stList[i][10]=""+temp.getIdVendedor();
                stList[i][9]=""+temp.getRutCliente();
                stList[i][1]=getSqlDate(temp.getFecha());
                stList[i][2]=getSqlDate(temp.getFechaEntrega());
                stList[i][3]=""+temp.getLugarEntrega();
                stList[i][4]=""+temp.getHoraEntrega();
                stList[i][5]=""+temp.getObservacion().replaceAll("\n", "").replaceAll("\"", "");
                stList[i][6]=""+temp.getValorTotal();
                stList[i][7]=""+temp.getDescuento();
                stList[i][8]=""+temp.getSaldo();
                stList[i][15]=""+temp.getEstado();
                stList[i][16]=""+getSqlDate(temp.getLastUpdate());
                stList[i][17]=""+temp.getLastHour();
                i++;
            }
            return stList;
        }
        if(type instanceof User){
            int columnas = 9;
            String[][] stList = new String[lista.size()][columnas];
            int i=0;
            for (Object object : lista) {
                User temp = (User)object;
                stList[i][0]=""+temp.getId();
                stList[i][1]=""+temp.getNombre();
                stList[i][2]=""+temp.getUsername();
                stList[i][3]=""+temp.getEmail();
                stList[i][4]=""+temp.getPass();
                stList[i][5]=""+temp.getTipo();
                stList[i][6]=""+temp.getEstado();
                stList[i][7]=""+getSqlDate(temp.getLastUpdate());
                stList[i][8]=""+temp.getLastHour();
                i++;
            }
            return stList;
        }
        if(type instanceof Cliente){
            int columnas = 13;
            String[][] stList = new String[lista.size()][columnas];
            int i=0;
            for (Object object : lista) {
                Cliente temp = (Cliente)object;
                stList[i][0]=""+temp.getCod();
                stList[i][1]=""+temp.getNombre();
                stList[i][2]=""+temp.getTelefono1();
                stList[i][3]=""+temp.getTelefono2();
                stList[i][4]=""+temp.getEmail();
                stList[i][5]=""+temp.getDireccion();
                stList[i][6]=""+temp.getComuna();
                stList[i][7]=""+temp.getCiudad();
                stList[i][8]=""+temp.getSexo();
                stList[i][9]=""+getSqlDate(temp.getNacimiento());
                stList[i][10]=""+temp.getEstado();
                stList[i][11]=""+getSqlDate(temp.getLastUpdate());
                stList[i][12]=""+temp.getLastHour();
                i++;
            }
            return stList;
        }
        if(type instanceof Descuento){
            int columnas = 8;
            String[][] stList = new String[lista.size()][columnas];
            int i=0;
            for (Object object : lista) {
                Descuento temp = (Descuento)object;
                stList[i][0]=""+temp.getId();
                stList[i][1]=""+temp.getNombre();
                stList[i][2]=""+temp.getDescripcion();
                stList[i][3]=""+temp.getPorcetange();
                stList[i][4]=""+temp.getMonto();
                stList[i][5]=""+temp.getEstado();
                stList[i][6]=""+getSqlDate(temp.getLastUpdate());
                stList[i][7]=""+temp.getLastHour();
                i++;
            }
            return stList;
        }
        if(type instanceof Despacho){
            int columnas = 8;
            String[][] stList = new String[lista.size()][columnas];
            int i=0;
            for (Object object : lista) {
                Despacho temp = (Despacho)object;
                stList[i][0]=""+temp.getCod();
                stList[i][1]=""+temp.getRut();
                stList[i][2]=GV.getToName(temp.getNombre());
                stList[i][3]=getSqlDate(temp.getFecha());
                stList[i][4]=""+temp.getIdVenta();
                stList[i][5]=""+temp.getEstado();
                stList[i][6]=""+getSqlDate(temp.getLastUpdate());
                stList[i][7]=""+temp.getLastHour();
                i++;
            }
            return stList;
        }
        if(type instanceof HistorialPago){
            int columnas = 8;
            String[][] stList = new String[lista.size()][columnas];
            int i=0;
            for (Object object : lista) {
                HistorialPago temp = (HistorialPago)object;
                stList[i][0]=""+temp.getCod();
                stList[i][1]=getSqlDate(temp.getFecha());
                stList[i][2]=""+temp.getAbono();
                stList[i][3]=""+temp.getIdTipoPago();
                stList[i][4]=""+temp.getIdVenta();
                stList[i][5]=""+temp.getEstado();
                stList[i][6]=""+getSqlDate(temp.getLastUpdate());
                stList[i][7]=""+temp.getLastHour();
                i++;
            }
            return stList;
        }
        return null;
            
    }
    
    private static String getSqlDate(Date date){
        return GV.dateToString(date, "yyyy-mm-dd");
    }

    public static String getSqlInsert(Object objectParam) {
        if(objectParam instanceof Cliente){
            Cliente object = (Cliente)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getNacimiento().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO cliente VALUES('"
                    + object.getCod() + "','"
                    + object.getNombre() + "','"
                    + object.getTelefono1() + "','"
                    + object.getTelefono2() + "','"
                    + object.getEmail() + "','"
                    + object.getDireccion() + "','"
                    + object.getComuna() + "','"
                    + object.getCiudad() + "',"
                    + object.getSexo() + ",'"
                    + sqlfecha1 + "',"
                    + object.getEstado() + ",'"
                    + sqlfecha2 + "',"
                    + object.getLastHour() + ")";
        }
        if(objectParam instanceof Descuento){
            Descuento object = (Descuento)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO descuento VALUES("
                    + object.getId() + ",'"
                    + object.getNombre() + "','"
                    + object.getDescripcion() + "',"
                    + object.getPorcetange() + ","
                    + object.getMonto() + ","
                    + object.getEstado() + ",'"
                    + sqlfecha + "',"
                    + object.getLastHour() + ")";
        }
        if(objectParam instanceof Detalle){
            Detalle object = (Detalle)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO detalle VALUES('"
                    + object.getCod()+ "','"
                    + object.getIdVenta()+ "','"
                    + object.getIdItem()+ "',"
                    + object.getCantidad()  + ","
                    + object.getPrecioUnitario()+ ","
                    + object.getEstado() + ",'"
                    + sqlfecha + "',"
                    + object.getLastHour() + ")";
        }
        if(objectParam instanceof Despacho){
            Despacho object = (Despacho)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO despacho VALUES('"
                    + object.getCod()+ "','"
                    + object.getRut()+ "','"
                    + object.getNombre()+ "','"
                    + sqlfecha1 + "','"
                    + object.getIdVenta()+ "',"
                    + object.getEstado() + ",'"
                    + sqlfecha2 + "',"
                    + object.getLastHour() + ")";
        }
        if(objectParam instanceof Equipo){
            Equipo object = (Equipo)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO equipo VALUES("
                            + object.getId()+ ",'"
                            + object.getNombre() + "','"
                            + object.getLicencia()+ "','"
                            + object.getBd()+ "','"
                            + object.getBdUser()+ "','"
                            + object.getBdPass()+ "','"
                            + object.getBdUrl()+ "',"
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof HistorialPago){
            HistorialPago object = (HistorialPago)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO historial_pago VALUES('"
                            + object.getCod()+ "','"
                            + sqlfecha1 + "',"
                            + object.getAbono()+ ","
                            + object.getIdTipoPago()+ ",'"
                            + object.getIdVenta()+ "',"
                            + object.getEstado() + ",'"
                            + sqlfecha2 + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof Inventario){
            Inventario object = (Inventario)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO inventario VALUES("
                            + object.getId()+ ",'"
                            + object.getNombre() + "','"
                            + object.getDescripcion()+ "',"
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof Item){
            Item object = (Item)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO item VALUES('"
                            + object.getCod()+ "','"
                            + object.getFoto()+ "','"
                            + object.getMarca()+ "',"
                            + object.getClasificacion()+ ",'"
                            + object.getDescripcion()+ "',"
                            + object.getPrecioRef()+ ","
                            + object.getPrecioAct()+ ","
                            + object.getStock()+ ","
                            + object.getStockMin()+ ","
                            + object.getInventario()+ ","
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof InternMail){
            InternMail object = (InternMail)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            int idRemitente = (object.getRemitente()!= null)?object.getRemitente().getId():0;
            int idDestinatario = (object.getDestinatario() != null)?object.getDestinatario().getId():0;
            return  "INSERT INTO message VALUES("
                            + object.getId()+ ","
                            + idRemitente + ","
                            + idDestinatario+ ",'"
                            + object.getAsunto()+ "','"
                            + object.getContenido()+ "','"
                            + sqlfecha1+ "','"
                            + object.getHora() + "',"
                            + object.getEstado()+ ",'"
                            + sqlfecha2 + "',"
                            + object.getLastHour()+ ")";
        }
        if(objectParam instanceof Oficina){
            Oficina object = (Oficina)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO oficina VALUES("
                            + object.getId() + ",'"
                            + object.getNombre() + "','"
                            + object.getDireccion() + "','"
                            + object.getCiudad() + "','"
                            + object.getTelefono1() + "','"
                            + object.getTelefono2() + "','"
                            + object.getEmail() + "','"
                            + object.getWeb() + "',"
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof Proveedor){
            Proveedor object = (Proveedor)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO proveedor VALUES('"
                            + object.getCod()+ "','"
                            + object.getNombre() + "','"
                            + object.getTelefono() + "','"
                            + object.getEmail() + "','"
                            + object.getWeb() + "','"
                            + object.getDireccion() + "','"
                            + object.getComuna()+ "','"
                            + object.getCiudad() + "',"
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof RegistroBaja){
            RegistroBaja object = (RegistroBaja)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO registro_bajas VALUES('"
                            + object.getCod()+ "','"
                            + sqlfecha1 + "','"
                            + object.getIdItem()+ "',"
                            + object.getCantidad()+ ",'"
                            + object.getObs()+ "',"
                            + object.getEstado() + ",'"
                            + sqlfecha2 + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof TipoPago){
            TipoPago object = (TipoPago)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO tipo_pago VALUES("
                            + object.getId()+ ",'"
                            + object.getNombre() + "',"
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof User){
            User object = (User)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO usuario VALUES("
                            + object.getId() + ",'"
                            + object.getNombre() + "','"
                            + object.getUsername() + "','"
                            + object.getEmail() + "','"
                            + object.getPass() + "',"
                            + object.getTipo() + ","
                            + object.getEstado() + ",'"
                            + sqlfecha + "',"
                            + object.getLastHour() + ")";
        }
        if(objectParam instanceof Venta){
            objectParam = new VentaDTO((Venta)objectParam);
        }
        if(objectParam instanceof VentaDTO){
            VentaDTO object = (VentaDTO)objectParam;
            java.sql.Date fecha = new java.sql.Date(object.getFecha().getTime());//la transforma a sql.Date
            java.sql.Date fechaEntrega = new java.sql.Date(object.getFechaEntrega().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "INSERT INTO venta VALUES('"
                    + object.getCod() + "',"
                    + object.getIdVendedor()+ ",'"
                    + object.getRutCliente()+ "','"
                    + fecha+ "','"
                    + fechaEntrega+ "','"
                    + object.getLugarEntrega()+ "','"
                    + object.getHoraEntrega()+ "','"
                    + object.getObservacion()+ "',"
                    + object.getValorTotal()+ ","
                    + object.getDescuento()+ ","
                    + object.getSaldo()+ ","
                    + object.getEstado()+ ",'"
                    + sqlfecha1 + "',"
                    + object.getLastHour() + ")";
        }
        return null;
    }

    public static String getSqlUpdate(Object objectParam) {
        if(objectParam instanceof Cliente){
            Cliente object = (Cliente)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getNacimiento().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return "UPDATE cliente set cli_nombre = '" + object.getNombre()
                        + "', cli_telefono1 = '" + object.getTelefono1()
                        + "', cli_telefono2 = '" + object.getTelefono2()
                        + "', cli_email = '" + object.getEmail()
                        + "', cli_direccion = '" + object.getDireccion()
                        + "', cli_comuna = '" + object.getComuna()
                        + "', cli_ciudad = '" + object.getCiudad()
                        + "', cli_sexo = " + object.getSexo()
                        + ", cli_nacimiento = '" + sqlfecha1
                        + "', cli_estado = " + object.getEstado()
                        + ", cli_last_update = '" + sqlfecha2
                        + "', cli_last_hour = " + object.getLastHour()
                        + " WHERE cli_rut = '" + object.getCod() 
                        + "' AND ((cli_last_update < '"+sqlfecha2+"')OR"
                        + "(cli_last_update = '"+sqlfecha2+"' AND cli_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Descuento){
            Descuento object = (Descuento)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE descuento set des_nombre = '" + object.getNombre()
                        + "', des_descripcion = '" + object.getDescripcion()
                        + "', des_porc = " + object.getPorcetange()
                        + ", des_monto = " + object.getMonto()
                        + ", des_estado = " + object.getEstado()
                        + ", des_last_update = '" + sqlfecha
                        + "', des_last_hour = " + object.getLastHour()
                        + " WHERE des_id = " + object.getId() 
                        + " AND ((des_last_update < '"+sqlfecha+"')OR"
                        + "(des_last_update = '"+sqlfecha+"' AND des_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Detalle){
            Detalle object = (Detalle)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE detalle set venta_ven_id = '" + object.getIdVenta()
                        + "', item_itm_id = '" + object.getIdItem()
                        + "', det_cantidad = " + object.getCantidad()
                        + ", det_precio_unit = " + object.getPrecioUnitario()
                        + ", det_estado = " + object.getEstado()
                        + ", det_last_update = '" + sqlfecha
                        + "', det_last_hour = " + object.getLastHour()
                        + " WHERE det_id = '" + object.getCod()
                        + "' AND ((det_last_update < '"+sqlfecha+"')OR"
                        + "(det_last_update = '"+sqlfecha+"' AND det_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Despacho){
            Despacho object = (Despacho)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE despacho set dsp_rut = '" + object.getRut()
                        + "', dsp_nombre = '" + object.getNombre()
                        + "', dsp_fecha = '" + sqlfecha1
                        + "', venta_ven_id = '" + object.getIdVenta()
                        + "', dsp_estado = " + object.getEstado()
                        + ", dsp_last_update = '" + sqlfecha2
                        + "', dsp_last_hour = " + object.getLastHour()
                        + " WHERE dsp_id = '" + object.getCod()
                        + "' AND ((dsp_last_update < '"+sqlfecha2+"')OR"
                        + "(dsp_last_update = '"+sqlfecha2+"' AND dsp_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Equipo){
            Equipo object = (Equipo)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE equipo set eq_nombre = '" + object.getNombre()
                        + "', eq_licencia = '" + object.getLicencia()
                        + "', eq_bd = '" + object.getBd()
                        + "', eq_bd_user = '" + object.getBdUser()
                        + "', eq_bd_pass = '" + object.getBdPass()
                        + "', eq_bd_url = '" + object.getBdUrl()
                        + "', eq_estado = " + object.getEstado()
                        + ", eq_last_update = '" + sqlfecha
                        + "', eq_last_hour = " + object.getLastHour()
                        + " WHERE eq_id = " + object.getId()    
                        + " AND ((eq_last_update < '"+sqlfecha+"')OR"
                        + "(eq_last_update = '"+sqlfecha+"' AND eq_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof HistorialPago){
            HistorialPago object = (HistorialPago)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE historial_pago set hp_fecha = '" + sqlfecha1
                        + "', hp_abono = " + object.getAbono()
                        + ", tipo_pago_tp_id = " + object.getIdTipoPago()
                        + ", venta_ven_id = '"+object.getIdVenta()
                        + "', hp_estado = "+object.getEstado()
                        + ", hp_last_update = '" + sqlfecha2
                        + "', hp_last_hour = " + object.getLastHour()
                        + " WHERE hp_id = '" + object.getCod()
                        + "' AND ((hp_last_update < '"+sqlfecha2+"')OR"
                        + "(hp_last_update = '"+sqlfecha2+"' AND hp_last_hour < "+object.getLastHour()+"))";
        }
        
        if(objectParam instanceof Inventario){
            Inventario object = (Inventario)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE inventario set inv_nombre = '" + object.getNombre()
                        + "', inv_descripcion = '" + object.getDescripcion()
                        + "', inv_estado = " + object.getEstado()
                        + ", inv_last_update = '" + sqlfecha
                        + "', inv_last_hour = " + object.getLastHour()
                        + " WHERE inv_id = " + object.getId()
                        + " AND ((inv_last_update < '"+sqlfecha+"')OR"
                        + "(inv_last_update = '"+sqlfecha+"' AND inv_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Item){
            Item object = (Item)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE item set itm_foto = '" + object.getFoto()
                        + "', itm_marca = '" + object.getMarca()
                        + "', itm_clasificacion = " + object.getClasificacion()
                        + ", itm_descripcion = '" + object.getDescripcion()
                        + "', itm_precio_ref = " + object.getPrecioRef()
                        + ", itm_precio_act = " + object.getPrecioAct()
                        + ", itm_stock = " + object.getStock()
                        + ", itm_stock_min = " + object.getStockMin()
                        + ", inventario_inv_id = " + object.getInventario()
                        + ", itm_estado = " + object.getEstado()
                        + ", itm_last_update = '" + sqlfecha
                        + "', itm_last_hour = " + object.getLastHour()
                        + " WHERE itm_id = '" + object.getCod()
                        + "' AND ((itm_last_update < '"+sqlfecha+"')OR"
                        + "(itm_last_update = '"+sqlfecha+"' AND itm_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof InternMail){
            InternMail object = (InternMail)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE message set us_id_remitente = " + object.getRemitente().getId()
                        + ", us_id_destinatario = " + object.getDestinatario().getId()
                        + ", msg_asunto = '" + object.getAsunto()
                        + "', msg_content = '" + object.getContenido()
                        + "', msg_fecha = '" + sqlfecha1
                        + "', msg_hora = '" + object.getHora()
                        + "', msg_estado = " + object.getEstado()
                        + ", msg_last_update = '" + sqlfecha2
                        + "', msg_last_hour = " + object.getLastHour()
                        + " WHERE msg_id = " + object.getId()
                        + " AND ((msg_last_update < '"+sqlfecha2+"')OR"
                        + "(msg_last_update = '"+sqlfecha2+"' AND msg_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Oficina){
            Oficina object = (Oficina)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE oficina set of_nombre = '" + object.getNombre()
                        + "', of_direccion = '" + object.getDireccion()
                        + "', of_ciudad = '" + object.getCiudad()
                        + "', of_telefono1 = '" + object.getTelefono1()
                        + "', of_telefono2 = '" + object.getTelefono2()
                        + "', of_email = '" + object.getEmail()
                        + "', of_web = '" + object.getWeb()
                        + "', of_estado = " + object.getEstado()
                        + ", of_last_update = '" + sqlfecha
                        + "', of_last_hour = " + object.getLastHour()
                        + " WHERE of_id = " + object.getId() 
                        + " AND ((of_last_update < '"+sqlfecha+"')OR"
                        + "(of_last_update = '"+sqlfecha+"' AND of_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Proveedor){
            Proveedor object = (Proveedor)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE proveedor set pro_nombre = '" + object.getNombre()
                        + "', pro_telefono = '" + object.getTelefono()
                        + "', pro_email = '" + object.getEmail()
                        + "', pro_web = '" + object.getWeb()
                        + "', pro_direccion = '" + object.getDireccion()
                        + "', pro_comuna = '" + object.getComuna()
                        + "', pro_ciudad = '" + object.getCiudad()
                        + "', pro_estado = " + object.getEstado()
                        + ", pro_last_update = '" + sqlfecha
                        + "', pro_last_hour = " + object.getLastHour()
                        + " WHERE pro_id = '" + object.getCod()
                        + "' AND ((pro_last_update < '"+sqlfecha+"')OR"
                        + "(pro_last_update = '"+sqlfecha+"' AND pro_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof RegistroBaja){
            RegistroBaja object = (RegistroBaja)objectParam;
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());//la transforma a sql.Date
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE registro_bajas set rb_fecha = '" + sqlfecha1
                        + "', item_itm_id = '" + object.getIdItem()
                        + "', rb_cantidad = " + object.getCantidad()
                        + ", rb_obs = '" + object.getObs()
                        + "', rb_estado = " + object.getEstado()
                        + ", rb_last_update = '" + sqlfecha2
                        + "', rb_last_hour = " + object.getLastHour()
                        + " WHERE rb_id = '" + object.getCod()
                        + "' AND ((rb_last_update < '"+sqlfecha2+"')OR"
                        + "(rb_last_update = '"+sqlfecha2+"' AND rb_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof TipoPago){
            TipoPago object = (TipoPago)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE tipo_pago set tp_nombre = '" + object.getNombre()
                        + "', tp_estado = " + object.getEstado()
                        + ", tp_last_update = '" + sqlfecha
                        + "', tp_last_hour = " + object.getLastHour()
                        + " WHERE tp_id = " + object.getId()
                        + " AND ((tp_last_update < '"+sqlfecha+"')OR"
                        + "(tp_last_update = '"+sqlfecha+"' AND tp_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof User){
            User object = (User)objectParam;
            String mail = "', us_email = '" + object.getEmail();
            if(object.getUsername().equals("admin")){
                mail = (GV.getStr(object.getEmail()).isEmpty())? "":"', us_email = '" + object.getEmail();
            }
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());//la transforma a sql.Date
            return  "UPDATE usuario set us_nombre = '" + object.getNombre()
                        + "', us_username = '" + object.getUsername()
                        + mail
                        + "', us_pass = '" + object.getPass()
                        + "', us_tipo = " + object.getTipo()
                        + ", us_estado = " + object.getEstado()
                        + ", us_last_update = '" + sqlfecha
                        + "', us_last_hour = " + object.getLastHour()
                        + " WHERE us_id = " + object.getId()
                        + " AND ((us_last_update < '"+sqlfecha+"')OR"
                        + "(us_last_update = '"+sqlfecha+"' AND us_last_hour < "+object.getLastHour()+"))";
        }
        if(objectParam instanceof Venta){
            objectParam = new VentaDTO((Venta)objectParam);
        }
        if(objectParam instanceof VentaDTO){
            VentaDTO object = (VentaDTO)objectParam;
            java.sql.Date sqlfecha = new java.sql.Date(object.getLastUpdate().getTime());
            java.sql.Date sqlfecha1 = new java.sql.Date(object.getFecha().getTime());
            java.sql.Date sqlfecha2 = new java.sql.Date(object.getFechaEntrega().getTime());//la transforma a sql.Date
            return "UPDATE venta set usuario_us_id = " + object.getIdVendedor()
                        + ", cliente_cli_rut = '" + object.getRutCliente()
                        + "', ven_fecha = '" + sqlfecha1
                        + "', ven_fecha_entrega = '" + sqlfecha2
                        + "', ven_lugar_entrega = '" + object.getLugarEntrega()
                        + "', ven_hora_entrega = '" + object.getHoraEntrega()
                        + "', ven_obs = '" + object.getObservacion()
                        + "', ven_valor_total = " + object.getValorTotal()
                        + ", ven_descuento = " + object.getDescuento()
                        + ", ven_saldo = " + object.getSaldo()
                        + ", ven_estado = " + object.getEstado()
                        + ", ven_last_update = '" + sqlfecha
                        + "', ven_last_hour = " + object.getLastHour()
                        + " WHERE ven_id = '" + object.getCod() 
                        + "' AND ((ven_last_update < '"+sqlfecha+"')OR"
                        + "(ven_last_update = '"+sqlfecha+"' AND ven_last_hour < "+object.getLastHour()+"))";
        }
        return null;
    }
}
