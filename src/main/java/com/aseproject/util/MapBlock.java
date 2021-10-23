package com.aseproject.util;

import java.awt.image.BufferedImage;

public class MapBlock
{
    private int x,y;
    private BufferedImage block;
    private String stringBlock;

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

    public BufferedImage getBlock()
    {
        return block;
    }

    public void setBlock(BufferedImage block)
    {
        this.block = block;
    }

    public String getStringBlock()
    {
        return stringBlock;
    }

    public void setStringBlock(String stringBlock)
    {
        this.stringBlock = stringBlock;
    }
}
