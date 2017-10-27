"use strict";
cc._RF.push(module, '21a84l8GWhGaJ4cPQfmakvy', 'Obj_winRankHead');
// Script/Objects/Obj_winRankHead.js

'use strict';

//结算界面中胜利排行的头像


cc.Class({
    extends: cc.Component,

    properties: {
        _defaultHeadFrame: null, //显示默认的头像

        node_head: {
            default: null,
            type: cc.Node,
            displayName: '头像精灵'
        },
        label_playerName: {
            default: null,
            type: cc.Label,
            displayName: '玩家名字'
        },
        label_gold: {
            default: null,
            type: cc.Label,
            displayName: '金币数值'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        this._setPlayerName(data.name);
        this._setGoldValue(data.addGold);
        G_TOOL.setHeadImg(this.node_head, data.icon);
    },

    //设置玩家名字
    _setPlayerName: function _setPlayerName(name) {
        if (!name) name = '';
        name = G_TOOL.getNameLimit(name, 8, true);
        this.label_playerName.string = name;
    },

    _setGoldValue: function _setGoldValue(newValue) {
        this.label_gold.string = G_TOOL.changeMoney(newValue);
    },

    setIsShow: function setIsShow(isShow) {
        this.node.active = isShow;
    },
    getIsShow: function getIsShow() {
        return this.node.active;
    }

});

cc._RF.pop();