package Controller;


import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import java.util.HashSet;
import java.util.Set;
import Modle.BarObstacle;
import Modle.Cookie;
import Modle.Ghost;
import Modle.Maze;
import Modle.Pacman;
import Modle.Score;


public class GameManager {

	public static Color background = Color.BLACK;
    private Pacman pacman;
    private Group root;
    private Set<Cookie> cookieSet;
    private Set<Ghost> ghosts;
    private AnimationTimer leftPacmanAnimation;
    private AnimationTimer rightPacmanAnimation;
    private AnimationTimer upPacmanAnimation;
    private AnimationTimer downPacmanAnimation;
    private Maze maze;
    private int lifes;
    public Score scoreBoard;
    private boolean gameEnded;
    public static int score;

    /**
     * Constructor
     */
    public GameManager(Group root) {
        this.root = root;
        this.maze = new Maze();
        this.ghosts = new HashSet<>();
        this.cookieSet = new HashSet<>();
        this.pacman = new Pacman(2.5 * BarObstacle.THICKNESS, 2.5 * BarObstacle.THICKNESS, maze, cookieSet, ghosts, this);
        this.leftPacmanAnimation = pacman.createAnimation("left");
        this.rightPacmanAnimation = pacman.createAnimation("right");
        this.upPacmanAnimation = pacman.createAnimation("up");
        this.downPacmanAnimation = pacman.createAnimation("down");
        this.lifes = 3;
        score = 0;
        Pacman.cookiesEaten = 0;
    }

    /**
     * Set one life less
     */
    public void lifeLost() {
        this.leftPacmanAnimation.stop();
        this.rightPacmanAnimation.stop();
        this.upPacmanAnimation.stop();
        this.downPacmanAnimation.stop();
        for (Ghost ghost : ghosts) {
            ghost.getAnimation().stop();
        }
        this.pacman.setCenterX(2.5 * BarObstacle.THICKNESS);
        this.pacman.setCenterY(2.5 * BarObstacle.THICKNESS);
        lifes--;
        score -= 10;
        scoreBoard.lifes.setText("Lifes: " + this.lifes);
        scoreBoard.score_text.setText("Score: " + score);
        if (lifes == 0) {
            this.endGame();
        }
    }

    /**
     * Ends the game
     */
    public void endGame() {
        this.gameEnded = true;
        root.getChildren().remove(pacman);
        for (Ghost ghost : ghosts) {
            root.getChildren().remove(ghost);
        }
        javafx.scene.text.Text endGame = new javafx.scene.text.Text("Game Over, press ESC to restart");
        endGame.setX(BarObstacle.THICKNESS * 3);
        endGame.setY(BarObstacle.THICKNESS * 28);
        endGame.setFont(Font.font("Arial", 40));
        endGame.setFill(Color.ROYALBLUE);
        root.getChildren().remove(scoreBoard.score_text);
        root.getChildren().remove(scoreBoard.lifes);
        root.getChildren().add(endGame);
    }

    /**
     * Restart the game
     * @param event
     */
    public void restartGame(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && gameEnded) {
            root.getChildren().clear();
            this.cookieSet.clear();
            this.ghosts.clear();
            this.drawBoard();
            this.pacman.setCenterX(2.5 * BarObstacle.THICKNESS);
            this.pacman.setCenterY(2.5 * BarObstacle.THICKNESS);
            this.lifes = 3;
            score = 0;
            Pacman.cookiesEaten = 0;
            gameEnded = false;
        }
    }

    /**
     * Draws the board of the game with the cookies and the Pacman
     */
    public void drawBoard() {
        this.maze.CreateMaze(root);
        this.maze.addcookies(root, cookieSet);
        root.getChildren().add(this.pacman);
        this.ghosts.add(new Ghost(18.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, "View/ghost1.png", maze, this, pacman));
        this.ghosts.add(new Ghost(22.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, "View/ghost2.png", maze, this, pacman));
        this.ghosts.add(new Ghost(28.5 * BarObstacle.THICKNESS, 12.5 * BarObstacle.THICKNESS, "View/ghost3.png", maze, this, pacman));
        this.ghosts.add(new Ghost(28.5 * BarObstacle.THICKNESS, 9.5 * BarObstacle.THICKNESS, "View/ghost4.png", maze, this, pacman));
        root.getChildren().addAll(this.ghosts);
        scoreBoard = new Score(root);
    }


    /**
     * Moves the pacman
     * @param event
     */
    public void startGame(KeyEvent event) {
        for (Ghost ghost : this.ghosts) {
            ghost.run();
        }
        switch(event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.start();
                break;
            case LEFT:
                this.leftPacmanAnimation.start();
                break;
            case UP:
                this.upPacmanAnimation.start();
                break;
            case DOWN:
                this.downPacmanAnimation.start();
                break;
		default:
			break;
        }
    }

    /**
     * Stops the pacman
     * @param event
     */
    public void stopPacman(KeyEvent event) {
        switch(event.getCode()) {
            case RIGHT:
                this.rightPacmanAnimation.stop();
                break;
            case LEFT:
                this.leftPacmanAnimation.stop();
                break;
            case UP:
                this.upPacmanAnimation.stop();
                break;
            case DOWN:
                this.downPacmanAnimation.stop();
                break;
		default:
			break;
        }
    }
}



