package com.aseproject.util.mapprocessor;

import javax.imageio.ImageIO;
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
    private static Boolean isDone;

    public MapConvertor()
    {
        this.baos = new ByteArrayOutputStream(10240*8);
        isDone = false;
    }

    public MapBlock mapToBase64(MapBlock block) throws IOException
    {
        ImageIO.write(block.getBlock(), "jpg", baos);
        block.setStringBlock(Base64.getEncoder().encodeToString(baos.toByteArray()));
        baos.reset();
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
                block = receiveQueue.poll(1, TimeUnit.SECONDS);
                if (block == null)
                {
                    baos.close();
                    MapConvertor.isDone = true;
                    return;
                }
                block = mapToBase64(block);
                base64MapBlock[block.getX()][block.getY()] = block.getStringBlock();
            } catch (InterruptedException | IOException e)
            {
                e.printStackTrace();
            }
        }
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

    public static void resetAll()
    {
        receiveQueue = null;
        base64MapBlock = null;
        isDone = null;
    }

    public static boolean isDone()
    {
        return isDone;
    }
}
