/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.bd.LcBd;
import cl.softdirex.enubase.dao.Dao;
import cl.softdirex.enubase.entities.Equipo;
import cl.softdirex.enubase.view.notifications.Notification;
import cl.softdirex.enubase.view.splash.SplashProgress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sdx
 */
public class StVars {
    /*  Nombres de sistema  */
    private static String PROJECTNAME="Optics";
    private static String VERSION = "v4.1.3";
    private static String EQUIPO;//el nombre debe concatenarse con la fecha de instalacion
    private static int EQUIPO_ID = 1;
    private static String INVENTARIO_NAME;
    private static int ID_INVENTARIO_SELECCIONADO = 0;
    private static String COMPANY_DESCRIPTION;
    private static String COMPANY_RUT;
    private static String COMPANY_GIRO;
    private static String MESSAGE_FILE;//texto informativo que se imprimirá en la ficha
    private static boolean IS_WINDOWS = true;
    
    /*porcentajes*/
    private static int PORC = 0;
    private static int SUMA_PORC = 0;
    private static int LIMIT = 0;
    private static int INI = 0;
    private static int FIN = 0;
    private static String REPORT;
    private static SplashProgress sp = null;
    
    /* Seguridad */
    private static String SALT = PropertiesUtils.getSecuritySalt();//favorite pet
    private static String PASS;
    private static int INTENTOS_ACCESO = 0;
    
    /* LICENCIA */
    private static String COMPANY_NAME;
    private static int TIPO_PLAN = 0;
    private static String LICENCE_CODE = null;
    private static boolean LICENCE_ACTIVE = false;
    private static String EXP_DATE;
    private static String API_URI;
    private static int SYNC_COUNT = 0;
    
    //TIPO DE PLAN
    private static int TP_FREE=0;
    private static int TP_LOCAL = 1;
    private static int TP_2X = 2;
    private static int TP_4X = 3;
    private static int TP_6X = 4;
    private static int TP_FULL_DATA = 5;
    
    
    //MAXIMO DE SINCRONIZACIONES SEGUN TIPO DE PLAN
    public static int TP_2X_MS = 2;
    public static int TP_4X_MS = 4;
    public static int TP_6X_MS = 6;
    
    /* Update */
    private static int ID_UPDATE=0;
    private static String PORT_KEY;
    
    
    /* Variables del sistema */
    
    private static String USERNAME;
    private static int ID_USER = 0;
    private static Date TMP_DATE_FROM = null;
    private static Date TMP_DATE_TO =null;
    private static String ID_PARAM_IS_USER = "USER/";
    private static String ID_PARAM_IS_CLIENT = "CLIENT/";
    private static String ID_PARAM_IS_TABLE_LIST = "LIST/";
    private static String ID_PARAM_IS_DATE_LIST = "DATE/";
    private static String FECHA_DEFAULT = "01-01-2001";//NO MODIFICAR
    private static String RUT_CLIENT_SELECTED="";
    private static String ID_USER_SELECTED="";
    public static Date LAST_UPDATE;
    private static String SQL_LOW_STOCK="lowStock";
    private static String ID_PARAM_IS_VENTA_LIST = "LISTAR_VENTAS/";
    
    public static int ITEMS_STOCK;
    public static int ITEMS_STOCK_BAJO;
    public static int ITEMS_STOCK_CERO;
    
    public static int ITEMS_COMPRA;
    public static int ITEMS_VENTA;
    
    public static String ITEMS_INVENTARIO;
    //used in filterList()
    private static List<String> FILTER_LIST = new ArrayList<>();
    
    /*
        Modificaciones tambien afectan en las consultas a bases de datos
    */
    private static final int DELETED = 0;
    private static final int PENDING = 1;
    private static final int PAID = 2;
    private static final int DELIVERED = 3;
    private static final int WARRANTY = 4;
    
