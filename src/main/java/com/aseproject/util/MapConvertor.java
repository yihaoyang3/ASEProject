package com.aseproject.util;

import javax.imageio.ImageIO;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class MapConvertor implements Runnable
{
    private static LinkedBlockingQueue<MapBlock> receiveQueue;
    private static String[][] base64MapBlock;
    private ByteArrayOutputStream baos;
    private static final LinkedList<FailedBlock> failureLog = new LinkedList<>();
    private static boolean isDone;

    public MapConvertor()
    {
        this.baos = new ByteArrayOutputStream();
    }

    public MapBlock mapToBase64(MapBlock block)
    {
        try
        {
            ImageIO.write(block.getBlock(), "jpg", baos);
            block.setStringBlock(Base64.getEncoder().encodeToString(baos.toByteArray()));
            baos.reset();
        } catch (IOException e)
        {
            failureLog.add(new FailedBlock(block.getX(), block.getY()));
            e.printStackTrace();
        }
        return block;
    }

    @Override
    public void run()
    {
        MapBlock block;
        while (true)
        {
            try
            {
                block = receiveQueue.poll(10, TimeUnit.SECONDS);
                if (block == null) break;
//                System.out.println("receive :" + block.getX() + ", " + block.getY() + "into receive queue");
                block = mapToBase64(block);
                base64MapBlock[block.getX()][block.getY()] = block.getStringBlock();
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        MapConvertor.isDone = true;
    }

    public static void initReceiveQueue(LinkedBlockingQueue<MapBlock> receiveQueue)
    {
        if (MapConvertor.receiveQueue == null)
        {
            MapConvertor.receiveQueue = receiveQueue;
        }
    }

    public static void initBase64Block(int row, int col)
    {
        base64MapBlock = new String[row][col];
    }

    public static void resetQueues()
    {
        MapConvertor.receiveQueue = null;
    }

    public static String[][] getBase64MapBlock()
    {
        return base64MapBlock;
    }

    public static void reset()
    {
        receiveQueue = null;
        base64MapBlock = null;
    }

    public static boolean isDone()
    {
        return isDone;
    }
}
