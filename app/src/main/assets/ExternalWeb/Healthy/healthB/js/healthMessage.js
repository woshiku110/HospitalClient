var choosed = false;
//健康详情
function getMyFamilyDetailed(){
        $.ajax({
            url: _serverHome + "yuyue/PatientPersonalCenter_getMyFamilyDetailed",
            type: 'post',
            dataType:'json',
            async:true,
            data: {
                token:'slXKGemXIzrgAD7/WRFqvgDHJWAFQ7wtoqSiqDPDTMU2g5RA6HOJQg__',
                id:_pid
            },
            success: function(res) {
                var msg = JSON.parse(res.msg)
                parseListMyFamily(msg);
                console.log(msg );
            },
            error:function(res){
                console.log("test"+"fail"+res);
            }
        });
}
function parseListMyFamily(msg){
    var picture = document.getElementById('picture');
    picture.src = _fileBed+msg[10];
    var name = document.getElementById('name');
    name.innerText = msg[1];
    var sex = document.getElementById('sex');
    sex.innerText = msg[2];
    var anaphylactogen = document.getElementById('anaphylactogen');
    anaphylactogen.innerText = msg[5];
    var avoid = document.getElementById('avoid');
    avoid.innerText = msg[6];
    var medictialH = document.getElementById('medictialH');
    medictialH.innerText = msg[7];
    var bloodType = document.getElementById('bloodType');
    bloodType.innerText = msg[4]+'型';

}

//身高体重可修改
function noCorrectMessage(){
    var height = document.getElementById('height');
    var weight = document.getElementById('weight');
    /*height.removeAttribute('disabled');
     weight.removeAttribute('disabled');*/
    height.setAttribute('disabled',true);
    weight.setAttribute('disabled',true);
}
//身高体重不可修改
function correctMessage(){
    var height = document.getElementById('height');
    var weight = document.getElementById('weight');
    height.removeAttribute('disabled');
    weight.removeAttribute('disabled');
    /*height.setAttribute('disabled',false);
    weight.setAttribute('disabled',false);*/
}

function showTitleBarBt(){
    window.control.showTitleBarBt();
}
