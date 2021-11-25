# Map Edit System Design Document 
This document designs only for the map edit system.  

The design of frontend is flexible. Developers can choose any JavaScript frameworks like Vue.js, React.js. The JQuery is also necessary. 

However, the style design must be based on Bootstrap 4.

## Prototype of The Frontend Interface
As the image below, the user interface should look like:

<span id="prototypeImage"></span>
![frontend interface 1.0](https://raw.githubusercontent.com/yihaoyang3/image-rep/main/Front%20End%20Prototype.drawio.png)

### Components to be Implemented 
When user enter the map edit platform, the load order of components should be:
1. Loading map blocks;
2. Loading location items and display them in the location list. If location list is null, then display a message 
   like: There is no location for now.
3. buttons of the zoom adjustment.
4. Other components like popup windows.

The chapter below will describe the components in detail. 
##### 1. Map Container
The map container receives map data from backend with json. The map data format is:
~~~
[
    [str0,str1...strn],
    [str0,str1...strn],
    ...
    [str0,str1...strn]
]
~~~
As it shows, the json is a two-dimensional array. Every str element is data of one map block via base64. Every map block is a square. Besides, the backend would also send a *mapId* of this map.

**Firstly, the developer must provide a JS function to display the map blocks in the container.** The style of the container looks like the grid in the image. Details will be provided in chapter 4. 

The job of map container is to display map blocks. The developer must provide a css class called *map-container*:
~~~
.map-container{
    ...
}
~~~

Notice, the map container must be responsive, which means the size of the container changes along with the browser size. Using Bootstrap to implement the responsive CSS style. 

Thus the html code may look like:
~~~
<div class="container">
    <div class="map-container" id=mapId>
        ...
    </div>
</div>
~~~
Remember to set the mapId as the id of the div label.

#### 2. Location List of Current Edited Map
Using Ajax to load the data.
This list shows all the user-defined locations of the current edited map. The request method is get request url of the location items is:
~~~
/mapEdit/getLocationItemsList?mapId=...
~~~
The backend server will also return a json object array that including all location items of the map. The json data includes fields:
~~~
[
    {
        'locationId':string，
        'locationName':string，
    },
    ...
]
~~~

The location list should show preview of all the location items inside the list. The preview only display the location name. The locationId is invisible for user, but should be accessible for program since other components will use locationId to request more information. 

#### 3. Location Items
A popup window appear at then center of the browser while a user clicks the location item in the list. The location name, description, medias should be displayed inside this window. 
Using Ajax to request data. The request method must be get and request url is:
~~~
/mapEdit/getLocationItems?locationId=...
~~~
This component obtains the locationId
The json data format returned from would like:
~~~
[
    {
        locationCoordinateX: ,
        locationCoordinateY: , 
        //X and Y represent the coordinate of the location in the map blocks.
        locationName: ,
        //description medias is an object arrays. It may include several objects.
        locationDescriptionMedia: [
            {
                type:[image|video],
                name: ,
                url: string
            },
            ...
        ]
    },
    ...
]
~~~

#### 4. Unselected Map Blocks
The developer must provide a CSS class:
~~~
.map-block-unselected{
    ...
}
~~~
The map-block class may define attributes including width and width. The final style must match the grid in the [interface preview image](#prototypeImage).

The html code of a map block may look like:
~~~
<div class="map-block">
    <img src="...">
</div>
~~~

Besides, the developer should provide a function of mouse event: onmousemove and onmouseout. The onmousemove event changes the style class *map-block-unselected* to *map-block-mouse-on* 
~~~
.map-block-mouse-on{
    ...
}
~~~
when mouse move on the map block to give a highlight. The class *map-block-mouse-on* must be different from the highlight class of selected map blocks.

#### 5. Selected Map Blocks
The developer should provide CSS class for selected map block when a map block is selected:
~~~
.map-block-selected{
    ...
}
~~~
Only one map block can be selected at the same time.

The style should be same with the [interface preview image](#prototypeImage).

#### 6. Location Edit Popup Window
While user selects a map block, a popup window appears on that block for user to edit. User can write a name, description, and upload media(including image and video) for this position. Then user can use save button to submit this edit or use cancel to quit the widow. 

Using Ajax to submit the data added by user. Using multipart/form-data as the carrier to upload the data.

The upload method is post and url for uploading is:
~~~
/mapEdit/addLocation
~~~
The developer may use *input label* to choose files. For this component there are at 3 input labels for name, description and media. The image and video share one input label. However, user can select more than one images but only one video. Please consider how to implement. Every labels must have id and name. Their id and name are same. For example:
~~~
<input id="locationName" name="locationName" type="text">
<input id="locationDescriptionMedia" name="locationDescriptionMedia" type="file" multiple>
~~~

Besides, the form data must includes the map block's coordinate X and Y. The X refers to the row and Y refers to the column. 

There must have a call back function to handle the response of Ajax request. This function should display the message returned from server. The returned messages have two type, success and fail. Considering how to user JQuery Ajax function to implement this requirement.

#### 7. Zoom Adjustment Button

As it is shown in the [interface preview image](#prototypeImage), there are two button to zoom the map. The developer should consider how to implement this feature by change the width and height of map blocks. Notice, there should be limits for both the maximal and minimal size of map block.


### Second Iteration 
The image below shows the preview of the homepage.
![homepage](https://raw.githubusercontent.com/yihaoyang3/image-rep/main/project-homepage.png)

#### 1. Navigation bar
For this function, the thymeleaf is necessary. Click the link to read the document of thymeleaf https://www.thymeleaf.org/documentation.html.  

The navigation bar always appear on the top of this page. 

The left side of this navigation bar has a icon that while user click it, it sends a request to ***root url "/"*** . 

The right side of this navigation bar shows a link, it acquires a attribute called isLoggedIn from session. Code example:
~~~
<div th:unless='${#session.getAttribute("isLoggedIn")}'>
    ...
</div>
 <div th:if='${#session.getAttribute("isLoggedIn")}'>
    ...
 </div>
~~~

If the user logged in, it acquires userName and userId from session and shows the userName. The userId should also be included in html file but it should be invisible. If there are no userName and uerId, it shows a link that the text is "click to log in".

#### 2. Search box and Map list
The search box for user to input text. Clicking the search button, the request will be sent to the url ***/map/query*** with user inputted text by json format.
The data format is:
~~~
{
    "keywords" : user inputted text
}
~~~
The ajax must be used. The data format returned from server like:
~~~
[{
    "mapId":"1",
    "mapName":"map1"
},
{
    "mapId":"2",
    "mapName":"map2"
},
{
    "mapId":"3",
    "mapName":"map3"
},
...
{
    "mapId":"n",
    "mapName":"mapN"
}]
~~~
While receiving the data from server, it traverses the data and display the mapName in the map list. The mapId should also be included in the html element but it must be invisible.

Besides, the **create new map** button should also be implemented. By clicking this button, it calls a method which will be describe in next chapter. 

#### 3. Map load UI
By clicking the **create new map**, a popup window shows for user to upload file. The image below is a preview:
![upload](https://github.com/yihaoyang3/image-rep/blob/main/project-upload.png?raw=true)

The user can only submit one image(jpg,png).

While user click submit button, the request should be send to **url /upload** with data by form submitting. The enctype must be multipart/form-data.

A form example:
~~~
<form id="mapForm" action="/upload" method="post" enctype="multipart/form-data">
    <input type="text" id="mapName" name="mapName">
    <input type="file" id="userMap" name="map">
</form>
~~~
