package com.aseproject.controller;

import com.aseproject.util.MapTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@Controller
public class MapUploadController
{
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
//            MapTool tool = new MapTool();
            BufferedImage[][] splitMap = MapTool.cutImage(ImageIO.read(f));
            int row = splitMap.length;
            int col = splitMap[0].length;
            mapBlock = new String[row][col];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            for (int i = 0; i < row; i++)
            {
                for (int j = 0; j < col; j++)
                {
                    mapBlock[i][j] = MapTool.imageToBase64(splitMap[i][j], baos);
                    baos.reset();
                }
            }
            baos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        model.addAttribute("mapBlocks", mapBlock);
        return "/mapDisplay";
    }
}
