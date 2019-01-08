import  java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
class KGC 
{
	Socket soc;
	ServerSocket ss;
	DataInputStream dis;
	DataOutputStream dos;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	Connection con;
	Statement st;
	ResultSet rs;
	String ip="",pass="",user="";
	String content,keyinfo;

	Vector nodena=new Vector();
	//Vector nodeList=new Vector();
	Vector<String> ipAddress=new Vector<String>();
	Vector portNum=new Vector();


	Vector v=new Vector();
	KGC()
		{	
		try
		{
		    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

			con=DriverManager.getConnection("jdbc:odbc:key");

			st=con.createStatement();

		   // int del=st.executeUpdate("delete from tempnodeinfo");

	    	ss=new ServerSocket(7373);

			System.out.println("KGC Listening.....");

			while(true)
			{

				soc=ss.accept();
		   
			   oos=new ObjectOutputStream(soc.getOutputStream());

		       ois=new ObjectInputStream(soc.getInputStream());

		       String msg = (String)ois.readObject();

			   System.out.println("NodeForm msg is :" +msg);



			  	 if(msg.equals("logincheck"))

				{
					ip="";
					pass="";
					user="";
					System.out.println("===Serverf Respond "+msg);

					user=(String)ois.readObject();					

					pass=(String)ois.readObject();
					
					ip=(String)ois.readObject();
					
					String nodeDetails = "";

					rs=st.executeQuery("select NodeName,IpAddress,PortNumber from nodeinfo where NodeName='"+user+"' and Password='"+pass+"'");

				   if(rs.next())

					{
						
						nodeDetails += rs.getString(1)+"#";

						nodeDetails += ip+"#";

						nodeDetails += rs.getString(3)+"#";
						
						st.executeUpdate("update nodeinfo set IpAddress='"+ip+"' where NodeName='"+user+"'");
						
						oos.writeObject("success");

						oos.writeObject( nodeDetails );					
						
						
					}					

						else

						{
							
							oos.writeObject("failed");							

						}					
				}


			 else if( msg.equals("getOthers")){
				
				Vector otherNodes = new Vector();

				  String nodeName = (String)ois.readObject();

				  System.out.println("===>"+nodeName);

				  rs = st.executeQuery("select NodeName from nodeinfo where NodeName != '"+nodeName+"'");

				  while( rs.next()){

					  otherNodes.addElement(rs.getString(1));

					   System.out.println("===>"+otherNodes);
						  
				  }				  
	
				  oos.writeObject( otherNodes );

			  }

			 else if( msg.equals("forsessionkey")){

				 String nodeName = (String)ois.readObject();

				Object[] selectedNodes = (Object[])ois.readObject();

				Vector nodeList = new Vector();

				nodeList.add(nodeName);

				for(int i=0;i<selectedNodes.length;i++){

					nodeList.add(selectedNodes[i]);					

				}

				String[] keys = new String[ selectedNodes.length ];

				String inputForHash = "";

				for( int i = 0; i < selectedNodes.length; i++ ){

					rs = st.executeQuery("select keyinfo from keyinfo2 where nodename = '"+selectedNodes[i].toString()+"'");

					if( rs.next()){

						keys[i] = rs.getString(1);
						
					}

					}

					Random random = new Random();

					int rNum = random.nextInt( keys.length );

					String sessionKey = keys[rNum];
					
					System.out.println("The Session Key "+sessionKey);

					inputForHash = nodeName;

					for( Object s : selectedNodes ){

						inputForHash += (String)s;
					}

					System.out.println( inputForHash );

					st.executeUpdate("delete from hashinfo");

					int insert=st.executeUpdate("insert into hashinfo values('"+inputForHash+"','"+sessionKey+"')");

					long hashValue = HashAlgorithm.cbuHash( inputForHash );

					System.out.println("HASHHHHHHHHHHHHH"+ hashValue );

					String encryptedSessionKey = FileProtection.encrypt( sessionKey,String.valueOf( hashValue ));

					System.out.println("The value is"+encryptedSessionKey );

					ArrayList nodeInfo = new ArrayList();

					for(int j=0;j<nodeList.size();j++)
					 {

					ResultSet rs=st.executeQuery("select * from nodeinfo where NodeName='"+nodeList.get(j).toString()+"'");

					while(rs.next()){
			
						nodeInfo.add( rs.getString(2));

						nodeInfo.add( rs.getString(3));
									
					}
				 }
				 System.out.println(nodeInfo);

				 BroadCastKey.secureChannel( nodeInfo,encryptedSessionKey,sessionKey);
			  }

			  else if(msg.equals("retrievSession")){
				 	  
				  String nodeName=(String)ois.readObject();

				   String nodeN=null;

				   String sessionKeyIs="";

				   rs=st.executeQuery("select * from hashinfo");

				  if(rs.next()){
					nodeN=rs.getString(1);

				  }

					if((nodeN.contains(nodeName))){

						

						oos.writeObject("Authorised");

						oos.writeObject(nodeN);
					}
					else{
						System.out.println("Not a Authorised User");

						oos.writeObject("NotAuthorised");
					}

					  
				  }

				  else if( msg.equals("getnodeinfo")){

					  Object[] nodeList = (Object[])ois.readObject();

					  System.out.println(""+nodeList);

					  ArrayList nodeInfo = new ArrayList();

					  for( int i =0; i < nodeList.length; i++ ){

						  rs = st.executeQuery("select * from NodeInfo where NodeName = '"+nodeList[i].toString()+"'");

						  while( rs.next() ){

							  nodeInfo.add( rs.getString( 2 ));

							  nodeInfo.add( rs.getString( 3 ));
						  }
					  }

					  oos.writeObject( nodeInfo );
				  }

				 else if(msg.equals("newregister"))
				{
					String nodename=(String)ois.readObject();

					String ip=(String)ois.readObject();					

					String port=(String)ois.readObject();
				
					String pass=(String)ois.readObject();
					
					System.out.println("NAME" +nodename+ "ip" +ip);

					int reg=st.executeUpdate("insert into nodeinfo values('"+nodename+"','"+ip+"','"+port+"','"+pass+"')");								

					GenerateKey gk=new GenerateKey();

				    gk.groupKey();	

					 rs=st.executeQuery("select * from keyinfo");

						if(rs.next())

						{
							keyinfo=rs.getString(1);

							System.out.println("Key info from DB  ....."+keyinfo);

							int t=st.executeUpdate("insert into keyinfo2 values('"+nodename+"','"+keyinfo+"')");
							
						}						

						oos.writeObject("registered");		

				        oos.writeObject(keyinfo);
					
				}			
			
			 }		
	      }
			 
	catch (Exception c)
	{
		c.printStackTrace();
	}
	}

	public static void main(String[] args) throws Exception
	{
		new KGC();
	}
}
