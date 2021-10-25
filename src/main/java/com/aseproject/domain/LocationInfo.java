package com.aseproject.domain;

public class LocationInfo
{
	  private  String locationId;
	  private  String mapId;
	  private  String locationName;
	  private  String locationDescriptionFileName;
	  private  String locationDescriptionFilePath;
	  private  int locationCoordinateX;
	  private  int locationCoordinateY;
	  
	  public void setlocationId(String loc)
	  {
		locationId = loc;  
	  }
	  public String getlocationId()
	  {
		  return locationId;
	  }
	  public void setmapId(String id)
	  {
		  mapId = id;
	  }
	  public String getmapId()
	  {
		  return mapId;
	  }
	  public void setlocationName(String name)
	  {
		  locationName = name;
	  }
	  public String getlocationName()
	  {
		  return locationName;
	  }
	  public void setlocationdescriptionfileName(String file)
	  {
		  locationDescriptionFileName = file;
	  }
	  public String getlocationdescriptionfileName()
	  {
		  return locationDescriptionFileName;
	  }
	  public void setlocationdescriptionfilepath(String path)
	  {
		  locationDescriptionFilePath = path;
	  }
	  public String getlocationdescriptionfilepath()
	  {
		  return locationDescriptionFilePath;
	  }
	  public void setlocationcoordinateX(int x)
	  {
		  locationCoordinateX = x;
	  }
	  public void setlocationCoordinateY(int y)
	  {
		  locationCoordinateY = y;
	  }
	  public int getlocationcoordinateX()
	  {
		  return locationCoordinateX;
	  }
	  public int getlocationCoordinateY()
	  {
		  return locationCoordinateY;
	  }
	  
	  
}
