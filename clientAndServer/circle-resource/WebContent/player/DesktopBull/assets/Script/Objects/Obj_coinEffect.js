//游戏大厅金币图案特效
cc.Class({
    extends: cc.Component,

    properties: {
        _coinAni : null,//节点骨骼动画
    },

    // use this for initialization
    onLoad: function () {
        this._initPlayCoinAni();
    },
    //播放金币特效动画
    _initPlayCoinAni : function () {
        if (!this._coinAni) {
            this._coinAni = this.node.getComponent(dragonBones.ArmatureDisplay);
            this._coinAni.addEventListener(dragonBones.EventObject.COMPLETE, this._playCoinsEffectAni, this);
        }
        this._coinAni.playAnimation('coins', 1);
    },

    //每隔3秒循环播放金币特效
    _playCoinsEffectAni : function () {
        this.node.runAction(cc.sequence(cc.delayTime(3), cc.callFunc(function () {
            this._initPlayCoinAni();
        }, this)));
    },

    onDestroy : function () {
        this.node.stopAllActions();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
