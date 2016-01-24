package model;

import java.util.ArrayList;

/**
 * Model. Zawiera wszystkie istotne informacje o rozgrwce.
 */


public class ArenaModel
{
    // Określa ilość pól na które podzielona jest plansza (rozmiar arenaView / BodyPart size)
    public static final int WIDTH = 40;
    public static final int HEIGHT = 40;

    private Snake snake;
    private ArrayList<Fruit> fruitList;
    private GameState gameState;
    private int score;

    public ArenaModel()
    {
        score = 0;
        snake = new Snake();
        fruitList = new ArrayList<>();
        gameState = GameState.STARTED;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public Snake getSnake()
    {
        return snake;
    }

    public void setSnake(Snake snake)
    {
        this.snake = snake;
    }

    public void incrementSnakeSize()
    {
        snake.setSize(snake.getSize() + 1);
    }

    public void setGameState(GameState gameState)
    {
        this.gameState = gameState;
    }

    public GameState getGameState()
    {
        return gameState;
    }

    public ArrayList<Fruit> getFruitList()
    {
        return fruitList;
    }

    public void setFruitList(ArrayList<Fruit> fruitList)
    {
        this.fruitList = fruitList;
    }

    public void addFood(Fruit fruit)
    {
        fruitList.add(fruit);
    }

    public void removeFood(int index)
    {
        fruitList.remove(index);
    }

}
