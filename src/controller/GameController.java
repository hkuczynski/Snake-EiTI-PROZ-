package controller;

import model.ArenaModel;
import model.BodyPart;
import model.Food;
import model.Snake;
import view.ArenaView;
import view.StatusView;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hubert on 22.01.2016.
 */
public class GameController implements Runnable, ButtonsListener
{
    private final StatusView statusView;
    private final ArenaModel arenaModel;
    private ArenaView arenaView;

    private Thread gameThread;
    private boolean running = false, pause = false;
    private int ticks = 0;
    private Timer timer;
    Random generator = new Random();

    private boolean left = false, right = true, up = false, down = false;




    public GameController(ArenaModel arenaModel, ArenaView arenaView, StatusView statusView)
    {
        this.statusView = statusView;
        this.arenaModel = arenaModel;

        this.arenaView = arenaView;
        arenaView.addBindings(this);

        start();

    }

    public void start()
    {
        running = true;
        gameThread = new Thread(this, "Game Loop");
        gameThread.start();
    }

    public void run() {
        timer = new Timer();
        timer.schedule( new TimerTask(){
            public void run(){
                updateSnake();
                updateFood();
                checkIfEaten();
                checkCollision();

                arenaView.repaint();
                statusView.update();
            }
        },0,  100 ); // 10 s.
    }

    public void updateSnake()
    {
        Snake snake = arenaModel.getSnake();
        int arenaWidth = arenaModel.getWidth();
        int arenaHeight = arenaModel.getHeight();

        if(left)
        {
            if(snake.getxCoor() < 0)
            {
                snake.setxCoor(arenaWidth);
            }
            else
            {
                snake.setxCoor(snake.getxCoor() - 1);
            }
        }
        if(right)
        {
            if(snake.getxCoor() > arenaWidth)
            {
                snake.setxCoor(0);
            }
            else
            {
                snake.setxCoor(snake.getxCoor() + 1);
            }
        }
        if(up)
        {
            if(snake.getyCoor() < 0)
            {
                snake.setyCoor(arenaHeight);
            }
            else
            {
                snake.setyCoor(snake.getyCoor() - 1);
            }
        }
        if(down)
        {
            if(snake.getyCoor() > arenaHeight)
            {
                snake.setyCoor(0);
            }
            else
            {
                snake.setyCoor(snake.getyCoor() + 1);
            }
        }

        snake.addPart();
        snake.updateSize();

        arenaModel.setSnake(snake);

    }

    public void updateFood()
    {
        int x = generator.nextInt(20);
        int xCoor, yCoor;

        if(x == 0)
        {
            xCoor = generator.nextInt(arenaModel.getWidth());
            yCoor = generator.nextInt(arenaModel.getHeight());

            arenaModel.addFood(new Food(xCoor, yCoor));
        }
    }

    public void checkIfEaten()
    {
        Snake snake = arenaModel.getSnake();
        BodyPart snakesHead = snake.getPart(snake.getSize() - 1);
        ArrayList<Food> foodList = arenaModel.getFoodList();
        int foodXCoor, foodYCoor, headXCoor, headYCoor, score;

        headXCoor = snakesHead.getxCoor();
        headYCoor = snakesHead.getyCoor();

        for(int i = 0; i < foodList.size(); i++)
        {
            foodXCoor = foodList.get(i).getxCoor();
            foodYCoor = foodList.get(i).getyCoor();

            if(foodXCoor == headXCoor && foodYCoor == headYCoor)
            {
                arenaModel.removeFood(i);
                arenaModel.incrementSnakeSize();

                score = arenaModel.getScore();
                arenaModel.setScore(score + 1);
            }
        }

    }

    public void checkCollision()
    {
        Snake snake = arenaModel.getSnake();
        BodyPart snakesHead = snake.getPart(snake.getSize() - 1);
        int partXCoor, partYCoor, headXCoor, headYCoor, lives;

        headXCoor = snakesHead.getxCoor();
        headYCoor = snakesHead.getyCoor();

        for(int i = 0; i < snake.getSize() - 1; i++)
        {
            partXCoor = snake.getPart(i).getxCoor();
            partYCoor = snake.getPart(i).getyCoor();

            if(partXCoor == headXCoor && partYCoor == headYCoor)
            {
                snake.resetSize();
                arenaModel.setSnake(snake);

                lives = arenaModel.getLives();

                if(lives <= 0)
                {
                    gameEnd();
                }
                else
                {
                    arenaModel.setLives(lives - 1);
                }
            }
        }
    }

    public void gamePause()
    {
        pause = true;
        timer.cancel();
    }

    public void gameResume()
    {
        pause = false;
        run();
    }

    public void gameEnd()
    {
        timer.cancel();
        arenaModel.saveHighScores();
        System.out.println("koniec gry");
    }

    @Override
    public void upKey(ActionEvent e)
    {
        if(!down)
        {
            up = true;
            left = false;
            right = false;
        }
    }

    @Override
    public void downKey(ActionEvent e)
    {
        if(!up)
        {
            down = true;
            left = false;
            right = false;
        }
    }

    @Override
    public void leftKey(ActionEvent e)
    {
        if(!right)
        {
            left = true;
            up = false;
            down = false;
        }
    }

    @Override
    public void rightKey(ActionEvent e)
    {
        if(!left)
        {
            right = true;
            up = false;
            down = false;
        }
    }

    @Override
    public void spaceKey(ActionEvent e)
    {
        if(!pause)
        {
            gamePause();
        }
        else
        {
            gameResume();
        }


    }

}
