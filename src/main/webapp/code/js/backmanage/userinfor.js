/**
 * Created by Administrator on 2016/11/22.
 */
$(function(){
    officialinvitetitle();
    officialinvitecont();
    $(".referbtn").click(function(){
        var usename=$("#usename").val(); var useId=$("#useId").val();var usetel=$("#usetel").val(); var registertime=$("#registertime").val();
        var wechatname=$("#wechatname").val(); var queryclass=$("#queryclass").get(0).selectedIndex;
        if(queryclass==0||queryclass==1){
            userinforscoretitle();
            userinforscorecont();
        }else if(queryclass==2||queryclass==3){
            scoretasttitle();
            scoretastcont();
        }else if(queryclass==4||queryclass==5){
            officialinvitetitle();
            officialinvitecont();
            $(".refercont").click(function(){
                var id=$(this).attr("id");
                var oficialcount=parseInt($(this).attr("name"));
                console.log(oficialcount);
                if(oficialcount==0){
                    $("#oficialdetail"+id).empty();
                    //$("#oficialdetail"+id).css("min-height","100px");
                    oficialdetaillisttitle(id);
                    oficialdetaillistcont(id);
                    $(".readOreye").change(function(){
                        var readOreye=$(".readOreye").get(0).selectedIndex;
                    });
                    //$.ajax({
                    //    type:'post',
                    //    url:'',
                    //    data:'',
                    //    success:function(params){
                    //        var json=eval(params);
                    //        if(json.data!=null&&json.errorCode==0){
                    //            oficialdetaillist(id);
                    //                json.data.list.forEach(function(){
                    //                    oficialdetaillist(id);
                    //                })
                    //                }
                    //            }
                    //})
                    oficialcount++;
                    $(this).attr("name",oficialcount);
                }else {
                    $("#oficialdetail"+id).empty();
                    oficialcount--;
                    $(this).attr("name",oficialcount);
                    console.log(oficialcount+"fff");
                }

            });
        }
        //$.ajax({
        //    type:'post',
        //    url:'',
        //    data:'',
        //    success:function(params){
        //        var json=eval(params);
        //        if(json.data!=null&&json.errorCode==0){
        //            if(queryclass==0||queryclass==1){
        //                userinforquerytitle();
        //                json.data.list.forEach(function(){
        //                    userinforquerycont();
        //                })
        //            }else if(queryclass==2||queryclass==3){
        //                userinforquerytitle();
        //                json.data.list.forEach(function(){
        //                    userinforquerycont();
        //                })
        //            }else if(queryclass==4||queryclass==5){
        //                userinforquerytitle();
        //                json.data.list.forEach(function(){
        //                    userinforquerycont();
        //                });
        //                if(queryclass==4){
        //                    $(".refercont").click(function(){
        //                        alert("aaa")
        //                            oficialdetaillist();
        //                    })
        //                }
        //            }
        //        }
        //    }
        //})
    })
});

