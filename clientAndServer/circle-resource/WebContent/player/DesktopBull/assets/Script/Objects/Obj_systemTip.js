//系统提示显示
cc.Class({
    extends: cc.Component,

    properties: {
        node_labelBG : cc.Node,
        label_center : cc.Label,
    },

    // use this for initialization
    onLoad: function () {
        this._firstPos = cc.p(this.node.x, this.node.y);
    },

    //显示系统提示内容
    showContent : function (str, pos, time, callBack) {
        this.node.active = true;
        this.label_center.string = str;

        if(!this._firstPos) this._firstPos = cc.p(this.node.x, this.node.y);
        if(!pos) pos = this._firstPos;
        this.node.position = pos;

        var showTime = 3;
        if(G_DATA.isNumber(time)){
            showTime = time;
        }

        if(this._countDownNum) this._forceEnd();
        //有倒计时
        this._curStr = str;
        this._callFunc = callBack;
        this._setCenterValue();
        this.node.stopAllActions();
        this.node.runAction(cc.sequence(cc.delayTime(showTime), cc.callFunc(this._hideTip, this)));
        this._setBackgroundLen(str);
    },
    hideSystemTip : function () {
        this.node.stopAllActions();
        this.node.active = false;
    },
    _forceEnd : function () {
        this._callFunc = null;
        this.node.stopAllActions();
        this._hideTip();
    },

    _hideTip : function () {
        this.node.active = false;
        if(this._callFunc) {
            this._callFunc();
            this._callFunc = null;
        }
    },

    _setCenterValue : function () {
        this.label_center.string = this._curStr;
    },
    //改变背景长度
    _setBackgroundLen : function (str) {
        if(!this._widthRate) this._widthRate = this.node_labelBG.width/14;
        if(!this.node_labelBG._firstW) this.node_labelBG._firstW = this.node_labelBG.width;

        var wordLen=0, name = str, curStr, reg = /^[\u4E00-\u9FA5]+$/;;
        for(var i =0; i < name.length; i ++){
            curStr = name[i];
            if(reg.test(curStr)) {
                wordLen += 2;
            } else {
                wordLen += 1;
            }
        }

        var targetW = wordLen * this._widthRate;
        this.node_labelBG.width = Math.max(this.node_labelBG._firstW, targetW);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
