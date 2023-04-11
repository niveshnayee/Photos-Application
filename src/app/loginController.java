package app;

import java.io.IOException;
//import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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


