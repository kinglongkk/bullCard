//玩家头像块

cc.Class({
    extends: cc.Component,

    properties: {
        _siteImg : null,
        _chooseMoneyLevel : null,
        _dict_chooseMoney : null,
        _callFunc : null,
        _defaultHeadFrame : null,                                        //没有玩家头像时候使用的默认头像
        _seatMyselfComp : null,                                          //座位上的自己玩家
        _data_betting : null,                                        //投注信息
        _goldValueEX : null,                                              //投注金额池信息
        _isSending : null,                                                //是否正在发送投注请求

        //data
        _dict_choosed : null,
        _curGoldValue : null,
        _grabGold : null,
        _playerName : null,
        _grabTimes : null,                                      //当前投注次数
        _seatIndex : null,
        _resultChangeMoney : null,                             //结算时候显示是金额变化
        _playerID : null,                                       //玩家id
        _usableBalance : null,                                 //可用余额

        node_headImg : {
            default : null,
            type : cc.Node,
            displayName : '头像'
        },
        label_playerName : {
            default : null,
            type : cc.Label,
            displayName : '玩家名'
        },
        node_goldValue : {
            default : null,
            type : cc.Node,
            displayName : '拥有金币'
        },
        _playerType : 0,
        playerType : {
            set : function (_type) {
                this._playerType = _type;
            },
            get : function () {
                return this._playerType;
            },
            type : G_TYPE.playerType,
            displayName : '玩家类型'
        },
        prefab_goldValue : cc.Prefab
    },

    // use this for initialization
    onLoad: function () {
        this._init();
    },

    _init : function () {
        this._dict_chooseLevel = G_Config_grab.dict_moveGoldNum;
        this._goldValueCompName = 'Obj_goldResultEffect';
        this._chooseMoneyLevel = 0;
    },

    start : function () {

    },

    //var dataObj = {
    //    name : '',
    //    headImg : '',
    //    goldValue : 11
    //}
    setPlayerData : function (dataObj) {
        if(!dataObj) return;
        this.node.active = true;

        this.clearData();
        if(dataObj.coin >= 0) this.setGoldValue(dataObj.coin);
        else this.setGoldValue(0);
        if(dataObj.seatIndex) this._seatIndex = dataObj.seatIndex;

        this._playerName = dataObj.nickname;
        this._setPlayerName(this._playerName);
        G_TOOL.setHeadImg(this.node_headImg, dataObj.icon);
        this._playerID = dataObj.playerId;
        this._usableBalance = dataObj.usableBalance;
    },
    setSelfInfo : function (selfInfo) {
        if(selfInfo.chooseDict) this._dict_chooseMoney = selfInfo.chooseDict;
        var homeLabel = this.node.getChildByName('homeName');
        if(homeLabel && selfInfo.roomName){
            homeLabel.getComponent(cc.Label).string = selfInfo.roomName;
        }
    },

    clearData : function () {
        this._dict_choosed = {};
        this._grabGold = 0;
        this._goldValueEX = 0;
        this._grabTimes = 0;
        this._isSending = false;
        this._data_betting = null;
        this._resultChangeMoney = null;
    },

    _refreshGoldShow : function () {
        this.node_goldValue.getComponent(cc.Label).string = G_TOOL.changeMoney(this._curGoldValue);
    },

    doChoose : function (tableIndex, goldNum) {
        if(this._dict_choosed[tableIndex]) this._dict_choosed[tableIndex] += goldNum;
        else this._dict_choosed[tableIndex] = goldNum;
    },

    //点击投注区域
    touchGrabArea : function (grabInfo, callFunc) {
        var goldNum=0;
        var money = this._dict_chooseMoney[this._chooseMoneyLevel];
        this._callFunc = callFunc;

        if(money > this._curGoldValue){
            //余额不足
            var dataObj = {
                tipIndex : 21,
            }
            GG.tipsMgr.showTxtTip(dataObj);
        }else{
            //if(this._getIsOverMaxGrabTimes()){
            //    //超出最大投注次数
            //    var dataObj = {
            //        tipIndex : 20,
            //        showStr : '超出最大投注次数'
            //    }
            //    GG.tipsMgr.showTxtTip(dataObj);
            //}else{
            //
            //}
            if(G_Config_common.isLocal){
                grabInfo.startPos = this.getWorldPos();
                this.forceGrabReturn(grabInfo, money, grabInfo.tableIndex);
            }else{
                if(this._isSending){
                    return
                }
                grabInfo.startPos = this.getWorldPos();
                grabInfo.goldValue = money;
                this._data_betting = grabInfo;

                //可以投注
                var netData = {
                    matchId: GG.grabMgr.getMatchID(),//赛事id  int
                    type:G_TYPE.net_gameModule.grab,//投注类型 string
                    item:grabInfo.tableIndex+'_W',//投注项 string
                    gold:money,//投注额 int
                }
                this._isSending = true;
                GG.socketMgr.SendMsg(NetType.s_doGrab, netData);
                GG.socketMgr.registerLong(NetType.r_doGrab_return, this.net_grabReturn.bind(this));
            }
        }
    },
    net_grabReturn : function (recvData) {
        this._isSending = false;
        var tip = recvData.tip;
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
            GG.exitHome();
            return;
        }
        //下分金额为非许可值踢出
        if (tip.code == G_TYPE.serverCodeType.betNotPermitCoin) {
            GG.exitHome();
            return;
        }
        //非法下注类型踢出
        if (tip.code == G_TYPE.serverCodeType.betNotPermitBet) {
            GG.exitHome();
            return;
        }
        if(!this._data_betting) return;
        var bettingData = G_OBJ.data_grabBetting();
        for(var attrName in this._data_betting){
            if(attrName) bettingData[attrName] = this._data_betting[attrName]
        }
        this._data_betting = null;

        bettingData.goldValue = recvData.bet.gold;
        bettingData.goldNum = parseInt(this._dict_chooseLevel[this._chooseMoneyLevel+1]);

        //记录投注信息
        this.doChoose(bettingData.tableIndex, bettingData.goldNum);
        //累计投注金额
        this._grabGold += bettingData.goldValue;
        this._goldValueEX += bettingData.goldValue;
        //更新玩家剩余金额
        this.setGoldValue(recvData.nbSelf.balance);
        //可用余额
        this._usableBalance = recvData.nbSelf.usableBalance;
        //押注过程中更新押注按钮（置灰）
        GG.grabMgr.getBtnsComp().refreshGrabOption(this.getGoldValue());

        this._grabTimes += 1;
        // GG.grabMgr.setBettingTimes(this._grabTimes);
        if(this._callFunc){
            this._callFunc(bettingData);
            this._callFunc = null;
        }
    },
    forceGrabReturn : function (grabInfo, money, tableIndex) {
        grabInfo.goldValue = money;
        this._grabGold += money;
        this._goldValueEX += money;
        this.setGoldValue(this._curGoldValue - money);
        var goldNum = parseInt(this._dict_chooseLevel[this._chooseMoneyLevel+1]);
        grabInfo.goldNum = goldNum;
        this.doChoose(tableIndex, goldNum);
        this._grabTimes += 1;
        if(this._callFunc){
            this._callFunc(grabInfo);
            this._callFunc = null;
        }
    },

    setResultInfo : function (changeMoney, haveMoney) {
        if(G_DATA.isNumber(haveMoney)) this._curGoldValue = haveMoney;
        this._resultChangeMoney = changeMoney;
    },
    //setResultGoldInfo : function (goldInfo) {
    //    goldInfo.targetPos = this.getWorldPos();
    //    //goldInfo.callFunc = this.showResultGold.bind(this);
    //    return goldInfo
    //},
    showResultGold : function () {
        if(this._resultChangeMoney){
            this._refreshGoldShow();
            this._showGoldCount(this._resultChangeMoney);
            return true
        }
        return false
    },
    //显示玩家数值的增减
    _showGoldCount : function (count) {
        if(!count) return;

        var labelNode = cc.instantiate(this.prefab_goldValue);
        labelNode.parent = this.node;
        labelNode.zIndex = 2;

        labelNode.getComponent(this._goldValueCompName).showGrabGold(count);

        labelNode.scale = 0.1;
        var parentScale, widthOff;
        widthOff = this.node.width*0.3;
        if(this.playerType == G_TYPE.playerType.owner){
            parentScale = this.node.scale;
        }else if(this.playerType == G_TYPE.playerType.others){
            parentScale = this.node.parent.scale;
        }
        if(this.node.x > cc.visibleRect.width/2) widthOff*= -1;
        var time = G_Config_grab.time_goldOnHead;
        var act1 = cc.moveTo(time, widthOff, this.node.height*0.4);
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.delayTime(time*2);
        labelNode.runAction(cc.sequence(cc.spawn(act1,act2),act3,cc.callFunc(this._labelMoveEnd, this, count)))
    },
    _labelMoveEnd : function (target, count) {
        target.destroy();
        //this._refreshGoldShow();
        //if(this._callFunc){
        //    this._callFunc();
        //    this._callFunc = null;
        //}
    },

    _payGold : function (goldNum) {
        this._curGoldValue -= goldNum;
        if(this._curGoldValue <= 0){
            this._curGoldValue = 0;

            var table = G_DATA.getChinese(21);
            if(table){
                var dataObj = {
                    showStr : table.content,
                    showPos : G_Config_common.bottomTipPos,
                    retainTime : table.retainTime
                }
            }
        }
    },

    //设置玩家名字
    _setPlayerName : function (playerName) {
        if(!playerName) playerName = '';
        playerName = G_TOOL.getNameLimit(playerName, 10, true);
        this.label_playerName.string = playerName;
    },
    //绑定座位上的玩家
    bindSeatMyself : function (myselfComp) {
        this._seatMyselfComp = myselfComp;
    },

    bindSiteImg : function (imgNode) {
        this._siteImg = imgNode;
    },
    //重置金额的显示
    resetShowGold : function () {
        this.setGoldValue(this._curGoldValue);
    },
    setGoldValue : function (newValue) {
        this._curGoldValue = newValue;
        this.node_goldValue.getComponent(cc.Label).string = G_TOOL.changeMoney(newValue);

        //位置上的自己
        if(this._seatMyselfComp){
            this._seatMyselfComp.setGoldValue(newValue);
        }

    },
    setPlayerType : function (toType) {
        this.playerType = toType;
    },
    //押宝——设置投注金额池的变化
    reduceGoldEX : function (changeGold) {
        if(this._goldValueEX >= changeGold){
            this._goldValueEX -= changeGold;
            return 0;
        }else{
            var value = changeGold - this._goldValueEX;
            this._goldValueEX = 0;
            return value
        }
    },


    getMoneyChooseDict : function () {
        return this._dict_chooseMoney
    },
    getPokerPoint : function () {
        return cc.p(this.node.x + (this.node.width*this.node.scaleY*0.7), this.node.y)
    },
    setChooseLevel : function (level) {
        this._chooseMoneyLevel = level;
    },
    getChooseLevel : function () {
        return this._chooseMoneyLevel;
    },
    getWinGoldNum : function (resutl) {
        return this._dict_choosed[resutl]
    },
    getAllGrabTable : function () {
        return this._dict_choosed
    },
    getWorldPos : function () {
        var parentPos = this.node.parent.position;
        return cc.p(this.node.x+parentPos.x, this.node.y+parentPos.y)
        //switch (this.playerType){
        //    case G_TYPE.playerType.owner:
        //        return this.node.position
        //    case G_TYPE.playerType.system:
        //        return this.node.position
        //    case G_TYPE.playerType.others:
        //        var siteImg = this.node.parent;
        //        return cc.p(siteImg.parent.x+siteImg.x, siteImg.parent.y+siteImg.y)
        //    default:
        //        break;
        //}
    },
    getPlayerName : function () {
        return this._playerName
    },
    getGoldValue : function () {
        return this._curGoldValue
    },
    _getIsOverMaxGrabTimes : function () {
        return this._grabTimes >= GG.grabMgr.getMaxGrabTimes()
    },
    getSeatIndex : function () {
        return this._seatIndex
    },
    //是否已经投注
    getIsGrab : function () {
        if(!this._dict_choosed) return false;
        var goldNum = 0;
        for(var key in this._dict_choosed){
            goldNum += this._dict_choosed[key];
            if(goldNum>0) break;
        }
        return Boolean(goldNum)
    },
    //是否已经投注结束
    getIsBettingEnd : function () {
        return Boolean(this._grabGold)
    },
    //获取玩家ID
    getPlayerID : function () {
        return this._playerID
    },
    getIsShow : function () {
        return this.node.active
    },
    //获取可用余额
    getUsableBalance : function () {
        return this._usableBalance;
    },


    //清理关于投注的记录
    clearRecord : function () {
        //this._dict_choosed = {};
        this._grabGold = 0;
        this._goldValueEX = 0;
    },
    hidePlayer : function () {
        this.node.active = false;
    },
    remove : function () {
        this.node.destroy();
    },
    onDestroy : function () {

    },


    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
