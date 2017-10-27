//點擊確認的界面

cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _callFunc : null,                                           //点击确定后的回调
        _callFunc2 : null,                                           //点击取消后的回调
        _registerFunc : null,                                       //额外注册的回调，关于关闭和开启
        _isPlayEffect : null,                                       //是否正在播放特效

        label_center : cc.Label,
        node_left : cc.Node,
        node_right : cc.Node,
        node_center : cc.Node,                                        //单选的确定按钮
        node_grayLayer : cc.Node,                                        //单选的确定按钮
        //node_continueImg : cc.Node,
    },

    // use this for initialization1
    onLoad: function () {
        this._showTime = G_Config_common.time_confirmTipShow/2;
        this._maxScale = G_Config_common.scale_confirmTipMax;
        this._touchCompName = 'Obj_touchLimit';
        this._isPlayEffect = false;
        //this._formatStr = '<b>%s<b/>';
        //this._label_left = this.node_left.children[0].getComponent(cc.Label);
        //this._label_right = this.node_right.children[0].getComponent(cc.Label);
    },

    //显示的内容多选
    showContent : function (contentStr, callFunc, callFunc2) {
        this._callFunc = callFunc;
        this._callFunc2 = callFunc2;
        this.node.active = true;
        this._showOneBtn(false);
        this._setContent(contentStr);

        this._showEffect(true);
        this.node_grayLayer.getComponent(this._touchCompName).addTouchLimit();
        //注册监听
        if(this._registerFunc){
            this._registerFunc(true);
        }
    },

    //显示的内容单选
    showContentEx : function (contentStr, callFunc) {
        this._callFunc = callFunc;
        this.node.active = true;
        this._showOneBtn(true);
        this._setContent(contentStr);

        this._showEffect(true);
        this.node_grayLayer.getComponent(this._touchCompName).addTouchLimit();
        //注册监听
        if(this._registerFunc){
            this._registerFunc(true);
        }
    },

    Btn_touchLeft : function (event) {
        if(this._isPlayEffect) return;
        this._callFunc2 = null;
        this._showEffect(false);
    },
    Btn_touchRight : function (event) {
        if(this._isPlayEffect) return;
        this._callFunc = null;
        this._showEffect(false);
    },
    Btn_touchCenter : function (event) {
        if(this._isPlayEffect) return;
        this._showEffect(false);
    },

    //显示界面的特效  isShow ： 判定当前是显示还是关闭
    _showEffect : function (isShow) {
        this._isPlayEffect = true;
        var effectNode = this.node_center.parent;
        effectNode.stopAllActions();
        if(isShow){
            // show tip
            effectNode.scale = 0;

            var act1 = cc.scaleTo(this._showTime, this._maxScale);
            var act2 = cc.scaleTo(this._showTime/2, 1);
            effectNode.runAction(cc.sequence(act1, act2, cc.callFunc(function () {
                this._isPlayEffect = false;
            }, this)));
            // this.playFrameEffect(effectNode);
        }else{
            //close tip
            effectNode.scale = 1;

            var act1 = cc.scaleTo(this._showTime/2, this._maxScale);
            var act2 = cc.scaleTo(this._showTime, 0.5);
            effectNode.runAction(cc.sequence(act1, act2, cc.callFunc(function () {
                this._isPlayEffect = false;
                this._removePage();
            }, this)));
        }
    },
    playFrameEffect : function (effectNode) {
        var time = 0.2;

        effectNode.scale = 0;
        effectNode.stopAllActions();
        if(effectNode._firstX === undefined) effectNode._firstX = effectNode.x;
        if(effectNode._firstY === undefined) effectNode._firstY = effectNode.y;

        var act1 = cc.moveBy(time, -cc.visibleRect.width*0.2, 0);
        var act2 = cc.scaleTo(time, 0.5);
        var act2_1 = cc.rotateTo(time, 0, 90);
        var act3 = cc.spawn(act1, act2, act2_1);
        var act4 = cc.sequence(act3, cc.callFunc(this._playFrameEffect2, this))
        effectNode.runAction(act4);
    },
    _playFrameEffect2 : function (target) {
        var time = 0.5;

        target.rotationY = -90;
        var act1 = cc.moveTo(time, target._firstX, target._firstY);
        var act2 = cc.scaleTo(time, 1);
        // var act2_1 = cc.skewTo(time, 0, 0);
        var act2_1 = cc.rotateTo(time, 0, 0);
        var act3 = cc.spawn(act1, act2, act2_1);
        var act4 = cc.sequence(act3, cc.callFunc(this.frameEffectEnd, this))
        target.runAction(act4);
    },
    frameEffectEnd : function () {
        this._isPlayEffect = false;
    },

    //显示弹窗的内容
    _setContent : function (str) {
        //this.label_center.string = G_TOOL.formatStr(this._formatStr, str);
        //遇到逗号需要分行
        str = str.replace(',','\n');
        str = str.replace('，','\n');
        this.label_center.string = str;
    },

    //是否只显示一个按钮
    _showOneBtn : function (isOne) {
        this.node_right.active = !isOne;
        this.node_left.active = !isOne;
        this.node_center.active = isOne;
    },

    //移除界面
    _removePage : function () {
        //注册监听
        if(this._registerFunc){
            this._registerFunc(false);
        }
        if(this._callFunc){
            this._callFunc();
            this._callFunc = null;
        }
        if(this._callFunc2){
            this._callFunc2();
            this._callFunc2 = null;
        }
        this.node_grayLayer.getComponent(this._touchCompName).cancelTouchLimit();
        this.node.active = false;
    },

    //关闭界面
    closePage : function () {
        this._callFunc = null;
        this._callFunc2 = null;
        this._showEffect(false);
    },

    //监听确认框的关闭和开启
    registerFunc : function (func, isRegister) {
        if(isRegister) this._registerFunc = func;
        else this._registerFunc = null;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
