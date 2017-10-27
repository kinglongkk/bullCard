"use strict";
cc._RF.push(module, '28cc9CnmIZBXLGDeIMG+yuu', 'Obj_touchLimit');
// Script/Objects/Obj_touchLimit.js

"use strict";

//最上面的触摸禁用层

cc.Class({
    extends: cc.Component,

    properties: {
        _netAni: null },

    // use this for initialization
    onLoad: function onLoad() {},

    addTouchLimit: function addTouchLimit(isGray) {
        this.node.on(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },
    _touchThisLayer: function _touchThisLayer() {},
    cancelTouchLimit: function cancelTouchLimit() {
        this.node.off(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },

    //设置是否有置灰层
    setIsShowGray: function setIsShowGray(isGray) {
        if (!this._grayLayer) {}
    }

});

cc._RF.pop();