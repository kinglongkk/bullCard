//卡牌容器


cc.Class({
    extends: cc.Component,

    properties: {
        _pool : null,                                                           //对象容器
        _startPos : null,                                                       //开始位置
        _pokerNum : null,                                                       //存在的卡牌数量
        _dict_pokerGroup : null,                                               //卡牌对象牌组
        _pokerSize : null,                                                      //卡牌的宽高
        _coverPokerCallFunc : null,                                            //所有背面卡牌发完
        _dict_winList : null,                                                   //胜利的卡牌
        _list_pokerResultValue : null,                                         //卡牌开牌后的结果数值
        _list_pokerPos : null,                                                   //卡牌位置信息
        _maxPokerNum : null,                                                    //最大的卡牌数量
        _list_hidePokers : null,                                                //隐藏的卡牌列表

        prefab_poker : cc.Prefab,                                               //卡牌容器
    },

    // use this for initialization
    onLoad: function () {
        this._pokerCompName = 'Obj_poker';
        this._list_hidePokers = [];
        this._pokerNum = 0;

        //卡牌间隔
        this._offPokerX = 20;
        //发牌时间间隔
        this._offGiveTime = 0.08;
        //每副牌之间的时间间隔
        this._offGroupTime = 0.3;
        //单张卡牌移动时间
        this._pokerMoveTime = 0.8;
        //一副牌有几张
        this._groupPokerNum = 5;
        //卡牌第二个动作移动时间
        this._pokerMoveTime2 = 0.2;
        //对卡牌进行缩放
        this._pokerScale = 1;
        //开牌间隔
        this._openInterval = 0.6;
    },

    //分发盖住的牌
    giveCoverPokers : function (posList, callFunc, isOverAni) {
        this._coverPokerCallFunc = callFunc;
        this._maxPokerNum = posList.length * this._groupPokerNum;
        this._clearPokers();
        this._pokerData = null;
        if(posList) {
            this._list_pokerPos = posList;
            this._pokerData = this._list_pokerPos.concat([]);
        }
        if(isOverAni) this._showPokerGroup();
        else {
            this._givePokerGroup();
        }
    },

    //-------------------------------

    //直接将需要的牌显示出来，不做动画
    _showPokerGroup : function () {
        var pos, posListLen = this._pokerData.length, poker, curNum;
        for(var i = posListLen-1; i >= 0; i --){
            pos = this._pokerData[i];
            for(var j = 0; j < this._groupPokerNum; j ++){
                poker = this._getOnePoker();
                if(poker) {
                    curNum = this._pokerNum%this._groupPokerNum;
                    poker.position = cc.p(pos.x + this._offPokerX*curNum, pos.y-this._pokerSize.height*this._pokerScale*0.5);
                    //poker.position = G_TOOL.adaptPos(poker.position);
                    if(!this._dict_pokerGroup[this._getGroupNum()]) this._dict_pokerGroup[this._getGroupNum()] = [];
                    this._dict_pokerGroup[this._getGroupNum()].push(poker);
                    this._pokerNum += 1;
                }
            }
        }
        if(this._coverPokerCallFunc){
            this._coverPokerCallFunc();
            this._coverPokerCallFunc = null;
        }
    },

    //--------------------------------
    //发一副牌
    _givePokerGroup : function () {
        if(!this._pokerData || this._pokerData.length < 1) {
            //所有卡牌发完
            if(this._coverPokerCallFunc){
                this._coverPokerCallFunc();
                this._coverPokerCallFunc = null;
            }
            return;
        }

        this._flyNum = 5;
        this._dict_pokerGroup[this._getGroupNum()] = [];
        var targetPos = this._pokerData.pop();
        for(var i = 0; i < this._groupPokerNum; i ++){
            //最后坐标需要计算卡牌的宽高
            this._flyOnePoker(targetPos);
        }
        GG.audioMgr.playSound(14);
    },
    //移动单张牌
    _flyOnePoker : function (targetPos) {
        var poker = this._getOnePoker();
        if(!targetPos || !poker) return;
        var curNum = this._pokerNum%this._groupPokerNum;
        var act1 = cc.delayTime(this._offGiveTime*curNum);

        var needPos = cc.p(targetPos.x, targetPos.y-this._pokerOffY);
        var act2 = cc.moveTo(G_TOOL.getUniformTime(poker.position, needPos, this._pokerMoveTime), needPos);
        poker.stopAllActions();
        poker.runAction(cc.sequence(act1,act2, cc.callFunc(this._onePokerFlyEnd, this)));
        this._dict_pokerGroup[this._getGroupNum()].push(poker);
        this._pokerNum += 1;
    },
    _onePokerFlyEnd : function (target) {
        //有一张卡牌移动结束
        this._flyNum -= 1;
        if(this._flyNum < 1) {
            this._sortPokerOff();
            var act = cc.delayTime(this._offGroupTime);
            this.node.runAction(cc.sequence(act,cc.callFunc(this._givePokerGroup, this)));
        }
    },
    //把发好的牌散开
    _sortPokerOff : function () {
        var pokerList = this._dict_pokerGroup[this._getGroupNum()-1];
        var len = pokerList.length;
        var poker;
        for(var i = 0; i < len; i ++){
            poker = pokerList[i];
            if(!poker) continue;
            poker.stopAllActions();
            poker.runAction(cc.moveBy(this._pokerMoveTime2, this._offPokerX*i,0));;
        }
    },

    //设置卡牌的信息
    setPokersData : function (dataList, pokerValueList) {
        this._list_pokerResultValue = pokerValueList;
        var pokerGroup, pokerData, pokerNodeList, pokerGroupNum = this._getGroupNum();
        for(var i = 0; i < pokerGroupNum; i ++){
            pokerGroup = dataList[i];
            pokerNodeList = this._dict_pokerGroup[i];
            if(pokerGroup){
                for(var j = 0; j < pokerGroup.length; j ++){
                    pokerData = pokerGroup[j];
                    this._setOnePokerInfo(pokerNodeList[j], pokerData.pokerIndex, pokerData.isOpen);
                }
            }
        }
    },
    _setOnePokerInfo : function (poker, pokerIndex, isOpen) {
        var pokerInfo = G_DATA.getPokerInfo(pokerIndex);
        var pokerComp = poker.getComponent(this._pokerCompName);
        pokerComp.setPokerInfo(pokerInfo.pokerType, pokerInfo.pokerValue, isOpen);
    },

    //开牌
    openPokers : function (dict) {
        this._dict_winList = dict;
        this._openGroupIndex = 0;
        this._openOneGroup();
    },
    //开启一副牌
    _openOneGroup : function () {
        if(this._pokerNum < 1){
            return
        }
        var pokerList = this._dict_pokerGroup[this._openGroupIndex];
        if(!pokerList){
            //卡牌全部打开
            return
        }
        var comp, pokerInfo, callFunc = this._openCallBack.bind(this);
        for(var i = 0; i < pokerList.length; i ++){
            comp = pokerList[i].getComponent(this._pokerCompName);
            comp.openPoker(callFunc);
            callFunc = null;
        }
        this._openGroupIndex += 1;
        GG.audioMgr.playSound(16);
        //this.node.runAction(cc.sequence(cc.delayTime(this._openInterval), cc.callFunc(this._openOneGroup, this)));
    },
    _openCallBack : function () {
        if(this._openGroupIndex > 1 && this._dict_winList){
            var tableIndex = this._openGroupIndex-2;
            var minIndex = GG.bull100Mgr.getMinTableIndex(tableIndex);
            var bigIndex = GG.bull100Mgr.getBigTableIndex(tableIndex);
            var curIndex = this._dict_winList[bigIndex] ? bigIndex : minIndex;
            GG.bull100Mgr.showTableWin(curIndex, this._dict_winList[curIndex]);
        }
        //显示这副卡牌的值
        var value = this._list_pokerResultValue[this._openGroupIndex-1];
        var pos = this._list_pokerPos[this._list_pokerPos.length - this._openGroupIndex];
        GG.bull100Mgr.showPokerResult(cc.p(pos.x+this._pokerSize.width*0.5, pos.y-+this._pokerSize.height*0.5), value);
        var audioID = value == 0 ? 11 : value;
        GG.audioMgr.playSound(audioID);
        //打开下一付牌
        this._openOneGroup();
    },

    //增加一张卡牌
    _getOnePoker : function () {
        if(!this.prefab_poker) return null;
        //if(!this._pool) this._pool = new cc.NodePool('Bull100_pokerContainer');

        var pokerNode = this._list_hidePokers.pop();
        if(!pokerNode){
            pokerNode = cc.instantiate(this.prefab_poker);
            if(!this._pokerSize) {
                this._pokerSize = pokerNode.getComponent(this._pokerCompName).getPokerSize();
                this._pokerOffY = this._pokerSize.height*this._pokerScale*0.5;
            }
            pokerNode.parent = this.node;
            pokerNode.scale = this._pokerScale;
        }
        pokerNode.stopAllActions();
        pokerNode.active = true;
        pokerNode.position = this._getStartPos();
        pokerNode.getComponent(this._pokerCompName).showPokerNegative();
        return pokerNode;
    },

    _removePoker : function (pokerNode) {
        //pokerNode.stopAllActions()
        pokerNode.getComponent(this._pokerCompName).clearAllActions();
        pokerNode.getComponent(this._pokerCompName).showPokerNegative();
        pokerNode.active = false;
        //this._pool.put(pokerNode);
        this._list_hidePokers.push(pokerNode);
    },

    _deletePokers : function () {
        //var poker = this.node.children;
        //for(var key in poker){
        //    poker[key].destroy();
        //}
    },
    _clearPokers : function () {
        this._pokerNum = 0;
        this._dict_pokerGroup = {};
        var pokers = this.node.children;
        this._list_hidePokers = [];
        var comp, pokerNum = pokers.length;
        for(var i = pokerNum-1; i >= 0; i --){
            comp = pokers[i].getComponent(this._pokerCompName);
            if(comp) this._removePoker(pokers[i]);
        }
    },

    //获取初始位置
    _getStartPos : function () {
        if(!this._startPos){
            this._startPos = G_DATA.getBottomTipPos();
        }
        return this._startPos
    },
    //获取当前有几组卡牌
    _getGroupNum : function () {
        return Math.floor(this._pokerNum/this._groupPokerNum)
    },
    //获取当前是否已经发完完整的牌
    getIsGivePokerEnd : function () {
        return !Boolean(this._coverPokerCallFunc)
    },

    //清理所有
    clearAll : function () {
        this._clearPokers();
        this._dict_winList = null;
        this.node.stopAllActions();
    },

    onDestroy : function () {
        this._deletePokers();
        if(this._pool) this._pool.clear();
    },


    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
