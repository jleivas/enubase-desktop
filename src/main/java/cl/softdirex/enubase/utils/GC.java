/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.view.notifications.Notification;
import java.security.MessageDigest;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author sdx
 */
public class GC {
    /*  Sincronizacion */
    public static Local LOCAL_SYNC = new Local();
    public static Remote REMOTE_SYNC = new Remote();
    /*********************BEGIN FUNCTIONS****************************/
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
            byte[] digestOfPassword = md.digest(StVars.getSalt().getBytes("utf-8"));
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
            byte[] digestOfPassword = md.digest(StVars.getSalt().getBytes("utf-8"));
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
            fecha = stringToDate(StVars.getFechaDefault());

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
            Logger.getLogger(GC.class.getName()).log(Level.SEVERE, null, ex);
            Notification.showMsg("Error al calcular dias", "Ocurrió un error inesperado...", 3);
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
        return (StVars.getLicenciaTipoPlan() != StVars.licenciaTipoFree());
    }
    
    public static String strToPrice(int monto){
        DecimalFormat formateador = new DecimalFormat("###,###,###");
        return "$ "+formateador.format (monto);
    }
    
    public static int getSyncCount(){
        XmlUtils.loadSyncCount();
        return StVars.getSyncCount();
    }
    
    public static void setSyncCount(int value){
        StVars.setSyncCount(value);
        XmlUtils.saveSyncCount();
    }
    
    public static boolean syncEnabled(){
        int tp = StVars.getLicenciaTipoPlan();
        int count = getSyncCount();
        if(tp==StVars.licenciaTipoFullData())return true;
        if(count<0){
            Notification.showMsg("Registos adulterados", "No es posible continuar porque los archivos del sistema\n"
                    + " se encuentran corrompidos, esto puede causar daños irreversibles en el sistema", 3);
            setSyncCount(StVars.TP_6X_MS*1000);
            return false;
        }
        if(tp==StVars.licenciaTipo2X()){
            return (count < StVars.TP_2X_MS);
        }
        if(tp==StVars.licenciaTipo4X()){
            return (count < StVars.TP_4X_MS);
        }
        if(tp==StVars.licenciaTipo6X()){
            return (count < StVars.TP_6X_MS);
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
        StVars.setLastUpdate(date);
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
    /*********************END FUNCTIONS****************************/
    /*********************BEGIN UI PROPERTIES****************************/
    public static int msgStatus(){
        return UI.getMsgStatus();
    }
    
    public static String iconInfo(){
        return UI.iconInfo();
    }
    
    public static String iconWarn(){
        return UI.iconWarn();
    }
    
    public static String iconError(){
        return UI.iconError();
    }
    /*********************END UI PROPERTIES****************************/
}
