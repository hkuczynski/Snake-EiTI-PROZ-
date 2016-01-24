package model;

import java.awt.*;

/*
 * Klasa definiująca pojedyńczą część, z której składa się wąż
 */

public class BodyPart
{
    public static final int SIZE = 15; // Rozmiar części węża (szerokość i wysokość)
    public static final Color BORDER_COLOR = new Color(54, 57, 27); // kolor obramowania
    public static final Color BACKGROUND_COLOR = Color.GREEN; // kolor wypełnienia
    private int xCoor, yCoor;

    /*
     * Konstruktor przyjmuje jako argumenty położenie tworzonej części
     */
    public BodyPart(int xCoor, int yCoor)
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
