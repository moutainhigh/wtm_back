/**
 * Created by Administrator on 2016/11/22.
 */
$(function(){
    //获取当前时间
    function getNowStartDate() {
        function add0(y) {
            return y < 10 ? '0' + y : y
        }
        var time = new Date();var y = time.getFullYear();var m = time.getMonth() + 1;var d = time.getDate();var h = time.getHours();var mm = time.getMinutes();var s = time.getSeconds();
        return add0((m-1)>0?y:y-1) + '-' +add0((m-1)>0?m-1:12) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm);
    }
    function getNowEndDate() {
        function add0(y) {
            return y < 10 ? '0' + y : y
        }
        var time = new Date();var y = time.getFullYear();var m = time.getMonth() + 1;var d = time.getDate();var h = time.getHours();var mm = time.getMinutes();var s = time.getSeconds();
        return add0(y) + '-' +add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm);
    }
    $("#startime").attr("value","2016-9-01 10:00");
    $("#endtime").attr("value","2016-11-01 10:00");
    var startdateStr=$("#startime").val();
    var enddateStr=$("#endtime").val();
    //页面初始化用户信息
    var pageIndex=1;
    var pageSize=30;
    var memberName=$("#usename").val(); var memberId=$("#useId").val();var telephone=$("#usetel").val();
    var registerStartTime=get_unix_startime(startdateStr);var registerEndTime=get_unix_endtime(enddateStr);var nickName=$("#wechatname").val();
    if(memberName==""){memberName=null}if(memberId==""){memberId=null}if(telephone==""){telephone=null}if(nickName==""){nickName=null}
    var obj=new UserInforCondi(memberName,nickName,telephone,memberId,registerStartTime,registerEndTime,pageIndex,pageSize);
    var requestObj = JSON.stringify(obj);
    userinforscoretitle();
    $(".refercont").remove();
    getMemberInformation(requestObj);
    //按页码查询用户信息
    var checkTotal=$(".tcdPageCode").attr("name");
    var pageCount=Math.ceil(checkTotal/pageSize);
    $(".tcdPageCode").createPage({
        pageCount:pageCount,
        current:1,
        backFn:function(p){
            console.log("当前页是第"+p+"页");
            obj=new UserInforCondi(memberName,nickName,telephone,memberId,registerStartTime,registerEndTime,p,pageSize);
            requestObj = JSON.stringify(obj);
            $(".refercont").remove();
            getMemberInformation(requestObj);
        }
    });
    //按搜索条件查询用户信息
    $(".referbtn").click(function(){
        $(".refercont").remove();
        var memberName=$("#usename").val(); var memberId=$("#useId").val();var telephone=$("#usetel").val();
        var registerStartTime=$("#startime").val();var registerEndTime=$("#endtime").val();
        registerStartTime=get_unix_startime(registerStartTime);registerEndTime=get_unix_endtime(registerEndTime);
        var nickName=$("#wechatname").val();
        console.log(nickName);
        if(memberName==""){memberName=null}if(memberId==""){memberId=null}if(telephone==""){telephone=null}if(nickName==""){nickName=null}
        var obj=new UserInforCondi(memberName,nickName,telephone,memberId,registerStartTime,registerEndTime,"1","10");
        var requestObj = JSON.stringify(obj);
        getMemberInformation(requestObj);
        });

    $("#queryclass").change(function(){
        console.log($("*").is(".userScoreTitle"));
        $(".refercontbtn").attr("name","0");
        $(".refercontbtn").click(function(){
            $(".refercont").children().remove("li");
        })
    })
});

