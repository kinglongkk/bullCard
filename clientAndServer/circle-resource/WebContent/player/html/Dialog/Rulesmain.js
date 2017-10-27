require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/Dialog/DialogRules.html','css!../css/Dialog.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
        $("[tab]").click(function(){
            $("[tab]").children("li a").removeClass('active')
            $("[tab]").children("li a").eq($(this).index()).addClass('active');
            $(this).parents(".dia_big").find(".Rules_content").hide();
            $(this).parents(".dia_big").find( "." + $(this).attr("tab")).show();
            // if($(this).index()==0){
            //     $(this).velocity({"margin-left":"6.5rem"},{duration:200});
            // }else{
            //     $($("[tab]")[0]).velocity({"margin-left":"-=2.7rem"},{duration:200});
            // }

        });

    }
);
