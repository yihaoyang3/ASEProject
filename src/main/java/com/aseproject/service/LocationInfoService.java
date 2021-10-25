package com.aseproject.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;
import com.aseproject.domain.LocationInfo;
import com.aseproject.util.MapTool;

public class LocationInfoService
{
	 LocationInfo storeDescriptionFilesInLocal(String mapId, MultipartFile[] files)
	 {
		 LocationInfo info  = new LocationInfo();
		  String originalName = files[0].getOriginalFilename();
	        String[] s = originalName.split("\\.");
	        String[][] mapBlock = null;
	        try
	        {
	            File f = File.createTempFile(s[0], s[1]);
	            files[0].transferTo(f);
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
	            info.setmapId(mapId);
	            info.setlocationId(UUID.randomUUID().toString());
	            info.setlocationdescriptionfileName(s[0]);
	            info.setlocationdescriptionfilepath(f.getAbsolutePath());
	           
	            baos.close();
	        } catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        return info;
	 }
}

