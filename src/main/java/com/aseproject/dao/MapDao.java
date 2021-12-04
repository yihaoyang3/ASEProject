package com.aseproject.dao;

import com.aseproject.domain.MapInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class MapDao {

    /* Basic Operations */
    // insert
    public void addMap(MapInfo info) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "insert into map_storage_info(map_id, map_name) values (?,?)";
        jdbcTemplate.update(sql, info.getMapId(), info.getMapName());
    }

    // delete
    public void delMap(String mapId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "delete from map_storage_info where map_id = ?";
        jdbcTemplate.update(sql, mapId);
    }

    /* Search */
    // by name
    public List<HashMap<String, String>> getMapByName(String query) {
        List<HashMap<String, String>> mapSet = new LinkedList<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        // default: select all (query is empty)
        String sql = "SELECT map_id, map_name FROM map_storage_info";

        // if query is not empty
        if (query != null || query != "") {
            String regex = "\\w*" + query + "\\w*";
            sql = "SELECT map_id, map_name FROM map_storage_info WHERE map_name REGEXP " + regex;
        }

        // put all result into a list
        List<Map <String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            HashMap <String, String> item =  new HashMap<>() {{
                put("mapID", (String) row.get("map_id"));
                put("mapName", (String) row.get("map_name"));
            }};
            mapSet.add(item);
        }
        return mapSet;
    }

//    public String getMapPathById(String id) {
//        JdbcTemplate jdbcTemplate = new JdbcTemplate();
//        String sql = "select map_storage_path from map_storage_info where map_id = ?";
//        String storagePath = jdbcTemplate.queryForObject( sql, String.class,id);
//        return storagePath;
//    }

    public List<Map<String, String>> getAllMaps() {
        List<Map <String, String>> mapSet =  new LinkedList<>();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        String sql = "select map_id, map_name from map_storage_info";
        jdbcTemplate.query(sql, resultSet -> {
            HashMap <String, String> mapObject = new HashMap<>();
            mapObject.put("id", (String) resultSet.getObject("map_id"));
            mapObject.put("name", (String) resultSet.getObject("map_name"));
            mapSet.add(mapObject);
        });
        return mapSet;
    }
}
