package com.aseproject.controller;

import com.aseproject.service.MapStorageInfoService;
import com.aseproject.service.MapUploadService;
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
public class MapUploadController
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
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        mapStorageService.storeMapInLocal(mapBlock);

        model.addAttribute("mapBlocks", mapBlock);
        return "/mapDisplay";
    }
}
