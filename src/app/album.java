package app;

import java.io.Serializable;
import java.util.ArrayList;

public class album implements Serializable
{
	private static final long serialVersionUID = 5L;
	public String name;
	public ArrayList<photoList> photos;
	
	public album(String name)
	{
		this.name = name;
	}
	
	
	
	
}
