package Modle;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Set;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import Controller.GameManager;
import Modle.Maze;

public class Pacman extends Circle {

	public static int cookiesEaten;
	private GameManager gameManager;
	private Maze maze;
	private Set<Cookie> cookieSet;
	private double step = 5;
	private Set<Ghost> ghosts;
    
    public Pacman(double x, double y, Maze maze, Set<Cookie> cookieSet, Set<Ghost> ghosts,GameManager gameManager) {
        this.setCenterX(x);
        this.setCenterY(y);
        this.setRadius(25);
        this.maze = maze;
        this.cookieSet = cookieSet;
        this.ghosts = ghosts;
        this.gameManager = gameManager;
        //Fill with the png
        FileInputStream input = null;
        
		try {
			input = new FileInputStream("View/pacman.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
        Image pacman = new Image(input);
        ImagePattern pacman_IP = new ImagePattern(pacman); 
        this.setFill(pacman_IP);

    }
    
    /**
     * Checks if the Pacman touches cookies.
     * @param pacman
     * @param axis
     */
    public void checkCookieCoalition(String axis) {
        double pacmanCenterY = this.getCenterY();
        double pacmanCenterX = this.getCenterX();
        double pacmanLeftEdge = pacmanCenterX - this.getRadius();
        double pacmanRightEdge = pacmanCenterX + this.getRadius();
        double pacmanTopEdge = pacmanCenterY - this.getRadius();
        double pacmanBottomEdge = pacmanCenterY + this.getRadius();
        for (Cookie cookie:cookieSet) {
            double cookieCenterX = cookie.getCenterX();
            double cookieCenterY = cookie.getCenterY();
            double cookieLeftEdge = cookieCenterX - cookie.getRadius();
            double cookieRightEdge = cookieCenterX + cookie.getRadius();
            double cookieTopEdge = cookieCenterY - cookie.getRadius();
            double cookieBottomEdge = cookieCenterY + cookie.getRadius();
            
            if (axis.equals("x")) {
                // pacman goes right
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanRightEdge >= cookieLeftEdge && pacmanRightEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                    	GameManager.score += cookie.getValue();
                        cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes left
                if ((cookieCenterY >= pacmanTopEdge && cookieCenterY <= pacmanBottomEdge) && (pacmanLeftEdge >= cookieLeftEdge && pacmanLeftEdge <= cookieRightEdge)) {
                    if (cookie.isVisible()) {
                        GameManager.score += cookie.getValue();
                        cookiesEaten++;
                    }
                    cookie.hide();
                }
            } else {
                // pacman goes up
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanBottomEdge >= cookieTopEdge && pacmanBottomEdge <= cookieBottomEdge)) {
                    if (cookie.isVisible()) {
                    	GameManager.score += cookie.getValue();
                        cookiesEaten++;
                    }
                    cookie.hide();
                }
                // pacman goes down
                if ((cookieCenterX >= pacmanLeftEdge && cookieCenterX <= pacmanRightEdge) && (pacmanTopEdge <= cookieBottomEdge && pacmanTopEdge >= cookieTopEdge)) {
                    if (cookie.isVisible()) {
                    	GameManager.score += cookie.getValue();
                        cookiesEaten++;
                    }
                    cookie.hide();
                }
            }
            gameManager.scoreBoard.score_text.setText("Score: " + GameManager.score);
            if (cookiesEaten == cookieSet.size()) {
            	gameManager.endGame();
            }
        }
    }
    /**
     * Creates an animation of the movement.
     * @param direction
     * @return
     */
    public AnimationTimer createAnimation(String direction) {

        return new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
            switch (direction) {
                case "left":
                    if (!maze.isTouching(getCenterX() - getRadius(), getCenterY(), 15)) {
                        setCenterX(getCenterX() - step);
                        if (getCenterX() < 0){
                        	setCenterX(1225);
                        }
                        checkCookieCoalition("x");
                        checkGhostCoalition();
                        setRotate(180);
                    }
                    break;
                case "right":
                    if (!maze.isTouching(getCenterX() + getRadius(), getCenterY(), 15)) {
                        setCenterX(getCenterX() + step);
                        if (getCenterX() > 1225){
                        	setCenterX(0);
                        }
                        checkCookieCoalition("x");
                        checkGhostCoalition();
                        setRotate(0);
                    }
                    break;
                case "up":
                    if (!maze.isTouching(getCenterX(), getCenterY() - getRadius(), 15)) {
                        setCenterY(getCenterY() - step);
                        checkCookieCoalition("y");
                        checkGhostCoalition();
                        setRotate(270);
                    }
                    break;
                case "down":
                   if (!maze.isTouching(getCenterX(), getCenterY() + getRadius(), 15)) {
                       setCenterY(getCenterY() + step);
                       checkCookieCoalition("y");
                       checkGhostCoalition();
                       setRotate(90);
                   }
                   break;
            }
            }
        };
    }
    
    /**
     * Checks if pacman is touching a ghost
     */
    public void checkGhostCoalition() {
        double pacmanCenterY = getCenterY();
        double pacmanCenterX = getCenterX();
        double pacmanLeftEdge = pacmanCenterX - getRadius();
        double pacmanRightEdge = pacmanCenterX + getRadius();
        double pacmanTopEdge = pacmanCenterY - getRadius();
        double pacmanBottomEdge = pacmanCenterY + getRadius();
        for (Ghost ghost : ghosts) {
            double ghostLeftEdge = ghost.getX();
            double ghostRightEdge = ghost.getX() + ghost.getWidth();
            double ghostTopEdge = ghost.getY();
            double ghostBottomEdge = ghost.getY() + ghost.getHeight();
            if ((pacmanLeftEdge <= ghostRightEdge && pacmanLeftEdge >= ghostLeftEdge) || (pacmanRightEdge >= ghostLeftEdge && pacmanRightEdge <= ghostRightEdge)) {
                if ((pacmanTopEdge <= ghostBottomEdge && pacmanTopEdge >= ghostTopEdge) || (pacmanBottomEdge >= ghostTopEdge && pacmanBottomEdge <= ghostBottomEdge)) {
                	gameManager.lifeLost();
                }
            }
        }
    }


    
}

