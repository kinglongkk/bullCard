require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/Dialog/DialogPrompt.html','css!../css/Dialog.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;
    }
);