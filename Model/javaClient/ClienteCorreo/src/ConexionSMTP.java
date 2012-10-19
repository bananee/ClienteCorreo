import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Abre una conexi�n SMTP a una m�quina remota y env�a un mensaje de correo.
 *
 */
public class ConexionSMTP {
    /* Socket para el servidor */
    private Socket socketParaConexion;

    /* Streams para leer y escribir al socket */
    private BufferedReader desdeElServidor;
    private DataOutputStream haciaElServidor;

    private static final int PUERTO_SMTP = 25;
    private static final String CRLF = "\r\n";

    /* �Est� conectado? Utilizado en close() para determinar que hacer. */
    private boolean estaConectado = false;

    /* Crea un objeto ConexionSMTP. Crea el socket y los streams 

       asociados. Inicializa la conexion SMTP. */
    public ConexionSMTP(Envoltura envoltura) throws IOException {
        // socketParaConexion = /* Completar */;
        desdeElServidor = /* Completar */;
        haciaElServidor =   /* Completar */;
        
        /* Completar */
        /* Lee una l�nea desde el servidor y revisa que el c�digo de respuesta sea 220.
           si no es as�, lanza una excepci�n. */
        /* Completar */

        /* Negociaci�n SMTP (handshake). Se necesita el nombre de la m�quina local.
           Envia el comando SMTP apropiado. */
        String localhost = /* Completar */;
        envieComandoSmtp( /* Completar */ );
        estaConectado = true;
    }

    /* Env�a el mensaje. Escribe los comandos SMTP correctos en el orden correcto.
       No revisa si se presentan errores, s�lo los lanza al invocador. */
    public void enviar(Envoltura envoltura) throws IOException {
        /* Completar */
        /* Env�a todos los comandos necesarios para env�ar un mensaje. 
           Invoca envieComandoSmtp() para que haga el trabajo sucio. NO atrapa la excepci�n
           lanzada desde envieComandoSmtp(). */
        /* Completar */
    }

    /* Cierra la conexi�n. Primero, termina en el nivel SMTP, luego
       cierra el socket. */
    public void close() {
        estaConectado = false;
        try {
            envieComandoSmtp( /* Completar */ );
            // socketParaConexion.close();
        } catch (IOException e) {
            System.out.println("No se puede cerrar la conexi�n: " + e);
            estaConectado = true;
        }
    }

    /* Env�a un comando SMTP al servidor. Revisa que el c�digo de respuesta
       es el que se supone de acuerdo con el RFC 821. */
    private void envieComandoSmtp(String comando, int rc) throws IOException 

{
        /* Completar */
        /* Escribe un comando al servidor y lee la respuesta desde el servidor. */
        /* Completar */

        /* Completar */
        /* Revisa que el c�digo de respuesta del servidor es la misma del par�metro rc. Si no es as�, lanza una excepci�n. */
        /* Completar */
    }

    /* analiza sint�cticamente la l�nea que llega del servidor. Retorna el c�digo de la respuesta. */
    private int analiceRespuesta(String respuesta) {
        /* Completar */
    }

    /* M�todo destructor. Cierra la conexi�n si algo sale mal. */
    protected void finalize() throws Throwable {
        if(estaConectado) {
            close();
        }
        super.finalize();
    }
}