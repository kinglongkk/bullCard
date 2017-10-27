/**
 *
 * 加载经典玩法
 */
define([
        'app/controller/BaseController',
        'jsrender',
        'text!app/template/BullClassicDesk.html'],
    function (BaseController,jsrender, bullClassicDeskTemplate) {

    return BaseController.extend({

            /**
             * 构造方法
             */
            init : function (param) {
                this._super();
                this.renderTemplate();
            },

            /**
             * 渲染模板
             */
            renderTemplate : function () {
                var html = $(bullClassicDeskTemplate).render();
                $(".container").html(html);
            }
    });
});