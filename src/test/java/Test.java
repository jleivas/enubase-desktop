
import cl.softdirex.enubase.utils.DirectoryUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sdx
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println(DirectoryUtils.getPropertiesPath()+"envelope.properties");
        propertiesTest();
    }
    
    public static void propertiesTest(){
        Properties prop = new Properties();
		InputStream is = null;
		
		try {
			is = new FileInputStream(DirectoryUtils.getPropertiesPath()+"envelope.properties");
			prop.load(is);
		} catch(IOException e) {
			System.out.println(e.toString());
		}
 
		// Acceder a las propiedades por su nombre
		System.out.println("Propiedades por nombre:");
		System.out.println("-----------------------");
		System.out.println(prop.getProperty("security.salt"));
		
		// Recorrer todas sin conocer los nombres de las propiedades
		System.out.println("Recorrer todas las propiedades:");
		System.out.println("-------------------------------");
 
		for (Enumeration e = prop.keys(); e.hasMoreElements() ; ) {
			// Obtenemos el objeto
			Object obj = e.nextElement();
			System.out.println(obj + ": " + prop.getProperty(obj.toString()));
		}
    }
    
    
}
