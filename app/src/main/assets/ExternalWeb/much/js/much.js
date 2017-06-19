function getSecondColumn(){
    $.ajax({
        url: _serverHome+"manage/ArticleManage_getSecondColumn",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            "pid":3
        },
        success: function(res) {
            var msg = JSON.parse(res.msg);
            console.log(msg);
            parseGetSecondColumn(msg)
        },
        error:function(res){
            alert("分配失败！");
            console.log("test"+"fail"+res);
        }
    });
}
var _msg;
function parseGetSecondColumn(msg){
    var code = '';
    var content = document.getElementsByClassName('content')[0];
    _msg = msg;
    for(var i=0;i<msg.length;i++){
        code +='\<div class="contentList"';
        code +='onclick = itemClick(' +i+ ')';
        code +='>';
        code +='\<a class="font">';
        code +=msg[i][1];
        code +='\</a>';
        code +='\<a class="img"></a>';
        code +='\</div>'
    }
    content.innerHTML = code;
}

function setMyPid(pid){
    window.control.setMyPid(pid);
}

function itemClick(index){
    console.log(_msg[index][0]);
    setMyPid(_msg[index][0]);
}
window.onload = function(){
    getSecondColumn()
};
