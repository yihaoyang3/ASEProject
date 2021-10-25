package com.aseproject.domain;

public class MapStorageInfo
{
	 private String mapId;
	 private String mapStorageName;
	 private String mapStoragePath;
	 
	
	public void setmapId(String id)
	 {
		 mapId = id;
	 }
	 public void setmapstorageName(String name)
	 {
		 mapStorageName = name;
	 }
	 public void setmapstoragePath(String path)
	 {
		 mapStoragePath = path;
	 }
	 public String getmapId()
	 {
		 return mapId;
	 }
	 public String getmapstorageName()
	 {
		 return mapStorageName;
	 }
	 public String getmapstoragePath()
	 {
		 return mapStoragePath;
	 }
}
