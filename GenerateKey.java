import javax.crypto.*;
import java.sql.*;

public class  GenerateKey
{	
		String[] secretKey;

		Connection con;

		Statement st;

		public GenerateKey()
		
		{

			try
			
			{

			  Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			  con=DriverManager.getConnection("jdbc:odbc:key");

			   st=con.createStatement();
				
			}
			catch (Exception c)
			{
				c.printStackTrace();
			}		

		}
	
		public String groupKey()
		{
			
			try
			{
				KeyGenerator keyGen = KeyGenerator.getInstance("AES");
				
				SecretKey key = keyGen.generateKey();
			
				String sec = key.toString();

				 secretKey = sec.split("@");

				System.out.println("The Secret Key is"+secretKey[1]);				

				int del=st.executeUpdate("delete from keyinfo");

				int registerkey=st.executeUpdate("insert into keyinfo values ('"+secretKey[1]+"')");

			    			
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
			return secretKey[1];
		}
	
}
