require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/Registered/Registered.html','css!../css/login.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
        $(document).ready(function(){
            $(".ui-login-remember-gou").click(function(){
                $(".ui-remember-gou-icon").toggle();
            })
        })
        //把所有隐藏的DIV显示出来
        // var ds=document.getElementsByTagName("div");for(var i in ds)ds[i].style.display='block';
    }
);
