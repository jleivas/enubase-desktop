package workTest;


import cl.softdirex.enubase.utils.DirectoryUtils;
import cl.softdirex.enubase.utils.GV;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        toArrayList();
    }
    
    public static void propertiesTest(){
        Properties prop = new Properties();
		InputStream is = null;
		
		try {
			is = new FileInputStream(DirectoryUtils.getPropertiesPath()+"clasificacion_de_niza.properties");
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
			System.out.println(obj.toString().replace(".", "") + ": " + prop.getProperty(obj.toString()));
		}
    }
    
    private static void toArrayList(){
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
                is = new FileInputStream(DirectoryUtils.getPropertiesPath()+"clasificacion_de_niza.properties");
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
        System.out.println("*****************SALIDA:");
        for (int i = 0; i < arrayClases.length; i++) {
            System.out.println(((Cal)arrayClases[i]).desc);
        }
        //return Arrays.asList(arrayClases);
    }
    
    
}
