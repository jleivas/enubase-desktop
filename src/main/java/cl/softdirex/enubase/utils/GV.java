/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.dao.Dao;
import cl.softdirex.enubase.entities.Descuento;
import cl.softdirex.enubase.entities.Despacho;
import cl.softdirex.enubase.entities.Equipo;
import cl.softdirex.enubase.entities.HistorialPago;
import cl.softdirex.enubase.entities.Item;
import cl.softdirex.enubase.entities.TipoPago;
import cl.softdirex.enubase.entities.User;
import cl.softdirex.enubase.entities.Venta;
import cl.softdirex.enubase.entities.abstractclasses.SyncIntId;
import cl.softdirex.enubase.entities.abstractclasses.SyncIntIdValidaName;
import cl.softdirex.enubase.entities.abstractclasses.SyncStringId;
import cl.softdirex.enubase.sync.entities.Local;
import cl.softdirex.enubase.sync.entities.LocalInventario;
import cl.softdirex.enubase.sync.entities.Remote;
import static cl.softdirex.enubase.utils.StEntities.getTipoUsuario;
import cl.softdirex.enubase.view.init.Acceso;
import cl.softdirex.enubase.view.notifications.OptionPane;
import cl.softdirex.enubase.view.notifications.panels.input.OpanelCompanyData;
import cl.softdirex.enubase.view.notifications.panels.input.OpanelSetLicencia;
import cl.softdirex.enubase.view.notifications.panels.input.OpanelSetToken;
import cl.softdirex.enubase.view.principal.ContentAdmin;
import com.toedter.calendar.JDateChooser;
import java.awt.event.KeyEvent;
import java.security.MessageDigest;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author sdx
 */
