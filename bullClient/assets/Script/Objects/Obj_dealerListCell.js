//上庄列表庄家条目

cc.Class({
    extends: cc.Component,

    properties: {
        _dealerWord : null,                                                                                            //庄字动画效果

        node_head : {
            default : null,
            type : cc.Node,
            displayName : '头像'
        },
        label_name : {
            default : null,
            type : cc.Label,
            displayName : '名字'
        },
        label_gold : {
            default : null,
            type : cc.Label,
            displayName : '金币'
        },
        prefab_dealerAni : {
            default : null,
            type : cc.Prefab,
            displayName : '庄字动画'
        }
    },

    // use this for initialization
    onLoad: function () {

    },

    setData : function (data) {
        G_TOOL.setHeadImg(this.node_head, data.headImg);
        this._setPlayerName(data.name);
        this._setGoldValue(data.gold);
        if(data.isDealer) this._addDealerWord();
        else this._delDealerWord()
    },

    _setPlayerName : function (playerName) {
        if(!playerName) playerName = '';
        else playerName = G_TOOL.getNameLimit(playerName);
        this.label_name.string = playerName;
    },
    _setGoldValue : function (value) {
        if(!value) value = 0;
        this.label_gold.string = G_TOOL.changeMoney(value);;
    },

    //添加庄字动画
    _addDealerWord : function () {
        this._dealerWord = cc.instantiate(this.prefab_dealerAni);
        this._dealerWord.parent = this.node;
        this._dealerWord.position = cc.p(-this.node.width*0.05, -this.node.height*0.5);
    },

    //删除庄字动画
    _delDealerWord : function () {
        if(this._dealerWord){
            this._dealerWord.destroy();
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
