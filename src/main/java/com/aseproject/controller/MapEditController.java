package com.aseproject.controller;

import com.aseproject.domain.LocationInfo;
import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.stream.Location;

@Controller
public class MapEditController
{

    @RequestMapping("/mapEdit/getLocationItems")
    public String locationItemsRequestHandler(@RequestParam("mapId") String mapId)
    {
        System.out.println(mapId);
        LocationInfo info = new LocationInfo();
        info.setLocationCoordinateX(8);
        info.setLocationCoordinateY(28);
        info.setLocationId("asidfnoiejrwoijqu");
        info.setLocationName("A lovely spot");
        Gson gson = new Gson();
        return gson.toJson(info);
    }
}
