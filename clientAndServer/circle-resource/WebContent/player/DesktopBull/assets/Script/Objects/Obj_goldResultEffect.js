//显示在头像上面的金币增减的漂浮特效

cc.Class({
    extends: cc.Component,
    _callFunc : null,                               //金币特效结束后回调
    properties: {
        node_addGold : cc.Node,
        node_lostGold : cc.Node,
    },

    // use this for initialization
    onLoad: function () {
        this._reloadLabel();
    },

    _reloadLabel : function () {
        this._label_add = this.node_addGold.getComponentInChildren(cc.Label);
        this._label_lost = this.node_lostGold.getComponentInChildren(cc.Label);
    },

    showGrabGold : function (count) {
        if(!count) return;
        if(!this._label_add) this._reloadLabel();

        if(count > 0){
            this.node_addGold.active = true;
            this.node_lostGold.active = false;
            count = '+'+count;
            this._label_add.string = count;
        }else{
            //count = '-'+count;
            this.node_addGold.active = false;
            this.node_lostGold.active = true;
            this._label_lost.string = count;
        }
    },

    showEffect : function (value, startPos, targetY, callFunc) {
        this.node.active = true;
        this._callFunc = callFunc;
        if(value > 0){
            this.node_addGold.active = true;
            this.node_lostGold.active = false;
            this._label_add.string = '+'+value;
            this._showGoldAddEffect(startPos, targetY);
        }else if(value < 0){
            this.node_lostGold.active = true;
            this.node_addGold.active = false;
            this._label_lost.string = value;
            this._showGoldLostEffect(startPos, targetY);
        }else{
            //value == 0
            this.node_addGold.active = true;
            this.node_lostGold.active = false;
            this._label_add.string = value;
            this._showGoldAddEffect(startPos, targetY);
        }
    },

    //金币显示的移动效果
    _showGoldAddEffect : function (startPos, targetY) {
        this.node.zIndex = -1;
        this.node.position = startPos;
        this.node.scale = 0;
        this.node.opacity = 0;

        var time = G_Config_classic.list_goldAddFly[0];
        var act1 = cc.moveTo(time, 0, targetY).easing(cc.easeOut(3.0));
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.fadeTo(time, 255);

        var act5 = cc.callFunc(this._addEffectMid, this, startPos);
        this.node.runAction(cc.sequence(cc.spawn(act1, act2, act3), act5));
    },
    _addEffectMid : function (target, startPos) {
        this.node.zIndex = 1;
        var time = G_Config_classic.list_goldAddFly[1];
        var act1 = cc.delayTime(G_Config_classic.list_goldAddFly[2]);
        var act2 = cc.fadeTo(time, 100);
        var act3 = cc.moveTo(time, startPos).easing(cc.easeInOut(3.0));
        var act4 = cc.scaleTo(time, 0.5);
        var act5 = cc.callFunc(this._effectEnd, this);
        this.node.runAction(cc.sequence(act1, cc.spawn(act2, act3, act4), act5));
    },

    _showGoldLostEffect : function (startPos, targetY) {
        this.node.zIndex = -1;
        this.node.position = startPos;
        this.node.scale = 0;
        this.node.opacity = 255;

        var time = G_Config_classic.list_goldLostFly[0];
        var act1 = cc.moveTo(time, 0, targetY).easing(cc.easeOut(3.0));
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.delayTime(G_Config_classic.list_goldLostFly[1]);
        var act4 = cc.fadeOut(G_Config_classic.list_goldLostFly[2]);
        var act5 = cc.callFunc(this._effectEnd, this);
        this.node.runAction(cc.sequence(cc.spawn(act1, act2), act3, act4, act5));
    },

    _effectEnd : function () {
        this.hideGold();
        if(this._callFunc) this._callFunc();
    },

    //隐藏
    hideGold : function () {
        this.node.active = false;
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
