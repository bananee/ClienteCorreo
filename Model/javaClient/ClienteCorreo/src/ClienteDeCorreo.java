import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Un cliente de correo simple con interface grafica (GUI) para enviar correo.
 */
public class ClienteDeCorreo extends Frame {
    /* Los elementos para la interface gr�fica. */
    private Button botonEnviar = new Button("Env�ar");
    private Button botonBorrar = new Button("Borrar");
    private Button botonSalir = new Button("Salir");
    private Label deLabel = new Label("De:       ");
    private TextField deTextField = new TextField("", 40);
    private Label paraLabel = new Label("Para:    "); 
    private TextField paraTextField = new TextField("", 40);
    private Label asuntoLabel = new Label("Asunto:");
    private TextField asuntoTextField = new TextField("", 40);
    private Label mensajeLabel = new Label("Mensaje:");
    private TextArea mensajeTextArea = new TextArea(10, 40);

    /**
     * Crea una nueva ventana ClienteDeCorreo con los campos para ingresar
     * la informaci�n requerida (De, Para, Asunto, y mensaje).
     */
    public ClienteDeCorreo() {
        super("Java ClienteDeCorreo");
        
        /* Crea paneles para contener los campos. Para hacerlo m�s presentable,
           crea un panel extra para contener todos los p�neles hijo. */
        Panel dePanel = new Panel(new BorderLayout());
        Panel paraPanel = new Panel(new BorderLayout());
        Panel asuntoPanel = new Panel(new BorderLayout());
        Panel mensajePanel = new Panel(new BorderLayout());
        dePanel.add(deLabel, BorderLayout.WEST);
        dePanel.add(deTextField, BorderLayout.CENTER);
        paraPanel.add(paraLabel, BorderLayout.WEST);
        paraPanel.add(paraTextField, BorderLayout.CENTER);
        asuntoPanel.add(asuntoLabel, BorderLayout.WEST);
        asuntoPanel.add(asuntoTextField, BorderLayout.CENTER);
        mensajePanel.add(mensajeLabel, BorderLayout.NORTH);     
        mensajePanel.add(mensajeTextArea, BorderLayout.CENTER);
        Panel camposPanel = new Panel(new GridLayout(0, 1));
        camposPanel.add(dePanel);
        camposPanel.add(paraPanel);
        camposPanel.add(asuntoPanel);

        /* Crea un panel para los botones y agrega "listeners" a los
           botones. */
        Panel botonesPanel = new Panel(new GridLayout(1, 0));
        botonEnviar.addActionListener(new ListenerEnviar());
        botonBorrar.addActionListener(new ListenerBorrar());
        botonSalir.addActionListener(new ListenerSalir());
        botonesPanel.add(botonEnviar);
        botonesPanel.add(botonBorrar);
        botonesPanel.add(botonSalir);
        
        /* Ensambla, empaca y muestra. */
        add(camposPanel, BorderLayout.NORTH);
        add(mensajePanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        pack();
        show();
    }

    static public void main(String argv[]) {
        new ClienteDeCorreo();
    }

    /* Manejador para el bot�n Enviar. */
    class ListenerEnviar implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            System.out.println("Enviando correo");

            /* Primero, revisa que tenga remitente y destinatario. */
            if((deTextField.getText()).equals("")) {
                System.out.println("�Necesita remitente!");
                return;
            }
            if((paraTextField.getText()).equals("")) {
                System.out.println("�Necesita un destinatario!");
                return;
            }

            /* Crea el mensaje */
            Mensaje mensajeDeCorreo = new Mensaje(deTextField.getText(), 
                                              paraTextField.getText(), 
                                              asuntoTextField.getText(), 
                                              mensajeTextArea.getText());

            /* Revisa que el mensaje sea v�lido, es decir, que las direcciones del remitente y el destinatario parezcan bien escritas. */
            if(!mensajeDeCorreo.esValido()) {
                return;
            }

            /* Crea la envoltura, abre la conexi�n y trata de enviar el mensaje. */
            Envoltura envoltura = new Envoltura(mensajeDeCorreo);
            try {
                ConexionSMTP connection = new ConexionSMTP(envoltura);
                connection.enviar(envoltura);
                connection.close();
            } catch (IOException error) {
                System.out.println("Env�o fall�: " + error);
                return;
            }
            System.out.println("�Mensaje enviado!");
        }
    }

    /* Borra los campos de la interface gr�fica. */
    class ListenerBorrar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Borrando los campos");
            deTextField.setText("");
            paraTextField.setText("");
            asuntoTextField.setText("");
            mensajeTextArea.setText("");
        }
    }

    /* Salir. */
    class ListenerSalir implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
}