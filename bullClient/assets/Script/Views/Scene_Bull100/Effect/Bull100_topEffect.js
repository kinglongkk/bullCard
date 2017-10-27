//放在游戏最上面的特效


cc.Class({
    extends: cc.Component,

    properties: {
        _bettingCallFunc : null,                                                                //开始投注动画的回调
        _startCallFunc : null,                                                                //开始游戏动画的回调
        _resultData : null,                                                                      //结算界面中的数据

        prefab_start : {
            default : null,
            type : cc.Prefab,
            displayName : '开始游戏动画'
        },
        prefab_betting : {
            default : null,
            type : cc.Prefab,
            displayName : '开始投注动画'
        },
        prefab_win : {
            default : null,
            type : cc.Prefab,
            displayName : '胜利动画'
        },
        prefab_lose : {
            default : null,
            type : cc.Prefab,
            displayName : '失败动画'
        },
        prefab_showResult : {
            default : null,
            type : cc.Prefab,
            displayName : '结果显示容器'
        },
        node_grayLayer : {
            default : null,
            type : cc.Node,
            displayName : '灰色层'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._winAniName = 'platform_win';
        this._loseAniName = 'platform_lose';
        this._resultCompName = 'Bull100_showResult';
        this._pokerResultCompName = 'Obj_onPokerResult';
    },

    //开始游戏
    playStartGameAni : function (callFunc) {
        this.playStartAni('grab_start',callFunc);
        GG.audioMgr.playSound(13);
    },
    //播放开始动画
    playStartAni : function (aniName, callFunc) {
        if(!aniName) aniName = '';
        this._startCallFunc = callFunc;
        if(!this._startAni){
            this._startAni = cc.instantiate(this.prefab_start);
            this._startAni.parent = this.node;
            //ani.scale = G_Config_common.frameScale;
            var comp = this._startAni.getComponent(dragonBones.ArmatureDisplay);
            //comp.addEventListener(dragonBones.EventObject.FRAME_EVENT, this._frameEvent, this);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        }

        var designSize = cc.view.getDesignResolutionSize()
        this._startAni.position = cc.p(designSize.width/2, designSize.height/2);
        this._startAni.active = true;
        this._startAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _startAniEnd : function (event) {
        //动画结束
        if(this._startAni) this._startAni.active = false;
        if(this._startCallFunc) {
            this._startCallFunc();
            this._startCallFunc = null;
        }
    },
    _frameEvent : function (event) {
        GG.bull100Mgr.shakeGame();
    },
    //清理开始动画
    clearStartAni : function () {
        if(this._startAni) this._startAni.active = false;
        this._startCallFunc = null;
    },

    //============================================

    //开始投注
    playStartBettingAni : function (callFunc) {
        this.playBettingAni('platform_bet',callFunc);
    },
    //投注结束
    playBettingEnd : function (callFunc) {
        this.playBettingAni('platform_betOver',callFunc);
    },
    //播放开始投注动画
    playBettingAni : function (aniName, callFunc) {
        if(!aniName) aniName = '';
        this._bettingCallFunc = callFunc;
        if(!this._bettingAni){
            this._bettingAni = cc.instantiate(this.prefab_betting);
            this._bettingAni.parent = this.node;
            //ani.scale = G_Config_common.frameScale;
            var comp = this._bettingAni.getComponent(dragonBones.ArmatureDisplay);
            //comp.addEventListener(dragonBones.EventObject.FRAME_EVENT, this._frameEvent, this);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._bettingAniEnd, this);
        }
        var pos = G_DATA.getTopTipPos();
        this._bettingAni.position = cc.p(pos.x, pos.y-150);
        this._bettingAni.active = true;
        this._bettingAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _bettingAniEnd : function (event) {
        //动画结束
        if(this._bettingAni) this._bettingAni.active = false;
        if(this._bettingCallFunc) {
            this._bettingCallFunc();
            this._bettingCallFunc = null;
        }
    },
    //清理开始投注动画
    clearBettingAni : function () {
        if(this._bettingAni) {
            this._bettingAni.active = false;
        }
        this._bettingCallFunc = null;
    },


    //================================胜利动画

    //显示胜利或者失败
    showResultEffect : function (data, callFunc) {
        if(data.myChangeGold != undefined){
            this.setGrayIsShow(true);
            if(data.myChangeGold > 0){
                this._playWin(data, callFunc);
            }else{
                this._playLose(data, callFunc);
            }
        }else {
            if(callFunc){
                callFunc();
                callFunc = null;
            }
        }
    },

    _playWin : function (data, callFunc) {
        this._resultData = data;
        this._winCallFunc = callFunc;
        if(!this._winAni){
            this._winAni = cc.instantiate(this.prefab_win);
            this._winAni.parent = this.node;
            var comp = this._winAni.getComponent(dragonBones.ArmatureDisplay);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._winEnd, this);
        }
        var designSize = cc.view.getDesignResolutionSize();
        var pos = cc.p(designSize.width/2, designSize.height/2);
        this._winAni.position = cc.p(pos.x, pos.y*1.45);
        this._winAni.active = true;
        this._winAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(this._winAniName, 1);
        GG.audioMgr.playSound(22);
    },
    _winEnd : function () {
        this._getResultComp().showResult(this._resultData);
        this._winAni.runAction(cc.sequence(cc.delayTime(2), cc.callFunc(function () {
            this._winAni.active = false;
            this._getResultComp()._setIsShow(false);
            this.setGrayIsShow(false);
            if(this._winCallFunc){
                this._winCallFunc();
                this._winCallFunc = null;
            }
        }, this)));
    },

    _clearWin : function () {
        if(this._winAni){
            this._getResultComp()._setIsShow(false);
            this._winAni.stopAllActions();
            this._winAni.active = false;
            this._winCallFunc = null;
        }
    },

    //======================================失败动画

    _playLose : function (data, callFunc) {
        this._resultData = data;
        this._loseCallFunc = callFunc;
        if(!this._loseAni){
            this._loseAni = cc.instantiate(this.prefab_lose);
            this._loseAni.parent = this.node;
            var comp = this._loseAni.getComponent(dragonBones.ArmatureDisplay);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._loseEnd, this);
        }
        var designSize = cc.view.getDesignResolutionSize();
        var pos = cc.p(designSize.width/2, designSize.height/2);
        this._loseAni.position = cc.p(pos.x, pos.y*1.45);
        this._loseAni.active = true;
        this._loseAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(this._loseAniName, 1);
        GG.audioMgr.playSound(21);
    },
    _loseEnd : function () {
        this._getResultComp().showResult(this._resultData);

        this._loseAni.runAction(cc.sequence(cc.delayTime(2), cc.callFunc(function () {
            this._loseAni.active = false;
            this._getResultComp()._setIsShow(false);
            this.setGrayIsShow(false);
            if(this._loseCallFunc){
                this._loseCallFunc();
                this._loseCallFunc = null;
            }
        }, this)));
    },

    _getResultComp : function () {
        if(!this._resultComp){
            var result = cc.instantiate(this.prefab_showResult);
            this.node.addChild(result, 5);
            if(this._winAni) result.position = this._winAni.position;
            if(this._loseAni) result.position = this._loseAni.position;
            this._resultComp = result.getComponent(this._resultCompName);
        }
        return this._resultComp
    },
    _clearLose : function () {
        if(this._loseAni){
            this._getResultComp()._setIsShow(false);
            this._loseAni.stopAllActions();
            this._loseAni.active = false;
            this._loseCallFunc = null;
        }
    },

    //====================================灰色层

    setGrayIsShow : function (isShow) {
        if(this.node_grayLayer) this.node_grayLayer.active = isShow;
    },

    getGrayLayer : function () {
        return this.node_grayLayer
    },

    //清理所有
    clearAll : function () {
        this.clearBettingAni();
        this.clearStartAni();
        this._clearWin();
        this._clearLose();
        this.setGrayIsShow(false);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
