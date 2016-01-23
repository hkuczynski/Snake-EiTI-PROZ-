package model;

/**
 * Created by hubert on 24.01.2016.
 */
public class PlayerScore implements java.io.Serializable
{
    public String name;
    public int score;

    public PlayerScore(String name, int score)
    {
        this.name = name;
        this.score = score;
    }
}
