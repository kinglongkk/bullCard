"use strict";
cc._RF.push(module, 'f6109FzSJ9AfKnDL7rPu4MO', 'BaseDialog');
// Script/Views/BaseDialog.js

'use strict';

//弹窗列表界面的父类


cc.Class({
    extends: require('BaseUI'),

    properties: {
        _parentComp: null, //容器的脚本

        node_closeBtn: {
            default: null,
            type: cc.Node,
            displayName: '关闭按钮'
        },
        prefab_leftButton: {
            default: null,
            type: cc.Prefab,
            displayName: '左边按钮预制体'
        },
        comp_leftBtnContainer: {
            default: null,
            type: require('Obj_dealScroll'),
            displayName: '左边按钮容器'
        },
        prefab_item: {
            default: null,
            type: cc.Prefab,
            displayName: '数据条目'
        },
        comp_itemContainer: {
            default: null,
            type: require('Obj_dealScroll'),
            displayName: '条目容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerCloseEvent();
    },

    bindContainer: function bindContainer(comp) {
        this._parentComp = comp;
    },

    showLayer: function showLayer() {
        this._super();

        if (this._parentComp && this._parentComp.setGrayLayerIsShow) this._parentComp.setGrayLayerIsShow(true);
    },

    hideLayer: function hideLayer() {
        this._super();

        if (this._parentComp && this._parentComp.setGrayLayerIsShow) this._parentComp.setGrayLayerIsShow(false);
    },

    setData: function setData() {},

    _registerCloseEvent: function _registerCloseEvent() {
        if (this.node_closeBtn) this.registerButton(this.node_closeBtn, this._onClick_close, this);
    },

    _onClick_close: function _onClick_close(event) {
        this.hideLayer();
    }

});

cc._RF.pop();