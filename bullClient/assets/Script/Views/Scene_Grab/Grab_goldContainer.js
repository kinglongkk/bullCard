//金币流动效果的处理层

//var goldInfo = {
//    startPos : cc.p(),
//    targetPos : cc.p(),
//    goldNum : 4,
//    userData : '',
//    tableIndex : 1,
//    callFunc : '',
//}

cc.Class({
    extends: cc.Component,

    properties: {
        _pool : null,
        _callFunc : null,

        frame_gold : cc.SpriteFrame,
    },

    // use this for initialization
    onLoad: function () {
        this._goldScale = G_Config_common.scale_moveGold;
        this._addGoldTime = G_Config_grab.time_goldFly_Add;
        this._resultGoldTime = G_Config_grab.time_goldFly_Result;

        this._pool = new cc.NodePool('Grab_goldContainer');
    },

    moveAddGolds : function (goldInfo, callFunc) {
        //this._callFunc = callFunc;
        GG.audioMgr.playSound(15);
        var gold,act1, act2, delayTime=0, delayValue = G_Config_grab.time_goldFly_delay;
        for(var i = 0; i < goldInfo.goldNum; i ++){
            gold = this._getGold();
            gold.position = goldInfo.startPos;
            gold._flyCallFunc = goldInfo.callFunc;

            act1 = cc.delayTime(delayTime);
            act2 = cc.moveTo(this._addGoldTime, goldInfo.targetPos);
            gold.runAction(cc.sequence(act1, act2, cc.callFunc(this._flyAddGoldEnd, this, goldInfo)));
            delayTime += delayValue;
        }
        if(gold) gold._endCallFunc = callFunc;
    },
    _flyAddGoldEnd : function (target, goldInfo) {
        var gold = target;
        if(gold._flyCallFunc) {
            gold._flyCallFunc();
            gold._flyCallFunc = null;
        }
        if(gold._endCallFunc) {
            gold._endCallFunc();
            gold._endCallFunc = null;
        }
        this._removeGold(gold);
    },

    //========================重构金币的回收显示

    flyResultGold : function (dataList, callFunc) {
        if(!dataList) return;
        var dataObj;
        this._callFunc = callFunc;
        for(var i = 0; i < dataList.length; i ++){
            dataObj = dataList[i];
            if(dataObj){
                this._setResultGolds(dataObj);
            }
        }
    },
    _setResultGolds : function (dataObj) {
        for(var i = 0; i < dataObj.goldNum; i ++){
            var gold = this._getGold();
            var table = GG.grabMgr.getTable(dataObj.tableIndex);
            var startPos = table.removeOneGoldImg();
            if(!startPos) continue;
            gold.position = startPos;

            var act1 = cc.moveTo(this._resultGoldTime, dataObj.targetPos);
            var self = this;
            gold.runAction(cc.sequence(act1, cc.callFunc(function (target) {
                self._removeGold(target);
                if(self.callBack){
                    self.callBack();
                    self.callBack = null;
                }
            }, this, this._resultGoldNum)));
        }
    },

    //剩余的金币全部移动到系统庄
    recordLeaveGolds : function (targetPos) {

    },

    //addResultGolds : function (dataList, callFunc) {
    //    if(dataList.length < 1) return;
    //    this._callFunc = callFunc;
    //    this._list_resultGold = dataList;
    //    this._resultGoldNum = 0;
    //
    //    this._update_resultGold();
    //    this.schedule(this._update_resultGold, G_Config_grab.time_goldFly_delay);
    //    GG.audioMgr.playSound(18);
    //},
    //_update_resultGold : function () {
    //    if(this._list_resultGold.length > 0){
    //        var dataObj,gold,table,act1;
    //        for(var i = this._list_resultGold.length-1; i >= 0; i --){
    //            dataObj = this._list_resultGold[i];
    //            if(dataObj.goldNum > 0){
    //                gold = this._getGold();
    //                this._resultGoldNum += 1;
    //
    //                table = GG.grabMgr.getTable(dataObj.tableIndex);
    //                table.removeOneGoldImg();
    //                gold.position = table.getWorldPos();
    //
    //                act1 = cc.moveTo(this._resultGoldTime, dataObj.targetPos);
    //                gold.runAction(cc.sequence(act1, cc.callFunc(this._flyResultGoldEnd, this, this._resultGoldNum)));
    //                dataObj.goldNum -= 1;
    //            }else {
    //                if(dataObj.callFunc) dataObj.callFunc(dataObj.tableIndex);
    //                this._list_resultGold.splice(i, 1);
    //                if(this._list_resultGold.length <= 0){
    //                    this.unschedule(this._update_resultGold);
    //                    this._list_resultGold = null;
    //                }
    //            }
    //        }
    //    }
    //},
    //_flyResultGoldEnd : function (target, userData) {
    //    this._removeGold(target);
    //    if(!this._list_resultGold && userData == this._resultGoldNum){
    //        this._resultGoldNum = 0;
    //        if(this._callFunc){
    //            this._callFunc();
    //            this._callFunc = null;
    //        }
    //    }
    //},

    _getGold : function () {
        var goldNode = this._pool.get();
        if(!goldNode){
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this.frame_gold;
        }
        goldNode.parent = this.node;
        goldNode.scale = this._goldScale;
        goldNode.active = true;
        return goldNode;
    },

    _removeGold : function (goldeNode) {
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    clearAll : function () {
        this.node.stopAllActions();
        var goldList = this.node.children;
        var num = goldList.length;
        for(var i = num-1; i >= 0; i --){
            this._removeGold(goldList[i]);
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
