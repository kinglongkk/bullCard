//简单基类，实现按钮的注册与释放

cc.Class({
    extends: cc.Component,
    _list_registerBtn : null,                   //注册事件的按钮集合
    properties: {

    },

    // use this for initialization
    onLoad: function () {

    },

    //注册按钮点击效果
    registerButton : function (node, callBack, target, userData, isNoScale) {
        if(!node) return;
        if(!this._list_registerBtn) this._list_registerBtn = [];
        node._isTouchEnabledEx = true;
        this._list_registerBtn.push([node, callBack, target]);
        node.on(cc.Node.EventType.TOUCH_START, function (event) {
            //if(event.target._isTouchEnabledEx) event.target.scale = G_Config_classic.scale_buttonTouch;
            if(event.target._isTouchEnabledEx) {
                if(!isNoScale){
                    if(!event.target.lastScale) event.target.lastScale = event.target.scale;
                    event.target.scale = event.target.lastScale * 0.9;
                }
                GG.audioMgr.playSound(17);
            }
        }, target);
        node.on(cc.Node.EventType.TOUCH_END, function (event) {
            if(event.target.lastScale) event.target.scale = event.target.lastScale
            if(event.target._isTouchEnabledEx) callBack.call(target, event, userData);
        }, target);
        node.on(cc.Node.EventType.TOUCH_CANCEL, function (event) {
            if(event.target.lastScale) event.target.scale = event.target.lastScale
        }, target);
    },
    //设置按钮是否可用
    setBtnEnable : function (node, isEnable, isNoGray) {
        if(isEnable){
            node.opacity = 255;
        }else{
            if(!isNoGray) node.opacity = 255 * G_Config_common.btnGrayValue;
        }
        var btn = node.getComponent(cc.Button);
        if(btn)btn.enabled = isEnable;
        node._isTouchEnabledEx = isEnable;
    },

    //获取是否可用
    getIsBtnEnable : function (node) {
        return node._isTouchEnabledEx
    },

    //释放事件,这里注册的才能在这里释放
    releaseButtonEvent : function () {
        if(!this._list_registerBtn) return;
        var parm1,parm2,parm3;
        for(var i = 0; i < this._list_registerBtn.length; i ++){
            parm1 = this._list_registerBtn[i][0];
            parm2 = this._list_registerBtn[i][1];
            parm3 = this._list_registerBtn[i][2];
            parm1.off(cc.Node.EventType.TOUCH_START, parm2, parm3);
            parm1.off(cc.Node.EventType.TOUCH_END, parm2, parm3);
            parm1.off(cc.Node.EventType.TOUCH_CANCEL, parm2, parm3);
        }
        this._list_registerBtn = null;
    },

    onDestroy : function () {
        this.releaseButtonEvent();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
