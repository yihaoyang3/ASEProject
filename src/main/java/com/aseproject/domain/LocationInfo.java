package com.aseproject.domain;

public class LocationInfo {

    private String mapId;
    private String locationName;
    private String locationDescriptionFileName;
    private String locationDescriptionFilePath;
    private int locationCoordinateX;
    private int locationCoordinateY;
    private String locationId;

    public String getMapId()
    {
        return mapId;
    }

    public void setMapId(String mapId)
    {
        this.mapId = mapId;
    }

    public String getLocationName()
    {
        return locationName;
    }

    public void setLocationName(String locationName)
    {
        this.locationName = locationName;
    }

    public String getLocationDescriptionFileName()
    {
        return locationDescriptionFileName;
    }

    public void setLocationDescriptionFileName(String locationDescriptionFileName)
    {
        this.locationDescriptionFileName = locationDescriptionFileName;
    }

    public String getLocationDescriptionFilePath()
    {
        return locationDescriptionFilePath;
    }

    public void setLocationDescriptionFilePath(String locationDescriptionFilePath)
    {
        this.locationDescriptionFilePath = locationDescriptionFilePath;
    }

    public int getLocationCoordinateX()
    {
        return locationCoordinateX;
    }

    public void setLocationCoordinateX(int locationCoordinateX)
    {
        this.locationCoordinateX = locationCoordinateX;
    }

    public int getLocationCoordinateY()
    {
        return locationCoordinateY;
    }

    public void setLocationCoordinateY(int locationCoordinateY)
    {
        this.locationCoordinateY = locationCoordinateY;
    }

    public String getLocationId()
    {
        return locationId;
    }

    public void setLocationId(String locationId)
    {
        this.locationId = locationId;
    }
}

