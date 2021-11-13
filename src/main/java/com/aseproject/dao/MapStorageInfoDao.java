package com.aseproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.aseproject.domain.MapStorageInfo;

import java.util.HashMap;
import java.util.LinkedList;
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

    public Map<String,LinkedList<String>> queryMapIdList()
    {
        LinkedList<String> mapIds = new LinkedList<>();
        String sql = "select map_id from map_storage_info";
        jdbcTemplate.query(sql, resultSet -> {
            mapIds.add(resultSet.getString("map_id"));
        });
        HashMap<String, LinkedList<String>> idSet = new HashMap<>();
        idSet.put("ids",mapIds);
        return idSet;
    }

}
