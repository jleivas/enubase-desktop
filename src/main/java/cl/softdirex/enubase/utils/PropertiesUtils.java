/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author sdx
 */
public class PropertiesUtils {
    private static final String PROPERTIES_PATH = DirectoryUtils.getPropertiesPath()+"envelope.properties";
    private static final String SECURITY_SALT = readPropertie("security.salt");
    private static final String PAY_PAGE_URL = readPropertie("url.paypage");
    
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

    
}
