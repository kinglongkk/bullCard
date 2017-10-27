//结算界面中胜利排行的头像


cc.Class({
    extends: cc.Component,

    properties: {
        _defaultHeadFrame : null,                                       //显示默认的头像

        node_head : {
            default : null,
            type : cc.Node,
            displayName : '头像精灵'
        },
        label_playerName : {
            default : null,
            type : cc.Label,
            displayName : '玩家名字'
        },
        label_gold : {
            default : null,
            type : cc.Label,
            displayName : '金币数值'
        },
    },

    // use this for initialization
    onLoad: function () {

    },

    setData : function (data) {
        this._setPlayerName(data.name);
        this._setGoldValue(data.addGold);
        G_TOOL.setHeadImg(this.node_head, data.icon);
    },

    //设置玩家名字
    _setPlayerName : function (name) {
        if(!name) name = '';
        name = G_TOOL.getNameLimit(name, 8, true);
        this.label_playerName.string = name;
    },

    _setGoldValue : function (newValue) {
        this.label_gold.string = G_TOOL.changeMoney(newValue);
    },

    setIsShow : function (isShow) {
        this.node.active = isShow;
    },
    getIsShow : function () {
        return this.node.active
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
