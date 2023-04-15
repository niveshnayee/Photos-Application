/**
 * @author Nivesh Nayee 
 * @author Manan Patel
 */
package app;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class slideshowController {

    @FXML
    private Button back;

    @FXML
    private ImageView bigimage;

    @FXML
    private Button left;

    @FXML
    private Button logout;

    @FXML
    private Button right;
    

    
    private static int currentIndex = User.albumName.photos.indexOf(photolistController.picShow);
    public ArrayList<photoList> photos = User.albumName.photos;
    Image image;
    
    
    public void initialize()
    {
//        ArrayList<photoList> photos = User.albumName.photos;
        
        image = new Image("file:"+ photos.get(currentIndex).path);
        bigimage.setImage(image);
    }

    @FXML
    void back(ActionEvent event) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("photolist.fxml"));
        Stage mainStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        AnchorPane root = (AnchorPane)loader.load();
        mainStage.setScene(new Scene(root));
        mainStage.show();
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
    void movel(ActionEvent event) {
        if (currentIndex > 0) {
            currentIndex--;
            image = new Image("file:"+ photos.get(currentIndex).path);
            bigimage.setImage(image);
        }
        else
        {
        	currentIndex = photos.size()-1;
        	image = new Image("file:"+ photos.get(currentIndex).path);
            bigimage.setImage(image);
        }
    }

    @FXML
    void mover(ActionEvent event) {
        if (currentIndex < photos.size() - 1) {
            currentIndex++;
            image = new Image("file:"+ photos.get(currentIndex).path);
            bigimage.setImage(image);
        }
        else
        {
        	currentIndex = 0;
        	image = new Image("file:"+ photos.get(currentIndex).path);
            bigimage.setImage(image);
        }
    }
}
