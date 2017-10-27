//规则界面
cc.Class({
    extends: require('BaseDialog'),

    properties: {
        _rulesContent : null,                                                     //所有预制体
        _dict_rulePage : null,                                                    //规则页面
        _lastPage : null,                                                         //上一个打开的规则页面
        _lastIndex : null,                                                        //上一个打开的规则页面的索引

        prefab_introduce : {
            default : null,
            type : cc.Prefab,
            displayName : '规则简介预制体'
        },
        prefab_bull100 : {
            default : null,
            type : cc.Prefab,
            displayName : '百人规则预制体'
        },
        prefab_grab : {
            default : null,
            type : cc.Prefab,
            displayName : '押宝规则预制体'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._super();

        this._comp_exchangPages = this.getComponent('Part_dialog_exchangePages');

        var dataObj = G_OBJ.data_dialog_exchangePages();
        dataObj.leftDealScrollComp = this.comp_leftBtnContainer;
        dataObj.leftPrefab = this.prefab_leftButton;
        dataObj.leftContentList = ['游戏简介','百人大战','押宝大战'];
        dataObj.leftClickFunc = null;
        dataObj.rightDealScrollComp = this.comp_itemContainer;
        dataObj.rightPrefabList = [this.prefab_introduce, this.prefab_bull100, this.prefab_grab];
        this._comp_exchangPages.initData(dataObj);
    },

    start : function () {
        this._comp_exchangPages.showDefault();
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
