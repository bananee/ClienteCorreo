package src;

import java.util.Properties;
 
// Mira las librerias aca
// ok, solo aplicas un sendMailTLS.send(envoltura)???
// eso hace todo, atras esta todo esto que ves
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class SendMailTLS {
    
	public static void send(Envoltura env) {
 
		final String username = env.Remitente;
		final String password = env.Pass;
		final String host = env.HostPropio;
		final String destino = env.Destinatario;
		final Mensaje m = env.Mensaje;
		final String msg = m.Cuerpo;
		final String asunto = env.Mensaje.consigaAsunto();
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);//smtp.gamil.com
		props.put("mail.smtp.port", "587");
 
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(destino));
			message.setSubject(asunto);
			message.setText(msg);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}