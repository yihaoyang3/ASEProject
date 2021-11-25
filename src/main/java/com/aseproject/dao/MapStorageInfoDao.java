package com.aseproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.aseproject.domain.MapStorageInfo;

import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Repository
public class MapStorageInfoDao
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addMap(MapStorageInfo info)
    {
        String sql = "insert into map_storage_info(map_id, map_storage_name, map_storage_path) values (?,?,?)";
        jdbcTemplate.update(sql, info.getMapId(), info.getMapStorageName(), info.getMapStoragePath());
    }

    public void deleteMap(String mapId)
    {
        String sql = "delete from map_storage_info where map_id = ?";
        jdbcTemplate.update(sql, mapId);
    }

    public List<Map<String, String>> queryMapIdList()
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

    public String queryMapStoragePathById(String id)
    {
        String sql = "select map_storage_path from map_storage_info where map_id = ?";
        String storagePath = jdbcTemplate.queryForObject(sql, String.class,id);
        return storagePath;
    }

}
