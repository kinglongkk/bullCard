define(['app/filter/Filter','app/controller/gameMain','view/viewBase','app/controller/netDefaultHandle'],
    function (Filter,gm,vBase,netDefaultHandle) {

    return Filter.extend({

        roomId : null,

        /**
         * 构造方法
         */
        init : function () {
            this._super();
            this.params = arguments[0];
            this.roomId = this.params[0]
            console.info("Init GameRoom Controller,Room Id is",this.roomId);
        },

        doFilter : function () {
            this.bindHandle();
            this.isCanIntoRoom(this.roomId);
            return this._super();
        },

        isCanIntoRoom: function () {
            var sendOk = gm.sendToServer("NbIntoRoomIn",{roomId : parseInt(this.roomId)});
            if (!sendOk) {
                var _this = this;
                window.setTimeout(function () {
                   _this.defered.reject();
                },1000)
            }
        },

        /**
         * 绑定ws处理事件
         */
        bindHandle : function () {
            var _this = this;
            netDefaultHandle.NbIntoRoomOut = function (nbIntoRoomOut) {
                if (nbIntoRoomOut.tip.code === '0') {
                    _this.defered.resolve(nbIntoRoomOut);
                    return;
                }
                console.log(nbIntoRoomOut.tip);
                vBase.msgDialog.show(nbIntoRoomOut.tip.tip, "确定");
                _this.defered.reject();
            };
        }

    });
});