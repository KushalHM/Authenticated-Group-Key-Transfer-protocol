/****************************************************************/
/*                      SigninForm	                            */
/*                                                              */
/****************************************************************/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.net.*;
import java.io.*;
/**
 * Summary description for SigninForm
 *
 */
public class SigninFormA extends JFrame
{
	// Variables declaration
	private JLabel jLname;
	private JLabel imageLabel; 
	private JLabel jLpass;
	private JTextField jTFname;
	private JPasswordField jTFpass;
	private JButton jBsignin;
	private JButton jBSignUp;
	private JPanel contentPane;
	public InetAddress inet;
	Connection con;
	Statement st;
	ResultSet rs;
	Socket soc;
	ServerSocket ss;
	
	String dbuser,nodename,pass;
	

	public SigninFormA()
	{
		super();
		initializeComponent();

		this.setVisible(true);
	}

	
	private void initializeComponent()
	{
		imageLabel=  new JLabel();
		jLname = new JLabel();
		jLpass = new JLabel();
		jTFname = new JTextField();
		jTFpass = new JPasswordField();
		jBsignin = new JButton();
		jBSignUp = new JButton();
		contentPane = (JPanel)this.getContentPane();
		
		jLname.setText("NodeName");
		
		jLpass.setText("Password");

		imageLabel.setIcon(new ImageIcon("images\\key-to-success.jpg")); 
		
		jTFname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jTFname_actionPerformed(e);
			}

		});
		
		jTFpass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jTFpass_actionPerformed(e);
			}

		});
		
		jBsignin.setText("Sign In");
		jBsignin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBsignin_actionPerformed(e);
			}

		});
		//
		// jBSignUp
		//
		jBSignUp.setText("Sign Up");
		jBSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				jBSignUp_actionPerformed(e);
			}

		});
		//
		// contentPane
		//
		contentPane.setLayout(null);
		addComponent(contentPane, jLname, 77,83,100,24);
		addComponent(contentPane, jLpass, 74,169,100,18);
		addComponent(contentPane, jTFname, 179,84,100,22);
		addComponent(contentPane, jTFpass, 181,165,100,22);
		addComponent(contentPane, jBsignin, 80,242,83,28);
		addComponent(contentPane, jBSignUp, 220,242,83,28);
		addComponent(contentPane, imageLabel,-0,-5,483,448);  		
		//
		// SigninForm
		//
		this.setTitle("Login");
		this.setLocation(new Point(350,225));
		this.setSize(new Dimension(400, 400));
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/** Add Component Without a Layout Manager (Absolute Positioning) */
	private void addComponent(Container container,Component c,int x,int y,int width,int height)
	{
		c.setBounds(x,y,width,height);
		container.add(c);
	}

	//
	// TODO: Add any appropriate code in the following Event Handling Methods
	//
	private void jTFname_actionPerformed(ActionEvent e)
	{
		System.out.println("\njTFname_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jTFpass_actionPerformed(ActionEvent e)
	{
		System.out.println("\njTFpass_actionPerformed(ActionEvent e) called.");
		// TODO: Add any handling code here

	}

	private void jBsignin_actionPerformed(ActionEvent e)
	{
		System.out.println("\njBsignin_actionPerformed(ActionEvent e) called.");

		getSignIn();
		
	}

	

	private void jBSignUp_actionPerformed(ActionEvent e)
	{
		System.out.println("\njBSignUp_actionPerformed(ActionEvent e) called.");

		new NodeRegister();	

		this.dispose();
	}

	
	public void  getSignIn()

	{
		 nodename=jTFname.getText();

		 pass=jTFpass.getText();	

		try
			{
			
			ObjectInputStream ois;

			ObjectOutputStream oos;

			//soc=new Socket("localhost",7373);
			InetAddress inet=InetAddress.getLocalHost();
			String s=inet.getHostAddress();
			System.out.println("aaaaaaaaaaaaaaaaaaa "+s);

			soc=new Socket(ServerAddress.getAddress(),7373);

			oos=new ObjectOutputStream(soc.getOutputStream());			
			
			oos.writeObject("logincheck");

			oos.writeObject(nodename);

			oos.writeObject(pass);
				
			oos.writeObject(s.trim());
		

			ois=new ObjectInputStream(soc.getInputStream());		
			
			String msg=(String)ois.readObject();		 
			
			if(msg.equals("success"))

				{	
					
				String nodeDetails =(String) ois.readObject();

				JOptionPane.showMessageDialog(this," Success");
				
				new NodeFormA( nodeDetails );
				
				this.dispose();

				
				}

				else
				{
					JOptionPane.showMessageDialog(this,"Invalid Login");

					jTFname.setText("");

				      jTFpass.setText("");
				}				
				}
			
		
			
			catch (Exception db)
			{
				db.printStackTrace();

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
		new SigninFormA();
	}
//= End of Testing =


}
