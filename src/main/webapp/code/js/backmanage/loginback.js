/**
 * Created by Administrator on 2016/11/22.
 */
/**
 * Created by Administrator on 2016/11/19.
 */
$(function(){
    //声明省
    var onemenu = ["wap", "pc", "back", "common","app/version"]; //直接声明Array
//声明市
    var twomenu = [
        ["login", "register", "common", "index","purse","center","taskrecord","invite","daysign"],
        ["index", "common","login","bussiness","paycash","publishArticle"],
        ["login", "withdraw"],
        ["pcwap","pcback"],
        ["download"]
    ];
    //设置一个省的公共下标
    var pIndex = -1;
//先设置一级地址的值
    onemenu.forEach(function(){
        var option=document.createElement("option");
        option.setAttribute("value",++pIndex);
        option.innerHTML=onemenu[pIndex];
        document.getElementById("onemenu").appendChild(option);
    });
    $("#onemenu").change(function(){
        if ($("#onemenu option:selected").val()>-1){
            var val=$("#onemenu option:selected").val();
            console.log(val);
            var pIndex=-1;
            console.log("pIndex"+pIndex);
            var tm = twomenu[val];
            $("#twomenu").empty();
            tm.forEach(function(){
                var option=document.createElement("option");
                option.setAttribute("value",pIndex++);
                option.innerHTML=tm[pIndex];
                document.getElementById("twomenu").appendChild(option);
            });
        }
    });
    //$(".CDNaddress").change(

});
var imagefile='';
function menu(){
    var oneselect=$("#onemenu option:selected");
    var onemenu=oneselect.text();
    var twoselect=$("#twomenu option:selected");
    var twomenu=twoselect.text();
    var myReg = /^[\u4e00-\u9fa5]+$/;
    if (myReg.test($("#title").val())||$("#title").val()==null){
        alert("命名格式不正确");
    }else{
        var timestamp=new Date().getTime();
    }
    console.log(timestamp);
    var title=$("#title").val();
    var imgselect=$("#imgtype option:selected");
    var imgtype=imgselect.text();
    $("#imgtype").attr("name",imgtype);
    if(oneselect.val()!=-1&&timestamp!=undefined){
        var pathaddress='/'+onemenu+'/'+twomenu+'/'+title+timestamp;
    }
    $("#cdnaddress").attr("name",pathaddress);
};
function loadupyun(){
    menu();
    var path= $("#cdnaddress").attr("name");
    var suffix=$("#imgtype").attr("name");
    var data="[]";
    var jsonarray=eval('('+data+')');
    var jsonstring='';
    var arr  =
    {
        "path" :path,
        "files" :imagefile,
        "suffix":suffix,
    };
    jsonarray.push(arr);
    jsonstring=JSON.stringify(jsonarray);
    $.ajax({
        type:'post',
        url:'/backward/uploadUnyunFiles',
        dataType:'json',
        contentType:'application/json',
        data:jsonstring,
        success:function(params){
            var json=eval(params);
            if(json.data!=null&&json.errorCode==0){
                alert("a");
            }else if(json.errorCode==4){
                alert("b");
            }
        },error:function(data){
            console.log(jsonstring);
            alert("c");
        }
    })
}
//function jsonstring(){
//
//}