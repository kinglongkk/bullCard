"use strict";
cc._RF.push(module, 'cd62eHkgCZHfo5RnyU+f4P8', 'BaseUI');
// Script/Views/BaseUI.js

'use strict';

//所有显示的UI的父类

cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _isRelease: null, //是否在关闭的时候释放
        _uiName: null },

    // use this for initialization
    onLoad: function onLoad() {},

    showLayer: function showLayer() {
        this.node.active = true;
        this.addTouchLimit();
    },
    hideLayer: function hideLayer() {
        this.cancelTouchLimit();
        if (this._isRelease) this.node.destroy();else this.node.active = false;
    },

    addTouchLimit: function addTouchLimit() {
        var comp = this._getTouchLimitComp();
        if (comp) comp.addTouchLimit();
    },
    cancelTouchLimit: function cancelTouchLimit() {
        var comp = this._getTouchLimitComp();
        if (comp) comp.cancelTouchLimit();
    },
    _getTouchLimitComp: function _getTouchLimitComp() {
        if (!this._touchLimitComp) {
            this._touchLimitComp = this.node.parent.getComponent('Obj_touchLimit');
        }
        return this._touchLimitComp;
    },

    setIsRelease: function setIsRelease(isRelease) {
        this._isRelease = isRelease;
    },
    setUIName: function setUIName(uiName) {
        this._uiName = uiName;
    },
    getUIName: function getUIName() {
        return this._uiName;
    }

});

cc._RF.pop();