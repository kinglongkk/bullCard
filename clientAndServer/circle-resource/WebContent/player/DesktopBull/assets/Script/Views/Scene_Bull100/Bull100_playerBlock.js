//玩家对象

cc.Class({
    extends: cc.Component,

    properties: {
        _chooseChip : null,                                             //选择的筹码
        _isHandle : null,                                                //是否是操作位
        _dict_betRecord : null,                                         //投注记录
        _playerID : null,                                                //玩家ID
        _siteIndex : null,                                               //座位索引
        _touchTableCallFunc : null,                                     //点击投注区域的回调
        _data_betting : null,                                             //当前投注需要的信息对象\

        _bettingTimes : null,                                            //当前投注次数
        _seatMyselfComp : null,                                          //座位上的自己玩家
        _dealerMyselfComp : null,                                         //庄家座位上的自己
        _isDealer : null,                                                 //是否是庄家
        _isSending : null,                                                //是否正在投注请求

        node_headImg : {
            default : null,
            type : cc.Node,
            displayName : '玩家头像'
        },
        node_playerName : {
            default : null,
            type : cc.Node,
            displayName : '玩家名字'
        },
        node_roomName : {
            default : null,
            type : cc.Node,
            displayName : '房间名字'
        },
        node_ownerGold : {
            default : null,
            type : cc.Node,
            displayName : '玩家金币父层'
        },
        prefab_goldResultEffect : {
            default : null,
            type : cc.Prefab,
            displayName : '金币增减特效'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._part_gold = this.getComponent('Part_playerBlock_Gold');
        this._part_gold.bindBlock(this);
        this._label_gold = this.node_ownerGold.children[0].getComponent(cc.Label);

        //头像上金币结果特效
        this._goldResultCompName = 'Obj_goldResultEffect';
        //筹码对应金币
        this._dict_goldMatch = G_Config_bull100.dict_betGoldNum;
        //头像上增减金币飞行时间
        //this._goldResultFlyTime = G_Config_grab.time_goldOnHead;
        this._goldResultFlyTime = 0.4;
        this._isSending = false;

        this.setDefaultChoose();
    },

    //初始设置玩家信息
    setPlayerData : function (dataObj) {
        this.node.active = true;
        this._dict_betRecord = null;
        this._part_gold.setBettingGold(0);
        this._bettingTimes = 0;
        this._data_betting = null;
        this._isDealer = dataObj.isDealer;
        this.setDefaultChoose();
        this._playerID = dataObj.playerId;
        this._siteIndex = dataObj.seatIndex;

        this._setHeadImg(dataObj.icon);
        this._setPlayerName(dataObj.nickname);
        this.setGoldValue(dataObj.coin);
        this._part_gold.setUsableBalance(dataObj.usableBalance);
    },

    //设置操作位的数据
    setHandleInfo : function (handleInfo) {
        if(handleInfo.chooseDict) this._dict_goldValueMatch = handleInfo.chooseDict;
        //房间名字
        this._setRoomName(handleInfo.roomName);
    },
    //绑定座位上的玩家
    bindSeatMyself : function (myselfComp) {
        this._seatMyselfComp = myselfComp;
    },
    //自己是庄家的时候，绑定庄家脚本
    bindMyDealer : function (myselfComp) {
        this._dealerMyselfComp = myselfComp;
    },

    //设置默认的筹码选择(如果已经选择过筹码了就不要重置了)
    setDefaultChoose : function () {
        if(!this._chooseChip) this._chooseChip = 1;
    },
    //选择一个筹码
    chooseChip : function (choose) {
        this._chooseChip = choose;
    },
    setGoldValue : function (value) {
        if(!value) value = 0;
        this._part_gold.setBalance(value);

        //位置上的自己
        if(this._seatMyselfComp){
            this._seatMyselfComp.setGoldValue(value);
        }
    },
    setIsHandle : function (isHandle) {
        this._isHandle = isHandle;
    },
    hidePlayer : function () {
        this.node.active = false;
    },
    setChooseGoldValue : function (dict) {
        this._dict_goldValueMatch = dict;
    },
    //设置玩家是否是庄家，因为已经没有每局刷新玩家信息，所以需要主动设置
    setIsDealer : function (isDealer) {
        this._isDealer = isDealer;
    },

    //====================get

    //获取已经选择的筹码需要飞行的金币数量
    getFlyGoldNum : function () {
        return this._dict_goldMatch[this._chooseChip]
    },
    getChooseChip : function () {
        return this._chooseChip
    },
    getChooseGoldValue : function () {
        return this._dict_goldValueMatch[this._chooseChip-1]
    },
    getMoneyChooseDict : function () {
        return this._dict_goldValueMatch;
    },
    getWorldPos : function () {
        //if(this._isHandle) return this.node.position;
        //else{
        //    return this.node.parent.position
        //}
        var parentPos = this.node.parent.position;
        return cc.p(this.node.x+parentPos.x, this.node.y+parentPos.y)
    },
    //获取玩家的投注记录
    getBetRecord : function () {
        return this._dict_betRecord;
    },
    getIsShow : function () {
        return this.node.active
    },
    getSeatIndex : function () {
        return this._siteIndex
    },
    //获取当前剩余多少金币
    getGoldValue : function () {
        return this._part_gold.getBalance()
    },
    //是否在本局已经投注过
    getIsBetting : function () {
        return Boolean(this._part_gold.getBettingGold())
    },
    //是否是庄家
    getIsDealer : function () {
        return this._isDealer
    },
    //获取玩家ID
    getPlayerID : function () {
        return this._playerID
    },
    //获取已经投注的金币数额
    getHasBettingGold : function () {
        return this._part_gold.getBettingGold()
    },
    //获取可用余额
    getUsableBalance : function () {
        return this._part_gold.getUsableBalance();
    },

    //清理投注记录
    clearBettingGold : function () {
        this._part_gold.setBettingGold(0);
        this._part_gold.setGoldValueEX(0);
    },

    //投注金额，仅用于自己是吃瓜群众的时候
    reduceGoldEX : function (changeGold) {
        var goldValue = this._part_gold.getGoldValueEX();
        if(goldValue >= changeGold){
            this._part_gold.payGoldValueEX(changeGold);
            return 0;
        }else{
            var value = changeGold - goldValue;
            this._part_gold.setGoldValueEX(0);
            return value
        }
    },

    //=============================做投注的请求

    //点击桌子的大或者小
    touchTable : function (grabInfo, callFunc) {
        this._touchTableCallFunc = callFunc;
        grabInfo.startPos = this.getWorldPos();
        grabInfo.goldNum = this.getFlyGoldNum();
        if(!this._isHandle || G_Config_common.isLocal){
            //不是在玩家自己或者只是单机模式
            var money = grabInfo.goldValue;
            grabInfo.goldNum = G_Config_grab.num_otherGrabGoldNum;
            this._data_betting = grabInfo;
            this._setTouchTableData(money, this.getGoldValue()-money);
            return
        }else{
            if(this._isSending){
                return
            }
            grabInfo.goldValue = this.getChooseGoldValue();
            this._data_betting = grabInfo;
            //可以投注
            var netData = {
                matchId: GG.bull100Mgr.getMatchID(),//赛事id  int
                type:G_TYPE.net_gameModule.bull100,//投注类型 string
                item:GG.bull100Mgr.getItemByTableIndex(grabInfo.tableIndex),//投注项 string
                gold:grabInfo.goldValue,//投注额 int
            }
            this._isSending = true;
            GG.socketMgr.SendMsg(NetType.s_doGrab, netData);
            GG.socketMgr.registerLong(NetType.r_doGrab_return, this.net_grabReturn.bind(this));
        }
    },

    //百人——投注成功后返回的函数
    net_grabReturn : function (recData) {
        this._isSending = false;

        var tip = recData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            var dataObj = {
                tipIndex : 19,
                retainTime : 3,
                showStr : tip.tip,
            }
            GG.tipsMgr.showTxtTip(dataObj)
            return;
        }
        //赛事不存在踢出
        if (tip.code == G_TYPE.serverCodeType.matchNonExistent) {
            //var netData = {
            //    roomId: GG.bull100Mgr.getHomeID(),
            //    isBreak: true
            //};
            //GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            GG.exitHome();
            return;
        }

        var bettingValue = recData.bet.gold;
        var leaveValue = recData.nbSelf.balance;
        this._setTouchTableData(bettingValue, leaveValue, recData.nbSelf.usableBalance);
        //押注过程中更新押注按钮（置灰）
        GG.bull100Mgr.getBtnsComp().refreshCanChooseBtn(recData.nbSelf.usableBalance);
    },

    _setTouchTableData : function (bettingValue, leaveValue, usableBalance) {
        //重组投注信息对象
        if(!this._data_betting) return;
        var dataObj = G_OBJ.data_bullBetting();
        for(var attrName in this._data_betting){
            if(attrName) dataObj[attrName] = this._data_betting[attrName];
        }
        this._data_betting = null;

        dataObj.goldValue = bettingValue;
        //更新投注金额池信息
        this._part_gold.addBettingGold(dataObj.goldValue);
        this._part_gold.addGoldValueEX(dataObj.goldValue);
        //更新余额信息
        this.setGoldValue(leaveValue);
        //更新可用余额信息
        this._part_gold.setUsableBalance(usableBalance);
        //记录投注信息
        this._touchTableSuccess(dataObj.tableIndex, dataObj.goldNum);
        this._bettingTimes += 1;
        // GG.bull100Mgr.setBettingTimes(this._bettingTimes);
        if(this._touchTableCallFunc){
            this._touchTableCallFunc(dataObj);
            this._touchTableCallFunc = null;
        }
    },

    //投注成功后记录投注信息
    _touchTableSuccess : function (tableIndex, goldImgNum) {
        if(!this._dict_betRecord) this._dict_betRecord = {};
        if(!this._dict_betRecord[tableIndex]) this._dict_betRecord[tableIndex] = goldImgNum;
        else this._dict_betRecord[tableIndex] += goldImgNum
    },

    //获取庄家卡牌的位置
    getDealerPokerPos : function () {
        //卡牌位置是庄家中心点偏左
        var pokerOffX = 0.4;
        var containerPos = this.node.parent.position;
        var pos = cc.p(this.node.x-this.node.width*0.5*this.node.scale*pokerOffX+containerPos.x, this.node.y-this.node.height*0.5*this.node.scale+containerPos.y)
        return pos
    },

    //ui表现相关===============================

    _setHeadImg : function (imageName) {
        G_TOOL.setHeadImg(this.node_headImg, imageName);
    },

    _setPlayerName : function (playerName) {
        if(!playerName) playerName = '';
        playerName = G_TOOL.getNameLimit(playerName, 10, true);
        this.node_playerName.getComponent(cc.Label).string = playerName;
    },

    _setRoomName : function (roomName) {
        if(roomName === undefined) roomName= '';
        if(this.node_roomName && roomName) this.node_roomName.getComponent(cc.Label).string = roomName;
    },

    showGoldValue : function (value) {
        this._label_gold.string = G_TOOL.changeMoney(value);
    },

    //显示玩家数值的增减
    showGoldChange : function (value) {
        //金钱没有变化
        //金钱变化正或者负
        //var dir = G_TOOL.getRandomBool() ? 1 : -1;
        this._showGoldCount(value);
    },
    //显示玩家数值特效
    _showGoldCount : function (count) {
        if(!count) return;

        var labelNode = cc.instantiate(this.prefab_goldResultEffect);
        labelNode.parent = this.node;
        labelNode.zIndex = 2;

        labelNode.getComponent(this._goldResultCompName).showGrabGold(count);

        labelNode.scale = 0.1;
        var parentScale, widthOff;
        widthOff = this.node.width*0.3;
        if(this._isHandle){
            parentScale = this.node.scale;
        }else {
            parentScale = this.node.parent.scale;
        }
        if(this.node.x > cc.visibleRect.width/2) widthOff*= -1;
        var time = this._goldResultFlyTime;
        var act1 = cc.moveTo(time, widthOff, this.node.height*0.4);
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.delayTime(time*2);
        labelNode.runAction(cc.sequence(cc.spawn(act1,act2),act3,cc.callFunc(this._labelMoveEnd, this, count)))
    },
    _labelMoveEnd : function (target, count) {
        target.destroy();
    },

    onDestroy : function () {

    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
