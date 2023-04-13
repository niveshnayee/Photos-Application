package app;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable 
{
	private static final long serialVersionUID = 2L;
	public String name;
	public static album albumName;
	
	public ArrayList<album> albums;
	
	public User(String name, ArrayList<album> p)
	{
		this.name = name;
		albums = p;
	}
	
	
	public album getAlbum(String name)
	{
		for(album alb : albums)
		{
			if(alb.name == name)
			{
				return alb;
			}
		}
		return null;
	}
	
	
	public void addAlbum(String name)
	{
//		if (albums == null) {
//	        albums = new ArrayList<album>();
//	    }
		album alb = new album(name, new ArrayList<photoList>());
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
