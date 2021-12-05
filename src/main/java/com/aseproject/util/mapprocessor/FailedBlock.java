package com.aseproject.util.mapprocessor;

/**
 * A class to record black that filed in processing.
 * @author Yihao Yang
 */
public class FailedBlock
{
    private int x,y;

    public FailedBlock(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
