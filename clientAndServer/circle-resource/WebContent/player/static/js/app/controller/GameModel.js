/**
 *
 * 加载游戏模式
 */
define([
        'app/controller/BaseController',
        'jsrender',
        'app/controller/PersonalInfo',
        'app/controller/Notice',
        'text!app/template/Bull100BigRoom.html',
        'text!app/template/BullClassicRoom.html',
        'text!app/template/BullBaoRoom.html'
        ],
    function (BaseController,jsrender, PersonalInfo, Notice, bull100RoomsTemplate, bullClassicRoomTemplate, bullBaoRoomTemplate) {

    return BaseController.extend({

            /**
             * 构造方法
             */
            init : function (param) {
                console.info("Init GameModel Controller,Model Type is:" + param[0]);
                this._super();
                this.setBackUrl("/");
                this.renderTemplate(param);
            },

            setBackUrl : function (url) {
                this._super(url);
            },

            /**
             * 渲染模板
             */
            renderTemplate : function (param) {
                var gameModel = param[0];
                $.ajax({
                    type: "post",
                    url: root + "/game/rooms.html",
                    dataType: "json",
                    data: {"gameModel": param[0]},
                    success: function (data) {
                        new PersonalInfo().renderTemplate(false);
                        if (gameModel === "bull100") {//百人
                            var html = $(bull100RoomsTemplate).render(data);
                        }
                        if (gameModel === "bullclassic") {//经典
                            var html = $(bullClassicRoomTemplate).render(data);
                        }
                        if (gameModel === "bullBao") {//押宝
                            var html = $(bullBaoRoomTemplate).render(data);
                        }
                        $(".container").html(html);
                        $('#dialogLoading').hide();
                        new Notice;
                        $(".bullRoom li").bind("click",function (e) {
                            $(e.currentTarget).addClass("BigRoom_animation")
                        });
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                    }
                });
            }
    });
});