"use strict";
cc._RF.push(module, '3827cXUXVxGSJC8QOIXG/UF', 'Obj_topOneLayer');
// Script/Objects/Obj_topOneLayer.js

'use strict';

//游戏场景中最上面的那个层


cc.Class({
    extends: cc.Component,

    properties: {
        _isConfirmTipOpen: null, //提示窗口是否已经打开
        _isHasOpen: null, //提示窗口是否已经打开

        prefab_loadingAni: {
            default: null,
            type: cc.Prefab,
            displayName: '网络请求动画'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        // GG.topTouchLayer = this;

        this._isConfirmTipOpen = false;
        this._touchLimitLayer = this.getComponent('Obj_touchLimit');
    },

    start: function start() {
        this._registerFunc(true);
    },

    //显示请求网络的动画
    showNetRequest: function showNetRequest(isShowTip) {
        if (!this.prefab_loadingAni) return;
        if (!this._netAni) {
            this._netAni = cc.instantiate(this.prefab_loadingAni);
            this._netAni.parent = this.node;
            //this._netAni.active = false;
        }
        if (this._netAni.active) return;
        if (this._isConfirmTipOpen) {
            //弹窗已经存在
            this._isHasOpen = true;
            return;
        }
        this._netAni.active = true;
        var center = cc.visibleRect.center;
        this._netAni.position = cc.p(center.x, center.y * 1.35);
        this._showReconnectTip(isShowTip);
        this._netAni.getComponent(dragonBones.ArmatureDisplay).playAnimation('');
        if (this._touchLimitLayer) this._touchLimitLayer.addTouchLimit();
    },

    closeNetRequest: function closeNetRequest() {
        if (this._netAni) {
            this._netAni.active = false;
            if (this._touchLimitLayer) this._touchLimitLayer.cancelTouchLimit();
        }
    },

    //显示重连提示
    _showReconnectTip: function _showReconnectTip(isShow) {
        var tipNode = this._netAni.getChildByName('tip');
        if (tipNode) {
            var content;
            if (isShow) {
                var tableData = G_DATA.getChinese(61);
                content = tableData.content;
            } else content = '';
            tipNode.getComponent(cc.Label).string = content;
        }
    },

    //注册确认窗口的监听
    _registerFunc: function _registerFunc(isRegister) {
        if (GG.tipsMgr) {
            GG.tipsMgr.registerConfirm(this._whenConfirmTipChange.bind(this), isRegister);
        }
    },

    //当窗口打开或者关闭
    _whenConfirmTipChange: function _whenConfirmTipChange(isOpen) {
        this._isConfirmTipOpen = isOpen;
        if (this._netAni.active) {
            //当显示连接的时候
            if (isOpen) {
                this._isHasOpen = true;
                this.closeNetRequest();
            }
        } else {
            if (!isOpen) {
                if (this._isHasOpen) {
                    var tipNode = this._netAni.getChildByName('tip');
                    var isShow = false;
                    if (tipNode) {
                        isShow = Boolean(tipNode.getComponent(cc.Label).string);
                    }
                    this.showNetRequest(isShow);
                }
            }
        }
    },

    onDestroy: function onDestroy() {
        this._registerFunc(false);
    }

});

cc._RF.pop();