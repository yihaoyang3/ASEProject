This document will only list the required functions like type of the returned value, parameters to be passed. The implementation depends on developer.
## Project Structure
The basic structure of the project is:
**ASEProject**
|--src
&emsp;|--main
&emsp;&emsp;|--java
&emsp;&emsp;&emsp;&emsp;|--com.aseproject
&emsp;&emsp;&emsp;&emsp;&emsp;|--controller
&emsp;&emsp;&emsp;&emsp;&emsp;|--service
&emsp;&emsp;&emsp;&emsp;&emsp;|--domain
&emsp;&emsp;&emsp;&emsp;&emsp;|--dao
&emsp;&emsp;&emsp;&emsp;&emsp;|--(others packages)
&emsp;&emsp;|--resource
&emsp;&emsp;&emsp;|--static
&emsp;&emsp;&emsp;&emsp;|--css
&emsp;&emsp;&emsp;&emsp;|--js
&emsp;&emsp;&emsp;&emsp;|--templates(html or template like thymeleaf)
&emsp;|--test
The backend developer should follow MVC pattern.
**Unfinished**
## Comments
All the services should be placed under this package. A service provides 
Developers should provide comment for classes and methods. The comment of classes should appear before the definition of the classes. The comment should include information including the author, explain of usage of this class. For example:
~~~ 
    /** This class provides methods for cutting image into small pieces and converting image to base64 format.
    * @author Yihao Yang
    */
    public class MapTool
    {
        ...
    }
~~~
A example of method comment:
~~~
    /**
        * This method splits the image into small blocks.
        * @param image The image waiting for cutting
        * @return A two-dimensional array of the split image. Every element in the array is a small part of the whole image.
        */
    public static BufferedImage[][] cutImage(BufferedImage image)
    {
        ...
    }
~~~
## API
### Domain 
1. Map Storage Info

Creating class named MapStorageInfo under package com.aseproject.domain. Including fields and methods:
~~~
    String mapId;
    String mapStorageName;
    String mapStoragePath;
    getter() and setter() for each fields.
~~~

2. Location Info 

Creating class LocationInfo under package com.aseproject.domain. Including fields and methods:
~~~
    String locationId;
    String mapId;
    String locationName;
    String locationDescriptionFileName;
    String locationDescriptionFilePath;
    int locationCoordinateX;
    int locationCoordinateY;
    getter() and setter() for each fields.
~~~

### Map Storage Service
Creating class named MapStorageService under package com.aseproject.service and creating class named MapStorageDao under com.aseproject.dao.
1. Storing images in local device

This method should be defined in class MapStorageService.
The user uploaded image will be processed first into small blocks. The BufferedImage[][] is used to contain the split image. The length of BufferedImage[] means the image is cut into how many rows and the length of BufferedImage[0][] means the image is cut into how many columns. Then this BufferedImage will be converted into String[][] with same capacity. Every element in String[][] represents the base64 of every single block. this functions should consider how to store the split image in local storage device. 
~~~
    MapStorageInfo storeMapInLocal(String[][] imageString)
~~~
This method should store the String[][] imageString in a **single file**. Considering the reuse, the stored file must include coordinate information of every single block. For example:
~~~
{
    "coordinate" : [x,y],
    "data" : base64 string
}
~~~
It's just an example, how to encode the storage file depends on developer.
The returned object of MapStorageInfo must have files filled especially the map_id. Developer should consider generating an appropriate id for this fields to avoid the conflict of duplicated names.

2. Adding map record to database

This method should be defined in class MapStorageDao.
~~~
    void addMap(MapStorageInfo info)
~~~
Adding one map record to map_storage_info table.

3. Reading map record from database

This method should be defined in class MapStorageDao.
~~~
    MapStorageInfo readMap(String id)
~~~
Querying map record from database by id. Return MapStorageInfo object with data.

4. Deleting map record from database

This method should be defined in class MapStorageDao.
~~~
    void deleteMap(String mapId)
~~~
Deleting map record from database by id. Notice, the data in location_info related to the map also need to be deleted.

5. Reading image form local

This method should be defined in class MapStorageService.  
~~~
    String[][] readMapFromLocal(MapStorageInfo storageInfo)
~~~
This method read the stored map file from local and return the map file with String[][].

### Location Service 
Creating class named LocationInfoService under package com.aseproject and creating class named LocationInfoDao under package com.aseproject.dao.

1. Storing location description files in local

This method should be defined in class LocationInfoService.
~~~
    LocationInfo storeDescriptionFilesInLocal(String mapId, MultipartFile[] files)
~~~
This method store user uploaded location description files in local. Notice, the user may upload both image and video, thus, this method should able to store both image and video perfectly. After storage, this method should return LocationInfo object with all fields filled appropriately.

2. Deleting location description files in local

This method should be defined in class LocationInfoService.
~~~
    void deleteDescriptionFilesInLocal(String path, String name)
~~~
This method delete specific files in local by the path and name.

3. Adding location info to database

This method should be defined in class LocationInfoDao.
~~~
    void addLocationInfoById(LocationInfo locationInfo)
~~~
This method add location info to the table location_info. Check database for the structure of this table.

4. Deleting location info from database

This method should be defined in class LocationInfoDao.
~~~
    void deleteLocationInfoById(String locationId)
~~~
This method delete one location info form database by location_id.