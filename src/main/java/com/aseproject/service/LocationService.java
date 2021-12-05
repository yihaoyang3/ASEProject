package com.aseproject.service;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * Other operations related to customizd location
 * @author Yicheng Lu
 */
@Service
public class LocationService {

    @Value("${project.location.path}")
    private String path;

    /**
     * Saving customized media file in local
     * @param file Media file
     * @return Message related to storage is successful or not
     */
    public String saveLocVideo(MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            System.out.println("path" + path);
            File filepath = new File(path, file.getOriginalFilename());
            if (!filepath.getParentFile().exists()) {
                filepath.getParentFile().mkdirs();
            }
            File fi = new File(path + File.separator + file.getOriginalFilename());

            file.transferTo(fi);

            String localAddress = fi.getAbsolutePath();
            System.out.println("Local path:" + localAddress);

            String suffix = localAddress.substring(localAddress.lastIndexOf("."));
            System.out.println("suffix:" + suffix);
        }
        else {
            return "Failure in saving video";
        }
        return "Succeed in saving video";
    }
}