"use strict";
cc._RF.push(module, '06af7RA1NNLNZrMIRx0CFe8', 'Obj_dialogPlayerListCell');
// Script/Objects/Obj_dialogPlayerListCell.js

'use strict';

//玩家列表条目
cc.Class({
    extends: cc.Component,

    properties: {
        node_head: {
            default: null,
            type: cc.Node,
            displayName: '头像'
        },
        label_name: {
            default: null,
            type: cc.Label,
            displayName: '名字'
        },
        label_gold: {
            default: null,
            type: cc.Label,
            displayName: '金币'
        }

    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        G_TOOL.setHeadImg(this.node_head, data.avatarUrl);
        this._setPlayerName(data.nickname);
        this._setGoldValue(data.coin);
    },

    _setPlayerName: function _setPlayerName(playerName) {
        //玩家列表玩家条目名称限制10个字符
        if (!playerName) playerName = '';else playerName = G_TOOL.getNameLimit(playerName, 10);
        this.label_name.string = playerName;
    },
    _setGoldValue: function _setGoldValue(value) {
        if (!value) value = 0;else value = G_TOOL.changeMoney(value);
        this.label_gold.string = value;
    }

});

cc._RF.pop();