public class GV {
    /*  Sincronizacion */
    public static Local LOCAL_SYNC = new Local();
    public static Remote REMOTE_SYNC = new Remote();
    private static Dao load = new Dao();
    /*********************BEGIN FUNCTIONS****************************/
    public static void funcionNoDisponible(){
        OptionPane.showMsg("Función disponible temporalmente", "Para activarla consulte los requisitos y características con su proveedor", 2);
    }
    
    
    public static void ventasToDelivery(List<Object> ventas){
        if(GV.tipoUserSuperAdmin()){
            if(OptionPane.getConfirmation("Confirmar modificación", "¿Estás seguro que deseas despachar todos los registros filtrados?", 2)){
                for (Object venta : ventas) {
                    if(((Venta)venta).getEstado() != GlobalValuesVariables.estadoVentaDeleted() && 
                       ((Venta)venta).getEstado() != GlobalValuesVariables.estadoVentaDelivered()){
                        try {
                            ((Venta)venta).setEstado(GlobalValuesVariables.estadoVentaDelivered());
                            ((Venta)venta).setObservacion(((Venta)venta).getObservacion()+"\n"
                                    + "==Despacho generado por defecto en el sistema=Autor: "+StEntities.USER.getNombre()+"==");
                            Despacho d = new Despacho(null, ((Venta)venta).getCliente().getCod(),
                                    ((Venta)venta).getCliente().getNombre(), ((Venta)venta).getFechaEntrega(),
                                    ((Venta)venta).getCod(), 1, null, 0);
                            load.update(venta);
                            load.add(d);
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        }
    }
    
    public static void printVentas(List<Object> ventas) {
        funcionNoDisponible();
//        InputStream is = null;
//        JasperPrint jsp = null;
//        FichaRecursoDatos dt = new FichaRecursoDatos();
//        dt.addTitle(GV.getContentAdminTitle(), "Documento generado por "+GV.projectName());
//        for (Object ficha : fichas) {
//            if(((Ficha)ficha).getEstado() > 0){
//                dt.addFicha((Ficha)ficha);
//            }
//        }
//        try{
//            is = new FileInputStream(directoryFilesReportsPath()+"fichas.jrxml");
//        }catch(FileNotFoundException e){
//            OptionPane.showMsg("No se puede obtener el recurso", 
//                    "Ocurrió un error al intentar abrir el formato de impresión\n"
//                            + e.getMessage(), 3);
//        }
//        
//        
//        try{
//            JasperDesign jsd = JRXmlLoader.load(is);
//            JasperReport jsrp = JasperCompileManager.compileReport(jsd);
//            jsp = JasperFillManager.fillReport(jsrp, null,dt);
//            JasperViewer viewer = new JasperViewer(jsp, false); //Se crea la vista del reportes
//            viewer.setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Se declara con dispose_on_close para que no se cierre el programa cuando se cierre el reporte
//            viewer.setVisible(true); //Se vizualiza el reporte
//        }catch( JRException e){
//            OptionPane.showMsg("No se puede visualizar el recurso", 
//                    "Ocurrió un error al intentar abrir visualización del formato de impresión\n"
//                            + e.getMessage(), 3);
//        }
    }
    public static void enviarReporteVentas() {
        funcionNoDisponible();
//        if(GlobalValuesFunctions.licenciaIsEnableToSendMails()){
//            if(!GV.licenciaExpirada()){
//                if(GV.getFichas().size() > 0){
//                    SalesFichaJasperReport reportSales = new SalesFichaJasperReport(GV.getFichas(), ContentAdmin.lblTitle.getText(), GV.companyName(), 
//                            GV.getOficinaWeb(), GV.getOficinaAddress(),
//                            GV.getOficinaPhone1());
//                    if(reportSales.getFilas()>0){
//                        GV.mailSendSalesReport(reportSales);
//                    }else{
//                        OptionPane.showMsg("No hay datos disponibles", "La operación no se puede realizar porque no existen datos", 2);
//                    }
//                }else{
//                    OptionPane.showMsg("No hay datos disponibles", "La operación no se puede realizar porque no existen datos en la tabla", 2);
//                }
//            }else{
//                GV.mensajeLicenceExpired();
//            }
//        }else{
//            GV.mensajeLicenceAccessDenied();
//        }
    }
    
    public static int obtenerDescuentoVenta(Descuento descuento, int total) {
        int porc = 0;
        int dscto = 0;
        if(descuento != null){
            if(descuento.getPorcetange() > 0){
                porc = descuento.getPorcetange();
                dscto = (total * porc)/100;
            }else{
                dscto = descuento.getMonto();
            }
        }
        return roundPrice(dscto);
    }
    
    public static int roundPrice(int price) {
        String temp = ""+price;
        int lastN = GV.strToNumber(temp.substring(temp.length()-1));
        return (lastN > 5)? (price-lastN)+10:price-lastN;
    }
    
    public static void spinnerNumberDisable(JSpinner spinnerNumber, int currentValue) {
        spinnerNumber.setModel(new SpinnerNumberModel(currentValue, currentValue, currentValue, 1));
        spinnerNumber.setValue(currentValue);
    }
    
    public static void compileJCalendar(JDateChooser jDate) {
        if(GV.getStr(jDate.getDateFormatString().replaceAll("[0-9-]", "")).isEmpty()){
            return;
        }else{
            jDate.setDate(null);
        }
    }
    
    public static String obtenerCodEntreLlaves(String arg){
        arg = GV.getStr(arg);
        if(arg.contains("<") && !arg.endsWith("<")){
            arg=arg.substring(arg.indexOf("<")+1).replaceAll(">", "");
            return arg;
        }
        return "0";
    }
    
    public static boolean validaRut(String vRut) 
    { 
        if(vRut.length() < 9 || vRut.length() > 10){
            return false;
        }
            
        String vverificador ="-1";
        
        String[] parts = vRut.split("-");
        
        String vrut = parts[0]; // 123
        try {
           vverificador = parts[1]; // 654321
        } catch (Exception e) {
            return false;
        }
        ////valido que ingrese mas de 1 -
         
        
        try {
        boolean flag = false; 
        String rut = vrut.trim(); 

        String posibleVerificador = vverificador.trim(); 
        int cantidad = rut.length(); 
        int factor = 2; 
        int suma = 0; 
        String verificador = ""; 

        for(int i = cantidad; i > 0; i--) 
        { 
            if(factor > 7) 
            { 
                factor = 2; 
            } 
            suma += (Integer.parseInt(rut.substring((i-1), i)))*factor; 
            factor++; 

        } 
        verificador = String.valueOf(11 - suma%11); 
        if(verificador.equals(posibleVerificador)) 
        { 
            flag = true; 
        } 
        else 
        { 
            if((verificador.equals("10")) && (posibleVerificador.toLowerCase().equals("k"))) 
            { 
                flag = true; 
            } 
            else 
            { 
                if((verificador.equals("11") && posibleVerificador.equals("0"))) 
                { 
                    flag = true; 
                } 
                else 
                { 
                    flag = false; 
                } 
            } 
        } 
        return flag; 
        } catch (Exception e) {
            return false;
        }        
    } 
    
    public static void sendReporteItemsMail(String email, String title) {
        Send mail = new Send();
        mail.sendReportItemsMail(email, title);
    }
    
    public static void calcularReporteItems(int index, Item item) {
        if(index == 0){
            GlobalValuesVariables.ITEMS_STOCK = 0;
            GlobalValuesVariables.ITEMS_STOCK_BAJO = 0;
            GlobalValuesVariables.ITEMS_STOCK_CERO = 0;

            GlobalValuesVariables.ITEMS_COMPRA = 0;
            GlobalValuesVariables.ITEMS_VENTA = 0;
        }
        if(item != null){
            if(item.getEstado() > 0){
                GlobalValuesVariables.ITEMS_STOCK = (item.getStock()>0)?
                        (GlobalValuesVariables.ITEMS_STOCK + item.getStock())
                        :GlobalValuesVariables.ITEMS_STOCK;
                GlobalValuesVariables.ITEMS_STOCK_BAJO = (item.getStockMin() >= item.getStock())?
                        (GlobalValuesVariables.ITEMS_STOCK_BAJO + 1)
                        :GlobalValuesVariables.ITEMS_STOCK_BAJO;
                GlobalValuesVariables.ITEMS_STOCK_CERO = (item.getStock() <= 0)?
                        (GlobalValuesVariables.ITEMS_STOCK_CERO + 1)
                        :GlobalValuesVariables.ITEMS_STOCK_CERO;
                GlobalValuesVariables.ITEMS_COMPRA = (item.getStock() > 0)?
                        (GlobalValuesVariables.ITEMS_COMPRA + (item.getStock() * item.getPrecioRef()))
                        :GlobalValuesVariables.ITEMS_COMPRA;
                GlobalValuesVariables.ITEMS_VENTA = (item.getStock() > 0)?
                        (GlobalValuesVariables.ITEMS_VENTA + (item.getStock() * item.getPrecioAct()))
                        :GlobalValuesVariables.ITEMS_VENTA;
            }
        }
    }
    
    public static void cerrarSistema() {
        OptionPane.closeInfoPanel();
        Boton boton = new Boton();
        boton.mensajeInfo("Cerrando el sistema","Finalizando procesos...El sistema se cerrará.");
        if(OptionPane.getConfirmation("Respaldar información antes de cerrar", "¿Deseas respaldar los datos?", JOptionPane.INFORMATION_MESSAGE)){
            BDUtils.generarBackup();
        }

        System.exit(0);
    }
    
    public static void contentAdminUpdateLabelUser(){
        ContentAdmin.lblUserName.setText(StEntities.USER.getNombre());
    }
    public static String mailValidate(String email) {
        email = getStr(email).toLowerCase();
        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
 
        // El email a validar
 
        Matcher mather = pattern.matcher(email);
        if(mather.find()){
            return email;
        }
        return "";
    }
    
    /**
     * retorna true si la fecha ingresada por parametros es
     * superior a la fecha actual
     * @param date
     * @return 
     */
    public static boolean fechaFutura(Date date){
        return date.after(new Date());
    }
    
    /**
     * retorna true si la fecha ingresada por parametros es igual
     * o superior a la fecha actual
     * @param date
     * @return 
     */
    public static boolean fechaActualOFutura(Date date){
        if(GV.dateToString(date, "ddmmyyyy")
                .equals(GV.dateToString(new Date(), "ddmmyyyy"))){
            return true;
        }
        return fechaFutura(date);
    }
    
    public static String licenciaEstado(){
        String status = "Cambiate a premium";
        if(GlobalValuesVariables.getLicenciaTipoPlan()> 0){
            status = (fechaActualOFutura(stringToDate(GlobalValuesVariables.getExpDate())))?
                    "Bajo licencia hasta el "+GlobalValuesVariables.getExpDate().replaceAll("-", "."):
                    "La licencia ha caducado";
            status = (isCurrentDate(stringToDate(GlobalValuesVariables.getExpDate())))?"Expirará hoy":status;
            status = (isCurrentDate(dateSumaResta(stringToDate(GlobalValuesVariables.getExpDate()), -1, "DAYS")))?"Expirará mañana":status;
        }
        if(GlobalValuesVariables.getLicenciaTipoPlan()==GlobalValuesVariables.licenciaTipoFree()){
            status = "Licencia gratuita: "+status;
        }
        if(GlobalValuesVariables.getLicenciaTipoPlan()==GlobalValuesVariables.licenciaTipo2X()){
            status = "Licencia 2x: "+status;
        }
        if(GlobalValuesVariables.getLicenciaTipoPlan()==GlobalValuesVariables.licenciaTipo4X()){
            status = "Licencia 4x: "+status;
        }
        if(GlobalValuesVariables.getLicenciaTipoPlan()==GlobalValuesVariables.licenciaTipo6X()){
            status = "Licencia 6x: "+status;
        }
        if(GlobalValuesVariables.getLicenciaTipoPlan()==GlobalValuesVariables.licenciaTipoFullData()){
            status = "Licencia FullData: "+status;
        }
        return status;
    }
    
    public static boolean licenciaIsEnableToSendInternMessages() {
        return (GlobalValuesVariables.getLicenciaTipoPlan() != GlobalValuesVariables.licenciaTipo2X() && 
                GlobalValuesVariables.getLicenciaTipoPlan() != GlobalValuesVariables.licenciaTipoFree());
    }
    
    public static boolean licenciaExpirada(){
        return fechaPasada(stringToDate(GlobalValuesVariables.getExpDate()));
    }
    
    public static void startSystem(){
        BDUtils.initDB();
        boolean error = false;
        try{XmlUtils.checkXmlFiles();}catch(Exception e){
            WebUtils.checkIfOnline();
            if(WebUtils.isOnline()){
                error = true;
                licenciaRegistrar();
            }else{
                JOptionPane.showMessageDialog(null, 
                        "Debes tener conexión a internet para validar la licencia", 
                        "No tienes acceso a la red", JOptionPane.WARNING_MESSAGE);
                System.exit(0);
            }
        }
        if(!error){
            initValues();
        }
    }
    
    private static void initValues(){
        validaOs();
        SubProcess.isOnline();
        XmlUtils.cargarRegistroLocal();
        SubProcess.licenciaComprobarOnline();
        validaBD();
        GlobalValuesVariables.setIdEquipo(LOCAL_SYNC.getIdEquipo());
        Acceso init = new Acceso();
        init.setVisible(true);
    }
    
    private static void validaBD(){
        Dao load = new Dao();
        try {    
            if(!GV.licenciaLocal()){
                //Comprueba si existe una base de datos remota con el usuario root
                if(load.get("root", 0, new User())==null){
                    sincronizeOrClose();
                }

            }else{
                load.addOnInit(new User(2, "Sistema", "root", "contacto@softdirex.cl", GV.enC("softdirex"), 7, 1, null, 0));
            }
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    /*BEGIN LICENCIA*/
    public static void licenciaRegistrar(){
        OptionPane.showOptionPanel(new OpanelSetLicencia(), OptionPane.titleRegistrarLicencia());
    }
    
    public static boolean licenciaComprobateOnline(String arg) {
        if(!KeyValid(arg)){licMsg("Debe ingresar una licencia válida.",2);return false;}
        if(!WebUtils.isOnline()){licMsg("No tienes conexión, debes conectarte a internet primero.", 2);return false;}
        String licencia = XmlUtils.getLicenciaOnline(keyGetLicencia(dsC(arg)),keyGetUrl(dsC(arg)));
        if(licencia == null){licMsg("Los datos ingresados son erróneos, consulte con su proveedor.", 2);return false;}
        try {
            if(load.get(licencia, 0, new Equipo())!=null){licMsg("Los datos ingresados son erróneos, Licencia duplicada.", 3);return false;}
                
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        validaToken(XmlUtils.getTipoPlanOnline(keyGetLicencia(dsC(arg)),keyGetUrl(dsC(arg))),licencia,arg);
        
        return true;
    }
    
    private static void validaToken(int tipoPlan,String licencia,String key) {
        if(tipoPlan != GlobalValuesVariables.licenciaTipoFree() && 
           tipoPlan != GlobalValuesVariables.licenciaTipoLocal()){
            OptionPane.showOptionPanel(new OpanelSetToken(key), OptionPane.titleRegistrarToken());
        }else{
            setLicenciaAsignarValoresPaso1(licencia, key);
        }
    }
    
    private static void setLicenciaAsignarValoresPaso1(String licencia,String arg){
        GlobalValuesVariables.setSyncCount(0);
        
        GlobalValuesVariables.setUserName("admin");
        GlobalValuesVariables.setLicenciaTipoPlan(XmlUtils.getTipoPlanOnline(keyGetLicencia(dsC(arg)),keyGetUrl(dsC(arg))));
        
        GlobalValuesVariables.setLicenceCode(licencia);
        GlobalValuesVariables.setExpDate(XmlUtils.getExpDateOnline(keyGetLicencia(dsC(arg)),keyGetUrl(dsC(arg))));
        GlobalValuesVariables.setCurrentEquipo(licencia+"_"+dateToString(new Date(), "yyyymmddhhmmss"));
        GlobalValuesVariables.setApiUriLicence(keyGetUrl(dsC(arg)));
        GlobalValuesVariables.setApiUriPort(keyGetPass(dsC(arg)));
        GlobalValuesVariables.setLastUpdateFromXml(stringToDate(GlobalValuesVariables.getFechaDefault()));
        OptionPane.closeOptionPanel();
        OptionPane.showOptionPanel(new OpanelCompanyData(1), OptionPane.titleCompanyDataCreate());
    }
    
    public static void asignarToken(String token,String key){
        if(GV.getStr(token).isEmpty())return;
        String licencia = keyGetLicencia(dsC(key));
        String bd = tokenGetBd(dsC(token));
        String bdUser = tokenGetBdUser(dsC(token));
        String bdPass = tokenGetBdPass(dsC(token));
        String bdUrl = tokenGetBdUrl(dsC(token));
        BDUtils.BD_NAME_REMOTE = bd;
        BDUtils.BD_USER_REMOTE = bdUser;
        BDUtils.BD_PASS_REMOTE = bdPass;
        BDUtils.BD_URL_REMOTE = bdUrl;
        setLicenciaAsignarValoresPaso1(licencia, key);
    }
    
    private static boolean KeyValid(String key){
        String unKey = dsC(key);
        String[] parts = unKey.split("=");
        int cont = 0;
        for (String part : parts) {
            cont++;
            if(getStr(part).isEmpty())
                return false;
        }
        return (cont == 3)?true:false;
    }
    
    private static String keyGetLicencia(String unKey){
        return unKey.substring(unKey.indexOf("=")+1,unKey.lastIndexOf("="));
    }
    
    private static String keyGetPass(String unKey){
        return unKey.substring(unKey.lastIndexOf("=")+1);
    }
    
    public static String tokenGetBdPass(String unKey){
        return unKey.substring(unKey.lastIndexOf("=")+1,unKey.lastIndexOf("<"));
    }
    
    public static String tokenGetBdUrl(String unKey){
        return unKey.substring(unKey.lastIndexOf("<")+1);
    }
    
    private static String keyGetUrl(String unKey){
        return unKey.substring(0,unKey.indexOf("="));
    }
    
    public static String tokenGetBd(String unKey){
        return unKey.substring(0,unKey.indexOf("="));
    }
    
    public static String tokenGetBdUser(String unKey){
        return unKey.substring(unKey.indexOf("=")+1,unKey.lastIndexOf("="));
    }
    
    /*END LICENCIA*/
    
    /**
     * Retorna true si es Jefe administrativo o Sistema
     * @return 
     */
    public static boolean tipoUserSuperAdmin(){
        int tipoUsuario = getTipoUsuario();
        if(tipoUsuario == 1 || tipoUsuario == 7){
            return true;
        }
        return false;
    }
    
    
    /**
     * Retorna true si es Jefe administrativo, Administrador o Sistema
     * @return 
     */
    public static boolean tipoUserAdmin(){
        int tipoUsuario = getTipoUsuario();
        if(tipoUsuario == 1 || tipoUsuario == 2 || tipoUsuario == 7){
            return true;
        }
        return false;
    }
    
    /**
     * Retorna true si es Jefe administrativo, Administrador, inventario o Sistema
     * @return 
     */
    public static boolean tipoUserIventario(){
        int tipoUsuario = getTipoUsuario();
        if(tipoUsuario == 1 || tipoUsuario == 2 || tipoUsuario == 4 || tipoUsuario == 7){
            return true;
        }
        return false;
    }
    
    /**
     * retorna true si la fecha ingresada por parametros es inferior
     * a la fecha actual
     * @param date
     * @return 
     */
    public static boolean fechaPasada(Date date){
        if(dateToString(date, "ddmmyyyy")
                .equals(dateToString(new Date(), "ddmmyyyy"))){
            return false;
        }
        return fechaActualOPasada(date);
    }
    
    /**
     * retorna true si la fecha ingresada por parametros es 
     * pasada o igual a la fecha actual
     * @param date
     * @return 
     */
    public static boolean fechaActualOPasada(Date date){
        return date.before(new Date());
    }
    
    public static boolean licenciaLocal() {
        return (GlobalValuesVariables.getLicenciaTipoPlan() == GlobalValuesVariables.licenciaTipoFree() ||
                GlobalValuesVariables.getLicenciaTipoPlan() == GlobalValuesVariables.licenciaTipoLocal());
    }
    
    public static Object searchInList(String code , List<Object> list, Object classType) {
        if(getStr(code).isEmpty()){
            return null;
        }
        if(list !=null){
            if(list.size() == 0){
                return null;
            }
            if(classType instanceof SyncIntIdValidaName){
                Optional<Object> objectFound = list.stream()
                .filter(p -> ((SyncIntIdValidaName)p).getNombre().equals(code))
                .findFirst();
                return objectFound.isPresent() ? objectFound.get() : null;
            }
            if(classType instanceof SyncIntId){
                Optional<Object> objectFound = list.stream()
                .filter(p -> ((SyncIntId)p).getId() == strToNumber(code))
                .findFirst();
                return objectFound.isPresent() ? objectFound.get() : null;
            }
            if(classType instanceof SyncStringId){
                Optional<Object> objectFound = list.stream()
                .filter(p -> ((SyncStringId)p).getCod().toLowerCase().equals(code.toLowerCase()))
                .findFirst();
                return objectFound.isPresent() ? objectFound.get() : null;
            }
        }
        return null;
    }
    
    public static boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }
    
    public static int strToNumber(String arg){
        arg = getStr(arg).replaceAll("[^0-9-]", "");
        boolean isNegative = (arg.startsWith("-"))? true:false;
        arg = arg.replaceAll("-", "").trim();
        if(arg.isEmpty())
            return 0;
        if(isNegative){
            return -Integer.parseInt(arg);
        }
        return Integer.parseInt(arg);
    }
    
    public static String getStr(String arg){
        if(arg == null || arg.replaceAll(" ", "").isEmpty())
            return "";
        else{
            return arg.trim();
        }
    }
    
    public static String getToName(String param){
        String[] str = getStr(param).toLowerCase().split(" ");
        StringBuffer value = new StringBuffer();
        for (String temp : str) {
            if(temp.length() > 1){
                value.append(Character.toUpperCase(temp.charAt(0))).append(temp.substring(1)).append(" ");
            }else{
                value.append(temp.toUpperCase()).append(" ");
            }
        }
        return value.toString().trim();
    }
    
    public static String dsC(String textContent) {
        String base64EncryptedString = "";
        if(getStr(textContent).isEmpty()){
            return "";
        }
        try {
            byte[] message = Base64.decodeBase64(textContent.getBytes("utf-8"));
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(GlobalValuesVariables.getSalt().getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
 
            Cipher decipher = Cipher.getInstance("DESede");
            decipher.init(Cipher.DECRYPT_MODE, key);
 
            byte[] plainText = decipher.doFinal(message);
 
            base64EncryptedString = new String(plainText, "UTF-8");
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
    
    public static String enC(String texto) {
        if(getStr(texto).isEmpty()){
            return "";
        }
        String base64EncryptedString = "";
 
        try {
 
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digestOfPassword = md.digest(GlobalValuesVariables.getSalt().getBytes("utf-8"));
            byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
 
            SecretKey key = new SecretKeySpec(keyBytes, "DESede");
            Cipher cipher = Cipher.getInstance("DESede");
            cipher.init(Cipher.ENCRYPT_MODE, key);
 
            byte[] plainTextBytes = texto.getBytes("utf-8");
            byte[] buf = cipher.doFinal(plainTextBytes);
            byte[] base64Bytes = Base64.encodeBase64(buf);
            base64EncryptedString = new String(base64Bytes);
 
        } catch (Exception ex) {
        }
        return base64EncryptedString;
    }
    
    public static Date stringToDate(String strFecha){
        //formato valido
        strFecha = getStr(strFecha);
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd-MM-yyyy");
        Date fecha = null;
        try {

        fecha = formatoDelTexto.parse(strFecha);

        } catch (ParseException ex) {

            ex.printStackTrace();
            fecha = stringToDate(GlobalValuesVariables.getFechaDefault());

        }
        
        return fecha;
    }
    
    public static int fechaDiferencia(Date date) {
        try {
            if(date == null) return 0;
            String stFecha = dateToString(date, "dd/mm/yyyy");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            cal.setTime(sdf.parse(stFecha));
            int dias = 0;
            if(cal.compareTo(Calendar.getInstance())>=0){
                Date fecha=cal.getTime();
                DateFormat dd = new SimpleDateFormat("dd/MM/yyyy");       
                boolean activo = false;
                Calendar calendar; Date aux;
                do{
                    calendar = Calendar.getInstance();           
                    calendar.add(Calendar.DAY_OF_YEAR, dias);
                    aux = calendar.getTime();
                    if(dd.format(aux).equals(dd.format(fecha)))
                        activo = true;
                    else
                        dias++;
                }while(activo != true);
            }else{
                Date fecha=cal.getTime();
                DateFormat dd = new SimpleDateFormat("dd/MM/yyyy");       
                boolean activo = false;
                Calendar calendar; Date aux;
                do{
                    calendar = Calendar.getInstance();           
                    calendar.add(Calendar.DAY_OF_YEAR, dias);
                    aux = calendar.getTime();
                    if(dd.format(aux).equals(dd.format(fecha)))
                        activo = true;
                    else
                        dias--;
                }while(activo != true);
            }
            
            return dias; 
        } catch (ParseException ex) {
            Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
            OptionPane.showMsg("Error al calcular dias", "Ocurrió un error inesperado...", 3);
        }
        return 0;
    }
    
    public static String dateToString(Object date,String strOrder){
        strOrder = (strOrder==null)?"dd/mm/yyyy":strOrder;
        String firstSeparator = (strOrder.toLowerCase().contains("de"))?"de":null;
        String lastSeparator = (strOrder.toLowerCase().contains("del"))?"del":null;
        if(firstSeparator!=null){
            strOrder = strOrder.toLowerCase().replaceAll(" ", "")
                                         .replaceAll(lastSeparator, "/")
                                         .replaceAll(firstSeparator, "/");
        }
        
        DateFormat fmt = new SimpleDateFormat(strOrder.replaceAll("m","M").replaceAll("hhMM", "hhmm"));
        String strDate = "date-error";
        if(date instanceof Date)
            strDate = fmt.format((Date)date);
        if(date instanceof java.sql.Date)
            strDate = fmt.format((java.sql.Date)date);
        if(firstSeparator!=null){
            strDate = strDate.replaceFirst("/", " "+firstSeparator+" ").replace("/", " "+lastSeparator+" ");
        }
        return strDate;
    }
    
    /**
     * Compara las fechas de las ultimas actualizaciones para 
     * realizar la sincronizacion con base de datos remota siempre que 
     * localIsNew(Date local,Date remote) sea verdadero
     * @param local Tipo Date lastUpdate de objeto local
     * @param remote Tipo Date lastUpdate de objeto remoto
     * @return true si el objeto local es mas reciente
     */
    public static boolean localIsNewOrEqual(Date local,Date remote){
        if(local == null || remote == null){
            return false;
        }
        return local.compareTo(remote) >= 0;
    }
    
    public static boolean objectIsNew(Date dateObject, int hourObject,Date dateBd, int hourBd){
        int compare = dateObject.compareTo(dateBd);
        if(compare > 0){
            return true;
        }else if(compare == 0){
            if(hourObject > hourBd){
                return true;
            }
        }
        return false;
    }
    
    public static int hourToInt(Date date){
        DateFormat hourFormat = new SimpleDateFormat("HHmmss");
        String formatHour = hourFormat.format(date);
        int hora = Integer.parseInt(formatHour);
        return hora;
    }
    public static String DateToStrHour(Date date){
        DateFormat hourFormat = new SimpleDateFormat("HH:mm");
        return hourFormat.format(date);
    }
    
    public static void stopSincronizacion(){
        BDUtils.setSincronizar(false);
    }
    
    public static boolean sincronizacionIsStopped() {
        return !BDUtils.sincronizacion();
    }
    
    public static boolean licenciaIsEnableToSendMails() {
        return (GlobalValuesVariables.getLicenciaTipoPlan() != GlobalValuesVariables.licenciaTipoFree());
    }
    
    public static String strToPrice(int monto){
        DecimalFormat formateador = new DecimalFormat("###,###,###");
        return "$ "+formateador.format (monto);
    }
    
    public static int getSyncCount(){
        XmlUtils.loadSyncCount();
        return GlobalValuesVariables.getSyncCount();
    }
    
    public static void setSyncCount(int value){
        GlobalValuesVariables.setSyncCount(value);
        XmlUtils.saveSyncCount();
    }
    
    public static boolean syncEnabled(){
        int tp = GlobalValuesVariables.getLicenciaTipoPlan();
        int count = getSyncCount();
        if(tp==GlobalValuesVariables.licenciaTipoFullData())return true;
        if(count<0){
            OptionPane.showMsg("Registos adulterados", "No es posible continuar porque los archivos del sistema\n"
                    + " se encuentran corrompidos, esto puede causar daños irreversibles en el sistema", 3);
            setSyncCount(GlobalValuesVariables.TP_6X_MS*1000);
            return false;
        }
        if(tp==GlobalValuesVariables.licenciaTipo2X()){
            return (count < GlobalValuesVariables.TP_2X_MS);
        }
        if(tp==GlobalValuesVariables.licenciaTipo4X()){
            return (count < GlobalValuesVariables.TP_4X_MS);
        }
        if(tp==GlobalValuesVariables.licenciaTipo6X()){
            return (count < GlobalValuesVariables.TP_6X_MS);
        }
        return false;
    }
    
    public static boolean isCurrentDate(Date date) {
        if(dateToString(date, "ddmmyyyy").equals(dateToString(new Date(), "ddmmyyyy"))){
            return true;
        }
        return false;
    }
    
    /**
     * Devuelde una fecha de tipo Date con el resultado según parámetro sumaresta ingresado
     * @param fecha
     * @param sumaresta
     * @param opcion Parámetros validos: DAYS, MONTHS o YEARS.
     * @return 
     */
    public static Date dateSumaResta(Date fecha, int sumaresta, String opcion){
        if(fecha == null)return null;
        if(fecha instanceof java.sql.Date){
            fecha = new Date(fecha.getTime());
        }
        LocalDate date = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        //Con Java9
        //LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());
        TemporalUnit unidadTemporal = null;
        switch(opcion){
            case "DAYS":
                unidadTemporal = ChronoUnit.DAYS;
                break;
            case "MONTHS":
                unidadTemporal = ChronoUnit.MONTHS;
                break;
            case "YEARS":
                unidadTemporal = ChronoUnit.YEARS;
                break;
            default:
                //Controlar error
        }
        LocalDate dateResultado = date.plus(sumaresta, unidadTemporal);
        return Date.from(dateResultado.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
    
    public static void setLastUpdate(Date date) {
        GlobalValuesVariables.setLastUpdate(date);
        XmlUtils.crearRegistroLocal();
    }
    
    /**
     * Retorna un where en sql para listar ventas y todos sus datos, si userId y clientCod son null,
     * buscara por fecha, 
     * de lo contratrio validara los userId o clientId
     * @param dateFrom
     * @param dateTo
     * @param idUser
     * @param codClient
     * @param idFicha 
     * @return 
     */
    public static String getWhereFromVentas(Date dateTo, Date dateFrom,String idUser, String codClient, String idVenta){
        if(idVenta!=null){
            return "where venta.ven_id = '"+idVenta+"'";
        }
        if(dateTo==null && dateFrom==null){
            if(idUser != null){
                return "where venta.usuario_us_id = "+idUser+" and venta.ven_estado <> 0 ORDER BY venta.ven_fecha DESC";
            }
            if(codClient != null){
                return "where venta.cliente_cli_rut = '"+codClient+"' and venta.ven_estado <> 0 ORDER BY venta.ven_fecha DESC";
            }
            return "where venta.ven_estado <> 0 ORDER BY venta.ven_fecha DESC";
        }
        dateTo=(dateTo==null)?dateFrom:dateTo;
        dateFrom=(dateFrom==null)?dateTo:dateFrom;
        if(localIsNewOrEqual(dateTo, dateFrom)){
            Date aux = dateFrom;
            dateFrom=dateTo;
            dateTo=aux;
        }
        String d1 = (!dateToString(dateTo, "yyyy-mm-dd").equals("date-error"))?dateToString(dateTo, "yyyy-mm-dd"):dateToString(new Date(), "yyyy-mm-dd");
        String d2 = (!dateToString(dateFrom, "yyyy-mm-dd").equals("date-error"))?dateToString(dateFrom, "yyyy-mm-dd"):dateToString(new Date(), "yyyy-mm-dd");
        return "where venta.ven_fecha BETWEEN '"+d1+"' and '"+d2+"' and venta.ven_estado <> 0 ORDER BY venta.ven_fecha DESC";
    }
    
    /**
     * Retorna un where en sql para listar todas las ventas incluidas las anuladas
     * y todos sus datos, si userId y clientCod son null,
     * buscara por fecha, 
     * de lo contratrio validara los userId o clientId
     * @param dateFrom
     * @param dateTo
     * @param idUser
     * @param codClient
     * @param idVenta 
     * @return 
     */
    public static String getWhereFromAllVentas(Date dateTo, Date dateFrom,String idUser, String codClient, String idVenta){
        if(idVenta!=null){
            return "where venta.ven_id = '"+idVenta+"'";
        }
        if(dateTo==null && dateFrom==null){
            if(idUser != null){
                return "where venta.usuario_us_id = "+idUser+" ORDER BY venta.ven_fecha DESC";
            }
            if(codClient != null){
                return "where venta.cliente_cli_rut = '"+codClient+"' ORDER BY venta.ven_fecha DESC";
            }
            return "where venta.ven_fecha ='"+dateToString(new Date(), "yyyy-mm-dd")+"' ORDER BY venta.ven_fecha DESC";
        }
        dateTo=(dateTo==null)?dateFrom:dateTo;
        dateFrom=(dateFrom==null)?dateTo:dateFrom;
        if(localIsNewOrEqual(dateTo, dateFrom)){
            Date aux = dateFrom;
            dateFrom=dateTo;
            dateTo=aux;
        }
        String d1 = (!dateToString(dateTo, "yyyy-mm-dd").equals("date-error"))?dateToString(dateTo, "yyyy-mm-dd"):dateToString(new Date(), "yyyy-mm-dd");
        String d2 = (!dateToString(dateFrom, "yyyy-mm-dd").equals("date-error"))?dateToString(dateFrom, "yyyy-mm-dd"):dateToString(new Date(), "yyyy-mm-dd");
        if(idUser != null){
            return "where (venta.usuario_us_id = "+idUser+") and (venta.ven_fecha BETWEEN '"+d1+"' and '"+d2+"') ORDER BY venta.ven_fecha DESC";
        }
        return "where venta.ven_fecha BETWEEN '"+d1+"' and '"+d2+"' ORDER BY venta.ven_fecha DESC";
    }
    
    public static String getClassName(Object type){
        if(type == null) return "[...]";
        String name = (type.getClass().getName().contains("."))?
                type.getClass().getName().substring(type.getClass().getName().lastIndexOf(".")+1):type.getClass().getName();
        return name;
    }
    
    public static String strToRut(String rut) {
        if(rut == null || rut.isEmpty()){
            return "";
        }
        int cont = 0;
        String format;
        rut = rut.replace(".", "");
        rut = rut.replace("-", "");
        format = "-" + rut.substring(rut.length() - 1);
        for (int i = rut.length() - 2; i >= 0; i--) {
            format = rut.substring(i, i + 1) + format;
            cont++;
            if (cont == 3 && i != 0) {
                format = "." + format;
                cont = 0;
            }
        }
        return format;
    }
    
    public static void validaOs(){
        GlobalValuesVariables.setIsWindowsOs((System.getProperty("os.name").toLowerCase().startsWith("win")));
    }
    
    /**
     * Obliga al usuario a sincronizar datos para evitar perdida importante de información
     */
    public static void sincronizeOrClose() {
        if(OptionPane.getConfirmation("Sincronización inicial", "Todos los datos deben ser sincronizados para que el sistema "
                + "funcione correctamente,\n el tiempo de espera puede ser largo dependiendo de los registros "
                + "almacenados\n en la base de datos remota.\n"
                + "Asegúrese de que su conexión a internet sea rápida para evitar posibles problemas de registro\n"
                + "de lo contrario inicie el sistema mas tarde."
                + "\n ¿Desea sincronizar los datos ahora?", 2)){
            GV.setSyncCount(0);
            BDUtils.sincronizarTodo();
        }else{
            OptionPane.showMsg("Operación cancelada", "El sistema no puede iniciar sin la sincronización,\n"
                    + "vuelva a intentarlo mas tarde.", 2);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.exit(0);
        }
    }
    
    public static User validar(String username, String pass) {
        User user = null;
        try {
            user = (User)load.get(username, 0, new User());
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
            OptionPane.showMsg("Error al cargar usuario", "Ocurrió un error inesperado al validar usuario:\n"
                    + ex.getMessage(), 3);
        }
        if(user!=null){
            if(user.getEstado() == 0){
                OptionPane.showMsg("Acceso denegado", "El usuario se encuentra anulado", 2);
                return null;
            }
            if(GV.dsC(user.getPass()).equals(pass)){
                GlobalValuesVariables.setIntentosAccesoReset();
                return user;
            }else{
                GlobalValuesVariables.setIntentosAccesoSuma();
                if(GlobalValuesVariables.getIntentosAcceso() < 3){
                    OptionPane.showMsg("Acceso denegado", "Clave de acceso inválida", 2);
                }else{
                    OptionPane.showMsg("Acceso denegado", "Clave de acceso inválida:\n"
                        + "Si usted está seguro que la clave ingresada es la correcta\n"
                        + "consulte una posible solución en el ícono de ayuda \"?\".", 2);
                }
            }
        }else{
            OptionPane.showMsg("Acceso denegado", "El usuario no existe", 2);
        }
        return null;
    }
    
    public static String getFilterString(String arg){
        if(arg == null || arg.replaceAll(" ", "").isEmpty())
            return "";
        else{
            String value = arg.replaceAll("[+^‘´'{}]","");
            return (value.startsWith(" "))?value.replaceFirst(" ", "").trim():value.trim();
        }
    }
    
    public static Venta openVentaByCod(String cod) {
        return (Venta)BDUtils.openVentaByCod(cod);
    }
    
    public static void validaLargo(KeyEvent evt, JTextField txtField, int largo) {
        if(txtField.getText().length() >= largo){
            evt.consume();
            OptionPane.showMsg("Error de ingreso de datos", "El registro solo debe contener hasta "+largo+" caracteres", 2);
        }
    }
    
    /**
     * debuelbe un arreglo de objetos de tipo string donde [monto][tipoPago][fecha]
     * @param codFicha
     * @return 
     */
    public static Object[][] listarAbonos(String codFicha) {
        
        List<Object> lista = load.listar(convertVentaIdParamToListAbonos(codFicha), new HistorialPago());
        if(lista.size() == 0){return null;}
        String[][] abonos = new String[lista.size()][GlobalValuesVariables.TAMANO_COLUMN_ABONOS];
        int i = 0;
        
        TipoPago tp = null;
        
        for (Object object : lista) {
            HistorialPago temp = (HistorialPago)object;
            try {
                tp = (TipoPago)load.get(null, temp.getIdTipoPago(), new TipoPago());
            } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
                Logger.getLogger(GV.class.getName()).log(Level.SEVERE, null, ex);
                tp = null;
            }
            abonos[i][GlobalValuesVariables.POS_MONTO] = GV.strToPrice(temp.getAbono());
            
            abonos[i][GlobalValuesVariables.POS_NAME] = (tp!=null)? tp.getNombre():GlobalValuesVariables.TIPO_PAGO_NO_REGISTRADO;
            
            abonos[i][GlobalValuesVariables.POS_DATE] = GV.dateToString(temp.getFecha(), "dd/mm/yyyy");
            i++;
        }
        return abonos;
    }
    
    public static String convertVentaIdParamToListAbonos(String arg) {
        return "<"+GV.getStr(arg)+">";
    }
    /*********************END FUNCTIONS****************************/
    /*********************BEGIN UI PROPERTIES****************************/
    public static int msgStatus(){
        return Icons.getMsgStatus();
    }
    
    public static String iconInfo(){
        return Icons.iconInfo();
    }
    
    public static String iconWarn(){
        return Icons.iconWarn();
    }
    
    public static String iconError(){
        return Icons.iconError();
    }
    /*********************END UI PROPERTIES****************************/
    /*BEGIN NOTIFICATIONS*/
    private static void licMsg(String msg,int status) {
        OptionPane.showMsg("Error de licencia", msg, status);
    }
    
    public static void mensajeAccessDenied() {
        OptionPane.showMsg("Acceso denegado", "No tienes permisos suficientes para realizar esta operación", 2);
    }

    public static void mensajeLicenceAccessDenied() {
        OptionPane.showMsg("Cambie su licencia", "La versión de su licencia no tiene esta opción disponible", 2);
    }
    
    public static void mensajeLicenceExpired() {
        OptionPane.showMsg("Renueve su licencia", "La versión de su licencia se encuentra expirada.\n"
                + "Algunas opciones no estarán disponibles.", 2);
    }
    
    public static void showMessageFunctionNonActive() {
        OptionPane.showMsg("Función no activada", "Esta opción no se encuentra disponible", 2);
    }
    
    public static void mensajeExcepcion(String error, int status) {
        JOptionPane.showMessageDialog(null, "Ocurrió un error inesperado:\n"+error, "Error critico", JOptionPane.ERROR_MESSAGE);
    }

    public static void showMsgOnEmptyTable(JComboBox cbo, JTextField txt, String registry){
        String end = "os";
        registry = (!registry.endsWith("s"))? registry+"s":registry;
        end = (registry.endsWith("as"))? "as":end;
        if(txt.getText().length() > 1){
            OptionPane.showMsg("No existen "+registry, "No existen registros disponibles que contengan la palabra \""+txt.getText()+"\"",1);
        }else{
            if(registry.toLowerCase().contains("items")){
                if(cbo.getSelectedIndex() == 2){
                    OptionPane.showMsg("No existen "+registry, "No existen "+registry+" eliminad"+end+".",1);
                }else{
                    OptionPane.showMsg("No existen "+registry, "No existen "+registry+" registrad"+end+".",1);
                }
            }else{
                if(cbo.getSelectedIndex() == 0){
                    OptionPane.showMsg("No existen "+registry, "No existen "+registry+" registrad"+end+".",1);
                }else{
                    OptionPane.showMsg("No existen "+registry, "No existen "+registry+" eliminad"+end+".",1);
                }
            }
        }
    }
    /*END NOTIFICATIONS*/

    public static Item getItemLocalInventario(String idItem, int idInventario) {
        Item item;
        int oldInventario = GlobalValuesVariables.getInventaryChooser();
        GlobalValuesVariables.setInventaryChooser(idInventario);
        item = LocalInventario.getItem(idItem);
        GlobalValuesVariables.setInventaryChooser(oldInventario);
        return item;
    }
}
