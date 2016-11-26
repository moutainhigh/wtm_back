/**
 * Created by Administrator on 2016/11/22.
 */
$(function(){
    //声明一级目录
    var onemenu = ["wap", "pc", "back", "common","app/version"]; //直接声明Array
//声明二级目录
    var twomenu = [
        ["login", "register", "common", "index","purse","center","taskrecord","invite","daysign"],
        ["index", "common","login","bussiness","paycash","publishArticle"],
        ["login", "withdraws", "CDNimg" , "CDNfile","inforsearch"],
        ["pcwap","pcback","common"],
        ["download"]
    ];
    var pIndex = -1;//设置公共下标
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
    })
});
function menu(){
    var oneselect=$("#onemenu option:selected");
    var onemenu=oneselect.text();
    var twoselect=$("#twomenu option:selected");
    var twomenu=twoselect.text();
    var myReg = /^[\u4e00-\u9fa5]+$/;
    if (myReg.test($("#title").val())||$("#title").val()==null){
        alert("命名格式不正确，请重新命名");
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
var imagefile="";
var flag="";
function loadupyun(){
    menu();
    var path= $("#cdnaddress").attr("name");
    var suffix=$("#imgtype").attr("name");
    var obj=new UploadFiles(path,imagefile,suffix,flag)
    var requestObj = JSON.stringify(obj);
    console.log(obj);
    console.log(requestObj);
    if(path!=null&&path!=undefined&&imagefile!=null){
        $.ajax({
            type:'post',
            url:'/backward/uploadUpyunFiles',
            contentType: "application/json",
            dataType:'json',
            timeout:180000,
            data:requestObj,
            success:function(params){
                var json=eval(params);
                if(json.data!=null&&json.errorCode==0){
                    $("#cdnaddress").empty();
                    $("#cdnaddress").html(json.data);
                    var li=document.createElement('li');
                    li.innerHTML=json.data;
                    document.getElementsByClassName("cdnrecord")[0].appendChild(li);
                }else if(json.errorCode==4){
                    alert(json.message);
                }
            },error:function(data){
                console.log(jsonstring+"没通过");
                alert("内容上传失败，请重试");
            }
        })
    }
}
function readAsDataURL(file){
    var file = file.files[0];
    if(!/image\/\w+/.test(file.type)){
        alert("看清楚，这个需要图片！");
        return false;
    }
    var reader = new FileReader();
    //将文件以Data URL形式读入页面
    reader.readAsDataURL(file);
    reader.onload=function(e){
        var result=$("#previewimg");
        result.attr("src",this.result);
        imagefile=this.result;
        flag=0;
    }
}
function readAsText(file){
    var file = file.files[0];
    var reader = new FileReader();
    //将文件以文本形式读入页面
    reader.readAsText(file);
    reader.onload=function(f){
        //显示文件
        $("#fileshowcont").html(this.result);
        imagefile=this.result;
        flag=1;
    }
}
$.fn.serializeObject = function() {
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
function UploadFiles(path,files,suffix,flag){
    this.path=path;
    this.files=files;
    this.suffix=suffix;
    this.flag=flag
}


