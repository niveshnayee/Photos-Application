/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class database implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private static database db = null;
	private static ArrayList<User> usersList;
	public static User userObj;
	
	/**
	 * contructor for database saving and initialize the stock user 
	 */
	 private database()
	 {
		 usersList = new ArrayList<User>();
		 User Stock = new User("Stock", new ArrayList<album>());
		 album alb = new album("stocks", new ArrayList<photoList>());
		 Stock.albums.add(alb);
		 
		 LocalDateTime currentDateTime = LocalDateTime.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
         String formattedDateTime = currentDateTime.format(formatter);
		 alb.photos.add(new photoList( "data/second.jpeg", "none", formattedDateTime));
		 alb.photos.add(new photoList( "data/camel.jpeg", "none", formattedDateTime));
		 alb.photos.add(new photoList( "data/fifth.jpeg", "none", formattedDateTime));
		 alb.photos.add(new photoList( "data/image5.jpeg", "none", formattedDateTime));
		 alb.photos.add(new photoList( "data/newpic.jpeg", "none", formattedDateTime));
		 
		 List<String> path = new ArrayList<>();
		 
		 path.add("data/second.jpeg");
		 path.add("data/camel.jpeg");
		 path.add("data/fifth.jpeg");
		 path.add("data/image5.jpeg");
		 path.add("data/newpic.jpeg");
		 
		 Stock.dateSearch.put(LocalDate.now(), path);

		 alb.old = LocalDate.now();

//		 Stock.setName("stock");
		 usersList.add(Stock);
	 }
	 
	 /**
	  * gets the instance of the database, only have one object of this class
	  * @return
	  */
	 public static database getInstance() 
	 {
		 if (db == null) {
		 db = new database();
		 }
		 return db;
	 }
	 
	 /**
	  * return user array 
	  * @return
	  */
	 public static ArrayList<User> users()
	 {
//		 database d = getInstance();
////		 System.out.println(d.exists("stock"));
		 return usersList;
	 }
	 
	 
	 /**
	  * remove user
	  * @param name
	  */
	 public static void removeUser(String name)
	 {
		 for(User user : usersList)
		 {
            if(user.getName().equalsIgnoreCase(name))
            {
            	usersList.remove(usersList.indexOf(user));
            	return;
            }
		 }
		 
	 }
	 
	 /**
	  * add user
	  * @param user
	  */
	 
	 public static void addUser(User user)
	 {
		 
//		 database d = getInstance();
		 usersList.add(user);
	 }
	 
	 /**
	  * 
	  * @param name
	  */
	 public static void setUser(String name)
	 {
//		 database d = getInstance();
		 for(User user : usersList)
		 {
            if(user.getName().equalsIgnoreCase(name)){
                userObj = user;
            }
		 }
	 }
	 
	 
	/**
	 *  check if the user is exists 
	 * @param name
	 * @return
	 */
	 public static boolean exists(String name)
	 {
//		 database d = getInstance();
		 for(User user : usersList)
		 {
            if(user.getName().equalsIgnoreCase(name)){
                return true;
            }
		 }
		 return false;
	 }
	 
	 
/**
 * writs in to ddata base
 * @throws IOException
 */
	 
	 public static void writeToDataBase() throws IOException
	 {
        FileOutputStream fileOutputStream = new FileOutputStream("data/data.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(db.usersList);
        outputStream.close();
	 }
	 
	 
	 /**
	  * reads in to db
	  */
	@SuppressWarnings("unchecked")
	public static void readFromDataBase()
	 {
		database d = getInstance();
		File f = new File("data/data.dat");
        try
        {
        	if(f.length() == 0)
            {
            	//
            }
        	else
            {
        		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
//                database obj = new database();
                Object o = ois.readObject();
                if(o instanceof ArrayList<?>)
                {
                	 ArrayList<User> currentUserList = (ArrayList<User>) o;
                     d.usersList = currentUserList;
                }
                ois.close();
               
            }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
	 }
}
