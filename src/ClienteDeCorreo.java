import java.io.*;
//import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.activation.CommandMap;
import javax.activation.MailcapCommandMap;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument.Content;

/**
 * Un cliente de correo simple con interface grafica (GUI) para enviar correo.
 */
// edit propio - cambio de superclase Frame a JFrame, permite cerrado  normal de ventana facilmente, con frame no se podia
public class ClienteDeCorreo extends JFrame {
    /* Los elementos para la interface gráfica. */
	private boolean attachFile = false;
	private boolean saveBackup = false;
	private Panel thePanel = new Panel();
    private JButton botonEnviar = new JButton("Enviar");
    private JButton botonBorrar = new JButton("Borrar");
    private JButton botonSalir = new JButton("Salir");
    private JButton botonMailBackup = new JButton("Guardar  Copia");
    private JButton botonAttach = new JButton("Adjuntar archivo");
    private JLabel attachLabel = new JLabel("Ubicacion archivo adjunto ");
    private JTextField attachLocation = new JTextField("", 60);
    private JLabel mailBackupLabel = new JLabel("Ubicacion carpeta para guardar: ");
    private JTextField mailBackup = new JTextField("", 60);
    private JLabel smtpLabel = new JLabel("Smtp Server: ");
    private JTextField smtpHost = new JTextField("", 40);
    private JLabel passLabel = new JLabel("Password:     ");
    private JPasswordField passText = new JPasswordField("", 40); 
    private JLabel deLabel = new JLabel("De:                  ");
    private JTextField deTextField = new JTextField("", 50);
    private JLabel paraLabel = new JLabel("Para:              "); 
    private JTextField paraTextField = new JTextField("", 40);
    private JLabel asuntoLabel = new JLabel("Asunto:          ");
    private JTextField asuntoTextField = new JTextField("", 40);
    private JLabel mensajeLabel = new JLabel("Mensaje:");
    private JTextArea mensajeTextArea = new JTextArea(10, 40);
    
    

    /**
     * Crea una nueva ventana ClienteDeCorreo con los campos para ingresar
     * la información requerida (De, Para, Asunto, y mensaje).
     * 
     * Agregados servidor smtp y password de usuario
     */
    /**
     * 
     */
    /**
     * 
     */
    public ClienteDeCorreo() {
        super("Proyecto RedesUNQ SimpleMailClient");
        
        /* Crea paneles para contener los campos. Para hacerlo más presentable,
           crea un panel extra para contener todos los páneles hijo. */
        JPanel smtp = new JPanel(new BorderLayout());
        JPanel pass = new JPanel(new BorderLayout());
        JPanel dePanel = new JPanel(new BorderLayout());
        JPanel paraPanel = new JPanel(new BorderLayout());
        JPanel asuntoPanel = new JPanel(new BorderLayout());
        JPanel mensajePanel = new JPanel(new BorderLayout());
        JPanel carpetaBackup = new JPanel(new BorderLayout());
        JPanel attachPanel = new JPanel(new BorderLayout()); 
        attachPanel.add(attachLabel, BorderLayout.WEST);
        attachPanel.add(attachLocation, BorderLayout.CENTER);
        carpetaBackup.add(mailBackupLabel, BorderLayout.WEST);
        carpetaBackup.add(mailBackup, BorderLayout.CENTER);
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
        
        JPanel camposPanel = new JPanel(new GridLayout(0, 1));
        camposPanel.add(smtp);
        camposPanel.add(pass);
        camposPanel.add(dePanel);
        camposPanel.add(paraPanel);
        camposPanel.add(asuntoPanel);
        camposPanel.add(carpetaBackup);
        camposPanel.add(attachPanel);
        
        mailBackup.setVisible(false);
		mailBackupLabel.setVisible(false);
		attachLocation.setVisible(false);
		attachLabel.setVisible(false);
		//mailBackupLabel.
        
		
	
		
        /* Crea un panel para los botones y agrega "listeners" a los
           botones. */
        JPanel botonesPanel = new JPanel(new GridLayout(1, 0));
        botonEnviar.addActionListener(new ListenerEnviar());
        botonBorrar.addActionListener(new ListenerBorrar());
        botonSalir.addActionListener(new ListenerSalir());
        botonMailBackup.addActionListener(new ListenerMailBackup());
        botonAttach.addActionListener(new ListenerAttach());
        
        botonesPanel.add(botonEnviar);
        botonesPanel.add(botonBorrar);        
        botonesPanel.add(botonMailBackup);
        botonesPanel.add(botonAttach);
        botonesPanel.add(botonSalir);
        
        
        /* Ensambla, empaca y muestra. */
        
        
        this.thePanel.add(camposPanel, BorderLayout.NORTH);
        this.thePanel.add(mensajePanel, BorderLayout.CENTER);
        this.thePanel.add(botonesPanel, BorderLayout.SOUTH);
        add(camposPanel, BorderLayout.NORTH);
        add(mensajePanel, BorderLayout.CENTER);
        add(botonesPanel, BorderLayout.SOUTH);
        
        Container con = getContentPane();
        con.setBackground(Color.LIGHT_GRAY);
        setSize(500, 500);
        pack();
        show();
    }
    
