//使用的卡牌对象

cc.Class({
    extends: cc.Component,
    _isMyself : null,                               //是否是玩家自己
    _ownPlayer : null,                              //属于哪个玩家
    _isChoose : null,                               //是否被选中
    _callFunc : null,                                //开牌动作的回调
    _pokerValue : null,                             //卡牌的值
    _pokerType : null,                              //卡牌的类型
    _label_value : null,                            //显示牌值的label
    properties: {
        node_pokerPlus : cc.Node,
        node_pokerNegative : cc.Node,
        node_pokerValue : cc.Node,
        node_minIcon : cc.Node,
        node_maxIcon : cc.Node,
        node_specialIcon : cc.Node,
        frame_pokerUI : cc.SpriteAtlas,             //卡牌基础显示图片集
        font_black : cc.Font,                       //黑色字体
        font_red : cc.Font,                         //红色字体
    },

    // use this for initialization
    onLoad: function () {
        this._init();

        //this.setPokerInfo(G_TYPE.pokerType.redHeart, 12)
    },

    _init : function () {
        this._isChoose = false;

        this._label_value = this.node_pokerValue.getComponent(cc.Label);
        this._sprite_min = this.node_minIcon.getComponent(cc.Sprite);
        this._sprite_max = this.node_maxIcon.getComponent(cc.Sprite);
        this._openPokerTime = G_Config_common.time_pokerOpen;
        this._classic_otherScale = G_Config_classic.scale_noMyPoker;
        this._classic_flyTime = G_Config_classic.time_pokerFly;
        this._classic_moveUp = G_Config_classic.num_pokerUp;

        this.node_pokerPlus.active = true;
    },

    bindPlayer : function (player) {
        this._ownPlayer = player;
    },

    //显示卡牌的背面
    showPokerNegative : function () {
        this.node_pokerPlus.y = 0;
        this.node_pokerPlus.scale = 1;
        //this.node_pokerPlus.active = true;

        this.node_pokerNegative.active = true;
        this.node_pokerNegative.scale = 1;
    },
    showPokerPlus : function () {
        //this.node_pokerPlus.active = true;
        if (this.node_pokerNegative) this.node_pokerNegative.active = false;
    },


    //=========================================

    //移动卡牌
    flyPoker : function (startPos, targetPos, isMyself) {
        this.onClear();
        this.showPokerNegative();
        this._isMyself = isMyself;
        if(!this._isMyself){
            //不是属于当前玩家的卡牌
            this.node.scale = this._classic_otherScale;
        }

        this.node.position = startPos;
        var curPosX = targetPos.x;//easeQuarticActionOut
        var act1 = cc.moveTo(this._classic_flyTime, curPosX, targetPos.y);
        var act1 = cc.moveTo(0.4, curPosX, targetPos.y);
        this.node.runAction(cc.sequence(act1, cc.callFunc(this._flyPokerEnd, this)));
    },
    _flyPokerEnd : function () {
        if(this._isMyself)this.openPoker();

        this._ownPlayer.addFlyEndPoker();
    },

    //=========================================

    //打开卡牌
    openPoker : function (callBack) {
        if(this.node_pokerNegative.active === false) return false;
        this.node_pokerNegative.active = false;
        this.node_pokerPlus.active = false;
        this._callFunc = callBack;
        this.showPokerNegative();

        var rotate1 = cc.scaleTo(this._openPokerTime*0.5, 0, 1);
        this.node_pokerNegative.runAction(cc.sequence(rotate1, cc.callFunc(this._openPoker_showValue, this)));
        return true
    },
    _openPoker_showValue : function () {
        this.node_pokerNegative.active = false;
        this.node_pokerPlus.active = true;
        this.node_pokerPlus.scaleX = 0;
        var rotate1 = cc.scaleTo(this._openPokerTime*0.5, 1, 1);
        this.node_pokerPlus.runAction(cc.sequence(rotate1, cc.callFunc(this._openPoker_end, this)));
    },
    _openPoker_end : function () {
        //openPoker_end-------------------
        if(this._callFunc) this._callFunc();
        this._callFunc = null;
    },

    //=========================================

    //打出卡牌结果  actionCallFunc : 卡牌移动到开牌位置后的回调
    //moveToResultPos : function (targetPosY, actionCallFunc) {
    //    this.node_pokerPlus.y = 0;
    //    var pos = cc.p(this.node.x, targetPosY);
    //    if(actionCallFunc)this.node.runAction(cc.sequence(cc.moveTo(G_Config_classic.time_pokerMoveToResult, pos), actionCallFunc));
    //    else this.node.runAction(cc.moveTo(G_Config_classic.time_pokerMoveToResult, pos));
    //},

    //=========================================

    //获取一个定制的牌面显示
    setPokerInfo : function (pokerType, pokerValue, isOpen) {
        this.node.active = true;
        this.node_maxIcon.active = true;
        if (this.node_specialIcon) this.node_specialIcon.active = false;
        this._pokerValue = pokerValue;
        this._pokerType = pokerType;
        if(isOpen) this.showPokerPlus();
        else this.showPokerNegative();

        //确定卡牌类型
        switch (pokerType){
            case G_TYPE.pokerType.block:
                //方块
                this._label_value.font = this.font_red;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blockMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blockMax);
                break;
            case G_TYPE.pokerType.blackHeart:
                //黑色桃心
                this._label_value.font = this.font_black;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blackHeartMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blackHeartMax);
                break;
            case G_TYPE.pokerType.redHeart:
                //红色桃心
                this._label_value.font = this.font_red;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.redHeartMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.redHeartMax);
                break;
            case G_TYPE.pokerType.flower:
                //梅花
                this._label_value.font = this.font_black;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.flowerMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.flowerMax);
                break;
        }
        //确定卡牌的值
        var realyValue;
        switch (pokerValue){
            case 1:
                realyValue = 'A';
                break;
            case 10:
                realyValue = '1';
                break;
            case 11:
                realyValue = 'J';
                // this.node_maxIcon.active = false;
                // this.node_specialIcon.active = true;
                // this.node_specialIcon.getComponent(cc.Sprite).spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.value_J);
                break;
            case 12:
                realyValue = 'Q';
                // this.node_maxIcon.active = false;
                // this.node_specialIcon.active = true;
                // this.node_specialIcon.getComponent(cc.Sprite).spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.value_Q);
                break;
            case 13:
                realyValue = 'K';
                // this.node_maxIcon.active = false;
                // this.node_specialIcon.active = true;
                // this.node_specialIcon.getComponent(cc.Sprite).spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.value_K);
                break;
            default:
                realyValue = pokerValue;
                break;
        }

        this._label_value.string = realyValue;
    },


    //=========================================

    getPokerValue : function () {
        return this._pokerValue
    },

    getPokerType : function () {
        return this._pokerType
    },

    getPokerSize : function () {
        return this.node_pokerNegative.getContentSize();
    },

    //是否黑色卡牌
    getIsBlack : function () {
        return this.isBlackColor
    },

    //设置卡牌是否可以点击
    setPokerTouchEnable : function (isEnable) {
        if(isEnable){
            this.node_pokerPlus.on(cc.Node.EventType.TOUCH_START, this._callback_clickPoker, this);
        }else{
            this.node_pokerPlus.off(cc.Node.EventType.TOUCH_START, this._callback_clickPoker, this);
        }
    },

    //点击卡牌的回调
    _callback_clickPoker : function (event) {
        var poker = event.target;
        if(this._isChoose){
            poker.y -= this._classic_moveUp;
            this._isChoose = false;
            this._ownPlayer.touchThePokerDown();
        }else{
            var value = this.getPokerValue();
            if(value > 10) value = 10;
            var isOk = this._ownPlayer.touchThePokerUp(value);
            if(isOk){
                poker.y += this._classic_moveUp;
                this._isChoose = true;
            }
        }
    },

    onClear : function () {
        this._isChoose = false;
        this.node.scale = 1;
    },

    clearAllActions : function () {
        this.node_pokerNegative.stopAllActions();
        this.node_pokerPlus.stopAllActions();
        this.node.stopAllActions();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
    
    onDestroy : function () {
        //this.setPokerTouchEnable(false);
    }
});
