package utilidades;

import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.File;
import java.util.Properties;

/**
 * Clase que permite enviar un correo con una factura adjunta en formato PDF.
 * Utiliza el servicio de Mailtrap para la simulación de envío de correos electrónicos.
 */
public class CorreoFactura {

    /**
     * Envía un correo electrónico con un archivo PDF adjunto.
     *
     * @param correoDestino La dirección de correo electrónico del destinatario.
     * @param rutaPDF La ruta completa del archivo PDF a adjuntar.
     */
    public static void enviarFactura(String correoDestino, String rutaPDF) {
        final String remitente = "djhon8366@gmail.com"; // Solo decorativo
        final String username = "505692e1fc5da5"; // CORRECTO
        final String password = "0fc16feb0c193f"; // CORRECTO

        // Configuración de las propiedades del servidor SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        props.put("mail.smtp.port", "587");

        // Crear sesión de correo utilizando autenticación
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje de correo
            Message mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente));
            mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
            mensaje.setSubject("Factura de su compra - Ferretería El Tornillo Feliz 🔩");

            // Cuerpo del correo
            MimeBodyPart texto = new MimeBodyPart();
            texto.setText("Gracias por su compra. Adjunto encontrará su factura.");

            // Crear la parte de adjunto (PDF)
            MimeBodyPart adjunto = new MimeBodyPart();
            adjunto.attachFile(new File(rutaPDF));

            // Componer el correo con el texto y el archivo adjunto
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(texto);
            multipart.addBodyPart(adjunto);

            // Establecer el contenido del mensaje
            mensaje.setContent(multipart);

            // Enviar el correo
            Transport.send(mensaje);
            System.out.println("✅ Correo enviado exitosamente con Mailtrap.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
