package app;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.image.Image;

public class SearchController {
	
	public static LocalDate fromD;
	public static LocalDate toD;
	
	
	public static String cat1 = null;
	public static String val1 = null;
	
	public static String opt = null;
	
	public static String cat2 = null;
	public static String val2 = null;

	
	
    @FXML
    private ListView<String> photoList;
    private ObservableList<String> data = FXCollections.observableArrayList();
    
    
    public void initialize()
    {
    	if(fromD != null & toD != null)
    	{
    		for (Map.Entry<LocalDate, String> entry : database.userObj.dateSearch.entrySet()) 
        	{
        		String value = entry.getValue();
        		if( entry.getKey().isBefore(toD) && entry.getKey().isAfter(fromD))
        		{
        			data.add(value);
        		}
            }
    	}
    	else
    	{
    		if(opt == null && opt == "")
    			singleSearch();
    		else if(opt.equals("And"))
    			andSearch();
    		else
    			orSearch();
    	}
    	
    	
    	
    	photoList.setItems(data);
    	
    	photoList.setCellFactory(new Callback<ListView<String>, ListCell<String>>()
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
    void createAlbum(ActionEvent event) 
    {
    	TextInputDialog dialog = new TextInputDialog();
    	dialog.setTitle("Add Album Name");
    	dialog.setHeaderText("Enter Album name:");
    	dialog.setContentText("Album:");

//    	final Optional<ButtonType> result = alert.showAndWait();
    	Optional<String> result = dialog.showAndWait();
    	if (result.isPresent()){
    	    String name = result.get();
    	    
    	    if(name!= "" && database.userObj.getAlbum(name) == null)
        	{
    	    	database.userObj.addAlbum(name);
    	    	for(String p : data)
    	    	{
    	    		if(database.userObj.albums != null) 
    	        	{
    	        		for(album alb : database.userObj.albums)
    	        		{
    	        			if(alb!= null) 
    	        	    	{
    	        				
    	        	    		for(photoList pic : alb.photos)
    	        	    		{
    	        	    			if(alb != database.userObj.getAlbum(name) && pic.path.equals(p))
    	        	    			{
    	        	    		        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    	        	    		        LocalDate currDate = LocalDate.parse(pic.dateNTime.substring(0, 10), formatter);
    	        	    		        
    	        	    		        if(database.userObj.getAlbum(name).old == null || currDate.isBefore(database.userObj.getAlbum(name).old))
    	        	    				{
    	        	    		        	database.userObj.getAlbum(name).old = currDate;
    	        	    				}
    	        	    				
    	        	    				if(database.userObj.getAlbum(name).latest == null || currDate.isAfter(database.userObj.getAlbum(name).latest))
    	        	    				{
    	        	    					database.userObj.getAlbum(name).latest  = currDate;
    	        	    				}
    	        	    				database.userObj.getAlbum(name).photos.add(pic);
    	        	    				
    	        	    			}
    	        	    				
    	        	    				
    	        	    		}
    	        	    	}
    	        		}
    	        	}
    	    		
    	    		
            		//database.userObj.getAlbum(name).photos.add(new photoList(p, ,formattedDateTime));
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
    
    
    private void singleSearch()
    {
    	for(Tags t: database.userObj.tag)
    	{
    		if(t.tags.containsKey(cat1) && t.tags.containsValue(val1))
    		{
    			for(String p : t.photoPath)
    			{
    				data.add(p);
    			}
    		}
    	}
    }
    
    
    private void andSearch()
    {
    	for(Tags t: database.userObj.tag)
    	{
    		if(t.tags.containsKey(cat1) && t.tags.containsValue(val1)  && t.tags.containsKey(cat2) && t.tags.containsValue(val2))
    		{
    			for(String p : t.photoPath)
    			{
    				
    				data.add(p);
    			}
    		}
    	}
    }
    
    private void orSearch()
    {
    	for(Tags t: database.userObj.tag)
    	{
    		if(  (t.tags.containsKey(cat1) && t.tags.containsValue(val1) ) || (t.tags.containsKey(cat2) && t.tags.containsValue(val2)) )
    		{
    			for(String p : t.photoPath)
    			{
    				data.add(p);
    			}
    		}
    	}
    }

}