    public void generarCopiaEn(String asunto, String para, String mail, String carpeta) throws FileNotFoundException{
    	// creamos una copia del asunto del mail y su contenido de texto
    	// y se crea una carpeta con el asunto del mail y dentro
    	// un archivo txt con el texto enviado
    	ManejadorArchivosYCarpetas filemanager = new ManejadorArchivosYCarpetas();
    	
    	String xasunt = asunto;
    	String xmen = "Mail para " + para+ " Campo del mail: " + mail;
    	
    	String stx =  carpeta+"/"+xasunt; // La carpeta donde se guada el mail enviado
    	String stx2 = stx + "/"+ xasunt + ".txt"; // esto corresponde al nombre del archivo que a su vez representa su ubicacion
    	
    	// ejemplo de ruta "/home/mae-ubu1004/CorreosEnviadosPorClienteCorreo/"
    	
    	
    	filemanager.checkFolder(carpeta);
		filemanager.crearCarpeta(stx);
		
    	filemanager.escribir(stx2, xmen);
    }

    
    /*
    public void addButton(){
    	
    	this.add(new JButton(new AbstractAction("Click to add") {
	        @Override
	        public void actionPerformed(ActionEvent e) {

	            SwingUtilities.invokeLater(new Runnable() {
	                @Override
	                public void run() {
	                	//JLabel l = new JLabel("Bla");
	                	//JPanel p = new JPanel();
	                	add(new JLabel("Bla"));
	                    validate();
	                    repaint();
	                }
	            });
	        }
	    }));
    }
    
    
    
    */
    
    
    /*
    static public void build(){
    	ClienteDeCorreo mail = new ClienteDeCorreo();
        mail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }*/

    /* Manejador para el botón Enviar. */
    class ListenerEnviar implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String st;

            /* Primero, revisa que tenga remitente y destinatario. */
            if((deTextField.getText()).equals("")) {
            	 st="¡Necesita remitente!";
                System.out.println(st);                
                JOptionPane.showMessageDialog(null,st);
                return;
            }
            if((paraTextField.getText()).equals("")) {
            	st = "¡Necesita un destinatario!";
                System.out.println(st);
                JOptionPane.showMessageDialog(null,st);
                return;
            }
            if((smtpHost.getText()).equals("")) {
            	st = "¡Necesita un Host!";
                System.out.println(st);
                JOptionPane.showMessageDialog(null,st);
                return;
            }
           

            /* Crea el mensaje */
            
            //\\\\\--->>>ANULADO. SE ELIMINA LA CLASE MENSAJE DEL MEDIO. LA CLASE ENVOLTURA, RENOMBRADA A MAIL REPRESENTARA AL MAIL.
            
            //Mensaje mensajeDeCorreo = new Mensaje( smtpHost.getText(), passText.getText(), deTextField.getText(), paraTextField.getText(), asuntoTextField.getText(),mensajeTextArea.getText());
            Mail m = new Mail(smtpHost.getText(), passText.getText(), deTextField.getText(), paraTextField.getText(), asuntoTextField.getText(),mensajeTextArea.getText(),attachFile,attachLocation.getText());
            /* Revisa que el MAIL sea válido, es decir, que las direcciones del remitente y el destinatario parezcan bien escritas. */
            if(!m.esValido()) {
            	JOptionPane.showMessageDialog(null,"Error en los campos, revise el contenido de los mismos");
                return;
            }
            st = "Enviando correo";
            System.out.println(st);
            JOptionPane.showMessageDialog(null,st);
            /* Crea la envoltura, abre la conexión y trata de enviar el mensaje. */
            //Mail envoltura = new Mail(mensajeDeCorreo);
            //SendMailTLS.send(envoltura);
            
