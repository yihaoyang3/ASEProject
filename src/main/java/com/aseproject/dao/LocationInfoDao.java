package com.aseproject.dao;
import com.aseproject.domain.LocationInfo;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
@Repository
public class LocationInfoDao
{
	@Autowired
	JdbcTemplate jdbcTemplate;
	  
	public LocationInfoDao()
	{
		
	}
	public void deleteDescriptionFilesInLocal(String path, String name) 
	{
		String query = "DELETE FROM location_info WHERE location_description_file_path = ? and location_description_file_name = ?";
		jdbcTemplate.update(query,path,name);
	}

	public void addLocationInfoById(LocationInfo locationInfo) {
		String query = "insert into  location_info values (?, ?, ?, ? , ?, ?,?)";
		jdbcTemplate.update(query,locationInfo.getlocationId(),locationInfo.getmapId(),locationInfo.getlocationName(),
				locationInfo.getlocationdescriptionfileName(),locationInfo.getlocationdescriptionfilepath(),locationInfo.getlocationcoordinateX(),
		locationInfo.getlocationCoordinateY());
	
	}

	public void deleteLocationInfoById(String locationId) {
		String query = "DELETE FROM location_info WHERE location_id = ?";
		jdbcTemplate.update(query,locationId);
		
	}
	
}
