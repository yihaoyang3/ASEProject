package com.aseproject.domain;

import java.util.ArrayList;

public class LocationInfo {

    private String locationId;
    private String mapId;
    private String locationName;
//    private ArrayList<String> DescriptionFileList;
    private String DescriptionFile;
    private int coordinateX;
    private int coordinateY;

    // location id
    public String getLocationId() { return locationId; }

    public void setLocationId(String locationId) { this.locationId = locationId; }

    // map id
    public String getMapId()
    {
        return mapId;
    }

    public void setMapId(String mapId)
    {
        this.mapId = mapId;
    }

    // location name
    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    // description file
    public String getDescriptionFile() { return this.DescriptionFile; }

    public void setDescriptionFile(String fileName) { this.DescriptionFile = fileName; }

//    // description files
//    public ArrayList<String> getDescriptionFileList()
//    {
//        return this.DescriptionFileList;
//    }
//
//    public String addLocationDescriptionFile(String newFile) {
//        this.DescriptionFileList.add(newFile);
//        return "Add file successfully";
//    }
//
//    public String delLocationDescriptionFile(String fileName) {
//        if(this.DescriptionFileList.contains(fileName)) {
//            this.DescriptionFileList.remove(fileName);
//            return "Removal successfully";
//        } else {
//            return "No such File";
//        }
//    }

    // coordinates
    public int getX() { return coordinateX; }

    public void setX(int x) { this.coordinateX = x; }

    public int getY() { return coordinateY; }

    public void setY(int y) { this.coordinateY = y; }
}

