package input;

import com.codeforall.online.simplegraphics.keyboard.Keyboard;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEvent;
import com.codeforall.online.simplegraphics.keyboard.KeyboardEventType;
import com.codeforall.online.simplegraphics.keyboard.KeyboardHandler;
import game.Direction;
import game.Game;
import snake.Snake;

import java.io.IOException;

public class MyKeyboard implements KeyboardHandler {

    private Keyboard keyboard;
    private Snake snake;
    private Game game;



    public void setSnake(Snake snake) {
        this.snake = snake;
    }


    public void init() {
        keyboard = new Keyboard(this);

        KeyboardEvent right = new KeyboardEvent();
        right.setKey(KeyboardEvent.KEY_RIGHT);
        right.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent left = new KeyboardEvent();
        left.setKey(KeyboardEvent.KEY_LEFT);
        left.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent up = new KeyboardEvent();
        up.setKey(KeyboardEvent.KEY_UP);
        up.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent down = new KeyboardEvent();
        down.setKey(KeyboardEvent.KEY_DOWN);
        down.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent restart = new KeyboardEvent();
        restart.setKey(KeyboardEvent.KEY_R);
        restart.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        keyboard.addEventListener(right);
        keyboard.addEventListener(left);
        keyboard.addEventListener(up);
        keyboard.addEventListener(down);
        keyboard.addEventListener(restart);
    }

    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_RIGHT) {
            game.setDirection(Direction.RIGHT);
        }

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_LEFT) {
            game.setDirection(Direction.LEFT);
        }

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_UP) {
            game.setDirection(Direction.UP);
        }

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_DOWN) {
            game.setDirection(Direction.DOWN);
        }

        if (keyboardEvent.getKey() == KeyboardEvent.KEY_R) {
            game.setGameIsRunning(true);

        }

    }


    @Override
    public void keyReleased (KeyboardEvent keyboardEvent){

    }

    public void setGame(Game game) {
        this.game = game;
    }
}