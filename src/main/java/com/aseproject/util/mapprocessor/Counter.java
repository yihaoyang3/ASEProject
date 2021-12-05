package com.aseproject.util.mapprocessor;

/**
 * A class to record how many bocks a map is divided into.
 * @author Yihao Yang
 */
public class Counter
{
    private int countX, countY;

    public Counter()
    {
        countX = countY = 0;
    }

    public int getCountX()
    {
        return countX;
    }

    public void setCountX(int countX)
    {
        this.countX = countX;
    }

    public int getCountY()
    {
        return countY;
    }

    public void setCountY(int countY)
    {
        this.countY = countY;
    }
}
