package model;

import java.util.ArrayList;

/**
 * Created by hubert on 22.01.2016.
 */
public class Snake
{
    private BodyPart b;
    private ArrayList<BodyPart> bodyParts;
    public final int startSize;
    private int xCoor = 20, yCoor = 20, size;

    public Snake()
    {
        bodyParts = new ArrayList<>();
        startSize = 5;
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

    public void updateSize()
    {
        while(bodyParts.size() > size)
        {
            bodyParts.remove(0);
        }
    }

}
