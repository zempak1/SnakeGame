package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {

    private Snake snake;
    private Apple apple;
    private int turnDelay;
    private boolean isGameStopped;
    private static final int GOAL = 28;
    private int score;
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;


    public void initialize() {

        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        isGameStopped = false;
        Snake snake = new Snake(WIDTH / 2, HEIGHT / 2);
        this.snake = snake;
        createNewApple();
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);
        score = 0;
        setScore(score);

    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "GAME OVER", Color.BLACK, 50);

    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "YOU WIN", Color.BLACK, 50);

    }

    private void createNewApple() {
        do {
            int x = getRandomNumber(WIDTH - 1);
            int y = getRandomNumber(HEIGHT - 1);
            Apple apple = new Apple(x, y);
            this.apple = apple;

        } while (snake.checkCollision(apple));

    }

    private void drawScene() {

        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.AZURE, "");
            }
        }

        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int x) {
        snake.move(apple);

        if (apple.isAlive == false) {
            score += 5;
            turnDelay -= 10;
            setScore(score);
            setTurnTimer(turnDelay);

            createNewApple();
        } else {
        }
        if (snake.isAlive == false) {
            gameOver();
        } else {
        }
        if (snake.getLength() > GOAL) {
            win();
        } else {
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        if (key == Key.LEFT) {
            snake.setDirection(Direction.LEFT);
        } else if (key == Key.RIGHT) {
            snake.setDirection(Direction.RIGHT);
        } else if (key == Key.UP) {
            snake.setDirection(Direction.UP);
        } else if (key == Key.DOWN) {
            snake.setDirection(Direction.DOWN);
        } else if (key == Key.SPACE && isGameStopped == true) {
            createGame();
        }
    }
}
