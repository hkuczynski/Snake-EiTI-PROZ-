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
public class GameController implements Runnable, KeyListener
{
    private final int MAX_DIRECTIONS = 3; // maksymalna liczba kierunków będących w liście directions
    private final ArenaModel arenaModel;
    private final ArenaView arenaView;
    private final StatusView statusView;
    private LinkedList<Direction> directions; // lista kierunków, jakie wcisnął gracz
    private Thread gameThread;
    private Timer timer;

    /**
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

    /**
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
                    arenaModel.updateSnake(directions.peek());

                    if(directions.size() > 1)
                    {
                        directions.poll();
                    }

                    arenaModel.updateFood();
                    arenaModel.checkIfEaten();
                    arenaModel.checkSelfCollision();

                    arenaView.repaint();
                    statusView.update();
                }
            }
        },0,  100 );
    }



    /**
     * Funkcja obsługująca wstrzymywanie gry.
     */
    private void gamePause()
    {
        arenaModel.setGameState(GameState.PAUSED);
        arenaView.repaint();
        timer.cancel();
    }

    /**
     * Funkcja obsługująca wznawianie gry.
     */
    private void gameResume()
    {
        arenaModel.setGameState(GameState.RUNNING);
        run();
    }


    /**
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

    /**
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

    /**
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
