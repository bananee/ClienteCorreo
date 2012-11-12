import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
 
// Mira las librerias aca
// ok, solo aplicas un sendMailTLS.send(envoltura)???
// eso hace todo, atras esta todo esto que ves


import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


 
public class SendMailTLS {
    
	public  void send(Mail theMail) {
 
		final String username = theMail.Remitente;
		final String password = theMail.Pass;
		final String host = theMail.HostPropio;
		final String destino = theMail.Destinatario;
		final String msg = theMail.Texto;
		final String asunto = theMail.Asunto;
		final boolean attachActivate = theMail.attach;
		final String fileAttached = theMail.theAttach;
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);//smtp.gamil.com
		props.put("mail.smtp.port", "587");
		
		
		Session session = Session.getInstance(props,
		  new  Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
 
		try {
			//DataSource source = new FileDataSource("name");
			
			Message mail = new MimeMessage(session);
			
			mail.setFrom(new InternetAddress(username));
			InternetAddress[] adds = { new InternetAddress(destino)};
			mail.setRecipients(Message.RecipientType.TO,
	        adds);//InternetAddress.parse(destino)
			mail.setSubject(asunto);
			
			//--- create and fill the first message part
		      MimeBodyPart mbp1 = new MimeBodyPart();
		      mbp1.setText(msg);

		      //--- create the second message part
		      MimeBodyPart mbp2 = new MimeBodyPart();
		      
		      
		      //File thefile = new File("/home/mae-ubu1004/pena.docx");
		      //mbp2.attachFile("/home/mae-ubu1004/pena.docx");
		      
		      //mbp2.setFileName("/home/mae-ubu1004/pena.docx");
		      
		      // attach the file to the message
		      
		      FileDataSource fds = new FileDataSource(fileAttached);
		      mbp2.setDataHandler(new DataHandler(fds));
		      mbp2.setFileName(fds.getName());
		      
		      
		      /* FileDataSource fds = new FileDataSource("/home/mae-ubu1004/pena.docx") {
		  		public String getContentType() {
		  		    return "application/octet-stream";
		  		}
		  	    };
		  	    mbp2.setDataHandler(new DataHandler(fds));
		  	    mbp2.setFileName(fds.getName());
		  	 */
		  	    
		     // mbp2.setHeader("Content-Transfer-Encoding", "base64");
		      //SimpleSendEmail

		      // create the Multipart and add its parts to it
		      
		      Multipart mp = new MimeMultipart();
		      
		      if(attachActivate){
		    	  mp.addBodyPart(mbp1);
		    	  mp.addBodyPart(mbp2);
		      }else{
		    	  mp.addBodyPart(mbp1);
		      }
		      

		      // add the Multipart to the message
		      mail.setContent(mp);

		      // set the Date: header
		      mail.setSentDate(new Date());
			
			
			Transport.send(mail);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
/*
 *
 * 
 * 
 * private static void send0(Message msg, Address[] addresses)
    throws MessagingException
  {
    if ((addresses == null) || (addresses.length == 0)) {
      throw new SendFailedException("No recipient addresses");
    }

    Hashtable protocols = new Hashtable();

    Vector invalid = new Vector();
    Vector validSent = new Vector();
    Vector validUnsent = new Vector();

    for (int i = 0; i < addresses.length; i++)
    {
      if (protocols.containsKey(addresses[i].getType())) {
        Vector v = (Vector)protocols.get(addresses[i].getType());
        v.addElement(addresses[i]);
      }
      else {
        Vector w = new Vector();
        w.addElement(addresses[i]);
        protocols.put(addresses[i].getType(), w);
      }
    }

    int dsize = protocols.size();
    if (dsize == 0) {
      throw new SendFailedException("No recipient addresses");
    }
    Session s = msg.session != null ? msg.session : Session.getDefaultInstance(System.getProperties(), null);

    if (dsize == 1) {
      Transport transport = s.getTransport(addresses[0]);
      try {
        transport.connect();
        transport.sendMessage(msg, addresses);
      } finally {
        transport.close();
      }
      return;
    }

    Object chainedEx = null;
    boolean sendFailed = false;

    Enumeration e = protocols.elements();
    while (e.hasMoreElements()) {
      Vector v = (Vector)e.nextElement();
      Address[] protaddresses = new Address[v.size()];
      v.copyInto(protaddresses);
      Transport transport;
      if ((transport = s.getTransport(protaddresses[0])) == null)
      {
        for (int j = 0; j < protaddresses.length; j++)
          invalid.addElement(protaddresses[j]);
      }
      else {
        try {
          transport.connect();
          transport.sendMessage(msg, protaddresses);
        } catch (SendFailedException sex) {
          sendFailed = true;

          if (chainedEx == null)
            chainedEx = sex;
          else {
            ((MessagingException)chainedEx).setNextException(sex);
          }

          Address[] a = sex.getInvalidAddresses();
          if (a != null) {
            for (int j = 0; j < a.length; j++) {
              invalid.addElement(a[j]);
            }
          }
          a = sex.getValidSentAddresses();
          if (a != null) {
            for (int k = 0; k < a.length; k++) {
              validSent.addElement(a[k]);
            }
          }
          Address[] c = sex.getValidUnsentAddresses();
          if (c != null)
            for (int l = 0; l < c.length; l++)
              validUnsent.addElement(c[l]);
        } catch (MessagingException mex) {
          sendFailed = true;

          if (chainedEx == null)
            chainedEx = mex;
          else
            ((MessagingException)chainedEx).setNextException(mex);
        } finally {
          transport.close();
        }
      }
    }

    if ((sendFailed) || (invalid.size() != 0) || (validUnsent.size() != 0)) {
      Address[] a = null; Address[] b = null; Address[] c = null;

      if (validSent.size() > 0) {
        a = new Address[validSent.size()];
        validSent.copyInto(a);
      }
      if (validUnsent.size() > 0) {
        b = new Address[validUnsent.size()];
        validUnsent.copyInto(b);
      }
      if (invalid.size() > 0) {
        c = new Address[invalid.size()];
        invalid.copyInto(c);
      }
      throw new SendFailedException("Sending failed", (Exception)chainedEx, a, b, c);
    }
  }

 * 
 */
