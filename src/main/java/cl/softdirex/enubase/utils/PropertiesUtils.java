/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author sdx
 */
public class PropertiesUtils {
    private static final String PROPERTIES_PATH = DirectoryUtils.getPropertiesPath()+"envelope.properties";
    private static final String CLASIFICACIONES_PATH = DirectoryUtils.getPropertiesPath()+"clasificacion_de_niza.properties";
    private static final String UNIDADES_PATH = DirectoryUtils.getPropertiesPath()+"unidades.properties";
    private static final String SECURITY_SALT = readPropertie("security.salt");
    private static final String PAY_PAGE_URL = readPropertie("url.paypage");
    private static final int IVA = Integer.parseInt(readPropertie("iva.porcentaje"));
    
    public static String getSecuritySalt(){
        return SECURITY_SALT;
    }
    
    public static String getPayPageUrl() {
        return PAY_PAGE_URL;
    }
    
    private static String readPropertie(String varName){
        Properties prop = new Properties();
	InputStream is = null;
		
        try {
                is = new FileInputStream(PROPERTIES_PATH);
                prop.load(is);
        } catch(IOException e) {
                System.out.println(e.toString());
                return "";
        }
	return prop.getProperty(varName);
    }
    
    public static List<String> getClasificaciones(){
        Properties prop = new Properties();
        class Cal implements Comparable<Cal>{
            int index;
            String desc;
            
            Cal(int index, String desc){
                this.index = index;
                this.desc = desc;
            }

            @Override
            public int compareTo(Cal o) {
                if (index < o.index) {
                return -1;
            }
            if (index > o.index) {
                return 1;
            }
            return 0;
            }
        }
        ArrayList<Cal> clases = new ArrayList<>();
        InputStream is = null;

        try {
                is = new FileInputStream(CLASIFICACIONES_PATH);
                prop.load(is);
        } catch(IOException e) {
                System.out.println(e.toString());
        }
        String value;
        
        for (Enumeration e = prop.keys(); e.hasMoreElements() ; ) {
                // Obtenemos el objeto
                Object obj = e.nextElement();
                value = obj.toString().replace("c.", "") + ": " + prop.getProperty(obj.toString());
                Cal cal = new Cal(Integer.parseInt(obj.toString().replace("c.", "")),value);
                
                clases.add(cal);
        }
        Object[] arrayClases = new Object[clases.size()];
        arrayClases = clases.toArray();
        Arrays.sort(arrayClases);
        ArrayList<String> lista = new ArrayList<>();
        for (int i = 0; i < arrayClases.length; i++) {
            lista.add("c"+((Cal)arrayClases[i]).desc);
        }
        return lista;
    }
    
    public static List<String> getUnidades(){
        Properties prop = new Properties();
        List<String> unidades = new ArrayList<>();
        InputStream is = null;

        try {
                is = new FileInputStream(UNIDADES_PATH);
                prop.load(is);
        } catch(IOException e) {
                System.out.println(e.toString());
        }
        String value;
        
        for (Enumeration e = prop.keys(); e.hasMoreElements() ; ) {
                // Obtenemos el objeto
                Object obj = e.nextElement();
                value = prop.getProperty(obj.toString());
                unidades.add(value);
        }
        Collections.sort(unidades);
        return unidades;
    }

    public static int getIVA() {
        return IVA;
    }
}
