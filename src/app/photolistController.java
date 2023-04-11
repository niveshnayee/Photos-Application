package app;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private ChoiceBox<?> copyDrag;

    @FXML
    private ImageView img;

    @FXML
    private ChoiceBox<?> moveDrag;

    @FXML
    private ListView<String> picView;
    private ObservableList<String> data = FXCollections.observableArrayList();

    @FXML
    private ListView<?> tags;
    
    private int selectedIndex = -1;
    
    public void initialize() throws MalformedURLException {
        // Set the cell factory to display images in the list view
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
                              // Load the image from the file path
                              Image image = new Image("file:"+item);
                              ImageView imageView = new ImageView();
//                              Label caption = new Label("Myself");
//                              Label date = new Label("03/22/2019");
//                              Label Time = new Label("3:58 PM");
                              imageView.setFitWidth(150);
                              imageView.setFitHeight(120);
                              imageView.setImage(image);
                              
                              // Set the image and text in the cell
                              // setText(item);
                              
                              HBox hb = new HBox();
//                              VBox vb = new VBox();
//                              vb.getChildren().addAll(caption, date, Time);
//                              vb.setPadding(new Insets(40, 0, 0, 40));
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
        if (file != null) {
        	//System.out.println(file.getPath());
        	if(User.albumName.photos == null)
        	{
        		User.albumName.photos = new ArrayList<photoList>();
        	}
        	TextInputDialog dialog = new TextInputDialog();
        	dialog.setTitle("Add Name");
        	dialog.setHeaderText("Enter your name:");
        	dialog.setContentText("Name:");

//        	final Optional<ButtonType> result = alert.showAndWait();
        	selectedIndex = picView.getSelectionModel().getSelectedIndex();
    		photoList photo = User.albumName.photos.get(selectedIndex);
        	Optional<String> result = dialog.showAndWait();
        	if (result.isPresent())
        	{
        		photo.caption = result.get();
        	}
        	photo.caption = "none";
        	User.albumName.photos.add(new photoList(file.getPath()));
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
    void copy(ActionEvent event) {

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
    	
    }

    @FXML
    void recaption(ActionEvent event) 
    {
    	
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
        	
    	}
    }

    @FXML
    void removeTag(ActionEvent event) 
    {
    	
    }

    @FXML
    void slideShow(ActionEvent event) 
    {
    	
    }

}
