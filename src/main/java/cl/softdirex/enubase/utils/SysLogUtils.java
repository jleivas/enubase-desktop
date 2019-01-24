/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.*;

/**
 *
 * @author sdx
 */
public class SysLogUtils {

    // Define a static logger variable so that it references the Logger instance
    String logfile = "C:\\tmp\\archivo.log";
    static Date fecha = new Date();

    public SysLogUtils(String message,Object clase) {
        try {
            Logger log = Logger.getLogger(clase.getClass());
            SimpleDateFormat formato = new SimpleDateFormat("dd.MM.yyyy");
            
            String fechaAc = formato.format(fecha);
            PatternLayout defaultLayout = new PatternLayout("%p %c, line %L, %d{dd.MM.yyyy/HH:mm:ss}, %m%n");
            RollingFileAppender rollingFileAppender = new RollingFileAppender();
            rollingFileAppender.setFile(DirectoryUtils.getFilesPath()+"/tmp/archivo_" + fechaAc + ".log", true, false, 0);
            
            //rollingFileAppender.setMaxFileSize("10MB");
            //rollingFileAppender.setMaxBackupIndex(5);
            rollingFileAppender.setLayout(defaultLayout);
            
            log.removeAllAppenders();
            log.addAppender(rollingFileAppender);
            log.setAdditivity(false);
            
            log.info(message);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(SysLogUtils.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}
