/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import java.io.File;

/**
 *
 * @author jlleivas
 */
public class DirectoryUtils {
    /* Direcciones de fichero*/
    private static String LOCAL_PATH = System.getProperty("user.dir")+File.separator;
    private static String FILES_PATH = LOCAL_PATH+"files"+File.separator;
    private static String FILES_REPORTS_PATH = "src"+File.separator+"reportes"+File.separator;
    private static String REPORT_EXCEL_PATH = LOCAL_PATH+"reports"+File.separator+"excel"+File.separator;
    private static String REPORT_VIEW_PATH = LOCAL_PATH+"reports"+File.separator+"view"+File.separator;
    
    
    public static String getFilesPath(){
        return GC.getStr(FILES_PATH);
    }
    
    public static String getFilesReportsPath(){
        return FILES_REPORTS_PATH;
    }
    
    public static String getLocalPath(){
        return GC.getStr(LOCAL_PATH);
    }

    public static String getReportViewPath() {
        return GC.getStr(REPORT_VIEW_PATH);
    }
    
    public static String getReportExcelPath() {
        return GC.getStr(REPORT_EXCEL_PATH);
    }
}