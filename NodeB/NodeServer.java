import java.util.*;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

class  NodeServer extends TimerTask
{
	
	String nodeName;

	String ipAdd;

	int portNum;

	Socket soc;

	ServerSocket serObj;

	ObjectInputStream ois;

	ObjectOutputStream oos;

	FileOutputStream fos,fos1;

	

	NodeServer(){
	}

	NodeServer( String nodeName,String ipAdd,int portNum ){

		this.nodeName = nodeName;

		this.ipAdd = ipAdd;

		this.portNum = portNum;
	}


	public void run(){

	try{

		serObj = new ServerSocket( portNum );

		System.out.println("Server Listening");

		while( true ){

			soc = serObj.accept();

			oos = new ObjectOutputStream( soc.getOutputStream());

			ois = new ObjectInputStream( soc.getInputStream());	

			String inputMsg = (String)ois.readObject();

			if( inputMsg.equals("sessionKey")){

				String sessionKey = (String)ois.readObject();
				
				String sk = (String)ois.readObject();

				System.out.println("The session Key "+sessionKey);

				fos = new FileOutputStream("enckey.txt");

				fos.write( sessionKey.getBytes());

				fos.close();
				
				
				fos1 = new FileOutputStream("session.txt");

				fos1.write( sk.getBytes());

				fos1.close();
			}

			else if( inputMsg.equals("inputdata")){
				
				String nme=(String)ois.readObject();

				String encryptedFile = (String)ois.readObject();

				fos = new FileOutputStream("encContent.txt");

				fos.write( encryptedFile.getBytes());

				fos.close();
				JOptionPane.showMessageDialog(null,"Message Received from :"+nme);
			}
			
			}

	}
	catch(Exception e){

		e.printStackTrace();
	}

	}
	
	
	public static void main(String[] args) 
	{
		
	}
}
