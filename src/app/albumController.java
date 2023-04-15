/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.IOException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class albumController {

    @FXML
    private ListView<String> albums;

    @FXML
    public Label userName;
    
    private ObservableList<String> data = FXCollections.observableArrayList();
    
    private int selectedIndex = -1;
    
    
    
    
    /**
     * intialize for album controller 
     * @throws IOException
     */
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
    	
    	userName.setText(database.userObj.name);
    	albums.setItems(data);
		albums.requestFocus();
		userName.setAccessibleText(database.userObj.getName());
		
		albums.setOnMouseClicked(event -> {
			if(data.size()> 0)
			{
				if (event.getClickCount() == 2) {
			    	// check if the mouse was double clicked
			    	try 
			    	{
			    		selectedIndex = albums.getSelectionModel().getSelectedIndex();
			    		User.albumName = database.userObj.albums.get(selectedIndex); //checks which album opened
			    		
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
			}
		});
		
		albums.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
        {
              @Override
              public ListCell<String> call(ListView<String> listView)
              {
                  return new ListCell<String>()
                  {
                     

                      @Override
                      protected void updateItem(String item, boolean empty)
                      {
                    	  Label date;
                    	  Image image;
                          super.updateItem(item, empty);
                          if(empty || item == null)
                          {
                              setText(null);
                              setGraphic(null);
                          }
                          else
                          {
                              // Load the image from the file path
                        	  int size = database.userObj.getAlbum(item).photos.size();
                        	  
                        	  if(size != 0)
                        	  {
                        		  String path = database.userObj.getAlbum(item).photos.get(size-1).path;
                        		  image = new Image("file:" + path);
                        	  }
                        	  else
                        		  image = new Image("file:data/noImage.jpeg");
                              ImageView imageView = new ImageView();
                              Label album = new Label(item);
                              
                              if( database.userObj.getAlbum(item).old != null)
                              {
                            	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                                  String formattedDateTime = database.userObj.getAlbum(item).old.format(formatter);
                                  
                                  if(database.userObj.getAlbum(item).latest != null)
                                  {
                                      String formattedDateTime2 = database.userObj.getAlbum(item).latest.format(formatter);
                                      date = new Label(formattedDateTime + " - " + formattedDateTime2);
                                  }
                                  else
                                  {
                                	  date = new Label(formattedDateTime + " - " + formattedDateTime);
                                  }
                                  
                              }
                              else
                              {
                            	  date = new Label( "N/A" );
                              }

                              Label picCount = new Label(Integer.toString(size) + " Photos");
                              //Label picCount = new Label(Integer.toString(User.albumName.photos.size()));
                              imageView.setFitWidth(150);
                              imageView.setFitHeight(120);
                              imageView.setImage(image);
                              
                              // Set the image and text in the cell
                              // setText(item);
                              
                              HBox hb = new HBox();
                              VBox vb = new VBox();
                              vb.getChildren().addAll(album, date, picCount);
                              vb.setPadding(new Insets(40, 0, 0, 40));
                              hb.getChildren().addAll(imageView, vb);
                              setGraphic(hb);
                          }
                      }
                  };
              }
        });
		
	}

    /**
     * add method for adding new album
     * @param event
     */
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
    	    	database.userObj.addAlbum(name);
    	    	data.add(name);        		
        	}
    	}
    }
    
    /**
     * delete event handler
     * @param event
     */

    @FXML
    void delete(ActionEvent event) 
    {
    	
    	selectedIndex = albums.getSelectionModel().getSelectedIndex();
        if (selectedIndex == 0 && data.size() < 0)
            selectedIndex = -1;

        if (selectedIndex > -1) {
            selectedIndex = albums.getSelectionModel().getSelectedIndex();

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Album");
            alert.setContentText("Are you sure you want to delete the selected album?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                database.userObj.albums.remove(selectedIndex);
                data.remove(selectedIndex);
            }
        }
    }
    
    /**
     * edit album name handler 
     * @param event
     */

    @FXML
    void edit(ActionEvent event) 
    {
    	selectedIndex = albums.getSelectionModel().getSelectedIndex();
    	if(selectedIndex == 0 && data.size() < 0)
    		selectedIndex = -1;
    	
    	if(selectedIndex > -1)
    	{
    		String old = albums.getSelectionModel().getSelectedItem();
        	TextInputDialog dialog = new TextInputDialog();
        	dialog.setTitle("Rename");
        	dialog.setHeaderText("Enter your Album name:");
        	dialog.setContentText("Name:");

//        	final Optional<ButtonType> result = alert.showAndWait();
        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent()){
        	    String name = result.get();
        	    
        	    if(name!= "" &&!data.contains(name))
            	{
            		
//            		User user = database.getUser(name);
//            		user.setName(name);
        	    	database.userObj.renameAlbum(old, name);
        	    	selectedIndex = albums.getSelectionModel().getSelectedIndex();
            		data.set(selectedIndex, name);
            	}
        	}
    	}
    	
    }

    /**
     * log out handler 
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
     * search date button event 
     * @param event
     */
    @FXML
    void searchDate(ActionEvent event) 
    {
       	// Create the second popup window
        Stage popup2 = new Stage();
        popup2.setTitle("Searching");
        popup2.setResizable(false);

        
        Label fromDateLabel = new Label("From Date:");
        DatePicker fromDatePicker = new DatePicker();

        Label toDateLabel = new Label("To Date:");
        DatePicker toDatePicker = new DatePicker();

        Button searchButton = new Button("Search");

        // Create an EventHandler for the search button to handle the action event
        searchButton.setOnAction(e -> {

                LocalDate fromDate = fromDatePicker.getValue();
                
                
                SearchController.fromD = fromDate;
                
                LocalDate toDate = toDatePicker.getValue();
                
                SearchController.toD = toDate;

                
                if (toDate.isBefore(fromDate)) {
                    // Show a warning popup if the "to date" is before the "from date"
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Warning");
                    alert.setContentText("To date cannot be before from date");
                    alert.showAndWait();
                } else {
                    // Perform the search if the "to date" is not before the "from date"
                	try {
                		FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("search.fxml"));
                        AnchorPane root = (AnchorPane) loader.load();
                        Scene scene = new Scene(root);
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.setScene(scene);
                        currentStage.show();
                	} catch (Exception e1) {
                	    e1.printStackTrace();
                	}
                	popup2.close();
                }
            //}
        });

        // Create a VBox layout to hold the UI elements for the second popup
        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(10));
        vbox2.getChildren().addAll(fromDateLabel, fromDatePicker, toDateLabel, toDatePicker, searchButton);

        

        // Set the VBox as the root node of the second popup window
        Scene scene2 = new Scene(vbox2);
        popup2.setScene(scene2);

        // Show the second popup window
        popup2.showAndWait();

      
    	
      }
    
    
    

