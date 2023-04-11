package app;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class albumController {

    @FXML
    private ListView<String> albums;

    @FXML
    public static Label userName = new Label();
    
    private ObservableList<String> data = FXCollections.observableArrayList();
    
    private int selectedIndex = -1;
    
    
    
    public void initialize() throws IOException 
	{
//		System.out.println(database.users());
    	if(database.userObj.albums != null) 
    	{
    		for(album alb : database.userObj.albums)
    		{
    			data.add(alb.name);
    		}
    	}
    	albums.setItems(data);
		albums.requestFocus();
		userName.setAccessibleText(database.userObj.getName());
		
		albums.setOnMouseClicked(event -> {
		    if (event.getClickCount() == 2) {
		    	// check if the mouse was double clicked
		    	try {
		    		FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("photolist.fxml"));
					Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
					AnchorPane root = (AnchorPane)loader.load();
					mainStage.setScene(new Scene(root));
					mainStage.show();
		    	} catch(Exception e)
		        {
		        	e.printStackTrace();
		        }
		    	
		    }
		});
		
	}

    @FXML
    void add(ActionEvent event) 
    {
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Add Name");
    	dialog.setHeaderText("Enter your name:");
    	dialog.setContentText("Name:");

//    	final Optional<ButtonType> result = alert.showAndWait();
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    String name = result.get();
    	    
    	    if(name!= "" &&!data.contains(name))
        	{
        		
//        		User user = database.getUser(name);
//        		user.setName(name);
    	    	database.userObj.addAlbum(name);
        		data.add(name);
        	}
    	}
    }

    @FXML
    void delete(ActionEvent event) 
    {
    	selectedIndex = albums.getSelectionModel().getSelectedIndex();
    	if(selectedIndex == 0 && data.size() < 0)
    		selectedIndex = -1;
    	
    	if(selectedIndex > -1)
    	{
    		selectedIndex = albums.getSelectionModel().getSelectedIndex();
        	database.userObj.removeAlbum(data.get(selectedIndex));
        	data.remove(selectedIndex);
    	}
    }

    @FXML
    void edit(ActionEvent event) 
    {
    	String old = albums.getSelectionModel().getSelectedItem();
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Rename");
    	dialog.setHeaderText("Enter your Album name:");
    	dialog.setContentText("Name:");

//    	final Optional<ButtonType> result = alert.showAndWait();
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    String name = result.get();
    	    
    	    if(name!= "" &&!data.contains(name))
        	{
        		
//        		User user = database.getUser(name);
//        		user.setName(name);
    	    	database.userObj.renameAlbum(old, name);
    	    	selectedIndex = albums.getSelectionModel().getSelectedIndex();
        		data.set(selectedIndex, name);
        	}
    	}
    }

    @FXML
    void logout(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("home.fxml"));
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = (AnchorPane)loader.load();
		mainStage.setScene(new Scene(root));
		mainStage.show();
    }

    @FXML
    void searchDate(ActionEvent event) 
    {
    	
    }

    @FXML
    void searchTag(ActionEvent event) {

    }

}
