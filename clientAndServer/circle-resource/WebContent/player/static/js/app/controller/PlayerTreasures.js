/**
 * Created by black on 2016/10/10.
 * 富豪榜
 */
define(['jsrender',
        'text!app/template/PlayerTreasures.html'], function (jsrender, template) {

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
                url: root + "/game/statistics/treasures.html",
                dataType:"json",
                success:function(data){
                    var html = $(template).render(data);
                    $(".list_content").html(html);
                    $("#commonLoading").hide();
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("you have a error treasures" + textStatus)
                }
            });
        }

    });

});