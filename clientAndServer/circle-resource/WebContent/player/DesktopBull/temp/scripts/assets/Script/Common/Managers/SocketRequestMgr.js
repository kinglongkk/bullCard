"use strict";
cc._RF.push(module, '8ebd8sa+V1C0ZZpdqrs0I/G', 'SocketRequestMgr');
// Script/Common/Managers/SocketRequestMgr.js

"use strict";

//socket请求数据处理,模拟http请求与回复

module.exports = cc.Class({
    properties: {
        _dict_sendInfo: null },

    // use this for initialization
    ctor: function ctor() {
        this._dict_sendInfo = {};
    },

    //进房请求
    send_inHome: function send_inHome(netData, callFunc) {
        this._dict_sendInfo[NetType.s_enterHouse] = callFunc;
        GG.socketMgr.SendMsg(NetType.s_enterHouse, netData, this._netData_inHome.bind(this));
    },
    _netData_inHome: function _netData_inHome(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            //进入房间失败-金币不足
            if (tip.code == G_TYPE.serverCodeType.goldNotEnough) {
                // if(GG.tipsMgr) GG.tipsMgr.showConfirmTip_ONE(tip.tip, function () {
                //     //显示房间列表
                //     //是否直接跳转到房间列表
                //     // this._isFirstEnter = false;
                //     // this._oneRequestEnd();
                // }.bind(this));
            }
            if (GG.tipsMgr) GG.tipsMgr.showConfirmTip_ONE(tip.tip, function () {
                //进入房间失败
                if (tip.code == G_TYPE.serverCodeType.matchNoEnd) {
                    //已经开始的比赛未结束

                    //清理hash信息
                    // G_DATA.setCurSceneHash('',0);
                    // GG.exitHome();
                }
            });
            recvData = null;
        } else {
            //进房请求成功
            var inHomeData = G_OBJ.data_inHomeData();
            inHomeData.net_inHomeData = recvData;
            GG.getPlayer().setInHomeData(inHomeData);
        }

        if (this._dict_sendInfo[NetType.s_enterHouse]) {
            this._dict_sendInfo[NetType.s_enterHouse](recvData);
            delete this._dict_sendInfo[NetType.s_enterHouse];
        }
    }

});

cc._RF.pop();