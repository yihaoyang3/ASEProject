package com.aseproject.controller;

import com.aseproject.service.MapStorageInfoService;
import com.aseproject.service.MapUploadService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

@Controller
public class MapController
{
    @Autowired
    private MapUploadService mapUploadService;

    @Autowired
    private MapStorageInfoService mapStorageService;

    @RequestMapping("/")
    public String welcome()
    {
        return "/home";
    }

    @RequestMapping("/upload")
    public String uploadMap(@RequestParam("map") MultipartFile mapImage, Model model)
    {
        String originalName = mapImage.getOriginalFilename();
        String[] s = originalName.split("\\.");
        String[][] mapBlock = null;

        try
        {
            File f = File.createTempFile(s[0], s[1]);
            mapImage.transferTo(f);
            mapBlock = mapUploadService.processUploadedMap(ImageIO.read(f));
        } catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        }

        String mapId = mapStorageService.storeMapInLocal(mapBlock);

        model.addAttribute("mapId", mapId);
        return "/mapDisplay";
    }

    @RequestMapping("/plazaHomepage")
    @ResponseBody
    public String requestMapList()
    {
        Gson gson = new Gson();
        String mapIds = gson.toJson(mapStorageService.queryMapIdList());
        return mapIds;
    }

    @RequestMapping("/map/browse")
    public String browseMap(@RequestParam("id") String id, Model model)
    {
        model.addAttribute("mapId",id);
        return "/homepage";
    }

    @RequestMapping("/map/browse/getMap")
    @ResponseBody
    public String getMapInJson(@RequestParam("id") String id)
    {
        String[][] mapBlock = mapStorageService.getMap(id);
        Gson gson = new Gson();
        String mapData = gson.toJson(mapBlock);
        return mapData;
    }
}
