"use strict";
cc._RF.push(module, '4c19f4ThPlPK6/llGibgPGc', 'Bull100_pokerResultContainer');
// Script/Views/Scene_Bull100/Bull100_pokerResultContainer.js

'use strict';

//显示卡牌的结果

cc.Class({
    extends: cc.Component,

    properties: {
        prefab_pokerResult: {
            default: null,
            type: cc.Prefab,
            displayName: '卡牌结果'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._pokerResultCompName = 'Obj_onPokerResult';
    },

    showPokerResult: function showPokerResult(pos, pokerValue) {
        if (!this._list_pokerResult) this._list_pokerResult = [];

        var resultComp, resultNode, needComp;
        for (var i = 0; i < this._list_pokerResult.length; i++) {
            resultComp = this._list_pokerResult[i];
            if (resultComp && !resultComp.getIsShow()) {
                //有隐藏的卡牌
                needComp = resultComp;
                break;
            }
        }
        if (!needComp) {
            resultNode = cc.instantiate(this.prefab_pokerResult);
            resultNode.parent = this.node;
            needComp = resultNode.getComponent(this._pokerResultCompName);
            this._list_pokerResult.push(needComp);
        }
        needComp.showResult(pos, pokerValue, true);
    },
    _clearPokerResult: function _clearPokerResult() {
        if (this._list_pokerResult) {
            for (var i = 0; i < this._list_pokerResult.length; i++) {
                this._list_pokerResult[i].hideOnPokerResult();
            }
        }
    },

    clearAll: function clearAll() {
        this._clearPokerResult();
    }

});

cc._RF.pop();