/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */

package app;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class adminController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button lgout;

    @FXML
    private TextField user;

    @FXML
    private ListView<String> userList;
    
    private ObservableList<String> data = FXCollections.observableArrayList();
    
    private int selectedIndex = -1;

    /**
     * method for add users 
     * @param event
     */
    @FXML
    void add(ActionEvent event) 
    {
    	String name = user.getText();
        // Add the name to the list
    	if(name!= "" &&!data.contains(name))
    	{
    		
    		User user = new User(name, new ArrayList<album>());
//    		user.setName(name);
    		database.addUser(user);
    		data.add(name);
    	}
        
        user.clear();
    }

    /**
     * deletion of users 
     * @param event
     */
    @FXML
    void delete(ActionEvent event) 
    {
    	selectedIndex = userList.getSelectionModel().getSelectedIndex();
        if (selectedIndex == 0 && data.size() < 0)
            selectedIndex = -1;

        if (selectedIndex > -1) {
            selectedIndex = userList.getSelectionModel().getSelectedIndex();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete User");
            alert.setContentText("Are you sure you want to delete the selected user?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                database.removeUser(data.get(selectedIndex));
                //removeAlbum(data.get(selectedIndex));
                data.remove(selectedIndex);
            }
        }
        
    }
    
    
/**
 * logout event handler 
 * @param event
 * @throws IOException
 */
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
/**
 * 
 */
	public static void start() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * intialize for admincontroller  before the scene
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
//		System.out.println(database.users());
		for(User user : database.users())
		{
			data.add(user.getName());
		}
		userList.setItems(data);
		userList.requestFocus();
	}

}
