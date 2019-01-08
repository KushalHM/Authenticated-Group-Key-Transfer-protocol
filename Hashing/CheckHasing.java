class  CheckHasing
{
	static String sample = "12wrKz";

	 static String ency;

	 static String decy;
	
	public static void main(String[] args) throws Exception
	{
		 long key = HashAlgorithm.cbuHash( sample );

		 ency = FileProtection.encrypt( sample,String.valueOf( key ) );

		 decy  = FileProtection.decrypt( ency,String.valueOf( key ) );

		 System.out.println("The Hash Function"+key);

		  System.out.println("The Encrypted Value"+ency);

		   System.out.println("The Decrypted Value"+decy);


	}
}
