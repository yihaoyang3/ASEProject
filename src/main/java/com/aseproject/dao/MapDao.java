package com.aseproject.dao;

import com.aseproject.domain.MapStorageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MapDao
{

    @Autowired
    JdbcTemplate jdbcTemplate;

    // insert, add mapStorageInfo
    public void addMap(MapStorageInfo info)
    {
        String sql = "insert into map_storage_info(map_id, map_storage_name, map_storage_path) values (?,?,?)";
        jdbcTemplate.update(sql, info.getMapId(), info.getMapStorageName(), info.getMapStoragePath());
    }

    // delete
    public void deleteMap(String mapId)
    {
        String sql = "delete from map_storage_info where map_id = ?";
        jdbcTemplate.update(sql, mapId);
    }

    public List<HashMap<String, String>> queryMapByName(String query)
    {
        List<HashMap<String, String>> mapSet = new LinkedList<>();

        // default: select all (query is empty)
        String sql = "SELECT map_id, map_name FROM map_storage_info";

        // if query is not empty
        if (query != null || query != "") {
            String regex = "\\w*" + query + "\\w*";
            sql = "SELECT map_id, map_name FROM map_storage_info WHERE map_name REGEXP " + regex;
        }

        // put all result into list
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map row : rows) {
            HashMap<String, String> item = new HashMap<>() {{
                put("mapID", (String) row.get("map_id"));
                put("mapName", (String) row.get("map_name"));
            }};
            mapSet.add(item);
        }
        return mapSet;
    }

    public List<Map<String, String>> getAllMapsById()
    {
        List<Map<String, String>> mapSet = new LinkedList<>();
        String sql = "select map_id, map_storage_name from map_storage_info";
        jdbcTemplate.query(sql, resultSet -> {

            HashMap<String, String> mapObject = new HashMap<>();
            mapObject.put("id", (String) resultSet.getObject("map_id"));
            mapObject.put("name", (String) resultSet.getObject("map_storage_name"));
            mapSet.add(mapObject);
        });
        return mapSet;
    }

    public String queryMapPathById(String id)
    {
        String sql = "select map_storage_path from map_storage_info where map_id = ?";
        String storagePath = jdbcTemplate.queryForObject(sql, String.class,id);
        return storagePath;
    }
}
