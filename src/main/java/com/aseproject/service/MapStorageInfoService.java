   
package com.aseproject.service;


import com.aseproject.domain.MapStorageInfo;
import com.aseproject.domain.Mapwithcoordinates;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
@Service
public class MapStorageInfoService {
    
    @Value("${project.map.path}")
    private String mappath;
    
    public void storeMapInLocal(String[][] mapBlock)  {
        try {
            Gson gson = new Gson();
            int row = mapBlock.length;
            int col = mapBlock[0].length;
            int len = row * col;
            int number =0;
            Mapwithcoordinates[] newc = new Mapwithcoordinates[len];
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Mapwithcoordinates c = new Mapwithcoordinates();
                    c.coordinates[0] = i;
                    c.coordinates[1] = j;
                    c.data = mapBlock[i][j];
                    newc[number++] = c;
                }
            }
            String json = gson.toJson(newc);
           
            File file = new File(mappath);

            BufferedWriter writer = null;
            Date date = new Date();
          
            File file = new File(mappath);
            if (!file.exists()) {

                 file.mkdirs();
            }
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            String fileName = uuid + ".json";
            File file1 = new File(mappath+fileName);
            file1.createNewFile();

            MapStorageInfo info = new MapStorageInfo();
            info.setMapId(uuidAsString);
            info.setMapStoragePath(mappath+fileName);
            info.setMapStorageName(fileName);

           
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1, false), "UTF-8"));
            writer.write(json);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*public String readMapFromLocal(MapStorageInfo storageInfo) throws IOException {
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
    public void addMap(MapStorageInfo info) throws IOException {
        byte[] std;
        ByteArrayOutputStream byt = new ByteArrayOutputStream();
        ObjectOutputStream obj = new ObjectOutputStream(byt);
        obj.writeObject(info);
        std=byt.toByteArray();
        JdbcTemplate jdbcTemplate = new Jdbctemplate
    }*/


    //read map from location
    public static void processJsonData(ArrayList objectArray, String data, String [][] macthedData) {
        int coordinateX = ((Long) objectArray.get(0)).intValue();
        int coordinateY = ((Long) objectArray.get(1)).intValue();

        macthedData[coordinateX][coordinateY] = data;
    }



    public static String[][] readMapFromLocal(String filePath) {
        String [][] macthedData = new String[0][];

        try {
            JSONParser parser = new JSONParser();
            JSONArray array = (JSONArray) parser.parse(new FileReader(filePath));
            Object lastObject = array.get(array.size() - 1);
            JSONObject lastJsonObject = (JSONObject) lastObject;
            JSONArray lastCoordinatesArray = (JSONArray) lastJsonObject.get("coordinates");
            ArrayList lastObjectArray = new ArrayList();
            for (Object o : lastCoordinatesArray) {
                lastObjectArray.add(o);
            }
            int lastCoordinateX = ((Long) lastObjectArray.get(0)).intValue() + 1;
            int lastCoordinateY = ((Long) lastObjectArray.get(1)).intValue() + 1;

            macthedData = new String[lastCoordinateX][lastCoordinateY];

            for (Object o1 : array) {
                JSONObject jo = (JSONObject) o1;
                JSONArray coordinatesArray = (JSONArray) jo.get("coordinates");
//                JSONObject jo2 = (JSONObject) o1;
                String data = (String) jo.get("data");
//                System.out.print(coordinatesArray + " " + data);
                ArrayList objectArray = new ArrayList();
                for (Object o2 : coordinatesArray) {
                    objectArray.add(o2);
                }

//                System.out.print(objectArray + " ");

                processJsonData(objectArray, data, macthedData);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return macthedData;

    }



    public static void main(String[] args) {

        String filePath = "/Users/Desktop/Data.json";
        String[][] ss = readMapFromLocal(filePath);
        System.out.print(new Gson().toJson(ss[88][66])); // test with coordinates [x][y]

    }
}
