"use strict";
cc._RF.push(module, '3006d3TqmhAraFdhAM3/ALl', 'Obj_dialogRecordCell');
// Script/Objects/Obj_dialogRecordCell.js

'use strict';

//玩家记录查询预制体
cc.Class({
    extends: cc.Component,

    properties: {
        label_time: {
            default: null,
            type: cc.Label,
            displayName: '对局时间'
        },
        label_roomType: {
            default: null,
            type: cc.Label,
            displayName: '房间类型'
        },
        label_roomName: {
            default: null,
            type: cc.Label,
            displayName: '房间名称'
        },
        label_bet: {
            default: null,
            type: cc.Label,
            displayName: '押注'
        },
        label_result: {
            default: null,
            type: cc.Label,
            displayName: '结果'
        },
        label_outCome: {
            default: null,
            type: cc.Label,
            displayName: '胜负'
        }

    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        this._setTime(data.confirmtime);
        this._setRoom(data.gamemodelname, data.gameroomname);
        this._setBet(data.effectiveamount);
        this._setResult(data.profitamount);
        this._setOutCome(data.profitamount);
    },

    _setTime: function _setTime(timeStr) {
        this.label_time.string = timeStr;
    },

    //房间名字为红色
    _setRoom: function _setRoom(gameModelName, gameRoomName) {
        this.label_roomType.string = gameModelName.substring(0, 2) + ":";
        this.label_roomName.string = gameRoomName;
    },

    //投注信息
    _setBet: function _setBet(betStr) {
        if (!betStr) {
            betStr = G_CHINESE.dealer; // 当庄
        }
        this.label_bet.string = betStr;
    },

    //投注结果
    _setResult: function _setResult(result) {
        this._recordFirstColor(this.label_result.node);
        var curColor;
        if (result < 0) {
            //颜色为红色
            curColor = this._getFontColor();
        } else {
            curColor = this._getFirstColor(this.label_result.node);
        }
        this.label_result.node.color = curColor;
        this.label_result.string = result;
    },

    _setOutCome: function _setOutCome(outCome) {
        this._recordFirstColor(this.label_outCome.node);
        var str, curColor;
        if (outCome < 0) {
            //颜色为红色
            curColor = this._getFontColor();
            str = G_CHINESE.loseMatch; // 负
        } else {
            curColor = this._getFirstColor(this.label_outCome.node);
            str = G_CHINESE.winMatch; // 胜
        }
        this.label_outCome.node.color = curColor;
        this.label_outCome.string = str;
    },

    //===================

    _recordFirstColor: function _recordFirstColor(node) {
        if (node._firstColor === undefined) {
            node._firstColor = node.color;
        }
    },
    _getFirstColor: function _getFirstColor(node) {
        return node._firstColor ? node._firstColor : node.color;
    },

    _getFontColor: function _getFontColor() {
        if (!this._fontsColor) {
            this._fontsColor = new cc.Color(255, 0, 0);
        }
        return this._fontsColor;
    }

});

cc._RF.pop();