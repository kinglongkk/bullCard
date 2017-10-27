//左上角的菜单按钮


cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _isOpen : null,                                                                                                        //是否打开菜单
        _isPlayingEff : null,                                                                                                 //是否正在播放特效
        _exitFunc : null,                                                                                                     //退出回调
        _changeFunc : null,                                                                                                   //换桌回调

        node_dirImg : {
            default : null,
            type : cc.Node,
            displayName : '菜单方向容器'
        },
        node_btnContainer : {
            default : null,
            type : cc.Node,
            displayName : '按钮容器'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._isOpen = false;
        this._scaleTime = 0.2;
        this._waitTime = 3.5;

        var exitBtn = this.node_btnContainer.children[0];//退出
        var changeBtn = this.node_btnContainer.children[1];//换桌
        this.registerButton(this.node_dirImg, this.OnClick_menu, this);
        this.registerButton(exitBtn, this.OnClick_exit, this);
        this.registerButton(changeBtn, this.OnClick_change, this);

        this.setBtnEnable(changeBtn, false)
    },

    //点击菜单
    OnClick_menu : function (target) {
        this._isOpen = !this._isOpen;
        this._changeDirImg(this._isOpen);
        this._showOpenEffect();
    },
    //点击退出
    OnClick_exit : function (target) {
        this.OnClick_menu();
        if(this._exitFunc) this._exitFunc();
    },
    //点击换桌
    OnClick_change : function (target) {
        this.OnClick_menu();
        if(this._changeFunc) this._changeFunc();
    },

    //设置退出函数
    bindFunc : function (exitFunc, changeFunc) {
        this._exitFunc = exitFunc;
        this._changeFunc = changeFunc;
    },

    //==================================效果显示

    //箭头方向
    _changeDirImg : function (isOpen) {
        this.node_dirImg.children[0].active = !isOpen;
        this.node_dirImg.children[1].active = isOpen;
    },

    //显示打开菜单特效
    _showOpenEffect : function () {
        if(!this._isOpen){
            //已经在播放动画了
            this._showCloseEffect();
            return
        }
        this.node_btnContainer.stopAllActions();
        this.node_btnContainer.active = true;
        this.node_btnContainer.scaleY = 0;

        var time = this._scaleTime;
        var scaleAct = cc.scaleTo(time, 1, 1);
        this.node_btnContainer.runAction(cc.sequence(scaleAct, cc.callFunc(this._addWaitAct, this)));
    },
    //增加等待动作
    _addWaitAct : function () {
        this.node_btnContainer.stopAllActions();
        var time1 = this._waitTime;
        this.node_btnContainer.runAction(cc.sequence(cc.delayTime(time1), cc.callFunc(this.OnClick_menu, this)));
    },
    //显示收起菜单的特效
    _showCloseEffect : function () {
        this.node_btnContainer.stopAllActions();
        var time = this._scaleTime/2;
        var scaleAct = cc.scaleTo(time, 1, 0);
        this.node_btnContainer.runAction(scaleAct);
    },

    resetShow : function () {
        this._changeDirImg(false);
        this.node_btnContainer.active = false;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
