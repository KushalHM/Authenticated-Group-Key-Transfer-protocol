/****************************************************************/
/*                      NodeForm	                            */
/*                                                              */
/****************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.net.*;
/**
 * Summary description for NodeForm
 *
 */
public class NodeFormE extends JFrame
{
	// Variables declaration
	private JTabbedPane jTabbedPane1;
	private JPanel contentPane;
	//-----
	private JLabel jLNodeList;
	private JComboBox jCBNodeList;
	private JTextArea jTAContent;
	private JButton jBbrowse;
	private JButton jBsession;
	private JButton jBTransmission;
	private JButton jBClear;
	private JButton jBExit;
	private JTextField jTFPath;
	private JLabel jLPath;
	private JScrollPane jScrollPane1;
	private JPanel jPanel1;
	private JList jList1; 
	private JScrollPane jScrollPane3;
 	
	
	//-----
	private JTextArea jTARFeceiveContent;
	private JButton jDecrypt;
	private JButton jBClearde;
	private JScrollPane jScrollPane2;
	private JPanel jPanel2;
	//-----
	private JPanel jPanel3;

	String nodeN;
	String ipAdd;
	int portNum;
	String content;
	Connection con;
	Statement st;
	ResultSet rs;

	Object[] desti= {"Select Node"};
	Socket soc;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	ObjectOutputStream oos2;
	DataOutputStream dos;
	DataInputStream dis;
	Object ostore;
	Object o;
	Vector x;
	Object[] nodeList;
	int i;
	//Vector nodeList=new Vector();
	private Vector loginnodes;

	//-----
	// End of variables declaration


	public NodeFormE(String nodeInfo)
	{
		
		super();

		String[] nodeDetails = nodeInfo.split("#");

		nodeN = nodeDetails[0];

		ipAdd = nodeDetails[1];

		portNum = Integer.parseInt(nodeDetails[2]);			   		
		
		initializeComponent();			
		
		this.setVisible(true);				
		
			
	}


