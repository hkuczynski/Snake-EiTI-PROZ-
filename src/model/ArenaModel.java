package model;

import com.sun.javafx.scene.traversal.Direction;

import java.util.ArrayList;
import java.util.Random;

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
    Random generator = new Random(); // potrzebny do generowania losowych współrzędnych

    public ArenaModel()
    {
        score = 0;
        snake = new Snake();
        fruitList = new ArrayList<>();
        gameState = GameState.STARTED;
    }

    /**
    * Funkcja zmieniająca położenie węża w zależności od kierunku w którym ma się poruszać.
    * Dodaje nową część na początku węża.
    */
    public void updateSnake(Direction direction)
    {
        switch(direction)
        {
            case RIGHT:
                if (snake.getxCoor() <= 0)
                {
                    snake.setxCoor(WIDTH);
                }
                else
                {
                    snake.setxCoor(snake.getxCoor() - 1);
                }
                break;
            case LEFT:
                if (snake.getxCoor() >= WIDTH)
                {
                    snake.setxCoor(0);
                }
                else
                {
                    snake.setxCoor(snake.getxCoor() + 1);
                }
                break;
            case UP:
                if (snake.getyCoor() <= 0)
                {
                    snake.setyCoor(HEIGHT);
                }
                else
                {
                    snake.setyCoor(snake.getyCoor() - 1);
                }
                break;
            case DOWN:
                if (snake.getyCoor() >= HEIGHT)
                {
                    snake.setyCoor(0);
                }
                else
                {
                    snake.setyCoor(snake.getyCoor() + 1);
                }
                break;
        }

        snake.addPart();
        snake.updateSize();
    }

    /**
     * Funkcja tworząca nowe owoce. Zostają one dodane jeżeli na planszy pozostanie mniej niż 4.
     */
    public void updateFood()
    {
        int xCoor, yCoor, partSnakeX, partSnakeY;
        boolean collision = false;

        if(fruitList.size() < 4)
        {
            xCoor = generator.nextInt(ArenaModel.WIDTH);
            yCoor = generator.nextInt(ArenaModel.HEIGHT);

            for(int i = 0; i < snake.getSize() - 1; i++)
            {
                partSnakeX = snake.getPart(i).getxCoor();
                partSnakeY = snake.getPart(i).getyCoor();

                if(partSnakeX == xCoor && partSnakeY == yCoor)
                {
                    collision = true;
                }
            }

            if(!collision)
            {
                addFood(new Fruit(xCoor, yCoor));
            }

        }
    }

    /**
     * Funkcja sprawdzająca czy wąż najechał na owoc.
     */
    public void checkIfEaten()
    {
        BodyPart snakesHead = snake.getPart(snake.getSize() - 1);
        int foodXCoor, foodYCoor, headXCoor, headYCoor;

        headXCoor = snakesHead.getxCoor();
        headYCoor = snakesHead.getyCoor();

        for(int i = 0; i < fruitList.size(); i++)
        {
            foodXCoor = fruitList.get(i).getxCoor();
            foodYCoor = fruitList.get(i).getyCoor();

            if(foodXCoor == headXCoor && foodYCoor == headYCoor)
            {
                removeFood(i);
                incrementSnakeSize();

                score++;
            }
        }

    }

    /**
     * Funkcja sprawdzająca czy wąż najechał na siebie.
     */
    public void checkSelfCollision()
    {
        BodyPart snakesHead = snake.getPart(snake.getSize() - 1);
        int partXCoor, partYCoor, headXCoor, headYCoor;

        headXCoor = snakesHead.getxCoor();
        headYCoor = snakesHead.getyCoor();

        for(int i = 0; i < snake.getSize() - 1; i++)
        {
            partXCoor = snake.getPart(i).getxCoor();
            partYCoor = snake.getPart(i).getyCoor();

            if(partXCoor == headXCoor && partYCoor == headYCoor)
            {
                snake.resetSize();
                gameState = GameState.ENDED;
            }
        }
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
