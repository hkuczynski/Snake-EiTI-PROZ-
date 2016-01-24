package model;

import java.util.ArrayList;

/**
 * Klasa reprezentująca węża.
 */
public class Snake
{
    public static final int startSize = 5; // Początkowa długość węża
    private BodyPart b;
    private ArrayList<BodyPart> bodyParts;

    private int xCoor = 20, yCoor = 20, size;

    /*
     * Konstruktor inicjalizuje listę części składowych węża, ustawia aktualny rozmiar na wartość
     * rozmiaru początkowego i dodaje jedną część.
     */
    public Snake()
    {
        bodyParts = new ArrayList<>();
        size = startSize;
        addPart();
    }

    public int getSize()
    {
        return bodyParts.size();
    }

    public void setSize(int size)
    {
        this.size = size;
    }

    public void resetSize()
    {
        size = startSize;
    }

    public BodyPart getPart(int index)
    {
        return bodyParts.get(index);
    }

    public int getxCoor()
    {
        return xCoor;
    }

    public int getyCoor()
    {
        return yCoor;
    }

    public void setxCoor(int xCoor)
    {
        this.xCoor = xCoor;
    }

    public void setyCoor(int yCoor)
    {
        this.yCoor = yCoor;
    }

    public void addPart()
    {
        b = new BodyPart(xCoor, yCoor);
        bodyParts.add(b);
    }

    /*
     * Funkcja mająca za zadanie utrzymywać stałą liczbę części węża w zależności od aktualnego rozmiaru.
     */
    public void updateSize()
    {
        while(bodyParts.size() > size)
        {
            bodyParts.remove(0);
        }
    }

}
