require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/Dialog/DialogActive.html','css!../css/Dialog.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
            $("[tab]").click(function(){
            $("[tab]").children("li a").removeClass('active').children("li a div")
            $("[tab]").children("li a").eq($(this).index()).addClass('active');
                $("[tab]").children("li a").children("div").toggleClass('Active_active_btn_after');
            $(this).parents(".dia_big").find(".Active_active_content").hide();
            $(this).parents(".dia_big").find( "." + $(this).attr("tab")).show();
            // if($(this).index()==0){
            //     $(this).velocity({"margin-left":"6.5rem"},{duration:200});
            // }else{
            //     $($("[tab]")[0]).velocity({"margin-left":"-=2.7rem"},{duration:200});
            // }

        });
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