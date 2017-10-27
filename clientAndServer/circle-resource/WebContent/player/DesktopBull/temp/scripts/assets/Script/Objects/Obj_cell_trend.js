"use strict";
cc._RF.push(module, 'e1879pYLx1Gf6hjS0kLc/eK', 'Obj_cell_trend');
// Script/Objects/Obj_cell_trend.js

'use strict';

//走势图和账目条目
cc.Class({
    extends: cc.Component,

    properties: {
        _list_labelSort: null },

    // use this for initialization
    onLoad: function onLoad() {
        this._failColor = new cc.Color(187, 111, 110);
        this._winColor = new cc.Color(58, 185, 250);
        this._failStr = '小';
        this._winStr = '大';

        this._list_labelSort = {};
        var labels = this.node.children;
        this._list_labelSort[G_TYPE.pokerTypeStr.spade] = labels[0];
        this._list_labelSort[G_TYPE.pokerTypeStr.heart] = labels[1];
        this._list_labelSort[G_TYPE.pokerTypeStr.club] = labels[2];
        this._list_labelSort[G_TYPE.pokerTypeStr.diamond] = labels[3];
    },

    //设置这个条目中的信息
    setData: function setData(data) {
        if (!data) data = [['SPADE', 1], ['DIAMOND', 0], ['CLUB', 1], ['HEART', 0]]; //胜利是1，失败是0
        var labels = this.node.children;
        for (var i = 0; i < data.length; i++) {
            this._setLabelResult(this._list_labelSort[data[i][0]], parseInt(data[i][1]));
        }
    },
    //设置胜利与否
    _setLabelResult: function _setLabelResult(labelNode, isWin) {
        if (isWin) {
            labelNode.color = this._winColor;
            labelNode.getComponent(cc.Label).string = this._winStr;
        } else {
            labelNode.color = this._failColor;
            labelNode.getComponent(cc.Label).string = this._failStr;
        }
    }

});

cc._RF.pop();