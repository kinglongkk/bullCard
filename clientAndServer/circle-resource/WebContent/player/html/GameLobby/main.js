require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/GameLobby/GameLobby.html','css!../css/GameLobby.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
        $('.gain').click(function(){


            $('.earnings').toggleClass('show');
            $('.list_content').toggleClass('hidden');
            $('.button2').toggleClass('show');
            $('.button').toggleClass('hidden');


            // $("#maintainReason").attr("required","required");
            // ("#maintainReason").removeAttr("required","required");
        })
        $('.rich2').click(function(){


            $('.earnings').toggleClass('show');
            $('.list_content').toggleClass('hidden');
            $('.button2').toggleClass('show');
            $('.button').toggleClass('hidden');


            // $("#maintainReason").attr("required","required");
            // ("#maintainReason").removeAttr("required","required");
        })

        //把所有隐藏的DIV显示出来
        // var ds=document.getElementsByTagName("div");for(var i in ds)ds[i].style.display='block';
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