function GetConfirmUserInfor(obj){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    if(queryclassfun==4){
        var readOfficial=$(".readOreye").get(0).selectedIndex;
        UserInforDetailCondi(memberId,readOfficial,1,10);
        $.ajax({
            type:'post',
            dataType:'json',
            contentType:'application/json',
            url:'/backward/getMemberInformationDetail',
            data:requestObj,
            success:function(params){
                var json=eval(params);
                if(json.data!=null&&json.errorCode==0){
                    json.data.list.forEach(function(){
                        oficialdetaillisttitle(id);
                        oficialdetaillistcont(id);
                    });
                }
                else if(json.errorCode==4){
                    alert(json.message);
                    $("#queryclass").get(0).options[0].selected=true;
                }
            },error:function(data){
                alert("加载页面失败，请重新加载");
                $("#queryclass").get(0).options[0].selected=true;
            }
        });
    }
}
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
}
function getMemberInformationDetail(requestObj){
    $.ajax({
        type:'post',
        dataType:'json',
        contentType:'application/json',
        url:'/backward/getMemberInformationDetail',
        data:requestObj,
        success:function(params){
            var json=eval(params);
            if(json.data!=null&&json.errorCode==0){
                userinforscorecont();
            }
            else if(json.errorCode==4){
                alert(json.message)
            }
        },error:function(data){
            alert("加载页面失败，请重新加载2")
        }
    });
}
function getLocalTime(nS){
    return new Date(+new Date(parseInt(nS) * 1000)+8*3600*1000).toISOString().replace(/T/g,' ').replace(/\.[\d]{3}Z/,'');
}
function userinforscoretitle(){
    $(".referlist").empty();
    var li=document.createElement('li');li.className="refertitle";
    var div0=document.createElement('div'); div0.className="col-xs-1"; div0.innerHTML="用户Id";
    var div1=document.createElement('div'); div1.className="col-xs-1"; div1.innerHTML="用户名";
    var div2=document.createElement('div'); div2.className="col-xs-1"; div2.innerHTML="微信昵称";
    var div3=document.createElement('div'); div3.className="col-xs-1"; div3.innerHTML="电话";
    var div4=document.createElement('div'); div4.className="col-xs-1"; div4.innerHTML="性别";
    var div5=document.createElement('div'); div5.className="col-xs-1"; div5.innerHTML="地址";
    var div6=document.createElement('div'); div6.className="col-xs-1"; div6.innerHTML="邀请码";
    var div7=document.createElement('div'); div7.className="col-xs-2"; div7.innerHTML="注册时间";
    var div8=document.createElement('div');div8.className="col-xs-1"; div8.innerHTML="来源";
    var div9=document.createElement('div');div9.className="col-xs-1"; div9.innerHTML="是否禁用";
    var div10=document.createElement('div');div10.className="col-xs-1";
    var select=document.createElement('select');var option1=document.createElement('option');option1.innerHTML="查询内容";
    var option2=document.createElement('option');option2.innerHTML="米币总数";
    var option3=document.createElement('option');option3.innerHTML="米币流水";var option4=document.createElement('option');
    option4.innerHTML="历史任务记录";var option5=document.createElement('option');option5.innerHTML="公众号信息";
    var option6=document.createElement('option');option6.innerHTML="邀请记录";select.setAttribute("id","queryclass");
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);
    li.appendChild(div6);li.appendChild(div7);li.appendChild(div8);li.appendChild(div9);select.appendChild(option1);select.appendChild(option2);
    select.appendChild(option3);select.appendChild(option4);select.appendChild(option5);select.appendChild(option6);div10.appendChild(select);li.appendChild(div10);
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function userinforscorecont(data){
    var li=document.createElement('li');li.className="refercont";li.setAttribute("name",data.memberId);
    var div0=document.createElement('div'); div0.className="col-xs-1";div0.innerHTML=data.memberId;
    var div1=document.createElement('div'); div1.className="col-xs-1";div1.innerHTML=data.memberName;
    var div2=document.createElement('div'); div2.className="col-xs-1";
    if(data.nickName==null||data.nickName==""){div2.innerHTML="---"}else{div2.innerHTML=data.nickName;}
    var div3=document.createElement('div'); div3.className="col-xs-1";div3.innerHTML=data.telephone;
    var div4=document.createElement('div'); div4.className="col-xs-1";
    if(data.sex==0){div4.innerHTML="保密";}if(data.sex==1){div4.innerHTML="男";}if(data.sex==2){div4.innerHTML="女";}
    var div5=document.createElement('div'); div5.className="col-xs-1";
    if(data.address==null||data.address==""){div5.innerHTML="---"}else{div5.innerHTML=data.address;}
    var div6=document.createElement('div'); div6.className="col-xs-1";div6.innerHTML=data.inviteCode;
    var div7=document.createElement('div'); div7.className="col-xs-2";
    if(data.createTime==null){div7.innerHTML="---"}else{div7.innerHTML=getLocalTime(data.createTime);}
    var div8=document.createElement('div');div8.className="col-xs-1";div8.innerHTML=data.source;
    var div9=document.createElement('div');div9.className="col-xs-1";
    var label=document.createElement("label");var input1=document.createElement('input');
    input1.className="switchOn";input1.setAttribute("type","checkbox");input1.setAttribute("data-on-color","primary");
    input1.setAttribute("data-off-color","warning");input1.setAttribute("data-size","small");input1.setAttribute("data-on-text","开启");
    input1.setAttribute("data-off-text","禁用");input1.setAttribute("id",data.memberId);
    var div10=document.createElement('div');div10.className="col-xs-1";
    var input2=document.createElement('input');div10.className="col-xs-1";div10.className="refercontbtn";div10.setAttribute("name","0");
    input2.setAttribute("type","button");input2.setAttribute("value","查询");div10.setAttribute("onClick","javaScript:confirmUserInforSearch(this)");
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);
    li.appendChild(div6);li.appendChild(div7);li.appendChild(div8);li.appendChild(div9); div9.appendChild(label);label.appendChild(input1);
    li.appendChild(div10);div10.appendChild(input2);
    document.getElementsByTagName("ul")[1].appendChild(li);
    if(data.isForbidden==0){
        $("#"+data.memberId+"").bootstrapSwitch('state', true);
    }else{
        $("#"+data.memberId+"").bootstrapSwitch('state', false);
    }
}

