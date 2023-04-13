package app;

import java.io.Serializable;
import java.util.ArrayList;

public class photoList implements Serializable 
{
	private static final long serialVersionUID = 6L;
	public String path;
	public String caption;
	public String dateNTime;
	
	
	public ArrayList<String> tags;
	
	public photoList(String path, String caption, String  dNt)
	{
		this.path = path;
		this.caption = caption;
		this.dateNTime = dNt;
		tags = new ArrayList<>();
	}
	
	
}
