package com.aseproject.service;
import com.aseproject.domain.MapStorageInfo;
import com.aseproject.domain.Mapwithcoordinates;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;



public class MapStorageInfoService {
    public void storeMapInLocal(String[][] mapBlock)  {
        try {
            Gson gson = new Gson();
            int row = mapBlock.length;
            int col = mapBlock[0].length;
            int len = row * col;
            Mapwithcoordinates[] newc = new Mapwithcoordinates[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Mapwithcoordinates c = new Mapwithcoordinates();
                    c.coordinates[0] = i;
                    c.coordinates[1] = j;
                    c.data = mapBlock[i][j];
                    newc[i] = c;
                }
            }
            String json = gson.toJson(newc);
            String str = "C:\\Users\\79989\\OneDrive\\桌面\\1\\1.txt";
            File file = new File(str);

           if (!file.exists()) {
                file.createNewFile();
            }

            String j = json.toString();
            byte[] b = j.getBytes();
            int l = j.length();
            OutputStream os = new FileOutputStream(file);
            os.write(b, 0, l);

            os.close();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public void addMap(MapStorageInfo info) throws IOException {
        byte[] std;
        ByteArrayOutputStream byt = new ByteArrayOutputStream();
        ObjectOutputStream obj = new ObjectOutputStream(byt);
        obj.writeObject(info);
        std=byt.toByteArray();

        JdbcTemplate jdbcTemplate = new Jdbctemplate
    }
    
    public String readMapFromLocal(MapStorageInfo storageInfo) throws IOException {
        File file = new File("mapwithcoordinates"); //filepath
        if(file.exists()) {
            InputStream input = new FileInputStream(file);
            byte data[] = new byte[1024];
            input.read(data);
            input.close();
            String res = new String(data);
            return res;
        }
        return null;
    }



}
