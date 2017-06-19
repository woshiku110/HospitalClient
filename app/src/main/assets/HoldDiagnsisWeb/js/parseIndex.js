/**
 * Created by Administrator on 2017/2/13.
 */
function parseHoldDiagsisList(res){
    var result = res;
    var listDiv = document.getElementById('list_div_id');
    var msg = "";
    for(var i=0;i<result.length;i++){
        //imgPath,name,age,isNan,addr,date,state
        if(result[i][3]=='男'){
            if(result[i][6]=='4'){
                //touxiang result[i][2]
                /*console.log(strToYear(result[i][4]));
                console.log("now"+getNowYear());*/
                var age = getNowYear() - strToYear(result[i][4]);
                msg += gengerItem(result[i][0],"../images/test.jpg",result[i][1],age,true,result[i][5],result[i][7],"候诊");
            }else if(result[i][6]=='5'){
                msg += gengerItem(result[i][0],"../images/test.jpg",result[i][1],age,false,result[i][5],result[i][7],"正在候诊");
            }
        }
    }
    listDiv.innerHTML = msg;
}
function strToYear(str){
    var tempStrs = str.split(" ");
    var dateStrs = tempStrs[0].split("-");
    var year = parseInt(dateStrs[0], 10);
    return year;
}
function getNowYear(){
    var year = new Date().getFullYear();
    return year;
}