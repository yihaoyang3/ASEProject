// zoom-in and zoom-out
function zoombig() {
    let mapBlock = document.getElementsByClassName("block");
    new_w = mapBlock[0].width * 1.1 + "px";
    for (let i = 0; i < mapBlock.length; i++) {
        mapBlock[i].style.width = new_w;
        mapBlock[i].style.height = "auto";
    }
}

function zoomsmall() {
    let mapBlock = document.getElementsByClassName("block");
    new_w = mapBlock[0].width / 1.1 + "px";
    for (let i = 0; i < mapBlock.length; i++) {
        mapBlock[i].style.width = new_w;
        mapBlock[i].style.height = "auto";
    }
}

function getDefault() {
    let mapBlock = document.getElementsByClassName("block");
    for (let i = 0; i < mapBlock.length; i++) {
        mapBlock[i].style.width = "100%";
        mapBlock[i].style.height = "auto";
    }
}

// move picture
let pic = document.getElementById("vis");
let isDrag = false;
let disX, disY;

pic.onmousedown = function(e){
    isDrag = true;
    this.style.cursor = "move";
    e = e||window.event;
    let x = e.clientX;
    let y = e.clientY;
    disX = x - this.offsetLeft;
    disY = y - this.offsetTop;
}

pic.onmousemove = function(e){
    if (!isDrag){return;}
    e = e||window.event;
    let x = e.clientX;
    let y = e.clientY;
    pic.style.left = x - disX + "px";
    pic.style.top = y - disY + "px";
}

pic.onmouseup = function(){
    isDrag = false;
    pic.style.cursor = "default";
}
