/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 https://www.google.com/settings/security/lesssecureapps
 */
package projectmanager;

/**
 *
 * @author Catherine Beata
 */
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {

    private String emailUser = "cm.beatamatthews@gmail.com";
    private String emailPass = "Bangosh.10";

    public boolean validarCorreo(String user) {

        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(user);

        return matcher.find();
    }

    public void enviarCorreoAsignacion(String correoANotificar, String nombre, String descripcion, String fechaInicio, String fechaFin) {
        try {
            String host = "smtp.gmail.com";
            String user = emailUser;
            String pass = emailPass;
            String to = correoANotificar;
            String from = emailUser;
            String subject = "ASIGNACION DE PROYECTO";
            String messageText = "Se te acaba de asignar el Proyecto: " + nombre + ", con la siguiente descripcion: "
                    + descripcion + ". Fecha Inicio: " + fechaInicio + " y Fecha de Finalización: " + fechaFin + ".";
            boolean sessionDebug = false;

            Properties props = System.getProperties();

            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.required", "true");

            java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            Session mailSession = Session.getDefaultInstance(props, null);
            mailSession.setDebug(sessionDebug);
            Message msg = new MimeMessage(mailSession);
            msg.setFrom(new InternetAddress(from));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoANotificar));
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(messageText);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect(host, user, pass);
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
            System.out.println();
            System.out.println("...................Se ha enviado un correo de Asignación....................");
            System.out.println();
        } catch (Exception ex) {
            System.out.println("......................Se produjo un error en el envio.......................");
            System.out.println(ex);
        }
    }
}
