//WebSocket 状态判定   ===》 WebSocket.readyState
//OPEN      连接已开启并准备好进行通信。
//CLOSING  连接正在关闭的过程中。
//CLOSED    连接已经关闭，或者连接无法建立。
//CONNECTING 连接还没开启。

//webSocket  管理器
var NetServer = require('SocketCenter');
var socketMgr = cc.Class({
    //extends: cc.Component,
    _socketCenter : null,                                   //当前使用的socket对象
    _dict_shortEvents : null,                              //短对接的事件，一般是主动的请求
    _dict_longEvents : null,                               //长对接的事件，被动接收的事件
    _dict_pauseInfo : null,                                //暂停时候保存下来的信息
    _list_sendFail : null,                                 //发送请求失败的协议
    _isHasBreak : null,                                    //是否已经端过网络

    _heartIntervalID : null,                               //心跳的循环ID
    _timeOutID1 : null,                                     //请求超时ID
    _dict_recvNetSpecial : null,                           //接收到异常处理信息
    properties: {

    },

    // use this for initialization
    ctor: function () {
        //GG.socketMgr = this;

        this._dict_shortEvents = {};
        this._dict_longEvents = {};
        this._dict_recvNetSpecial = {};
        this._isHasBreak = false;
    },

    //增加socket链接
    connectSocket : function (callFunc) {
        if(this._socketCenter) return;
        //新建一个socket链接
        var host, port;
        if(cc.sys.isNative){
            host = '192.168.0.100/player/';
            port = 8888;//默认端口
        }else{
            host = GG.webHandler.getLocalHost();
        }
        if (host.indexOf(":") > -1)host = host.substring(0, host.indexOf(":"));
        if(host == "")host="localhost";
        if(window.wsPort) port = window.wsPort;
        var url = "ws://"+host+":"+port+"/ws";

        this._socketCenter = new NetServer(url, null, false);
        this._socketCenter.msgReceived = this.receiveMsg.bind(this);
        var self = this;
        this._socketCenter.onOpen = function () {
            self._whenOpen(callFunc);
        }
        this._socketCenter.onClose = function () {
            self._whenClose();
        }
        this._connect();
    },

    _whenOpen : function (callFunc) {
        if(this._list_sendFail){
            var netData;
            for(var i = 0; i < this._list_sendFail.length; i ++){
                netData = this._list_sendFail[i]
                this.SendMsg(netData.netType, netData.msgDict, netData.callFunc);
            }
        }
        this._list_sendFail = null;
        if(this._isHasBreak) {
            //如果已经断过链接，则重连的时候需要返回房间
            GG.topTouchLayer.closeNetRequest();
            this._cancelTimeOutCheck();
            this._isHasBreak = false;
            if (!this._isLimit) if(GG.getGameType() != G_TYPE.gameModule.platform) GG.exitHome();
        }
        if (!this._isLimit) GG.loginSocket();
        this._isLimit = false;

        if(callFunc) {
            callFunc(true);
            callFunc = null;
        }
    },
    _whenClose : function () {
        this._connect();
        this._isHasBreak = true;
        if (this._isLimit) this._startTimeOutCheck();
    },

    _connect : function () {
        if(this._socketCenter) {
            this._socketCenter.connect();
            this._addHeartInfo();
        }
    },

    getIsConnected : function () {
        return Boolean(this._socketCenter && this._socketCenter.isConnected())
    },

    getServerTime : function () {
        if(this._socketCenter) return this._socketCenter.getServerTime()
        else return (new Date()).getTime()
    },

    //发送数据
    SendMsg : function(netType, msgDict, callFunc) {
        if(this._dict_shortEvents.hasOwnProperty(netType)) return;

        var msg;
        if(NetBean[netType]){
            msg = new NetBean[netType]();
            for(var key in msgDict){
                msg[key] = msgDict[key];
            }
            // if(callFunc) this._dict_shortEvents[netType] = callFunc;
        }else {
            // console.log('no this nettype on server==='+netType)
            if(!this._list_sendFail) this._list_sendFail = [];
            this._list_sendFail.push({
                netType : netType,
                msgDict : msgDict,
                callFunc : callFunc
            });
            return;
        }
        if(callFunc) this._autoRegisterListenerFunc(netType, callFunc);
        //console.log('发送的数据是')
        //console.log(msg)
        this._socketCenter.send(netType, msg);
    },
    _autoRegisterListenerFunc : function (netType, callFunc) {
        switch (netType){
            case NetType.s_enterHouse:
                this.registerLong(NetType.r_enterHouseReturn, callFunc);
                break
            default:
                break
        }
    },

    //接受数据
    receiveMsg : function(netType, recvData) {
        //console.log('接受数据:: '+netType)
        //console.log(recvData)
        //console.log(recvData.tip.tip)
        var gameMain = cc.find('Canvas');
        //游戏已经关闭，不需要消息
        if(!gameMain || !gameMain.active) return true;

        if(GG.getGameState() == G_TYPE.webGameState.pause){
            //游戏暂停了
            this._savePauseInfo(netType, recvData);
            return false;
        }

        //该功能函数没有完善，只会返回true
        var isOk = this._checkCode(recvData);
        if(!isOk){
            // console.log('get server data fail'+recvData.tip.tip)
            return false;
        }
        //需要全局接收消息的协议
        this._globalNetType(netType, recvData);
        //加载场景中对某些协议进行处理
        if(GG.getIsLoading()) {
            var isStop = this._setLoadingNetData(netType, recvData);
            if(isStop) return;
        }


        var isContinue = true;
        if(this._dict_shortEvents && this._dict_shortEvents[netType]){
            //主动请求的消息
            this._dict_shortEvents[netType](recvData);
            delete this._dict_shortEvents[netType];
            isContinue = false;
        }else if(this._dict_longEvents && this._dict_longEvents[netType]){
            //
            this._dict_longEvents[netType](recvData);
            isContinue = false;
        }
        return isContinue
    },

    //注册长监听事件
    registerLong : function (netType, callFunc) {
        //if(this._dict_longEvents.hasOwnProperty(netType)) return null;
        this._dict_longEvents[netType] = callFunc;
    },
    //取消某个长监听
    cancelLong : function (netType) {
        delete this._dict_longEvents[netType];
    },
    //取消所有长监听
    cancelAllLongListen : function () {
        for(var key in this._dict_longEvents){
            delete this._dict_longEvents[key]
        }
        for(var key in this._dict_shortEvents){
            delete this._dict_shortEvents[key]
        }
    },

    //================================

    //获取两个时间戳的间隔
    getOffSeconds : function (startTime, endTime) {
        return Math.max(Math.floor((endTime - startTime) * 0.001), 0);
    },

    //添加心跳
    _addHeartInfo : function () {
        if(G_DATA.isNumber(this._heartIntervalID)){
            clearInterval(this._heartIntervalID);
            this._heartIntervalID = null;
        }
        var heartLastTime = new Date().getTime();
        this._heartIntervalID = setInterval(function () {
            var now = new Date().getTime();
            if(now - heartLastTime >= 3500){//心跳连接至少每3.5秒一次
                if(this._socketCenter && this._socketCenter.isConnected()) this._socketCenter.sendHeart();
                heartLastTime = new Date().getTime();
            }
        }.bind(this), 500, null);
    },

    //检查是否是可用信息
    _checkCode : function (recvData) {
        if(!recvData.tip) return true;
        switch (recvData.tip.code){
            case G_TYPE.serverCodeType.success:
                //success
                break
            case G_TYPE.serverCodeType.serverErr1:
                //服务器忙
                GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip);
                /*, function () {
                 GG.exitHome();
                 }*/
                return false;
            default:
                return true
        }
        return true;
    },

    //通过某些协议刷新玩家金币消息
    _globalNetType : function (netType, recvData) {
        switch (netType){
            case NetType.r_mineHaveGrab:
                var tip = recvData.tip;
                if(tip.code != G_TYPE.serverCodeType.success) {
                    return;
                }
                var dataObj = G_OBJ.data_nbSelf();
                dataObj.balance = recvData.player.balance;
                dataObj.usableBalance = recvData.usableBalance;
                GG.getPlayer().setPlayerGold(dataObj);
                //GG.Listener.dispatchEventEX(G_TYPE.globalListener.playerGold, dataObj);
                break
            case NetType.r_nbSelf:
                var tip = recvData.tip;
                if(!tip || tip.code == G_TYPE.serverCodeType.success) {
                    //接收成功
                    var dataObj = G_OBJ.data_nbSelf();
                    dataObj.balance = recvData.balance;
                    dataObj.usableBalance = recvData.usableBalance;
                    GG.getPlayer().setPlayerGold(dataObj);
                }
                break
            default:
                break
        }
    },

    //拦截场景加载中途传入的数据
    _setLoadingNetData : function (netType, recvData) {

        switch (netType){
            case NetType.r_bull100_reStartGame:
                //刷新进房信息_百人
                GG.curMgr.updateEnterHomeData(recvData);

                var homeData = GG.getPlayer().getInHomeData();
                if(homeData){
                    homeData.net_resetHomeData = recvData;
                    GG.getPlayer().setInHomeData(homeData);
                }
                break;
            case NetType.r_grab_reStartGame:
                //刷新进房信息_押宝
                GG.curMgr.updateEnterHomeData(recvData);

                var homeData = GG.getPlayer().getInHomeData();
                if(homeData){
                    homeData.net_resetHomeData = recvData;
                    GG.getPlayer().setInHomeData(homeData);
                }
                break;
            case NetType.r_enterHouseReturn:
                //加载时候可以请求入房
                return false
            default:
                break;
        }
        return true
    },

    //=====================暂停处理

    //保存暂停时候接收的数据
    _savePauseInfo : function (netType, recvData) {
        // console.log('when game pause ::'+netType)

        // if(!this._dict_pauseInfo) this._dict_pauseInfo = {};

        ////记录暂停前的金币
        //if(!this._goldWhenPause) {
        //    if(GG.getModuleMgr().getMyselfComp) this._goldWhenPause = GG.getModuleMgr().getMyselfComp().getGoldValue();
        //}
        //if(netType == NetType.r_otherGrab){
        //    //有人有投注行为
        //    if(!this._dict_pauseInfo[netType]) this._dict_pauseInfo[netType] = [];
        //    this._dict_pauseInfo[netType] = this._dict_pauseInfo[netType].concat(recvData.bets);
        //}else{
        //    this._dict_pauseInfo[netType] = recvData;
        //    //结算后清理
        //    if(netType == NetType.r_grabEnd) this._dict_pauseInfo[NetType.r_otherGrab] = null;
        //}

        //处理接收到的数据
        if(GG.curMgr){
            switch (netType){
                case NetType.r_bull100_reStartGame:
                    //刷新进房信息_百人
                    GG.curMgr.updateEnterHomeData(recvData);
                    break;
                case NetType.r_grab_reStartGame:
                    //刷新进房信息_押宝
                    GG.curMgr.updateEnterHomeData(recvData);
                    break;
                case NetType.r_grabEnd:
                    //刷新进房信息
                    GG.curMgr.setBettingEndInfo(recvData);
                    break;
                case NetType.r_mineHaveGrab:
                    //可能在暂停之前玩家已经有投注,则需要记录金额变化
                    if(recvData.player){
                        if(!isNaN(recvData.player.balance)) {
                            GG.curMgr.getMyselfComp().setGoldValue(recvData.player.balance);
                        }
                    }
                    break;
                case NetType.r_idleTimeOut:
                    //长时间没有做操作，已经被T
                    this._dict_recvNetSpecial[NetType.r_idleTimeOut] = recvData;
                    break;
                case NetType.r_passiveOut:
                    //登录异常
                    this._dict_recvNetSpecial[NetType.r_passiveOut] = recvData;
                    break;
                default:
                    break;
            }
        }
    },
    //当恢复游戏的时候，解析暂停时候收到的消息
    parsePauseInfo : function () {
        if(GG.curMgr){
            GG.curMgr.refreshHome();

            for(var netType in this._dict_recvNetSpecial){
                switch (netType){
                    case NetType.r_idleTimeOut:
                        GG.curMgr.net_idleTimeOut(this._dict_recvNetSpecial[netType]);
                        break
                    case NetType.r_passiveOut:
                        GG.curMgr.net_passiveOut(this._dict_recvNetSpecial[netType]);
                        break
                    default:
                        break
                }
            }
            this._dict_recvNetSpecial = {};
            return
        }
    },

    //=======================================请求延时判定

    //计算网络请求时间
    _startTimeOutCheck : function () {
        this._cancelTimeOutCheck();
        var time = 6000;
        var self = this;
        this._timeOutID1 = setTimeout(function () {
            GG.topTouchLayer.closeNetRequest();
            GG.showReconnect(function () {
                self._whenClose();
                GG.topTouchLayer.showNetRequest(true);
            });
        }, time)
    },
    //取消超时监听
    _cancelTimeOutCheck : function () {
        if(G_DATA.isNumber(this._timeOutID1)){
            clearTimeout(this._timeOutID1);
        }
    },
    //异地登陆后取消超长监听
    setTimeOutLimit : function (isLimit) {
        this._cancelTimeOutCheck();
        this._isLimit = isLimit;
    },


    //是否已经连接socket
    //getIsConnecting : function () {
    //    return this._socketObj && this._socketObj.readyState == WebSocket.OPEN;
    //},

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});

module.exports = socketMgr;