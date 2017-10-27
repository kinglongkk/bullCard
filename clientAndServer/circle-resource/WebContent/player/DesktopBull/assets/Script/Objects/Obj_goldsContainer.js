//金币管理容器

cc.Class({
    extends: cc.Component,

    properties: {
        //_isUpdate : null,                                                           //是否开启金币的移动
        _dict_goldSort : null,                                                      //已经投注的金币的整理字典

        frame_gold : {
            default : null,
            type : cc.SpriteFrame,
            displayName : '金币图标'
        },
    },

    // use this for initialization
    onLoad: function () {
        //金币的移动时间
        this._goldMoveTime = 0.3;
        //胜利金币回收的移动速度
        this._recoveGoldMoveTime = 0.4;
        //金币的缩放
        this._goldScale = G_Config_common.scale_moveGold;

        this._dict_goldSort = {};
    },

    start : function () {
        if(GG.curMgr) this._tableContainer = GG.curMgr.getTableContainer();
    },

    //从玩家到投注区域
    playerToTable : function (dataObj) {
        //var dataObj = G_OBJ.data_flyGold_playerToTable();
        var goldPosList = this._tableContainer.getGoldPosList(dataObj.tableIndex, dataObj.goldNum);
        var targetPos, goldNode, lastGold;
        for(var i = 0; i < dataObj.goldNum; i ++){
            targetPos = goldPosList[i];
            if(targetPos){
                goldNode = this._getOneGold();
                this._addFlyAction(goldNode, {
                    startPos : dataObj.startPos,
                    targetPos : targetPos
                })
                if(!this._dict_goldSort[dataObj.tableIndex]) this._dict_goldSort[dataObj.tableIndex] = [];
                this._dict_goldSort[dataObj.tableIndex].push(goldNode);
                lastGold = goldNode;
            }
        }
        if(dataObj.goldNum > 0) GG.audioMgr.playSound(15);
        if(lastGold) lastGold.callBack = dataObj.callFunc;
    },
    //从投注区域到玩家
    tableToPlayer : function (dataObj) {
        //var dataObj = G_OBJ.data_flyGold_tableToPlayer();
        var goldList = this._getRemoveGold(dataObj.tableIndex, dataObj.goldNum);
        var goldNode;
        for(var i = 0; i < dataObj.goldNum; i ++){
            goldNode = goldList[i];
            if(goldNode){
                this._addFlyAction2(goldNode, {
                    //startPos : startPos,  //可以让其从当前的位置出发
                    targetPos : dataObj.targetPos
                })
            }
        }
        //if(dataObj.goldNum > 0) GG.audioMgr.playSound(18);
    },

    //回收剩余的金币
    recoverAllGold : function (targetPos) {
        var goldList = [], isHaveGold = false;
        for(var tableIndex in this._dict_goldSort){
            if(this._dict_goldSort[tableIndex]) goldList = goldList.concat(this._dict_goldSort[tableIndex]);
        }
        this._dict_goldSort = {};
        var goldNode;
        for(var i = 0; i < goldList.length; i ++){
            goldNode = goldList[i];
            if(goldNode){
                isHaveGold = true;
                this._addFlyAction2(goldNode, {
                    //startPos : startPos,  //可以让其从当前的位置出发
                    targetPos : targetPos
                })
            }
        }
        //if(goldList.length > 0) GG.audioMgr.playSound(18);
        return isHaveGold
    },

    //从一个投注区域中获取随机的几个金币,用于回收移动
    _getRemoveGold : function (tableIndex, goldNum) {
        if(!this._dict_goldSort[tableIndex]) return []
        if(goldNum >= this._dict_goldSort[tableIndex].length) {
            var goldList = this._dict_goldSort[tableIndex].concat([]);
            this._dict_goldSort[tableIndex] = null;
            return goldList
        }else{
            return this._dict_goldSort[tableIndex].splice(0,goldNum);
        }
    },

    //增加一个金币移动的动作---投注金币
    _addFlyAction : function (goldNode, flyInfo) {
        var startPos = flyInfo.startPos;
        var targetPos = flyInfo.targetPos;
        if(startPos) goldNode.position = startPos;
        // var act = cc.moveTo(this._goldMoveTime, targetPos).easing(cc.easeOut(3.0));
        var act = cc.moveTo(this._goldMoveTime, targetPos);
        goldNode.runAction(cc.sequence(act, cc.callFunc(this._goldFlyEnd, this, flyInfo.isRemove)));
    },
    //增加一个金币移动的动作---回收金-币
    _addFlyAction2 : function (goldNode, flyInfo) {
        var startPos = flyInfo.startPos;
        var targetPos = flyInfo.targetPos;
        if(startPos) goldNode.position = startPos;
        var act = cc.moveTo(this._recoveGoldMoveTime, targetPos);
        goldNode.runAction(cc.sequence(act, cc.callFunc(this._goldFlyEnd, this, true)));
    },
    //金币移动结束
    _goldFlyEnd : function (goldNode, isRemove) {
        if(goldNode.callBack){
            goldNode.callBack();
            goldNode.callBack = null;
        }

        if(isRemove){
            this._removeGold(goldNode);
        }
    },

    //===================

    //获取一个金币
    _getOneGold : function () {
        if(!this.frame_gold) return null;
        if(!this._pool) this._pool = new cc.NodePool('Obj_goldContainer');

        var goldNode = this._pool.get();
        if(!goldNode){
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this.frame_gold;
        }
        goldNode.parent = this.node;
        //goldNode.scale = (1/this.node.parent.scale)*this._goldScale;
        goldNode.scale = this._goldScale;
        goldNode.active = true;
        return goldNode;
    },
    //移除金币
    _removeGold : function (goldeNode) {
        goldeNode.stopAllActions();
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    getGoldSize : function () {
        if(!this._goldSize){
            var gold = this._getOneGold();
            this._goldSize = gold.getContentSize();
            this._removeGold(gold);
        }
        return this._goldSize
    },

    //清理所有
    clearAll : function () {
        var goldList = this.node.children;
        for(var i = goldList.length-1; i >= 0; i --){
            this._removeGold(goldList[i]);
        }

        this._dict_goldSort = {};
        this.node.stopAllActions();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    onDestroy : function () {
        if(this._pool){
            this._pool.clear();
        }
    },
});
