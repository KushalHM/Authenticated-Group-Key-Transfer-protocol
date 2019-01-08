import java.util.*;
import java.io.*;
import java.net.*;

class  BroadCastKey
{
		
		public static void secureChannel( ArrayList nodeInfo,String sessionKeyIs ,String sk){
			
			try{
			
			for( int i = 0; i < nodeInfo.size(); i = i+2 ){

			Socket soc = new Socket( nodeInfo.get(i).toString(),Integer.parseInt((String)nodeInfo.get(i+1)));

			ObjectOutputStream oos = new ObjectOutputStream( soc.getOutputStream());

			oos.writeObject("sessionKey");

			oos.writeObject( sessionKeyIs );
			if(i==0)
			{
				oos.writeObject( sk.trim() );
			}
			else
			{
				oos.writeObject("Request for Session Key");
			}
			}
			}

			catch(Exception e){

				e.printStackTrace();
			}
		}


}