function userScoreTitle(name){
    var li=document.createElement('li');li.className="referusertitle";
    var div0=document.createElement('div'); div0.className="col-xs-1";div0.innerHTML="米币总数";
    var div1=document.createElement('div'); div1.className="col-xs-1";div1.innerHTML="可用米币数";
    var div2=document.createElement('div'); div2.className="col-xs-2";div2.innerHTML="当前充值米币数";
    var div3=document.createElement('div'); div3.className="col-xs-2";div3.innerHTML="充值米币总数";
    var div4=document.createElement('div');div4.className="col-xs-3";div4.innerHTML="即将可用米币数（关注）";
    var div5=document.createElement('div');div5.className="col-xs-3";div5.innerHTML="即将可用米币数（邀请）";
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);
    li.appendChild(div5);
    document.getElementsByName(name)[0].appendChild(li);
}
function userScoreCont(name,data){
    var li=document.createElement('li');li.className="referusercont";
    var div0=document.createElement('div'); div0.className="col-xs-1";
    if(data==null){div0.innerHTML="0";}else{div0.innerHTML=data.totalScore;}
    var div1=document.createElement('div'); div1.className="col-xs-1";
    if(data==null){div1.innerHTML="0";}else{div1.innerHTML=data.avaliableScore;}
    var div2=document.createElement('div'); div2.className="col-xs-2";
    if(data==null){div2.innerHTML="0";}else{div2.innerHTML=data.currentChargeScore;}
    var div3=document.createElement('div'); div3.className="col-xs-2";
    if(data==null){div3.innerHTML="0";}else{div3.innerHTML=data.totalChargeScore;}
    var div4=document.createElement('div');div4.className="col-xs-3";
    if(data==null){div4.innerHTML="0"}else {if(data.futureAvaliableFollowScore==null||data.futureAvaliableFollowScore==""){div4.innerHTML="0"}
    else {div4.innerHTML=data.futureAvaliableFollowScore;};}
    var div5=document.createElement('div');div5.className="col-xs-3";
    if(data==null){div5.innerHTML="0";}else {div5.innerHTML=data.futureAvaliableInvitedScore;}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);
    document.getElementsByName(name)[0].appendChild(li);
}

