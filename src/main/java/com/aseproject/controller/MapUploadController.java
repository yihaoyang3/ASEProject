package com.aseproject.controller;

import com.aseproject.dao.LocationInfoDao;
import com.aseproject.domain.LocationInfo;
import com.aseproject.service.MapStorageInfoService;
import com.aseproject.service.MapUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import javax.xml.stream.Location;
import java.io.File;
import java.io.IOException;

@RestController
public class MapUploadController
{

    @Autowired
    private LocationInfoDao locationInfoDao;

    private MapUploadService mapUploadService = new MapUploadService();

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

        MapStorageInfoService service = new MapStorageInfoService();
        service.storeMapInLocal(mapBlock);

        model.addAttribute("mapBlocks", mapBlock);
        return "/mapDisplay";
    }


    //test-add
    @RequestMapping("/addlocation")
    public void addLocationTest(){
        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setLocationId("18");
        locationInfo.setMapId("12");
        locationInfo.setLocationName("4565");
        locationInfo.setLocationDescriptionFileName("fshad");
        locationInfo.setLocationDescriptionFilePath("/Users/yangjitong/Desktop");
        locationInfo.setLocationCoordinateX(2);
        locationInfo.setLocationCoordinateY(3);

        locationInfoDao.addLocationInfoById(locationInfo);

//        return "";
    }

    //test-delete
    @RequestMapping("/deletelocation")
    public void deleteLocationTest(){
       String locationId = "10";

       locationInfoDao.deleteLocationInfoById(locationId);


    }
}
