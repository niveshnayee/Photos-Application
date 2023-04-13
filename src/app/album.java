package app;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class album implements Serializable
{
	private static final long serialVersionUID = 5L;
	public String name;
	public LocalDateTime oldDate, newDate;
	public ArrayList<photoList> photos;
	
	
	public album(String name, ArrayList<photoList> p)
	{
		this.name = name;
		photos = p;
		oldDate = null;
		newDate = null;
	}
	
	public String toString() {
        return name;
    }
	
	
}
