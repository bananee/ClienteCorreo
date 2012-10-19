import java.util.*;
import java.text.*;

/**
 * Mensaje de correo.
 */
public class Mensaje {
    /* Los encabezados y el cuerpo del mensaje. */
    public String Encabezados;
    public String Cuerpo;

    /* Remitente y destinatario. Con esto no es necesario tomarlos de los encabezados. */
    private String De;
    private String Para;

    /* Para hacerlo m�s bonito se define CRLF */
    private static final String CRLF = "\r\n";

    /* Crea el objeto Mensaje al insertar los encabezados requeridos 
       por el RFC 822 (From, To, Date). */
    public Mensaje(String de, String para, String asunto, String texto) 
    {
        /* Quita los espacios en blanco */
        De = de.trim();
        Para = para.trim();
        Encabezados = "From: " + De + CRLF;
        Encabezados += "To: " + Para + CRLF;
        Encabezados += "Subject: " + asunto.trim() + CRLF;

        /* Una aproximaci�n al formato requerido. S�lo GMT. */
        SimpleDateFormat formatoFecha = 
            new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        String fechaString = formatoFecha.format(new Date());
        Encabezados += "Date: " + fechaString + CRLF;
        Cuerpo = texto;
    }

    /* Dos funciones para acceder el remitente y el destinatario. */
    public String consigaDe() {
        return De;
    }

    public String consigaPara() {
        return Para;
    }

    /* Revisa si el mensaje es v�lido. En otras palabra, si el remitente
       y el destinatario tienen s�lo un signo @ */
    public boolean esValido() {
        int arrobaDe = De.indexOf('@');
        int arrobaPara = Para.indexOf('@');

        if(arrobaDe < 1 || (De.length() - arrobaDe) <= 1) {
            System.out.println("La direcci�n del remitente es inv�lida");
            return false;
        }
        if(arrobaPara < 1 || (Para.length() - arrobaPara) <= 1) {
            System.out.println("La direcci�n del destinatario es inv�lida");
            return false;
        }
        if(arrobaDe != De.lastIndexOf('@')) {
            System.out.println("La direcci�n del Remitente es inv�lida");
            return false;
        }
        if(arrobaPara != Para.lastIndexOf('@')) {
            System.out.println("La direcci�n del destinatario es inv�lida");
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