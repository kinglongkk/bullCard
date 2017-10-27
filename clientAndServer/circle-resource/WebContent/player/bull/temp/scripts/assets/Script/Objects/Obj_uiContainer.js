"use strict";
cc._RF.push(module, '82b09GVpxBPap6yQdYUAPAT', 'Obj_uiContainer');
// Script/Objects/Obj_uiContainer.js

'use strict';

//显示UI界面的容器


cc.Class({
    extends: cc.Component,
    properties: {
        _dict_ui: null, //ui整理的字典
        _ui_baseUrl: null, //ui整理的字典
        _isOnLoad: null, //是否正在加载中

        node_grayLayer: {
            default: null,
            type: cc.Node,
            displayName: '灰色层'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._dict_ui = {};
        this._ui_baseUrl = 'UI_prefabs/';
        this._isOnLoad = false;
    },

    //获取一个可以显示的ui
    addUI: function addUI(uiName, callBakc) {
        this.showUI(uiName, callBakc);
        return;
        //var uiComp = this._dict_ui[uiName];
        //if(!uiComp){
        //    //var prefabName = 'prefab_'+uiName.split('_')[1];
        //    var prefabName = uiName;
        //    if(!this[prefabName]) return null;
        //    var uiNode = cc.instantiate(this[prefabName]);
        //    uiNode.parent = this.node;
        //    var pos = this._getDialogPos();
        //    uiNode.position = this._getDialogPos();
        //    uiComp = uiNode.getComponent(uiName);
        //    uiComp.setUIName(uiName);
        //    uiComp.bindContainer(this);
        //    this._dict_ui[uiName] = uiComp;
        //}
        //uiComp.hideLayer();
        //return uiComp
    },

    showUI: function showUI(uiName, callBack) {
        if (this._isOnLoad) return;
        if (!uiName) {
            //错误的ui名称
            if (callBack) {
                callBack(null);
                callBack = null;
            }
            return;
        }
        var uiComp = this._dict_ui[uiName];
        if (!uiComp) {
            //创建窗口
            var prefabName = uiName;
            var self = this;
            this._isOnLoad = true;
            cc.loader.loadRes(this._ui_baseUrl + prefabName, function (err, prefab) {
                self._isOnLoad = false;
                var uiNode = cc.instantiate(prefab);
                uiNode.parent = self.node;
                uiNode.position = self._getDialogPos();
                uiComp = uiNode.getComponent(uiName);
                uiComp.setUIName(uiName);
                uiComp.bindContainer(self);
                self._dict_ui[uiName] = uiComp;
                uiComp.hideLayer();
                if (callBack) {
                    callBack(uiComp);
                    callBack = null;
                }
            });
        } else {
            //已经创建过该ui
            uiComp.hideLayer();
            if (callBack) {
                callBack(uiComp);
                callBack = null;
            }
        }
    },

    //增加一个额外的ui到ui管理字典
    addUIEx: function addUIEx(uiName, uiComp) {
        if (!uiComp) return;
        uiComp.setUIName(uiName);
        this._dict_ui[uiName] = uiComp;
    },

    //关闭一个ui
    closeUI: function closeUI(uiName) {
        if (!this._dict_ui[uiName]) return;
        this._dict_ui[uiName].hideLayer();
    },

    //获取一个UI的操作脚本
    getUIComp: function getUIComp(uiName) {
        var uiComp = this._dict_ui[uiName];
        if (!uiComp) uiComp = this.addUI(uiName);
        return uiComp;
    },

    //关闭所有的窗口显示
    closeAllUI: function closeAllUI() {
        for (var uiName in this._dict_ui) {
            this.closeUI(uiName);
        }
    },

    _getDialogPos: function _getDialogPos() {
        var designSize = cc.sys.isMobile ? cc.view.getDesignResolutionSize() : cc.visibleRect;
        return cc.p(designSize.width * 0.5, designSize.height * 0.5);
    },

    //设置置灰层的显隐
    setGrayLayerIsShow: function setGrayLayerIsShow(isShow) {
        if (this.node_grayLayer) this.node_grayLayer.active = isShow;
    }

});

cc._RF.pop();