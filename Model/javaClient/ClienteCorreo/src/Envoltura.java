import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Envoltura SMTP para un mensaje de correo.
 */
public class Envoltura {
    /* Remitente del mensaje SMTP (en este caso, contenido del header From. */
    public String Remitente;

    /* Destinatario SMTP, o contenido del header To. */
    public String Destinatario;

    /* Host MX objetivo */
    public String HostDestino;
    public InetAddress DireccionDestino;

    /* El mensaje real */
    public Mensaje Mensaje;

    /* Crea la envoltura. */
    public Envoltura(Mensaje mensaje) {
        /* consigue el remitente y el destinatario. */
        Remitente = mensaje.consigaDe();
        Destinatario = mensaje.consigaPara();

        /* Consigue el mensaje. El mensaje debe ser revisado para asegurarnos 
           que no hay puntos solitarios sobre una l�nea. Esto podr�a arruinar
           el env�o del mensaje. */
        Mensaje = revisarMensaje(mensaje);

        /* Busca la parte del nombre del host de la direcci�n del destinatario. Este debe ser el
           nombre del host MX para el dominio del destinatario. */
        int signoArroba = Destinatario.lastIndexOf('@');
        HostDestino = Destinatario.substring(signoArroba + 1);

        /* "Mapea" el nombre de host a la direcci�n IP */
        try {
            DireccionDestino = InetAddress.getByName(HostDestino);
        } catch (UnknownHostException e) {
            System.out.println("Host desconocido: " + HostDestino);
            System.out.println(e);
            return;
        }
        return;
    }

    /* Elimina los puntos que queden s�los en una l�nea duplicando todos los puntos
       al comienzo de una l�nea del mensaje. */
    private Mensaje revisarMensaje(Mensaje mensaje) {
        String cuerpoSinPuntoSolo = "";
        String token;
        StringTokenizer textoParaRevisar = new StringTokenizer(mensaje.Cuerpo, "\n", true);

        while(textoParaRevisar.hasMoreTokens()) {
            token = textoParaRevisar.nextToken();
            if(token.startsWith(".")) {
                token = "." + token;
            }
            cuerpoSinPuntoSolo += token;
        }
        mensaje.Cuerpo = cuerpoSinPuntoSolo;
        return mensaje;
    }

    /* Para imprimir la envoltura. S�lo para depurar. */
    public String toString() {
        String res = "Remitente: " + Remitente + '\n';
        res += "Destinatario: " + Destinatario + '\n';
        res += "Host MX: " + HostDestino + ", direcci�n IP: " + DireccionDestino + 

'\n';
        res += "Mensaje:" + '\n';
        res += Mensaje.toString();
        
        return res;
    }
}