package com.aseproject.service;


import com.aseproject.dao.MapDao;
import com.aseproject.domain.MapStorageInfo;
import com.aseproject.domain.Mapwithcoordinates;
import com.google.gson.Gson;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;



public class MapStorageInfoService {
    public void storeMapInLocal(String[][] mapBlock)  {
    @Autowired
    private MapDao mapDao;
    
    @Value("${project.map.path}")
    private String mappath;
    
    public String storeMapInLocal(String[][] mapBlock)  {
        String uuidAsString = null;
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

            UUID uuid = UUID.randomUUID();
            uuidAsString = uuid.toString();
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
            mapDao.addMap(info);
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
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

    public List<Map<String, String>> queryMapIdList()
    {
        return mapDao.getAllMapsById();
    }

    public String[][] getMap(String id)
    {
        String mapStoragePath = mapDao.queryMapPathById(id);
        String[][] mapBlock = readMapFromLocal(mapStoragePath);
        return mapBlock;
    }
}