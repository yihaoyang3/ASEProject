package com.aseproject.dao;

import com.aseproject.domain.LocationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
}
