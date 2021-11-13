package com.aseproject.controller;

import com.aseproject.service.MapStorageInfoService;
import com.aseproject.service.MapUploadService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

        mapStorageService.storeMapInLocal(mapBlock);

        model.addAttribute("mapBlocks", mapBlock);
        return "/mapDisplay";
    }

    @RequestMapping("/plazaHomepage")
    public String requestMapList(Model model)
    {
        Gson gson = new Gson();
        String mapIds = gson.toJson(mapStorageService.queryMapIdList());
        model.addAttribute(mapIds);
        return "";
    }
}
