"use strict";
cc._RF.push(module, '0f796fh/rJIb4s54HJEIHLg', 'Obj_coinEffect');
// Script/Objects/Obj_coinEffect.js

'use strict';

//游戏大厅金币图案特效
cc.Class({
    extends: cc.Component,

    properties: {
        _coinAni: null },

    // use this for initialization
    onLoad: function onLoad() {
        this._initPlayCoinAni();
    },
    //播放金币特效动画
    _initPlayCoinAni: function _initPlayCoinAni() {
        if (!this._coinAni) {
            this._coinAni = this.node.getComponent(dragonBones.ArmatureDisplay);
            this._coinAni.addEventListener(dragonBones.EventObject.COMPLETE, this._playCoinsEffectAni, this);
        }
        this._coinAni.playAnimation('coins', 1);
    },

    //每隔3秒循环播放金币特效
    _playCoinsEffectAni: function _playCoinsEffectAni() {
        this.node.runAction(cc.sequence(cc.delayTime(3), cc.callFunc(function () {
            this._initPlayCoinAni();
        }, this)));
    },

    onDestroy: function onDestroy() {
        this.node.stopAllActions();
    }

});

cc._RF.pop();