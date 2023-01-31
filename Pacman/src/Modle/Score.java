package Modle;


import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class Score {

    public Text score_text;
    public Text lifes;
    
    
    public Score(Group root) {
        
    	this.score_text = new Text(BarObstacle.THICKNESS * 4, BarObstacle.THICKNESS * 28, "Score: 0");
        this.lifes = new Text(BarObstacle.THICKNESS * 20, BarObstacle.THICKNESS * 28,"Lifes: 3");
        score_text.setFill(Color.MAGENTA);
        score_text.setFont(Font.font("Arial", 30));
        lifes.setFill(Color.MAROON);
        lifes.setFont(Font.font("Arial", 30));
        root.getChildren().add(score_text);
        root.getChildren().add(lifes);
    }
    
    
    
}
