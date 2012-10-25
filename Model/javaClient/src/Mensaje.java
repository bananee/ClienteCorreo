package src;

import java.util.*;
import java.text.*;

/**
 * Mensaje de correo.
 */
public class Mensaje {
    /* Los encabezados y el cuerpo del mensaje. */
	
    public String smtpHost;
	public String Encabezados;
    public String Cuerpo;
    
    // LA CLAVE DE USUARIO
    private String pass;
    /* Remitente y destinatario. Con esto no es necesario tomarlos de los encabezados. */
    private String De;
    private String Para;
    private String Subject;

    /* Para hacerlo más bonito se define CRLF */
    private static final String CRLF = "\r\n";

    /* Crea el objeto Mensaje al insertar los encabezados requeridos 
       por el RFC 822 (From, To, Date). */
    public Mensaje(String smtp, String passss, String de, String para, String asunto, String texto) 
    {
        /* Quita los espacios en blanco */
    	this.smtpHost = smtp;
    	this.pass = passss;  // edit propio
    	this.Subject = asunto;
        De = de.trim();
        Para = para.trim();
        Encabezados = "From: " + De + CRLF;
        Encabezados += "To: " + Para + CRLF;
        Encabezados += "Subject: " + asunto.trim() + CRLF;

        /* Una aproximación al formato requerido. Sólo GMT. */
        SimpleDateFormat formatoFecha = 
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        String fechaString = formatoFecha.format(new Date());
        Encabezados += "Date: " + fechaString + CRLF;
        Cuerpo = texto;
    }

    /* Dos funciones para acceder el remitente y el destinatario. */
    public String consigaAsunto(){
    	return Subject;
    }
    
    public String consigaDe() {
        return De;
    }

    public String consigaPara() {
        return Para;
    }
    
    //Funcion para tomar el pass  - edit propio
    
    public String consigaPass(){
    	return pass;
    }
    
    public String consigaHostPropio(){
    	return smtpHost;
    }
    /* Revisa si el mensaje es válido. En otras palabra, si el remitente
       y el destinatario tienen sólo un signo @ */
    public boolean esValido() {
        int arrobaDe = De.indexOf('@');
        int arrobaPara = Para.indexOf('@');

        if(arrobaDe < 1 || (De.length() - arrobaDe) <= 1) {
            System.out.println("La dirección del remitente es inválida");
            return false;
        }
        if(arrobaPara < 1 || (Para.length() - arrobaPara) <= 1) {
            System.out.println("La dirección del destinatario es inválida");
            return false;
        }
        if(arrobaDe != De.lastIndexOf('@')) {
            System.out.println("La dirección del Remitente es inválida");
            return false;
        }
        if(arrobaPara != Para.lastIndexOf('@')) {
            System.out.println("La dirección del destinatario es inválida");
            return false;
        }       
        return true;
    }
    
    /* Para imprimir el mensaje. */
    public String toString() {
        String res;
        res = Encabezados + CRLF;
        res += Cuerpo;
        return res;
    }
}