function userFlowTitle(name){
    var li1=document.createElement('li');li1.className="PageCodeLi";var div11=document.createElement('div');
    div11.className="PageCode";
    li1.appendChild(div11);
    var li=document.createElement('li');li.className="referusertitle";
    var div0=document.createElement('div'); div0.className="col-xs-2";div0.innerHTML="流动类型";
    var div1=document.createElement('div'); div1.className="col-xs-2";div1.innerHTML="流动详情";
    var div2=document.createElement('div'); div2.className="col-xs-2";div2.innerHTML="创建时间";
    var div3=document.createElement('div'); div3.className="col-xs-2";div3.innerHTML="流动前";
    var div4=document.createElement('div'); div4.className="col-xs-2";div4.innerHTML="流动后";
    var div5=document.createElement('div'); div5.className="col-xs-1";div5.innerHTML="流动米币";
    var div6=document.createElement('div'); div6.className="col-xs-1";div6.innerHTML="流动状态";
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);li.appendChild(div6);
    document.getElementsByName(name)[0].appendChild(li);
    document.getElementsByName(name)[0].appendChild(li1);
}
function userFlowContent(name,data){
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var li=document.createElement('li');li.className="referusercont";
    var div0=document.createElement('div'); div0.className="col-xs-2";{if(data=="0"){div0.innerHTML="---"}else{div0.innerHTML=data.typeName};}
    var div1=document.createElement('div'); div1.className="col-xs-2"; if(data=="0"){div1.innerHTML="---"}else{div1.innerHTML=data.typeDesc;}
    var div2=document.createElement('div'); div2.className="col-xs-2"; if(data=="0"){div2.innerHTML="---"}else{div2.innerHTML=getLocalTime(data.createTime);}
    var div3=document.createElement('div'); div3.className="col-xs-2"; if(data=="0"){div3.innerHTML="---"}else{div3.innerHTML=data.memberScoreBefore;}
    var div4=document.createElement('div'); div4.className="col-xs-2"; if(data=="0"){div4.innerHTML="---"}else{div4.innerHTML=data.memberScoreAfter;}
    var div5=document.createElement('div'); div5.className="col-xs-1"; if(data=="0"){div5.innerHTML="---"}else{div5.innerHTML=data.flowScore;}
    var div6=document.createElement('div'); div6.className="col-xs-1"; if(data=="0"){div6.innerHTML="---"}else
    {if(data.isFinished==0){div6.innerHTML="未完成";}else{div6.innerHTML="已完成";}}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);li.appendChild(div6);
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}

function userContTitle(name){
    var li1=document.createElement('li');li1.className="PageCodeLi";var div11=document.createElement('div');
    div11.className="PageCode";
    li1.appendChild(div11);
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    var li=document.createElement('li');li.className="referusertitle";
    var div0=document.createElement('div'); div0.className="col-xs-3";
    if(queryclassfun==3){div0.innerHTML="任务类型";}else if(queryclassfun==4){div0.innerHTML="用户名";}
    else if(queryclassfun==5){div0.innerHTML="用户名";}
    var div1=document.createElement('div'); div1.className="col-xs-4";
    if(queryclassfun==3){div1.innerHTML="任务详情";}else if(queryclassfun==4){div1.innerHTML="公众号名称";}
    else if(queryclassfun==5){div1.innerHTML="被邀请人";}
    var div2=document.createElement('div'); div2.className="col-xs-2";
    if(queryclassfun==3){div2.innerHTML="任务米币";}else if(queryclassfun==4){div2.innerHTML="原始Id";}
    else if(queryclassfun==5){div2.innerHTML="是否已得奖励";}
    var div3=document.createElement('div'); div3.className="col-xs-3";
    if(queryclassfun==3){div3.innerHTML="任务时间";}else if(queryclassfun==4){div3.innerHTML="创建时间";}
    else if(queryclassfun==5){div3.innerHTML="邀请时间";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);
    document.getElementsByName(name)[0].appendChild(li);
    document.getElementsByName(name)[0].appendChild(li1);
}
function userContContent(name,data){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var li=document.createElement('li');li.className="referusercont";
    var div0=document.createElement('div'); div0.className="col-xs-3";
    if(queryclassfun==3){if(data=="0"){div0.innerHTML="---";}else{div0.innerHTML=data.taskName;}}
    else if(queryclassfun==5){if(data=="0"){div0.innerHTML="---";}else{div0.innerHTML=data.memberId;}}
    var div1=document.createElement('div'); div1.className="col-xs-4";
    if(queryclassfun==3){if(data=="0"){div1.innerHTML="---";}else{div1.innerHTML=data.taskDesc;}}
    else if(queryclassfun==5){if(data=="0"){div1.innerHTML="---";}else{
        if(data.invitedName==null){div1.innerHTML="---"}else{div1.innerHTML=data.invitedName;}}}
    var div2=document.createElement('div'); div2.className="col-xs-2";
    if(queryclassfun==3){if(data=="0"){div2.innerHTML="---";}else{div2.innerHTML=data.pointCount;}}
    else if(queryclassfun==5){if(data=="0"){div2.innerHTML="---";}else{
        if(data.isAccessForInvitor==0){div2.innerHTML="否";}else{div2.innerHTML="是";}}}
    var div3=document.createElement('div'); div3.className="col-xs-3";
    if(queryclassfun==3){if(data=="0"){div3.innerHTML="---";}else{div3.innerHTML=getLocalTime(data.createTime);}}
    else if(queryclassfun==5){if(data=="0"){div3.innerHTML="---";}else{div3.innerHTML=getLocalTime(data.createTime);}}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}
