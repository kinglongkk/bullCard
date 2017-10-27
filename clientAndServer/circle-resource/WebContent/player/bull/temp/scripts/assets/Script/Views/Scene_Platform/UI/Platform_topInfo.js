"use strict";
cc._RF.push(module, '94707lnJddKIpBS9jaL9Sif', 'Platform_topInfo');
// Script/Views/Scene_Platform/UI/Platform_topInfo.js

'use strict';

//游戏大厅中玩家信息
cc.Class({
    extends: require('AutoDealing'),

    properties: {
        node_head: {
            default: null,
            type: cc.Node,
            displayName: '头像'
        },
        label_name: {
            default: null,
            type: cc.Label,
            displayName: '昵称'
        },
        label_coin: {
            default: null,
            type: cc.Label,
            displayName: '金币'
        },
        node_exit: {
            default: null,
            type: cc.Node,
            displayName: '退出按钮'
        },
        node_back: {
            default: null,
            type: cc.Node,
            displayName: '返回按钮'
        },
        node_setting: {
            default: null,
            type: cc.Node,
            displayName: '设置按钮'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerButton();
        this.node.active = false;
    },

    _registerButton: function _registerButton() {
        this.registerButton(this.node_exit, this.onClick_exit, this);
        this.registerButton(this.node_back, this.onClick_back, this);
        this.registerButton(this.node_setting, this.onClick_setting, this);
        //增加对玩家身上金币的监听
        this._listenerName = GG.Listener.registerFunc(G_TYPE.globalListener.playerGold, this.on_goldChange.bind(this));
    },

    //退出游戏
    onClick_exit: function onClick_exit(event) {
        GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText2, function () {
            //确认退出
            GG.exitGame();
        });
    },
    //返回主场景
    onClick_back: function onClick_back(event) {
        GG.platformMgr.showHomeList(false);
    },
    //系统设置
    onClick_setting: function onClick_setting(event) {
        //GG.audioMgr.playMusic('gameLose');
        var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet, function (layer) {
            layer.setData();
        });
        //if(G_Config_common.isLocal){
        //    var data = {'music':true,'sound':true};
        //    if(GG.getPlayer().getSystemSet()) data = GG.getPlayer().getSystemSet();
        //    //console.log(data);
        //    var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet);
        //    layer.setData(data);
        //}else{
        //    //获取玩家系统设置
        //    if(!GG.getPlayer().getSystemSet()){
        //        var sendData = null;
        //        GG.httpMgr.sendHttpLogin(G_DIALOG_URL.loginToSetUrl, sendData, function (data) {
        //            GG.getPlayer().setSystemSet(data[0]);
        //            var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet);
        //            layer.setData(data[0]);
        //        }.bind(this));
        //    }else {
        //        var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet);
        //        layer.setData(GG.getPlayer().getSystemSet());
        //    }
        //}
    },
    //监听金币的变化
    on_goldChange: function on_goldChange(listenerName, dataObj) {
        if (this.label_coin) this.label_coin.string = GG.getPlayer().getPlayerGold();
    },

    initShow: function initShow() {
        var jsonData = GG.getPlayer().getBaseInfo();
        if (jsonData) {
            G_TOOL.setHeadImg(this.node_head, jsonData.avatarUrl);
            this._setName(jsonData.nickname);
            this.label_coin.string = GG.getPlayer().getPlayerGold();
        }
    },

    //显示房间列表的时候，返回按钮需要改变
    setHomeListShow: function setHomeListShow(isShow) {
        if (!this.node.active) this.node.active = true;
        this.node_exit.active = !isShow;
        this.node_back.active = isShow;
    },
    //设置名字
    _setName: function _setName(name) {
        if (!name) name = '';
        this.label_name.string = G_TOOL.getNameLimit(name, 12);
    },

    onDestroy: function onDestroy() {
        GG.Listener.delListen(G_TYPE.globalListener.playerGold, this._listenerName);
    }

});

cc._RF.pop();