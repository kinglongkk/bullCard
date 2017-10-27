/**
 *
 * 加载百人玩法
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
            init: function (param, filterArgs) {
            this.roomId = param[0];
            var nbIntoRoomOut = filterArgs[0];
                window.nbIntoRoomOut = nbIntoRoomOut;
                window.G_InRoomType = 1;
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