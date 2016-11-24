/**
 * Created by Administrator on 2016/11/5.
 */
var approveHead = ' <li class="nobd" id="total">' +
    '<div class="col-xs-1"><label class="col-xs-12"><input class="totalcleck" type="checkbox" value="全选"/>全选</label></div>' +
    '<div class="col-xs-1">提现方式</div>' +
    '<div class="col-xs-2">用户账号</div>' +
    '<div class="col-xs-1">用户ID</div>' +
    '<div class="col-xs-3">提现时间</div>' +
    '<div class="col-xs-1">提现金额</div>' +
    '<div class="col-xs-1">处理意见</div>' +
    '<div class="col-xs-2 nobd">审批</div>' +
    '</li>'

function getApproveList(pageIndex,pageSize) {
    if (typeof pageIndex =='undefined' || pageIndex ==null){
        pageIndex=0
    }
    if (typeof pageSize =='undefined' || pageSize ==null){
        pageSize=0
    }
    //patchWechatCustomers
    $.ajax({
        type: 'get',
        dataType: 'json',
        async:false,
        url: '/pc/admin/paymemberCallBack/getApproveList',
        data: {pageIndex:pageIndex,pageSize:pageSize},
        success: function (params){
            var json = eval(params); //数组
            console.log("json数据为：" + params)
            if (json != null && json.errorCode == 0){
                var data = json.data;
                $(".listcontent").empty();
                var trs=dealGetApproveData(data);
                $(".listcontent").append(trs);
                console.log("总数是"+total);
                checkTotal=data.total
            } else {
                alert("获取列表失败")
            }
        }
    })
}


function dealGetApproveData(data){
    total=data.total;
    var i=0;
    var elements=approveHead;
    if (!Array.prototype.forEach) {
        Array.prototype.forEach = function(fun /*, thisp*/){
            var len = this.length;
            if (typeof fun != "function")
                throw new TypeError();
            var thisp = arguments[1];
            for (var i = 0; i < len; i++){
                if (i in this)
                    fun.call(thisp, this[i], i, this);
            }
        };
    }//使ie6、7、8支持forEach
    data.list.forEach(function (child) {
        var id=child.id;
        elements  = elements + getApproveListTr(child.payType,child.accountName,child.createTime,child.amount,child.isPaid,child.memberId,child.id,i++);
    });
    return elements
}

