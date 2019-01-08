public class HashAlgorithm
{
	public HashAlgorithm()
	{
		
	}
	
	public static long cbuHash( String MSG  )
	{
		long h = 0;
		
		byte[] str = MSG.getBytes();
		
		for ( int i= 0; i < str.length ; i++ )
		{
			h = ( h << 2 ) + str[i];
		}
		
		return h;
	}
}


//ABC = msg