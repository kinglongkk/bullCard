/**
 * Created by black on 2016/10/28.
 * helpers函数
 */
define(['jsrender'], function (jsrender) {

    /**
     * data数据判断
     */
    $.views.helpers({
        "dataIsEmpty": function (code) {
            if (code == 0){

                return false;
            }else {

                return true;
            }
        }
    });

});