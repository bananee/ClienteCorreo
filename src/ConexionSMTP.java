import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Abre una conexión SMTP a una máquina remota y envía un mensaje de correo.
 *
 */
public class ConexionSMTP {
	/*
    // Socket para el servidor 
    //private Socket socketParaConexion;
    

    // Streams para leer y escribir al socket 
    private BufferedReader desdeElServidor;
    private DataOutputStream haciaElServidor;

    private static final int PUERTO_SMTP = 25;
    private static final String CRLF = "\r\n";

    // ¿Está conectado? Utilizado en close() para determinar que hacer. 
    private boolean estaConectado = false;

    // Crea un objeto ConexionSMTP. Crea el socket y los streams 

      // asociados. Inicializa la conexion SMTP. 
    public ConexionSMTP(Envoltura envoltura) throws IOException {
        // socketParaConexion = 
    	
    	
        //desdeElServidor = 
        //haciaElServidor =   
        
        
        // Lee una línea desde el servidor y revisa que el código de respuesta sea 220.
        //   si no es así, lanza una excepción. 
        // Completar 

        // Negociación SMTP (handshake). Se necesita el nombre de la máquina local.
         //  Envia el comando SMTP apropiado. 
        //String localhost =  ;
        //envieComandoSMTP(  );
        //estaConectado = true;
    }

    // Envía el mensaje. Escribe los comandos SMTP correctos en el orden correcto.
    //   No revisa si se presentan errores, sólo los lanza al invocador. 
    public void enviar(Envoltura envoltura) throws IOException {
        // Completar 
        // Envía todos los comandos necesarios para envíar un mensaje. 
        //   Invoca envieComandoSMTP() para que haga el trabajo sucio. NO atrapa la excepción
        //   lanzada desde envieComandoSMTP(). 
        // Completar 
    }

    // Cierra la conexión. Primero, termina en el nivel SMTP, luego
    //   cierra el socket. 
    public void close() {
        estaConectado = false;
        try {
            envieComandoSMTP(    );
            // socketParaConexion.close();
        } catch (IOException e) {
            System.out.println("No se puede cerrar la conexión: " + e);
            estaConectado = true;
        }
    }

    // Envía un comando SMTP al servidor. Revisa que el código de respuesta
    //   es el que se supone de acuerdo con el RFC 821. 
    private void envieComandoSMTP(String comando, int rc) throws IOException 

{
        // Completar 
        // Escribe un comando al servidor y lee la respuesta desde el servidor. 
        // Completar 

        // Completar 
        // Revisa que el código de respuesta del servidor es la misma del parámetro rc. Si no es así, lanza una excepción. 
        // Completar 
    }

    // analiza sintácticamente la línea que llega del servidor. Retorna el código de la respuesta. 
    private int analiceRespuesta(String respuesta) {
        // Completar 
    }

    // Método destructor. Cierra la conexión si algo sale mal. 
   protected void finalize() throws Throwable {
        if(estaConectado) {
            close();
        }
        super.finalize();
    }*/
}


