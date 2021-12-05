package com.aseproject.domain;

//import java.util.ArrayList;

/**
 * Location DTO
 * @author Yuchen Shen
 */
public class LocationInfo {

    private String locationId;
    private String mapId;
    private String locationName;
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

    // coordinates
    public int getX() { return coordinateX; }

    public void setX(int x) { this.coordinateX = x; }

    public int getY() { return coordinateY; }

    public void setY(int y) { this.coordinateY = y; }
}

