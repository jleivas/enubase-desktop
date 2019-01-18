/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.dao.Dao;
import cl.softdirex.enubase.view.notifications.Notification;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author sdx
 */
public class SubProcess {
    volatile static boolean ejecucion = true;
    volatile static boolean pause = false;
    private static String className="SubProcess";
    private static final ScheduledExecutorService scheduler = 
            Executors.newScheduledThreadPool(1);
    private static String defaultText = "";
    
    
    private static final int TIME_MIN_COMPROBAR_ONLINE = 5;
    private static final int MIN_EXPIRE_TODAY = 30;
    private static final int MIN_EXPIRED = TIME_MIN_COMPROBAR_ONLINE;
    
    Dao load = new Dao();
    
    /**
     * Comprueba la conección a internet cada 5 segundos
     */
    
    public static void isOnline(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
                while(ejecucion){
                    if(!pause){
                        NetWrk.checkIfOnline();
                    }
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SubProcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
    }
    
    public static void licenciaComprobarOnline(){
        XmlUtils.readXMLOnline();
        PanelUtils.licenciaShowMessageLicenceStatus();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                int sumMinutes = 0;
                int diffDate = 0;
                while(true){
                    Thread.sleep(TIME_MIN_COMPROBAR_ONLINE*60000);
                    XmlUtils.readXMLOnline();
                    sumMinutes = sumMinutes + TIME_MIN_COMPROBAR_ONLINE;
                    diffDate = GV.fechaDiferencia(GV.stringToDate(StVars.getExpDate()));
                    if(diffDate == 0 && sumMinutes >= MIN_EXPIRE_TODAY){
                        sumMinutes=0;
                        PanelUtils.licenciaShowMessageLicenceStatus();
                    }
                    if(diffDate < 0 && sumMinutes >= MIN_EXPIRED){
                        sumMinutes=0;
                        PanelUtils.licenciaShowMessageLicenceStatus();
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(SubProcess.class.getName()).log(Level.SEVERE, null, ex);
                Notification.showMsg("Error al comprobar datos en línea", className+"\n"
                        + ex.getMessage(), 3);
            }
        });
    }
    
    public static void lblSyncStatus(JLabel txtTitle) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String text = txtTitle.getText();
            if(!text.contains("%")){
                defaultText = text;
            }
            int porcentaje = 0;
                while(ejecucion){
                    porcentaje = StVars.getPorc();
                    if(porcentaje > 0){
                        txtTitle.setText("Sincronizando dependencias... ("+porcentaje+"%)");
                    }else{
                        txtTitle.setText(defaultText);
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(SubProcess.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
        });
    }
    public static void stopAll(){
        ejecucion = false;
    }
    
    public static void suspendConnectionOnline(){
        NetWrk.setIsOnline(false);
        pause = true;
    }
    
    public static void activateConnectionOnline(){
        NetWrk.setIsOnline(true);
        pause = false;
    }
    
    public static void report(String title, String message){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            Send mail = new Send();
            mail.sendReportMail(title, message);
        });
    }
    
    public static void isOnlineDeprecated() {
        NetWrk.checkIfOnline();
        final Runnable beeper = new Runnable() {
          public void run() { 
              NetWrk.checkIfOnline();
          }
        };
        final ScheduledFuture<?> beeperHandle =
            scheduler.scheduleAtFixedRate(beeper, 5, 5, SECONDS);
            scheduler.schedule(
                    new Runnable() {
                        public void run() { 
                            beeperHandle.cancel(true); 
                        }
                    }
                    , 60 * 60, SECONDS);
    }
    
    public static boolean ejecucion(){
        return ejecucion;
    }
    
    public static void SyncAll(){
        try {
            Boton boton = new Boton();
            boton.index();
            if(Notification.getConfirmation("Sincronizar", "Para efectuar una sincronización debes tener una conexión rápida y estable\n"
                    + "de esta forma evitarás pérdida importante de información.\n\n"
                    + "¿Tu conexión cumple con los requisitos?\n"
                    + "Presione \"SI\" para continuar.", 1)){
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.submit(() -> {
                    BDUtils.sincronizarTodo();
                });
            }else{
                Notification.showMsg("Sincronización", "La sincronización ha sido cancelada", 1);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(SubProcess.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
