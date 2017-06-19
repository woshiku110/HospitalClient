/*function generItems(){
    var listDiv = document.getElementById('list_div_id');
    var msg = "";
    for(var i=0;i<2;i++){
        if(i%2 == 0){
            msg += gengerItem("../images/test.jpg",'周明',20+i,true,'南京路',"2016/12/13 12:08:22","候诊");
        }else{
            msg += gengerItem("../images/test.jpg",'周明',20+i,false,'南京路',"2016/12/13 12:08:22","候诊");
        }
    }
    listDiv.innerHTML = msg;
}*/
function gengerItem(orderId,imgPath,name,age,isNan,addr,date,state){
    var item = "";
    item += "\<div class=list_item_div>";
        item += "\<div class=list_item_div_left>";
            item += "<img class=list_item_round_img "+"src="+imgPath+"/>";
        item += "\</div>";
        item += "\<div class=list_item_div_content>";
            item += "\<div class=list_item_div_content_left>";
                item += "\<h1>"+name+"\</h1>";
                item += "\<div class=list_item_div_content_left_sex>";
                    item += "\<div class=list_item_div_content_left_sex_txt>";
                        item += "\<span>"+age+"\</span>";
                    item += "\</div>";
                    if(isNan){
                        item += "<img class=list_item_div_content_left_sex_icon "+"src="+"../images/ico_male.png/>"
                    }else{
                        item += "<img class=list_item_div_content_left_sex_icon "+"src="+"../images/ico_female.png/>"
                    }
                item += "\</div>";
                item += "\<h2>";
                    item += addr;
                item += "\</h2>";
            item += "\</div>";
            item +="\<div class=list_item_div_content_right>";
                item += "\<h3 style=float:right >";
                    item += date;
                item += "\</h3>";
                item += "\<div style=width:100%;height:30px >";
                item += "\</div>";
                item += "\<div style=min-height: 35px>";
                    if(state == '候诊'){
                        item += "\<div onclick=userClick(this,"+orderId+") class=list_item_div_content_right_state_blue>";
                    }else{
                        item += "\<div onclick=userClick(this,"+orderId+") class=list_item_div_content_right_state>";
                    }
                        item += state;
                    item += "\</div>";
                item += "\</div>";
            item += "\</div>";
        item += "\</div>";
    item += "\</div>";
    return item;
}
/**
 * 得到候诊列表
 * */
function getHoldDiagsisList(){
    alert("load dialoglist"+_baseUrl);
    $.ajax({
        url: _baseUrl + "jfs1.1/yuyue/PatientPersonalCenter_waitList",
        type: 'post',
        dataType:'json',
        async:true,
        data: {
            "token":_token,
            "state":4
        },
        success: function(res) {
            if(res.success){
                var result = JSON.parse(res.msg);
                parseHoldDiagsisList(result);
            }
        },
        error:function(res){

        }
    });
}

function userClick(thisa,orderId){
    console.log(thisa+"orderId"+orderId);
}