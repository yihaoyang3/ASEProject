package com.aseproject.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.aseproject.domain.MapStorageInfo;
@Repository
public class MapStorageInfoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void addMap(MapStorageInfo info){
        String sql = "insert into map_sotrage_info(map_id, map_storage_name, map_storage_path) values (?,?,?)";
        jdbcTemplate.update(sql, info.getMapId(), info.getMapStorageName(), info.getMapStoragePath());
    }

//    public MapStorageInfo readMap(String id){
////
////
////        return
////    }

    public void deleteMap(String mapId){
        String sql = "delete from map_sotrage_info where map_id = ?";
        jdbcTemplate.update(sql, mapId);
    }

}
