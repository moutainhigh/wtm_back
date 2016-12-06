//鍒嗛〉鎻掍欢
/**
 2014-08-05 ch
 **/
(function($){
    var ms = {
        init:function(obj,args){
            return (function(){
                ms.fillHtml(obj,args);
                ms.bindEvent(obj,args);
                //是否和绑定有关？目前绑定一个数据后不消失绑定，生成多个args就会绑定多个args。
            })();
        },
        //濉厖html
        fillHtml:function(obj,args){
            return (function(){
                obj.empty();
                //涓婁竴椤�
                if(args.current > 1){
                    obj.append('<a href="javascript:;" class="start">首页</a>');
                    obj.append('<a href="javascript:;" class="prevPage">&lt</a>');
                }else{
                    obj.remove('.prevPage');
                    obj.remove('.start');
                    obj.append('<span class="disabled">首页</span>');
                    obj.append('<span class="disabled">&lt</span>');
                }
                //涓棿椤电爜
                if(args.current != 1 && args.current >= 4 && args.pageCount != 4){
                    obj.append('<a href="javascript:;" class="tcdNumber">'+1+'</a>');
                }
                if(args.current-2 > 2 && args.current <= args.pageCount && args.pageCount > 5){
                    obj.append('<span>...</span>');
                }
                var start = args.current -2,end = args.current+2;
                if((start > 1 && args.current < 4)||args.current == 1){
                    end++;
                }
                if(args.current > args.pageCount-4 && args.current >= args.pageCount){
                    start--;
                }
                for (;start <= end; start++) {
                    if(start <= args.pageCount && start >= 1){
                        if(start != args.current){
                            obj.append('<a href="javascript:;" class="tcdNumber">'+ start +'</a>');
                        }else{
                            obj.append('<span class="current">'+ start +'</span>');
                        }
                    }
                }
                if(args.current + 2 < args.pageCount - 1 && args.current >= 1 && args.pageCount > 5){
                    obj.append('<span>...</span>');
                }
                if(args.current != args.pageCount && args.current < args.pageCount -2  && args.pageCount != 4){
                    obj.append('<a href="javascript:;" class="tcdNumber">'+args.pageCount+'</a>');
                }
                //涓嬩竴椤�
                if(args.current < args.pageCount){
                    obj.append('<a href="javascript:;" class="nextPage">&gt</a>');
                    obj.append('<a href="javascript:;" class="end">末页</a>');
                }else{
                    obj.remove('.nextPage');
                    obj.remove('.end');
                    obj.append('<span class="disabled">&gt</span>');
                    obj.append('<span class="disabled">末页</span>');
                }
                obj.append('<label class="jumplabel">跳转到<input type="text" id="selectcount" value=""/><a href="javascript:;" class="jumpgo">GO</a></label>');
            })();
        },
        //缁戝畾浜嬩欢
        bindEvent:function(obj,args){
            return (function(){
                obj.on("click","a.tcdNumber",function(){
                    var current = parseInt($(this).text());
                    ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current);
                    }
                });
                //涓婁竴椤�
                obj.on("click","a.start",function(){
                    var current =parseInt("1");
                    ms.fillHtml(obj,{"current":1,"pageCount":args.pageCount});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current);
                    }
                });
                obj.on("click","a.prevPage",function(){
                    var current = parseInt(obj.children("span.current").text());
                    ms.fillHtml(obj,{"current":current-1,"pageCount":args.pageCount});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current-1);
                    }
                });
                //涓嬩竴椤�
                obj.on("click","a.nextPage",function(){
                    var current = parseInt(obj.children("span.current").text());
                    ms.fillHtml(obj,{"current":current+1,"pageCount":args.pageCount});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current+1);
                    }
                });
                obj.on("click","a.end",function(){
                    var current =args.pageCount;
                    ms.fillHtml(obj,{"current":current,"pageCount":current});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current);
                    }
                });
                obj.on("click","a.jumpgo",function(){
                    var current = parseInt($("#selectcount").val());
                    if(current>0&&current<=args.pageCount){
                    ms.fillHtml(obj,{"current":current,"pageCount":args.pageCount});
                    if(typeof(args.backFn)=="function"){
                        args.backFn(current);
                    }
                    }
                });
            })();
        }
    }
    $.fn.createPage = function(options){
        var args = $.extend({
            pageCount : "",
            current : "",
            backFn : function(){}
        },options);
        ms.init(this,args);
    }
})(jQuery);
