/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tags implements Serializable 
{
	private static final long serialVersionUID = 7L;
	public HashMap<String, List<String>> tags;
	public ArrayList<String> photoPath;
	
	/**
	 * constructor
	 * @param hp
	 * @param p
	 */
	public Tags(HashMap<String,List<String>> hp, ArrayList<String> p)
	{
		tags = hp;
		photoPath = p;
	}
	
}
