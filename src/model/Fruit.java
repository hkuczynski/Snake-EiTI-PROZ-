package model;

import java.awt.*;

/**
 * Klasa definiująca owoce zjadane przez węża.
 */
public class Fruit
{
    public static final int SIZE = 15; // Rozmiar części węża (szerokość i wysokość)
    public static final Color BORDER_COLOR = new Color(54, 57, 27); // kolor obramowania
    public static final Color BACKGROUND_COLOR = Color.RED; // kolor wypełnienia
    private int xCoor, yCoor;

    /*
     * Konctruktor przyjmuje jako argumenty położenie tworzonego owocu
     */
    public Fruit(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
    }

    public int getxCoor()
    {
        return this.xCoor;
    }

    public int getyCoor()
    {
        return this.yCoor;
    }
}

