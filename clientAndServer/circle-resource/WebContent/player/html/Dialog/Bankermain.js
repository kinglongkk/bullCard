require.config({
    baseUrl : '../static/js',
    packages : [
        {   name     : "view",
            location : "../js/app/view"
        },
        { name     : "site",
            location : "../js"
        },
    ],
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css',
        Velocity : '../../../common/js/velocity/velocity'
    }
});
require(['../../../common/js/circle/common/ClassTool']);

/** 清理缓存开始 **/
var all=[];
require.onResourceLoad = function (context, map, depArray) {
    all.push(map.name);
};
function end(){
    console.log("--------------------------- END requirejs:");
    all.map(function(item){
        require.undef(item);
    });
    all = [];
};
var toEnd = false;
document.body.addEventListener("touchstart", function(e){
    toEnd = (e.touches[0].clientX < document.body.clientWidth/10 && e.touches[0].clientY > document.body.clientWidth*9/10);
});
document.body.addEventListener("touchmove", function(e){
    if(toEnd && e.touches[0].clientX > document.body.clientWidth*9/10
        && e.touches[0].clientY < document.body.clientWidth/10){
        end();
        location.reload();
    }
});

function version(){
   return Math.floor(Math.random()*2100000000);
}
/**----清理缓存结束----**/

require(['site/common/MoneyFormat','view/viewBase','text!../../html/Dialog/DialogBanker.html?v='+version(),'css!../css/Dialog.css'],
function (moneyFormat,vBase,temlate) {
    document.getElementsByTagName("body")[0].innerHTML=temlate;
    var slider = new vBase.Slider('btn','bar', 'step', 'btnTip', function (percent) {
        console.log(percent);//根据已改变的滑动条的百分比设置金额
    }, moneyFormat.formatBy1000, 10000);
    slider.setRange(30000, 20000, 3000000);
    // slider.setEnable(false);
}
);