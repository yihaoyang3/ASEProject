package com.aseproject.service;


import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

class mapwithcoordinates{
    public int coordinates[];
    public String data;
}

public class MapStorageInfoService {
    public void storeMapInLocal(String[][] mapBlock) throws IOException {
        Gson gson = new Gson();
        int row = mapBlock.length;
        int col = mapBlock[0].length;
        int len = row * col;
        mapwithcoordinates[] newc = new mapwithcoordinates[len];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                mapwithcoordinates c = new mapwithcoordinates();
                c.coordinates[0] = i;
                c.coordinates[1] = j;
                c.data = mapBlock[i][j];
                newc[i] = c;
            }
        }
        String json = gson.toJson(newc);

        File file = new File("mapwithcoordinates");
        if(!file.exists()){
            file.createNewFile();
        }
        String j =json.toString();
        byte[] b = j.getBytes();
        int l = j.length();
        OutputStream os = new FileOutputStream(file);
        os.write(b,0,l);
        os.close();
    }
}
