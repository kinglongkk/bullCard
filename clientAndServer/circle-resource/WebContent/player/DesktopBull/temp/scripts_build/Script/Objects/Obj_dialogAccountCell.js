"use strict";
cc._RF.push(module, 'f1326nfQM5IboIc/NP0kvBu', 'Obj_dialogAccountCell');
// Script/Objects/Obj_dialogAccountCell.js

'use strict';

//账目条目
cc.Class({
    extends: cc.Component,

    properties: {
        label_time: {
            default: null,
            type: cc.Label,
            displayName: '充值时间'
        },
        label_type: {
            default: null,
            type: cc.Label,
            displayName: '操作类型'
        },
        label_money: {
            default: null,
            type: cc.Label,
            displayName: '金额'
        },
        label_balance: {
            default: null,
            type: cc.Label,
            displayName: '余额'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        this._setTime(data.completiontime);
        this._setType(data.transactiontype);
        this._setChangeMoney(data.transactionmoney);
        this._setBalance(data.balance);
    },

    _setType: function _setType(type) {
        this._recordFirstColor(this.label_type.node);
        var curColor, strNum;
        if (type != 'DEPOSIT') {
            //颜色为红色
            curColor = this._getFontColor();;
            strNum = G_CHINESE.turnOut; //转出
        } else {
            curColor = this._getFirstColor(this.label_type.node);
            strNum = G_CHINESE.recharge; //转出
        }
        this.label_type.node.color = curColor;
        this.label_type.string = strNum; //充值
    },

    _setChangeMoney: function _setChangeMoney(money) {
        this._recordFirstColor(this.label_money.node);
        var curColor;
        if (money < 0) {
            //颜色为红色
            curColor = this._getFontColor();
        } else {
            curColor = this._getFirstColor(this.label_money.node);
        }
        this.label_money.node.color = curColor;
        this.label_money.string = money;
    },

    _setTime: function _setTime(timeStr) {
        this.label_time.string = timeStr;
    },

    _setBalance: function _setBalance(value) {
        if (!value) value = 0;
        this.label_balance.string = value;
    },

    //======================================

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