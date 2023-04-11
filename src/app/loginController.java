package app;

import java.io.IOException;
//import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class loginController  {

    @FXML
    private Button login;

    @FXML
    private TextField uName;

    
    public void start()
    {
    	
    }
    @FXML
    void login(ActionEvent event) throws IOException 
    {
//    	FXMLLoader loader = new FXMLLoader(getClass().getResource("admin.fxml"));
//        Parent root = loader.load();
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) uName.getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        
//        AnchorPane root = FXMLLoader.load(getClass().getResource("admin.fxml"));
//        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root, 800, 600);
//        mainStage.setScene(scene);
//        mainStage.show();
        
//        Parent admin = FXMLLoader.load(getClass().getResource("admin.fxml"));
//        Scene adminScene = new Scene(admin);
//        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        mainStage.setScene(adminScene);
        
//        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
//    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//    	Scene scene = new Scene(root);
//    	stage.setScene(scene);
//    	stage.show();

    	
    	if(uName.getText().equalsIgnoreCase("admin")) 
    	{
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("admin.fxml"));
    		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		AnchorPane root = (AnchorPane)loader.load();
    		mainStage.setScene(new Scene(root));
    		mainStage.show();
    	}
    	else if(database.exists(uName.getText()))
    	{
    		
//    		albumController.userName.setText(uName.getText());
    		
    		database.setUser(uName.getText());
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(getClass().getResource("album.fxml"));
    		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		AnchorPane root = (AnchorPane)loader.load();
    		mainStage.setScene(new Scene(root));
    		mainStage.show();
    	}else	
    	{
    		uName.clear();
    		uName.requestFocus();
    		uName.positionCaret(0);
    	}
             
    }

}


