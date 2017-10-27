"use strict";
cc._RF.push(module, '0779d1B55VI+5GfT/GEwecN', 'Obj_txtTip');
// Script/Objects/Obj_txtTip.js

'use strict';

//显示在屏幕中央的文字提示

//var dataObj = {
//    tipIndex : 1,
//    showStr : '',
//    formatStr : '',
//    showPos : cc.p(0,0),
//    retainTime : 5,
//    isCountDown : false,
//    isRepeat : false,
//    callBack : 'target'
//}

cc.Class({
    extends: cc.Component,
    properties: {
        _maxCountDown: null, //最大倒计时
        _curCountDown: null, //剩余时间
        _callBack: null, //倒计时结束后的回调
        _curBaseStr: null, //显示的文字基础
        _tipIndex: null, //tip索引
        _tipMgr: null, //管理器
        _widthRate: null,
        _hideFunc: null,

        node_centerLabel: cc.Node },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },

    _init: function _init() {
        this._mainLabel = this.node_centerLabel.getComponent(cc.Label);
        this._defaultTime = G_Config_common.time_tipDefault;
    },

    bindMgr: function bindMgr(mgr) {
        this._tipMgr = mgr;
    },

    //==========================================

    showTip: function showTip(dataObj) {
        //this.forceEnd();
        var retainTime;
        if (dataObj.tipIndex) {
            var table = G_DATA.getChinese(dataObj.tipIndex);
            if (table) {
                this._curBaseStr = table.content;
                retainTime = table.retainTime;
            }
        }
        this.node.active = true;
        var pos = dataObj.showPos;
        if (!this._initPos) this._initPos = this.node.position;
        if (!pos) pos = this._initPos;
        this.node.position = pos;
        this.showByOpacity();

        if (dataObj.showStr) this._curBaseStr = dataObj.showStr;
        if (!isNaN(dataObj.retainTime)) {
            retainTime = Math.max(dataObj.retainTime, 0);
        }
        this._callBack = dataObj.callBack;
        var formatStr = dataObj.formatStr;
        this._tipIndex = dataObj.tipIndex;

        if (dataObj.isCountDown) {
            if (!retainTime) {
                this._hideTip();
                if (this._callBack) {
                    this._callBack();
                    this._callBack = null;
                }
                return;
            }
            this._addCountDown(retainTime);
            this._resetBackground(G_TOOL.formatStr(this._curBaseStr, 123));
        } else {
            if (!retainTime) retainTime = this._defaultTime;
            this._showTxt();
            this._resetBackground(G_TOOL.formatStr(this._curBaseStr, formatStr));
            var act1 = cc.fadeOut(retainTime);
            this.node.runAction(cc.sequence(act1, cc.callFunc(this._clearTxtTip, this)));
        }
    },
    //中途修改显示文字
    forceChangeStr: function forceChangeStr(changeStr) {
        this._curBaseStr = changeStr;
        this._resetBackground(G_TOOL.formatStr(this._curBaseStr, ''));
    },
    //中途修改显示倒计时的数值
    forceChangeCountDown: function forceChangeCountDown(num) {
        this._maxCountDown = num;
        this._curCountDown = this._maxCountDown;
        this._showTxt(this._curCountDown);
    },
    //强制结束倒计时
    forceEnd: function forceEnd() {
        if (this._curCountDown) this.unschedule(this.let_update);
        this._curCountDown = 0;
        this._callBack = null;
        this._hideTip();
    },
    //重置显示内容和时间
    resetTxtAndTime: function resetTxtAndTime(content, time) {
        if (!time) time = this._defaultTime;
        this.node.stopAllActions();

        this._curBaseStr = content;
        this._showTxt();
        this._resetBackground(G_TOOL.formatStr(this._curBaseStr, ''));
        var act1 = cc.fadeOut(time);
        this.node.runAction(cc.sequence(act1, cc.callFunc(this._clearTxtTip, this)));
    },

    showByOpacity: function showByOpacity() {
        this.node.opacity = 255;
    },
    hideByOpacity: function hideByOpacity() {
        this.node.opacity = 0;
    },

    _showTxt: function _showTxt(formatStr) {
        if (!formatStr) this._mainLabel.string = this._curBaseStr;else this._mainLabel.string = G_TOOL.formatStr(this._curBaseStr, formatStr);
    },

    _resetBackground: function _resetBackground(str) {
        if (!this._widthRate) this._widthRate = this.node.width / 13;
        this.node.width = str.length * this._widthRate;
    },

    _addCountDown: function _addCountDown(maxTime) {
        if (this._curCountDown) this._countDownEnd();
        this._maxCountDown = maxTime;
        this._curCountDown = this._maxCountDown;
        this._interval = 1.0;
        this.schedule(this.let_update, this._interval);
        this._showTxt(this._curCountDown);
    },

    _countDownEnd: function _countDownEnd() {
        this.unschedule(this.let_update);
        this._clearTxtTip();
    },
    //frame
    let_update: function let_update(dt) {
        if (this._curCountDown > 0) {
            this._curCountDown -= 1;
            this._showTxt(this._curCountDown);
            if (this._curCountDown <= 0) {
                //倒计时结束
                this._countDownEnd();
            }
        }
    },

    _clearTxtTip: function _clearTxtTip() {
        if (this._callBack) {
            this._callBack();
            this._callBack = null;
        }
        this._hideTip();
    },

    //==========================================

    //绑定消失函数
    bindHideFunc: function bindHideFunc(func) {
        this._hideFunc = func;
    },

    _hideTip: function _hideTip() {
        if (this._tipMgr) this._tipMgr.oneTipHide(this.node);
        this.node.active = false;
        if (this._hideFunc) {
            this._hideFunc();
            this._hideFunc = null;
        }
    },

    //是否已经在显示了
    getIsShow: function getIsShow() {
        return this.node.active;
    },
    setIsShow: function setIsShow(isShow) {
        this.node.active = isShow;
    },

    //获取该tip的表格索引
    getTipIndex: function getTipIndex() {
        return this._tipIndex;
    },

    onDestroy: function onDestroy() {
        if (this._curCountDown) this.unschedule(this.let_update);
    }

});

cc._RF.pop();