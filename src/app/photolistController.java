package app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class photolistController {

	@FXML
	private Button addpic;
	
	@FXML
	private Button deletepic;
	
    @FXML
    private Label albumName;
    
    @FXML
    private Label caption;
    
    @FXML
    private Label photoDate;

    @FXML
    private TextField catValue;

    @FXML
    private Button category;

    @FXML
    private ChoiceBox<?> categoryDrag;

    @FXML
    private ChoiceBox<album> copyDrag;

    @FXML
    private ImageView img;

    @FXML
    private ChoiceBox<album> moveDrag;

    @FXML
    private ListView<String> picView;
    private ObservableList<String> data = FXCollections.observableArrayList();

    @FXML
    private ListView<?> tags;
    
//    public static String oldDate = "00-00-0000", newDate = "99-99-9999";
    //public static LocalDateTime oldestDate = null, newestDate = null;
    
    
    private int selectedIndex = -1;
    
    public void initialize() throws MalformedURLException {
        // Set the cell factory to display images in the list view
    	
    	copyDrag.getItems().addAll(database.userObj.albums);
    	moveDrag.getItems().addAll(database.userObj.albums);
    	
    	if(User.albumName.photos != null) 
    	{
    		for(photoList pic : User.albumName.photos)
    		{
    			data.add(pic.path);
    		}
    	}
    	
    	picView.setItems(data);
		picView.requestFocus();
		
    	picView.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
        {
              @Override
              public ListCell<String> call(ListView<String> listView)
              {
                  return new ListCell<String>()
                  {
                     

                      @Override
                      protected void updateItem(String item, boolean empty)
                      {
                          super.updateItem(item, empty);
                          if(empty || item == null)
                          {
                              setText(null);
                              setGraphic(null);
                          }
                          else
                          {
                              Image image = new Image("file:"+item);
                              ImageView imageView = new ImageView();
                              imageView.setFitWidth(150);
                              imageView.setFitHeight(120);
                              imageView.setImage(image);
                              

                              
                              HBox hb = new HBox();
                              hb.getChildren().addAll(imageView);
                              setGraphic(hb);
                          }
                      }
                  };
              }
        });
        
        picView.setOnMouseClicked(e -> {
        	
            String selectedFile = picView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) 
            {
            	Image image = new Image("file:"+selectedFile);
				img.setImage(image);
				
				selectedIndex = picView.getSelectionModel().getSelectedIndex();
	    		photoList photo = User.albumName.photos.get(selectedIndex);
				caption.setText(photo.caption);
				photoDate.setText(photo.dateNTime);
				
				
            }else
            {
            	img.setVisible(false);
            	caption.setVisible(false);
				photoDate.setVisible(false);
            }
        });
    }

    @FXML
    void addPic(ActionEvent event) throws MalformedURLException {
    	
        // Create a file chooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose image Image");

        // Set the filter for image files
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image files (*.HEIC, *.png, *.jpg, *.gif)", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser dialog
        File file = fileChooser.showOpenDialog(null);
        if (file != null && !data.contains(file.getPath())) {
        	//System.out.println(file.getPath());
        	if(User.albumName.photos == null)
        	{
        		User.albumName.photos = new ArrayList<photoList>();
        	}
        	TextInputDialog dialog = new TextInputDialog();
        	dialog.setTitle("Add Caption");
        	dialog.setHeaderText("Enter your Caption:");
        	dialog.setContentText("Caption:");

//        	final Optional<ButtonType> result = alert.showAndWait();
//        	selectedIndex = picView.getSelectionModel().getSelectedIndex();
//    		photoList photo = User.albumName.photos.get(selectedIndex);
			LocalDateTime currentDateTime = LocalDateTime.now();
			if(User.albumName.oldDate == null || currentDateTime.isBefore(User.albumName.oldDate))
			{
				User.albumName.oldDate = currentDateTime;
			}
			
			if(User.albumName.newDate == null || currentDateTime.isAfter(User.albumName.newDate))
			{
				User.albumName.newDate = currentDateTime;
			}
            
            // Format the date and time
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
            String formattedDateTime = currentDateTime.format(formatter);
            
//            if(Integer.parseInt(formattedDateTime.substring(6)) > Integer.parseInt(oldDate.substring(6)))
//            {
//            	
//            }
            
        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent() && result.get() != "")
        		User.albumName.photos.add(new photoList(file.getPath(), result.get(),formattedDateTime));
        	else
        		User.albumName.photos.add(new photoList(file.getPath(), "none", formattedDateTime));
        	
            data.add(file.getPath());
        }
    }

    @FXML
    void addTag(ActionEvent event) 
    {
    	
    }

    @FXML
    void back(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("album.fxml"));
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = (AnchorPane)loader.load();
		mainStage.setScene(new Scene(root));
		mainStage.show();
    }

    @FXML
    void copy(ActionEvent event) 
    {
    	selectedIndex = picView.getSelectionModel().getSelectedIndex();
    	album alb = copyDrag.getSelectionModel().getSelectedItem();
    	if(alb != null && selectedIndex != -1)
    	{
    		if(alb.photos.size() == 0) 
    		{
    			
    			alb.photos.add(User.albumName.photos.get(selectedIndex));
    		}
    		else
    		{
    			if(!alb.photos.contains(User.albumName.photos.get(selectedIndex)))
    				alb.photos.add(User.albumName.photos.get(selectedIndex));
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
    void move(ActionEvent event) 
    {
    	selectedIndex = picView.getSelectionModel().getSelectedIndex();
    	album alb = moveDrag.getSelectionModel().getSelectedItem();
    	
    	if(alb != null && selectedIndex != -1)
    	{
    		System.out.println("in");
    		if(alb.photos.size() == 0) 
    		{
    			
    			alb.photos.add(User.albumName.photos.get(selectedIndex));
    			User.albumName.photos.remove(selectedIndex);
    			data.remove(selectedIndex);
    		}
    		else
    		{
    			if(!alb.photos.contains(User.albumName.photos.get(selectedIndex)))
    			{
    				alb.photos.add(User.albumName.photos.get(selectedIndex));
    				User.albumName.photos.remove(selectedIndex);
    				data.remove(selectedIndex);
    			}	
    		}
    		
    		picView.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
        	        0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
        	        true, true, true, true, true, true, null));
    	}
    }

    @FXML
    void recaption(ActionEvent event) 
    {
    	if(data.size() > 0)
    	{
    		TextInputDialog dialog = new TextInputDialog();
        	dialog.setTitle("Re-Caption");
        	dialog.setHeaderText("Enter your Caption:");
        	dialog.setContentText("Caption:");

//        	final Optional<ButtonType> result = alert.showAndWait();
        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent() && result.get() != "")
        	{
//        	    String name = result.get();
        	    selectedIndex = picView.getSelectionModel().getSelectedIndex();
        	    LocalDateTime currentDateTime = LocalDateTime.now();
        	    
        	    if(User.albumName.oldDate == null || currentDateTime.isBefore(User.albumName.oldDate))
    			{
        	    	User.albumName.oldDate = currentDateTime;
    			}
    			
    			if(User.albumName.newDate == null || currentDateTime.isAfter(User.albumName.newDate))
    			{
    				User.albumName.newDate = currentDateTime;
    			}
                
                // Format the date and time
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
                String formattedDateTime = currentDateTime.format(formatter);
                
        	    User.albumName.photos.get(selectedIndex).caption = result.get();
        	    User.albumName.photos.get(selectedIndex).dateNTime = formattedDateTime;
        	    
        	    picView.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
            	        0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
            	        true, true, true, true, true, true, null));

        	}
    	}
    }

    @FXML
    void removePic(ActionEvent event) 
    {
    	selectedIndex = picView.getSelectionModel().getSelectedIndex();
    	if(selectedIndex == 0 && data.size() < 0)
    		selectedIndex = -1;
    	
    	if(selectedIndex > -1)
    	{
    		selectedIndex = picView.getSelectionModel().getSelectedIndex();
        	User.albumName.photos.remove(selectedIndex);
        	//removeAlbum(data.get(selectedIndex));
        	
        	data.remove(selectedIndex);
            	//selectedIndex = picView.getSelectionModel().getSelectedIndex();
            	picView.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
            	        0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
            	        true, true, true, true, true, true, null));
        	
        	
    	}
    }

    @FXML
    void removeTag(ActionEvent event) 
    {
    	
    }

    @FXML
    void slideShow(ActionEvent event) throws IOException 
    {
    	FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("slideshow.fxml"));
		Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		AnchorPane root = (AnchorPane)loader.load();
		mainStage.setScene(new Scene(root));
		mainStage.show();
    }

}
