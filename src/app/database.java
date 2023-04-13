package app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class database implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	private static database db = null;
	private static ArrayList<User> usersList;
	public static User userObj;
	
	 private database()
	 {
		 usersList = new ArrayList<User>();
		 User Stock = new User("stock", new ArrayList<album>());
//		 Stock.setName("stock");
		 usersList.add(Stock);
	 }
	 
	 public static database getInstance() 
	 {
		 if (db == null) {
		 db = new database();
		 }
		 return db;
	 }
	 
	 public static ArrayList<User> users()
	 {
//		 database d = getInstance();
////		 System.out.println(d.exists("stock"));
		 return usersList;
	 }
	 
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
	 
	 public static void addUser(User user)
	 {
		 
//		 database d = getInstance();
		 usersList.add(user);
	 }
	 
	 
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
	 
	 
//	 public static void addUser(User user)
//	 {
//		 db.usersList.add(user);
//	 }
//	 
//	 public static User exists(String userName)
//	 {
//        for(User user : usersList){
//            if(user.getName().equalsIgnoreCase(userName)){
//                return user;
//            }
//        }
//        return null;
//    }
//    
//	 public static ArrayList<User> getUsersList()
//	 {
//        return usersList;
//	 }
	 
	 
	 public static void writeToDataBase() throws IOException
	 {
        FileOutputStream fileOutputStream = new FileOutputStream("src/data.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(db.usersList);
        outputStream.close();
	 }
	 
	 
	@SuppressWarnings("unchecked")
	public static void readFromDataBase()
	 {
		database d = getInstance();
		File f = new File("src/data.dat");
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
