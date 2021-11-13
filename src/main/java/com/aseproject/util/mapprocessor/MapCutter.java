package com.aseproject.util.mapprocessor;

import java.awt.image.BufferedImage;
import java.awt.image.RasterFormatException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The MapCutter is used to split an image into several pieces. It implements the Runnable interface to create multi
 * thread to increase performance
 *
 * @author Yihao Yang
 */
public class MapCutter implements Runnable
{
    private static BufferedImage originalMap;
    private static Counter counter;
    private static LinkedBlockingQueue<MapBlock> queue;
    private final static int widthPerBlock = 30;
    private final static int heightPerBlock = 30;
    private static int totalRow, totalCol;


    public MapBlock cutImage(Counter counter)
    {
        int x, y;
        MapBlock block = new MapBlock();
        synchronized (counter)
        {
            x = counter.getCountX();
            y = counter.getCountY();
            if (y == totalCol - 1)
            {
                counter.setCountX(x + 1);
                counter.setCountY(0);
            } else
            {
                counter.setCountY(y + 1);
            }
        }
        if (x == totalRow )
        {
            return null;
        }
        BufferedImage i = null;
        try
        {
            i = originalMap.getSubimage(y * MapCutter.widthPerBlock, x * MapCutter.heightPerBlock,
                    MapCutter.widthPerBlock, MapCutter.heightPerBlock);
        } catch (RasterFormatException e)
        {
            System.out.println(x + " " + y);
            throw e;
        }
        block.setBlock(i);
        block.setX(x);
        block.setY(y);
        return block;
        /*synchronized (counter)
        {
            if (y == totalCol - 1)
            {
                counter.setCountX(x + 1);
                counter.setCountY(0);
            } else
            {
                counter.setCountY(y + 1);
            }
            return block;
        }*/
    }

    @Override
    public void run()
    {
        while (true)
        {
            MapBlock block = this.cutImage(counter);
            if (block != null)
            {
                try
                {
                    queue.put(block);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            } else
            {
                MapCutter.resetAll();
                break;
            }
        }
    }

    public static void setTotalRow(int totalRow)
    {
        MapCutter.totalRow = totalRow;
    }

    public static void setTotalCol(int totalCol)
    {
        MapCutter.totalCol = totalCol;
    }

    public static BufferedImage getOriginalMap()
    {
        return originalMap;
    }

    public static Counter getCounter()
    {
        return counter;
    }

    public static void setCounter(Counter counter)
    {
        if (MapCutter.counter == null) MapCutter.counter = counter;
    }

    public static void resetCounter()
    {
        MapCutter.counter = null;
    }

    public static LinkedBlockingQueue<MapBlock> getQueue()
    {
        return queue;
    }

    public static void setQueue(LinkedBlockingQueue<MapBlock> queue)
    {
        if (MapCutter.queue == null) MapCutter.queue = queue;
    }

    public static void resetQueue()
    {
        MapCutter.queue = null;
    }

    public static void setOriginalMap(BufferedImage originalMap)
    {
        if (MapCutter.originalMap == null) MapCutter.originalMap = originalMap;
    }

    public static void resetMapData()
    {
        MapCutter.originalMap = null;
    }

    public static void resetAll()
    {
        MapCutter.resetMapData();
        MapCutter.resetCounter();
        MapCutter.resetQueue();
        MapCutter.totalRow = 0;
        MapCutter.totalCol = 0;
    }

}
