// JavaScript Document

// Date: 2014-11-07
// Author: Agnes Xu


i = 0;
j = 0;
count = 0;
MM = 0;
SS = 120;  // 秒 90s
MS = 0;
totle = (MM+1)*600;
d = 180*(MM+1);
MM = "0" + MM;
var gameTime = 120;
//count down
var showTime = function(){
    totle = totle - 1;
    if (totle == 0) {
        clearInterval(s);
        //clearInterval(t1);
        //clearInterval(t2);

    } else {
        if (totle > 0 && MS > 0) {
            MS = MS - 1;
            if (MS < 10) {
                MS = "0" + MS
            }
            ;
        }
        ;
        if (MS == 0 && SS > 0) {
            MS = 10;
            SS = SS - 1;
            if (SS < 10) {
                SS = "0" + SS
            }
            ;
        }
        ;
        if (SS == 0 && MM > 0) {
            SS = 60;
            MM = MM - 1;
            if (MM < 10) {
                MM = "0" + MM
            }
            ;
        }
        ;
    }
    ;
    $("#downtime").html(SS + "s");

};


var countDown = function() {
    //80*80px 时间进度条
    i = 0;
    j = 0;
    count = 0;
    MM = 0;
    SS = gameTime;
    MS = 0;
    totle = (MM + 1) * gameTime * 10;
    d = 180 * (MM + 1);
    MM = "0" + MM;

    showTime();
    s = setInterval("showTime()", 100);


    //start1();
    //start2();
    //t1 = setInterval("start1()", 100);

}