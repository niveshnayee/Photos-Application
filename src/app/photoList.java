/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * searilizable class to save photo data 
 * @author niveshnayee
 *
 */
public class photoList implements Serializable 
{
	private static final long serialVersionUID = 6L;
	public String path;
	public String caption;
	public String dateNTime;
	
	
	public ArrayList<String> tags;
	
	/**
	 * list of photos and their paths 
	 * @param path
	 * @param caption
	 * @param dNt
	 */
	public photoList(String path, String caption, String  dNt)
	{
		this.path = path;
		this.caption = caption;
		this.dateNTime = dNt;
		tags = new ArrayList<>();
	}
	
	
}
