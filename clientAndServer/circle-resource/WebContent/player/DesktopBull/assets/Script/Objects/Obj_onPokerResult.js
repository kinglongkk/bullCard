//卡牌结果的显示

cc.Class({
    extends: cc.Component,

    properties: {
        node_upImage : cc.Node,
        node_downImage : cc.Node,
        Atlas_onPokerResult : cc.SpriteAtlas,
    },

    // use this for initialization
    onLoad: function () {

    },

    _initSprite : function () {
        this._upSprite = this.node_upImage.getComponent(cc.Sprite);
        this._downSprite = this.node_downImage.getComponent(cc.Sprite);
        this._dontFrame = this.Atlas_onPokerResult.getSpriteFrame(G_RES_URL.atlas_pokerResult.dont);
    },

    //显示牌型计算完成, 显示完成两个字
    showCalculateSuccess : function () {
        this.node.active = true;
        if(!this._upSprite)this._initSprite();
        this.node_downImage.active = false;
        this._upSprite.spriteFrame = this._dontFrame;
    },

    //显示牌型
    showPokerResultValue : function (pokerValue, isOverAni) {
        this.node.active = true;
        if(!this._upSprite)this._initSprite();

        if(pokerValue <= 0){
            //没牛
            this.node_downImage.active = false;
            pokerValue = 'no';
            if(this._callFunc){
                this._callFunc();
                this._callFunc = null;
            }
        }else{
            this.node_downImage.active = true;
            if(!isOverAni) this._openResultEffect();
            else{
                if(this._callFunc){
                    this._callFunc();
                    this._callFunc = null;
                }
            }
        }

        var toFrame = this.Atlas_onPokerResult.getSpriteFrame(G_RES_URL.atlas_pokerResult.resultImgName+pokerValue);
        this._upSprite.spriteFrame = toFrame;
    },
    _openResultEffect : function () {
        this.node_upImage.scale = 0;
        this.node_downImage.scale = 0;

        var time = G_Config_classic.time_pokerResultShow;
        var act1 = cc.sequence(cc.scaleTo(time, G_Config_classic.scale_pokerResultShow), cc.scaleTo(time/2, 1.0));
        var act2 = cc.scaleTo(time, 1.0);
        this.node_downImage.runAction(act1);
        this.node_upImage.runAction(cc.sequence(act2, cc.callFunc(function () {
            if(this._callFunc){
                this._callFunc();
                this._callFunc = null;
            }
        }, this)));
    },

    setTruePos : function (pos) {
        this.node.position = pos;
        this.hideOnPokerResult();
    },

    hideOnPokerResult : function () {
        this.node.active = false;
    },

    getIsShow : function () {
        return this.node.active
    },

    showResult : function (resultPos, resultValue, isOverAni, callFunc) {
        this._callFunc = callFunc;
        if(isOverAni){
            //跳过动画
            this.node.position = resultPos;
            this.showPokerResultValue(resultValue, true);
        }else{
            this.setTruePos(resultPos);
            this.showPokerResultValue(resultValue);
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
