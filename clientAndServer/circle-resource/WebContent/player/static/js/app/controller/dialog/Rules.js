/**
 * Created by jin on 2016/11/17.
 * 游戏规则弹窗
 */

define(['jsrender',
        'view/viewBase',
        'text!app/template/dialog/Rules.html'],
    function (jsrender, vBase,template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init: function () {
            this.renderTemplate();
            this.bindEvent();
        },

        /**
         * 渲染模板
         * jqXHR jqXHR对象
         * textStatus 返回的状态
         * errorThrown 服务器抛出返回的错误信息
         */
        renderTemplate: function () {
            var html = $(template).render();
            $(".dialogFront").html(html);
            var dialog = new vBase.Dialog();
            dialog.show();
            dialog.onHide = function () {
                console.log("Rules dialog hide");
            }
        },

        /**
         * 绑定事件
         */
        bindEvent: function () {
            $("[tab]").click(function(){
                $("[tab]").children("li a").removeClass('active');
                $("[tab]").children("li a").eq($(this).index()).addClass('active');
                $(this).parents(".dia_big").find(".Rules_content").hide();
                $(this).parents(".dia_big").find( "." + $(this).attr("tab")).show();
            });
        }
    });
});
