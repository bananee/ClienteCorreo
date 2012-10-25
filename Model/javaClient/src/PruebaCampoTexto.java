package src;

// Demostracion de la clase JTextField.
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class PruebaCampoTexto extends JFrame
{
   private JTextField campoTexto1, campoTexto2, campoTexto3;
   private JPasswordField campoContrasenia;
 
   // configurar GUI
   public PruebaCampoTexto()
   {
      super( "Prueba de JTextField y JPasswordField" );
 
      Container contenedor = getContentPane();
      contenedor.setLayout( new FlowLayout() );
 
      // crear campo de texto con tamaño predeterminado
      campoTexto1 = new JTextField( 10 );
      contenedor.add( campoTexto1 );
 
      // crear campo de texto con texto predeterminado
      campoTexto2 = new JTextField( "Escriba el texto aqui" );
      contenedor.add( campoTexto2 );
 
      // crear campo de texto con texto predeterminado,
      // 20 elementos visibles y sin manejador de eventos
      campoTexto3 = new JTextField( "Campo de texto no editable", 20 );
      campoTexto3.setEditable( false );
      campoTexto3.setToolTipText("Esta mierda no se edita");
 
      contenedor.add( campoTexto3 );
 
      // crear campo de contraseña con texto predeterminado
      campoContrasenia = new JPasswordField( "Texto oculto" );
      contenedor.add( campoContrasenia );
 
      // registrar manejadores de eventos
      ManejadorCampoTexto manejador = new ManejadorCampoTexto();
      campoTexto1.addActionListener( manejador );
      campoTexto2.addActionListener( manejador );
      campoTexto3.addActionListener( manejador );
      campoContrasenia.addActionListener( manejador );
 
      setSize( 325, 100 );
      setVisible( true );
 
   } // fin del constructor de PruebaCampoTexto
 
   public static void main( String args[] )
   { 
      JFrame.setDefaultLookAndFeelDecorated(true);
      PruebaCampoTexto aplicacion = new PruebaCampoTexto();
      aplicacion.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
   }
 
   // clase interna privada para el manejo de eventos
   private class ManejadorCampoTexto implements ActionListener
   {
      // procesar eventos de campo de texto
      public void actionPerformed( ActionEvent evento )
      {
         String cadena = "";
 
         // el usuario oprimio Intro en objeto JTextField campoTexto1
         if ( evento.getSource() == campoTexto1 )
            cadena = "campoTexto1: " + evento.getActionCommand();
 
         // el usuario oprimio Intro en objeto JTextField campoTexto2
         else if ( evento.getSource() == campoTexto2 )
            cadena = "campoTexto2: " + evento.getActionCommand();
 
         // el usuario oprimio Intro en objeto JTextField campoTexto3
         else if ( evento.getSource() == campoTexto3 )
            cadena = "campoTexto3: " + evento.getActionCommand();
 
         // el usuario oprimio Intro en objeto JTextField campoContrasenia
         else if ( evento.getSource() == campoContrasenia ) {
            cadena = "campoContrasenia: " + 
               new String( campoContrasenia.getPassword() );
         }
 
         JOptionPane.showMessageDialog( null, cadena,"Resultados",JOptionPane.CANCEL_OPTION);
 
      } // fin del metodo actionPerformed
 
   } // fin de la clase interna privada ManejadorCampoTexto
 
} // fin de la clase PruebaCampoTexto

