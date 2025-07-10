
import field.Field;
import game.Game;
import input.MyKeyboard;
import snake.Snake;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {

        Field field = new Field(60,38);
        field.init();

        MyKeyboard keyboard = new MyKeyboard();
        Snake snake = new Snake(field);
        keyboard.setSnake(snake);
        keyboard.init();

        Game game = new Game(field, snake);
        keyboard.setGame(game);

        game.start();


    }


}