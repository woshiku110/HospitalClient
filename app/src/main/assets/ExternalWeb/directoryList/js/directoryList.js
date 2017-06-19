function getSecondColumn(){
    $.ajax({
        url: _serverHome+"manage/ArticleManage_getSecondColumn",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            "pid":2
        },
        success: function(res) {
            var msg = JSON.parse(res.msg);
            console.log(msg);
            parseGetSecondColumn(msg);
        },
        error:function(res){
          /*  alert("分配失败！")*/
            console.log("test"+"fail"+res);
        }
    });
}
/**
 * 周明做
 * */
var _msg;
function  parseGetSecondColumn(msg){
    var code = '';
    var container = document.getElementsByClassName('container')[0];
    _msg = msg;
    code += '\<table>';
    for(var i=0;i<msg.length;i++){
        if(i%3==0){
            code += '\<tr>';
        }
        code += '\<td ';
        code +='onclick= itemClick(' +i+ ')';
        code +='>';
        code += '\ <br>';
        code += '\<img src=';
        code += _fileBed+msg[i][2];
        code += '>';
        code += '\<a>';
        code += msg[i][1];
        code +='\</a>';
        code += '\<br>';
        code += '\</td>';
        if((i%3)-3==0){
            code += '\</tr>';
        }
    }
    code += "\</table>"
    container.innerHTML = code;
}

function setMyPid(pid){
    window.control.setMyPid(pid);
}
function itemClick(index){
    //alert(pid);
    console.log(_msg[index][0]);
    setMyPid(_msg[index][0]);
}
window.onload = function(){
    getSecondColumn();
    console.log(_serverHome);
};