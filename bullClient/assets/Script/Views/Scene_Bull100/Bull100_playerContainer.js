//所有玩家容器管理

//座位索引seatIndex是从1开始的

//var playerData = {
//    nickname : content.nickname,
//    icon : content.icon,
//    playerId : content.playerId,
//    coin : content.coin,
//    moneyChoose : 0,
//    seatIndex : seatIndex
//}

cc.Class({
    extends: require('AutoDealing'),

    properties: {
        _dict_playerComp : null,                                                        //座位上的玩家脚本
        _playerNum : null,                                                               //场上的玩家数量

        node_playerContainer : {                                                          //所有玩家的容器，庄家和自己除外
            default : null,
            type : cc.Node,
            displayName : '所有玩家容器'
        },
        node_btnGrabDealer : {                                                           //抢庄按钮
            default : null,
            type : cc.Node,
            displayName : '抢庄按钮'
        },
        node_myself : {
            default : null,
            type : cc.Node,
            displayName : '玩家自己'
        },
        node_dealer : {
            default : null,
            type : cc.Node,
            displayName : '庄家'
        },
        prefab_player : {
            default : null,
            type : cc.Prefab,
            displayName : '座位上的玩家'
        },
        comp_seatsContainer : {
            default : null,
            type : require('Obj_seatsContainer'),
            displayName : '座位容器'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._dict_playerComp = {};
        this._playerNum = 0;
        this._playerCompName = 'Bull100_playerBlock';
        this._delaerWordCompName = 'Obj_dealerWordEffect';
        this.registerButton(this.node_btnGrabDealer, this.OnClick_grabDealer, this);

        this.getMyselfComp().setIsHandle(true);
    },

    //点击了上庄按钮
    OnClick_grabDealer : function (event) {
        GG.bull100Mgr.send_getDealerList();
    },

    //seatInfoList：座位上的玩家；selfInfo自己；dealerInfo庄家
    setStartInfo : function (seatInfoList, selfInfo, dealerInfo) {
        //整理座位信息
        var dictPlayer = {};
        var selfID = GG.getPlayer().getPlayerID();
        var listLen = seatInfoList.length, seatIndex, onePlayer, content, playerComp;
        for(var i = 0; i < listLen; i ++){
            onePlayer = seatInfoList[i];
            seatIndex = onePlayer['seatIndex'];
            content = onePlayer['player'];
            content.seatIndex = seatIndex;
            dictPlayer[seatIndex] = content;
        }
        //根据信息控制位置上玩家的显隐
        //显示有数据的玩家
        for(var seatIndex in dictPlayer){
            content = dictPlayer[seatIndex];
            //从列表中获取属于自己的信息
            if(content && content.playerId == selfID) {
                for(var attrName in content){
                    if(attrName == 'seatIndex' && content[attrName] == 0) continue;
                    selfInfo[attrName] = content[attrName];
                }
            }
            //座位号从1开始
            if(seatIndex < 1) continue;
            //有玩家数据
            playerComp = this._addOnePlayer(seatIndex);
            if(playerComp) {
                playerComp.setPlayerData(content);
                if(content && content.playerId == selfID) this.getMyselfComp().bindSeatMyself(playerComp);
            }
        }
        //隐藏没有数据的位置
        var siteNum = this.comp_seatsContainer.getSeatsNum();
        for(var i = 0; i < siteNum; i ++){
            seatIndex = i+1;
            if(!dictPlayer[seatIndex]) this._removeOnePlayer(seatIndex);
        }
        //从庄家数据中获取玩家自己的数据
        var isDealer = Boolean(selfID == dealerInfo.playerId);
        selfInfo.isDealer = isDealer;
        if (selfInfo.isDealer) {
            for (var attrName in dealerInfo) {
                selfInfo[attrName] = dealerInfo[attrName];
            }
            //庄家上庄按钮不可用
            this.setUpDealerBtnEnable(false);
        } else {
            //玩家上庄按钮可用
            this.setUpDealerBtnEnable(true);
            //if(!GG.bull100Mgr.checkMyselfInfo(seatInfoList)) return;
        }
        if(selfInfo.playerId) this.setOwnerInfo(selfInfo);
        this.setDealerInfo(dealerInfo);

        //当前自己是庄家的时候自己玩家需要显示的是可用余额而不是全额
        if(isDealer){
            //因为新的机制已经没有给予玩家的信息，为了更新金额所以只能从庄家信息中计算
            this.getMyselfComp().setGoldValue(selfInfo.coin);
        }
        this.getMyselfComp().setIsDealer(isDealer);
    },

    setOwnerInfo : function (selfInfo) {
        this.getMyselfComp().setPlayerData(selfInfo);
        this.getMyselfComp().setHandleInfo(selfInfo);
    },
    setDealerInfo : function (dealerInfo) {
        var newDealerInfo = {};
        for(var attrName in dealerInfo){
            newDealerInfo[attrName] = dealerInfo[attrName];
        }
        newDealerInfo.coin = dealerInfo.usableBalance;
        this.getDealerComp().setPlayerData(newDealerInfo);
    },

    //有一个玩家中途进入
    onePlayerInsert : function (seatIndex, playerData) {
        var reg = /^\+?[1-9][0-9]*$/;
        if (reg.test(seatIndex)) {
            playerData.seatIndex = seatIndex;
            this._hidePlayer(seatIndex, playerData.playerId);
            var playerComp = this._addOnePlayer(seatIndex);
            if(playerComp) playerComp.setPlayerData(playerData);
        }
    },

    //删除重复的玩家，结束本局时候，重复进出房间会出现多个同样玩家
    _hidePlayer : function (targetIndex, playerID) {
        var playerComp;
        for(var seatIndex in this._dict_playerComp){
            playerComp = this._dict_playerComp[seatIndex];
            if(seatIndex == targetIndex){
                //找到位置
                //if(playerComp) playerComp.setPlayerData(playerData);
            }else{
                if(playerComp && playerComp.getPlayerID() == playerID){
                    this._removeOnePlayer(seatIndex);
                }
            }
        }
    },
    //增加一个上场玩家
    _addOnePlayer : function (siteIndex) {
        var playerComp = this._dict_playerComp[siteIndex];
        if(!playerComp){
            var player = cc.instantiate(this.prefab_player);
            player.parent = this.node_playerContainer;
            var size = G_TOOL.adaptSize(player.width, player.height);
            player.width = size.width;
            player.height = size.height;
            player.position = this.comp_seatsContainer.addOnePlayer(siteIndex);
            playerComp = player.getComponent(this._playerCompName);
            this._dict_playerComp[siteIndex] = playerComp;
        }else{
            this.comp_seatsContainer.addOnePlayer(siteIndex);
        }
        this._playerNum += 1;
        return playerComp;
    },
    //一个玩家离场
    _removeOnePlayer : function (siteIndex) {
        var playerComp = this._dict_playerComp[siteIndex];
        if(!playerComp || !playerComp.getIsShow()) return
        playerComp.hidePlayer();
        this.comp_seatsContainer.onePlayerLeave(siteIndex);
        this._playerNum -= 1;
    },

    //显示所有玩家的金币增减特效
    showGoldChangeEffect : function () {
        var player;
        for(var siteIndex in this._dict_playerComp){
            player = this._dict_playerComp[siteIndex];
            if(player){
                player.showGoldChange();
            }
        }
        //mine
        this.getMyselfComp().showGoldChange();
    },

    //=======================

    //场上的金币结算  winDict = {0:1, 2:1, 4:1},  {桌子索引：是否胜利}
    flyWinGold : function (winDict) {
        var isMoveGold = false;
        //自己的金币结算
        var recordDict;
        var comp = this.getMyselfComp();
        recordDict = comp.getBetRecord();
        if(recordDict){
            var newDict = this._getWinGoldDict(winDict, recordDict);
            this._moveGold(newDict, comp.getWorldPos());
        }
        //其他玩家的胜利金币移动
        var playerComp;
        for(var seatIndex in this._dict_playerComp){
            playerComp = this._dict_playerComp[seatIndex];
            if(playerComp.getPlayerID() == GG.getPlayerID()) continue;
            if(playerComp && playerComp.getIsShow()){
                recordDict = playerComp.getBetRecord();
                if(recordDict){
                    isMoveGold = true;
                    var newDict = this._getWinGoldDict(winDict, recordDict);
                    this._moveGold(newDict, playerComp.getWorldPos());
                }
            }
        }
        //是否有吃瓜群众赢钱
        recordDict = GG.bull100Mgr.getIdleBetting();
        if(recordDict){
            isMoveGold = true;
            var newDict = this._getWinGoldDict(winDict, recordDict);
            this._moveGold(newDict, GG.bull100Mgr.getIdleBettingPos());
        }

        //庄家回收剩余的金币
        var goldContainer = this.getGoldContainer();
        var isFly = goldContainer.recoverAllGold(this.getDealerComp().getWorldPos());
        if(isFly) isMoveGold = true;

        if(isMoveGold) GG.audioMgr.playSound(18);
    },

    _getWinGoldDict : function (winDict, recordDict) {
        var tableType, newDict = {};
        for(var tableIndex in winDict){
            tableType = winDict[tableIndex];
            if(tableType && recordDict[tableIndex]){
                newDict[tableIndex] = recordDict[tableIndex];
            }
        }
        return newDict
    },
    //金币结算移动
    _moveGold : function (recoverDict, targetPos) {
        var goldNum, goldContainer = this.getGoldContainer();
        for(var tableIndex in recoverDict){
            goldNum = recoverDict[tableIndex];

            var flyDataList = G_OBJ.data_flyGold_tableToPlayer();
            flyDataList.tableIndex = tableIndex;
            flyDataList.goldNum = goldNum;
            flyDataList.targetPos = targetPos;
            goldContainer.tableToPlayer(flyDataList);
        }
    },

    //设置玩家操作位置的显隐
    setMyselfIsShow : function (isShow) {
        this.node_myself.active = isShow;
    },
    //设置抢庄按钮是否可用
    setUpDealerBtnEnable : function (isEnable) {
        this.setBtnEnable(this.node_btnGrabDealer,isEnable);
    },

    //=======================

    //获取庄家的卡牌位置
    getDealerPokerPos : function () {
        return this.getDealerComp().getDealerPokerPos();
    },
    //获取玩家自己
    getMyselfComp : function () {
        return this.node_myself.getComponent(this._playerCompName)
    },
    //获取玩家庄字特效
    getMyselfEffectComp : function () {
        var myselfComp = this.getMyselfComp();
        var child = myselfComp.node.getChildByName('bankerMark');
        var comp;
        if(child){
            comp = child.getComponent(this._delaerWordCompName);
        }
        return comp
    },
    getMyselfPos : function () {
        return this.node_myself.position
    },
    //获取庄家
    getDealerComp : function () {
        return this.node_dealer.getComponent(this._playerCompName)
    },
    //获取庄家庄字特效
    getDealerWordEffectComp : function () {
        var dealerComp = this.getDealerComp();
        var child = dealerComp.node.getChildByName('bankerMark');
        var comp;
        if(child){
            comp = child.getComponent(this._delaerWordCompName);
        }
        return comp
    },
    //获取在座位上的玩家脚本
    getPlayerComp : function (siteIndex) {
        return this._dict_playerComp[siteIndex];
    },
    //获取金币容器
    getGoldContainer : function () {
        return GG.bull100Mgr.getGoldContainer();
    },

    //清理所有
    clearAll : function () {
        for(var seatIndex in this._dict_playerComp){
            this._removeOnePlayer(seatIndex);
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
