import java.io.*;

public class  ServerAddress
{
	 ServerAddress()
	 {

	 }

	 public static String getAddress()
	{
		 String ipAdd = null;
		 try
		 {
			 FileInputStream fis = new FileInputStream("ip.txt");
			
			 byte[] fileContent = new byte[ fis.available()];

			 fis.read( fileContent );

			 ipAdd = new String( fileContent );
		 }

		 catch(FileNotFoundException fne)
		 {
			 fne.printStackTrace();
	     }
		 catch (IOException ioe)
		 {
			 ioe.printStackTrace();
		 }
		
		return ipAdd;
	}

}
