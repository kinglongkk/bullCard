define(['jsrender','text!app/template/Games.html'], function (jsrender,template) {

    return Class.extend({

        /**
         * 构造方法
         */
        init : function (param) {
            console.info("Init Game Controller")
            this.renderTemplate();
        },

        /**
         * 渲染模板
         */
        renderTemplate : function () {
            var html = $(template).render();
            $(".container").html(html);
        }

    });
});