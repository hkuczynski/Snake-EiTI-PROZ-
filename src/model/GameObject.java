package model;

/**
 * Klasa abstrakcyjna reprezentująca dowolny obiekt na planszy gry
 */
public abstract class GameObject
{
    public static final int SIZE = 15;
    protected int xCoor, yCoor;



    /**
     * Konctruktor przyjmuje jako argumenty położenie tworzonego obiektu
     */
    public GameObject(int xCoor, int yCoor)
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
