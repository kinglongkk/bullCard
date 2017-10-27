//圆形进度条，用于倒计时的显示，优化的话可做成预制体

cc.Class({
    extends: cc.Component,
    _firstFrame : null,                             //第一张圆形图片
    _callFunc : null,                               //倒计时回调结束时候调用的函数
    properties: {
        node_progress : cc.Node,
        frame_timeImage1 : cc.SpriteFrame,              //第二张圆形图片
        frame_timeImage2 : cc.SpriteFrame,              //第三张圆形图片
        label_count : cc.Label,                         //绿色字体
        font_green : cc.Font,                           //绿色字体
        font_orange : cc.Font,                          //橙色字体
        font_red : cc.Font,                             //红色字体
    },

    // use this for initialization
    onLoad: function () {

    },

    _initData : function () {
        this._firstFrame = this.node_progress.getComponentInChildren(cc.Sprite).spriteFrame;
        this._progressBarSprite = this.node_progress.getComponentInChildren(cc.Sprite);
        this._progressComp = this.node_progress.getComponent(cc.ProgressBar);
    },

    //显示圆形进度条的倒计时
    showCountDown : function (showTime, callFunc) {
        if(!this._firstFrame) this._initData();
        if(isNaN(showTime)) return;
        this.node.active = true;
        this.node_progress.children[0].active = true;
        this._callFunc = callFunc;
        this._runTime = 0;
        this._interval = 0.97;
        this._speedRate = 1;
        this._curTime = showTime;
        this._firstTime = Math.min(Math.ceil(this._curTime*G_Config_classic.list_centerProgressTime[0]), this._curTime-1);
        this._secondTime = Math.max(Math.floor(this._curTime*G_Config_classic.list_centerProgressTime[1]), 1);
        this._progressInterval = 1/this._curTime;

        this._curTime += 1;
        this._runTime = this._interval;
        this._clearLabels();
        this._progressComp.progress = 1;
        this.schedule(this._let_update, 0.01);
    },

    //帧函数
    _let_update : function (dt) {
        this._progressComp.progress -= this._progressInterval*dt;
        if(this._runTime >= this._interval){
            this._runTime = 0;
            this._setNumImg();
        }else this._runTime += dt;
    },
    
    //设置图片
    _setNumImg : function () {
        this._curTime -= 1;
        if(this._curTime <= this._firstTime){
            if(this._curTime <= this._secondTime){
                //  step 3
                if(this._curTime <= 0){
                    //结束倒计时
                    if(this._callFunc) this._callFunc();
                    this._callFunc = null;
                    this._endSchedule();
                }else{
                    this._progressBarSprite.spriteFrame = this.frame_timeImage2;
                    this.label_count.font = this.font_red;
                    this.label_count.string = this._curTime;
                }
            }else {
                //  step 2
                this._progressBarSprite.spriteFrame = this.frame_timeImage1;
                this.label_count.font = this.font_orange;
                this.label_count.string = this._curTime;
            }
        }else {
            //  step 1
            this._progressBarSprite.spriteFrame = this._firstFrame;
            this.label_count.font = this.font_green;
            this.label_count.string = this._curTime;
        }
    },

    _endSchedule : function () {
        this._curTime = 0;
        this._progressComp.progress = 0;
        this.unschedule(this._let_update);
        this.node.active = false;
    },

    getLeaveTime : function () {
        return this._curTime
    },

    //强制结束
    forceEnd : function () {
        var callFunc = this._callFunc;
        this._callFunc = null;
        this._curTime = 0;
        this._runTime = this._interval;
        return callFunc
    },

    _clearLabels : function () {
        this.label_count.string = '';
    },

    onDestory : function () {
        this.unschedule(this._let_update);
    },


    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
