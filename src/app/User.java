/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class User implements Serializable 
{
	private static final long serialVersionUID = 2L;
	public String name;
	public static album albumName;
	
	public  ArrayList<Tags> tag;
	public ArrayList<album> albums;
	
	public HashMap<LocalDate,List<String>> dateSearch; 
	
	/**
	 * contructor
	 * @param name
	 * @param p
	 */
	public User(String name, ArrayList<album> p)
	{
		this.name = name;
		albums = p;
		tag = new ArrayList<Tags>();
		dateSearch = new HashMap<>();
	}
	
	/**
	 * gets the tag object from the path given
	 * @param path
	 * @return
	 */
	public Tags getTagFromPath(String path)
	{
		for(Tags t : tag)
		{
			if(t.photoPath.contains(path))
				return t;
		}
		return null;
	}
	
	/**
	 * check if caterogry contains in Tags 
	 * @param name
	 * @return
	 */
	public boolean containsCat(String name)
	{
		for(Tags t : tag)
		{
			if(t.tags.containsKey(name))
				return true;
		}
		return false;
	}
	
	/**
	 * check if value contains in Tags
	 * @param name
	 * @return
	 */
	public boolean containsVal(String name)
	{
		for(Tags t : tag)
		{
			for(List<String> value : t.tags.values())
			{
				if(value.contains(name))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * check if name contains in Tags
	 * @param name
	 * @return
	 */
	
	public boolean containsPath(String name)
	{
		for(Tags t : tag)
		{
			if(t.photoPath.contains(name))
				return true;
		}
		return false;
	}
	
	
	/**
	 * get the album from name 
	 * @param name
	 * @return
	 */
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
	
	/**
	 * add album by given name for that
	 * @param name
	 */
	public void addAlbum(String name)
	{
//		if (albums == null) {
//	        albums = new ArrayList<album>();
//	    }
		album alb = new album(name, new ArrayList<photoList>());
		albums.add(alb);
	}
	
	/**
	 * renames the album
	 * @param old
	 * @param rename
	 */
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
	
	
	/**
	 * removes the album  
	 * @param name
	 */
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
	
	/**
	 * gets the name of user
	 * @return
	 */
	public String getName()
	{
		return name;
	}
	
	
	/**
	 * set the name for user
	 * @param name
	 */
	public void setName(String name){ this.name = name;}
}
