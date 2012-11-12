import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Envoltura SMTP para un mensaje de correo.
 */
public class Mail {
	
	public String HostPropio;
    /* Remitente del mensaje SMTP (en este caso, contenido del header From. */
    public String Remitente;

    /* Destinatario SMTP, o contenido del header To. */
    public String Destinatario;
    
    public String Asunto;
    
    /* Host MX objetivo */
    public String HostDestino;
    public InetAddress DireccionDestino;
    
    public boolean leido = false;
    public boolean attach;
    public String theAttach;

    /* El mensaje real */
    public String Texto;
    
    //LA PASS
    
    public String Pass;
    
    /* Crea la envoltura. */
   public Mail(String smtp, String passss, String de, String para, String asunto, String texto, boolean at, String file) {
        /* consigue el remitente y el destinatario. */
        Remitente = de;
        Destinatario = para;
        HostPropio = smtp;
        Pass = passss;
        Asunto = asunto;
        Texto = texto;
        attach = at;
        theAttach = file;
        
        /* Consigue el mensaje. El mensaje debe ser revisado para asegurarnos 
           que no hay puntos solitarios sobre una línea. Esto podría arruinar
           el envío del mensaje. */
        Texto = revisarMail(Texto);

        /* Busca la parte del nombre del host de la dirección del destinatario. Este debe ser el
           nombre del host MX para el dominio del destinatario. */
        int signoArroba = Destinatario.lastIndexOf('@');
        HostDestino = Destinatario.substring(signoArroba + 1);

        /* "Mapea" el nombre de host a la dirección IP */
        try {
            DireccionDestino = InetAddress.getByName(HostDestino);
        } catch (UnknownHostException e) {
            System.out.println("Host desconocido: " + HostDestino);
            System.out.println(e);
            return;
        }
        return;
    }
   	
   public Mail(String smtp, String passss, String de, String para, String asunto, String texto) {
       /* consigue el remitente y el destinatario. */
       Remitente = de;
       Destinatario = para;
       HostPropio = smtp;
       Pass = passss;
       Asunto = asunto;
       Texto = texto;
       

       
       /* Consigue el mensaje. El mensaje debe ser revisado para asegurarnos 
          que no hay puntos solitarios sobre una línea. Esto podría arruinar
          el envío del mensaje. */
       Texto = revisarMail(Texto);

       /* Busca la parte del nombre del host de la dirección del destinatario. Este debe ser el
          nombre del host MX para el dominio del destinatario. */
       int signoArroba = Destinatario.lastIndexOf('@');
       HostDestino = Destinatario.substring(signoArroba + 1);

       /* "Mapea" el nombre de host a la dirección IP */
       try {
           DireccionDestino = InetAddress.getByName(HostDestino);
       } catch (UnknownHostException e) {
           System.out.println("Host desconocido: " + HostDestino);
           System.out.println(e);
           return;
       }
       return;
   }
    /* Elimina los puntos que queden sólos en una línea duplicando todos los puntos
       al comienzo de una línea del mensaje. */
   
   public boolean esValido() {
       int arrobaDe = Remitente.indexOf('@');
       int arrobaPara = Destinatario.indexOf('@');

       if(arrobaDe < 1 || (Remitente.length() - arrobaDe) <= 1) {
           System.out.println("La dirección del remitente es inválida");
           return false;
       }
       if(arrobaPara < 1 || (Destinatario.length() - arrobaPara) <= 1) {
           System.out.println("La dirección del destinatario es inválida");
           return false;
       }
       if(arrobaDe != Remitente.lastIndexOf('@')) {
           System.out.println("La dirección del Remitente es inválida");
           return false;
       }
       if(arrobaPara != Destinatario.lastIndexOf('@')) {
           System.out.println("La dirección del destinatario es inválida");
           return false;
       }       
       return true;
   }
    private String revisarMail(String ml) {
        String cuerpoSinPuntoSolo = "";
        String token;
        StringTokenizer textoParaRevisar = new StringTokenizer(Texto, "\n", true);

        while(textoParaRevisar.hasMoreTokens()) {
            token = textoParaRevisar.nextToken();
            if(token.startsWith(".")) {
                token = "." + token;
            }
            cuerpoSinPuntoSolo += token;
        }
        Texto = cuerpoSinPuntoSolo;
        return ml;
    }

    /* Para imprimir la envoltura. Sólo para depurar. */
    public String toString() {
        String res = "Remitente: " + Remitente + '\n';
        res += "Destinatario: " + Destinatario + '\n';
        res += "Host MX: " + HostDestino + ", dirección IP: " + DireccionDestino + 

'\n';
        res += "Mensaje:" + '\n';
        res += Texto.toString();
        
        return res;
    }
}