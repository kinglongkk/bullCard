define(['jsrender',
        'text!app/template/Bull100BigSmall.html',
        'bull100/ctrlBull',
        'app/controller/gameMain',
        'app/controller/netDefaultHandle',
        'view/viewBase',
        'text!app/template/BullAnimation.html',
        'css!../../../../css/Bull100BigSmall.css',//css文件放最后不被使用
        'css!../../../../css/Animation.css'
    ],
    function (jsrender, template, cBull, gm, handle, vBase, animation) {
    return Class.extend({

            roomId: null,

            gameModel: 'bull100',

            activeBreak: null,

            /**
             * 构造方法
             */
            init: function (param, filterArgs) {
                this.roomId = param[0];
                var nbIntoRoomOut = filterArgs[0];
                var roomName = nbIntoRoomOut.roomName;
                var bullBetTimes = nbIntoRoomOut.betTimes;
                this.renderTemplate(roomName, bullBetTimes);
                this.bindEvent();
                cBull.init(nbIntoRoomOut);
            },
            /**
             * 渲染模板
             */
            renderTemplate: function (roomName, bullBetTimes) {
                var _this = this;
                $('.container').html(template+animation);
                $('#room_nub').html(_this.roomId);
                $('#room_model').html(_this.gameModel);
                $('#room_name').html(roomName);
                $('#bullBetTimes').html(bullBetTimes);
            },

            /**
             * 绑定事件
             */
            bindEvent: function () {
                //按钮:返回桌面
                var _this = this;

                //退出房间:处理器
                handle.NbExitRoomOut = function (exitRoomOut) {
                    if (exitRoomOut.tip.code == "0" && (parseInt(_this.roomId) == exitRoomOut.exitRoomIn.roomId)) {
                        console.info("退出房间成功");
                        cBull.clear();
                        window.location.href = "#/model/" + _this.gameModel;
                        return;
                    }
                    vBase.msgDialog.show(exitRoomOut.tip.tip,"确定", "取消", function () {
                        _this.exitRoom(false);
                    });
                    console.log(exitRoomOut.tip.code + exitRoomOut.tip.tip);
                };

                handle.NbTimeoutOut = function (timeoutOut) {
                    vBase.msgDialog.show(timeoutOut.tip.tip,"确定", null, function (){
                        cBull.clear();
                        window.location.href = "#/model/" + _this.gameModel;
                    });
                };

                //退出房间:请求
                $('.ui-destop-back').on('click', function () {
                    _this.exitRoom(true);
                });

                //吃瓜群众列表
                $('.ui-destop-onlookers').click(function () {
                    $('#dialogLoading').show();
                    requirejs(['app/controller/dialog/PlayerList'],function (PlayerList) {
                        new PlayerList;
                    });
                });

                //房间胜负走势图
                $('.ui-destop-charts').click(function () {
                    $('#dialogLoading').show();
                    var gameRoomId  = _this.roomId;
                    requirejs(['app/controller/dialog/Trend'],function (Trend) {
                        new Trend(gameRoomId);
                    });
                });

                //游戏规则
                $('.ui-destop-rules').click(function () {
                    requirejs(['app/controller/dialog/Rules'],function (Rules) {
                        new Rules;
                    });
                 });
            },

        /**
         * 退房请求
         */
        exitRoom: function (active) {
            var _this = this;
            active ? activeBreak = false : activeBreak = true;
            gm.sendToServer("NbExitRoomIn",{
                gameModel : _this.gameModel,
                roomId : parseInt(_this.roomId),
                isBreak : activeBreak
            });
        }
    });
});