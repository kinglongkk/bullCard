require=(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);var f=new Error("Cannot find module '"+o+"'");throw f.code="MODULE_NOT_FOUND",f}var l=n[o]={exports:{}};t[o][0].call(l.exports,function(e){var n=t[o][1][e];return s(n?n:e)},l,l.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({"AudioMgr":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f9da7zP5+VFlLLli2GUyvCm', 'AudioMgr');
// Script\Common\Managers\AudioMgr.js

//音效管理器
// music 是音乐； sound是音效
var audioMgr = cc.Class({
    ctor: function ctor() {
        this._musicVolume = 1; //音乐音量大小
        this._soundVolume = 1; //音效音量大小
        //this._dict_audioConfig = {'music':true,'sound':true};                  //音乐音效的配置
        this._dict_audioConfig = null; //音乐音效的配置
        this._bgMusicID = -1; //背景音乐
        this._bgMusicName = null; //背景音乐
        this._isReloadPlatform = false; //是否已经加载大厅音效
        this._isReloadGame = false; //是否已经加载游戏房间音效
        this._reloadPlatformURL = ''; //使用音频格式的加载路劲
        this._reloadGameRoomURL = ''; //使用音频格式的加载路劲
        this._dict_audioUrl = {}; //存放所有音效的名字对应路径
        //环境检测
        this._environmentConfirm();
    },

    //播放音乐
    playMusic: function playMusic(audioID) {
        if (!audioID) audioID = 19;
        var musicName = this.getAudioName(audioID);
        if (this._musicVolume <= 0) {
            //暂停音乐
            if (this._bgMusicName) {
                cc.audioEngine.stop(this._bgMusicID);
                this._bgMusicName = null;
            }
        } else {
            //开启音乐
            if (musicName) {
                if (this._bgMusicName !== musicName) {
                    //换音乐
                    this._bgMusicName = musicName;
                    var url = this.getAudio(this._bgMusicName);
                    //console.log('播放背景音乐'+url)
                    this._bgMusicID = cc.audioEngine.play(url, true);
                    //this._bgMusicID = cc.audioEngine.playMusic(url, true);
                }
            } else {
                    //if(this._bgMusicName) {
                    //    //播放上次的音乐
                    //    cc.audioEngine.resume(this._bgMusicID);
                    //}
                }
        }
    },

    //播放音效
    playSound: function playSound(audioID) {
        if (this._soundVolume <= 0) return;
        var url = this.getAudio(this.getAudioName(audioID));
        //console.log('播放音效'+url);
        return cc.audioEngine.play(url, false);
        //return cc.audioEngine.playEffect(url, false);
    },

    //调整音乐大小
    setMusicVolume: function setMusicVolume(num) {
        this._musicVolume = num;
        this.playMusic();
    },

    //调整音效大小
    setSoundVolume: function setSoundVolume(num) {
        this._soundVolume = num;
    },

    //停止某个音效
    stopAudio: function stopAudio(audioID) {
        cc.audioEngine.stop(audioID);
    },

    //停止音乐
    stopMusic: function stopMusic() {
        if (this._bgMusicName) {
            cc.audioEngine.stop(this._bgMusicID);
            this._bgMusicName = null;
        }
    },

    //获取音频文件
    getAudio: function getAudio(audioName) {
        if (!this._dict_audioUrl[audioName]) return cc.url.raw(G_TOOL.formatStr(G_RES_URL.audioUrl, audioName));
        return this._dict_audioUrl[audioName];
    },

    //获取音乐是否正在播放
    getIsPlayMusic: function getIsPlayMusic() {
        if (this._bgMusicName && this._musicVolume > 0) return true;
        return false;
    },

    //=====================================
    //获取音效配置
    getAudioConfig: function getAudioConfig() {
        return this._dict_audioConfig;
    },
    //设置音效配置
    setAudioConfig: function setAudioConfig(dict) {
        this.setMusicVolume(dict.music ? 1 : 0);
        this.setSoundVolume(dict.sound ? 1 : 0);
        this._dict_audioConfig = dict;
    },
    //保存音乐配置
    saveMusicConfig: function saveMusicConfig(isPlay) {
        this.setMusicVolume(isPlay ? 1 : 0);
        this._dict_audioConfig.music = isPlay;
    },
    //保存音效配置
    saveSoundConfig: function saveSoundConfig(isPlay) {
        this.setSoundVolume(isPlay ? 1 : 0);
        this._dict_audioConfig.sound = isPlay;
    },
    //=====================================
    //获取某个音频的名字
    getAudioName: function getAudioName(audioID) {
        var tableObj = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.audioName);
        var data = tableObj.getDataByID(audioID);
        if (data) return data.audioName;
        return null;
    },

    //预加载音频资源
    //reloadAudio : function (callBack) {
    //    var self = this;
    //    cc.loader.loadResDir('Sounds', function (err, audioList) {
    //        var isOk = false;
    //        if(!err){
    //            var content = audioList[0];
    //            var index = content.lastIndexOf('/');
    //            self._basePlatformUrl = content.substring(0, index+1)+self._audioFormatStr;
    //            isOk = true;
    //        }else {
    //            //加载音效发生问题
    //        }
    //        if(callBack){
    //            callBack(isOk);
    //            callBack = null;
    //        }
    //    })
    //},

    //加载大厅音效
    reloadPlatformAudio: function reloadPlatformAudio(callBack) {
        var self = this;
        cc.loader.loadResDir(this._reloadPlatformURL, function (err, audioList) {
            var isOk = false;
            if (!err) {
                var audioUrl, urlEnd, audioName;
                for (var i = 0; i < audioList.length; i++) {
                    audioUrl = audioList[i];
                    urlEnd = audioUrl.substring(audioUrl.lastIndexOf('/') + 1, audioUrl.length);
                    audioName = urlEnd.split('.')[0];
                    self._dict_audioUrl[audioName] = audioUrl;
                }
                self._isReloadPlatform = true;
            } else {
                //加载音效发生问题
            }
            if (callBack) {
                callBack(isOk);
                callBack = null;
            }
        });
    },

    //加载游戏房间内的音效
    reloadGameAudio: function reloadGameAudio(callBack) {
        var self = this;
        cc.loader.loadResDir(this._reloadGameRoomURL, function (err, audioList) {
            if (!err) {
                //没有加载错误
                var audioUrl, urlEnd, audioName;
                for (var i = 0; i < audioList.length; i++) {
                    audioUrl = audioList[i];
                    urlEnd = audioUrl.substring(audioUrl.lastIndexOf('/') + 1, audioUrl.length);
                    audioName = urlEnd.split('.')[0];
                    self._dict_audioUrl[audioName] = audioUrl;
                }
                self._isReloadGame = true;
            }
            if (callBack) {
                callBack();
                callBack = null;
            }
        });
    },
    getIsReloadPlatform: function getIsReloadPlatform() {
        return this._isReloadPlatform;
    },
    getIsReloadGame: function getIsReloadGame() {
        return this._isReloadGame;
    },

    //平台鉴定
    _environmentConfirm: function _environmentConfirm() {
        //switch (cc.sys.os){
        //    case cc.sys.OS_ANDROID:
        //        //android手机用ogg
        //        //this._reloadPlatformURL = 'Sounds/ogg_platform';
        //        //this._reloadGameRoomURL = 'Sounds/ogg_gameRoom';
        //        this._reloadPlatformURL = 'Sounds/mp3_platform';
        //        this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
        //        break
        //    case cc.sys.OS_IOS:
        //        //ios手机用mp3
        //        this._reloadPlatformURL = 'Sounds/mp3_platform';
        //        this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
        //        break
        //    default:
        //        break
        //}
        if (cc.sys.isNative) {
            this._reloadPlatformURL = 'Sounds/mp3_platform';
            this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
        } else {
            var audio = new Audio();
            if (audio.canPlayType("audio/mp3")) {
                this._reloadPlatformURL = 'Sounds/mp3_platform';
                this._reloadGameRoomURL = 'Sounds/mp3_gameRoom';
            } else if (audio.canPlayType("audio/ogg")) {
                this._reloadPlatformURL = 'Sounds/ogg_platform';
                this._reloadGameRoomURL = 'Sounds/ogg_gameRoom';
            }
        }
    }
});

module.exports = audioMgr;

cc._RFpop();
},{}],"AutoDealing":[function(require,module,exports){
"use strict";
cc._RFpush(module, '2ff73PoWa1GZ53LXCiuQ9TW', 'AutoDealing');
// Script\Objects\AutoDealing.js

//简单基类，实现按钮的注册与释放

cc.Class({
    "extends": cc.Component,
    _list_registerBtn: null, //注册事件的按钮集合
    properties: {},

    // use this for initialization
    onLoad: function onLoad() {},

    //注册按钮点击效果
    registerButton: function registerButton(node, callBack, target, userData, isNoScale) {
        if (!node) return;
        if (!this._list_registerBtn) this._list_registerBtn = [];
        node._isTouchEnabledEx = true;
        this._list_registerBtn.push([node, callBack, target]);
        node.on(cc.Node.EventType.TOUCH_START, function (event) {
            //if(event.target._isTouchEnabledEx) event.target.scale = G_Config_classic.scale_buttonTouch;
            if (event.target._isTouchEnabledEx) {
                if (!isNoScale) {
                    if (!event.target.lastScale) event.target.lastScale = event.target.scale;
                    event.target.scale = event.target.lastScale * 0.9;
                }
                GG.audioMgr.playSound(17);
            }
        }, target);
        node.on(cc.Node.EventType.TOUCH_END, function (event) {
            if (event.target.lastScale) event.target.scale = event.target.lastScale;else event.target.scale = 1;
            if (event.target._isTouchEnabledEx) callBack.call(target, event, userData);
        }, target);
        node.on(cc.Node.EventType.TOUCH_CANCEL, function (event) {
            if (event.target.lastScale) event.target.scale = event.target.lastScale;else event.target.scale = 1;
        }, target);
    },
    //设置按钮是否可用
    setBtnEnable: function setBtnEnable(node, isEnable, isNone) {
        if (isEnable) {
            node.opacity = 255;
        } else {
            if (!isNone) node.opacity = 255 * G_Config_common.btnGrayValue;
        }
        var btn = node.getComponent(cc.Button);
        if (btn) btn.enabled = isEnable;
        node._isTouchEnabledEx = isEnable;
    },

    //释放事件,这里注册的才能在这里释放
    releaseButtonEvent: function releaseButtonEvent() {
        var parm1, parm2, parm3;
        for (var key in this._list_registerBtn) {
            parm1 = this._list_registerBtn[key][0];
            parm2 = this._list_registerBtn[key][1];
            parm3 = this._list_registerBtn[key][2];
            parm1.off(cc.Node.EventType.TOUCH_START, parm2, parm3);
            parm1.off(cc.Node.EventType.TOUCH_END, parm2, parm3);
            parm1.off(cc.Node.EventType.TOUCH_CANCEL, parm2, parm3);
        }
        this._list_registerBtn = null;
    },

    onDestroy: function onDestroy() {
        this.releaseButtonEvent();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"BaseDialog":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f6109FzSJ9AfKnDL7rPu4MO', 'BaseDialog');
// Script\Views\BaseDialog.js

//弹窗列表界面的父类

cc.Class({
    'extends': require('BaseUI'),

    properties: {
        _parentComp: null, //容器的脚本

        node_closeBtn: {
            'default': null,
            type: cc.Node,
            displayName: '关闭按钮'
        },
        prefab_leftButton: {
            'default': null,
            type: cc.Prefab,
            displayName: '左边按钮预制体'
        },
        comp_leftBtnContainer: {
            'default': null,
            type: require('Obj_dealScroll'),
            displayName: '左边按钮容器'
        },
        prefab_item: {
            'default': null,
            type: cc.Prefab,
            displayName: '数据条目'
        },
        comp_itemContainer: {
            'default': null,
            type: require('Obj_dealScroll'),
            displayName: '条目容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerCloseEvent();
    },

    bindContainer: function bindContainer(comp) {
        this._parentComp = comp;
    },

    setData: function setData() {},

    _registerCloseEvent: function _registerCloseEvent() {
        if (this.node_closeBtn) this.registerButton(this.node_closeBtn, this._onClick_close, this);
    },

    _onClick_close: function _onClick_close(event) {
        this.hideLayer();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseUI":"BaseUI","Obj_dealScroll":"Obj_dealScroll"}],"BaseManager":[function(require,module,exports){
"use strict";
cc._RFpush(module, '81a4a4XtK1Om55doheYVRiA', 'BaseManager');
// Script\Views\BaseManager.js

//管理器的父类

cc.Class({
    "extends": cc.Component,

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {
        GG.curMgr = this;
        this.addLongListen();
        GG.setIsLoading(false);
    },

    //在游戏暂停的时候，还需要持续更新进房信息
    updateEnterHomeData: function updateEnterHomeData(recvData) {
        if (!this._enterHomeData) return;
        if (this._enterHomeData.match.beginTimeNext != recvData.match.beginTimeNext) {
            //进入到下一局比赛
            this._isPauseOverMatch = true;
        }
        this._enterHomeData.seats = recvData.seats;
        this._enterHomeData.match.dealer = recvData.match.dealer;
        this._enterHomeData.match.beginTimeNext = recvData.match.beginTimeNext;
        this._enterHomeData.match.settleTime = recvData.match.settleTime;
        if (recvData.poker) this._enterHomeData.poker = recvData.poker;
    },
    //使用最新的进房信息刷新页面
    refreshHome: function refreshHome() {
        if (G_Config_common.isLocal) return;
        if (this.net_setHomeData && this._enterHomeData) this.net_setHomeData(this._enterHomeData);
    },
    //当暂停的时候触发该函数
    whenPauseGame: function whenPauseGame() {
        this._isPauseOverMatch = false;
    },
    //当恢复游戏的时候触发该函数
    whenResumeGame: function whenResumeGame() {
        if (this.comp_uiLayer) {
            this.comp_uiLayer.closeAllUI();
        }
    },
    //设置结算信息的数据
    setBettingEndInfo: function setBettingEndInfo(recvData) {},

    //每局开局，效验玩家自己的数据是否正常
    checkMyselfInfo: function checkMyselfInfo(info) {
        if (!info || info.length < 1) {
            GG.tipsMgr.showConfirmTip_ONE(G_CHINESE.noMyself, (function () {
                GG.exitGame();
            }).bind(this));
            return false;
        } else return true;
    },

    //=========================================

    //添加长监听
    addLongListen: function addLongListen() {
        //异地登陆
        GG.socketMgr.registerLong(NetType.r_passiveOut, this.net_passiveOut.bind(this));
        //单独的tip
        GG.socketMgr.registerLong(NetType.r_tips, this.net_tipData.bind(this));
        //跑马灯
        GG.socketMgr.registerLong(NetType.r_getAnnouncement, this.net_getAnnouncement.bind(this));
    },
    //tip 单独发送过来的事件
    net_tipData: function net_tipData(recvData) {
        //未登陆，请登陆
        if (recvData.code == G_TYPE.serverCodeType.needLogin) {
            GG.tipsMgr.showConfirmTip_ONE(recvData.tip, (function () {
                GG.loginSocket(function () {
                    GG.exitHome();
                });
            }).bind(this));
        }
    },
    //异地登陆
    net_passiveOut: function net_passiveOut(recvData) {
        GG.socketMgr.setTimeOutLimit(true);
        GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip, (function () {
            GG.exitGame();
        }).bind(this));
    },
    //有公告
    net_getAnnouncement: function net_getAnnouncement(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        //recvData.announcementType
        //recvData.title
        //recvData.content
        GG.tipsMgr.addNotice(recvData.content);
    },

    onDestroy: function onDestroy() {
        GG.socketMgr.cancelAllLongListen();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"BaseUI":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'cd62eHkgCZHfo5RnyU+f4P8', 'BaseUI');
// Script\Views\BaseUI.js

//所有显示的UI的父类

cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        _isRelease: null, //是否在关闭的时候释放
        _uiName: null },

    //ui名字
    // use this for initialization
    onLoad: function onLoad() {},

    showLayer: function showLayer() {
        this.node.active = true;
        this.addTouchLimit();
        if (this._parentComp && this._parentComp.setGrayLayerIsShow) this._parentComp.setGrayLayerIsShow(true);
    },
    hideLayer: function hideLayer() {
        this.cancelTouchLimit();
        if (this._parentComp && this._parentComp.setGrayLayerIsShow) this._parentComp.setGrayLayerIsShow(false);
        if (this._isRelease) this.node.destroy();else this.node.active = false;
    },

    addTouchLimit: function addTouchLimit() {
        var comp = this._getTouchLimitComp();
        if (comp) comp.addTouchLimit();
    },
    cancelTouchLimit: function cancelTouchLimit() {
        var comp = this._getTouchLimitComp();
        if (comp) comp.cancelTouchLimit();
    },
    _getTouchLimitComp: function _getTouchLimitComp() {
        if (!this._touchLimitComp) {
            this._touchLimitComp = this.node.parent.getComponent('Obj_touchLimit');
        }
        return this._touchLimitComp;
    },

    setIsRelease: function setIsRelease(isRelease) {
        this._isRelease = isRelease;
    },
    setUIName: function setUIName(uiName) {
        this._uiName = uiName;
    },
    getUIName: function getUIName() {
        return this._uiName;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Bull100_Manager":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'fb19fRj6b9GDZMv4BilqtWr', 'Bull100_Manager');
// Script\Views\Scene_Bull100\Bull100_Manager.js

//百人大战

var _indexToItemStr = {
    0: 'SPADE_W', //黑桃赢
    1: 'SPADE_L',
    2: 'HEART_W', //方片赢
    3: 'HEART_L',
    4: 'CLUB_W', //梅花赢
    5: 'CLUB_L',
    6: 'DIAMOND_W', //红桃赢
    7: 'DIAMOND_L'
};
var _itemToIndex = {
    'SPADE_W': '0', //黑桃赢
    'SPADE_L': '1',
    'HEART_W': '2', //方片赢
    'HEART_L': '3',
    'CLUB_W': '4', //梅花赢
    'CLUB_L': '5',
    'DIAMOND_W': '6', //红桃赢
    'DIAMOND_L': '7'
};

var initData = {
    myselfData: null, //玩家自己的数据
    othersData: null, //其他玩家的数据
    dealerData: null, //庄家的数据
    homeData: null };

//房间配置的数据
cc.Class({
    'extends': require('BaseManager'),

    properties: {
        _gameState: null, //游戏进程
        _idleTip: null, //提示休息中的tip
        _grabTip: null, //提示投注中的tip
        _isMyDealer: null, //庄家是否是自己

        _enterHomeData: null, //入房信息
        _homeID: null, //房间ID
        _matchID: null, //赛事ID
        _nextStartTime: null, //下局赛事开始时间点
        _curEndGrabTime: null, //当前赛事结束时间点
        _playerID: null, //玩家自己的ID
        _maxBettingTimes: null, //最大投注次数
        _maxBettingGold: null, //最大投注金币
        _chipValueList: null, //筹码选项值
        _homeName: null, //房间名字
        _netEndInfo: null, //结算信息
        _dealerNetType: null, //当前请求的是上庄还是续庄还是请求列表的类型
        _minDealerGold: null, //最小上庄金额
        _isPauseOverMatch: null, //暂停的时候是否进入新的一局
        _isDealerSign: null, //是否是庄家
        _dict_idleBetting: null, //吃瓜群众的投注记录

        comp_topEffect: {
            'default': null,
            type: require('Bull100_topEffect'),
            displayName: '动画容器'
        },
        comp_pokerLayer: {
            'default': null,
            type: require('Bull100_pokerContainer'),
            displayName: '卡牌容器'
        },
        comp_playersLayer: {
            'default': null,
            type: require('Bull100_playerContainer'),
            displayName: '玩家容器'
        },
        comp_tablesLayer: {
            'default': null,
            type: require('Bull100_tableContainer'),
            displayName: '桌子容器'
        },
        comp_goldsLayer: {
            'default': null,
            type: require('Obj_goldsContainer'),
            displayName: '金币容器'
        },
        comp_btnsLayer: {
            'default': null,
            type: require('Bull100_btnContainer'),
            displayName: '底部按钮容器'
        },
        comp_uiLayer: {
            'default': null,
            type: require('Obj_uiContainer'),
            displayName: '窗口界面容器'
        },
        comp_pokerResultLayer: {
            'default': null,
            type: require('Bull100_pokerResultContainer'),
            displayName: '卡牌结果显示容器'
        },
        label_bettingTimes: {
            'default': null,
            type: cc.Label,
            displayName: '投注次数'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        GG.bull100Mgr = this;
        this._super();
        this._isDealerSign = false;

        this._bettingTimesStr = G_CHINESE.limitBetting;
        this._isPauseOverMatch = true;
    },

    //本地测试时候使用的数据
    _initLocalData: function _initLocalData() {
        if (G_Config_common.isLocal) {
            var chip = [100, 500, 1000, 2000, 5000];
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
                        usableBalance: 7568
                    },
                    seatIndex: 1
                }, {
                    player: {
                        coin: 7568,
                        icon: null,
                        nickname: "black",
                        playerId: 5,
                        usableBalance: 7568
                    },
                    seatIndex: 2
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "solider",
                        playerId: 6,
                        usableBalance: 888888
                    },
                    seatIndex: 3
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin",
                        playerId: 7,
                        usableBalance: 888888
                    },
                    seatIndex: 4
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin1",
                        playerId: 8,
                        usableBalance: 888888
                    },
                    seatIndex: 5
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin2",
                        playerId: 9,
                        usableBalance: 888888
                    },
                    seatIndex: 6
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin3",
                        playerId: 9,
                        usableBalance: 888888
                    },
                    seatIndex: 7
                }],
                dealerData: {
                    coin: 297300,
                    icon: null,
                    nickname: "系统大牛",
                    playerId: 7,
                    usableBalance: 74325
                },
                homeData: {
                    roomId: 1,
                    userId: 6,
                    betTimes: 100,
                    matchId: 1,
                    //beginTimeNext : 1487557651083,
                    beginTimeNext: 1487559489225,
                    //settleTime : 1487557651083,
                    settleTime: 1487559474225,
                    betChip: chip,
                    minDealerGold: 100000
                }
            };
            this._initInHomeData(initData);

            //是否开局判定
            var serverTime = 1487559484012;
            if (this._nextStartTime > serverTime && serverTime >= this._curEndGrabTime) {
                //未开局
                var secs = this._getOffSeconds(serverTime, this._nextStartTime);
                this.cmd_wait(secs, this.cmd_startReady.bind(this));
            } else {
                //已经开局
                this._givePokers(true);
            }
        } else {}
    },
    //初始化房间必须要有的数据
    _initInHomeData: function _initInHomeData(initData) {
        //房间配置的数据
        if (initData.homeData) {
            var data = initData.homeData;
            if (!this._homeID) this._homeID = data.roomId;
            this._matchID = data.matchId;
            this._nextStartTime = data.beginTimeNext;
            this._curEndGrabTime = data.settleTime;
            if (data.minDealerGold) this._minDealerGold = data.minDealerGold;
            if (data.betTimes) this._maxBettingTimes = data.betTimes;
            if (data.betChip) {
                this._chipValueList = data.betChip;
                this._maxBettingGold = this._maxBettingTimes * data.betChip[data.betChip.length - 1];
                //设置初始化数据表现
                this.comp_btnsLayer.initChipBtn(data.betChip);
            }
            if (data.userId) {
                GG.getPlayer().setPlayerID(data.userId);
                this._playerID = GG.getPlayer().getPlayerID();
            }
        }
        //玩家自己的数据
        //其他玩家的数据
        //庄家的数据
        this.comp_playersLayer.setStartInfo(initData.othersData, initData.myselfData, initData.dealerData);

        //判定庄家
        if (initData.dealerData) {
            if (initData.dealerData.playerId == GG.getPlayerID()) {
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

    start: function start() {
        this.resetShow();

        if (G_Config_common.isLocal) {
            this._initLocalData();
        } else {
            this.addLongListenOnStart();

            var homeData = GG.getPlayer().getInHomeData();
            if (homeData) {
                if (homeData.net_resetHomeData) {
                    this._dealInHomeNetData(homeData.net_inHomeData);
                    this.updateEnterHomeData(homeData.net_resetHomeData);
                    this.refreshHome();
                } else {
                    this.net_setHomeData(homeData.net_inHomeData);
                }
            }
        }
    },

    testExitBtn: function testExitBtn() {
        //this.comp_uiLayer.showPlayerList();
        //var time1 = new Date(1487835810156);
        //console.log('时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        //var time2 = new Date(1487835795156);
        //console.log('时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
        //this._givePokers(true);
        //GG.tipsMgr.addNotice('好好好好好好好好好好好好好好好好好好好好');
        return true;
        //
        //var time1 = new Date(1487596437912);
        //console.log('时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        //var time2 = new Date(1487596422912);
        //console.log('时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
    },

    //开启休息时间的时候的清理
    _clearByWait: function _clearByWait() {
        this.getMyselfComp().clearBettingGold();
    },

    //游戏流程===============================================

    //执行游戏休息
    cmd_wait: function cmd_wait(retainTime, callFunc) {
        this._gameState = G_TYPE.bull100State.wait;
        if (this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceEnd();
        }
        this.comp_tablesLayer.setTableTouchEnable(false);
        var time1 = new Date(GG.socketMgr.getServerTime());
        // console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(this._nextStartTime);
        // console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
        if (this._idleTip && this._idleTip.getIsShow()) {
            //已经存在该tip
            this._idleTip.forceChangeCountDown(retainTime);
        } else {
            var pos = G_DATA.getBottomTipPos();
            var dataObj = {
                tipIndex: 19,
                isCountDown: true,
                showPos: cc.p(pos.x, pos.y * 1.3)
            };
            //callBack : this.on_beforeStart.bind(this)
            dataObj.retainTime = retainTime;
            if (callFunc) dataObj.callBack = callFunc;
            this._idleTip = GG.tipsMgr.showOnlyOne(dataObj);
            //this._idleTip.setIsShow(true);
        }

        this._clearByWait();
    },

    //投注前的动画
    cmd_startReady: function cmd_startReady() {
        this._gameState = G_TYPE.bull100State.startReady;
        if (this._idleTip && this._idleTip.getIsShow()) {
            this._idleTip.forceEnd();
        }
        //庄家庄字特效显示
        this.comp_playersLayer.getDealerWordEffectComp().playDealerWordAni();

        this.comp_topEffect.playStartGameAni((function () {
            //this.comp_topEffect.playStartBettingAni(function () {
            //
            //}.bind(this));
            var secs;
            if (G_Config_common.isLocal) {
                secs = 10;
            } else {
                secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            }
            //if(this.getMyselfComp().getIsDealer()) this.cmd_startBetting(secs);
            //else{
            //    if(!G_Config_common.isLocal) secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            //    this.cmd_startBetting(secs);
            //}
            this.cmd_startBetting(secs);
        }).bind(this));

        if (this.getMyselfComp().getIsDealer()) {
            //如果是庄家开始动画播放完就进入等待玩家投注倒计时
            this._givePokers(true);
        } else {
            this._givePokers(false);
        }
    },
    _givePokers: function _givePokers(isOverAni) {
        //发牌
        var dealerPokerPos = this.comp_playersLayer.getDealerPokerPos();
        var pokerPosList = this.comp_tablesLayer.getTablePokerPos();
        pokerPosList.push(dealerPokerPos);
        this.comp_pokerLayer.giveCoverPokers(pokerPosList, (function () {
            //发牌结束
        }).bind(this), isOverAni);
    },
    //开始投注
    cmd_startBetting: function cmd_startBetting(retainTime) {
        var isDealer = this.getMyselfComp().getIsDealer();
        if (!isDealer) {
            this.comp_tablesLayer.setTableTouchEnable(true);
            this.refreshBtnChoose();
        }
        var secs = 0;
        var pos = G_DATA.getBottomTipPos();
        var dataObj = {
            tipIndex: 18,
            showPos: cc.p(pos.x, pos.y * 1.3),
            isCountDown: true,
            callBack: this.cmd_bettingEnd.bind(this)
        };
        dataObj.retainTime = retainTime;
        //是庄家倒计时显示内容替换
        if (G_Config_common.isLocal) dataObj.retainTime = 10;
        if (isDealer) dataObj.showStr = G_CHINESE.isDealerTip;
        this._grabTip = GG.tipsMgr.showOnlyOne(dataObj);
    },

    //投注结束
    cmd_bettingEnd: function cmd_bettingEnd(target) {
        this.comp_tablesLayer.setTableTouchEnable(false);
        this.comp_btnsLayer.setBtnGroupEnable(false);
        if (this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceEnd();
        }

        //显示休息中
        var secs;
        if (G_Config_common.isLocal) {
            this._winDict = { 0: 1, 2: 1, 5: 1, 7: 1 };
            var dataList = [];
            for (var i = 0; i < 5; i++) {
                var pokerInfo = [];
                for (var j = 0; j < 5; j++) {
                    pokerInfo.push({
                        pokerIndex: 13,
                        isOpen: false
                    });
                }
                dataList.push(pokerInfo);
            }
            var pokerValueList = [10, 2, 3, 4, 5];

            if (!this._netEndInfo) this._netEndInfo = {};
            this._netEndInfo.pokersInfoList = dataList;
            this._netEndInfo.pokersResultList = pokerValueList;

            this._netEndInfo.myselfInfo = {
                changeGold: 44444 * (G_TOOL.getRandomBool() ? 1 : -1),
                leaveGold: 999666
            };

            this._netEndInfo.dealerInfo = {
                leaveGold: 8888888
            };

            this._netEndInfo.othersInfo = {
                2: {
                    changeGold: 11111 * (G_TOOL.getRandomBool() ? 1 : -1),
                    leaveGold: 33333,
                    name: '黑人1'
                },
                3: {
                    changeGold: 44444 * (G_TOOL.getRandomBool() ? 1 : -1),
                    leaveGold: 999666,
                    name: '黑人2'
                }
            };

            secs = 10;
            this.cmd_wait(secs);
        }

        if (this._winDict) {
            //确认接收到了结算信息，才能继续
            this.comp_topEffect.playBettingEnd((function () {
                this._showWhenGotEndInfo();
            }).bind(this));
        }
    },
    //显示结算信息
    _showWhenGotEndInfo: function _showWhenGotEndInfo() {
        if (!this._netEndInfo) {
            this._resetGameWhenEnd();
            return;
        }
        //设置卡牌信息
        this.comp_pokerLayer.setPokersData(this._netEndInfo.pokersInfoList, this._netEndInfo.pokersResultList);
        //开牌
        //if(!this._winDict) this._winDict = {0:1,2:1,5:1,7:1};
        this.comp_pokerLayer.openPokers(this._winDict);
        var act1 = cc.delayTime(3);
        this.node.runAction(cc.sequence(act1, cc.callFunc(function () {
            //设置玩家信息,头像上的金币增减效果
            if (this._netEndInfo.dealerInfo) this.comp_playersLayer.getDealerComp().setGoldValue(this._netEndInfo.dealerInfo.leaveGold);
            if (this._netEndInfo.myselfInfo) {
                var myselfComp = this.getMyselfComp();
                myselfComp.setGoldValue(this._netEndInfo.myselfInfo.leaveGold);
                myselfComp.showGoldChange(this._netEndInfo.myselfInfo.changeGold);
            }
            if (this._netEndInfo.othersInfo) {
                var playerInfo, playerComp;
                for (var seatIndex in this._netEndInfo.othersInfo) {
                    playerInfo = this._netEndInfo.othersInfo[seatIndex];
                    if (playerInfo) {
                        playerComp = this.comp_playersLayer.getPlayerComp(seatIndex);
                        if (playerComp) {
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
    _openPokerEnd: function _openPokerEnd(target) {
        //this._netEndInfo = null;
        //移动金币
        this.comp_playersLayer.flyWinGold(this._winDict);
        //显示结果面板
        var act = cc.delayTime(2);
        this.node.runAction(cc.sequence(act, cc.callFunc(function () {
            var data = {};
            if (this._netEndInfo) {
                if (this._netEndInfo.myselfInfo) data.myChangeGold = this._netEndInfo.myselfInfo.changeGold;
                if (this._netEndInfo.othersInfo) data.othersInfo = this._netEndInfo.othersInfo;
                this.comp_topEffect.showResultEffect(data, this._resetGameWhenEnd.bind(this));
            } else this._resetGameWhenEnd();
        }, this)));
    },
    //开牌显示完成后，进行新的倒计时
    _resetGameWhenEnd: function _resetGameWhenEnd() {
        this.changeIdleTip();
        if (G_Config_common.isLocal) {
            this.resetShow();
            this._initLocalData();
        }
    },

    //=======================================================

    //清理界面中的所有显示效果
    resetShow: function resetShow() {
        this.node.stopAllActions();
        //管理器中的存储数据
        this._isMyDealer = false;
        this._winDict = null;
        this._netEndInfo = null;
        //玩家
        //this.comp_playersLayer.clearAll();
        //卡牌
        this.comp_pokerLayer.clearAll();
        //如果已经结束一局，则清理以下数据
        if (this._isPauseOverMatch) {
            //金币
            this.comp_goldsLayer.clearAll();
            //桌子
            this.comp_tablesLayer.initAllTables();
            //清理吃瓜群众的投注记录
            this._dict_idleBetting = null;
        } else this._isPauseOverMatch = true;
        //按钮
        this.comp_btnsLayer.setStartInfo();
        //动画特效
        this.comp_topEffect.clearAll();
        //tip提示
        //GG.tipsMgr.forceStopAllTips();
        //显示底部的按钮和头像
        // this.setBottomIsShow(true);
        //隐藏上庄列表
        var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
        if (comp) comp.hideLayer();
        //清理卡牌结果显示
        this.comp_pokerResultLayer.clearAll();
    },
    //根据用户操作投放金币
    //flyGold : function (goldList) {
    //    this.comp_goldsLayer.flyGolds(goldList);
    //},
    //玩家点击了筹码选择
    chooseChip: function chooseChip(choose) {
        this.getMyselfComp().chooseChip(choose);
    },
    //玩家点击了某个桌子
    touchTable: function touchTable(tableIndex, posList) {
        if (this.getMyselfComp().getIsDealer()) {
            //是庄家
            return;
        }

        var dataObj = G_OBJ.data_bullBetting();
        dataObj.tableIndex = tableIndex;
        dataObj.posList = posList;
        dataObj.isMine = true;

        //记录玩家事件
        this.getMyselfComp().touchTable(dataObj, this._touchTableSuccess.bind(this));
    },
    //投注成功后的表现
    _touchTableSuccess: function _touchTableSuccess(bettingData) {
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
    goldRecover: function goldRecover(recoverDict, targetPos) {
        var goldNum;
        for (var tableIndex in recoverDict) {
            goldNum = recoverDict[tableIndex];

            var flyDataList = G_OBJ.data_flyGold_tableToPlayer();
            flyDataList.tableIndex = tableIndex;
            flyDataList.goldNum = goldNum;
            flyDataList.targetPos = targetPos;
            this.comp_goldsLayer.tableToPlayer(flyDataList);
        }
    },
    //刷新按钮的筹码显示结果
    refreshBtnChoose: function refreshBtnChoose() {
        this.comp_btnsLayer.setBtnGroupEnable(true);
        this.comp_btnsLayer.changeBet(); //下注开始之后设置可押注的按钮
        var choose = this.getMyselfComp().getChooseChip();
        this.comp_btnsLayer.chooseMoney(choose);
    },
    //显示某张桌子的胜利, 在卡牌容器中，打开所有卡牌后调用
    showTableWin: function showTableWin(tableIndex, multiple) {
        this.comp_tablesLayer.showTableWin(tableIndex, multiple);
    },

    //=============================================

    //显示上庄列表窗口
    showUI_dealerList: function showUI_dealerList(dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.dealerList, function (layerComp) {
            layerComp.showLayer();
            layerComp.setData(dataObj);
        });
    },
    //显示玩家列表窗口
    showUI_playerList: function showUI_playerList(dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.playerList, function (layerComp) {
            layerComp.showLayer();
            layerComp.setData(dataObj);
        });
    },
    //显示走势图列表窗口
    showUI_trendList: function showUI_trendList(dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.bull100Trend, function (layerComp) {
            layerComp.showLayer();
            layerComp.setData(dataObj);
        });
    },
    //显示规则窗口
    showUI_rules: function showUI_rules(dataObj) {
        var layerComp = this.comp_uiLayer.addUI(G_RES_URL.uiName.rulesInHome, function (layerComp) {
            layerComp.showLayer();
            layerComp.setData(dataObj);
        });
    },

    //============================================= 暂停后服务器数据处理

    //重新修改投注期间倒计时数值
    changeGrabTip: function changeGrabTip(num) {
        var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
        if (this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceChangeCountDown(secs);
        } else {
            //投注动画未执行完成
            this.comp_topEffect.clearAll();

            if (this.comp_pokerLayer.getIsGivePokerEnd()) {
                //已经发完牌
            } else {
                    //未发牌
                    this._givePokers(true);
                }
            this.cmd_startBetting(secs);
        }
    },
    //重新修改休息期间倒计时数值
    changeIdleTip: function changeIdleTip(num) {
        if (this._idleTip && this._idleTip.getIsShow()) {
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime);
            if (num) secs = num;
            this._idleTip.forceChangeCountDown(secs);
        }
    },
    //重新设置投注信息
    resetTableGold: function resetTableGold(goldList) {
        if (!goldList || goldList.length < 1) return;

        var netBeanData;
        for (var i = 0; i < goldList.length; i++) {
            netBeanData = goldList[i];
            var areaValue = parseInt(netBeanData.item.split('_')[0]);
            if (netBeanData.seatIndex === this.getMyselfComp().getSeatIndex()) continue;
            var dataObj = {};
            var playerComp = this.comp_playersContainer.getCompByIndex(netBeanData.seatIndex);

            dataObj.goldValue = netBeanData.gold;
            dataObj = playerComp.touchGrabArea_other(dataObj);
            dataObj = this.comp_tablesContainer.touchOneTable(areaValue, dataObj);
            if (dataObj.callFunc) dataObj.callFunc();
        }
    },

    //========================================  庄家的相关操作

    //请求上庄列表
    send_getDealerList: function send_getDealerList() {
        if (G_Config_common.isLocal) {
            var dealerInfo = {
                playerId: 9, //玩家id
                icon: '', //玩家头像
                nickname: '玩家1', //玩家昵称
                coin: 210000, //玩家余额
                maxDealerCoin: 210000, // 最大上庄金额
                usableBalance: 200000 };
            //可用金额
            var playerList = [{
                playerId: 1,
                headImg: '',
                nickname: '玩家3',
                coin: 99999,
                usableBalance: 11111
            }, {
                playerId: 2,
                headImg: '',
                nickname: '玩家2',
                coin: 99999,
                usableBalance: 11111
            }];
            var dataObj = {
                //myGold : GG.bull100Mgr.getMyselfComp().getGoldValue(),
                myGold: dealerInfo.usableBalance,
                dealerInfo: dealerInfo,
                rankList: playerList
            };
            this.showUI_dealerList(dataObj);
        } else {
            var netData = {};
            this.setDealerNetType(G_TYPE.dealerNetType.getList);
            GG.socketMgr.SendMsg(NetType.s_bull100_getDealerList, netData);
        }
    },
    //请求上庄
    send_upDealer: function send_upDealer(sendGold) {
        if (G_Config_common.isLocal) {
            var dealerInfo = {
                playerId: 6, //玩家id
                icon: '', //玩家头像
                nickname: '玩家1', //玩家昵称
                coin: 110000, //玩家余额
                maxDealerCoin: 110000, // 最大上庄金额
                usableBalance: 100000 };
            //可用金额
            var playerList = [{
                playerId: 1,
                headImg: '',
                nickname: '玩家3',
                coin: 99999,
                usableBalance: 11111
            }, {
                playerId: 2,
                headImg: '',
                nickname: '玩家2',
                coin: 99999,
                usableBalance: 11111
            }, {
                playerId: 3,
                headImg: '',
                nickname: '玩家5',
                coin: 9909,
                usableBalance: 1101
            }];
            var dataObj = {
                //myGold : GG.bull100Mgr.getMyselfComp().getGoldValue(),
                myGold: dealerInfo.usableBalance,
                dealerInfo: dealerInfo,
                rankList: playerList,
                tipStr: '上庄成功！！！'
            };
            var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
            if (comp) comp.upDealerSuccess(dataObj);
        } else {
            var netData = {
                coin: sendGold
            };
            this.setDealerNetType(G_TYPE.dealerNetType.upDealer);
            GG.socketMgr.SendMsg(NetType.s_bull100_dealerUp, netData);
        }
    },
    //请求下庄
    send_downDealer: function send_downDealer() {
        var isDealer = this.getMyselfComp().getIsDealer();
        if (isDealer) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.giveUpDealer2, this._commitDownDealer.bind(this));
            var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
            if (comp) comp.hideLayer();
        } else {
            this._commitDownDealer();
        }
    },
    //确认下庄
    _commitDownDealer: function _commitDownDealer() {
        var netData = {};
        GG.socketMgr.SendMsg(NetType.s_bull100_dealerDown, netData);
    },
    //请求续庄  cancelCallFunc: 取消续庄的时候调用
    send_continueDealer: function send_continueDealer(sendGold, cancelCallFunc) {
        GG.tipsMgr.showConfirmTip_TWO(G_TOOL.formatStr(G_CHINESE.continueDealer, sendGold), (function () {
            this.commitContinueDealer(sendGold);
        }).bind(this), cancelCallFunc);
    },
    //主动确认续庄家
    commitContinueDealer: function commitContinueDealer(sendGold) {
        var netData = {
            coin: sendGold
        };
        GG.bull100Mgr.setDealerNetType(G_TYPE.dealerNetType.continueDealer);
        GG.socketMgr.SendMsg(NetType.s_bull100_dealerContinue, netData);
    },

    //==========================================

    //上庄后隐藏底部
    setBottomIsShow: function setBottomIsShow(isShow) {
        this.comp_btnsLayer.setBottomIsShow(isShow);
        //玩家庄字特效显示
        this.comp_playersLayer.getMyselfEffectComp().playDealerWordAni();
        // this.comp_playersLayer.setMyselfIsShow(isShow);
    },
    //设置当前点击请求的庄家相关的请求类型
    setDealerNetType: function setDealerNetType(type) {
        this._dealerNetType = type;
    },
    //设置投注次数的显示
    setBettingTimes: function setBettingTimes(times) {
        this.label_bettingTimes.string = G_TOOL.formatStr(this._bettingTimesStr, times);
    },

    //抖动游戏
    shakeGame: function shakeGame() {
        var off = 2;
        var off2 = off * 2;
        var time = 0.07;
        var act1 = cc.moveBy(time, -off2, 0);
        var act2 = cc.moveBy(time, off2 * 2, 0);
        var act3 = cc.moveBy(time, -off2, 0);
        var act4 = cc.moveBy(time, off2, 0);
        var act5 = cc.moveBy(time, -off, 0);
        this.node.runAction(cc.sequence(act1, act2, act3, act4, act5));
    },

    //通过特效层显示卡牌的结果
    showPokerResult: function showPokerResult(pos, pokerValue) {
        this.comp_pokerResultLayer.showPokerResult(pos, pokerValue);
    },

    //获取玩家操作时的位置
    getMyselfPos: function getMyselfPos() {
        return this.comp_playersLayer.getMyselfPos();
    },
    //获取玩家对象
    getMyselfComp: function getMyselfComp() {
        return this.comp_playersLayer.getMyselfComp();
    },
    //获取在座位上的玩家
    getPlayerCompInSite: function getPlayerCompInSite(seatIndex) {
        return this.comp_playersLayer.getPlayerComp(seatIndex);
    },
    //获取玩家当前投注需要飞行的金币数量
    getMyselfFlyGoldNum: function getMyselfFlyGoldNum() {
        return this.getMyselfComp().getFlyGoldNum();
    },
    //获取玩家当前投注需要增加的金币数量
    getMyselfBetGoldValue: function getMyselfBetGoldValue() {
        return this.getMyselfComp().getChooseGoldValue();
    },
    //获取金币的宽高
    getGoldImgSize: function getGoldImgSize() {
        return this.comp_goldsLayer.getGoldSize();
    },
    //获取最大的投注金额
    getMaxBettingGold: function getMaxBettingGold() {
        return this._maxBettingGold;
    },
    //获取最大的投注次数
    getMaxBettingTimes: function getMaxBettingTimes() {
        return this._maxBettingTimes;
    },
    getMatchID: function getMatchID() {
        return this._matchID;
    },
    getBtnsComp: function getBtnsComp() {
        return this.comp_btnsLayer;
    },
    //当知道桌子的索引的时候，可以获取取大的格子的索引
    getBigTableIndex: function getBigTableIndex(index) {
        return index * 2;
    },
    //当知道桌子的索引的时候，可以获取取小的格子的索引
    getMinTableIndex: function getMinTableIndex(index) {
        return index * 2 + 1;
    },
    //当知道桌子的大小索引的时候，桌子容器中的桌子索引
    getTableContainerIndex: function getTableContainerIndex(index) {
        var containerIndex;
        if (index % 2 == 0) {
            //偶数
            containerIndex = index / 2;
        } else {
            containerIndex = (index - 1) / 2;
        }
        return containerIndex;
    },
    //获取桌子索引对应的投注项表示
    getItemByTableIndex: function getItemByTableIndex(tableIndex) {
        return _indexToItemStr[tableIndex];
    },
    //获取投注项表示对应的桌子索引
    getTableIndexByItem: function getTableIndexByItem(itemStr) {
        return _itemToIndex[itemStr];
    },
    //获取筹码选项值
    getChipValueList: function getChipValueList() {
        return this._chipValueList;
    },
    //获取进房信息
    getEnterHomeData: function getEnterHomeData() {
        return this._enterHomeData;
    },
    //获取当前比赛是否已经开局
    getIsBetStart: function getIsBetStart(_nextStartTime, _curEndGrabTime) {
        if (!_nextStartTime) _nextStartTime = this._nextStartTime;
        if (!_curEndGrabTime) _curEndGrabTime = this._curEndGrabTime;
        var serverTime = GG.socketMgr.getServerTime();
        if (_nextStartTime > serverTime && serverTime >= _curEndGrabTime) {
            //未开局
            return false;
        }
        return true;
    },
    //获取房间名字
    getHomeName: function getHomeName() {
        return this._homeName;
    },
    //获取房间ID
    getHomeID: function getHomeID() {
        return this._homeID;
    },
    //是否已经投注
    getIsBetting: function getIsBetting() {
        return this.getMyselfComp().getIsBetting();
    },
    //获取最小上庄金额
    getMinDealerGold: function getMinDealerGold() {
        return this._minDealerGold;
    },
    //本地是否为庄家状态
    getDealerState: function getDealerState() {
        return this._isDealerSign;
    },
    //获取桌子容器
    getTableContainer: function getTableContainer() {
        return this.comp_tablesLayer;
    },
    //获取金币容器
    getGoldContainer: function getGoldContainer() {
        return this.comp_goldsLayer;
    },
    //获取吃瓜群众的投注位置
    getIdleBettingPos: function getIdleBettingPos() {
        return this.comp_btnsLayer.getIdleBettingPos();
    },
    //获取吃瓜群众的投注记录
    getIdleBetting: function getIdleBetting() {
        return this._dict_idleBetting;
    },
    //第一次进入游戏是否可押注
    getFirstBetting: function getIsWait() {
        return this._startGame;
    },

    //当有吃瓜群众投注的时候记录信息
    _addIdleBetting: function _addIdleBetting(tableIndex, goldImgNum) {
        if (!this._dict_idleBetting) this._dict_idleBetting = {};
        if (!this._dict_idleBetting[tableIndex]) this._dict_idleBetting[tableIndex] = goldImgNum;else this._dict_idleBetting[tableIndex] += goldImgNum;
    },
    //===============================================

    addLongListenOnStart: function addLongListenOnStart() {
        GG.socketMgr.registerLong(NetType.r_bull100_reStartGame, this.net_resetGameData.bind(this));
        GG.socketMgr.registerLong(NetType.r_grabEnd, this.net_bettingEnd.bind(this));
        GG.socketMgr.registerLong(NetType.r_otherGrab, this.net_otherGrab.bind(this));
        GG.socketMgr.registerLong(NetType.r_mineHaveGrab, this.net_mineHasGrab.bind(this));
        GG.socketMgr.registerLong(NetType.r_oneInHome, this.net_someOneInHome.bind(this));

        GG.socketMgr.registerLong(NetType.r_grab_exitReturn, this.net_exitHome.bind(this));
        GG.socketMgr.registerLong(NetType.r_idleTimeOut, this.net_idleTimeOut.bind(this));
        GG.socketMgr.registerLong(NetType.r_bull100_dealerDownWarning, this.net_dealerContinueWarning.bind(this));
        GG.socketMgr.registerLong(NetType.r_bull100_dealerListReturn, this.net_returnDealerList.bind(this));
        GG.socketMgr.registerLong(NetType.r_bull100_downDealerReturn, this.net_returnDownDealer.bind(this));
    },

    //百人——进入房间接收到的信息
    net_setHomeData: function net_setHomeData(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            return;
        }
        this._dealInHomeNetData(recvData);
        //是否开局判定
        var serverTime = GG.socketMgr.getServerTime();
        if (serverTime < this._curEndGrabTime) {
            //投注时间
            if (this._idleTip && this._idleTip.getIsShow()) {
                //已经存在该tip
                this._idleTip.forceEnd();
            }
            this.resetShow();
            this._startGame = true;
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            this.cmd_startBetting(secs);
            this._givePokers(true);
        } else {
            //未开局
            this._isPauseOverMatch = true;
            this.resetShow();
            this._startGame = false;
            var secs = this._getOffSeconds(serverTime, this._nextStartTime);
            if (secs <= 0) {
                //卡时间点了,直接开始投注
                secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
                this.cmd_startBetting(secs);
                this._givePokers(true);
            } else this.cmd_wait(secs);
        }
    },

    //处理进房信息
    _dealInHomeNetData: function _dealInHomeNetData(recvData) {
        this._enterHomeData = recvData;
        var match = recvData.match;

        var time1 = new Date(GG.socketMgr.getServerTime());
        // console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(match.beginTimeNext);
        // console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())

        var id = GG.getPlayerID();
        var initData = {
            myselfData: {
                chooseDict: recvData.betChip,
                roomName: recvData.roomName
            },
            othersData: recvData.seats,
            dealerData: match.dealer,
            homeData: {
                roomId: recvData.roomId,
                userId: id,
                betTimes: recvData.betTimes,
                matchId: match.matchId,
                beginTimeNext: match.beginTimeNext,
                settleTime: match.settleTime,
                betChip: recvData.betChip,
                minDealerGold: recvData.minDealerCoin
            }
        };
        this._initInHomeData(initData);
    },

    //百人——重新设置开局数据
    net_resetGameData: function net_resetGameData(recvData) {
        GG.tipsMgr.clearConfirm(); //清理被动续庄

        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        //局信息
        this.updateEnterHomeData(recvData);
        var match = recvData.match;
        var initData = {
            myselfData: {
                chooseDict: null,
                roomName: null
            },
            othersData: recvData.seats,
            dealerData: match.dealer,
            homeData: {
                roomId: recvData.roomId,
                userId: null,
                betTimes: null,
                matchId: match.matchId,
                beginTimeNext: match.beginTimeNext,
                settleTime: match.settleTime,
                betChip: null
            }
        };
        this._initInHomeData(initData);
        this.resetShow();
        this.cmd_startReady();
    },

    //百人——结束投注，进入结算
    net_bettingEnd: function net_bettingEnd(recvData) {
        var isRight = this.setBettingEndInfo(recvData);
        //结束投注，进入结算
        if (isRight) {
            this.cmd_bettingEnd();
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime);
            this.cmd_wait(secs);
        }
    },

    //百人——设置结算信息的数据
    setBettingEndInfo: function setBettingEndInfo(recvData) {
        var tip = recvData.tip;
        switch (tip.code) {
            case G_TYPE.serverCodeType.success:
                break;
            case G_TYPE.serverCodeType.noBetting:
                break;
            default:
                return false;
        }
        this._super(recvData);

        this._winDict = {};
        var pokerIndex,
            dataList = [];
        var pokerData, dealerOdds;
        var pokerValueList = [];
        for (var i = 0; i < recvData.pokers.length; i++) {
            pokerData = recvData.pokers[i];
            var pokerList = pokerData['code'].split(","); //第一个是庄家
            var pokerInfo = [];
            for (var j = 0; j < pokerList.length; j++) {
                pokerIndex = parseInt(pokerList[j]);
                if (!isNaN(pokerIndex)) {
                    pokerInfo.push({
                        pokerIndex: pokerIndex,
                        isOpen: false
                    });
                }
            }
            dataList.push(pokerInfo);
            pokerValueList.push(pokerData.cardsPoint);
            if (i > 0) {
                //去除庄家结果
                if (pokerData.result == 'WIN') {
                    this._winDict[this.getBigTableIndex(i - 1)] = Math.max(pokerData.odds, dealerOdds);
                } else if (pokerData.result == 'LOSE') {
                    this._winDict[this.getMinTableIndex(i - 1)] = Math.max(pokerData.odds, dealerOdds);
                }
            } else dealerOdds = pokerData.odds;
        }
        //this.comp_pokerLayer.setPokersData(dataList, pokerValueList);

        if (!this._netEndInfo) this._netEndInfo = {};
        this._netEndInfo.pokersInfoList = dataList;
        this._netEndInfo.pokersResultList = pokerValueList;

        //结算的时候更新座位上的其他玩家信息
        var seats = recvData.players;
        if (seats) {
            this._netEndInfo.othersInfo = {};
            for (var i = 0; i < seats.length; i++) {
                var player = seats[i];
                switch (player.seatIndex) {
                    case G_DATA.getDealerSeatIndex():
                        //庄家剩余金币
                        //this.comp_playersLayer.getDealerComp().setGoldValue(player.dealerBalance);
                        this._netEndInfo.dealerInfo = {
                            changeGold: player.coin,
                            leaveGold: player.balance
                        };
                        break;
                    case G_DATA.getIdlePlayersSeatIndex():
                        break;
                    default:
                        this._netEndInfo.othersInfo[player.seatIndex] = {
                            changeGold: player.coin,
                            leaveGold: player.balance,
                            name: player.nickname,
                            icon: player.icon
                        };
                        break;
                }
            }
        }
        return true;
    },

    //百人——结束投注，金钱的变化数据(当有人投注时候，才会进入这个接口)
    net_mineHasGrab: function net_mineHasGrab(recvData) {

        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        var myInfo = recvData.player;
        if (!this._netEndInfo) this._netEndInfo = {};
        this._netEndInfo.myselfInfo = {
            changeGold: myInfo.coin,
            leaveGold: myInfo.balance
        };
    },

    //百人——当有某个玩家投注,包括自己
    net_otherGrab: function net_otherGrab(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        var others = recvData.bets,
            netBeanData;
        for (var i = 0; i < others.length; i++) {
            netBeanData = others[i];
            var tableIndex = this.getTableIndexByItem(netBeanData.item);
            var seatIndex = netBeanData.seatIndex;
            var goldImgNum = G_Config_grab.num_otherGrabGoldNum;
            //整理投注信息
            var dataObj = G_OBJ.data_bullBetting();
            dataObj.tableIndex = tableIndex;
            dataObj.posList = this.comp_tablesLayer.getGoldPosList(tableIndex, goldImgNum);
            dataObj.goldValue = netBeanData.gold;
            dataObj.isMine = false;
            //根据座位判定，投注玩家身份
            switch (seatIndex) {
                case this.getMyselfComp().getSeatIndex():
                    //在座位上的自己的投注
                    break;
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
                    break;
                case G_DATA.getDealerSeatIndex():
                    //庄家
                    break;
                default:
                    //正常座位上玩家
                    var playerComp = this.getPlayerCompInSite(seatIndex);
                    if (playerComp) playerComp.touchTable(dataObj, this._touchTableSuccess.bind(this));
                    break;
            }
        }
    },

    //百人——有人入座
    net_someOneInHome: function net_someOneInHome(recvData) {

        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        var player,
            playerList = recvData.seats,
            myPlayerID = GG.getPlayerID();
        for (var i = 0; i < playerList.length; i++) {
            player = playerList[i];
            if (!player || player.player.playerId == myPlayerID) continue;
            this.comp_playersLayer.onePlayerInsert(player.seatIndex, player.player);
        }
    },

    //百人——续庄警告.在庄时候，如果金额不足，会有警告;
    net_dealerContinueWarning: function net_dealerContinueWarning(recvData) {
        // console.log('上庄成功，续庄警告,金额不足=====')
        var tip = recvData.tip;
        // console.log('tip.tip==='+tip.tip);
        switch (tip.code) {
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
                GG.tipsMgr.showConfirmTip_ONE(tip.tip);
                break;
            case G_TYPE.serverCodeType.downDealer4:
                //您剩余金币不足,已下庄!被动下庄   系统提示  按钮显示置灰
                GG.tipsMgr.showConfirmTip_ONE(tip.tip);
                if (this.getMyselfComp().getIsDealer()) {}
                break;
            case G_TYPE.serverCodeType.warnDealer1:
                //友情提示: 您的上庄资金不足80%，请及时续庄！显示 4 秒
                GG.tipsMgr.showSystem(G_CHINESE.downDealerWarning, null, 4);
                break;
            case G_TYPE.serverCodeType.continueFail2:
                //庄家被动续庄
                this.send_continueDealer(recvData.coin, (function () {
                    //放弃被动续庄
                    this.setBottomIsShow(true);
                }).bind(this));
                break;
            case G_TYPE.serverCodeType.downDealer3:
                //庄家被动下庄
                GG.tipsMgr.showConfirmTip_ONE(tip.tip);
                return;
            default:
                GG.tipsMgr.showConfirmTip_ONE(tip.code + tip.tip);
                return;
        }
    },

    //百人——获取上庄列表的返回(上庄，续庄和请求上庄列表都是返回这个接口)
    net_returnDealerList: function net_returnDealerList(recvData) {
        var tip = recvData.tip;
        //if(tip.code != G_TYPE.serverCodeType.success) {
        //    console.log(tip.tip);
        //    this._dealerNetType = null;
        //    return;
        //}

        switch (tip.code) {
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
                GG.tipsMgr.showSystem(tip.tip, null, 2); //600014提示
                return;
            case G_TYPE.serverCodeType.downDealer5:
                //下庄之后又续庄
                GG.tipsMgr.showConfirmTip_ONE(tip.tip); //600019提示
                return;
            default:
                GG.tipsMgr.showConfirmTip_ONE(tip.code + tip.tip);
                return;
        }

        var dealerInfo = recvData.deskDealer;
        var playerList = recvData.players;
        var dataObj = {
            myGold: recvData.dealer.maxDealerCoin,
            dealerInfo: dealerInfo,
            rankList: playerList,
            tipStr: ''
        };

        //设置当前玩家的可用金币
        this.getMyselfComp().setGoldValue(recvData.dealer.maxDealerCoin);
        //庄家金币更新
        if (this.getMyselfComp().getIsDealer()) {
            this.comp_playersLayer.getDealerComp().setGoldValue(recvData.deskDealer.usableBalance);
        }

        switch (this._dealerNetType) {
            case G_TYPE.dealerNetType.upDealer:
                //上庄
                dataObj.tipStr = G_CHINESE.dealerSuccess;
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
                if (comp) comp.upDealerSuccess(dataObj);
                //this.setBottomIsShow(false);
                break;
            case G_TYPE.dealerNetType.continueDealer:
                //续庄
                dataObj.tipStr = G_CHINESE.continueDealerSuccess;
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
                if (comp) comp.upDealerSuccess(dataObj);
                break;
            case G_TYPE.dealerNetType.getList:
                //获取上庄列表
                this.showUI_dealerList(dataObj);
                break;
            default:
                break;
        }
        this._dealerNetType = null;
    },

    //百人——下庄请求的回调
    net_returnDownDealer: function net_returnDownDealer(recvData) {
        var tip = recvData.tip;
        // console.log(tip.tip);
        switch (tip.code) {
            case G_TYPE.serverCodeType.success:
                break;
            case G_TYPE.serverCodeType.downDealer1:
                //本身在庄的情况下下庄
                this.setBottomIsShow(true);
                var tipObj = {
                    tipIndex: 19,
                    showStr: G_CHINESE.downSuccess,
                    isCountDown: false,
                    showPos: G_DATA.getCenterTipPos()
                };
                //callBack : this.on_beforeStart.bind(this)
                // GG.tipsMgr.showTxtTip(tipObj);
                //您已经成功下庄 显示 2 秒
                GG.tipsMgr.showSystem(G_CHINESE.downSuccess, null, 2);
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
                if (comp) comp.setBtnShow(false);
                //下庄清除庄字特效
                this.comp_playersLayer.getMyselfEffectComp().clearDealerWordAni();
                //更新抢庄按钮是否可用
                this.comp_playersLayer.setUpDealerBtnEnable(false);
                break;
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
                dataObj.tipStr = G_CHINESE.giveUpDealer1;
                //更新上庄列表数据
                var comp = this.comp_uiLayer.getUIComp(G_RES_URL.uiName.dealerList);
                if (comp) comp.downDealerSuccess(dataObj);
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
                    tipIndex: 19,
                    showStr: G_CHINESE.downSuccess,
                    isCountDown: false,
                    showPos: G_DATA.getCenterTipPos()
                };
                //callBack : this.on_beforeStart.bind(this)
                // GG.tipsMgr.showTxtTip(tipObj);
                //您已经成功下庄
                GG.tipsMgr.showSystem(G_CHINESE.downSuccess, null, 2);
                break;
            default:
                break;
        }
        this._isDealerSign = false;
    },

    //百人——退出房间的请求
    net_exitHome: function net_exitHome(recvData) {

        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }

        GG.exitHome();
    },

    //百人——游戏中长时间没有做操作
    net_idleTimeOut: function net_idleTimeOut(recvData) {

        GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip, (function () {
            GG.exitHome();
        }).bind(this));
    },

    _getOffSeconds: function _getOffSeconds(startTime, endTime) {
        return GG.socketMgr.getOffSeconds(startTime, endTime);
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    onDestroy: function onDestroy() {
        this._super();
        GG.bull100Mgr = null;
    }
});

cc._RFpop();
},{"BaseManager":"BaseManager","Bull100_btnContainer":"Bull100_btnContainer","Bull100_playerContainer":"Bull100_playerContainer","Bull100_pokerContainer":"Bull100_pokerContainer","Bull100_pokerResultContainer":"Bull100_pokerResultContainer","Bull100_tableContainer":"Bull100_tableContainer","Bull100_topEffect":"Bull100_topEffect","Obj_goldsContainer":"Obj_goldsContainer","Obj_uiContainer":"Obj_uiContainer"}],"Bull100_btnContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8ebeakRcqlHerVhexIQiAHf', 'Bull100_btnContainer');
// Script\Views\Scene_Bull100\Bull100_btnContainer.js

//百人大战按钮管理类

cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        _lastBtn: null,

        node_btnGroup: cc.Node,
        // node_effectOnButton : cc.Node,
        node_effectUnderButton: cc.Node,
        node_exitHomeBtn: cc.Node,

        node_rightBtn1: cc.Node,
        node_rightBtn2: cc.Node,
        node_rightBtn3: cc.Node,

        node_continueDealer: {
            'default': null,
            type: cc.Node,
            displayName: '续庄按钮容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._btnCompName = 'Button_multiple';

        this._registerAllBtn();
    },

    initChipBtn: function initChipBtn(valueDict) {
        this._setBtnInfo(valueDict);
    },

    setStartInfo: function setStartInfo() {
        this.setBtnGroupEnable(false);
    },

    _registerAllBtn: function _registerAllBtn() {
        var btns = this.node_btnGroup.children;
        for (var key in btns) {
            this.registerButton(btns[key], this.OnClick_chooseMoney, this, parseInt(key), true);
        }
        this.registerButton(this.node_exitHomeBtn, this.OnClick_exit, this);
        //this.node_exitHomeBtn.getComponent('Obj_leftTopMenu').bindFunc(this.OnClick_exit.bind(this));

        this.registerButton(this.node_rightBtn1, this.OnClick_right1, this);
        this.registerButton(this.node_rightBtn2, this.OnClick_right2, this);
        this.registerButton(this.node_rightBtn3, this.OnClick_right3, this);

        var continueDealer = this.node_continueDealer.children[0];
        var downDealer = this.node_continueDealer.children[1];
        this.registerButton(continueDealer, this.OnClick_continueDealer, this);
        this.registerButton(downDealer, this.OnClick_leaveDealer, this);
    },
    OnClick_chooseMoney: function OnClick_chooseMoney(event, userData) {
        var chooseLevel = parseInt(userData);
        this._showEffect(event.target);

        var playerChoose = chooseLevel + 1;
        GG.bull100Mgr.chooseChip(playerChoose);
    },
    OnClick_exit: function OnClick_exit(event) {
        var isContinue = GG.bull100Mgr.testExitBtn();
        if (!isContinue) return;

        if (G_Config_common.isLocal) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                GG.exitHome();
            });
            return;
        }

        if (GG.bull100Mgr.getIsBetting()) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                var netData = {
                    roomId: GG.bull100Mgr.getHomeID(),
                    isBreak: true
                };
                GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            });
        } else if (GG.bull100Mgr.getMyselfComp().getIsDealer() && GG.bull100Mgr.getDealerState()) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText3, function () {
                var netData = {
                    roomId: GG.bull100Mgr.getHomeID(),
                    isBreak: true
                };
                GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            });
        } else {
            var netData = {
                roomId: GG.bull100Mgr.getHomeID(),
                isBreak: true
            };
            GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
        }
    },
    //顯示玩家列表
    OnClick_right1: function OnClick_right1(event) {
        GG.bull100Mgr.showUI_playerList();
    },
    //顯示游戏规则
    OnClick_right2: function OnClick_right2(event) {
        var data = G_TYPE.gameModule.bull100;
        GG.bull100Mgr.showUI_rules(data);
    },
    //顯示走勢圖
    OnClick_right3: function OnClick_right3(event) {
        GG.bull100Mgr.showUI_trendList(GG.bull100Mgr.getHomeID());
    },

    //点击续庄
    OnClick_continueDealer: function OnClick_continueDealer(event) {
        GG.bull100Mgr.send_getDealerList();
    },
    //点击下庄
    OnClick_leaveDealer: function OnClick_leaveDealer(event) {
        GG.bull100Mgr.send_downDealer();
    },

    _setBtnInfo: function _setBtnInfo(valueDict) {
        var btns = this.node_btnGroup.children,
            index;
        for (var i = 0; i < btns.length; i++) {
            index = parseInt(i);
            btns[i].tag = valueDict[index];
            btns[i].getComponent(this._btnCompName).showMoney(valueDict[index], false);
        }
    },
    chooseMoney: function chooseMoney(index) {
        var btn = this._getBtnByIndex(index);
        //获取第一次进房是否可下注
        this.isBetting = GG.bull100Mgr.getFirstBetting();
        this._showEffect(btn);
    },

    //move touch effect
    _showEffect: function _showEffect(targetBtn) {
        this._chooseBtn(targetBtn);

        //effect
        // this.node_effectOnButton.active = true;
        this.node_effectUnderButton.active = true;

        // this.node_effectOnButton.position = cc.p(targetBtn.x, targetBtn.y);;
        this.node_effectUnderButton.x = targetBtn.x;
        this.node_effectUnderButton.y = -targetBtn.height / 2;
        //刚进入游戏如果可下注 强制自适应
        if (this.isBetting && !this._isAdaptPos) {
            this.node_effectUnderButton.position = G_TOOL.adaptPos(this.node_effectUnderButton.position);
            var size = G_TOOL.adaptSize(targetBtn.width, targetBtn.height);
            this.node_effectUnderButton.width = size.width;
            this._isAdaptPos = true;
        } else {
            this.node_effectUnderButton.width = targetBtn.width;
        }
        ////ani
        // //this.node_effectOnButton.getComponent(cc.Animation).play();
    },
    _hideEffect: function _hideEffect() {
        // this.node_effectOnButton.active = false;
        this.node_effectUnderButton.active = false;
    },

    //button touch move up
    _chooseBtn: function _chooseBtn(targetBtn) {
        this._cancelChoose();
        targetBtn.getComponent(this._btnCompName).showMoney(null, true);
        //targetBtn.y += targetBtn.height/2;
        this.setBtnEnable(targetBtn, false, true);
        this._lastBtn = targetBtn;
    },
    _cancelChoose: function _cancelChoose() {
        if (this._lastBtn) {
            this._hideEffect();
            this._lastBtn.getComponent(this._btnCompName).showMoney(null, false);
            this.setBtnEnable(this._lastBtn, true);
            this._lastBtn.y = 0;
            this._lastBtn = null;
        }
    },

    setBtnGroupEnable: function setBtnGroupEnable(isEnable) {
        this._cancelChoose();
        var btnGroup = this.node_btnGroup.children;
        for (var key in btnGroup) {
            this.setBtnEnable(btnGroup[key], isEnable);
        }
    },
    //更新押注按钮可点击
    changeBet: function changeBet(playerGoldValue) {
        var valueDict = GG.bull100Mgr.getMyselfComp().getMoneyChooseDict(),
            playerGold;
        if (!playerGoldValue) {
            playerGold = GG.bull100Mgr.getMyselfComp().getGoldValue();
        } else {
            playerGold = playerGoldValue;
        }
        this._changeBtn(valueDict, playerGold);
    },
    _changeBtn: function _changeBtn(valueDict, playerGoldValue) {
        var btns = this.node_btnGroup.children,
            index;
        for (var i = 0; i < btns.length; i++) {
            index = parseInt(i);
            if (valueDict[index] > playerGoldValue) {
                this.setOneBtnEnable(i);
            }
        }
    },

    //设置部分按钮置灰
    setOneBtnEnable: function setOneBtnEnable(num) {
        var key,
            keyInt,
            btns = this.node_btnGroup.children;
        for (key in btns) {
            keyInt = parseInt(key);
            if (num - 1 < keyInt) {
                this.setBtnEnable(btns[key], false);
                btns[key].y = 0;
                btns[key].getComponent(this._btnCompName).showMoney(null, false);
            }
        }
    },

    //是否显示续庄和下庄的按钮
    _setIsShowDealerBtn: function _setIsShowDealerBtn(isShow) {
        this.node_continueDealer.active = isShow;
    },
    //隐藏底部的所有按钮
    setBottomIsShow: function setBottomIsShow(isShow) {
        this.setBtnGroupEnable(false);
        this.node_btnGroup.active = isShow;
        this._setIsShowDealerBtn(!isShow);
    },

    _getBtnByIndex: function _getBtnByIndex(index) {
        return this.node_btnGroup.children[index - 1];
    },

    //获取吃瓜群众投注起点
    getIdleBettingPos: function getIdleBettingPos() {
        var pos = this.node_rightBtn1.position;
        var basePos = this.node_rightBtn1.parent.parent.position;
        var posX = pos.x + this.node_rightBtn1.parent.x + basePos.x + this.node_rightBtn1.width / 2;
        var posY = pos.y + this.node_rightBtn1.parent.y + basePos.y + this.node_rightBtn1.height / 2;
        return cc.p(posX, posY);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Bull100_goldContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd7585N/geFLooWNCmoG/bC9', 'Bull100_goldContainer');
// Script\Views\Scene_Bull100\Bull100_goldContainer.js

//移动金币的容器

var dataObj = {
    startPos: null,
    targetPos: null,
    isMine: null,
    callFunc: null
};

cc.Class({
    'extends': cc.Component,

    properties: {
        _dict_sortGold: null, //将显示的金币按照桌子索引来分类
        _allGoldNum: null, //金币的数量
        _isUpdate: null, //是否开启定时器
        _updateFunc: null, //定时器调用的函数

        frame_gold: cc.SpriteFrame },

    //金币图片
    // use this for initialization
    onLoad: function onLoad() {
        this._allGoldNum = 0;
        this._isUpdate = false;

        //金币的缩放
        this._goldScale = G_Config_common.scale_moveGold;
        //金币的移动时间
        this._goldMoveTime = 0.4;
        //胜利金币回收的移动速度
        this._recoveGoldMoveTime = 0.7;
        //胜利金币回收时候，金币移动间隔
        this._goldRecoveInterval = 0.03;
    },

    flyGolds: function flyGolds(dataList) {
        if (!dataList) dataList = [{
            startPos: cc.p(100, 500),
            targetPos: cc.p(600, 600),
            isMine: false,
            callFunc: null
        }, {
            startPos: cc.p(100, 500),
            targetPos: cc.p(550, 600),
            isMine: false,
            callFunc: null
        }, {
            startPos: cc.p(100, 500),
            targetPos: cc.p(600, 550),
            isMine: false,
            callFunc: null
        }];
        if (dataList.length > 0) GG.audioMgr.playSound(15);
        var dataObj;
        for (var i = 0; i < dataList.length; i++) {
            dataObj = dataList[i];
            this._flyOneGold(dataObj);
        }
    },

    //移动一个金币
    _flyOneGold: function _flyOneGold(dataObj) {
        if (!this._dict_sortGold) this._dict_sortGold = {};
        var gold = this._getOneGold();
        if (!dataObj || !gold) return;

        gold.tag = this._allGoldNum;
        var startPos = dataObj.startPos;
        var targetPos = dataObj.targetPos;
        gold.position = startPos;
        //gold.tableIndex = dataObj.tableIndex;
        //var index = dataObj.tableIndex+'_'+dataObj.tableType;
        var index = dataObj.tableIndex;
        if (!this._dict_sortGold[index]) this._dict_sortGold[index] = [];
        this._dict_sortGold[index].push(gold);
        this._allGoldNum += 1;
        var act1 = cc.moveTo(this._goldMoveTime, targetPos).easing(cc.easeOut(3.0));
        gold.runAction(cc.sequence(act1, cc.callFunc(this._oneGoldMoveEnd, this, dataObj)));
    },
    _oneGoldMoveEnd: function _oneGoldMoveEnd(target, dataObj) {
        //金币移动结束
        if (dataObj.callFunc) {
            dataObj.callFunc(dataObj.isMine);
        }
    },

    //获取一个金币
    _getOneGold: function _getOneGold() {
        if (!this.frame_gold) return null;
        if (!this._pool) this._pool = new cc.NodePool('Bull100_goldContainer');

        var goldNode = this._pool.get();
        if (!goldNode) {
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this.frame_gold;
        }
        goldNode.parent = this.node;
        //goldNode.scale = (1/this.node.parent.scale)*this._goldScale;
        goldNode.scale = this._goldScale;
        goldNode.active = true;
        return goldNode;
    },

    //金币回流的操作=========================================

    addRecoverCold: function addRecoverCold() {},

    //根据坐标回收金币
    recoverGold: function recoverGold(goldDict, targetPos) {
        if (this.node.children.length < 1) return;
        var goldNum,
            delList = [];
        for (var tableIndex in goldDict) {
            goldNum = goldDict[tableIndex];
            var addGoldList = this._getGoldListByIndex(tableIndex, goldNum);
            delList = delList.concat(addGoldList);
        }
        this._startRecover(delList, targetPos);
        GG.audioMgr.playSound(18);
    },
    //从某个桌子上拿走多少个金币
    _getGoldListByIndex: function _getGoldListByIndex(tableIndex, goldNum) {
        var goldListInTable = this._dict_sortGold[tableIndex];
        if (goldListInTable && goldListInTable.length > 0) {
            return goldListInTable.slice(0, goldNum);
        }
        return [];
    },
    _startRecover: function _startRecover(delList, targetPos) {
        this._delList = delList;
        this._recoverTargetPos = targetPos;

        this._interval = this._goldRecoveInterval;
        this._runTime = this._interval;
        this._updateFunc = this._updateRecover.bind(this);
        this._isUpdate = true;
    },
    _updateRecover: function _updateRecover() {
        if (this._delList.length > 0) {
            var gold = this._delList.pop();
            this._recoverOneGold(gold, this._recoverTargetPos);
        } else {
            this._updateFunc = null;
            this._isUpdate = false;
            this._recoverTargetPos = null;
            this._delList = null;
        }
    },
    _recoverOneGold: function _recoverOneGold(gold, targetPos) {
        var act1 = cc.moveTo(this._recoveGoldMoveTime, targetPos);
        gold.runAction(cc.sequence(act1, cc.callFunc(this._removeGold, this)));
    },

    _removeGold: function _removeGold(goldeNode) {
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    _deleteGoldImg: function _deleteGoldImg() {
        var golds = this.node.children;
        for (var key in golds) {
            golds[key].destroy();
        }
    },
    getGoldSize: function getGoldSize() {
        if (!this._goldSize) {
            var gold = this._getOneGold();
            this._goldSize = gold.getContentSize();
            this._removeGold(gold);
        }
        return this._goldSize;
    },

    //清理所有
    clearAll: function clearAll() {
        var goldList = this.node.children;
        for (var i = goldList.length - 1; i >= 0; i--) {
            this._removeGold(goldList[i]);
        }
        this._allGoldNum = 0;
        this._isUpdate = false;
        this._dict_sortGold = null;
        this.node.stopAllActions();
    },

    // called every frame, uncomment this function to activate update callback
    update: function update(dt) {
        if (!this._isUpdate) return;
        if (this._runTime >= this._interval) {
            this._runTime = 0;
            if (this._updateFunc) {
                this._updateFunc(dt);
            }
        } else this._runTime += dt;
    },

    onDestroy: function onDestroy() {
        if (this._pool) this._pool.clear();
    }
});

cc._RFpop();
},{}],"Bull100_playerBlock":[function(require,module,exports){
"use strict";
cc._RFpush(module, '56d8eUX/ttGJZhl4dhP/ZFi', 'Bull100_playerBlock');
// Script\Views\Scene_Bull100\Bull100_playerBlock.js

//玩家对象

cc.Class({
    'extends': cc.Component,

    properties: {
        _defaultHeadFrame: null, //默认的头像
        _chooseChip: null, //选择的筹码
        _isHandle: null, //是否是操作位
        _dict_betRecord: null, //投注记录
        _playerID: null, //玩家ID
        _siteIndex: null, //座位索引
        _touchTableCallFunc: null, //点击投注区域的回调
        _data_betting: null, //当前投注需要的信息对象\
        _isForceAdapt: null, //是否强制适配一次

        _goldValue: null, //拥有的金币数量
        _bettingGoldValue: null, //已经投注的金额
        _bettingTimes: null, //当前投注次数
        _seatMyselfComp: null, //座位上的自己玩家
        _dealerMyselfComp: null, //庄家座位上的自己
        _isDealer: null, //是否是庄家
        _goldValueEX: null, //额外的金币，用于吃瓜群众扣除
        _isSending: null, //是否正在投注请求

        node_headImg: {
            'default': null,
            type: cc.Node,
            displayName: '玩家头像'
        },
        node_playerName: {
            'default': null,
            type: cc.Node,
            displayName: '玩家名字'
        },
        node_roomName: {
            'default': null,
            type: cc.Node,
            displayName: '房间名字'
        },
        node_ownerGold: {
            'default': null,
            type: cc.Node,
            displayName: '玩家金币父层'
        },
        prefab_goldResultEffect: {
            'default': null,
            type: cc.Prefab,
            displayName: '金币增减特效'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        //头像上金币结果特效
        this._goldResultCompName = 'Obj_goldResultEffect';
        //筹码对应金币
        this._dict_goldMatch = G_Config_bull100.dict_betGoldNum;
        //头像上增减金币飞行时间
        //this._goldResultFlyTime = G_Config_grab.time_goldOnHead;
        this._goldResultFlyTime = 0.5;
        this._goldValue = 0;
        this._goldValueEX = 0;
        this._bettingGoldValue = 0;
        this._isSending = false;
        this._isForceAdapt = true;

        this.setDefaultChoose();
    },

    //初始设置玩家信息
    setPlayerData: function setPlayerData(dataObj) {
        this.node.active = true;
        this._dict_betRecord = null;
        this._bettingGoldValue = 0;
        this._bettingTimes = 0;
        this._data_betting = null;
        this._isDealer = dataObj.isDealer;
        this._setHeadImg(dataObj.icon);
        this._setPlayerName(dataObj.nickname);
        this.setGoldValue(dataObj.coin);
        this.setDefaultChoose();

        this._playerID = dataObj.playerId;
        this._siteIndex = dataObj.seatIndex;
    },

    //设置操作位的数据
    setHandleInfo: function setHandleInfo(handleInfo) {
        if (handleInfo.chooseDict) this._dict_goldValueMatch = handleInfo.chooseDict;
        //房间名字
        if (handleInfo.roomName) {
            //有房间名字
            this.node_roomName.getComponent(cc.Label).string = handleInfo.roomName;
        }
    },
    //绑定座位上的玩家
    bindSeatMyself: function bindSeatMyself(myselfComp) {
        this._seatMyselfComp = myselfComp;
    },
    //自己是庄家的时候，绑定庄家脚本
    bindMyDealer: function bindMyDealer(myselfComp) {
        this._dealerMyselfComp = myselfComp;
    },

    //设置默认的筹码选择
    setDefaultChoose: function setDefaultChoose() {
        if (!this._chooseChip) this._chooseChip = 1;
    },
    //选择一个筹码
    chooseChip: function chooseChip(choose) {
        this._chooseChip = choose;
    },

    _setHeadImg: function _setHeadImg(imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = this.node_headImg.getComponent(cc.Sprite).spriteFrame;
        if (!imgName) this.node_headImg.getComponent(cc.Sprite).spriteFrame = this._defaultHeadFrame;else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'head01';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    this.node_headImg.getComponent(cc.Sprite).spriteFrame = spriteFrame;
                }).bind(this));
            } else {
                this._headUrl = G_DATA.getResNetRoot(imgName);
                //this._headUrl = 'http://192.168.0.109:8080/classic/res/raw-assets/Texture/Common/head/head00.jpg';
                cc.loader.load(this._headUrl, (function (err, texture) {
                    if (err) {} else {
                        if (texture) {
                            var frame = new cc.SpriteFrame(texture);
                            this.node_headImg.getComponent(cc.Sprite).spriteFrame = frame;
                        }
                    }
                }).bind(this));
            }
        }
    },
    _setPlayerName: function _setPlayerName(playerName) {
        if (!playerName) playerName = '';
        playerName = G_TOOL.getNameLimit(playerName, 10, true);
        this.node_playerName.getComponent(cc.Label).string = playerName;
    },
    setGoldValue: function setGoldValue(value) {
        if (!value) value = 0;
        this._goldValue = value;
        var goldLabel = this.node_ownerGold.children[0].getComponent(cc.Label);
        goldLabel.string = G_TOOL.changeMoney(value);;

        //位置上的自己
        if (this._seatMyselfComp) {
            this._seatMyselfComp.setGoldValue(value);
        }
    },
    setIsHandle: function setIsHandle(isHandle) {
        this._isHandle = isHandle;
    },
    hidePlayer: function hidePlayer() {
        this.node.active = false;
    },
    setChooseGoldValue: function setChooseGoldValue(dict) {
        this._dict_goldValueMatch = dict;
    },
    //设置玩家是否是庄家，因为已经没有每局刷新玩家信息，所以需要主动设置
    setIsDealer: function setIsDealer(isDealer) {
        this._isDealer = isDealer;
    },

    //====================get

    //获取已经选择的筹码需要飞行的金币数量
    getFlyGoldNum: function getFlyGoldNum() {
        return this._dict_goldMatch[this._chooseChip];
    },
    getChooseChip: function getChooseChip() {
        return this._chooseChip;
    },
    getChooseGoldValue: function getChooseGoldValue() {
        return this._dict_goldValueMatch[this._chooseChip - 1];
    },
    getMoneyChooseDict: function getMoneyChooseDict() {
        return this._dict_goldValueMatch;
    },
    getWorldPos: function getWorldPos() {
        //if(this._isHandle) return this.node.position;
        //else{
        //    return this.node.parent.position
        //}
        return this.node.position;
    },
    //获取玩家的投注记录
    getBetRecord: function getBetRecord() {
        return this._dict_betRecord;
    },
    getIsShow: function getIsShow() {
        return this.node.active;
    },
    getSeatIndex: function getSeatIndex() {
        return this._siteIndex;
    },
    //获取当前剩余多少金币
    getGoldValue: function getGoldValue() {
        return this._goldValue;
    },
    //是否在本局已经投注过
    getIsBetting: function getIsBetting() {
        return Boolean(this._bettingGoldValue);
    },
    //是否是庄家
    getIsDealer: function getIsDealer() {
        return this._isDealer;
    },
    //获取玩家ID
    getPlayerID: function getPlayerID() {
        return this._playerID;
    },
    //获取已经投注的金币数额
    getHasBettingGold: function getHasBettingGold() {
        return this._bettingGoldValue;
    },

    //清理投注记录
    clearBettingGold: function clearBettingGold() {
        this._bettingGoldValue = 0;
        this._goldValueEX = 0;
    },

    //=======================头像上面金币的增减效果

    //显示玩家数值的增减
    showGoldChange: function showGoldChange(value) {
        //金钱没有变化
        //金钱变化正或者负
        //var dir = G_TOOL.getRandomBool() ? 1 : -1;
        this._showGoldCount(value);
    },
    //显示玩家数值特效
    _showGoldCount: function _showGoldCount(count) {
        if (!count) return;

        var labelNode = cc.instantiate(this.prefab_goldResultEffect);
        labelNode.parent = this.node;
        labelNode.zIndex = 2;

        labelNode.getComponent(this._goldResultCompName).showGrabGold(count);

        labelNode.scale = 0.1;
        var parentScale, widthOff;
        widthOff = this.node.width * 0.3;
        if (this._isHandle) {
            parentScale = this.node.scale;
        } else {
            parentScale = this.node.parent.scale;
        }
        if (this.node.x > cc.visibleRect.width / 2) widthOff *= -1;
        var time = this._goldResultFlyTime;
        var act1 = cc.moveTo(time, widthOff, this.node.height * 0.75);
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.fadeOut(time * 2);
        labelNode.runAction(cc.sequence(cc.spawn(act1, act2), act3, cc.callFunc(this._labelMoveEnd, this, count)));
    },
    _labelMoveEnd: function _labelMoveEnd(target, count) {
        target.destroy();
    },

    //投注金额，仅用于自己是吃瓜群众的时候
    reduceGoldEX: function reduceGoldEX(changeGold) {
        if (this._goldValueEX >= changeGold) {
            this._goldValueEX -= changeGold;
            return 0;
        } else {
            var value = changeGold - this._goldValueEX;
            this._goldValueEX = 0;
            return value;
        }
    },

    //=============================做投注的请求

    //点击桌子的大或者小
    touchTable: function touchTable(grabInfo, callFunc) {
        this._touchTableCallFunc = callFunc;
        grabInfo.startPos = this.getWorldPos();
        grabInfo.goldNum = this.getFlyGoldNum();
        if (!this._isHandle || G_Config_common.isLocal) {
            //不是在玩家自己或者只是单机模式
            var money = grabInfo.goldValue;
            grabInfo.goldNum = G_Config_grab.num_otherGrabGoldNum;
            this._data_betting = grabInfo;
            this._setTouchTableData(money, this.getGoldValue() - money);
            return;
        } else {
            if (this._isSending) {
                return;
            }
            grabInfo.goldValue = this.getChooseGoldValue();
            this._data_betting = grabInfo;
            //可以投注
            var netData = {
                matchId: GG.bull100Mgr.getMatchID(), //赛事id  int
                type: G_TYPE.net_gameModule.bull100, //投注类型 string
                item: GG.bull100Mgr.getItemByTableIndex(grabInfo.tableIndex), //投注项 string
                gold: grabInfo.goldValue };
            //投注额 int
            this._isSending = true;
            GG.socketMgr.SendMsg(NetType.s_doGrab, netData);
            GG.socketMgr.registerLong(NetType.r_doGrab_return, this.net_grabReturn.bind(this));
        }
    },

    //百人——投注成功后返回的函数
    net_grabReturn: function net_grabReturn(recData) {
        this._isSending = false;

        var tip = recData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            var dataObj = {
                tipIndex: 19,
                retainTime: 3,
                showStr: tip.tip
            };
            GG.tipsMgr.showTxtTip(dataObj);
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
        this._setTouchTableData(bettingValue, leaveValue);
    },

    _setTouchTableData: function _setTouchTableData(bettingValue, leaveValue) {
        //重组投注信息对象
        if (!this._data_betting) return;
        var dataObj = G_OBJ.data_bullBetting();
        for (var attrName in this._data_betting) {
            if (attrName) dataObj[attrName] = this._data_betting[attrName];
        }
        this._data_betting = null;

        dataObj.goldValue = bettingValue;
        //更新投注金额池信息
        this._bettingGoldValue += dataObj.goldValue;
        this._goldValueEX += dataObj.goldValue;
        //更新余额信息
        this.setGoldValue(leaveValue);
        //记录投注信息
        this._touchTableSuccess(dataObj.tableIndex, dataObj.goldNum);
        //押注过程中更新押注按钮（置灰）
        GG.bull100Mgr.getBtnsComp().changeBet(leaveValue);
        this._bettingTimes += 1;
        // GG.bull100Mgr.setBettingTimes(this._bettingTimes);
        if (this._touchTableCallFunc) {
            this._touchTableCallFunc(dataObj);
            this._touchTableCallFunc = null;
        }
    },

    //投注成功后记录投注信息
    _touchTableSuccess: function _touchTableSuccess(tableIndex, goldImgNum) {
        if (!this._dict_betRecord) this._dict_betRecord = {};
        if (!this._dict_betRecord[tableIndex]) this._dict_betRecord[tableIndex] = goldImgNum;else this._dict_betRecord[tableIndex] += goldImgNum;
    },

    //获取庄家卡牌的位置
    getDealerPokerPos: function getDealerPokerPos() {
        //强制适配
        if (this._isForceAdapt) {
            this._isForceAdapt = false;
            var widget = this.getComponent(cc.Widget);
            if (widget) {
                widget.updateAlignment();
            }
        }
        //卡牌位置是庄家中心点偏左
        var pokerOffX = 0.4;
        return cc.p(this.node.x - this.node.width * 0.5 * this.node.scale * pokerOffX, this.node.y - this.node.height * 0.5 * this.node.scale);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Bull100_playerContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6c7ea/tmxtDvIFMLUdtx57X', 'Bull100_playerContainer');
// Script\Views\Scene_Bull100\Bull100_playerContainer.js

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
    'extends': require('AutoDealing'),

    properties: {
        _dict_playerComp: null, //座位上的玩家脚本
        _playerNum: null, //场上的玩家数量

        node_playerContainer: { //所有玩家的容器，庄家和自己除外
            'default': null,
            type: cc.Node,
            displayName: '所有玩家容器'
        },
        node_btnGrabDealer: { //抢庄按钮
            'default': null,
            type: cc.Node,
            displayName: '抢庄按钮'
        },
        node_myself: {
            'default': null,
            type: cc.Node,
            displayName: '玩家自己'
        },
        node_dealer: {
            'default': null,
            type: cc.Node,
            displayName: '庄家'
        },
        prefab_player: {
            'default': null,
            type: cc.Prefab,
            displayName: '座位上的玩家'
        },
        comp_seatsContainer: {
            'default': null,
            type: require('Obj_seatsContainer'),
            displayName: '座位容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._dict_playerComp = {};
        this._playerNum = 0;
        this._playerCompName = 'Bull100_playerBlock';
        this._delaerWordCompName = 'Obj_dealerWordEffect';
        this.registerButton(this.node_btnGrabDealer, this.OnClick_grabDealer, this);

        this.getMyselfComp().setIsHandle(true);
    },

    //点击了上庄按钮
    OnClick_grabDealer: function OnClick_grabDealer(event) {
        GG.bull100Mgr.send_getDealerList();
    },

    //seatInfoList：座位上的玩家；selfInfo自己；dealerInfo庄家
    setStartInfo: function setStartInfo(seatInfoList, selfInfo, dealerInfo) {
        //整理座位信息
        var dictPlayer = {};
        var selfID = GG.getPlayer().getPlayerID();
        var listLen = seatInfoList.length,
            seatIndex,
            onePlayer,
            content,
            playerComp;
        for (var i = 0; i < listLen; i++) {
            onePlayer = seatInfoList[i];
            seatIndex = onePlayer['seatIndex'];
            content = onePlayer['player'];
            content.seatIndex = seatIndex;
            dictPlayer[seatIndex] = content;
        }
        //根据信息控制位置上玩家的显隐
        //显示有数据的玩家
        for (var seatIndex in dictPlayer) {
            content = dictPlayer[seatIndex];
            //从列表中获取属于自己的信息
            if (content && content.playerId == selfID) {
                for (var attrName in content) {
                    if (attrName == 'seatIndex' && content[attrName] == 0) continue;
                    selfInfo[attrName] = content[attrName];
                }
            }
            //座位号从1开始
            if (seatIndex < 1) continue;
            //有玩家数据
            playerComp = this._addOnePlayer(seatIndex);
            if (playerComp) {
                playerComp.setPlayerData(content);
                if (content && content.playerId == selfID) this.getMyselfComp().bindSeatMyself(playerComp);
            }
        }
        //隐藏没有数据的位置
        var siteNum = this.comp_seatsContainer.getSeatsNum();
        for (var i = 0; i < siteNum; i++) {
            seatIndex = i + 1;
            if (!dictPlayer[seatIndex]) this._removeOnePlayer(seatIndex);
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
        if (selfInfo.playerId) this.setOwnerInfo(selfInfo);
        this.setDealerInfo(dealerInfo);

        //当前自己是庄家的时候自己玩家需要显示的是可用余额而不是全额
        if (isDealer) {
            //因为新的机制已经没有给予玩家的信息，为了更新金额所以只能从庄家信息中计算
            this.getMyselfComp().setGoldValue(selfInfo.coin);
        }
        this.getMyselfComp().setIsDealer(isDealer);
    },

    setOwnerInfo: function setOwnerInfo(selfInfo) {
        this.getMyselfComp().setPlayerData(selfInfo);
        this.getMyselfComp().setHandleInfo(selfInfo);
    },
    setDealerInfo: function setDealerInfo(dealerInfo) {
        var newDealerInfo = {};
        for (var attrName in dealerInfo) {
            newDealerInfo[attrName] = dealerInfo[attrName];
        }
        newDealerInfo.coin = dealerInfo.usableBalance;
        this.getDealerComp().setPlayerData(newDealerInfo);
    },

    //有一个玩家中途进入
    onePlayerInsert: function onePlayerInsert(seatIndex, playerData) {
        var reg = /^\+?[1-9][0-9]*$/;
        if (reg.test(seatIndex)) {
            playerData.seatIndex = seatIndex;
            this._hidePlayer(seatIndex, playerData.playerId);
            var playerComp = this._addOnePlayer(seatIndex);
            if (playerComp) playerComp.setPlayerData(playerData);
        }
    },

    //删除重复的玩家，结束本局时候，重复进出房间会出现多个同样玩家
    _hidePlayer: function _hidePlayer(targetIndex, playerID) {
        var playerComp;
        for (var seatIndex in this._dict_playerComp) {
            playerComp = this._dict_playerComp[seatIndex];
            if (seatIndex == targetIndex) {
                //找到位置
                //if(playerComp) playerComp.setPlayerData(playerData);
            } else {
                    if (playerComp && playerComp.getPlayerID() == playerID) {
                        this._removeOnePlayer(seatIndex);
                    }
                }
        }
    },
    //增加一个上场玩家
    _addOnePlayer: function _addOnePlayer(siteIndex) {
        var playerComp = this._dict_playerComp[siteIndex];
        if (!playerComp) {
            var player = cc.instantiate(this.prefab_player);
            player.parent = this.node_playerContainer;
            var size = G_TOOL.adaptSize(player.width, player.height);
            player.width = size.width;
            player.height = size.height;
            player.position = this.comp_seatsContainer.addOnePlayer(siteIndex);
            playerComp = player.getComponent(this._playerCompName);
            this._dict_playerComp[siteIndex] = playerComp;
        }
        this._playerNum += 1;
        return playerComp;
    },
    //一个玩家离场
    _removeOnePlayer: function _removeOnePlayer(siteIndex) {
        var playerComp = this._dict_playerComp[siteIndex];
        if (!playerComp || !playerComp.getIsShow()) return;
        playerComp.hidePlayer();
        this.comp_seatsContainer.onePlayerLeave(siteIndex);
        this._playerNum -= 1;
    },

    //显示所有玩家的金币增减特效
    showGoldChangeEffect: function showGoldChangeEffect() {
        var player;
        for (var siteIndex in this._dict_playerComp) {
            player = this._dict_playerComp[siteIndex];
            if (player) {
                player.showGoldChange();
            }
        }
        //mine
        this.getMyselfComp().showGoldChange();
    },

    //=======================

    //场上的金币结算  winDict = {0:1, 2:1, 4:1},  {桌子索引：是否胜利}
    flyWinGold: function flyWinGold(winDict) {
        var isMoveGold = false;
        //自己的金币结算
        var recordDict;
        var comp = this.getMyselfComp();
        recordDict = comp.getBetRecord();
        if (recordDict) {
            var newDict = this._getWinGoldDict(winDict, recordDict);
            this._moveGold(newDict, comp.getWorldPos());
        }
        //其他玩家的胜利金币移动
        var playerComp;
        for (var seatIndex in this._dict_playerComp) {
            playerComp = this._dict_playerComp[seatIndex];
            if (playerComp.getPlayerID() == GG.getPlayerID()) continue;
            if (playerComp && playerComp.getIsShow()) {
                recordDict = playerComp.getBetRecord();
                if (recordDict) {
                    isMoveGold = true;
                    var newDict = this._getWinGoldDict(winDict, recordDict);
                    this._moveGold(newDict, playerComp.getWorldPos());
                }
            }
        }
        //是否有吃瓜群众赢钱
        recordDict = GG.bull100Mgr.getIdleBetting();
        if (recordDict) {
            isMoveGold = true;
            var newDict = this._getWinGoldDict(winDict, recordDict);
            this._moveGold(newDict, GG.bull100Mgr.getIdleBettingPos());
        }

        //庄家回收剩余的金币
        var goldContainer = this.getGoldContainer();
        var isFly = goldContainer.recoverAllGold(this.getDealerComp().getWorldPos());
        if (isFly) isMoveGold = true;

        if (isMoveGold) GG.audioMgr.playSound(18);
    },

    _getWinGoldDict: function _getWinGoldDict(winDict, recordDict) {
        var tableType,
            newDict = {};
        for (var tableIndex in winDict) {
            tableType = winDict[tableIndex];
            if (tableType && recordDict[tableIndex]) {
                newDict[tableIndex] = recordDict[tableIndex];
            }
        }
        return newDict;
    },
    //金币结算移动
    _moveGold: function _moveGold(recoverDict, targetPos) {
        var goldNum,
            goldContainer = this.getGoldContainer();
        for (var tableIndex in recoverDict) {
            goldNum = recoverDict[tableIndex];

            var flyDataList = G_OBJ.data_flyGold_tableToPlayer();
            flyDataList.tableIndex = tableIndex;
            flyDataList.goldNum = goldNum;
            flyDataList.targetPos = targetPos;
            goldContainer.tableToPlayer(flyDataList);
        }
    },

    //设置玩家操作位置的显隐
    setMyselfIsShow: function setMyselfIsShow(isShow) {
        this.node_myself.active = isShow;
    },
    //设置抢庄按钮是否可用
    setUpDealerBtnEnable: function setUpDealerBtnEnable(isEnable) {
        this.setBtnEnable(this.node_btnGrabDealer, isEnable);
    },

    //=======================

    //获取庄家的卡牌位置
    getDealerPokerPos: function getDealerPokerPos() {
        return this.getDealerComp().getDealerPokerPos();
    },
    //获取玩家自己
    getMyselfComp: function getMyselfComp() {
        return this.node_myself.getComponent(this._playerCompName);
    },
    //获取玩家庄字特效
    getMyselfEffectComp: function getMyselfEffectComp() {
        var myselfComp = this.getMyselfComp();
        var child = myselfComp.node.getChildByName('bankerMark');
        var comp;
        if (child) {
            comp = child.getComponent(this._delaerWordCompName);
        }
        return comp;
    },
    getMyselfPos: function getMyselfPos() {
        return this.node_myself.position;
    },
    //获取庄家
    getDealerComp: function getDealerComp() {
        return this.node_dealer.getComponent(this._playerCompName);
    },
    //获取庄家庄字特效
    getDealerWordEffectComp: function getDealerWordEffectComp() {
        var dealerComp = this.getDealerComp();
        var child = dealerComp.node.getChildByName('bankerMark');
        var comp;
        if (child) {
            comp = child.getComponent(this._delaerWordCompName);
        }
        return comp;
    },
    //获取在座位上的玩家脚本
    getPlayerComp: function getPlayerComp(siteIndex) {
        return this._dict_playerComp[siteIndex];
    },
    //获取金币容器
    getGoldContainer: function getGoldContainer() {
        return GG.bull100Mgr.getGoldContainer();
    },

    //清理所有
    clearAll: function clearAll() {
        for (var seatIndex in this._dict_playerComp) {
            this._removeOnePlayer(seatIndex);
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing","Obj_seatsContainer":"Obj_seatsContainer"}],"Bull100_pokerContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '36070K0trNCHKLbIVOexRNn', 'Bull100_pokerContainer');
// Script\Views\Scene_Bull100\Bull100_pokerContainer.js

//卡牌容器

cc.Class({
    'extends': cc.Component,

    properties: {
        _pool: null, //对象容器
        _startPos: null, //开始位置
        _pokerNum: null, //存在的卡牌数量
        _dict_pokerGroup: null, //卡牌对象牌组
        _pokerSize: null, //卡牌的宽高
        _coverPokerCallFunc: null, //所有背面卡牌发完
        _dict_winList: null, //胜利的卡牌
        _list_pokerResultValue: null, //卡牌开牌后的结果数值
        _list_pokerPos: null, //卡牌位置信息
        _maxPokerNum: null, //最大的卡牌数量
        _list_hidePokers: null, //隐藏的卡牌列表

        prefab_poker: cc.Prefab },

    //卡牌容器
    // use this for initialization
    onLoad: function onLoad() {
        this._pokerCompName = 'Obj_poker';
        this._list_hidePokers = [];
        this._pokerNum = 0;

        //卡牌间隔
        this._offPokerX = 20;
        //发牌时间间隔
        this._offGiveTime = 0.08;
        //每副牌之间的时间间隔
        this._offGroupTime = 0.3;
        //单张卡牌移动时间
        this._pokerMoveTime = 0.3;
        //一副牌有几张
        this._groupPokerNum = 5;
        //卡牌第二个动作移动时间
        this._pokerMoveTime2 = 0.2;
        //对卡牌进行缩放
        this._pokerScale = 0.5;
        //开牌间隔
        this._openInterval = 0.6;
    },

    //分发盖住的牌
    giveCoverPokers: function giveCoverPokers(posList, callFunc, isOverAni) {
        this._coverPokerCallFunc = callFunc;
        this._maxPokerNum = posList.length * this._groupPokerNum;
        this._clearPokers();
        this._pokerData = null;
        if (posList) {
            this._list_pokerPos = posList;
            this._pokerData = this._list_pokerPos.concat([]);
        }
        if (isOverAni) this._showPokerGroup();else {
            this._givePokerGroup();
        }
    },

    //-------------------------------

    //直接将需要的牌显示出来，不做动画
    _showPokerGroup: function _showPokerGroup() {
        var pos,
            posListLen = this._pokerData.length,
            poker,
            curNum;
        for (var i = posListLen - 1; i >= 0; i--) {
            pos = this._pokerData[i];
            for (var j = 0; j < this._groupPokerNum; j++) {
                poker = this._getOnePoker();
                if (poker) {
                    curNum = this._pokerNum % this._groupPokerNum;
                    poker.position = cc.p(pos.x + this._offPokerX * curNum, pos.y - this._pokerSize.height * this._pokerScale * 0.5);
                    //poker.position = G_TOOL.adaptPos(poker.position);
                    if (!this._dict_pokerGroup[this._getGroupNum()]) this._dict_pokerGroup[this._getGroupNum()] = [];
                    this._dict_pokerGroup[this._getGroupNum()].push(poker);
                    this._pokerNum += 1;
                }
            }
        }
        if (this._coverPokerCallFunc) {
            this._coverPokerCallFunc();
            this._coverPokerCallFunc = null;
        }
    },

    //--------------------------------
    //发一副牌
    _givePokerGroup: function _givePokerGroup() {
        if (!this._pokerData || this._pokerData.length < 1) {
            //所有卡牌发完
            if (this._coverPokerCallFunc) {
                this._coverPokerCallFunc();
                this._coverPokerCallFunc = null;
            }
            return;
        }

        this._flyNum = 5;
        this._dict_pokerGroup[this._getGroupNum()] = [];
        var targetPos = this._pokerData.pop();
        for (var i = 0; i < this._groupPokerNum; i++) {
            //最后坐标需要计算卡牌的宽高
            this._flyOnePoker(targetPos);
        }
        GG.audioMgr.playSound(14);
    },
    //移动单张牌
    _flyOnePoker: function _flyOnePoker(targetPos) {
        var poker = this._getOnePoker();
        if (!targetPos || !poker) return;
        var curNum = this._pokerNum % this._groupPokerNum;
        var act1 = cc.delayTime(this._offGiveTime * curNum);

        var act2 = cc.moveTo(this._pokerMoveTime, cc.p(targetPos.x, targetPos.y - this._pokerOffY));
        poker.stopAllActions();
        poker.runAction(cc.sequence(act1, act2, cc.callFunc(this._onePokerFlyEnd, this)));
        this._dict_pokerGroup[this._getGroupNum()].push(poker);
        this._pokerNum += 1;
    },
    _onePokerFlyEnd: function _onePokerFlyEnd(target) {
        //有一张卡牌移动结束
        this._flyNum -= 1;
        if (this._flyNum < 1) {
            this._sortPokerOff();
            var act = cc.delayTime(this._offGroupTime);
            this.node.runAction(cc.sequence(act, cc.callFunc(this._givePokerGroup, this)));
        }
    },
    //把发好的牌散开
    _sortPokerOff: function _sortPokerOff() {
        var pokerList = this._dict_pokerGroup[this._getGroupNum() - 1];
        var len = pokerList.length;
        var poker;
        for (var i = 0; i < len; i++) {
            poker = pokerList[i];
            if (!poker) continue;
            poker.stopAllActions();
            poker.runAction(cc.moveBy(this._pokerMoveTime2, this._offPokerX * i, 0));;
        }
    },

    //设置卡牌的信息
    setPokersData: function setPokersData(dataList, pokerValueList) {
        this._list_pokerResultValue = pokerValueList;
        var pokerGroup,
            pokerData,
            pokerNodeList,
            pokerGroupNum = this._getGroupNum();
        for (var i = 0; i < pokerGroupNum; i++) {
            pokerGroup = dataList[i];
            pokerNodeList = this._dict_pokerGroup[i];
            if (pokerGroup) {
                for (var j = 0; j < pokerGroup.length; j++) {
                    pokerData = pokerGroup[j];
                    this._setOnePokerInfo(pokerNodeList[j], pokerData.pokerIndex, pokerData.isOpen);
                }
            }
        }
    },
    _setOnePokerInfo: function _setOnePokerInfo(poker, pokerIndex, isOpen) {
        var pokerInfo = G_DATA.getPokerInfo(pokerIndex);
        var pokerComp = poker.getComponent(this._pokerCompName);
        pokerComp.setPokerInfo(pokerInfo.pokerType, pokerInfo.pokerValue, isOpen);
    },

    //开牌
    openPokers: function openPokers(dict) {
        this._dict_winList = dict;
        this._openGroupIndex = 0;
        this._openOneGroup();
    },
    //开启一副牌
    _openOneGroup: function _openOneGroup() {
        if (this._pokerNum < 1) {
            return;
        }
        var pokerList = this._dict_pokerGroup[this._openGroupIndex];
        if (!pokerList) {
            //卡牌全部打开
            return;
        }
        var comp,
            pokerInfo,
            callFunc = this._openCallBack.bind(this);
        for (var i = 0; i < pokerList.length; i++) {
            comp = pokerList[i].getComponent(this._pokerCompName);
            comp.openPoker(callFunc);
            callFunc = null;
        }
        this._openGroupIndex += 1;
        GG.audioMgr.playSound(16);
        //this.node.runAction(cc.sequence(cc.delayTime(this._openInterval), cc.callFunc(this._openOneGroup, this)));
    },
    _openCallBack: function _openCallBack() {
        if (this._openGroupIndex > 1 && this._dict_winList) {
            var tableIndex = this._openGroupIndex - 2;
            var minIndex = GG.bull100Mgr.getMinTableIndex(tableIndex);
            var bigIndex = GG.bull100Mgr.getBigTableIndex(tableIndex);
            var curIndex = this._dict_winList[bigIndex] ? bigIndex : minIndex;
            GG.bull100Mgr.showTableWin(curIndex, this._dict_winList[curIndex]);
        }
        //显示这副卡牌的值
        var value = this._list_pokerResultValue[this._openGroupIndex - 1];
        var pos = this._list_pokerPos[this._list_pokerPos.length - this._openGroupIndex];
        GG.bull100Mgr.showPokerResult(cc.p(pos.x + this._pokerSize.width * 0.5 * 0.5, pos.y - +this._pokerSize.height * 0.5 * 0.5), value);
        var audioID = value == 0 ? 11 : value;
        GG.audioMgr.playSound(audioID);
        //打开下一付牌
        this._openOneGroup();
    },

    //增加一张卡牌
    _getOnePoker: function _getOnePoker() {
        if (!this.prefab_poker) return null;
        //if(!this._pool) this._pool = new cc.NodePool('Bull100_pokerContainer');

        var pokerNode = this._list_hidePokers.pop();
        if (!pokerNode) {
            pokerNode = cc.instantiate(this.prefab_poker);
            if (!this._pokerSize) {
                this._pokerSize = pokerNode.getComponent(this._pokerCompName).getPokerSize();
                this._pokerOffY = this._pokerSize.height * this._pokerScale * 0.5;
            }
            pokerNode.parent = this.node;
            pokerNode.scale = this._pokerScale;
        }
        pokerNode.stopAllActions();
        pokerNode.active = true;
        pokerNode.position = this._getStartPos();
        pokerNode.getComponent(this._pokerCompName).showPokerNegative();
        return pokerNode;
    },

    _removePoker: function _removePoker(pokerNode) {
        //pokerNode.stopAllActions()
        pokerNode.getComponent(this._pokerCompName).clearAllActions();
        pokerNode.getComponent(this._pokerCompName).showPokerNegative();
        pokerNode.active = false;
        //this._pool.put(pokerNode);
        this._list_hidePokers.push(pokerNode);
    },

    _deletePokers: function _deletePokers() {
        //var poker = this.node.children;
        //for(var key in poker){
        //    poker[key].destroy();
        //}
    },
    _clearPokers: function _clearPokers() {
        this._pokerNum = 0;
        this._dict_pokerGroup = {};
        var pokers = this.node.children;
        this._list_hidePokers = [];
        var comp,
            pokerNum = pokers.length;
        for (var i = pokerNum - 1; i >= 0; i--) {
            comp = pokers[i].getComponent(this._pokerCompName);
            if (comp) this._removePoker(pokers[i]);
        }
    },

    //获取初始位置
    _getStartPos: function _getStartPos() {
        if (!this._startPos) {
            this._startPos = G_DATA.getBottomTipPos();
        }
        return this._startPos;
    },
    //获取当前有几组卡牌
    _getGroupNum: function _getGroupNum() {
        return Math.floor(this._pokerNum / this._groupPokerNum);
    },
    //获取当前是否已经发完完整的牌
    getIsGivePokerEnd: function getIsGivePokerEnd() {
        return !Boolean(this._coverPokerCallFunc);
    },

    //清理所有
    clearAll: function clearAll() {
        this._clearPokers();
        this._dict_winList = null;
        this.node.stopAllActions();
    },

    onDestroy: function onDestroy() {
        this._deletePokers();
        if (this._pool) this._pool.clear();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Bull100_pokerResultContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '4c19f4ThPlPK6/llGibgPGc', 'Bull100_pokerResultContainer');
// Script\Views\Scene_Bull100\Bull100_pokerResultContainer.js

//显示卡牌的结果

cc.Class({
    'extends': cc.Component,

    properties: {
        prefab_pokerResult: {
            'default': null,
            type: cc.Prefab,
            displayName: '卡牌结果'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._pokerResultCompName = 'Obj_onPokerResult';
    },

    showPokerResult: function showPokerResult(pos, pokerValue) {
        if (!this._list_pokerResult) this._list_pokerResult = [];

        var resultComp, resultNode, needComp;
        for (var i = 0; i < this._list_pokerResult.length; i++) {
            resultComp = this._list_pokerResult[i];
            if (resultComp && !resultComp.getIsShow()) {
                //有隐藏的卡牌
                needComp = resultComp;
                break;
            }
        }
        if (!needComp) {
            resultNode = cc.instantiate(this.prefab_pokerResult);
            resultNode.parent = this.node;
            needComp = resultNode.getComponent(this._pokerResultCompName);
            this._list_pokerResult.push(needComp);
        }
        needComp.showResult(pos, pokerValue, true);
    },
    _clearPokerResult: function _clearPokerResult() {
        if (this._list_pokerResult) {
            for (var i = 0; i < this._list_pokerResult.length; i++) {
                this._list_pokerResult[i].hideOnPokerResult();
            }
        }
    },

    clearAll: function clearAll() {
        this._clearPokerResult();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Bull100_showResult":[function(require,module,exports){
"use strict";
cc._RFpush(module, '420eb+YEfVKo5P4BV/wWN52', 'Bull100_showResult');
// Script\Views\Scene_Bull100\Bull100_showResult.js

//显示百人的赛事结果界面

cc.Class({
    'extends': cc.Component,

    properties: {
        _list_rankHead: null, //排行的头像列表
        _winColor: null, //胜利的颜色
        _loseColor: null, //失败的颜色

        node_myWinGold: {
            'default': null,
            type: cc.Node,
            displayName: '自己赢的钱'
        },
        prefab_head: {
            'default': null,
            type: cc.Prefab,
            displayName: '头像'
        },
        node_container: {
            'default': null,
            type: cc.Node,
            displayName: '头像容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._headCompName = 'Obj_winRankHead';
        this._loseColor = new cc.Color(199, 199, 199);
    },

    //显示结果
    showResult: function showResult(dataObj) {
        this.node.active = true;
        var myChangeGold = dataObj.myChangeGold;
        if (!myChangeGold) myChangeGold = 0;
        this._setGoldValue(myChangeGold);

        var dataList = [{
            name: '人1',
            addGold: 99
        }];
        var rankDict = dataObj.othersInfo;
        if (rankDict) {
            dataList = [];
            var info;
            for (var seatIndex in rankDict) {
                info = rankDict[seatIndex];
                var changeGold = info.changeGold;
                if (changeGold > 0) {
                    dataList.push({
                        name: info.name,
                        addGold: changeGold,
                        icon: info.icon
                    });
                }
            }
        }
        this._showRank(dataList);
    },

    _showRank: function _showRank(dataList) {
        var heads = this.node_container.children;
        var len = heads.length,
            head,
            data;
        for (var i = 0; i < heads.length; i++) {
            head = heads[i].getComponent(this._headCompName);
            if (head) {
                data = dataList[i];
                if (data) {
                    head.setIsShow(true);
                    head.setData(data);
                } else {
                    head.setIsShow(false);
                }
            }
        }
    },

    _setGoldValue: function _setGoldValue(newValue) {
        var isPlus = Boolean(newValue > 0);
        this._setMyGoldColor(isPlus);
        var showStr;
        if (isPlus) showStr = '+' + newValue;else showStr = newValue + '';
        this.node_myWinGold.getComponent(cc.Label).string = showStr;
    },

    _setIsShow: function _setIsShow(isShow) {
        this.node.active = isShow;
    },

    _setMyGoldColor: function _setMyGoldColor(isWin) {
        if (isWin) {
            if (this._winColor) {
                this.node_myWinGold.color = this._winColor;
                this._winColor = null;
            }
        } else {
            if (!this._winColor) this._winColor = this.node_myWinGold.color;
            this.node_myWinGold.color = this._loseColor;
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Bull100_tableContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '29ed5kqelRJQ6wjhrsG5Xlr', 'Bull100_tableContainer');
// Script\Views\Scene_Bull100\Bull100_tableContainer.js

//存放所有的桌子

cc.Class({
    'extends': cc.Component,

    properties: {
        prefab_table: cc.Prefab },

    //桌子预制体
    // use this for initialization
    onLoad: function onLoad() {
        this._tableCompName = 'Bull100_table';

        //this._addTables();
    },

    _addTables: function _addTables() {
        var tableNum = 4;
        for (var i = 0; i < tableNum; i++) {
            var table = cc.instantiate(this.prefab_table);
            if (!table) continue;
            table.parent = this.node;
        }
    },

    //初始化所有的桌子
    initAllTables: function initAllTables() {
        var tables = this.node.children;
        var table, comp;
        for (var i = 0; i < tables.length; i++) {
            table = tables[i];
            comp = table.getComponent(this._tableCompName);
            if (comp) comp.initTable(i);
        }
    },

    //获取每张桌子对应的卡牌的位置
    getTablePokerPos: function getTablePokerPos() {
        var tables = this.node.children;
        var table,
            comp,
            posList = [];
        for (var i = tables.length - 1; i >= 0; i--) {
            table = tables[i];
            comp = table.getComponent(this._tableCompName);
            if (comp) {
                posList.push(comp.getPokerPos());
            }
        }
        return posList;
    },

    //开启所有桌子的点击功能
    setTableTouchEnable: function setTableTouchEnable(isEnable) {
        var tables = this.node.children;
        var table, comp;
        for (var i = 0; i < tables.length; i++) {
            table = tables[i];
            comp = table.getComponent(this._tableCompName);
            if (comp) comp.setTouchEnable(isEnable);
        }
    },

    //显示某张桌子的胜利失败情况
    showTableWin: function showTableWin(tableIndex, showWin) {
        var tables = this.node.children;
        var containerIndex = GG.bull100Mgr.getTableContainerIndex(tableIndex);
        var table = tables[containerIndex];
        if (table) {
            var comp = table.getComponent(this._tableCompName);
            if (comp) {
                comp.showWin(tableIndex, showWin);
            }
        }
    },

    //获取对应桌子索引的，多个随机金币位置
    getGoldPosList: function getGoldPosList(tableIndex, goldNum) {
        var tables = this.node.children;
        var containerIndex = GG.bull100Mgr.getTableContainerIndex(tableIndex);
        var table = tables[containerIndex];
        if (table) {
            return table.getComponent(this._tableCompName).getGoldPosList(tableIndex, goldNum);
        }
        return null;
    },

    //设置桌子的成功投注信息
    setBettingSuccess: function setBettingSuccess(tableIndex, goldValue, isMine) {
        var tables = this.node.children;
        var containerIndex = GG.bull100Mgr.getTableContainerIndex(tableIndex);
        var table = tables[containerIndex];
        if (table) {
            //设置自己桌子的成功投注信息
            if (isMine) table.getComponent(this._tableCompName).setMyselfBettingSuccess(tableIndex, goldValue);else table.getComponent(this._tableCompName).setBettingSuccess(tableIndex, goldValue);
        }
    }
});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Bull100_table":[function(require,module,exports){
"use strict";
cc._RFpush(module, '67349AfexdLn4EN0FD/POqC', 'Bull100_table');
// Script\Views\Scene_Bull100\Bull100_table.js

//一张赌桌

cc.Class({
    'extends': cc.Component,

    properties: {
        _typeValue: null, //该桌赌牌的类型值
        _pool: null, //放置金币的容器
        _bigTableIndex: null, //当前桌子的索引,同时也是取大的桌子的索引
        _minTableIndex: null, //是取小的桌子的索引
        _bigCenterPos: null, //左边的金币坐标中心
        _minCenterPos: null, //右边的金币坐标中心
        _winTableType: null, //胜利的桌子类型

        _bigGoldNum: null, //左边金币
        _minGoldNum: null, //右边金币
        _bigMyGoldNum: null, //左边金币
        _minMyGoldNum: null, //右边金币
        _isForceAdapt: null, //是否强制适配

        node_big: cc.Node,
        node_min: cc.Node,
        frame_bigLight: cc.SpriteFrame,
        frame_minLight: cc.SpriteFrame,
        //显示金币的上下label
        node_bigUpLabel: {
            'default': null,
            type: cc.Node,
            displayName: '押大总额节点'
        },
        node_bigDownLabel: {
            'default': null,
            type: cc.Node,
            displayName: '自己押大节点'
        },
        node_minUpLabel: {
            'default': null,
            type: cc.Node,
            displayName: '押小总额节点'
        },
        node_minDownLabel: {
            'default': null,
            type: cc.Node,
            displayName: '自己押小节点'
        },
        node_winFrame: {
            'default': null,
            type: cc.Node,
            displayName: '胜利特效框'
        },
        label_leftUp: {
            'default': null,
            type: cc.Label,
            displayName: '所有玩家押大金币总额'
        },
        label_rightUp: {
            'default': null,
            type: cc.Label,
            displayName: '所有玩家押小金币总额'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._scrollCompName = 'Obj_scrollValue';

        //卡牌X位置偏移
        this._pokerOffX = 0.4;
        this._winColor = this.node_bigDownLabel.children[1].color;
        this._lostColor = new cc.Color(189, 188, 188);
        this._isForceAdapt = true;
    },

    //初始化赌桌
    initTable: function initTable(_index) {
        this._setTableIndex(_index);
        this._setBigMultiple(0);
        this._setMinMultiple(0);

        this._setBigDownGold(0);
        this._setBigUpGold(0);
        this._setMinDownGold(0);
        this._setMinUpGold(0);
        this.resetWinShow();
    },

    onClick_big: function onClick_big() {
        var mgr = GG.bull100Mgr;
        var goldNum = mgr.getMyselfFlyGoldNum();
        mgr.touchTable(this._bigTableIndex, this._getBigGoldPos(goldNum));
    },
    onClick_min: function onClick_min() {
        var mgr = GG.bull100Mgr;
        var goldNum = GG.bull100Mgr.getMyselfFlyGoldNum();
        GG.bull100Mgr.touchTable(this._minTableIndex, this._getMinGoldPos(goldNum));
    },

    //投注成功后显示该桌子的投注信息
    setBettingSuccess: function setBettingSuccess(tableIndex, goldValue) {
        if (tableIndex % 2 == 0) {
            //所有玩家押大总额显示
            this._bigGoldNum += goldValue;
            this._setBigUpGold(this._bigGoldNum);
        } else {
            //所有玩家押小总额显示
            this._minGoldNum += goldValue;
            this._setMinUpGold(this._minGoldNum);
        }
    },

    //自己投注成功后显示该桌子的投注信息
    setMyselfBettingSuccess: function setMyselfBettingSuccess(tableIndex, goldValue) {
        if (tableIndex % 2 == 0) {
            //自己押大
            this._bigMyGoldNum += goldValue;
            this._setBigDownGold(this._bigMyGoldNum);
        } else {
            //自己押小
            this._minMyGoldNum += goldValue;
            this._setMinDownGold(this._minMyGoldNum);
        }
        this.setBettingSuccess(tableIndex, goldValue);
    },

    //显示该桌子上金币的胜利
    showWin: function showWin(tableIndex, multiple) {
        //区域倍数
        var bettingLabel = null,
            curGoldValue = null;
        if (!multiple) multiple = 1;
        if (!this._firstBigImg) this._firstBigImg = this.node_big.getComponent(cc.Sprite).spriteFrame;
        if (!this._firstMinImg) this._firstMinImg = this.node_min.getComponent(cc.Sprite).spriteFrame;
        this.node_winFrame.active = true;
        this.node_winFrame.width = this.node_big.parent.width * 1.15;

        if (tableIndex % 2 == 0) {
            //大
            this._winTableType = G_TYPE.tableType.big;
            this.node_big.getComponent(cc.Sprite).spriteFrame = this.frame_bigLight;
            this.node_winFrame.x = this.node_big.parent.x;
            if (this._bigMyGoldNum > 0) {
                this._setBigMultiple(multiple);
                bettingLabel = this.node_bigDownLabel.children[1].getComponent(cc.Label);
                curGoldValue = this._bigMyGoldNum;
            }
            //lost
            if (this._minMyGoldNum > 0) {
                this._setMinMultiple(multiple);
                this._setMinDownGold(this._minMyGoldNum * multiple);
                this.node_minDownLabel.children[0].color = this._lostColor;
                this.node_minDownLabel.children[1].color = this._lostColor;
            }
        } else {
            //小
            this._winTableType = G_TYPE.tableType.min;
            this.node_min.getComponent(cc.Sprite).spriteFrame = this.frame_minLight;
            this.node_winFrame.x = this.node_min.parent.x;
            if (this._minMyGoldNum > 0) {
                this._setMinMultiple(multiple);
                bettingLabel = this.node_minDownLabel.children[1].getComponent(cc.Label);
                curGoldValue = this._minMyGoldNum;
            }

            //lost
            if (this._bigMyGoldNum > 0) {
                this._setBigMultiple(multiple);
                this._setBigDownGold(this._bigMyGoldNum * multiple);
                this.node_bigDownLabel.children[0].color = this._lostColor;
                this.node_bigDownLabel.children[1].color = this._lostColor;
            }
        }

        if (bettingLabel) {
            //胜利区域有投注
            var dataObj = {
                startNum: 0,
                targetNum: curGoldValue * multiple,
                label: bettingLabel,
                formatStr: '',
                callFunc: this._scrollCall.bind(this)
            };
            this.node.getComponent(this._scrollCompName).scrollLabel(dataObj);
        }
    },
    //胜利滚动结束
    _scrollCall: function _scrollCall() {},
    //清理胜利的显示
    resetWinShow: function resetWinShow() {
        this.node_winFrame.active = false;
        switch (this._winTableType) {
            case G_TYPE.tableType.big:
                this.node_big.getComponent(cc.Sprite).spriteFrame = this._firstBigImg;
                this.node_minDownLabel.children[0].color = this._winColor;
                this.node_minDownLabel.children[1].color = this._winColor;
                break;
            case G_TYPE.tableType.min:
                this.node_min.getComponent(cc.Sprite).spriteFrame = this._firstMinImg;
                this.node_bigDownLabel.children[0].color = this._winColor;
                this.node_bigDownLabel.children[1].color = this._winColor;
                break;
            default:
                break;
        }
    },

    //====================================================

    setTouchEnable: function setTouchEnable(isEnable) {
        var bigParent = this.node_big.parent;
        var minParent = this.node_min.parent;
        if (isEnable) {
            bigParent.on(cc.Node.EventType.TOUCH_START, this.onClick_big, this);
            minParent.on(cc.Node.EventType.TOUCH_START, this.onClick_min, this);
        } else {
            bigParent.off(cc.Node.EventType.TOUCH_START, this.onClick_big, this);
            minParent.off(cc.Node.EventType.TOUCH_START, this.onClick_min, this);
        }
    },

    //获取随机的金币位置
    getGoldPosList: function getGoldPosList(tableIndex, goldNum) {
        var posList;
        if (tableIndex % 2 == 0) {
            //大
            posList = this._getBigGoldPos(goldNum);
        } else posList = this._getMinGoldPos(goldNum);
        return posList;
    },
    _getBigGoldPos: function _getBigGoldPos(num) {
        if (!num) num = 1;
        var goldPos,
            posList = [];
        for (var i = 0; i < num; i++) {
            goldPos = this._initGoldPos();
            goldPos.x += this._bigCenterPos.x;
            goldPos.y += this._bigCenterPos.y;
            posList.push(goldPos);
        }
        return posList;
    },
    _getMinGoldPos: function _getMinGoldPos(num) {
        if (!num) num = 1;
        var goldPos,
            posList = [];
        for (var i = 0; i < num; i++) {
            goldPos = this._initGoldPos();
            goldPos.x += this._minCenterPos.x;
            goldPos.y += this._minCenterPos.y;
            posList.push(goldPos);
        }
        return posList;
    },
    _initGoldPos: function _initGoldPos() {
        var parentScale = this.node.parent.scale;
        var bigBG = this.node_big.parent;
        if (!this._bigCenterPos) {
            var parentPos = this.node.parent.position;
            var minBG = this.node_min.parent;
            this._bigCenterPos = cc.p(parentPos.x + (this.node.x + bigBG.x) * parentScale, parentPos.y + (this.node.y + bigBG.y) * parentScale);
            this._minCenterPos = cc.p(parentPos.x + (this.node.x + minBG.x) * parentScale, parentPos.y + (this.node.y + minBG.y) * parentScale);
            this._goldHeight = (bigBG.height * 0.5 - this.node_bigDownLabel.height) * parentScale;
        }

        //假设金币的宽度为该值
        var goldWidth2 = this._getGoldSize().width * 0.5 * 0.5;
        var dir;

        var maxX = bigBG.width * 0.5 * parentScale - goldWidth2;
        var goldX = G_TOOL.getRandomArea(0, maxX);
        dir = G_TOOL.getRandomBool() ? 1 : -1;
        goldX *= dir;

        var maxY = this._goldHeight - goldWidth2;
        var goldY = G_TOOL.getRandomArea(0, maxY);
        dir = G_TOOL.getRandomBool() ? 1 : -1;
        goldY *= dir;
        return cc.p(goldX, goldY);
    },

    //----------------------------

    //所有玩家押大总额
    _setBigUpGold: function _setBigUpGold(goldNum) {
        if (!goldNum) {
            this._bigGoldNum = 0;
            this.node_bigUpLabel.active = false;
            this.label_leftUp.string = '';
        } else {
            this.node_bigUpLabel.active = true;
            var labelNode = this.label_leftUp;
            if (labelNode) labelNode.string = G_TOOL.changeMoney(goldNum);
        }
    },
    //所有玩家押小总额
    _setMinUpGold: function _setMinUpGold(goldNum) {
        if (!goldNum) {
            this._minGoldNum = 0;
            this.node_minUpLabel.active = false;
            this.label_rightUp.string = '';
        } else {
            this.node_minUpLabel.active = true;
            var labelNode = this.label_rightUp;
            if (labelNode) labelNode.string = G_TOOL.changeMoney(goldNum);
        }
    },

    //自己押注大显示
    _setBigDownGold: function _setBigDownGold(goldNum) {
        if (!goldNum) {
            this._bigMyGoldNum = 0;
            this.node_bigDownLabel.active = false;
        } else {
            this.node_bigDownLabel.active = true;
            var labelNode = this.node_bigDownLabel.children[1];
            if (labelNode) labelNode.getComponent(cc.Label).string = G_TOOL.changeMoney(goldNum);
        }
    },
    //设置押大的时候倍数的位置
    _setBigMultiple: function _setBigMultiple(multiple) {
        var labelNode = this.node_bigDownLabel.children[0];
        if (labelNode) {
            var str = '',
                rightNode = this.node_bigDownLabel.children[1];
            if (multiple) {
                str = "X" + multiple;
                this._changeDownLabel(rightNode, true);
            } else {
                this._changeDownLabel(rightNode, false);
            }
            labelNode.getComponent(cc.Label).string = str;
        }
    },

    //----------------------------

    //自己押注小显示
    _setMinDownGold: function _setMinDownGold(goldNum) {
        if (!goldNum) {
            this._minMyGoldNum = 0;
            this.node_minDownLabel.active = false;
        } else {
            this.node_minDownLabel.active = true;
            var labelNode = this.node_minDownLabel.children[1];
            if (labelNode) labelNode.getComponent(cc.Label).string = G_TOOL.changeMoney(goldNum);
        }
    },
    //设置押小的时候倍数的位置
    _setMinMultiple: function _setMinMultiple(multiple) {
        var labelNode = this.node_minDownLabel.children[0];
        if (labelNode) {
            var str = '',
                rightNode = this.node_minDownLabel.children[1];
            if (multiple) {
                str = "X" + multiple;
                this._changeDownLabel(rightNode, true);
            } else {
                this._changeDownLabel(rightNode, false);
            }
            labelNode.getComponent(cc.Label).string = str;
        }
    },

    //设置底部label随着倍数是否显示而变化位置
    _changeDownLabel: function _changeDownLabel(curLabel, isRight) {
        if (isRight) {
            curLabel.anchorX = 1;
            curLabel.x = curLabel.parent.width * 0.43;
        } else {
            curLabel.anchorX = 0.5;
            curLabel.x = 0;
        }
    },
    //设置索引信息
    _setTableIndex: function _setTableIndex(index) {
        this._bigTableIndex = GG.bull100Mgr.getBigTableIndex(index);
        this._minTableIndex = GG.bull100Mgr.getMinTableIndex(index);
    },

    //获取桌子底下卡牌的位置
    getPokerPos: function getPokerPos() {
        //强制适配
        if (this._isForceAdapt) {
            this._isForceAdapt = false;
            var widget = this.getComponent(cc.Widget);
            if (widget) {
                widget.updateAlignment();
            }
        }

        var parentPos = this.node.parent.position;
        var scale = this.node.parent.scale;
        var posX = parentPos.x + this.node.x * scale - this.node.width * 0.5 * scale * this._pokerOffX;
        var posY = parentPos.y + this.node.y * scale - this.node.height * 0.5 * scale;
        return cc.p(posX, posY);
    },
    //获取父节点的坐标
    _getParentPos: function _getParentPos() {
        var parentPos = this.node.parent.position;
        var scale = this.node.parent.scale;
        return cc.p(parentPos.x * scale, parentPos.y * scale);
    },
    //获取金币的宽高
    _getGoldSize: function _getGoldSize() {
        if (!this._goldSize) {
            this._goldSize = GG.bull100Mgr.getGoldImgSize();
        }
        return this._goldSize;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Bull100_topEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6fe37k7FJBF1p4TXNOChhYN', 'Bull100_topEffect');
// Script\Views\Scene_Bull100\Effect\Bull100_topEffect.js

//放在游戏最上面的特效

cc.Class({
    'extends': cc.Component,

    properties: {
        _bettingCallFunc: null, //开始投注动画的回调
        _startCallFunc: null, //开始游戏动画的回调
        _resultData: null, //结算界面中的数据

        prefab_start: {
            'default': null,
            type: cc.Prefab,
            displayName: '开始游戏动画'
        },
        prefab_betting: {
            'default': null,
            type: cc.Prefab,
            displayName: '开始投注动画'
        },
        prefab_win: {
            'default': null,
            type: cc.Prefab,
            displayName: '胜利动画'
        },
        prefab_lose: {
            'default': null,
            type: cc.Prefab,
            displayName: '失败动画'
        },
        prefab_showResult: {
            'default': null,
            type: cc.Prefab,
            displayName: '结果显示容器'
        },
        node_grayLayer: {
            'default': null,
            type: cc.Node,
            displayName: '灰色层'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._winAniName = 'platform_win';
        this._loseAniName = 'platform_lose';
        this._resultCompName = 'Bull100_showResult';
        this._pokerResultCompName = 'Obj_onPokerResult';
    },

    //开始游戏
    playStartGameAni: function playStartGameAni(callFunc) {
        this.playStartAni('bull_start', callFunc);
        GG.audioMgr.playSound(13);
    },
    //播放开始动画
    playStartAni: function playStartAni(aniName, callFunc) {
        if (!aniName) aniName = '';
        this._startCallFunc = callFunc;
        if (!this._startAni) {
            this._startAni = cc.instantiate(this.prefab_start);
            this._startAni.parent = this.node;
            //ani.scale = G_Config_common.frameScale;
            var comp = this._startAni.getComponent(dragonBones.ArmatureDisplay);
            //comp.addEventListener(dragonBones.EventObject.FRAME_EVENT, this._frameEvent, this);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        }
        this._startAni.position = cc.visibleRect.center;
        this._startAni.active = true;
        this._startAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _startAniEnd: function _startAniEnd(event) {
        //动画结束
        if (this._startAni) this._startAni.active = false;
        if (this._startCallFunc) {
            this._startCallFunc();
            this._startCallFunc = null;
        }
    },
    _frameEvent: function _frameEvent(event) {
        GG.bull100Mgr.shakeGame();
    },
    //清理开始动画
    clearStartAni: function clearStartAni() {
        if (this._startAni) this._startAni.active = false;
        this._startCallFunc = null;
    },

    //============================================

    //开始投注
    playStartBettingAni: function playStartBettingAni(callFunc) {
        this.playBettingAni('platform_bet', callFunc);
    },
    //投注结束
    playBettingEnd: function playBettingEnd(callFunc) {
        this.playBettingAni('platform_betOver', callFunc);
    },
    //播放开始投注动画
    playBettingAni: function playBettingAni(aniName, callFunc) {
        if (!aniName) aniName = '';
        this._bettingCallFunc = callFunc;
        if (!this._bettingAni) {
            this._bettingAni = cc.instantiate(this.prefab_betting);
            this._bettingAni.parent = this.node;
            //ani.scale = G_Config_common.frameScale;
            var comp = this._bettingAni.getComponent(dragonBones.ArmatureDisplay);
            //comp.addEventListener(dragonBones.EventObject.FRAME_EVENT, this._frameEvent, this);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._bettingAniEnd, this);
        }
        var pos = G_DATA.getTopTipPos();
        this._bettingAni.position = cc.p(pos.x, pos.y - 150);
        this._bettingAni.active = true;
        this._bettingAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _bettingAniEnd: function _bettingAniEnd(event) {
        //动画结束
        if (this._bettingAni) this._bettingAni.active = false;
        if (this._bettingCallFunc) {
            this._bettingCallFunc();
            this._bettingCallFunc = null;
        }
    },
    //清理开始投注动画
    clearBettingAni: function clearBettingAni() {
        if (this._bettingAni) {
            this._bettingAni.active = false;
        }
        this._bettingCallFunc = null;
    },

    //================================胜利动画

    //显示胜利或者失败
    showResultEffect: function showResultEffect(data, callFunc) {
        if (!isNaN(data.myChangeGold)) {
            this.setGrayIsShow(true);
            if (data.myChangeGold > 0) {
                this._playWin(data, callFunc);
            } else {
                this._playLose(data, callFunc);
            }
        } else {
            if (callFunc) {
                callFunc();
                callFunc = null;
            }
        }
    },

    _playWin: function _playWin(data, callFunc) {
        this._resultData = data;
        this._winCallFunc = callFunc;
        if (!this._winAni) {
            this._winAni = cc.instantiate(this.prefab_win);
            this._winAni.parent = this.node;
            var comp = this._winAni.getComponent(dragonBones.ArmatureDisplay);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._winEnd, this);
        }
        var pos = cc.visibleRect.center;
        this._winAni.position = cc.p(pos.x, pos.y * 1.45);
        this._winAni.active = true;
        this._winAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(this._winAniName, 1);
        GG.audioMgr.playSound(22);
    },
    _winEnd: function _winEnd() {
        this._getResultComp().showResult(this._resultData);
        this._winAni.runAction(cc.sequence(cc.delayTime(2), cc.callFunc(function () {
            this._winAni.active = false;
            this._getResultComp()._setIsShow(false);
            this.setGrayIsShow(false);
            if (this._winCallFunc) {
                this._winCallFunc();
                this._winCallFunc = null;
            }
        }, this)));
    },

    _clearWin: function _clearWin() {
        if (this._winAni) {
            this._getResultComp()._setIsShow(false);
            this._winAni.stopAllActions();
            this._winAni.active = false;
            this._winCallFunc = null;
        }
    },

    //======================================失败动画

    _playLose: function _playLose(data, callFunc) {
        this._resultData = data;
        this._loseCallFunc = callFunc;
        if (!this._loseAni) {
            this._loseAni = cc.instantiate(this.prefab_lose);
            this._loseAni.parent = this.node;
            var comp = this._loseAni.getComponent(dragonBones.ArmatureDisplay);
            comp.addEventListener(dragonBones.EventObject.COMPLETE, this._loseEnd, this);
        }
        var pos = cc.visibleRect.center;
        this._loseAni.position = cc.p(pos.x, pos.y * 1.45);
        this._loseAni.active = true;
        this._loseAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(this._loseAniName, 1);
        GG.audioMgr.playSound(21);
    },
    _loseEnd: function _loseEnd() {
        this._getResultComp().showResult(this._resultData);

        this._loseAni.runAction(cc.sequence(cc.delayTime(2), cc.callFunc(function () {
            this._loseAni.active = false;
            this._getResultComp()._setIsShow(false);
            this.setGrayIsShow(false);
            if (this._loseCallFunc) {
                this._loseCallFunc();
                this._loseCallFunc = null;
            }
        }, this)));
    },

    _getResultComp: function _getResultComp() {
        if (!this._resultComp) {
            var result = cc.instantiate(this.prefab_showResult);
            this.node.addChild(result, 5);
            if (this._winAni) result.position = this._winAni.position;
            if (this._loseAni) result.position = this._loseAni.position;
            this._resultComp = result.getComponent(this._resultCompName);
        }
        return this._resultComp;
    },
    _clearLose: function _clearLose() {
        if (this._loseAni) {
            this._getResultComp()._setIsShow(false);
            this._loseAni.stopAllActions();
            this._loseAni.active = false;
            this._loseCallFunc = null;
        }
    },

    //====================================灰色层

    setGrayIsShow: function setGrayIsShow(isShow) {
        if (this.node_grayLayer) this.node_grayLayer.active = isShow;
    },

    getGrayLayer: function getGrayLayer() {
        return this.node_grayLayer;
    },

    //清理所有
    clearAll: function clearAll() {
        this.clearBettingAni();
        this.clearStartAni();
        this._clearWin();
        this._clearLose();
        this.setGrayIsShow(false);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Button_multiple":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8c63aYww1pKUbxfhi3c3Geu', 'Button_multiple');
// Script\Common\DIYNodes\Button_multiple.js

cc.Class({
    'extends': cc.Component,
    _valueOffX: null, //倍数大于10，显示容器的偏移量
    properties: {
        font_red: cc.Font, //红色字体
        font_green: cc.Font, //绿色字体
        frame_disable: cc.SpriteFrame, //不可用的时候使用的图片

        _firstFrame: null,
        _lastValue: null
    },

    // use this for initialization
    onLoad: function onLoad() {},

    //显示倍数，调整label的对齐
    showValue: function showValue(value, isRed) {
        if (!this._initContainer()) return;
        if (isRed) this._showRed();else this._showGreen();

        if (this._isValidValue(value)) {
            if (this.node_leftLabel) {
                this.node_leftLabel.getComponent(cc.Label).string = value;
            }
            var len = value.toString().split('').length;
            if (len > 1) {
                this.node_leftLabel.scaleX = 0.8;
                this._container.x = this._valueOffX * (len - 1);
            } else {
                this.node_leftLabel.scaleX = 1;
                this._container.x = 0;
            }
        }
    },
    //显示金钱
    showMoney: function showMoney(value, isRed) {
        if (!this._initContainer()) return;
        if (isRed) this._showRed();else this._showGreen();

        if (this._isValidValue(value)) {
            var len = (value + '').length;
            var curLen;
            for (var key in G_Config_common.dict_moneyUnit) {
                curLen = key.toString().length;
                break;
            }
            if (len >= curLen) {
                var newValue = G_TOOL.changeMoney(value);
                len = (newValue + '').length;
                value = newValue.substring(0, len - 1);
                this._rightLabel.string = newValue[len - 1];
                len -= 1;
            } else {
                len += 2;
                this._rightLabel.string = '';
            }

            if (this.node_leftLabel) {
                this.node_leftLabel.getComponent(cc.Label).string = value;
            }

            this.node_leftLabel.scaleX = 0.8;
            this._container.x = this._valueOffX * (len - 1);
        }
    },
    _initContainer: function _initContainer() {
        if (!this._container) {
            this._container = this.node.getChildByName('container');
            if (!this._container) return false;
            this._firstFrame = this.getComponent(cc.Sprite).spriteFrame;
            this._valueOffX = G_Config_common.num_multipleValueOff;
            this.node_leftLabel = this._container.getChildByName('left');
            this._rightLabel = this._container.getChildByName('right').getComponent(cc.Label);
        }
        return true;
    },
    _isValidValue: function _isValidValue(value) {
        if (!value) return false;
        if (value == this._lastValue) return false;
        this._lastValue = value;
        return true;
    },

    //替换红色资源
    _showRed: function _showRed() {
        this.node_leftLabel.getComponent(cc.Label).font = this.font_red;
        this._rightLabel.font = this.font_red;

        this._showDisable(true);
    },

    //替换绿色资源
    _showGreen: function _showGreen() {
        this.node_leftLabel.getComponent(cc.Label).font = this.font_green;
        this._rightLabel.font = this.font_green;

        this._showDisable(false);
    },

    _showDisable: function _showDisable(isDisable) {
        if (isDisable) {
            this.getComponent(cc.Sprite).spriteFrame = this.frame_disable;
        } else {
            this.getComponent(cc.Sprite).spriteFrame = this._firstFrame;
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Classic_Manager":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6126cVc/b9Ker4kVTuRmBTf', 'Classic_Manager');
// Script\Views\Scene_classic\Classic_Manager.js

//经典对战模式对战管理

cc.Class({
    'extends': cc.Component,
    _dict_playerComp: null, //所有玩家
    _list_onDeskPlayerComp: null, //已经上桌的玩家
    _curPlayerNum: null, //当前玩家的数量
    _busyPlayerNum: null, //正在操作中的玩家数量
    _dealerComp: null, //庄家
    _flyCallBack: null, //移动卡牌的回调
    _myselfPlayerComp: null, //玩家自己
    _dealerLogMoveStep: null, //随机庄家时候移动的步数
    _isInTurn: null, //是否开始轮流机制
    _callFunc_inTurn: null, //轮流机制中调用的函数
    _callFunc_inTurnEnd: null, //轮流机制结束时候调用的函数
    _callFunc_flyGoldEnd: null, //金币飞行结束调用的函数
    properties: {
        _gameState: null, //游戏运行状态
        _tip_inTurn: null, //一次显示的提示
        node_givePokerArea: {
            'default': null,
            type: cc.Node,
            displayName: '卡牌容器'
        },
        node_flyGoldArea: {
            'default': null,
            type: cc.Node,
            displayName: '金币容器'
        },
        node_controlGroup: {
            'default': null,
            type: cc.Node,
            displayName: '底部按钮容器'
        },
        node_topUI: cc.Node,
        node_pageResult: {
            'default': null,
            type: cc.Node,
            displayName: '结算显示界面'
        },
        node_player1: cc.Node,
        node_player2: cc.Node,
        node_player3: cc.Node,
        node_player4: cc.Node,
        node_player5: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {
        GG.classicMgr = this;
    },

    _initAttributes: function _initAttributes() {
        if (!this._dict_playerComp) {
            this._pokerAreaComp = this.node_givePokerArea.getComponent('Classic_pokerGiveArea');
            this._goldAreaComp = this.node_flyGoldArea.getComponent('Classic_goldArea');
            this._controlGroupComp = this.node_controlGroup.getComponent('Classic_controlButton');
            this._topUIComp = this.node_topUI.getComponent('Classic_topUI');
            this._playerCompName = 'Classic_playerBlock';
            this._pageResultCompName = 'Classic_page_result';

            this._dict_playerComp = {};
            var comp;
            comp = this.node_player5.getComponent(this._playerCompName);
            this._dict_playerComp[comp.getPlayerIndex()] = comp;
            if (comp.getIsMySelf()) this._myselfPlayerComp = comp;
            comp = this.node_player4.getComponent(this._playerCompName);
            this._dict_playerComp[comp.getPlayerIndex()] = comp;
            if (comp.getIsMySelf()) this._myselfPlayerComp = comp;
            comp = this.node_player3.getComponent(this._playerCompName);
            this._dict_playerComp[comp.getPlayerIndex()] = comp;
            if (comp.getIsMySelf()) this._myselfPlayerComp = comp;
            comp = this.node_player2.getComponent(this._playerCompName);
            this._dict_playerComp[comp.getPlayerIndex()] = comp;
            if (comp.getIsMySelf()) this._myselfPlayerComp = comp;
            comp = this.node_player1.getComponent(this._playerCompName);
            this._dict_playerComp[comp.getPlayerIndex()] = comp;
            if (comp.getIsMySelf()) this._myselfPlayerComp = comp;
        }
        this._resetData();
    },

    //初始本局数据
    _resetData: function _resetData(playerNum) {
        this._busyPlayerNum = 0;
        this._isInTurn = false;
        this._dealerComp = null;
        this._list_onDeskPlayerComp = [];
        this._curPlayerNum = 0;
        this._setOnDeskPlayers(playerNum);
    },

    //上桌玩家
    _setOnDeskPlayers: function _setOnDeskPlayers(playerNum) {
        if (!playerNum) playerNum = 5;
        for (var i = 1; i <= G_Config_classic.num_maxPlayer; i++) {
            var playerComp = this._dict_playerComp[i];
            if (i <= playerNum) this._addOnePlayer(playerComp);else playerComp.hideBlock();
        }
    },

    //刷新游戏开局信息，并重置所有的效果
    resetGame: function resetGame() {
        this._pokerAreaComp.onClear();
        this._goldAreaComp.onClear();
        this._topUIComp.onClear();
        this._controlGroupComp.onClear();
        //this._resetData();
    },

    hideAni: function hideAni() {
        this._topUIComp.hideAni();
    },

    //===================================游戏流程,依次进行

    //开场前的等待
    showStartWait: function showStartWait() {
        this._initAttributes();
        this._controlGroupComp.setGroup1Enable(false);

        this.setGameState(G_TYPE.classicState.wait);
        var tipData = G_DATA.getChinese(1);
        if (tipData) {
            var dataObj = {
                showStr: tipData.content,
                retainTime: tipData.retainTime,
                callBack: this.startGame.bind(this)
            };
            GG.tipsMgr.showCountDownTip(dataObj);
        }
    },

    //开始本局游戏
    startGame: function startGame() {
        this.setGameState(G_TYPE.classicState.readyStart);
        this._topUIComp.playStartAni(this._onGivePoker.bind(this));
    },
    _onGivePoker: function _onGivePoker() {
        //开始发牌
        this.setGameState(G_TYPE.classicState.givePoker);
        this._flyCallBack = this._onGivePokersEnd.bind(this);
        this._busyPlayerNum = this._curPlayerNum;
        this._pokerAreaComp.startGivePoker();
    },

    //发牌结束
    _onGivePokersEnd: function _onGivePokersEnd() {
        //开始抢庄
        this.setGameState(G_TYPE.classicState.grabDealer);
        var tipData = G_DATA.getChinese(2);
        if (!tipData) return;
        var dataObj = {
            showStr: tipData.content,
            retainTime: tipData.retainTime,
            callBack: this._beDealerTimeEnd.bind(this)
        };
        this._tip_inTurn = GG.tipsMgr.showCountDownTip(dataObj);
        this._controlGroupComp.showGroup1();
    },

    //抢庄时间结束
    _beDealerTimeEnd: function _beDealerTimeEnd() {
        this._tip_inTurn = null;
        this._controlGroupComp.setGroup1Enable(false, true);

        if (this._dealerComp) {
            //庄家已经确定
            this._dealerComp.confirmDealer(true);
            //选择下分倍数
            this._startSetScore();
        } else {
            //没有人抢庄
            this._dealerComp = this._list_onDeskPlayerComp[1];
            this._showGrabDealerEffect();
        }
    },

    //开始下分
    _startSetScore: function _startSetScore() {
        //确定庄家表现,并进入押注下分时间
        this.setGameState(G_TYPE.classicState.setScore);
        var dataKey = 3;
        if (this._myselfPlayerComp.getIsDealer()) dataKey = 4;
        var tipData = G_DATA.getChinese(dataKey);
        if (!tipData) return;
        var dataObj = {
            showStr: tipData.content,
            retainTime: tipData.retainTime,
            callBack: this._setScoreRateTimeEnd.bind(this)
        };
        this._tip_inTurn = GG.tipsMgr.showCountDownTip(dataObj);
        this.allPlayerDone('grabDealer');
        this._controlGroupComp.showGroup2(this._myselfPlayerComp.getIsDealer());
    },

    //选择下分倍数倒计时结束
    _setScoreRateTimeEnd: function _setScoreRateTimeEnd() {
        //下分默认处理
        this._tip_inTurn = null;
        this._checkScore();
        this._controlGroupComp.setGroup2Enable(false, true);
        //发最后一张牌
        this.setGameState(G_TYPE.classicState.givePoker);
        this._busyPlayerNum = this._curPlayerNum;
        this._flyCallBack = this._onLastPokerGive.bind(this);
        this._pokerAreaComp.giveOneMorePoker();
    },

    //最后一张牌发牌结束
    _onLastPokerGive: function _onLastPokerGive() {
        this._myselfPlayerComp.showPokerCalculate();
        this._myselfPlayerComp.setPokerTouchEnable(true);
        this._controlGroupComp.setGroup3TouchEnable(true);
        //进入算牌时间
        this.setGameState(G_TYPE.classicState.calculate);
        GG.tipsMgr.showProgressCountDown(9, this.calculateEnd.bind(this));
    },

    //算牌时间结束
    calculateEnd: function calculateEnd() {
        //显示牌型
        this.setGameState(G_TYPE.classicState.showResult);
        this._copy_players = this._list_onDeskPlayerComp.concat([]);
        this.inTurn(this._curPlayerNum, G_Config_classic.time_pokerResultInterval, true, this._calculateEnd_callBack.bind(this), this._startFlyGold.bind(this));
    },
    //显示每个玩家的牌型
    _calculateEnd_callBack: function _calculateEnd_callBack() {
        var player = this._copy_players[0];
        this._copy_players.splice(0, 1);
        if (player) player.endCalculate();
    },

    //开始金币的飞行特效
    _startFlyGold: function _startFlyGold() {
        var nodeComp1 = this._dict_playerComp[1];
        var nodeComp2 = this._dict_playerComp[2];
        var nodeComp3 = this._dict_playerComp[3];
        var nodeComp4 = this._dict_playerComp[4];
        var nodeComp5 = this._dict_playerComp[5];
        this._list_goldFly1 = [];
        this._list_goldFly1.push([nodeComp2, nodeComp1]);
        this._list_goldFly1.push([nodeComp3, nodeComp1]);
        this._list_goldFly2 = [];
        this._list_goldFly2.push([nodeComp1, nodeComp4]);
        this._list_goldFly2.push([nodeComp1, nodeComp5]);

        this._goldFly1();
    },

    //金币飞行结束
    _flyGoldEnd: function _flyGoldEnd() {
        this.allPlayerDone('showGoldValueFly', 9654122);

        var self = this;
        this.node.runAction(cc.sequence(cc.delayTime(1.5), cc.callFunc(function () {
            self.node_pageResult.getComponent(this._pageResultCompName).showWin();
        }, this)));
    },

    //====================================

    //自己抢庄
    grabDealerMyself: function grabDealerMyself(grabNum) {
        this._myselfPlayerComp.grabDealer(grabNum);
        if (grabNum > 0) {
            this._dealerComp = this._myselfPlayerComp;
            if (this._tip_inTurn) this._tip_inTurn.forceEnd();
            this._beDealerTimeEnd();
        }
    },
    //自己下分
    setScoreMyself: function setScoreMyself(chooseNum) {
        this._myselfPlayerComp.showSetScore(chooseNum);
        if (this._gameState == G_TYPE.classicState.setScore) {
            var data = G_DATA.getChinese(4);
            if (this._tip_inTurn) this._tip_inTurn.forceChangeStr(data.content);
        }
    },
    //自己确定有牛没牛
    clickConfirmResult: function clickConfirmResult() {
        this._myselfPlayerComp.confirmResult();
    },
    confirmResult: function confirmResult() {
        this._controlGroupComp.setGroup3TouchEnable(false);
        if (this._gameState == G_TYPE.classicState.calculate) {
            var data = G_DATA.getChinese(6);
            var dataObj = {
                showStr: data.content,
                retainTime: GG.tipsMgr.getProgressLeaveTime(),
                callBack: this.calculateEnd.bind(this)
            };
            GG.tipsMgr.forceProgressEnd();
            GG.tipsMgr.showCountDownTip(dataObj);
        }
    },

    //确定庄家
    confirmDealer: function confirmDealer(playerComp) {
        this._dealerComp = this._myselfPlayerComp;
        if (this._tip_inTurn) this._tip_inTurn.forceEnd();
    },

    //设置游戏的状态
    setGameState: function setGameState(type) {
        var lastType = this._gameState;
        this._gameState = type;
        switch (type) {
            case G_TYPE.classicState.wait:
                //等待开始
                this._topUIComp.setChangeTouchEnable(true);
                break;
            case G_TYPE.classicState.readyStart:
                //开始动画
                this._topUIComp.setChangeTouchEnable(false);
                break;
            case G_TYPE.classicState.givePoker:
                //发牌
                break;
            case G_TYPE.classicState.grabDealer:
                //抢庄
                break;
            case G_TYPE.classicState.setScore:
                //下注
                break;
            case G_TYPE.classicState.calculate:
                //计算结果
                break;
            case G_TYPE.classicState.showResult:
                //显示结果
                break;
            default:
                this._gameState = lastType;
                return;
        }
    },

    //金币飞行特效步骤1
    _goldFly1: function _goldFly1() {
        this._callFunc_flyGoldEnd = this.goldFly2.bind(this);
        for (var key in this._list_goldFly1) {
            this._addGoldFlyEvent(this._list_goldFly1[key][0], this._list_goldFly1[key][1]);
        }
    },
    //金币飞行特效步骤2
    goldFly2: function goldFly2() {
        this._callFunc_flyGoldEnd = this._flyGoldEnd.bind(this);
        if (this._list_goldFly2.length > 0) {
            for (var key in this._list_goldFly2) {
                this._addGoldFlyEvent(this._list_goldFly2[key][0], this._list_goldFly2[key][1]);
            }
        } else {
            this._flyGoldEnd();
        }
    },
    //金币从fromNodeComp 飞到 toNodeComp
    _addGoldFlyEvent: function _addGoldFlyEvent(fromNodeComp, toNodeComp) {
        toNodeComp.startReceive();
        this._goldAreaComp.flyGolds(fromNodeComp.getNodePos(), toNodeComp.getNodePos(), toNodeComp.getPlayerIndex());
    },
    //金币到达玩家头像的回调
    whenGoldMoveToHead: function whenGoldMoveToHead(playerIndex) {
        this.getPlayerComp(playerIndex).showGetGoldEffect();
    },
    //金币特效结束; 两次的金币特效结束都会进入
    onGoldEffectEnd: function onGoldEffectEnd(fromPos, toPlayerIndex) {
        if (this._callFunc_flyGoldEnd) this._callFunc_flyGoldEnd();
    },

    //下分处理
    _checkScore: function _checkScore() {
        var playerComp;
        for (var key in this._list_onDeskPlayerComp) {
            playerComp = this._list_onDeskPlayerComp[key];
            if (playerComp.getIsSetScore()) {
                if (playerComp.getIsMySelf()) this._controlGroupComp.setGroup2Enable(false);
                if (!playerComp.getIsDealer()) playerComp.setDefaultScore();
            } else playerComp.setDefaultScore();
        }
    },

    //加入一个玩家
    _addOnePlayer: function _addOnePlayer(playerComp) {
        playerComp.setPlayerData();
        this._list_onDeskPlayerComp.push(playerComp);
        this._curPlayerNum += 1;
    },

    //一个玩家离开
    removeOnePlayer: function removeOnePlayer(playerBlock) {
        //从上桌列表中移除
        for (var key in this._list_onDeskPlayerComp) {
            if (this._list_onDeskPlayerComp[key].getPlayerIndex() == playerBlock.getPlayerIndex()) {
                this._list_onDeskPlayerComp.splice(key, 1);
                break;
            }
        }
    },

    //有某个玩家完成收牌（卡牌飞行结束）
    addPlayerWhoGotPoker: function addPlayerWhoGotPoker(playerIndex) {
        this._busyPlayerNum -= 1;
        if (this._busyPlayerNum <= 0) {
            //所有的卡牌都飞行到位
            this._busyPlayerNum = 0;
            if (this._flyCallBack) {
                this._flyCallBack();
                this._flyCallBack = null;
            }
        }
    },

    //==========================

    //随机抢庄特效
    _showGrabDealerEffect: function _showGrabDealerEffect() {
        var step = G_Config_classic.num_dealerEffMoveStep * this._curPlayerNum + this._dealerComp.getPlayerIndex();
        this._playerIndex_effMove = 0;
        this._lastShowPlayerComp = null;
        this.inTurn(step, G_Config_classic.time_dealerMove, true, this._setDealerEff.bind(this), this._showDealerEffEnd.bind(this));
    },
    //确定庄家特效
    _setDealerEff: function _setDealerEff() {
        var playerComp = this._list_onDeskPlayerComp[this._playerIndex_effMove];
        if (this._lastShowPlayerComp) this._lastShowPlayerComp.hideGrabDealer();
        playerComp.playGrabDealer();
        this._lastShowPlayerComp = playerComp;

        this._playerIndex_effMove += 1;
        if (this._playerIndex_effMove >= this._curPlayerNum) this._playerIndex_effMove = 0;
    },
    _showDealerEffEnd: function _showDealerEffEnd() {
        var num = this._playerIndex_effMove - 1;
        if (num < 0) num = 0;
        var comp = this._list_onDeskPlayerComp[num];
        this._lastShowPlayerComp = null;
        this._dealerComp.confirmDealer(true);
        this._startSetScore();
    },

    //==========================

    //同时让所有玩家执行某些操作  funcName: 玩家对象中可操作的函数名
    allPlayerDone: function allPlayerDone(funcName, userData) {
        for (var key in this._list_onDeskPlayerComp) {
            this._list_onDeskPlayerComp[key][funcName](userData);
        }
    },

    //进入轮流机制-----有时间间隔依次执行
    //turnStep : 运行几步； interval : 每一步的时间间隔;callBack : 每次执行的函数;isAtOnce:是否马上执行;callBackInEnd:机制结束时候调用
    inTurn: function inTurn(turnStep, interval, isAtOnce, callBack, callBackInEnd) {
        this._callFunc_inTurnEnd = null;
        this._turnStep = turnStep;
        this._interval_oneTurn = interval;
        if (isAtOnce) this._runTime_turn = this._interval_oneTurn;else this._runTime_turn = 0;
        this._callFunc_inTurn = callBack;
        this._callFunc_inTurnEnd = callBackInEnd;
        this._isInTurn = true;
    },

    // called every frame, uncomment this function to activate update callback
    update: function update(dt) {
        this._doTurn(dt);
    },

    //运行轮流机制
    _doTurn: function _doTurn(dt) {
        if (this._isInTurn) {
            if (this._runTime_turn >= this._interval_oneTurn) {
                this._runTime_turn = 0;
                if (this._callFunc_inTurn) {
                    this._callFunc_inTurn();
                    this._turnStep -= 1;
                    if (this._turnStep <= 0) {
                        //结束
                        this._isInTurn = false;
                        this._interval_oneTurn = 0;
                        this._callFunc_inTurn = null;
                        if (this._callFunc_inTurnEnd) {
                            this._callFunc_inTurnEnd();
                            this._callFunc_inTurnEnd = null;
                        }
                    }
                }
            } else this._runTime_turn += dt;
        }
    },

    getPlayerComp: function getPlayerComp(playerIndex) {
        if (!playerIndex) return this._myselfPlayerComp;
        return this._dict_playerComp[playerIndex];
    },
    getAllPlayerComp: function getAllPlayerComp() {
        return this._dict_playerComp;
    },
    getOnDeskPlayerList: function getOnDeskPlayerList() {
        return this._list_onDeskPlayerComp;
    },
    //获取上桌的玩家数量
    getPlayerNumOnDesk: function getPlayerNumOnDesk() {
        return this._curPlayerNum;
    },
    getGameMainNode: function getGameMainNode() {
        return this.node;
    },
    //获取游戏运行到的状态
    getGameState: function getGameState() {
        return this._gameState;
    },

    onDestroy: function onDestroy() {
        GG.classicMgr = null;
    }
});

cc._RFpop();
},{}],"Classic_controlButton":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7eff5jYiuVMdKLNvBqjU1kg', 'Classic_controlButton');
// Script\Views\Scene_classic\Classic_controlButton.js

//玩家游戏时操作的按钮的管理

cc.Class({
    'extends': require('AutoDealing'),
    _node_lastTouchBtn: null, //前一次点击的按钮
    properties: {
        node_btnGroup1: cc.Node,
        node_btnGroup2: cc.Node,
        Atlas_buttonImg: cc.SpriteAtlas,
        node_group3Btn1: cc.Node,
        node_group3Btn2: cc.Node,
        node_btnBottomEffect: cc.Node,
        node_btnUpEffect: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerAll();
        this.setGroup3TouchEnable(false);

        //test-----
        this.showGroup1();
    },

    _registerAll: function _registerAll() {
        this._multipleCompName = 'Button_multiple';

        //group1
        var children = this.node_btnGroup1.children;
        for (var key in children) {
            this.registerButton(children[key], this.Btn_group1, this, null, true);
        }
        //group2
        children = this.node_btnGroup2.children;
        for (var key in children) {
            this.registerButton(children[key], this.Btn_group2, this, null, true);
        }
        //group3
        this.node_group3Btn1.tag = 1;
        this.node_group3Btn2.tag = 2;
        this.registerButton(this.node_group3Btn1, this.Btn_group3, this);
        this.registerButton(this.node_group3Btn2, this.Btn_group3, this);
    },

    Btn_group1: function Btn_group1(event, userData) {
        this._showTouchEff(event.target);
        this.setGroup1Enable(false, false);
        ////test
        var userData = event.target.tag;
        if (Object.prototype.toString.call(userData) == '[object Number]') GG.classicMgr.grabDealerMyself(userData);
    },

    Btn_group2: function Btn_group2(event) {
        this._showTouchEff(event.target);
        this.setGroup2Enable(false, false);

        var userData = event.target.tag;
        if (Object.prototype.toString.call(userData) == '[object Number]') GG.classicMgr.setScoreMyself(userData);
    },

    Btn_group3: function Btn_group3(event) {
        //var button = event.target;

        GG.classicMgr.clickConfirmResult();
    },

    //========================group1

    showGroup1: function showGroup1() {
        this.node_btnGroup2.active = false;
        this.node_btnGroup1.active = true;
        this.setGroup1Enable(true, true);

        this.setGroup1Info([0, 1, 2, 3, 4]);
    },
    //isClear : 清理所有的按钮效果
    setGroup1Enable: function setGroup1Enable(isEnable, isClear) {
        this._setGroupEnable(this.node_btnGroup1, isEnable, isClear);
    },

    //传入对应位置的倍数{0:0, 1: 10}  对应0~4
    setGroup1Info: function setGroup1Info(mutipleList) {
        this._setGroupInfo(this.node_btnGroup1, mutipleList);
    },

    //============================group2

    showGroup2: function showGroup2(isDealer) {
        this.node_btnGroup1.active = false;
        this.node_btnGroup2.active = true;

        this.setGroup2Enable(!isDealer, true);
        this.setGroup2Info([5, 7, 10, 15, 25]);
    },
    //isClear : 是否清理所有的按钮
    setGroup2Enable: function setGroup2Enable(isEnable, isClear) {
        this._setGroupEnable(this.node_btnGroup2, isEnable, isClear);
    },

    //传入对应位置的倍数{0:0, 1: 10}  对应0~4
    setGroup2Info: function setGroup2Info(mutipleList) {
        this._setGroupInfo(this.node_btnGroup2, mutipleList);
    },

    _setGroupEnable: function _setGroupEnable(group, isEnable, isClear) {
        for (var key in group.children) {
            var btnNode = group.children[key];
            if (btnNode.y != 0) {
                if (isClear) {
                    btnNode.getComponent(this._multipleCompName).showValue();
                    this.setBtnEnable(btnNode, isEnable);
                    this._hideTouchEff();
                }
            } else {
                btnNode.getComponent(this._multipleCompName).showValue();
                this.setBtnEnable(btnNode, isEnable);
            }
        }
    },

    _setGroupInfo: function _setGroupInfo(group, mutipleList) {
        var multiple;
        for (var key in group.children) {
            var btnNode = group.children[key];
            multiple = mutipleList[key];
            if (btnNode) {
                btnNode.tag = multiple;
                btnNode.getComponent(this._multipleCompName).showValue(multiple, false);
            }
        }
    },

    //============================

    setGroup3TouchEnable: function setGroup3TouchEnable(isEnable) {
        this.setBtnEnable(this.node_group3Btn1, isEnable);
        this.setBtnEnable(this.node_group3Btn2, isEnable);
    },

    //================================

    _showTouchEff: function _showTouchEff(target) {
        if (this._node_lastTouchBtn) {
            this._node_lastTouchBtn.y = 0;
            this._node_lastTouchBtn.getComponent(this._multipleCompName).showValue();
        }
        var curScale = target.parent.scale;
        target.y += target.height / 2;
        this.node_btnBottomEffect.active = true;
        this.node_btnBottomEffect.x = target.x * curScale;

        if (!this.node_btnUpEffect.active) {
            this.node_btnUpEffect.active = true;
            this.node_btnUpEffect.getComponent(cc.Animation).play();
        }
        this.node_btnUpEffect.position = cc.p(target.x * curScale, target.y * curScale);

        this.setBtnEnable(target, false, true);
        target.getComponent(this._multipleCompName).showValue(null, true);
        this._node_lastTouchBtn = target;
    },

    _hideTouchEff: function _hideTouchEff() {
        if (this._node_lastTouchBtn) {
            this.node_btnBottomEffect.active = false;
            this.node_btnUpEffect.getComponent(cc.Animation).stop();
            this.node_btnUpEffect.active = false;
            this._node_lastTouchBtn.y = 0;
            this._node_lastTouchBtn.getComponent(this._multipleCompName).showValue(null, false);
            this._node_lastTouchBtn = null;
        }
    },

    onClear: function onClear() {
        if (this.node_btnGroup1.active) {
            this.setGroup1Enable(false, true);
        }
        if (this.node_btnGroup2.active) {
            this.setGroup2Enable(false, true);
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Classic_goldArea":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6370bnDQhNIlKz/uYQhbumR', 'Classic_goldArea');
// Script\Views\Scene_classic\Classic_goldArea.js

cc.Class({
    'extends': cc.Component,
    _pool: null, //对象池
    _list_flyInfo: null, //金币飞行的信息
    properties: {
        prefab_gold: cc.Prefab
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._goldCompName = 'Obj_gold';
        this._pool = new cc.NodePool('Classic_goldArea');
        this._interval_showGold = G_Config_classic.time_showGold;
        this._runTime = this._interval_showGold;
        this._resetData();
    },

    //=====================================

    _resetData: function _resetData() {
        this._list_flyInfo = [];
    },

    flyGolds: function flyGolds(startPos, targetPos, playerIndex) {
        this._list_flyInfo.push([startPos, targetPos, G_Config_classic.num_goldGroup, playerIndex]);
    },

    //飞行金币,从某个位置飞到某个位置
    _flyOneGold: function _flyOneGold(startPos, targetPos, playerIndex) {
        var goldNode = this._getGold();
        goldNode.active = true;
        var bezierX, bezierY, maxX, minX, maxY, minY, firstX, firstY;
        var firstX = startPos.x;
        var firstY = startPos.y;
        //firstX += G_TOOL.getRandomBool() ? G_Config_classic.num_goldFirstOff : -G_Config_classic.num_goldFirstOff;
        //firstY += G_TOOL.getRandomBool() ? G_Config_classic.num_goldFirstOff : -G_Config_classic.num_goldFirstOff;
        goldNode.x = firstX;
        goldNode.y = firstY;
        goldNode.playerIndex = playerIndex;

        var off = 150;
        //if(startPos.x > targetPos.x){
        //    maxX = startPos.x-off;
        //    minX = targetPos.x;
        //}else {
        //    maxX = targetPos.x+off;
        //    minX = startPos.x;
        //}
        //if(startPos.y > targetPos.y){
        //    maxY = startPos.y-off;
        //    minY = targetPos.y;
        //}else {
        //    maxY = targetPos.y+off;
        //    minY = startPos.y;
        //}

        maxX = startPos.x > targetPos.x ? startPos.x : targetPos.x;
        maxY = startPos.y > targetPos.y ? startPos.y : targetPos.y;
        minX = maxX - off;
        minY = maxY - off;
        bezierX = G_TOOL.getRandomArea(minX, maxX);
        bezierY = G_TOOL.getRandomArea(minY, maxY);
        var bezier = [cc.p(firstX, firstY), cc.p(bezierX, bezierY), targetPos];
        var bezierTo = cc.bezierTo(G_Config_classic.time_goldFly, bezier).easing(cc.easeQuadraticActionOut());
        goldNode.runAction(cc.sequence(bezierTo, cc.callFunc(this.flyEnd, this)));
    },
    flyEnd: function flyEnd(target) {
        if (target.playerIndex) GG.classicMgr.whenGoldMoveToHead(target.playerIndex);

        this._removeGold(target);
    },

    _getGold: function _getGold() {
        var goldNode = this._pool.get();
        if (!goldNode) {
            goldNode = cc.instantiate(this.prefab_gold);
        }
        goldNode.parent = this.node;
        goldNode.scale = 0.5;
        return goldNode;
    },

    _removeGold: function _removeGold(goldeNode) {
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    _sortGold: function _sortGold() {
        for (var key in this._list_flyInfo) {
            var flyInfo = this._list_flyInfo[key];
            if (flyInfo[2] > 0) {
                this._flyOneGold(flyInfo[0], flyInfo[1], flyInfo[3]);
                //if(flyInfo[2] > 5 ) {
                //    //从多到少
                //    this._flyOneGold(flyInfo[0], flyInfo[1]);
                //    this._flyOneGold(flyInfo[0], flyInfo[1]);
                //}
                this._flyOneGold(flyInfo[0], flyInfo[1], flyInfo[3]);
                this._flyOneGold(flyInfo[0], flyInfo[1], flyInfo[3]);
                this._list_flyInfo[key][2] -= 1;
            } else {
                this._list_flyInfo.splice(key, 1);
                //所有的金币特效结束
                if (this._list_flyInfo.length <= 0) GG.classicMgr.onGoldEffectEnd();
            }
        }
    },

    onClear: function onClear() {
        this._resetData();
        for (var key in this.node.children) {
            var node = this.node.children[key];
            if (node.active) {
                this._removeGold(node);
            }
        }
    },

    // called every frame, uncomment this function to activate update callback
    update: function update(dt) {
        if (this._list_flyInfo.length > 0) {
            if (this._runTime >= this._interval_showGold) {
                this._runTime = 0;
                this._sortGold();
            } else this._runTime += dt;
        }
    }
});

cc._RFpop();
},{}],"Classic_page_result":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a766dN/8eNOxpktDKulrKae', 'Classic_page_result');
// Script\Views\Scene_classic\Classic_page_result.js

//胜利和失败的结算面板

cc.Class({
    'extends': require('AutoDealing'),
    _isShowWin: null, //是否显示胜利
    properties: {
        node_otherMan: cc.Node,
        node_btnContinue: cc.Node,
        prefab_otherGold: cc.Prefab,
        label_goldWin: cc.Label,
        label_goldFail: cc.Label
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._effectComp = this.getComponent('Obj_pageResult_effect');
        this.registerButton(this.node_btnContinue, this.Btn_continue, this);
    },

    Btn_continue: function Btn_continue(event) {
        this._hidePage();
        GG.classicMgr.resetGame();
    },

    //显示胜利
    showWin: function showWin() {
        this.node.active = true;
        this._isShowWin = true;

        this._showMyGold(99);
        this._showOtherGold();

        this._effectComp.showBGEffect(this._isShowWin);
    },

    //显示失败
    showFail: function showFail() {
        this.node.active = true;
        this._isShowWin = false;

        this._showMyGold(-88);
        this._showOtherGold();

        this._effectComp.showBGEffect(this._isShowWin);
    },

    //显示自己的金币增减
    _showMyGold: function _showMyGold(value) {
        if (value > 0) {
            //加钱
            value = '+' + value;
            this.label_goldFail.string = '';
            this.label_goldWin.string = value;
        } else if (value < 0) {
            //减钱
            value += '';
            this.label_goldWin.string = '';
            this.label_goldFail.string = value;
        } else {
            //value == 0
            value += '';
            this.label_goldFail.string = '';
            this.label_goldWin.string = value;
        }
    },

    //显示其他玩家的金币增减
    _showOtherGold: function _showOtherGold() {
        for (var i = 0; i < G_Config_classic.num_maxPlayer - 1; i++) {
            var oneGold = this.node_otherMan.getChildByTag(i);
            if (!oneGold) {
                oneGold = cc.instantiate(this.prefab_otherGold);
                oneGold.parent = this.node_otherMan;
            }
            oneGold.getComponent('Obj_othersGold').showInfo(i, 99 + i, null);
        }
    },

    //隐藏其他玩家的金币显示
    _hideOthers: function _hideOthers() {
        for (var key in this.node_otherMan.children) {
            this.node_otherMan.children[key].active = false;
        }
    },

    //隐藏界面
    _hidePage: function _hidePage() {
        this.node.active = false;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Classic_playerBlock":[function(require,module,exports){
"use strict";
cc._RFpush(module, '118e4Ljvv1CjpvmTcD8O6VU', 'Classic_playerBlock');
// Script\Views\Scene_classic\Classic_playerBlock.js

//对战中的玩家对象

cc.Class({
    'extends': cc.Component,
    _flyEndNum: null, //完成移动的卡牌数量
    _dict_pokerNode: null, //存放所有的卡牌，方便操作
    _ani_dealerLog: null, //庄字动画
    _pokerResultComp: null, //牌型结果的类
    _pokerCalculateComp: null, //牌型的计算
    _isOnDesk: null, //是否有玩家上桌
    _effectComp: null, //控制效果显示的类
    _isOnOpening: null, //是否正在开牌
    _curPokerNum: null, //当前拥有的卡牌数量
    _pokerStartPos: null, //卡牌发牌的最初坐标
    _isShowResult: null, //是否已经计算结束了

    _isDealer: null, //是否是庄家
    _num_grabDealer: null, //抢庄的倍数
    _num_setScore: null, //下分的倍数
    _isPlayGoldEffect: null, //是否播放过接收金币的特效
    properties: {
        node_homeIndex: cc.Node,
        node_name: cc.Node,
        node_goldValue: {
            'default': null,
            type: cc.Node,
            displayName: '金币lable节点'
        },
        node_pokerStart: cc.Node,
        node_calculateResult: cc.Node,
        boolean_isMyself: false, //是否作为玩家自己操作的节点
        number_playerIndex: 1,
        boolean_isHorizontal: false },

    //是否横向摆放
    // use this for initialization
    onLoad: function onLoad() {
        this._initPlayerAttributes();
    },

    _initPlayerAttributes: function _initPlayerAttributes() {
        this._pokerCompName = 'Obj_poker';
        this._effectComp = this.getComponent('Obj_playerBlock_effect');
        //卡牌间的间隔
        this._lenInPokers = G_Config_classic.len_betweenOtherPoker;
        if (this.boolean_isMyself) {
            this._lenInPokers = G_Config_classic.len_betweenMyPoker;
            this._pokerCalculateComp = this.node_calculateResult.getComponent('Obj_calculateResult');
        }
        this._resetData();
    },

    _resetData: function _resetData() {
        this._curPokerNum = 0;
        this._flyEndNum = 0;
        this._isOnOpening = false;
        this._num_grabDealer = 0;
        this._num_setScore = 0;
        this._isOnOpening = false;
        this._dict_pokerNode = {};
        this._isOnDesk = false;
        this._isPlayGoldEffect = true;
        this._isDealer = false;
        this._isShowResult = false;
        //首次发牌的时候的最大卡牌数量
        this._maxPokerNum = G_Config_classic.num_firstGive;
    },

    setPlayerData: function setPlayerData() {
        this._isOnDesk = true;
        this._clearAll();
        this._resetData();
        this.node.active = true;
    },
    hideBlock: function hideBlock() {
        this._resetData();
        this.node.active = false;
    },

    //-=====================================pokers

    //改变最大的卡牌拥有数量
    addMaxPokerNum: function addMaxPokerNum(addNum) {
        if (!addNum) addNum = 1;
        this._maxPokerNum += addNum;
    },
    //有一张卡牌完成移动
    addFlyEndPoker: function addFlyEndPoker() {
        this._flyEndNum += 1;
        if (this._flyEndNum >= this._maxPokerNum) {
            GG.classicMgr.addPlayerWhoGotPoker(this.number_playerIndex);
        }
    },
    //增加一张卡牌时候需要的位置
    getNewPokerPos: function getNewPokerPos() {
        if (this._curPokerNum >= this._maxPokerNum) return null;
        var firstPos = this._getPokerFirstPos();
        var pos = cc.p(firstPos.x + this._curPokerNum * this._lenInPokers, firstPos.y);
        this._curPokerNum += 1;
        return [pos, parseInt(this.number_playerIndex + '' + this._curPokerNum)];
    },
    //能放置的最大卡牌数量
    getMaxPokerNum: function getMaxPokerNum() {
        return this._maxPokerNum;
    },
    //添加一个新的卡牌
    addOnePoker: function addOnePoker(pokerIndex, pokerNode) {
        this._dict_pokerNode[pokerIndex] = pokerNode;
        pokerNode.getComponent(this._pokerCompName).bindPlayer(this);
    },
    //设置卡牌是否可以点击
    setPokerTouchEnable: function setPokerTouchEnable(isEnable) {
        for (var key in this._dict_pokerNode) {
            this._dict_pokerNode[key].getComponent(this._pokerCompName).setPokerTouchEnable(isEnable);
        }
    },
    //卡牌完成开牌动作后调用
    _openPokerEnd: function _openPokerEnd() {
        if (this._isOnOpening) {
            this._isOnOpening = false;
            this._pokerResultComp.showPokerResultValue(5);
        }
    },
    //打开所有的卡牌
    _openAllPokers: function _openAllPokers(callFunc) {
        this._isOnOpening = true;
        for (var key in this._dict_pokerNode) {
            this._dict_pokerNode[key].getComponent(this._pokerCompName).openPoker(callFunc);
            callFunc = null;
        }
    },

    //================================

    //自己的牌需要重新排列后才能将结果打出
    _showMyPokersResult: function _showMyPokersResult() {
        this._receivePokers();
        this._openAllPokers();
        this._pokerResultComp.showPokerResultValue(10);
    },
    //主动或者被动地确认结果,隐藏计算，收牌，禁止点击
    confirmResult: function confirmResult() {
        this._hideCalculateResult();
        this._receivePokers();
        this._pokerResultComp.showCalculateSuccess();
        GG.classicMgr.confirmResult();
    },
    //收牌动作
    _receivePokers: function _receivePokers() {
        if (this._isShowResult) return;
        this._isShowResult = true;
        var poker,
            pokerNum = 0;
        for (var key in this._dict_pokerNode) {
            poker = this._dict_pokerNode[key];
            poker.scale = G_Config_classic.scale_noMyPoker;
            //var resultPosX = this._getPokerResultPosX();
            poker.x = cc.visibleRect.center.x + pokerNum * G_Config_classic.len_betweenOtherPoker;
            poker.getComponent(this._pokerCompName).showPokerNegative();
            pokerNum += 1;
        }
    },
    //将显示在卡牌上面的卡牌结果的操作类绑定到当前类
    bindPokerResult: function bindPokerResult(resultComp) {
        this._pokerResultComp = resultComp;
        var firstPos = this._getPokerFirstPos();
        var resultPosX = this._getPokerResultPosX();
        this._pokerResultComp.setTruePos(cc.p(resultPosX, firstPos.y));
    },

    //================================grabDealer

    //点击倍数抢庄
    grabDealer: function grabDealer(grabNum) {
        if (isNaN(grabNum) && !this._isDealer) this._effectComp.hideMultiple();else {
            this._num_grabDealer = grabNum;
            this._effectComp.showGrabDealerValue(grabNum);
        }
    },
    //显示抢庄动画
    playGrabDealer: function playGrabDealer() {
        this._effectComp.showGrabDealer();
    },
    hideGrabDealer: function hideGrabDealer() {
        this._effectComp.hideGrabDealer();
    },
    //确定庄家后播放的特效
    confirmDealer: function confirmDealer(isDealer) {
        this._isDealer = isDealer;
        if (isDealer) {
            //是庄家
            this._effectComp.hideGrabDealer();
            this._effectComp.showConfirmDealer();
        } else {
            //清理抢庄表现
            this._effectComp.hideMultiple();
        }
    },

    //================================

    //显示下分的倍数
    showSetScore: function showSetScore(chooseNum) {
        if (!chooseNum) return;
        this._num_setScore = chooseNum;
        if (this._isDealer) {
            //庄家

        } else {
                //闲家
                this._effectComp.showSetScoreValue(chooseNum);
            }
    },
    hideSetScore: function hideSetScore() {
        this._effectComp.hideMultiple();
    },
    //默认下分的倍数
    setDefaultScore: function setDefaultScore() {
        var defaultNum = 5;
        this.showSetScore(defaultNum);
    },

    //================================Calculate

    //显示卡牌的计算公式
    showPokerCalculate: function showPokerCalculate() {
        this._pokerCalculateComp.showCalculate();
    },
    _hideCalculateResult: function _hideCalculateResult() {
        this._pokerCalculateComp.hideCalculate();
    },
    //点击一张卡牌
    touchThePokerUp: function touchThePokerUp(pokerValue) {
        var isOk = this._pokerCalculateComp.chooseOnePoker(pokerValue);
        if (isOk) {
            var pokerValue = 10;
            this._pokerCalculateComp.showTrueResult(pokerValue, this.confirmResult.bind(this));
        } else this._pokerCalculateComp.showOverTouch();
        return isOk;
    },

    //点击了一张已经点击过的卡牌
    touchThePokerDown: function touchThePokerDown() {
        this._pokerCalculateComp.cancelOnePoker();
    },
    //结束算牌
    endCalculate: function endCalculate() {
        if (!this.boolean_isMyself) {
            this._openAllPokers(this._openPokerEnd.bind(this));
        } else {
            this.setPokerTouchEnable(false);
            this._hideCalculateResult();
            this._showMyPokersResult();
        }
    },

    //================================

    //显示获取金币的特效
    showGetGoldEffect: function showGetGoldEffect() {
        if (!this._isPlayGoldEffect) {
            this._effectComp.showGetGoldEffect();
            this._isPlayGoldEffect = true;
        }
    },
    //开始接收金币
    startReceive: function startReceive() {
        this._isPlayGoldEffect = false;
    },
    //显示金币增减的特效
    showGoldValueFly: function showGoldValueFly(num) {
        this._effectComp.showGoldValueFly(num);
    },

    //================================

    //清理所有
    _clearAll: function _clearAll() {
        if (this._effectComp) this._effectComp.clearAllEffect();
    },

    //这个块的位置是否有玩家上桌
    getIsHavePlayer: function getIsHavePlayer() {
        return this._isOnDesk;
    },
    //获取当前玩家块的摆放方向是否横向
    getBlockIsHorizontal: function getBlockIsHorizontal() {
        return this.boolean_isHorizontal;
    },
    //抢庄的倍数
    setGrabDealerNum: function setGrabDealerNum(value) {
        this._num_grabDealer = value;
    },
    getGrabDealerNum: function getGrabDealerNum(value) {
        return this._num_grabDealer;
    },

    //下分的倍数
    setSetScoreNum: function setSetScoreNum(value) {
        this._num_setScore = value;
    },
    getSetScoreNum: function getSetScoreNum(value) {
        return this._num_setScore;
    },
    getIsSetScore: function getIsSetScore() {
        //是否已经下分
        return this._num_setScore > 0;
    },

    //是否是庄家
    getIsDealer: function getIsDealer() {
        return this._isDealer;
    },
    //是否是玩家自己
    getIsMySelf: function getIsMySelf() {
        return this.boolean_isMyself;
    },
    //玩家对应的索引，最好能用id
    getPlayerIndex: function getPlayerIndex() {
        return this.number_playerIndex;
    },
    //是否拥有显示卡牌结果的节点
    getIsBindResult: function getIsBindResult() {
        return Boolean(this._pokerResultComp);
    },
    getNodePos: function getNodePos() {
        return this.node.position;
    },
    //获取卡牌结果显示的位置
    _getPokerResultPosX: function _getPokerResultPosX() {
        var posX;
        if (this.boolean_isMyself) {
            posX = cc.visibleRect.center.x;
        } else {
            var firstPos = this._getPokerFirstPos();
            posX = firstPos.x;
        }
        return posX + G_Config_classic.num_pokerResultOff;
    },
    //获取最初的卡牌位置
    _getPokerFirstPos: function _getPokerFirstPos() {
        if (!this._pokerStartPos) {
            this._pokerStartPos = cc.p(this.node.x + this.node_pokerStart.x * this.node.scale, this.node.y + this.node_pokerStart.y * this.node.scale);
        }
        return this._pokerStartPos;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Classic_pokerGiveArea":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0f7d2XihrhDJLyJM/BflkpF', 'Classic_pokerGiveArea');
// Script\Views\Scene_classic\Classic_pokerGiveArea.js

//容器节点，所有的卡牌都加在这个节点上

cc.Class({
    'extends': cc.Component,
    _isStartGive: null, //是否开始发牌
    _myselfPlayerIndex: null, //玩家自己操作的对象索引
    _list_playerComp: null, //已经上桌的玩家
    _pool: null, //对象池
    _list_pokerIndex: null, //卡牌的索引
    properties: {
        prefab_poker: cc.Prefab,
        prefab_pokerResult: cc.Prefab
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._initAttributes();
    },

    _initAttributes: function _initAttributes() {
        this._playerCompName = 'Obj_playerBlock';
        this._pokerCompName = 'Obj_poker';
        this._resultCompName = 'Obj_onPokerResult';
        this._pool = new cc.NodePool('Classic_pokerGiveArea');
        this._pokerResultStartIndex = 60;
        this._curPokerResultIndex = this._pokerResultStartIndex;

        //开始发牌的位置
        this._startGivePos = cc.visibleRect.center;
        this._resetData();
    },

    _resetData: function _resetData() {
        this._isStartGive = false;
        this._list_pokerIndex = [];
    },

    //开始发牌
    startGivePoker: function startGivePoker() {
        this._list_playerComp = GG.classicMgr.getOnDeskPlayerList().concat([]);
        this._isStartGive = true;

        this.schedule(this._let_update, G_Config_classic.time_givePoker);
    },
    //发一张牌
    _giveOnePokers: function _giveOnePokers() {
        var playerComp = this._list_playerComp[0];
        if (!playerComp) {
            this._isStartGive = false;
        } else {
            var pokerInfo = playerComp.getNewPokerPos();
            if (!pokerInfo) {
                //先将卡牌结果放置到指定位置
                if (!playerComp.getIsBindResult()) {
                    var result = cc.instantiate(this.prefab_pokerResult);
                    this.node.addChild(result, 1, this._curPokerResultIndex);
                    playerComp.bindPokerResult(result.getComponent(this._resultCompName));
                    this._curPokerResultIndex += 1;
                }
                this._list_playerComp.splice(0, 1);
            } else {
                var targetPos = pokerInfo[0];
                var targetIndex = pokerInfo[1];
                var poker, pokerComp;
                poker = this._getPoker();
                poker.active = true;
                poker.tag = targetIndex;
                this._list_pokerIndex.push(targetIndex);
                pokerComp = poker.getComponent(this._pokerCompName);

                playerComp.addOnePoker(targetIndex, poker);
                var isMyself = playerComp.getIsMySelf();
                pokerComp.flyPoker(this._startGivePos, targetPos, isMyself);
            }
        }
    },

    //全体多发一张
    giveOneMorePoker: function giveOneMorePoker() {
        this._list_playerComp = GG.classicMgr.getOnDeskPlayerList().concat([]);
        for (var index in this._list_playerComp) {
            this._list_playerComp[index].addMaxPokerNum();
        }

        this._isStartGive = true;
        this.schedule(this._let_update, G_Config_classic.time_givePoker);
    },

    _getPoker: function _getPoker() {
        var pokerNode = this._pool.get();
        if (!pokerNode) {
            pokerNode = cc.instantiate(this.prefab_poker);
        }
        pokerNode.parent = this.node;
        return pokerNode;
    },

    _removePoker: function _removePoker(pokerNode) {
        pokerNode.active = false;
        this._pool.put(pokerNode);
    },

    //收回所有卡牌--
    _clearAllPokers: function _clearAllPokers() {
        var num = 0;
        for (var key in this._list_pokerIndex) {
            var node = this.node.getChildByTag(this._list_pokerIndex[key]);
            this._removePoker(node);
            num += 1;
        }
    },
    //收回所有卡牌结果显示
    _clearAllPokerResult: function _clearAllPokerResult() {
        var num = this._pokerResultStartIndex;
        for (var i = 0; i < 5; i++) {
            var node = this.node.getChildByTag(num + i);
            if (node) node.getComponent(this._resultCompName).hideOnPokerResult();
        }
    },

    onClear: function onClear() {
        this._clearAllPokers();
        this._clearAllPokerResult();
        this._resetData();
    },

    _let_update: function _let_update(dt) {
        if (this._isStartGive) {
            this._giveOnePokers();
        } else this.unschedule(this._let_update);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {
//     if(this._isStartGive){
//         this._giveOnePokers();
//     }
// },

cc._RFpop();
},{}],"Classic_topTouchLayer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '2c9e99LEv5PFJK/41Ajzx0o', 'Classic_topTouchLayer');
// Script\Views\Scene_classic\Classic_topTouchLayer.js

/**
 * Created by lenovo on 2016/12/27.
 */

cc._RFpop();
},{}],"Classic_topUI":[function(require,module,exports){
"use strict";
cc._RFpush(module, '483d8kWKhVMsLHK3s/34j0r', 'Classic_topUI');
// Script\Views\Scene_classic\Classic_topUI.js

//浮现在游戏上层的显示

cc.Class({
    'extends': require('AutoDealing'),
    _aniCallFunc: null, //动画回调
    properties: {
        prefab_loadingAni: cc.Prefab, //显示加载中或者速配的动画

        btnEX_start: cc.Node,
        btnEx_up_leftTop: cc.Node,
        btnEx_down_leftTop: cc.Node,
        node_boneAni_startGame: cc.Node,
        node_boneAni_loading: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {
        this.registerButton(this.btnEX_start, this.Btn_startImg, this);
        this.registerButton(this.btnEx_up_leftTop, this.Btn_leftTop_up, this);
        this.registerButton(this.btnEx_down_leftTop, this.Btn_leftTop_down, this);
    },

    Btn_leftTop_up: function Btn_leftTop_up(event) {
        //重新开局
        GG.classicMgr.resetGame();
    },

    Btn_leftTop_down: function Btn_leftTop_down(event) {

        this.playMatchAni();
        //GG.classicMgr._startFlyGold();
    },
    _receiveData: function _receiveData(rec) {},

    Btn_startImg: function Btn_startImg(event) {
        event.target.active = false;
        GG.classicMgr.showStartWait();
    },

    setChangeTouchEnable: function setChangeTouchEnable(isEnabled) {
        this.setBtnEnable(this.btnEx_up_leftTop, isEnabled);
    },

    //=================================

    //播放开始动画
    playStartAni: function playStartAni(callFunc) {
        this.node_boneAni_startGame.active = true;
        this._aniCallFunc = callFunc;
        var comp = this.node_boneAni_startGame.getComponent(dragonBones.ArmatureDisplay);
        comp.addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        comp.playAnimation('', 1);
    },
    _startAniEnd: function _startAniEnd() {
        this._hideStartAni();
        if (this._aniCallFunc) this._aniCallFunc();
    },
    _hideStartAni: function _hideStartAni() {
        //隐藏动画
        this.node_boneAni_startGame.active = false;
    },

    //=================================

    //播放加载和速配动画
    playLoadingAni: function playLoadingAni() {
        var loading = cc.instantiate(this.prefab_loadingAni);
        loading.parent = this.node;
        loading.getComponent('Obj_loadingEffect').showLoading(this._countDownCall.bind(this));
    },
    playMatchAni: function playMatchAni() {
        var loading = cc.instantiate(this.prefab_loadingAni);
        loading.parent = this.node;
        loading.getComponent('Obj_loadingEffect').showMatch(this._countDownCall.bind(this));
    },
    _countDownCall: function _countDownCall() {},

    //=================================

    onClear: function onClear() {
        this.btnEX_start.active = true;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Config_bull100":[function(require,module,exports){
"use strict";
cc._RFpush(module, '95222qkqtxKS6CJbdacFDND', 'Config_bull100');
// Script\Common\Configs\Config_bull100.js

/**
 * Created by lenovo on 2017/2/16.
 */

//夺宝模式的配置
var bull100 = {
  dict_betGoldNum: { 1: 1, 2: 1, 3: 3, 4: 3, 5: 5 }, //筹码选项对应金币数量
  dealerSliderInterval: 10000 };

//上庄进度条增减间隔数值
window.G_Config_bull100 = bull100;

cc._RFpop();
},{}],"Config_chinese":[function(require,module,exports){
"use strict";
cc._RFpush(module, '9616fPzp/pPkaXn+SquUBhT', 'Config_chinese');
// Script\Common\Configs\Config_chinese.js

//所有的非编辑器中的中文配置

var chinese = {
    //tip01 : '请抢庄: %s',                                        //开始抢庄
    //tip02 : '请选择下分倍数: %s',                               //请选择下分倍数
    dealerGoldText1: '上庄最低金额为', //上庄金额的限制
    dealerGoldText2: '金钱不足,上庄至少需要', //
    dealerGoldText3: '您已是当前庄家！', //已经在庄
    dealerGoldText4: '您目前上庄排序：第%s位！',
    exitText1: '当前游戏未结束，退出由系统代打?', //
    exitText2: '你确定要退出游戏？',
    exitText3: '您已做庄,强制下庄系统则继续帮您打完本局!',
    dealerSuccess: '上庄成功!!!', //
    continueDealerSuccess: '续庄成功!', //
    giveUpDealer1: '您已从上庄列表退出竞庄', //还不是庄家
    giveUpDealer2: '您已在庄,下庄系统将帮您打完本局?', //已经在庄了
    downSuccess: '您已经成功下庄!', //
    continueDealer: '此局结束后您将面临下庄危险，是否需要续庄！最低续庄金额为%s',
    limitBetting: '本桌限投%s次', //百人桌子右下角下注次数提示
    bettingTimes: '当前投注次数: %s',
    isDealerTip: '请等待玩家投注(%s)',
    intoRoomLimit1: '您有未完成的牌局，请等待牌局结束！',
    //系统提示
    upDealerSuccess: '恭喜您，成为本局庄家!',
    downDealerWarning: '友情提示: 您的上庄资金不足80%，请及时续庄！',
    downDealerSuccess: '您已下庄!',
    noMyself: '没有玩家数据', //
    inToRoomLimit: '进房下限', //房间列表预制体
    noRecord: '本周暂无数据', //排行榜条目显示
    upperLimit: '9999兆', //金币上限总显示
    winMatch: '胜',
    loseMatch: '负',
    notInvolvedMatch: '未参与',
    turnOut: '转出',
    recharge: '充值',
    dealer: '当庄'
};

window.G_CHINESE = chinese;

cc._RFpop();
},{}],"Config_classic":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd5efdlRiO5OtrWl+DQwQ2Ov', 'Config_classic');
// Script\Common\Configs\Config_classic.js

//在经典模式对战中使用到的常用配置

var classicConfig = {
    //num_firstGive : 4,                                          //首次发牌数量
    //num_maxPlayer : 5,                                          //最大玩家数量
    //num_maxChoose : 3,                                          //看牌的时候能选中的最大卡牌数量

    //len_betweenOtherPoker : 25,                                //参战玩家两张卡牌之间的间隔
    //len_betweenMyPoker : 135,                                    //玩家自己拥有的两张卡牌之间的间隔
    //time_givePoker : 0.1,                                       //发每张牌的间隔
    //time_pokerFly : 0.8,                                        //卡牌移动需要的时间
    //scale_noMyPoker : 0.5,                                      //盖住的卡牌的缩放程度
    //num_pokerUp : 30,                                           //点击卡牌后向上移动的高度
    //time_dealerMove : 0.2,                                      //抢庄特效移动间隔
    //time_confirmDealer : 1.5,                                   //确定庄家特效持续时间
    //time_goldFly : 0.3,                                            //金币飞行的时间
    //num_goldGroup : 10,                                           //一次飞行的金币量
    //
    //time_showGold : 0.08,                                          //显示单个金币间隔
    //list_centerProgressTime : [0.7,0.2],                        //中间圆形进度条倒计时:参数1:第一个开始变化颜色占总时间的比例；参数2:第二个变化
    //time_pageResult : [0.3,0.8,1.3],                             //结算面板,time1:胜利文字动作；time2:背景动作; time3图标动作
    //time_pokerResultShow : 0.5,                                 //卡牌上面的结果特效显示的时间
    //scale_pokerResultShow : 1.2,                                //卡牌上方结果特效最大倍数
    //list_backgroundEffect1 : [1.0, 1.3],                        //背景特效1 ：参数1：缩放时间，参数2：缩放倍数
    //time_pokerResultInterval : 0.5,                             //依次显示卡牌结果的间隔
    //list_goldAddFly : [0.8, 0.8, 0.5],                           //增加金币效果参数1：飞出去的时间，参数2：飞回label的时间，参数3：中间停顿的间隔
    //list_goldLostFly : [0.8, 1.0, 0.3],                          //减少金币效果参数1：飞出去的时间，参数2：中间停顿的时间，参数3：消失花费的时间
    //num_pokerResultOff : 45,                                      //卡牌上面结果显示矫正
    //num_dealerEffMoveStep : 2,                                 //该数值乘以当前玩家数==随机庄家效果必定移动步数

    //num_goldFirstOff : 15,                                        //飞行金币初始位置偏移量
};

window.G_Config_classic = classicConfig;

cc._RFpop();
},{}],"Config_common":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'b26deKEmoBAK7r3zwyki638', 'Config_common');
// Script\Common\Configs\Config_common.js

/**
 * Created by lenovo on 2016/12/29.
 */

//所有对战模式通用的配置
var common = {
    version: '1.0.1', //游戏版本
    isLocal: true, //是否走本地
    frameScale: 0.5, //界面缩放标准
    bottomTipPos: cc.p(cc.visibleRect.width * 0.5, cc.visibleRect.height * 0.1),
    time_tipDefault: 3, //没有设置时间，默认存在的时长
    num_maxNameLen: 10, //最大的名字长度,超过的则用省略号显示,按字符宽度

    scale_moveGold: 0.5, //移动中的金币的缩放比例

    showTrendItems: 5, //显示走势图条目五个
    connectTimeOut: 3, //3秒超时
    defaultVolume: 1, //默认音量大小
    notice_speed: 240, //跑马灯移动速度
    notice_showTime: 4 };

//跑马灯单条存在时间

//btnGrayValue : 0.3,                                          //按钮置灰的透明度
//time_confirmTipShow : 0.4,                                  //确认框显示出现时间
//scale_confirmTipMax : 1.2,                                  //确认框最大缩放
//num_multipleValueOff : 12,                                  //倍数按钮，数值显示大于10后的偏移
//dict_moneyUnit : {
//    10000: '万',
//    100000000 : '亿',
//},                                                            //超过数额后显示金钱单位
//time_eyeEffect : [1.5, 2.0],                                  //眼睛上的特效，time1:移动时间，time2：停顿时间
//time_pokerOpen : 0.6,                                        //翻牌消耗的时间，分两段

window.G_Config_common = common;

cc._RFpop();
},{}],"Config_dialogUrl":[function(require,module,exports){
"use strict";
cc._RFpush(module, '852465MKD5NP7E8Dfu10X+Z', 'Config_dialogUrl');
// Script\Common\Configs\Config_dialogUrl.js

//配置弱网资源请求路径及参数

var weekNetworkUrl = {
    loginTokenUrl: '/user/token.html', //token获取
    loginToSetUrl: '/user/voiceSet.html', //声音设置获取
    getSystemSetUrl: '/game/PlayerSysSet.html', //设置获取
    saveSystemSetUrl: '/game/PlayerSysSetSave.html', //声音设置保存(musicClass:true, soundClass:true, gameType:'DOU_NIU')
    timedRefreshUrl: '/user/alive.html', //定时刷新
    logout: '/passport/logout.html', //确定退出游戏

    personalInfoUrl: '/game/playerInfo.html', //获取玩家信息
    playerTreasuresUrl: 'game/statistics/treasures.html', //获取富豪榜数据
    playerProfitUrl: '/game/statistics/profits.html', //获取盈利榜数据  需要gameId 目前为1
    gameRoomListUrl: '/game/rooms.html', //获取房间列表 需要游戏玩法 gameModelName

    announcementUrl: '', //公告内容获取
    rulesUrl: '', //规则内容获取
    recordUrl: '/game/statistics/user/records.html', //记录内容获取  需要"gameId":1, dateClass:0,1,2, pageSize: 20
    accountUrl: '/playerRecharge/playerAccountRecord.html', //账目内容获取  需要"gameId":1, dateClass:0,1,2, pageSize: 20
    trendUrl: 'game/chart/inning.html', //走势图数据获取     需要 "gameModelId": ModelId,  "gameRoomId": roomId
    playerListUrl: 'game/bull100/playerList.html' };

//玩家列表  需要游戏玩法
window.G_DIALOG_URL = weekNetworkUrl;

cc._RFpop();
},{}],"Config_grab":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0512eKgI7FPO4yvK6/Ay6rT', 'Config_grab');
// Script\Common\Configs\Config_grab.js

/**
 * Created by lenovo on 2016/12/29.
 */

//夺宝模式的配置
var grab = {
    num_otherGrabGoldNum: 2, //其他玩家投注的时候飞行的金币数量
    time_grabWillEnd: 2, //距离投注结束剩余该秒数的时候不播放发牌动画
    num_winPokerOff: 15, //有牛时候，后面两张牌增加的间隔
    time_goldFlyOnHead: 1.5 };

//头像上金币增减飞行需要空出来的时间

//time_goldFly_Add : 0.3,                                                  //押注时候，金币的移动时间
//time_goldFly_Result : 0.5,                                               //计算收益的时候，金币的移动时间
//time_goldFly_delay : 0.1,                                                //金币飞行，金币间隔
//num_goldImgArea : 0.3,                                                   //金币在投注区显示的范围=最大范围的百分比
//dict_moveGoldNum : {1:1,2:1,3:2,4:3,5:4},                               //玩家投注时候增加的金币图片数量
//num_pokerOffX : 0.35,                                                     //卡牌之间的间隔=卡牌宽的百分比
//time_pokerMove : 0.5,                                                    //卡牌移动花费的时间
//delayTime_pokerMove : 0.05,                                             //飞出卡牌的间隔
//time_openMovePoker : 0.2,                                               //庄家开牌时候，牌移动耗时
//time_showFlag : 3.5,                                                     //赢钱旗帜保持显示的时长
//time_goldOnHead : 0.5,                                                   //头像上面金币值移动速度
//time_openDelay : 0.5,                                                    //开牌间隔
window.G_Config_grab = grab;

cc._RFpop();
},{}],"Config_resourceUrl":[function(require,module,exports){
"use strict";
cc._RFpush(module, '23f282iOddN5KHJaiCrIFod', 'Config_resourceUrl');
// Script\Common\Configs\Config_resourceUrl.js

//配置资源的路径或者资源的名字

var resURL = {
    atlas_multipleImgName: { //按钮上的倍数图片
        0: 'comm_text_dont',
        imgName: 'comm_text_'
    },
    atlas_pokerResult: { //卡牌上面的牌面结果
        resultImgName: 'comm_text_list_according_',
        noResult: 'comm_text_list_according_no',
        dont: 'comm_text_list_according_ok'
    },
    atlas_chooseMultipleName: { //头像旁边的抢几和下分倍数图片
        dealer_0: 'Scene_Classic_buqaing02',
        dealer_1: 'Scene_Classic_qz_1',
        dealer_2: 'Scene_Classic_qz_2',
        dealer_3: 'Scene_Classic_qz_3',
        dealer_4: 'Scene_Classic_qz_4',
        score_5: 'Scene_Classic_x5',
        score_10: 'Scene_Classic_x10',
        score_15: 'Scene_Classic_x15',
        score_25: 'Scene_Classic_x25'
    },
    //确定庄家后的特效
    ani_getDealerName: {
        horizontal: 'GetDealer01',
        vertical: 'GetDealer02'
    },
    //获取金币时候的头像特效
    ani_getGoldOnHeadName: {
        horizontal: 'GoldOnHead_horizontal',
        vertical: 'GoldOnHead_vertical'
    },
    //开始动画
    boneAni: {
        loading: 'BoneAni_loading', //加载中
        matching: 'BoneAni_loading_dating' },
    //速配中
    //卡牌基础的显示
    pokerType: {
        blockMin: 'common_card_f_02', //方块小
        blockMax: 'common_card_f_01', //方块大
        blackHeartMin: 'common_card_h_02', //黑色桃心小
        blackHeartMax: 'common_card_h_01', //黑色桃心大
        redHeartMin: 'common_card_hong_02', //红色桃心小
        redHeartMax: 'common_card_hong_01', //红色桃心大
        flowerMin: 'common_card_mei_02', //梅花小
        flowerMax: 'common_card_mei_01', //梅花大
        value_J: 'common_card_J', //卡牌j的牌型显示
        value_Q: 'common_card_Q', //卡牌Q的牌型显示
        value_K: 'common_card_k' },
    //卡牌K的牌型显示
    //房间基础的显示
    roomType: {
        roomBGImgName: 'platform_room_b_', //房间背景图片
        roomNameImgName: 'platform_room_n_' },
    //房间名称
    //表名枚举
    dict_tablesName: {
        platformConfig: '',
        commonConfig: 'CommonConfig',
        commonLoading: 'Common_loadingTips',
        //classicConfig : 'Classic_mainConfig',
        chinese: 'Chinese',
        grabConfig: 'Grab_mainConfig',
        //bull100Config : 'Bull100_mainConfig',
        audioName: 'Enum_audioName'
    },

    //ui界面
    uiName: cc.Enum({
        account: 'Prefab_dialog_account', //账目界面
        announcement: 'Prefab_dialog_announcement', //公告界面
        dealerList: 'Prefab_dialog_dealerList', //上庄列表界面
        playerList: 'Prefab_dialog_playerList', //玩家列表界面
        record: 'Prefab_dialog_record', //记录界面
        rules: 'Prefab_dialog_rules', //规则界面
        rulesInHome: 'Prefab_dialog_rulesInHome', //房间内的规则界面
        systemSet: 'Prefab_dialog_set', //系统设置界面
        bull100Trend: 'Prefab_dialog_bull100Trend', //百人走势图界面
        grabTrend: 'Prefab_dialog_grabTrend' }),

    //押宝走势图界面

    //音效路径
    audioUrl: 'resources/Sounds/%s.mp3'
};
window.G_RES_URL = resURL;

cc._RFpop();
},{}],"DataObject_socketMsg":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a0e2268/ERBzaXmjmotkm3P', 'DataObject_socketMsg');
// Script\Common\DataObject_socketMsg.js

//socket网络请求使用的消息结构

var Msg = function Msg() {
    this.pos = 0;
    this.data = "";
    this.len = 0; //消息长度
    this.deepCount = 0; //deepCount 用于object嵌套检测,>=10时不再解析
};

/** 数据类型,名称后面带S为列表,必须为奇数,单数据则为偶数 */
Msg.TYPE_NUM = 0, Msg.TYPE_NUMS = 1, Msg.TYPE_STRING = 2, Msg.TYPE_STRINGS = 3, Msg.TYPE_BOOL = 4, Msg.TYPE_BOOLS = 5;
Msg.Type = function (isList, jsonClass) {
    this.isList = isList;
    this.jsonClass = jsonClass;
    return this;
};

Msg.getMsg = function (fromServerData) {
    var m = new Msg();
    m.data = fromServerData;
    return m;
};

Msg.objIndexStart = 8; //基础数据定义数,Msg.TYPE_NUM 等最大值+1
Msg.prototype.loadJsons = function () {
    var getJson = function getJson(index) {
        if (!NetBean[index]) NetBean[index] = function () {
            return this;
        };
        return NetBean[index];
    };
    var jsonCount = this.getLong();
    for (var i = 0; i < jsonCount; i++) {
        var index = this.getLong();
        var t = getJson(index);
        t.event = index;
        t.className = this.getString();
        t.sendBy = this.getLong();
        if ((t.sendBy & 1) != 0) {//SENDBY_SERVER=1;
            //if(!netSrv.msgHandle[t.className]){//只报错，不抛出
            //    console.error("NetServer的msgServer必须包含子对象来处理从服务端传过来的消息："+t.className);
            //}
        }
        NetBean[t.className] = t;
        var fieldCount = this.getLong();
        t.fields = new Array();
        t.types = new Array();
        t.requireds = new Array();
        for (var j = 0; j < fieldCount; j++) {
            var fieldIndex = this.getLong();
            t.requireds[fieldIndex] = this.getLong() == 1;
            t.fields[fieldIndex] = this.getString();
            var fieldType = this.getLong();
            var isArray = false;
            if (fieldType < 0) {
                isArray = true;
                fieldType = -fieldType;
            }
            if (fieldType < Msg.objIndexStart) {
                t.types[fieldIndex] = fieldType;
            } else {
                t.types[fieldIndex] = new Msg.Type(isArray, getJson(fieldType - Msg.objIndexStart));
            }
        }
    }
};

Msg.toObject = function (fromServerData) {
    var msg = Msg.getMsg(fromServerData);
    var serverTime = msg.getLong();
    //netSrv.resetServerTime(serverTime);
    var classIndex = msg.getLong();
    if (classIndex == -1) {
        msg.loadJsons();
        return -1;
    }
    return msg.toObject(NetBean[classIndex]);
};

Msg.toMsg = function (obj) {
    var msg = new Msg();
    msg.putLong(obj.constructor.event);
    msg.toMsg(obj);
    return msg.data;
};

Msg.prototype.toMsg = function (o) {
    var isArray = o instanceof Array,
        isNumStr = false,
        fieldCount = 0;
    this.mark(); //fieldCount
    var types = o.constructor.types,
        fields = o.constructor.fields,
        requireds = o.constructor.requireds;
    for (var i in fields) {
        var val = o[fields[i]];
        if (val == undefined || val == null) {
            if (requireds[i]) {
                throw new Error("required的值不能为空,NetBean." + o.constructor.className + "." + o.constructor.fields[i]);
            }
            continue;
        }
        fieldCount++;
        this.putLong(i);
        this.toFieldMsg(val);
    }
    this.putByteOnMark(fieldCount);
};

Msg.prototype.toFieldMsg = function (val) {
    if (typeof val == "number") {
        this.putLong(val);
    } else if (typeof val == "string") {
        this.putString(val);
    } else if (typeof val == "boolean") {
        this.putBoolean(val);
    } else if (val instanceof Array) {
        this.putLong(val.length);
        for (var j in val) {
            this.toFieldMsg(val[j]);
        }
    } else {
        this.toMsg(val);
    }
};

/**
 * @param jsonClass Json对应的类
 * @returns 消息转换的对象
 */
Msg.prototype.toObject = function (jsonClass) {
    var obj;
    try {
        obj = new jsonClass();
    } catch (e) {
        console.log(e);
    }
    var fieldLen = this.getByte();
    for (var i = 0; i < fieldLen; i++) {
        //获取fieldIndex,再获取变量值的模式
        var fieldIndex = this.getLong();
        var jsonType = jsonClass.types[fieldIndex];
        var isObject = typeof jsonType != 'number';
        if (!isObject && jsonType & 1 == 1 || isObject && jsonType.isList) {
            //奇数,列表,先读取数量
            if (!isObject) jsonType--;
            var listLen = this.getFiled(Msg.TYPE_NUM);
            obj[jsonClass.fields[fieldIndex]] = new Array();
            for (var j = 0; j < listLen; j++) {
                obj[jsonClass.fields[fieldIndex]][j] = this.getFiled(jsonType);
            }
        } else {
            obj[jsonClass.fields[fieldIndex]] = this.getFiled(jsonType);
        }
    }
    return obj;
};

Msg.prototype.getFiled = function (jsonType) {
    switch (jsonType) {
        case Msg.TYPE_NUM:
            return this.getLong();
            break;
        case Msg.TYPE_STRING:
            return this.getString();
            break;
        case Msg.TYPE_BOOL:
            return this.getBoolean();
            break;
        default:
            return this.toObject(jsonType.jsonClass);
            break;
    }
};

Msg.prototype.getLen = function () {
    var len = this.data.charCodeAt(this.pos++);
    var byteCount = len >> 4 & 7;
    len = len & 15;
    for (var i = 0; i < byteCount; i++) {
        len = len | this.data.charCodeAt(this.pos++) << i * 7 + 4;
    }
    return len + this.pos;
};

Msg.prototype.putLong = function (val) {
    var positive = val > 0;
    if (!positive) val = val * -1;
    var bit = 0;var tmp = val;
    while (tmp > 0) {
        tmp = parseInt(tmp / 2);
        bit++;
    }
    bit = parseInt((bit + 3) / 7);
    this.data += String.fromCharCode(bit << 4 | (positive ? 0 : 1) << 3 | val & 7); //高1-3多余byte数，低4位正0(负1)，低1-3位数据最低3位
    val = parseInt(val / (1 << 3));
    for (var i = 0; i < bit; i++) {
        this.data += String.fromCharCode(val & 127);
        val = parseInt(val / (1 << 7));
    }
};

Msg.prototype.getLong = function () {
    var d = this.data.charCodeAt(this.pos++);
    var rs = d & 7;
    var byteCount = d >> 4 & 7;
    var rate = 1 << 3; //用乘法代替位移避免溢出
    for (var i = 0; i < byteCount; i++) {
        rs = rs + (this.data.charCodeAt(this.pos++) & 255) * rate;
        rate *= 1 << 7;
    }
    rs = rs * ((d & 8) == 0 ? 1 : -1);
    return rs;
};

Msg.prototype.mark = function () {
    if (!this.marks) this.marks = new Array();
    this.marks.push(this.data);
    this.data = "";
};

Msg.prototype.putByteOnMark = function (val) {
    this.data = this.marks.pop() + String.fromCharCode(val & 127) + this.data;
};

Msg.prototype.putByte = function (val) {
    return this.data += String.fromCharCode(val & 127);
};

Msg.prototype.getByte = function () {
    return this.data.charCodeAt(this.pos++) & 127;
};
Msg.prototype.putBoolean = function (val) {
    return this.data += String.fromCharCode(val ? 1 : 0);
};

Msg.prototype.getBoolean = function () {
    return this.data.charCodeAt(this.pos++) == 1;
};

Msg.prototype.putString = function (val) {
    var count = 0;
    for (var i = 0; i < val.length; i++) {
        var char = val.charCodeAt(i);
        if (char < 0x7f) {
            count += 1;
        } else if (char <= 0x7ff) {
            count += 2;
        } else if (char <= 0xFFFF) {
            count += 3;
        } else {
            count += 4;
        }
    }
    var bit = 0,
        tmp = count;
    while (tmp > 0) {
        tmp = tmp >> 1;
        bit++;
    }
    bit = parseInt((bit + 2) / 7);
    tmp = count;
    this.data += String.fromCharCode(bit << 5 | tmp & 31);
    tmp = tmp >> 5;
    for (var i = 0; i < bit; i++) {
        this.data += String.fromCharCode(tmp & 127);
        tmp = tmp >> 7;
    }
    this.len += count - val.length;
    this.data += val;
};

Msg.prototype.getString = function () {
    var d = this.data.charCodeAt(this.pos++);
    var lenCount = d >> 5 & 3; //高2位字节数
    var byteCount = d & 31;
    for (var i = 0; i < lenCount; i++) {
        byteCount = byteCount | (this.data.charCodeAt(this.pos++) & 255) << i * 7 + 5;
    }
    if (byteCount == 0) return "";
    var count = 0,
        start = this.pos;
    for (var i = this.pos; i < this.data.length; i++) {
        var char = this.data.charCodeAt(this.pos++);
        if (char < 0x7f) {
            count += 1;
        } else if (char <= 0x7ff) {
            count += 2;
        } else if (char <= 0xFFFF) {
            count += 3;
        } else {
            count += 4;
        }
        if (count >= byteCount) {
            break;
        }
    }
    return this.data.substring(start, this.pos);
};

module.exports = Msg;

cc._RFpop();
},{}],"GameData":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'bc6ad5lIgpKkqG/gsjUbiiI', 'GameData');
// Script\Common\Managers\GameData.js

/**
 * Created by lenovo on 2016/12/30.
 */

//游戏数据的剪接获取方式
var gameData = {
    _curHash: null, //记录刷新页面前的场景
    //tableData
    getChinese: function getChinese(keyID) {
        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.chinese);
        if (!table) return null;
        return table.getDataByID(keyID);
    },
    getBottomTipPos: function getBottomTipPos() {
        return cc.p(cc.visibleRect.width * 0.5, cc.visibleRect.height * 0.17);
    },
    getTopTipPos: function getTopTipPos() {
        return cc.p(cc.visibleRect.width * 0.5, cc.visibleRect.height * 0.8);
    },
    getCenterTipPos: function getCenterTipPos() {
        return cc.p(cc.visibleRect.width * 0.5, cc.visibleRect.height * 0.55);
    },
    //根据索引获取卡牌的值和类型
    getPokerInfo: function getPokerInfo(index) {
        this.pokerValue = (index >> 2) + 1; //1-13对应A到K
        this.pokerType = 4 - (index & 3); //1-4对应黑桃，红桃，梅花，方块
        return this;
    },

    //获取图片请求接口路径
    getResNetRoot: function getResNetRoot(resUrl) {
        //return 'http://192.168.0.109:8080/static/'+resUrl
        if (G_Config_common.isLocal) {
            var url = window.location.href;
            var pointIndex = url.lastIndexOf(':');
            url = url.substring(0, pointIndex + 5);
            url += '/static/';
            return url + resUrl;
        } else return resRoot + '/' + resUrl;
    },
    //获取吃瓜群众的座位ID
    getIdlePlayersSeatIndex: function getIdlePlayersSeatIndex() {
        return -2;
    },
    //获取庄家的座位标号
    getDealerSeatIndex: function getDealerSeatIndex() {
        return -1;
    },
    //获取场景的名字
    getSceneName: function getSceneName(sceneType) {
        var sceneName;
        switch (parseInt(sceneType)) {
            case G_TYPE.gameModule.bull100:
                sceneName = 'Poker_Bull100';
                break;
            case G_TYPE.gameModule.classic:
                sceneName = 'Poker_classic';
                break;
            case G_TYPE.gameModule.grab:
                sceneName = 'Poker_Grab';
                break;
            default:
                break;
        }
        return sceneName;
    },

    //获取游戏所处的平台
    getOS: function getOS() {
        switch (cc.sys.os) {
            case cc.sys.OS_ANDROID:
                break;
            case cc.sys.OS_IOS:
                break;
            default:
                break;
        }
        return cc.sys.os;
    },
    //获取浏览器类型
    getBrowserType: function getBrowserType() {
        switch (cc.sys.browserType) {
            case cc.sys.BROWSER_TYPE_QQ:
                break;
            case cc.sys.BROWSER_TYPE_SAFARI:
                break;
            case cc.sys.BROWSER_TYPE_ANDROID:
                break;
            default:
                break;
        }
        return cc.sys.browserType;
    },

    //获取上次登陆的场景
    getLastSceneHash: function getLastSceneHash() {
        if (!window.location.hash || this._curHash === window.location.hash) return null;
        var data = {
            enterType: null,
            roomId: null
        };
        var strList = window.location.hash.split('-');
        var enterType = strList[0].substring(1, strList[0].length);
        if (!enterType) return null;
        data.enterType = enterType;
        data.roomId = parseInt(strList[1]);
        return data;
    },
    //设置当前的场景
    setCurSceneHash: function setCurSceneHash(enterType, roomId) {
        window.location.hash = "#" + enterType + "-" + roomId;
    },

    //根据进房类型获取进游戏房时候需要的请求类型
    getEnterHomeModel: function getEnterHomeModel(enterType) {
        var gameType;
        switch (parseInt(enterType)) {
            case G_TYPE.gameModule.bull100:
                gameType = G_TYPE.net_gameModule.bull100;
                break;
            case G_TYPE.gameModule.grab:
                gameType = G_TYPE.net_gameModule.grab;
                break;
            case G_TYPE.gameModule.classic:
                gameType = G_TYPE.net_gameModule.classic;
                break;
            default:
                break;
        }
        return gameType;
    }
};

window.G_DATA = gameData;

cc._RFpop();
},{}],"GameStart":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0bb59bZNjlOg7qETPpsxzXm', 'GameStart');
// Script\Views\GameStart.js

//整个游戏开始前的处理

//对应G_TYPE.gameModule
var gameModule = cc.Enum({
    platform: 0,
    bull100: 1,
    grab: 2,
    classic: 3
});

cc.Class({
    'extends': cc.Component,

    properties: {
        _gameState: null,

        node_gameScene: {
            'default': null,
            type: cc.Node,
            displayName: '整个游戏的父层'
        },
        gameType: {
            'default': 0,
            type: gameModule,
            displayName: '游戏模式'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        //this.node_gameScene.active = false;
        //临时的处理方案，切换不同的场景
        //if(window.G_InRoomType && window.G_InRoomType != this.gameType){
        //    switch (window.G_InRoomType){
        //        case G_TYPE.gameModule.bull100:
        //            cc.director.preloadScene('Poker_Bull100', function () {
        //                cc.director.loadScene('Poker_Bull100');
        //            });
        //            break
        //        case G_TYPE.gameModule.grab:
        //            cc.director.preloadScene('Poker_Grab', function () {
        //                cc.director.loadScene('Poker_Grab');
        //            });
        //            break
        //        case G_TYPE.gameModule.classic:
        //            cc.director.preloadScene('Poker_classic', function () {
        //                cc.director.loadScene('Poker_classic');
        //            });
        //            break
        //        case G_TYPE.gameModule.platform:
        //            cc.director.preloadScene('Platform', function () {
        //                cc.director.loadScene('Platform');
        //            });
        //            break
        //        default:
        //            break
        //    }
        //    return
        //}

        //一些动画效果在脏矩形下发生错误
        //if (cc._renderType === cc.game.RENDER_TYPE_CANVAS) {
        //    cc.renderer.enableDirtyRegion(false);
        //    cc.view.enableRetina(true);
        //}

        //this._reloadTable();
    },

    _reloadTable: function _reloadTable() {
        var tables = [];
        switch (this.gameType) {
            case gameModule.platform:
                //tables.push(G_RES_URL.dict_tablesName.platformConfig);
                break;
            case gameModule.classic:
                //tables.push(G_RES_URL.dict_tablesName.classicConfig);
                break;
            case gameModule.grab:
                tables.push(G_RES_URL.dict_tablesName.grabConfig);
                break;
            case gameModule.bull100:
                //tables.push(G_RES_URL.dict_tablesName.bull100Config);
                break;
            default:
                break;
        }
        tables.push(G_RES_URL.dict_tablesName.commonConfig);
        tables.push(G_RES_URL.dict_tablesName.chinese);
        tables.push(G_RES_URL.dict_tablesName.audioName);
        tables.push(G_RES_URL.dict_tablesName.grabConfig);
        GG.tableMgr.reloadTables(tables, this._addConfigCallFunc.bind(this));
    },

    //加载配置文件结束
    _addConfigCallFunc: function _addConfigCallFunc() {
        this._gameState = G_TYPE.webGameState.running;

        //经典看牌的主要配置
        switch (this.gameType) {
            case gameModule.platform:
                //var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.platformConfig);
                //if(table) {
                //
                //}
                break;
            case gameModule.classic:
                var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.classicConfig);
                if (table) {
                    var dataObj = table.getFirstData();
                    this._addConfig(G_Config_classic, dataObj);
                }
                break;
            case gameModule.grab:
                var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.grabConfig);
                if (table) {
                    var dataObj = table.getFirstData();
                    this._addConfig(G_Config_grab, dataObj);
                }
                break;
            case gameModule.bull100:
                var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.bull100Config);
                if (table) {
                    var dataObj = table.getFirstData();
                    this._addConfig(G_Config_grab, dataObj);
                }
                break;
            default:
                break;
        }

        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonConfig);
        if (table) {
            var dataObj = table.getFirstData();
            this._addConfig(G_Config_common, dataObj);
        }
        this.node_gameScene.active = true;
    },

    //增加配置信息
    _addConfig: function _addConfig(targetObject, addObject) {
        if (addObject) {
            for (var attrName in addObject) {
                targetObject[attrName] = addObject[attrName];
            }
        }
    },

    getGameState: function getGameState() {
        return this._gameState;
    },

    getGameType: function getGameType() {
        return this.gameType;
    },

    onDestroy: function onDestroy() {
        cc.game.off(cc.game.EVENT_HIDE, this._pauseWebGame, this);
        cc.game.off(cc.game.EVENT_SHOW, this._resumeWebGame, this);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Global":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'fe86ejAMf9Av4e2z1Y1HOfE', 'Global');
// Script\Common\Global.js

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
    for (var i = 0; i < mgrList.length; i++) {
        if (mgrList[i]) return mgrList[i];
    }
    return null;
};
GG.curMgr = null;

//弹窗提示管理器
GG.tipsMgr = null;
//最上面的触摸层
GG.topTouchLayer = null;
//所有预加载资源管理
GG.resMgr = null;

//=======================================================

//玩家对象
GG._player = null;
GG.getPlayer = function () {
    if (!this._player) {
        var player = require('M_Player');
        this._player = new player();
    }
    return this._player;
};
GG.getPlayerID = function () {
    return this.getPlayer().getPlayerID();
};
//弱联网管理器
GG.httpMgr = GG.httpMgr || (function () {
    var http = require('HTTPMgr');
    return new http();
})();

//socket网络请求管理器
GG.socketMgr = GG.socketMgr || (function () {
    var socket = require('SocketMgr');
    return new socket();
})();

//配置文件管理器
GG.tableMgr = GG.tableMgr || (function () {
    var tableMgr = require('TablesMgr');
    return new tableMgr();
})();

//配置文件管理器
GG.audioMgr = GG.audioMgr || (function () {
    var audioMgr = require('AudioMgr');
    return new audioMgr();
})();

//当前的游戏状态
GG.getGameState = function () {
    return this._gameState;
};

//当前的游戏模式
GG.getGameType = function () {
    var canvas = cc.find('Canvas');
    if (canvas) {
        var startComp = canvas.getComponent('GameStart');
        if (startComp) {
            return startComp.getGameType();
        }
    }
    return null;
};

//资源管理器
GG.resMgr = GG.resMgr || (function () {
    var mgr = require('ReloadResMgr');
    return new mgr();
})();

//退出赛事所在的房间
GG.exitHome = function () {
    //需要增加一个正在退出的逻辑，给释放缓存一定的时间
    var dataObj = {
        gameType: this.getGameType()
    };
    this.getPlayer().setOutHomeData(dataObj);
    GG.changeScene(G_TYPE.sceneName.platform);
};

//退出游戏
GG.exitGame = function () {
    GG.httpMgr.sendHttpLogin(G_DIALOG_URL.logout, null, (function (data) {
        cc.director.end();
        location.replace(location.href);
    }).bind(this));
};

//加载场景
GG.changeScene = function (sceneName) {
    GG.getPlayer().setReloadSceneInfo({
        sceneName: sceneName
    });
    cc.director.preloadScene('InHomeLoading', function () {
        cc.director.loadScene('InHomeLoading');
    });
};

//发起是否重连的请求; callFunc : 点击确定后的回调
GG.showReconnect = function (callFunc) {
    if (GG.tipsMgr) {
        var tableData = G_DATA.getChinese(62);
        GG.tipsMgr.showConfirmTip_TWO(tableData.content, callFunc, function () {
            //直接关闭游戏
            cc.director.end();
            location.replace(location.href);
        });
    }
};

//是否正在加载中
GG.getIsLoading = function () {
    return this._isLoading;
};
//正在加载中
GG.setIsLoading = function (isLoading) {
    this._isLoading = isLoading;
};
//登陆socket
GG.loginSocket = function (func) {
    //登陆socket
    GG.socketMgr.SendMsg(NetType.s_login, {
        token: GG.getPlayer().getPlayerToken(),
        userId: GG.getPlayer().getUID()
    }, func);
};

GG._isFirstEnterPlatform = true;
GG._gameState = null; //游戏暂停和开始的状态
//刚登陆后的处理
GG.whenGameStart = function (callBack) {
    if (!GG._isFirstEnterPlatform) return false;

    //初次进入游戏
    GG._isFirstEnterPlatform = false;
    console.log('浏览器类型' + cc.sys.browserType);
    //调整适配问题
    if (cc.sys.isNative) {
        //本地原生处理

    } else {
            //隐藏地址栏
            window.scrollTo(0, 1);
            //网页上
            var designSize = cc.view.getDesignResolutionSize();
            cc.view.setDesignResolutionSize(designSize.width, designSize.height, cc.ResolutionPolicy.EXACT_FIT);
            cc.view.adjustViewPort(true);
            cc.view.setOrientation(cc.macro.ORIENTATION_LANDSCAPE);
            cc.view.resizeWithBrowserSize(Boolean(cc.sys.browserType != cc.sys.BROWSER_TYPE_MOBILE_QQ));
            window.addEventListener("resize", function () {
                var frameSize = cc.view.getFrameSize();
                if (frameSize.width > frameSize.height) {
                    //横屏
                } else {
                        //竖屏
                    }
                cc.view.resizeWithBrowserSize(true);
            });
        }
    //增加游戏暂停和开始的监听
    this._gameState = G_TYPE.webGameState.running;
    if (!cc.sys.isNative && document) {
        var showStateName, showChange;
        if (typeof document.hidden !== "undefined") {
            showChange = "visibilitychange";
            showStateName = "visibilityState";
        } else if (typeof document.mozHidden !== "undefined") {
            showChange = "mozvisibilitychange";
            showStateName = "mozVisibilityState";
        } else if (typeof document.msHidden !== "undefined") {
            showChange = "msvisibilitychange";
            showStateName = "msVisibilityState";
        } else if (typeof document.webkitHidden !== "undefined") {
            showChange = "webkitvisibilitychange";
            showStateName = "webkitVisibilityState";
        }
        var self = this;
        document.addEventListener(showChange, function () {
            var isShow = Boolean(document[showStateName] == "visible");
            if (Boolean(document[showStateName] == "visible")) {
                //回到游戏
                self._resumeWebGame();
            } else {
                //暂停游戏
                self._pauseWebGame();
            }
        }, false);
    } else {
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
            if (callBack) {
                callBack();
                callBack = null;
            }
        });
    });
    return true;
};
GG._pauseWebGame = function () {
    if (this._gameState == G_TYPE.webGameState.pause) return;
    this._gameState = G_TYPE.webGameState.pause;
    //cc.Audio.useWebAudio = true
    cc.audioEngine.pauseAll();
    GG.audioMgr.stopMusic();
    var mgr = GG.curMgr;
    if (mgr && mgr.whenPauseGame) {
        mgr.whenPauseGame();
    }
    cc.sys.garbageCollect();
};
GG._resumeWebGame = function () {
    if (this._gameState == G_TYPE.webGameState.running) return;
    this._gameState = G_TYPE.webGameState.running;
    cc.audioEngine.resumeAll();
    GG.audioMgr.playMusic();
    var mgr = GG.curMgr;
    if (mgr && mgr.whenResumeGame) {
        mgr.whenResumeGame();
    }
    GG.socketMgr.parsePauseInfo();
};

cc._RFpop();
},{"AudioMgr":"AudioMgr","HTTPMgr":"HTTPMgr","M_Player":"M_Player","ReloadResMgr":"ReloadResMgr","SocketMgr":"SocketMgr","TablesMgr":"TablesMgr"}],"Grab_Manager":[function(require,module,exports){
"use strict";
cc._RFpush(module, '44fc1hvIW1Cf70BkdECoI0L', 'Grab_Manager');
// Script\Views\Scene_Grab\Grab_Manager.js

//押宝系统的管理

cc.Class({
    'extends': require('BaseManager'),

    properties: {
        _gameState: null,

        //data
        _list_winResult: null,
        _maxBettingGold: null,
        _homeID: null, //该房间的ID
        _maxBettingTimes: null, //最大投注次数
        _list_pokerIndex: null, //本局所有卡牌的索引
        _matchID: null, //赛事ID
        _playerID: null, //玩家自己的ID
        _nextStartTime: null, //下次赛事开始的时间戳
        _curEndGrabTime: null, //当前局投注结束时间
        _enterHomeData: null, //进房信息
        _idleTip: null, //提示休息中的tip
        _grabTip: null, //提示投注中的tip
        _isPauseOverMatch: null, //暂停的时候是否进入新的一局
        _dict_idleBetting: null, //吃瓜群众的投注记录

        comp_topLayer: {
            'default': null,
            type: require('Grab_topLayer'),
            displayName: '顶部层'
        },
        comp_goldContainer: {
            'default': null,
            type: require('Grab_goldContainer'),
            displayName: '金币容器'
        },
        comp_playersContainer: {
            'default': null,
            type: require('Grab_playersLayer'),
            displayName: '玩家容器'
        },
        comp_btnsContainer: {
            'default': null,
            type: require('Grab_btnContainer'),
            displayName: '底部按钮'
        },
        comp_tablesContainer: {
            'default': null,
            type: require('Grab_tablesLayer'),
            displayName: '压住格子层'
        },
        comp_pokersLayer: {
            'default': null,
            type: require('Grab_pokerLayer'),
            displayName: '卡牌层'
        },
        comp_uiLayer: {
            'default': null,
            type: require('Obj_uiContainer'),
            displayName: '窗口UI层'
        },
        label_bettingTimes: {
            'default': null,
            type: cc.Label,
            displayName: '投注次数'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        GG.grabMgr = this;
        this._super();

        this._list_pokerIndex = [];
        this._list_winResult = [];
        this._isPauseOverMatch = true;
        this._bettingTimesStr = G_CHINESE.limitBetting;
        this.comp_topEffect = this.comp_topLayer.getTopEffect();
    },

    start: function start() {
        // console.log('游戏版本'+G_Config_common.version)
        if (G_Config_common.isLocal) {} else {
            this.addLongListenOnStart();

            var homeData = GG.getPlayer().getInHomeData();
            if (homeData) {
                if (homeData.net_resetHomeData) {
                    this._dealInHomeNetData(homeData.net_inHomeData);
                    this.updateEnterHomeData(homeData.net_resetHomeData);
                    this.refreshHome();
                } else {
                    this.net_setHomeData(homeData.net_inHomeData);
                }
            }
        }
    },

    //=================================游戏流程

    resetShow: function resetShow() {
        this.node.stopAllActions();
        this.comp_pokersLayer.setStartInfo(this.comp_btnsContainer.getWheelPos(), this.comp_playersContainer.getPokerPos());

        //暂停的时候如果已经重新开局
        if (this._isPauseOverMatch) {
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

    on_Wait: function on_Wait(waitTime, callFunc) {
        this.setGameState(G_TYPE.grabState.wait);
        if (this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceEnd();
        }
        var time1 = new Date(GG.socketMgr.getServerTime());
        //console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(this._nextStartTime);
        //console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())
        if (this._idleTip && this._idleTip.getIsShow()) {
            //已经存在该tip
            //var serverTime = GG.socketMgr.getServerTime();
            //var secs = this._getOffSeconds(serverTime, this._nextStartTime);
            this._idleTip.forceChangeCountDown(waitTime);
        } else {
            var pos = G_DATA.getBottomTipPos();
            var dataObj = {
                tipIndex: 19,
                isCountDown: true,
                showPos: cc.p(pos.x, pos.y * 1.3),
                callBack: null
            };
            dataObj.retainTime = waitTime;
            if (callFunc) dataObj.callBack = callFunc;
            this._idleTip = GG.tipsMgr.showOnlyOne(dataObj);
        }
        this._clearByWait();
    },

    on_beforeStart: function on_beforeStart() {
        this.setGameState(G_TYPE.grabState.readyStart);
        this.comp_topEffect.playGameStartAni(this.on_startTouchTables.bind(this));
        this.on_giveOnePoker();
    },

    on_giveOnePoker: function on_giveOnePoker() {
        this.setGameState(G_TYPE.grabState.givePoker);

        //this.comp_pokersLayer.addPokers([{
        //    pokerIndex : this._list_pokerIndex[0],
        //    isOpen : true
        //}], this._list_winResult[0]);

        var pokerData = G_OBJ.data_grabPokerInfo();
        pokerData.pokerInfoList = [{
            pokerIndex: this._list_pokerIndex[0],
            isOpen: true
        }];
        pokerData.startPokerIndex = 1;
        pokerData.pokerResult = this._list_winResult[0];
        pokerData.isFlyEffect = true;

        this.comp_pokersLayer.showPokerEX(pokerData);
    },

    //on_beforeTouchTable : function () {
    //    this.setGameState(G_TYPE.grabState.readyTouchTable)
    //    this.comp_topEffect.playTouchStartAni(this.on_startTouchTables.bind(this));
    //},

    on_startTouchTables: function on_startTouchTables(retainTime) {
        this.setGameState(G_TYPE.grabState.touchTables);
        if (this._idleTip && this._idleTip.getIsShow()) {
            this._idleTip.forceEnd();
        }
        this.comp_tablesContainer.setTouchEnable(true);
        this.comp_btnsContainer.chooseDefaultMoney();

        if (this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceChangeCountDown(retainTime);
        } else {
            var secs;
            var pos = G_DATA.getBottomTipPos();
            var dataObj = {
                tipIndex: 18,
                showPos: cc.p(pos.x, pos.y * 1.3),
                isCountDown: true
            };
            if (G_Config_common.isLocal) {
                secs = 6;
                dataObj.callBack = this._touchTableEnd.bind(this);
            } else {
                secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
            }
            if (retainTime) secs = retainTime;
            dataObj.retainTime = secs;
            this._grabTip = GG.tipsMgr.showOnlyOne(dataObj);
        }
    },

    _touchTableEnd: function _touchTableEnd() {
        this.comp_tablesContainer.setTouchEnable(false);
        this.comp_btnsContainer.setBtnGroupEnable(false);
        if (G_Config_common.isLocal) {
            var netData = {
                pokers: [{
                    code: '33,34,4,36,2', //牌组 string 11，12
                    odds: 1, //倍数
                    cardsPoint: parseInt(5) }]
            };
            //this.net_grabEnd(netData);
            //点数
            var pokerInfo = this.setBettingEndInfo(netData);
            if (pokerInfo) this.comp_topEffect.playGrabDoneAni(this.on_giveFivePokers.bind(this), pokerInfo);
            this.on_Wait(15);

            this.comp_playersContainer.setMyselfMoney(this.getWinGold(), 58888);
            //庄家剩余金币
            this.comp_playersContainer.getSystemComp().setGoldValue(88888888);
        } else {}
    },

    on_giveFivePokers: function on_giveFivePokers(pokerInfo) {
        this.setGameState(G_TYPE.grabState.givePoker);
        var pokerData = G_OBJ.data_grabPokerInfo();
        pokerData.pokerInfoList = pokerInfo;
        pokerData.pokerResult = this._list_winResult[0];
        pokerData.isFlyEffect = true;
        pokerData.startPokerIndex = 2;
        pokerData.callFunc = this._giveFivePokersEnd.bind(this);
        this.comp_pokersLayer.showPokerEX(pokerData);
    },

    _giveFivePokersEnd: function _giveFivePokersEnd() {
        var time = G_Config_grab.time_openDelay;
        var act = cc.delayTime(time);
        this.node.runAction(cc.sequence(act, cc.callFunc(function () {
            //this.comp_pokersLayer.resetPokerInfo();
            this.comp_pokersLayer.openPokers(this.on_showResult.bind(this));
        }, this)));
    },

    on_showResult: function on_showResult() {
        this.setGameState(G_TYPE.grabState.showFlag);

        if (this._list_winResult.length < 1) return;
        var pokerResult, tablePos;
        for (var key in this._list_winResult) {
            pokerResult = this._list_winResult[key];
            tablePos = this.comp_tablesContainer.setTableWin(pokerResult);
            this.comp_topEffect.playOneFlag(tablePos);
        }
        this.comp_tablesContainer.showOwnerGold(this.getMyselfComp().getAllGrabTable());

        this.node.runAction(cc.sequence(cc.delayTime(G_Config_grab.time_showFlag), cc.callFunc(this.on_flyGold, this)));
    },
    //金币的飞行
    on_flyGold: function on_flyGold() {
        this.setGameState(G_TYPE.grabState.flyGold);
        this.comp_playersContainer.setWinTableResult(this._list_winResult);
        this.node.runAction(cc.sequence(cc.delayTime(1.5), cc.callFunc(function () {
            //金币飞行结束后的表现
            this._flyGoldEnd();
        }, this)));
    },
    _flyGoldEnd: function _flyGoldEnd() {
        var isShow = this.comp_playersContainer.showAllGoldResult();
        if (isShow) {
            var act = cc.delayTime(G_Config_grab.time_goldFlyOnHead);
            this.node.runAction(cc.sequence(act, cc.callFunc(this.on_showGoldResultEnd, this)));
        } else this.on_showGoldResultEnd();
    },
    //显示头像上的金币增减结束
    on_showGoldResultEnd: function on_showGoldResultEnd() {
        //this.resetShow();
        if (G_Config_common.isLocal) {
            //this.on_beforeStart();
        } else {}
    },

    //================================

    //点击投注区域
    playMyGoldMove: function playMyGoldMove(dataObj) {
        dataObj.isMine = true;
        this.getMyselfComp().touchGrabArea(dataObj, this._ownerGrabContinue.bind(this));
    },
    //服务端投注成功
    _ownerGrabContinue: function _ownerGrabContinue(dataObj) {
        if (dataObj.goldValue > 0) {
            this.comp_goldContainer.moveAddGolds(dataObj, (function () {
                //所有金币飞行完毕
            }).bind(this));
            //请求成功则马上刷新桌子上的金额信息
            this.comp_tablesContainer.getTable(dataObj.tableIndex).addOwnerGold(dataObj.goldValue);
        }
    },
    //结算移动金币
    playResultGoldMove: function playResultGoldMove(dataList) {
        //if(dataList.length < 1) this._flyGoldEnd();
        //else
        this.comp_goldContainer.flyResultGold(dataList);
    },

    setGameState: function setGameState(type) {
        var lastType = this._gameState;
        this._gameState = type;
        switch (type) {
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
    initButtonInfo: function initButtonInfo() {
        this.comp_btnsContainer.setStartInfo();
    },

    //===========================================

    //重新修改休息期间倒计时数值
    changeIdleTip: function changeIdleTip(num) {
        if (this._idleTip && this._idleTip.getIsShow()) {
            var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime);
            this._idleTip.forceChangeCountDown(secs);
        }
    },
    //重新修改投注期间倒计时数值
    changeGrabTip: function changeGrabTip(num) {
        var secs = this._getOffSeconds(GG.socketMgr.getServerTime(), this._curEndGrabTime);
        if (this._grabTip && this._grabTip.getIsShow()) {
            this._grabTip.forceChangeCountDown(secs);
        } else {
            //投注动画未执行完成
            this.comp_topEffect.clearAll();

            if (!this.comp_pokersLayer.getPokerNum()) {
                //还没有发第一张牌
                //this.comp_pokersLayer.addPokers([{
                //    pokerIndex : this._list_pokerIndex[0],
                //    isOpen : true
                //}], this._list_winResult[0]);

                var pokerData = G_OBJ.data_grabPokerInfo();
                pokerData.pokerInfoList = [{
                    pokerIndex: this._list_pokerIndex[0],
                    isOpen: true
                }];
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
    showDialogUI: function showDialogUI(uiName, callBack) {
        this.comp_uiLayer.addUI(uiName, callBack);
    },

    //===========================================
    //设置投注次数的显示
    setBettingTimes: function setBettingTimes(times) {
        this.label_bettingTimes.string = G_TOOL.formatStr(this._bettingTimesStr, times);
    },

    getGameState: function getGameState() {
        return this._gameState;
    },
    getMyselfComp: function getMyselfComp() {
        return this.comp_playersContainer.getMyselfComp();
    },
    getTable: function getTable(result) {
        return this.comp_tablesContainer.getTable(result);
    },
    getBtnsComp: function getBtnsComp() {
        return this.comp_btnsContainer;
    },
    getMaxGrabGold: function getMaxGrabGold() {
        return this._maxBettingGold;
    },
    //测试用：主动计算区域金币值
    getWinGold: function getWinGold() {
        var tableIndex,
            goldNum,
            winList = [];
        for (var key in this._list_winResult) {
            tableIndex = this._list_winResult[key];
            goldNum = this.getMyselfComp().getWinGoldNum(tableIndex);
            if (goldNum) winList.push(tableIndex);
        }
        return this.comp_tablesContainer.getWinGold(winList);
    },
    //每局最大的投注次数
    getMaxGrabTimes: function getMaxGrabTimes() {
        return this._maxBettingTimes;
    },
    getMatchID: function getMatchID() {
        return this._matchID;
    },
    getOwnerID: function getOwnerID() {
        return this._playerID;
    },
    _getOffSeconds: function _getOffSeconds(startTime, endTime) {
        return GG.socketMgr.getOffSeconds(startTime, endTime);
    },
    getCurGrabResult: function getCurGrabResult() {
        return this._list_winResult[0];
    },
    getHomeID: function getHomeID() {
        return this._homeID;
    },
    //是否已经投注结束
    getIsGrab: function getIsGrab() {
        return this.getMyselfComp().getIsBettingEnd();
    },
    //获取当前比赛是否已经开局
    getIsBetStart: function getIsBetStart(_nextStartTime, _curEndGrabTime) {
        if (!_nextStartTime) _nextStartTime = this._nextStartTime;
        if (!_curEndGrabTime) _curEndGrabTime = this._curEndGrabTime;
        var serverTime = GG.socketMgr.getServerTime();
        if (_nextStartTime > serverTime && serverTime >= _curEndGrabTime) {
            //未开局
            return false;
        }
        return true;
    },
    //第一次进入游戏是否可押注
    getFirstBetting: function getIsWait() {
        return this._startGame;
    },
    //获取进房信息
    getEnterHomeData: function getEnterHomeData() {
        return this._enterHomeData;
    },
    //获取吃瓜群众投注的位置
    getIdleBettingPos: function getIdleBettingPos() {
        return this.comp_btnsContainer.getIdleBettingPos();
    },
    //获取吃瓜群众的投注记录
    getIdleBetting: function getIdleBetting() {
        return this._dict_idleBetting;
    },
    //获取投注区域容器
    getTableContainer: function getTableContainer() {
        return this.comp_tablesContainer;
    },

    //记录吃瓜群众的投注
    _recodeIdleBetting: function _recodeIdleBetting(tableIndex, goldImgNum) {
        if (!this._dict_idleBetting) this._dict_idleBetting = {};
        if (this._dict_idleBetting[tableIndex]) this._dict_idleBetting[tableIndex] += goldImgNum;else this._dict_idleBetting[tableIndex] = goldImgNum;
    },
    //开启休息时间的时候的清理
    _clearByWait: function _clearByWait() {
        this.getMyselfComp().clearRecord();
    },

    //on net -----------

    forceStart: function forceStart() {

        if (G_Config_common.isLocal) {
            var chip = [100, 500, 1000, 2000, 5000];
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
                        usableBalance: 7568
                    },
                    seatIndex: 1
                }, {
                    player: {
                        coin: 7568,
                        icon: null,
                        nickname: "black",
                        playerId: 5,
                        usableBalance: 7568
                    },
                    seatIndex: 2
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "solider",
                        playerId: 6,
                        usableBalance: 888888
                    },
                    seatIndex: 3
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin",
                        playerId: 7,
                        usableBalance: 888888
                    },
                    seatIndex: 4
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin1",
                        playerId: 8,
                        usableBalance: 888888
                    },
                    seatIndex: 5
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin2",
                        playerId: 9,
                        usableBalance: 888888
                    },
                    seatIndex: 6
                }, {
                    player: {
                        coin: 888888,
                        icon: null,
                        nickname: "jin3",
                        playerId: 9,
                        usableBalance: 888888
                    },
                    seatIndex: 7
                }],
                dealerData: {
                    coin: 297300,
                    icon: null,
                    nickname: "系统大牛",
                    playerId: 7,
                    usableBalance: 74325
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
                    areaInfo: { 0: 2, 1: 13, 2: 13, 3: 13, 4: 13, 5: 13, 6: 13, 7: 13, 8: 13, 9: 13, 10: 12 }
                }
            };
            this._initInHomeData(initData);

            //是否开局判定
            var serverTime = 1487559484012;
            if (serverTime < this._curEndGrabTime) {
                //未开局
                this.resetShow();
                //是否在游戏进行一半的时候进入
                var firstPokerIndex = 30;
                var secs = this._getOffSeconds(serverTime, this._curEndGrabTime);
                if (firstPokerIndex > 0) {
                    this._list_pokerIndex[0] = firstPokerIndex;
                    //游戏中途加入,计算剩余秒数
                    if (secs > G_Config_grab.time_grabWillEnd) {
                        //充足的发牌时间
                        //this.comp_pokersLayer.addPokers([{
                        //    pokerIndex : firstPokerIndex,
                        //    isOpen : true
                        //}]);

                        var pokerData = G_OBJ.data_grabPokerInfo();
                        pokerData.pokerInfoList = [{
                            pokerIndex: firstPokerIndex,
                            isOpen: true
                        }];
                        pokerData.pokerResult = 0;
                        pokerData.isFlyEffect = false;
                        pokerData.startPokerIndex = 1;
                        this.comp_pokersLayer.showPokerEX(pokerData);
                    } else {
                        //不播放发牌动画
                        this.comp_pokersLayer.onlyShowPoker([{
                            pokerIndex: firstPokerIndex,
                            isOpen: true
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
        } else {}
    },

    testGetPoker: function testGetPoker(getNum) {
        var p1 = [40, 44, 48, 5, 10];
        var p2 = [36, 37, 39, 10, 11];
        var p3 = [39, 41, 50, 13, 22];
        var p4 = [5, 31, 38, 0, 29];
        var p5 = [33, 34, 4, 36, 2];
        var pokerList = [{ 5: p1 }, { 6: p2 }, { 10: p3 }, { 9: p4 }, { 1: p5 }];
        if (!this._testPokerList) this._testPokerList = pokerList[G_TOOL.getRandomArea(0, 4)];
        if (getNum == 1) {
            var info = {};
            for (var key in this._testPokerList) {
                info.value = key;
                info.pokerIndex = this._testPokerList[key][0];
                break;
            }
            return info;
        } else {
            var info = {};
            for (var key in this._testPokerList) {
                info.value = key;
                info.pokerIndex = this._testPokerList[key];
            }
            return info;
        }
    },

    //初始化押宝房间必须要有的数据
    _initInHomeData: function _initInHomeData(initData) {
        //房间配置的数据
        if (initData.homeData) {
            var data = initData.homeData;
            if (!this._homeID) this._homeID = data.roomId;
            if (!this._matchID) this._matchID = data.matchId;
            this._nextStartTime = data.beginTimeNext;
            this._curEndGrabTime = data.settleTime;
            //if(data.minDealerGold) this._minDealerGold = data.minDealerGold;
            if (data.userId) {
                GG.getPlayer().setPlayerID(data.userId);
            }
            //最大投注次数
            if (data.betTimes) {
                this._maxBettingTimes = data.betTimes;
                this.setBettingTimes(this._maxBettingTimes);
            }
            //筹码选项
            if (data.betChip) {
                this._chipValueList = data.betChip;
                this._maxBettingGold = this._maxBettingTimes * data.betChip[data.betChip.length - 1];
                //设置初始化数据表现
                //this.comp_btnsLayer.initChipBtn(data.betChip);
            }
            //投注区域倍数
            if (data.areaInfo) {
                this.comp_tablesContainer.setStartInfo(data.areaInfo);
            }
        }
        //玩家自己的数据
        //其他玩家的数据
        //庄家的数据
        this.comp_playersContainer.setStartInfo(initData.othersData, initData.myselfData, initData.dealerData);
        //判定庄家
        if (initData.dealerData) {
            if (initData.dealerData.playerId == GG.getPlayerID()) {
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
    net_setHomeData: function net_setHomeData(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        this._dealInHomeNetData(recvData);

        //是否开局判定
        var serverTime = GG.socketMgr.getServerTime();
        //var isWait = this._nextStartTime > serverTime && serverTime >= this._curEndGrabTime;
        if (serverTime < this._curEndGrabTime) {
            //已经开局
            this.resetShow();
            this._startGame = true;
            //是否在游戏进行一半的时候进入
            var firstPokerIndex = recvData.poker;
            var secs = this._getOffSeconds(serverTime, this._curEndGrabTime);
            if (firstPokerIndex >= 0) {
                this._list_pokerIndex[0] = firstPokerIndex;
                var pokerData = G_OBJ.data_grabPokerInfo();
                pokerData.pokerInfoList = [{
                    pokerIndex: firstPokerIndex,
                    isOpen: true
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
        } else {
            //未开局
            this._isPauseOverMatch = true;
            this.resetShow();
            this._startGame = false;
            var secs = this._getOffSeconds(serverTime, this._nextStartTime);
            if (secs <= 0) {
                //卡时间点了,直接开始投注
                var firstPokerIndex = recvData.poker;
                secs = this._getOffSeconds(serverTime, this._curEndGrabTime);
                if (firstPokerIndex >= 0) {
                    this._list_pokerIndex[0] = firstPokerIndex;
                    var pokerData = G_OBJ.data_grabPokerInfo();
                    pokerData.pokerInfoList = [{
                        pokerIndex: firstPokerIndex,
                        isOpen: true
                    }];
                    pokerData.isFlyEffect = false;
                    pokerData.startPokerIndex = 1;
                    this.comp_pokersLayer.showPokerEX(pokerData);
                }
                // else console.log('已经开局，但没有收到服务端卡牌信息'+firstPokerIndex)
                this.on_startTouchTables(secs);
            } else this.on_Wait(secs);
        }
    },

    //押宝——处理请求到的进房信息
    _dealInHomeNetData: function _dealInHomeNetData(recvData) {
        this._enterHomeData = recvData;
        var match = recvData.match;

        var time1 = new Date(GG.socketMgr.getServerTime());
        //console.log('当前：时:'+time1.getHours()+'分:'+time1.getMinutes()+'秒:'+time1.getSeconds())
        var time2 = new Date(match.settleTime);
        //console.log('下次开局：时:'+time2.getHours()+'分:'+time2.getMinutes()+'秒:'+time2.getSeconds())

        var id = GG.getPlayerID();
        var initData = {
            myselfData: {
                chooseDict: recvData.betChip,
                roomName: recvData.roomName
            },
            othersData: recvData.seats,
            dealerData: match.dealer,
            homeData: {
                roomId: recvData.roomId,
                userId: id,
                betTimes: recvData.betTimes,
                matchId: match.matchId,
                beginTimeNext: match.beginTimeNext,
                settleTime: match.settleTime,
                betChip: recvData.betChip,
                minDealerGold: recvData.minDealerCoin,
                areaInfo: recvData.betAreas
            }
        };
        this._initInHomeData(initData);
    },

    //重新设置开局数据
    net_resetGameData: function net_resetGameData(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            return;
        }
        this.updateEnterHomeData(recvData);
        this.resetShow();
        var match = recvData.match;
        var id = GG.getPlayerID();
        var initData = {
            myselfData: {
                chooseDict: null,
                roomName: null
            },
            othersData: recvData.seats,
            dealerData: match.dealer,
            homeData: {
                //roomId : recvData.roomId,
                //userId : id,
                //betTimes : recvData.betTimes,
                //matchId : match.matchId,
                beginTimeNext: match.beginTimeNext,
                settleTime: match.settleTime
            }
        };
        //betChip : recvData.betChip,
        //minDealerGold : recvData.minDealerCoin,
        //areaInfo : recvData.betAreas
        this._initInHomeData(initData);

        //记录第一张卡牌
        this._list_pokerIndex[0] = recvData.poker;
        this.on_beforeStart();
    },

    //当有某个玩家投注,包括自己
    net_otherGrab: function net_otherGrab(recData) {
        var tip = recData.tip;
        if (tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        var others = recData.bets,
            netBeanData;
        for (var i = 0; i < others.length; i++) {
            netBeanData = others[i];
            //if(netBeanData.seatIndex===this.getMyselfComp().getSeatIndex())continue;
            var areaValue = parseInt(netBeanData.item.split('_')[0]);
            //投注信息
            var bettingData = G_OBJ.data_grabBetting();
            bettingData.goldNum = G_Config_grab.num_otherGrabGoldNum;
            bettingData.goldValue = netBeanData.gold;
            bettingData.tableIndex = areaValue;
            bettingData.isMine = false;

            switch (netBeanData.seatIndex) {
                case this.getMyselfComp().getSeatIndex():
                    //自己的投注
                    break;
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
                    break;
                case G_DATA.getDealerSeatIndex():
                    //庄家
                    break;
                default:
                    //正常座位上玩家
                    var playerComp = this.comp_playersContainer.getCompByIndex(netBeanData.seatIndex);
                    if (playerComp) {
                        bettingData.startPos = playerComp.getWorldPos();
                        playerComp.doChoose(bettingData.tableIndex, bettingData.goldNum);
                    }
                    bettingData = this.comp_tablesContainer.touchOneTable(bettingData.tableIndex, bettingData);
                    this.comp_goldContainer.moveAddGolds(bettingData);
                    break;
            }
        }
    },

    //结束投注，进入结算
    net_grabEnd: function net_grabEnd(recvData) {
        this._touchTableEnd();
        var pokerInfo = this.setBettingEndInfo(recvData);
        if (pokerInfo) this.comp_topEffect.playGrabDoneAni(this.on_giveFivePokers.bind(this), pokerInfo);
        this.on_Wait(this._getOffSeconds(GG.socketMgr.getServerTime(), this._nextStartTime));
    },

    setBettingEndInfo: function setBettingEndInfo(recvData) {
        var pokerInfo, pokerIndex, pokerData;
        if (G_Config_common.isLocal) {} else {
            var tip = recvData.tip;
            switch (tip.code) {
                case G_TYPE.serverCodeType.success:
                    break;
                case G_TYPE.serverCodeType.noBetting:
                    break;
                default:
                    return null;
            }
            this._super(recvData);
        }
        //整理卡牌数据
        pokerData = recvData.pokers[0];
        var pokerList = pokerData['code'].split(",");
        pokerInfo = [];
        for (var i = 0; i < pokerList.length; i++) {
            pokerIndex = parseInt(pokerList[i]);
            if (!isNaN(pokerIndex)) {
                pokerInfo.push({
                    pokerIndex: pokerIndex,
                    isOpen: false
                });
            }
        }

        this._list_winResult.push(pokerData.cardsPoint);
        //其他玩家
        var playerList = recvData.players;
        if (playerList) {
            var player, playerComp;
            for (var i = 0; i < playerList.length; i++) {
                player = playerList[i];
                //if(player.playerId == GG.getPlayer().getPlayerID()) continue;

                switch (player.seatIndex) {
                    case G_DATA.getDealerSeatIndex():
                        //庄家剩余金币
                        this.comp_playersContainer.getSystemComp().setGoldValue(player.balance);
                        break;
                    case G_DATA.getIdlePlayersSeatIndex():
                        break;
                    default:
                        //场上玩家
                        playerComp = this.comp_playersContainer.getCompByIndex(player.seatIndex);
                        if (playerComp) playerComp.setResultInfo(player.coin, player.balance);
                        break;
                }
            }
        }
        return pokerInfo;
    },

    //结束投注，如果自己有投注行为
    net_mineHasGrab: function net_mineHasGrab(recvData) {
        var tip = recvData.tip;
        if (tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        var myInfo = recvData.player;
        this.comp_playersContainer.setMyselfMoney(myInfo.coin, myInfo.balance);
    },

    //有人入座
    net_someOneInHome: function net_someOneInHome(recData) {
        var tip = recData.tip;
        if (tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        var player,
            playerList = recData.seats,
            myPlayerID = GG.getPlayerID();
        for (var i = 0; i < playerList.length; i++) {
            player = playerList[i];
            if (!player || player.player.playerId == myPlayerID) continue;
            this.comp_playersContainer.enterOnePlayer(player.seatIndex, player.player);
        }
    },

    //退出房间的请求
    net_exitHome: function net_exitHome(recData) {
        var tip = recData.tip;
        if (tip.code != '0') {
            // console.log(tip.tip);
            return;
        }

        GG.exitHome();
    },

    //游戏中长时间没有做操作
    net_idleTimeOut: function net_idleTimeOut(recData) {
        GG.tipsMgr.showConfirmTip_ONE(recData.tip.tip, (function () {
            GG.exitHome();
        }).bind(this));
    },

    _getWaitTimeSec: function _getWaitTimeSec(waitTime) {
        var serverTime = GG.socketMgr.getServerTime();
        var date = new Date(waitTime - serverTime);
        return date.getSeconds();
    },

    addLongListenOnStart: function addLongListenOnStart() {
        GG.socketMgr.registerLong(NetType.r_grab_reStartGame, this.net_resetGameData.bind(this));
        GG.socketMgr.registerLong(NetType.r_grabEnd, this.net_grabEnd.bind(this));
        GG.socketMgr.registerLong(NetType.r_mineHaveGrab, this.net_mineHasGrab.bind(this));
        GG.socketMgr.registerLong(NetType.r_oneInHome, this.net_someOneInHome.bind(this));
        GG.socketMgr.registerLong(NetType.r_grab_exitReturn, this.net_exitHome.bind(this));
        GG.socketMgr.registerLong(NetType.r_idleTimeOut, this.net_idleTimeOut.bind(this));
        GG.socketMgr.registerLong(NetType.r_otherGrab, this.net_otherGrab.bind(this));
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    onDestroy: function onDestroy() {
        this._super();
        GG.grabMgr = null;
    }
});

cc._RFpop();
},{"BaseManager":"BaseManager","Grab_btnContainer":"Grab_btnContainer","Grab_goldContainer":"Grab_goldContainer","Grab_playersLayer":"Grab_playersLayer","Grab_pokerLayer":"Grab_pokerLayer","Grab_tablesLayer":"Grab_tablesLayer","Grab_topLayer":"Grab_topLayer","Obj_uiContainer":"Obj_uiContainer"}],"Grab_btnContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '26aca2lT0ZA06ebEnuK1Os6', 'Grab_btnContainer');
// Script\Views\Scene_Grab\Grab_btnContainer.js

//按钮管理类

cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        _lastBtn: null,

        node_btnGroup: cc.Node,
        // node_effectOnButton : cc.Node,
        node_effectUnderButton: cc.Node,
        node_exitHomeBtn: cc.Node, //退出房间的按钮
        //右下角的按钮
        node_playersList: cc.Node,
        node_rules: cc.Node,
        node_trend: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();

        this._registerAllBtn();
    },

    setStartInfo: function setStartInfo() {
        var valueDict = GG.grabMgr.getMyselfComp().getMoneyChooseDict();
        this._setBtnInfo(valueDict);

        this.setBtnGroupEnable(false);
    },

    _init: function _init() {
        this._btnCompName = 'Button_multiple';
        this._isAdaptPos = false;
    },
    //更新押注按钮可点击
    changeBet: function changeBet(playerGoldValue) {
        var valueDict = GG.grabMgr.getMyselfComp().getMoneyChooseDict(),
            playerGold;
        if (!playerGoldValue) {
            playerGold = GG.grabMgr.getMyselfComp().getGoldValue();
        } else {
            playerGold = playerGoldValue;
        }
        this._changeBtn(valueDict, playerGold);
    },
    _changeBtn: function _changeBtn(valueDict, playerGoldValue) {
        var btns = this.node_btnGroup.children,
            index;
        for (var i = 0; i < btns.length; i++) {
            index = parseInt(i);
            if (valueDict[index] > playerGoldValue) {
                this.setOneBtnEnable(i);
            }
        }
    },
    _registerAllBtn: function _registerAllBtn() {
        var btns = this.node_btnGroup.children;
        for (var key in btns) {
            this.registerButton(btns[key], this.OnClick_chooseMoney, this, parseInt(key), true);
        }
        this.node_exitHomeBtn.parent.getComponent('Obj_leftTopMenu').bindFunc(this.OnClick_exit.bind(this), this.OnClick_changeDesk.bind(this));
        //右下角按钮
        this.registerButton(this.node_playersList, this.OnClick_playerList, this);
        this.registerButton(this.node_rules, this.OnClick_rules, this);
        this.registerButton(this.node_trend, this.OnClick_trend, this);
    },
    OnClick_chooseMoney: function OnClick_chooseMoney(event, userData) {
        var chooseLevel = parseInt(userData);
        GG.grabMgr.getMyselfComp().setChooseLevel(chooseLevel);
        this._showEffect(event.target);
    },
    //点击换桌
    OnClick_changeDesk: function OnClick_changeDesk() {},
    //点击退出房间
    OnClick_exit: function OnClick_exit(event) {
        if (G_Config_common.isLocal) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                GG.exitHome();
            });
            return;
        }

        if (GG.grabMgr.getIsGrab()) {
            GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText1, function () {
                var netData = {
                    roomId: GG.grabMgr.getHomeID(),
                    isBreak: true
                };
                GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
            });
        } else {
            var netData = {
                roomId: GG.grabMgr.getHomeID(),
                isBreak: true
            };
            GG.socketMgr.SendMsg(NetType.s_grab_Exit, netData);
        }
    },
    //点击玩家列表
    OnClick_playerList: function OnClick_playerList() {
        var data = 1;
        var layer = GG.grabMgr.showDialogUI(G_RES_URL.uiName.playerList, function (layer) {
            layer.showLayer();
            layer.setData(data);
        });
    },
    OnClick_rules: function OnClick_rules() {
        var data = G_TYPE.gameModule.grab;
        var layer = GG.grabMgr.showDialogUI(G_RES_URL.uiName.rulesInHome, function (layer) {
            layer.showLayer();
            layer.setData(data);
        });
    },
    //点击走势图列表
    OnClick_trend: function OnClick_trend() {
        var data = GG.grabMgr.getHomeID();
        var layer = GG.grabMgr.showDialogUI(G_RES_URL.uiName.grabTrend, function (layer) {
            layer.showLayer();
            layer.setData(data);
        });
    },

    _testGet: function _testGet() {},

    _setBtnInfo: function _setBtnInfo(valueDict) {
        var btns = this.node_btnGroup.children,
            index;
        for (var i = 0; i < btns.length; i++) {
            index = parseInt(i);
            btns[i].tag = valueDict[index];
            btns[i].getComponent(this._btnCompName).showMoney(valueDict[index], false);
        }
    },
    _getBtnByIndex: function _getBtnByIndex(index) {
        return this.node_btnGroup.children[index];
    },
    chooseDefaultMoney: function chooseDefaultMoney() {
        this.setBtnGroupEnable(true);
        this.changeBet(); //下注开始之后设置可押注的按钮
        this.chooseMoney(GG.grabMgr.getMyselfComp().getChooseLevel());
    },
    chooseMoney: function chooseMoney(index) {
        var btn = this._getBtnByIndex(index);
        //获取第一次进房是否可下注
        this.isBetting = GG.grabMgr.getFirstBetting();
        this._showEffect(btn);
    },

    //move touch effect
    _showEffect: function _showEffect(targetBtn) {
        this._chooseBtn(targetBtn);

        //effect
        // //this.node_effectOnButton.active = true;
        this.node_effectUnderButton.active = true;
        //
        // //this.node_effectOnButton.position = cc.p(targetBtn.x, targetBtn.y);;
        this.node_effectUnderButton.x = targetBtn.x;
        this.node_effectUnderButton.y = -targetBtn.height / 2;
        //刚进入游戏如果可下注 强制自适应
        if (this.isBetting && !this._isAdaptPos) {
            this.node_effectUnderButton.position = G_TOOL.adaptPos(this.node_effectUnderButton.position);
            var size = G_TOOL.adaptSize(targetBtn.width, targetBtn.height);
            this.node_effectUnderButton.width = size.width;
            this._isAdaptPos = true;
        } else {
            this.node_effectUnderButton.width = targetBtn.width;
        }
        ////ani
        // //this.node_effectOnButton.getComponent(cc.Animation).play();
    },
    _hideEffect: function _hideEffect() {
        // this.node_effectOnButton.active = false;
        this.node_effectUnderButton.active = false;
    },

    //button touch move up
    _chooseBtn: function _chooseBtn(targetBtn) {
        this._cancelChoose();
        targetBtn.getComponent(this._btnCompName).showMoney(null, true);
        //targetBtn.y += targetBtn.height/2;
        this.setBtnEnable(targetBtn, false, true);
        this._lastBtn = targetBtn;
    },
    _cancelChoose: function _cancelChoose() {
        if (this._lastBtn) {
            this._hideEffect();
            this._lastBtn.getComponent(this._btnCompName).showMoney(null, false);
            this.setBtnEnable(this._lastBtn, true);
            this._lastBtn.y = 0;
            this._lastBtn = null;
        }
    },

    setBtnGroupEnable: function setBtnGroupEnable(isEnable) {
        this._cancelChoose();
        var btnGroup = this.node_btnGroup.children;
        for (var key in btnGroup) {
            this.setBtnEnable(btnGroup[key], isEnable);
        }
    },
    //设置部分按钮置灰
    setOneBtnEnable: function setOneBtnEnable(num) {
        var key,
            keyInt,
            btns = this.node_btnGroup.children;
        for (key in btns) {
            keyInt = parseInt(key);
            if (num - 1 < keyInt) {
                this.setBtnEnable(btns[key], false);
                btns[key].y = 0;
                btns[key].getComponent(this._btnCompName).showMoney(null, false);
            }
        }
    },
    //获取轮子装饰物的位置
    getWheelPos: function getWheelPos() {
        var visitRect = cc.visibleRect;
        return cc.p(visitRect.width * 0.8, visitRect.height * 0.2);
    },
    //获取吃瓜群众投注起点
    getIdleBettingPos: function getIdleBettingPos() {
        var pos = this.node_playersList.position;
        var basePos = this.node_playersList.parent.parent.position;
        var posX = pos.x + this.node_playersList.parent.x + basePos.x + this.node_playersList.width / 2;
        var posY = pos.y + this.node_playersList.parent.y + basePos.y + this.node_playersList.height / 2;
        return cc.p(posX, posY);
    }
});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Grab_goldContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '93263ztXJhLmK5n7/NR+HHn', 'Grab_goldContainer');
// Script\Views\Scene_Grab\Grab_goldContainer.js

//金币流动效果的处理层

//var goldInfo = {
//    startPos : cc.p(),
//    targetPos : cc.p(),
//    goldNum : 4,
//    userData : '',
//    tableIndex : 1,
//    callFunc : '',
//}

cc.Class({
    'extends': cc.Component,

    properties: {
        _pool: null,
        _callFunc: null,

        frame_gold: cc.SpriteFrame
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._goldScale = G_Config_common.scale_moveGold;
        this._addGoldTime = G_Config_grab.time_goldFly_Add;
        this._resultGoldTime = G_Config_grab.time_goldFly_Result;

        this._pool = new cc.NodePool('Grab_goldContainer');
    },

    moveAddGolds: function moveAddGolds(goldInfo, callFunc) {
        //this._callFunc = callFunc;
        GG.audioMgr.playSound(15);
        var gold,
            act1,
            act2,
            delayTime = 0,
            delayValue = G_Config_grab.time_goldFly_delay;
        for (var i = 0; i < goldInfo.goldNum; i++) {
            gold = this._getGold();
            gold.position = goldInfo.startPos;
            gold._flyCallFunc = goldInfo.callFunc;

            act1 = cc.delayTime(delayTime);
            act2 = cc.moveTo(this._addGoldTime, goldInfo.targetPos);
            gold.runAction(cc.sequence(act1, act2, cc.callFunc(this._flyAddGoldEnd, this, goldInfo)));
            delayTime += delayValue;
        }
        if (gold) gold._endCallFunc = callFunc;
    },
    _flyAddGoldEnd: function _flyAddGoldEnd(target, goldInfo) {
        var gold = target;
        if (gold._flyCallFunc) {
            gold._flyCallFunc();
            gold._flyCallFunc = null;
        }
        if (gold._endCallFunc) {
            gold._endCallFunc();
            gold._endCallFunc = null;
        }
        this._removeGold(gold);
    },

    //========================重构金币的回收显示

    flyResultGold: function flyResultGold(dataList, callFunc) {
        if (!dataList) return;
        var dataObj;
        this._callFunc = callFunc;
        for (var i = 0; i < dataList.length; i++) {
            dataObj = dataList[i];
            if (dataObj) {
                this._setResultGolds(dataObj);
            }
        }
    },
    _setResultGolds: function _setResultGolds(dataObj) {
        for (var i = 0; i < dataObj.goldNum; i++) {
            var gold = this._getGold();
            var table = GG.grabMgr.getTable(dataObj.tableIndex);
            var startPos = table.removeOneGoldImg();
            if (!startPos) continue;
            gold.position = startPos;

            var act1 = cc.moveTo(this._resultGoldTime, dataObj.targetPos);
            var self = this;
            gold.runAction(cc.sequence(act1, cc.callFunc(function (target) {
                self._removeGold(target);
                if (self.callBack) {
                    self.callBack();
                    self.callBack = null;
                }
            }, this, this._resultGoldNum)));
        }
    },

    //剩余的金币全部移动到系统庄
    recordLeaveGolds: function recordLeaveGolds(targetPos) {},

    //addResultGolds : function (dataList, callFunc) {
    //    if(dataList.length < 1) return;
    //    this._callFunc = callFunc;
    //    this._list_resultGold = dataList;
    //    this._resultGoldNum = 0;
    //
    //    this._update_resultGold();
    //    this.schedule(this._update_resultGold, G_Config_grab.time_goldFly_delay);
    //    GG.audioMgr.playSound(18);
    //},
    //_update_resultGold : function () {
    //    if(this._list_resultGold.length > 0){
    //        var dataObj,gold,table,act1;
    //        for(var i = this._list_resultGold.length-1; i >= 0; i --){
    //            dataObj = this._list_resultGold[i];
    //            if(dataObj.goldNum > 0){
    //                gold = this._getGold();
    //                this._resultGoldNum += 1;
    //
    //                table = GG.grabMgr.getTable(dataObj.tableIndex);
    //                table.removeOneGoldImg();
    //                gold.position = table.getWorldPos();
    //
    //                act1 = cc.moveTo(this._resultGoldTime, dataObj.targetPos);
    //                gold.runAction(cc.sequence(act1, cc.callFunc(this._flyResultGoldEnd, this, this._resultGoldNum)));
    //                dataObj.goldNum -= 1;
    //            }else {
    //                if(dataObj.callFunc) dataObj.callFunc(dataObj.tableIndex);
    //                this._list_resultGold.splice(i, 1);
    //                if(this._list_resultGold.length <= 0){
    //                    this.unschedule(this._update_resultGold);
    //                    this._list_resultGold = null;
    //                }
    //            }
    //        }
    //    }
    //},
    //_flyResultGoldEnd : function (target, userData) {
    //    this._removeGold(target);
    //    if(!this._list_resultGold && userData == this._resultGoldNum){
    //        this._resultGoldNum = 0;
    //        if(this._callFunc){
    //            this._callFunc();
    //            this._callFunc = null;
    //        }
    //    }
    //},

    _getGold: function _getGold() {
        var goldNode = this._pool.get();
        if (!goldNode) {
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this.frame_gold;
        }
        goldNode.parent = this.node;
        goldNode.scale = this._goldScale;
        goldNode.active = true;
        return goldNode;
    },

    _removeGold: function _removeGold(goldeNode) {
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    clearAll: function clearAll() {
        this.node.stopAllActions();
        var goldList = this.node.children;
        var num = goldList.length;
        for (var i = num - 1; i >= 0; i--) {
            this._removeGold(goldList[i]);
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Grab_oneTable":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'cd914lZMLpN6b4JTXgsnLK+', 'Grab_oneTable');
// Script\Views\Scene_Grab\Grab_oneTable.js

//单个投注区域格子

cc.Class({
    'extends': cc.Component,

    properties: {
        _ownerGold: 0,
        _areaGold: 0,
        _goldFrame: null,
        _bullType: null,
        _multiple: 1,
        _maxAdaptGold: null,
        _curGoldNum: null,
        _isWin: null,

        num_result: {
            'default': 0,
            type: cc.Integer,
            displayName: '代表的结果值'
        },
        label_multiple: {
            'default': null,
            type: cc.Label,
            displayName: '倍数'
        },
        node_goldContainer: {
            'default': null,
            type: cc.Node,
            displayName: '金币容器'
        },
        label_areaValue: {
            'default': null,
            type: cc.Label,
            displayName: '格子内总金币'
        },
        label_ownerValue: {
            'default': null,
            type: cc.Label,
            displayName: '投注值'
        },
        label_resultMultiple: {
            'default': null,
            type: cc.Label,
            displayName: '结果倍数'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },

    _init: function _init() {
        this._ownerFormatStr = '<b>%s</b>';
        //this._areaFormatStr = '<b>%s</b>';
        //this._multipleFormatStr = '<b>%s</b>';
        //this._areaFormatStr = '';
        //this._multipleFormatStr = '';
        //this.node_goldContainer.x += 200;
        var rate = G_Config_grab.num_goldImgArea;
        this._minGoldX = -this.node.width * rate * 1.3;
        this._maxGoldX = -this._minGoldX;
        this._minGoldY = -this.node.height * rate * 0.65;
        this._maxGoldY = -this._minGoldY;
        this._maxAdaptGold = 50;
        this._curGoldNum = 0;
        this._loseColor = new cc.Color(253, 41, 31); //失败的颜色
        this._winColor = this.label_ownerValue.node.color, //胜利的颜色

        this._goldScale = G_Config_grab.scale_moveGold;
        this.comp_scrollValue = this.getComponent('Obj_scrollValue');
    },

    setInfo: function setInfo(goldFrame, bullType, multiple) {
        this._goldFrame = goldFrame;
        this._bullType = bullType;
        this._multiple = parseInt(multiple);
    },

    clearData: function clearData() {
        this._ownerGold = 0;
        this._areaGold = 0;
        this._isWin = false;

        this._setMultiple();
        this._clearGoldImg();
        this.label_areaValue.string = '';
        this.label_ownerValue.node.color = this._winColor; //胜利颜色
        this._setOwnerGold(0);
        //
        this.label_ownerValue.fontSize = 60;
    },

    OnClick_table: function OnClick_table(event) {
        var dataObj = G_OBJ.data_grabBetting();
        dataObj.tableIndex = this.getResultType();
        dataObj.targetPos = this.getWorldPos();
        dataObj.callFunc = this._goldAdd.bind(this);

        GG.grabMgr.playMyGoldMove(dataObj);
    },
    _goldAdd: function _goldAdd() {
        this._addOneGoldImg();
    },

    otherAddGold: function otherAddGold(goldInfo) {
        goldInfo.targetPos = this.getWorldPos();
        goldInfo.callFunc = this._goldAdd.bind(this);

        var addGoldValue = goldInfo.goldValue;
        if (goldInfo.isMine) {
            this.addOwnerGold(addGoldValue); //自己押注
        } else this._addOthersGold(addGoldValue); //他人押注
        return goldInfo;
    },

    //自己押注值
    addOwnerGold: function addOwnerGold(goldValue) {
        if (!goldValue) return;
        this._ownerGold += goldValue;
        this._setOwnerGold(this._ownerGold);
        this._addOthersGold(goldValue);
    },
    //他人押注值
    _addOthersGold: function _addOthersGold(goldValue) {
        if (!goldValue) goldValue = 0;
        this._areaGold += goldValue;
        this._setAreaGold(this._areaGold);
    },

    _setOwnerGold: function _setOwnerGold(goldNum) {
        if (!goldNum) {
            this.label_ownerValue.node.parent.active = false;
        } else {
            this.label_ownerValue.node.parent.active = true;
            //this.label_ownerValue.string = G_TOOL.formatStr(this._ownerFormatStr, G_TOOL.changeMoney(goldNum));
            this.label_ownerValue.string = G_TOOL.changeMoney(goldNum);
            //this.label_ownerValue.string = G_TOOL.changeMoney(goldNum);
            this.label_resultMultiple.string = '';
            this._changeDownLabel(this.label_ownerValue.node, false);
        }
    },
    _setAreaGold: function _setAreaGold(goldNum) {
        if (!goldNum) this.label_areaValue.string = '';else this.label_areaValue.string = G_TOOL.changeMoney(goldNum);
        //else this.label_areaValue.string = G_TOOL.formatStr(this._areaFormatStr, G_TOOL.changeMoney(goldNum));
    },

    showOwnerResult: function showOwnerResult() {
        if (this._ownerGold < 1) return;
        var showStr = G_TOOL.changeMoney(this._ownerGold);
        this._changeDownLabel(this.label_ownerValue.node, this._isWin);
        if (!this._isWin) {
            showStr = '-' + showStr;
            this.label_resultMultiple.string = '';
            this.label_ownerValue.string = showStr;
            this.label_ownerValue.node.color = this._loseColor; //失败颜色
        } else {
                //胜利
                this.label_resultMultiple.string = 'X' + this._multiple;
                this.label_ownerValue.fontSize = 50;
                var winValue = this._ownerGold * this._multiple;
                var dataObj = {
                    startNum: 0,
                    targetNum: winValue,
                    label: this.label_ownerValue,
                    formatStr: '',
                    callFunc: (function () {
                        this.label_ownerValue.string = G_TOOL.changeMoney(winValue);
                        //this.label_ownerValue.node.color = this._winColor;//胜利颜色
                    }).bind(this)
                };
                this.comp_scrollValue.scrollLabel(dataObj);
            }
        //this.label_ownerValue.string = G_TOOL.formatStr(this._ownerFormatStr, showStr);
    },
    //滚动金币结束
    _scrollCall: function _scrollCall() {},
    _setMultiple: function _setMultiple() {
        var str = this._multiple + '倍';
        this.label_multiple.string = str;
    },
    _addOneGoldImg: function _addOneGoldImg() {
        if (!this._goldFrame) return;

        var rate = this._curGoldNum / this._maxAdaptGold;
        rate = Math.min(Math.max(rate, 0.55), 1);
        var goldX = G_TOOL.getRandomArea(this._minGoldX * rate, this._maxGoldX * rate);
        var goldY = G_TOOL.getRandomArea(this._minGoldY * rate, this._maxGoldY * rate);
        var gold = this._getGold();
        gold.position = cc.p(0, 0);
        gold.runAction(cc.moveTo(0.35, goldX, goldY));
        this._curGoldNum += 1;
    },

    removeOneGoldImg: function removeOneGoldImg() {
        var gold = this.node_goldContainer.children[0];
        if (gold) {
            var worldPos = this.getWorldPos();
            var curPos = cc.p(worldPos.x + gold.x, worldPos.y + gold.y);
            this._removeGold(gold);
            this._curGoldNum -= 1;
            return curPos;
        }
        return null;
    },

    _getGold: function _getGold() {
        if (!this._pool) this._pool = new cc.NodePool('Grab_oneTable' + this.num_result);

        var goldNode = this._pool.get();
        if (!goldNode) {
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this._goldFrame;
        }
        goldNode.parent = this.node_goldContainer;
        goldNode.scale = 1 / this.node.parent.scale * this._goldScale;
        goldNode.active = true;
        return goldNode;
    },
    _removeGold: function _removeGold(goldeNode) {
        goldeNode.stopAllActions();
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    _clearGoldImg: function _clearGoldImg() {
        var golds = this.node_goldContainer.children;
        for (var key in golds) {
            golds[key].destroy();
        }
        this._curGoldNum = 0;
    },

    setTouchEnable: function setTouchEnable(isEnable) {
        if (isEnable) {
            this.node.on(cc.Node.EventType.TOUCH_START, this.OnClick_table, this);
        } else this.node.off(cc.Node.EventType.TOUCH_START, this.OnClick_table, this);
    },
    //设置底部label随着倍数是否显示而变化位置
    _changeDownLabel: function _changeDownLabel(curLabel, isRight) {
        if (isRight) {
            curLabel.anchorX = 1;
            curLabel.x = curLabel.parent.width * 0.47;
        } else {
            curLabel.anchorX = 0.5;
            curLabel.x = 0;
        }
    },

    getWorldPos: function getWorldPos() {
        var parentPos = this.node.parent.position;
        var parentScale = this.node.parent.scale;
        return cc.p(parentPos.x + this.node.x * parentScale, parentPos.y + this.node.y * parentScale - this.node.height * 0.08); //赢钱旗帜位置
    },

    getResultType: function getResultType() {
        return this.num_result;
    },
    setTableWin: function setTableWin() {
        this._isWin = true;
    },

    getGrabGold: function getGrabGold() {
        return this._ownerGold * this._multiple;
    },
    getLostGold: function getLostGold() {
        return this._ownerGold;
    },
    //获取剩余没有被移除的金币数量
    getLeaveGoldImg: function getLeaveGoldImg() {
        return this._curGoldNum;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Grab_playerBlock":[function(require,module,exports){
"use strict";
cc._RFpush(module, '96991gtPcZLnLBL5qF9FsyE', 'Grab_playerBlock');
// Script\Views\Scene_Grab\Grab_playerBlock.js

//玩家头像块

cc.Class({
    'extends': cc.Component,

    properties: {
        _siteImg: null,
        _chooseMoneyLevel: null,
        _dict_chooseMoney: null,
        _callFunc: null,
        _defaultHeadFrame: null, //没有玩家头像时候使用的默认头像
        _valueDict: null, //押注值
        _seatMyselfComp: null, //座位上的自己玩家
        _data_betting: null, //投注信息
        _goldValueEX: null, //投注金额池信息
        _isSending: null, //是否正在发送投注请求

        //data
        _dict_choosed: null,
        _curGoldValue: null,
        _grabGold: null,
        _playerName: null,
        _headUrl: null,
        _grabTimes: null, //当前投注次数
        _seatIndex: null,
        _resultChangeMoney: null, //结算时候显示是金额变化
        _playerID: null, //玩家id

        node_headImg: {
            'default': null,
            type: cc.Node,
            displayName: '头像'
        },
        label_playerName: {
            'default': null,
            type: cc.Label,
            displayName: '玩家名'
        },
        node_goldValue: {
            'default': null,
            type: cc.Node,
            displayName: '拥有金币'
        },
        _playerType: 0,
        playerType: {
            set: function set(_type) {
                this._playerType = _type;
            },
            get: function get() {
                return this._playerType;
            },
            type: G_TYPE.playerType,
            displayName: '玩家类型'
        },
        prefab_goldValue: cc.Prefab
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },

    _init: function _init() {
        this._dict_chooseLevel = G_Config_grab.dict_moveGoldNum;
        this._goldValueCompName = 'Obj_goldResultEffect';
        this._chooseMoneyLevel = 0;
        this._valueDict = GG.grabMgr.getMyselfComp().getMoneyChooseDict();
    },

    start: function start() {},

    //var dataObj = {
    //    name : '',
    //    headImg : '',
    //    goldValue : 11
    //}
    setPlayerData: function setPlayerData(dataObj) {
        if (!dataObj) return;
        this.node.active = true;

        this.clearData();
        if (dataObj.coin >= 0) this.setGoldValue(dataObj.coin);else this.setGoldValue(0);
        if (dataObj.seatIndex) this._seatIndex = dataObj.seatIndex;

        this._playerName = dataObj.nickname;
        this._setPlayerName(this._playerName);
        this._setHeadImg(dataObj.icon);
        this._playerID = dataObj.playerId;
    },
    setSelfInfo: function setSelfInfo(selfInfo) {
        if (selfInfo.chooseDict) this._dict_chooseMoney = selfInfo.chooseDict;
        var homeLabel = this.node.getChildByName('homeName');
        if (homeLabel && selfInfo.roomName) {
            homeLabel.getComponent(cc.Label).string = selfInfo.roomName;
        }
    },

    clearData: function clearData() {
        this._dict_choosed = {};
        this._grabGold = 0;
        this._goldValueEX = 0;
        this._grabTimes = 0;
        this._isSending = false;
        this._data_betting = null;
        this._resultChangeMoney = null;
    },

    _refreshGoldShow: function _refreshGoldShow() {
        this.node_goldValue.getComponent(cc.Label).string = G_TOOL.changeMoney(this._curGoldValue);
    },

    doChoose: function doChoose(tableIndex, goldNum) {
        if (this._dict_choosed[tableIndex]) this._dict_choosed[tableIndex] += goldNum;else this._dict_choosed[tableIndex] = goldNum;
    },

    //点击投注区域
    touchGrabArea: function touchGrabArea(grabInfo, callFunc) {
        var goldNum = 0;
        var money = this._dict_chooseMoney[this._chooseMoneyLevel];
        this._callFunc = callFunc;

        if (money > this._curGoldValue) {
            //余额不足
            var dataObj = {
                tipIndex: 21
            };
            GG.tipsMgr.showTxtTip(dataObj);
        } else {
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
            if (G_Config_common.isLocal) {
                grabInfo.startPos = this.getWorldPos();
                this.forceGrabReturn(grabInfo, money, grabInfo.tableIndex);
            } else {
                if (this._isSending) {
                    return;
                }
                grabInfo.startPos = this.getWorldPos();
                grabInfo.goldValue = money;
                this._data_betting = grabInfo;

                //可以投注
                var netData = {
                    matchId: GG.grabMgr.getMatchID(), //赛事id  int
                    type: G_TYPE.net_gameModule.grab, //投注类型 string
                    item: grabInfo.tableIndex + '_W', //投注项 string
                    gold: money };
                //投注额 int
                this._isSending = true;
                GG.socketMgr.SendMsg(NetType.s_doGrab, netData);
                GG.socketMgr.registerLong(NetType.r_doGrab_return, this.net_grabReturn.bind(this));
            }
        }
    },
    net_grabReturn: function net_grabReturn(recvData) {
        this._isSending = false;
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            var dataObj = {
                tipIndex: 19,
                retainTime: 3,
                showStr: tip.tip
            };
            GG.tipsMgr.showTxtTip(dataObj);
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
        if (!this._data_betting) return;
        var bettingData = G_OBJ.data_grabBetting();
        for (var attrName in this._data_betting) {
            if (attrName) bettingData[attrName] = this._data_betting[attrName];
        }
        this._data_betting = null;

        bettingData.goldValue = recvData.bet.gold;
        bettingData.goldNum = parseInt(this._dict_chooseLevel[this._chooseMoneyLevel + 1]);

        //记录投注信息
        this.doChoose(bettingData.tableIndex, bettingData.goldNum);
        //累计投注金额
        this._grabGold += bettingData.goldValue;
        this._goldValueEX += bettingData.goldValue;
        //更新玩家剩余金额
        this.setGoldValue(recvData.nbSelf.balance);
        //押注过程中更新押注按钮（置灰）
        GG.grabMgr.getBtnsComp().changeBet(this._curGoldValue);

        this._grabTimes += 1;
        // GG.grabMgr.setBettingTimes(this._grabTimes);
        if (this._callFunc) {
            this._callFunc(bettingData);
            this._callFunc = null;
        }
    },
    forceGrabReturn: function forceGrabReturn(grabInfo, money, tableIndex) {
        grabInfo.goldValue = money;
        this._grabGold += money;
        this._goldValueEX += money;
        this.setGoldValue(this._curGoldValue - money);
        var goldNum = parseInt(this._dict_chooseLevel[this._chooseMoneyLevel + 1]);
        grabInfo.goldNum = goldNum;
        this.doChoose(tableIndex, goldNum);
        this._grabTimes += 1;
        if (this._callFunc) {
            this._callFunc(grabInfo);
            this._callFunc = null;
        }
    },

    setResultInfo: function setResultInfo(changeMoney, haveMoney) {
        if (haveMoney) this.setGoldValue(haveMoney);
        this._resultChangeMoney = changeMoney;
    },
    //setResultGoldInfo : function (goldInfo) {
    //    goldInfo.targetPos = this.getWorldPos();
    //    //goldInfo.callFunc = this.showResultGold.bind(this);
    //    return goldInfo
    //},
    showResultGold: function showResultGold() {
        if (this._resultChangeMoney) {
            this._refreshGoldShow();
            this._showGoldCount(this._resultChangeMoney);
            return true;
        }
        return false;
    },
    //显示玩家数值的增减
    _showGoldCount: function _showGoldCount(count) {
        if (!count) return;

        var labelNode = cc.instantiate(this.prefab_goldValue);
        labelNode.parent = this.node;
        labelNode.zIndex = 2;

        labelNode.getComponent(this._goldValueCompName).showGrabGold(count);

        labelNode.scale = 0.1;
        var parentScale, widthOff;
        widthOff = this.node.width * 0.3;
        if (this.playerType == G_TYPE.playerType.owner) {
            parentScale = this.node.scale;
        } else if (this.playerType == G_TYPE.playerType.others) {
            parentScale = this.node.parent.scale;
        }
        if (this.node.x > cc.visibleRect.width / 2) widthOff *= -1;
        var time = G_Config_grab.time_goldOnHead;
        var act1 = cc.moveTo(time, widthOff, this.node.height * 0.75);
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.fadeOut(time * 2);
        labelNode.runAction(cc.sequence(cc.spawn(act1, act2), act3, cc.callFunc(this._labelMoveEnd, this, count)));
    },
    _labelMoveEnd: function _labelMoveEnd(target, count) {
        target.destroy();
        //this._refreshGoldShow();
        //if(this._callFunc){
        //    this._callFunc();
        //    this._callFunc = null;
        //}
    },

    _payGold: function _payGold(goldNum) {
        this._curGoldValue -= goldNum;
        if (this._curGoldValue <= 0) {
            this._curGoldValue = 0;

            var table = G_DATA.getChinese(21);
            if (table) {
                var dataObj = {
                    showStr: table.content,
                    showPos: G_Config_common.bottomTipPos,
                    retainTime: table.retainTime
                };
            }
        }
    },

    //设置头像的图标
    _setHeadImg: function _setHeadImg(imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = this.node_headImg.getComponent(cc.Sprite).spriteFrame;
        if (!imgName) this.node_headImg.getComponent(cc.Sprite).spriteFrame = this._defaultHeadFrame;else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'head01';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    this.node_headImg.getComponent(cc.Sprite).spriteFrame = spriteFrame;
                }).bind(this));
            } else {
                this._headUrl = G_DATA.getResNetRoot(imgName);
                GG.resMgr.getHeadImg(this._headUrl, (function (frame) {
                    this.node_headImg.getComponent(cc.Sprite).spriteFrame = frame;
                }).bind(this));
            }
        }
    },
    //设置玩家名字
    _setPlayerName: function _setPlayerName(playerName) {
        if (!playerName) playerName = '';
        playerName = G_TOOL.getNameLimit(playerName, 10, true);
        this.label_playerName.string = playerName;
    },
    //绑定座位上的玩家
    bindSeatMyself: function bindSeatMyself(myselfComp) {
        this._seatMyselfComp = myselfComp;
    },

    bindSiteImg: function bindSiteImg(imgNode) {
        this._siteImg = imgNode;
    },
    setGoldValue: function setGoldValue(newValue) {
        this._curGoldValue = newValue;
        this.node_goldValue.getComponent(cc.Label).string = G_TOOL.changeMoney(newValue);

        //位置上的自己
        if (this._seatMyselfComp) {
            this._seatMyselfComp.setGoldValue(newValue);
        }
    },
    setPlayerType: function setPlayerType(toType) {
        this.playerType = toType;
    },
    //押宝——设置投注金额池的变化
    reduceGoldEX: function reduceGoldEX(changeGold) {
        if (this._goldValueEX >= changeGold) {
            this._goldValueEX -= changeGold;
            return 0;
        } else {
            var value = changeGold - this._goldValueEX;
            this._goldValueEX = 0;
            return value;
        }
    },

    getMoneyChooseDict: function getMoneyChooseDict() {
        return this._dict_chooseMoney;
    },
    getPokerPoint: function getPokerPoint() {
        //强制适配
        if (!this.node._isAdaptFirst) {
            this.node._isAdaptFirst = true;
            var widget = this.getComponent(cc.Widget);
            if (widget) {
                widget.updateAlignment();
            }
        }

        return cc.p(this.node.x, this.node.y - this.node.height * this.node.scaleX * 0.5);
    },
    setChooseLevel: function setChooseLevel(level) {
        this._chooseMoneyLevel = level;
    },
    getChooseLevel: function getChooseLevel() {
        return this._chooseMoneyLevel;
    },
    getWinGoldNum: function getWinGoldNum(resutl) {
        return this._dict_choosed[resutl];
    },
    getAllGrabTable: function getAllGrabTable() {
        return this._dict_choosed;
    },
    getWorldPos: function getWorldPos() {
        return this.node.position;
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
    getPlayerName: function getPlayerName() {
        return this._playerName;
    },
    getHeadUrl: function getHeadUrl() {
        return this._headUrl;
    },
    getGoldValue: function getGoldValue() {
        return this._curGoldValue;
    },
    _getIsOverMaxGrabTimes: function _getIsOverMaxGrabTimes() {
        return this._grabTimes >= GG.grabMgr.getMaxGrabTimes();
    },
    getSeatIndex: function getSeatIndex() {
        return this._seatIndex;
    },
    //是否已经投注
    getIsGrab: function getIsGrab() {
        if (!this._dict_choosed) return false;
        var goldNum = 0;
        for (var key in this._dict_choosed) {
            goldNum += this._dict_choosed[key];
            if (goldNum > 0) break;
        }
        return Boolean(goldNum);
    },
    //是否已经投注结束
    getIsBettingEnd: function getIsBettingEnd() {
        return Boolean(this._grabGold);
    },
    //获取玩家ID
    getPlayerID: function getPlayerID() {
        return this._playerID;
    },
    getIsShow: function getIsShow() {
        return this.node.active;
    },
    //清理关于投注的记录
    clearRecord: function clearRecord() {
        //this._dict_choosed = {};
        this._grabGold = 0;
        this._goldValueEX = 0;
    },
    hidePlayer: function hidePlayer() {
        //if(this._headUrl) cc.loader.releaseRes(this._headUrl);
        this.node.active = false;
    },
    remove: function remove() {
        this.node.destroy();
    },
    onDestroy: function onDestroy() {}

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Grab_playersLayer":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e3d36NeeddC047NW/XvKcvH', 'Grab_playersLayer');
// Script\Views\Scene_Grab\Grab_playersLayer.js

//管理所有玩家的容器

cc.Class({
    'extends': cc.Component,

    properties: {
        _playerNum: null, //场上的玩家数量
        _dict_playerBlocks: null,
        _dict_playerByID: null,

        node_owner: cc.Node,
        node_system: cc.Node,
        node_playerContainer: { //所有玩家的容器，庄家和自己除外
            'default': null,
            type: cc.Node,
            displayName: '所有玩家容器'
        },
        prefab_playerBlock: cc.Prefab,
        comp_seatsContainer: {
            'default': null,
            type: require('Obj_seatsContainer'),
            displayName: '座位容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },
    _init: function _init() {
        this._playerCompName = 'Grab_playerBlock';
        this._dict_playerBlocks = {};
        this._dict_playerByID = {};
        this._playerNum = 0;
    },

    //设置当前所有座位玩家的信息
    setStartInfo: function setStartInfo(seatInfoList, selfInfo, systemInfo) {
        var dictPlayer = {};
        var selfID = GG.getPlayer().getPlayerID();
        var listLen = seatInfoList.length,
            seatIndex,
            onePlayer,
            content,
            playerComp;
        //座位是从1开始，0是自己玩家的信息
        for (var i = 0; i < listLen; i++) {
            onePlayer = seatInfoList[i];
            seatIndex = onePlayer['seatIndex'];
            content = onePlayer['player'];
            content.seatIndex = seatIndex;
            dictPlayer[seatIndex] = content;
        }
        //根据信息控制位置上玩家的显隐
        //显示有数据的玩家
        for (var seatIndex in dictPlayer) {
            content = dictPlayer[seatIndex];
            if (content) this._dict_playerByID[content.playerId] = seatIndex; //获取玩家id
            //从列表中获取属于自己的信息
            if (content && content.playerId == selfID) {
                for (var attrName in content) {
                    if (attrName == 'seatIndex' && content[attrName] == 0) continue;
                    selfInfo[attrName] = content[attrName];
                }
            }
            //座位号从1开始
            if (seatIndex < 1) continue;
            //有玩家数据
            playerComp = this._addOnePlayer(seatIndex);
            if (playerComp) playerComp.setPlayerData(content);
            if (content && content.playerId == selfID) this.getMyselfComp().bindSeatMyself(playerComp);
        }
        //隐藏没有数据的位置
        var siteNum = this.comp_seatsContainer.getSeatsNum();
        for (var i = 0; i < siteNum; i++) {
            seatIndex = i + 1;
            if (!dictPlayer[seatIndex]) this._removeOnePlayer(seatIndex);
        }

        //if(!GG.grabMgr.checkMyselfInfo(seatInfoList)) return;
        if (!isNaN(selfInfo.playerId) && selfInfo.playerId > 0) this.showOwner(selfInfo);
        this.showSystem(systemInfo);
    },

    showOwner: function showOwner(selfInfo) {
        this.getMyselfComp().setPlayerData(selfInfo);
        this.getMyselfComp().setSelfInfo(selfInfo);

        GG.grabMgr.initButtonInfo();
    },

    //有个玩家中途插入
    enterOnePlayer: function enterOnePlayer(seatIndex, playerData) {
        var reg = /^\+?[1-9][0-9]*$/;
        if (reg.test(seatIndex)) {
            //是正确的桌子索引
            playerData.seatIndex = seatIndex;
            this._hidePlayer(seatIndex, playerData.playerId);
            var playerComp = this._addOnePlayer(seatIndex);
            if (playerComp) playerComp.setPlayerData(playerData);
        }
    },

    showSystem: function showSystem(systemInfo) {
        systemInfo.moneyChoose = 0;
        this.node_system.getComponent(this._playerCompName).setPlayerData(systemInfo);
    },

    clearData: function clearData() {
        //var block;
        //for(var siteIndex in this._dict_playerBlocks){
        //    block = this._dict_playerBlocks[siteIndex];
        //    if(block) block.clearData();
        //}
        //this.getMyselfComp().clearData();
    },

    //删除重复的玩家，结束本局时候，重复进出房间会出现多个同样玩家
    _hidePlayer: function _hidePlayer(targetIndex, playerID) {
        var playerComp;
        for (var seatIndex in this._dict_playerBlocks) {
            playerComp = this._dict_playerBlocks[seatIndex];
            if (seatIndex == targetIndex) {
                //找到位置
                //if(playerComp) playerComp.setPlayerData(playerData);
            } else {
                    if (playerComp && playerComp.getPlayerID() == playerID) {
                        this._removeOnePlayer(seatIndex);
                    }
                }
        }
    },
    //增加一个上场玩家
    _addOnePlayer: function _addOnePlayer(siteIndex) {
        var playerComp = this._dict_playerBlocks[siteIndex];
        if (!playerComp) {
            var player = cc.instantiate(this.prefab_playerBlock);
            player.parent = this.node_playerContainer;
            var size = G_TOOL.adaptSize(player.width, player.height);
            player.width = size.width;
            player.height = size.height;
            player.position = this.comp_seatsContainer.addOnePlayer(siteIndex);
            var playerComp = player.getComponent(this._playerCompName);
            this._dict_playerBlocks[siteIndex] = playerComp;
        }
        this._playerNum += 1;
        return playerComp;
    },
    //一个玩家离场
    _removeOnePlayer: function _removeOnePlayer(siteIndex) {
        var playerComp = this._dict_playerBlocks[siteIndex];
        if (!playerComp || !playerComp.getIsShow()) return;
        playerComp.hidePlayer();
        this.comp_seatsContainer.onePlayerLeave(siteIndex);
        this._playerNum -= 1;
    },
    //清理所有
    _clearAllPlayer: function _clearAllPlayer() {
        for (var seatIndex in this._dict_playerBlocks) {
            this._removeOnePlayer(seatIndex);
        }
    },

    setWinTableResult: function setWinTableResult(resultList) {
        //其他座位上玩家的金币
        var playerComp,
            dataList,
            isMoveGold = false;
        for (var key in this._dict_playerBlocks) {
            playerComp = this._dict_playerBlocks[key];
            if (!playerComp.getIsShow() || playerComp.getPlayerID() == GG.getPlayerID()) continue;
            dataList = this._checkResult(resultList, playerComp.getAllGrabTable(), playerComp.getWorldPos());
        }
        if (dataList && dataList.length > 0) isMoveGold = true;
        GG.grabMgr.playResultGoldMove(dataList);
        //玩家自己的金币
        playerComp = this.getMyselfComp();
        recordDict = playerComp.getAllGrabTable();
        if (recordDict) {
            dataList = this._checkResult(resultList, recordDict, playerComp.getWorldPos());
            if (dataList && dataList.length > 0) isMoveGold = true;
            GG.grabMgr.playResultGoldMove(dataList);
        }
        //吃瓜群众的金币
        var recordDict = GG.grabMgr.getIdleBetting();
        if (recordDict) {
            dataList = this._checkResult(resultList, recordDict, GG.grabMgr.getIdleBettingPos());
            if (dataList && dataList.length > 0) isMoveGold = true;
            GG.grabMgr.playResultGoldMove(dataList);
        }
        //剩余的金币飞向系统
        var recordDict = GG.grabMgr.getTableContainer().getLeaveGoldImages();
        if (recordDict) {
            //var curList = this._checkResult(resultList, recordDict, this.getSystemComp().getWorldPos())
            dataList = [];
            var goldNum,
                targetPos = this.getSystemComp().getWorldPos();
            for (var tableIndex in recordDict) {
                goldNum = recordDict[tableIndex];
                if (goldNum) {
                    var dataObj = G_OBJ.data_flyGold_tableToPlayer();
                    dataObj.goldNum = goldNum;
                    dataObj.tableIndex = tableIndex;
                    dataObj.targetPos = targetPos;
                    dataList.push(dataObj);
                }
            }
            if (dataList && dataList.length > 0) isMoveGold = true;
            GG.grabMgr.playResultGoldMove(dataList);
        }
        if (isMoveGold) GG.audioMgr.playSound(18);
    },
    _checkResult: function _checkResult(resultList, recordDict, targetPos) {
        var result,
            goldNum,
            dataList = [];
        for (var index in resultList) {
            result = resultList[index];
            goldNum = recordDict[result];
            if (goldNum) {
                var dataObj = G_OBJ.data_flyGold_tableToPlayer();
                dataObj.goldNum = goldNum;
                dataObj.tableIndex = result;
                dataObj.targetPos = targetPos;
                dataList.push(dataObj);
            }
        }
        return dataList;
    },

    //显示所有玩家对象的头像上的金币浮动信息
    showAllGoldResult: function showAllGoldResult() {
        var player, isShow, isOk;
        for (var seatIndex in this._dict_playerBlocks) {
            player = this._dict_playerBlocks[seatIndex];
            if (!player.getIsShow()) continue;
            isOk = player.showResultGold();
            if (!isShow) isShow = isOk;
        }

        isOk = this.getMyselfComp().showResultGold();
        if (!isShow) isShow = isOk;
        return isShow;
    },

    setMyselfMoney: function setMyselfMoney(addCoin, money) {
        this.getMyselfComp().setResultInfo(addCoin, money);
    },
    //获取在座位上的玩家脚本
    getCompByIndex: function getCompByIndex(siteIndex) {
        return this._dict_playerBlocks[siteIndex];
    },
    getPlayerByID: function getPlayerByID(playerID) {
        return this.getCompByIndex(this._dict_playerByID[playerID]);
    },
    //获取玩家自己
    getMyselfComp: function getMyselfComp() {
        return this.node_owner.getComponent(this._playerCompName);
    },
    getPokerPos: function getPokerPos() {
        return this.node_system.getComponent(this._playerCompName).getPokerPoint();
    },
    getSystemComp: function getSystemComp() {
        return this.node_system.getComponent(this._playerCompName);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"Obj_seatsContainer":"Obj_seatsContainer"}],"Grab_pokerLayer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7367eR4I8tHuLC3vrJVwjYL', 'Grab_pokerLayer');
// Script\Views\Scene_Grab\Grab_pokerLayer.js

//卡牌活动层

cc.Class({
    'extends': cc.Component,

    properties: {
        _pokerShowPos: null,
        _pokerNum: null,
        _StartPos: null,
        _pokerW: null,
        _pokerH: null, //卡牌的高
        _pokerOffX: null,
        _callFunc: null,
        _curResultNum: null,

        //=================卡牌容器重构
        _list_pokers: null,
        _firstPokerIndex: null, //展示结果的时候，第一张卡牌的信息变化了

        prefab_poker: cc.Prefab,
        atlas_pokerResult: {
            'default': null,
            type: cc.SpriteAtlas,
            displayName: '显示牛几图集'
        },
        prefab_pokerResult: {
            'default': null,
            type: cc.Prefab,
            displayName: '显示牛几结果'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },

    _init: function _init() {
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

    setStartInfo: function setStartInfo(startPos, targetPos) {
        this._StartPos = startPos;
        this._pokerShowPos = cc.p(targetPos.x, targetPos.y);
        if (this._pokerW) this._pokerShowPos = cc.p(this._pokerShowPos.x - this._pokerW * 0.5, this._pokerShowPos.y - this._pokerH * 0.5);
    },
    clearData: function clearData() {
        this.node.stopAllActions();
        this._pokerNum = 0;
        this._flyNum = 0;

        for (var i = 0; i < this._maxPokerNum; i++) {
            if (this._list_pokers[i]) this._delOnePoker(this._list_pokers[i]);
        }
        if (this._pokerResult) {
            this._pokerResult.destroy();
            this._pokerResult = null;
        }
    },

    // 显示卡牌
    showPokerEX: function showPokerEX(dataObj) {
        //var dataObj = G_OBJ.data_grabPokerInfo();
        //这幅卡牌结果值(没有传值的话，默认最终卡牌值为0)
        this._curResultNum = dataObj.pokerResult ? dataObj.pokerResult : 0;
        //卡牌信息
        this._flyNum = 0;
        var pokerList = dataObj.pokerInfoList;
        if (pokerList) {
            var lastPoker;
            if (dataObj.startPokerIndex == 1) {
                //显示第一张卡牌
                this._pokerNum = 0;
                lastPoker = this._addOnePokerEX(0, pokerList[0], dataObj.isFlyEffect);
            } else {
                //取其余四张信息
                this._firstPokerIndex = pokerList[0].pokerIndex;
                for (var i = 1; i < pokerList.length; i++) {
                    lastPoker = this._addOnePokerEX(i, pokerList[i], dataObj.isFlyEffect);
                }
            }
            if (lastPoker) lastPoker._flyCall = dataObj.callFunc;
            GG.audioMgr.playSound(14);
        }
    },

    //增加一张卡牌
    _addOnePokerEX: function _addOnePokerEX(pokerIndex, pokerData, isFlyEffect) {
        var poker = this._list_pokers[pokerIndex];
        if (!poker) {
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
        if (isFlyEffect) {
            //有飞行特效
            poker.position = this._StartPos;
            this._addFlyAction(poker, pokerData.isOpen);
        } else {
            //没有飞行特效
            this._addNoFly(poker);
            if (pokerData.isOpen) poker.getComponent(this._pokerCompName).showPokerPlus();
        }
        this._pokerNum += 1;
        this._flyNum += 1;
        return poker;
    },

    //=======================================

    //增加一张卡牌，纯粹的创建
    _createPoker: function _createPoker() {
        var poker = new cc.instantiate(this.prefab_poker);
        poker.parent = this.node;
        poker.scale = 0.5;
        poker.active = true;
        return poker;
    },
    //刷新卡牌数据
    _resetPokerInfo: function _resetPokerInfo(pokerNode, pokerIndex) {
        var pokerInfo = G_DATA.getPokerInfo(pokerIndex);
        var pokerComp = pokerNode.getComponent(this._pokerCompName);
        pokerComp.setPokerInfo(pokerInfo.pokerType, pokerInfo.pokerValue, false);
    },
    //给卡牌增加动作
    _addFlyAction: function _addFlyAction(pokerNode, isOpen) {
        var pokerComp = pokerNode.getComponent(this._pokerCompName);

        if (!this._pokerW) this._setPokerConfigInfo(pokerNode);
        var act1 = cc.delayTime(this._delayTime * this._flyNum);
        var act3 = cc.moveTo(this._moveTime, this._getTargetPos());
        pokerNode.runAction(cc.sequence(act1, act3, cc.callFunc(this._moveEnd, this, isOpen)));
    },
    //卡牌做无飞行效果显示
    _addNoFly: function _addNoFly(pokerNode) {
        if (!this._pokerW) this._setPokerConfigInfo(pokerNode);
        pokerNode.position = this._getTargetPos();
    },
    _moveEnd: function _moveEnd(target, isOpen) {
        if (isOpen) {
            var pokerComp = target.getComponent(this._pokerCompName);
            pokerComp.openPoker();
        }
        if (target._flyCall) {
            target._flyCall();
            target._flyCall = null;
        }
    },

    _setPokerConfigInfo: function _setPokerConfigInfo(poker) {
        var pokerComp = poker.getComponent(this._pokerCompName);
        var pokerSize = pokerComp.getPokerSize();
        this._pokerW = pokerSize.width * poker.scaleX;
        this._pokerH = pokerSize.height * poker.scaleX;
        this._pokerOffX = this._pokerW * G_Config_grab.num_pokerOffX;
        this._pokerShowPos = cc.p(this._pokerShowPos.x - this._pokerW * 0.5, this._pokerShowPos.y - this._pokerH * 0.5);
    },

    //重新设置第一张卡牌信息
    _resetFirstPokerInfo: function _resetFirstPokerInfo() {
        if (this._list_pokers[0]) this._resetPokerInfo(this._list_pokers[0], this._firstPokerIndex);
    },

    //开牌==============================
    openPokers: function openPokers(callFunc) {
        this._callFunc = callFunc;
        //重置第一张牌信息
        this._resetFirstPokerInfo();
        //开始播放开牌
        var poker,
            index = 0,
            lastPoker,
            pokerComp,
            posX;
        //var sortList = this._sortPokers();
        var sortList = this.node.children;
        //如果有牛，需要分隔开来
        var offX = 0;
        if (this._curResultNum > 0) offX = this._winPokerOff;
        //开牌
        for (var i = 0; i < this._list_pokers.length; i++) {
            poker = this._list_pokers[i];
            if (!poker) continue;
            //poker.zIndex = index;
            pokerComp = poker.getComponent(this._pokerCompName);
            pokerComp.showPokerPlus();

            if (!this._pokerW) this._setPokerConfigInfo(poker);
            //posX = this._pokerShowPos.x+this._pokerW*0.5;
            posX = this._pokerShowPos.x;
            if (i > 2) posX += offX;
            poker.position = cc.p(posX, this._pokerShowPos.y);
            this._actOpenPoker(poker, index * this._pokerOffX);
            index += 1;
            lastPoker = poker;
        }
        lastPoker._callFunc = this._showResult.bind(this);
        GG.audioMgr.playSound(16);
    },
    _actOpenPoker: function _actOpenPoker(poker, offX) {
        var act1 = cc.moveTo(this._openMoveTime, poker.x + offX, poker.y);
        poker.runAction(cc.sequence(act1, cc.callFunc(function (target) {
            //this._showResult(5);
            if (target._callFunc) {
                target._callFunc();
                target._callFunc = null;
            }
        }, this)));
    },
    //显示卡牌的牌型结果
    _showResult: function _showResult() {
        var pokerResult = this._curResultNum;
        var audioID = pokerResult == 0 ? 12 : pokerResult;
        GG.audioMgr.playSound(audioID);

        var pokerPos = cc.p(this._pokerShowPos.x + this._pokerW * 0.65, this._pokerShowPos.y);
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
    _showResultEnd: function _showResultEnd() {
        if (this._callFunc) {
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

    _getTargetPos: function _getTargetPos() {
        //return cc.p(this._pokerShowPos.x + this._pokerOffX*this._pokerNum + this._pokerW*0.5, this._pokerShowPos.y)
        return cc.p(this._pokerShowPos.x + this._pokerOffX * this._pokerNum, this._pokerShowPos.y);
    },

    _delOnePoker: function _delOnePoker(pokerNode) {
        //pokerNode.destroy();
        pokerNode.getComponent(this._pokerCompName).clearAllActions();
        pokerNode.getComponent(this._pokerCompName).showPokerNegative();
        pokerNode.active = false;
    },

    getPokerNum: function getPokerNum() {
        return this._pokerNum;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Grab_tablesLayer":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e4ec3rqaY5I0qKUPfGwL5Fi', 'Grab_tablesLayer');
// Script\Views\Scene_Grab\Grab_tablesLayer.js

//可以点击投注的所有格子所在的层

cc.Class({
    'extends': cc.Component,

    properties: {
        _dict_tables: null,

        frame_gold: {
            'default': null,
            type: cc.SpriteFrame,
            displayName: '金币图片'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },

    _init: function _init() {
        this._talbeCompName = 'Grab_oneTable';
        if (!this._dict_tables) {
            this._dict_tables = {};
            var tables = this.node.children;
            for (var i = 0; i < tables.length; i++) {
                var table = tables[i].getComponent(this._talbeCompName);
                this._dict_tables[table.getResultType()] = table;
            }
        }
    },

    setStartInfo: function setStartInfo(areaInfo) {
        var multipleInfo = {};
        //for(var i = 0; i < areaInfo.length; i ++){
        //    var infoDict = areaInfo[i];
        //    multipleInfo[infoDict['area']] = infoDict['bei'];
        //}
        //multipleInfo = {0:2,1:13,2:13,3:13,4:13,5:13,6:13,7:13,8:13,9:13,10:12}
        multipleInfo = areaInfo;
        var table;
        for (var key in this._dict_tables) {
            table = this._dict_tables[key];
            table.setInfo(this.frame_gold, key, multipleInfo[key]);
        }
    },

    clearData: function clearData() {
        for (var key in this._dict_tables) {
            this._dict_tables[key].clearData();
        }
    },

    //0--10
    touchOneTable: function touchOneTable(tableIndex, goldInfo) {
        return this._dict_tables[tableIndex].otherAddGold(goldInfo);
    },

    setTouchEnable: function setTouchEnable(isEnable) {
        for (var key in this._dict_tables) {
            this._dict_tables[key].setTouchEnable(isEnable);
        }
    },

    setTableWin: function setTableWin(pokerResult) {
        var table = this._dict_tables[pokerResult];
        table.setTableWin(true);
        return table.getWorldPos();
    },
    showOwnerGold: function showOwnerGold(dict) {
        for (var tableIndex in dict) {
            this._dict_tables[tableIndex].showOwnerResult();
        }
    },
    getTablePos: function getTablePos(pokerResult) {
        return this._dict_tables[pokerResult].getWorldPos();
    },

    getTable: function getTable(pokerResult) {
        return this._dict_tables[pokerResult];
    },

    getWinGold: function getWinGold(tableList) {
        var isWin = function isWin(index) {
            for (var i = 0; i < tableList.length; i++) {
                if (index == tableList[i]) return true;
            }
            return false;
        };

        var lostGold = 0,
            winGold = 0;
        for (var index in this._dict_tables) {
            var isWinIndex = isWin(index);
            if (isWinIndex) {
                winGold += this._dict_tables[index].getGrabGold();
            } else lostGold += this._dict_tables[index].getLostGold();
        }
        var goldNum = winGold - lostGold;
        return goldNum;
    },
    //获取剩余没有被移除的金币信息
    getLeaveGoldImages: function getLeaveGoldImages() {
        var table,
            leaveGoldNum,
            recordDict = {};
        for (var index in this._dict_tables) {
            table = this._dict_tables[index];
            leaveGoldNum = table.getLeaveGoldImg();
            if (table && leaveGoldNum > 0) {
                //该区域还有剩余金币
                recordDict[index] = leaveGoldNum;
            }
        }
        return recordDict;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Grab_topEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'cb93fp5RkNDeb3yh6PN0aG/', 'Grab_topEffect');
// Script\Views\Scene_Grab\Effects\Grab_topEffect.js

//放置在最上面的动画特效

cc.Class({
    'extends': cc.Component,

    properties: {
        _aniCallFunc: null, //开始动画的回调
        _grabDoneCallFunc: null, //结算动画的回调
        _list_flags: null, //胜利特效

        node_aniContainer: {
            'default': null,
            type: cc.Node,
            displayName: '动画容器'
        },
        prefab_startAni: {
            'default': null,
            type: cc.Prefab,
            displayName: '开始动画'
        },
        prefab_winFlagAni: {
            'default': null,
            type: cc.Prefab,
            displayName: '赢钱旗帜'
        },
        prefab_grabDoneAni: {
            'default': null,
            type: cc.Prefab,
            displayName: '押注完成'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    //开始游戏动画
    playGameStartAni: function playGameStartAni(callFunc) {
        this.playStartAni('bull_start', callFunc);
        GG.audioMgr.playSound(13);
    },

    playStartAni: function playStartAni(aniName, callFunc) {
        if (!aniName) aniName = '';
        this._aniCallFunc = callFunc;
        if (!this._startGrabAni) {
            this._startGrabAni = cc.instantiate(this.prefab_startAni);
            this._startGrabAni.parent = this.node_aniContainer;

            //ani.scale = G_Config_common.frameScale;
            this._startGrabAni.position = cc.visibleRect.center;
            this._startGrabAni.getComponent(dragonBones.ArmatureDisplay).addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        }
        this._startGrabAni.active = true;
        //comp = this._startAni.getComponent(dragonBones.ArmatureDisplay);
        this._startGrabAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _startAniEnd: function _startAniEnd(event) {
        if (this._startGrabAni) this._startGrabAni.active = false;
        if (this._aniCallFunc) {
            this._aniCallFunc();
            this._aniCallFunc = null;
        }
    },
    forceStopStart: function forceStopStart() {
        if (this._startGrabAni) this._startGrabAni.active = false;
        this._aniCallFunc = null;
    },

    //=====================

    //开始幸运区域投注动画
    playTouchStartAni: function playTouchStartAni(callFunc) {
        this.playGrabAni('platform_bet', callFunc);
    },

    //投注完成的动画
    playGrabDoneAni: function playGrabDoneAni(callFunc, userData) {
        this.playGrabAni('platform_betOver', callFunc, userData);
    },

    playGrabAni: function playGrabAni(aniName, callFunc, userData) {
        if (!aniName) aniName = '';
        this._grabDoneCallFunc = callFunc;
        if (!this._grabDoneAni) {
            this._grabDoneAni = cc.instantiate(this.prefab_grabDoneAni);
            this._grabDoneAni.parent = this.node_aniContainer;

            this._grabDoneAni.scale = G_Config_common.frameScale;
            this._grabDoneAni.position = cc.visibleRect.center;
            this._grabDoneAni.getComponent(dragonBones.ArmatureDisplay).addEventListener(dragonBones.EventObject.COMPLETE, this._grabDoneAniEnd, this);
        }
        this._grabDoneAni.pokerInfo = userData;
        this._grabDoneAni.active = true;
        this._grabDoneAni.getComponent(dragonBones.ArmatureDisplay).playAnimation(aniName, 1);
    },
    _grabDoneAniEnd: function _grabDoneAniEnd(event) {
        if (this._grabDoneCallFunc) {
            this._grabDoneCallFunc(this._grabDoneAni.pokerInfo);
            this._grabDoneCallFunc = null;
        }
        if (this._grabDoneAni) this._grabDoneAni.active = false;
    },

    //=====================

    playOneFlag: function playOneFlag(pos) {

        var flag = this._getOneFlag();
        flag.position = pos;
        flag.active = true;

        var comp = flag.getComponent(dragonBones.ArmatureDisplay);
        // comp.addEventListener(dragonBones.EventObject.COMPLETE, this._startAniEnd, this);
        comp.playAnimation('', 1);
    },
    clearFlag: function clearFlag() {
        //var flag;
        //for(var i = 0; i < this._list_flags.length; i ++){
        //    flag = this._list_flags[i];
        //    if(flag) flag.active = false;
        //}
        this.forceStopAllAni();
    },
    _getOneFlag: function _getOneFlag() {
        if (!this._flagAni) {
            this._flagAni = cc.instantiate(this.prefab_winFlagAni);
            this._flagAni.parent = this.node_aniContainer;
            this._flagAni.scale = G_Config_common.frameScale;
        }
        return this._flagAni;
    },

    forceStopAllAni: function forceStopAllAni() {
        var anis = this.node_aniContainer.children;
        var aniNode;
        for (var i = 0; i < anis.length; i++) {
            aniNode = anis[i];
            if (aniNode) {
                aniNode.active = false;
            }
        }
        this._grabDoneCallFunc = null;
    },

    clearAll: function clearAll() {
        this.forceStopStart();
        this.forceStopAllAni();
        this.node.stopAllActions();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Grab_topLayer":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'dcc54iGPEZLnrgK9grY01fm', 'Grab_topLayer');
// Script\Views\Scene_Grab\Grab_topLayer.js

//游戏最上面的层容器

cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        node_startImg: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerStart();
    },
    _registerStart: function _registerStart() {
        if (this.node_startImg) {
            this.node_startImg.active = true;
            this.registerButton(this.node_startImg, this.OnClick_startImg, this);
        }
        if (G_Config_common.isLocal) {} else {
            this.node_startImg.active = false;
        }
    },
    OnClick_startImg: function OnClick_startImg(event) {
        if (G_Config_common.isLocal) {
            this.node_startImg.active = false;
            GG.grabMgr.forceStart();
        } else {
            GG.socketMgr.connectSocket(this._connectEnd.bind(this));
        }
    },

    _connectEnd: function _connectEnd(isSuccess) {
        if (isSuccess) {
            this.node_startImg.active = false;
            this._login();
            //GG.grabMgr.firstStart();
        }
    },

    _login: function _login() {
        var netData = {
            userId: GG.getPlayer().getPlayerID()
        };
        GG.socketMgr.SendMsg(NetType.s_login, netData);
    },

    //_enterHome : function (data) {
    //    console.log('_enterHome success------')
    //    console.log(data)
    //    GG.grabMgr.requestEnterHome();
    //},

    getTopEffect: function getTopEffect() {
        return this.getComponent('Grab_topEffect');
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"HTTPMgr":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd63f2BZmKhJNpeBm9NQSiKV', 'HTTPMgr');
// Script\Common\Managers\HTTPMgr.js

//http请求管理

var http = cc.Class({
    _list_request: null, //请求列表
    _isRequesting: null, //正在请求中
    _isAddHeart: null, //是否有心跳
    _failTime: null, //多长时间请求失败
    properties: {},

    // use this for initialization
    ctor: function ctor() {
        this._list_request = [];
        this._isRequesting = false;
        this._isAddHeart = false;
    },

    _initTimeOutList: function _initTimeOutList() {
        this._list_longTimeOutUrl = [G_DIALOG_URL.recordUrl, G_DIALOG_URL.accountUrl];
    },

    sendHttpLogin: function sendHttpLogin(url, sendData, callFunc) {
        if (!this._list_longTimeOutUrl) this._initTimeOutList();
        this._failTime = G_Config_common.connectTimeOut;
        for (var i = 0; i < this._list_longTimeOutUrl.length; i++) {
            if (url == this._list_longTimeOutUrl[i]) {
                this._failTime = 15;
            }
        }
        var strList = url.split('/');
        this._list_request.splice(0, 0, {
            url: url,
            sendData: sendData,
            callFunc: callFunc
        });
        this._nextRequest();
    },

    _nextRequest: function _nextRequest() {
        if (this._isRequesting) return;
        var requestObj = this._list_request.pop();
        this._cancelTimeOutCheck();
        if (requestObj) {
            this._sendHttp(requestObj.url, requestObj.sendData, requestObj.callFunc);
        } else {
            GG.topTouchLayer.closeNetRequest();
        }
    },

    _sendHttp: function _sendHttp(url, sendData, callFunc) {
        var xhr = new XMLHttpRequest();
        this._isRequesting = true;
        GG.topTouchLayer.showNetRequest();
        var self = this;
        this._startTimeOutCheck(function () {
            self._sendHttp(url, sendData, callFunc);
        });
        xhr.onreadystatechange = (function () {
            var strList = url.split('/');
            //if (xhr.readyState == 4 && (xhr.status >= 200 && xhr.status < 400)) {
            if (xhr.readyState == 4 && xhr.status == 200) {
                var response = xhr.responseText;
                var oneSign = response[0];
                if (url == G_DIALOG_URL.logout) {
                    //xhr.responseText 是整个页面的HTML
                    response = true;
                } else {
                    try {
                        response = JSON.parse(xhr.responseText);
                    } catch (e) {
                        response = null;
                        GG.showReconnect((function () {
                            this._sendHttp(url, sendData, callFunc);
                        }).bind(this));
                        return;
                    }
                }
                if (callFunc) {
                    callFunc(response);
                }
                this._isRequesting = false;
                this._nextRequest();
            }
        }).bind(this);
        xhr.open("POST", url, true);
        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xhr.send(sendData);
    },

    //增加http心跳
    addHeart: function addHeart() {
        if (this._isAddHeart) return;
        this._isAddHeart = true;
        var _this = this;
        setInterval(function () {
            _this.timedRefresh();
        }, 60000);
    },

    timedRefresh: function timedRefresh() {
        this.sendHttpLogin(G_DIALOG_URL.timedRefreshUrl, { t: new Date() }, (function (data) {}).bind(this));
    },

    //计算网络请求时间
    _startTimeOutCheck: function _startTimeOutCheck(callFunc) {
        var emptyNode = cc.find('Canvas/emptyNode');
        if (emptyNode) {
            //if(!this._failTime) this._failTime = G_Config_common.connectTimeOut;
            emptyNode.stopAllActions();
            emptyNode.runAction(cc.sequence(cc.delayTime(this._failTime), cc.callFunc(function () {
                GG.topTouchLayer.closeNetRequest();
                GG.showReconnect(function () {
                    if (callFunc) {
                        callFunc();
                        callFunc = null;
                    }
                    GG.topTouchLayer.showNetRequest();
                });
            }, this)));
        }
    },
    //取消超时监听
    _cancelTimeOutCheck: function _cancelTimeOutCheck() {
        var emptyNode = cc.find('Canvas/emptyNode');
        if (emptyNode) {
            emptyNode.stopAllActions();
        }
    }

});

// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },
module.exports = http;

cc._RFpop();
},{}],"M_Player":[function(require,module,exports){
"use strict";
cc._RFpush(module, '20f361J4TtC0qM5pIHpvoN4', 'M_Player');
// Script\Modules\M_Player.js

//玩家对象，存在于整个游戏流程中

var player = cc.Class({
    _playerID: null,
    _playerToken: null,
    _playerName: null, //姓名
    _playerUID: null, //
    _playerGold: null, //玩家的金币

    _dict_systemSet: null, //系统配置信息
    _dict_baseInfo: null, //基础信息列表

    _inHomeData: null, //临时记录当前的进房间信息
    _outHomeData: null, //临时记录当前的出房间信息
    _reloadSceneInfo: null, //预加载场景的时候传递的参数
    _goldListenerFunc: null, //金币变化的监听
    ctor: function ctor() {},

    setPlayerToken: function setPlayerToken(tokenData) {
        this._playerToken = tokenData.token;
        this._playerUID = tokenData.uid;
    },
    setPlayerID: function setPlayerID(id) {
        this._playerID = id;
    },
    //名字
    setPlayerName: function setPlayerName(name) {
        this._playerName = name;
    },
    //系统设置
    setSystemSet: function setSystemSet(curDict) {
        this._dict_systemSet = curDict;
    },
    //玩家基础信息: nickname,avatarUrl,id
    setBaseInfo: function setBaseInfo(rectData) {
        if (!this._dict_baseInfo) this._dict_baseInfo = {};
        var data = rectData.data[0];
        if (data) {
            for (var attrName in data) {
                //头像avatarUrl；金币coin；nickname昵称
                this._dict_baseInfo[attrName] = data[attrName];
                //if(attrName == 'id') this.setPlayerID(data[attrName]);
                switch (attrName) {
                    case 'id':
                        this.setPlayerID(data[attrName]);
                        break;
                    case 'coin':
                        this.setPlayerGold(data[attrName]);
                        break;
                    default:
                        break;
                }
            }
        }
    },
    setInHomeData: function setInHomeData(dataObj) {
        this._inHomeData = dataObj;
    },
    setOutHomeData: function setOutHomeData(dataObj) {
        this._outHomeData = dataObj;
    },
    setReloadSceneInfo: function setReloadSceneInfo(info) {
        this._reloadSceneInfo = info;
    },
    setPlayerGold: function setPlayerGold(goldValue) {
        this._playerGold = goldValue;
        if (this._goldListenerFunc) {
            this._goldListenerFunc(goldValue);
        }
    },

    //=============================================

    getPlayerGold: function getPlayerGold() {
        return this._playerGold;
    },
    getPlayerID: function getPlayerID() {
        return this._playerID;
    },
    getPlayerToken: function getPlayerToken() {
        return this._playerToken;
    },
    //玩家姓名
    getPlayerName: function getPlayerName() {
        return this._dict_baseInfo['nickname'];
    },
    //获取系统配置信息
    getSystemSet: function getSystemSet() {
        return this._dict_systemSet;
    },
    //获取基础信息
    getBaseInfo: function getBaseInfo() {
        return this._dict_baseInfo;
    },
    //获取玩家头像
    getHeadImgUrl: function getHeadImgUrl() {
        return this._dict_baseInfo['avatarUrl'];
    },
    //获取进房间信息
    getInHomeData: function getInHomeData() {
        var homeData = this._inHomeData;
        this._inHomeData = null;
        return homeData;
    },
    //获取出房间信息
    getOutHomeData: function getOutHomeData() {
        var homeData = this._outHomeData;
        this._outHomeData = null;
        return homeData;
    },
    //获取UID
    getUID: function getUID() {
        return this._playerUID;
    },
    //获取游戏ID，第几款游戏
    getGameID: function getGameID() {
        return 1;
    },
    //获取预加载场景传递的信息
    getReloadSceneInfo: function getReloadSceneInfo() {
        var info = this._reloadSceneInfo;
        this._reloadSceneInfo = null;
        return info;
    },

    //====================================

    //是否在玩家身上注册金币的监听
    registerGoldListener: function registerGoldListener(func, isOpen) {
        if (isOpen) this._goldListenerFunc = func;else this._goldListenerFunc = null;
    },

    //退出游戏时候的处理（比如记录当前的音乐音效设置信息）
    whenExit: function whenExit() {}
});

module.exports = player;

cc._RFpop();
},{}],"M_PokerConfig":[function(require,module,exports){
"use strict";
cc._RFpush(module, '569adFbOfBJaJs2ypDlVtW8', 'M_PokerConfig');
// Script\Modules\M_PokerConfig.js



cc._RFpop();
},{}],"Mask_extend":[function(require,module,exports){
"use strict";
cc._RFpush(module, '57e04/dWKlETa5e+LjTXGw7', 'Mask_extend');
// Script\Common\DIYNodes\Mask_extend.js

var Base = cc._RendererInSG;

var MaskType = cc.Enum({
    RECT: 0,
    ELLIPSE: 1,
    IRR: 2
});

//SP:3
var Mask_extend = cc.Class({

    'extends': Base,
    editor: {
        requireComponent: cc.PolygonCollider
    },
    properties: {

        _clippingStencil: {
            'default': null,
            serializable: false
        },

        _Case: null,

        _type: 2,
        _segements: 64,
        _area: {
            'default': function _default() {
                return [cc.v2(-50, -50), cc.v2(-50, 50), cc.v2(50, 50), cc.v2(50, -50)];
            },
            type: [cc.Vec2]
        },

        live: {
            'default': true,
            tooltip: "是否实时刷新多边形（编辑时开启，运行看需求，主要怕影响性能？）"

        },

        //rev: {
        //default: false,
        //tooltip: "是否反转",

        //},

        type: {
            get: function get() {
                return this._type;
            },
            set: function set(value) {
                this._type = value;
                this._refreshStencil();
            },
            type: MaskType
        },

        segements: {
            get: function get() {
                return this._segements;
            },
            set: function set(value) {
                if (value < 3) value = 3;
                this._segements = value;
                this._refreshStencil();
            },
            type: cc.Integer
        }

    },

    statics: {
        Type: MaskType
    },

    lateUpdate: function lateUpdate() {
        if (this.live) {
            this._refreshStencil();
        }
    },

    _createSgNode: function _createSgNode() {
        this._clippingStencil = new cc.DrawNode();
        if (CC_JSB) {
            this._clippingStencil.retain();
        }
        this._Case = new cc.ClippingNode(this._clippingStencil);
        this._Case.setInverted(0);
        this._Case.setAlphaThreshold(1);

        //Case.setStencil(this.A)
        //cc.log(Case.getStencil())

        return this._Case;
    },

    _initSgNode: function _initSgNode() {},

    _hitTest: function _hitTest(point) {
        var size = this.node.getContentSize(),
            w = size.width,
            h = size.height,
            trans = this.node.getNodeToWorldTransform();

        if (this._type === MaskType.RECT) {
            var rect = cc.rect(0, 0, w, h);
            cc._rectApplyAffineTransformIn(rect, trans);
            var left = point.x - rect.x,
                right = rect.x + rect.width - point.x,
                bottom = point.y - rect.y,
                top = rect.y + rect.height - point.y;
            if (left >= 0 && right >= 0 && top >= 0 && bottom >= 0) {
                return true;
            } else {
                return false;
            }
        } else {
            var a = w / 2,
                b = h / 2;
            var cx = trans.a * a + trans.c * b + trans.tx;
            var cy = trans.b * a + trans.d * b + trans.ty;
            var px = point.x - cx,
                py = point.y - cy;
            if (px * px / (a * a) + py * py / (b * b) < 1) {
                return true;
            } else {
                return false;
            }
        }
    },

    onEnable: function onEnable() {
        this._refreshStencil();
        this._super();
        this.node.on('size-changed', this._refreshStencil, this);
        this.node.on('anchor-changed', this._refreshStencil, this);
    },

    onDisable: function onDisable() {
        this._super();
        this.node.off('size-changed', this._refreshStencil, this);
        this.node.off('anchor-changed', this._refreshStencil, this);
    },

    _calculateCircle: function _calculateCircle(center, radius, segements) {
        var polies = [];
        var anglePerStep = Math.PI * 2 / segements;
        for (var step = 0; step < segements; ++step) {
            polies.push(cc.v2(radius.x * Math.cos(anglePerStep * step) + center.x, radius.y * Math.sin(anglePerStep * step) + center.y));
        }

        return polies;
    },

    _refreshStencil: function _refreshStencil() {

        this._area = this.getComponent(cc.PolygonCollider).points;

        var contentSize = this.node.getContentSize();
        var anchorPoint = this.node.getAnchorPoint();
        var width = contentSize.width;
        var height = contentSize.height;
        var x = -width * anchorPoint.x;
        var y = -height * anchorPoint.y;
        var color = cc.color(255, 255, 255, 0);
        this._clippingStencil.clear();
        if (this._type === MaskType.RECT) {
            var rectangle = [cc.v2(x, y), cc.v2(x + width, y), cc.v2(x + width, y + height), cc.v2(x, y + height)];
            this._clippingStencil.drawPoly(rectangle, color, 0, color);
        } else if (this._type === MaskType.IRR) {

            this._clippingStencil.drawPoly(this._area, color, 0, color);
        } else if (this._type === MaskType.SP) {

            this._clippingStencil = this.A;
        } else {
            var center = cc.v2(x + width / 2, y + height / 2);
            var radius = { x: width / 2, y: height / 2 };
            var segements = this._segements;
            this._clippingStencil.drawPoly(this._calculateCircle(center, radius, segements), color, 0, color);
        }
    }
});

if (CC_JSB) {
    // override onDestroy
    Mask_extend.prototype.__superOnDestroy = Base.prototype.onDestroy;
    Mask_extend.prototype.onDestroy = function () {
        this.__superOnDestroy();
        this._clippingStencil.release();
        this._clippingStencil = null;
    };
}

cc._RFpop();
},{}],"NetConfig":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e648ajm8FRKKbTsCIIs0DrC', 'NetConfig');
// Script\Common\Configs\NetConfig.js

//与服务端交互的事件类型，r_是只能接收的事件receive。s_客户端请求的事件send

var config = {
    s_login: 'NbLogin', //登陆
    s_enterHouse: 'NbIntoRoomIn', //进入房间的请求
    r_tips: 'NbTip', //直接返回信息结果
    r_enterHouseReturn: 'NbIntoRoomOut', //进入房间后返回的信息
    r_idleTimeOut: 'NbTimeoutOut', //长时间没有做操作
    s_doGrab: 'NbBet', //玩家选中一个投注区域
    r_getAnnouncement: 'NbGameAnnouncement', //公告的接收
    r_passiveOut: 'NbKickOut', //异地登陆
    //classic
    s_calculating: 'NbClassicCounting', //算牌
    s_grabDealerOrSetScore: 'NbClassicMultipleIn', //抢庄或者押注
    r_setScoreEnd: 'NbClassicBetMultipleOverOut', //押倍数结束，单个通知每一个桌内玩家数据.
    r_grabDealerEnd: 'NbClassicDealerMultipleOverOut', //抢庄结束，单个通知每一个桌内玩家数据.
    r_getPlayersInfo: 'NbClassicInitializeOut', //进入房间后，入座初始化桌内玩家数据.
    r_oneGrab: 'NbClassicMultipleOut', //抢庄，押倍数批量通知桌内玩家数据.
    r_startInfo: 'NbClassicNewMatchOut', //游戏开始数据.
    r_oneInsert: 'NbClassicSeatOut', //新玩家入座，批量通知桌内玩家数据.
    r_calculateEnd: 'NbClassicSettleOut', //算牌结束结算数据，批量通知桌内玩家.
    //grab
    r_grab_reStartGame: 'NbBullBaoStartMatchOut', //在房间内重新开始游戏时候的数据
    r_doGrab_return: 'NbBetOut', //玩家选中一个投注区域
    r_otherGrab: 'NbBetBatchOut', //有某个玩家投注
    r_grabEnd: 'NbSettleMatchOut', //投注结束
    r_mineHaveGrab: 'NbSettlePlayerOut', //自己有投注则有这条消息显示收益
    r_oneInHome: 'NbSeatOut', //有某个玩家入座
    s_grab_Exit: 'NbExitRoomIn', //退出房间
    r_grab_exitReturn: 'NbExitRoomOut', //退出房间返回
    //bull100
    r_bull100_reStartGame: 'NbStartMatchOut', //在房间内重新开始游戏时候的数据
    s_bull100_dealerUp: 'NbUpDealerIn', //上庄请求
    s_bull100_dealerContinue: 'NbKeepDealerIn', //续庄请求
    s_bull100_dealerDown: 'NbDownDealerIn', //下庄请求
    s_bull100_getDealerList: 'NbUpDealerListIn', //获取上庄列表
    r_bull100_dealerDownWarning: 'NbWarningDealerOut', //下庄警告
    r_bull100_dealerListReturn: 'NbUpDealerOut', //上庄下发消息
    r_bull100_downDealerReturn: 'NbDownDealerOut' };

//下庄请求回调
window.NetType = config;

cc._RFpop();
},{}],"Obj_backgroundEffect1":[function(require,module,exports){
"use strict";
cc._RFpush(module, '28a78rvxppJzKoK8JjQGnX/', 'Obj_backgroundEffect1');
// Script\Views\Scene_classic\Effects\Obj_backgroundEffect1.js

cc.Class({
    "extends": cc.Component,

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {
        var childList = this.node.children;
        for (var key in childList) {
            var node = childList[key];
            if (node) this._showEffect(node);
        }
    },

    _showEffect: function _showEffect(effectNode) {
        effectNode.scale = 0;

        effectNode.runAction(cc.sequence(cc.delayTime(G_TOOL.getRandomArea(1, 10) / 10), cc.callFunc(this._setRepeat, this)));
    },
    _setRepeat: function _setRepeat(target) {
        var act1 = cc.scaleTo(G_Config_classic.list_backgroundEffect1[0], G_Config_classic.list_backgroundEffect1[1]);
        var act2 = cc.scaleTo(G_Config_classic.list_backgroundEffect1[0], 0);
        target.runAction(cc.repeatForever(cc.sequence(act1, act2)));
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_calculateResult":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ef458IN8zhBxrU698dYUZ8d', 'Obj_calculateResult');
// Script\Objects\Obj_calculateResult.js

cc.Class({
    'extends': cc.Component,
    _choosePokerNum: null, //已经选择的卡牌数量
    properties: {
        _tip_trueResult: null,
        _callFunc: null,

        label_01: cc.Label,
        label_02: cc.Label,
        label_03: cc.Label,
        label_04: cc.Label
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._choosePokerNum = 0;
        this._list_label = [this.label_01, this.label_02, this.label_03, this.label_04];
    },

    showCalculate: function showCalculate() {
        this.node.active = true;
    },

    //选中一张卡牌,超过数量时候需要做处理
    chooseOnePoker: function chooseOnePoker(chooseNum) {
        if (this._choosePokerNum >= G_Config_classic.num_maxChoose) {
            //choose more
            return false;
        }
        this._list_label[this._choosePokerNum].string = chooseNum.toString();
        this._choosePokerNum += 1;
        if (this._choosePokerNum >= G_Config_classic.num_maxChoose) {
            //选中了三张
            var getNum = this._getResult();
            this._list_label[this._choosePokerNum].string = getNum.toString();
        }
        return true;
    },

    //取消某张卡牌的值
    cancelOnePoker: function cancelOnePoker() {
        if (this._tip_trueResult) {
            this._tip_trueResult.forceEnd();
            this._tip_trueResult = null;
        }
        this._choosePokerNum -= 1;
        this._list_label[this._choosePokerNum].string = '';
        this._list_label[G_Config_classic.num_maxChoose].string = '';
    },

    _getResult: function _getResult() {
        var getNum = 0;
        for (var i = 0; i < this._choosePokerNum; i++) {
            getNum += parseInt(this._list_label[i].string);
        }
        return getNum;
    },

    _checkCalculate: function _checkCalculate(value) {},
    _countDownEnd: function _countDownEnd() {
        this._tip_trueResult = null;
        if (this._callFunc) this._callFunc();
    },

    showTrueResult: function showTrueResult(pokerValue, callFunc) {
        if (this._choosePokerNum < G_Config_classic.num_maxChoose) return;

        this._callFunc = callFunc;
        var value = this._getResult();
        if (value % 10 == 0) {
            //calculate success
            var data1 = G_DATA.getChinese(17);
            var data2 = G_DATA.getChinese(16);
            var strList = data2.content.split(',');
            var showStr = G_TOOL.formatStr(data1.content, strList[pokerValue]);
            var dataObj = {
                showStr: showStr,
                showPos: cc.p(this.node.x, this.node.y + this.node.height),
                retainTime: data1.retainTime,
                callBack: this._countDownEnd.bind(this)
            };
            this._tip_trueResult = GG.tipsMgr.showCountDownTip(dataObj);
        }
    },
    showOverTouch: function showOverTouch() {
        var data = G_DATA.getChinese(5);
        var dataObj = {
            showStr: data.content,
            showPos: cc.p(this.node.x, this.node.y + this.node.height),
            retainTime: data.retainTime,
            callBack: this._showCountDown.bind(this)
        };
        GG.tipsMgr.showTxtTip(dataObj);
        this._hideCountDown();
    },
    _showCountDown: function _showCountDown() {
        if (this._tip_trueResult) this._tip_trueResult.showByOpacity();
    },
    _hideCountDown: function _hideCountDown() {
        if (this._tip_trueResult) this._tip_trueResult.hideByOpacity();
    },

    hideCalculate: function hideCalculate() {
        if (!this.node.active) return;
        if (this._tip_trueResult) {
            this._tip_trueResult.forceEnd();
            this._tip_trueResult = null;
        }
        this.onClear();
        this.node.active = false;
    },
    onClear: function onClear() {
        for (var key in this._list_label) {
            this._list_label[key].string = '';
        }
        this._choosePokerNum = 0;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_cell_trend":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e1879pYLx1Gf6hjS0kLc/eK', 'Obj_cell_trend');
// Script\Objects\Obj_cell_trend.js

//走势图和账目条目
cc.Class({
    'extends': cc.Component,

    properties: {
        _list_labelSort: null },

    //整理后的label列表
    // use this for initialization
    onLoad: function onLoad() {
        this._failColor = new cc.Color(187, 111, 110);
        this._winColor = new cc.Color(58, 185, 250);
        this._failStr = '小';
        this._winStr = '大';

        this._list_labelSort = {};
        var labels = this.node.children;
        this._list_labelSort[G_TYPE.pokerTypeStr.spade] = labels[0];
        this._list_labelSort[G_TYPE.pokerTypeStr.heart] = labels[1];
        this._list_labelSort[G_TYPE.pokerTypeStr.club] = labels[2];
        this._list_labelSort[G_TYPE.pokerTypeStr.diamond] = labels[3];
    },

    //设置这个条目中的信息
    setData: function setData(data) {
        if (!data) data = [['SPADE', 1], ['DIAMOND', 0], ['CLUB', 1], ['HEART', 0]]; //胜利是1，失败是0
        var labels = this.node.children;
        for (var i = 0; i < data.length; i++) {
            this._setLabelResult(this._list_labelSort[data[i][0]], parseInt(data[i][1]));
        }
    },
    //设置胜利与否
    _setLabelResult: function _setLabelResult(labelNode, isWin) {
        if (isWin) {
            labelNode.color = this._winColor;
            labelNode.getComponent(cc.Label).string = this._winStr;
        } else {
            labelNode.color = this._failColor;
            labelNode.getComponent(cc.Label).string = this._failStr;
        }
    }

});
// called every frame, uncomment this function to activate update callback

cc._RFpop();
},{}],"Obj_coinEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0f796fh/rJIb4s54HJEIHLg', 'Obj_coinEffect');
// Script\Objects\Obj_coinEffect.js

//游戏大厅金币图案特效
cc.Class({
    'extends': cc.Component,

    properties: {
        _coinAni: null },

    //节点骨骼动画
    // use this for initialization
    onLoad: function onLoad() {
        this._initPlayCoinAni();
    },
    //播放金币特效动画
    _initPlayCoinAni: function _initPlayCoinAni() {
        if (!this._coinAni) {
            this._coinAni = this.node.getComponent(dragonBones.ArmatureDisplay);
            this._coinAni.addEventListener(dragonBones.EventObject.COMPLETE, this._playCoinsEffectAni, this);
        }
        this.node.active = true;
        this._coinAni.playAnimation('coins', 1);
    },

    //每隔3秒循环播放金币特效
    _playCoinsEffectAni: function _playCoinsEffectAni() {
        this.node.runAction(cc.sequence(cc.delayTime(3), cc.callFunc(function () {
            this._initPlayCoinAni();
        }, this)));
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_confirmTip":[function(require,module,exports){
"use strict";
cc._RFpush(module, '953a2PNGbdKQpEu8W0IIJ5g', 'Obj_confirmTip');
// Script\Objects\Obj_confirmTip.js

//點擊確認的界面

cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        _callFunc: null, //点击确定后的回调
        _callFunc2: null, //点击取消后的回调
        _registerFunc: null, //额外注册的回调，关于关闭和开启
        _isPlayEffect: null, //是否正在播放特效

        label_center: cc.Label,
        node_left: cc.Node,
        node_right: cc.Node,
        node_center: cc.Node, //单选的确定按钮
        node_grayLayer: cc.Node },

    //单选的确定按钮
    //node_continueImg : cc.Node,
    // use this for initialization1
    onLoad: function onLoad() {
        this._showTime = G_Config_common.time_confirmTipShow;
        this._maxScale = G_Config_common.scale_confirmTipMax;
        this._touchCompName = 'Obj_touchLimit';
        this._isPlayEffect = false;
        //this._formatStr = '<b>%s<b/>';
        //this._label_left = this.node_left.children[0].getComponent(cc.Label);
        //this._label_right = this.node_right.children[0].getComponent(cc.Label);
    },

    //显示的内容多选
    showContent: function showContent(contentStr, callFunc, callFunc2) {
        this._callFunc = callFunc;
        this._callFunc2 = callFunc2;
        this.node.active = true;
        this._showOneBtn(false);
        this._setContent(contentStr);

        this._showEffect(true);
        this.node_grayLayer.getComponent(this._touchCompName).addTouchLimit();
        //注册监听
        if (this._registerFunc) {
            this._registerFunc(true);
        }
    },

    //显示的内容单选
    showContentEx: function showContentEx(contentStr, callFunc) {
        this._callFunc = callFunc;
        this.node.active = true;
        this._showOneBtn(true);
        this._setContent(contentStr);

        this._showEffect(true);
        this.node_grayLayer.getComponent(this._touchCompName).addTouchLimit();
        //注册监听
        if (this._registerFunc) {
            this._registerFunc(true);
        }
    },

    Btn_touchLeft: function Btn_touchLeft(event) {
        if (this._isPlayEffect) return;
        this._callFunc2 = null;
        this._showEffect(false);
    },
    Btn_touchRight: function Btn_touchRight(event) {
        if (this._isPlayEffect) return;
        this._callFunc = null;
        this._showEffect(false);
    },
    Btn_touchCenter: function Btn_touchCenter(event) {
        if (this._isPlayEffect) return;
        this._showEffect(false);
    },

    //显示界面的特效  isShow ： 判定当前是显示还是关闭
    _showEffect: function _showEffect(isShow) {
        this._isPlayEffect = true;
        var effectNode = this.node_center.parent;
        effectNode.stopAllActions();
        if (isShow) {
            // show tip
            effectNode.scale = 0;

            var act1 = cc.scaleTo(this._showTime, this._maxScale);
            var act2 = cc.scaleTo(this._showTime / 2, 1.0);
            effectNode.runAction(cc.sequence(act1, act2, cc.callFunc(function () {
                this._isPlayEffect = false;
            }, this)));
        } else {
            //close tip
            effectNode.scale = 1;

            var act1 = cc.scaleTo(this._showTime / 2, this._maxScale);
            var act2 = cc.scaleTo(this._showTime, 0);
            effectNode.runAction(cc.sequence(act1, act2, cc.callFunc(function () {
                this._isPlayEffect = false;
                this._removePage();
            }, this)));
        }
    },

    //显示弹窗的内容
    _setContent: function _setContent(str) {
        //this.label_center.string = G_TOOL.formatStr(this._formatStr, str);
        //遇到逗号需要分行
        str = str.replace(',', '\n');
        str = str.replace('，', '\n');
        this.label_center.string = str;
    },

    //是否只显示一个按钮
    _showOneBtn: function _showOneBtn(isOne) {
        this.node_right.active = !isOne;
        this.node_left.active = !isOne;
        this.node_center.active = isOne;
    },

    //移除界面
    _removePage: function _removePage() {
        //注册监听
        if (this._registerFunc) {
            this._registerFunc(false);
        }
        if (this._callFunc) {
            this._callFunc();
            this._callFunc = null;
        }
        if (this._callFunc2) {
            this._callFunc2();
            this._callFunc2 = null;
        }
        this.node_grayLayer.getComponent(this._touchCompName).cancelTouchLimit();
        this.node.active = false;
    },

    //关闭界面
    closePage: function closePage() {
        this._callFunc = null;
        this._callFunc2 = null;
        this._showEffect(false);
    },

    //监听确认框的关闭和开启
    registerFunc: function registerFunc(func, isRegister) {
        if (isRegister) this._registerFunc = func;else this._registerFunc = null;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Obj_countDownProgress":[function(require,module,exports){
"use strict";
cc._RFpush(module, '9bcf6jOxYNHGon7BRMofV/U', 'Obj_countDownProgress');
// Script\Objects\Obj_countDownProgress.js

//圆形进度条，用于倒计时的显示，优化的话可做成预制体

cc.Class({
    'extends': cc.Component,
    _firstFrame: null, //第一张圆形图片
    _callFunc: null, //倒计时回调结束时候调用的函数
    properties: {
        node_progress: cc.Node,
        frame_timeImage1: cc.SpriteFrame, //第二张圆形图片
        frame_timeImage2: cc.SpriteFrame, //第三张圆形图片
        label_count: cc.Label, //绿色字体
        font_green: cc.Font, //绿色字体
        font_orange: cc.Font, //橙色字体
        font_red: cc.Font },

    //红色字体
    // use this for initialization
    onLoad: function onLoad() {},

    _initData: function _initData() {
        this._firstFrame = this.node_progress.getComponentInChildren(cc.Sprite).spriteFrame;
        this._progressBarSprite = this.node_progress.getComponentInChildren(cc.Sprite);
        this._progressComp = this.node_progress.getComponent(cc.ProgressBar);
    },

    //显示圆形进度条的倒计时
    showCountDown: function showCountDown(showTime, callFunc) {
        if (!this._firstFrame) this._initData();
        if (isNaN(showTime)) return;
        this.node.active = true;
        this.node_progress.children[0].active = true;
        this._callFunc = callFunc;
        this._runTime = 0;
        this._interval = 0.97;
        this._speedRate = 1;
        this._curTime = showTime;
        this._firstTime = Math.min(Math.ceil(this._curTime * G_Config_classic.list_centerProgressTime[0]), this._curTime - 1);
        this._secondTime = Math.max(Math.floor(this._curTime * G_Config_classic.list_centerProgressTime[1]), 1);
        this._progressInterval = 1 / this._curTime;

        this._curTime += 1;
        this._runTime = this._interval;
        this._clearLabels();
        this._progressComp.progress = 1;
        this.schedule(this._let_update, 0.01);
    },

    //帧函数
    _let_update: function _let_update(dt) {
        this._progressComp.progress -= this._progressInterval * dt;
        if (this._runTime >= this._interval) {
            this._runTime = 0;
            this._setNumImg();
        } else this._runTime += dt;
    },

    //设置图片
    _setNumImg: function _setNumImg() {
        this._curTime -= 1;
        if (this._curTime <= this._firstTime) {
            if (this._curTime <= this._secondTime) {
                //  step 3
                if (this._curTime <= 0) {
                    //结束倒计时
                    if (this._callFunc) this._callFunc();
                    this._callFunc = null;
                    this._endSchedule();
                } else {
                    this._progressBarSprite.spriteFrame = this.frame_timeImage2;
                    this.label_count.font = this.font_red;
                    this.label_count.string = this._curTime;
                }
            } else {
                //  step 2
                this._progressBarSprite.spriteFrame = this.frame_timeImage1;
                this.label_count.font = this.font_orange;
                this.label_count.string = this._curTime;
            }
        } else {
            //  step 1
            this._progressBarSprite.spriteFrame = this._firstFrame;
            this.label_count.font = this.font_green;
            this.label_count.string = this._curTime;
        }
    },

    _endSchedule: function _endSchedule() {
        this._curTime = 0;
        this._progressComp.progress = 0;
        this.unschedule(this._let_update);
        this.node.active = false;
    },

    getLeaveTime: function getLeaveTime() {
        return this._curTime;
    },

    //强制结束
    forceEnd: function forceEnd() {
        var callFunc = this._callFunc;
        this._callFunc = null;
        this._curTime = 0;
        this._runTime = this._interval;
        return callFunc;
    },

    _clearLabels: function _clearLabels() {
        this.label_count.string = '';
    },

    onDestory: function onDestory() {
        this.unschedule(this._let_update);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dealScroll":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'a9194v8KDNAMoLVXJItM1QP', 'Obj_dealScroll');
// Script\Objects\Obj_dealScroll.js

//自动处理滚动层逻辑

var dataObj = {
    itemPrefab: null,
    lineNum: null,
    scrollType: null
};

cc.Class({
    'extends': cc.Component,

    properties: {
        _itemPrefab: null, //条目的复制体
        _showItemNum: null, //显示的条目数量
        _itemHeight: null, //条目的高度
        _itemWidth: null, //条目的宽度
        _itemOffX: null, //条目之间的间隔
        _itemOffY: null, //条目之间的间隔
        _pool: null, //对象池
        _scrollType: null, //滚动容器的类型
        _viewW2: null, //可视区域宽度的一半
        //背包类型需要用到的属性
        _lineNum: null },

    //每一行的单元数量
    // use this for initialization
    onLoad: function onLoad() {
        this._itemOffY = 0;
        this._itemOffX = 0;
    },

    setData: function setData(dataObj) {
        this._initData();
        this._itemPrefab = dataObj.itemPrefab;
        this._lineNum = dataObj.lineNum;
        this._scrollType = dataObj.scrollType;
        //默认类型
        if (!this._scrollType) this._scrollType = G_TYPE.scrollType.vertical;
    },
    _initData: function _initData() {
        this._showItemNum = 0;
        if (!this._viewW2) this._viewW2 = this.node.parent.width * 0.5;
    },

    //添加一个条目
    addOneItem: function addOneItem() {
        var item = this._getOnePoolNode();

        switch (this._scrollType) {
            case G_TYPE.scrollType.vertical:
                item.y = -this._showItemNum * (this._itemHeight + this._itemOffY);
                break;
            case G_TYPE.scrollType.horizontal:
                //水平滚动层，需要去掉水平方向的widget
                var startPosX = -this._viewW2;
                item.x = startPosX + this._showItemNum * (this._itemWidth + this._itemOffY);
                break;
            case G_TYPE.scrollType.bag:
                var startPosX = -this._viewW2;
                var lineNum;
                if (this._showItemNum == 0) lineNum = 0;else lineNum = this._showItemNum % this._lineNum;
                var rowNum = Math.floor(this._showItemNum / this._lineNum);
                item.x = startPosX + lineNum * (this._itemWidth + this._itemOffY) + this._itemWidth / 2;
                item.y = -rowNum * (this._itemHeight + this._itemOffX);
                break;
            default:
                break;
        }

        this._showItemNum += 1;
        this._resetContainerSize();
        return item;
    },

    //获取某个条目(从0开始)
    getItemByIndex: function getItemByIndex(index) {
        var items = this.node.children;
        var item = items[index];
        if (item) return item;
        return this.addOneItem();
    },

    //清理条目（清理index之后的条目, 不包括index）
    clearItems: function clearItems(index) {
        var items = this.node.children;
        for (var i = items.length - 1; i > index; i--) {
            if (items[i]) this._removePoolNode(items[i]);
        }
    },

    //直接设置容器的大小
    setContentSize: function setContentSize(width, height) {
        if (width) this.node.width = width;
        if (height) this.node.height = height;
    },

    //=====================================================

    //获取一个庄家条目
    _getOnePoolNode: function _getOnePoolNode() {
        if (!this._itemPrefab) return null;
        if (!this._pool) this._pool = new cc.NodePool('Obj_dealScroll' + this._itemPrefab.name);

        var cNode = this._pool.get();
        if (!cNode) {
            cNode = cc.instantiate(this._itemPrefab);
            if (!this._itemHeight) this._itemHeight = cNode.height;
            if (!this._itemWidth) this._itemWidth = cNode.width;
        }
        cNode.parent = this.node;
        cNode.active = true;
        return cNode;
    },

    _removePoolNode: function _removePoolNode(cNode) {
        cNode.active = false;
        this._pool.put(cNode);

        this._showItemNum -= 1;
        this._resetContainerSize();
    },

    _resetContainerSize: function _resetContainerSize() {
        var contentH, contentW;
        switch (this._scrollType) {
            case G_TYPE.scrollType.vertical:
                contentW = this._itemWidth + this._itemOffX;
                contentH = this._showItemNum * (this._itemHeight + this._itemOffY);
                break;
            case G_TYPE.scrollType.horizontal:
                contentW = this._showItemNum * (this._itemWidth + this._itemOffX);
                contentH = this._itemHeight + this._itemOffY;
                break;
            case G_TYPE.scrollType.bag:
                contentW = this._lineNum * (this._itemWidth + this._itemOffX);
                var rowNum = Math.ceil(this._showItemNum / this._lineNum);
                contentH = rowNum * (this._itemHeight + this._itemOffY);
                break;
            default:
                break;
        }
        this.node.height = contentH;
        this.node.width = contentW;
    },

    //获取当前已经显示的条目数量
    getShowItemNum: function getShowItemNum() {
        return this._showItemNum;
    },

    onDestroy: function onDestroy() {
        if (this._pool) this._pool.clear();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dealerListCell":[function(require,module,exports){
"use strict";
cc._RFpush(module, '687f8AC+TdI2q0s9AnuqZPO', 'Obj_dealerListCell');
// Script\Objects\Obj_dealerListCell.js

//上庄列表庄家条目

cc.Class({
    'extends': cc.Component,

    properties: {
        _dealerWord: null, //庄字动画效果

        sprite_headImg: {
            'default': null,
            type: cc.Sprite,
            displayName: '头像'
        },
        label_name: {
            'default': null,
            type: cc.Label,
            displayName: '名字'
        },
        label_gold: {
            'default': null,
            type: cc.Label,
            displayName: '金币'
        },
        prefab_dealerAni: {
            'default': null,
            type: cc.Prefab,
            displayName: '庄字动画'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        this._setHeadImg(this.sprite_headImg, data.headImg);
        this._setPlayerName(data.name);
        this._setGoldValue(data.gold);
        if (data.isDealer) this._addDealerWord();else this._delDealerWord();
    },

    _setHeadImg: function _setHeadImg(spriteComp, imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = spriteComp.spriteFrame;
        if (!imgName) spriteComp.spriteFrame = this._defaultHeadFrame;else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'unknown1';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    spriteComp.spriteFrame = spriteFrame;
                }).bind(this));
            } else {
                this._headUrl = G_DATA.getResNetRoot(imgName);
                //this._headUrl = 'http://192.168.0.109:8080/classic/res/raw-assets/Texture/Common/head/head00.jpg';
                cc.loader.load(this._headUrl, (function (err, texture) {
                    if (err) {
                        console.log(err);
                    } else {
                        if (texture) {
                            var frame = new cc.SpriteFrame(texture);
                            spriteComp.spriteFrame = frame;
                        }
                    }
                }).bind(this));
            }
        }
    },

    _setPlayerName: function _setPlayerName(playerName) {
        if (!playerName) playerName = '';
        playerName = G_TOOL.getNameLimit(playerName);
        this.label_name.string = playerName;
    },
    _setGoldValue: function _setGoldValue(value) {
        if (!value) value = 0;
        this.label_gold.string = G_TOOL.changeMoney(value);;
    },

    //添加庄字动画
    _addDealerWord: function _addDealerWord() {
        this._dealerWord = cc.instantiate(this.prefab_dealerAni);
        this._dealerWord.parent = this.node;
        this._dealerWord.position = cc.p(-this.node.width * 0.15, -this.node.height * 0.5);
    },

    //删除庄字动画
    _delDealerWord: function _delDealerWord() {
        if (this._dealerWord) {
            this._dealerWord.destroy();
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dealerListSlider":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7110ckrbJVGjadOXy4EREkS', 'Obj_dealerListSlider');
// Script\Objects\Obj_dealerListSlider.js

//上庄进度条处理

cc.Class({
    'extends': cc.Component,

    properties: {
        _sliderComp: null, //进度条组件
        _ui_moveNum: null, //移动的数字
        _label_moveNum: null, //移动的数字
        _oneWordWidth: null, //一个数字的长度
        _offNum: null, //间隔数额
        _curMoveNum: null, //当前的移动到的数额
        _handleNode: null, //滑动的点
        _idleMoney: null, //空闲的资金，如果有则可以增大上庄金额

        node_lightLine: {
            'default': null,
            type: cc.Node,
            displayName: '进度条'
        },
        node_touchLimit: {
            'default': null,
            type: cc.Node,
            displayName: '进度条触摸吞噬层'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._sliderComp = this.node.getComponent(cc.Slider);

        this._baseWordNum = 4.5;
        this._time_numHide = 2;

        this._addSliderEvent();
        this._initMoveNum();
        this._resetLightLine();
    },
    //增加监听事件
    _addSliderEvent: function _addSliderEvent() {
        this._sliderComp.node.on('slide', this._sliderMove, this);
        this._sliderComp.node.on(cc.Node.EventType.TOUCH_END, this.sliderEnd, this);

        var handle = this.node.getChildByName('Handle');
        if (handle) {
            var btn = handle.getComponent(cc.Button);
            handle.on(cc.Node.EventType.TOUCH_CANCEL, this.sliderEnd, this);
        }
    },
    //初始化可操作节点
    _initMoveNum: function _initMoveNum() {
        var handle = this.node.getChildByName('Handle');
        if (handle.children.length > 0) {
            this._ui_moveNum = handle.children[0];
            this._label_moveNum = this._ui_moveNum.children[0].getComponent(cc.Label);
            this._oneWordWidth = this._ui_moveNum.width / this._baseWordNum;
        }
    },

    //myGold: 自己拥有的金币; offGold：移动金币的差值，minLimitGold：最小金币限额
    setData: function setData(myGold, offGold, minLimitGold) {
        if (!myGold) {
            this._setTouchLimit(true);
            return;
        }
        this._playerGold = myGold;
        this._offNum = offGold;
        this._minNum = minLimitGold;
        this._idleMoney = this._playerGold - this._minNum;

        var firstNum;
        if (this._playerGold < this._minNum) {
            //钱不足
            this._setTouchLimit(true);
            firstNum = this._minNum;
            this._setGoldProgress(0);
        } else if (this._playerGold < this._minNum + this._offNum) {
            this._setTouchLimit(true);
            this._setGoldProgress(1);
            this._resetLightLine();
            firstNum = this._minNum;
        } else {
            this._setTouchLimit(false);
            firstNum = this._minNum;
            this._setGoldProgress(0);
        }
        this._setUIMoveNum(firstNum);
        this._setMoveNumShow(true);
        this._isShow = true;
    },

    //移动回调，如果进度条有移动，则这里会被调用
    _sliderMove: function _sliderMove(event) {
        //console.log(event)
        this._resetLightLine();
        //显示数值
        var num = this._idleMoney * this._sliderComp.progress;
        var changeNum = Math.floor(num / this._offNum) * this._offNum;
        this._setUIMoveNum(changeNum + this._minNum);

        this._setMoveNumShow(true);
    },
    sliderEnd: function sliderEnd() {
        this._addTimeHide();
    },

    //设置进度
    _setGoldProgress: function _setGoldProgress(value) {
        this._sliderComp.progress = value;
        this._resetLightLine();
    },

    //设置进度条滑动的表现
    _resetLightLine: function _resetLightLine() {
        this.node_lightLine.width = this.node.width * this._sliderComp.progress;
    },

    //=================================滑动点上的数字显示

    //显示移动在进度条上的数值
    _setUIMoveNum: function _setUIMoveNum(num) {
        this._curMoveNum = num;
        num += '';
        var len = num.length;
        this._ui_moveNum.width = len * this._oneWordWidth;
        this._label_moveNum.string = num;

        //this._ui_moveNum.x = this._handleNode.x;
    },

    //获取选择数值
    getMoveNum: function getMoveNum() {
        return this._curMoveNum;
    },

    //控制进度条上面的数值的显隐
    _setMoveNumShow: function _setMoveNumShow(isShow) {
        if (this._ui_moveNum) {
            this._ui_moveNum.stopAllActions();
            if (isShow) {
                this._ui_moveNum.opacity = 255;
                this._ui_moveNum.active = true;
            } else this._ui_moveNum.active = false;
        }
    },

    _addTimeHide: function _addTimeHide() {
        if (this._ui_moveNum) {
            this._ui_moveNum.stopAllActions();
            this._ui_moveNum.runAction(cc.sequence(cc.fadeOut(this._time_numHide), cc.callFunc(function () {
                this._setMoveNumShow(false);
            }, this)));
        }
    },

    //==========================================

    _setTouchLimit: function _setTouchLimit(isLimit) {
        if (isLimit) {
            this.node_touchLimit.active = true;
            this.node_touchLimit.on(cc.Node.EventType.TOUCH_START, this._onClick_Touch, this);
        } else {
            this.node_touchLimit.off(cc.Node.EventType.TOUCH_START, this._onClick_Touch, this);
            this.node_touchLimit.active = false;
        }
    },
    _onClick_Touch: function _onClick_Touch() {}

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dealerWordEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f34b9gL6nxOYr0lRtDl0NMD', 'Obj_dealerWordEffect');
// Script\Objects\Obj_dealerWordEffect.js

//庄家庄字特效
cc.Class({
    'extends': cc.Component,

    properties: {
        _dealerWordAni: null },

    //节点骨骼动画
    // use this for initialization
    onLoad: function onLoad() {},

    //播放庄字特效动画
    playDealerWordAni: function playDealerWordAni() {
        if (!this._dealerWordAni) {
            this._dealerWordAni = this.node.getComponent(dragonBones.ArmatureDisplay);
            this._dealerWordAni.addEventListener(dragonBones.EventObject.COMPLETE, this._playDealerWordEffectAni, this);
        }
        this.node.active = true;
        this._dealerWordAni.playAnimation('zhuang_mark_2', 1);
    },

    //循环播放的庄字特效
    _playDealerWordEffectAni: function _playDealerWordEffectAni() {
        this._dealerWordAni.playAnimation('zhuang_mark_1', 0);
    },

    //清理庄字特效动画
    clearDealerWordAni: function clearDealerWordAni() {
        if (this.node) this.node.active = false;
    }
});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dialogAccountCell":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f1326nfQM5IboIc/NP0kvBu', 'Obj_dialogAccountCell');
// Script\Objects\Obj_dialogAccountCell.js

//账目条目
cc.Class({
    'extends': cc.Component,

    properties: {
        _fontsColor: null, //颜色为红色的字
        label_time: {
            'default': null,
            type: cc.Label,
            displayName: '充值时间'
        },
        label_type: {
            'default': null,
            type: cc.Label,
            displayName: '操作类型'
        },
        label_money: {
            'default': null,
            type: cc.Label,
            displayName: '金额'
        },
        label_balance: {
            'default': null,
            type: cc.Label,
            displayName: '余额'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._fontsColor = new cc.Color(255, 0, 0);
    },

    setData: function setData(data) {
        this.label_time.string = data.completiontime;
        this._setType(data.transactiontype);
        this._setMoney(data.transactionmoney);
        this.label_balance.string = data.balance;
    },

    _setType: function _setType(type) {
        if (type != 'DEPOSIT') {
            //颜色为红色
            this.label_type.node.color = this._fontsColor;
            this.label_type.string = G_CHINESE.turnOut; //转出
        } else {
                this.label_type.string = G_CHINESE.recharge; //充值
            }
    },

    _setMoney: function _setMoney(money) {
        if (money < 0) {
            //颜色为红色
            this.label_money.node.color = this._fontsColor;
        }
        this.label_money.string = money;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dialogContentCell":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8156bsdEGtATY39dH3NmvBf', 'Obj_dialogContentCell');
// Script\Objects\Obj_dialogContentCell.js

//公告和规则预制体
cc.Class({
    'extends': cc.Component,

    properties: {
        sprite_picture: {
            'default': null,
            type: cc.Sprite,
            displayName: '图片资源'
        },

        label_textContent: {
            'default': null,
            type: cc.Label,
            displayName: '文本内容'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData() {
        this.label_textContent.string = "公告与规则内容";
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dialogPlayerListCell":[function(require,module,exports){
"use strict";
cc._RFpush(module, '06af7RA1NNLNZrMIRx0CFe8', 'Obj_dialogPlayerListCell');
// Script\Objects\Obj_dialogPlayerListCell.js

//玩家列表条目
cc.Class({
    'extends': cc.Component,

    properties: {
        sprite_headImg: {
            'default': null,
            type: cc.Sprite,
            displayName: '头像'
        },
        label_name: {
            'default': null,
            type: cc.Label,
            displayName: '名字'
        },
        label_gold: {
            'default': null,
            type: cc.Label,
            displayName: '金币'
        }

    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        this._setHeadImg(this.sprite_headImg, data.avatarUrl);
        this._setPlayerName(data.nickname);
        this._setGoldValue(data.coin);
    },

    _setHeadImg: function _setHeadImg(spriteComp, imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = spriteComp.spriteFrame;
        if (!imgName) spriteComp.spriteFrame = this._defaultHeadFrame;else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'unknown1';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    spriteComp.spriteFrame = spriteFrame;
                }).bind(this));
            } else {
                this._headUrl = G_DATA.getResNetRoot(imgName);
                //this._headUrl = 'http://192.168.0.109:8080/classic/res/raw-assets/Texture/Common/head/head00.jpg';
                cc.loader.load(this._headUrl, (function (err, texture) {
                    if (err) {
                        console.log(err);
                    } else {
                        if (texture) {
                            var frame = new cc.SpriteFrame(texture);
                            spriteComp.spriteFrame = frame;
                        }
                    }
                }).bind(this));
            }
        }
    },

    _setPlayerName: function _setPlayerName(playerName) {
        if (!playerName) playerName = '';
        //玩家列表玩家条目名称限制3个字符
        playerName = G_TOOL.getNameLimit(playerName, 3);
        this.label_name.string = playerName;
    },
    _setGoldValue: function _setGoldValue(value) {
        if (!value) value = 0;
        this.label_gold.string = G_TOOL.changeMoney(value);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_dialogRecordCell":[function(require,module,exports){
"use strict";
cc._RFpush(module, '3006d3TqmhAraFdhAM3/ALl', 'Obj_dialogRecordCell');
// Script\Objects\Obj_dialogRecordCell.js

//玩家记录查询预制体
cc.Class({
    'extends': cc.Component,

    properties: {
        _fontsColor: null, //红色字显示
        label_time: {
            'default': null,
            type: cc.Label,
            displayName: '对局时间'
        },
        label_roomType: {
            'default': null,
            type: cc.Label,
            displayName: '房间类型'
        },
        label_roomName: {
            'default': null,
            type: cc.Label,
            displayName: '房间名称'
        },
        label_bet: {
            'default': null,
            type: cc.Label,
            displayName: '押注'
        },
        label_result: {
            'default': null,
            type: cc.Label,
            displayName: '结果'
        },
        label_outCome: {
            'default': null,
            type: cc.Label,
            displayName: '胜负'
        }

    },

    // use this for initialization
    onLoad: function onLoad() {
        this._fontsColor = new cc.Color(255, 0, 0);
    },

    setData: function setData(data) {
        this.label_time.string = data.confirmtime;
        this._setRoom(data.gamemodelname, data.gameroomname);
        this._setBet(data.effectiveamount);
        this._setResult(data.profitamount);
        this._setOutCome(data.profitamount);
    },
    //房间名字为红色
    _setRoom: function _setRoom(gameModelName, gameRoomName) {
        this.label_roomType.string = gameModelName.substring(0, 2) + ":";
        this.label_roomName.string = gameRoomName;
    },

    _setBet: function _setBet(bet) {
        if (!bet) {
            this.label_bet.string = G_CHINESE.dealer; // 当庄
            return;
        }
        this.label_bet.string = bet;
    },

    _setResult: function _setResult(result) {
        if (result < 0) {
            //颜色为红色
            this.label_result.node.color = this._fontsColor;
        }
        this.label_result.string = result;
    },

    _setOutCome: function _setOutCome(outCome) {
        var str;
        if (outCome < 0) {
            //颜色为红色
            str = G_CHINESE.loseMatch; // 负
            this.label_outCome.node.color = this._fontsColor;
        } else {
            str = G_CHINESE.winMatch; // 胜
        }
        this.label_outCome.string = str;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_effect_inHomeLoading":[function(require,module,exports){
"use strict";
cc._RFpush(module, '14d3aZ8ia5Aeo4hKGQ1GkG5', 'Obj_effect_inHomeLoading');
// Script\Objects\Obj_effect_inHomeLoading.js

//进入房间时候的加载动画

cc.Class({
    'extends': cc.Component,

    properties: {
        _maxProgress: null, //进度滚动的最大效果0-1
        _toSceneName: null, //准备跳转的场景名字

        _loadingNum: null, //正在加载的数量
        label_tip: {
            'default': null,
            type: cc.Label,
            displayName: '加载时候的提示信息'
        },
        label_rate: {
            'default': null,
            type: cc.Label,
            displayName: '显示百分比'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this.node._firstPosY = this.node.y;
        this._node_sliderImg = this.node.children[0];
        this._node_sliderImg._firstPosY = this._node_sliderImg.y;

        this._firstMaxProgress = 0.9;
        var runTime = 2;
        this._curProgress = 0;
        this._loadingNum = 0;
        //每秒的进度
        this._progressInSec = this._firstMaxProgress / runTime;
        this._isStartEffect = false;
    },

    start: function start() {
        //进入加载状态
        GG.setIsLoading(true);
        //开始预加载
        this.preloadedScene();
        //额外的资源加载
        var self = this;
        if (!GG.audioMgr.getIsReloadGame()) {
            this._loadingNum += 1;
            GG.audioMgr.reloadGameAudio(function () {
                self._oneCompleted();
            });
        }
        this._table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonLoading);
        if (!this._table) {
            this._setLoadingTable();
        } else {
            this._showTableTime();
        }
    },

    //预加载场景
    preloadedScene: function preloadedScene() {
        //直接显示进度信息
        this._showSliderEffect();
        //获取场景跳转信息
        var reloadInfo = GG.getPlayer().getReloadSceneInfo();
        if (reloadInfo && reloadInfo.sceneName) {
            var self = this;
            this._toSceneName = reloadInfo.sceneName;
            this._loadingNum += 1;
            cc.director.preloadScene(reloadInfo.sceneName, function () {
                self._oneCompleted();
            });
        }
    },
    //有某个资源加载完成
    _oneCompleted: function _oneCompleted() {
        this._loadingNum -= 1;
        if (this._loadingNum <= 0) {
            //所有资源加载完成，且进度达到90%,则将进度特效播放到结束
            this._firstMaxProgress = 1;
            this._isStartEffect = true;
        }
    },

    //开始进度条的显示，直接播放到90%等待
    _showSliderEffect: function _showSliderEffect() {
        this._loadingNum += 1;
        this._curProgress = 0;
        this._setSliderValue(this._curProgress);
        this._isStartEffect = true;
    },
    //设置显示进度 0-1
    _setSliderValue: function _setSliderValue(rate) {
        this._setRateInfo(rate);
        var len = rate * this.node.height;
        var needH = this.node.height - len;
        this.node.y = -needH;
        this._node_sliderImg.y = needH;
    },
    //设置提示信息
    _setTipInfo: function _setTipInfo(str) {
        if (this.label_tip) {
            this.label_tip.string = str;
        }
    },
    //设置百分比信息
    _setRateInfo: function _setRateInfo(rate) {
        var str = Math.floor(rate * 100) + '%';
        if (this.label_rate) {
            this.label_rate.string = str;
        }
    },

    // called every frame, uncomment this function to activate update callback
    update: function update(dt) {
        if (this._isStartEffect) {
            this._playerSliderEffect(dt);
        }
    },
    //播放进度动画到90%
    _playerSliderEffect: function _playerSliderEffect(dt) {
        if (this._curProgress >= this._firstMaxProgress) {
            //达到第一阶段，如90%
            this._isStartEffect = false;
            this._oneCompleted();
            return;
        }
        this._curProgress += dt * this._progressInSec;
        this._setSliderValue(this._curProgress);
        if (this._curProgress >= 1) {
            this._curProgress = 1;
            this._setSliderValue(this._curProgress);
            this._isStartEffect = false;
            //进度已经走完
            cc.director.loadScene(this._toSceneName);
            clearInterval(this.inervalId);
        }
    },

    //设置文本表格
    _setLoadingTable: function _setLoadingTable() {
        GG.tableMgr.reloadTables(G_RES_URL.dict_tablesName.commonLoading, this._getLoadingTable.bind(this));
    },
    //获取文本表格
    _getLoadingTable: function _getLoadingTable() {
        this._table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonLoading);
        if (this._table) {
            var loadMessage = this._table.getFirstData().content;
            this._setTipInfo(loadMessage);
            this._showTableTime();
        }
    },
    _getRandomTable: function _getRandomTable() {
        var index = G_TOOL.getRandomArea(1, 7);
        var loadMessage = this._table.getDataByID(index).content;
        return loadMessage;
    },
    //定时显示
    _showTableTime: function _showTableTime() {
        var self = this;
        this.inervalId = setInterval(function () {
            self._setTipInfo(self._getRandomTable());
        }, 2000);
    },
    onDestroy: function onDestroy() {}
});

cc._RFpop();
},{}],"Obj_goldResultEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'ffaf0Vp7ShAUbno9OmhAAEZ', 'Obj_goldResultEffect');
// Script\Objects\Obj_goldResultEffect.js

//显示在头像上面的金币增减的漂浮特效

cc.Class({
    'extends': cc.Component,
    _callFunc: null, //金币特效结束后回调
    properties: {
        node_addGold: cc.Node,
        node_lostGold: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._reloadLabel();
    },

    _reloadLabel: function _reloadLabel() {
        this._label_add = this.node_addGold.getComponentInChildren(cc.Label);
        this._label_lost = this.node_lostGold.getComponentInChildren(cc.Label);
    },

    showGrabGold: function showGrabGold(count) {
        if (!count) return;
        if (!this._label_add) this._reloadLabel();

        if (count > 0) {
            count = '+' + count;
            this._label_lost.string = '';
            this._label_add.string = count;
        } else {
            //count = '-'+count;
            this._label_add.string = '';
            this._label_lost.string = count;
        }
    },

    showEffect: function showEffect(value, startPos, targetY, callFunc) {
        this.node.active = true;
        this._callFunc = callFunc;
        if (value > 0) {
            this.node_addGold.active = true;
            this.node_lostGold.active = false;
            this._label_add.string = '+' + value;
            this._showGoldAddEffect(startPos, targetY);
        } else if (value < 0) {
            this.node_lostGold.active = true;
            this.node_addGold.active = false;
            this._label_lost.string = value;
            this._showGoldLostEffect(startPos, targetY);
        } else {
            //value == 0
            this.node_addGold.active = true;
            this.node_lostGold.active = false;
            this._label_add.string = value;
            this._showGoldAddEffect(startPos, targetY);
        }
    },

    //金币显示的移动效果
    _showGoldAddEffect: function _showGoldAddEffect(startPos, targetY) {
        this.node.zIndex = -1;
        this.node.position = startPos;
        this.node.scale = 0;
        this.node.opacity = 0;

        var time = G_Config_classic.list_goldAddFly[0];
        var act1 = cc.moveTo(time, 0, targetY).easing(cc.easeOut(3.0));
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.fadeTo(time, 255);

        var act5 = cc.callFunc(this._addEffectMid, this, startPos);
        this.node.runAction(cc.sequence(cc.spawn(act1, act2, act3), act5));
    },
    _addEffectMid: function _addEffectMid(target, startPos) {
        this.node.zIndex = 1;
        var time = G_Config_classic.list_goldAddFly[1];
        var act1 = cc.delayTime(G_Config_classic.list_goldAddFly[2]);
        var act2 = cc.fadeTo(time, 100);
        var act3 = cc.moveTo(time, startPos).easing(cc.easeInOut(3.0));
        var act4 = cc.scaleTo(time, 0.5);
        var act5 = cc.callFunc(this._effectEnd, this);
        this.node.runAction(cc.sequence(act1, cc.spawn(act2, act3, act4), act5));
    },

    _showGoldLostEffect: function _showGoldLostEffect(startPos, targetY) {
        this.node.zIndex = -1;
        this.node.position = startPos;
        this.node.scale = 0;
        this.node.opacity = 255;

        var time = G_Config_classic.list_goldLostFly[0];
        var act1 = cc.moveTo(time, 0, targetY).easing(cc.easeOut(3.0));
        var act2 = cc.scaleTo(time, 1);
        var act3 = cc.delayTime(G_Config_classic.list_goldLostFly[1]);
        var act4 = cc.fadeOut(G_Config_classic.list_goldLostFly[2]);
        var act5 = cc.callFunc(this._effectEnd, this);
        this.node.runAction(cc.sequence(cc.spawn(act1, act2), act3, act4, act5));
    },

    _effectEnd: function _effectEnd() {
        this.hideGold();
        if (this._callFunc) this._callFunc();
    },

    //隐藏
    hideGold: function hideGold() {
        this.node.active = false;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_goldsContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '77608wtKXBFOYPUMcPVJ0dp', 'Obj_goldsContainer');
// Script\Objects\Obj_goldsContainer.js

//金币管理容器

cc.Class({
    'extends': cc.Component,

    properties: {
        //_isUpdate : null,                                                           //是否开启金币的移动
        _dict_goldSort: null, //已经投注的金币的整理字典

        frame_gold: {
            'default': null,
            type: cc.SpriteFrame,
            displayName: '金币图标'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        //金币的移动时间
        this._goldMoveTime = 0.4;
        //胜利金币回收的移动速度
        this._recoveGoldMoveTime = 1;
        //金币的缩放
        this._goldScale = G_Config_common.scale_moveGold;

        this._dict_goldSort = {};
    },

    start: function start() {
        if (GG.curMgr) this._tableContainer = GG.curMgr.getTableContainer();
    },

    //从玩家到投注区域
    playerToTable: function playerToTable(dataObj) {
        //var dataObj = G_OBJ.data_flyGold_playerToTable();
        var goldPosList = this._tableContainer.getGoldPosList(dataObj.tableIndex, dataObj.goldNum);
        var targetPos, goldNode, lastGold;
        for (var i = 0; i < dataObj.goldNum; i++) {
            targetPos = goldPosList[i];
            if (targetPos) {
                goldNode = this._getOneGold();
                this._addFlyAction(goldNode, {
                    startPos: dataObj.startPos,
                    targetPos: targetPos
                });
                if (!this._dict_goldSort[dataObj.tableIndex]) this._dict_goldSort[dataObj.tableIndex] = [];
                this._dict_goldSort[dataObj.tableIndex].push(goldNode);
                lastGold = goldNode;
            }
        }
        if (dataObj.goldNum > 0) GG.audioMgr.playSound(15);
        if (lastGold) lastGold.callBack = dataObj.callFunc;
    },
    //从投注区域到玩家
    tableToPlayer: function tableToPlayer(dataObj) {
        //var dataObj = G_OBJ.data_flyGold_tableToPlayer();
        var goldList = this._getRemoveGold(dataObj.tableIndex, dataObj.goldNum);
        var goldNode;
        for (var i = 0; i < dataObj.goldNum; i++) {
            goldNode = goldList[i];
            if (goldNode) {
                this._addFlyAction2(goldNode, {
                    //startPos : startPos,  //可以让其从当前的位置出发
                    targetPos: dataObj.targetPos
                });
            }
        }
        //if(dataObj.goldNum > 0) GG.audioMgr.playSound(18);
    },

    //回收剩余的金币
    recoverAllGold: function recoverAllGold(targetPos) {
        var goldList = [],
            isHaveGold = false;
        for (var tableIndex in this._dict_goldSort) {
            if (this._dict_goldSort[tableIndex]) goldList = goldList.concat(this._dict_goldSort[tableIndex]);
        }
        this._dict_goldSort = {};
        var goldNode;
        for (var i = 0; i < goldList.length; i++) {
            goldNode = goldList[i];
            if (goldNode) {
                isHaveGold = true;
                this._addFlyAction2(goldNode, {
                    //startPos : startPos,  //可以让其从当前的位置出发
                    targetPos: targetPos
                });
            }
        }
        //if(goldList.length > 0) GG.audioMgr.playSound(18);
        return isHaveGold;
    },

    //从一个投注区域中获取随机的几个金币,用于回收移动
    _getRemoveGold: function _getRemoveGold(tableIndex, goldNum) {
        if (!this._dict_goldSort[tableIndex]) return [];
        if (goldNum >= this._dict_goldSort[tableIndex].length) {
            var goldList = this._dict_goldSort[tableIndex].concat([]);
            this._dict_goldSort[tableIndex] = null;
            return goldList;
        } else {
            return this._dict_goldSort[tableIndex].splice(0, goldNum);
        }
    },

    //增加一个金币移动的动作---投注金币
    _addFlyAction: function _addFlyAction(goldNode, flyInfo) {
        var startPos = flyInfo.startPos;
        var targetPos = flyInfo.targetPos;
        if (startPos) goldNode.position = startPos;
        var act = cc.moveTo(this._goldMoveTime, targetPos).easing(cc.easeOut(3.0));
        goldNode.runAction(cc.sequence(act, cc.callFunc(this._goldFlyEnd, this, flyInfo.isRemove)));
    },
    //增加一个金币移动的动作---回收金币
    _addFlyAction2: function _addFlyAction2(goldNode, flyInfo) {
        var startPos = flyInfo.startPos;
        var targetPos = flyInfo.targetPos;
        if (startPos) goldNode.position = startPos;
        var act = cc.moveTo(this._goldMoveTime * 2, targetPos);
        goldNode.runAction(cc.sequence(act, cc.callFunc(this._goldFlyEnd, this, true)));
    },
    //金币移动结束
    _goldFlyEnd: function _goldFlyEnd(goldNode, isRemove) {
        if (goldNode.callBack) {
            goldNode.callBack();
            goldNode.callBack = null;
        }

        if (isRemove) {
            this._removeGold(goldNode);
        }
    },

    //===================

    //获取一个金币
    _getOneGold: function _getOneGold() {
        if (!this.frame_gold) return null;
        if (!this._pool) this._pool = new cc.NodePool('Obj_goldContainer');

        var goldNode = this._pool.get();
        if (!goldNode) {
            goldNode = new cc.Node();
            var sp = goldNode.addComponent(cc.Sprite);
            sp.spriteFrame = this.frame_gold;
        }
        goldNode.parent = this.node;
        //goldNode.scale = (1/this.node.parent.scale)*this._goldScale;
        goldNode.scale = this._goldScale;
        goldNode.active = true;
        return goldNode;
    },
    //移除金币
    _removeGold: function _removeGold(goldeNode) {
        goldeNode.stopAllActions();
        goldeNode.active = false;
        this._pool.put(goldeNode);
    },

    getGoldSize: function getGoldSize() {
        if (!this._goldSize) {
            var gold = this._getOneGold();
            this._goldSize = gold.getContentSize();
            this._removeGold(gold);
        }
        return this._goldSize;
    },

    //清理所有
    clearAll: function clearAll() {
        var goldList = this.node.children;
        for (var i = goldList.length - 1; i >= 0; i--) {
            this._removeGold(goldList[i]);
        }

        this._dict_goldSort = {};
        this.node.stopAllActions();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    onDestroy: function onDestroy() {
        if (this._pool) {
            this._pool.clear();
        }
    }
});

cc._RFpop();
},{}],"Obj_leftTopMenu":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c17a0Sz5yVIXJmuOlz/I4bH', 'Obj_leftTopMenu');
// Script\Objects\Obj_leftTopMenu.js

//左上角的菜单按钮

cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        _isOpen: null, //是否打开菜单
        _isPlayingEff: null, //是否正在播放特效
        _exitFunc: null, //退出回调
        _changeFunc: null, //换桌回调

        node_dirImg: {
            'default': null,
            type: cc.Node,
            displayName: '菜单方向容器'
        },
        node_btnContainer: {
            'default': null,
            type: cc.Node,
            displayName: '按钮容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._isOpen = false;
        this._scaleTime = 0.2;
        this._waitTime = 3.5;

        var exitBtn = this.node_btnContainer.children[0]; //退出
        var changeBtn = this.node_btnContainer.children[1]; //换桌
        this.registerButton(this.node_dirImg, this.OnClick_menu, this);
        this.registerButton(exitBtn, this.OnClick_exit, this);
        this.registerButton(changeBtn, this.OnClick_change, this);

        this.setBtnEnable(changeBtn, false);
    },

    //点击菜单
    OnClick_menu: function OnClick_menu(target) {
        this._isOpen = !this._isOpen;
        this._changeDirImg(this._isOpen);
        this._showOpenEffect();
    },
    //点击退出
    OnClick_exit: function OnClick_exit(target) {
        this.OnClick_menu();
        if (this._exitFunc) this._exitFunc();
    },
    //点击换桌
    OnClick_change: function OnClick_change(target) {
        this.OnClick_menu();
        if (this._changeFunc) this._changeFunc();
    },

    //设置退出函数
    bindFunc: function bindFunc(exitFunc, changeFunc) {
        this._exitFunc = exitFunc;
        this._changeFunc = changeFunc;
    },

    //==================================效果显示

    //箭头方向
    _changeDirImg: function _changeDirImg(isOpen) {
        this.node_dirImg.children[0].active = !isOpen;
        this.node_dirImg.children[1].active = isOpen;
    },

    //显示打开菜单特效
    _showOpenEffect: function _showOpenEffect() {
        if (!this._isOpen) {
            //已经在播放动画了
            this._showCloseEffect();
            return;
        }
        this.node_btnContainer.stopAllActions();
        this.node_btnContainer.active = true;
        this.node_btnContainer.scaleY = 0;

        var time = this._scaleTime;
        var scaleAct = cc.scaleTo(time, 1, 1);
        this.node_btnContainer.runAction(cc.sequence(scaleAct, cc.callFunc(this._addWaitAct, this)));
    },
    //增加等待动作
    _addWaitAct: function _addWaitAct() {
        this.node_btnContainer.stopAllActions();
        var time1 = this._waitTime;
        this.node_btnContainer.runAction(cc.sequence(cc.delayTime(time1), cc.callFunc(this.OnClick_menu, this)));
    },
    //显示收起菜单的特效
    _showCloseEffect: function _showCloseEffect() {
        this.node_btnContainer.stopAllActions();
        var time = this._scaleTime / 2;
        var scaleAct = cc.scaleTo(time, 1, 0);
        this.node_btnContainer.runAction(scaleAct);
    },

    resetShow: function resetShow() {
        this._changeDirImg(false);
        this.node_btnContainer.active = false;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Obj_loadingEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, '84acfna9cFK5qjICnuMxaqf', 'Obj_loadingEffect');
// Script\Objects\Obj_loadingEffect.js

//loading动作中光的管理

cc.Class({
    "extends": cc.Component,

    properties: {
        _curTime: null, //倒计时所用的时间
        _callFunc: null, //倒计时结束后回调

        node_leftCanvas: cc.Node,
        node_rightCanvas: cc.Node,
        node_leftWebGL: cc.Node,
        node_rightWebGL: cc.Node,

        node_loadingAni: cc.Node,
        label_countDown: cc.Label,
        label_tips: cc.Label
    },

    // use this for initialization
    onLoad: function onLoad() {

        this._eyeMoveTime = G_Config_common.time_eyeEffect[0];
        this._eyePauseTime = G_Config_common.time_eyeEffect[1];
        this._initShow();
    },

    //显示加载
    showLoading: function showLoading(callFunc) {
        this._callFunc = callFunc;
        var comp = this.node_loadingAni.getComponent(dragonBones.ArmatureDisplay);
        comp.playAnimation(G_RES_URL.boneAni.loading);
        this.showCountDown(10);
    },
    //显示速配
    showMatch: function showMatch(callFunc) {
        this._callFunc = callFunc;
        var comp = this.node_loadingAni.getComponent(dragonBones.ArmatureDisplay);
        comp.playAnimation(G_RES_URL.boneAni.matching);
        this.showCountDown(30);
    },

    //显示倒计时
    showCountDown: function showCountDown(time, callFunc) {
        this.node.active = true;

        if (!time) return;
        this._curTime = time;
        this.label_countDown.string = this._curTime;
        this.schedule(this._updateFunc, 1.0);
    },
    //强制停止倒计时
    forceStop: function forceStop() {
        if (this._curTime > 0) this.unschedule(this._updateFunc);
        this._curTime = 0;
        if (this._callFunc) {
            this._callFunc();
            this._callFunc = null;
            this.node.destroy();
        }
    },
    _updateFunc: function _updateFunc(dt) {
        this._curTime -= 1;
        this.label_countDown.string = this._curTime;
        if (this._curTime <= 0) {
            this._curTime = 0;
            this.unschedule(this._updateFunc);
            this.forceStop();
            return;
        }
    },

    _initShow: function _initShow() {
        var _isCanvas = cc._renderType === cc.game.RENDER_TYPE_CANVAS;
        if (_isCanvas) {
            this.node_leftWebGL.active = false;
            this.node_rightWebGL.active = false;
            this.node_leftCanvas.active = true;
            this.node_rightCanvas.active = true;

            this.node_leftLight = this.node_leftCanvas;
            this.node_rightLight = this.node_rightCanvas;
        } else {
            this.node_leftCanvas.active = false;
            this.node_rightCanvas.active = false;
            this.node_leftWebGL.active = true;
            this.node_rightWebGL.active = true;

            this.node_leftLight = this.node_leftWebGL;
            this.node_rightLight = this.node_rightWebGL;
        }

        this._curTime = 0;
        var _startLeftPos = this.node_leftLight.position;
        var _startRightPos = this.node_rightLight.position;
        var _leftEyeSize = this.node_leftLight.parent.getContentSize();
        var _rightEyeSize = this.node_rightLight.parent.getContentSize();
        if (!_leftEyeSize.width) {
            //canvas
            _leftEyeSize = cc.size(61, 50);
            _rightEyeSize = cc.size(61, 50);
        }

        this._runLight(this.node_leftLight, _leftEyeSize, _startLeftPos);
        this._runLight(this.node_rightLight, _rightEyeSize, _startRightPos);
    },

    //运行光的循环特效
    _runLight: function _runLight(node, baseSize, startPos) {
        if (!node || !baseSize) return;

        node.position = startPos;

        var act1 = cc.moveTo(this._eyeMoveTime, startPos.x + baseSize.width, startPos.y - baseSize.height);
        var act2 = cc.moveTo(0, startPos);
        var act3 = cc.delayTime(this._eyePauseTime);
        node.runAction(cc.repeatForever(cc.sequence(act1, act2, act3)));
    },

    // 显示提示文本
    showTips: function showTips(content) {
        this.label_tips.string = content;
    },

    removeEff: function removeEff() {
        this.node.destory();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_notice":[function(require,module,exports){
"use strict";
cc._RFpush(module, '77d28KRz+1JubOiS0r0wfYZ', 'Obj_notice');
// Script\Objects\Obj_notice.js

//跑马灯，公告

cc.Class({
    "extends": cc.Component,
    _moveSpeed: null, //每秒移动的速度
    _isMove: null, //是否移动
    _list_msg: null, //显示消息的队列
    _firstPosX: null, //通知出现的初始位置
    properties: {
        node_view: cc.Node, //显示节点
        node_content: cc.Node },

    //滚动节点
    // node_wifi1 : cc.Node,                               //wifi1
    // node_wifi2 : cc.Node,                               //wifi2
    // node_wifi3 : cc.Node,                               //wifi3
    // use this for initialization
    onLoad: function onLoad() {
        this._curLabelNode = this.node_content.children[0];
        this._curLabel1 = this._curLabelNode.getComponent(cc.RichText);
        this._firstPosX = this._curLabelNode.x;
        // if(this.node_wifi1) this.node_wifi1.parent.active = false;

        this._resetData();
    },

    //设置公告运行属性
    _resetData: function _resetData() {
        this._isMove = false;
        this._moveSpeed = G_Config_common.notice_speed;
        this._runTime = 0;
        this._interval_next = G_Config_common.notice_showTime;
        this._list_msg = [];
    },

    ////开启wifi实时监听
    //openWifiAni : function () {
    //
    //},
    ////关闭wifi监听
    //closeWifiAni : function () {
    //
    //},
    //隐藏公告
    hideNotice: function hideNotice() {
        this._isMove = false;
        this.node.active = false;
    },

    //增加准备显示的公告消息
    addMsg: function addMsg(msg) {
        this._resetShowContent(msg);
        //msg = "<color=#00ff00>Ric111111</c><color=#0fffff>Text</color>12132132132131313213213213211";
    },

    ////增加优先显示的消息(当前显示结束后显示)
    //addHeightPriorityMsg : function (msg) {
    //    if(!this._list_msg) this._resetData();
    //    this._list_msg.splice(0,0,msg);
    //    this._setNextMsg();
    //},
    //
    ////直接显示的消息(将会替换掉当前的消息)
    //addFirstMsg : function (msg) {
    //    if(!this._list_msg) this._resetData();
    //    this._curLabel1.string = msg;
    //    this._restartLabel();
    //},

    //===========================================

    //下一条公告
    //_setNextMsg : function () {
    //    if(this._isMove) return;
    //
    //    if(this._list_msg.length > 0){
    //        this.node.active = true;
    //        var msg = this._list_msg[0];
    //        this._list_msg.splice(0, 1);
    //
    //        this._curLabel1.string = msg;
    //        this._restartLabel();
    //    }else this.node.active = false;
    //},

    //重新设置内容
    _resetShowContent: function _resetShowContent(msg) {
        this.node.active = true;
        this._isMove = true;
        this._curLabel1.string = msg;
        this._restartLabel();
    },

    //重新开始移动label
    _restartLabel: function _restartLabel() {
        this._curLabelNode.x = this._firstPosX;
        this._runTime = 0;
        this._isMove = true;
    },

    // called every frame, uncomment this function to activate update callback
    update: function update(dt) {
        if (this._isMove) {
            this._curLabelNode.x -= dt * this._moveSpeed;
            if (this._runTime >= this._interval_next) {
                this._isMove = false;
                this.node.active = false;
            } else this._runTime += dt;
        }
    }
});

cc._RFpop();
},{}],"Obj_onPokerResult":[function(require,module,exports){
"use strict";
cc._RFpush(module, '64c66ub77NHYbI6OjWUwiq5', 'Obj_onPokerResult');
// Script\Objects\Obj_onPokerResult.js

//卡牌结果的显示

cc.Class({
    'extends': cc.Component,

    properties: {
        node_upImage: cc.Node,
        node_downImage: cc.Node,
        Atlas_onPokerResult: cc.SpriteAtlas
    },

    // use this for initialization
    onLoad: function onLoad() {},

    _initSprite: function _initSprite() {
        this._upSprite = this.node_upImage.getComponent(cc.Sprite);
        this._downSprite = this.node_downImage.getComponent(cc.Sprite);
        this._dontFrame = this.Atlas_onPokerResult.getSpriteFrame(G_RES_URL.atlas_pokerResult.dont);
    },

    //显示牌型计算完成, 显示完成两个字
    showCalculateSuccess: function showCalculateSuccess() {
        this.node.active = true;
        if (!this._upSprite) this._initSprite();
        this.node_downImage.active = false;
        this._upSprite.spriteFrame = this._dontFrame;
    },

    //显示牌型
    showPokerResultValue: function showPokerResultValue(pokerValue, isOverAni) {
        this.node.active = true;
        if (!this._upSprite) this._initSprite();

        if (pokerValue <= 0) {
            //没牛
            this.node_downImage.active = false;
            pokerValue = 'no';
            if (this._callFunc) {
                this._callFunc();
                this._callFunc = null;
            }
        } else {
            this.node_downImage.active = true;
            if (!isOverAni) this._openResultEffect();else {
                if (this._callFunc) {
                    this._callFunc();
                    this._callFunc = null;
                }
            }
        }

        var toFrame = this.Atlas_onPokerResult.getSpriteFrame(G_RES_URL.atlas_pokerResult.resultImgName + pokerValue);
        this._upSprite.spriteFrame = toFrame;
    },
    _openResultEffect: function _openResultEffect() {
        this.node_upImage.scale = 0;
        this.node_downImage.scale = 0;

        var time = G_Config_classic.time_pokerResultShow;
        var act1 = cc.sequence(cc.scaleTo(time, G_Config_classic.scale_pokerResultShow), cc.scaleTo(time / 2, 1.0));
        var act2 = cc.scaleTo(time, 1.0);
        this.node_downImage.runAction(act1);
        this.node_upImage.runAction(cc.sequence(act2, cc.callFunc(function () {
            if (this._callFunc) {
                this._callFunc();
                this._callFunc = null;
            }
        }, this)));
    },

    setTruePos: function setTruePos(pos) {
        this.node.position = pos;
        this.hideOnPokerResult();
    },

    hideOnPokerResult: function hideOnPokerResult() {
        this.node.active = false;
    },

    getIsShow: function getIsShow() {
        return this.node.active;
    },

    showResult: function showResult(resultPos, resultValue, isOverAni, callFunc) {
        this._callFunc = callFunc;
        if (isOverAni) {
            //跳过动画
            this.node.position = resultPos;
            this.showPokerResultValue(resultValue, true);
        } else {
            this.setTruePos(resultPos);
            this.showPokerResultValue(resultValue);
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_othersGold":[function(require,module,exports){
"use strict";
cc._RFpush(module, '59d126ycJtMSpOA1alnpVbK', 'Obj_othersGold');
// Script\Objects\Obj_othersGold.js

cc.Class({
    "extends": cc.Component,
    _defaultFrame: null, //默认显示的头像
    properties: {
        // foo: {
        //    default: null,      // The default value will be used only when the component attaching
        //                           to a node for the first time
        //    url: cc.Texture2D,  // optional, default is typeof default
        //    serializable: true, // optional, default is true
        //    visible: true,      // optional, default is true
        //    displayName: 'Foo', // optional
        //    readonly: false,    // optional, default is false
        // },
        // ...
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._defaultFrame = this.node.getComponentInChildren(cc.Sprite).spriteFrame;
        this._interval = this.node.width * 1.15;
    },

    showInfo: function showInfo(xIndex, goldNum, headImgName) {
        this.node.active = true;
        if (!headImgName) headImgName = this._defaultFrame;
        this.node.getComponentInChildren(cc.Sprite).spriteFrame = headImgName;

        this.node.getComponentInChildren(cc.Label).string = goldNum.toString();

        this.node.x = xIndex * this._interval;

        this.node.tag = xIndex;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_pageResult_effect":[function(require,module,exports){
"use strict";
cc._RFpush(module, '501eeONeNlKaYxVHQLZoKF7', 'Obj_pageResult_effect');
// Script\Views\Scene_classic\Effects\Obj_pageResult_effect.js

//结算面板的特效配置

cc.Class({
    "extends": cc.Component,
    _isShowWin: null, //是否显示胜利
    properties: {
        node_background: cc.Node,
        node_winTop: cc.Node,
        node_failTop: cc.Node,
        node_winStar1: cc.Node,
        node_winStar2: cc.Node,
        node_failStar1: cc.Node,
        node_failStar2: cc.Node
    },

    // use this for initialization
    onLoad: function onLoad() {},

    //背景效果
    showBGEffect: function showBGEffect(isWin) {
        this._isShowWin = isWin;
        if (isWin) {
            this.node_winTop.active = true;
            this.node_failTop.active = false;
        } else {
            this.node_winTop.active = false;
            this.node_failTop.active = true;
        }
        this.node_background.scale = 0;

        var topNode;
        if (this._isShowWin) topNode = this.node_winTop;else topNode = this.node_failTop;
        topNode.scale = 0;
        topNode.runAction(cc.sequence(cc.scaleTo(G_Config_classic.time_pageResult[0], 1), cc.callFunc(this._playBackgroundEffect, this)));

        this._showStarEffect();
    },

    //显示放在上层的星星特效
    _showStarEffect: function _showStarEffect() {
        var time = G_Config_classic.time_pageResult[2];
        if (this._isShowWin) {
            //win
            this._showOneWinStar(this.node_winStar1, time);
            this._showOneWinStar(this.node_winStar2, time);
        } else {
            //fail
            this._showOneFailStar(this.node_failStar1, time);
            this._showOneFailStar(this.node_failStar2, time);
        }
    },
    _showOneWinStar: function _showOneWinStar(starNode, time) {
        starNode.active = true;
        starNode.opacity = 0;
        starNode.scale = 0;

        var act1 = cc.fadeIn(time * 0.7);
        var act2 = cc.scaleTo(time * 0.7, 2);
        var act3 = cc.fadeOut(time * 0.3);
        starNode.runAction(cc.sequence(cc.spawn(act1, act2), act3));
    },
    _showOneFailStar: function _showOneFailStar(starNode, time) {
        starNode.active = true;
        starNode.opacity = 0;
        starNode.scale = 0;
        starNode.rotation = 0;

        var act1 = cc.fadeIn(time * 0.7);
        var act2 = cc.scaleTo(time * 0.7, 2);
        var act3 = cc.fadeOut(time * 0.3);
        var act4 = cc.rotateBy(time / 3, 360);
        starNode.runAction(cc.sequence(cc.spawn(act1, act2), act3));
        starNode.runAction(cc.repeatForever(act4));
    },

    _playBackgroundEffect: function _playBackgroundEffect() {
        this.node_background.y = this.node_background.height / 2;
        var act = cc.spawn(cc.scaleTo(G_Config_classic.time_pageResult[1], 1), cc.moveTo(G_Config_classic.time_pageResult[1], 0, 0));
        this.node_background.runAction(cc.sequence(act, cc.callFunc(this._bgEffectEnd, this)));
    },

    //所有特效播放结束
    _bgEffectEnd: function _bgEffectEnd() {
        this.node_winStar1.stopAllActions();
        this.node_winStar2.stopAllActions();
        this.node_failStar1.stopAllActions();
        this.node_failStar2.stopAllActions();
        this.node_winStar1.active = false;
        this.node_winStar2.active = false;
        this.node_failStar1.active = false;
        this.node_failStar2.active = false;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_playerBlock_effect":[function(require,module,exports){
"use strict";
cc._RFpush(module, '4637fWL/kxHwIjrjDvRCVTK', 'Obj_playerBlock_effect');
// Script\Views\Scene_classic\Effects\Obj_playerBlock_effect.js

//所有在头像区域播放的特效都在这里管理

cc.Class({
    'extends': cc.Component,
    _ani_confirmDealer: null, //确定庄家后显示的特效
    _ani_dealerWord: null, //庄字效果
    _confirmEffectName: null, //确定庄家时候的动画名称
    _ani_getGoldEffect: null, //获取金币的特效
    _getGoldAniName: null, //获取金币时候的头像特效名字
    _goldResultComp: null, //金币增减结果的显示类
    properties: {
        node_playerHead: cc.Node, //玩家的头像节点
        node_frame: cc.Node, //玩家位置的高亮边框
        prefab_ani_dealerWord: cc.Prefab, //动画预制体, 庄字特效
        prefab_ani_confirmDealer: cc.Prefab, // 确定庄家后的特效
        prefab_ani_getGold: cc.Prefab, //获取金币的特效
        node_multiple_base: cc.Node, //显示抢几的父节点
        atlas_multiple: cc.SpriteAtlas, //显示抢几中用到的图片文字
        prefab_goldValueFly: cc.Prefab, //金币结果的显示特效
        node_goldLabel: cc.Node },

    //金币显示的label
    // use this for initialization
    onLoad: function onLoad() {
        this._blockComp = this.getComponent('Classic_playerBlock');
        this._goldResultCompName = 'Obj_goldResultEffect';

        if (this._blockComp.getBlockIsHorizontal()) {
            this._confirmEffectName = G_RES_URL.ani_getDealerName.horizontal;
            this._getGoldAniName = G_RES_URL.ani_getGoldOnHeadName.horizontal;
        } else {
            this._confirmEffectName = G_RES_URL.ani_getDealerName.vertical;
            this._getGoldAniName = G_RES_URL.ani_getGoldOnHeadName.vertical;
        }

        //显示抢几的节点中显示的图片
        this.node_multiple_left = this.node_multiple_base.children[0];
        this.node_multiple_right = this.node_multiple_base.children[1];
        this._multiple_rightPosX = this.node_multiple_right.x;
    },

    //========================================

    //显示随机选庄动画
    showGrabDealer: function showGrabDealer() {
        this.node_frame.active = true;
    },
    hideGrabDealer: function hideGrabDealer() {
        this.node_frame.active = false;
    },

    //========================================

    //显示确定庄家后的特效
    showConfirmDealer: function showConfirmDealer() {
        if (!this._ani_confirmDealer) {
            this._ani_confirmDealer = cc.instantiate(this.prefab_ani_confirmDealer);
            this.node.addChild(this._ani_confirmDealer, 0, this._getDealerTag);
        }
        this._ani_confirmDealer.active = true;
        this._ani_confirmDealer.getComponent(cc.Animation).play(this._confirmEffectName);
        this.node.runAction(cc.sequence(cc.delayTime(G_Config_classic.time_confirmDealer), cc.callFunc(this._hideConfirmDealer, this)));
    },
    _hideConfirmDealer: function _hideConfirmDealer(target) {
        if (this._ani_confirmDealer) {
            this._ani_confirmDealer.getComponent(cc.Animation).stop();
            this._ani_confirmDealer.active = false;
        }
        if (target) {
            this.showGrabDealer();
            this._showDealerWord();
        }
    },

    //========================================

    //显示庄字特效
    _showDealerWord: function _showDealerWord() {
        if (!this._ani_dealerWord) {
            this._ani_dealerWord = cc.instantiate(this.prefab_ani_dealerWord);
            this.node.addChild(this._ani_dealerWord);
            this._ani_dealerWord.x = this.node_playerHead.parent.x + this.node_playerHead.width / 2;
            this._ani_dealerWord.y = this.node_playerHead.parent.y + this.node_playerHead.height / 2;
        } else this._ani_dealerWord.active = true;
        this._ani_dealerWord.getComponent(cc.Animation).play();
    },
    _hideDealerWord: function _hideDealerWord() {
        if (this._ani_dealerWord) {
            this._ani_dealerWord.active = false;
        }
    },

    //========================================

    //显示抢庄倍数(0--》4)
    showGrabDealerValue: function showGrabDealerValue(multipleValue) {
        if (multipleValue === undefined) return;
        this.node_multiple_base.active = true;
        this.node_multiple_right.x = this._multiple_rightPosX;
        if (multipleValue != 0) this.node_multiple_left.active = true;else {
            this.node_multiple_left.active = false;
            this.node_multiple_right.x -= this.node_multiple_right.width;
        }

        var frame = this.atlas_multiple.getSpriteFrame(G_RES_URL.atlas_chooseMultipleName['dealer_' + multipleValue]);
        if (frame) this.node_multiple_right.getComponent(cc.Sprite).spriteFrame = frame;
    },
    //显示下分倍数
    showSetScoreValue: function showSetScoreValue(multipleValue) {
        if (!multipleValue) return;
        this.node_multiple_base.active = true;
        this.node_multiple_left.active = false;
        this.node_multiple_right.x = this._multiple_rightPosX;
        //this.node_multiple_right.x -= this.node_multiple_right.width;
        this.node_multiple_right.x = 0;
        var frame = this.atlas_multiple.getSpriteFrame(G_RES_URL.atlas_chooseMultipleName['score_' + multipleValue]);
        if (frame) this.node_multiple_right.getComponent(cc.Sprite).spriteFrame = frame;else this.hideMultiple();
    },
    //隐藏倍数
    hideMultiple: function hideMultiple() {
        this.node_multiple_base.active = false;
    },

    //========================================

    //显示获取金币时候的头像特效
    showGetGoldEffect: function showGetGoldEffect() {
        if (!this._ani_getGoldEffect) {
            this._ani_getGoldEffect = cc.instantiate(this.prefab_ani_getGold);
            this._ani_getGoldEffect.parent = this.node;
        }
        this._ani_getGoldEffect.active = true;
        this._ani_getGoldEffect.getComponent(cc.Animation).play(this._getGoldAniName);
        this._ani_getGoldEffect.getComponent(cc.Animation).on('finished', this._hideGetGoldEffect, this);
    },
    _hideGetGoldEffect: function _hideGetGoldEffect() {
        this._ani_getGoldEffect.getComponent(cc.Animation).stop();
        this._ani_getGoldEffect.active = false;
    },

    //========================================

    //显示金币结果的显示特效
    showGoldValueFly: function showGoldValueFly(num) {
        if (!this._goldResultComp) {
            var goldResultEffect = cc.instantiate(this.prefab_goldValueFly);
            goldResultEffect.parent = this.node;
            this._goldResultComp = goldResultEffect.getComponent(this._goldResultCompName);
        }
        var callFunc = null;
        if (num > 0) callFunc = this._showGoldFlyEnd.bind(this);
        this._goldResultComp.showEffect(num, this.node_goldLabel.parent.position, this.node_playerHead.height, callFunc);
    },
    _showGoldFlyEnd: function _showGoldFlyEnd() {
        this.node_goldLabel.runAction(cc.sequence(cc.scaleTo(0.15, 1.3), cc.scaleTo(0.15, 1)));
    },

    //显示金币减少
    showGoldLostFly: function showGoldLostFly(num) {
        if (!this._goldResultComp) {
            var goldResultEffect = cc.instantiate(this.prefab_goldValueFly);
            goldResultEffect.parent = this.node;
            this._goldResultComp = goldResultEffect.getComponent(this._goldResultCompName);
        }
        this._goldResultComp.showGoldLost(num, this.node_goldLabel.parent.position, this.node_playerHead.height, this._showGoldFlyEnd.bind(this));
    },

    //========================================

    clearAllEffect: function clearAllEffect() {
        this._hideConfirmDealer();
        this._hideDealerWord();
        this.hideGrabDealer();
        this.hideMultiple();
    }

});
//========================================

// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_poker":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e21cbBuH+ZDuJqo2L/4lK4r', 'Obj_poker');
// Script\Objects\Obj_poker.js

//使用的卡牌对象

cc.Class({
    'extends': cc.Component,
    _isMyself: null, //是否是玩家自己
    _ownPlayer: null, //属于哪个玩家
    _isChoose: null, //是否被选中
    _callFunc: null, //开牌动作的回调
    _pokerValue: null, //卡牌的值
    _pokerType: null, //卡牌的类型
    _label_value: null, //显示牌值的label
    properties: {
        node_pokerPlus: cc.Node,
        node_pokerNegative: cc.Node,
        node_pokerValue: cc.Node,
        node_minIcon: cc.Node,
        node_maxIcon: cc.Node,
        node_specialIcon: cc.Node,
        frame_pokerUI: cc.SpriteAtlas, //卡牌基础显示图片集
        font_black: cc.Font, //黑色字体
        font_red: cc.Font },

    //红色字体
    // use this for initialization
    onLoad: function onLoad() {
        this._init();

        //this.setPokerInfo(G_TYPE.pokerType.redHeart, 12)
    },

    _init: function _init() {
        this._isChoose = false;

        this._label_value = this.node_pokerValue.getComponent(cc.Label);
        this._sprite_min = this.node_minIcon.getComponent(cc.Sprite);
        this._sprite_max = this.node_maxIcon.getComponent(cc.Sprite);
        this._openPokerTime = G_Config_common.time_pokerOpen;
        this._classic_otherScale = G_Config_classic.scale_noMyPoker;
        this._classic_flyTime = G_Config_classic.time_pokerFly;
        this._classic_moveUp = G_Config_classic.num_pokerUp;

        this.node_pokerPlus.active = true;
    },

    bindPlayer: function bindPlayer(player) {
        this._ownPlayer = player;
    },

    //显示卡牌的背面
    showPokerNegative: function showPokerNegative() {
        this.node_pokerPlus.y = 0;
        this.node_pokerPlus.scale = 1;
        //this.node_pokerPlus.active = true;

        this.node_pokerNegative.active = true;
        this.node_pokerNegative.scale = 1;
    },
    showPokerPlus: function showPokerPlus() {
        //this.node_pokerPlus.active = true;
        if (this.node_pokerNegative) this.node_pokerNegative.active = false;
    },

    //=========================================

    //移动卡牌
    flyPoker: function flyPoker(startPos, targetPos, isMyself) {
        this.onClear();
        this.showPokerNegative();
        this._isMyself = isMyself;
        if (!this._isMyself) {
            //不是属于当前玩家的卡牌
            this.node.scale = this._classic_otherScale;
        }

        this.node.position = startPos;
        var curPosX = targetPos.x; //easeQuarticActionOut
        var act1 = cc.moveTo(this._classic_flyTime, curPosX, targetPos.y);
        var act1 = cc.moveTo(0.4, curPosX, targetPos.y);
        this.node.runAction(cc.sequence(act1, cc.callFunc(this._flyPokerEnd, this)));
    },
    _flyPokerEnd: function _flyPokerEnd() {
        if (this._isMyself) this.openPoker();

        this._ownPlayer.addFlyEndPoker();
    },

    //=========================================

    //打开卡牌
    openPoker: function openPoker(callBack) {
        if (this.node_pokerNegative.active === false) return false;
        this.node_pokerNegative.active = false;
        this.node_pokerPlus.active = false;
        this._callFunc = callBack;
        this.showPokerNegative();

        var rotate1 = cc.scaleTo(this._openPokerTime * 0.5, 0, 1);
        this.node_pokerNegative.runAction(cc.sequence(rotate1, cc.callFunc(this._openPoker_showValue, this)));
        return true;
    },
    _openPoker_showValue: function _openPoker_showValue() {
        this.node_pokerNegative.active = false;
        this.node_pokerPlus.active = true;
        this.node_pokerPlus.scaleX = 0;
        var rotate1 = cc.scaleTo(this._openPokerTime * 0.5, 1, 1);
        this.node_pokerPlus.runAction(cc.sequence(rotate1, cc.callFunc(this._openPoker_end, this)));
    },
    _openPoker_end: function _openPoker_end() {
        //openPoker_end-------------------
        if (this._callFunc) this._callFunc();
        this._callFunc = null;
    },

    //=========================================

    //打出卡牌结果  actionCallFunc : 卡牌移动到开牌位置后的回调
    //moveToResultPos : function (targetPosY, actionCallFunc) {
    //    this.node_pokerPlus.y = 0;
    //    var pos = cc.p(this.node.x, targetPosY);
    //    if(actionCallFunc)this.node.runAction(cc.sequence(cc.moveTo(G_Config_classic.time_pokerMoveToResult, pos), actionCallFunc));
    //    else this.node.runAction(cc.moveTo(G_Config_classic.time_pokerMoveToResult, pos));
    //},

    //=========================================

    //获取一个定制的牌面显示
    setPokerInfo: function setPokerInfo(pokerType, pokerValue, isOpen) {
        this.node.active = true;
        this.node_maxIcon.active = true;
        if (this.node_specialIcon) this.node_specialIcon.active = false;
        this._pokerValue = pokerValue;
        this._pokerType = pokerType;
        if (isOpen) this.showPokerPlus();else this.showPokerNegative();

        //确定卡牌类型
        switch (pokerType) {
            case G_TYPE.pokerType.block:
                //方块
                this._label_value.font = this.font_red;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blockMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blockMax);
                break;
            case G_TYPE.pokerType.blackHeart:
                //黑色桃心
                this._label_value.font = this.font_black;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blackHeartMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.blackHeartMax);
                break;
            case G_TYPE.pokerType.redHeart:
                //红色桃心
                this._label_value.font = this.font_red;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.redHeartMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.redHeartMax);
                break;
            case G_TYPE.pokerType.flower:
                //梅花
                this._label_value.font = this.font_black;
                this._sprite_min.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.flowerMin);
                this._sprite_max.spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.flowerMax);
                break;
        }
        //确定卡牌的值
        var realyValue;
        switch (pokerValue) {
            case 1:
                realyValue = 'A';
                break;
            case 10:
                realyValue = '1';
                break;
            case 11:
                realyValue = 'J';
                // this.node_maxIcon.active = false;
                // this.node_specialIcon.active = true;
                // this.node_specialIcon.getComponent(cc.Sprite).spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.value_J);
                break;
            case 12:
                realyValue = 'Q';
                // this.node_maxIcon.active = false;
                // this.node_specialIcon.active = true;
                // this.node_specialIcon.getComponent(cc.Sprite).spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.value_Q);
                break;
            case 13:
                realyValue = 'K';
                // this.node_maxIcon.active = false;
                // this.node_specialIcon.active = true;
                // this.node_specialIcon.getComponent(cc.Sprite).spriteFrame = this.frame_pokerUI.getSpriteFrame(G_RES_URL.pokerType.value_K);
                break;
            default:
                realyValue = pokerValue;
                break;
        }

        this._label_value.string = realyValue;
    },

    //=========================================

    getPokerValue: function getPokerValue() {
        return this._pokerValue;
    },

    getPokerType: function getPokerType() {
        return this._pokerType;
    },

    getPokerSize: function getPokerSize() {
        return this.node_pokerNegative.getContentSize();
    },

    //是否黑色卡牌
    getIsBlack: function getIsBlack() {
        return this.isBlackColor;
    },

    //设置卡牌是否可以点击
    setPokerTouchEnable: function setPokerTouchEnable(isEnable) {
        if (isEnable) {
            this.node_pokerPlus.on(cc.Node.EventType.TOUCH_START, this._callback_clickPoker, this);
        } else {
            this.node_pokerPlus.off(cc.Node.EventType.TOUCH_START, this._callback_clickPoker, this);
        }
    },

    //点击卡牌的回调
    _callback_clickPoker: function _callback_clickPoker(event) {
        var poker = event.target;
        if (this._isChoose) {
            poker.y -= this._classic_moveUp;
            this._isChoose = false;
            this._ownPlayer.touchThePokerDown();
        } else {
            var value = this.getPokerValue();
            if (value > 10) value = 10;
            var isOk = this._ownPlayer.touchThePokerUp(value);
            if (isOk) {
                poker.y += this._classic_moveUp;
                this._isChoose = true;
            }
        }
    },

    onClear: function onClear() {
        this._isChoose = false;
        this.node.scale = 1;
    },

    clearAllActions: function clearAllActions() {
        this.node_pokerNegative.stopAllActions();
        this.node_pokerPlus.stopAllActions();
        this.node.stopAllActions();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },

    onDestroy: function onDestroy() {
        //this.setPokerTouchEnable(false);
    }
});

cc._RFpop();
},{}],"Obj_rankItem_friend":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c45a8ez/DpOGqvONZJKQxoV', 'Obj_rankItem_friend');
// Script\Objects\Obj_rankItem_friend.js

//好友排行榜条目处理

cc.Class({
    'extends': cc.Component,

    properties: {

        richRankBG: {
            'default': [],
            type: cc.SpriteFrame,
            displayName: '富豪榜排名背景'
        },
        gainRankBG: {
            'default': [],
            type: cc.SpriteFrame,
            displayName: '盈利榜排名背景'
        },
        sprite_hat: {
            'default': null,
            type: cc.Sprite,
            displayName: '皇冠'
        },
        labelRank: {
            'default': null,
            type: cc.Label,
            displayName: '排名数字'
        },
        sprite_headImg: {
            'default': null,
            type: cc.Sprite,
            displayName: '头像'
        },
        label_name: {
            'default': null,
            type: cc.Label,
            displayName: '名字'
        },
        label_goldValue: {
            'default': null,
            type: cc.Label,
            displayName: '金币数值'
        },
        node_goldBG: {
            'default': null,
            type: cc.Node,
            displayName: '金币背景'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    init: function init(isGain, rank, playerInfo) {
        this._setHat(isGain, rank);

        this._setPlayerName(playerInfo.nickname);
        this._setGoldValue(playerInfo.walletbalance ? playerInfo.walletbalance : playerInfo.profitamount);
        this._setHeadImg(this.sprite_headImg, playerInfo.avatarurl);
    },

    _setHat: function _setHat(isGain, rank) {
        if (rank < 3) {
            this.labelRank.node.active = false;
            if (isGain) {
                this.sprite_hat.spriteFrame = this.gainRankBG[rank];
            } else {
                this.sprite_hat.spriteFrame = this.richRankBG[rank];
            }
        } else {
            this.labelRank.node.active = true;
            this.labelRank.string = (rank + 1).toString();
        }
    },

    _setHeadImg: function _setHeadImg(spriteComp, imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = spriteComp.spriteFrame;
        if (imgName) {
            this._headUrl = G_DATA.getResNetRoot(imgName);
            //this._headUrl = 'http://192.168.0.109:8080/classic/res/raw-assets/Texture/Common/head/head00.jpg';
            cc.loader.load(this._headUrl, (function (err, texture) {
                if (err) {
                    console.log(err);
                } else {
                    if (texture) {
                        var frame = new cc.SpriteFrame(texture);
                        spriteComp.spriteFrame = frame;
                    }
                }
            }).bind(this));
        } else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'unknown1';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    spriteComp.spriteFrame = spriteFrame;
                }).bind(this));
            }
        }
    },

    _setPlayerName: function _setPlayerName(playerName) {
        if (!playerName) playerName = '';
        playerName = G_TOOL.getNameLimit(playerName, 16, false);
        this.label_name.string = playerName;
    },

    _setGoldValue: function _setGoldValue(value) {
        var str;
        if (!value) {
            str = G_CHINESE.noRecord;
            this.node_goldBG.active = false;
        } else {
            str = value + '';
            if (str.length >= 17) {
                str = G_CHINESE.upperLimit;
            } else {
                str = G_TOOL.changeMoney(value);
            }
            this.node_goldBG.active = true;
            // this._resetBackground(str);   保留
        }
        this.label_goldValue.string = str;
    },

    //改变背景长度
    _resetBackground: function _resetBackground(str) {
        if (!this._widthRate) this._widthRate = this.node_goldBG.width / 6;
        if (!this.node_goldBG._firstW) this.node_goldBG._firstW = this.node_goldBG.width;

        var wordLen = 0,
            name = str,
            curStr,
            reg = /^[\u4E00-\u9FA5]+$/;;
        for (var i = 0; i < name.length; i++) {
            curStr = name[i];
            if (reg.test(curStr)) {
                wordLen += 2;
            } else {
                wordLen += 1;
            }
        }

        var targetW = wordLen * this._widthRate;
        this.node_goldBG.width = Math.max(this.node_goldBG._firstW, targetW);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_roomListCell":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e303fXC4KlIfIXdry/z8Mz2', 'Obj_roomListCell');
// Script\Objects\Obj_roomListCell.js

//房间列表条目
cc.Class({
    'extends': require('BaseUI'),

    properties: {
        _enterType: null, //进入房间的类型
        _homeID: null, //房间ID

        node_backgroundBtn: {
            'default': null,
            type: cc.Node,
            displayName: '进入房间'
        },
        label_intoLimit: {
            'default': null,
            type: cc.Label,
            displayName: '进房限制'
        },
        label_onNumber: {
            'default': null,
            type: cc.Label,
            displayName: '在房人数'
        },
        node_roomBG: {
            'default': null,
            type: cc.Node,
            displayName: '房间背景'
        },
        node_roomName: {
            'default': null,
            type: cc.Node,
            displayName: '房间名称'
        },
        frame_roomTypeUI: {
            'default': null,
            type: cc.SpriteAtlas,
            displayName: '房间背景基础显示图片集'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this.registerButton(this.node, this.onClick_into, this);
        this._getShowSprite();
    },
    //获取房间背景房间名图片精灵
    _getShowSprite: function _getShowSprite() {
        this._sprite_roomBG = this.node_roomBG.getComponent(cc.Sprite);
        this._sprite_roomName = this.node_roomName.getComponent(cc.Sprite);
    },

    setData: function setData(data) {
        if (!data) return;
        this._enterType = data.enterType;
        this._homeID = data.id;
        this._setDefaultRoomImage(data.minLimitPlayerBlance);
        this.label_intoLimit.string = G_CHINESE.inToRoomLimit + G_TOOL.changeMoney(data.minLimitPlayerBlance);
        this.label_onNumber.string = data.currentPlayerCount;
    },
    //点击进入游戏
    onClick_into: function onClick_into(event) {
        if (G_Config_common.isLocal) {
            this._doEnter();
        } else {
            var netData = {
                roomId: this._homeID,
                gameModel: G_DATA.getEnterHomeModel(this._enterType)
            };
            GG.platformMgr.requestEnterRoom(netData, this._doEnter.bind(this));
        }
    },

    _doEnter: function _doEnter() {
        //记录hash信息
        G_DATA.setCurSceneHash(this._enterType, this._homeID);
        var isReload = GG.platformMgr.getIsReload();
        if (isReload) {
            //cc.director.loadScene(G_DATA.getSceneName(this._enterType));
            GG.changeScene(G_DATA.getSceneName(this._enterType));
        } else {
            GG.platformMgr.confirmSceneChange();
        }
    },
    //设置房间图片
    _setDefaultRoomImage: function _setDefaultRoomImage(minLimit) {
        this._sprite_roomBG.spriteFrame = this.frame_roomTypeUI.getSpriteFrame(G_RES_URL.roomType.roomBGImgName + minLimit);
        this._sprite_roomName.spriteFrame = this.frame_roomTypeUI.getSpriteFrame(G_RES_URL.roomType.roomNameImgName + minLimit);
    }
});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseUI":"BaseUI"}],"Obj_scrollValue":[function(require,module,exports){
"use strict";
cc._RFpush(module, '6f6ceHVjj1Dn7Voy9rX4nwo', 'Obj_scrollValue');
// Script\Objects\Obj_scrollValue.js

//滚动数字的通用脚本

var dataObj = {
    startNum: 0,
    targetNum: 1,
    label: null,
    formatStr: '',
    callFunc: null
};

cc.Class({
    'extends': cc.Component,

    properties: {
        _curLabel: null, //当前使用的滚动label
        _curNum: null, //当前的滚动值
        _maxNum: null, //最大的滚动值
        _formatStr: null, //需要组合显示的字符串
        _callFunc: null, //滚动结束后的回调

        _isStartScroll: null, //是否开始滚动
        _interval: null, //滚动间隔
        _maxScrollTimes: null },

    //最大的滚动次数
    // use this for initialization
    onLoad: function onLoad() {
        this._maxScrollTimes = 50;
        this._isStartScroll = false;
    },

    //开始滚动数值
    scrollLabel: function scrollLabel(dataObj) {
        if (!dataObj) return;
        this._isStartScroll = false;
        this._curNum = dataObj.startNum;
        this._maxNum = dataObj.targetNum;
        this._curLabel = dataObj.label;
        this._formatStr = dataObj.formatStr + '%s';
        this._callFunc = dataObj.callFunc;
        this._initScrollData();
    },

    _initScrollData: function _initScrollData() {
        this._interval = Math.max(Math.floor((this._maxNum - this._curNum) / this._maxScrollTimes), 0);
        this._isStartScroll = true;
    },

    _change: function _change() {
        this._curNum += this._interval;
        if (this._curNum >= this._maxNum) {
            //已经滚动到最大值
            this._curNum = this._maxNum;
            this._isStartScroll = false;
        }
        this._showValue();
        if (!this._isStartScroll) {
            this._clearData();
            if (this._callFunc) {
                this._callFunc();
                this._callFunc = null;
            }
        }
    },

    _showValue: function _showValue() {
        var str = G_TOOL.formatStr(this._formatStr, G_TOOL.changeMoney(this._curNum));
        this._curLabel.string = str;
    },

    _clearData: function _clearData() {
        this._curNum = 0;
        this._maxNum = 0;
        this._curLabel = null;
        this._formatStr = '';
        this._interval = 0;
    },

    //强制结束,显示最终结果
    forceEnd: function forceEnd() {
        this._callFunc = null;
        this._curNum = this._maxNum;
    },

    // called every frame, uncomment this function to activate update callback
    update: function update(dt) {
        if (!this._isStartScroll) return;
        this._change();
    }
});

cc._RFpop();
},{}],"Obj_seatsContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e779eMDuShEOqiJv281BvTL', 'Obj_seatsContainer');
// Script\Objects\Obj_seatsContainer.js

//座位容器管理

cc.Class({
    "extends": cc.Component,

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {},

    //获取一个位置的坐标
    addOnePlayer: function addOnePlayer(seatIndex) {
        var seat = this.node.children[seatIndex - 1];
        //强制适配
        if (!seat._isAdaptFirst) {
            seat._isAdaptFirst = true;
            var widget = seat.getComponent(cc.Widget);
            if (widget) {
                widget.updateAlignment();
            }
        }

        if (seat) {
            seat.active = false;
            //return cc.p((seat.x/this.node.width)*cc.visibleRect.width, (seat.y/this.node.height)*cc.visibleRect.height);
            return seat.position;
        }
        return null;
    },

    //有一个玩家离开
    onePlayerLeave: function onePlayerLeave(seatIndex) {
        var seat = this.node.children[seatIndex - 1];
        if (seat) {
            seat.active = true;
        }
    },

    //获取座位数量
    getSeatsNum: function getSeatsNum() {
        return this.node.children.length;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_setTouchEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, '2d913IYK5xMtoEMitw8o0jr', 'Obj_setTouchEffect');
// Script\Objects\Obj_setTouchEffect.js

/**
 * Created by lenovo on 2016/12/27.
 */

//最上层的点击
cc.Class({
    "extends": cc.Component,

    properties: {
        _touchEffect: null, //点击特效

        frame_touchEffect: cc.SpriteFrame
    },

    // use this for initialization
    onLoad: function onLoad() {
        this.addTouchListener();
    },

    //添加点击事件
    addTouchListener: function addTouchListener() {
        if (!this.frame_touchEffect) return;
        this.node.on(cc.Node.EventType.TOUCH_START, this.touchCall, this);
    },
    touchCall: function touchCall(event) {
        var pos = cc.p(event.getLocationX(), event.getLocationY());
        this._showTouchEff(pos);
    },

    //显示点击特效
    _showTouchEff: function _showTouchEff(pos) {
        if (!this._touchEffect) {
            this._touchEffect = new cc.Node();
            this._touchEffect.parent = this.node;

            var node1 = new cc.Node();
            this._touchEffect.addChild(node1, 0, 1);
            var sp1 = node1.addComponent(cc.Sprite).spriteFrame = this.frame_touchEffect;

            var node2 = new cc.Node();
            this._touchEffect.addChild(node2, 0, 2);
            var sp1 = node2.addComponent(cc.Sprite).spriteFrame = this.frame_touchEffect;
        }
        this._touchEffect.active = true;
        this._touchEffect.position = pos;
        var node1 = this._touchEffect.getChildByTag(1);
        var node2 = this._touchEffect.getChildByTag(2);
        node1.stopAllActions();
        node2.stopAllActions();

        node1.scale = 0.1;
        node1.opacity = 255;

        var time = 0.5;
        var act1 = cc.scaleTo(time, 1);
        var act2 = cc.fadeTo(time, 70);
        node1.runAction(cc.sequence(cc.spawn(act1, act2), cc.callFunc(this._effCall, this)));

        node2.scale = 0.1;
        node2.opacity = 255;
        act1 = cc.delayTime(time * 0.2);
        act2 = cc.scaleTo(time * 0.6, 0.7);
        var act3 = cc.fadeTo(0, 0);
        node2.runAction(cc.sequence(act1, act2, act3));
    },

    _effCall: function _effCall() {
        if (this._touchEffect) {
            this._touchEffect.active = false;
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_systemTip":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8fea3wUoYNOl5pJOGqAF8VX', 'Obj_systemTip');
// Script\Objects\Obj_systemTip.js

//系统提示显示
cc.Class({
    "extends": cc.Component,

    properties: {
        node_labelBG: cc.Node,
        label_center: cc.Label
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._firstPos = cc.p(this.node.x, this.node.y);
    },

    //显示系统提示内容
    showContent: function showContent(str, pos, time, callBack) {
        this.node.active = true;
        this.label_center.string = str;
        /*if(!pos) pos = this._firstPos;
        this.node.position = pos;*/

        if (!isNaN(time)) {
            if (this._countDownNum) this._forceEnd();
            //有倒计时
            this._curStr = str;
            this._callFunc = callBack;
            this._setCenterValue();
            this.node.stopAllActions();
            this.node.runAction(cc.sequence(cc.delayTime(time), cc.callFunc(this._hideTip, this)));
        } else this._hideTip();
        this._setBackgroundLen(str);
    },
    hideSystemTip: function hideSystemTip() {
        this.node.stopAllActions();
        this.node.active = false;
    },
    _forceEnd: function _forceEnd() {
        this._callFunc = null;
        this.node.stopAllActions();
        this._hideTip();
    },

    _hideTip: function _hideTip() {
        this.node.active = false;
        if (this._callFunc) {
            this._callFunc();
            this._callFunc = null;
        }
    },

    _setCenterValue: function _setCenterValue() {
        this.label_center.string = this._curStr;
    },
    //改变背景长度
    _setBackgroundLen: function _setBackgroundLen(str) {
        if (!this._widthRate) this._widthRate = this.node_labelBG.width / 14;
        if (!this.node_labelBG._firstW) this.node_labelBG._firstW = this.node_labelBG.width;

        var wordLen = 0,
            name = str,
            curStr,
            reg = /^[\u4E00-\u9FA5]+$/;;
        for (var i = 0; i < name.length; i++) {
            curStr = name[i];
            if (reg.test(curStr)) {
                wordLen += 2;
            } else {
                wordLen += 1;
            }
        }

        var targetW = wordLen * this._widthRate;
        this.node_labelBG.width = Math.max(this.node_labelBG._firstW, targetW);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_topOneLayer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '3827cXUXVxGSJC8QOIXG/UF', 'Obj_topOneLayer');
// Script\Objects\Obj_topOneLayer.js

//游戏场景中最上面的那个层

cc.Class({
    'extends': cc.Component,

    properties: {
        _isConfirmTipOpen: null, //提示窗口是否已经打开
        _isHasOpen: null, //提示窗口是否已经打开

        prefab_loadingAni: {
            'default': null,
            type: cc.Prefab,
            displayName: '网络请求动画'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        GG.topTouchLayer = this;

        this._isConfirmTipOpen = false;
        this._touchLimitLayer = this.getComponent('Obj_touchLimit');
    },

    start: function start() {
        this._registerFunc(true);
    },

    //显示请求网络的动画
    showNetRequest: function showNetRequest(isShowTip) {
        if (!this.prefab_loadingAni) return;
        if (!this._netAni) {
            this._netAni = cc.instantiate(this.prefab_loadingAni);
            this._netAni.parent = this.node;
            var center = cc.visibleRect.center;
            this._netAni.position = cc.p(center.x, center.y * 1.35);
            //this._netAni.active = false;
        }
        if (this._netAni.active) return;
        if (this._isConfirmTipOpen) {
            //弹窗已经存在
            this._isHasOpen = true;
            return;
        }
        this._netAni.active = true;
        var center = cc.visibleRect.center;
        this._netAni.position = cc.p(center.x, center.y * 1.35);
        this._showReconnectTip(isShowTip);
        this._netAni.getComponent(dragonBones.ArmatureDisplay).playAnimation('');
        if (this._touchLimitLayer) this._touchLimitLayer.addTouchLimit();
    },

    closeNetRequest: function closeNetRequest() {
        if (this._netAni) {
            this._netAni.active = false;
            if (this._touchLimitLayer) this._touchLimitLayer.cancelTouchLimit();
        }
    },

    //显示重连提示
    _showReconnectTip: function _showReconnectTip(isShow) {
        var tipNode = this._netAni.getChildByName('tip');
        if (tipNode) {
            var content;
            if (isShow) {
                var tableData = G_DATA.getChinese(61);
                content = tableData.content;
            } else content = '';
            tipNode.getComponent(cc.Label).string = content;
        }
    },

    //注册确认窗口的监听
    _registerFunc: function _registerFunc(isRegister) {
        if (GG.tipsMgr) {
            GG.tipsMgr.registerConfirm(this._whenConfirmTipChange.bind(this), isRegister);
        }
    },

    //当窗口打开或者关闭
    _whenConfirmTipChange: function _whenConfirmTipChange(isOpen) {
        this._isConfirmTipOpen = isOpen;
        if (this._netAni.active) {
            //当显示连接的时候
            if (isOpen) {
                this._isHasOpen = true;
                this.closeNetRequest();
            }
        } else {
            if (!isOpen) {
                if (this._isHasOpen) {
                    var tipNode = this._netAni.getChildByName('tip');
                    var isShow = false;
                    if (tipNode) {
                        isShow = Boolean(tipNode.getComponent(cc.Label).string);
                    }
                    this.showNetRequest(isShow);
                }
            }
        }
    },

    onDestroy: function onDestroy() {
        this._registerFunc(false);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_touchLimit":[function(require,module,exports){
"use strict";
cc._RFpush(module, '28cc9CnmIZBXLGDeIMG+yuu', 'Obj_touchLimit');
// Script\Objects\Obj_touchLimit.js

//最上面的触摸禁用层

cc.Class({
    "extends": cc.Component,

    properties: {
        _netAni: null },

    //预存的网络请求动画
    // use this for initialization
    onLoad: function onLoad() {},

    addTouchLimit: function addTouchLimit(isGray) {
        this.node.on(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },
    _touchThisLayer: function _touchThisLayer() {},
    cancelTouchLimit: function cancelTouchLimit() {
        this.node.off(cc.Node.EventType.TOUCH_START, this._touchThisLayer, this);
    },

    //设置是否有置灰层
    setIsShowGray: function setIsShowGray(isGray) {
        if (!this._grayLayer) {}
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_txtTip":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0779d1B55VI+5GfT/GEwecN', 'Obj_txtTip');
// Script\Objects\Obj_txtTip.js

//显示在屏幕中央的文字提示

//var dataObj = {
//    tipIndex : 1,
//    showStr : '',
//    formatStr : '',
//    showPos : cc.p(0,0),
//    retainTime : 5,
//    isCountDown : false,
//    isRepeat : false,
//    callBack : 'target'
//}

cc.Class({
    'extends': cc.Component,
    properties: {
        _maxCountDown: null, //最大倒计时
        _curCountDown: null, //剩余时间
        _callBack: null, //倒计时结束后的回调
        _curBaseStr: null, //显示的文字基础
        _tipIndex: null, //tip索引
        _tipMgr: null, //管理器
        _widthRate: null,
        _hideFunc: null,

        node_centerLabel: cc.Node },

    //
    // use this for initialization
    onLoad: function onLoad() {
        this._init();
    },

    _init: function _init() {
        this._mainLabel = this.node_centerLabel.getComponent(cc.Label);
        this._defaultTime = G_Config_common.time_tipDefault;
    },

    bindMgr: function bindMgr(mgr) {
        this._tipMgr = mgr;
    },

    //==========================================

    showTip: function showTip(dataObj) {
        //this.forceEnd();
        var retainTime;
        if (dataObj.tipIndex) {
            var table = G_DATA.getChinese(dataObj.tipIndex);
            if (table) {
                this._curBaseStr = table.content;
                retainTime = table.retainTime;
            }
        }
        this.node.active = true;
        var pos = dataObj.showPos;
        if (!pos) pos = G_DATA.getCenterTipPos();
        this.node.position = pos;
        this.showByOpacity();

        if (dataObj.showStr) this._curBaseStr = dataObj.showStr;
        if (!isNaN(dataObj.retainTime)) {
            retainTime = Math.max(dataObj.retainTime, 0);
        }
        this._callBack = dataObj.callBack;
        var formatStr = dataObj.formatStr;
        this._tipIndex = dataObj.tipIndex;

        if (dataObj.isCountDown) {
            if (!retainTime) {
                this._hideTip();
                if (this._callBack) {
                    this._callBack();
                    this._callBack = null;
                }
                return;
            }
            this._addCountDown(retainTime);
            this._resetBackground(G_TOOL.formatStr(this._curBaseStr, 123));
        } else {
            if (!retainTime) retainTime = this._defaultTime;
            this._showTxt();
            this._resetBackground(G_TOOL.formatStr(this._curBaseStr, formatStr));
            var act1 = cc.fadeOut(retainTime);
            this.node.runAction(cc.sequence(act1, cc.callFunc(this._clearTxtTip, this)));
        }
    },
    //中途修改显示文字
    forceChangeStr: function forceChangeStr(changeStr) {
        this._curBaseStr = changeStr;
        this._resetBackground(G_TOOL.formatStr(this._curBaseStr, ''));
    },
    //中途修改显示倒计时的数值
    forceChangeCountDown: function forceChangeCountDown(num) {
        this._maxCountDown = num;
        this._curCountDown = this._maxCountDown;
        this._showTxt(this._curCountDown);
    },
    //强制结束倒计时
    forceEnd: function forceEnd() {
        if (this._curCountDown) this.unschedule(this.let_update);
        this._curCountDown = 0;
        this._callBack = null;
        this._hideTip();
    },
    //重置显示内容和时间
    resetTxtAndTime: function resetTxtAndTime(content, time) {
        if (!time) time = this._defaultTime;
        this.node.stopAllActions();

        this._curBaseStr = content;
        this._showTxt();
        this._resetBackground(G_TOOL.formatStr(this._curBaseStr, ''));
        var act1 = cc.fadeOut(time);
        this.node.runAction(cc.sequence(act1, cc.callFunc(this._clearTxtTip, this)));
    },

    showByOpacity: function showByOpacity() {
        this.node.opacity = 255;
    },
    hideByOpacity: function hideByOpacity() {
        this.node.opacity = 0;
    },

    _showTxt: function _showTxt(formatStr) {
        if (!formatStr) this._mainLabel.string = this._curBaseStr;else this._mainLabel.string = G_TOOL.formatStr(this._curBaseStr, formatStr);
    },

    _resetBackground: function _resetBackground(str) {
        if (!this._widthRate) this._widthRate = this.node.width / 13;
        this.node.width = str.length * this._widthRate;
    },

    _addCountDown: function _addCountDown(maxTime) {
        if (this._curCountDown) this._countDownEnd();
        this._maxCountDown = maxTime;
        this._curCountDown = this._maxCountDown;
        this._interval = 1.0;
        this.schedule(this.let_update, this._interval);
        this._showTxt(this._curCountDown);
    },

    _countDownEnd: function _countDownEnd() {
        this.unschedule(this.let_update);
        this._clearTxtTip();
    },
    //frame
    let_update: function let_update(dt) {
        if (this._curCountDown > 0) {
            this._curCountDown -= 1;
            this._showTxt(this._curCountDown);
            if (this._curCountDown <= 0) {
                //倒计时结束
                this._countDownEnd();
            }
        }
    },

    _clearTxtTip: function _clearTxtTip() {
        if (this._callBack) {
            this._callBack();
            this._callBack = null;
        }
        this._hideTip();
    },

    //==========================================

    //绑定消失函数
    bindHideFunc: function bindHideFunc(func) {
        this._hideFunc = func;
    },

    _hideTip: function _hideTip() {
        this._tipMgr.oneTipHide(this.node);
        this.node.active = false;
        if (this._hideFunc) {
            this._hideFunc();
            this._hideFunc = null;
        }
    },

    //是否已经在显示了
    getIsShow: function getIsShow() {
        return this.node.active;
    },
    setIsShow: function setIsShow(isShow) {
        this.node.active = isShow;
    },

    //获取该tip的表格索引
    getTipIndex: function getTipIndex() {
        return this._tipIndex;
    },

    onDestroy: function onDestroy() {
        if (this._curCountDown) this.unschedule(this.let_update);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {
//
// },

cc._RFpop();
},{}],"Obj_uiContainer":[function(require,module,exports){
"use strict";
cc._RFpush(module, '82b09GVpxBPap6yQdYUAPAT', 'Obj_uiContainer');
// Script\Objects\Obj_uiContainer.js

//显示UI界面的容器

cc.Class({
    'extends': cc.Component,
    properties: {
        _dict_ui: null, //ui整理的字典
        _ui_baseUrl: null, //ui整理的字典
        _isOnLoad: null, //是否正在加载中

        node_grayLayer: {
            'default': null,
            type: cc.Node,
            displayName: '灰色层'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._dict_ui = {};
        this._ui_baseUrl = 'UI_prefabs/';
        this._isOnLoad = false;
    },

    //获取一个可以显示的ui
    addUI: function addUI(uiName, callBakc) {
        this.showUI(uiName, callBakc);
        return;
        //var uiComp = this._dict_ui[uiName];
        //if(!uiComp){
        //    //var prefabName = 'prefab_'+uiName.split('_')[1];
        //    var prefabName = uiName;
        //    if(!this[prefabName]) return null;
        //    var uiNode = cc.instantiate(this[prefabName]);
        //    uiNode.parent = this.node;
        //    var pos = this._getDialogPos();
        //    uiNode.position = this._getDialogPos();
        //    uiComp = uiNode.getComponent(uiName);
        //    uiComp.setUIName(uiName);
        //    uiComp.bindContainer(this);
        //    this._dict_ui[uiName] = uiComp;
        //}
        //uiComp.hideLayer();
        //return uiComp
    },

    showUI: function showUI(uiName, callBack) {
        if (this._isOnLoad) return;
        if (!uiName) {
            //错误的ui名称
            if (callBack) {
                callBack(null);
                callBack = null;
            }
            return;
        }
        var uiComp = this._dict_ui[uiName];
        if (!uiComp) {
            //创建窗口
            var prefabName = uiName;
            var self = this;
            this._isOnLoad = true;
            cc.loader.loadRes(this._ui_baseUrl + prefabName, function (err, prefab) {
                self._isOnLoad = false;
                var uiNode = cc.instantiate(prefab);
                uiNode.parent = self.node;
                uiNode.position = self._getDialogPos();
                uiComp = uiNode.getComponent(uiName);
                uiComp.setUIName(uiName);
                uiComp.bindContainer(this);
                self._dict_ui[uiName] = uiComp;
                uiComp.hideLayer();
                if (callBack) {
                    callBack(uiComp);
                    callBack = null;
                }
            });
        } else {
            //已经创建过该ui
            uiComp.hideLayer();
            if (callBack) {
                callBack(uiComp);
                callBack = null;
            }
        }
    },

    //增加一个额外的ui到ui管理字典
    addUIEx: function addUIEx(uiName, uiComp) {
        if (!uiComp) return;
        uiComp.setUIName(uiName);
        this._dict_ui[uiName] = uiComp;
    },

    //关闭一个ui
    closeUI: function closeUI(uiName) {
        if (!this._dict_ui[uiName]) return;
        this._dict_ui[uiName].hideLayer();
    },

    //获取一个UI的操作脚本
    getUIComp: function getUIComp(uiName) {
        var uiComp = this._dict_ui[uiName];
        if (!uiComp) uiComp = this.addUI(uiName);
        return uiComp;
    },

    //关闭所有的窗口显示
    closeAllUI: function closeAllUI() {
        for (var uiName in this._dict_ui) {
            this.closeUI(uiName);
        }
    },

    _getDialogPos: function _getDialogPos() {
        return cc.p(cc.visibleRect.width * 0.5, cc.visibleRect.height * 0.5);
    },

    //设置置灰层的显隐
    setGrayLayerIsShow: function setGrayLayerIsShow(isShow) {
        if (this.node_grayLayer) this.node_grayLayer.active = isShow;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Obj_winRankHead":[function(require,module,exports){
"use strict";
cc._RFpush(module, '21a84l8GWhGaJ4cPQfmakvy', 'Obj_winRankHead');
// Script\Objects\Obj_winRankHead.js

//结算界面中胜利排行的头像

cc.Class({
    'extends': cc.Component,

    properties: {
        _defaultHeadFrame: null, //显示默认的头像

        sprite_headImg: {
            'default': null,
            type: cc.Sprite,
            displayName: '头像精灵'
        },
        label_playerName: {
            'default': null,
            type: cc.Label,
            displayName: '玩家名字'
        },
        label_gold: {
            'default': null,
            type: cc.Label,
            displayName: '金币数值'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {},

    setData: function setData(data) {
        this._setPlayerName(data.name);
        this._setGoldValue(data.addGold);
        this._setHeadImg(data.icon);
    },

    //设置玩家名字
    _setPlayerName: function _setPlayerName(name) {
        if (!name) name = '';
        name = G_TOOL.getNameLimit(name, 8, true);
        this.label_playerName.string = name;
    },

    _setGoldValue: function _setGoldValue(newValue) {
        this.label_gold.string = G_TOOL.changeMoney(newValue);
    },

    //设置头像的图标
    _setHeadImg: function _setHeadImg(imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = this.sprite_headImg.spriteFrame;
        if (!imgName) this.sprite_headImg.spriteFrame = this._defaultHeadFrame;else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'head01';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    this.sprite_headImg.spriteFrame = spriteFrame;
                }).bind(this));
            } else {
                this._headUrl = G_DATA.getResNetRoot(imgName);
                GG.resMgr.getHeadImg(this._headUrl, (function (frame) {
                    this.sprite_headImg.spriteFrame = frame;
                }).bind(this));
            }
        }
    },

    setIsShow: function setIsShow(isShow) {
        this.node.active = isShow;
    },
    getIsShow: function getIsShow() {
        return this.node.active;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Object_config":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'b6eb4DffzpAE7fb1TCIU67g', 'Object_config');
// Script\Common\Configs\Object_config.js

//对象内容枚举

window.G_OBJ = window.G_OBJ || {};

//百人投注信息
G_OBJ.data_bullBetting = function () {
    return {
        //来源于桌子的信息
        tableIndex: null, //桌子索引
        posList: null, //金币的位置列表
        //来源与玩家的信息
        startPos: null, //金币开始移动的位置
        goldValue: null, //投注的金币实际数值
        goldNum: null, //飞行在场景中的金币数量
        //是否是操作位投注
        isMine: null
    };
};
//押宝投注信息
G_OBJ.data_grabBetting = function () {
    return {
        //来源于桌子的信息
        tableIndex: null, //桌子索引
        targetPos: null, //投注区域的位置
        callFunc: null, //确定移动金币到投注区域后的回调
        //来源与玩家的信息
        startPos: null, //金币开始移动的位置
        goldValue: null, //投注的金币实际数值
        goldNum: null, //飞行在场景中的金币数量
        //是否是操作位投注
        isMine: null
    };
};
//押宝显示卡牌信息
G_OBJ.data_grabPokerInfo = function () {
    return {
        pokerInfoList: null, //卡牌信息列表
        pokerResult: null, //这副牌的结果值
        isFlyEffect: null, //是否播放飞行效果
        startPokerIndex: null, //显示的卡牌索引，从第几个开始显示
        callFunc: null };
};
//从大厅进房需要传输的信息
//发牌结束的回调
G_OBJ.data_inHomeData = function () {
    return {
        net_inHomeData: null, //进房请求信息
        net_resetHomeData: null };
};
//飞行金币需要的数据
//进房后的开局信息
G_OBJ.data_flyGold_playerToTable = function () {
    return {
        tableIndex: null, //投注区域索引
        goldNum: null, //投注飞行的金币数量
        startPos: null, //金币飞行的启示位置
        callFunc: null };
};
//飞行金币回收到玩家需要的数据
//金币飞行后的回调
G_OBJ.data_flyGold_tableToPlayer = function () {
    return {
        tableIndex: null, //投注区域索引
        goldNum: null, //投注飞行的金币数量
        targetPos: null };
};
//金币飞行的启示位置

cc._RFpop();
},{}],"Platform_Manager":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'c9e2amfcZJE94McrGnyuBWh', 'Platform_Manager');
// Script\Views\Scene_Platform\Platform_Manager.js

//大厅场景管理

cc.Class({
    'extends': require('BaseManager'),
    properties: {
        _dict_ui: null, //UI字典
        _richRankData: null, //财富排行榜的信息
        _ui_baseUrl: null, //ui整理的字典
        _roomComp: null, //房间脚本
        _enterRoomCallFunc: null, //进入游戏房间后的回调
        _isFirstEnter: null, //是否第一次进入游戏

        comp_uiContainer: {
            'default': null,
            type: require('Obj_uiContainer'),
            displayName: 'UI容器'
        },
        comp_enterBtn: {
            'default': null,
            type: require('Platform_ui_enter'),
            displayName: '房间入口'
        },
        comp_leftRank: {
            'default': null,
            type: require('Platform_leftRank'),
            displayName: '左边列表容器'
        },
        comp_bottomBtns: {
            'default': null,
            type: require('Platform_bottomBtns'),
            displayName: '底部按钮容器'
        },
        comp_topInfo: {
            'default': null,
            type: require('Platform_topInfo'),
            displayName: '大厅顶部信息'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();
        this._ui_baseUrl = 'UI_prefabs/';
        GG.platformMgr = this;
        this._roomNode = false;
        this._isFirstEnter = false;
    },

    start: function start() {
        if (G_Config_common.isLocal) {
            this._richRankData = {};
            this._richRankData.code = 1;
            this._richRankData.data = [];
            for (var i = 0; i < 20; i++) {
                this._richRankData.data.push({
                    nickname: '997',
                    walletbalance: 667,
                    avatarurl: ''
                });
            }
            this.comp_leftRank.setRichData(this._richRankData);
            GG.topTouchLayer.showNetRequest();
            var self = this;
            var isOk = GG.whenGameStart(function () {
                self._addConfigCallFunc();
                self.node.runAction(cc.sequence(cc.delayTime(1), cc.callFunc(function () {
                    GG.topTouchLayer.closeNetRequest();
                    self.showHomeList(false, true);
                    GG.audioMgr.playMusic();
                }, self)));
            });
            if (!isOk) {
                self.node.runAction(cc.sequence(cc.delayTime(1), cc.callFunc(function () {
                    GG.topTouchLayer.closeNetRequest();
                    self.showHomeList(false, true);
                    GG.audioMgr.playMusic();
                }, self)));
            }
        } else {
            var self = this;
            GG.topTouchLayer.showNetRequest();
            var isOk = GG.whenGameStart(function () {
                self._addConfigCallFunc();
                self._sendLogin();
            });
            if (!isOk) {
                //已经加载过配置了
                this._sendLogin();
            } else {
                //首次进入游戏或者已经刷新了页面
                this._isFirstEnter = true;
            }
        }
    },

    //发送登陆请求
    _sendLogin: function _sendLogin() {
        var sendData = null;
        if (!GG.getPlayer().getPlayerToken()) {
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.loginTokenUrl, sendData, (function (data) {
                GG.getPlayer().setPlayerToken(data);
                GG.socketMgr.connectSocket();

                this._initData();
            }).bind(this));
        } else this._initData();
    },

    //获取初始需要现实的 信息
    _initData: function _initData() {
        this._requestNum = 0;
        //获取玩家Token信息
        var sendData = null;
        //富豪榜
        if (!this._richRankData) {
            this._requestNum += 1;
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.playerTreasuresUrl, sendData, (function (data) {
                this._richRankData = data;
                this._oneRequestEnd();
            }).bind(this));
        }
        //获取玩家信息并放置M_Player脚本中
        if (!GG.getPlayer().getBaseInfo()) {
            this._requestNum += 1;
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.personalInfoUrl, sendData, (function (data) {
                GG.getPlayer().setBaseInfo(data);
                this._oneRequestEnd();
            }).bind(this));
        }
        //一开始请求声音的设置
        if (!GG.audioMgr.getAudioConfig()) {
            this._requestNum += 1;
            //GG.httpMgr.sendHttpLogin(G_DIALOG_URL.loginToSetUrl, sendData, function (data) {
            //    var config = data[0];
            //    if(!data || data.length < 1){
            //        config = {'music':true,'sound':true};
            //    }
            //    GG.audioMgr.setAudioConfig(config);
            //    this._oneRequestEnd();
            //}.bind(this));
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.getSystemSetUrl, 'gameType=DOU_NIU', (function (data) {
                var config;
                if (data.code == 0) {
                    config = { 'music': true, 'sound': true };
                } else {
                    config = data.data.result[0];
                }
                //if(config.music && GG.audioMgr.getIsPlayMusic()) GG.audioMgr.saveSoundConfig(config.sound);
                GG.audioMgr.setAudioConfig(config);
                this._oneRequestEnd();
            }).bind(this));
        }
        if (this._requestNum < 1) {
            this._oneRequestEnd();
        }
    },
    _oneRequestEnd: function _oneRequestEnd() {
        this._requestNum -= 1;
        if (this._requestNum < 1) {
            //加载数据结束

            //判定是否刷新后直接进入房间
            if (this._isFirstEnter) {
                //刷新过游戏，或者第一次进入房间
                var hashData = G_DATA.getLastSceneHash();
                if (hashData) {
                    var netData = {
                        roomId: hashData.roomId,
                        gameModel: G_DATA.getEnterHomeModel(hashData.enterType)
                    };
                    this.requestEnterRoom(netData, function () {
                        GG.changeScene(G_DATA.getSceneName(hashData.enterType));
                        //记录hash信息
                        G_DATA.setCurSceneHash(hashData.enterType, hashData.roomId);
                    });
                    return;
                }
            }

            //设置玩家信息
            this.comp_topInfo.initShow();
            //设置排行榜信息
            this.comp_leftRank.setRichData(this._richRankData);
            //是否直接跳转到房间列表
            var dataObj = GG.getPlayer().getOutHomeData();
            if (dataObj && dataObj.gameType != G_TYPE.gameModule.platform) this.comp_enterBtn.showHomeListFirst(dataObj.gameType, true);else this.showHomeList(false, true);

            //清理hash信息
            G_DATA.setCurSceneHash('', 0);
        }
    },
    _loginSocket: function _loginSocket() {},
    //显示某个UI，传入G_RES_URL里面的UI名字
    openUI: function openUI(uiName, callBack) {
        this.comp_uiContainer.addUI(uiName, callBack);
    },

    //控制房间列表的显隐
    showHomeList: function showHomeList(isOut, isNoEffect) {
        this.comp_enterBtn.moveOutScene(isOut, isNoEffect);
        this.comp_leftRank.moveOutScene(isOut, isNoEffect);
        this.comp_bottomBtns.moveOutScene(isOut, isNoEffect);
        this.comp_topInfo.setHomeListShow(isOut, isNoEffect);
        if (!isOut && this.comp_enterBtn.getRoomComp()) this.comp_enterBtn.getRoomComp().hideLayer();
        //else this._comp_homeList.hideLayer();
    },
    //显示房间列表信息
    showHomeListInfo: function showHomeListInfo(data) {
        this.comp_enterBtn.getRoomComp().setDataList(data);
    },

    //确认场景是否已经预加载完成
    getIsReload: function getIsReload() {
        return this.comp_enterBtn.getRoomComp().getIsReload();
    },
    //确认可以立即跳转场景
    confirmSceneChange: function confirmSceneChange() {
        this.comp_enterBtn.getRoomComp().setIsChangeNow(true);
    },

    //加载配置文件结束
    _addConfigCallFunc: function _addConfigCallFunc() {
        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.grabConfig);
        if (table) {
            var dataObj = table.getFirstData();
            this._addConfig(G_Config_grab, dataObj);
        }

        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonConfig);
        if (table) {
            var dataObj = table.getFirstData();
            this._addConfig(G_Config_common, dataObj);
        }
    },
    //增加配置信息
    _addConfig: function _addConfig(targetObject, addObject) {
        if (addObject) {
            for (var attrName in addObject) {
                targetObject[attrName] = addObject[attrName];
            }
        }
    },

    showRoomUI: function showRoomUI(prefabName, callBack) {
        if (!this._roomComp) {
            var self = this,
                uiComp;
            cc.loader.loadRes(this._ui_baseUrl + prefabName, function (err, prefab) {
                var uiNode = cc.instantiate(prefab);
                uiNode.parent = self.comp_bottomBtns.node;
                uiComp = uiNode.getComponent(prefabName);
                self._roomComp = uiComp;
                if (callBack) {
                    callBack(uiComp);
                    callBack = null;
                }
            });
        } else {
            if (callBack) {
                callBack(this._roomComp);
                callBack = null;
            }
        }
    },

    //进入游戏房间的请求
    requestEnterRoom: function requestEnterRoom(netData, callFunc) {
        this._enterRoomCallFunc = callFunc;
        GG.socketMgr.SendMsg(NetType.s_enterHouse, netData);
        GG.socketMgr.registerLong(NetType.r_enterHouseReturn, this.net_setHomeData.bind(this));
    },

    //接收到进房信息
    net_setHomeData: function net_setHomeData(recvData) {
        var tip = recvData.tip;
        if (tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            GG.tipsMgr.showConfirmTip_ONE(tip.tip, function () {
                //进入房间失败
                if (tip.code == G_TYPE.serverCodeType.matchNoEnd) {
                    //已经开始的比赛未结束

                    //清理hash信息
                    G_DATA.setCurSceneHash('', 0);
                    GG.exitHome();
                }
            });
            return;
        }
        var inHomeData = G_OBJ.data_inHomeData();
        inHomeData.net_inHomeData = recvData;
        GG.getPlayer().setInHomeData(inHomeData);
        //this._doEnter();
        if (this._enterRoomCallFunc) {
            this._enterRoomCallFunc();
            this._enterRoomCallFunc = null;
        }
    },

    _doEnter: function _doEnter() {
        var isReload = GG.platformMgr.getIsReload();
        if (isReload) {
            //cc.director.loadScene(G_DATA.getSceneName(this._enterType));
            GG.changeScene(G_DATA.getSceneName(this._enterType));
        } else {
            GG.platformMgr.confirmSceneChange();
        }
    },

    onDestroy: function onDestroy() {
        this._super();
        GG.platformMgr = null;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseManager":"BaseManager","Obj_uiContainer":"Obj_uiContainer","Platform_bottomBtns":"Platform_bottomBtns","Platform_leftRank":"Platform_leftRank","Platform_topInfo":"Platform_topInfo","Platform_ui_enter":"Platform_ui_enter"}],"Platform_bottomBtns":[function(require,module,exports){
"use strict";
cc._RFpush(module, '25e9d3pzRtN9IL5uJp1EWk8', 'Platform_bottomBtns');
// Script\Views\Scene_Platform\UI\Platform_bottomBtns.js

//底部按钮

cc.Class({
    'extends': require('BaseUI'),

    properties: {
        node_buttonContainer: {
            'default': null,
            type: cc.Node,
            displayName: '按钮容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerEvent();
    },

    _registerEvent: function _registerEvent() {
        var btns = this.node_buttonContainer.children;
        for (var i = 0; i < btns.length; i++) {
            btns[i].tag = i;
            this.registerButton(btns[i], this._onClick_touchBtn, this);
        }
    },

    _onClick_touchBtn: function _onClick_touchBtn(event) {
        switch (event.currentTarget.tag) {
            case 0:
                //点击打开公告弹窗
                //GG.platformMgr.showUI('UI_announcement', false);
                var uiName = G_RES_URL.uiName.announcement;
                var layer = GG.platformMgr.openUI(uiName, function (layer) {
                    layer.showLayer();
                });
                break;
            case 1:
                //点击打开规则弹窗
                //GG.platformMgr.showUI('UI_rules', false);
                var uiName = G_RES_URL.uiName.rules;
                GG.platformMgr.openUI(uiName, function (layer) {
                    layer.showLayer();
                });
                break;
            case 2:
                //点击打开记录弹窗
                //GG.platformMgr.showUI('UI_record', false);
                //GG.platformMgr.getRecordData();

                var uiName = G_RES_URL.uiName.record;
                var layer = GG.platformMgr.openUI(uiName, function (layer) {
                    layer.setData();
                });
                break;
            case 3:
                //点击打开规则弹窗
                var uiName = G_RES_URL.uiName.account;
                var layer = GG.platformMgr.openUI(uiName, function (layer) {
                    layer.setData();
                });
                break;
            default:
                break;
        }
    },

    //移出屏幕外
    moveOutScene: function moveOutScene(isOut, isNoEffect) {
        if (!this.node_buttonContainer._firstBtnPosY) {
            this.node_buttonContainer._firstBtnPosY = -this.node_buttonContainer.y;
        }
        if (isNoEffect) {
            this.node_buttonContainer.y = this.node_buttonContainer._firstBtnPosY - (isOut ? this.node_buttonContainer.height * this.node_buttonContainer.scale : 0);
        } else {
            var time = 0.5;
            this.node_buttonContainer.stopAllActions();
            var targetPos = cc.p(this.node_buttonContainer.x, this.node_buttonContainer._firstBtnPosY);
            if (isOut) targetPos.y = -(this.node_buttonContainer.height * this.node_buttonContainer.scale / 2);
            this.node_buttonContainer.runAction(cc.moveTo(time, targetPos));
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseUI":"BaseUI"}],"Platform_leftRank":[function(require,module,exports){
"use strict";
cc._RFpush(module, '834471qZIhPe7wXg+6nY9a4', 'Platform_leftRank');
// Script\Views\Scene_Platform\UI\Platform_leftRank.js

//游戏大厅玩家排行榜
var rankType = {
    type1: 1,
    type2: 2
};
cc.Class({
    'extends': require('BaseUI'),

    properties: {
        _list_idleFriends: null, //没有显示出来的好友
        _curRankType: null, //当前选中的类型
        _gainsRankData: null, //盈利排行榜的信息
        _firstShowNum: null, //一开始显示的条目数量
        _list_richData: null, //富豪榜信息
        _list_gainsData: null, //盈利榜信息

        sprite_listGainOn: {
            'default': null,
            type: cc.Sprite,
            displayName: '盈利榜图标'
        },
        sprite_listRichOn: {
            'default': null,
            type: cc.Sprite,
            displayName: '富豪榜图标'
        },
        sprite_listRankingBG: {
            'default': null,
            type: cc.Sprite,
            displayName: '高亮显示条'
        },
        node_listGainBtn: {
            'default': null,
            type: cc.Node,
            displayName: '盈利榜按钮'
        },
        node_listRichBtn: {
            'default': null,
            type: cc.Node,
            displayName: '富豪榜按钮'
        },
        comp_itemContainer: {
            'default': null,
            type: require('Obj_dealScroll'),
            displayName: '好友排行榜容器'
        },
        prefab_friendItem: {
            'default': null,
            type: cc.Prefab,
            displayName: '好友条目'
        },
        label_null: {
            'default': null,
            type: cc.Label,
            displayName: '无数据时显示'
        },
        comp_scroll: {
            'default': null,
            type: cc.ScrollView,
            displayName: '滚动的容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._itemCompName = 'Obj_rankItem_friend';
        this._firstShowNum = 6;
        this.registerButton(this.node_listGainBtn, this.onClick_tabBtn, this, rankType.type1, true);
        this.registerButton(this.node_listRichBtn, this.onClick_tabBtn, this, rankType.type2, true);
        this.comp_scroll.node.on('scroll-to-bottom', this._scrollToBottom, this);
        //初始化滚动层1
        var dataObj = {
            itemPrefab: this.prefab_friendItem
        };
        this.comp_itemContainer.setData(dataObj);
    },

    start: function start() {
        //默认显示
        this._curRankType = rankType.type2;
        this._setRankTitle(this._curRankType);
    },

    //点击切换富豪榜
    onClick_tabBtn: function onClick_tabBtn(event, type) {
        if (this._curRankType != type) {
            this._curRankType = type;
            this._setRankTitle(type);
        }
    },
    //设置按钮显示
    _setRankTitle: function _setRankTitle(type) {
        var isLeft = false,
            posX;
        switch (type) {
            case rankType.type1:
                //盈利榜
                //盈利榜
                if (!this._gainsRankData) {
                    if (!G_Config_common.isLocal) {
                        var sendData = 'gameId=' + GG.getPlayer().getGameID();
                        GG.httpMgr.sendHttpLogin(G_DIALOG_URL.playerProfitUrl, sendData, (function (data) {
                            this._gainsRankData = data;
                            isLeft = true;
                            posX = this.node_listGainBtn.x;
                            this.setGainData(this._gainsRankData);

                            this.sprite_listRankingBG.node.x = posX;
                            this.sprite_listGainOn.node.active = isLeft;
                            this.sprite_listRichOn.node.active = !isLeft;
                        }).bind(this));
                        return;
                    } else {
                        isLeft = true;
                        posX = this.node_listGainBtn.x;
                        this._gainsRankData = {};
                        this._gainsRankData.code = 1;
                        this._gainsRankData.data = [];
                        for (var i = 0; i < 20; i++) {
                            this._gainsRankData.data.push({
                                nickname: '1111',
                                walletbalance: 3333,
                                avatarurl: ''
                            });
                        }
                        this.setGainData(this._gainsRankData);
                    }
                } else {
                    isLeft = true;
                    posX = this.node_listGainBtn.x;
                    this.setGainData(this._gainsRankData);
                }
                break;
            case rankType.type2:
                //富豪榜
                this.setRichData();
                posX = this.node_listRichBtn.x;
                break;
        };
        this.sprite_listRankingBG.node.x = posX;
        this.sprite_listGainOn.node.active = isLeft;
        this.sprite_listRichOn.node.active = !isLeft;
    },

    //设置盈利榜数据
    setGainData: function setGainData(dataObj) {
        this._scrollToUp();
        if (!dataObj || !dataObj.data) {
            this.comp_itemContainer.clearItems(-1);
            return false;
        }
        if (dataObj.code == '0') {
            this._setNull(dataObj.msg);
        } else {
            this._list_gainsData = dataObj.data.concat([]);
            var item, comp;
            for (var i = 0; i < this._firstShowNum; i++) {
                var playerInfo = this._list_gainsData.splice(0, 1)[0];
                if (playerInfo) {
                    item = this.comp_itemContainer.getItemByIndex(i);
                    if (item) {
                        comp = item.getComponent(this._itemCompName);
                        comp.init(true, i, playerInfo);
                    }
                } else break;
            }
            this.comp_itemContainer.clearItems(i - 1);
        }
    },

    //设置富豪榜数据
    setRichData: function setRichData(dataObj) {
        this._scrollToUp();
        if (!this._richData) {
            if (!dataObj || !dataObj.data) {
                this.comp_itemContainer.clearItems(-1);
                return false;
            }
            this._richData = dataObj;
        } else dataObj = this._richData;
        if (dataObj.code == '0') {
            this._setNull(dataObj.msg);
        } else {
            var item, comp;
            this._list_richData = dataObj.data.concat([]);
            for (var i = 0; i < this._firstShowNum; i++) {
                var playerInfo = this._list_richData.splice(0, 1)[0];
                if (playerInfo) {
                    item = this.comp_itemContainer.getItemByIndex(i);
                    if (item) {
                        comp = item.getComponent(this._itemCompName);
                        comp.init(false, i, playerInfo);
                    }
                } else break;
            }
            this.comp_itemContainer.clearItems(i - 1);
        }
        return true;
    },

    //当scroll滚动到底边
    _updateBottom: function _updateBottom() {
        switch (this._curRankType) {
            case rankType.type1:
                //盈利榜
                var item,
                    comp,
                    itemNum = this.comp_itemContainer.getShowItemNum();
                for (var i = itemNum; i < itemNum + this._firstShowNum; i++) {
                    var playerInfo = this._list_gainsData.splice(0, 1)[0];
                    if (playerInfo) {
                        item = this.comp_itemContainer.getItemByIndex(i);
                        if (item) {
                            comp = item.getComponent(this._itemCompName);
                            comp.init(true, i, playerInfo);
                        }
                    } else break;
                }
                break;
            case rankType.type2:
                //富豪榜
                var item,
                    comp,
                    itemNum = this.comp_itemContainer.getShowItemNum();
                for (var i = itemNum; i < itemNum + this._firstShowNum; i++) {
                    var playerInfo = this._list_richData.splice(0, 1)[0];
                    if (playerInfo) {
                        item = this.comp_itemContainer.getItemByIndex(i);
                        if (item) {
                            comp = item.getComponent(this._itemCompName);
                            comp.init(false, i, playerInfo);
                        }
                    } else break;
                }
                break;
            default:
                break;
        }
    },
    //滚动到底部
    _scrollToBottom: function _scrollToBottom(event) {
        this._updateBottom();
    },
    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp: function _scrollToUp(event) {
        this.comp_scroll.scrollToTop();
    },

    _setNull: function _setNull(msg) {
        this.label_null.string = msg;
    },

    //移出屏幕外
    moveOutScene: function moveOutScene(isOut, isNoEffect) {
        if (!this._firstPosX) this._firstPosX = -this.node.x;
        if (isNoEffect) {
            var rightW = this.node.width * this.node.scale / 2;
            this.node.x = this._firstPosX * (isOut ? -1 : 1);
        } else {
            var time = 0.5;
            this.node.stopAllActions();
            var targetPos = cc.p(this._firstPosX, this.node.y);
            if (isOut) targetPos.x = -this._firstPosX;
            this.node.runAction(cc.moveTo(time, targetPos));
        }
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseUI":"BaseUI","Obj_dealScroll":"Obj_dealScroll"}],"Platform_topEffect":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e4cc4YH/zRDyK1swL8hLL4k', 'Platform_topEffect');
// Script\Views\Scene_Platform\Effects\Platform_topEffect.js

//显示在最上面的特效

cc.Class({
    "extends": cc.Component,

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {}

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Platform_topInfo":[function(require,module,exports){
"use strict";
cc._RFpush(module, '94707lnJddKIpBS9jaL9Sif', 'Platform_topInfo');
// Script\Views\Scene_Platform\UI\Platform_topInfo.js

//游戏大厅中玩家信息
cc.Class({
    'extends': require('AutoDealing'),

    properties: {
        sprite_head: {
            'default': null,
            type: cc.Sprite,
            displayName: '头像'
        },
        label_name: {
            'default': null,
            type: cc.Label,
            displayName: '昵称'
        },
        label_coin: {
            'default': null,
            type: cc.Label,
            displayName: '金币'
        },
        node_exit: {
            'default': null,
            type: cc.Node,
            displayName: '退出按钮'
        },
        node_back: {
            'default': null,
            type: cc.Node,
            displayName: '返回按钮'
        },
        node_setting: {
            'default': null,
            type: cc.Node,
            displayName: '设置按钮'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerButton();
        this.node.active = false;
    },

    _registerButton: function _registerButton() {
        this.registerButton(this.node_exit, this.onClick_exit, this);
        this.registerButton(this.node_back, this.onClick_back, this);
        this.registerButton(this.node_setting, this.onClick_setting, this);
        //增加对玩家身上金币的监听
        GG.getPlayer().registerGoldListener(this.on_goldChange.bind(this), true);
    },

    //退出游戏
    onClick_exit: function onClick_exit(event) {
        GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText2, function () {
            //确认退出
            GG.exitGame();
        });
    },
    //返回主场景
    onClick_back: function onClick_back(event) {
        GG.platformMgr.showHomeList(false);
    },
    //系统设置
    onClick_setting: function onClick_setting(event) {
        //GG.audioMgr.playMusic('gameLose');
        var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet, function (layer) {
            layer.setData();
        });
        //if(G_Config_common.isLocal){
        //    var data = {'music':true,'sound':true};
        //    if(GG.getPlayer().getSystemSet()) data = GG.getPlayer().getSystemSet();
        //    //console.log(data);
        //    var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet);
        //    layer.setData(data);
        //}else{
        //    //获取玩家系统设置
        //    if(!GG.getPlayer().getSystemSet()){
        //        var sendData = null;
        //        GG.httpMgr.sendHttpLogin(G_DIALOG_URL.loginToSetUrl, sendData, function (data) {
        //            GG.getPlayer().setSystemSet(data[0]);
        //            var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet);
        //            layer.setData(data[0]);
        //        }.bind(this));
        //    }else {
        //        var layer = GG.platformMgr.openUI(G_RES_URL.uiName.systemSet);
        //        layer.setData(GG.getPlayer().getSystemSet());
        //    }
        //}
    },
    //监听金币的变化
    on_goldChange: function on_goldChange(goldValue) {
        if (this.label_coin) this.label_coin.string = goldValue;
    },

    initShow: function initShow() {
        var jsonData = GG.getPlayer().getBaseInfo();
        if (jsonData) {
            this._setHeadImg(this.sprite_head, jsonData.avatarUrl);
            this._setName(jsonData.nickname);
            this.label_coin.string = GG.getPlayer().getPlayerGold();
        }
    },

    _setHeadImg: function _setHeadImg(spriteComp, imgName) {
        if (!this._defaultHeadFrame) this._defaultHeadFrame = spriteComp.spriteFrame;
        if (imgName) {
            this._headUrl = G_DATA.getResNetRoot(imgName);
            //this._headUrl = 'http://192.168.0.109:8080/classic/res/raw-assets/Texture/Common/head/head00.jpg';
            cc.loader.load(this._headUrl, (function (err, texture) {
                if (err) {
                    console.log(err);
                } else {
                    if (texture) {
                        var frame = new cc.SpriteFrame(texture);
                        spriteComp.spriteFrame = frame;
                    }
                }
            }).bind(this));
        } else {
            if (G_Config_common.isLocal) {
                this._headUrl = "Heads/" + 'unknown1';
                cc.loader.loadRes(this._headUrl, cc.SpriteFrame, (function (err, spriteFrame) {
                    spriteComp.spriteFrame = spriteFrame;
                }).bind(this));
            }
        }
    },

    //显示房间列表的时候，返回按钮需要改变
    setHomeListShow: function setHomeListShow(isShow) {
        if (!this.node.active) this.node.active = true;
        this.node_exit.active = !isShow;
        this.node_back.active = isShow;
    },
    //设置名字
    _setName: function _setName(name) {
        if (!name) name = '';
        this.label_name.string = G_TOOL.getNameLimit(name, 16);
    },

    onDestroy: function onDestroy() {
        GG.getPlayer().registerGoldListener(this.on_goldChange, false);
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"AutoDealing":"AutoDealing"}],"Platform_ui_enter":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'f10c7FbLoVAZ7LCP0U5t4AP', 'Platform_ui_enter');
// Script\Views\Scene_Platform\UI\Platform_ui_enter.js

//显示所有入口动画的UI

cc.Class({
    'extends': require('BaseUI'),
    properties: {
        _homeData_bull100: null, //百人的房间列表信息
        _homeData_classic: null, //经典的房间列表信息
        _homeData_grab: null, //夺宝的房间列表信息
        _compHomeList: null, //房间脚本
        _roomPrefabName: null, //房间节点名称

        node_classic: {
            'default': null,
            type: cc.Node,
            displayName: '经典玩法'
        },
        node_bull100: {
            'default': null,
            type: cc.Node,
            displayName: '百人大战'
        },
        node_grab: {
            'default': null,
            type: cc.Node,
            displayName: '押宝大战'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._registerButton(this.node_bull100, this._btn_clickEnter, this, G_TYPE.gameModule.bull100);
        this._registerButton(this.node_classic, this._btn_clickEnter, this, G_TYPE.gameModule.classic);
        this._registerButton(this.node_grab, this._btn_clickEnter, this, G_TYPE.gameModule.grab);
        this._compHomeList = null;
        this._roomPrefabName = 'Prefab_roomList';
    },

    _btn_clickEnter: function _btn_clickEnter(target, enterType) {
        if (G_Config_common.isLocal) {
            var dataObj = {};
            dataObj.data = [];
            for (var i = 0; i < 10; i++) {
                var curObj = {
                    'id': 1,
                    'minLimitPlayerBlance': 20000,
                    'currentPlayerCount': 9999
                };
                dataObj.data.push(curObj);
            }
            dataObj.enterType = enterType;

            var self = this;
            GG.platformMgr.showRoomUI(this._roomPrefabName, function (roomComp) {
                if (roomComp) {
                    self._compHomeList = roomComp;
                    GG.platformMgr.showHomeListInfo(dataObj);
                    GG.platformMgr.showHomeList(true, false);
                }
            });
            return;
        }
        this.showHomeList(enterType);
    },

    showHomeList: function showHomeList(enterType, isNoEffect) {
        if (this._compHomeList) {
            this._requestEnter(enterType, isNoEffect);
        } else {
            GG.platformMgr.showRoomUI(this._roomPrefabName, (function (roomComp) {
                if (roomComp) {
                    this._compHomeList = roomComp;
                    this._requestEnter(enterType, isNoEffect);
                }
            }).bind(this));
        }
    },

    _requestEnter: function _requestEnter(enterType, isNoEffect) {
        switch (enterType) {
            case G_TYPE.gameModule.bull100:
                if (!this._homeData_bull100) {
                    GG.httpMgr.sendHttpLogin(G_DIALOG_URL.gameRoomListUrl, 'gameModel=' + G_TYPE.net_gameModule2.bull100, (function (data) {
                        data.enterType = enterType;
                        this._homeData_bull100 = data;
                        GG.platformMgr.showHomeListInfo(data);
                        GG.platformMgr.showHomeList(true, isNoEffect);
                    }).bind(this));
                } else {
                    GG.platformMgr.showHomeListInfo(this._homeData_bull100);
                    GG.platformMgr.showHomeList(true, isNoEffect);
                }
                break;
            case G_TYPE.gameModule.grab:
                if (!this._homeData_grab) {
                    GG.httpMgr.sendHttpLogin(G_DIALOG_URL.gameRoomListUrl, 'gameModel=' + G_TYPE.net_gameModule2.grab, (function (data) {
                        data.enterType = enterType;
                        this._homeData_grab = data;
                        GG.platformMgr.showHomeListInfo(data);
                        GG.platformMgr.showHomeList(true, isNoEffect);
                    }).bind(this));
                } else {
                    GG.platformMgr.showHomeListInfo(this._homeData_grab);
                    GG.platformMgr.showHomeList(true, isNoEffect);
                }
                break;
            case G_TYPE.gameModule.classic:
                GG.tipsMgr.showConfirmTip_ONE('暂未开放！');
                //if(!this._homeData_classic){
                //    GG.httpMgr.sendHttpLogin(G_DIALOG_URL.gameRoomListUrl, 'gameModel=' +  G_TYPE.net_gameModule2.classic, function (data) {
                //        data.enterType = enterType;
                //        this._homeData_classic = data;
                //        GG.platformMgr.showHomeListInfo(data);
                //        GG.platformMgr.showHomeList(true, isNoEffect);
                //    }.bind(this));
                //}else{
                //    GG.platformMgr.showHomeListInfo(this._homeData_classic);
                //    GG.platformMgr.showHomeList(true, isNoEffect);
                //}
                break;
            default:
                break;
        }
    },

    //从游戏房间返回大厅，需要先显示房间列表
    showHomeListFirst: function showHomeListFirst(enterType, isNoEffect) {
        GG.platformMgr.showHomeList(true, isNoEffect);
        this.showHomeList(enterType, isNoEffect);
    },

    _registerButton: function _registerButton(node, callBack, target, userData, isNoScale) {
        //if(!this._list_registerBtn) this._list_registerBtn = [];
        var self = this;
        node._isTouchEnabledEx = true;
        //this._list_registerBtn.push([node, callBack, target]);
        node.on(cc.Node.EventType.TOUCH_START, function (event) {
            //if(event.target._isTouchEnabledEx) event.target.scale = G_Config_classic.scale_buttonTouch;
            var aniNode = event.target.children[0];
            if (event.target._isTouchEnabledEx && !isNoScale) {
                self._setAniPause(aniNode, true);
                event.target.scale = 1.1;
                GG.audioMgr.playSound(17);
            }
        }, target);
        node.on(cc.Node.EventType.TOUCH_END, function (event) {
            var aniNode = event.target.children[0];
            event.target.scale = 1;
            self._setAniPause(aniNode, false);
            if (event.target._isTouchEnabledEx) callBack.call(target, event, userData);
        }, target);
        node.on(cc.Node.EventType.TOUCH_CANCEL, function (event) {
            var aniNode = event.target.children[0];
            self._setAniPause(aniNode, false);
            event.target.scale = 1;
        }, target);
    },

    _setAniPause: function _setAniPause(node, isPause) {
        var comp = node.getComponent(dragonBones.ArmatureDisplay);
        if (isPause) {
            comp._lastTimeScale = comp.timeScale;
            comp.timeScale = 0.01;
        } else {
            if (comp._lastTimeScale) comp.timeScale = comp._lastTimeScale;
        }
    },
    _resumeAni: function _resumeAni(node) {},

    //移出屏幕外
    moveOutScene: function moveOutScene(isOut, isNoEffect) {
        if (!this._firstPosX) this._firstPosX = this.node.x;
        if (isNoEffect) {
            this.node.x = isOut ? this._firstPosX : cc.visibleRect.width * 2 - this._firstPosX;
        } else {
            var time = 0.5;
            this.node.stopAllActions();
            var targetPos = cc.p(cc.visibleRect.width * 2 - this._firstPosX, this.node.y);
            if (isOut) targetPos.x = this._firstPosX;
            this.node.runAction(cc.moveTo(time, targetPos));
        }
    },
    //获取房间脚本
    getRoomComp: function getRoomComp() {
        return this._compHomeList;
    }
});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseUI":"BaseUI"}],"Prefab_dialog_account":[function(require,module,exports){
"use strict";
cc._RFpush(module, '16c16qbJrVKdriJTtyWvtmd', 'Prefab_dialog_account');
// Script\Views\UIComponents\Prefab_dialog_account.js

//玩家账目界面

var sendObj = {
    gameId: 1,
    dateClass: 0,
    pageSize: 20,
    pageNow: 0
};

cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _lastTouchBtn: null, //上次点击的按钮
        _list_cacheData: null, //缓存数据

        label_null: {
            'default': null,
            type: cc.Label,
            displayName: '无数据时显示'
        },
        comp_scroll: {
            'default': null,
            type: cc.ScrollView,
            displayName: '滚动的容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();
        this._dealerItemCompName = 'Obj_dialogAccountCell';
        this._list_cacheData = [];
        //初始右边的条目容器
        var dataObj = {
            itemPrefab: this.prefab_item,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_itemContainer.setData(dataObj);
        //设置左边的按钮
        var dataObj = {
            itemPrefab: this.prefab_leftButton,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_leftBtnContainer.setData(dataObj);
        var leftBtnNum = 3;
        var strList = ['今天数据', '昨天数据', '前天数据'];
        var item, leftBtn, leftLabel;
        for (var i = 0; i < leftBtnNum; i++) {
            item = this.comp_leftBtnContainer.getItemByIndex(i);
            if (item) {
                leftBtn = item.children[0];
                leftLabel = item.children[1];
                leftBtn.tag = i;
                if (leftBtn) this.registerButton(leftBtn, this.onClick_leftBtn, this, i, true);
                if (leftLabel) leftLabel.getComponent(cc.Label).string = strList[i];
            }
        }
    },

    setData: function setData() {
        if (G_Config_common.isLocal) {
            this.showLayer();
            var curList = [];
            for (var i = 0; i < 6; i++) {
                var curObj = {
                    'completiontime': '2007/1/1',
                    transactiontype: '',
                    transactionmoney: 555,
                    balance: 6666
                };
                curList.push(curObj);
            }
            this._setBtnChoose(0);
            this._setAccountList(curList);
        } else {
            var gameID = GG.getPlayer().getGameID();
            var sendObj = {
                gameId: gameID,
                dateClass: 0,
                pageSize: 20,
                pageNow: 0
            };
            this._sendRequest(sendObj, (function (dataObj) {
                this.showLayer();
                this.setDataList(dataObj);
            }).bind(this));
        }
    },

    //点击左边的选项按钮
    onClick_leftBtn: function onClick_leftBtn(event, type) {
        var curBtn = event.currentTarget;
        if (curBtn.tag == this._lastTouchBtn.tag) return;
        if (G_Config_common.isLocal) {
            this._setBtnChoose(type);
            return;
        }
        var gameID = GG.getPlayer().getGameID();
        var sendObj = {
            gameId: gameID,
            dateClass: 0,
            pageSize: 20,
            pageNow: 0
        };
        switch (type) {
            case 0:
                sendObj.dateClass = 0;
                this._scrollToUp();
                this._sendRequest(sendObj, (function (dataObj) {
                    this.setDataList(dataObj);
                }).bind(this));
                break;
            case 1:
                sendObj.dateClass = 1;
                this._scrollToUp();
                this._sendRequest(sendObj, (function (dataObj) {
                    this.setDataList(dataObj);
                }).bind(this));
                break;
            case 2:
                sendObj.dateClass = 2;
                this._scrollToUp();
                this._sendRequest(sendObj, (function (dataObj) {
                    this.setDataList(dataObj);
                }).bind(this));
                break;
            default:
                break;
        }
        this._setBtnChoose(type);
    },

    //dayIndex : 日期索引0开始
    _setBtnChoose: function _setBtnChoose(dayIndex) {
        var item = this.comp_leftBtnContainer.getItemByIndex(dayIndex);
        if (item) {
            var leftBtn = item.children[0];
            if (this._lastTouchBtn) {
                this._lastTouchBtn.children[0].active = false;
                this._lastTouchBtn = null;
            }
            this._lastTouchBtn = leftBtn;
            leftBtn.children[0].active = true;
        }
    },

    setDataList: function setDataList(dataObj) {
        var str;
        if (dataObj.code == '1') {
            this._setAccountList(dataObj.data.datas);
            str = '';
        } else {
            str = dataObj.msg;
            this.comp_itemContainer.clearItems(-1);
        }
        this._setNull(str);
    },

    _setAccountList: function _setAccountList(dataList) {
        var item, comp;
        for (var i = 0; i < dataList.length; i++) {
            item = this.comp_itemContainer.getItemByIndex(i);
            if (item) {
                comp = item.getComponent(this._dealerItemCompName);
                comp.setData(dataList[i]);
            }
        }
        this.comp_itemContainer.clearItems(i - 1);
    },

    _setNull: function _setNull(msg) {
        this.label_null.string = msg;
    },
    //发送账目的请求
    _sendRequest: function _sendRequest(dataObj, callFunc) {
        var needData = this._list_cacheData[dataObj.dateClass];
        if (needData) {
            if (callFunc) callFunc(needData);
            this._setBtnChoose(dataObj.dateClass);
        } else {
            //gameId ： 属于那款游戏； dateClass：日期（0是当天,1,2）；pageSize：当前页面条目总数；pageNow：第几页；
            var sendStr = "gameId=" + dataObj.gameId + "&dateClass=" + dataObj.dateClass + "&pageSize=" + dataObj.pageSize + "&pageNow=" + dataObj.pageNow;
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.accountUrl, sendStr, (function (data) {
                if (callFunc) callFunc(data);
                this._setBtnChoose(dataObj.dateClass);
                this._list_cacheData[dataObj.dateClass] = data;
            }).bind(this));
        }
    },
    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp: function _scrollToUp(event) {
        this.comp_scroll.scrollToTop();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_announcement":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0a979XuE+1DBZdWzYrxkmwc', 'Prefab_dialog_announcement');
// Script\Views\UIComponents\Prefab_dialog_announcement.js

//公告弹窗界面
cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _lastTouchBtn: null, //上次点击的按钮
        _announcementContent: null, //所有预制体
        prefab_versionAnnouncement: {
            'default': null,
            type: cc.Prefab,
            displayName: '版本公告预制体'
        },
        prefab_officialDescAnnouncement: {
            'default': null,
            type: cc.Prefab,
            displayName: '官方说明预制体'
        },
        comp_scroll: {
            'default': null,
            type: cc.ScrollView,
            displayName: '滚动的容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();

        //设置左边的按钮
        var dataObj = {
            itemPrefab: this.prefab_leftButton,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_leftBtnContainer.setData(dataObj);
        var leftBtnNum = 2;
        var strList = ['版本公告', '官方说明'];
        var item, leftBtn, leftLabel;
        for (var i = 0; i < leftBtnNum; i++) {
            item = this.comp_leftBtnContainer.getItemByIndex(i);
            if (item) {
                leftLabel = item.children[2];
                this.registerButton(item, this.onClick_leftBtn, this, i, true);
                if (leftLabel) leftLabel.getComponent(cc.Label).string = strList[i];
            }
        }
        this._setBtnChoose(0);
        //设置规则内容
        this._announcementContent = [this.prefab_versionAnnouncement, this.prefab_officialDescAnnouncement];
        this._initAnnouncementsContent();
    },

    //点击左边的选项按钮
    onClick_leftBtn: function onClick_leftBtn(event, type) {
        switch (type) {
            case 0:
                this._scrollToUp();
                this._showOneRules(type);
                break;
            case 1:
                this._scrollToUp();
                this._showOneRules(type);
                break;
            case 2:
                this._scrollToUp();
                this._showOneRules(type);
                break;
            case 2:
                this._scrollToUp();
                this._showOneRules(type);
                break;
            default:
                break;
        }
        this._setBtnChoose(type);
    },

    //dayIndex : 日期索引0开始
    _setBtnChoose: function _setBtnChoose(dayIndex) {
        var item = this.comp_leftBtnContainer.getItemByIndex(dayIndex);
        if (item) {
            if (this._lastTouchBtn) {
                this._lastTouchBtn.children[0].active = true;
                this._lastTouchBtn.children[1].active = false;
                this._lastTouchBtn = null;
            }
            this._lastTouchBtn = item;
            item.children[0].active = false;
            item.children[1].active = true;
        }
    },
    //初始化公告内容
    _initAnnouncementsContent: function _initAnnouncementsContent() {
        var items = [];
        for (var i = 0; i < this._announcementContent.length; i++) {
            items[i] = cc.instantiate(this._announcementContent[i]);
            items[i].parent = this.comp_itemContainer.node;
            if (i == 0) {
                items[i].active = true;
                this.comp_itemContainer.node.height = items[i].height;
            } else items[i].active = false;
        }
    },
    //显示选中的公告内容
    _showOneRules: function _showOneRules(num) {
        var items = this.comp_itemContainer.node.children;
        for (var i = 0; i < items.length; i++) {
            if (i != num) {
                items[i].active = false;
            } else {
                items[i].active = true;
                this.comp_itemContainer.node.height = items[i].height;
            }
        }
    },
    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp: function _scrollToUp(event) {
        this.comp_scroll.scrollToTop();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_bull100Trend":[function(require,module,exports){
"use strict";
cc._RFpush(module, '925ecqVmzFC678Qg63jFlDO', 'Prefab_dialog_bull100Trend');
// Script\Views\UIComponents\Prefab_dialog_bull100Trend.js

//百人走势图

cc.Class({
    'extends': require('BaseDialog'),

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {
        this._super('UI_trend');
        this._setScrollData();
    },

    _setScrollData: function _setScrollData() {
        this._itemCompName = 'Obj_cell_trend';
        var dataObj = {
            itemPrefab: this.prefab_item,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_itemContainer.setData(dataObj);
    },

    setData: function setData(homeID) {
        var dataList;
        if (G_Config_common.isLocal) {
            dataList = [];
            for (var i = 0; i < 5; i++) {
                var curList = [['SPADE', 1], ['DIAMOND', 0], ['CLUB', 1], ['HEART', 0]];
                dataList.push(curList);
            }
            this._showList(dataList);
        } else {
            var moduleType;
            switch (GG.getGameType()) {
                case G_TYPE.gameModule.bull100:
                    moduleType = G_TYPE.http_gameModule.bull100;
                    break;
                case G_TYPE.gameModule.grab:
                    moduleType = G_TYPE.http_gameModule.grab;
                    break;
                case G_TYPE.gameModule.classic:
                    moduleType = G_TYPE.http_gameModule.classic;
                    break;
                default:
                    moduleType = 1;
                    break;
            }
            var sendData = 'gameModelId=' + moduleType + '&gameRoomId=' + homeID;
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.trendUrl, sendData, (function (data) {
                var needList = [];
                var mathList;
                var dataList = data.data;
                if (!dataList) {
                    data.msg;
                } else {
                    for (var i = 0; i < dataList.length; i++) {
                        if (!needList[i]) needList[i] = [];
                        mathList = dataList[i].matchResult;
                        if (mathList) {
                            for (var j = 0; j < mathList.length; j++) {
                                needList[i].push([mathList[j]['itemType'], mathList[j]['outcome']]);
                            }
                        }
                    }
                    this._showList(needList);
                }
            }).bind(this));
        }
    },
    _showList: function _showList(dataList) {
        var data, item, comp; //走势图显示条目为5
        for (var i = 0; i < G_Config_common.showTrendItems; i++) {
            data = dataList[i];
            if (data) {
                item = this.comp_itemContainer.getItemByIndex(i);
                if (item) {
                    comp = item.getComponent(this._itemCompName);
                    comp.setData(data);
                }
            }
        }
        this.comp_itemContainer.clearItems(i);
    },

    hideLayer: function hideLayer() {
        this._super();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_dealerList":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'd94ba+GJL9EE60oQvN313Hn', 'Prefab_dialog_dealerList');
// Script\Views\UIComponents\Prefab_dialog_dealerList.js

//上庄列表的界面

cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _minDealerGold: null, //最小上庄金额

        node_bottomBtns: {
            'default': null,
            type: cc.Node,
            displayName: '按钮容器'
        },
        node_goldTip: {
            'default': null,
            type: cc.Node,
            displayName: '金币限制提示'
        },
        comp_goldSlider: {
            'default': null,
            type: require('Obj_dealerListSlider'),
            displayName: '进度条'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super('UI_dealerList');
        this._dealerItemCompName = 'Obj_dealerListCell';
        this._moneyFormat = '<color=#FFFD61>%s</c>';
        this._limitText = [G_CHINESE.dealerGoldText1, G_CHINESE.dealerGoldText2, G_CHINESE.dealerGoldText3, G_CHINESE.dealerGoldText4];
        this._changeInterval = G_Config_bull100.dealerSliderInterval;

        this._registerEvent();
        this._initDealerList();
    },

    start: function start() {},

    setData: function setData(dataObj) {
        this._minDealerGold = GG.bull100Mgr.getMinDealerGold();
        var myGold = dataObj.myGold;
        var dealerInfo = dataObj.dealerInfo;
        //var myMaxDealerGold = dealerInfo.maxDealerCoin;
        var myselfID = GG.getPlayerID();
        var isInDealerList = false; //玩家自己在上庄列表
        var rankList = dataObj.rankList;
        if (!rankList) rankList = [];
        var dataList = [],
            playerData,
            isDealer,
            myRankNum = '';
        for (var i = 0; i < rankList.length; i++) {
            playerData = rankList[i];
            isDealer = false;

            if (playerData.playerId == dealerInfo.playerId) {
                dealerInfo = null;
                isDealer = true;
                dataList.splice(0, 0, {
                    headImg: playerData.icon,
                    name: playerData.nickname,
                    gold: playerData.usableBalance,
                    isDealer: isDealer
                });
            } else {
                dataList.push({
                    headImg: playerData.icon,
                    name: playerData.nickname,
                    gold: playerData.usableBalance,
                    isDealer: isDealer
                });
            }
            if (playerData.playerId == myselfID) {
                isInDealerList = true;
                myRankNum = i + 1;
            }
        }
        if (dealerInfo) {
            dataList.splice(0, 0, {
                headImg: dealerInfo.icon,
                name: dealerInfo.nickname,
                gold: dealerInfo.usableBalance,
                isDealer: true
            });
            if (dealerInfo.playerId == myselfID) {
                isDealer = true;
                isInDealerList = true;
            }
        }

        this.setBtnShow(isInDealerList, isDealer);
        this._setDealerList(dataList);

        var offGold = this._changeInterval;
        if (isInDealerList) {
            //在上庄列表
            this.comp_goldSlider.setData(myGold, offGold, offGold);
            if (isDealer) {
                this._showDownTip(this._limitText[2]);
            } else {
                //在排队中
                this._showDownTip(G_TOOL.formatStr(this._limitText[3], myRankNum));
            }
        } else {
            //不在上庄列表
            this.comp_goldSlider.setData(myGold, offGold, this._minDealerGold);

            if (myGold < this._minDealerGold) {
                //金币不足，无法上庄
                this._showDownTip(this._limitText[1], this._minDealerGold);
                this.setBtnEnable(this.node_bottomBtns.children[1], false);
            } else {
                //金币足够
                this._showDownTip(this._limitText[0], this._minDealerGold);
                this.setBtnEnable(this.node_bottomBtns.children[1], true);
            }
        }
    },

    _registerEvent: function _registerEvent() {
        var btns = this.node_bottomBtns.children;
        for (var i = 0; i < btns.length; i++) {
            btns[i].tag = i;
            this.registerButton(btns[i], this._onClick_dealerBtnsr, this);
        }
    },
    hideLayer: function hideLayer(event) {
        this._super();
    },
    _onClick_dealerBtnsr: function _onClick_dealerBtnsr(event) {
        switch (event.currentTarget.tag) {
            case 0:
                //续庄
                GG.bull100Mgr.commitContinueDealer(this.comp_goldSlider.getMoveNum());
                break;
            case 1:
                //上庄
                GG.bull100Mgr.send_upDealer(this.comp_goldSlider.getMoveNum());
                break;
            case 2:
                //下庄
                GG.bull100Mgr.send_downDealer();
                break;
            default:
                break;
        }
        //cc.eventManager.pauseTarget(this.comp_goldSlider.node, true);
    },

    //初始化庄家列表
    _initDealerList: function _initDealerList() {
        var dataObj = {
            itemPrefab: this.prefab_item
        };
        this.comp_itemContainer.setData(dataObj);
    },
    //设置列表内容
    _setDealerList: function _setDealerList(dataList) {
        var item,
            comp,
            i = 0;
        for (i = 0; i < dataList.length; i++) {
            item = this.comp_itemContainer.getItemByIndex(i);
            if (item) {
                comp = item.getComponent(this._dealerItemCompName);
                comp.setData(dataList[i]);
            }
        }
        this.comp_itemContainer.clearItems(i - 1);
    },

    //===================================

    //设置上庄金额限制提示
    setGoldLimitTip: function setGoldLimitTip(myGold, minDealerGold) {
        var str;
        if (myGold > minDealerGold) {
            str = this._limitText[0];
        } else {
            str = this._limitText[1];
        }
        this.node_goldTip.getComponent(cc.Label).string = str;
    },

    //根据是否已经在庄家列表来判定按钮的显隐
    setBtnShow: function setBtnShow(isMyDealer, isDealer) {
        var btns = this.node_bottomBtns.children;
        btns[0].active = isMyDealer || isDealer; //我要续庄
        btns[1].active = !(isMyDealer || isDealer); //我要上庄
        btns[2].active = isMyDealer || isDealer; //我要下庄
    },

    //上庄成功
    upDealerSuccess: function upDealerSuccess(dataObj) {
        this.showLayer();
        this.setData(dataObj);
        if (dataObj.tipStr) {
            var tipObj = {
                tipIndex: 19,
                showStr: dataObj.tipStr,
                isCountDown: false,
                showPos: G_DATA.getCenterTipPos()
            };
        }
    },

    //callBack : this.on_beforeStart.bind(this)
    // GG.tipsMgr.showTxtTip(tipObj);
    //从上庄列表下来
    downDealerSuccess: function downDealerSuccess(dataObj) {
        this.showLayer();
        this.setData(dataObj);
        if (dataObj.tipStr) {
            var tipObj = {
                tipIndex: 19,
                showStr: dataObj.tipStr,
                isCountDown: false,
                showPos: G_DATA.getCenterTipPos()
            };
            // GG.tipsMgr.showTxtTip(tipObj);
        }
    },

    //==============================

    //设置底部的tip限制提示
    _showDownTip: function _showDownTip(leftStr, gold) {
        if (gold) gold = G_TOOL.formatStr(this._moneyFormat, G_TOOL.changeMoney(gold));else gold = '';
        this.node_goldTip.getComponent(cc.RichText).string = leftStr + gold;
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog","Obj_dealerListSlider":"Obj_dealerListSlider"}],"Prefab_dialog_grabTrend":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7eb00tVidxEJaSOXaAgURkD', 'Prefab_dialog_grabTrend');
// Script\Views\UIComponents\Prefab_dialog_grabTrend.js

//押宝走势图窗体
cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _resultDict: null,
        _oneResultDict: null,
        _loseColor: null,
        _bullResult: null,
        _bullValue: null,
        node_data: {
            'default': null,
            type: cc.Node,
            displayName: '走势图数据节点'
        },
        node_noData: {
            'default': null,
            type: cc.Node,
            displayName: '无走势记录时显示节点'
        },
        node_trendBG: {
            'default': null,
            type: cc.Node,
            displayName: '无走势记录时背景节点'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();
        this._pokerCompName = 'Obj_poker';
        this._pokerResultCompName = 'Obj_onPokerResult';
        this._resultDict = {};
        this._oneResultDict = {};
        this._loseColor = new cc.Color(253, 41, 31); //失败的颜色
        this._winColor = new cc.Color(155, 176, 235); //胜利的颜色
        this._notMatchColor = this.node_data.color;
        this.node_noData.active = false;
        this._winPokerOff = G_Config_grab.num_winPokerOff;
        this._bullResult = 0;
        this._bullValue = 0;
    },

    setData: function setData(homeID) {

        var dataList;
        if (G_Config_common.isLocal) {
            dataList = {
                code: 1,
                data: [{
                    client: '[29, 30, 11, 17, 2]',
                    profitamount: 1
                }, {
                    client: '[27, 13, 45, 30, 26]',
                    profitamount: -1
                }, {
                    client: '[48, 51, 40, 15, 10]'
                }]
            };
            this._showList(dataList);
        } else {
            var sendObj = {
                gameModelId: G_TYPE.http_gameModule.grab,
                gameRoomId: homeID
            };
            this._sendRequest(sendObj, (function (dataObj) {
                this._showList(dataObj);
            }).bind(this));
        }
    },

    //发送押宝走势图信息的请求
    _sendRequest: function _sendRequest(dataObj, callFunc) {
        var sendData = 'gameModelId=' + dataObj.gameModelId + '&gameRoomId=' + dataObj.gameRoomId;
        GG.httpMgr.sendHttpLogin(G_DIALOG_URL.trendUrl, sendData, (function (data) {
            if (callFunc) callFunc(data);
        }).bind(this));
    },

    _showList: function _showList(data) {
        if (data.code == 0) {
            this.node_noData.getComponent(cc.Label).string = data.msg;
            this.node_noData.active = true;
            this.node_data.active = false;
        } else {
            var dataList = data.data;
            this.node_noData.active = false;
            this.node_data.active = true;
            this._showItem(dataList.length);
            for (var i = 0; i < dataList.length; i++) {
                //牌型牛几显示
                this._setPokerGroupInfo(dataList[i].client, this.node_data.children[i]);
                //胜负显示
                this._setWinOrLose(parseInt(dataList[i].profitamount), this.node_data.children[i]);
            }
        }
    },
    //显示一副新卡牌信息
    _setPokerGroupInfo: function _setPokerGroupInfo(groupInfoList, pokerNodeList) {
        var pokerIndex,
            pokerInfo,
            pokerList = [];
        groupInfoList = JSON.parse(groupInfoList);
        for (var i = 0; i < groupInfoList.length; i++) {
            pokerIndex = groupInfoList[i];
            pokerInfo = G_DATA.getPokerInfo(pokerIndex);
            pokerList.push({ flow: pokerInfo.pokerType, pokerValue: pokerInfo.pokerValue });
            if (i < 3) {
                if (pokerInfo.pokerValue > 10) pokerInfo.pokerValue = 10;
                this._bullResult += pokerInfo.pokerValue;
            }
            if (i == 3) {
                if (pokerInfo.pokerValue > 10) pokerInfo.pokerValue = 10;
                this._bullValue = pokerInfo.pokerValue;
            }
            if (i == 4) {
                if (pokerInfo.pokerValue > 10) pokerInfo.pokerValue = 10;
                this._bullValue += pokerInfo.pokerValue;
            }
        }
        this._refreshPokerGroundInfo(pokerList, pokerNodeList);
    },
    //根据数据条数显示
    _showItem: function _showItem(len) {
        for (var i = 0; i < len; i++) {
            this.getNodeData().children[i].active = true;
        }
    },
    //刷新一副卡牌信息
    _refreshPokerGroundInfo: function _refreshPokerGroundInfo(pokerList, pokerNodeList) {
        var pokerNode,
            bullValue = this._bullValue,
            bullResult = parseInt(this._bullResult);

        for (var i = 0; i < pokerList.length + 1; i++) {
            pokerNode = pokerNodeList.children[i];
            if (i < 5) pokerNode.getComponent(this._pokerCompName).setPokerInfo(pokerList[i].flow, pokerList[i].pokerValue, true);
            if (i == 5) {
                if (bullResult % 10 == 0) {
                    bullValue = this._bullValue % 10;
                    if (bullValue == 0) bullValue = 10;
                } else bullValue = -1;
                pokerNode.getComponent(this._pokerResultCompName).showPokerResultValue(parseInt(bullValue), true);
            }
        }
        this._bullValue = 0;
        this._bullResult = 0;
    },
    //显示胜负信息
    _setWinOrLose: function _setWinOrLose(winOrLose, node) {
        var labelText, labelColor;
        this._winOrLoseLabel = node.children[6].getComponent(cc.Label);
        this._winOrLoseNode = node.children[6];
        if (winOrLose) {
            //大于0 显示胜；小于0显示负
            if (winOrLose > 0) {
                labelText = G_CHINESE.winMatch;
                labelColor = this._winColor;
            } else {
                labelText = G_CHINESE.loseMatch;
                labelColor = this._loseColor;
            }
        } else {
            labelText = G_CHINESE.notInvolvedMatch; //显示未参与
            labelColor = this._notMatchColor;
        }
        this._winOrLoseLabel.string = labelText;
        this._winOrLoseNode.color = labelColor;
    },
    getNodeData: function getNodeData() {
        return this.node_data;
    }
});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_playerList":[function(require,module,exports){
"use strict";
cc._RFpush(module, '31df5LxwYNGio3rVyUA8TeG', 'Prefab_dialog_playerList');
// Script\Views\UIComponents\Prefab_dialog_playerList.js

//玩家列表的界面

cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        label_null: {
            'default': null,
            type: cc.Label,
            displayName: '无数据时显示'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super('UI_playerList');

        this._dealerItemCompName = 'Obj_dialogPlayerListCell';
        var dataObj = {
            itemPrefab: this.prefab_item,
            lineNum: 4,
            scrollType: G_TYPE.scrollType.bag
        };
        this.comp_itemContainer.setData(dataObj);
    },

    setData: function setData() {
        GG.httpMgr.sendHttpLogin(G_DIALOG_URL.playerListUrl, null, (function (data) {
            var dataList = data.data,
                str;
            if (data.code == '0') {
                str = data.msg;
            } else {
                str = '';
                this._showList(dataList);
            }
            this._setNull(str);
        }).bind(this));
    },
    _showList: function _showList(dataList) {
        var item, comp;
        for (var i = 0; i < dataList.length; i++) {
            item = this.comp_itemContainer.getItemByIndex(i);
            if (item) {
                comp = item.getComponent(this._dealerItemCompName);
                comp.setData(dataList[i]);
            }
        }
        this.comp_itemContainer.clearItems(i - 1);
    },
    _setNull: function _setNull(msg) {
        this.label_null.string = msg;
    },
    hideLayer: function hideLayer() {
        this._super();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_record":[function(require,module,exports){
"use strict";
cc._RFpush(module, '8f2caKCwp1J3b0ZdiUpPIaq', 'Prefab_dialog_record');
// Script\Views\UIComponents\Prefab_dialog_record.js

//玩家记录界面
cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _lastTouchBtn: null, //上次点击的按钮
        _list_cacheData: null, //缓存数据
        label_null: {
            'default': null,
            type: cc.Label,
            displayName: '无数据时显示'
        },
        comp_scroll: {
            'default': null,
            type: cc.ScrollView,
            displayName: '滚动的容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();
        this._dealerItemCompName = 'Obj_dialogRecordCell';
        this._list_cacheData = [];
        //右边滚动容器
        var dataObj = {
            itemPrefab: this.prefab_item,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_itemContainer.setData(dataObj);
        //设置左边的按钮
        var dataObj = {
            itemPrefab: this.prefab_leftButton,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_leftBtnContainer.setData(dataObj);
        var leftBtnNum = 3;
        var strList = ['今天数据', '昨天数据', '前天数据'];
        var item, leftBtn, leftLabel;
        for (var i = 0; i < leftBtnNum; i++) {
            item = this.comp_leftBtnContainer.getItemByIndex(i);
            if (item) {
                leftBtn = item.children[0];
                leftLabel = item.children[1];
                leftBtn.tag = i;
                if (leftBtn) this.registerButton(leftBtn, this.onClick_leftBtn, this, i, true);
                if (leftLabel) leftLabel.getComponent(cc.Label).string = strList[i];
            }
        }
    },

    setData: function setData() {
        if (G_Config_common.isLocal) {
            this.showLayer();
            var curList = [];
            for (var i = 0; i < 7; i++) {
                var curObj = {
                    'confirmtime': '2007/1/1',
                    'gamemodelname': 'home1',
                    'gameroomname': 'home2',
                    'effectiveamount': null,
                    'profitamount': -1
                };
                curList.push(curObj);
            }
            this._setBtnChoose(0);
            this._setRecordList(curList);
        } else {
            var gameID = GG.getPlayer().getGameID();
            var sendObj = {
                gameId: gameID,
                dateClass: 0,
                pageSize: 20,
                pageNow: 0
            };
            this._sendRequest(sendObj, (function (dataObj) {
                this.showLayer();
                this.setDataList(dataObj);
            }).bind(this));
        }
    },

    //点击左边的选项按钮
    onClick_leftBtn: function onClick_leftBtn(event, type) {
        var curBtn = event.currentTarget;
        if (curBtn.tag == this._lastTouchBtn.tag) return;
        if (G_Config_common.isLocal) {
            this._setBtnChoose(type);
            return;
        }
        var gameID = GG.getPlayer().getGameID();
        var sendObj = {
            gameId: gameID,
            dateClass: 0,
            pageSize: 20,
            pageNow: 0
        };

        switch (type) {
            case 0:
                sendObj.dateClass = 0;
                this._scrollToUp();
                this._sendRequest(sendObj, (function (dataObj) {
                    this.setDataList(dataObj);
                }).bind(this));
                break;
            case 1:
                sendObj.dateClass = 1;
                this._scrollToUp();
                this._sendRequest(sendObj, (function (dataObj) {
                    this.setDataList(dataObj);
                }).bind(this));
                break;
            case 2:
                sendObj.dateClass = 2;
                this._scrollToUp();
                this._sendRequest(sendObj, (function (dataObj) {
                    this.setDataList(dataObj);
                }).bind(this));
                break;
            default:
                break;
        }
        this._setBtnChoose(type);
    },

    //dayIndex : 日期索引0开始
    _setBtnChoose: function _setBtnChoose(dayIndex) {
        var item = this.comp_leftBtnContainer.getItemByIndex(dayIndex);
        if (item) {
            var leftBtn = item.children[0];
            if (this._lastTouchBtn) {
                this._lastTouchBtn.children[0].active = false;
                this._lastTouchBtn = null;
            }
            this._lastTouchBtn = leftBtn;
            leftBtn.children[0].active = true;
        }
    },

    setDataList: function setDataList(dataObj) {
        var str;
        if (dataObj.code == '1' && dataObj.data.datas) {
            this._setRecordList(dataObj.data.datas);
            str = '';
        } else {
            //没有记录
            str = dataObj.msg;
            this.comp_itemContainer.clearItems(-1);
        }
        this._setNull(str);
    },

    _setRecordList: function _setRecordList(dataList) {
        var item, comp;
        for (var i = 0; i < dataList.length; i++) {
            item = this.comp_itemContainer.getItemByIndex(i);
            if (item) {
                comp = item.getComponent(this._dealerItemCompName);
                comp.setData(dataList[i]);
            }
        }
        this.comp_itemContainer.clearItems(i - 1);
    },

    _setNull: function _setNull(msg) {
        this.label_null.string = msg;
    },

    //发送记录信息的请求
    _sendRequest: function _sendRequest(dataObj, callFunc) {
        var needData = this._list_cacheData[dataObj.dateClass];
        if (needData) {
            if (callFunc) callFunc(needData);
            this._setBtnChoose(dataObj.dateClass);
        } else {
            //gameId ： 属于那款游戏； dateClass：日期（0是当天,1,2）；pageSize：当前页面条目总数；pageNow：第几页；
            var sendStr = "gameId=" + dataObj.gameId + "&dateClass=" + dataObj.dateClass + "&pageSize=" + dataObj.pageSize + "&pageNow=" + dataObj.pageNow;
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.recordUrl, sendStr, (function (data) {
                if (callFunc) callFunc(data);
                this._setBtnChoose(dataObj.dateClass);
                this._list_cacheData[dataObj.dateClass] = data;
            }).bind(this));
        }
    },

    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp: function _scrollToUp(event) {
        this.comp_scroll.scrollToTop();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_rulesInHome":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0a6d2HHpjdIAJJYp8T52/0Q', 'Prefab_dialog_rulesInHome');
// Script\Views\UIComponents\Prefab_dialog_rulesInHome.js

//房间内的规则显示

cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        prefab_Bull100: {
            'default': null,
            type: cc.Prefab,
            displayName: '百人规则内容'
        },
        prefab_grab: {
            'default': null,
            type: cc.Prefab,
            displayName: '押宝规则内容'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super('UI_rulesInHome');
        this._rulesContent = [this.prefab_Bull100, this.prefab_grab];
    },

    setData: function setData(num) {
        this._initRulesContent(num);
    },

    //初始化规则内容
    _initRulesContent: function _initRulesContent(num) {
        var item = cc.instantiate(this._rulesContent[num - 1]);
        item.parent = this.comp_itemContainer.node;

        item.active = true;
    },
    hideLayer: function hideLayer() {
        this._super();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_rules":[function(require,module,exports){
"use strict";
cc._RFpush(module, '966e2xbsN5Cn4mFk1IER7RH', 'Prefab_dialog_rules');
// Script\Views\UIComponents\Prefab_dialog_rules.js

//规则界面
cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _rulesContent: null, //所有预制体
        prefab_introduce: {
            'default': null,
            type: cc.Prefab,
            displayName: '规则简介预制体'
        },
        prefab_bull100: {
            'default': null,
            type: cc.Prefab,
            displayName: '百人规则预制体'
        },
        prefab_grab: {
            'default': null,
            type: cc.Prefab,
            displayName: '押宝规则预制体'
        },
        comp_scroll: {
            'default': null,
            type: cc.ScrollView,
            displayName: '滚动的容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();

        //设置左边的按钮
        var dataObj = {
            itemPrefab: this.prefab_leftButton,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_leftBtnContainer.setData(dataObj);
        var strList = ['游戏简介', '百人大战', '押宝大战']; //"经典大战" 暂时未使用
        var item, leftBtn, leftLabel;
        for (var i = 0; i < strList.length; i++) {
            item = this.comp_leftBtnContainer.getItemByIndex(i);
            if (item) {
                leftLabel = item.children[2];
                this.registerButton(item, this.onClick_leftBtn, this, i, true);
                if (leftLabel) leftLabel.getComponent(cc.Label).string = strList[i];
            }
        }
        this._setBtnChoose(0);
        // this._prefab_item = cc.instantiate(cc.instantiate(this.prefab_item));

        //设置规则内容
        this._rulesContent = [this.prefab_introduce, this.prefab_bull100, this.prefab_grab]; //this.prefab_grab 暂时未使用
        this._initRulesContent();
    },

    //点击左边的选项按钮
    onClick_leftBtn: function onClick_leftBtn(event, type) {
        switch (type) {
            case 0:
                //简介
                this._scrollToUp();
                this._showOneRules(type);
                break;
            case 1:
                //百人大战
                this._scrollToUp();
                this._showOneRules(type);
                break;
            case 2:
                //押宝大战
                this._scrollToUp();
                this._showOneRules(type);
                break;
            case 3:
                //经典大战
                this._scrollToUp();
                this._showOneRules(type);
                break;
            default:
                break;
        }
        this._setBtnChoose(type);
    },

    //dayIndex : 日期索引0开始
    _setBtnChoose: function _setBtnChoose(dayIndex) {
        var item = this.comp_leftBtnContainer.getItemByIndex(dayIndex);
        if (item) {
            if (this._lastTouchBtn) {
                this._lastTouchBtn.children[0].active = true;
                this._lastTouchBtn.children[1].active = false;
                this._lastTouchBtn = null;
            }
            this._lastTouchBtn = item;
            item.children[0].active = false;
            item.children[1].active = true;
        }
    },
    //初始化规则内容
    _initRulesContent: function _initRulesContent() {
        var items = [];
        for (var i = 0; i < this._rulesContent.length; i++) {
            items[i] = cc.instantiate(this._rulesContent[i]);
            items[i].parent = this.comp_itemContainer.node;
            if (i == 0) {
                this.comp_itemContainer.node.height = items[i].height;
                items[i].active = true;
            } else items[i].active = false;
        }
    },
    //显示选中的规则内容
    _showOneRules: function _showOneRules(num) {
        var items = this.comp_itemContainer.node.children;
        for (var i = 0; i < items.length; i++) {
            if (i != num) {
                items[i].active = false;
            } else {
                this.comp_itemContainer.node.height = items[i].height;
                items[i].active = true;
            }
        }
    },
    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp: function _scrollToUp(event) {
        this.comp_scroll.scrollToTop();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_set":[function(require,module,exports){
"use strict";
cc._RFpush(module, '82611/wMStMw5RCoIcdMM0t', 'Prefab_dialog_set');
// Script\Views\UIComponents\Prefab_dialog_set.js

//玩家系统设置
var setType = {
    typeMusic: 1,
    typeSound: 2
};

cc.Class({
    'extends': require('BaseDialog'),

    properties: {
        _musicIsOpen: null, //音乐是否开启
        _soundIsOpen: null, //音效是否开启
        _isOpenFirst: null, //是否第一次打开
        _dict_lastSet: null, //上次的设定，判定是否有改变配置

        node_changeBtn: {
            'default': null,
            type: cc.Node,
            displayName: '切换账号'
        },
        node_musicSwitch: {
            'default': null,
            type: cc.Node,
            displayName: '音乐开关'
        },
        node_soundSwitch: {
            'default': null,
            type: cc.Node,
            displayName: '音效开关'
        },
        node_musicIcon: {
            'default': null,
            type: cc.Node,
            displayName: '音乐左边图标'
        },
        node_soundIcon: {
            'default': null,
            type: cc.Node,
            displayName: '音效左边图标'
        },
        label_name: {
            'default': null,
            type: cc.Label,
            displayName: '玩家名字'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._super();
        this._registerEvent();
        this._isOpenFirst = true;
    },
    _registerEvent: function _registerEvent() {
        this.registerButton(this.node_changeBtn, this._onClick_change, this);
        this.registerButton(this.node_musicSwitch, this.onClick_open, this, setType.typeMusic, true);
        this.registerButton(this.node_soundSwitch, this.onClick_open, this, setType.typeSound, true);
    },
    hideLayer: function hideLayer() {
        this._super();
        this._dict_lastSet = null;
    },

    _onClick_close: function _onClick_close() {
        if (G_Config_common.isLocal) {
            this.hideLayer();
            return;
        }

        if (this._dict_lastSet) {
            if (this._dict_lastSet['music'] != this._musicIsOpen || this._dict_lastSet['sound'] != this._soundIsOpen) {
                //配置发生改变,保存设置
                var musiceStr = this._musicIsOpen ? 'Set_open' : 'Set_close';
                var soundStr = this._soundIsOpen ? 'Set_open' : 'Set_close';
                var sendStr = "musicClass=" + musiceStr + '&soundClass=' + soundStr + '&gameType=DOU_NIU';
                var self = this;
                GG.httpMgr.sendHttpLogin(G_DIALOG_URL.saveSystemSetUrl, sendStr, (function (data) {
                    self.hideLayer();
                }).bind(this));
            } else this.hideLayer();
        } else this.hideLayer();
    },

    setData: function setData() {
        var self = this;
        if (G_Config_common.isLocal) {
            var data = { 'music': true, 'sound': true };
            this._dict_lastSet = data;
            if (GG.audioMgr.getAudioConfig()) data = GG.audioMgr.getAudioConfig();else GG.audioMgr.setAudioConfig({ 'music': true, 'sound': true });
            this.defaultSetSound(data.music, data.sound);
            this._setName();
            self.showLayer();
        } else {
            //获取玩家系统设置
            //if(this._isOpenFirst){
            //    GG.httpMgr.sendHttpLogin(G_DIALOG_URL.getSystemSetUrl, 'gameType=DOU_NIU', function (data) {
            //        var config;
            //        if(data.code == 0){
            //            config = {'music':true,'sound':true};
            //        } else {
            //            config = data.data.result[0];
            //        }
            //        //如果音乐已经播放，则不需要重复播放
            //        if(config.music && GG.audioMgr.getIsPlayMusic()) GG.audioMgr.saveSoundConfig(config.sound);
            //        else GG.audioMgr.setAudioConfig(config);
            //
            //        var data = GG.audioMgr.getAudioConfig();
            //        self.defaultSetSound(data.music, data.sound);
            //        self._setName();
            //        self._isOpenFirst = false;
            //        self.showLayer();
            //    }.bind(this));
            //}else {
            //    var data = GG.audioMgr.getAudioConfig();
            //    this.defaultSetSound(data.music, data.sound);
            //    this._setName();
            //    self.showLayer();
            //}
            var data = GG.audioMgr.getAudioConfig();
            if (data) {
                this._dict_lastSet = {};
                for (var attr in data) {
                    this._dict_lastSet[attr] = data[attr];
                }
                this.defaultSetSound(data.music, data.sound);
                this._setName();
                self.showLayer();
            }
        }
    },

    //玩家点击了开关
    onClick_open: function onClick_open(event, type) {
        switch (type) {
            case setType.typeMusic:
                //音乐
                this._setMusicOpen();
                GG.audioMgr.saveMusicConfig(this._musicIsOpen);
                break;
            case setType.typeSound:
                //音效
                this._setSoundOpen();
                GG.audioMgr.saveSoundConfig(this._soundIsOpen);
                break;
        }
    },
    //设置音乐开关
    _setMusicOpen: function _setMusicOpen() {
        this._musicIsOpen = !this._musicIsOpen;
        //表现
        var pointImg, openImg, lightIcon;
        openImg = this.node_musicSwitch.children[0];
        pointImg = this.node_musicSwitch.children[1];
        lightIcon = this.node_musicIcon.children[0];

        openImg.active = this._musicIsOpen;
        if (this._musicIsOpen) {
            pointImg.x = this.node_musicSwitch.width / 2 - pointImg.width / 2;
        } else {
            pointImg.x = -this.node_musicSwitch.width / 2 + pointImg.width / 2;
        }
        lightIcon.active = this._musicIsOpen;
    },
    //设置音效开关
    _setSoundOpen: function _setSoundOpen() {
        this._soundIsOpen = !this._soundIsOpen;
        //表现
        var pointImg, openImg, lightIcon;
        openImg = this.node_soundSwitch.children[0];
        pointImg = this.node_soundSwitch.children[1];
        lightIcon = this.node_soundIcon.children[0];

        openImg.active = this._soundIsOpen;
        if (this._soundIsOpen) {
            pointImg.x = this.node_soundSwitch.width / 2 - pointImg.width / 2;
        } else {
            pointImg.x = -this.node_soundSwitch.width / 2 + pointImg.width / 2;
        }
        lightIcon.active = this._soundIsOpen;
    },

    //玩家默认系统设置(第一次进入游戏设置), isOpenMusic=true则开启音乐，音效等同
    defaultSetSound: function defaultSetSound(isOpenMusic, isOpenSound) {
        this._musicIsOpen = !isOpenMusic;
        this._soundIsOpen = !isOpenSound;
        this._setMusicOpen();
        this._setSoundOpen();
    },

    //设置名字
    _setName: function _setName() {
        var name = '';
        if (!G_Config_common.isLocal) name = GG.getPlayer().getPlayerName();
        if (!name) name = '';
        this.label_name.string = G_TOOL.getNameLimit(name, 16);
    },

    _onClick_change: function _onClick_change(event) {
        GG.tipsMgr.showConfirmTip_TWO(G_CHINESE.exitText2, function () {
            //确认退出
            GG.exitGame();
        });

        //var sendData = "gameId=1&dateClass=0&pageSize=20&pageNow=0";
        //GG.httpMgr.sendHttpLogin("game/statistics/user/records.html", sendData, function (data) {
        //    console.log('get login======');
        //    console.log(data);
        //}.bind(this));
    }
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_dialog_trend":[function(require,module,exports){
"use strict";
cc._RFpush(module, '414dePmJ3NOd7eATXcLfkn7', 'Prefab_dialog_trend');
// Script\Views\UIComponents\Prefab_dialog_trend.js

//走势图

cc.Class({
    'extends': require('BaseDialog'),

    properties: {},

    // use this for initialization
    onLoad: function onLoad() {
        this._super('UI_trend');
        this._setScrollData();
    },

    _setScrollData: function _setScrollData() {
        this._itemCompName = 'Obj_cell_trend';
        var dataObj = {
            itemPrefab: this.prefab_item,
            scrollType: G_TYPE.scrollType.vertical
        };
        this.comp_itemContainer.setData(dataObj);
    },

    setData: function setData(homeID) {
        var dataList;
        if (G_Config_common.isLocal) {
            dataList = [];
            for (var i = 0; i < 5; i++) {
                var curList = [['SPADE', 1], ['DIAMOND', 0], ['CLUB', 1], ['HEART', 0]];
                dataList.push(curList);
            }
            this._showList(dataList);
        } else {
            var moduleType;
            switch (GG.getGameType()) {
                case G_TYPE.gameModule.bull100:
                    moduleType = G_TYPE.http_gameModule.bull100;
                    break;
                case G_TYPE.gameModule.grab:
                    moduleType = G_TYPE.http_gameModule.grab;
                    break;
                case G_TYPE.gameModule.classic:
                    moduleType = G_TYPE.http_gameModule.classic;
                    break;
                default:
                    moduleType = 1;
                    break;
            }
            var sendData = 'gameModelId=' + moduleType + '&gameRoomId=' + homeID;
            GG.httpMgr.sendHttpLogin(G_DIALOG_URL.trendUrl, sendData, (function (data) {
                var needList = [];
                var mathList;
                var dataList = data.data;
                if (!dataList) {
                    data.msg;
                } else {
                    for (var i = 0; i < dataList.length; i++) {
                        if (!needList[i]) needList[i] = [];
                        mathList = dataList[i].matchResult;
                        if (mathList) {
                            for (var j = 0; j < mathList.length; j++) {
                                needList[i].push([mathList[j]['itemType'], mathList[j]['outcome']]);
                            }
                        }
                    }
                    this._showList(needList);
                }
            }).bind(this));
        }
    },
    _showList: function _showList(dataList) {
        var data, item, comp; //走势图显示条目为5
        for (var i = 0; i < G_Config_common.showTrendItems; i++) {
            data = dataList[i];
            if (data) {
                item = this.comp_itemContainer.getItemByIndex(i);
                if (item) {
                    comp = item.getComponent(this._itemCompName);
                    comp.setData(data);
                }
            }
        }
        this.comp_itemContainer.clearItems(i);
    },

    hideLayer: function hideLayer() {
        this._super();
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseDialog":"BaseDialog"}],"Prefab_roomList":[function(require,module,exports){
"use strict";
cc._RFpush(module, '45c8dc1TENGMo9nIQJY25EY', 'Prefab_roomList');
// Script\Views\UIComponents\Prefab_roomList.js

//存放所有的房间列表
cc.Class({
    'extends': require('BaseUI'),

    properties: {
        _dict_loadScene: null, //是否已经预加载需要的场景
        _isChangeNow: null, //是否立即跳转场景
        _curEnterType: null, //当前的选择的场景类型

        prefab_item: {
            'default': null,
            type: cc.Prefab,
            displayName: '房间预制体'
        },
        comp_itemContainer: {
            'default': null,
            type: require('Obj_dealScroll'),
            displayName: '房间列表容器'
        }
    },

    // use this for initialization
    onLoad: function onLoad() {
        this._dealerItemCompName = 'Obj_roomListCell';
        this._dict_loadScene = {};
        //设置滚动层信息
        var dataObj = {
            itemPrefab: this.prefab_item,
            lineNum: 3,
            scrollType: G_TYPE.scrollType.bag
        };
        this.comp_itemContainer.setData(dataObj);
    },

    setDataList: function setDataList(dataObj) {
        if (!dataObj) return;
        this.showLayer();
        this._isChangeNow = false;
        this._curEnterType = dataObj.enterType;
        this._reloadScene(this._curEnterType);
        this.setRoomList(dataObj.data, dataObj.enterType);
    },

    setRoomList: function setRoomList(dataList, enterType) {
        if (dataList) {
            var item, comp, dataObj;
            for (var i = 0; i < dataList.length; i++) {
                item = this.comp_itemContainer.getItemByIndex(i);
                if (item) {
                    comp = item.getComponent(this._dealerItemCompName);
                    dataObj = dataList[i];
                    dataObj.enterType = enterType;
                    comp.setData(dataObj);
                }
            }
        } else var i = -1;
        this.comp_itemContainer.clearItems(i - 1);
    },

    //预加载场景
    _reloadScene: function _reloadScene(enterType) {
        if (!this._dict_loadScene[enterType]) {
            //var sceneName = G_DATA.getSceneName(enterType);
            var self = this;
            var sceneName = G_TYPE.sceneName.loadingScene;
            cc.director.preloadScene(sceneName, function () {
                if (self._isChangeNow) cc.director.loadScene(sceneName);else self._dict_loadScene[enterType] = true;
            });
        }
    },
    showLayer: function showLayer() {
        this.node.active = true;
    },
    hideLayer: function hideLayer() {
        this.node.active = false;
    },
    //设置是否加载完成后立即跳转场景
    setIsChangeNow: function setIsChangeNow(isChange) {
        this._isChangeNow = isChange;
    },

    //确认场景是否已经预加载完成
    getIsReload: function getIsReload() {
        return this._dict_loadScene[this._curEnterType];
    }

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{"BaseUI":"BaseUI","Obj_dealScroll":"Obj_dealScroll"}],"ReloadResMgr":[function(require,module,exports){
"use strict";
cc._RFpush(module, '45da4o85DFHhavko98DhvWK', 'ReloadResMgr');
// Script\Common\Managers\ReloadResMgr.js

//资源预加载管理

var mgr = cc.Class({
    _dict_urlRes: null, //路径资源，不包括头像
    _dict_headImg: null, //头像资源
    _list_headImg: null,
    ctor: function ctor() {},
    //加载单张头像图片资源
    getHeadImg: function getHeadImg(url, callFunc) {
        if (!this._dict_headImg) {
            this._dict_headImg = {};
            this._list_headImg = [];
        }

        if (this._dict_headImg[url]) {
            if (callFunc) callFunc(this._dict_headImg[url]);
            var index = this._list_headImg.indexOf(url);
            if (index > -1) {
                this._list_headImg.splice(index, 1);
                this._list_headImg.splice(0, 0, url);
            }
        } else {
            cc.loader.load(url, (function (err, texture) {
                if (err) {
                    console.log(err);
                } else {
                    if (texture) {
                        var frame = new cc.SpriteFrame(texture);
                        if (frame) {
                            this._dict_headImg[url] = frame;
                            this._list_headImg.splice(0, 0, url);
                            if (this._list_headImg.length > 15) {
                                var lastUrl = this._list_headImg.pop();
                                this.removeOneHeadImg(lastUrl);
                            }
                            if (callFunc) callFunc(frame);
                            return;
                        }
                    }
                    if (callFunc) callFunc(null);
                }
            }).bind(this));
        }
    },

    //释放单个资源
    removeOneHeadImg: function removeOneHeadImg(url) {
        if (this._dict_headImg[url]) {
            delete this._dict_headImg[url];
            cc.loader.releaseRes(url);
        }
    },

    //释放所有资源
    removeAllImages: function removeAllImages() {
        for (var url in this._dict_headImg) {
            cc.loader.releaseRes(url);
        }
    }
});

module.exports = mgr;

cc._RFpop();
},{}],"SocketCenter":[function(require,module,exports){
"use strict";
cc._RFpush(module, 'e36ccbGsY1MGLLYmQC2ljPd', 'SocketCenter');
// Script\Common\Managers\SocketCenter.js


//数据对象结构体，具体的属性将通过服务端来完善
//socket对象
//var WebSocket = WebSocket || window.WebSocket || window.MozWebSocket;
if (window.netBeanInited == undefined || !window.netBeanInited) {
    //只能初始化一次
    window.NetBean = new Object();
}
/** 消息类,用来封装消息,解析消息 */
var Msg = function Msg() {
    this.pos = 0;
    this.data = "";
    this.len = 0; //消息长度
    this.deepCount = 0; //deepCount 用于object嵌套检测,>=10时不再解析
};
var initTime = 1400000000;
var NetServer = function NetServer(wsUrl, msgHandle, printMsg) {
    this.wsUrl = wsUrl;this.msgHandle = msgHandle;this.printMsg = printMsg;
    this.timeDiff = initTime; //时间差=当前时间-服务器时间
    this.getServerTime = function () {
        return new Date().getTime() - this.timeDiff;
    };
    this.resetServerTime = function (serverTime) {
        this.timeDiff = Math.min(this.timeDiff, new Date().getTime() - serverTime);
    };
};
//网络服务是否已连接
NetServer.prototype.isConnected = function () {
    return this.socket && this.socket.readyState == WebSocket.OPEN;
};
NetServer.prototype.connect = function () {
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
    }
    if (window.WebSocket) {
        this.socket = new WebSocket(this.wsUrl);
        var netSrv = this;
        this.socket.onmessage = function (event) {
            //读取数据
            var obj = Msg.toObject(event.data, netSrv);
            if (obj == -1) {
                if (netSrv.onOpen) {
                    netSrv.onOpen();
                }
            } else {
                // console.dir(obj);
                // if(obj.constructor.className == "NbTest"){
                //     netSrv.send("NbTest", obj);
                // }
                var byHandle = true;
                //夺宝大战通过下面代码注入接收消息,返回值表示是否由默认handle处理
                // netSrv.msgReceived = function (className, obj) {
                //     return false;
                // }
                if (netSrv.msgReceived) {
                    byHandle = netSrv.msgReceived(obj.constructor.className, obj);
                }
                //if(byHandle && netSrv.msgHandle[obj.constructor.className]){
                //    netSrv.msgHandle[obj.constructor.className](obj);
                //}
                if (netSrv.printMsg) {
                    // console.log('\n收到数据 event:'+obj.constructor.event +" ,eventName:"+ obj.constructor.className+',\n' + TestUtil.objToStr(obj));
                }
            }
        };
        this.socket.onopen = function (event) {
            this.send('0'); //发送一个字节获取json
        };
        this.socket.onclose = function (event) {
            if (netSrv.onClose) {
                netSrv.onClose();
            }
        };
        return this;
    } else {
        return undefined;
    }
};
/** 发送心跳消息 */
NetServer.prototype.sendHeart = function () {
    if (this.socket.readyState == WebSocket.OPEN) {
        this.socket.send(String.fromCharCode(9));
        return true;
    } else {
        throw new Error("Disconnect from Server,Please try again...");
    }
};

NetServer.prototype.send = function (type, obj) {
    if (this.socket.readyState == WebSocket.OPEN) {
        try {
            var msg = Msg.toMsg(type, obj);
            this.socket.send(msg);
        } catch (e) {
            console.error(e);
        }
        return true;
    } else {
        throw new Error("Disconnect from Server,Please try again...");
    }
};
/** 数据类型,名称后面带S为列表,MAP为map或对象,必须为奇数,单数据则为偶数 */
Msg.TYPE_NUM = 0, Msg.TYPE_NUMS = 1, Msg.TYPE_NUM_MAP = 2, Msg.TYPE_STRING = 4, Msg.TYPE_STRINGS = 5, Msg.TYPE_STRING_MAP = 6, Msg.TYPE_BOOL = 8, Msg.TYPE_BOOLS = 9, Msg.TYPE_BOOL_MAP = 10;
Msg.Type = function (collectionType, jsonClass) {
    this.collectionType = collectionType;
    this.jsonClass = jsonClass;
    return this;
};

Msg.getMsg = function (fromServerData) {
    var m = new Msg();
    m.data = fromServerData;
    return m;
};

var TYPE_SINGLE = 0,
    TYPE_ARRAY = 1,
    TYPE_LIST = 2,
    TYPE_MAP = 3;
Msg.prototype.loadJsons = function (netSrv) {
    var getJson = function getJson(index) {
        if (!NetBean[index]) NetBean[index] = function () {
            return this;
        };
        return NetBean[index];
    };
    var jsonCount = this.getLong();
    for (var i = 0; i < jsonCount; i++) {
        var index = this.getLong();
        var t = getJson(index);
        t.event = index;
        t.className = this.getString();
        t.sendBy = this.getLong();
        NetBean[t.className] = t;
        var fieldCount = this.getLong();
        t.fields = new Array();
        t.types = new Array();
        t.requireds = new Array();
        for (var j = 0; j < fieldCount; j++) {
            var fieldIndex = this.getLong();
            t.requireds[fieldIndex] = this.getLong() == 1;
            t.fields[fieldIndex] = this.getString();
            var collectionType = this.getLong();
            var fieldType = this.getLong();
            if (fieldType < 8) {
                //基本数据类型
                fieldType = fieldType * 4 + collectionType;
                t.types[fieldIndex] = fieldType;
            } else {
                t.types[fieldIndex] = new Msg.Type(collectionType, getJson(fieldType - 8));
            }
        }
    }
    window.netBeanInited = true;
};

Msg.toObject = function (fromServerData, netSrv) {
    var msg = Msg.getMsg(fromServerData);
    var serverTime = msg.getLong();
    netSrv.resetServerTime(serverTime);
    var classIndex = msg.getLong();
    if (classIndex == -1) {
        msg.loadJsons(netSrv);
        return -1;
    }
    return msg.toObject(NetBean[classIndex]);
};

Msg.toMsg = function (nbType, obj) {
    var msg = new Msg();
    var nb = NetBean[nbType];
    msg.putLong(nb.event);
    msg.toMsg(nb, obj);
    return msg.data;
};

Msg.prototype.toMsg = function (nb, o) {
    var fieldCount = 0;
    this.mark(); //fieldCount
    var types = nb.types,
        fields = nb.fields,
        requireds = nb.requireds;
    for (var i in fields) {
        var val = o[fields[i]];
        if (val == undefined || val == null) {
            if (requireds[i]) {
                throw new Error("required的值不能为空,NetBean." + nb.className + "." + nb.fields[i]);
            }
            continue;
        }
        fieldCount++;
        this.putLong(i);
        var error = this.toFieldMsg(types[i], val);
        if (error != null) {
            throw new Error("变量赋值错误,NetBean." + nb.className + "." + nb.fields[i] + "=" + val + ",数据类型应该为:" + error);
        }
    }
    this.putByteOnMark(fieldCount);
};

Msg.prototype.toFieldMsg = function (type, val) {
    switch (type) {
        case Msg.TYPE_NUM:
            if (!Number.isInteger(val)) return "数字整型";
            this.putLong(val);
            break;
        case Msg.TYPE_STRING:
            if (typeof val != "string") return "字符串类型";
            this.putString(val);
            break;
        case Msg.TYPE_BOOL:
            if (typeof val != "boolean") return "布尔类型";
            this.putBoolean(val);
            break;
        default:
            {
                var collectionType = TYPE_SINGLE;
                var dataType = Msg.TYPE_NUM;
                if (typeof type == "number") {
                    collectionType = type & 3;
                    dataType = type - collectionType;
                } else {
                    collectionType = type.collectionType;
                    dataType = -1;
                }
                switch (collectionType) {
                    case TYPE_SINGLE:
                        {
                            this.toMsg(type, val);
                        }break;
                    case TYPE_MAP:
                        {
                            this.mark();
                            var len = 0;
                            for (var j in val) {
                                this.toFieldMsg(Msg.TYPE_STRING, j + "");
                                if (dataType != -1) {
                                    this.toFieldMsg(dataType, val[j]);
                                } else {
                                    this.toMsg(type.jsonClass, val[j]);
                                }
                                len++;
                            }
                            this.putLongOnMark(val.length);
                        }break;
                    default:
                        {
                            //Array
                            this.putLong(val.length);
                            for (var j in val) {
                                if (dataType != -1) {
                                    this.toFieldMsg(dataType, val[j]);
                                } else {
                                    this.toMsg(type.jsonClass, val[j]);
                                }
                            }
                        };
                }
                break;
            }
    }
    return null;
};

/**
 * @param jsonClass Json对应的类
 * @returns 消息转换的对象
 */
Msg.prototype.toObject = function (jsonClass) {
    var obj;
    try {
        obj = new jsonClass();
    } catch (e) {
        console.log(e);
    }
    var fieldLen = this.getByte();
    for (var i = 0; i < fieldLen; i++) {
        //获取fieldIndex,再获取变量值的模式
        var fieldIndex = this.getLong();
        var jsonType = jsonClass.types[fieldIndex];
        var isObject = typeof jsonType != 'number';
        if (!isObject && jsonType & 3 != 0 || isObject && jsonType.collectionType != TYPE_SINGLE) {
            //奇数,列表,先读取数量
            var isMap = false;
            if (!isObject) {
                if ((jsonType & 3) == 3) isMap = true;
                jsonType = jsonType - (jsonType & 3);
            } else {
                isMap = jsonType.collectionType == TYPE_MAP;
            }
            var listLen = this.getFiled(Msg.TYPE_NUM);
            if (!isMap) {
                obj[jsonClass.fields[fieldIndex]] = new Array();
            } else {
                obj[jsonClass.fields[fieldIndex]] = {};
            }
            for (var j = 0; j < listLen; j++) {
                var key = isMap ? this.getFiled(Msg.TYPE_STRING) : j;
                obj[jsonClass.fields[fieldIndex]][key] = this.getFiled(jsonType);
            }
        } else {
            obj[jsonClass.fields[fieldIndex]] = this.getFiled(jsonType);
        }
    }
    return obj;
};

Msg.prototype.getFiled = function (jsonType) {
    switch (jsonType) {
        case Msg.TYPE_NUM:
            return this.getLong();
            break;
        case Msg.TYPE_STRING:
            return this.getString();
            break;
        case Msg.TYPE_BOOL:
            return this.getBoolean();
            break;
        default:
            return this.toObject(jsonType.jsonClass);
            break;
    }
};

Msg.prototype.getLen = function () {
    var len = this.data.charCodeAt(this.pos++);
    var byteCount = len >> 4 & 7;
    len = len & 15;
    for (var i = 0; i < byteCount; i++) {
        len = len | this.data.charCodeAt(this.pos++) << i * 7 + 4;
    }
    return len + this.pos;
};

Msg.prototype.putLong = function (val) {
    var positive = val > 0;
    if (!positive) val = val * -1;
    var bit = 0;var tmp = val;
    while (tmp > 0) {
        tmp = parseInt(tmp / 2);
        bit++;
    }
    bit = parseInt((bit + 3) / 7);
    this.data += String.fromCharCode(bit << 4 | (positive ? 0 : 1) << 3 | val & 7); //高1-3多余byte数，低4位正0(负1)，低1-3位数据最低3位
    val = parseInt(val / (1 << 3));
    for (var i = 0; i < bit; i++) {
        this.data += String.fromCharCode(val & 127);
        val = parseInt(val / (1 << 7));
    }
};

Msg.prototype.getLong = function () {
    var d = this.data.charCodeAt(this.pos++);
    var rs = d & 7;
    var byteCount = d >> 4 & 7;
    var rate = 1 << 3; //用乘法代替位移避免溢出
    for (var i = 0; i < byteCount; i++) {
        rs = rs + (this.data.charCodeAt(this.pos++) & 255) * rate;
        rate *= 1 << 7;
    }
    rs = rs * ((d & 8) == 0 ? 1 : -1);
    return rs;
};

Msg.prototype.mark = function () {
    if (!this.marks) this.marks = new Array();
    this.marks.push(this.data);
    this.data = "";
};

Msg.prototype.putByteOnMark = function (val) {
    this.data = this.marks.pop() + String.fromCharCode(val & 127) + this.data;
};

Msg.prototype.putLongOnMark = function (val) {
    var rs = this.marks.pop();
    var positive = val > 0;
    if (!positive) val = val * -1;
    var bit = 0;var tmp = val;
    while (tmp > 0) {
        tmp = parseInt(tmp / 2);
        bit++;
    }
    bit = parseInt((bit + 3) / 7);
    rs += String.fromCharCode(bit << 4 | (positive ? 0 : 1) << 3 | val & 7); //高1-3多余byte数，低4位正0(负1)，低1-3位数据最低3位
    val = parseInt(val / (1 << 3));
    for (var i = 0; i < bit; i++) {
        rs += String.fromCharCode(val & 127);
        val = parseInt(val / (1 << 7));
    }
    this.data = rs + this.data;
};

Msg.prototype.putByte = function (val) {
    return this.data += String.fromCharCode(val & 127);
};

Msg.prototype.getByte = function () {
    return this.data.charCodeAt(this.pos++) & 127;
};
Msg.prototype.putBoolean = function (val) {
    return this.data += String.fromCharCode(val ? 1 : 0);
};

Msg.prototype.getBoolean = function () {
    return this.data.charCodeAt(this.pos++) == 1;
};

Msg.prototype.putString = function (val) {
    var count = 0;
    for (var i = 0; i < val.length; i++) {
        var char = val.charCodeAt(i);
        if (char < 0x7f) {
            count += 1;
        } else if (char <= 0x7ff) {
            count += 2;
        } else if (char <= 0xFFFF) {
            count += 3;
        } else {
            count += 4;
        }
    }
    var bit = 0,
        tmp = count;
    while (tmp > 0) {
        tmp = tmp >> 1;
        bit++;
    }
    bit = parseInt((bit + 2) / 7);
    tmp = count;
    this.data += String.fromCharCode(bit << 5 | tmp & 31);
    tmp = tmp >> 5;
    for (var i = 0; i < bit; i++) {
        this.data += String.fromCharCode(tmp & 127);
        tmp = tmp >> 7;
    }
    this.len += count - val.length;
    this.data += val;
};

Msg.prototype.getString = function () {
    var d = this.data.charCodeAt(this.pos++);
    var lenCount = d >> 5 & 3; //高2位字节数
    var byteCount = d & 31;
    for (var i = 0; i < lenCount; i++) {
        byteCount = byteCount | (this.data.charCodeAt(this.pos++) & 255) << i * 7 + 5;
    }
    if (byteCount == 0) return "";
    var count = 0,
        start = this.pos;
    for (var i = this.pos; i < this.data.length; i++) {
        var char = this.data.charCodeAt(this.pos++);
        if (char < 0x7f) {
            count += 1;
        } else if (char <= 0x7ff) {
            count += 2;
        } else if (char <= 0xFFFF) {
            count += 3;
        } else {
            count += 4;
        }
        if (count >= byteCount) {
            break;
        }
    }
    return this.data.substring(start, this.pos);
};
module.exports = NetServer;

cc._RFpop();
},{}],"SocketMgr":[function(require,module,exports){
"use strict";
cc._RFpush(module, '0988511JJBLsZEgA1+HGHAL', 'SocketMgr');
// Script\Common\Managers\SocketMgr.js

//WebSocket 状态判定   ===》 WebSocket.readyState
//OPEN      连接已开启并准备好进行通信。
//CLOSING  连接正在关闭的过程中。
//CLOSED    连接已经关闭，或者连接无法建立。
//CONNECTING 连接还没开启。

//webSocket  管理器
var NetServer = require('SocketCenter');
var socketMgr = cc.Class({
    //extends: cc.Component,
    _socketCenter: null, //当前使用的socket对象
    _dict_shortEvents: null, //短对接的事件，一般是主动的请求
    _dict_longEvents: null, //长对接的事件，被动接收的事件
    _dict_pauseInfo: null, //暂停时候保存下来的信息
    _list_sendFail: null, //发送请求失败的协议
    _isHasBreak: null, //是否已经端过网络
    properties: {},

    // use this for initialization
    ctor: function ctor() {
        //GG.socketMgr = this;

        this._dict_shortEvents = {};
        this._dict_longEvents = {};
        this._isHasBreak = false;
    },

    //增加socket链接
    connectSocket: function connectSocket(callFunc) {
        if (this._socketCenter) return;
        var netServer = window.netServer;
        if (netServer && netServer.isConnected()) {
            //console.log('已经存在socket链接。。。。。。')
            if (callFunc) callFunc(true);
            netServer.msgReceived = this.receiveMsg.bind(this);
            this._socketCenter = netServer;
        } else {
            //console.log('新建一个socket链接。。。。。。')
            var host = window.location.host;
            if (host.indexOf(":") >= 0) host = host.substring(0, host.indexOf(":"));
            if (host == "") host = "localhost";
            //host = '192.168.0.113'
            //host = '192.168.0.107'
            var port = 8888;
            if (window.wsPort) port = window.wsPort;
            this._socketCenter = new NetServer("ws://" + host + ":" + port + "/ws", null, false);
            this._socketCenter.msgReceived = this.receiveMsg.bind(this);
            var self = this;
            this._socketCenter.onOpen = (function () {
                // console.log("socket loading open ==");
                GG.topTouchLayer.closeNetRequest();
                if (callFunc) callFunc(true);
                if (this._list_sendFail) {
                    var netData;
                    for (var i = 0; i < this._list_sendFail.length; i++) {
                        netData = this._list_sendFail[i];
                        this.SendMsg(netData.netType, netData.msgDict, netData.callFunc);
                    }
                }
                this._list_sendFail = null;
                if (this._isHasBreak) {
                    //如果已经断过链接，则重连的时候需要返回房间
                    this._isHasBreak = false;
                    if (!this._isLimit) if (GG.getGameType() != G_TYPE.gameModule.platform) GG.exitHome();
                }
                if (!this._isLimit) GG.loginSocket();
                this._isLimit = false;
            }).bind(this);
            this._socketCenter.onClose = function () {
                // console.log("socket close ==");

                self._connect();
                self._isHasBreak = true;
                if (this._isLimit) self._startTimeOutCheck();
            };
            this._connect();
            GG.topTouchLayer.showNetRequest();
        }
    },

    _connect: function _connect() {
        if (this._socketCenter) {
            this._socketCenter.connect();
            this._addHeartInfo();
        }
    },

    getServerTime: function getServerTime() {
        if (this._socketCenter) return this._socketCenter.getServerTime();else return new Date().getTime();
    },

    //发送数据
    SendMsg: function SendMsg(netType, msgDict, callFunc) {
        if (this._dict_shortEvents.hasOwnProperty(netType)) return;

        var msg;
        if (NetBean[netType]) {
            msg = new NetBean[netType]();
            for (var key in msgDict) {
                msg[key] = msgDict[key];
            }
            if (callFunc) this._dict_shortEvents[netType] = callFunc;
        } else {
            // console.log('no this nettype on server==='+netType)
            if (!this._list_sendFail) this._list_sendFail = [];
            this._list_sendFail.push({
                netType: netType,
                msgDict: msgDict,
                callFunc: callFunc
            });
            return;
        }
        console.log('发送的数据是');
        console.log(msg);
        this._socketCenter.send(netType, msg);
    },

    //接受数据
    receiveMsg: function receiveMsg(netType, recvData) {
        console.log('接受数据:: ' + netType);
        console.log(recvData);
        console.log(recvData.tip.tip);
        var gameMain = cc.find('Canvas/GameMain');
        //游戏已经关闭，不需要消息
        if (!gameMain || !gameMain.active) return true;

        if (GG.getGameState() == G_TYPE.webGameState.pause) {
            //游戏暂停了
            this._savePauseInfo(netType, recvData);
            return false;
        }

        //该功能函数没有完善，只会返回true
        var isOk = this._checkCode(recvData);
        if (!isOk) {
            // console.log('get server data fail'+recvData.tip.tip)
            return false;
        }

        this._refreshPlayerGold(netType, recvData);

        if (GG.getIsLoading()) {
            //拦截加载场景过程中接收到的数据
            this._setLoadingNetData(netType, recvData);
            return;
        }

        var isContinue = true;
        if (this._dict_shortEvents && this._dict_shortEvents[netType]) {
            //主动请求的消息
            this._dict_shortEvents[netType](recvData);
            delete this._dict_shortEvents[netType];
            isContinue = false;
        } else if (this._dict_longEvents && this._dict_longEvents[netType]) {
            //
            this._dict_longEvents[netType](recvData);
            isContinue = false;
        }
        return isContinue;
    },

    //注册长监听事件
    registerLong: function registerLong(netType, callFunc) {
        //if(this._dict_longEvents.hasOwnProperty(netType)) return null;
        this._dict_longEvents[netType] = callFunc;
    },
    //取消某个长监听
    cancelLong: function cancelLong(netType) {
        delete this._dict_longEvents[netType];
    },
    //取消所有长监听
    cancelAllLongListen: function cancelAllLongListen() {
        for (var key in this._dict_longEvents) {
            delete this._dict_longEvents[key];
        }
        for (var key in this._dict_shortEvents) {
            delete this._dict_shortEvents[key];
        }
    },

    //================================

    //获取两个时间戳的间隔
    getOffSeconds: function getOffSeconds(startTime, endTime) {
        return Math.max(Math.floor((endTime - startTime) * 0.001), 0);
    },

    //添加心跳
    _addHeartInfo: function _addHeartInfo() {
        var heartLastTime = new Date().getTime();
        var heartTimer = setInterval((function () {
            var now = new Date().getTime();
            if (now - heartLastTime >= 3500) {
                //心跳连接至少每3.5秒一次
                if (this._socketCenter && this._socketCenter.isConnected()) this._socketCenter.sendHeart();
                heartLastTime = new Date().getTime();
            }
        }).bind(this), 500, null);
    },

    //检查是否是可用信息
    _checkCode: function _checkCode(recvData) {
        switch (recvData.tip.code) {
            case G_TYPE.serverCodeType.success:
                //success
                break;
            case G_TYPE.serverCodeType.serverErr1:
                //服务器忙
                GG.tipsMgr.showConfirmTip_ONE(recvData.tip.tip);
                /*, function () {
                 GG.exitHome();
                 }*/
                return false;
        }
        return true;
    },

    //通过某些协议刷新玩家金币消息
    _refreshPlayerGold: function _refreshPlayerGold(netType, recvData) {
        if (netType == NetType.r_mineHaveGrab) {
            var tip = recvData.tip;
            if (tip.code != G_TYPE.serverCodeType.success) {
                return;
            }
            GG.getPlayer().setPlayerGold(recvData.player.balance);
        }
    },

    //拦截场景加载中途传入的数据
    _setLoadingNetData: function _setLoadingNetData(netType, recvData) {
        switch (netType) {
            case NetType.r_bull100_reStartGame:
                //刷新进房信息_百人
                GG.curMgr.updateEnterHomeData(recvData);

                var homeData = GG.getPlayer().getInHomeData();
                homeData.net_resetHomeData = recvData;
                GG.getPlayer().setInHomeData(homeData);
                break;
            case NetType.r_grab_reStartGame:
                //刷新进房信息_押宝
                GG.curMgr.updateEnterHomeData(recvData);

                var homeData = GG.getPlayer().getInHomeData();
                homeData.net_resetHomeData = recvData;
                GG.getPlayer().setInHomeData(homeData);
                break;
            default:
                break;
        }
    },

    //=====================暂停处理

    //保存暂停时候接收的数据
    _savePauseInfo: function _savePauseInfo(netType, recvData) {
        // console.log('when game pause ::'+netType)

        if (!this._dict_pauseInfo) this._dict_pauseInfo = {};

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
        if (GG.curMgr) {
            switch (netType) {
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
                    if (recvData.player) {
                        if (!isNaN(recvData.player.balance)) {
                            GG.curMgr.getMyselfComp().setGoldValue(recvData.player.balance);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    },
    //解析暂停时候收到的消息
    parsePauseInfo: function parsePauseInfo() {
        if (GG.curMgr) {
            GG.curMgr.refreshHome();
            return;
        }
    },

    //=======================================请求延时判定

    //计算网络请求时间
    _startTimeOutCheck: function _startTimeOutCheck() {
        var emptyNode = cc.find('Canvas/emptyNode');
        if (emptyNode) {
            if (!this._failTime) {
                this._failTime = G_Config_common.connectTimeOut;
                GG.topTouchLayer.showNetRequest(true);
                emptyNode.stopAllActions();
                var self = this;
                emptyNode.runAction(cc.sequence(cc.delayTime(this._failTime), cc.callFunc(function () {
                    GG.topTouchLayer.closeNetRequest();
                    GG.showReconnect(function () {
                        self._cancelTimeOutCheck();
                    });
                }, this)));
            }
        }
    },
    //取消超时监听
    _cancelTimeOutCheck: function _cancelTimeOutCheck() {
        var emptyNode = cc.find('Canvas/emptyNode');
        if (emptyNode) {
            this._failTime = null;
            emptyNode.stopAllActions();
        }
        GG.topTouchLayer.showNetRequest(true);
    },
    //异地登陆后取消超长监听
    setTimeOutLimit: function setTimeOutLimit(isLimit) {
        var emptyNode = cc.find('Canvas/emptyNode');
        if (emptyNode) {
            this._failTime = null;
            emptyNode.stopAllActions();
        }
        this._isLimit = isLimit;
    }

});

//是否已经连接socket
//getIsConnecting : function () {
//    return this._socketObj && this._socketObj.readyState == WebSocket.OPEN;
//},

// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },
module.exports = socketMgr;

cc._RFpop();
},{"SocketCenter":"SocketCenter"}],"TablesMgr":[function(require,module,exports){
"use strict";
cc._RFpush(module, '4cc05tUHe5HaZ3zY38K9L/1', 'TablesMgr');
// Script\Common\Managers\TablesMgr.js

//表格操作，读取与解析

//表格对象，存放着这个表格所有的信息
var tableObj = cc.Class({
    _nameList: null,
    _typeList: null,
    _firstDict: null,
    _dataDict: null,
    _keyList: null,
    setData: function setData(_nameList, _typeList, dataDict, keyList) {
        this._nameList = _nameList;
        this._typeList = _typeList;
        this._firstDict = dataDict;
        this._keyList = keyList;
        this._dataDict = {};
    },

    getDataByID: function getDataByID(keyID) {
        if (this._dataDict[keyID]) return this._dataDict[keyID];
        var data = this._parseObj(keyID);
        this._dataDict[keyID] = data;
        return data;
    },
    getKeyList: function getKeyList() {
        return this._keyList;
    },
    //获取第一条信息的值
    getFirstData: function getFirstData() {
        var data;
        for (var key in this._firstDict) {
            data = this.getDataByID(key);
            if (data) break;
        }
        return data;
    },
    //获取所有的表格信息
    getAllData: function getAllData() {
        var index,
            data = {};
        for (var key in this._keyList) {
            index = this._keyList[key];
            data[index] = this.getDataByID(index);
        }
        return data;
    },

    _parseObj: function _parseObj(keyID) {
        if (!this._firstDict) return null;
        var curData = this._firstDict[keyID];
        if (!curData) {
            console.log('table parse _____ no this key :' + keyID);
            return null;
        }
        var dataObj = {};
        //第一个ID的值忽略
        for (var i = 1; i < curData.length; i++) {
            var data = this._parseByType(curData[i], this._typeList[i], this._nameList[i]);
            dataObj[this._nameList[i]] = data;
        }
        return dataObj;
    },
    _parseByType: function _parseByType(data, type, objName) {
        if (data == undefined || data == null) return null;

        try {
            switch (type) {
                case 'key_int':
                    return parseInt(data);
                case 'number':
                    return parseFloat(data);
                case 'string':
                    return data.toString();
                case 'json':
                    data = data.replace(/\'/g, '\"');
                    return JSON.parse(data);
                case 'calculate':
                    return data.toString();

                default:
                    return null;
            }
        } catch (e) {
            console.log(e);
            // console.log('table parse _______ fileName =' + this._fileName + ';  objName =' + objName + ';  error obj =' + data)
            return null;
        }
    }

});

//表格管理器
var tableMgr = cc.Class({
    //extends : cc.Component,
    properties: {
        _tDict: null,
        _callFunc: null,
        _list_reloadNames: null
    },
    ctor: function ctor() {
        //GG.tableMgr = this;
    },
    getTable: function getTable(tableName) {
        return this._tDict[tableName];
    },

    //导入表格
    reloadTables: function reloadTables(tableNames, callFunc) {
        var type = Object.prototype.toString.call(tableNames);
        switch (type) {
            case '[object String]':
                this._list_reloadNames = [tableNames];
                break;
            case '[object Array]':
                this._list_reloadNames = tableNames;
                break;
            case '[object Object]':
                this._list_reloadNames = [];
                for (var key in tableNames) {
                    this._list_reloadNames.push(tableNames[key]);
                }
                break;
            default:
                return;
        }
        this._callFunc = callFunc;
        this._loadStep();
    },
    _loadStep: function _loadStep() {
        if (this._list_reloadNames.length <= 0) {
            if (this._callFunc) {
                this._callFunc();
                this._callFunc = null;
            }
            return;
        }
        //var name = this._list_reloadNames[0];
        var name = this._list_reloadNames.splice(0, 1);
        this._loadTable(name);
    },
    _loadTable: function _loadTable(tableName) {
        if (this._tDict && this._tDict[tableName]) {
            this._loadStep();
            return;
        }
        cc.loader.loadRes("Tables/" + tableName, (function (err, data) {
            if (err) {
                console.log(err);
                if (this._callFunc) {
                    this._callFunc();
                    this._callFunc = null;
                }
            } else {
                if (!this._tDict) this._tDict = {};
                this._tDict[tableName] = this._sortData(data);
                this._loadStep();
            }
        }).bind(this));
    },

    _sortData: function _sortData(xdata) {
        if (!xdata) {
            console.log('table is null');
            return null;
        }
        this._data = xdata;
        var _nameList, _typeList;
        var _xData = {};
        var _keyList = [];
        for (var i = 0; i < xdata.length; i++) {
            switch (i) {
                case 0:
                    //tips
                    break;
                case 1:
                    //dataName
                    _nameList = xdata[i];
                    break;
                case 2:
                    //dataType
                    _typeList = xdata[i];
                    break;
                default:
                    //dataDetail
                    var keyID = xdata[i][0];
                    _keyList.push(keyID);
                    _xData[keyID] = xdata[i];
                    break;
            }
        }

        var table = new tableObj();
        table.setData(_nameList, _typeList, _xData, _keyList);
        return table;
    },

    //游戏一开始加载所有的配置
    reloadConfigOnStart: function reloadConfigOnStart(callFunc) {
        this.reloadTables(G_RES_URL.dict_tablesName, callFunc);
    },

    onDestroy: function onDestroy() {
        //for(var tableName in this._tDict){
        //    cc.loader.release("Tables/"+tableName);
        //}
    }
});

module.exports = tableMgr;

cc._RFpop();
},{}],"TipsMgr":[function(require,module,exports){
"use strict";
cc._RFpush(module, '1d79e9uH8xIC6c4VzCQUoc6', 'TipsMgr');
// Script\Common\Managers\TipsMgr.js

//提示弹窗类的管理器

cc.Class({
    'extends': cc.Component,
    properties: {
        _curTxtTipNum: null,
        _confirmTag: null, //确认框显示标记
        _list_hideTips: null, //隐藏并可复用的tip

        prefab_confirmTip: cc.Prefab, //可点击确定的弹窗
        prefab_txtTip: cc.Prefab, //文本提示

        node_countDownProgress: cc.Node, //图形文字倒计时提示
        node_systemTip: cc.Node, //系统提示
        Node_topNotice: cc.Node },

    //公告通知类
    // use this for initialization
    onLoad: function onLoad() {
        GG.tipsMgr = this;

        this._progressCompName = 'Obj_countDownProgress';
        this._systemTipCompName = 'Obj_systemTip';
        this._txtTipCompName = 'Obj_txtTip';
        this._confirmTipCompName = 'Obj_confirmTip';
        this._topNoticeCompName = 'Obj_notice';
        this._dict_onlyOne = {};
        this._curTxtTipNum = 0;
        this._commonScale = G_Config_common.frameScale;
        this._confirmTag = 3;
        this._list_hideTips = [];
    },

    //显示纯文本弹窗, pos为空默认显示在中央, 可重复使用的tip
    showTxtTip: function showTxtTip(dataObj) {
        var tip = this._getTxtTip();;
        tip.showTip(dataObj);
        return tip;
    },
    _getTxtTip: function _getTxtTip() {
        var tip = this._list_hideTips.splice(0, 1)[0];
        if (!tip) {
            tip = this._getNewTip();
        }

        return tip.getComponent(this._txtTipCompName);
    },
    _getNewTip: function _getNewTip() {
        var tip = cc.instantiate(this.prefab_txtTip);
        //this.node.addChild(tip, 0, this._curTxtTipNum);
        tip.parent = this.node;
        tip.scale = this._commonScale;
        tip.getComponent(this._txtTipCompName).bindMgr(this);
        this._curTxtTipNum += 1;
        return tip;
    },
    oneTipHide: function oneTipHide(tipNode) {
        var tipComp = tipNode.getComponent(this._txtTipCompName);
        if (tipComp) {
            if (!this._dict_onlyOne[tipComp.getTipIndex()]) {
                this._list_hideTips.push(tipNode);
            }
        }
    },

    //=---------------------------只显示一次的tip

    showOnlyOne: function showOnlyOne(dataObj) {
        if (!this._dict_onlyOne) this._dict_onlyOne = {};
        var tipIndex = dataObj.tipIndex;
        var tip = this._dict_onlyOne[tipIndex];
        if (tip) {
            if (tip.getIsShow()) {
                tip.forceChangeCountDown(dataObj.retainTime);
            } else {
                tip.showTip(dataObj);
            }
        } else {
            tip = this._getNewTip().getComponent(this._txtTipCompName);
            this._dict_onlyOne[tipIndex] = tip;
            tip.showTip(dataObj);
        }
        return tip;
    },

    //======================================

    //显示倒计时文字图形
    showProgressCountDown: function showProgressCountDown(time, callFunc) {
        this.node_countDownProgress.getComponent(this._progressCompName).showCountDown(time, callFunc);
    },
    getProgressLeaveTime: function getProgressLeaveTime() {
        return this.node_countDownProgress.getComponent(this._progressCompName).getLeaveTime();
    },
    forceProgressEnd: function forceProgressEnd() {
        this.node_countDownProgress.getComponent(this._progressCompName).forceEnd();
    },

    //======================================

    //显示可点击确定的弹窗   单选按钮
    showConfirmTip_ONE: function showConfirmTip_ONE(showStr, callFunc) {
        var confirmPage = this.node.getChildByTag(this._confirmTag);
        if (!confirmPage) {
            confirmPage = cc.instantiate(this.prefab_confirmTip);
            confirmPage.parent = this.node;
            confirmPage.tag = 3;
        }
        confirmPage.getComponent(this._confirmTipCompName).showContentEx(showStr, callFunc);
    },
    //多选按钮
    showConfirmTip_TWO: function showConfirmTip_TWO(showStr, callFunc, callFunc2) {
        var confirmPage = this.node.getChildByTag(this._confirmTag);
        if (!confirmPage) {
            confirmPage = cc.instantiate(this.prefab_confirmTip);
            confirmPage.parent = this.node;
            confirmPage.tag = 3;
        }
        confirmPage.getComponent(this._confirmTipCompName).showContent(showStr, callFunc, callFunc2);
    },

    //清理弹出的确认框
    clearConfirm: function clearConfirm() {
        var confirm = this.node.getChildByTag(this._confirmTag);
        if (confirm) {
            var comp = confirm.getComponent(this._confirmTipCompName);
            if (comp && confirm.active) comp.closePage();
        }
    },

    //注册确认框的打开关闭事件监听   isRegister: 是否开启监听
    registerConfirm: function registerConfirm(func, isRegister) {
        if (!this.node) return;
        var confirm = this.node.getChildByTag(this._confirmTag);
        if (confirm) {
            confirm.getComponent(this._confirmTipCompName).registerFunc(func, isRegister);
        }
    },

    //======================================

    //显示体统提示
    showSystem: function showSystem(showStr, pos, time, callBack) {
        this.node_systemTip.getComponent(this._systemTipCompName).showContent(showStr, pos, time, callBack);
    },

    //======================================

    //显示公告信息
    addNotice: function addNotice(msg) {
        this.Node_topNotice.getComponent(this._topNoticeCompName).addMsg(msg);
    },

    //添加禁止点击层，仅对touch事件吞噬，对mouse事件没有效果;
    setTouchLimit: function setTouchLimit(isLimit) {
        var parent = this.node.parent;
        if (isLimit) parent.on(cc.Node.EventType.TOUCH_START, this._limitCall, this);else parent.off(cc.Node.EventType.TOUCH_START, this._limitCall, this);
    },
    _limitCall: function _limitCall() {}

});
// called every frame, uncomment this function to activate update callback
// update: function (dt) {

// },

cc._RFpop();
},{}],"Tools":[function(require,module,exports){
"use strict";
cc._RFpush(module, '7371f6mFJVCgZ3MshobzVAP', 'Tools');
// Script\Common\Tools.js

//项目中可以通用的工具

var tools = {
    //返回数值包括最大最小值
    getRandomArea: function getRandomArea(downNum, upNum) {
        return parseInt(Math.random() * (upNum - downNum + 1) + downNum);
    },
    //返回随机的bool值是或否
    getRandomBool: function getRandomBool() {
        return Boolean(Math.random() > 0.5);
    },
    //使用%s，%d重新配置字符串，如 this.formatStr('这是:%s',9099)  ==> 这是:9099
    formatStr: function formatStr() {
        var t = arguments,
            e = t.length;
        if (e < 1) return "";
        var i = /(%d)|(%s)/,
            n = 1,
            r = t[0],
            s = "string" == typeof r && i.test(r);
        if (s) for (var o = /%s/; n < e; ++n) {
            var a = t[n],
                c = "number" == typeof a ? i : o;
            c.test(r) ? r = r.replace(c, a) : r += " " + a;
        } else if (e > 1) for (; n < e; ++n) r += " " + t[n];else r = "" + r;
        return r;
    },
    //改变金钱的单位
    changeMoney: function changeMoney(money) {
        //var rate=null,name = '',dict = G_Config_common.dict_moneyUnit;
        //for(var rateLimit in dict){
        //    if(num >= rateLimit){
        //        rate = rateLimit;
        //        name = dict[rateLimit];
        //    }else break;
        //}
        //if(!rate) return num;
        //var limitLen = 4;       //将金额的数值控制在4位数
        //var value = parseFloat((num / rate).toFixed(3)).toString();
        //var valueLen = value.length;
        //if(valueLen >limitLen){
        //    var pointIndex = value.indexOf('.');
        //    if(pointIndex < 0){
        //        //整数
        //        value = value.substring(0,limitLen+1);
        //    }else{
        //        //有小数
        //        if(pointIndex >= limitLen) value = value.substring(0,limitLen);
        //        else value = value.substring(0,limitLen+1);
        //    }
        //}
        //return value+name
        var zeroCnt = 0,
            nega = ""; //带多少个0，负数标志位
        if (money < 0) {
            money = -money;
            nega = "-";
        }
        var tmp = money;
        while ((tmp = tmp / 10) >= 1) zeroCnt++;
        if (zeroCnt <= 3) return nega + money;
        for (var i = 0, tmp = 1; i < zeroCnt - 3; i++) {
            money /= 10;
            tmp = tmp == 10000 ? 10 : tmp * 10;
        }
        return nega + parseInt(money) / (10000 / tmp) + (zeroCnt < 8 ? "万" : zeroCnt < 12 ? "亿" : "兆");
    },
    //千分符，千位加逗号
    formatBy1000: function formatBy1000(money) {
        return ("" + money).replace(/(\d{1,3})(?=(\d{3})+(?:$|\D))/g, "$1,");
    },
    //获取当前的时间戳, 这种获取方式可以精确到毫秒
    getTimeStamp: function getTimeStamp() {
        //var timestamp = Date.parse(new Date());  //精确到秒
        return new Date().valueOf();
    },

    quickSort: function quickSort(array) {
        function sort(prev, numsize) {
            var nonius = prev;
            var j = numsize - 1;
            var flag = array[prev];
            if (numsize - prev > 1) {
                while (nonius < j) {
                    for (; nonius < j; j--) {
                        if (array[j] < flag) {
                            array[nonius++] = array[j]; //a[i] = a[j]; i += 1;
                            break;
                        };
                    }
                    for (; nonius < j; nonius++) {
                        if (array[nonius] > flag) {
                            array[j--] = array[nonius];
                            break;
                        }
                    }
                }
                array[nonius] = flag;
                sort(0, nonius);
                sort(nonius + 1, numsize);
            }
        }
        sort(0, array.length);
        return array;
    },

    //名字规范  name为传入的名字， len为需要留住的字符长度， isNot多出的字符用...表示（true表示要）
    getNameLimit: function getNameLimit(name, len, isNot) {
        var str, maxNameLength;
        len ? maxNameLength = len : maxNameLength = G_Config_common.num_maxNameLen;
        var reg = /^[\u4E00-\u9FA5]+$/;
        var wordsNum = 0;
        var newStr = '';
        for (var i = 0; i < name.length; i++) {
            if (wordsNum >= maxNameLength) {
                if (isNot) newStr = newStr;else newStr += '...';
                break;
            }
            str = name[i];
            newStr += str;
            if (reg.test(str)) {
                wordsNum += 2;
            } else {
                wordsNum += 1;
            }
        }
        return newStr;
    },
    objectLog: function objectLog(object) {
        var str = '{';
        for (var attrName in object) {
            str += attrName + ':' + object[attrName] + ',';
        }
        str += '}';
    },
    //适配初始节点大小
    adaptSize: function adaptSize(curW, curH) {
        var designSize = cc.view.getDesignResolutionSize();
        var rateW = cc.visibleRect.width / designSize.width;
        var rateH = cc.visibleRect.height / designSize.height;
        return { width: curW * rateW, height: curH * rateH };
    },
    //适配初始节点位置
    adaptPos: function adaptPos(pos) {
        var designSize = cc.view.getDesignResolutionSize();
        var rateW = cc.visibleRect.width / designSize.width;
        var rateH = cc.visibleRect.height / designSize.height;
        return cc.p(pos.x * rateW, pos.y * rateH);
    }
};

window.G_TOOL = tools;

cc._RFpop();
},{}],"TypeConfig":[function(require,module,exports){
"use strict";
cc._RFpush(module, '84957MQ/nJJGIYd0VCQ2IhU', 'TypeConfig');
// Script\Common\Configs\TypeConfig.js

//游戏中各种类型信息的定义

var typeConfig = {

    webGameState: cc.Enum({
        pause: 1,
        running: 2
    }),

    gameModule: cc.Enum({
        platform: 0,
        bull100: 1,
        grab: 2,
        classic: 3
    }),

    //场景名称，用于切换场景
    sceneName: cc.Enum({
        platform: 'Platform',
        bull100: 'Poker_Bull100',
        grab: 'Poker_Grab',
        classic: 'Poker_classic',
        loadingScene: 'InHomeLoading'
    }),

    //http请求
    http_gameModule: cc.Enum({
        platform: 0,
        bull100: 1,
        classic: 2,
        grab: 3
    }),

    grabState: cc.Enum({
        wait: 0, //等待
        readyStart: 1, //
        givePoker: 2, //
        touchTables: 3, //
        showFlag: 4, //
        flyGold: 5, //
        readyTouchTable: 6, //
        isBetting: 7 }),

    //是否已下注
    //游戏流程的状态机制
    classicState: cc.Enum({
        wait: 0, //游戏等待开始
        readyStart: 1, //游戏准备开始（播放开始动画时候）
        givePoker: 2, //正在发牌
        grabDealer: 3, //正在抢庄
        setScore: 4, //正在下注
        calculate: 5, //算牌时间
        showResult: 6 }),

    //显示结果
    //百人大战进程
    bull100State: cc.Enum({
        wait: 0, //游戏等待时间
        startReady: 1, //游戏投注前的表现
        betting: 2, //游戏开始投注
        endEff: 3 }),

    //游戏结束后的结算表现
    //牌型的值
    pokerResult: cc.Enum({
        bull_0: 0, //没牛
        bull_1: 1,
        bull_2: 2,
        bull_3: 3,
        bull_4: 4,
        bull_5: 5,
        bull_6: 6,
        bull_7: 7,
        bull_8: 8,
        bull_9: 9,
        bull_10: 10, //牛牛
        bull_11: 11,
        bull_12: 12
    }),

    //卡牌的类型  大小排列为黑桃 红桃 草花 方块
    pokerType: cc.Enum({
        blackHeart: 1, //黑色桃心
        redHeart: 2, //红色桃心
        flower: 3, //梅花
        block: 4 }),

    //方块
    playerType: cc.Enum({
        system: 0,
        owner: 1,
        others: 2
    }),

    net_gameModule: cc.Enum({
        bull100: 'BULL_100',
        grab: 'BULL_BAO',
        classic: 'BULL_CLASSIC'
    }),
    net_gameModule2: cc.Enum({
        bull100: 'bull100',
        grab: 'bullBao',
        classic: 'bullclassic'
    }),

    //桌子大小类型
    tableType: cc.Enum({
        big: 1,
        min: 2
    }),

    //tip显示的类型
    tipType: cc.Enum({}),

    //接受服务端消息后的code类型
    serverCodeType: cc.Enum({
        success: '0',
        serverErr1: '00001', //服务器忙
        remoteLanding: '00005', //异地登陆
        goldNotEnough: '200002', //您剩余金币不足
        upDealerErr1: '200003', //您剩余金币不足,无法上庄
        matchNonExistent: '500001', //赛事不存在
        upDealerErr2: '600002', //未达到最低上庄金额
        upDealerErr3: '600013', //输入的上庄金额格式错误
        noBetting: '00003', //没有投注
        downDealer1: '600016', //您已在庄，下庄系统将帮您打完本局！
        downDealer2: '600017', //您已从上庄列表退出竞庄！
        downDealer3: '600012', //您已下庄!
        downDealer4: '600018', //您剩余金币不足,已下庄!
        downDealer5: '600019', //您当期已下庄!
        continueFail1: '200004', //庄家金币不足续庄
        continueFail2: '600008', //庄家被动续庄
        warnDealer1: '600007', //友情提示: 您的上庄资金不足80%，请及时续庄！
        warnDealer2: '600009',
        upDealerSuccess: '600010', //成为本局庄家
        continueFail3: '600014', //输入的续庄金额格式错误
        continueSuccess: '600011', //输入的续庄金额格式错误
        needLogin: '10001', //未登陆，请登陆
        betNotPermitCoin: '400004', //下分金额为非许可值
        betNotPermitBet: '400005', //非法下注类型
        matchNoEnd: '300003' }),

    //已经 开始的对局未结束
    //
    dealerNetType: cc.Enum({
        upDealer: 1, //上庄
        continueDealer: 2, //续庄
        getList: 3 }),

    //获取上庄列表
    //滚动层的类型
    scrollType: cc.Enum({
        horizontal: 1, //横向放置单元
        vertical: 2, //竖向放置单元
        bag: 3 }),

    //背包格式放置单元
    //公告的通知方式枚举
    //announcementType : cc.Enum({
    //    general : 'general',                                                                                                  //一般公告
    //    importance : 'importance',                                                                                           //重要公告
    //    personal : 'personal',                                                                                                //个人公告
    //    allAgent : 'allAgent',                                                                                                //全局代理商
    //    appointAgent : 'appointAgent',                                                                                       //指定代理商
    //    enable : 'enable',                                                                                                     //启用
    //    disable : 'disable',                                                                                                  //取消
    //}),

    //走势图，判定是什么牌型，如梅花
    pokerTypeStr: cc.Enum({
        spade: 'SPADE', //黑桃
        diamond: 'DIAMOND', //方片
        club: 'CLUB', //梅花
        heart: 'HEART' }),

    //红桃
    //座位的定义
    seatsIndex: cc.Enum({
        seat_1: 1,
        seat_2: 2,
        seat_3: 3,
        seat_4: 4,
        seat_5: 5,
        seat_6: 6,
        seat_7: 7,
        seat_8: 8
    })
};

window.G_TYPE = typeConfig;

cc._RFpop();
},{}]},{},["Config_bull100","Config_chinese","Config_classic","Config_common","Config_dialogUrl","Config_grab","Config_resourceUrl","NetConfig","Object_config","TypeConfig","Button_multiple","Mask_extend","DataObject_socketMsg","Global","AudioMgr","GameData","HTTPMgr","ReloadResMgr","SocketCenter","SocketMgr","TablesMgr","TipsMgr","Tools","M_Player","M_PokerConfig","AutoDealing","Obj_calculateResult","Obj_cell_trend","Obj_coinEffect","Obj_confirmTip","Obj_countDownProgress","Obj_dealScroll","Obj_dealerListCell","Obj_dealerListSlider","Obj_dealerWordEffect","Obj_dialogAccountCell","Obj_dialogContentCell","Obj_dialogPlayerListCell","Obj_dialogRecordCell","Obj_effect_inHomeLoading","Obj_goldResultEffect","Obj_goldsContainer","Obj_leftTopMenu","Obj_loadingEffect","Obj_notice","Obj_onPokerResult","Obj_othersGold","Obj_poker","Obj_rankItem_friend","Obj_roomListCell","Obj_scrollValue","Obj_seatsContainer","Obj_setTouchEffect","Obj_systemTip","Obj_topOneLayer","Obj_touchLimit","Obj_txtTip","Obj_uiContainer","Obj_winRankHead","BaseDialog","BaseManager","BaseUI","GameStart","Bull100_Manager","Bull100_btnContainer","Bull100_goldContainer","Bull100_playerBlock","Bull100_playerContainer","Bull100_pokerContainer","Bull100_pokerResultContainer","Bull100_showResult","Bull100_table","Bull100_tableContainer","Bull100_topEffect","Grab_topEffect","Grab_Manager","Grab_btnContainer","Grab_goldContainer","Grab_oneTable","Grab_playerBlock","Grab_playersLayer","Grab_pokerLayer","Grab_tablesLayer","Grab_topLayer","Platform_topEffect","Platform_Manager","Platform_bottomBtns","Platform_leftRank","Platform_topInfo","Platform_ui_enter","Classic_Manager","Classic_controlButton","Classic_goldArea","Classic_page_result","Classic_playerBlock","Classic_pokerGiveArea","Classic_topTouchLayer","Classic_topUI","Obj_backgroundEffect1","Obj_pageResult_effect","Obj_playerBlock_effect","Prefab_dialog_account","Prefab_dialog_announcement","Prefab_dialog_bull100Trend","Prefab_dialog_dealerList","Prefab_dialog_grabTrend","Prefab_dialog_playerList","Prefab_dialog_record","Prefab_dialog_rules","Prefab_dialog_rulesInHome","Prefab_dialog_set","Prefab_dialog_trend","Prefab_roomList"]);
