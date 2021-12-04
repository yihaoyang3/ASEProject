package com.aseproject.controller;

import com.aseproject.dao.MapDao;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
public class MapQueryController {

    @RequestMapping( "/map/query")
    public String mapQuery( @RequestParam("keywords") String keywords) {
        MapDao mapDao = new MapDao();
        JSONArray mapList =  new JSONArray();
        List<HashMap <String, String>> mapSet = mapDao.getMapByName(keywords);
        for (HashMap <String, String> row : mapSet)
        {
            JSONObject obj = new JSONObject();
            obj.put("mapID", row.get("mapID"));
            obj.put("mapName", row.get("mapName"));
            mapList.add(obj);
        }
        Gson gson = new Gson();
        return gson.toJson(mapList);
    }
}