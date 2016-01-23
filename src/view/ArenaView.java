package view;

import controller.ButtonsListener;
import model.ArenaModel;
import model.Food;
import model.Snake;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by hubert on 22.01.2016.
 */
public class ArenaView extends JPanel
{
    private final int WIDTH = 600;
    private final int HEIGHT = 600;

    private final ArenaModel arenaModel;
    java.io.File groundTexture    = new  java.io.File("textures/grass.png");

    public ArenaView(ArenaModel arenaModel)
    {
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.LIGHT_GRAY);

        this.arenaModel = arenaModel;

    }

    public void paintComponent(Graphics g)
    {
        System.out.println("sss");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        if (g2 == null) {
            System.out.println("error");
            return;
        }
        try {
            BufferedImage mImage = ImageIO.read(groundTexture);
            java.awt.geom.Rectangle2D tr = new java.awt.geom.Rectangle2D.Double(0, 0, mImage.getWidth(), mImage.getHeight());
            TexturePaint tp = new TexturePaint(mImage, tr);
            g2.setPaint(tp);
            java.awt.geom.Rectangle2D r = (java.awt.geom.Rectangle2D) this.getBounds();
            g2.fill(r);
        } catch (java.io.IOException ex) {
        }
    }

    public void paint(Graphics g)
    {
        Snake snake = arenaModel.getSnake();
        ArrayList<Food> foodList = arenaModel.getFoodList();
        g.setColor(Color.black);

        for(int i = 0; i < snake.getSize(); i++)
        {
            snake.getPart(i).draw(g);
        }

        for(int i = 0; i < foodList.size(); i++)
        {
            foodList.get(i).draw(g);
        }
    }

    public void addBindings(ButtonsListener l) {
        InputMap inputMap = this.getInputMap();

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

        this.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.upKey(e);
            }
        });

        this.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.downKey(e);
            }
        });

        this.getActionMap().put("left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.leftKey(e);
            }
        });

        this.getActionMap().put("right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.rightKey(e);
            }
        });

        this.getActionMap().put("space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {

                l.spaceKey(e);
            }
        });


    }

}