            try {
            	//////
            	//////
            	
            	//Las siguientes lineas representan una de las soluciones posibles para 
            	// lograr resolver la excepcion de unsuportedDataType levantada por el DCH
            	// Hay algun problema extraño cargandose la clase Data content handler
            	// Esta clase es propia del java y no está claro porque el error
            	// La solucion siguiente se anticipa a la creacion del mail y envío
            	// creando una metaconfiguracion de lo que será el mail
            	
            	MailcapCommandMap mc = (MailcapCommandMap) CommandMap.getDefaultCommandMap();
                mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
                mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
                mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
                mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");
                mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
                CommandMap.setDefaultCommandMap(mc);
                //////
                /////
                ///***///
                
                /// Comienza el envío del mail
                
               ////creamos la clase sender
            	
            	// le pasamos el mail y le decimos que lo envie
                SendMailTLS sender = new SendMailTLS();
                
            	if (saveBackup){
            		try{
            			
            			generarCopiaEn(asuntoTextField.getText(), paraTextField.getText(), mensajeTextArea.getText(), mailBackup.getText() );
            			           			
            		     sender.send(m);
            			
            			
            		}catch(FileNotFoundException e){
            			
            			st = "La ubicación de la carpeta no es válida, puede que no exista o no tenga permisos suficientes, el email no fue enviado";
            			System.out.println(st);
            			JOptionPane.showMessageDialog(null,st);
            			return;
            		}
            		System.out.println("Copia Generada en este equipo");
            	}else{
            		System.out.println("Enviando mensaje, no se guardará copia en este equipo");
            		sender.send(m);
            	}
            	
            	st = "¡Mensaje enviado!";
            	System.out.println(st);
            	JOptionPane.showMessageDialog(null,st);
            	
            	//filemanager.escribir(fileName, text)
            }catch (RuntimeException e){
            	System.out.println(" Error de envío, revisar parámentros D:");
            	JOptionPane.showMessageDialog(null,"Error de envío, revisar parámetros y su conexión...");
            	st = e.getMessage();
            	System.out.println(st);
            	JOptionPane.showMessageDialog(null,st);
            
            }
            
            
            //
            //
            //
            //
            //asi cuando nos encontramos los investigamos, o cantamelos que los browseo
            /**try {
                ConexionSmtp connection = new ConexionSmtp(envoltura);
                connection.enviar(envoltura);
                connection.close();
            } catch (IOException error) {
                System.out.println("Envío falló: " + error);
                return;
            }**/
            //System.out.println("¡Mensaje enviado!");
        }
    }

   
    
    

    class ListenerBorrar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	
        	if((deTextField.getText()).equals("123")) {
           	 String st=" AutoCompletar Activado";
               System.out.println(st);
               System.out.println("Completando datos para testear, ingrese pass...");
       		   smtpHost.setText("smtp.gmail.com");
               passText.setText("");
               deTextField.setText("eiroam@gmail.com");
               paraTextField.setText("ma_eir_oa@hotmail.com");
               asuntoTextField.setText("AsuntoTest");
               mensajeTextArea.setText("Campo del mensaje test");
               JOptionPane.showMessageDialog(null,st);
               return;
           }
        	
            System.out.println("Borrando los campos");
            smtpHost.setText("");
            passText.setText("");
            deTextField.setText("");
            paraTextField.setText("");
            asuntoTextField.setText("");
            mensajeTextArea.setText("");
            mailBackup.setText("");
        }
    }
    
    // Boton para guardar copia texto del mail en la computadora
    
    class ListenerMailBackup implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		
    			
    		//add(mailBackupLabel, BorderLayout.WEST);
    		//add(mailBackup, BorderLayout.CENTER);
    		
    		//mailBackupLabel.hide();
    		//addButton();
    		
    		
    		if(saveBackup){
    			mailBackup.setVisible(false);
    			mailBackupLabel.setVisible(false);
    			saveBackup = false;
    			botonMailBackup.setText("Guardar Copia");
    			mailBackup.setText("");
    			mailBackupLabel.removeAll();
    			mailBackup.removeAll();
    			remove(mailBackup);
        		validate();
        		String st = "No se guardará copia del texto del mail";
        		System.out.println(st);
        		JOptionPane.showMessageDialog(null,st);
    		}else{
    			mailBackup.setVisible(true);
    			mailBackupLabel.setVisible(true);
    			saveBackup = true;
    			botonMailBackup.setText("Desactivar Guardar Copia");
    			String st = "Guardado de copia del texto del mail activado, ingrese la ubicación de la carpeta donde quiere que se guarde el mail que enviará";
        		System.out.println(st);
        		JOptionPane.showMessageDialog(null,st);
        		validate();
    		}
    		
    		
    		
    		
    		
    		
    		//thePanel.
    		//thePanel.add(camposPanel, BorderLayout.NORTH);
    		//add(camposPanel, BorderLayout.NORTH);
            //add(mensajePanel, BorderLayout.CENTER);
            //add(botonesPanel, BorderLayout.SOUTH);
            //pack();
            //show();
    		
    	
    	}
    }
    
    
   

   
    class ListenerAttach implements ActionListener{
    	public void actionPerformed(ActionEvent e){
    		
    			
    		
    		
    		
    		if(attachFile){
    			attachLocation.setVisible(false);
    			attachLabel.setVisible(false);
    			attachFile = false;
    			botonAttach.setText("Adjuntar archivo");
    			attachLocation.setText("");
        		validate();
        		String st = "No se adjuntará archivo";
        		System.out.println(st);
        		JOptionPane.showMessageDialog(null,st);
    		}else{
    			attachLocation.setVisible(true);
    			attachLabel.setVisible(true);
    			attachFile = true;
    			botonAttach.setText("No adjuntar archivo");
    			
    			String st = "Se adjuntará un archivo, ingrese la ubicación del archivo quiere que se guarde el mail que enviará";
        		System.out.println(st);
        		JOptionPane.showMessageDialog(null,st);
        		validate();
    		}
    		
    		
    		
    		
    		
    		
    		
    	}
    }
    
    
    
    class ListenerSalir implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    static public void main(String argv[]) {
    	new TheLog("Log del cliente de mail");
        ClienteDeCorreo mail = new ClienteDeCorreo();
        mail.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }
}