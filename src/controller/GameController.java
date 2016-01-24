package controller;

import com.sun.javafx.scene.traversal.Direction;
import model.*;
import view.ArenaView;
import view.StatusView;

import java.awt.event.ActionEvent;
import java.util.*;

/**
 * Kontroler. Steruje całą logiką gry oraz przepływam danych między widokami a modelem.
 */
public class GameController implements Runnable, ButtonsListener
{
    private final int MAX_DIRECTIONS = 3; // maksymalna liczba kierunków będących w liście directions
    private final ArenaModel arenaModel;
    private final ArenaView arenaView;
    private final StatusView statusView;
    private LinkedList<Direction> directions; // lista kierunków, jakie wcisnął gracz
    private Thread gameThread;
    private Timer timer;
    Random generator = new Random(); // potrzebny do generowania losowych współrzędnych

    /*
     * Konstruktor przyjmuje jako argumenty model oraz widoki. Ustawia akcje dla klawiszy, tworzy listę
     * kierunków i dodaje do niej kierunek w górę. Na końcu uruchamia funkcję start, która tworzy wątek dla pętli gry.
     */
    public GameController(ArenaModel arenaModel, ArenaView arenaView, StatusView statusView)
    {
        this.arenaModel = arenaModel;
        this.arenaView = arenaView;
        this.statusView = statusView;
        arenaView.addBindings(this);
        directions = new LinkedList<>();
        directions.add(Direction.UP);
        start();
    }

    public void start()
    {
        gameThread = new Thread(this, "Game Loop");
        gameThread.start();
    }

    /*
     * "Pętla" gry.
     */
    public void run()
    {
        timer = new Timer();
        timer.schedule( new TimerTask()
        {
            public void run()
            {
                if(arenaModel.getGameState() == GameState.RUNNING)
                {
                    updateSnake();
                    updateFood();
                    checkIfEaten();
                    checkSelfCollision();

                    arenaView.repaint();
                    statusView.update();
                }
            }
        },0,  100 );
    }

    /*
     * Funkcja zmieniająca położenie węża w zależności od kierunku w którym ma się poruszać.
     * Dodaje nową część na początku węża.
     */
    public void updateSnake()
    {
        Snake snake = arenaModel.getSnake();
        int arenaWidth = ArenaModel.WIDTH;
        int arenaHeight = ArenaModel.HEIGHT;
        Direction direction = directions.peekFirst();

        switch(direction)
        {
            case RIGHT:
                if (snake.getxCoor() <= 0)
                {
                    snake.setxCoor(arenaWidth);
                }
                else
                {
                    snake.setxCoor(snake.getxCoor() - 1);
                }
                break;
            case LEFT:
                if (snake.getxCoor() >= arenaWidth)
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
                    snake.setyCoor(arenaHeight);
                }
                else
                {
                    snake.setyCoor(snake.getyCoor() - 1);
                }
                break;
            case DOWN:
                if (snake.getyCoor() >= arenaHeight)
                {
                    snake.setyCoor(0);
                }
                else
                {
                    snake.setyCoor(snake.getyCoor() + 1);
                }
                break;
        }

        if(directions.size() > 1)
        {
            directions.poll();
        }

        snake.addPart();
        snake.updateSize();

