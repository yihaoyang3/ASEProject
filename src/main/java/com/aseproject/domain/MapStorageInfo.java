package com.aseproject.domain;

public class MapStorageInfo //JavaBean
{

    private String mapStorageName;
    private String mapStoragePath;
    private String mapId;

    public String getMapStorageName()
    {
        return mapStorageName;
    }

    public void setMapStorageName(String mapStorageName)
    {
        this.mapStorageName = mapStorageName;
    }

    public String getMapStoragePath()
    {
        return mapStoragePath;
    }

    public void setMapStoragePath(String mapStoragePath)
    {
        this.mapStoragePath = mapStoragePath;
    }

    public String getMapId()
    {
        return mapId;
    }

    public void setMapId(String mapId)
    {
        this.mapId = mapId;
    }
}
