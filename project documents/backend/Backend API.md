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

&emsp;&emsp;&emsp;&emsp;&emsp;|--util

&emsp;&emsp;&emsp;&emsp;&emsp;|--interceptor

&emsp;&emsp;&emsp;&emsp;&emsp;|--customexception

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
## First Iteration API
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

### Service
Service in MVC is the object who handle business logical.

#### MapStorageService
Creating class named MapStorageService under package com.aseproject.service.
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

2. Reading image form local

This method should be defined in class MapStorageService.
~~~
    String[][] readMapFromLocal(MapStorageInfo storageInfo)
~~~
This method read the stored map file from local and return the map file with String[][].

#### LocationInfoService
Creating class named LocationInfoService under package com.aseproject.

1. Storing location description files in local

This method should be defined in class LocationInfoService.
~~~
    LocationInfo storeDescriptionFilesInLocal(String mapId, MultipartFile[] files)
~~~
This method store user uploaded location description files in local. Notice, the user may upload both image and video, thus, this method should able to store both image and video perfectly. After storage, this method should return LocationInfo object with all fields filled appropriately.

2. Deleting location description files in local
~~~
    void deleteDescriptionFilesInLocal(String path, String name)
~~~
This method delete specific files in local by the path and name.

### DAO
DAO - Data Access Object - in MVC is the only object who is responsible for database operation. The DAO is a class with methods to execute query, add, update and etc.

#### MaoStorageDao
Creating class named MapStorageDao under package com.aseproject.dao. This class only operates table **map_storage_info**.

1. Adding map record to database
~~~
    void addMap(MapStorageInfo info)
~~~
Adding one map record to map_storage_info table.

2. Reading map record from database
~~~
    MapStorageInfo readMap(String id)
~~~
Querying map record from database by id. Return MapStorageInfo object with data.

3. Deleting map record from database
~~~
    void deleteMap(String mapId)
~~~
Deleting map record from database by id. Notice, the data in location_info related to the map also need to be deleted.

#### LocationInfoDao
Creating class named LocationInfoDao under package com.aseproject.dao. This class only operates table **location_info**

1. Adding location info to database
~~~
    void addLocationInfoById(LocationInfo locationInfo)
~~~
This method add location info to the table location_info. Check database for the structure of this table.

2. Deleting location info from database
~~~
    void deleteLocationInfoById(String locationId)
~~~
This method delete one location info form database by location_id.

## Second Iteration API

### Map query function 
#### MapQueryController.java
This controller class is responsible for handling the request related to map query. The methods listed below should be defined in this java class.
##### 1. String queryMap(String keywords)
The request from url ***/map/query*** should be handled by this method. This method should be able to receive the keywords included in the http request. To do that, the annotation **@RequestParam()** might be used. The example of how to use it:
~~~
String queryMap(@RequestParam("keywords") String keywords)
~~~
This method invokes other methods for query data in database. The keywords might be a string with keywords or a empty string. 

The return value is a json string that includes the map name and map id. 
#### MapQueryDAO.java
##### 1. List<Map<String,String>> queryMapByName(String mapName)
This method query map records from table map_storage_info that the map_name equal to or contain mapName. The queried field includes map_id and map_name. The matched records may beyond one. Thus every record should be sealed in a Map object like HashMap with values mapName and mapId. For example:
~~~
HashMap.put("mapId","");
HashMap.put("mapName","");
~~~

Then every record should be added into a List object like LinkedList.

The mapName passed into this method might be a empty string or null. In this case, this method should return all the recodes in table map_storage_info.

### Log in status check
To implement this function, the interceptor class might be used. 

This function check if user logged in. If not, the request from this user to access specific url should be permitted. The urls include:
~~~
/upload
~~~
This list will be updated. Considering a convenient way to add new permitted url.

Besides, all first time requests from client to access the server should be added a attribute named isLoggedIn that value is false to session.