/**
 * Created by Administrator on 2016/11/28.
 */
$(function(){
    var wheight=$(document).height();
    $(".bnbody").css("height",wheight);
    console.log(wheight);
    var loginheight=$(".loginbg").height();
    console.log(loginheight);
    $(".bnbody").css("padding-top",(wheight-loginheight)/2);
    var wwidth=$(window).width();
    console.log("宽度是"+wwidth);
    var loginwidth=$(".loginbg").width();
    console.log("宽度是"+loginwidth);
    if(wwidth<1080){
        $(".loginbg").css("margin-left",(wwidth-loginwidth)/2.6);
    }else {
        $(".loginbg").css("margin-left",(wwidth-loginwidth)/2);
    }

    $(".loginbutton").click(function (){
        var memberName = $("#memberName").val();
        var password = $("#password").val();
        console.log(memberName + password);
        $.ajax({
            type: 'post',
            url: '/backDeal/login',
            timeout: 180000,
            data: {realName: memberName, password: password},
            success: function (params){
                var param = eval(params);
                if (param != null && param.errorCode == 0) {
                    var data = param.data;
                    if (data.id != null){
                        $.cookie("accountId", data.id, {expires: 30, path: "/frontPage/backward"})
                        $.cookie("realName", encodeURI(data.memberName), {expires: 30, path: "/frontPage/backward"})
                        $.cookie("password", data.password, {expires: 30, path: "/frontPage/backward"})
                        location.href = "/frontPage/backward/withdraws.html";
                    }
                } else if (params.errorCode == 4) {
                    alert(params.message);
                }
            },error:function(data){
                alert("加载页面失败，请重新加载");
            }
        })
    })
})