/**
 * Created by lenovo on 2016/12/30.
 */

//游戏数据的剪接获取方式
var gameData = {
    _curHash : null,//记录刷新页面前的场景
    //tableData
    getChinese : function (keyID) {
        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.chinese);
        if(!table) return null;
        return table.getDataByID(keyID);
    },
    getBottomTipPos : function () {
        var designSize = cc.view.getDesignResolutionSize();
        return cc.p(designSize.width*0.5, designSize.height*0.17)
    },
    getTopTipPos : function () {
        var designSize = cc.view.getDesignResolutionSize();
        return cc.p(designSize.width*0.5, designSize.height*0.8)
    },
    getCenterTipPos : function () {
        var designSize = cc.view.getDesignResolutionSize();
        return cc.p(designSize.width*0.5, designSize.height*0.55)
    },
    //根据索引获取卡牌的值和类型
    getPokerInfo : function (index) {
        this.pokerValue = (index>>2)+1; //1-13对应A到K
        this.pokerType = 4-(index&3);//1-4对应黑桃，红桃，梅花，方块
        return this
    },

    //获取图片请求接口路径
    getResNetRoot : function (resUrl) {
        //return 'http://192.168.0.109:8080/static/'+resUrl
        if(G_Config_common.isLocal){
            var url = this.getLocalHref();
            var pointIndex = url.lastIndexOf(':');
            url = url.substring(0, pointIndex+5);
            url+='/static/';
            return url+resUrl
        }else return resRoot+'/'+resUrl
    },
    getLocalHref : function () {
        if(window.location){
            return window.location.href;
        }else return 'http://192.168.0.109:8080'
    },
    //获取吃瓜群众的座位ID
    getIdlePlayersSeatIndex : function () {
        return -2
    },
    //获取庄家的座位标号
    getDealerSeatIndex : function () {
        return -1
    },
    //获取场景的名字
    getSceneName : function (sceneType) {
        var sceneName;
        switch (parseInt(sceneType)){
            case G_TYPE.gameModule.bull100:
                sceneName = 'Poker_Bull100';
                break
            case G_TYPE.gameModule.classic:
                sceneName = 'Poker_classic';
                break
            case G_TYPE.gameModule.grab:
                sceneName = 'Poker_Grab';
                break
            default:
                break
        }
        return sceneName
    },

    //获取游戏所处的平台
    getOS : function () {
        switch (cc.sys.os){
            case cc.sys.OS_ANDROID:
                break
            case cc.sys.OS_IOS:
                break
            default:
                break
        }
        return cc.sys.os
    },
    //获取浏览器类型
    getBrowserType : function () {
        switch (cc.sys.browserType){
            case cc.sys.BROWSER_TYPE_QQ:
                break
            case cc.sys.BROWSER_TYPE_SAFARI:
                break
            case cc.sys.BROWSER_TYPE_ANDROID:
                break
            default:
                break
        }
        return cc.sys.browserType
    },

    //根据进房类型获取进游戏房时候需要的请求类型
    getEnterHomeModel : function (enterType) {
        var gameType;
        switch (parseInt(enterType)){
            case G_TYPE.gameModule.bull100:
                gameType = G_TYPE.net_gameModule.bull100;
                break
            case G_TYPE.gameModule.grab:
                gameType = G_TYPE.net_gameModule.grab;
                break
            case G_TYPE.gameModule.classic:
                gameType = G_TYPE.net_gameModule.classic;
                break
            default:
                break
        }
        return gameType
    },

    //判定是否是微信浏览器
    getIsWinXin : function () {
        var ua = window.navigator.userAgent.toLowerCase();
        if(ua.match(/MicroMessenger/i) == 'micromessenger'){
            return true;
        }else{
            return false;
        }
    },

    isNumber : function (data) {
        return Boolean(Object.prototype.toString.call(data)==='[object Number]')
    },

    //=========================  cookie name = poker_roomInfo

    //获取上次登陆的场景
    getLastSceneHash : function () {
        if(cc.sys.isNative){
            return null;
        }
        var hashData = this._getCookie('poker_roomInfo');
        // var hashData = document.cookie;
        if(!hashData) return null;
        var data = {
            enterType : null,
            roomId : null
        }
        var strList = hashData.split('-');
        var enterType = strList[0];
        if(!enterType) return null;
        data.enterType = enterType;
        data.roomId = parseInt(strList[1]);
        return data
    },
    //设置当前的场景
    setCurSceneHash : function (enterType, roomId) {
        if(cc.sys.isNative){
            return;
        }
        this._setCookie('poker_roomInfo', enterType+"-"+roomId);
    },
    _setCookie : function(name,value) {
        // var Days = 0.5;
        // var exp = new Date();
        // exp.setTime(exp.getTime() + Days*24*60*60*1000);
        // document.cookie = name + "="+ encodeURI (value) + ";expires=" + exp.toGMTString();
        document.cookie = name + "="+ encodeURI (value) + ";"
    },
    _getCookie : function(name) {
        var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
        if(arr=document.cookie.match(reg))
            return encodeURIComponent(arr[2]);
        else
            return null;
    },

    //============================

    //设置富豪榜
    setRichData : function (data) {
        this._richData = {};
        for(var key in data){
            this._richData[key] = data[key];
        }
    },
    //设置盈利榜单
    setGainsData : function (data) {
        this._gainsData = {};
        for(var key in data){
            this._gainsData[key] = data[key];
        }
    },
    //获取富豪榜
    getRichData : function () {
        if(G_Config_common.isLocal){
            var _richRankData = {};
            _richRankData.code = 1;
            _richRankData.data = [];
            for(var i = 0; i < 3; i ++){
                _richRankData.data.push({
                    nickname:'997_'+i,
                    walletbalance:667,
                    avatarurl:'',
                });
            }
            return _richRankData
        }else return this._richData
    },
    //获取盈利榜单
    getGainsData : function () {
        if(G_Config_common.isLocal){
            var _gainsRankData = {};
            _gainsRankData.code = 1;
            _gainsRankData.data = [];
            for(var i = 0; i < 20; i ++){
                _gainsRankData.data.push({
                    nickname:'1111',
                    walletbalance:3333,
                    avatarurl:'',
                });
            }
            return _gainsRankData
        }else return this._gainsData
    },
    //======================

    //获取房间列表的json配置
    getRoomListJson : function (callFunc) {
        if(!this._roomListJson){
            var self = this;
            self._roomListJson = null;
            cc.loader.load(G_DATA.getResNetRoot('config/room.data.json'), function(err, data) {
                if(err){

                }else{
                    self._roomListJson = data;
                }
                if(callFunc){
                    callFunc(self._roomListJson);
                    callFunc = null;
                }
                // GG.platformMgr.showRoomUI(self._roomPrefabName, function (roomComp) {
                //     if (roomComp) {
                //         self._compHomeList = roomComp;
                //         self._requestEnter(enterType, isNoEffect);
                //     }
                // });
            });
        }else{
            if(callFunc){
                callFunc(this._roomListJson);
                callFunc = null;
            }
        }
    },
}

window.G_DATA = gameData;