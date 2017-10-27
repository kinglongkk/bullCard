//滚动数字的通用脚本

var dataObj = {
    startNum : 0,
    targetNum : 1,
    label : null,
    formatStr : '',
    callFunc : null
}

cc.Class({
    extends: cc.Component,

    properties: {
        _curLabel : null,                                                       //当前使用的滚动label
        _curNum : null,                                                          //当前的滚动值
        _maxNum : null,                                                          //最大的滚动值
        _formatStr : null,                                                       //需要组合显示的字符串
        _callFunc : null,                                                        //滚动结束后的回调

        _isStartScroll : null,                                                   //是否开始滚动
        _interval : null,                                                         //滚动间隔
        _maxScrollTimes :  null,                                                 //最大的滚动次数
    },

    // use this for initialization
    onLoad: function () {
        this._maxScrollTimes = 50;
        this._isStartScroll = false;
    },

    //开始滚动数值
    scrollLabel : function (dataObj) {
        if(!dataObj) return;
        this._isStartScroll = false;
        this._curNum = dataObj.startNum;
        this._maxNum = dataObj.targetNum;
        this._curLabel = dataObj.label;
        this._formatStr = dataObj.formatStr+'%s';
        this._callFunc = dataObj.callFunc;
        this._initScrollData();
    },

    _initScrollData : function () {
        this._interval = Math.max(Math.floor((this._maxNum - this._curNum)/this._maxScrollTimes), 0);
        this._isStartScroll = true;
    },

    _change : function () {
        this._curNum += this._interval;
        if(this._curNum >= this._maxNum){
            //已经滚动到最大值
            this._curNum = this._maxNum;
            this._isStartScroll = false;
        }
        this._showValue();
        if(!this._isStartScroll){
            this._clearData();
            if(this._callFunc){
                this._callFunc();
                this._callFunc = null;
            }
        }
    },

    _showValue : function () {
        var str = G_TOOL.formatStr(this._formatStr, G_TOOL.changeMoney(this._curNum));
        this._curLabel.string = str;
    },

    _clearData : function () {
        this._curNum = 0;
        this._maxNum = 0;
        this._curLabel = null;
        this._formatStr = '';
        this._interval = 0;
    },

    //强制结束,显示最终结果
    forceEnd : function () {
        this._callFunc = null;
        this._curNum = this._maxNum;
    },

    // called every frame, uncomment this function to activate update callback
     update: function (dt) {
         if(!this._isStartScroll) return;
         this._change();
     },
});
