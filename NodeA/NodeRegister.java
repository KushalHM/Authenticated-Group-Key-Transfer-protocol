/****************************************************************/
/*                      NodeRegister	                            */
/*                                                              */
/****************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.net.*;
import java.io.*;

/**
 * Summary description for NodeRegister
 *
 */
public class NodeRegister extends JFrame
{
	// Variables declaration
	private JLabel jLNodeName;
	private JLabel jLip;
	private JLabel jLPort;	
	private JLabel jLPass;
	private JPasswordField jTFPass;
	private JTextField jTFnodename;
	private JTextField jTFip;
	private JTextField jTFPort;
	private JButton jBSave;
	private JButton jBMain;
	private JPanel contentPane;

	Connection con;
	Statement st;
	ResultSet rs;	
	
	// End of variables declaration


	public NodeRegister()
	{
		super();
		initializeComponent();

		try
		{	
			
		}
		catch (Exception c)
		{
			c.printStackTrace();
		}

		

		this.setVisible(true);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always regenerated
	 * by the Windows Form Designer. Otherwise, retrieving design might not work properly.
	 * Tip: If you must revise this method, please backup this GUI file for JFrameBuilder
	 * to retrieve your design properly in future, before revising this method.
	 */
	private void initializeComponent()
	{
		jLNodeName = new JLabel();
		jLip = new JLabel();
		jLPass=new JLabel();
		jLPort = new JLabel();		
		jTFnodename = new JTextField();
		jTFip = new JTextField();
		jTFPort = new JTextField();	
		jTFPass=new JPasswordField();
		jBSave = new JButton();
		jBMain=new JButton();
		contentPane = (JPanel)this.getContentPane();

		//
		// jLNodeName
		//
		jLNodeName.setText("NodeName");
		//
		// jLip
		//
		jLip.setText("IP Address");
		//
		// jLPort
		//
		jLPort.setText("Port Number");

		jLPass.setText("Password");
		//
		// jLKey
		//
	
		//
		// jTFnodename
		//
		jTFnodename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jTFnodename_actionPerformed(e);
			}

		});

			jTFPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jTFPass_actionPerformed(e);
			}

		});
		//
		// jTFip
		//
		jTFip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jTFip_actionPerformed(e);
			}

		});
		//
		// jTFPort
		//
		jTFPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jTFPort_actionPerformed(e);
			}

		});
		//
		// jPFKey
	
		//
		// jBSave
		//
		jBSave.setText("Register");
		jBSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBSave_actionPerformed(e);
			}

		});
		jBMain.setText("Back");
		jBMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBMain_actionPerformed(e);
			}

		});
		//
		// contentPane
		//
		contentPane.setLayout(null);
		addComponent(contentPane, jLNodeName, 70,50,100,18);
		addComponent(contentPane, jLip, 70,125,100,17);
		addComponent(contentPane, jLPort, 70,200,100,18);		
		addComponent(contentPane, jLPass, 70,275,100,18);		
		addComponent(contentPane, jTFnodename, 145,50,100,22);
		addComponent(contentPane, jTFip, 145,125,100,22);
		addComponent(contentPane, jTFPort, 145,200,100,22);	
		addComponent(contentPane, jTFPass, 145,275,100,22);		
		addComponent(contentPane, jBSave, 80,325,83,28);
		addComponent(contentPane, jBMain, 180,325,83,28);
		
		this.setTitle("New Register");
		this.setLocation(new Point(350,225));
		this.setSize(new Dimension(350,420));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		this.setResizable(false);
	}

	private void addComponent(Container container,Component c,int x,int y,int width,int height)

	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}

	
	private void jTFnodename_actionPerformed(ActionEvent e)
	{
		System.out.println("\njTFnodename_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

		private void jTFPass_actionPerformed(ActionEvent e)
	{
		System.out.println("\njTFnodename_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}



	private void jTFip_actionPerformed(ActionEvent e)
	{
		System.out.println("\njTFip_actionPerformed(ActionEvent e) called.");
		
	}

	private void jTFPort_actionPerformed(ActionEvent e)
	{
		System.out.println("\njTFPort_actionPerformed(ActionEvent e) called.");
		
	}

	

	private void jBSave_actionPerformed(ActionEvent e)

	{		
		ObjectOutputStream oos;

		ObjectInputStream ois;

		Socket soc;	

		String nodename=jTFnodename.getText();

		String ip=jTFip.getText();

		String port=jTFPort.getText();
		
		String pass=jTFPass.getText();

		try

		{
			if(nodename.equals("")){

				JOptionPane.showMessageDialog(this,"Enter the Name");
			}		
    	else if(ip.equals(""))	
		{
			JOptionPane.showMessageDialog(this,"Enter the IP address");
		}

		else if(port.equals(""))
		{
			JOptionPane.showMessageDialog(this,"Enter the Port Number");
		}

		else if(pass.equals("")){			
				JOptionPane.showMessageDialog(this,"Enter the Password");
				}			

		else

			{

		  soc=new Socket(ServerAddress.getAddress(),7373);	

		  oos=new ObjectOutputStream(soc.getOutputStream());

		  ois=new ObjectInputStream(soc.getInputStream());

		  oos.writeObject("newregister");

		  oos.writeObject(nodename);

		  oos.writeObject(ip);

		  oos.writeObject(port);

		  oos.writeObject(pass);

		  System.out.println("==>write complete");		  

		   String msg=(String)ois.readObject();	
		   
		   System.out.println("Server Reply"+msg);

		  if(msg.equals("registered"))
				
		  {
			  
			  String key=(String)ois.readObject();
			  
			  JOptionPane.showMessageDialog(this,"Node Informations Regitered");

    		   JOptionPane.showMessageDialog(this,"Key Generated By KGC");

			 JOptionPane.showMessageDialog(this,"Key is  :"+key);
			  
			  jTFnodename.setText("");

			  jTFip.setText("");

			  jTFPort.setText("");

			  jTFPass.setText("");
			
			
		}
		else{

			  JOptionPane.showMessageDialog(this,"Invalid Informations");		

			   jTFnodename.setText("");

			  jTFip.setText("");

			  jTFPort.setText("");

			  jTFPass.setText("");
			
		}
	}
	}
		catch (Exception n){
			n.printStackTrace();
		}	

	}

	private void jBMain_actionPerformed(ActionEvent e)
	{

		new SigninFormA();

		this.dispose();

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
		new NodeRegister();
	}

}
