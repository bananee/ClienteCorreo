import java.io.*;

import java.util.Properties;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class ManejadorArchivosYCarpetas {
	
	public void checkFolder(String pathname) throws FileNotFoundException{
		File dir = new File(pathname); 
		if (!(dir.isDirectory()) || pathname == ""){
			throw new FileNotFoundException("No existe la carpeta");
		}
	}
	public void crearCarpeta (String pathname) throws FileNotFoundException{
		File dir = new File(pathname); 
		dir.mkdir();
		
		
	}
	public void escribir(String fileName, String text){
		File f;
		f = new File(fileName);
		

		//Escritura
		try{
		FileWriter w = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);	
		wr.write(text);//escribimos en el archivo 
		//wr.append(" - y aqui continua"); //concatenamos en el archivo sin borrar lo existente
		              //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
		              //de no hacerlo no se escribirá nada en el archivo
		wr.close();
		bw.close();
		
		}catch(IOException e){
			System.out.println("eRROR en la ruta!");
		};
	}
	
	public void escribirYCrearCarpeta(String fileName, String pathName, String text) throws FileNotFoundException{
		File f;
		crearCarpeta(pathName);
		f = new File(fileName);
		

		//Escritura
		try{
	    
		FileWriter w = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(w);
		PrintWriter wr = new PrintWriter(bw);	
		wr.write(text);//escribimos en el archivo 
		//wr.append(" - y aqui continua"); //concatenamos en el archivo sin borrar lo existente
		              //ahora cerramos los flujos de canales de datos, al cerrarlos el archivo quedará guardado con información escrita
		              //de no hacerlo no se escribirá nada en el archivo
		wr.close();
		bw.close();
		//crearCarpeta(pathName);
		}catch(IOException e){
			
		};
	}
	
	public static void renameFile()
    {	
    	try{
 
    	   File afile =new File("C:\\folderA\\Afile.txt");
 
    	   if(afile.renameTo(new File("C:\\folderB\\" + afile.getName()))){
    		System.out.println("File is moved successful!");
    	   }else{
    		System.out.println("File is failed to move!");
    	   }
 
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
	
	public static void moveFile()
    {	
 
    InputStream inStream = null;
	OutputStream outStream = null;
 
    	try{
 
    	    File afile =new File("C:\\folderA\\Afile.txt");
    	    File bfile =new File("C:\\folderB\\Afile.txt");
 
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
 
    	    byte[] buffer = new byte[1024];
 
    	    int length; 
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
 
    	    	outStream.write(buffer, 0, length);
 
    	    }
 
    	    inStream.close();
    	    outStream.close();
 
    	    //delete the original file
    	    afile.delete();
 
    	    System.out.println("File is copied successful!");
 
    	}catch(IOException e){
    	    e.printStackTrace();
    	}
    }
	
	 public static void main3() 
	 {
	  
	   // Directory path here
		 File folder = new File("S:\\BASE CLIENTES\\Petrobras\\Campa�a de Medicion\\Mediciones\\A enviar");
		    File[] listOfFiles = folder.listFiles();
		    System.out.println("A continuacion se listan las carpetas en las cuales se crearan las carpetas solicitadas");
		    for (int i = 0; i < listOfFiles.length; i++) {
		      if (listOfFiles[i].isFile()) {
		        System.out.println("File " + listOfFiles[i].getPath());
		      } else if (listOfFiles[i].isDirectory()) {
		        System.out.println( "Carpeta" + listOfFiles[i].getPath());
		        
		      }
		    }for (int i = 0; i < listOfFiles.length; i++){
		    	main2(listOfFiles[i]);
		    }
	 }
	 
	
	    public static void main2(File fil)
	    {	
		File file = fil;
		/*if(file.mkdir()){
			System.out.println("Directory is created!");
		}else{
			System.out.println("Failed to create directory!");
		}*/
	 
		File files1 = new File(file+"\\Registros");
		File files2 = new File(file+"\\Lecturas");
		if(files1.mkdirs()&files2.mkdirs()){
			System.out.println("Multiple directories are created!");
		}else{
			System.out.println("Failed to create multiple directories! "+"in "+"directory "+file);
			
		}
	 
	    }
	
	public static void main(String[] args) throws FileNotFoundException {
		ManejadorArchivosYCarpetas fx = new ManejadorArchivosYCarpetas();
		File carpeta = new File("");
		carpeta.isDirectory();
		fx.checkFolder("");
		//fx.crearCarpeta();
		//fx.crearCarpeta("/home/mae-ubu1004/teeeeeeest");
		//fx.escribir("/home/mae-ubu1004/teeeeeeest/textoDePrueba.txt", "lalalalal");
	}
}

