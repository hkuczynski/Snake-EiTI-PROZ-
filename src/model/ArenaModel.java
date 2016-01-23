package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by hubert on 22.01.2016.
 */
public class ArenaModel
{
    //WIDTH equals ArenaView's panel WIDTH / BodyPart size
    private final int WIDTH = 40;
    private final int HEIGHT = 40;

    private Snake snake;
    private ArrayList<Food> foodList;
    private ArrayList<PlayerScore> highScores = null;

    private int score = 0, lives = 1;

    public ArenaModel()
    {
        this.snake = new Snake();
        foodList = new ArrayList<>();
        loadHighScores();
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getLives()
    {
        return lives;
    }

    public void setLives(int lives)
    {
        this.lives = lives;
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

    public void resetSnakeSize()
    {

    }

    public ArrayList<Food> getFoodList()
    {
        return foodList;
    }

    public void setFoodList(ArrayList<Food> foodList)
    {
        this.foodList = foodList;
    }

    public void addFood(Food food)
    {
        foodList.add(food);
    }

    public void removeFood(int index)
    {
        foodList.remove(index);
    }

    public int getWidth()
    {
        return WIDTH;
    }

    public int getHeight()
    {
        return HEIGHT;
    }


    public void saveHighScores()
    {
        highScores.add(new PlayerScore("Hubert", 2222));

        try
        {
            //String baseDir = getServletContext().getRealPath( "/" );
            FileOutputStream fileOut =
                    new FileOutputStream("high_scores.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(highScores);
            out.close();
            fileOut.close();
            System.out.printf("Serialized data is saved in /tmp/high_scores.ser");
        }catch(IOException i)
        {
            i.printStackTrace();
        }
    }

    public void loadHighScores()
    {
        try
        {
            FileInputStream fileIn = new FileInputStream("high_scores.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            highScores = (ArrayList<PlayerScore>) in.readObject();
            in.close();
            fileIn.close();
        }catch(IOException i)
        {
            i.printStackTrace();
            return;
        }catch(ClassNotFoundException c)
        {
            System.out.println("Employee class not found");
            c.printStackTrace();
            return;
        }

        System.out.println(highScores.get(0).name);
    }

}
