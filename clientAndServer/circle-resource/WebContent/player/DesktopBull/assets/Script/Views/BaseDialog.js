//弹窗列表界面的父类


cc.Class({
    extends: require('BaseUI'),

    properties: {
        _parentComp : null,                                                                                               //容器的脚本

        node_closeBtn : {
            default : null,
            type : cc.Node,
            displayName : '关闭按钮'
        },
        prefab_leftButton : {
            default : null,
            type : cc.Prefab,
            displayName : '左边按钮预制体'
        },
        comp_leftBtnContainer : {
            default : null,
            type : require('Obj_dealScroll'),
            displayName : '左边按钮容器'
        },
        prefab_item : {
            default : null,
            type : cc.Prefab,
            displayName : '数据条目'
        },
        comp_itemContainer : {
            default : null,
            type : require('Obj_dealScroll'),
            displayName : '条目容器'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._registerCloseEvent();
    },

    bindContainer : function (comp) {
        this._parentComp = comp;
    },

    showLayer : function () {
        this._super();

        if(this._parentComp && this._parentComp.setGrayLayerIsShow) this._parentComp.setGrayLayerIsShow(true);
    },

    hideLayer : function () {
        this._super();

        if(this._parentComp && this._parentComp.setGrayLayerIsShow) this._parentComp.setGrayLayerIsShow(false);
    },

    setData : function () {

    },

    _registerCloseEvent : function () {
        if(this.node_closeBtn) this.registerButton(this.node_closeBtn, this._onClick_close, this);
    },

    _onClick_close : function (event) {
        this.hideLayer();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
