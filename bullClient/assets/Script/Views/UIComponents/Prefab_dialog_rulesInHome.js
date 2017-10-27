//房间内的规则显示


cc.Class({
    extends: require('BaseDialog'),

    properties: {
        prefab_Bull100 : {
            default : null,
            type : cc.Prefab,
            displayName : '百人规则内容'
        },
        prefab_grab : {
            default : null,
            type : cc.Prefab,
            displayName : '押宝规则内容'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super('UI_rulesInHome');
        this._rulesContent = [this.prefab_Bull100, this.prefab_grab];
    },

    setData : function (num) {
        this._initRulesContent(num);
    },

    //初始化规则内容
    _initRulesContent : function (num) {
        var item = cc.instantiate(this._rulesContent[num - 1]);
        item.parent = this.comp_itemContainer.node;

        item.active = true;
    },
    hideLayer : function () {
        this._super();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
