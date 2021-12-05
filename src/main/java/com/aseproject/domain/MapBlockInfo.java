package com.aseproject.domain;

/**
 * @classname MapBlockInfo
 * @description Map block DTO
 * @author Yicheng Lu
 * @date Dec 5th, 2021
 */
public class MapBlockInfo {

    private int[] coordinates;
    private String data;

    public MapBlockInfo(int[] coord, String dat){
        this.coordinates = coord;
        this.data = dat;
    }

    public int[] getCoordinate() { return this.coordinates; }

    public void setCoordinate(int[] coord) {
        this.coordinates = coord;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String dat) {
        this.data = dat;
    }
}