function getApproveListTr(account,name,time,amount,isCheck,memberId,listid,i) {
    var i=i%pageSize+1;
    var checkState='';
    var payType='微信';
    if (account==1){
        payType='支付宝'
    }
    if (isCheck==1){
        checkState='已审核'
    }else if(isCheck==0){
        checkState='拒绝'
    }
    var str = "[/\$%&<>]+";
    var reg = new RegExp(str);
    if (reg.test(name)){
        var names = name.replace(/[/\$%&<>]/g, '');
    }else {names=name;}
    var approvecontent = '<li class="list '+account+'">'+
        '<div class="col-xs-1 childcheck"><label class="col-xs-12">'+i+'<input class="radiocleck" type="checkbox" value=""/></label></div>' +
        '<div class="col-xs-1 paytype">'+payType+'</div>' +
        '<div class="col-xs-2">'+names+'</div>' +
        '<div  class="col-xs-1" id="one">'+memberId+'</div>' +
        '<div class="col-xs-3">'+getLocalTime(time)+'</div>' +
        '<div class="col-xs-1">'+amount+'</div>' +
        '<div class="col-xs-1"></div>' +
        '<div class="audit col-xs-2 nobd">'+
        '<select class="col-xs-12" onchange="selectSubmit(this)" id="'+listid+'">'+
        '<option>'+'待审核'+'</option>'+
        '<option>'+'审核'+'</option>'+
        '<option>'+'拒绝'+'</option>'+
        '</select>'+
        '</div>' +
        '</li>'
    return approvecontent;
}
var selecount=0;
var selectSubmit=function(obj){
    selecount++;
    var id=obj.getAttribute("id");
    console.log("主体id是"+id);
    //var index=$("select").get(0).selectedIndex;
    var index=obj.selectedIndex;
    index=parseInt(index);
    obj.parentNode.html="已审核";
console.log(obj.parentNode);
    console.log("index是"+index);
    console.log("selecount是"+selecount);
    if(index!=0){
        selecount--;
        console.log("点击次数减过后"+selecount);
        var view=prompt("请输入审核意见","通过");
        //$("this").parent().prev().empty();
        //$(".audit").prev().text(view);
        var a=obj.parentNode.previousElementSibling;
        a.innerHTML ="";
        a.innerHTML =view;
        if(view){
            var isCheck=0;
            var data="[]";
            var jsonarray= eval('('+data+')');
            var jsonstring='';
            switch (index){
                case 1:
                    isCheck=1;

                    break;
                case 2:
                    isCheck=0;
            }
            var arr  =
            {
                "approveId" : id,
                "isApprove" : isCheck,
                "remark":view,
            };
            jsonarray.push(arr);
            console.log(jsonarray);
            jsonstring=JSON.stringify(jsonarray);
            $.ajax({
                type: 'post',
                dataType: 'json',
                contentType:'application/json',
                url: '/pc/admin/paymemberCallBack/patchWechatCustomers',
                data: jsonstring,
                success: function (params){
                    var json = eval(params); //数组
                    console.log("json数据为：" + params)
                    if (json != null && json.errorCode == 0){
                        var data = json.data;
                        obj.parentNode.innerHTML ="已审核";
                        obj.parent.style.color="green";
                    } else {
                        //$("this").parent().prev().empty();
                        var a=obj.parentNode.previousElementSibling;
                        a.innerHTML ="";
                        alert("获取数据失败");
                        //$(".audit select option:first").prop("selected", 'selected');
                        obj.options[0].selected = true;
                    }
                },error:function(){
                    //$("this").parent().prev().empty();
                    var a=obj.parentNode.previousElementSibling;
                    a.innerHTML ="";
                    alert("审核失败，请重新审核");
                    //$(".audit select option:first").prop("selected", 'selected');
                    obj.options[0].selected = true;
                }
            })
        }else{
            //$(".audit select option:first").prop("selected", 'selected');
            obj.options[0].selected = true;
            a.innerHTML =name;
        }
    }
};
function batchReview(){
    var btnlength = $(".radiocleck:checked").length;
    if (btnlength > 0){
        var view=prompt("请输入审核意见","通过");
        if (view){
            var data="[]";
            var jsonarray= eval('('+data+')');
            var jsonstring='';
            $(".radiocleck:checked").each(function(){
                var obj=$(this).closest(".list").find(".audit");
                console.log(obj.prev());
                obj.prev().html("");
                obj.prev().html(view);
                obj.find("select option:eq(1)").attr('selected','selected');
                //$(this).closest(".list").find(".audit option:eq(1)").attr('selected','selected');
                var id=obj.find("select").attr("id");
                console.log("主体id是"+id);
                var isCheck=1;
                var arr  =
                {
                    "approveId" : id,
                    "isApprove" : isCheck,
                    "remark":view,
                };
                jsonarray.push(arr);
                console.log(jsonarray);
                jsonstring=JSON.stringify(jsonarray);
                //alert(jsonstring);
            });
            var obj=$(".radiocleck:checked").closest(".list").find(".audit");
                $.ajax({
                    type: 'post',
                    dataType: 'json',
                    contentType:'application/json',
                    url: '/pc/admin/paymemberCallBack/patchWechatCustomers',
                    data:jsonstring,
                    success: function (params){
                        var json = eval(params); //数组
                        console.log("json数据为：" + params);
                        if (json != null && json.errorCode == 0){
                            obj.html("已审核");
                            obj.css("color","green");
                        } else {
                            obj.prev().html("");
                            alert("获取数据失败");
                            //$(".audit select option:first").prop("selected", 'selected');
                            obj.find("select option:first").attr('selected','selected');
                        }
                    },error:function(){
                        //$("this").parent().prev().empty();
                        obj.prev().html("");
                        alert("获取数据失败");
                        alert("审核失败，请重新审核");
                        //$(".audit select option:first").prop("selected", 'selected');
                        obj.find("select option:first").attr('selected','selected');
                    }
                });

        }
    } else {
        alert("请选择要处理的数据");
    }
};




function getLocalTime(nS) {
    return new Date(parseInt(nS) * 1000).toLocaleString().replace(/年|月/g, "-").replace(/日/g, " ");
}