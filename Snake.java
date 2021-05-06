package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    private List<GameObject> snakeParts = new ArrayList<>();
    private Direction direction = Direction.LEFT;
    public boolean isAlive = true;

    Snake(int x, int y) {

        GameObject peace1 = new GameObject(x, y);
        GameObject peace2 = new GameObject(x + 1, y);
        GameObject peace3 = new GameObject(x + 2, y);

        snakeParts.add(peace1);
        snakeParts.add(peace2);
        snakeParts.add(peace3);
    }

    public void draw(Game game) {
        int x = snakeParts.get(0).x;
        int y = snakeParts.get(0).y;

        if (isAlive == true) {
            game.setCellValueEx(x, y, Color.NONE, HEAD_SIGN, Color.BLACK, 75);           //Alive
        } else {
            game.setCellValueEx(x, y, Color.NONE, HEAD_SIGN, Color.RED, 75);             //Dead
        }

        for (int i = 1; i < snakeParts.size(); i++) {
            int x1 = snakeParts.get(i).x;
            int y1 = snakeParts.get(i).y;

            if (isAlive == true) {
                game.setCellValueEx(x1, y1, Color.NONE, BODY_SIGN, Color.BLACK, 75);     //Alive
            } else {
                game.setCellValueEx(x1, y1, Color.NONE, BODY_SIGN, Color.RED, 75);       //Dead
            }
        }

    }

    public boolean checkCollision(GameObject object) {
        for (int i = 0; i < snakeParts.size(); i++) {
            int x = snakeParts.get(i).x;
            int y = snakeParts.get(i).y;

            if (object.x == x && object.y == y) {
                return true;
            }
        }
        return false;
    }

    public void setDirection(Direction direction) {

        if (this.direction == Direction.UP && direction == Direction.DOWN
                || this.direction == Direction.DOWN && direction == Direction.UP
                || this.direction == Direction.LEFT && direction == Direction.RIGHT
                || this.direction == Direction.RIGHT && direction == Direction.LEFT) {

        } else if (this.direction == Direction.LEFT && snakeParts.get(0).x == snakeParts.get(1).x
                || this.direction == Direction.RIGHT && snakeParts.get(0).x == snakeParts.get(1).x
                || this.direction == Direction.UP && snakeParts.get(0).y == snakeParts.get(1).y
                || this.direction == Direction.DOWN && snakeParts.get(0).y == snakeParts.get(1).y) {

        } else {
            this.direction = direction;
        }
    }

    public GameObject createNewHead() {

        if (direction == Direction.LEFT) {
            int x = snakeParts.get(0).x;
            int y = snakeParts.get(0).y;
            GameObject object = new GameObject(x - 1, y);
            return object;
        } else if (direction == Direction.RIGHT) {
            int x = snakeParts.get(0).x;
            int y = snakeParts.get(0).y;
            GameObject object = new GameObject(x + 1, y);
            return object;
        } else if (direction == Direction.UP) {
            int x = snakeParts.get(0).x;
            int y = snakeParts.get(0).y;
            GameObject object = new GameObject(x, y - 1);
            return object;
        } else {
            int x = snakeParts.get(0).x;
            int y = snakeParts.get(0).y;
            GameObject object = new GameObject(x, y + 1);
            return object;
        }
    }

    public void move(Apple apple) {
        GameObject head = createNewHead();

        if (head.x < 0 || head.x > SnakeGame.WIDTH - 1 || head.y < 0 || head.y > SnakeGame.HEIGHT - 1) {
            isAlive = false;
        } else {
            boolean equal = checkCollision(head);

            if (equal == false) {
                snakeParts.add(0, head);

                if (apple.x == head.x && apple.y == head.y) {
                    apple.isAlive = false;
                } else {
                    removeTail();
                }
            } else {
                isAlive = false;
            }
        }
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public int getLength() {
        return snakeParts.size();
    }
}
