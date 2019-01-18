/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.softdirex.enubase.utils;

import cl.softdirex.enubase.entities.reports.SalesVentasJasperReport;
import cl.softdirex.enubase.view.notifications.Notification;
import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author jorge
 */
public class Send {
    private int width = 100;
    private int height = 140;
    private String color_turquesa = "#22b59f";
    private String color_rojo = "#b21d00";
    private String color_verde = "#45a849";
    private String color_azul = "#2d2e77";
    private String color_rosa = "#ee4c50";
    private String color_celeste = "#70bbd9";
    private String color1 = "#70bbd9";
    private String color2 = "#ee4c50";
    
    private void sendMail(String subject, String mailTo, String title,String content,String subcontent1,String subcontent2,String urlImg,String urlSubImg1, String urlSubImg2){
        // SMTP server information
        if(color2.equals(color_turquesa)){
            color2 = color_verde;
        }
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = StVars.getMailSystemName();
        String password = StVars.getMailSystemPass();
 
        // outgoing message information
 
        // message contains HTML markups
        String message ="<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
"<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
"<head>\n" +
"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n" +
"<title>Demystifying Email Design</title>\n" +
"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" +
"</head>\n" +
"<body style=\"margin: 0; padding: 0;\">\n" +
"	<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">	\n" +
"		<tr>\n" +
"			<td style=\"padding: 10px 0 30px 0;\">\n" +
"				<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border: 1px solid #cccccc; border-collapse: collapse;\">\n" +
"					<tr>\n" +
"						<td align=\"center\" bgcolor=\""+color1+"\" style=\"padding: 40px 0 30px 0; color: #153643; font-size: 28px; font-weight: bold; font-family: Arial, sans-serif;\">\n" +
"							<img src=\""+urlImg+"\" alt=\"Creating Email Magic\" width=\"80\" height=\"80\" style=\"display: block;\" />\n" +
"						</td>\n" +
"					</tr>\n" +
"					<tr>\n" +
"						<td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px;\">\n" +
"							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"								<tr>\n" +
"									<td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 24px;\">\n" +
"										<b>"+title+"</b>\n" +
"									</td>\n" +
"								</tr>\n" +
"								<tr>\n" +
"									<td style=\"padding: 20px 0 30px 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
"										"+content+"\n" +
"									</td>\n" +
"								</tr>\n" +
"								<tr>\n" +
"									<td>\n" +
"										<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"											<tr>\n" +
"												<td width=\"260\" valign=\"top\">\n" +
"													<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"														<tr>\n" +
"															<td>\n" +
"																<img src=\""+urlSubImg1+"\" alt=\"\" width=\""+width+"%\" height=\""+height+"\" style=\"display: block;\" />\n" +
"															</td>\n" +
"														</tr>\n" +
"														<tr>\n" +
"															<td style=\"padding: 25px 0 0 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
"																"+subcontent1+"\n" +
"															</td>\n" +
"														</tr>\n" +
"													</table>\n" +
"												</td>\n" +
"												<td style=\"font-size: 0; line-height: 0;\" width=\"20\">\n" +
"													&nbsp;\n" +
"												</td>\n" +
"												<td width=\"260\" valign=\"top\">\n" +
"													<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"														<tr>\n" +
"															<td>\n" +
"																<img src=\""+urlSubImg2+"\" alt=\"\" width=\""+width+"%\" height=\""+height+"\" style=\"display: block;\" />\n" +
"															</td>\n" +
"														</tr>\n" +
"														<tr>\n" +
"															<td style=\"padding: 25px 0 0 0; color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 20px;\">\n" +
"																"+subcontent2+"\n" +
"															</td>\n" +
"														</tr>\n" +
"													</table>\n" +
"												</td>\n" +
"											</tr>\n" +
"										</table>\n" +
"									</td>\n" +
"								</tr>\n" +
"							</table>\n" +
"						</td>\n" +
"					</tr>\n" +
"					<tr>\n" +
"						<td bgcolor=\""+color2+"\" style=\"padding: 30px 30px 30px 30px;\">\n" +
"							<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
"								<tr>\n" +
"									<td style=\"color: #ffffff; font-family: Arial, sans-serif; font-size: 14px;\" width=\"75%\">\n" +
"										&reg; Todos los derechos reservados 2018<br/>\n" +
"										Un producto de <a href=\"http://www.softdirex.cl\" style=\"color: #ffffff;\"><font color=\"#ffffff\">Softdirex</font></a>\n" +
"									</td>\n" +
"									<td align=\"right\" width=\"25%\">\n" +
"										<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">\n" +
"											<tr>\n" +
"												<td style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: bold;\">\n" +
"													<a href=\"https://twitter.com/softdirex\" style=\"color: #ffffff;\">\n" +
"														<img src=\"http://www.softdirex.cl/imgMail/tw-logo.png\" alt=\"Twitter\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
"													</a>\n" +
"												</td>\n" +
"												<td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
"												<td style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: bold;\">\n" +
"													<a href=\"http://fb.me/Softdirex\" style=\"color: #ffffff;\">\n" +
"														<img src=\"http://www.softdirex.cl/imgMail/fb-logo.png\" alt=\"Facebook\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
"													</a>\n" +
"												</td>\n" +
"											</tr>\n" +
"										</table>\n" +
"									</td>\n" +
"								</tr>\n" +
"							</table>\n" +
"						</td>\n" +
"					</tr>\n" +
"				</table>\n" +
"			</td>\n" +
"		</tr>\n" +
"	</table>\n" +
"</body>\n" +
"</html>";
 
 
        try {
            sendHtmlEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void sendReportMail(String title,String content){
        if(NetWrk.isOnline()){
            width = 25;
            height = 50;
            color1 =  color_turquesa;
            String userName = (StEntities.USER != null)?StEntities.USER.getUsername():"No iniciado";
            String equipo = StVars.getEquipo().substring(0,StVars.getEquipo().indexOf("_"));
            sendMail("Reporte desde equipo: "+equipo+", Comercial: "+StVars.getCompanyName(),
                    StVars.getMailReport(), title, content,
                    "Usuario: "+userName, StVars.getCompanyName(), StVars.LOGO_MAIL, 
                    StVars.ICON_USER_MAIL, 
                    StVars.ICON_COMPANY_MAIL);
            width = 100;
            height = 140;
        }
    }
    
    public void sendMessageMail(String asunto,String mailDestino){
        if(NetWrk.isOnline()){
            width = 25;
            height = 50;
            color1 =  color_turquesa;
            color2 = color_verde;
            
            sendMail("["+StVars.getProjectName()+"] Nuevo mensaje: "+asunto,
                    mailDestino, "Tienes un nuevo mensaje en tu buzon de entrada", "Inicia sesión en "+StVars.getProjectName()+" para verlo.",
                    "Usuario: "+StEntities.USER.getNombre(), StVars.getCompanyName(), StVars.LOGO_MAIL, 
                    StVars.ICON_USER_MAIL, 
                    StVars.ICON_COMPANY_MAIL);
            width = 100;
            height = 140;
        }
    }
    
    public void sendReportSalesMail(SalesVentasJasperReport salesReport,String mail, String title){
        if(NetWrk.isOnline() && GV.licenciaIsEnableToSendMails()){
            width = 25;
            height = 50;
            color1 =  color_turquesa;
            String equipo = StVars.getEquipo().substring(0,StVars.getEquipo().indexOf("_"));
            sendMail("Reporte de ventas desde "+equipo+" ["+StVars.getCompanyName()+"]",
                    mail, "Reporte de ventas", salesReport.generateHtml(title),
                    "Usuario: "+StEntities.USER.getUsername(), StVars.getCompanyName(), StVars.LOGO_MAIL, 
                    StVars.ICON_USER_MAIL, 
                    StVars.ICON_COMPANY_MAIL);
            width = 100;
            height = 140;
        }
    }
    
    public void sendReportItemsMail(String mail, String title){
        if(NetWrk.isOnline() && GV.licenciaIsEnableToSendMails()){
            width = 25;
            height = 50;
            color1 =  color_turquesa;
            int compra = StVars.ITEMS_COMPRA;
            int venta = StVars.ITEMS_VENTA;
            String reporte = 
                "<table style=\"width:100%\">\n" +
                "  <tr>\n" +
                "    <th></th>\n" +
                "    <th></th> \n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td><strong>Stock total de productos</strong></td>\n" +
                "    <td><strong>"+StVars.ITEMS_STOCK+"</strong></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Productos con stock bajo</td>\n" +
                "    <td>"+StVars.ITEMS_STOCK_BAJO+"</td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td>Productos con stock en cero</td>\n" +
                "    <td>"+StVars.ITEMS_STOCK_CERO+"</td>\n" +
                "  </tr>\n" +
                "</table>"
                + "</br></br>"
                    + "<table style=\"width:100%\">\n" +
                    "  <tr>\n" +
                    "    <th>Detalle</th>\n" +
                    "    <th>Monto</th> \n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>En inventario</td>\n" +
                    "    <td>"+GV.strToPrice(compra)+"</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td>Monto de venta</td>\n" +
                    "    <td>"+GV.strToPrice(venta)+"</td>\n" +
                    "  </tr>\n" +
                    "  <tr>\n" +
                    "    <td><strong>Retorno</strong></td>\n" +
                    "    <td><strong>"+GV.strToPrice((venta-compra))+"</strong></td>\n" +
                    "  </tr>\n" +
                    "</table>"
                + "</br><small>El monto calculado en inventario corresponde al precio de referencia ingresado por item.</small>";
            String equipo = StVars.getEquipo().substring(0,StVars.getEquipo().indexOf("_"));
            sendMail("Reporte de inventario desde "+equipo+" ["+StVars.getCompanyName()+"]",
                    mail, title, reporte,
                    "Usuario: "+StEntities.USER.getUsername(), StVars.getCompanyName(), StVars.LOGO_MAIL, 
                    StVars.ICON_USER_MAIL, 
                    StVars.ICON_COMPANY_MAIL);
            width = 100;
            height = 140;
        }
    }
    
    
    private void sendHtmlEmail(String host, String port,
            final String userName, final String password, String toAddress,
            String subject, String message) throws AddressException,
            MessagingException,
            GeneralSecurityException {
        if(GV.getStr(toAddress).isEmpty())
            return;
        // sets SMTP server properties
        Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", StVars.getMailSystemName());
            p.setProperty("mail.smtp.auth", "true");
 
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };
 
        Session session = Session.getInstance(p, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        MimeMultipart m = new MimeMultipart();
        // set plain text message
        msg.setContent(message, "text/html");
 
        // sends the e-mail
        try{
            Transport.send(msg);
        }catch(Exception e){
            Notification.showMsg("Error de datos", "No se pudo enviar email al correo electronico: "+toAddress, 3);
        }
        
 
    }
    
    public boolean sendFileMail(String directorio1){
        try{
            Properties p = new Properties();
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            p.setProperty("mail.smtp.starttls.enable", "true");
            p.setProperty("mail.smtp.port", "587");
            p.setProperty("mail.smtp.user", StVars.getMailSystemName());
            p.setProperty("mail.smtp.auth", "true");
            
            Session s = Session.getDefaultInstance(p,null);
            BodyPart text = new MimeBodyPart();
            String equipo = StVars.getEquipo().substring(0,StVars.getEquipo().indexOf("_"));
            text.setText("Respaldo de base de datos Derby, programa "+StVars.getProjectName()+" version "+StVars.getVersion()+", equipo: "+equipo);
            BodyPart adjunto1=new MimeBodyPart();
            if(directorio1 != null){
                if(!directorio1.equals("")){
                    adjunto1.setDataHandler(new DataHandler(new FileDataSource(directorio1)));
                    adjunto1.setFileName(GV.getToName(directorio1.substring(directorio1.lastIndexOf(File.separator)+1)));

                }
            }
            
            MimeMultipart m = new MimeMultipart("mixed"); 
            m.addBodyPart(text);
            if(directorio1 != null){
                if(!directorio1.equals("")){
                    m.addBodyPart(adjunto1);
                }
            }
            
            MimeMessage mensaje = new MimeMessage(s);
            mensaje.setFrom(new InternetAddress(StVars.getMailSystemName()));
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(StVars.getMailReport()));
            mensaje.setSubject("Backup: "+StVars.getCompanyName()+", System and version: "+StVars.getProjectName()+"-"+StVars.getVersion());
            mensaje.setContent(m);
            Transport t = s.getTransport("smtp");
            try{
                t.connect(StVars.getMailSystemName(),StVars.getMailSystemPass());
            }catch(MessagingException ex){
                System.out.println("Error en t.connect:"+ex);
            }
            
            
            t.sendMessage(mensaje, mensaje.getAllRecipients());
            
            t.close();
            return true;
        }catch(Exception ex){
            System.out.println("Error "+ex);
            return false;
        }
        
    }
}
