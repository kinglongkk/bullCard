//显示卡牌的结果

cc.Class({
    extends: cc.Component,

    properties: {
        prefab_pokerResult : {
            default : null,
            type : cc.Prefab,
            displayName : '卡牌结果'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._pokerResultCompName = 'Obj_onPokerResult';
    },

    showPokerResult : function (pos, pokerValue) {
        if(!this._list_pokerResult) this._list_pokerResult = [];

        var resultComp, resultNode, needComp;
        for(var i = 0; i < this._list_pokerResult.length; i ++){
            resultComp = this._list_pokerResult[i];
            if(resultComp && !resultComp.getIsShow()){
                //有隐藏的卡牌
                needComp = resultComp;
                break;
            }
        }
        if(!needComp) {
            resultNode = cc.instantiate(this.prefab_pokerResult);
            resultNode.parent = this.node;
            needComp = resultNode.getComponent(this._pokerResultCompName);
            this._list_pokerResult.push(needComp);
        }
        needComp.showResult(pos, pokerValue, true);
    },
    _clearPokerResult : function () {
        if(this._list_pokerResult){
            for(var i = 0; i < this._list_pokerResult.length; i ++){
                this._list_pokerResult[i].hideOnPokerResult();
            }
        }
    },

    clearAll : function () {
        this._clearPokerResult();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
