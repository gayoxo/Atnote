package lector.client.reader.localstore;

import java.util.ArrayList;

public class MyTypes {

	private static ArrayList<String> Lista=null;
	


	
	public static ArrayList<String> getTypes()
	{	if (Lista==null) 
			Lista=new ArrayList<String>(); 
		return Lista;
	}
	
	public static void setTypes(ArrayList<String> newlista)
	{
		Lista=newlista;
	}
	
	
	
	
	
	
}