    /* Mail */
    public static String MAIL_ADDRES = "sdx.respaldo.bd@gmail.com";
    public static String MAIL_PASS= "qwpzedzqucvpyjzt";
    public static String MAIL_REPORT= "softdirex@gmail.com";
    public static String MAIL_LOG = "";
    public static String LOGO_MAIL = "https://www.softdirex.cl/imgOptics/report/logo.png";
    public static String ICON_COMPANY_MAIL = "https://www.softdirex.cl/imgOptics/report/company.png";
    public static String ICON_USER_MAIL = "https://www.softdirex.cl/imgOptics/report/user.png";
    
    
    private static List<String> filterList(){
        FILTER_LIST.clear();
        FILTER_LIST.add("<");
        FILTER_LIST.add(">");
        FILTER_LIST.add(ID_PARAM_IS_USER);
        FILTER_LIST.add(ID_PARAM_IS_CLIENT);
        FILTER_LIST.add(ID_PARAM_IS_TABLE_LIST);
        FILTER_LIST.add(ID_PARAM_IS_DATE_LIST);
        return FILTER_LIST;
    }
    
    public static String getCompanyDescription(){
        return GV.getStr(COMPANY_DESCRIPTION);
    }
    
    public static void setCompanyDescription(String companyDescription){
        COMPANY_DESCRIPTION = companyDescription;
    }
    
    public static String getCompanyRut(){
        return GV.getStr(COMPANY_RUT);
    }
    
    public static void setCompanyRut(String companyRut){
        COMPANY_RUT = companyRut;
    }
    
    public static String getCompanyGiro(){
        return GV.getStr(COMPANY_GIRO);
    }
    
    public static void setCompanyGiro(String companyGiro){
        COMPANY_GIRO = companyGiro;
    }
    
    public static String getMessageFile(){
        return GV.getStr(MESSAGE_FILE);
    }
    
    public static void setMessageFile(String messageFile){
        MESSAGE_FILE = messageFile;
    }
    
    public static void setInventarioLocal(String inventario){
        INVENTARIO_NAME = GV.getStr(inventario);
    }
    
    public static int estadoFichaDeleted(){
        return DELETED;
    }
    
    public static int estadoFichaPending(){
        return PENDING;
    }
    
    public static int estadoFichaPaid(){
        return PAID;
    }
    
    public static int estadoFichaDelivered(){
        return DELIVERED;
    }
    
    public static int estadoFichaWarranty(){
        return WARRANTY;
    }
    
    public static void setCompanyName(String nombre) {
        COMPANY_NAME = GV.getToName(nombre);
    }
    
    public static String getCompanyName(){
        String value = (GV.getStr(COMPANY_NAME).isEmpty())?"[Empresa no ingresada]":GV.getStr(COMPANY_NAME);
        return value;
    }

    public static String getProjectName() {
        return GV.getStr(PROJECTNAME);
    }
    
    public static String getVersion(){
        return GV.getStr(VERSION);
    }
    
    public static int getLicenciaTipoPlan(){
        return TIPO_PLAN;
    }
    
    public static String getLicenceCode(){
        return GV.getStr(LICENCE_CODE);
    }
    
    public static String getExpDate(){
        return GV.getStr(EXP_DATE);
    }

    public static String getInventarioName() {
        return GV.getStr(INVENTARIO_NAME);
    }
    
    public static Date getDateFrom(){
        return TMP_DATE_FROM;
    }
    
    public static void setDateFrom(Date date){
        TMP_DATE_FROM = date;
    }
    
    public static Date getDateTo(){
        return TMP_DATE_TO;
    }
    
    public static void setDateTo(Date date){
        TMP_DATE_TO = date;
    }
    
    public static String getSalt(){
        return GV.getStr(SALT);
    }
    
    public static String getEquipo(){
        return GV.getToName(EQUIPO);
    }
    
