package utilidades;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.util.Properties;

public class CorreoFactura {

    public static void enviarFactura(String correoDestino, String rutaPDF) {
        final String remitente = "djhon8366@gmail.com"; // solo decorativo
        final String username = "505692e1fc5da5"; // CORRECTO
        final String password = "0fc16feb0c193f"; // CORRECTO

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            mensaje.setSubject("Factura de su compra - Ferretería El Tornillo Feliz 🔩");

            // Cuerpo del correo
            MimeBodyPart texto = new MimeBodyPart();
            texto.setText("Gracias por su compra. Adjunto encontrará su factura.");

            // Adjuntar el PDF
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.attachFile(new File(rutaPDF));

            // Enviar como multipartes
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(adjunto);

            mensaje.setContent(multipart);

            Transport.send(mensaje);
            System.out.println("✅ Correo enviado exitosamente con Mailtrap.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
