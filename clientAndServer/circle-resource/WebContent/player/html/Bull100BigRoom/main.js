require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/Bull100BigRoom/Bull100BigRoom.html','css!../css/Bull100BigRoom.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
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