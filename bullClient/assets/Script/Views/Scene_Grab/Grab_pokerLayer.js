//卡牌活动层

cc.Class({
    extends: cc.Component,

    properties: {
        _pokerShowPos : null,
        _pokerNum : null,
        _StartPos : null,
        _pokerW : null,
        _pokerH : null,                                                                                                     //卡牌的高
        _pokerOffX : null,
        _callFunc : null,
        _curResultNum : null,

        //=================卡牌容器重构
        _list_pokers : null,
        _firstPokerIndex : null,                                                                                           //展示结果的时候，第一张卡牌的信息变化了


        prefab_poker : cc.Prefab,
        atlas_pokerResult : {
            default : null,
            type : cc.SpriteAtlas,
            displayName : '显示牛几图集'
        },
        prefab_pokerResult : {
            default : null,
            type : cc.Prefab,
            displayName : '显示牛几结果'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._init();
    },

    _init : function () {
        this._pokerCompName = 'Obj_poker';
        this._pokerResultCompName = 'Obj_onPokerResult';
        this._moveTime = G_Config_grab.time_pokerMove;
        this._delayTime = G_Config_grab.delayTime_pokerMove;
        this._openMoveTime = G_Config_grab.time_openMovePoker;
        this._winPokerOff = G_Config_grab.num_winPokerOff;
        this._maxPokerNum = 5;
        this._pokerNum = 0;
        //重构
        this._list_pokers = [];
    },

    setStartInfo : function (startPos, targetPos) {
        this._StartPos = startPos;
        this._pokerShowPos = cc.p(targetPos.x, targetPos.y);
        if(this._pokerW) this._pokerShowPos = cc.p(this._pokerShowPos.x-this._pokerW*0.5, this._pokerShowPos.y-this._pokerH*0.5);
    },
    clearData : function () {
        this.node.stopAllActions();
        this._pokerNum = 0;
        this._flyNum = 0;

        for(var i = 0; i < this._maxPokerNum; i ++){
            if(this._list_pokers[i]) this._delOnePoker(this._list_pokers[i]);
        }
        if(this._pokerResult){
            this._pokerResult.destroy();
            this._pokerResult = null;
        }
    },

    // 显示卡牌
    showPokerEX : function (dataObj) {
        //var dataObj = G_OBJ.data_grabPokerInfo();
        //这幅卡牌结果值(没有传值的话，默认最终卡牌值为0)
        this._curResultNum = dataObj.pokerResult ? dataObj.pokerResult : 0;
        //卡牌信息
        this._flyNum = 0;
        var pokerList = dataObj.pokerInfoList;
        if(pokerList){
            var lastPoker;
            if(dataObj.startPokerIndex == 1){
                //显示第一张卡牌
                this._pokerNum = 0;
                lastPoker = this._addOnePokerEX(0, pokerList[0], dataObj.isFlyEffect);
            }else{
                //取其余四张信息
                this._firstPokerIndex = pokerList[0].pokerIndex;
                for(var i = 1; i < pokerList.length; i ++){
                    lastPoker = this._addOnePokerEX(i, pokerList[i], dataObj.isFlyEffect);
                }
            }
            if(lastPoker) lastPoker._flyCall = dataObj.callFunc;
            GG.audioMgr.playSound(14);
        }
    },

    //增加一张卡牌
    _addOnePokerEX : function (pokerIndex, pokerData, isFlyEffect) {
        var poker = this._list_pokers[pokerIndex];
        if(!poker){
            poker = this._createPoker();
            poker.zIndex = pokerIndex;
            this._list_pokers[pokerIndex] = poker;
        }
        //卡牌信息
        this._resetPokerInfo(poker, pokerData.pokerIndex);
        //飞行表现
        poker.getComponent(this._pokerCompName).clearAllActions();
        poker.getComponent(this._pokerCompName).showPokerNegative();
        poker.active = true;
        if(isFlyEffect){
            //有飞行特效
            poker.position = this._StartPos;
            this._addFlyAction(poker, pokerData.isOpen);
        }else{
            //没有飞行特效
            this._addNoFly(poker);
            if(pokerData.isOpen) poker.getComponent(this._pokerCompName).showPokerPlus();
        }
        this._pokerNum += 1;
        this._flyNum += 1;
        return poker
    },

    //=======================================

    //增加一张卡牌，纯粹的创建
    _createPoker : function () {
        var  poker = new cc.instantiate(this.prefab_poker);
        poker.parent = this.node;
        //poker.scale = 0.5;
        poker.active = true;
        return poker
    },
    //刷新卡牌数据
    _resetPokerInfo : function (pokerNode, pokerIndex) {
        var pokerInfo = G_DATA.getPokerInfo(pokerIndex);
        var pokerComp = pokerNode.getComponent(this._pokerCompName);
        pokerComp.setPokerInfo(pokerInfo.pokerType, pokerInfo.pokerValue, false);
    },
    //给卡牌增加动作
    _addFlyAction : function (pokerNode, isOpen) {
        var pokerComp = pokerNode.getComponent(this._pokerCompName);

        if(!this._pokerW) this._setPokerConfigInfo(pokerNode);
        var act1 = cc.delayTime(this._delayTime*this._flyNum);
        var act3 = cc.moveTo(this._moveTime, this._getTargetPos());
        pokerNode.runAction(cc.sequence(act1, act3, cc.callFunc(this._moveEnd, this, isOpen)));
    },
    //卡牌做无飞行效果显示
    _addNoFly : function (pokerNode) {
        if(!this._pokerW) this._setPokerConfigInfo(pokerNode);
        pokerNode.position = this._getTargetPos();
    },
    _moveEnd : function (target, isOpen) {
        if(isOpen){
            var pokerComp = target.getComponent(this._pokerCompName);
            pokerComp.openPoker();
        }
        if(target._flyCall){
            target._flyCall();
            target._flyCall = null;
        }
    },

    _setPokerConfigInfo : function (poker) {
        var pokerComp = poker.getComponent(this._pokerCompName);
        var pokerSize = pokerComp.getPokerSize();
        this._pokerW = pokerSize.width * poker.scaleX;
        this._pokerH = pokerSize.height * poker.scaleX;
        this._pokerOffX = this._pokerW * G_Config_grab.num_pokerOffX;
        this._pokerShowPos = cc.p(this._pokerShowPos.x-this._pokerW*0.5, this._pokerShowPos.y-this._pokerH*0.5);
    },

    //重新设置第一张卡牌信息
    _resetFirstPokerInfo : function () {
        if(this._list_pokers[0]) this._resetPokerInfo(this._list_pokers[0], this._firstPokerIndex);
    },

    //开牌==============================
    openPokers : function (callFunc) {
        this._callFunc = callFunc;
        //重置第一张牌信息
        this._resetFirstPokerInfo();
        //开始播放开牌
        var poker, index= 0, lastPoker, pokerComp, posX;
        //var sortList = this._sortPokers();
        var sortList = this.node.children;
        //如果有牛，需要分隔开来
        var offX = 0;
        if(this._curResultNum > 0) offX = this._winPokerOff;
        //开牌
        for(var i = 0; i < this._list_pokers.length; i ++){
            poker = this._list_pokers[i];
            if(!poker) continue;
            //poker.zIndex = index;
            pokerComp = poker.getComponent(this._pokerCompName);
            pokerComp.showPokerPlus();

            if(!this._pokerW) this._setPokerConfigInfo(poker);
            //posX = this._pokerShowPos.x+this._pokerW*0.5;
            posX = this._pokerShowPos.x;
            if(i > 2) posX += offX;
            poker.position = cc.p(posX, this._pokerShowPos.y);
            this._actOpenPoker(poker, index*this._pokerOffX);
            index += 1;
            lastPoker = poker;
        }
        lastPoker._callFunc = this._showResult.bind(this);
        GG.audioMgr.playSound(16);
    },
    _actOpenPoker : function (poker, offX) {
        var act1 = cc.moveTo(this._openMoveTime, poker.x+offX, poker.y);
        poker.runAction(cc.sequence(act1, cc.callFunc(function (target) {
            //this._showResult(5);
            if(target._callFunc){
                target._callFunc();
                target._callFunc = null;
            }
        }, this)));
    },
    //显示卡牌的牌型结果
    _showResult : function () {
        var pokerResult = this._curResultNum;
        var audioID = pokerResult == 0 ? 12 : pokerResult;
        GG.audioMgr.playSound(audioID);

        var pokerPos = cc.p(this._pokerShowPos.x+this._pokerW*0.65, this._pokerShowPos.y);
        var result = cc.instantiate(this.prefab_pokerResult);
        this.node.addChild(result, this.node.children.length);
        result.getComponent(this._pokerResultCompName).showResult(pokerPos, pokerResult, true, this._showResultEnd.bind(this));


        //var result = new cc.Node();
        //var childrenNum = this.node.children.length;
        //this.node.addChild(result, childrenNum);
        //result.position = cc.p(this._pokerShowPos.x+this._pokerW*1, this._pokerShowPos.y-10)
        //var sp = result.addComponent(cc.Sprite);
        //var imgName;
        //if(pokerResult < 1){
        //    imgName = G_RES_URL.atlas_pokerResult.noResult;
        //}else{
        //    imgName = G_RES_URL.atlas_pokerResult.resultImgName+pokerResult;
        //}
        //sp.spriteFrame = this.atlas_pokerResult.getSpriteFrame(imgName);
        //result.scale = 0;
        //var act1 = cc.scaleTo(0.25, 0.7);
        //var act2 = cc.scaleTo(0.1, 0.42);
        //var act3 = cc.scaleTo(0.1, 0.5);
        //result.runAction(cc.sequence(act1, act2, act3, cc.callFunc(this._showResultEnd, this)));

        this._pokerResult = result;
    },
    _showResultEnd : function () {
        if(this._callFunc){
            this._callFunc();
            this._callFunc = null;
        }
    },

    //先按值大小，再依照花色排列   ======改服务端完成
    //_sortPokers : function () {
    //    var sortDict = {}, keyList = [], poker;
    //    for(var key in this.node.children){
    //        poker = this.node.children[key];
    //        var pokerValue = poker.getComponent(this._pokerCompName).getPokerValue();
    //        keyList.push(pokerValue);
    //        if(!sortDict[pokerValue]) sortDict[pokerValue] = [poker];
    //        else sortDict[pokerValue].push(poker);;
    //    }
    //
    //    keyList = G_TOOL.quickSort(keyList);
    //    var lastPoker, sortList = [], lastValue=null, curValue;
    //    for(var key in keyList){
    //        curValue = keyList[key];
    //        poker = sortDict[curValue].pop();
    //        if(lastValue != curValue){
    //            sortList.splice(0, 0, poker);
    //            lastPoker = poker;
    //            lastValue = curValue;
    //        }else{
    //            if(!lastPoker) {
    //                sortList.splice(0, 0, poker);
    //                lastPoker = poker;
    //            }else{
    //                var lastType = lastPoker.getComponent(this._pokerCompName).getPokerType();
    //                var curType = poker.getComponent(this._pokerCompName).getPokerType();
    //                if(lastType >= curType){
    //                    sortList.splice(0, 0, poker);
    //                    lastPoker = poker;
    //                }else if(lastType < curType){
    //                    sortList.splice(1, 0, poker);
    //                }
    //                //else{
    //                //    sortList.splice(0, 0, poker);
    //                //    lastPoker = poker;
    //                //}
    //            }
    //        }
    //    }
    //    return sortList
    //},

    _getTargetPos : function () {
        //return cc.p(this._pokerShowPos.x + this._pokerOffX*this._pokerNum + this._pokerW*0.5, this._pokerShowPos.y)
        return cc.p(this._pokerShowPos.x + this._pokerOffX*this._pokerNum, this._pokerShowPos.y)
    },

    _delOnePoker : function (pokerNode) {
        //pokerNode.destroy();
        pokerNode.getComponent(this._pokerCompName).clearAllActions();
        pokerNode.getComponent(this._pokerCompName).showPokerNegative();
        pokerNode.active = false;
    },

    getPokerNum : function () {
        return this._pokerNum
    },


    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
