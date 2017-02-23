package view;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Util {

	public static long copy (Reader input, Writer output) throws IOException {

	    char[] buffer = new char[8192];
	    long count = 0;
	    int n;
	    while ((n = input.read( buffer )) != -1) {
	        output.write( buffer, 0, n );
	        count += n;
	    }
	    return count;
	}
	public static void infoBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }
	public static File setUrlToFile(String url,int version){
		
		File file01=new File("version"+ version+".html");
		String filename="version"+ version+".html";
		
		file01=getUrlContents(url,filename);
		
/*		try{
		// TODO Auto-generated method stub
		 URL oracle = new URL(url);
	     BufferedReader in = new BufferedReader(
	     new InputStreamReader(new ByteArrayInputStream(getUrlContents(url).getBytes())));
	     
	     try (PrintStream out = new PrintStream(new FileOutputStream(file01))) {
	    	    out.print(getUrlContents(url));
	    	    System.out.println("done");
	    	}
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
	     
	     System.out.println(getUrlContents(url));
	     Util.copy(in , new FileWriter(file01));
	     

		}
		catch(Exception b)
		{
			b.printStackTrace();
		}

*/
		return file01;
	}
	 private static File getUrlContents(String theUrl,String filename)
	  {
	    StringBuilder content = new StringBuilder();
	    File file=new File(filename);

	    // many of these calls can throw exceptions, so i've just
	    // wrapped them all in one try/catch statement.
	    try
	    {
	      // create a url object
	      URL url = new URL(theUrl);

	      // create a urlconnection object
	      URLConnection urlConnection = url.openConnection();

	      // wrap the urlconnection in a bufferedreader
	      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	      PrintWriter writer = new PrintWriter(file);
//	      copy(bufferedReader, new FileWriter(file));
	      String line;
	      
	      writer.flush();
	      // read from the urlconnection via the bufferedreader
	      while ((line = bufferedReader.readLine()) != null)
	      {
	    	  writer.println(line);
	        content.append(line + "\n");
	        
	      }
	      writer.close();
	      bufferedReader.close();
	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
	    return file;
	  }
}
