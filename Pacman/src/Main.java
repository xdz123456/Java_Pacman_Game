
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class, this class is used to open the whole program
 * @author Dongzhan Xie
 * @version 1.1 (Create the start page and jump)
 */

public class Main extends Application {

    public void start(Stage theStage) throws Exception{
    	
    	Parent root = FXMLLoader.load(getClass().getResource("View/Start_page.fxml"));
        theStage.setTitle( "Pacman" );
        //Change the Scene into 720p, remove the group into controller
        Scene theScene = new Scene(root, 1280, 720);
        theStage.setScene( theScene );
        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}