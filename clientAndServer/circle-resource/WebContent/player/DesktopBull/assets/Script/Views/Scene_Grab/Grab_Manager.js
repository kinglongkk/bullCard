//押宝系统的管理

cc.Class({
    extends: require('BaseManager'),

    properties: {
        _gameState : null,

        //data
        _list_winResult : null,
        _maxBettingGold : null,
        _homeID : null,                                         //该房间的ID
        _maxBettingTimes : null,                                     //最大投注次数
        _list_pokerIndex : null,                               //本局所有卡牌的索引
        _matchID : null,                                        //赛事ID
        _playerID : null,                                       //玩家自己的ID
        _nextStartTime : null,                                  //下次赛事开始的时间戳
        _curEndGrabTime : null,                                 //当前局投注结束时间
        _enterHomeData : null,                                  //进房信息
        _idleTip : null,                                         //提示休息中的tip
        _grabTip : null,                                         //提示投注中的tip
        _isPauseOverMatch : null,                                                                                        //暂停的时候是否进入新的一局
        _dict_idleBetting : null,                               //吃瓜群众的投注记录

        comp_topLayer : {
            default : null,
            type : require('Grab_topLayer'),
            displayName : '顶部层'
        },
        comp_goldContainer : {
            default : null,
            type : require('Grab_goldContainer'),
            displayName : '金币容器'
        },
        comp_playersContainer : {
            default : null,
            type : require('Grab_playersLayer'),
            displayName : '玩家容器'
        },
        comp_btnsContainer : {
            default : null,
            type : require('Grab_btnContainer'),
            displayName : '底部按钮'
        },
        comp_tablesContainer : {
            default : null,
            type : require('Grab_tablesLayer'),
            displayName : '压住格子层'
        },
        comp_pokersLayer : {
            default : null,
            type : require('Grab_pokerLayer'),
            displayName : '卡牌层'
        },
        comp_uiLayer : {
            default : null,
            type : require('Obj_uiContainer'),
            displayName : '窗口UI层'
        },
        label_bettingTimes : {
            default : null,
            type : cc.Label,
            displayName : '投注次数'
        },
        node_diyTip : {
            default : null,
            type : cc.Node,
            displayName : 'tip'
        },
    },

    // use this for initialization
    onLoad: function () {
        GG.grabMgr = this;
        this._super();

        this._list_pokerIndex = [];
        this._list_winResult = [];
        this._isPauseOverMatch = true;
        this._bettingTimesStr = G_CHINESE.limitBetting;
        this.comp_topEffect = this.comp_topLayer.getTopEffect();
    },

    start : function () {
        GG.tipsMgr.setTextTip(this.node_diyTip.getComponent('Obj_txtTip'));
        // console.log('游戏版本'+G_Config_common.version)
        if(G_Config_common.isLocal){

        }else{
            this.addLongListenOnStart();

            var homeData = GG.getPlayer().getInHomeData();
            if(homeData){
                if(homeData.net_resetHomeData){
                    this._dealInHomeNetData(homeData.net_inHomeData);
                    this.updateEnterHomeData(homeData.net_resetHomeData);
                    this.refreshHome();
                }else{
                    this.net_setHomeData(homeData.net_inHomeData);
                }
            }
        }
    },

    //=================================游戏流程

    resetShow : function () {
        this.node.stopAllActions();
        this.comp_pokersLayer.setStartInfo(this.comp_btnsContainer.getWheelPos(), this.comp_playersContainer.getPokerPos());

        //暂停的时候如果已经重新开局
        if(this._isPauseOverMatch){
            this._list_pokerIndex = [];
            this._list_winResult = [];
            //卡牌
            this.comp_pokersLayer.clearData();
            //点击区域
            this.comp_tablesContainer.clearData();
            //禁用点击
            this.comp_tablesContainer.setTouchEnable(false);
            //清理吃瓜群众投注信息
            this._dict_idleBetting = null;
        }
        this._isPauseOverMatch = true;
        //运动的金币
        this.comp_goldContainer.clearAll();
        //玩家
        this.comp_playersContainer.clearData();
        //底部按钮
        this.comp_btnsContainer.setBtnGroupEnable(false);
        //特效
        this.comp_topEffect.clearAll();
    },

    on_Wait : function (waitTime, callFunc) {
        this.setGameState(G_TYPE.grabState.wait);
        if(this._grabTip && this._grabTip.getIsShow()){
            this._grabTip.forceEnd();
        }
        var time1 = new Date(GG.socketMgr.getServerTime());
        //console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(this._nextStartTime);
        //console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
        if(this._idleTip && this._idleTip.getIsShow()) {
            //已经存在该tip
            //var serverTime = GG.socketMgr.getServerTime();
            //var secs = this._getOffSeconds(serverTime, this._nextStartTime);
            this._idleTip.forceChangeCountDown(waitTime);
        }else{
            var dataObj = {
                tipIndex : 19,
                isCountDown : true,
                showPos : this.node_diyTip.position,
                callBack : null
            }
            dataObj.retainTime = waitTime;
            if(callFunc) dataObj.callBack = callFunc;
            this._idleTip = GG.tipsMgr.showOnlyOne(dataObj);
        }
        this._clearByWait();
    },

    on_beforeStart : function () {
        this.setGameState(G_TYPE.grabState.readyStart);
        this.comp_topEffect.playGameStartAni(this.on_startTouchTables.bind(this));
        this.on_giveOnePoker();
    },

    on_giveOnePoker : function () {
        this.setGameState(G_TYPE.grabState.givePoker)

        //this.comp_pokersLayer.addPokers([{
        //    pokerIndex : this._list_pokerIndex[0],
        //    isOpen : true
        //}], this._list_winResult[0]);

        var pokerData = G_OBJ.data_grabPokerInfo();
        pokerData.pokerInfoList = [{
            pokerIndex : this._list_pokerIndex[0],
            isOpen : true
        }]
        pokerData.startPokerIndex = 1;
        pokerData.pokerResult = this._list_winResult[0];
        pokerData.isFlyEffect = true;

        this.comp_pokersLayer.showPokerEX(pokerData);
    },

    //on_beforeTouchTable : function () {
    //    this.setGameState(G_TYPE.grabState.readyTouchTable)
    //    this.comp_topEffect.playTouchStartAni(this.on_startTouchTables.bind(this));
    //},

    on_startTouchTables : function (retainTime) {
        this.setGameState(G_TYPE.grabState.touchTables)
        if(this._idleTip && this._idleTip.getIsShow()){
            this._idleTip.forceEnd();
        }
        this.comp_tablesContainer.setTouchEnable(true);
        this.comp_btnsContainer.refreshGrabOption();
        if(this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceChangeCountDown(retainTime);
        }else{
            var secs;
            var dataObj = {
                tipIndex : 18,
                showPos : this.node_diyTip.position,
                isCountDown : true
            }
            if(G_Config_common.isLocal){
                secs = 6;
                dataObj.callBack = this._touchTableEnd.bind(this);
            }else{
                secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            }
            if(retainTime) secs = retainTime;
            dataObj.retainTime = secs;
            this._grabTip = GG.tipsMgr.showOnlyOne(dataObj);
        }
    },

    _touchTableEnd : function () {
        this.comp_tablesContainer.setTouchEnable(false);
        this.comp_btnsContainer.setBtnGroupEnable(false);
        if(G_Config_common.isLocal){
            var netData = {
                pokers:[{
                        code:'33,34,4,36,2',//牌组 string 11，12
                        odds:1,//倍数
                        cardsPoint:parseInt(5),//点数
                        }
                    ]
            }
            //this.net_grabEnd(netData);
            var pokerInfo = this.setBettingEndInfo(netData);
            if(pokerInfo) this.comp_topEffect.playGrabDoneAni(this.on_giveFivePokers.bind(this), pokerInfo);
            this.on_Wait(15);

            this.comp_playersContainer.setMyselfMoney(this.getWinGold(), 58888);
            //庄家剩余金币
            this.comp_playersContainer.getSystemComp().setGoldValue(88888888);
        }else{

        }
    },

    on_giveFivePokers : function (pokerInfo) {
        this.setGameState(G_TYPE.grabState.givePoker)
        var pokerData = G_OBJ.data_grabPokerInfo();
        pokerData.pokerInfoList = pokerInfo;
        pokerData.pokerResult = this._list_winResult[0];
        pokerData.isFlyEffect = true;
        pokerData.startPokerIndex = 2;
        pokerData.callFunc = this._giveFivePokersEnd.bind(this);
        this.comp_pokersLayer.showPokerEX(pokerData);
    },

    _giveFivePokersEnd : function () {
        var time = G_Config_grab.time_openDelay;
        var act = cc.delayTime(time);
        this.node.runAction(cc.sequence(act, cc.callFunc(function () {
            //this.comp_pokersLayer.resetPokerInfo();
            this.comp_pokersLayer.openPokers(this.on_showResult.bind(this));
        }, this)));
    },

    on_showResult : function () {
        this.setGameState(G_TYPE.grabState.showFlag);

        if(this._list_winResult.length < 1) return;
        var pokerResult, tablePos;
        for(var i = 0; i < this._list_winResult.length; i ++){
            pokerResult = this._list_winResult[i];
            tablePos = this.comp_tablesContainer.setTableWin(pokerResult);
            this.comp_topEffect.playOneFlag(tablePos);
        }
        this.comp_tablesContainer.showOwnerGold(this.getMyselfComp().getAllGrabTable());

        this.node.runAction(cc.sequence(cc.delayTime(G_Config_grab.time_showFlag), cc.callFunc(this.on_flyGold, this)));
    },
    //金币的飞行
    on_flyGold : function () {
        this.setGameState(G_TYPE.grabState.flyGold);
        this.comp_playersContainer.setWinTableResult(this._list_winResult);
        this.node.runAction(cc.sequence(cc.delayTime(1), cc.callFunc(function () {
            //金币飞行结束后的表现
            this._flyGoldEnd();
        }, this)))
    },
    _flyGoldEnd : function () {
        var isShow = this.comp_playersContainer.showAllGoldResult();
        if(isShow){
            var act = cc.delayTime(G_Config_grab.time_goldFlyOnHead);
            this.node.runAction(cc.sequence(act, cc.callFunc(this.on_showGoldResultEnd, this)));
        }else this.on_showGoldResultEnd();
    },
    //显示头像上的金币增减结束
    on_showGoldResultEnd : function () {
        this.getMyselfComp().resetShowGold();
        //this.resetShow();
        if(G_Config_common.isLocal){
            this.on_beforeStart();
        }else {

        }
    },

    //================================

    //点击投注区域
    playMyGoldMove : function (dataObj) {
        dataObj.isMine = true;
        this.getMyselfComp().touchGrabArea(dataObj, this._ownerGrabContinue.bind(this));
    },
    //服务端投注成功
    _ownerGrabContinue : function (dataObj) {
        if(dataObj.goldValue > 0){
            this.comp_goldContainer.moveAddGolds(dataObj, function () {
                //所有金币飞行完毕
            }.bind(this));
            //请求成功则马上刷新桌子上的金额信息
            this.comp_tablesContainer.getTable(dataObj.tableIndex).addOwnerGold(dataObj.goldValue);
        }
    },
    //结算移动金币
    playResultGoldMove : function (dataList) {
        //if(dataList.length < 1) this._flyGoldEnd();
        //else
        this.comp_goldContainer.flyResultGold(dataList);
    },

    setGameState : function (type) {
        var lastType = this._gameState;
        this._gameState = type;
        switch (type){
            case G_TYPE.grabState.wait:
                //等待开始
                break;
            case G_TYPE.grabState.readyStart:
                //开始动画
                break;
            case G_TYPE.grabState.givePoker:
                //发牌
                break;
            case G_TYPE.grabState.readyTouchTable:
                //准备押注
                break;
            case G_TYPE.grabState.touchTables:
                //选区域押注
                break;
            case G_TYPE.grabState.showFlag:
                //显示胜利旗帜
                break;
            case G_TYPE.grabState.flyGold:
                //结算金币
                break;
            case G_TYPE.grabState.isBetting:
                //有下注  刷新回到房间列表
                break;
            default:
                this._gameState = lastType;
                return;
        }
    },
    //在重新设置玩家信息的时候更新按钮倍数
    initButtonInfo : function () {
        this.comp_btnsContainer.setStartInfo();
    },

    //===========================================

    //重新修改休息期间倒计时数值
    changeIdleTip : function (num) {
        if(this._idleTip && this._idleTip.getIsShow()) {
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime);
            this._idleTip.forceChangeCountDown(secs);
        }
    },
    //重新修改投注期间倒计时数值
    changeGrabTip : function (num) {
        var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
        if(this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceChangeCountDown(secs);
        }else{
            //投注动画未执行完成
            this.comp_topEffect.clearAll();

            if(!this.comp_pokersLayer.getPokerNum()){
                //还没有发第一张牌
                //this.comp_pokersLayer.addPokers([{
                //    pokerIndex : this._list_pokerIndex[0],
                //    isOpen : true
                //}], this._list_winResult[0]);

                var pokerData = G_OBJ.data_grabPokerInfo();
                pokerData.pokerInfoList = [{
                    pokerIndex : this._list_pokerIndex[0],
                    isOpen : true
                }]
                pokerData.pokerResult = this._list_winResult[0];
                pokerData.isFlyEffect = false;
                pokerData.startPokerIndex = 1;
                this.comp_pokersLayer.showPokerEX(pokerData);
            }

            this.on_startTouchTables(secs);
        }
    },
    //重新设置投注信息
    //resetTableGold : function (goldList) {
    //    if(!goldList || goldList.length < 1) return;
    //
    //    var netBeanData;
    //    for(var i = 0; i < goldList.length; i ++){
    //        netBeanData = goldList[i];
    //        var areaValue = parseInt(netBeanData.item.split('_')[0]);
    //        if(netBeanData.seatIndex===this.getMyselfComp().getSeatIndex())continue;
    //        var dataObj = {};
    //        var playerComp = this.comp_playersContainer.getCompByIndex(netBeanData.seatIndex);
    //
    //        dataObj.goldValue = netBeanData.gold;
    //        dataObj = playerComp.touchGrabArea_other(dataObj);
    //        dataObj = this.comp_tablesContainer.touchOneTable(areaValue, dataObj);
    //        if(dataObj.callFunc) dataObj.callFunc();
    //    }
    //},

    //显示窗口ui
    showDialogUI : function (uiName, callBack) {
        this.comp_uiLayer.addUI(uiName, callBack);
    },

    //===========================================
    //设置投注次数的显示
    setBettingTimes : function (times) {
        this.label_bettingTimes.string = G_TOOL.formatStr(this._bettingTimesStr, times);
    },


    getGameState : function () {
        return this._gameState
    },
    getMyselfComp : function () {
        return this.comp_playersContainer.getMyselfComp()
    },
    getTable : function (result) {
        return this.comp_tablesContainer.getTable(result);
    },
    getBtnsComp : function () {
        return this.comp_btnsContainer;
    },
    getMaxGrabGold : function () {
        return this._maxBettingGold
    },
    //测试用：主动计算区域金币值
    getWinGold : function () {
        var tableIndex, goldNum, winList=[];
        for(var i = 0; i < this._list_winResult.length; i ++){
            tableIndex = this._list_winResult[i];
            goldNum = this.getMyselfComp().getWinGoldNum(tableIndex);
            if(goldNum) winList.push(tableIndex);
        }
        return this.comp_tablesContainer.getWinGold(winList);
    },
    //每局最大的投注次数
    getMaxGrabTimes : function () {
        return this._maxBettingTimes
    },
    getMatchID : function () {
        return this._matchID
    },
    getOwnerID : function () {
        return this._playerID
    },
    _getOffSeconds : function (startTime, endTime) {
        return GG.socketMgr.getOffSeconds(startTime, endTime);
    },
    getCurGrabResult : function () {
        return this._list_winResult[0]
    },
    getHomeID : function () {
        return this._homeID
    },
    //是否已经投注结束
    getIsGrab : function () {
        return this.getMyselfComp().getIsBettingEnd();
    },
    //获取当前比赛是否已经开局
    getIsBetStart : function (_nextStartTime, _curEndGrabTime) {
        if(!_nextStartTime) _nextStartTime = this._nextStartTime;
        if(!_curEndGrabTime) _curEndGrabTime = this._curEndGrabTime;
        var serverTime = GG.socketMgr.getServerTime();
        if(_nextStartTime > serverTime && serverTime >= _curEndGrabTime){
            //未开局
            return false
        }
        return true
    },
    //第一次进入游戏是否可押注
    getFirstBetting: function getIsWait() {
        return this._startGame;
    },
    //获取进房信息
    getEnterHomeData : function () {
        return this._enterHomeData
    },
    //获取吃瓜群众投注的位置
    getIdleBettingPos : function () {
        return this.comp_btnsContainer.getIdleBettingPos()
    },
    //获取吃瓜群众的投注记录
    getIdleBetting : function () {
        return this._dict_idleBetting
    },
    //获取投注区域容器
    getTableContainer : function () {
        return this.comp_tablesContainer
    },
    //获取卡牌的放置位置
    getPokerShowPos : function () {
        return this.comp_playersContainer.getPokerPos()
    },


    //记录吃瓜群众的投注
    _recodeIdleBetting : function (tableIndex, goldImgNum) {
        if(!this._dict_idleBetting) this._dict_idleBetting = {};
        if(this._dict_idleBetting[tableIndex]) this._dict_idleBetting[tableIndex] += goldImgNum;
        else this._dict_idleBetting[tableIndex] = goldImgNum;
    },
    //开启休息时间的时候的清理
    _clearByWait : function () {
        this.getMyselfComp().clearRecord();
    },

    //on net -----------

    forceStart : function () {

        if (G_Config_common.isLocal) {
            var chip = [100, 500, 1000, 2000];
            var initData = {
                myselfData: {
                    chooseDict: chip,
                    roomName: null
                },
                othersData: [{
                    player: {
                        coin: 7568,
                        icon: null,
                        nickname: "black1",
                        playerId: 5,
                        usableBalance: 7568,
                    },
                    seatIndex: 1
                }, {
                    player: {
                        coin: 7568,
                        icon: null,
                        nickname: "black",
                        playerId: 5,
                        usableBalance: 7568,
                    },
                    seatIndex: 2
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "solider",
                        playerId: 6,
                        usableBalance: 888888,
                    },
                    seatIndex: 3
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin",
                        playerId: 7,
                        usableBalance: 888888,
                    },
                    seatIndex: 4
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin1",
                        playerId: 8,
                        usableBalance: 888888,
                    },
                    seatIndex: 5
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin2",
                        playerId: 9,
                        usableBalance: 888888,
                    },
                    seatIndex: 6
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin3",
                        playerId: 9,
                        usableBalance: 888888,
                    },
                    seatIndex: 7
                }],
                dealerData: {
                    coin: 297300,
                    icon: null,
                    nickname: "系统大牛",
                    playerId: 7,
                    usableBalance: 74325,
                },
                homeData: {
                    roomId: 1,
                    userId: 6,
                    betTimes: 10,
                    matchId: 1,
                    //beginTimeNext : 1487557651083,
                    beginTimeNext: 1487559489225,
                    //settleTime : 1487557651083,
                    settleTime: 1487559474225,
                    betChip: chip,
                    minDealerGold: 100000,
                    areaInfo:{0:2,1:13,2:13,3:13,4:13,5:13,6:13,7:13,8:13,9:13,10:12}
                },
            }
            this._initInHomeData(initData);

            //是否开局判定
            var serverTime = 1487559484012;
            if (serverTime < this._curEndGrabTime) {
                //未开局
                this.resetShow();
                //是否在游戏进行一半的时候进入
                var firstPokerIndex = 30;
                var secs = this._getOffSeconds(serverTime, this._curEndGrabTime);
                if(firstPokerIndex > 0){
                    this._list_pokerIndex[0] = firstPokerIndex;
                    //游戏中途加入,计算剩余秒数
                    if(secs > G_Config_grab.time_grabWillEnd){
                        //充足的发牌时间
                        //this.comp_pokersLayer.addPokers([{
                        //    pokerIndex : firstPokerIndex,
                        //    isOpen : true
                        //}]);

                        var pokerData = G_OBJ.data_grabPokerInfo();
                        pokerData.pokerInfoList = [{
                            pokerIndex : firstPokerIndex,
                            isOpen : true
                        }]
                        pokerData.pokerResult = 0;
                        pokerData.isFlyEffect = false;
                        pokerData.startPokerIndex = 1;
                        this.comp_pokersLayer.showPokerEX(pokerData);
                    }else{
                        //不播放发牌动画
                        this.comp_pokersLayer.onlyShowPoker([{
                            pokerIndex : firstPokerIndex,
                            isOpen : true
                        }]);
                    }
                }
                // else console.log('已经开局，但没有收到服务端卡牌信息'+firstPokerIndex)
                this.on_startTouchTables(secs);
            } else {
                //已经开局
                this._isPauseOverMatch = true;
                this.resetShow();
                var secs = this._getOffSeconds(serverTime, this._nextStartTime);
                this.on_Wait(2, this.on_beforeStart.bind(this));;
            }
        } else {

        }
    },

    testGetPoker : function (getNum) {
        var p1 = [40,44,48,5,10];
        var p2 = [36,37,39,10,11];
        var p3 = [39,41,50,13,22];
        var p4 = [5,31,38,0,29];
        var p5 = [33,34,4,36,2];
        var pokerList = [{5:p1},{6:p2},{10:p3},{9:p4},{1:p5}]
        if(!this._testPokerList) this._testPokerList = pokerList[G_TOOL.getRandomArea(0, 4)];
        if(getNum == 1){
            var info={};
            for(var i = 0; i < this._testPokerList.length; i ++){
                info.value = i;
                info.pokerIndex = this._testPokerList[i][0];
                break;
            }
            return info
        }else{
            var info={};
            for(var key = 0; key < this._testPokerList.length; key ++){
                info.value = key;
                info.pokerIndex = this._testPokerList[key];
            }
            return info
        }
    },

    //初始化押宝房间必须要有的数据
    _initInHomeData : function (initData) {
        //房间配置的数据
        if(initData.homeData){
            var data = initData.homeData;
            if(!this._homeID) this._homeID = data.roomId;
            if(!this._matchID) this._matchID = data.matchId;
            this._nextStartTime = data.beginTimeNext;
            this._curEndGrabTime = data.settleTime;
            //if(data.minDealerGold) this._minDealerGold = data.minDealerGold;
            if(data.userId){
                GG.getPlayer().setPlayerID(data.userId);
            }
            //最大投注次数
            if(data.betTimes) {
                this._maxBettingTimes = data.betTimes;
                this.setBettingTimes(this._maxBettingTimes);
            }
            //筹码选项
            if(data.betChip) {
                this._chipValueList = data.betChip;
                this._maxBettingGold = this._maxBettingTimes * data.betChip[data.betChip.length - 1];
                //设置初始化数据表现
                //this.comp_btnsLayer.initChipBtn(data.betChip);
            }
            //投注区域倍数
            if(data.areaInfo){
                this.comp_tablesContainer.setStartInfo(data.areaInfo);
            }
        }
        //玩家自己的数据
        //其他玩家的数据
        //庄家的数据
        this.comp_playersContainer.setStartInfo(initData.othersData, initData.myselfData, initData.dealerData);
        //判定庄家
        if(initData.dealerData){
            if(initData.dealerData.playerId == GG.getPlayerID()){
                //自己是庄家
                //this.setBottomIsShow(false);
            } else {
                //this.setBottomIsShow(true);
                //清除庄字特效
                //this.comp_playersLayer.getMyselfEffectComp().clearDealerWordAni();
            }
        }
    },

    //进入房间接收到的信息
    net_setHomeData : function (recvData) {
        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        this._dealInHomeNetData(recvData);

        //是否开局判定
        var serverTime = GG.socketMgr.getServerTime();
        //var isWait = this._nextStartTime > serverTime && serverTime >= this._curEndGrabTime;
        if(serverTime < this._curEndGrabTime){
            //已经开局
            this.resetShow();
            this._startGame = true;
            //是否在游戏进行一半的时候进入
            var firstPokerIndex = recvData.poker;
            var secs = this._getOffSeconds(serverTime, this._curEndGrabTime);
            if(firstPokerIndex >= 0){
                this._list_pokerIndex[0] = firstPokerIndex;
                var pokerData = G_OBJ.data_grabPokerInfo();
                pokerData.pokerInfoList = [{
                    pokerIndex : firstPokerIndex,
                    isOpen : true
                }];
                pokerData.isFlyEffect = false;
                pokerData.startPokerIndex = 1;

                this.comp_pokersLayer.showPokerEX(pokerData);
                //游戏中途加入,计算剩余秒数
                //if(secs > G_Config_grab.time_grabWillEnd){
                //    //充足的发牌时间
                //    this.comp_pokersLayer.addPokers([{
                //        pokerIndex : firstPokerIndex,
                //        isOpen : true
                //    }]);
                //}else{
                //    //不播放发牌动画
                //    this.comp_pokersLayer.onlyShowPoker([{
                //        pokerIndex : firstPokerIndex,
                //        isOpen : true
                //    }]);
                //}
            }
            // else console.log('已经开局，但没有收到服务端卡牌信息'+firstPokerIndex)
            this.on_startTouchTables(secs);
        }else{
            //未开局
            this._isPauseOverMatch = true;
            this.resetShow();
            this._startGame = false;
            var secs = this._getOffSeconds(serverTime, this._nextStartTime);
            if(secs <= 0){
                //卡时间点了,直接开始投注
                var firstPokerIndex = recvData.poker;
                secs = this._getOffSeconds(serverTime, this._curEndGrabTime);
                if(firstPokerIndex >= 0){
                    this._list_pokerIndex[0] = firstPokerIndex;
                    var pokerData = G_OBJ.data_grabPokerInfo();
                    pokerData.pokerInfoList = [{
                        pokerIndex : firstPokerIndex,
                        isOpen : true
                    }];
                    pokerData.isFlyEffect = false;
                    pokerData.startPokerIndex = 1;
                    this.comp_pokersLayer.showPokerEX(pokerData);
                }
                // else console.log('已经开局，但没有收到服务端卡牌信息'+firstPokerIndex)
                this.on_startTouchTables(secs);
            }else this.on_Wait(secs);
        }
    },

    //押宝——处理请求到的进房信息
    _dealInHomeNetData : function (recvData) {
        this._enterHomeData = recvData;
        var match = recvData.match;

        var time1 = new Date(GG.socketMgr.getServerTime());
        //console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(match.settleTime);
        //console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())

        var id = GG.getPlayerID();
        var initData = {
            myselfData : {
                chooseDict : recvData.betChip,
                roomName : recvData.roomName,
            },
            othersData : recvData.seats,
            dealerData : match.dealer,
            homeData : {
                roomId : recvData.roomId,
                userId : id,
                betTimes : recvData.betTimes,
                matchId : match.matchId,
                beginTimeNext : match.beginTimeNext,
                settleTime : match.settleTime,
                betChip : recvData.betChip,
                minDealerGold : recvData.minDealerCoin,
                areaInfo : recvData.betAreas
            },
        }
        this._initInHomeData(initData);
    },

    //重新设置开局数据
    net_resetGameData : function (recvData) {
        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        this.updateEnterHomeData(recvData);
        this.resetShow();
        var match = recvData.match;
        var id = GG.getPlayerID();
        var initData = {
            myselfData : {
                chooseDict : null,
                roomName : null,
            },
            othersData : recvData.seats,
            dealerData : match.dealer,
            homeData : {
                //roomId : recvData.roomId,
                //userId : id,
                //betTimes : recvData.betTimes,
                //matchId : match.matchId,
                beginTimeNext : match.beginTimeNext,
                settleTime : match.settleTime,
                //betChip : recvData.betChip,
                //minDealerGold : recvData.minDealerCoin,
                //areaInfo : recvData.betAreas
            },
        }
        this._initInHomeData(initData);

        //记录第一张卡牌
        this._list_pokerIndex[0] = recvData.poker;
        this.on_beforeStart();
    },

    //当有某个玩家投注,包括自己
    net_otherGrab : function (recData) {
        var tip = recData.tip;
        if(tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        var others = recData.bets, netBeanData;
        for(var i = 0; i < others.length; i ++){
            netBeanData = others[i];
            //if(netBeanData.seatIndex===this.getMyselfComp().getSeatIndex())continue;
            var areaValue = parseInt(netBeanData.item.split('_')[0])
            //投注信息
            var bettingData = G_OBJ.data_grabBetting();
            bettingData.goldNum = G_Config_grab.num_otherGrabGoldNum;
            bettingData.goldValue = netBeanData.gold;
            bettingData.tableIndex = areaValue;
            bettingData.isMine = false;

            switch (netBeanData.seatIndex){
                case this.getMyselfComp().getSeatIndex():
                    //自己的投注
                    break
                case G_DATA.getIdlePlayersSeatIndex():
                    //吃瓜群众
                    bettingData.startPos = this.comp_btnsContainer.getIdleBettingPos();
                    //自己没在座位上又有押注要扣掉玩家列表中自己的投注额
                    if (!this.getMyselfComp().getSeatIndex() && this.getMyselfComp().getIsBettingEnd()) {
                        //如果这个吃瓜群众是自己
                        bettingData.goldValue = this.getMyselfComp().reduceGoldEX(bettingData.goldValue);
                    }
                    if (bettingData.goldValue <= 0) {
                        break;
                    }
                    //有其他的吃瓜群众投注
                    bettingData = this.comp_tablesContainer.touchOneTable(bettingData.tableIndex, bettingData);
                    this.comp_goldContainer.moveAddGolds(bettingData);
                    this._recodeIdleBetting(bettingData.tableIndex, bettingData.goldNum);
                    break
                case G_DATA.getDealerSeatIndex():
                    //庄家
                    break
                default:
                    //正常座位上玩家
                    var playerComp = this.comp_playersContainer.getCompByIndex(netBeanData.seatIndex);
                    if(playerComp) {
                        bettingData.startPos = playerComp.getWorldPos();
                        playerComp.doChoose(bettingData.tableIndex, bettingData.goldNum);
                    }
                    bettingData = this.comp_tablesContainer.touchOneTable(bettingData.tableIndex, bettingData);
                    this.comp_goldContainer.moveAddGolds(bettingData);
                    break
            }
        }
    },

    //结束投注，进入结算
    net_grabEnd : function (recvData) {
        this._touchTableEnd();
        var pokerInfo = this.setBettingEndInfo(recvData);
        if(pokerInfo) this.comp_topEffect.playGrabDoneAni(this.on_giveFivePokers.bind(this), pokerInfo);
        this.on_Wait(this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime));
    },

    setBettingEndInfo : function (recvData) {
        var pokerInfo, pokerIndex, pokerData;
        if(G_Config_common.isLocal) {

        } else{
            var tip = recvData.tip;
            switch (tip.code){
                case G_TYPE.serverCodeType.success:
                    break;
                case G_TYPE.serverCodeType.noBetting:
                    break;
                default:
                    return null
            }
            this._super(recvData);
        }
        //整理卡牌数据
        pokerData = recvData.pokers[0];
        var pokerList = pokerData['code'].split(",");
        pokerInfo = [];
        for(var i = 0; i < pokerList.length; i ++){
            pokerIndex = parseInt(pokerList[i]);
            if(!isNaN(pokerIndex)){
                pokerInfo.push({
                    pokerIndex : pokerIndex,
                    isOpen : false
                });
            }
        }

        this._list_winResult.push(pokerData.cardsPoint);
        //其他玩家
        var playerList = recvData.players
        if(playerList){
            var player, playerComp;
            for(var i = 0; i < playerList.length; i ++){
                player = playerList[i];
                //if(player.playerId == GG.getPlayer().getPlayerID()) continue;

                switch (player.seatIndex){
                    case G_DATA.getDealerSeatIndex():
                        //庄家剩余金币
                        this.comp_playersContainer.getSystemComp().setGoldValue(player.balance);
                        break;
                    case G_DATA.getIdlePlayersSeatIndex():
                        break;
                    default:
                        //场上玩家
                        playerComp = this.comp_playersContainer.getCompByIndex(player.seatIndex);
                        if(playerComp) playerComp.setResultInfo(player.coin, player.balance);
                        break;
                }
            }
        }
        return pokerInfo
    },

    //结束投注，如果自己有投注行为
    net_mineHasGrab : function (recvData) {
        var tip = recvData.tip;
        if(tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        var myInfo = recvData.player;
        this.comp_playersContainer.setMyselfMoney(myInfo.coin, myInfo.balance);
    },

    //有人入座
    net_someOneInHome : function (recData) {
        var tip = recData.tip;
        if(tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        var player, playerList = recData.seats, myPlayerID = GG.getPlayerID();
        for(var i = 0; i < playerList.length; i ++){
            player = playerList[i];
            if(!player || player.player.playerId == myPlayerID) continue;
            this.comp_playersContainer.enterOnePlayer(player.seatIndex, player.player);
        }
    },

    //退出房间的请求
    net_exitHome : function (recData) {
        var tip = recData.tip;
        if(tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        GG.exitHome();
    },

    _getWaitTimeSec : function (waitTime) {
        var serverTime = GG.socketMgr.getServerTime();
        var date = new Date(waitTime - serverTime);
        return date.getSeconds()
    },


    addLongListenOnStart : function () {
        GG.socketMgr.registerLong(NetType.r_grab_reStartGame, this.net_resetGameData.bind(this));
        GG.socketMgr.registerLong(NetType.r_grabEnd, this.net_grabEnd.bind(this));
        GG.socketMgr.registerLong(NetType.r_mineHaveGrab, this.net_mineHasGrab.bind(this));
        GG.socketMgr.registerLong(NetType.r_oneInHome, this.net_someOneInHome.bind(this));
        GG.socketMgr.registerLong(NetType.r_grab_exitReturn, this.net_exitHome.bind(this));
        // GG.socketMgr.registerLong(NetType.r_idleTimeOut, this.net_idleTimeOut.bind(this));
        GG.socketMgr.registerLong(NetType.r_otherGrab, this.net_otherGrab.bind(this));
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    onDestroy : function () {
        this._super();
        GG.grabMgr = null;
    }
});
