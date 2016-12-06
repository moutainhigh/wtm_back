/**
 * Created by Administrator on 2016/12/6.
 */
var count = 0;
$(function(){
    var sessionId=new Date().getTime();
    sendVerifyCode();
    blur();
    buttondata(sessionId);
});
function sendVerifyCode(){
    $("#sendVerifyCode").click( function (){
    var telephone = $("#telephone").val().replace(/(^\s*)|(\s*$)/g, "");
    if(telephone==null || !telephone.match(/^1\d{10}$/)){
        alert("手机号码不正确");
    }else {
        $.ajax({
            type: 'post',
            url: '/pc/admin/memberScore/sendIdentifyCode',
            timeout:180000,
            data: {mobile:1},
            success: function (params) {
                var data=eval(params);
                var errorCode=data.errorCode
                if (errorCode==0){

                } else if (errorCode==4){
                    alert(data.message)
                }
            }
        });
        $("#sendVerifyCode").css("display","none");
        $("#downtime").css("display","block");
        countDown();
        setTimeout(function (){
            $("#sendVerifyCode").css("display","block");
            $("#downtime").css("display","none");
        }, SS*1000);
    }
    });
}
function blur(){
    $("#cleckmoney").blur(function (){
        var howmoney=parseInt(Math.ceil($("#cleckmoney").val()));
        $("#cleckmoney").val(howmoney);
        if(howmoney>=50){
            if (howmoney!=null&&howmoney>0) {
                var review = confirm("确定充值" + howmoney + "元？");
                if (review){
                    $("#cleckmoney").css("border", "#ddd 1px solid");
                    $(".moneylist li").css("cursor", "default");
                    $(".moneylist li").css("color", "#666");
                    $(".moneylist li").css("border", "#ddd 1px solid");
                    $("#cleckmoney").attr("disabled", "disabled");
                    count = 1;
                    $("#cleckmoney").attr("name",howmoney);
                }
            }else {
                $("#cleckmoney").val("");
                alert("输入金额有误，请重新输入");
            }
        }else{
            alert("最小充值金额为50元");
            $("#cleckmoney").val("");
        }
    });
    cleckpaycash();
}
function cleckpaycash(){
    $(".moneylist li").click(function(){
        if (count==0) {
            count++;
            howmoney = $(this).find("span").text();
            var review = confirm("确定充值" + howmoney + "元？");
            if (review){
                $(this).siblings().css("border", "#ddd 1px solid");
                $(this).siblings().css("color", "#666");
                $("#cleckmoney").css("border", "#ddd 1px solid");
                $("#cleckmoney").attr("disabled", "disabled");
                $(".moneylist li").css("cursor","default");
                $("#cleckmoney").attr("name",howmoney);
            }
        }
    })
}
function buttondata(sessionId){
    $("#button").click(function(){
        var tel=$("#telephone").val();
        var code=$("#identifyCode").val();
        var memberId=$("#memberId").val();
        var score=$("#cleckmoney").attr("name");
        var rate=$("#percent").val()/100;
        if(rate<=0||rate>1){
            alert("加成比例不正确，请重新输入")
        }
        $.ajax({
            type: 'post',
            url: '/pc/admin/memberScore/addMemberScore',
            timeout:180000,
            data: {memberId:memberId,typeId:1,score:score,rate:rate,sessionId:sessionId,telephone:tel,identifyCode:code},
            success: function (params) {
                var json=eval(params);
                if (json.data!=null&&json.errorCode==0){
                    alert("充值成功");
                    location.reload();
                } else if (son.errorCode==4){
                    alert(data.message)
                }
            },error:function(){
                alert("页面加载失败，请重新加载")
            }
        });
})
}