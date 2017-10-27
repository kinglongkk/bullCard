//所有显示的UI的父类

cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _isRelease : null,                                                                                                //是否在关闭的时候释放
        _uiName : null,                                                                                                    //ui名字
    },

    // use this for initialization
    onLoad: function () {

    },

    showLayer : function () {
        this.node.active = true;
        this.addTouchLimit();
    },
    hideLayer : function () {
        this.cancelTouchLimit();
        if(this._isRelease) this.node.destroy();
        else this.node.active = false;
    },

    addTouchLimit : function () {
        var comp = this._getTouchLimitComp();
        if(comp) comp.addTouchLimit();
    },
    cancelTouchLimit : function () {
        var comp = this._getTouchLimitComp();
        if(comp) comp.cancelTouchLimit();
    },
    _getTouchLimitComp : function () {
        if(!this._touchLimitComp) {
            this._touchLimitComp = this.node.parent.getComponent('Obj_touchLimit')
        }
        return this._touchLimitComp
    },

    setIsRelease : function (isRelease) {
        this._isRelease = isRelease;
    },
    setUIName : function (uiName) {
        this._uiName = uiName;
    },
    getUIName : function () {
        return this._uiName
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
