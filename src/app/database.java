package app;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class database implements Serializable 
{
	 private static ArrayList<User> usersList;
	 
	 public static void addUser(User user)
	 {
		 usersList.add(user);
	 }
	 
	 public static User exists(String userName)
	 {
        for(User user : usersList){
            if(user.getName().equalsIgnoreCase(userName)){
                return user;
            }
        }
        return null;
    }
    
	 public static ArrayList<User> getUsersList()
	 {
        return usersList;
	 }
	 
	 public static void writeToDataBase() throws IOException
	 {
        FileOutputStream fileOutputStream = new FileOutputStream("data.dat");
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(usersList);
	 }
	 
	 
	 public static void readFromDataBase() throws IOException, ClassNotFoundException 
	 {
        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream("data.dat");
        } catch (Exception e) {
            usersList = new ArrayList<>();
            adminController.start();
            return;
        }
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        usersList = (ArrayList<User>) objectInputStream.readObject();
        if(database.exists("stock") == null){
        	adminController.start();
        }
        fileInputStream.close();
        objectInputStream.close();

	 }
}
