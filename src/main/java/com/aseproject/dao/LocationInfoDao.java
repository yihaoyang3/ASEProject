package com.aseproject.dao;

import com.aseproject.domain.LocationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class LocationInfoDao{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addLocationInfoById(LocationInfo locationInfo){
        String sql = "insert into location_info(location_id, map_id, location_name, location_description_file_name, location_description_file_path, location_coordinate_x, location_coordinate_y) " +
                "values (?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, locationInfo.getLocationId(), locationInfo.getMapId(), locationInfo.getLocationName(), locationInfo.getLocationDescriptionFileName(),
                locationInfo.getLocationDescriptionFilePath(), locationInfo.getLocationCoordinateX(), locationInfo.getLocationCoordinateY());
    }

    public void deleteLocationInfoById(String locationId){
        String sql = "delete from location_info where location_id = ?";
        jdbcTemplate.update(sql, locationId);
    }

    public ArrayList<LocationInfo> selectLocByMapId(String mapid) {
        ArrayList<LocationInfo> locList = new ArrayList<>();
        String sql = "SELECT * FROM location_info WHERE map_id = " + mapid;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            LocationInfo obj = new LocationInfo();
            obj.setLocationId((String) row.get("location_id"));
            obj.setLocationName((String) row.get("location_name"));
            obj.setLocationCoordinateX((Integer) row.get("location_coordinate_x"));
            obj.setLocationCoordinateY((Integer) row.get("location_coordinate_y"));
            obj.setLocationDescriptionFilePath((String) row.get("location_description_file_path"));
            obj.setLocationDescriptionFileName((String) row.get("location_description_file_name"));
            obj.setMapId((String) row.get("map_id"));
            locList.add(obj);
        }
        return locList;
    }
}
