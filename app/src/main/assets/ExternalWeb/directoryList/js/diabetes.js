function getArticleList(t_id){
    $.ajax({
        url: _serverHome+"manage/ArticleManage_getArticleList",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            "pid":t_id
        },
        success: function(res) {
            var msg = JSON.parse(res.msg);
            console.log(msg);
            parseGetArticleList(msg);
        },
        error:function(res){
            alert("分配失败！")
            console.log("test"+"fail"+res);
        }
    });
}
function parseGetArticleList(msg){
    var code = '';
    var container = document.getElementsByClassName('container')[0];
    for(var i=0;i<msg.length;i++){
        code += '\<div class="contentList"';
        code += 'onclick= window.location.href="articleContent.html'+'?pid='+msg[i][0]+'"';
        code += '>';
        code += '<img src=';
        code += _fileBed+msg[i][4];
        code += '>';
        code += '\<div class="font">';
        code += '\<a class="title">';
        code += msg[i][1];
        code += '</a>';
        code += '\<a class="time">';
        code += msg[i][6];
        code += '\</a>';
        code += '\</div>';
        code += '\<div class="right"></div>';
        code += '\</div>';
    }
    container.innerHTML = code;
}
/*赵莉做的部分*/
/*window.onload = function (){
    var t_id = getParam('pid');
    getArticleList(t_id)
};*/
/*周明做的部分*/
function getMyPart(){
    return window.control.getMyPid();
}
window.onload = function (){
    var t_id = getMyPart();
    getArticleList(t_id)
};