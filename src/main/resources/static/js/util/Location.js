//2. Location List of Current Edited Map
function get_location_list(map_id){
var ret='';
$(document).ready(function(){

$.ajax({
url: "/mapEdit/getLocationItemsList?mapId="+mapId,
type: "GET",
success:function(data){
ret=data;
}
});

});


return JSON.parse(ret);
}


//3.. get location items


function location_items(locationId){
var ret='';
$(document).ready(function(){
$.ajax({
url: "/mapEdit/getLocationItems?locationId="+locationId,
type: "GET",
success:function(data){
ret=data;
}
});

});
return JSON.parse(ret);
}



    $("#userMap").fileinput({
        allowedFileTypes: 'image',
        allowedFileExtensions: ['jpg', 'png'],
        required: true
    });



  //6.. Location Edit Pop up window


$.ajax({
    type: "POST",
    url: "/mapEdit/addLocation",
    data: new FormData(document.forms[0]),
    cache: false,
    success: function(htm){
        alert(htm);
    }
});