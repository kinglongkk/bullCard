/**
 * Created by black on 2016/10/27.
 * 吃瓜群众列表
 */

define(['jsrender',
        'view/viewBase',
        'text!app/template/dialog/PlayerList.html'], function (jsrender, vBase, template) {


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
                url: root + "game/bull100/playerList.html",
                dataType:"json",
                data:{
                    t:new Date
                },
                success:function(data){

                    var html = $(template).render(data);
                    $(".dialogFront").html(html);
                    var dialog = new vBase.Dialog();
                    dialog.show();
                    $('#dialogLoading').hide();
                    dialog.onHide = function () {
                        console.log("PlayerList dialog hide");
                    }
                },
                error:function (jqXHR, textStatus, errorThrown) {

                    alert("you have a error PlayerList"+ textStatus)
                }
            });
        }
    });
});