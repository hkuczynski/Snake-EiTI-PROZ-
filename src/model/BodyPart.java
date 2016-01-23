package model;

import java.awt.*;

/**
 * Created by hubert on 22.01.2016.
 */
public class BodyPart
{
    private int xCoor, yCoor, width, height;

    public BodyPart(int xCoor, int yCoor)
    {
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.width = 15;
        this.height = 15;
    }

    public void draw(Graphics g)
    {
        g.setColor(Color.BLACK);
        g.fillRect(xCoor * width, yCoor * height, width, height);
        g.setColor(Color.GREEN);
        g.fillRect(xCoor * width + 2, yCoor * height + 2, width - 4, height - 4);

    }

    public int getxCoor()
    {
        return this.xCoor;
    }

    public int getyCoor()
    {
        return this.yCoor;
    }

    public void setxCoor(int xCoor)
    {
        this.xCoor = xCoor;
    }

    public void setyCoor(int yCoor)
    {
        this.yCoor = yCoor;
    }
}
