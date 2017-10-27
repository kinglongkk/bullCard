cc.Class({
    extends: cc.Component,
    _choosePokerNum : null,                       //已经选择的卡牌数量
    properties: {
        _tip_trueResult : null,
        _callFunc : null,

        label_01 : cc.Label,
        label_02 : cc.Label,
        label_03 : cc.Label,
        label_04 : cc.Label,
    },

    // use this for initialization
    onLoad: function () {
        this._choosePokerNum = 0;
        this._list_label = [this.label_01, this.label_02, this.label_03, this.label_04];
    },

    showCalculate : function () {
        this.node.active = true;
    },

    //选中一张卡牌,超过数量时候需要做处理
    chooseOnePoker : function (chooseNum) {
        if(this._choosePokerNum >= G_Config_classic.num_maxChoose){
            //choose more
            return false;
        }
        this._list_label[this._choosePokerNum].string = chooseNum.toString();
        this._choosePokerNum += 1;
        if(this._choosePokerNum >= G_Config_classic.num_maxChoose){
            //选中了三张
            var getNum = this._getResult();
            this._list_label[this._choosePokerNum].string = getNum.toString();
        }
        return true;
    },

    //取消某张卡牌的值
    cancelOnePoker : function () {
        if(this._tip_trueResult){
            this._tip_trueResult.forceEnd();
            this._tip_trueResult = null;
        }
        this._choosePokerNum -= 1;
        this._list_label[this._choosePokerNum].string = '';
        this._list_label[G_Config_classic.num_maxChoose].string = '';
    },

    _getResult : function () {
        var getNum = 0;
        for(var i = 0; i < this._choosePokerNum; i ++){
            getNum += parseInt(this._list_label[i].string);
        }
        return getNum
    },

    _checkCalculate : function (value) {

    },
    _countDownEnd : function () {
        this._tip_trueResult = null;
        if(this._callFunc) this._callFunc();
    },

    showTrueResult : function (pokerValue, callFunc) {
        if(this._choosePokerNum < G_Config_classic.num_maxChoose) return;

        this._callFunc = callFunc;
        var value = this._getResult();
        if(value % 10 == 0){
            //calculate success
            var data1 = G_DATA.getChinese(17);
            var data2 = G_DATA.getChinese(16);
            var strList = data2.content.split(',');
            var showStr = G_TOOL.formatStr(data1.content, strList[pokerValue]);
            var dataObj = {
                showStr : showStr,
                showPos : cc.p(this.node.x, this.node.y + this.node.height),
                retainTime : data1.retainTime,
                callBack : this._countDownEnd.bind(this)
            }
            this._tip_trueResult = GG.tipsMgr.showCountDownTip(dataObj);
        }
    },
    showOverTouch : function () {
        var data = G_DATA.getChinese(5);
        var dataObj = {
            showStr : data.content,
            showPos : cc.p(this.node.x, this.node.y + this.node.height),
            retainTime : data.retainTime,
            callBack : this._showCountDown.bind(this)
        }
        GG.tipsMgr.showTxtTip(dataObj);
        this._hideCountDown();
    },
    _showCountDown : function () {
        if(this._tip_trueResult) this._tip_trueResult.showByOpacity();
    },
    _hideCountDown : function () {
        if(this._tip_trueResult) this._tip_trueResult.hideByOpacity();
    },

    hideCalculate : function () {
        if(!this.node.active) return;
        if(this._tip_trueResult) {
            this._tip_trueResult.forceEnd();
            this._tip_trueResult = null;
        }
        this.onClear();
        this.node.active = false;
    },
    onClear : function () {
        for(var i = 0; i < this._list_label.length; i ++){
            this._list_label[i].string = '';
        }
        this._choosePokerNum = 0;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
