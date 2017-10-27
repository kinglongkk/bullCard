"use strict";
cc._RF.push(module, 'f34b9gL6nxOYr0lRtDl0NMD', 'Obj_dealerWordEffect');
// Script/Objects/Obj_dealerWordEffect.js

'use strict';

//庄家庄字特效
cc.Class({
    extends: cc.Component,

    properties: {
        _dealerWordAni: null },

    // use this for initialization
    onLoad: function onLoad() {},

    //播放庄字特效动画
    playDealerWordAni: function playDealerWordAni() {
        if (!this._dealerWordAni) {
            this._dealerWordAni = this.node.getComponent(dragonBones.ArmatureDisplay);
            this._dealerWordAni.addEventListener(dragonBones.EventObject.COMPLETE, this._playDealerWordEffectAni, this);
        }
        this.node.active = true;
        this._dealerWordAni.playAnimation('zhuang_mark_2', 1);
    },

    //循环播放的庄字特效
    _playDealerWordEffectAni: function _playDealerWordEffectAni() {
        this._dealerWordAni.playAnimation('zhuang_mark_1', 0);
    },

    //清理庄字特效动画
    clearDealerWordAni: function clearDealerWordAni() {
        if (this.node) this.node.active = false;
    }
});

cc._RF.pop();