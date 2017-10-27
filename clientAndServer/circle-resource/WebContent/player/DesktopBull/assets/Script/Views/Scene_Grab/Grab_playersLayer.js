//管理所有玩家的容器

cc.Class({
    extends: cc.Component,

    properties: {
        _playerNum : null,                                                               //场上的玩家数量
        _dict_playerBlocks : null,
        _dict_playerByID : null,

        node_owner : cc.Node,
        node_system : cc.Node,
        node_playerContainer : {                                                          //所有玩家的容器，庄家和自己除外
            default : null,
            type : cc.Node,
            displayName : '所有玩家容器'
        },
        prefab_playerBlock : cc.Prefab,
        comp_seatsContainer : {
            default : null,
            type : require('Obj_seatsContainer'),
            displayName : '座位容器'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._init();
    },
    _init : function () {
        this._playerCompName = 'Grab_playerBlock';
        this._dict_playerBlocks = {};
        this._dict_playerByID = {};
        this._playerNum = 0;
    },

    //设置当前所有座位玩家的信息
    setStartInfo : function (seatInfoList, selfInfo, systemInfo) {
        var dictPlayer = {};
        var selfID = GG.getPlayer().getPlayerID();
        var listLen = seatInfoList.length, seatIndex, onePlayer, content, playerComp;
        //座位是从1开始，0是自己玩家的信息
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
            if (content) this._dict_playerByID[content.playerId] = seatIndex;          //获取玩家id
            //从列表中获取属于自己的信息
            if(content && content.playerId == selfID) {
                for(var attrName in content){
                    if (attrName == 'seatIndex' && content[attrName] == 0)  continue;
                    selfInfo[attrName] = content[attrName];
                }
            }
            //座位号从1开始
            if(seatIndex < 1) continue;
            //有玩家数据
            playerComp = this._addOnePlayer(seatIndex);
            if(playerComp) playerComp.setPlayerData(content);
            if(content && content.playerId == selfID) this.getMyselfComp().bindSeatMyself(playerComp);
        }
        //隐藏没有数据的位置
        var siteNum = this.comp_seatsContainer.getSeatsNum();
        for(var i = 0; i < siteNum; i ++){
            seatIndex = i+1;
            if(!dictPlayer[seatIndex]) this._removeOnePlayer(seatIndex);
        }

        //if(!GG.grabMgr.checkMyselfInfo(seatInfoList)) return;
        if(!isNaN(selfInfo.playerId) && selfInfo.playerId > 0)  this.showOwner(selfInfo);
        this.showSystem(systemInfo);
    },

    showOwner : function (selfInfo) {
        this.getMyselfComp().setPlayerData(selfInfo);
        this.getMyselfComp().setSelfInfo(selfInfo);

        GG.grabMgr.initButtonInfo();
    },

    //有个玩家中途插入
    enterOnePlayer : function (seatIndex, playerData) {
        var reg = /^\+?[1-9][0-9]*$/;
        if(reg.test(seatIndex)){
            //是正确的桌子索引
            playerData.seatIndex = seatIndex;
            this._hidePlayer(seatIndex, playerData.playerId);
            var playerComp = this._addOnePlayer(seatIndex);
            if(playerComp) playerComp.setPlayerData(playerData);
        }
    },

    showSystem : function (systemInfo) {
        systemInfo.moneyChoose = 0;
        this.node_system.getComponent(this._playerCompName).setPlayerData(systemInfo);
    },

    clearData : function () {
        //var block;
        //for(var siteIndex in this._dict_playerBlocks){
        //    block = this._dict_playerBlocks[siteIndex];
        //    if(block) block.clearData();
        //}
        //this.getMyselfComp().clearData();
    },

    //删除重复的玩家，结束本局时候，重复进出房间会出现多个同样玩家
    _hidePlayer : function (targetIndex, playerID) {
        var playerComp;
        for(var seatIndex in this._dict_playerBlocks){
            playerComp = this._dict_playerBlocks[seatIndex];
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
        var playerComp = this._dict_playerBlocks[siteIndex];
        if (!playerComp) {
            var  player = cc.instantiate(this.prefab_playerBlock);
            player.parent = this.node_playerContainer;
            var size = G_TOOL.adaptSize(player.width, player.height);
            player.width = size.width;
            player.height = size.height;
            var pos = this.comp_seatsContainer.addOnePlayer(siteIndex);
            player.position = G_TOOL.adaptPos(pos);
            var playerComp = player.getComponent(this._playerCompName);
            this._dict_playerBlocks[siteIndex] = playerComp;
        }else{
            this.comp_seatsContainer.addOnePlayer(siteIndex);
        }
        this._playerNum += 1;
        return playerComp;
    },
    //一个玩家离场
    _removeOnePlayer : function (siteIndex) {
        var playerComp = this._dict_playerBlocks[siteIndex];
        if(!playerComp || !playerComp.getIsShow()) return
        playerComp.hidePlayer();
        this.comp_seatsContainer.onePlayerLeave(siteIndex);
        this._playerNum -= 1;
    },
    //清理所有
    _clearAllPlayer : function () {
        for(var seatIndex in this._dict_playerBlocks){
            this._removeOnePlayer(seatIndex);
        }
    },

    setWinTableResult : function (resultList) {
        //其他座位上玩家的金币
        var playerComp, dataList, isMoveGold = false;
        for(var key in this._dict_playerBlocks){
            playerComp = this._dict_playerBlocks[key];
            if(!playerComp.getIsShow() || playerComp.getPlayerID() == GG.getPlayerID()) continue;
            dataList = this._checkResult(resultList, playerComp.getAllGrabTable(), playerComp.getWorldPos());
        }
        if(dataList && dataList.length > 0) isMoveGold = true;
        GG.grabMgr.playResultGoldMove(dataList);
        //玩家自己的金币
        playerComp = this.getMyselfComp();
        recordDict = playerComp.getAllGrabTable();
        if(recordDict){
            dataList = this._checkResult(resultList, recordDict, playerComp.getWorldPos());
            if(dataList && dataList.length > 0) isMoveGold = true;
            GG.grabMgr.playResultGoldMove(dataList);
        }
        //吃瓜群众的金币
        var recordDict = GG.grabMgr.getIdleBetting();
        if(recordDict){
            dataList = this._checkResult(resultList, recordDict, GG.grabMgr.getIdleBettingPos())
            if(dataList && dataList.length > 0) isMoveGold = true;
            GG.grabMgr.playResultGoldMove(dataList);
        }
        //剩余的金币飞向系统
        var recordDict = GG.grabMgr.getTableContainer().getLeaveGoldImages();
        if(recordDict){
            //var curList = this._checkResult(resultList, recordDict, this.getSystemComp().getWorldPos())
            dataList = [];
            var goldNum, targetPos = this.getSystemComp().getWorldPos();
            for(var tableIndex in recordDict){
                goldNum = recordDict[tableIndex];
                if(goldNum){
                    var dataObj = G_OBJ.data_flyGold_tableToPlayer();
                    dataObj.goldNum = goldNum;
                    dataObj.tableIndex = tableIndex;
                    dataObj.targetPos = targetPos;
                    dataList.push(dataObj);
                }
            }
            if(dataList && dataList.length > 0) isMoveGold = true;
            GG.grabMgr.playResultGoldMove(dataList);
        }
        if(isMoveGold) GG.audioMgr.playSound(18);
    },
    _checkResult : function (resultList, recordDict, targetPos) {
        var result, goldNum, dataList = [];
        for(var index in resultList){
            result = resultList[index];
            goldNum = recordDict[result];
            if(goldNum){
                var dataObj = G_OBJ.data_flyGold_tableToPlayer();
                dataObj.goldNum = goldNum;
                dataObj.tableIndex = result;
                dataObj.targetPos = targetPos;
                dataList.push(dataObj);
            }
        }
        return dataList
    },

    //显示所有玩家对象的头像上的金币浮动信息
    showAllGoldResult : function () {
        var player, isShow, isOk;
        for(var seatIndex in this._dict_playerBlocks){
            player = this._dict_playerBlocks[seatIndex];
            if(!player.getIsShow()) continue;
            isOk = player.showResultGold();
            if(!isShow) isShow = isOk;
        }

        isOk = this.getMyselfComp().showResultGold();
        if(!isShow) isShow = isOk;
        return isShow
    },

    setMyselfMoney : function (addCoin, money) {
        this.getMyselfComp().setResultInfo(addCoin, money);
    },
    //获取在座位上的玩家脚本
    getCompByIndex : function (siteIndex) {
        return this._dict_playerBlocks[siteIndex];
    },
    getPlayerByID : function (playerID) {
        return this.getCompByIndex(this._dict_playerByID[playerID]);
    },
    //获取玩家自己
    getMyselfComp : function () {
        return this.node_owner.getComponent(this._playerCompName);
    },
    getPokerPos : function () {
        return this.node_system.getComponent(this._playerCompName).getPokerPoint();
    },
    getSystemComp : function () {
        return this.node_system.getComponent(this._playerCompName)
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
