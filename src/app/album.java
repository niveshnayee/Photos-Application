package app;

import java.io.Serializable;

public class album implements Serializable
{
	private static final long serialVersionUID = 5L;
	public String name;
	
	public album(String name)
	{
		this.name = name;
	}
	
	
}
