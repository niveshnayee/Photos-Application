/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;



import java.io.IOException;

//import java.io.FileWriter;
//import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * 
 * @author niveshnayee
 * @author mananpatel
 *
 */
public class Photos extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			database.readFromDataBase();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("home.fxml"));
			primaryStage.setTitle("PhotoApp");
			primaryStage.setResizable(false);
			AnchorPane root = (AnchorPane)loader.load();
			loginController listController = loader.getController();
			primaryStage.setOnCloseRequest(event -> 
	    	{
	    		try
	    		{
	    			database.writeToDataBase();
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
    	);
			listController.start();
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			
		}
	}
	/**
	 * starts the main scene
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}