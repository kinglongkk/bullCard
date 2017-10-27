//庄家庄字特效
cc.Class({
    extends: cc.Component,

    properties: {
        _dealerWordAni : null,//节点骨骼动画
    },

    // use this for initialization
    onLoad: function () {},

    //播放庄字特效动画
    playDealerWordAni : function () {
        if (!this._dealerWordAni) {
            this._dealerWordAni = this.node.getComponent(dragonBones.ArmatureDisplay);
            this._dealerWordAni.addEventListener(dragonBones.EventObject.COMPLETE, this._playDealerWordEffectAni, this);
        }
        this.node.active = true;
        this._dealerWordAni.playAnimation('zhuang_mark_2', 1);
    },

    //循环播放的庄字特效
    _playDealerWordEffectAni : function () {
        this._dealerWordAni.playAnimation('zhuang_mark_1', 0);
    },

    //清理庄字特效动画
    clearDealerWordAni : function () {
        if(this.node) this.node.active = false;
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
