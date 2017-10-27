require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!app/template/Bull100Big.html','css!../css/Bull100BigSmall.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
        $('.rule_left').click(function(){
            $('.rule_right').toggleClass('rule_right1');
            $('.rule_left').toggleClass('rule_left1');
        })
        $('.ui-destop-stake').click(function(){
            $('.Betting').toggleClass('show');
        })
        //把所有隐藏的DIV显示出来
        //var ds=document.getElementsByTagName("div");for(var i in ds)ds[i].style.display='block';
    }
);
/**
 *
 */
// define(['director'], function (director) {
//     var StringRouter = {
//         init : function () {
//             var routes = {
//                 "/bull100":
//             };
//             var router = director(routes);
//             router.init();
//         }
//     }
//     return StringRouter;
// });