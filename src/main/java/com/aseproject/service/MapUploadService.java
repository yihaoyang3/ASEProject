package com.aseproject.service;

import com.aseproject.util.mapprocessor.Counter;
import com.aseproject.util.mapprocessor.MapConvertor;
import com.aseproject.util.mapprocessor.MapCutter;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class MapUploadService
{
    public String[][] processUploadedMap(BufferedImage originalMap) throws InterruptedException
    {
        String[][] mapBlock = null;

        int totalRow = originalMap.getHeight() / 30;
        int totalCol = originalMap.getWidth() / 30;

        MapCutter.resetAll();
        MapCutter.setOriginalMap(originalMap);
        MapCutter.setTotalCol(totalCol);
        MapCutter.setTotalRow(totalRow);
        MapCutter.setCounter(new Counter());
        MapCutter.setQueue(new LinkedBlockingQueue<>());
        /*MapCutter m = new MapCutter();
        Thread mapCutter = new Thread(m);
        mapCutter.start();*/
        for (int i = 0; i < 3; i++)
        {
            Thread mapCutter = new Thread(new MapCutter());
            mapCutter.start();
        }

        MapConvertor.resetAll();
        MapConvertor.initReceiveQueue(MapCutter.getQueue());
        MapConvertor.initBase64Block(totalRow, totalCol);

        Thread[] mapConvertors = new Thread[6];
        for (int i = 0; i < mapConvertors.length; i++)
        {
            mapConvertors[i] = new Thread(new MapConvertor());
            mapConvertors[i].start();
        }

        long start = System.currentTimeMillis();
        while (true)
        {
            if (MapConvertor.isDone())
            {
                mapBlock = MapConvertor.getBase64MapBlock();
                break;
            }
            TimeUnit.MILLISECONDS.sleep(500);
        }
        long end = System.currentTimeMillis();
        System.out.println("Total time:" + (end - start)/1000.0);
        return mapBlock;
    }
}