    public static void setCurrentEquipo(String equipo){
        try {
            LcBd.cerrar();
            EQUIPO = GV.getToName(equipo);
            
            if(LICENCE_CODE != null){
                Dao load = new Dao();
                Equipo e = (Equipo)load.get(EQUIPO, 0, new Equipo());
                //Si ya existe un equipo con la misma licencia se cierra el programa
                if(e == null || !e.getNombre().equals(EQUIPO)){
                    if(e != null){
                        if(e.getEstado() != 0){
                            Notification.showMsg("Licencia duplicada", "Esta licencia ya se encuentra asociada y vigente.\n"
                                    + "Solicite una nueva licencia para este equipo.\n"
                                    + "Conflicto con equipo: "+e.getNombre(), 3);
                            XmlUtils.deleteXmlFiles();
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(StVars.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            System.exit(0);
                        }
                    }
                    e = new Equipo(0, EQUIPO, LICENCE_CODE,
                        GV.enC(BDUtils.BD_NAME_REMOTE),
                        GV.enC(BDUtils.BD_USER_REMOTE),
                        GV.enC(BDUtils.BD_PASS_REMOTE),
                        GV.enC(BDUtils.BD_URL_REMOTE),
                        1, null, 0);
                    load.add(e);
                }else{
                    BDUtils.BD_NAME_REMOTE = GV.dsC(e.getBd());
                    BDUtils.BD_USER_REMOTE = GV.dsC(e.getBdUser());
                    BDUtils.BD_PASS_REMOTE = GV.dsC(e.getBdPass());
                    BDUtils.BD_URL_REMOTE  = GV.dsC(e.getBdUrl());
                }
            }
        } catch (InstantiationException | IllegalAccessException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(StVars.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void setEquipo(String equipo){
        EQUIPO = GV.getStr(equipo)+"_"+GV.dateToString(new Date(),"yyyymmddss");
    }

    public static void setLicenciaTipoPlan(int value) {
        TIPO_PLAN = value;
    }
    
    public static void setLicenceCode(String licenceCode){
       LICENCE_CODE = GV.getStr(licenceCode);
    }
    
    public static String apiUriLicence(){
        return API_URI;
    }
    
    public static String urlUriPort(){
        return PORT_KEY;
    }

    public static void setUserName(String userName) {
        USERNAME = GV.getStr(userName);
    }

    public static String getUserName() {
        return GV.getStr(USERNAME);
    }

    public static void setExpDate(String date) {
        EXP_DATE = GV.getStr(date);
    }

    public static void setApiUriLicence(String uri) {
        API_URI = GV.getStr(uri);
    }
    
    public static void setApiUriPort(String port) {
        PORT_KEY = GV.getStr(port);
    }
    

    public static void setRutClienteSelected(String rut) {
        RUT_CLIENT_SELECTED = rut;
    }

    public static String getRutClienteSelected() {
        return RUT_CLIENT_SELECTED;
    }
    
    public static void setIdUserSelected(String idUser) {
        ID_USER_SELECTED = idUser;
    }

    public static String getIdUserSelected() {
        return ID_USER_SELECTED;
    }
    
    public static boolean licenciaActiva(){
        return LICENCE_ACTIVE;
    }
    
    public static void licencaActiva(boolean value){
        LICENCE_ACTIVE = value;
    }
    
    public static int licenciaTipoFree(){
        return TP_FREE;
    }
    
    public static int licenciaTipoLocal(){
        return TP_LOCAL;
    }
    
    public static int licenciaTipo2X(){
        return TP_2X;
    }
    
    public static int licenciaTipo4X(){
        return TP_4X;
    }
        
    public static int licenciaTipo6X(){
        return TP_6X;
    }
    
    public static int licenciaTipoFullData(){
        return TP_FULL_DATA;
    }
    
    public static int getSyncCount(){
        return SYNC_COUNT;
    }
    
    public static void setSyncCount(int value){
        SYNC_COUNT = value;
    }
    
    public static String getFechaDefault(){
        return FECHA_DEFAULT;
    }
    
    public static void setIdEquipo(int id){
        EQUIPO_ID = id;
    }
    
    public static int getIdEquipo(){
        return EQUIPO_ID;
    }
    
    public static void setIsWindowsOs(boolean value){
        IS_WINDOWS = value;
    }
    
    public static boolean getIsWindows(){
        return IS_WINDOWS;
    }
    
    public static int getIntentosAcceso(){
        return INTENTOS_ACCESO;
    }
    
    public static void setIntentosAccesoSuma(){
        INTENTOS_ACCESO = INTENTOS_ACCESO + 1;
    }
    
    public static void setIntentosAccesoReset(){
        INTENTOS_ACCESO = 0;
    }
    
    public static void setLastUpdate(Date date) {
        LAST_UPDATE = date;
        XmlUtils.crearRegistroLocal();
    }
    
    public static void setLastUpdateFromXml(Date date) {
        LAST_UPDATE = date;
    }
    
    public static Date getLastUpdate(){
        return LAST_UPDATE;
    }
    
    /*BEGIN PORCENTAJE*/
    public static void resetPorc(){
        PORC = 0;
        SUMA_PORC = 0;
        sp = null;
    }
    
    public static void resetAllPorcentaje(){
        PORC = 0;
        SUMA_PORC = 0;
        LIMIT = 0;
        sp = null;
    }
    
    public static void setPorc(int value){
        PORC = (PORC + value)/2;
    }
    
    public static void calcularPorcentaje(int limit, String text){
        INI = PORC;
        LIMIT = limit;
        PORC = ((SUMA_PORC * 100)/LIMIT);
        SUMA_PORC++;
        if(PORC > 100){
            PORC = 100;
        }
        FIN = ((SUMA_PORC)*100)/LIMIT;
        if(sp == null){
            sp =  new SplashProgress();
            sp.setVisible(true);
        }
    }
    
    public static void calcularSubPorcentaje(int subLimite){
        int temp = (FIN-INI)/subLimite;
        PORC = PORC + temp;
        PORC = (PORC>FIN)?FIN:PORC;
        PORC = (PORC>100)?100:PORC;
    }
    
    public static int getPorc(){
        return PORC;
    }
    
    public static String getReport(){
        return GV.getStr(REPORT);
    }
    
    public static void setReport(String report){
        REPORT = GV.getStr(report);
    }
    /*END PORCENTAJE*/
    /*BREGIN MAIL PROPERTIES*/
    public static String getMailSystemName() {
        return GV.getStr(MAIL_ADDRES).toLowerCase();
    }
    
    public static String getMailSystemPass() {
        return GV.getStr(MAIL_PASS);
    }
    
    public static String getMailReport(){
        return GV.getStr(MAIL_REPORT).toLowerCase();
    }
    
    public static String getMailLog(){
        return GV.getStr(MAIL_LOG);
    }
    
    public static void setMailLog(String className, String mailLog){
        className = GV.getStr(className);
        mailLog = GV.getStr(mailLog);
        if(MAIL_LOG.length() < 2)
            MAIL_LOG = "Registro del sistema:\n"+className+mailLog;
        else
            MAIL_LOG = MAIL_LOG+"\n"+className+mailLog;
    }
    /*END MAIL PROPERTIES*/
    
    /**
     * recibe por parametro el id de el inventario seleccionado,
     * se usa para cargar valores desde la base de datos
     * @param idInventario 
     */
    public static void setInventaryChooser(int idInventario){
        ID_INVENTARIO_SELECCIONADO = idInventario;
    }
    
    /**
     * retorna el id del inventario seleccionado para uso temporal,
     * si el valor es cero es porque no se ha seleccionado un inventario
     * @return 
     */
    public static int getInventaryChooser(){
        return ID_INVENTARIO_SELECCIONADO;
    }
    
    public static String getSqlLowStock() {
        return SQL_LOW_STOCK;
    }
    
    public static String convertVentaIdToVentaList(String arg){
        return ID_PARAM_IS_VENTA_LIST+arg;
    }
    
    public static boolean ventaIdParamIsVentaList(String arg) {
        return GV.getStr(arg).startsWith(ID_PARAM_IS_VENTA_LIST);
    }
    
    public static boolean ventaIdParamIsIdVenta(String arg){
        List<String> filter = filterList();
        for (String str : filter) {
            if(arg.contains(str)){
                return false;
            }
        }
        return true;
    }
    
    public static String cleanIdParam(String arg){
        if(ventaIdParamIsVentaList(arg)){
        /*
            No se agrega diecto al filterList porque en una validacion del Local.java
            debe pasar inadvertido para listar entidades de tipo Ficha
        */
            return GV.getStr(arg).replaceAll(ID_PARAM_IS_VENTA_LIST, "");
        }
        List<String> filter = filterList();
        for (String clean : filter) {
            arg = GV.getStr(arg).replaceAll(clean, "");
        }
        return GV.getStr(arg).trim();
    }
    
    public static int estadoVentaPaid(){
        return PAID;
    }
}
