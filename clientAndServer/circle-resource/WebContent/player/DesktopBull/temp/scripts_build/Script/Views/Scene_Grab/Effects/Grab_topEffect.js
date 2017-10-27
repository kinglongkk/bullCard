"use strict";
cc._RF.push(module, 'cb93fp5RkNDeb3yh6PN0aG/', 'Grab_topEffect');
// Script/Views/Scene_Grab/Effects/Grab_topEffect.js

'use strict';

//放置在最上面的动画特效

cc.Class({
    extends: cc.Component,

    properties: {
        _aniCallFunc: null, //开始动画的回调
        _grabDoneCallFunc: null, //结算动画的回调
        _list_flags: null, //胜利特效

        node_aniContainer: {
            default: null,
            type: cc.Node,
            displayName: '动画容器'
        },
        prefab_startAni: {
            default: null,
            type: cc.Prefab,
            displayName: '开始动画'
        },
        prefab_winFlagAni: {
            default: null,
            type: cc.Prefab,
            displayName: '赢钱旗帜'
        },
        prefab_grabDoneAni: {
            default: null,
            type: cc.Prefab,
            displayName: '押注完成'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    //开始游戏动画
    playGameStartAni: function playGameStartAni(callFunc) {
        this.playStartAni('grab_start', callFunc);
        GG.audioMgr.playSound(13);
    },

    playStartAni: function playStartAni(aniName, callFunc) {
        if (!aniName) aniName = '';
        this._aniCallFunc = callFunc;
        if (!this._startGrabAni) {
            this._startGrabAni = cc.instantiate(this.prefab_startAni);
            this._startGrabAni.parent = this.node_aniContainer;

            //ani.scale = G_Config_common.frameScale;
            this._startGrabAni.position = this._getStartAniPos();
            this._startGrabAni.getComponent(dragonBones.ArmatureDisplay).addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        }
        this._startGrabAni.active = true;
        //comp = this._startAni.getComponent(dragonBones.ArmatureDisplay);
        this._startGrabAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _startAniEnd: function _startAniEnd(event) {
        if (this._startGrabAni) this._startGrabAni.active = false;
        if (this._aniCallFunc) {
            this._aniCallFunc();
            this._aniCallFunc = null;
        }
    },
    forceStopStart: function forceStopStart() {
        if (this._startGrabAni) this._startGrabAni.active = false;
        this._aniCallFunc = null;
    },

    //=====================

    //开始幸运区域投注动画
    playTouchStartAni: function playTouchStartAni(callFunc) {
        this.playGrabAni('platform_bet', callFunc);
    },

    //投注完成的动画
    playGrabDoneAni: function playGrabDoneAni(callFunc, userData) {
        this.playGrabAni('platform_betOver', callFunc, userData);
    },

    playGrabAni: function playGrabAni(aniName, callFunc, userData) {
        if (!aniName) aniName = '';
        this._grabDoneCallFunc = callFunc;
        if (!this._grabDoneAni) {
            this._grabDoneAni = cc.instantiate(this.prefab_grabDoneAni);
            this._grabDoneAni.parent = this.node_aniContainer;

            //this._grabDoneAni.scale = G_Config_common.frameScale;
            this._grabDoneAni.position = this._getStartAniPos();
            this._grabDoneAni.getComponent(dragonBones.ArmatureDisplay).addEventListener(dragonBones.EventObject.COMPLETE, this._grabDoneAniEnd, this);
        }
        this._grabDoneAni.pokerInfo = userData;
        this._grabDoneAni.active = true;
        this._grabDoneAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _grabDoneAniEnd: function _grabDoneAniEnd(event) {
        if (this._grabDoneCallFunc) {
            this._grabDoneCallFunc(this._grabDoneAni.pokerInfo);
            this._grabDoneCallFunc = null;
        }
        if (this._grabDoneAni) this._grabDoneAni.active = false;
    },

    _getStartAniPos: function _getStartAniPos() {
        var designSize = cc.visibleRect;
        return cc.p(designSize.width / 2, designSize.height * 0.6);
    },

    //=====================

    playOneFlag: function playOneFlag(pos) {

        var flag = this._getOneFlag();
        flag.position = pos;
        flag.active = true;

        var comp = flag.getComponent(dragonBones.ArmatureDisplay);
        // comp.addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        comp.playAnimation('', 1);
    },
    clearFlag: function clearFlag() {
        //var flag;
        //for(var i = 0; i < this._list_flags.length; i ++){
        //    flag = this._list_flags[i];
        //    if(flag) flag.active = false;
        //}

        this.forceStopAllAni();
    },
    _getOneFlag: function _getOneFlag() {
        if (!this._flagAni) {
            this._flagAni = cc.instantiate(this.prefab_winFlagAni);
            this._flagAni.parent = this.node_aniContainer;
            //this._flagAni.scale = G_Config_common.frameScale;
        }
        return this._flagAni;
    },

    forceStopAllAni: function forceStopAllAni() {
        var anis = this.node_aniContainer.children;
        var aniNode;
        for (var i = 0; i < anis.length; i++) {
            aniNode = anis[i];
            if (aniNode) {
                aniNode.active = false;
            }
        }
        this._grabDoneCallFunc = null;
    },

    clearAll: function clearAll() {
        this.forceStopStart();
        this.forceStopAllAni();
        this.node.stopAllActions();
    }

});

cc._RF.pop();