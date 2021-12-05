package com.aseproject.controller;

import com.aseproject.dao.MapDao;
import com.aseproject.service.MapService;
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

/**
 * @classname MapController
 * @description Handling request from map upload page. Processing map image, save in local and send to MapDao to store related information into database
 * @author Yihao Yang
 * @date Dec 05th, 2021
 */
@Controller
public class MapController {

    @Autowired
    private MapDao mapDao;

    @Autowired
    private MapService mapService;

    /**
     * @descirption Setting home page
     * @return Home page url
     */
    @RequestMapping("/")
    public String welcome() {
        return "/home";
    }

    /**
     * @description Receiving map image and processing
     * @param mapImage: Map image
     * @param model: Data transfer flow
     * @return Redirecting to map display page
     */
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

    /**
     * @description Reading all maps from database
     * @return String of all map id
     */
    @RequestMapping("/plazaHomepage")
    @ResponseBody
    public String requestMapList() {
        Gson gson = new Gson();
        String mapIds = gson.toJson(mapDao.getAllMaps());
        return mapIds;
    }

    /**
     * @description Searching for maps according to map id
     * @param id: Map id string
     * @param model: Data transfer flow
     * @return Home url
     */
    @RequestMapping("/map/browse")
    public String browseMap(@RequestParam("id") String id, Model model) {
        model.addAttribute("mapId",id);
        return "/homepage";
    }

    /**
     * @description Reading map local storage json file according to map id
     * @param id: Map id string
     * @return map data in json format
     */
    @RequestMapping("/map/browse/getMap")
    @ResponseBody
    public String getMapInJson(@RequestParam("id") String id) {
        String[][] mapBlock = mapService.readMapFromLocal(id);
        Gson gson = new Gson();
        String mapData = gson.toJson(mapBlock);
        return mapData;
    }
}
