package app;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable 
{
	private static final long serialVersionUID = 2L;
	public String name;
	public static album albumName;
	
	public  ArrayList<Tags> tag;
	public ArrayList<album> albums;
	
	public HashMap<LocalDate,String> dateSearch; 
	
	public User(String name, ArrayList<album> p)
	{
		this.name = name;
		albums = p;
		tag = new ArrayList<Tags>();
		dateSearch = new HashMap<>();
	}
	
	public Tags getTagFromPath(String path)
	{
		for(Tags t : tag)
		{
			if(t.photoPath.contains(path))
				return t;
		}
		return null;
	}
	
	public boolean containsCat(String name)
	{
		for(Tags t : tag)
		{
			if(t.tags.containsKey(name))
				return true;
		}
		return false;
	}
	
	public boolean containsVal(String name)
	{
		for(Tags t : tag)
		{
			if(t.tags.containsValue(name))
				return true;
		}
		return false;
	}
	
	
	public boolean containsPath(String name)
	{
		for(Tags t : tag)
		{
			if(t.photoPath.contains(name))
				return true;
		}
		return false;
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