function userinforscoretitle(){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    console.log(queryclassfun);
    $(".referlist").empty();
    var li=document.createElement('li');li.className="refertitle";
    var div0=document.createElement('div'); div0.className="col-xs-1"; if(queryclassfun==0){div0.innerHTML="用户Id";}else if(queryclassfun==1){div0.innerHTML="用户Id";}
    var div1=document.createElement('div'); div1.className="col-xs-2"; if(queryclassfun==0){div1.innerHTML="用户名";}else if(queryclassfun==1){div1.innerHTML="用户名";}
    var div2=document.createElement('div'); div2.className="col-xs-2"; if(queryclassfun==0){div2.innerHTML="电话";}else if(queryclassfun==1){div2.innerHTML="电话";}
    var div3=document.createElement('div'); div3.className="col-xs-1"; if(queryclassfun==0){div3.innerHTML="性别";}else if(queryclassfun==1){div3.innerHTML="米币总数";}
    var div4=document.createElement('div'); div4.className="col-xs-1"; if(queryclassfun==0){div4.innerHTML="地址";}else if(queryclassfun==1){div4.innerHTML="可用米币数";}
    var div5=document.createElement('div'); div5.className="col-xs-1"; if(queryclassfun==0){div5.innerHTML="邀请码";}else if(queryclassfun==1){div5.innerHTML="当前充值米币数";}
    var div6=document.createElement('div'); div6.className="col-xs-2"; if(queryclassfun==0){div6.innerHTML="注册时间";}else if(queryclassfun==1){div6.innerHTML="充值米币总数";}
    var div7=document.createElement('div');div7.className="col-xs-1"; if(queryclassfun==0){div7.innerHTML="来源";}
    else if(queryclassfun==1){div0.setAttribute("title","即将可用米币数");div7.innerHTML="即将可用米币数";}
    var div8=document.createElement('div');div8.className="col-xs-1"; if(queryclassfun==0){div8.innerHTML="是否禁用";}else if(queryclassfun==1){div8.innerHTML="即将可用米币数（邀请）";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);
    li.appendChild(div6);li.appendChild(div7);li.appendChild(div8);
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function userinforscorecont(){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    $(".refercont").empty();
    var li=document.createElement('li');li.className="refercont";
    var div0=document.createElement('div'); div0.className="col-xs-1"; if(queryclassfun==0){div0.innerHTML="用户Id";}else if(queryclassfun==1){div0.innerHTML="用户Id";}
    var div1=document.createElement('div'); div1.className="col-xs-2"; if(queryclassfun==0){div1.innerHTML="用户名";}else if(queryclassfun==1){div1.innerHTML="用户名";}
    var div2=document.createElement('div'); div2.className="col-xs-2"; if(queryclassfun==0){div2.innerHTML="15689931603";}else if(queryclassfun==1){div2.innerHTML="电话";}
    var div3=document.createElement('div'); div3.className="col-xs-1"; if(queryclassfun==0){div3.innerHTML="15689931603";}else if(queryclassfun==1){div3.innerHTML="米币总数";}
    var div4=document.createElement('div'); div4.className="col-xs-1"; if(queryclassfun==0){div4.innerHTML="地址";}else if(queryclassfun==1){div4.innerHTML="可用米币数";}
    var div5=document.createElement('div'); div5.className="col-xs-1"; if(queryclassfun==0){div5.innerHTML="邀请码";}else if(queryclassfun==1){div5.innerHTML="当前充值米币数";}
    var div6=document.createElement('div'); div6.className="col-xs-2"; if(queryclassfun==0){div6.innerHTML="注册时间";}else if(queryclassfun==1){div6.innerHTML="充值米币总数";}
    var div7=document.createElement('div');div7.className="col-xs-1"; if(queryclassfun==0){div7.innerHTML="来源";}else if(queryclassfun==1){div7.innerHTML="即将可用米币数（关注）";}
    var div8=document.createElement('div');div8.className="col-xs-1"; if(queryclassfun==0){div8.innerHTML="是否禁用";}else if(queryclassfun==1){div8.innerHTML="即将可用米币数（邀请）";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);li.appendChild(div5);
    li.appendChild(div6);li.appendChild(div7);li.appendChild(div8);
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function scoretasttitle(){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    $(".referlist").empty();
    var li=document.createElement('li');li.className="refertitle";
    var div0=document.createElement('div'); div0.className="col-xs-2"; if(queryclassfun==2){div0.innerHTML="用户Id";}else if(queryclassfun==3){div0.innerHTML="用户Id";}
    var div1=document.createElement('div'); div1.className="col-xs-2"; if(queryclassfun==2){div1.innerHTML="流动类型";}else if(queryclassfun==3){div1.innerHTML="任务类型";}
    var div2=document.createElement('div'); div2.className="col-xs-3"; if(queryclassfun==2){div2.innerHTML="流动前";}else if(queryclassfun==3){div2.innerHTML="任务详情";}
    var div3=document.createElement('div'); div3.className="col-xs-2"; if(queryclassfun==2){div3.innerHTML="流动米币";}else if(queryclassfun==3){div3.innerHTML="任务米币";}
    var div4=document.createElement('div'); div4.className="col-xs-3"; if(queryclassfun==2){div4.innerHTML="流动后";}else if(queryclassfun==3){div4.innerHTML="任务时间";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function scoretastcont(){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    $(".refercont").empty();
    var li=document.createElement('li');li.className="refercont";
    var div0=document.createElement('div'); div0.className="col-xs-2"; if(queryclassfun==2){div0.innerHTML="用户Id";}else if(queryclassfun==3){div0.innerHTML="用户Id";}
    var div1=document.createElement('div'); div1.className="col-xs-2"; if(queryclassfun==2){div1.innerHTML="流动类型";}else if(queryclassfun==3){div1.innerHTML="任务类型";}
    var div2=document.createElement('div'); div2.className="col-xs-3"; if(queryclassfun==2){div2.innerHTML="流动前";}else if(queryclassfun==3){div2.innerHTML="任务详情";}
    var div3=document.createElement('div'); div3.className="col-xs-2"; if(queryclassfun==2){div3.innerHTML="流动米币";}else if(queryclassfun==3){div3.innerHTML="任务米币";}
    var div4=document.createElement('div'); div4.className="col-xs-3"; if(queryclassfun==2){div4.innerHTML="流动后";}else if(queryclassfun==3){div4.innerHTML="任务时间";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function officialinvitetitle(){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    $(".referlist").empty();
    var li=document.createElement('li');li.className="refertitle";
    var div0=document.createElement('div'); div0.className="col-xs-3"; if(queryclassfun==4){div0.innerHTML="用户名";}else if(queryclassfun==5){div0.innerHTML="邀请人";}
    var div1=document.createElement('div'); div1.className="col-xs-3"; if(queryclassfun==4){div1.innerHTML="公众号名称";}else if(queryclassfun==5){div1.innerHTML="被邀请人";}
    var div2=document.createElement('div'); div2.className="col-xs-3"; if(queryclassfun==4){div2.innerHTML="原始Id";}else if(queryclassfun==5){div2.innerHTML="邀请时间";}
    var div3=document.createElement('div'); div3.className="col-xs-3"; if(queryclassfun==4){div3.innerHTML="创建时间";}else if(queryclassfun==5){div3.innerHTML="是否已得奖励";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);
    document.getElementsByTagName("ul")[1].appendChild(li);
}
function officialinvitecont(){
    var queryclassfun=$("#queryclass").get(0).selectedIndex;
    $(".refercont").empty();
    var li=document.createElement('li');li.className="refercont";li.setAttribute("id","1");li.setAttribute("name","0");
    var li1=document.createElement('li');li1.className="oficialdetail";li1.setAttribute("id","oficialdetail"+"1");
    var div0=document.createElement('div'); div0.className="col-xs-3"; if(queryclassfun==4){div0.innerHTML="小花";}else if(queryclassfun==5){div0.innerHTML="邀请人";}
    var div1=document.createElement('div'); div1.className="col-xs-3"; if(queryclassfun==4){div1.innerHTML="七月花语";}else if(queryclassfun==5){div1.innerHTML="被邀请人";}
    var div2=document.createElement('div'); div2.className="col-xs-3"; if(queryclassfun==4){div2.innerHTML="dongofjojgolfsd";}else if(queryclassfun==5){div2.innerHTML="邀请时间";}
    var div3=document.createElement('div'); div3.className="col-xs-3"; if(queryclassfun==4){div3.innerHTML="2016-11-23 13:53";}else if(queryclassfun==5){div3.innerHTML="是否已得奖励";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);
    document.getElementsByTagName("ul")[1].appendChild(li);
    document.getElementsByTagName("ul")[1].appendChild(li1);
    var li=document.createElement('li');li.className="refercont";li.setAttribute("id","2");li.setAttribute("name","0");
    var li1=document.createElement('li');li1.className="oficialdetail";li1.setAttribute("id","oficialdetail"+"2");
    var div0=document.createElement('div'); div0.className="col-xs-3"; if(queryclassfun==4){div0.innerHTML="小花";}else if(queryclassfun==5){div0.innerHTML="邀请人";}
    var div1=document.createElement('div'); div1.className="col-xs-3"; if(queryclassfun==4){div1.innerHTML="七月花语";}else if(queryclassfun==5){div1.innerHTML="被邀请人";}
    var div2=document.createElement('div'); div2.className="col-xs-3"; if(queryclassfun==4){div2.innerHTML="dongofjojgolfsd";}else if(queryclassfun==5){div2.innerHTML="邀请时间";}
    var div3=document.createElement('div'); div3.className="col-xs-3"; if(queryclassfun==4){div3.innerHTML="2016-11-23 13:53";}else if(queryclassfun==5){div3.innerHTML="是否已得奖励";}
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);
    document.getElementsByTagName("ul")[1].appendChild(li);
    document.getElementsByTagName("ul")[1].appendChild(li1);
}
function oficialdetaillisttitle(id){
    var li=document.createElement('li');li.className="oficialconttitle";
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
    document.getElementById("oficialdetail"+id).appendChild(li);
}
function oficialdetaillistcont(id){
    var li=document.createElement('li');li.className="oficialcontbody";
    var div0=document.createElement('div'); div0.className="col-xs-2"; div0.innerHTML="关注公众号";
    var div1=document.createElement('div'); div1.className="col-xs-2";div1.innerHTML="已上线";
    var div2=document.createElement('div'); div2.className="col-xs-1";div2.innerHTML="40";
    var div3=document.createElement('div'); div3.className="col-xs-1";div3.innerHTML="500";
    var div4=document.createElement('div'); div4.className="col-xs-2"; div4.innerHTML="2016-11-23 17:09";
    var div5=document.createElement('div'); div5.className="col-xs-2";div5.innerHTML="24:56";
    var div6=document.createElement('div'); div6.className="col-xs-2";div6.innerHTML="50";
    li.appendChild(div0);li.appendChild(div1);li.appendChild(div2);li.appendChild(div3);li.appendChild(div4);
    li.appendChild(div5);li.appendChild(div6);
    document.getElementById("oficialdetail"+id).appendChild(li);
}