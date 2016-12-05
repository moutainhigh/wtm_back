/**
 * Created by Administrator on 2016/12/1.
 */
$(function(){
    datepicker();/*初始化日期*/
    //headSearch();/*初始化用户信息*/
    $(".referbtn").click(function(){
        headSearch();
        queryClassChange();
    }); /*按搜索条件查询用户信息*/
    queryClassChange();
});
function queryClassChange(){
    $("#queryclass").change(function(){
        console.log($("*").is(".userScoreTitle"));
        $(".refercontbtn").attr("name","0");
        $(".refercontbtn").click(function(){
            $(".refercont").children().remove("li");
        })
    })/*改变查询内容初始化页码*/
}//查询条件改变时重置点击效果
function headSearch(){
    $(".tcdPageCode").children().remove();
    var pageIndex=1;
    var pageSize=20;
    var memberName=$("#usename").val();
    var memberId=$("#useId").val();
    var telephone=$("#usetel").val();
    var nickName=$("#wechatname").val();
    var registerStartTime=get_unix_time($("#startime").val());
    var registerEndTime=get_unix_time($("#endtime").val());
    if($("#startime").val()==""){registerStartTime="1475193600"}
    if($("#endtime").val()==""){registerEndTime=get_unix_time(getNowEndDate());}
    if(memberName==""){memberName=null}if(memberId==""){memberId=null}if(telephone==""){telephone=null}if(nickName==""){nickName=null}
    var obj=new UserInforCondi(memberName,nickName,telephone,memberId,registerStartTime,registerEndTime,pageIndex,pageSize);
    var requestObj = JSON.stringify(obj);
    console.log(requestObj);
    userinforscoretitle();
    $(".refercont").remove();
    getMemberInformation(requestObj);
    var checkTotal=$(".tcdPageCode").attr("name");
    if(checkTotal>pageSize){
        var pageCount=Math.ceil(checkTotal/pageSize);
        $(".tcdPageCode").createPage({
            pageCount:pageCount,
            current:pageIndex,
            backFn:function(p){
                console.log("当前页是第"+p+"页");
                obj=new UserInforCondi(memberName,nickName,telephone,memberId,registerStartTime,registerEndTime,p,pageSize);
                requestObj = JSON.stringify(obj);
                $(".refercont").remove();
                getMemberInformation(requestObj);
            }
        });
    }
}//查询用户信息
function getMemberInformation(requestObj){
    $.ajax({
        type:'post',
        async:false,
        dataType:'json',
        contentType:'application/json',
        url:'/backward/getMemberInformation',
        data:requestObj,
        success:function(params){
            var json=eval(params);
            if(json.data!=null&&json.errorCode==0){
                if(json.data.total==0){
                    $(".tcdPageCode").attr("name",json.data.total);
                    alert("没有符合查询条件的对象");
                }else {
                    $(".tcdPageCode").attr("name",json.data.total);
                    json.data.list.forEach(function(e){
                        userinforscorecont(e);
                    })
                }
            }
            else if(json.errorCode==4){
                alert(json.message);
            }
        },error:function(data){
            alert("加载页面失败，请重新加载1")
        }
    });
}//查询用户信息请求
function confirmUserInforSearch(object){
    $(object.parentNode).siblings().children(".refercontbtn").attr("name","0");
    var clickCount=object.getAttribute("name");
    if(clickCount==0){
        var flag = $("#queryclass").get(0).selectedIndex;
        console.log(flag);
        var memberId=object.parentNode.childNodes[0].innerHTML;
        if (flag != 0) {
            if($(object.parentNode).siblings().children().is("li")){
                $(object.parentNode).siblings().children().remove("li");
            };
            var pageIndex=1;
            var pageSize=10;
            GetPageInfor(memberId,flag,pageIndex,pageSize,object);
            var checkTotal=$(".PageCode").attr("name");
        }
    }else {
        object.setAttribute("name","0");
        $(object.parentNode).children().remove("li")
    }
}//查询确定用户详细信息
function GetPageInfor(memberId,flag,pageIndex,pageSize,object){
    $.ajax({
        type: 'post',
        url: '/backward/getMemberInformationDetail',
        data:{memberId:memberId,flag:flag,pageIndex:pageIndex,pageSize:pageSize},
        success: function (params) {
            var json = eval(params);
            if (json.errorCode == 0){
                if(flag==1){
                    userScoreTitle(memberId);
                    userScoreCont(memberId,json.data);
                }else if(flag==2){
                    userFlowTitle(memberId);
                    if(json.data.total>pageSize){
                        CreatePage(memberId,flag,pageIndex,pageSize,object,json.data.total);
                    }else {$(".PageCodeLi").css("display","none");}
                    if(json.data.total==0){
                        userFlowContent(memberId,"0");
                    }
                    json.data.list.forEach(function(e){
                        userFlowContent(memberId,e);
                    });
                }
                else{
                    userContTitle(memberId);
                    if(json.data.total>pageSize){
                        CreatePage(memberId,flag,pageIndex,pageSize,object,json.data.total);
                    }else {$(".PageCodeLi").css("display","none");}
                    if(flag==4){
                        if(json.data.total==0){
                            oficialtitle(memberId,"0");
                        }
                        json.data.list.forEach(function(e){
                            oficialtitle(memberId,e);
                        });
                        $(".PageCodeList").css("display","none");
                    }else{
                        if(json.data.total==0){
                            userContContent(memberId,"0");
                        }
                        json.data.list.forEach(function(e){
                            userContContent(memberId,e);
                        });
                    }
                }
                object.setAttribute("name","1");
            }
            else if (json.errorCode == 4){
                alert(json.message);
                $("#queryclass").get(0).options[0].selected = true;
            }
        }, error: function (data) {
            alert("加载页面失败，请重新加载");
            $("#queryclass").get(0).options[0].selected = true;
        }
    });
}//查询确定用户详细信息请求
function getOfficialDetail(object){
    var pageIndex=1;
    var pageSize=10;
    var id=$(object).children().eq(0).text();
    var originId=$(object).children().eq(2).text();
    var clickCount=object.getAttribute("name");
    $(object.parentNode).siblings().children().remove("li");
    $(object.parentNode).siblings().children(".officialcount").attr("name","0");
    var memberId=$(object).children(":first").text();
    if(clickCount==0){
        oficialdetailtitle(originId);
        officialinforrequest(id,pageIndex,pageSize,object,memberId)
        $(".readOreye").change(function(){
            $(".oficialcontbody").remove();
            officialinforrequest(id,pageIndex,pageSize,object,memberId)
        });
        object.setAttribute("name","1");
    }else{
        object.setAttribute("name","0");
        $(object.parentNode).children().remove("li");
    }
}//查询公众号信息请求
function officialinforrequest(id,pageIndex,pageSize,object,memberId){
    var flag="00";
    var readOreye=$(".readOreye").get(0).selectedIndex;
    var originId=$(object).children().eq(2).text();
    $.ajax({
        type: 'post',
        url: '/pc/admin/official/getTaskPoolDto',
        timeout:180000,
        data: {officialAccountId:id, type:readOreye,pageIndex:pageIndex,pageSize:pageSize},
        success: function (params) {
            var json=eval(params);
            if (json.errorCode == 0) {
                if(json.data.total>pageSize){
                    CreatePage(memberId,flag,pageIndex,pageSize,object,json.data.total);
                }else {$(".PageCodeLi").css("display","none");}
                if(json.data.total==0){
                    oficialdetailcont(originId,"0");
                }
                json.data.list.forEach(function(e){
                    oficialdetailcont(originId,e,readOreye);
                });
            }else if(json.errorCode==4){
                alert(json.message);
            }
        }, error: function (data) {
            alert("页面加载错误，请重试");
        }
    })
}
function userinforscoretitle(){
    $(".referlist").empty();
    var listlength=11;
    var selectlength=6;
    var div=[];
    var option=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:name*/
    divcont[0] = ["refertitle"];
    divcont[1] = ["col-xs-1", "用户Id"];divcont[2] = ["col-xs-1", "用户名"];divcont[3] = ["col-xs-1", "微信昵称"];
    divcont[4] = ["col-xs-1", "电话"];divcont[5] = ["col-xs-1", "性别"];divcont[6] = ["col-xs-1", "地址"];
    divcont[7] = ["col-xs-1", "邀请码"];divcont[8] = ["col-xs-2", "注册时间"];divcont[9] = ["col-xs-1", "来源"];
    divcont[10] = ["col-xs-1","是否禁用"];divcont[11] = ["col-xs-1"];
    var selectcont=new Array();  /*[i][0]:innerHTML*/
    selectcont[0]=["查询内容"];selectcont[1]=["米币总数"];selectcont[2]=["米币流水"];selectcont[3]=["历史任务记录"];
    selectcont[4]=["公众号信息"];selectcont[5]=["邀请记录"];
    var li=createElementList(div,listlength,divcont,"0");//0：需要加载页码，1：不需要加载页码
    console.log(li);
    var select=createElementSelect(option,selectlength,selectcont);
    select.setAttribute("id","queryclass");
    div[10].appendChild(select);
    document.getElementsByTagName("ul")[1].appendChild(li);
}//用户信息
function userinforscorecont(data){
    var listlength=11;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:name*/
    divcont[0] = ["refercont","",data.memberId];
    divcont[1] = ["col-xs-1", data.memberId];divcont[2] = ["col-xs-1", data.memberName];
    if(data.nickName==null||data.nickName==""){data.nickName="---"}divcont[3] = ["col-xs-1", data.nickName];
    divcont[4] = ["col-xs-1", data.telephone];
    if(data.sex==0){data.sex="保密"}else if(data.sex==1){data.sex="男"}else if(data.sex==2){data.sex="女"}
    divcont[5] = ["col-xs-1", data.sex];
    if(data.address==null||data.address==""){data.address="---"}divcont[6] = ["col-xs-1",data.address];
    divcont[7] = ["col-xs-1",data.inviteCode];
    if(data.createTime==null||data.createTime==""){data.createTime=="---"}
    divcont[8] = ["col-xs-2",getLocalTime(data.createTime)];
    divcont[9] = ["col-xs-1", data.source];
    divcont[10] = ["col-xs-1"];divcont[11] = ["col-xs-1 refercontbtn","","0"];
    var li=createElementList(div,listlength,divcont,"0");//0：不需要divbox，1：需要divbox
    var label=document.createElement("label");var input1=document.createElement('input');
    input1.className="switchOn";input1.setAttribute("type","checkbox");input1.setAttribute("data-on-color","primary");
    input1.setAttribute("data-off-color","warning");input1.setAttribute("data-size","small");input1.setAttribute("data-on-text","开启");
    input1.setAttribute("data-off-text","禁用");input1.setAttribute("id",data.memberId);
    var input2=document.createElement('input');
    input2.setAttribute("type","button");input2.setAttribute("value","查询");
    div[10].setAttribute("onClick","javaScript:confirmUserInforSearch(this)");
    div[2].setAttribute("title",data.nickName);div[5].setAttribute("title",data.address);
    div[9].appendChild(label);label.appendChild(input1);div[10].appendChild(input2);
    if(data.isForbidden==0){
        $("#"+data.memberId+"").bootstrapSwitch('state', true);
    }else{
        $("#"+data.memberId+"").bootstrapSwitch('state', false);
    }
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function userScoreTitle(name){
    var listlength=6;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["referusertitle col-xs-12"];
    divcont[1] = ["col-xs-1", "米币总数"];divcont[2] = ["col-xs-1", "可用米币数"];divcont[3] = ["col-xs-2", "当前充值米币数"];
    divcont[4] = ["col-xs-2", "充值米币总数"];divcont[5] = ["col-xs-3", "即将可用米币数（关注）"];
    divcont[6] = ["col-xs-3", "即将可用米币数（邀请）"];
    var li=createElementList(div,listlength,divcont,"0");
    document.getElementsByName(name)[0].appendChild(li);
}//米币总数
function userScoreCont(name,data){
    var listlength=6;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    if(data==null){
        data.totalScore=data.avaliableScore=data.currentChargeScore=data.totalChargeScore=data.futureAvaliableFollowScore=data.futureAvaliableInvitedScore=0;
    }
    if(data.futureAvaliableFollowScore==null||data.futureAvaliableFollowScore==""){
        data.futureAvaliableFollowScore=0;
    }
    divcont[0] = ["referusercont col-xs-12"];
    divcont[1] = ["col-xs-1", data.totalScore];divcont[2] = ["col-xs-1", data.avaliableScore];
    divcont[3] = ["col-xs-2", data.currentChargeScore];
    divcont[4] = ["col-xs-2", data.totalChargeScore];divcont[5] = ["col-xs-3", data.futureAvaliableFollowScore];
    divcont[6] = ["col-xs-3", data.futureAvaliableInvitedScore];
    var li=createElementList(div,listlength,divcont,"0");
    document.getElementsByName(name)[0].appendChild(li);
}
function userFlowTitle(name){
    var listlength=7;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["referusertitle col-xs-12"];
    divcont[1] = ["col-xs-2", "流动类型"];divcont[2] = ["col-xs-2", "流动详情"];divcont[3] = ["col-xs-2", "创建时间"];
    divcont[4] = ["col-xs-2", "流动前"];divcont[5] = ["col-xs-2", "流动后"];
    divcont[6] = ["col-xs-1", "流动米币"];divcont[7] = ["col-xs-1", "流动状态"];
    var li1=createPageElement();
    var li=createElementList(div,listlength,divcont,"0");
    document.getElementsByName(name)[0].appendChild(li);
    document.getElementsByName(name)[0].appendChild(li1);
}//米币流水
function userFlowContent(name,data){
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var listlength=7;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:name*/
    divcont[0] = ["referusercont col-xs-12"];
    if(data=="0"){
        divcont[1] = ["col-xs-2", "---"];divcont[2] = ["col-xs-2", "---"];
        divcont[3] = ["col-xs-2", "---"];
        divcont[4] = ["col-xs-2", "---"];divcont[5] = ["col-xs-2", "---"];
        divcont[6] = ["col-xs-1", "---"];divcont[7] = ["col-xs-1", "---"];
    }else {
        if(data.isFinished==0){
            data.isFinished="未完成";
        }else {data.isFinished="已完成";}
        divcont[1] = ["col-xs-2", data.typeName];divcont[2] = ["col-xs-2", data.typeDesc];
        divcont[3] = ["col-xs-2", getLocalTime(data.createTime)];
        divcont[4] = ["col-xs-2", data.memberScoreBefore];divcont[5] = ["col-xs-2", data.memberScoreAfter];
        divcont[6] = ["col-xs-1", data.flowScore];divcont[7] = ["col-xs-1", data.isFinished];
    }
    var li=createElementList(div,listlength,divcont,"0");
    div[1].setAttribute("title",data.typeDesc);
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}
function userContTitle(name){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    var listlength=4;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["referusertitle col-xs-12"];
    if(queryclassfun==3){
        divcont[1] = ["col-xs-3", "任务类型"];divcont[2] = ["col-xs-4", "任务详情"];
        divcont[3] = ["col-xs-2", "任务米币"];divcont[4] = ["col-xs-3", "任务时间"];
    }else if(queryclassfun==4){
        divcont[1] = ["col-xs-3", "公众号Id"];divcont[2] = ["col-xs-4", "公众号名称"];
        divcont[3] = ["col-xs-2", "原始Id"];divcont[4] = ["col-xs-3", "创建时间"];
    }else if(queryclassfun==5){
        divcont[1] = ["col-xs-3", "用户名"];divcont[2] = ["col-xs-4", "被邀请人"];
        divcont[3] = ["col-xs-2", "是否已得奖励"];divcont[4] = ["col-xs-3", "邀请时间"];
    }
    var li=createElementList(div,listlength,divcont,"0");
    var li1=createPageElement();
    document.getElementsByName(name)[0].appendChild(li);
    document.getElementsByName(name)[0].appendChild(li1);
}//历史记录、公众号信息、邀请记录
function userContContent(name,data){
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    var listlength=4;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["referusercont col-xs-12"];
    for(var i=1;i<5;i++){
        divcont[i]=new Array();
    }
    divcont[1][0]="col-xs-3";divcont[2][0]="col-xs-4";divcont[3][0]="col-xs-2";divcont[4][0]="col-xs-3";
    if(data=="0"){
        for(var i=1;i<5;i++) {
            divcont[i][1] = "---";
        }
    }else {
        switch (queryclassfun){
            case 3:
                divcont[1][1]=data.taskName;divcont[2][1]=data.taskDesc;
                divcont[3][1]=data.pointCount;divcont[4][1]=getLocalTime(data.createTime);
                break;
            case 4:
                divcont[1][1]=data.memberId;divcont[2][1]=data.userName;
                divcont[3][1]=data.originId;divcont[4][1]=getLocalTime(data.createTime);
                break;
            case 5:
                divcont[1][1]=data.memberId;divcont[2][1]=data.invitedName;
                if(data.isAccessForInvitor==0){divcont[3] = ["col-xs-2", "否"];
                }else{divcont[3] = ["col-xs-2", "是"];}
                divcont[4][1]=getLocalTime(data.createTime);
        }
    }
    var li=createElementList(div,listlength,divcont,"0");
    if(queryclassfun==3){
        div[1].setAttribute("title",data.taskDesc);
    }
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}
function oficialtitle(name,data){
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var listlength=5;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["officialcont col-xs-12","",data.originId];
    divcont[1] = ["col-xs-12 officialcount", "","0"];
    if(data=="0"){
        divcont[2] = ["col-xs-3", "---"];divcont[3] = ["col-xs-4", "---"];
        divcont[4] = ["col-xs-2", "---"];divcont[5] = ["col-xs-3", "---"];
    }else {
        divcont[2] = ["col-xs-3", data.id];divcont[3] = ["col-xs-4", data.userName];
        divcont[4] = ["col-xs-2", data.originId];divcont[5] = ["col-xs-3", getLocalTime(data.createTime)];
    }
    var li=createElementList(div,listlength,divcont,"1");
    div[0].setAttribute("onClick","javaScirpt:getOfficialDetail(this)");
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}//公众号信息一级主体
function oficialdetailtitle(name){
    var listlength=8;
    var selectlength=2;
    var div=[];
    var option=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["oficialconttitle col-xs-12"];
    divcont[1] = ["col-xs-2", "任务名称"];divcont[2] = ["col-xs-2", "任务情况"];divcont[3] = ["col-xs-1", "单个任务奖励"];
    divcont[4] = ["col-xs-1", "所需数量"];divcont[5] = ["col-xs-2", "发布时间"];
    divcont[6] = ["col-xs-2", "剩余时间"];divcont[7] = ["col-xs-1", "剩余数量"];
    divcont[8] = ["col-xs-1"];
    var selectcont=new Array();  /*[i][0]:innerHTML*/
    selectcont[0]=["关注"];selectcont[1]=["阅读"];
    var li=createElementList(div,listlength,divcont,"0");
    var select=createElementSelect(option,selectlength,selectcont);
    select.className="readOreye";
    div[7].appendChild(select);
    var li1=createPageElement();
    document.getElementsByName(name)[0].appendChild(li);
    document.getElementsByName(name)[0].appendChild(li1);
}//公众号详细信息（二级）
function oficialdetailcont(name,data,flag){
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var listlength=7;
    var div=[];
    var divcont=new Array(); /*[i][0]:className, [i][1]:innerHTML, [i][2]:className*/
    divcont[0] = ["oficialcontbody col-xs-12"];
    if(flag==0){divcont[1] = ["col-xs-2","关注公众号"];}else{divcont[1] = ["col-xs-2","阅读文章"]}
    divcont[2] = ["col-xs-2"];divcont[3] = ["col-xs-1",data.singleScore];
    divcont[4] = ["col-xs-1",data.needNumber];divcont[5] = ["col-xs-2",getLocalTime(data.createTime)];
    divcont[6] = ["col-xs-2",data.remainDays];divcont[7] = ["col-xs-1",data.remainNumber];
    var li=createElementList(div,listlength,divcont,"0");
    var label=document.createElement("label");var input1=document.createElement('input');
    input1.className="switchOn";input1.setAttribute("type","checkbox");input1.setAttribute("data-on-color","primary");
    input1.setAttribute("data-off-color","warning");input1.setAttribute("data-size","small");input1.setAttribute("data-on-text","开启");
    input1.setAttribute("data-off-text","禁用");input1.setAttribute("id",data.taskId);
    div[1].appendChild(label);label.appendChild(input1);
    $("#"+data.taskId+"").bootstrapSwitch('state',true);
    //if(data.isPublishNow==0){
    //    $("#"+data.taskId+"").bootstrapSwitch('state',true);
    //}else{
    //    $("#"+data.taskId+"").bootstrapSwitch('state',false);
    //}
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}

function createElementList(div,length,cont,divbox){
    var li=document.createElement('li');li.className=cont[0][0];
    if(cont[0][2]!=null&&cont[0][2]!=undefined){li.setAttribute("name",cont[0][2]);}
    for(var i=0;i<length;i++){
        div[i]=document.createElement('div');div[i].className=cont[i+1][0];
        if(cont[i+1][1]==null||cont[i+1][1]==undefined){
            div[i].innerHTML="";
        }else {div[i].innerHTML=cont[i+1][1];}
        if(cont[i+1][2]!=null&&cont[i+1][2]!=undefined){div[i].setAttribute("name",cont[i+1][2]);}
        if(divbox==1){
            li.appendChild(div[0]);
            if(i>0){
                div[0].appendChild(div[i]);
            }
        }else {li.appendChild(div[i]);}
    }
    return li;
}//动态生成div封装
function createPageElement(){
    var li1=document.createElement('li');li1.className="PageCodeLi col-xs-12";
    var divP=document.createElement('div');divP.className="PageCode";
    li1.appendChild(divP);
    return li1;
}//动态生成页码标签封装
function createElementSelect(option,length,cont){
    var select=document.createElement('select');
    for(var i=0;i<length;i++){
        option[i]=document.createElement('option');option[i].innerHTML=cont[i];
        select.appendChild(option[i]);
    }
    return select;
}//动态生成select封装
function CreatePage(memberId,flag,pageIndex,pageSize,object,checkTotal){
    var pageCount=Math.ceil(checkTotal/pageSize);
    $(".PageCode").createPage({
        pageCount:pageCount,
        current:pageIndex,
        backFn:function(p){
            if(flag=="00"){
                $(object.parentNode).children(".oficialcontbody").remove();
                var id=$(object).children().eq(0).text();
                officialinforrequest(id,p,pageSize,object,memberId)
            }else{
                $(object.parentNode).children().remove("li");
                GetPageInfor(memberId,flag,p,pageSize,object);
            }
        }
    });
}/*创建页码*/

//json格式封装
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
function UserInforCondi(memberName,nickName,telephone,memberId,registerStartTime,registerEndTime,pageIndex,pageSize){
    this.memberName=memberName;
    this.nickName=nickName;
    this.telephone=telephone;
    this.memberId=memberId;
    this.registerStartTime=registerStartTime;
    this.registerEndTime=registerEndTime;
    this.pageIndex=pageIndex;
    this.pageSize=pageSize;
}
//datepicker日期插件配置
function datepicker(){
    $('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1, todayBtn:  1, autoclose: 1, todayHighlight: 1, startView: 2, forceParse: 0, showMeridian: 1
    });
    $('.form_date').datetimepicker({
        language:  'fr', weekStart: 1, todayBtn:  1, autoclose: 1, todayHighlight: 1, startView: 2, minView: 2, forceParse: 0
    });
    $('.form_time').datetimepicker({
        language:  'fr', weekStart: 1, todayBtn:  1, autoclose: 1, todayHighlight: 1, startView: 1, minView: 0, maxView: 1, forceParse: 0
    });
    $("#fixstarttime").attr("data-date",getNowStartDate());
    $("#fixendtime").attr("data-date",getNowEndDate());
    //$("#startime").attr("value","2016-9-30 8:00");
    //$("#endtime").attr("value","2016-11-01 12:00");
}
//获取当前时间
function getNowStartDate() {
    function add0(y) {
        return y < 10 ? '0' + y : y
    }
    var time = new Date();var y = time.getFullYear();var m = time.getMonth() + 1;var d = time.getDate();var h = time.getHours();var mm = time.getMinutes();var s = time.getSeconds();
    return add0((m-1)>0?y:y-1) + '-' +add0((m-1)>0?m-1:12) + '-' + add0(d)+'T'+ add0(h) + ':' + add0(mm)+':'+add0(s)+'Z';
}
function getNowEndDate() {
    function add0(y) {
        return y < 10 ? '0' + y : y
    }
    var time = new Date();var y = time.getFullYear();var m = time.getMonth() + 1;var d = time.getDate();var h = time.getHours();var mm = time.getMinutes();var s = time.getSeconds();
    return add0(y) + '-' +add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm);
}
//日期转时间戳
function get_unix_time(dateStr){
    return new Date(dateStr.replace(/-/g,'/')).getTime().toString().substr(0,10);
}
//时间戳转日期
function getLocalTime(nS){
    return new Date(+new Date(parseInt(nS) * 1000)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
}
