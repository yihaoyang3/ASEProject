package com.aseproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MapStorageInfo //JavaBean
{
//    private String mapId;
    private String mapStorageName;
    private String mapStoragePath;
    @Id
    @GeneratedValue
    private String mapId;

    public MapStorageInfo(){

        super();
    }

    public String getMapId(){

        return mapId;
    }
    public void setMapId(String mapId){

        this.mapId = mapId;
    }

    public String getMapStorageName(){

        return mapStorageName;
    }
    public void setMapStorageName(String mapStorageName){

        this.mapStorageName = mapStorageName;
    }

    public String getMapStoragePath(){

        return mapStoragePath;
    }
    public void setMapStoragePath(String mapStoragePath){

        this.mapStoragePath = mapStoragePath;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
}
