package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Tags implements Serializable 
{
	private static final long serialVersionUID = 7L;
	public HashMap<String, String> tags;
	public ArrayList<String> photoPath;
	
	public Tags(HashMap<String,String> hp, ArrayList<String> p)
	{
		tags = hp;
		photoPath = p;
	}
	
}
