package model;

import java.awt.*;

/**
 * Klasa definiująca owoce zjadane przez węża.
 */
public class Fruit extends GameObject
{
    public static final Color BORDER_COLOR = new Color(54, 57, 27); // kolor obramowania
    public static final Color BACKGROUND_COLOR = Color.RED; // kolor wypełnienia

    public Fruit(int xCoor, int yCoor)
    {
        super(xCoor, yCoor);
    }
}

