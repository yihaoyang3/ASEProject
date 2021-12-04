package com.aseproject.service;


import com.aseproject.dao.MapDao;
import com.aseproject.domain.MapBlockInfo;
import com.aseproject.domain.MapInfo;
import com.aseproject.util.mapprocessor.Counter;
import com.aseproject.util.mapprocessor.MapConvertor;
import com.aseproject.util.mapprocessor.MapCutter;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class MapService {

    @Value("${project.map.path}")
    private String mapPath;

    public String[][] processUploadedMap(BufferedImage originalMap) throws InterruptedException {
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

        Thread[] mapConvertors = new Thread[16];
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

    public void storeMapInLocal(String[][] mapBlock)  {
        try {
            // write blocks information in local json file
            Gson gson = new Gson();
            int row = mapBlock.length;
            int col = mapBlock[0].length;
            int len = row * col;
            int counter = 0;
            MapBlockInfo[] tmp = new MapBlockInfo[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    MapBlockInfo block = new MapBlockInfo(new int[]{i, j}, mapBlock[i][j]);
                    tmp[counter++] = block;
                }
            }
            String json = gson.toJson(tmp);
            // open folder
            File folder = new File(mapPath);
            if (!folder.exists()) { folder.mkdirs(); }  // check folder exist or not

            // create unique id for each file
            UUID uuid = UUID.randomUUID();
            String fileName = uuid + ".json";
            File file = new File(mapPath + fileName);
            file.createNewFile();

            // write in local
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
            writer.write(json);
            writer.flush();
            writer.close();

            // write map information into database
            MapDao mapDao = new MapDao();
            MapInfo mapInfo = new MapInfo();
            mapInfo.setMapId(uuid.toString());
            mapInfo.setMapName(fileName);
            mapDao.addMap(mapInfo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] readMapFromLocal(String mapID) {
        String[][] map = new String[0][];
        try {
            JSONParser parser = new JSONParser();
            String filePath = mapPath + mapID + ".json";
            JSONArray blocksDat = (JSONArray) parser.parse(new FileReader(filePath));

            // get map size
            JSONObject lastObj = (JSONObject) blocksDat.get(blocksDat.size() - 1);
            JSONArray coord = (JSONArray) lastObj.get("coordinates");
            String [][] blocksMat = new String[((int) coord.get(0)) + 1][((int) coord.get(1)) + 1];

            // read map blocks data
            for (Object obj : blocksDat) {
                JSONObject block = (JSONObject) obj;
                coord = (JSONArray) block.get("coordinates");
                String data = (String) block.get("data");
                blocksMat[(int) coord.get(0)][(int) coord.get(1)] = data;
            }
            map = blocksMat;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}