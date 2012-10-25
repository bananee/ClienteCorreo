package src;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * Un cliente de correo simple con interface grafica (GUI) para enviar correo.
 */
// edit propio - cambio de superclase Frame a JFrame, permite cerrado  normal de ventana facilmente, con frame no se podia
public class ClienteDeCorreo extends JFrame {
    /* Los elementos para la interface gr√°fica. */
    private Button botonEnviar = new Button("Enviar");
    private Button botonBorrar = new Button("Borrar");
    private Button botonSalir = new Button("Salir");
    private Label smtpLabel = new Label("smtp server: ");
    private TextField smtpHost = new TextField("", 40);
    private Label passLabel = new Label("Password:     ");
    private JPasswordField passText = new JPasswordField("", 40); 
    private Label deLabel = new Label("De:               ");
    private TextField deTextField = new TextField("", 50);
    private Label paraLabel = new Label("Para:            "); 
    private TextField paraTextField = new TextField("", 40);
    private Label asuntoLabel = new Label("Asunto:        ");
    private TextField asuntoTextField = new TextField("", 40);
    private Label mensajeLabel = new Label("Mensaje:");
    private TextArea mensajeTextArea = new TextArea(10, 40);
    

    /**
     * Crea una nueva ventana ClienteDeCorreo con los campos para ingresar
     * la informacion requerida (De, Para, Asunto, y mensaje).
     */
    /**
     * 
     */
    /**
     * 
     */
    public ClienteDeCorreo() {
        super("Java ClienteDeCorreo");
        
        /* Crea paneles para contener los campos. Para hacerlo mas presentable,
           crea un panel extra para contener todos los paneles hijo. */
        Panel smtp = new Panel(new BorderLayout());
        Panel pass = new Panel(new BorderLayout());
        Panel dePanel = new Panel(new BorderLayout());
        Panel paraPanel = new Panel(new BorderLayout());
        Panel asuntoPanel = new Panel(new BorderLayout());
        Panel mensajePanel = new Panel(new BorderLayout());
        smtp.add(smtpLabel, BorderLayout.WEST);
        smtp.add(smtpHost, BorderLayout.CENTER);
        pass.add(passLabel, BorderLayout.WEST);
        pass.add(passText, BorderLayout.CENTER);
        dePanel.add(deLabel, BorderLayout.WEST);
        dePanel.add(deTextField, BorderLayout.CENTER);
        paraPanel.add(paraLabel, BorderLayout.WEST);
        paraPanel.add(paraTextField, BorderLayout.CENTER);
        asuntoPanel.add(asuntoLabel, BorderLayout.WEST);
        asuntoPanel.add(asuntoTextField, BorderLayout.CENTER);
        mensajePanel.add(mensajeLabel, BorderLayout.NORTH);     
        mensajePanel.add(mensajeTextArea, BorderLayout.CENTER);
        Panel camposPanel = new Panel(new GridLayout(0, 1));
        camposPanel.add(smtp);
        camposPanel.add(pass);
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
        ClienteDeCorreo mail = new ClienteDeCorreo();
        mail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
    
    static public void build(){
    	ClienteDeCorreo mail = new ClienteDeCorreo();
        mail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /* Manejador para el boton Enviar. */
    class ListenerEnviar implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String st;

            /* Primero, revisa que tenga remitente y destinatario. */
            if((deTextField.getText()).equals("")) {
            	st="°Necesita remitente!";
                System.out.println(st);                
                JOptionPane.showMessageDialog(null,st);
                return;
            }
            if((paraTextField.getText()).equals("")) {
            	st = "°Necesita un destinatario!";
                System.out.println(st);
                JOptionPane.showMessageDialog(null,st);
                return;
            }
            if((smtpHost.getText()).equals("")) {
            	st = "°Necesita un Host!";
                System.out.println(st);
                JOptionPane.showMessageDialog(null,st);
                return;
            }
           

            /* Crea el mensaje */
            Mensaje mensajeDeCorreo = new Mensaje( smtpHost.getText(), passText.getText(), deTextField.getText(), paraTextField.getText(), asuntoTextField.getText(),mensajeTextArea.getText());

            /* Revisa que el mensaje sea v√°lido, es decir, que las direcciones del remitente y el destinatario parezcan bien escritas. */
            if(!mensajeDeCorreo.esValido()) {
                return;
            }
            st = "Enviando correo";
            System.out.println(st);
            JOptionPane.showMessageDialog(null,st);
            /* Crea la envoltura, abre la conexion y trata de enviar el mensaje. */
            Envoltura envoltura = new Envoltura(mensajeDeCorreo);
            //SendMailTLS.send(envoltura);
            
            try {
            	SendMailTLS.send(envoltura);
            	st = "°Mensaje enviado!";
            	System.out.println(st);
            	JOptionPane.showMessageDialog(null,st);
            }catch (RuntimeException e){
            	System.out.println("Error de envio, revisar paramentros D:");
            	JOptionPane.showMessageDialog(null,"Error de envio, revisar parametros y su conexion...");
            	st = e.getMessage();
            	JOptionPane.showMessageDialog(null,st);
            
            }

            /**try {
                ConexionSmtp connection = new ConexionSmtp(envoltura);
                connection.enviar(envoltura);
                connection.close();
            } catch (IOException error) {
                System.out.println("Envio fallo: " + error);
                return;
            }**/
            //System.out.println("°Mensaje enviado!");
        }
    }

    /* Borra los campos de la interface grafica. */
    class ListenerBorrar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Borrando los campos");
            smtpHost.setText("");
            passText.setText("");
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