package com.aseproject.controller;

import com.aseproject.dao.LocationDao;
import com.aseproject.domain.LocationInfo;
import com.aseproject.service.LocationService;
import com.google.gson.Gson;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class MapEditController
{
    @Value("${project.location.path}")
    private String locPath;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private LocationService locationService;

    @RequestMapping("/mapEdit/getLocationItems")
    public String locationItemsRequestHandler(@RequestParam("mapId") String mapId) {
        JSONArray locSet = new JSONArray();
        ArrayList<LocationInfo> locList = locationDao.getLocByMapId(mapId);
        for (LocationInfo loc : locList)
        {
            JSONObject obj = new JSONObject();
            obj.put("loc_id", loc.getLocationId());
            obj.put("loc_name", loc.getLocationName());
            obj.put("loc_x", loc.getX());
            obj.put("loc_y", loc.getY());
            obj.put("loc_file_path", locPath + loc.getDescriptionFile());
            locSet.add(obj);
        }
        Gson gson = new Gson();
        return gson.toJson(locSet);
    }

    @PostMapping("/saveVideo")
    @ResponseBody
    public String saveVideoHandler(@RequestParam("file") MultipartFile file) throws IllegalStateException, IOException {
        String msg = locationService.saveLocVideo(file);
        Gson gson = new Gson();
        return gson.toJson(msg);
    }

    @RequestMapping(value = "/showVideo", method = RequestMethod.GET)
    @ResponseBody
    public void getVideoHandler(@RequestParam("loc_file_path") String path, HttpServletResponse response) {
        try {
            File file = new File(path);
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName().replace(" ", "_"));
            InputStream iStream = new FileInputStream(file);
            IOUtils.copy(iStream, response.getOutputStream());
            response.flushBuffer();
        } catch (java.nio.file.NoSuchFileException e) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    /**
     * @param itemInfo should include two files: mapId and locationName, location coordinate X and Y
     * @param media include one video or one image
     * @return
     */

    @RequestMapping("/mapEdit/addLocation")
    @ResponseBody
    public String addLocationItem(@RequestParam("itemInfo") Map<String, Object> itemInfo, @RequestParam("media") MultipartFile media) {
        if (itemInfo != null&& media != null) {
            itemInfo.get("mapID");
            return "Success";
        } else {
            return "No data";
        }
    }
}
