import java.io.File;
import javax.swing.JOptionPane;

public class SysMan {

    public String[] analyzePath( String path ){
	      

	   	  int count = 0;
	   	  String[] shutCompilerUp = new String[1];
	   	  
	      File name = new File( path );

	         if ( name.isDirectory() ){
	            String directory[] = name.list();
	  	   	    String[] contents = new String[directory.length];
	            
	            for ( String directoryName : directory ){
	            		contents[count] = directoryName;
	            		count++;
	            }
	            return contents;
	         } 
	         
	      else{
	    	 JOptionPane.showMessageDialog(null, path,"ERROR: Path Does Not Exist!", JOptionPane.ERROR_MESSAGE);
	    	 System.exit(0);
	         return shutCompilerUp;
	      } 
	   } 
}
