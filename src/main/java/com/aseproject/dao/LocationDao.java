package com.aseproject.dao;

import com.aseproject.domain.LocationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Database operation related to customized location on map
 * @author Jitong Yang
 */
@Repository
public class LocationDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Saving customized location into database
     * @param locationInfo Location DTO
     */
    public void addLocInfoById(LocationInfo locationInfo){
        String sql = "insert into location_info(location_id, map_id, location_name, coordinate_x, coordinate_y, location_description_file_name) " +
                "values (?,?,?,?,?,?)";
        jdbcTemplate.update(sql, locationInfo.getLocationId(), locationInfo.getMapId(), locationInfo.getLocationName(),
                locationInfo.getX(), locationInfo.getY(), locationInfo.getDescriptionFile());
    }

    /**
     * Deleting location information according to location id
     * @param locationId Location unique id string
     */
    public void deleteLocInfoById(String locationId){
        String sql = "delete from location_info where location_id = ?";
        jdbcTemplate.update(sql, locationId);
    }

    /**
     * Searching for all location information according to unique map id
     * @param mapid Map unique id string
     * @return List of related location
     */
    public ArrayList<LocationInfo> getLocByMapId(String mapid) {
        ArrayList<LocationInfo> locList = new ArrayList<>();
        String sql = "SELECT * FROM location_info WHERE map_id = " + mapid;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            LocationInfo obj = new LocationInfo();
            obj.setLocationId((String) row.get("location_id"));
            obj.setLocationName((String) row.get("location_name"));
            obj.setX((Integer) row.get("location_coordinate_x"));
            obj.setY((Integer) row.get("location_coordinate_y"));
            obj.setDescriptionFile((String) row.get("location_description_file_name"));
            obj.setMapId((String) row.get("map_id"));
            locList.add(obj);
        }
        return locList;
    }
}