	private void initializeComponent()
	{
	
		NodeServer obj = new NodeServer( nodeN,ipAdd,portNum );

		java.util.Timer timer = new java.util.Timer();

		timer.schedule( obj,3000 );

		jTabbedPane1 = new JTabbedPane();
		contentPane = (JPanel)this.getContentPane();
		//-----
		jLNodeList = new JLabel();
		jList1 = new JList(getNodeListForCombo()); 
		jLPath=new JLabel();
		jScrollPane3 = new JScrollPane();
		jCBNodeList = new JComboBox();
		jTAContent = new JTextArea();
		jScrollPane1 = new JScrollPane();
		jBsession=new JButton();
		jBbrowse=new JButton();
		jBTransmission=new JButton();	
		jBClear=new JButton();
		jBExit=new JButton();
		jTFPath=new JTextField();
		jPanel1 = new JPanel();
		//-----
		jTARFeceiveContent = new JTextArea();
		jScrollPane2 = new JScrollPane();
		jDecrypt = new JButton();
		jBClearde = new JButton();
		jPanel2 = new JPanel();
		//-----
		jPanel3 = new JPanel();
		//-----

		//
		// jTabbedPane1
		//
		jTabbedPane1.addTab("Transmission", jPanel1);
		jTabbedPane1.addTab("Receiver", jPanel2);		
		jTabbedPane1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e)
			{
				jTabbedPane1_stateChanged(e);
			}

		});
		//
		// contentPane
		//
		contentPane.setLayout(null);
		addComponent(contentPane, jTabbedPane1, 8,13,867,626);
		//addComponent()
		//
		// jLNodeList
		//
		jLNodeList.setText("Destination");


			jLPath.setText("File Directory");
		//
		// jCBNodeList
		//
		
		

		jBsession.setText("Request");
		jBsession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBsession_actionPerformed(e);
			}

		});
		jBbrowse.setText("Browse");
		jBbrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBbrowse_actionPerformed(e);
			}

		});
		jBTransmission.setText("Transmission");
		jBTransmission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBTransmission_actionPerformed(e);
			}

		});
		jBExit.setText("Retriev Session Key");
		jBExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBExit_actionPerformed(e);
			}

		});
		jBClear.setText("Clear");
		jBClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBClear_actionPerformed(e);
			}

		});

			
 		jList1.addListSelectionListener(new ListSelectionListener() { 
 			public void valueChanged(ListSelectionEvent e) 
 			{ 
 				jList1_valueChanged(e); 
 			} 
  
 		});
 		// 
 		// jScrollPane1 
 		// 
 		
 		// 
		//
		// jTAContent
		//
		//
		// jScrollPane1
		//
		jScrollPane1.setViewportView(jTAContent);
		jScrollPane3.setViewportView(jList1); 
 		// 
		//
		// jPanel1
		//
		jPanel1.setLayout(null);
		jPanel1.setOpaque(true);
		addComponent(jPanel1, jLNodeList, 81,69,100,18);
		addComponent(jPanel1, jScrollPane3, 175,58,60,60); 
		//addComponent(jPanel1, jCBNodeList, 175,64,150,30);
		addComponent(jPanel1, jLNodeList, 81,69,100,18);
		addComponent(jPanel1, jLPath, 81,150,100,22);
		addComponent(jPanel1, jTFPath, 175,150,150,30);
		addComponent(jPanel1, jBbrowse, 600,240,150,32);
		addComponent(jPanel1, jBsession, 600,310,150,32);
		addComponent(jPanel1, jBTransmission, 600,370,150,32);	
		addComponent(jPanel1, jBClear, 600,440,150,32);	
		addComponent(jPanel1, jBExit, 600,510,150,32);		
		addComponent(jPanel1, jScrollPane1, 81,230,411,300);
	
		// jTARFeceiveContent
		//
		//
		// jScrollPane2
		//
		jScrollPane2.setViewportView(jTARFeceiveContent);
		jDecrypt.setText("Decrypt");
		jDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jDecrypt_actionPerformed(e);
			}

		});

		jBClearde.setText("Clear");
		jBClearde.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBClearde_actionPerformed(e);
			}

		});
		//
		// jPanel2
		//
		jPanel2.setLayout(null);
		jPanel2.setOpaque(false);
		addComponent(jPanel2, jScrollPane2, 68,131,402,294);
		addComponent(jPanel2, jDecrypt, 88,480,130,34);
		addComponent(jPanel2, jBClearde, 88,520,130,34);

		//
		// jPanel3
		//
		jPanel3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		jPanel3.setOpaque(false);
		//
		// NodeForm
		//

        System.out.println("nodename return :"+nodeN);
		this.setTitle("NodeForm " +nodeN);
		this.setLocation(new Point(0, 0));
		this.setSize(new Dimension(889, 676));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}
	
	private void jTabbedPane1_stateChanged(ChangeEvent e)
	{
		System.out.println("\njTabbedPane1_stateChanged(ChangeEvent e) called.");

	//	getMsg();	

	}


	private void jBsession_actionPerformed(ActionEvent e)

	{

		System.out.println("\njBSave_actionPerformed(ActionEvent e) called.");

		requestForSessionKey();

	}

		private void jBbrowse_actionPerformed(ActionEvent e)

	{

		System.out.println("\njBSave_actionPerformed(ActionEvent e) called.");

		try
		{
			JFileChooser jfc=new JFileChooser();
			int i=jfc.showOpenDialog(this);
			if(i==JFileChooser.APPROVE_OPTION)
			{
				File f=jfc.getSelectedFile();
				String path=f.getAbsolutePath();	
				jTFPath.setText(path);

				FileInputStream fis=new FileInputStream(path);
				int length=fis.available();

				byte data[]=new byte[length];
				fis.read(data);
				content=new String(data);

				jTAContent.setText(content);

		}
		}
		catch (Exception f)
		{
			f.printStackTrace();
		}

	}
		private void jBTransmission_actionPerformed(ActionEvent e)

	{
		try
		{
			Transmission();	
			
		}
		catch (Exception m)
		{
			m.printStackTrace();
		}	
		
	}


	private void jBClear_actionPerformed(ActionEvent e)

	{

		System.out.println("\njBSave_actionPerformed(ActionEvent e) called.");

		jTAContent.setText("");

		jTFPath.setText("");

	}

	private void jBExit_actionPerformed(ActionEvent e)

	{

		System.out.println("\njBSave_actionPerformed(ActionEvent e) called.");

		retrievSessionKey();
		
	}
	

	private void jDecrypt_actionPerformed(ActionEvent e)
	{
		
		decryptFile();
			
	}

		private void jBClearde_actionPerformed(ActionEvent e)
	{
			jTARFeceiveContent.setText("")			;

	}
	private void jList1_valueChanged(ListSelectionEvent e) 
 	{ 
 		System.out.println("\njList1_valueChanged(ListSelectionEvent e) called."); 
 		if(!e.getValueIsAdjusting()) 
 		{ 
 			Object o = jList1.getSelectedValue(); 
 			System.out.println(">>" + ((o==null)? "null" : o.toString()) + " is selected."); 
 			
 		} 
 	}



	public Vector getNodeListForCombo()
	{
		Vector otherNodes = new Vector();
		try
		{
			soc=new Socket(ServerAddress.getAddress(),7373); 
			
			oos=new ObjectOutputStream(soc.getOutputStream());
			
			oos.writeObject("getOthers");

			oos.writeObject( nodeN );

			ois=new ObjectInputStream(soc.getInputStream());
			
			otherNodes = (Vector)ois.readObject();
			
			}
		catch (Exception c)
		{
			c.printStackTrace();
		}
		return otherNodes;
	}


	


	public void Transmission()
	{	
		   try{
			soc = new Socket( ServerAddress.getAddress(),7373 );

			oos = new ObjectOutputStream( soc.getOutputStream());

			oos.writeObject("getnodeinfo");

			oos.writeObject( nodeList );

			ois = new ObjectInputStream( soc.getInputStream() );

			ArrayList nodeInfo = (ArrayList)ois.readObject();

			String encryptedContent = FileProtection.encrypt( jTAContent.getText().trim(),readFile("session.txt"));

			transmitData( nodeInfo,encryptedContent );

		}
		catch(Exception e){
			
			e.printStackTrace();
		}
	}

	public void transmitData(ArrayList nodeInfo,String encryptedData){

		try{

			for (int i = 0; i < nodeInfo.size(); i = i+2 )
			{

				Socket socObj = new Socket( nodeInfo.get(i).toString(),Integer.parseInt((String)nodeInfo.get(i+1)));

				oos = new ObjectOutputStream( socObj.getOutputStream());

				oos.writeObject("inputdata");
				
				oos.writeObject(nodeN.trim());

				oos.writeObject( encryptedData );				

				JOptionPane.showMessageDialog(this,"message Sent Successfully");
			}
		}
		catch(Exception e){

				JOptionPane.showMessageDialog(this,"Sender Node Not Available");

			e.printStackTrace();
		}
	}




	public void requestForSessionKey()
	{
		try
		{
			 nodeList=jList1.getSelectedValues();

			soc=new Socket(ServerAddress.getAddress(),7373);

			oos=new ObjectOutputStream(soc.getOutputStream());

			oos.writeObject("forsessionkey");
		
			oos.writeObject( nodeN );
			
			oos.writeObject(nodeList);

			JOptionPane.showMessageDialog(this,"Request to KGC")		;
			
						
		}
		catch (Exception m)
		{
			m.printStackTrace();
		}
	}


	public void retrievSessionKey()
	{
		try{
			soc=new Socket(ServerAddress.getAddress(),7373);

			oos=new ObjectOutputStream(soc.getOutputStream());

			ois=new ObjectInputStream(soc.getInputStream());

			oos.writeObject("retrievSession");

			oos.writeObject(nodeN);

			String msg=(String)ois.readObject();

			System.out.println("Msg from KGC.."+msg);

			if(msg.equals("Authorised")){

				String inputForHash=(String)ois.readObject();

				JOptionPane.showMessageDialog(this,"Authroized User");

				long hashValue = HashAlgorithm.cbuHash( inputForHash );

				System.out.println("The Hash Value"+hashValue);

				String sessionKey = FileProtection.decrypt( readFile("enckey.txt"),String.valueOf( hashValue ));

				JOptionPane.showMessageDialog(this,"The SessionKey is"+sessionKey);

				writeFile( sessionKey );
				
			}

			else{

				JOptionPane.showMessageDialog(this,"UnAuthorized User");			
			}

							
		}
		catch (Exception rt){
			rt.printStackTrace();
		}
	}

	public String readFile(String fileNameIs) {
		
		String fileContent = "";
		
		try{

			FileInputStream fis = new FileInputStream( fileNameIs );

			byte[] byteArray = new byte[ fis.available()];

			fis.read( byteArray );

			fileContent = new String( byteArray );

			System.out.println("The Value is"+fileContent);
			
			fis.close();
		}
		catch(Exception e){

			e.printStackTrace();
		}
		return fileContent;
	}


	public void writeFile( String  sessionKeyIs ){

		try{

			FileOutputStream fos = new FileOutputStream("session.txt");

			fos.write( sessionKeyIs.getBytes() );

			fos.close();
		}
		catch(Exception e){

			e.printStackTrace();
		}
	}
		
	
	public void  decryptFile(){

		try{
			
			String decryptedContent = FileProtection.decrypt( readFile("encContent.txt"),readFile("session.txt"));

			jTARFeceiveContent.setText( decryptedContent );

		}
		catch( Exception e ){

			e.printStackTrace();
		}

	}
//============================= Testing ================================//
//=                                                                    =//
//= The following main method is just for testing this class you built.=//
//= After testing,you may simply delete it.                            =//
//======================================================================//
	public static void main(String[] args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		try
		{
			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
		//new NodeFormA("a");
	}


}
