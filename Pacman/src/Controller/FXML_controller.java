package Controller;

import Modle.BarObstacle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class FXML_controller {
	@FXML ColorPicker bgcolor;
	@FXML ColorPicker wallcolor;
	
	public static Color wall;
	public static Color background = Color.WHITE;


	/**
	 * The button of start
	 * @param start
	 */
	public void startgame(ActionEvent start) {

	    Group root = new Group();
	    Scene theScene = new Scene(root,1225,700);
	        
	    Node source = (Node) start.getSource();
		Stage homepage = (Stage) source.getScene().getWindow();
	    homepage.setScene( theScene );
	        
	    Canvas canvas = new Canvas( 1225, 600 );
	    
	    root.getChildren().add( canvas );
	    theScene.setFill(background);
	    GameManager gameManager = new GameManager(root);

	    gameManager.drawBoard();

	    theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.startGame(event));
	    theScene.addEventHandler(KeyEvent.KEY_RELEASED, event -> gameManager.stopPacman(event));
	    theScene.addEventHandler(KeyEvent.KEY_PRESSED, event -> gameManager.restartGame(event));

	    homepage.show();
		   
	   }
	   
	
	/**
	 * The button of setup
	 * @param setup
	 * @throws Exception
	 */
	public void setup(ActionEvent setup) throws Exception {
			
		Parent root = FXMLLoader.load(getClass().getResource("../View/Setup_page.fxml"));
		Scene theScene = new Scene(root, 1280, 720);
		Node source = (Node) setup.getSource();
		Stage homepage = (Stage) source.getScene().getWindow();
	    homepage.setScene( theScene );
	        
	}

	
	/**
	 * The button of back
	 * @param back
	 * @throws Exception
	 */
	public void backstart(ActionEvent back) throws Exception {
		
		getColor();
		Parent root = FXMLLoader.load(getClass().getResource("../View/Start_page.fxml"));
		Scene theScene = new Scene(root, 1280, 720);
		Node source = (Node) back.getSource();
	    Stage homepage = (Stage) source.getScene().getWindow();
        homepage.setScene( theScene );
        
	}

	
	/**
	 * The colorpicker's controller, which could get a color 
	 */
	public void getColor() {
		FXML_controller.background = bgcolor.getValue();
		BarObstacle.wall = wallcolor.getValue();
	}
	
	
}


