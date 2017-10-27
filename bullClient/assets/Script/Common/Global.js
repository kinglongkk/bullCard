//配置全局使用的管理器
var gg = gg || {};
window.GG = gg;

//经典对战管理器
GG.classicMgr = null;
//夺宝对战
GG.grabMgr = null;
//大厅管理
GG.platformMgr = null;
//百人大战管理
GG.bull100Mgr = null;
//获取当前游戏的管理器--------------------每次推出场景，需要将当前的场景管理器置空
GG.getModuleMgr = function () {
    var mgrList = [GG.grabMgr, GG.classicMgr, GG.platformMgr, GG.bull100Mgr];
    for(var i = 0; i < mgrList.length; i ++){
        if(mgrList[i]) return mgrList[i];
    }
    return null;
}
GG.curMgr = null;


//弹窗提示管理器
GG.tipsMgr = null;
//所有预加载资源管理
GG.resMgr = null;


//=======================================================

//玩家对象
GG._player = null;
GG.getPlayer = function () {
    if(!this._player){
        var player = require('M_Player');
        this._player = new player();
    }
    return this._player;
}
GG.getPlayerID = function () {
    return this.getPlayer().getPlayerID();
}
//弱联网管理器
GG.httpMgr = GG.httpMgr || (function () {
        var http = require('HTTPMgr');
        return new http()
    })();

//socket网络请求管理器
GG.socketMgr =  GG.socketMgr || (function () {
        var socket = require('SocketMgr');
        return new socket()
    })();

//配置文件管理器
GG.tableMgr =  GG.tableMgr || (function () {
        var tableMgr = require('TablesMgr');
        return new tableMgr()
    })();

//配置文件管理器
GG.audioMgr =  GG.audioMgr || (function () {
        var audioMgr = require('AudioMgr');
        return new audioMgr()
    })();

//全局的监听管理
GG.Listener =  GG.Listener || (function () {
        var Listener = require('Global_listener');
        return new Listener()
    })();
//最上面的触摸层
GG.topTouchLayer = GG.topTouchLayer || (function () {
        var topLayer = require('M_TopRequestLayer');
        return new topLayer()
    })();

//资源管理器
GG.resMgr = GG.resMgr || (function () {
        var mgr = require('ReloadResMgr');
        return new mgr()
    })()

//socket数据请求管理器
GG.s_requestMgr = GG.s_requestMgr || (function () {
        var mgr = require('SocketRequestMgr');
        return new mgr()
    })()

//web相关操作
GG.webHandler = GG.webHandler || (function () {
        var handler = require('WebMobile_handler');
        return new handler();
    })()

//=========================

//当前的游戏状态
GG.getGameState = function(){
    return this._gameState
}

//当前的游戏模式
GG.getGameType = function(){
    var canvas = cc.find('Canvas');
    if(canvas){
        var startComp = canvas.getComponent('GameStart');
        if(startComp){
            return startComp.getGameType()
        }
    }
    return null
}

//退出赛事所在的房间
GG.exitHome = function () {
    //需要增加一个正在退出的逻辑，给释放缓存一定的时间
    var dataObj = {
        gameType : this.getGameType(),
    }
    this.getPlayer().setOutHomeData(dataObj);
    G_DATA.setCurSceneHash('',0);
    GG.changeScene(G_TYPE.sceneName.platform);
}

//退出游戏
GG.exitGame = function () {
    GG.httpMgr.sendHttpRequest(G_DIALOG_URL.logout, null, function (data) {
        if(data){
            cc.director.end();
            location.replace(location.href)
        }
    }.bind(this));
}

//加载场景
GG.changeScene = function (sceneName) {
    GG.getPlayer().setReloadSceneInfo({
        sceneName : sceneName
    });
    cc.director.preloadScene('InHomeLoading', function () {
        cc.director.loadScene('InHomeLoading');
    });
};

//发起是否重连的请求; callFunc : 点击确定后的回调
GG.showReconnect = function (callFunc) {
    if(GG.tipsMgr){
        var tableData = G_DATA.getChinese(62);
        GG.tipsMgr.showConfirmTip_TWO(tableData.content, callFunc, function () {
            //直接关闭游戏
            cc.director.end();
            location.replace(location.href)
        });
    }
}

//是否正在加载中
GG.getIsLoading = function () {
    return this._isLoading
}
//正在加载中
GG.setIsLoading = function (isLoading) {
    this._isLoading = isLoading;
}
//登陆socket
GG.loginSocket = function (func) {
    //登陆socket
    GG.socketMgr.SendMsg(NetType.s_login,{
        token : GG.getPlayer().getPlayerToken(),
        userId : GG.getPlayer().getUID()
    }, func);
};

GG._isFirstEnterPlatform = true;
GG._gameState = null;//游戏暂停和开始的状态
//刚登陆后的处理
GG.whenGameStart = function (callBack) {
    if(!GG._isFirstEnterPlatform) return false;
    cc.game.config.showFPS = false;
    //初次进入游戏
    GG._isFirstEnterPlatform = false;
    //自由旋转
    // cc.view.setOrientation(cc.macro.ORIENTATION_AUTO);
    //增加游戏暂停和开始的监听
    this._gameState = G_TYPE.webGameState.running;
    if(!cc.sys.isNative && document){
        GG.webHandler.gameStart();
    }else{
        cc.game.on(cc.game.EVENT_HIDE, this._pauseWebGame, this);
        cc.game.on(cc.game.EVENT_SHOW, this._resumeWebGame, this);
    }

    //加载表格配置
    var tables = [];
    tables.push(G_RES_URL.dict_tablesName.commonConfig);
    tables.push(G_RES_URL.dict_tablesName.chinese);
    tables.push(G_RES_URL.dict_tablesName.audioName);
    tables.push(G_RES_URL.dict_tablesName.grabConfig);
    GG.tableMgr.reloadTables(tables, function () {
        //加载音效配置
        GG.audioMgr.reloadPlatformAudio(function () {
            if(callBack){
                callBack();
                callBack = null;

            }
        })
    });

    // cc.game.setFrameRate(49);
    return true
}
GG._pauseWebGame = function () {
    if(this._gameState == G_TYPE.webGameState.pause) return;
    this._gameState = G_TYPE.webGameState.pause;
    //cc.Audio.useWebAudio = true
    cc.audioEngine.pauseAll();
    GG.audioMgr.stopMusic();
    var mgr = GG.curMgr;
    if(mgr && mgr.whenPauseGame){
        mgr.whenPauseGame();
    }
    cc.sys.garbageCollect();
};
GG._resumeWebGame = function () {
    if(this._gameState == G_TYPE.webGameState.running) return;
    this._gameState = G_TYPE.webGameState.running;
    cc.audioEngine.resumeAll();
    GG.audioMgr.playMusic();
    var mgr = GG.curMgr;
    if(mgr && mgr.whenResumeGame){
        mgr.whenResumeGame();
    }
    GG.socketMgr.parsePauseInfo();
};