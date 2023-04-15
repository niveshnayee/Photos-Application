package app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class album implements Serializable
{
	private static final long serialVersionUID = 5L;
	public String name;
//	public LocalDateTime oldDate, newDate;
	public LocalDate old, latest;
	public ArrayList<photoList> photos;
	
	
	public album(String name, ArrayList<photoList> p)
	{
		this.name = name;
		photos = p;
		old = null;
		latest = null;
	}
	
	public String toString() {
        return name;
    }
	
	
}
