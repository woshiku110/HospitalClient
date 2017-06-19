/**
 * 调节正面的图片
 * */
function adjustAreaZhenMian(xscale,yscale){
    var map = document.getElementById('Map1');
    var areas = map.getElementsByTagName('area');
    for(var i=0;i<areas.length;i++){
        areas[i].coords = adjustCoords(areas[i].coords,xscale,yscale);
    }
}
/**
 * 调节后背的图片
 * */
function adjustAreaHouBen(xscale,yscale){
    var map = document.getElementById('CribMap');
    var areas = map.getElementsByTagName('area');
    for(var i=0;i<areas.length;i++){
        areas[i].coords = adjustCoords(areas[i].coords,xscale,yscale);
    }
}
function adjustCoords(oldCoord,xscale,yscale){
    var oldCoords = oldCoord.split(",");
    var newCoords = "";
    for(var i=0;i<oldCoords.length;i++){
        var newCoord;
        if(i%2==0){
            //x坐标
            newCoord = parseInt(oldCoords[i])*xscale;
        }else{
            //y坐标
            newCoord = parseInt(oldCoords[i])*yscale;
        }
        newCoords += newCoord + ",";
    }
    return newCoords.substr(0,newCoords.length-1);
}