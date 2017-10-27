/**
 * Created by black on 2016/10/25.
 * 胜负走势图
 */

define(['jsrender',
        'view/viewBase',
        'text!app/template/dialog/Trend.html'], function (jsrender, vBase,template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init : function (roomId) {
            this.renderTemplate(roomId);
        },

        /**
         * 渲染模板
         */
        renderTemplate : function (roomId) {
            $.ajax({
                type: "post",
                url: root + "game/chart/inning.html",
                dataType:"json",
                data:{
                    "gameModelId": 1,
                    "gameRoomId": roomId
                },
                success:function(data){

                    var html = $(template).render(data);
                    $(".dialogFront").html(html);
                    var dialog = new vBase.Dialog();
                    dialog.show();
                    $('#dialogLoading').hide();
                    dialog.onHide = function () {
                        console.log("Trend dialog hide");
                    }
                },
                error:function (jqXHR, textStatus, errorThrown) {
                    alert("you have a error Trend Trend" + textStatus)
                }
            });
        }
    });
});
