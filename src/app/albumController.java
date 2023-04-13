package app;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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
                        		  image = new Image("file:/Users/niveshnayee/Desktop/IMG_3411.jpeg");
                              ImageView imageView = new ImageView();
                              Label album = new Label(item);
                              
                              if( database.userObj.getAlbum(item).oldDate != null)
                              {
                            	  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
                                  String formattedDateTime = database.userObj.getAlbum(item).oldDate.format(formatter);
                                  
                                  if(database.userObj.getAlbum(item).newDate != null)
                                  {
                                      String formattedDateTime2 = database.userObj.getAlbum(item).newDate.format(formatter);
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

    @FXML
    void delete(ActionEvent event) 
    {
    	selectedIndex = albums.getSelectionModel().getSelectedIndex();
    	if(selectedIndex == 0 && data.size() < 0)
    		selectedIndex = -1;
    	
    	if(selectedIndex > -1)
    	{
    		selectedIndex = albums.getSelectionModel().getSelectedIndex();
        	database.userObj.albums.remove(selectedIndex);
        	//removeAlbum(data.get(selectedIndex));
        	
        	data.remove(selectedIndex);
    	}
    }

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