function oficialtitle(name,data){
    var li1=document.createElement('li');li1.className="PageCodeList";var div11=document.createElement('div');
    div11.className="PageCode";li1.appendChild(div11);
    var PageCodeLi=document.getElementsByClassName("PageCodeLi");
    var li=document.createElement('li');li.className="officialcont";li.setAttribute("name",data.originId);
    var div=document.createElement('div'); div.className="col-xs-12 officialcount";div.setAttribute("name","0");
    div.setAttribute("onClick","javaScirpt:getOfficialDetail(this)");
    var div0=document.createElement('div'); div0.className="col-xs-3";
    if(data=="0"){div0.innerHTML="---";}else{div0.innerHTML=data.memberId;}
    var div1=document.createElement('div'); div1.className="col-xs-4";
    if(data=="0"){div1.innerHTML="---";}else{div1.innerHTML=data.userName;}
    var div2=document.createElement('div'); div2.className="col-xs-2";
    if(data=="0"){div2.innerHTML="---";}else{div2.innerHTML=data.originId;}
    var div3=document.createElement('div'); div3.className="col-xs-3";
    if(data=="0"){div3.innerHTML="---";}else{div3.innerHTML=getLocalTime(data.createTime);}/*div3.innerHTML="创建时间";*/
    li.appendChild(div);div.appendChild(div0);div.appendChild(div1);div.appendChild(div2);div.appendChild(div3);
    document.getElementsByName(name)[0].appendChild(li1);
    document.getElementsByName(name)[0].insertBefore(li,PageCodeLi[0]);
}
function oficialdetailtitle(name){
    var li=document.createElement('li');li.className="col-xs-12 oficialconttitle";
    var div0=document.createElement('div'); div0.className="col-xs-2"; div0.innerHTML="任务名称";
    var div1=document.createElement('div'); div1.className="col-xs-2";div1.innerHTML="任务情况";
    var div2=document.createElement('div'); div2.className="col-xs-1";div2.innerHTML="单个任务奖励";
    var div3=document.createElement('div'); div3.className="col-xs-1";div3.innerHTML="所需数量";
    var div4=document.createElement('div'); div4.className="col-xs-2"; div4.innerHTML="发布时间";
    var div5=document.createElement('div'); div5.className="col-xs-2";div5.innerHTML="剩余时间";
    var div6=document.createElement('div'); div6.className="col-xs-1";div6.innerHTML="剩余数量";
    var div7=document.createElement('div'); div7.className="col-xs-1";
    var select=document.createElement('select');select.className="readOreye";var option1=document.createElement('option');var option2=document.createElement('option');
    option1.innerHTML="关注";option2.innerHTML="阅读";select.appendChild(option1);select.appendChild(option2);div7.appendChild(select);
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);
    li.appendChild(div5);li.appendChild(div6);li.appendChild(div7);
    document.getElementsByName(name)[0].appendChild(li);
}
function oficialdetailcont(name){
    var li=document.createElement('li');li.className="col-xs-12 oficialcontbody";
    var div0=document.createElement('div'); div0.className="col-xs-2"; div0.innerHTML="关注公众号";
    var div1=document.createElement('div'); div1.className="col-xs-2";
    var label=document.createElement("label");var input1=document.createElement('input');
    input1.className="switchOn";input1.setAttribute("type","checkbox");input1.setAttribute("data-on-color","primary");
    input1.setAttribute("data-off-color","warning");input1.setAttribute("data-size","small");input1.setAttribute("data-on-text","开启");
    input1.setAttribute("data-off-text","禁用");input1.setAttribute("id","task");
    var div2=document.createElement('div'); div2.className="col-xs-1";div2.innerHTML="40";
    var div3=document.createElement('div'); div3.className="col-xs-1";div3.innerHTML="500";
    var div4=document.createElement('div'); div4.className="col-xs-2"; div4.innerHTML="2016-11-23 17:09";
    var div5=document.createElement('div'); div5.className="col-xs-2";div5.innerHTML="24:56";
    var div6=document.createElement('div'); div6.className="col-xs-2";div6.innerHTML="50";
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);
    li.appendChild(div5);li.appendChild(div6);div1.appendChild(label);label.appendChild(input1);
    document.getElementsByName(name)[0].appendChild(li);
}
//查询确定用户详细信息
function confirmUserInforSearch(object){
    var clickCount=object.getAttribute("name");
    if(clickCount==0){
        var flag = $("#queryclass").get(0).selectedIndex;
        console.log(flag);
        var memberId=object.parentNode.childNodes[0].innerHTML;
        if (flag != 0) {
            if($(object.parentNode).siblings().children().is("li")){
                $(object.parentNode).siblings().children().remove("li");
                console.log($(object.parentNode).siblings().children(".refercontbtn").attr("name","0"));
            };
            var pageIndex=1;
            var pageSize=10;
            GetPageInfor(memberId,flag,pageIndex,pageSize,object);
            var checkTotal=$(".PageCode").attr("name");
            console.log(checkTotal+"total");
        }
}else {
    object.setAttribute("name","0");
        $(object.parentNode).children().remove("li")
}
}
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
}
function getOfficialDetail(obj){
    var originId=$(obj).children().eq(2).text();
    var clickCount=obj.getAttribute("name");
    $(obj.parentNode).siblings().children().remove("li");
    $(obj.parentNode).siblings().children(".officialcount").attr("name","0");
    if(clickCount==0){
        //$.ajax({
        //    type: 'post',
        //    url: '/backward/getMemberInformation',
        //    data:{memberId:memberId,flag:flag,pageIndex:pageIndex,pageSize:pageSize},
        //    success: function (params) {
        //
        //    }
        //})
        oficialdetailtitle(originId);
        var readOreye=$(".readOreye").get(0).selectedIndex;
        oficialdetailcont(originId);
        obj.setAttribute("name","1");
    }else{
        obj.setAttribute("name","0");
        $(obj.parentNode).children().remove("li");
    }
}

function CreatePage(memberId,flag,pageIndex,pageSize,object,checkTotal){
    var pageCount=Math.ceil(checkTotal/pageSize);
    $(".PageCode").createPage({
        pageCount:pageCount,
        current:pageIndex,
        backFn:function(p){
            $(object.parentNode).children().remove("li");
            GetPageInfor(memberId,flag,p,pageSize,object);
        }
    });
}
//日期插件配置
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
function UserInforDetailCondi(memberId,flag,pageIndex,pageSize){
    this.memberId=memberId;
    this.flag=flag;
    this.pageIndex=pageIndex;
    this.pageSize=pageSize;
}
//日期格式转时间戳
function get_unix_startime(startdateStr)
{
    var newstr = startdateStr.replace(/-/g,'/');
    var date =  new Date(newstr);
    var time_str = date.getTime().toString();
    return time_str.substr(0, 10);
}
function get_unix_endtime(enddateStr)
{
    var newstr = enddateStr.replace(/-/g,'/');
    var date =  new Date(newstr);
    var time_str = date.getTime().toString();
    return time_str.substr(0, 10);
}
