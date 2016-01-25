package model;

import java.awt.*;

/**
 * Klasa definiująca pojedyńczą część, z której składa się wąż
 */

public class BodyPart extends GameObject
{
    public static final Color BORDER_COLOR = new Color(54, 57, 27); // kolor obramowania
    public static final Color BACKGROUND_COLOR = Color.GREEN; // kolor wypełnienia


    public BodyPart(int xCoor, int yCoor)
    {
        super(xCoor, yCoor);
    }
}
