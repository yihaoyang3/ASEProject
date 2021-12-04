package com.aseproject.controller;

import com.aseproject.dao.MapDao;
import com.aseproject.service.MapService;
import com.google.gson.Gson;
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
public class MapController {

    @RequestMapping("/")
    public String welcome() {
        return "/home";
    }

    @RequestMapping("/upload")
    public String uploadMap(@RequestParam("map") MultipartFile mapImage, Model model) {
        String originalName = mapImage.getOriginalFilename();
        String[] s = originalName.split("\\.");
        MapService mapService = new MapService();
        String[][] mapBlock = null;

        try {
            File f = File.createTempFile(s[0], s[1]);
            mapImage.transferTo(f);
            mapBlock = mapService.processUploadedMap(ImageIO.read(f));
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        mapService.storeMapInLocal(mapBlock);

        model.addAttribute("mapBlocks", mapBlock);
        return "/mapDisplay";
    }

    @RequestMapping("/plazaHomepage")
    @ResponseBody
    public String requestMapList() {
        Gson gson = new Gson();
        MapDao mapDao = new MapDao();
        String mapIds = gson.toJson(mapDao.getAllMaps());
        return mapIds;
    }

    @RequestMapping("/map/browse")
    public String browseMap(@RequestParam("id") String id, Model model) {
        model.addAttribute("mapId",id);
        return "/homepage";
    }

    @RequestMapping("/map/browse/getMap")
    @ResponseBody
    public String getMapInJson(@RequestParam("id") String id) {
        MapService mapService = new MapService();
        String[][] mapBlock = mapService.readMapFromLocal(id);
        Gson gson = new Gson();
        String mapData = gson.toJson(mapBlock);
        return mapData;
    }
}
