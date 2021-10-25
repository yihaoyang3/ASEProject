package com.aseproject.dao;

import com.aseproject.domain.MapStorageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class MapStorageInfoDao 
{
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public MapStorageInfoDao()
	{
		
	}

	public MapStorageInfo readMap(String id) {
		String query = "SELECT * FROM map_storage_info map_id = ?";		
		var info = (MapStorageInfo) jdbcTemplate.queryForObject(query, new Object[]{id},
                BeanPropertyRowMapper.newInstance(MapStorageInfo.class));
		return info;
	}

	public void deleteMap(String mapId) {
		String query = "DELETE FROM map_storage_info WHERE map_id = ?";
		jdbcTemplate.update(query,mapId);	
	}

	public void addMap(MapStorageInfo info) {
		String query = "insert into map_storage_info values (?, ?, ?)";
		jdbcTemplate.update(query,info.getmapId(),info.getmapstoragePath(),info.getmapstorageName());	
	}

}
