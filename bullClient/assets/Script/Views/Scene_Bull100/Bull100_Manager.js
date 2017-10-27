//百人大战

var _indexToItemStr = {
    0:'SPADE_W',                                                                                                         //黑桃赢
    1:'SPADE_L',
    2:'HEART_W',                                                                                                       //方片赢
    3:'HEART_L',
    4:'CLUB_W',                                                                                                          //梅花赢
    5:'CLUB_L',
    6:'DIAMOND_W',                                                                                                         //红桃赢
    7:'DIAMOND_L',
}
var _itemToIndex = {
    'SPADE_W':'0',                                                                                                         //黑桃赢
    'SPADE_L':'1',
    'HEART_W':'2',                                                                                                       //方片赢
    'HEART_L':'3',
    'CLUB_W':'4',                                                                                                          //梅花赢
    'CLUB_L':'5',
    'DIAMOND_W':'6',                                                                                                         //红桃赢
    'DIAMOND_L':'7',
}

var initData = {
    myselfData : null,                                                                                                    //玩家自己的数据
    othersData : null,                                                                                                    //其他玩家的数据
    dealerData : null,                                                                                                    //庄家的数据
    homeData : null,                                                                                                    //房间配置的数据
}

cc.Class({
    extends: require('BaseManager'),

    properties: {
        _gameState : null,                                                                                               //游戏进程
        _idleTip : null,                                                                                                 //提示休息中的tip
        _grabTip : null,                                                                                                 //提示投注中的tip
        _isMyDealer : null,                                                                                              //庄家是否是自己

        _enterHomeData : null,                                                                                           //入房信息
        _homeID : null,                                                                                                   //房间ID
        _matchID : null,                                                                                                  //赛事ID
        _nextStartTime : null,                                                                                           //下局赛事开始时间点
        _curEndGrabTime : null,                                                                                          //当前赛事结束时间点
        _playerID : null,                                                                                                 //玩家自己的ID
        _maxBettingTimes : null,                                                                                          //最大投注次数
        _maxBettingGold : null,                                                                                           //最大投注金币
        _chipValueList : null,                                                                                            //筹码选项值
        _homeName : null,                                                                                                  //房间名字
        _matchEndData : null,                                                                                     //结算信息
        _dealerNetType : null,                                                                                           //当前请求的是上庄还是续庄还是请求列表的类型
        _minDealerGold : null,                                                                                            //最小上庄金额
        _isPauseOverMatch : null,                                                                                        //暂停的时候是否进入新的一局
        _isDealerSign : null,                                                                                            //是否是庄家
        _dict_idleBetting : null,                                                                                         //吃瓜群众的投注记录

        comp_topEffect : {
            default : null,
            type : require('Bull100_topEffect'),
            displayName : '动画容器'
        },
        comp_pokerLayer : {
            default : null,
            type : require('Bull100_pokerContainer'),
            displayName : '卡牌容器'
        },
        comp_playersLayer : {
            default : null,
            type : require('Bull100_playerContainer'),
            displayName : '玩家容器'
        },
        comp_tablesLayer : {
            default : null,
            type : require('Bull100_tableContainer'),
            displayName : '桌子容器'
        },
        comp_goldsLayer : {
            default : null,
            type : require('Obj_goldsContainer'),
            displayName : '金币容器'
        },
        comp_btnsLayer : {
            default : null,
            type : require('Bull100_btnContainer'),
            displayName : '底部按钮容器'
        },
        comp_uiLayer : {
            default : null,
            type : require('Obj_uiContainer'),
            displayName : '窗口界面容器'
        },
        comp_pokerResultLayer : {
            default : null,
            type : require('Bull100_pokerResultContainer'),
            displayName : '卡牌结果显示容器'
        },
        label_bettingTimes : {
            default : null,
            type : cc.Label,
            displayName : '投注次数'
        },
    },

    // use this for initialization
    onLoad: function () {
        GG.bull100Mgr = this;
        this._super();
        this._isDealerSign = false;

        this._bettingTimesStr = G_CHINESE.limitBetting;
        this._isPauseOverMatch = true;
    },

    //本地测试时候使用的数据
    _initLocalData : function () {
        if(G_Config_common.isLocal){
            var chip = [100, 500, 1000, 2000, 5000];
            var initData = {
                myselfData : {
                    chooseDict : chip,
                    roomName : null
                },
                othersData : [{
                    player : {
                        coin: 7568,
                        icon: null,
                        nickname: "black1",
                        playerId: 5,
                        usableBalance: 7568,
                    },
                    seatIndex: 1
                },{
                    player : {
                        coin: 7568,
                        icon: null,
                        nickname: "black",
                        playerId: 5,
                        usableBalance: 7568,
                    },
                    seatIndex: 2
                },{
                    player : {
                        coin: 888888,
                        icon: null,
                        nickname: "solider",
                        playerId: 6,
                        usableBalance: 888888,
                    },
                    seatIndex: 3
                },{
                    player : {
                        coin: 888888,
                        icon: null,
                        nickname: "jin",
                        playerId: 7,
                        usableBalance: 888888,
                    },
                    seatIndex: 4
                },{
                    player : {
                        coin: 888888,
                        icon: null,
                        nickname: "jin1",
                        playerId: 8,
                        usableBalance: 888888,
                    },
                    seatIndex: 5
                },{
                    player : {
                        coin: 888888,
                        icon: null,
                        nickname: "jin2",
                        playerId: 9,
                        usableBalance: 888888,
                    },
                    seatIndex: 6
                },{
                    player : {
                        coin: 888888,
                        icon: null,
                        nickname: "jin3",
                        playerId: 9,
                        usableBalance: 888888,
                    },
                    seatIndex: 7
                }],
                dealerData : {
                    coin: 297300,
                    icon: null,
                    nickname: "系统大牛",
                    playerId: 7,
                    usableBalance: 74325,
                },
                homeData : {
                    roomId : 1,
                    userId : 6,
                    betTimes : 100,
                    matchId : 1,
                    //beginTimeNext : 1487557651083,
                    beginTimeNext : 1487559489225,
                    //settleTime : 1487557651083,
                    settleTime : 1487559474225,
                    betChip : chip,
                    minDealerGold : 100000,
                },
            }
            this._initInHomeData(initData);

            //是否开局判定
            var serverTime = 1487559484012;
            if(this._nextStartTime > serverTime && serverTime >= this._curEndGrabTime){
                //未开局
                var secs = this._getOffSeconds(serverTime, this._nextStartTime);
                this.cmd_wait(secs, this.cmd_startReady.bind(this));
            }else{
                //已经开局
                this._givePokers(true);
            }
        }else{

        }
    },
    //初始化房间必须要有的数据
    _initInHomeData : function (initData) {
        //房间配置的数据
        if(initData.homeData){
            var data = initData.homeData;
            if(!this._homeID) this._homeID = data.roomId;
            this._matchID = data.matchId;
            this._nextStartTime = data.beginTimeNext;
            this._curEndGrabTime = data.settleTime;
            if(data.minDealerGold) this._minDealerGold = data.minDealerGold;
            if(data.betTimes) this._maxBettingTimes = data.betTimes;
            if(data.betChip) {
                this._chipValueList = data.betChip;
                this._maxBettingGold = this._maxBettingTimes * data.betChip[data.betChip.length - 1];
                //设置初始化数据表现
                this.comp_btnsLayer.initChipBtn(data.betChip);
            }
            if(data.userId) {
                GG.getPlayer().setPlayerID(data.userId);
                this._playerID = GG.getPlayer().getPlayerID();
            }
        }
        //玩家自己的数据
        //其他玩家的数据
        //庄家的数据
        this.comp_playersLayer.setStartInfo(initData.othersData, initData.myselfData, initData.dealerData);

        //判定庄家
        if(initData.dealerData){
            if(initData.dealerData.playerId == GG.getPlayerID()){
                //自己是庄家
                this.setBottomIsShow(false);
                this._isDealerSign = true;
            } else {
                this.setBottomIsShow(true);
                //清除庄字特效
                this.comp_playersLayer.getMyselfEffectComp().clearDealerWordAni();
            }
        }
        //初始投注次数
        this.setBettingTimes(this._maxBettingTimes);
    },

    start : function () {
        this.resetShow();

        if(G_Config_common.isLocal){
            this._initLocalData();
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

    testExitBtn : function () {
        //this.comp_uiLayer.showPlayerList();
        //var time1 = new Date(1487835810156);
        //console.log('时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        //var time2 = new Date(1487835795156);
        //console.log('时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
        //this._givePokers(true);
        //GG.tipsMgr.addNotice('好好好好好好好好好好好好好好好好好好好好');
        return true
        //
        //var time1 = new Date(1487596437912);
        //console.log('时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        //var time2 = new Date(1487596422912);
        //console.log('时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
    },

    //开启休息时间的时候的清理
    _clearByWait : function () {
        this.getMyselfComp().clearBettingGold();
    },


    //游戏流程===============================================

    //执行游戏休息
    cmd_wait : function (retainTime, callFunc) {
        this._gameState = G_TYPE.bull100State.wait;
        if(this._grabTip && this._grabTip.getIsShow()){
            this._grabTip.forceEnd();
        }
        this.comp_tablesLayer.setTableTouchEnable(false);
        var time1 = new Date(GG.socketMgr.getServerTime());
        // console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(this._nextStartTime);
        // console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
        if(this._idleTip && this._idleTip.getIsShow()) {
            //已经存在该tip
            this._idleTip.forceChangeCountDown(retainTime);
        }else{
            var pos = G_DATA.getBottomTipPos();
            var dataObj = {
                tipIndex : 19,
                isCountDown : true,
                showPos : cc.p(pos.x, pos.y*1.3),
                //callBack : this.on_beforeStart.bind(this)
            }
            dataObj.retainTime = retainTime;
            if(callFunc) dataObj.callBack = callFunc;
            this._idleTip = GG.tipsMgr.showOnlyOne(dataObj);
            //this._idleTip.setIsShow(true);
        }

        this._clearByWait();

    },

    //投注前的动画
    cmd_startReady : function () {
        this._gameState = G_TYPE.bull100State.startReady;
        if(this._idleTip && this._idleTip.getIsShow()) {
            this._idleTip.forceEnd();
        }
        //庄家庄字特效显示
        this.comp_playersLayer.getDealerWordEffectComp().playDealerWordAni();

        this.comp_topEffect.playStartGameAni(function () {
            //this.comp_topEffect.playStartBettingAni(function () {
            //
            //}.bind(this));
            var secs;
            if(G_Config_common.isLocal){
                secs = 10;
            }else {
                secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            }
            //if(this.getMyselfComp().getIsDealer()) this.cmd_startBetting(secs);
            //else{
            //    if(!G_Config_common.isLocal) secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            //    this.cmd_startBetting(secs);
            //}
            this.cmd_startBetting(secs);
        }.bind(this));

        if (this.getMyselfComp().getIsDealer()) {//如果是庄家开始动画播放完就进入等待玩家投注倒计时
            this._givePokers(true);
        } else {
            this._givePokers(false);
        }
    },
    _givePokers : function (isOverAni) {
        //发牌
        var dealerPokerPos = this.comp_playersLayer.getDealerPokerPos();
        var pokerPosList = this.comp_tablesLayer.getTablePokerPos();
        pokerPosList.push(dealerPokerPos);
        this.comp_pokerLayer.giveCoverPokers(pokerPosList, function () {
            //发牌结束
        }.bind(this), isOverAni);
    },
    //开始投注
    cmd_startBetting : function (retainTime) {
        var isDealer = this.getMyselfComp().getIsDealer();
        if (!isDealer) {
            this.comp_tablesLayer.setTableTouchEnable(true);
            this.comp_btnsLayer.refreshCanChooseBtn();
            // this.refreshBtnChoose();
        }
        var secs=0;
        var pos = G_DATA.getBottomTipPos();
        var dataObj = {
            tipIndex : 18,
            showPos : cc.p(pos.x, pos.y*1.3),
            isCountDown : true,
            callBack : this.cmd_bettingEnd.bind(this)
        }
        dataObj.retainTime = retainTime;
        //是庄家倒计时显示内容替换
        if(G_Config_common.isLocal) dataObj.retainTime = 10;
        if(isDealer) dataObj.showStr = G_CHINESE.isDealerTip;
        this._grabTip = GG.tipsMgr.showOnlyOne(dataObj);
    },

    //投注结束
    cmd_bettingEnd : function (target) {
        this.comp_tablesLayer.setTableTouchEnable(false);
        this.comp_btnsLayer.setBtnGroupEnable(false);
        if(this._grabTip && this._grabTip.getIsShow()){
            this._grabTip.forceEnd();
        }

        //显示休息中
        var secs;
        if(G_Config_common.isLocal){
            this._winDict = {0:1,2:1,5:1,7:1};
            var dataList = [];
            for(var i = 0; i < 5; i ++){
                var pokerInfo = [];
                for(var j = 0; j < 5; j ++){
                    pokerInfo.push({
                        pokerIndex : 13,
                        isOpen : false
                    })
                }
                dataList.push(pokerInfo);
            }
            var pokerValueList = [10,2,3,4,5]

            this._initMatchEndData();
            this._matchEndData.pokersInfoList = dataList;
            this._matchEndData.pokersResultList = pokerValueList;

            this._matchEndData.myselfInfo = {
                changeGold : 44444*(G_TOOL.getRandomBool()?1:-1),
                leaveGold : 999666,
            };

            this._matchEndData.dealerInfo = {
                leaveGold : 8888888,
            };

            this._matchEndData.othersInfo = {
                2:{
                    changeGold : 11111*(G_TOOL.getRandomBool()?1:-1),
                    leaveGold : 33333,
                    name : '黑人1',
                },
                3:{
                    changeGold : 44444*(G_TOOL.getRandomBool()?1:-1),
                    leaveGold : 999666,
                    name : '黑人2',
                }
            };

            secs = 10;
            this.cmd_wait(secs);
        }

        if(this._winDict){
            //确认接收到了结算信息，才能继续
            this.comp_topEffect.playBettingEnd(function () {
                this._showWhenGotEndInfo();
            }.bind(this));
        }
    },
    //显示结算信息
    _showWhenGotEndInfo : function () {
        if(!this._matchEndData) {
            this._resetGameWhenEnd();
            return;
        }
        //设置卡牌信息
        if(this._matchEndData.pokersInfoList && this._matchEndData.pokersResultList){
            this.comp_pokerLayer.setPokersData(this._matchEndData.pokersInfoList, this._matchEndData.pokersResultList);
        }
        //开牌
        //if(!this._winDict) this._winDict = {0:1,2:1,5:1,7:1};
        if(this._winDict) this.comp_pokerLayer.openPokers(this._winDict);
        var act1 = cc.delayTime(3);
        this.node.runAction(cc.sequence(act1, cc.callFunc(function () {
            //设置玩家信息,头像上的金币增减效果
            if(this._matchEndData.dealerInfo) this.comp_playersLayer.getDealerComp().setGoldValue(this._matchEndData.dealerInfo.leaveGold);
            if(this._matchEndData.myselfInfo){
                var myselfComp = this.getMyselfComp();
                myselfComp.setGoldValue(this._matchEndData.myselfInfo.leaveGold);
                myselfComp.showGoldChange(this._matchEndData.myselfInfo.changeGold);
            }
            if(this._matchEndData.othersInfo){
                var playerInfo, playerComp;
                for(var seatIndex in this._matchEndData.othersInfo){
                    playerInfo = this._matchEndData.othersInfo[seatIndex];
                    if(playerInfo){
                        playerComp = this.comp_playersLayer.getPlayerComp(seatIndex);
                        if(playerComp) {
                            //这个时候获取到的余额是总额
                            //playerComp.setGoldValue(playerInfo.leaveGold);
                            playerComp.showGoldChange(playerInfo.changeGold);
                        }
                    }
                }
            }
            this._openPokerEnd();
        }, this)));
    },
    //开牌后的处理
    _openPokerEnd : function (target) {
        //this._matchEndData = null;
        //移动金币
        this.comp_playersLayer.flyWinGold(this._winDict);
        //显示结果面板
        var act = cc.delayTime(2);
        this.node.runAction(cc.sequence(act, cc.callFunc(function () {
            var data = {}
            if(this._matchEndData){
                if(this._matchEndData.myselfInfo) data.myChangeGold = this._matchEndData.myselfInfo.changeGold;
                if(this._matchEndData.othersInfo) data.othersInfo = this._matchEndData.othersInfo;
                this.comp_topEffect.showResultEffect(data, this._resetGameWhenEnd.bind(this));
            }else this._resetGameWhenEnd();
        }, this)));
    },
    //开牌显示完成后，进行新的倒计时
    _resetGameWhenEnd : function () {
        this.changeIdleTip();
        if(G_Config_common.isLocal){
            this.resetShow();
            this._initLocalData();
        }
    },

    //=======================================================

    //清理界面中的所有显示效果
    resetShow : function () {
        this.node.stopAllActions();
        //管理器中的存储数据
        this._isMyDealer = false;
        this._winDict = null;
        this._matchEndData = null;
        //玩家
        //this.comp_playersLayer.clearAll();
        //卡牌
        this.comp_pokerLayer.clearAll();
        //如果已经结束一局，则清理以下数据
        if(this._isPauseOverMatch) {
            //金币
            this.comp_goldsLayer.clearAll();
            //桌子
            this.comp_tablesLayer.initAllTables();
            //清理吃瓜群众的投注记录
            this._dict_idleBetting = null;
        }
        else this._isPauseOverMatch = true;
        //按钮
        this.comp_btnsLayer.setStartInfo();
        //动画特效
        this.comp_topEffect.clearAll();
        //tip提示
        //GG.tipsMgr.forceStopAllTips();
        //显示底部的按钮和头像
        // this.setBottomIsShow(true);
        //隐藏上庄列表
        var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
        if(comp) comp.hideLayer();
        //清理卡牌结果显示
        this.comp_pokerResultLayer.clearAll();
    },
    //根据用户操作投放金币
    //flyGold : function (goldList) {
    //    this.comp_goldsLayer.flyGolds(goldList);
    //},
    //玩家点击了某个桌子
    touchTable : function (tableIndex, posList) {
        if(this.getMyselfComp().getIsDealer()){
            //是庄家
            return
        }

        var dataObj = G_OBJ.data_bullBetting();
        dataObj.tableIndex = tableIndex;
        dataObj.posList = posList;
        dataObj.isMine = true;

        //记录玩家事件
        this.getMyselfComp().touchTable(dataObj, this._touchTableSuccess.bind(this));
    },
    //投注成功后的表现
    _touchTableSuccess : function (bettingData) {
        var tableIndex = bettingData.tableIndex;
        var posList = bettingData.posList;
        //整理金币飞行需要的信息
        //var flyDataList = [];
        //for(var i = 0; i < posList.length; i ++){
        //    var dataObj = {
        //        startPos : bettingData.startPos,
        //        targetPos : posList[i],
        //        tableIndex : tableIndex,
        //        callFunc : null
        //    }
        //    flyDataList.push(dataObj);
        //}
        //显示桌子的投注成功信息
        this.comp_tablesLayer.setBettingSuccess(tableIndex, bettingData.goldValue, bettingData.isMine);
        //显示金币的飞行
        //this.flyGold(flyDataList);
        var flyDataList = G_OBJ.data_flyGold_playerToTable();
        flyDataList.tableIndex = tableIndex;
        flyDataList.goldNum = bettingData.posList.length;
        flyDataList.startPos = bettingData.startPos;
        this.comp_goldsLayer.playerToTable(flyDataList);
    },
    //金币结算效果
    goldRecover : function (recoverDict, targetPos) {
        var goldNum;
        for(var tableIndex in recoverDict){
            goldNum = recoverDict[tableIndex];

            var flyDataList = G_OBJ.data_flyGold_tableToPlayer();
            flyDataList.tableIndex = tableIndex;
            flyDataList.goldNum = goldNum;
            flyDataList.targetPos = targetPos;
            this.comp_goldsLayer.tableToPlayer(flyDataList);
        }
    },
    //刷新按钮的筹码显示结果
    refreshBtnChoose : function () {
        // this.comp_btnsLayer.setBtnGroupEnable(true);
        // this.comp_btnsLayer.changeBet();//下注开始之后设置可押注的按钮
        // var choose = this.getMyselfComp().getChooseChip();
        // this.comp_btnsLayer.chooseMoney(choose);
    },
    //显示某张桌子的胜利, 在卡牌容器中，打开所有卡牌后调用
    showTableWin : function (tableIndex, multiple) {
        this.comp_tablesLayer.showTableWin(tableIndex, multiple);
    },

    //=============================================

    //显示上庄列表窗口
    showUI_dealerList : function (dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.dealerList, function (layerComp) {
            if(dataObj){
                layerComp.showLayer();
                layerComp.setData(dataObj);
            }
        });
    },
    //显示玩家列表窗口
    showUI_playerList : function (dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.playerList, function (layerComp) {
            layerComp.setData(dataObj);
        });
    },
    //显示走势图列表窗口
    showUI_trendList : function (dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.bull100Trend, function (layerComp) {
            layerComp.setData(dataObj);
        });
    },
    //显示规则窗口
    showUI_rules : function (dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.rulesInHome, function (layerComp) {
            layerComp.showLayer();
            layerComp.setData(dataObj);
        });
    },

    //============================================= 暂停后服务器数据处理

    //重新修改投注期间倒计时数值
    changeGrabTip : function (num) {
        var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
        if(this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceChangeCountDown(secs);
        }else{
            //投注动画未执行完成
            this.comp_topEffect.clearAll();

            if(this.comp_pokerLayer.getIsGivePokerEnd()){
                //已经发完牌
            }else{
                //未发牌
                this._givePokers(true);
            }
            this.cmd_startBetting(secs);
        }
    },
    //重新修改休息期间倒计时数值
    changeIdleTip : function (num) {
        if(this._idleTip && this._idleTip.getIsShow()) {
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime);
            if(num) secs = num;
            this._idleTip.forceChangeCountDown(secs);
        }
    },
    //重新设置投注信息
    resetTableGold : function (goldList) {
        if(!goldList || goldList.length < 1) return;

        var netBeanData;
        for(var i = 0; i < goldList.length; i ++){
            netBeanData = goldList[i];
            var areaValue = parseInt(netBeanData.item.split('_')[0]);
            if(netBeanData.seatIndex===this.getMyselfComp().getSeatIndex())continue;
            var dataObj = {};
            var playerComp = this.comp_playersContainer.getCompByIndex(netBeanData.seatIndex);

            dataObj.goldValue = netBeanData.gold;
            dataObj = playerComp.touchGrabArea_other(dataObj);
            dataObj = this.comp_tablesContainer.touchOneTable(areaValue, dataObj);
            if(dataObj.callFunc) dataObj.callFunc();
        }
    },


    //========================================  庄家的相关操作

    //请求上庄列表
    send_getDealerList : function () {
        if(G_Config_common.isLocal){
            var dealerInfo = {
                playerId:9,//玩家id
                icon:'',//玩家头像
                nickname:'玩家1',//玩家昵称
                coin:210000,//玩家余额
                maxDealerCoin:210000, // 最大上庄金额
                usableBalance : 200000, //可用金额
            };
            var playerList = [
                {
                    playerId : 1,
                    headImg : '',
                    nickname : '玩家3',
                    coin : 99999,
                    usableBalance : 11111,
                },
                {
                    playerId : 2,
                    headImg : '',
                    nickname : '玩家2',
                    coin : 99999,
                    usableBalance : 11111,
                }
            ]
            var dataObj = {
                //myGold : GG.bull100Mgr.getMyselfComp().getGoldValue(),
                myGold : dealerInfo.usableBalance,
                dealerInfo : dealerInfo,
                rankList : playerList,
            }
            this.showUI_dealerList(dataObj);
        }else{
            var netData = {}
            this.setDealerNetType(G_TYPE.dealerNetType.getList);
            GG.socketMgr.SendMsg(NetType.s_bull100_getDealerList, netData);
        }
    },
    //请求上庄
    send_upDealer : function (sendGold) {
        if(G_Config_common.isLocal) {
            var dealerInfo = {
                playerId:6,//玩家id
                icon:'',//玩家头像
                nickname:'玩家1',//玩家昵称
                coin:110000,//玩家余额
                maxDealerCoin:110000, // 最大上庄金额
                usableBalance : 100000, //可用金额
            };
            var playerList = [
                {
                    playerId : 1,
                    headImg : '',
                    nickname : '玩家3',
                    coin : 99999,
                    usableBalance : 11111,
                },
                {
                    playerId : 2,
                    headImg : '',
                    nickname : '玩家2',
                    coin : 99999,
                    usableBalance : 11111,
                },
                {
                    playerId : 3,
                    headImg : '',
                    nickname : '玩家5',
                    coin : 9909,
                    usableBalance : 1101,
                }
            ]
            var dataObj = {
                //myGold : GG.bull100Mgr.getMyselfComp().getGoldValue(),
                myGold : dealerInfo.usableBalance,
                dealerInfo : dealerInfo,
                rankList : playerList,
                tipStr : '上庄成功！！！'
            }
            var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
            if(comp) comp.upDealerSuccess(dataObj);
        }else{
            var netData = {
                coin : sendGold
            };
            this.setDealerNetType(G_TYPE.dealerNetType.upDealer);
            GG.socketMgr.SendMsg(NetType.s_bull100_dealerUp, netData);
        }
    },
    //请求下庄
    send_downDealer : function () {
        var isDealer = this.getMyselfComp().getIsDealer();
        if (isDealer) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.giveUpDealer2, this._commitDownDealer.bind(this));
            var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
            if(comp) comp.hideLayer();
        } else {
            this._commitDownDealer();
        }
    },
    //确认下庄
    _commitDownDealer : function () {
        var netData = {};
        GG.socketMgr.SendMsg(NetType.s_bull100_dealerDown, netData);

    },
    //请求续庄  cancelCallFunc: 取消续庄的时候调用
    send_continueDealer : function (sendGold, cancelCallFunc) {
        GG.tipsMgr.showConfirmTip_TWO(G_TOOL.formatStr(G_CHINESE.continueDealer, sendGold), function () {
            this.commitContinueDealer(sendGold);
        }.bind(this), cancelCallFunc)
    },
    //主动确认续庄家
    commitContinueDealer : function (sendGold) {
        var netData = {
            coin : sendGold
        };
        GG.bull100Mgr.setDealerNetType(G_TYPE.dealerNetType.continueDealer);
        GG.socketMgr.SendMsg(NetType.s_bull100_dealerContinue, netData);
    },

    //==========================================

    //上庄后隐藏底部
    setBottomIsShow : function (isShow) {
        this.comp_btnsLayer.setBottomIsShow(isShow);
        //玩家庄字特效显示
        this.comp_playersLayer.getMyselfEffectComp().playDealerWordAni();
        // this.comp_playersLayer.setMyselfIsShow(isShow);
    },
    //设置当前点击请求的庄家相关的请求类型
    setDealerNetType : function (type) {
        this._dealerNetType = type;
    },
    //设置投注次数的显示
    setBettingTimes : function (times) {
        this.label_bettingTimes.string = G_TOOL.formatStr(this._bettingTimesStr, times);
    },

    //通过特效层显示卡牌的结果
    showPokerResult : function (pos, pokerValue) {
        this.comp_pokerResultLayer.showPokerResult(pos, pokerValue);
    },

    //获取玩家操作时的位置
    getMyselfPos : function () {
        return this.comp_playersLayer.getMyselfPos()
    },
    //获取玩家对象
    getMyselfComp : function () {
        return this.comp_playersLayer.getMyselfComp()
    },
    //获取在座位上的玩家
    getPlayerCompInSite : function (seatIndex) {
        return this.comp_playersLayer.getPlayerComp(seatIndex);
    },
    //获取玩家当前投注需要飞行的金币数量
    getMyselfFlyGoldNum : function () {
        return this.getMyselfComp().getFlyGoldNum()
    },
    //获取玩家当前投注需要增加的金币数量
    getMyselfBetGoldValue : function () {
        return this.getMyselfComp().getChooseGoldValue()
    },
    //获取金币的宽高
    getGoldImgSize : function () {
        return this.comp_goldsLayer.getGoldSize()
    },
    //获取最大的投注金额
    getMaxBettingGold : function () {
        return this._maxBettingGold
    },
    //获取最大的投注次数
    getMaxBettingTimes : function () {
        return this._maxBettingTimes
    },
    getMatchID : function () {
        return this._matchID
    },
    getBtnsComp : function () {
        return this.comp_btnsLayer;
    },
    //当知道桌子的索引的时候，可以获取取大的格子的索引
    getBigTableIndex : function (index) {
        return index*2
    },
    //当知道桌子的索引的时候，可以获取取小的格子的索引
    getMinTableIndex : function (index) {
        return index*2+1
    },
    //当知道桌子的大小索引的时候，桌子容器中的桌子索引
    getTableContainerIndex : function (index) {
        var containerIndex;
        if(index%2==0){
            //偶数
            containerIndex = index/2;
        }else{
            containerIndex = (index-1)/2;
        }
        return containerIndex
    },
    //获取桌子索引对应的投注项表示
    getItemByTableIndex : function (tableIndex) {
        return _indexToItemStr[tableIndex]
    },
    //获取投注项表示对应的桌子索引
    getTableIndexByItem : function (itemStr) {
        return _itemToIndex[itemStr]
    },
    //获取筹码选项值
    getChipValueList : function () {
        return this._chipValueList
    },
    //获取进房信息
    getEnterHomeData : function () {
        return this._enterHomeData
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
    //获取房间名字
    getHomeName : function () {
        return this._homeName
    },
    //获取房间ID
    getHomeID : function () {
        return this._homeID
    },
    //是否已经投注
    getIsBetting : function () {
        return this.getMyselfComp().getIsBetting();
    },
    //获取最小上庄金额
    getMinDealerGold : function () {
        return this._minDealerGold
    },
    //本地是否为庄家状态
    getDealerState : function () {
        return this._isDealerSign;
    },
    //获取桌子容器
    getTableContainer : function () {
        return this.comp_tablesLayer
    },
    //获取金币容器
    getGoldContainer : function () {
        return this.comp_goldsLayer
    },
    //获取吃瓜群众的投注位置
    getIdleBettingPos : function () {
        return this.comp_btnsLayer.getIdleBettingPos()
    },
    //获取吃瓜群众的投注记录
    getIdleBetting : function () {
        return this._dict_idleBetting
    },
    //第一次进入游戏是否可押注
    getFirstBetting: function getIsWait() {
        return this._startGame;
    },

    //======================================增加对玩家自己金额变化的监听

    addMyGoldListener : function () {
        this._listenerName = GG.Listener.registerFunc(G_TYPE.globalListener.playerGold, this.on_goldChange.bind(this));
    },
    on_goldChange : function (listenerName, dataObj) {
        if(this.node.active){
            var dataObj = G_OBJ.data_selfMatchEndData();
            dataObj.leaveGold = parseInt(dataObj.balance);
            this.setMatchEndMySelfData(dataObj);
        }
    },


    //设置结算时候自己的金币信息
    setMatchEndMySelfData : function (dataObj) {
        this._initMatchEndData();
        //var dataObj = G_OBJ.data_selfMatchEndData();
        if(!this._matchEndData.myselfInfo) this._matchEndData.myselfInfo = G_OBJ.data_selfMatchEndData();
        if(Object.prototype.toString.call(dataObj.changeGold)==='[object Number]') {
            this._matchEndData.myselfInfo.changeGold = dataObj.changeGold;
        }
        if(Object.prototype.toString.call(dataObj.leaveGold)==='[object Number]') {
            this._matchEndData.myselfInfo.leaveGold = dataObj.leaveGold;
        }
    },
    //当有吃瓜群众投注的时候记录信息
    _addIdleBetting : function (tableIndex, goldImgNum) {
        if(!this._dict_idleBetting) this._dict_idleBetting = {};
        if(!this._dict_idleBetting[tableIndex]) this._dict_idleBetting[tableIndex] = goldImgNum;
        else this._dict_idleBetting[tableIndex] += goldImgNum
    },
    //初始化结算信息
    _initMatchEndData : function () {
        if(!this._matchEndData) this._matchEndData = G_OBJ.data_matchEnd();
    },

    //===============================================

    addLongListenOnStart : function () {
        GG.socketMgr.registerLong(NetType.r_bull100_reStartGame, this.net_resetGameData.bind(this));
        GG.socketMgr.registerLong(NetType.r_grabEnd, this.net_bettingEnd.bind(this));
        GG.socketMgr.registerLong(NetType.r_otherGrab, this.net_otherGrab.bind(this));
        GG.socketMgr.registerLong(NetType.r_mineHaveGrab, this.net_mineHasGrab.bind(this));
        GG.socketMgr.registerLong(NetType.r_oneInHome, this.net_someOneInHome.bind(this));

        GG.socketMgr.registerLong(NetType.r_grab_exitReturn, this.net_exitHome.bind(this));
        // GG.socketMgr.registerLong(NetType.r_idleTimeOut, this.net_idleTimeOut.bind(this));
        GG.socketMgr.registerLong(NetType.r_bull100_dealerDownWarning, this.net_dealerContinueWarning.bind(this));
        GG.socketMgr.registerLong(NetType.r_bull100_dealerListReturn, this.net_returnDealerList.bind(this));
        GG.socketMgr.registerLong(NetType.r_bull100_downDealerReturn, this.net_returnDownDealer.bind(this));
    },

    //百人——进入房间接收到的信息
    net_setHomeData : function (recvData) {
        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            return;
        }
        this._dealInHomeNetData(recvData);
        //是否开局判定
        var serverTime = GG.socketMgr.getServerTime();
        if(serverTime < this._curEndGrabTime){
            //投注时间
            if(this._idleTip && this._idleTip.getIsShow()) {
                //已经存在该tip
                this._idleTip.forceEnd();
            }
            this.resetShow();
            this._startGame = true;
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            this.cmd_startBetting(secs);
            this._givePokers(true);
        }else{
            //未开局
            this._isPauseOverMatch = true;
            this.resetShow();
            this._startGame = false;
            var secs = this._getOffSeconds(serverTime, this._nextStartTime);
            if(secs <= 0){
                //卡时间点了,直接开始投注
                secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
                this.cmd_startBetting(secs);
                this._givePokers(true);
            }else this.cmd_wait(secs);
        }
    },

    //处理进房信息
    _dealInHomeNetData : function (recvData) {
        this._enterHomeData = recvData;
        var match = recvData.match;

        var time1 = new Date(GG.socketMgr.getServerTime());
        // console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(match.beginTimeNext);
        // console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())

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
            },
        }
        this._initInHomeData(initData);
    },

    //百人——重新设置开局数据
    net_resetGameData : function (recvData) {
        GG.tipsMgr.clearConfirm(); //清理被动续庄

        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        //局信息
        this.updateEnterHomeData(recvData);
        var match = recvData.match;
        var initData = {
            myselfData : {
                chooseDict : null,
                roomName : null
            },
            othersData : recvData.seats,
            dealerData : match.dealer,
            homeData : {
                roomId : recvData.roomId,
                userId : null,
                betTimes : null,
                matchId : match.matchId,
                beginTimeNext : match.beginTimeNext,
                settleTime : match.settleTime,
                betChip : null,
            },
        }
        this._initInHomeData(initData);
        this.resetShow();
        this.cmd_startReady();
    },

    //百人——结束投注，进入结算
    net_bettingEnd : function (recvData) {
        var isRight = this.setBettingEndInfo(recvData);
        //结束投注，进入结算
        if(isRight) {
            this.cmd_bettingEnd();
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime);
            this.cmd_wait(secs);
        }
    },

    //百人——设置结算信息的数据
    setBettingEndInfo : function (recvData) {
        var tip = recvData.tip;
        switch (tip.code){
            case G_TYPE.serverCodeType.success:
                break;
            case G_TYPE.serverCodeType.noBetting:
                break;
            default:
                return false
        }
        this._super(recvData);

        this._winDict = {};
        var pokerIndex, dataList = [];
        var pokerData, dealerOdds;
        var pokerValueList = [];
        for(var i = 0;i < recvData.pokers.length; i ++){
            pokerData = recvData.pokers[i];
            var pokerList = pokerData['code'].split(",");//第一个是庄家
            var pokerInfo = [];
            for(var j = 0; j < pokerList.length; j ++){
                pokerIndex = parseInt(pokerList[j]);
                if(!isNaN(pokerIndex)){
                    pokerInfo.push({
                        pokerIndex : pokerIndex,
                        isOpen : false
                    });
                }
            }
            dataList.push(pokerInfo);
            pokerValueList.push(pokerData.cardsPoint);
            if (i > 0) {
                //去除庄家结果
                if(pokerData.result == 'WIN'){
                    this._winDict[this.getBigTableIndex(i-1)] = Math.max(pokerData.odds, dealerOdds);
                }else if(pokerData.result == 'LOSE'){
                    this._winDict[this.getMinTableIndex(i-1)] = Math.max(pokerData.odds, dealerOdds);
                }
            }else dealerOdds = pokerData.odds;
        }
        //this.comp_pokerLayer.setPokersData(dataList, pokerValueList);

        this._initMatchEndData();
        this._matchEndData.pokersInfoList = dataList;
        this._matchEndData.pokersResultList = pokerValueList;

        //结算的时候更新座位上的其他玩家信息
        var seats = recvData.players;
        if(seats){
            this._matchEndData.othersInfo = {};
            for (var i = 0; i < seats.length; i++) {
                var player = seats[i];
                switch (player.seatIndex){
                    case G_DATA.getDealerSeatIndex():
                        //庄家剩余金币
                        //this.comp_playersLayer.getDealerComp().setGoldValue(player.dealerBalance);
                        this._matchEndData.dealerInfo = {
                            changeGold : player.coin,
                            leaveGold : player.balance,
                        };
                        break;
                    case G_DATA.getIdlePlayersSeatIndex():
                        break;
                    default:
                        this._matchEndData.othersInfo[player.seatIndex] = {
                            changeGold : player.coin,
                            leaveGold : player.balance,
                            name : player.nickname,
                            icon : player.icon
                        }
                        break;
                }
            }
        }
        return true
    },

    //百人——结束投注，金钱的变化数据(当有人投注时候，才会进入这个接口)
    net_mineHasGrab : function (recvData) {

        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        var myInfo = recvData.player;
        var dataObj = G_OBJ.data_selfMatchEndData();
        dataObj.changeGold = parseInt(myInfo.coin);
        dataObj.leaveGold = parseInt(myInfo.balance);
        this.setMatchEndMySelfData(dataObj);
    },

    //百人——当有某个玩家投注,包括自己
    net_otherGrab : function (recvData) {
        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        var others = recvData.bets, netBeanData;
        for(var i = 0; i < others.length; i ++){
            netBeanData = others[i];
            var tableIndex = this.getTableIndexByItem(netBeanData.item);
            var seatIndex = netBeanData.seatIndex;
            var goldImgNum= G_Config_grab.num_otherGrabGoldNum;
            //整理投注信息
            var dataObj = G_OBJ.data_bullBetting();
            dataObj.tableIndex = tableIndex;
            dataObj.posList = this.comp_tablesLayer.getGoldPosList(tableIndex, goldImgNum);
            dataObj.goldValue = netBeanData.gold;
            dataObj.isMine = false;
            //根据座位判定，投注玩家身份
            switch (seatIndex){
                case this.getMyselfComp().getSeatIndex():
                    //在座位上的自己的投注
                    break
                case G_DATA.getIdlePlayersSeatIndex():
                    //吃瓜群众
                    dataObj.startPos = this.comp_btnsLayer.getIdleBettingPos();
                    dataObj.goldNum = goldImgNum;
                    //自己没在座位上又有押注要扣掉玩家列表中自己的投注额
                    if (!this.getMyselfComp().getSeatIndex() && this.getMyselfComp().getIsBetting()) {
                        //如果这个吃瓜群众是自己
                        dataObj.goldValue = this.getMyselfComp().reduceGoldEX(dataObj.goldValue);
                    }
                    if (dataObj.goldValue > 0) {
                        //有其他的吃瓜群众投注
                        this._addIdleBetting(dataObj.tableIndex, dataObj.goldNum);
                        this._touchTableSuccess(dataObj);
                    }
                    break
                case G_DATA.getDealerSeatIndex():
                    //庄家
                    break
                default:
                    //正常座位上玩家
                    var playerComp = this.getPlayerCompInSite(seatIndex);
                    if(playerComp) playerComp.touchTable(dataObj, this._touchTableSuccess.bind(this));
                    break
            }
        }
    },

    //百人——有人入座
    net_someOneInHome : function (recvData) {

        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        var player, playerList = recvData.seats, myPlayerID = GG.getPlayerID();
        for(var i = 0; i < playerList.length; i ++){
            player = playerList[i];
            if(!player || player.player.playerId == myPlayerID) continue;
            this.comp_playersLayer.onePlayerInsert(player.seatIndex, player.player);
        }
    },

    //百人——续庄警告.在庄时候，如果金额不足，会有警告;
    net_dealerContinueWarning : function (recvData) {
        // console.log('上庄成功，续庄警告,金额不足=====')
        var tip = recvData.tip;
        // console.log('tip.tip==='+tip.tip);
        switch (tip.code){
            case G_TYPE.serverCodeType.success:
                break;
            case G_TYPE.serverCodeType.upDealerErr2:
                //金币不足上庄金额
                GG.tipsMgr.showSystem(tip.tip);
                break;
            case G_TYPE.serverCodeType.upDealerSuccess:
                //恭喜您，成为本局庄家!  显示 2 秒
                GG.tipsMgr.showSystem(G_CHINESE.upDealerSuccess, null, 2);
                break;
            case G_TYPE.serverCodeType.continueFail1:
                //庄家金币不足续庄
                GG.tipsMgr.showConfirmTip_ONE(tip.tip)
                break;
            case G_TYPE.serverCodeType.downDealer4:
                //您剩余金币不足,已下庄!被动下庄   系统提示  按钮显示置灰
                GG.tipsMgr.showConfirmTip_ONE(tip.tip);
                if(this.getMyselfComp().getIsDealer()){

                }
                break;
            case G_TYPE.serverCodeType.warnDealer1:
                //友情提示: 您的上庄资金不足80%，请及时续庄！显示 4 秒
                GG.tipsMgr.showSystem(G_CHINESE.downDealerWarning, null, 4);
                break;
            case G_TYPE.serverCodeType.continueFail2:
                //庄家被动续庄
                this.send_continueDealer(recvData.coin, function () {
                    //放弃被动续庄
                    this.setBottomIsShow(true);
                }.bind(this));
                break
            case G_TYPE.serverCodeType.downDealer3:
                //庄家被动下庄
                GG.tipsMgr.showConfirmTip_ONE(tip.tip)
                return;
            default:
                GG.tipsMgr.showConfirmTip_ONE(tip.code + tip.tip)
                return;
        }
    },

    //百人——获取上庄列表的返回(上庄，续庄和请求上庄列表都是返回这个接口)
    net_returnDealerList : function (recvData) {
        var tip = recvData.tip;
        //if(tip.code != G_TYPE.serverCodeType.success) {
        //    console.log(tip.tip);
        //    this._dealerNetType = null;
        //    return;
        //}

        switch (tip.code){
            case G_TYPE.serverCodeType.success:
                //请求成功
                break;
            case G_TYPE.serverCodeType.continueSuccess:
                //续庄成功

                break;
            case G_TYPE.serverCodeType.continueFail1:
                //主动续庄，余额不足提示
                GG.tipsMgr.showConfirmTip_ONE(tip.tip);
                return;
            case G_TYPE.serverCodeType.upDealerErr2:
                //玩家金币不足最低上庄余额
                GG.tipsMgr.showSystem(tip.tip, null, 2);
                return;
            case G_TYPE.serverCodeType.continueFail3:
                //续庄金额为非法数值
                GG.tipsMgr.showSystem(tip.tip, null, 2);//600014提示
                return;
            case G_TYPE.serverCodeType.downDealer5:
                //下庄之后又续庄
                GG.tipsMgr.showConfirmTip_ONE(tip.tip);//600019提示
                return;
            default:
                GG.tipsMgr.showConfirmTip_ONE(tip.code + tip.tip)
                return;
        }

        var dealerInfo = recvData.deskDealer;
        var playerList = recvData.players;
        var dataObj = {
            myGold : recvData.dealer.maxDealerCoin,
            dealerInfo : dealerInfo,
            rankList : playerList,
            tipStr : ''
        }

        //设置当前玩家的可用金币
        this.getMyselfComp().setGoldValue(recvData.dealer.coin);
        //庄家金币更新
        if (this.getMyselfComp().getIsDealer()) {
            this.comp_playersLayer.getDealerComp().setGoldValue(recvData.deskDealer.usableBalance);
        }

        switch (this._dealerNetType){
            case G_TYPE.dealerNetType.upDealer:
                //上庄
                dataObj.tipStr = G_CHINESE.dealerSuccess;
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
                if(comp) comp.upDealerSuccess(dataObj);
                //this.setBottomIsShow(false);
                break
            case G_TYPE.dealerNetType.continueDealer:
                //续庄
                dataObj.tipStr = G_CHINESE.continueDealerSuccess;
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
                if(comp) comp.upDealerSuccess(dataObj);
                break
            case G_TYPE.dealerNetType.getList:
                //获取上庄列表
                this.showUI_dealerList(dataObj);
                break
            default:
                break
        }
        this._dealerNetType = null;
    },

    //百人——下庄请求的回调
    net_returnDownDealer : function (recvData) {
        var tip = recvData.tip;
        // console.log(tip.tip);
        switch (tip.code){
            case G_TYPE.serverCodeType.success:
                break
            case G_TYPE.serverCodeType.downDealer1:
                //本身在庄的情况下下庄
                this.setBottomIsShow(true);
                var tipObj = {
                    tipIndex : 19,
                    showStr : G_CHINESE.downSuccess,
                    isCountDown : false,
                    showPos : G_DATA.getCenterTipPos(),
                    //callBack : this.on_beforeStart.bind(this)
                }
                // GG.tipsMgr.showTxtTip(tipObj);
                //您已经成功下庄 显示 2 秒
                GG.tipsMgr.showSystem(G_CHINESE.downSuccess, null, 2);
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
                if(comp) comp.setBtnShow(false);
                //下庄清除庄字特效
                this.comp_playersLayer.getMyselfEffectComp().clearDealerWordAni();
                //更新抢庄按钮是否可用
                this.comp_playersLayer.setUpDealerBtnEnable(false);
                break
            case G_TYPE.serverCodeType.downDealer2:
                //在上庄列表中，但是并未上庄的情况下下庄
                var dealerInfo = recvData.deskDealer;
                var playerList = recvData.players;
                var dataObj = {
                    myGold: recvData.dealer.maxDealerCoin,
                    dealerInfo: dealerInfo,
                    rankList: playerList,
                    tipStr: ''
                };
                dataObj.tipStr = G_CHINESE.giveUpDealer1
                //更新上庄列表数据
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList)
                if(comp) comp.downDealerSuccess(dataObj);
                //您已从上庄列表退出竞庄
                GG.tipsMgr.showSystem(G_CHINESE.giveUpDealer1, null, 2);
                //更新玩家数据
                this.getMyselfComp().setGoldValue(dataObj.myGold);
                break;
            case G_TYPE.serverCodeType.downDealer3:
                //你已经下庄，（不确定具体引用情况,可能是本身没有参与竞庄也没有上庄
                //如果还在庄就更改押注按钮
                this.setBottomIsShow(true);
                var tipObj = {
                    tipIndex : 19,
                    showStr : G_CHINESE.downSuccess,
                    isCountDown : false,
                    showPos : G_DATA.getCenterTipPos(),
                    //callBack : this.on_beforeStart.bind(this)
                }
                // GG.tipsMgr.showTxtTip(tipObj);
                //您已经成功下庄
                GG.tipsMgr.showSystem(G_CHINESE.downSuccess, null, 2);
                break
            default:
                break
        }
        this._isDealerSign = false;
    },

    //百人——退出房间的请求
    net_exitHome : function (recvData) {

        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        GG.exitHome();
    },

    //百人——游戏中长时间没有做操作
    net_idleTimeOut : function (recvData) {

        GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip, function () {
            GG.exitHome();
        }.bind(this));
    },

    _getOffSeconds : function (startTime, endTime) {
        return GG.socketMgr.getOffSeconds(startTime, endTime);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    //========================ui表现

    //抖动游戏
    shakeGame : function () {
        var off = 2;
        var off2 = off*2;
        var time = 0.07;
        var act1 = cc.moveBy(time, -off2, 0);
        var act2 = cc.moveBy(time, off2*2, 0);
        var act3 = cc.moveBy(time, -off2, 0);
        var act4 = cc.moveBy(time, off2, 0);
        var act5 = cc.moveBy(time, -off, 0);
        this.node.runAction(cc.sequence(act1,act2,act3,act4,act5));
    },

    onDestroy : function () {
        this._super();
        GG.bull100Mgr = null;
        GG.Listener.delListen(G_TYPE.globalListener.playerGold, this._listenerName);
    }
});
