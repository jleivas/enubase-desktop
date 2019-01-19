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
    private static final String BARS = File.separator;
    private static final String LOCAL_PATH = System.getProperty("user.dir")+BARS;
    private static final String PROPERTIES_PATH = LOCAL_PATH+"src"+BARS+"main"+BARS+"resources"+BARS+"properties"+BARS;
    private static final String FILES_PATH = LOCAL_PATH+"files"+BARS;
    private static final String FILES_REPORTS_PATH = "src"+BARS+"reportes"+BARS;
    private static final String REPORT_EXCEL_PATH = LOCAL_PATH+"reports"+BARS+"excel"+BARS;
    private static final String REPORT_VIEW_PATH = LOCAL_PATH+"reports"+BARS+"view"+BARS;
    
    
    public static String getFilesPath(){
        return GV.getStr(FILES_PATH);
    }
    
    public static String getPropertiesPath(){
        return GV.getStr(PROPERTIES_PATH);
    }
    
    public static String getFilesReportsPath(){
        return FILES_REPORTS_PATH;
    }
    
    public static String getLocalPath(){
        return GV.getStr(LOCAL_PATH);
    }

    public static String getReportViewPath() {
        return GV.getStr(REPORT_VIEW_PATH);
    }
    
    public static String getReportExcelPath() {
        return GV.getStr(REPORT_EXCEL_PATH);
    }
}