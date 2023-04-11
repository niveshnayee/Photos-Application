package app;

import java.io.Serializable;
import java.util.ArrayList;

public class photoList implements Serializable 
{
	private static final long serialVersionUID = 6L;
	public String path;
	public String caption;
	public String date;
	public String time;
	
	public ArrayList<String> Tags;
	
	public photoList(String path)
	{
		this.path = path;
	}
	
	
}