/**
 * search by tag event 
 * @param event
 * @throws IOException
 */
    @FXML
    void searchTag(ActionEvent event) throws IOException 
    {
    	// Create the UI elements for the popup
    	// Create the popup window
        Stage popup = new Stage();
        popup.setTitle("Search by tag");
        popup.setResizable(false);
        
        ChoiceBox<String> category1 = new ChoiceBox<>();
        category1.getItems().addAll("Person", "Place");
        for(Tags t: database.userObj.tag)
    	{
    		for (String cat : t.tags.keySet()) 
			{
			    if(!cat.equals("Place") && !cat.equals("Person"))
			    	category1.getItems().add(cat);
			}
    	}
        category1.setValue("Select Category");
        
        TextField value1 = new TextField();
        value1.setPromptText("Enter the value");

        
        ChoiceBox<String> AndOr = new ChoiceBox<>();
        AndOr.getItems().addAll("AND", "OR", "N/A");
        AndOr.setValue("Conjunctive/Disjunctive");

        
       
        ChoiceBox<String> category2 = new ChoiceBox<>();
        category2.getItems().addAll("Person", "Place");
        for(Tags t: database.userObj.tag)
    	{
    		for (String cat : t.tags.keySet()) 
			{
			    if(!cat.equals("Place") && !cat.equals("Person"))
			    	category2.getItems().add(cat);
			}
    	}
        
        TextField value2 = new TextField();
        value2.setPromptText("Enter the value");

        Button button = new Button("Submit");

        // Create an EventHandler for the button to handle the action event
        button.setOnAction(e ->{
//            @Override
//            
//            @FXML
//            public void handle(ActionEvent event) {
               
            	try
            	{
            		SearchController.cat1 = category1.getSelectionModel().getSelectedItem();
            		SearchController.val1 = value1.getText();
            		System.out.println(AndOr.getSelectionModel().getSelectedItem());
            		SearchController.opt = AndOr.getSelectionModel().getSelectedItem();
            		
            		SearchController.cat2 = category2.getSelectionModel().getSelectedItem();
            		SearchController.val2 = value2.getText();
            		
            		FXMLLoader loader = new FXMLLoader();
            		loader.setLocation(getClass().getResource("search.fxml"));
            		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            		AnchorPane root = (AnchorPane)loader.load();
            		mainStage.setScene(new Scene(root));
            		mainStage.show();
            	}catch(Exception e1)
		        {
		        	e1.printStackTrace();
		        }
            	popup.close();
            //}
        });

        // Create an anonymous ChangeListener for the second ChoiceBox to show or hide the second text field
        AndOr.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("AND") || newValue.equals("OR")) {
                    value2.setVisible(true);
                    category2.setVisible(true);
                } else {
                	value2.setVisible(false);
                	category2.setVisible(false);
                }
            }
        });

        // Hide the second text field by default
        value2.setVisible(false);
        category2.setVisible(false);

        // Create a VBox layout to hold the UI elements
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll( category1,value1, AndOr, category2,value2, button);

        

        // Set the VBox as the root node of the popup window
        Scene scene = new Scene(vbox);
        popup.setScene(scene);

        // Show the popup window
        popup.show();
    	
    	
    }

}