        arenaModel.setSnake(snake);
    }

    /*
     * Funkcja tworząca nowe owoce. Zostają one dodane jeżeli na planszy pozostanie mniej niż 4.
     */
    public void updateFood()
    {
        int xCoor, yCoor, partSnakeX, partSnakeY;
        boolean collision = false;
        Snake snake = arenaModel.getSnake();

        if(arenaModel.getFruitList().size() < 4)
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
                arenaModel.addFood(new Fruit(xCoor, yCoor));
            }

        }
    }

    /*
     * Funkcja sprawdzająca czy wąż najechał na owoc.
     */
    public void checkIfEaten()
    {
        Snake snake = arenaModel.getSnake();
        BodyPart snakesHead = snake.getPart(snake.getSize() - 1);
        ArrayList<Fruit> fruitList = arenaModel.getFruitList();
        int foodXCoor, foodYCoor, headXCoor, headYCoor, score;

        headXCoor = snakesHead.getxCoor();
        headYCoor = snakesHead.getyCoor();

        for(int i = 0; i < fruitList.size(); i++)
        {
            foodXCoor = fruitList.get(i).getxCoor();
            foodYCoor = fruitList.get(i).getyCoor();

            if(foodXCoor == headXCoor && foodYCoor == headYCoor)
            {
                arenaModel.removeFood(i);
                arenaModel.incrementSnakeSize();

                score = arenaModel.getScore();
                arenaModel.setScore(score + 1);
            }
        }

    }

    /*
     * Funkcja sprawdzająca czy wąż najechał na siebie.
     */
    public void checkSelfCollision()
    {
        Snake snake = arenaModel.getSnake();
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
                arenaModel.setSnake(snake);
                gameEnd();
            }
        }
    }

    /*
     * Funkcja obsługująca wstrzymywanie gry.
     */
    private void gamePause()
    {
        arenaModel.setGameState(GameState.PAUSED);
        arenaView.repaint();
        timer.cancel();
    }

    /*
 * Funkcja obsługująca wznawianie gry.
 */
    private void gameResume()
    {
        arenaModel.setGameState(GameState.RUNNING);
        run();
    }

    /*
     * Funkcja obsługująca kończenie gry.
     */
    private void gameEnd()
    {
        arenaModel.setGameState(GameState.ENDED);

    }

    /*
     * Funkcja obsługująca restartowanie gry.
     */
    private void restartGame()
    {
        arenaModel.setScore(0);
        arenaModel.setSnake(new Snake());
        arenaModel.setFruitList(new ArrayList<>());
        directions.clear();
        directions.add(Direction.UP);
    }

    /*
     * Funkcja wywołująca odpowiednie akcje w zależności od wciśniętego przycisku.
     */
    private void move(Direction d) {
        if (arenaModel.getGameState() == GameState.RUNNING) {
            if (directions.size() < MAX_DIRECTIONS) {
                Direction last = directions.peekLast();
                switch (d)
                {
                    case UP:
                    {
                        if(last != Direction.DOWN && last != Direction.UP) {
                            directions.addLast(Direction.UP);
                        }
                        break;
                    }
                    case DOWN:
                    {
                        if(last != Direction.UP && last != Direction.DOWN) {
                            directions.addLast(Direction.DOWN);
                        }
                        break;
                    }
                    case RIGHT:
                    {
                        if(last != Direction.LEFT && last != Direction.RIGHT) {
                            directions.addLast(Direction.LEFT);
                        }
                        break;
                    }
                    case LEFT:
                    {
                        if(last != Direction.LEFT && last != Direction.RIGHT) {
                            directions.addLast(Direction.RIGHT);
                        }
                        break;
                    }
                }
            }
        }
    }

    /*
     * Poniżej znajdują się przypisania akcji do konkretnych przycisków
     */

    @Override
    public void upKey(ActionEvent e)
    {
        move(Direction.UP);
    }

    @Override
    public void downKey(ActionEvent e)
    {
        move(Direction.DOWN);
    }

    @Override
    public void leftKey(ActionEvent e)
    {
        move(Direction.LEFT);
    }

    @Override
    public void rightKey(ActionEvent e)
    {
        move(Direction.RIGHT);
    }

    @Override
    public void spaceKey(ActionEvent e)
    {
        switch(arenaModel.getGameState())
        {
            case RUNNING:
                gamePause();
                break;
            case PAUSED:
                gameResume();
                break;
        }
    }

    @Override
    public void enterKey(ActionEvent e)
    {
        if(arenaModel.getGameState() == GameState.STARTED)
        {
            arenaModel.setGameState(GameState.RUNNING);
        }
        else if(arenaModel.getGameState() == GameState.ENDED)
        {
            restartGame();
            arenaModel.setGameState(GameState.RUNNING);
        }
    }

}
