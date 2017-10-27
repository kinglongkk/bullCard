/**
 * Created by lenovo on 2016/12/27.
 */

//最上层的点击
cc.Class({
    extends: cc.Component,

    properties: {
        _touchEffect : null,                        //点击特效

        frame_touchEffect : cc.SpriteFrame,
    },

    // use this for initialization
    onLoad: function () {
        this.addTouchListener();
    },

    //添加点击事件
    addTouchListener : function () {
        if(!this.frame_touchEffect) return;
        this.node.on(cc.Node.EventType.TOUCH_START, this.touchCall, this);
    },
    touchCall : function (event) {
        var pos = cc.p(event.getLocationX(), event.getLocationY());
        this._showTouchEff(pos);
    },

    //显示点击特效
    _showTouchEff : function (pos) {
        pos = this.node.convertToNodeSpace(pos);
        if(!this._touchEffect){
            this._touchEffect = new cc.Node();
            this._touchEffect.parent = this.node;

            var node1 = new cc.Node();
            this._touchEffect.addChild(node1, 0, 1);
            var sp1 = node1.addComponent(cc.Sprite).spriteFrame = this.frame_touchEffect;

            var node2 = new cc.Node();
            this._touchEffect.addChild(node2, 0, 2);
            var sp1 = node2.addComponent(cc.Sprite).spriteFrame = this.frame_touchEffect;
        }
        this._touchEffect.active = true;
        this._touchEffect.position = pos;
        var node1 = this._touchEffect.getChildByTag(1);
        var node2 = this._touchEffect.getChildByTag(2);
        node1.stopAllActions();
        node2.stopAllActions();

        node1.scale = 0.1;
        node1.opacity = 255;

        var time = 0.5;
        var act1 = cc.scaleTo(time, 1);
        var act2 = cc.fadeTo(time, 70);
        node1.runAction(cc.sequence(cc.spawn(act1, act2), cc.callFunc(this._effCall, this)));

        node2.scale = 0.1;
        node2.opacity = 255;
        act1 = cc.delayTime(time*0.2);
        act2 = cc.scaleTo(time*0.6, 0.7);
        var act3 = cc.fadeTo(0, 0);
        node2.runAction(cc.sequence(act1, act2, act3))
    },

    _effCall : function () {
        if(this._touchEffect){
            this._touchEffect.active = false;
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
