package com.aseproject.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LocationInfo {
//    private String locationId;
    private String mapId;
    private String locationName;
    private String locationDescriptionFileName;
    private String locationDescriptionFilePath;
    private int locationCoordinateX;
    private int locationCoordinateY;
    @Id
    @GeneratedValue
    private String locationId;

    public LocationInfo() {

        super();
    }

    public String getLocationId() {

        return locationId;
    }

    public void setLocationId(String locationId) {

        this.locationId = locationId;
    }

    public String getMapId() {

        return mapId;
    }

    public void setMapId(String mapId) {

        this.mapId = mapId;
    }

    public String getLocationName() {

        return locationName;
    }

    public void setLocationName(String locationName) {

        this.locationName = locationName;
    }

    public String getLocationDescriptionFileName() {

        return locationDescriptionFileName;
    }
    public void setLocationDescriptionFileName(String locationDescriptionFileName){
        this.locationDescriptionFileName = locationDescriptionFileName;
    }

    public String getLocationDescriptionFilePath(){

        return locationDescriptionFilePath;
    }
    public void setLocationDescriptionFilePath(String locationDescriptionFilePath){
        this.locationDescriptionFilePath = locationDescriptionFilePath;
    }

    public int getLocationCoordinateX(){

        return locationCoordinateX;
    }
    public void setLocationCoordinateX(int locationCoordinateX){

        this.locationCoordinateX = locationCoordinateX;
    }

    public int getLocationCoordinateY(){

        return locationCoordinateY;
    }
    public void setLocationCoordinateY(int locationCoordinateY){

        this.locationCoordinateY = locationCoordinateY;
    }

//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
}

