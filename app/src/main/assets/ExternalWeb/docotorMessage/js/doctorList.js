window.onload = function(){
    var myId = needId();
    var myName = needName();
    searchDoctor(myId,myName);
    //searchDoctor(1,'王');
};
function needId(){
    return window.control.getMyId();
}
function needName(){
    return window.control.getMyName();
}
//检索医生列表
function searchDoctor(id,name){
    $.ajax({
        url: _serverHome + "manage/DoctorManage_searchDoctor",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            id:id,
            name:name
        },
        success: function(res) {
            var msg = JSON.parse(res.msg);
            parseSearchDoctor(msg)
            //console.log(msg );
        },
        error:function(res){
            console.log("test"+"fail"+res);
        }
    });
}

//解析检索医生列表
function parseSearchDoctor(msg){
    var container = document.getElementsByClassName('container')[0];
    //console.log(container);
    var code='';
    for(var i=0;i<msg.length;i++){
        code +='<div class=';
        code +='doctorList ';
        code +='onclick= window.location.href="doctorMessage.html'+'?id='+msg[i][0]+'"';
        code +='>';
        code +='\<div class="touxiang">';
        code +='\<img src="img/qq.jpg">';
        code +='\</div>'
        code += '\ <div class="message">';
        code += '\<h2>';
        code += msg[i][1];
        code += '\</h2>';
        code += '\<a>';
        code += '['+msg[i][4]+']';
        code += '\</a>';
        code += '\<br>';
        code += '\<div class="office">';
        code += '\<span>';
        code += msg[i][3];
        code += '\</span>';
        code += '\</div>';
        code += '\<span>';
        code += '擅长:';
        code += '\</span>';
        code += '\<span>';
        code += msg[i][2];
        code += '\</span>';
        code += '\</div>';
        code += '\</div>';
    }
    container.innerHTML=code;
}