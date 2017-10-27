/**
 * Created by black on 2016/10/12.
 * 每周盈利榜
 */
define(['jsrender',
        'text!app/template/PlayerProfit.html'],
    function (jsrender, template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init : function () {
            this.renderTemplate();
        },

        /**
         * 渲染模板
         */
        renderTemplate : function () {
            $.ajax({
                type: "post",
                url: root + "/game/statistics/profits.html",
                dataType:"json",
                data:{
                    "gameId": 1//TODO:是否使用变量作为参数
                },
                success:function(data){
                    var html = $(template).render(data);
                    $(".earnings").html(html);
                    $("#commonLoading").hide();
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("you have a error profits" +textStatus)
                }
            });
        }
    });
});