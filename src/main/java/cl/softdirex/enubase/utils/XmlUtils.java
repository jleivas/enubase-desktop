/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.Date;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author sdx
 */
public class XmlUtils {
    public static void crearRegistroLocal(){
         
         Document document = null;
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            document = implementation.createDocument(null, "local", null);
            /**************************USER******************************/
            //Creación de elementos
            //creamos el elemento principal usr
            Element user = document.createElement("usr"); 
            //creamos un nuevo elemento. usr contiene name
            Element name= document.createElement("name");
            Text vName = document.createTextNode(StVars.getUserName()); 
            /**************************LICENCE******************************/
            Element lic = document.createElement("lic"); 
            //creamos un nuevo elemento. lic contiene st y date
            Element st= document.createElement("st");
            Element code= document.createElement("code");
            Element date= document.createElement("date");
            //Ingresamos la info. 
            Text vSt = document.createTextNode(GC.enC(""+StVars.getLicenciaTipoPlan()));
            Text vCode = document.createTextNode(GC.enC(StVars.getLicenceCode()));
            Text vDate = document.createTextNode(GC.enC(StVars.getExpDate())); 
            /**************************NETWORK******************************/
            Element network = document.createElement("network"); 
            //creamos un nuevo elemento. lic contiene st y date
            Element equipo= document.createElement("equipo");
            Element uri= document.createElement("uri");
            Element port= document.createElement("port");
            //Ingresamos la info. 
            Text vEquipo = document.createTextNode(GC.enC(StVars.getEquipo()));
            Text vUri = document.createTextNode(GC.enC(StVars.apiUriLicence()));
            Text vPort = document.createTextNode(GC.enC(StVars.urlUriPort()));
            
             /**************************REGISTRY******************************/
            Element registry = document.createElement("registry"); 
            //creamos un nuevo elemento. lic contiene st y date
            Element office= document.createElement("office");
            Element company= document.createElement("company");
            Element inventary= document.createElement("inventary");
            Element lastUpdate = document.createElement("last_update_bd");
            Element companyDesc = document.createElement("company_description");
            Element companyRut = document.createElement("company_rut");
            Element companyGiro = document.createElement("company_giro");
            Element msgFile = document.createElement("message_file");
            //Ingresamos la info. 
            Text vOffice = document.createTextNode(GC.enC(StVars.get()));
            Text vCompany = document.createTextNode(GC.enC(GC.companyName()));
            Text vInventary = document.createTextNode(GC.enC(GC.inventarioName()));
            Text vLastUpdate = document.createTextNode(GC.enC(GC.dateToString(GC.LAST_UPDATE, "dd-mm-yyyy")));
            Text vCompanyDesc = document.createTextNode(GC.enC(GC.getCompanyDescription()));
            Text vCompanyRut = document.createTextNode(GC.enC(GC.getCompanyRut()));
            Text vCompanyGiro = document.createTextNode(GC.enC(GC.getCompanyGiro()));
            Text vMsgMessage = document.createTextNode(GC.enC(GC.getMessageFile()));
            
            /**************USER*******************************************/
            //Asignamos la versión de nuestro XML
            document.setXmlVersion("1.0"); 
            //Añadimos al usr al documento
            document.getDocumentElement().appendChild(user); 
            user.appendChild(name); 
            //Añadimos valor
            name.appendChild(vName); 
            /**************LICENCE*******************************************/
            document.getDocumentElement().appendChild(lic);
            //Añadimos el elemento hijo a la raíz
            lic.appendChild(st);
            st.appendChild(vSt);
            lic.appendChild(code);
            code.appendChild(vCode);
            lic.appendChild(date); 
            
            date.appendChild(vDate);
            /**************NETWORK*******************************************/
            document.getDocumentElement().appendChild(network);
            //Añadimos el elemento hijo a la raíz
            network.appendChild(equipo);
            equipo.appendChild(vEquipo);
            //Añadimos valor
            network.appendChild(uri); 
            uri.appendChild(vUri);
            
            network.appendChild(port); 
            port.appendChild(vPort);
            
            /**************REGISTRY*******************************************/
            document.getDocumentElement().appendChild(registry);
            //Añadimos el elemento hijo a la raíz
            registry.appendChild(office);
            office.appendChild(vOffice);
            //Añadimos valor
            registry.appendChild(company); 
            company.appendChild(vCompany);
            
            registry.appendChild(inventary); 
            inventary.appendChild(vInventary);
            
            registry.appendChild(lastUpdate); 
            lastUpdate.appendChild(vLastUpdate);
            
            registry.appendChild(companyDesc); 
            companyDesc.appendChild(vCompanyDesc);
            
            registry.appendChild(companyGiro); 
            companyGiro.appendChild(vCompanyGiro);
            
            registry.appendChild(companyRut); 
            companyRut.appendChild(vCompanyRut);
            
            registry.appendChild(msgFile); 
            msgFile.appendChild(vMsgMessage);
            
            guardaConFormato(document,GC.directoryFilesPath()+"local.xml");
            
         }catch(Exception e){
             System.err.println("Class RegistroGlobal: Error");
         }
    }
    
    private static void guardaConFormato(Document document, String URI){
    try {
        TransformerFactory transFact = TransformerFactory.newInstance();

        //Formateamos el fichero. Añadimos sangrado y la cabecera de XML
        transFact.setAttribute("indent-number", new Integer(3));
        Transformer trans = transFact.newTransformer();
        trans.setOutputProperty(OutputKeys.INDENT, "yes");
        trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        //Hacemos la transformación
        StringWriter sw = new StringWriter();
        StreamResult sr = new StreamResult(sw);
        DOMSource domSource = new DOMSource(document);
        trans.transform(domSource, sr);

        //Mostrar información a guardar por consola (opcional)
        //Result console= new StreamResult(System.out);
        //trans.transform(domSource, console);
        try {
            //Creamos fichero para escribir en modo texto
            PrintWriter writer = new PrintWriter(new FileWriter(URI));

            //Escribimos todo el árbol en el fichero
            writer.println(sw.toString());

            //Cerramos el fichero
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } catch(Exception ex) {
        ex.printStackTrace();
    }
    }
    
    public static void checkXmlFiles() throws ParserConfigurationException, SAXException, IOException{
        File archivo = new File(GC.directoryFilesPath()+"local.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document documento = db.parse(archivo);
    }
    
    public static void cargarRegistroLocal(){
        try{
            File archivo = new File(GC.directoryFilesPath()+"local.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(archivo);
            
            /***********USER************************************************/
            documento.getDocumentElement().normalize();
            NodeList filas = documento.getElementsByTagName("usr");
            for (int temp = 0; temp < filas.getLength(); temp++) {
                Node nodo = filas.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    GC.username(element.getElementsByTagName("name").item(0).getTextContent());
                }
            }
            
            /***********LICENCE************************************************/
            int st = 0;
            documento.getDocumentElement().normalize();
            filas = documento.getElementsByTagName("lic");
            for (int temp = 0; temp < filas.getLength(); temp++) {
                Node nodo = filas.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String fInteger = GC.dsC(element.getElementsByTagName("st").item(0).getTextContent());
                    st = Integer.parseInt((GC.getStr(fInteger).isEmpty())?"0":fInteger);
                    GC.licenciaTipoPlan(st);
                    GC.setLicenceCode(GC.dsC(element.getElementsByTagName("code").item(0).getTextContent()));
                    GC.expDate(GC.dsC(element.getElementsByTagName("date").item(0).getTextContent()));
                }
            }
            
            /***********NETWORK************************************************/
            documento.getDocumentElement().normalize();
            filas = documento.getElementsByTagName("network");
            for (int temp = 0; temp < filas.getLength(); temp++) {
                Node nodo = filas.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    GC.setCurrentEquipo(GC.dsC(element.getElementsByTagName("equipo").item(0).getTextContent()));
                    GC.setUri(GC.dsC(element.getElementsByTagName("uri").item(0).getTextContent()));
                    GC.setPort(GC.dsC(element.getElementsByTagName("port").item(0).getTextContent()));
                }
            }
            
            /***********REGISTRY************************************************/
            Date fecha = null;
            documento.getDocumentElement().normalize();
            filas = documento.getElementsByTagName("registry");
            for (int temp = 0; temp < filas.getLength(); temp++) {
                Node nodo = filas.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    GC.setOficinaFromXml(GC.dsC(element.getElementsByTagName("office").item(0).getTextContent()));
                    GC.setCompanyNameFromXml(GC.dsC(element.getElementsByTagName("company").item(0).getTextContent()));
                    GC.setInventarioLocalFromXml(GC.dsC(element.getElementsByTagName("inventary").item(0).getTextContent()));
                    GC.setLastUpdateFromXml(GC.strToDate(GC.dsC(element.getElementsByTagName("last_update_bd").item(0).getTextContent())));
                    GC.setCompanyDescriptionFromXml(GC.dsC(element.getElementsByTagName("company_description").item(0).getTextContent()));
                    GC.setCompanyRutFromXml(GC.dsC(element.getElementsByTagName("company_rut").item(0).getTextContent()));
                    GC.setCompanyGiroFromXml(GC.dsC(element.getElementsByTagName("company_giro").item(0).getTextContent()));
                    GC.setMessageFileFromXml(GC.dsC(element.getElementsByTagName("message_file").item(0).getTextContent()));
                }
            }
        } catch (Exception e) {
            System.out.println("Class RegistroGlobal: Error al cargar xml local");
            return;
        }
    }
    
    public static void loadSyncCount(){
        try{
            File archivo = new File(GC.directoryFilesPath()+"reg.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(archivo);
            
            /***********UPT************************************************/
            documento.getDocumentElement().normalize();
            NodeList filas = documento.getElementsByTagName("upt");
            for (int temp = 0; temp < filas.getLength(); temp++) {
                Node nodo = filas.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    String fInteger = GC.dsC(element.getElementsByTagName("cont").item(0).getTextContent());
                    Date vReg = GC.fechaPorDefectoDate();
                    vReg = GC.strToDate(GC.dsC(element.getElementsByTagName("reg").item(0).getTextContent()));
                    int value =0;
                    try{value = Integer.parseInt(fInteger);}catch(Exception e){value = -1;}
                    value=compobarSyncCount(value,vReg);
                    GlobalValuesVariables.setSyncCount(value);
                }
            }
        } catch (Exception e) {
            System.out.println("Class RegistroGlobal: Error al cargar xml local");
            GC.setSyncCount(-1);
            return;
        }
    }
    
    public static void saveSyncCount(){
         
         Document document = null;
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         try{
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            document = implementation.createDocument(null, "reg", null);
            /**************************UPT******************************/
            Element upt = document.createElement("upt"); 
            Element cont= document.createElement("cont");
            Element reg= document.createElement("reg");
            Text vCont = document.createTextNode(GC.enC(""+GlobalValuesVariables.getSyncCount())); 
            Text vReg = document.createTextNode(GC.enC(GC.dateToString(new Date(), "dd-mm-yyyy"))); 
            
            /**************************NETWORK******************************/
             /**************************REGISTRY******************************/
            /**************UPT*******************************************/
            //Asignamos la versión de nuestro XML
            document.setXmlVersion("1.0"); 
            //Añadimos al usr al documento
            document.getDocumentElement().appendChild(upt); 
            upt.appendChild(cont);
            upt.appendChild(reg);
            //Añadimos valor
            cont.appendChild(vCont); 
            reg.appendChild(vReg);
            
            guardaConFormato(document,GC.directoryFilesPath()+"reg.xml");
            
         }catch(Exception e){
             System.err.println("Class RegistroGlobal: Error");
         }
    }
    
    public static void cargarDatosLicencia(){
        try{
            File archivo = new File(GC.directoryFilesPath()+"local.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document documento = db.parse(archivo);
            
            /***********LICENCE************************************************/
            int st = 0;
            documento.getDocumentElement().normalize();
            NodeList filas = documento.getElementsByTagName("lic");
            for (int temp = 0; temp < filas.getLength(); temp++) {
                Node nodo = filas.item(temp);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) nodo;
                    st = Integer.parseInt(GC.dsC(element.getElementsByTagName("st").item(0).getTextContent()));
                    GC.licenciaTipoPlan(st);
                    GC.setLicenceCode(GC.dsC(element.getElementsByTagName("code").item(0).getTextContent()));
                    GC.expDate(GC.dsC(element.getElementsByTagName("date").item(0).getTextContent()));
                }
            }
            
        } catch (Exception e) {
            System.out.println("Class RegistroGlobal: Error al cargar xml local");
            return;
        }
    }
    
    public static String getLicenciaOnline(String licencia,String stUrl) {
            try{
                URL url = new URL(stUrl);
                //URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                String entrada;
                String cadena="";

                while ((entrada = br.readLine()) != null){
                        cadena = cadena + entrada;
                }

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource archivo = new InputSource();
                archivo.setCharacterStream(new StringReader(cadena)); 

                Document documento = db.parse(archivo);
                documento.getDocumentElement().normalize();
                documento.getDocumentElement().normalize();

                NodeList nodeLista = documento.getElementsByTagName("lic");

                for (int s = 0; s < nodeLista.getLength(); s++) {

                    Node primerNodo = nodeLista.item(s);
                    String id;
                    if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {

                        Element primerElemento = (Element) primerNodo;

                        NodeList primerNombreElementoLista =
                                        primerElemento.getElementsByTagName("id");
                        Element primerNombreElemento =
                                        (Element) primerNombreElementoLista.item(0);
                        NodeList primerNombre = primerNombreElemento.getChildNodes();
                        id = ((Node) primerNombre.item(0)).getNodeValue().toString();
                        if(id.equals(licencia)){
                            return licencia;
                        }
                    }
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        
    }
    
    public static boolean readXMLOnline() {
        cargarDatosLicencia();
        if(GC.licenciaTipoPlan()!=GlobalValuesVariables.licenciaTipoFree()){
            try{
                int cont=0;
                URL url = new URL(GC.uri());
                //URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                String entrada;
                String cadena="";

                while ((entrada = br.readLine()) != null){
                        cadena = cadena + entrada;
                }

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                //Document documento = db.parse(conn.getInputStream());
                InputSource archivo = new InputSource();
                archivo.setCharacterStream(new StringReader(cadena)); 

                Document documento = db.parse(archivo);
                documento.getDocumentElement().normalize();
                documento.getDocumentElement().normalize();

                NodeList nodeLista = documento.getElementsByTagName("lic");

                for (int s = 0; s < nodeLista.getLength(); s++) {

                    Node primerNodo = nodeLista.item(s);
                    String id;
                    int st;

                    if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {

                        Element primerElemento = (Element) primerNodo;

                        NodeList primerNombreElementoLista =
                                        primerElemento.getElementsByTagName("id");
                        Element primerNombreElemento =
                                        (Element) primerNombreElementoLista.item(0);
                        NodeList primerNombre = primerNombreElemento.getChildNodes();
                        id = ((Node) primerNombre.item(0)).getNodeValue().toString();
                        if(id.equals(GC.licenceCode())){
                            cont++;
                            NodeList segundoNombreElementoLista =
                                        primerElemento.getElementsByTagName("st");
                            Element segundoNombreElemento =
                                        (Element) segundoNombreElementoLista.item(0);
                            NodeList segundoNombre = segundoNombreElemento.getChildNodes();

                            st = Integer.parseInt(((Node) segundoNombre.item(0)).getNodeValue().toString());

                            NodeList tercerNombreElementoLista =
                                        primerElemento.getElementsByTagName("date");
                            Element tercerNombreElemento =
                                            (Element) tercerNombreElementoLista.item(0);
                            NodeList tercerNombre = tercerNombreElemento.getChildNodes();
                            GC.licenciaTipoPlan(st);
                            GC.expDate(((Node) tercerNombre.item(0)).getNodeValue().toString());
                            crearRegistroLocal();
                        }
                    }
                }
                if(cont == 0){
                    OptionPane.showMsg("Error de licencia", "Esta es una copia fraudulenta del software original.",2);
                    GC.licenciaTipoPlan(0);
                    GC.setLicenceCode("free");
                    crearRegistroLocal();
                }
                return true;
            }
            catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    /**
     * Se encarga de reiniciar el contador validando la fecha actual
     * @param value
     * @param vReg
     * @return 
     */
    private static int compobarSyncCount(int value, Date vReg) {
        if(value<0)return -1;
        if(GC.dateToString(vReg, "dd-mm-yyyy").equals(GC.fechaPorDefectoString()))return -1;
        if(GC.fechaPasada(vReg))return 0;
        return value;
    }

    static int getTipoPlanOnline(String licencia, String stUrl) {
        try{
                URL url = new URL(stUrl);
                //URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                String entrada;
                String cadena="";

                while ((entrada = br.readLine()) != null){
                        cadena = cadena + entrada;
                }

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource archivo = new InputSource();
                archivo.setCharacterStream(new StringReader(cadena)); 

                Document documento = db.parse(archivo);
                documento.getDocumentElement().normalize();
                documento.getDocumentElement().normalize();

                NodeList nodeLista = documento.getElementsByTagName("lic");

                for (int s = 0; s < nodeLista.getLength(); s++) {

                    Node primerNodo = nodeLista.item(s);
                    String id;

                    if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {

                        Element primerElemento = (Element) primerNodo;

                        NodeList primerNombreElementoLista =
                                        primerElemento.getElementsByTagName("id");
                        Element primerNombreElemento =
                                        (Element) primerNombreElementoLista.item(0);
                        NodeList primerNombre = primerNombreElemento.getChildNodes();
                        id = ((Node) primerNombre.item(0)).getNodeValue().toString();
                        if(id.equals(licencia)){
                            NodeList segundoNombreElementoLista =
                                        primerElemento.getElementsByTagName("st");
                            Element segundoNombreElemento =
                                        (Element) segundoNombreElementoLista.item(0);
                            NodeList segundoNombre = segundoNombreElemento.getChildNodes();

                            return Integer.parseInt(((Node) segundoNombre.item(0)).getNodeValue().toString());
                        }
                    }
                }
                return 0;
            }
            catch (Exception e) {
                return 0;
            }
    }
    
    static String getExpDateOnline(String licencia, String stUrl) {
        try{
                URL url = new URL(stUrl);
                //URLConnection conn = url.openConnection();
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                String entrada;
                String cadena="";

                while ((entrada = br.readLine()) != null){
                        cadena = cadena + entrada;
                }

                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                InputSource archivo = new InputSource();
                archivo.setCharacterStream(new StringReader(cadena)); 

                Document documento = db.parse(archivo);
                documento.getDocumentElement().normalize();
                documento.getDocumentElement().normalize();

                NodeList nodeLista = documento.getElementsByTagName("lic");

                for (int s = 0; s < nodeLista.getLength(); s++) {

                    Node primerNodo = nodeLista.item(s);
                    String id;

                    if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {

                        Element primerElemento = (Element) primerNodo;

                        NodeList primerNombreElementoLista =
                                        primerElemento.getElementsByTagName("id");
                        Element primerNombreElemento =
                                        (Element) primerNombreElementoLista.item(0);
                        NodeList primerNombre = primerNombreElemento.getChildNodes();
                        id = ((Node) primerNombre.item(0)).getNodeValue().toString();
                        if(id.equals(licencia)){
                            NodeList segundoNombreElementoLista =
                                        primerElemento.getElementsByTagName("date");
                            Element segundoNombreElemento =
                                        (Element) segundoNombreElementoLista.item(0);
                            NodeList segundoNombre = segundoNombreElemento.getChildNodes();

                            return ((Node) segundoNombre.item(0)).getNodeValue().toString();
                        }
                    }
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
    }
    
    public static String imprimirDatosLeidos(){
        return "\n"+
        "Username: "+GC.username()+"\n"+
        "tp: "+GC.licenciaTipoPlan()+"\n"+
        "code: "+GC.licenceCode()+"\n"+
        "expdate: "+GC.expDate()+"\n"+
        "equipo: "+GC.equipo()+"\n"+
        "uri: "+GC.uri()+"\n"+
        "port: "+GC.port()+"\n"+
        "lastupdate: "+GC.dateToString(GC.LAST_UPDATE, "dd-mm-yyyy")+"\n"+
        
        "companyname: "+GC.companyName()+"\n"+
        "companyrut: "+GC.getCompanyRut()+"\n"+
        "companydescription: "+GC.getCompanyDescription()+"\n"+
        "companyGiro: "+GC.getCompanyGiro()+"\n"+
        "officename: "+GC.getNombreOficina()+"\n"+
        "inventary: "+GC.inventarioName()+"\n"+
        "messagefile: "+GC.getMessageFile();
    }

    public static void deleteXmlFiles() {
        
        File fichero = new File(GC.directoryFilesPath()+"local.xml");

        if (fichero.delete())
            System.out.println("El fichero local.xml ha sido borrado satisfactoriamente");
        else
            System.out.println("El fichero local.xml no pudó ser borrado");
        
        fichero = new File(GC.directoryFilesPath()+"reg.xml");

        if (fichero.delete())
            System.out.println("El fichero reg.xml ha sido borrado satisfactoriamente");
        else
            System.out.println("El fichero reg.xml no pudó ser borrado");
    }
}
