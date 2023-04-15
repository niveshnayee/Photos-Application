/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;


/**
 * album class 
 * @author niveshnayee
 *
 */
public class album implements Serializable
{
	private static final long serialVersionUID = 5L;
	public String name;
//	public LocalDateTime oldDate, newDate;
	public LocalDate old, latest;
	public ArrayList<photoList> photos;
	
	/**
	 * album constructor 
	 * @param name
	 * @param p
	 */
	public album(String name, ArrayList<photoList> p)
	{
		this.name = name;
		photos = p;
		old = null;
		latest = null;
	}
	
	/**
	 * returns te name of the album for category options 
	 */
	public String toString() {
        return name;
    }
	
	
}
