require.config({
    baseUrl : '../static/js',
    paths : {
        text: '../../../common/js/RequireJS-2.3.2/text',
        css: '../../../common/js/RequireJS-2.3.2/css'
    }
});
require(['text!../../html/Dialog/DialogSet.html','css!../css/Dialog.css'],
    function (temlate) {
        document.getElementsByTagName("body")[0].innerHTML=temlate;

    }
);
function ChangeCloseOnButton(obj, prevOpenClass, prevCloseClass) {
    var jqObj = $(obj);
    if (jqObj.hasClass('Set_close')) {
        jqObj.removeClass('Set_close').addClass('Set_open');
        jqObj.prev().removeClass(prevCloseClass).addClass(prevOpenClass);
    } else if (jqObj.hasClass('Set_open')) {
        jqObj.removeClass('Set_open').addClass('Set_close');
        jqObj.prev().removeClass(prevOpenClass).addClass(prevCloseClass);
    }
}