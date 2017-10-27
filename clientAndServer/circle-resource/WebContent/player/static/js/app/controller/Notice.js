/**
 * Created by black on 2016/10/14.
 * 滚动通知
 */
define(['jsrender', 'text!app/template/Notice.html'], function (jsrender, template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init : function () {
            this.renderTemplate();
            this.bindEvent();
        },

        /**
         * 渲染模板
         * jqXHR jqXHR对象
         * textStatus 返回的状态
         * errorThrown 服务器抛出返回的错误信息
         */
        renderTemplate : function () {

            var html = $(template).render();
            $(".announcement").html(html);
        },

        /**
         * 绑定事件
         */
        bindEvent : function () {

        }
    });

});