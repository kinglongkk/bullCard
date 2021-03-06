"use strict";
cc._RF.push(module, '81a4a4XtK1Om55doheYVRiA', 'BaseManager');
// Script/Views/BaseManager.js

"use strict";

//管理器的父类

cc.Class({
    extends: cc.Component,

    properties: {
        _isADLimit: null },

    // use this for initialization
    onLoad: function onLoad() {
        GG.webHandler.adaptSceneSize();
        GG.curMgr = this;
        this.addLongListen();
        GG.setIsLoading(false);

        this._isADLimit = null;
    },

    //在游戏暂停的时候，还需要持续更新进房信息
    updateEnterHomeData: function updateEnterHomeData(recvData) {
        if (!this._enterHomeData) return;
        if (this._enterHomeData.match.beginTimeNext != recvData.match.beginTimeNext) {
            //进入到下一局比赛
            this._isPauseOverMatch = true;
        }
        this._enterHomeData.seats = recvData.seats;
        this._enterHomeData.match.dealer = recvData.match.dealer;
        this._enterHomeData.match.beginTimeNext = recvData.match.beginTimeNext;
        this._enterHomeData.match.settleTime = recvData.match.settleTime;
        if (recvData.poker) this._enterHomeData.poker = recvData.poker;
    },
    //使用最新的进房信息刷新页面
    refreshHome: function refreshHome() {
        if (G_Config_common.isLocal) return;
        if (this.net_setHomeData && this._enterHomeData) this.net_setHomeData(this._enterHomeData);
    },
    //当暂停的时候触发该函数
    whenPauseGame: function whenPauseGame() {
        this._isPauseOverMatch = false;
    },
    //当恢复游戏的时候触发该函数
    whenResumeGame: function whenResumeGame() {
        if (this.comp_uiLayer) {
            this.comp_uiLayer.closeAllUI();
        }
    },
    //设置结算信息的数据
    setBettingEndInfo: function setBettingEndInfo(recvData) {},

    //每局开局，效验玩家自己的数据是否正常
    checkMyselfInfo: function checkMyselfInfo(info) {
        if (!info || info.length < 1) {
            GG.tipsMgr.showConfirmTip_ONE(G_CHINESE.noMyself, function () {
                GG.exitGame();
            }.bind(this));
            return false;
        } else return true;
    },

    setAdLimit: function setAdLimit(isLimit) {
        this._isADLimit = isLimit;
    },

    getAdIsLimit: function getAdIsLimit() {
        return this._isADLimit;
    },

    //=========================================

    //添加长监听
    addLongListen: function addLongListen() {
        //异地登陆
        GG.socketMgr.registerLong(NetType.r_passiveOut, this.net_passiveOut.bind(this));
        //单独的tip
        GG.socketMgr.registerLong(NetType.r_tips, this.net_tipData.bind(this));
        //跑马灯
        GG.socketMgr.registerLong(NetType.r_getAnnouncement, this.net_getAnnouncement.bind(this));
        //游戲长时间没有操作
        GG.socketMgr.registerLong(NetType.r_idleTimeOut, this.net_idleTimeOut.bind(this));
    },
    //tip 单独发送过来的事件
    net_tipData: function net_tipData(recvData) {
        //未登陆，请登陆
        if (recvData.code == G_TYPE.serverCodeType.needLogin) {
            GG.tipsMgr.showConfirmTip_ONE(recvData.tip, function () {
                GG.loginSocket(function () {
                    if (GG.socketMgr.getIsConnected()) {
                        GG.exitHome();
                    } else {
                        GG.exitGame();
                    }
                });
            }.bind(this));
        }
    },
    //异地登陆
    net_passiveOut: function net_passiveOut(recvData) {
        GG.socketMgr.setTimeOutLimit(true);
        GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip, function () {
            GG.exitGame();
        }.bind(this));
    },
    //有公告
    net_getAnnouncement: function net_getAnnouncement(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        //recvData.announcementType
        //recvData.title
        //recvData.content
        if (this._isADLimit) return;
        GG.tipsMgr.addNotice(recvData.content);
    },
    //百人——游戏中长时间没有做操作
    net_idleTimeOut: function net_idleTimeOut(recvData) {

        GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip, function () {
            GG.exitHome();
        }.bind(this));
    },

    onDestroy: function onDestroy() {
        GG.socketMgr.cancelAllLongListen();
    }

});

cc._RF.pop();