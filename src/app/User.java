package app;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable 
{
	private static final long serialVersionUID = 2L;
	public String name;
	public static album albumName;
	
	public ArrayList<album> albums;
	
	public User(String name)
	{
		this.name = name;
		this.albums = new ArrayList<album>();
	}
	
	public void addAlbum(String name)
	{
		if (albums == null) {
	        albums = new ArrayList<album>();
	    }
		album alb = new album(name);
		albums.add(alb);
	}
	
	public void renameAlbum(String old, String rename)
	{
		for(album alb : albums)
		{
			if(alb.name == rename)
			{
				alb.name = rename;
			}
		}
	}
	
	public void removeAlbum(String name)
	{
		for(album alb : albums)
		{
			if(alb.name.equals(name))
			{
				albums.remove(alb);
				return;
			}
		}
	}
	
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name){ this.name = name;}
}
