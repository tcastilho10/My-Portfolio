package game;

import com.codeforall.online.simplegraphics.graphics.Text;
import com.codeforall.online.simplegraphics.pictures.Picture;
import field.Field;
import fruits.Fruit;
import snake.Snake;

import java.io.IOException;


public class Game {

    private Field field;
    private Snake snake;
    private Fruit[] fruits;
    private Fruit fruit;
    private Direction direction;
    private int threadSleepUnits = 200;
    private final int INITIAL_THREAD_SLEEP_UNITS = 200;
    private int score = 0;
    private Text scoreDisplay;
    private Text highScoreDisplay;
    private int scoreToReach = 10;
    private String headDirectionPath;
    private int highestScore;
    private MyHighScore highScore;
    private boolean gameIsRunning = true;


    public Game(Field field, Snake snake) {
        scoreDisplay = new Text(130,30,"" + score);
        scoreDisplay.draw();
        direction = Direction.DOWN;
        this.field = field;
        this.snake = snake;
        createFruits();
        highScore = new MyHighScore();
        highestScore = highScore.loadHighScore();
        highScoreDisplay = new Text(1400,30,"" + highestScore);
        highScoreDisplay.draw();

    }

    public void createFruits() {
        fruits = new Fruit[1];

        for (int i = 0; i < fruits.length; i++) {
            fruits[i] = new Fruit(field);
        }

    }

    public boolean checkCollisionWithFruits() {
        for (Fruit f : fruits) {
            if (snake.getRow() == f.getRow() && snake.getCol() == f.getCol()) {
                fruit = f;
                return true;
            }
        }

         return false;
    }

    public void deleteFruit () {
           for (int i = 0; i < fruits.length; i++) {

               if (snake.getRow() == fruits[i].getRow() && snake.getCol() == fruits[i].getCol()) {
                   fruit.getFruitPic().delete();
                   updateScore(fruit.getFruitScore());
                   fruits[i] = new Fruit(field);
               }

           }

    }

    private void updateScore(int scoreToAdd){
        System.out.println("updates core");
        score += scoreToAdd;
        System.out.println(score);
        scoreDisplay.setText("" + score);
        updateSpeed();
    }

    private void updateSpeed(){

        if(score > scoreToReach) {
            scoreToReach = scoreToReach*2;
            if(threadSleepUnits > 50) {
                threadSleepUnits = threadSleepUnits-50;
                System.out.println("thread: "+threadSleepUnits);
            }
        }

    }

    public void start() throws InterruptedException, IOException {

        while (gameIsRunning) {
            if (snake.isCrashed()){
                gameIsRunning = false;
                break;
            }

            Thread.sleep(threadSleepUnits);
            moveInDirection();
            eatingFruitAndGrowing();
            updateSpeed();
        }


        /*while (!snake.isCrashed()){

            Thread.sleep(threadSleepUnits);

            moveInDirection();
            eatingFruitAndGrowing();

        }*/


        if(score > highestScore) {
            highScore.saveHighScore(score);
        }

        Picture gameOverPic = new Picture(500,175,"resources/game-over(1).png");
        gameOverPic.draw();
        Picture restartGamePic = new Picture(250,700,"resources/RestartTransp.png");
        restartGamePic.draw();

        pressRToRestart();

    }

    private void pressRToRestart() throws InterruptedException, IOException {
        while (!gameIsRunning) {
            Thread.sleep(100);
        }
        restartGame();
    }

    private void restartGame() throws IOException, InterruptedException {
        score = 0;
        field.init();
        snake = new Snake(field);
        createFruits();
        scoreDisplay.setText("" + score);
        threadSleepUnits = INITIAL_THREAD_SLEEP_UNITS;
        scoreToReach = 10;
        start();
    }

    private void moveInDirection(){

        switch (direction) {

            case UP:
                snake.moveUp();
                break;
            case DOWN:
                snake.moveDown();
                break;
            case RIGHT:
                snake.moveRight();
                break;
            case LEFT:
                snake.moveLeft();
                break;

        }



    }

    private void eatingFruitAndGrowing(){
        if(checkCollisionWithFruits()){
            snake.growSnake();
            deleteFruit();
        }
    }

    public void setDirection(Direction direction){
        this.direction = direction;
        setHeadDirectionPath();
        snake.redrawHead(headDirectionPath);
    }

    private void setHeadDirectionPath(){
        switch (direction){

            case UP:
                headDirectionPath = "resources/snake (3).png";
                break;

            case DOWN:
                headDirectionPath = "resources/snake (1).png";
                break;

            case RIGHT:
                headDirectionPath = "resources/snake (2).png";
                break;

            case LEFT:
                headDirectionPath = "resources/snake (4).png";
                break;
        }

    }

    public void setGameIsRunning(boolean gameIsRunning) {
        this.gameIsRunning = gameIsRunning;
    }


}


