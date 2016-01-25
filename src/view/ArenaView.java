package view;

import controller.KeyListener;
import model.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Widok pola rozgrywki.
 */
public class ArenaView extends JPanel
{
    public static final int WIDTH = 620; // szerokość panelu
    public static final int HEIGHT = 620; // wysokość panelu
    private static final Font FONT = new Font("Tahoma", Font.BOLD, 25);
    private static final Color FONT_COLOR = new Color(54, 57, 27);
    private final ArenaModel arenaModel;

    /**
     * Konstruktor ArenaView przyjmuje jako argument arenaModel, z którego pobiera wszystkie potrzebne informacje
     * np. instancję węża.
     */
    public ArenaView(ArenaModel arenaModel)
    {
        this.arenaModel = arenaModel;

        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBorder(new LineBorder(new Color(54, 57, 27), 5));
        setBackground(new Color(143, 182, 103));
    }

    /**
     * Funkcja rysująca elementy takie jak: Snake, Fruit oraz wyśietlająca w razie potrzeby
     * informacje o stanie gry (początek, pauza, koniec).
     */
    public void paint(Graphics g)
    {
        GameState gameState = arenaModel.getGameState();

        super.paint(g);

        switch(gameState)
        {
            case STARTED:
                showStartingMessage(g);
                break;
            case PAUSED:
                showPauseMessage(g);
                break;
            case ENDED:
                showEndMessage(g);
                break;
            case RUNNING:
                draw(g);
                break;
        }
    }

    private void draw(Graphics g)
    {
        Snake snake = arenaModel.getSnake();
        BodyPart snakePart;
        ArrayList<Fruit> fruitList = arenaModel.getFruitList();
        int xCoor, yCoor;

        for (int i = 0; i < snake.getSize(); i++)
        {
            snakePart = snake.getPart(i);
            xCoor = snakePart.getxCoor();
            yCoor = snakePart.getyCoor();

            g.setColor(BodyPart.BORDER_COLOR);
            g.fillOval(xCoor * BodyPart.SIZE, yCoor * BodyPart.SIZE, BodyPart.SIZE, BodyPart.SIZE);
            g.setColor(BodyPart.BACKGROUND_COLOR);
            g.fillOval(xCoor * BodyPart.SIZE + 2, yCoor * BodyPart.SIZE + 2, BodyPart.SIZE - 4, BodyPart.SIZE - 4);
        }

        for (int i = 0; i < fruitList.size(); i++)
        {
            xCoor = fruitList.get(i).getxCoor();
            yCoor = fruitList.get(i).getyCoor();

            g.setColor(Fruit.BORDER_COLOR);
            g.fillOval(xCoor * Fruit.SIZE, yCoor * Fruit.SIZE, Fruit.SIZE, Fruit.SIZE);
            g.setColor(Fruit.BACKGROUND_COLOR);
            g.fillOval(xCoor * Fruit.SIZE + 2, yCoor * Fruit.SIZE + 2, Fruit.SIZE - 4, Fruit.SIZE - 4);
        }
    }

    /**
     * Funkcja wyświetlająca napisy poitalne.
     */
    private void showStartingMessage(Graphics g)
    {
        String largeMessage = "Press ENTER to play";

        g.setFont(FONT);
        g.setColor(FONT_COLOR);
        g.drawString(largeMessage, WIDTH / 2 - g.getFontMetrics().stringWidth(largeMessage) / 2, HEIGHT / 2 - 50);
    }

    /**
     * Funkcja wyświetlająca napisy w trakcie wstrzymania rozgrywki.
     */
    private void showPauseMessage(Graphics g)
    {
        String largeMessage = "Game Paused";
        String smallMessage = "Press SPACE to resume";

        g.setFont(FONT);
        g.setColor(FONT_COLOR);
        g.drawString(largeMessage, WIDTH / 2 - g.getFontMetrics().stringWidth(largeMessage) / 2, HEIGHT / 2 - 50);
        g.drawString(smallMessage, WIDTH / 2 - g.getFontMetrics().stringWidth(smallMessage) / 2, HEIGHT / 2 + 50);
    }

    /**
     * Funkcja wyświtlająca napisy po porażce.
     */
    private void showEndMessage(Graphics g)
    {
        String largeMessage = "Game Over";
        String smallMessage = "Press ENTER to play again";

        g.setFont(FONT);
        g.setColor(FONT_COLOR);
        g.drawString(largeMessage, WIDTH / 2 - g.getFontMetrics().stringWidth(largeMessage) / 2, HEIGHT / 2 - 50);
        g.drawString(smallMessage, WIDTH / 2 - g.getFontMetrics().stringWidth(smallMessage) / 2, HEIGHT / 2 + 50);
    }

    /**
     * Funkcja przypisująca akcje dla konkretnych klawiszy
     */
    public void addBindings(KeyListener l)
    {
        InputMap inputMap = getInputMap();
        ActionMap actionMap = getActionMap();

        // UP key
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
        inputMap.put(key, "up");

        // DOWN key
        key = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
        inputMap.put(key, "down");

        // LEFT key
        key = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
        inputMap.put(key, "left");

        // RIGHT key
        key = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
        inputMap.put(key, "right");

        // SPACE key
        key = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
        inputMap.put(key, "space");

        // ENTER key
        key = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
        inputMap.put(key, "enter");

        actionMap.put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.upKey(e);
            }
        });

        actionMap.put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.downKey(e);
            }
        });

        actionMap.put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.leftKey(e);
            }
        });

        actionMap.put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.rightKey(e);
            }
        });
        actionMap.put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.spaceKey(e);
            }
        });

        actionMap.put("enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.enterKey(e);
            }
        });
    }
}
