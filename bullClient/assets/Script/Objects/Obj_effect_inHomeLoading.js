//进入房间时候的加载动画


cc.Class({
    extends: require('BaseManager'),

    properties: {
        _maxProgress : null,                                                            //进度滚动的最大效果0-1
        _toSceneName : null,                                                            //准备跳转的场景名字

        _loadingNum : null,                                                             //正在加载的数量
        _reConnectNum : null,                                                           //重连次数
        label_tip : {
            default : null,
            type : cc.Label,
            displayName : '加载时候的提示信息'
        },
        label_rate : {
            default : null,
            type : cc.Label,
            displayName : '显示百分比'
        }
    },

    // use this for initialization
    onLoad: function () {
        this._super();

        this.node._firstPosY = this.node.y;
        this._node_sliderImg = this.node.children[0];
        this._node_sliderImg._firstPosY = this._node_sliderImg.y;

        this._firstMaxProgress = 0.9;
        var runTime = 2;
        this._curProgress = 0;
        this._loadingNum = 0;
        this._reConnectNum = 0;
        //每秒的进度
        this._progressInSec = this._firstMaxProgress/runTime;
        this._isStartEffect = false;
    },

    start : function () {
        //进入加载状态
        GG.setIsLoading(true);
        //显示进度信息
        this._showSliderEffect();
        //加载即将进入的场景
        this.preloadedScene();
    },

    _step_loadAudioConfig : function () {
        var self = this;
        if(!GG.audioMgr.getIsReloadGame()){
            this._loadingNum += 1;
            GG.audioMgr.reloadGameAudio(function () {
                self._oneCompleted();
            });
        }
    },

    //预加载场景
    preloadedScene : function () {
        var reloadInfo = GG.getPlayer().getReloadSceneInfo();
        if(reloadInfo && reloadInfo.sceneName){
            //基本配置已经加载完
            this._addReloadScene(reloadInfo.sceneName);
            //提示文本
            this._addTip();
        }else{
            //首次默认加载
            this._loadInitGameConfig();
        }
    },
    //有某个资源加载完成
    _oneCompleted : function () {
        this._loadingNum -= 1;
        if(this._loadingNum <= 0){
            //所有资源加载完成，且进度达到90%,则将进度特效播放到结束
            this._firstMaxProgress = 1;
            this._isStartEffect = true;
        }
    },

    _whenReloadEnd : function () {
        cc.sys.garbageCollect();
        //进度已经走完
        cc.director.loadScene(this._toSceneName);
        clearInterval(this.inervalId);
    },

    //=========================

    //加载初始配置
    _loadInitGameConfig : function () {
        var self = this;
        this._loadingNum += 1;
        var isFirst = GG.whenGameStart(function () {
            self._addConfigCallFunc();
            if(!G_Config_common.isLocal) {
                self._sendLogin();
                // self._doLogin();
            }else {
                self._toSceneName = G_TYPE.sceneName.platform;
            }

            //界面下方的提示文本
            self._addTip();
            self._oneCompleted();
        });
        if(!isFirst) {
            self._oneCompleted();
        }
    },

    //直接登录（测试）
    _doLogin : function () {
        console.log('发送登录请求')
        var username = 'mk06';
        var password = '1234567';
        var sendData = 'username='+username+'&password='+password;
        this._loadingNum += 1;
        GG.httpMgr.sendHttpRequest(G_DIALOG_URL.login+"?"+sendData, null, function (data) {
            console.log('接受到登录信息')
            console.log(data)
            // if(data){
            //
            // }
            this._sendLogin();
            this._oneCompleted();
        }.bind(this), 'GET');
    },

    //发送登陆确认请求
    _sendLogin : function () {
        var sendData = null;
        if(!GG.getPlayer().getPlayerToken()){
            this._loadingNum += 1;
            GG.httpMgr.sendHttpRequest(G_DIALOG_URL.loginTokenUrl, sendData, function (data) {
                if(data){
                    GG.getPlayer().setPlayerToken(data);
                    this._reConnectNum = 0;
                    this._loadingNum += 1;
                    var self = this;
                    //登陆socket
                    GG.socketMgr.connectSocket(function () {
                        self._whenLoginEnd();
                        self._oneCompleted();
                    });
                }else {
                    this._reConnectNum += 1;
                    if(this._reConnectNum > 6){
                        //重连次数太多
                        var self = this;
                        GG.showReconnect(function () {
                            self._reConnectNum = 0;
                            self._sendLogin();
                        });
                    }else this._sendLogin();
                }
                this._oneCompleted();
            }.bind(this));
        }else this._whenLoginEnd();
    },
    _whenLoginEnd : function () {
        this._step1_setPlayerInfo();
        this._step2_setAudioConfig();
        this._step3_setReloadScene();
    },
    //获取玩家信息并放置M_Player脚本中
    _step1_setPlayerInfo : function () {
        var sendData = null;
        if(!GG.getPlayer().getBaseInfo()){
            this._loadingNum += 1;
            GG.httpMgr.sendHttpRequest(G_DIALOG_URL.personalInfoUrl, sendData, function (data) {
                GG.getPlayer().setBaseInfo(data);
                this._oneCompleted();
            }.bind(this));
        }
    },
    //请求服务器声音的设置
    _step2_setAudioConfig : function () {
        if(!GG.audioMgr.getAudioConfig()){
            this._loadingNum += 1;
            //GG.httpMgr.sendHttpRequest(G_DIALOG_URL.loginToSetUrl, sendData, function (data) {
            //    var config = data[0];
            //    if(!data || data.length < 1){
            //        config = {'music':true,'sound':true};
            //    }
            //    GG.audioMgr.setAudioConfig(config);
            //    this._oneRequestEnd();
            //}.bind(this));
            GG.httpMgr.sendHttpRequest(G_DIALOG_URL.getSystemSetUrl, 'gameType=DOU_NIU', function (data) {
                var config;
                if(data){
                    if(data.code == 0){
                        config = {'music':true,'sound':true};
                    } else {
                        config = data.data.result[0];
                    }
                }else config = {'music':true,'sound':true};

                //if(config.music && GG.audioMgr.getIsPlayMusic()) GG.audioMgr.saveSoundConfig(config.sound);
                GG.audioMgr.setAudioConfig(config);
                this._oneCompleted();
            }.bind(this));
        }
    },
    //登陆后，加载即将进入的场景
    _step3_setReloadScene : function () {
        var self = this;
        //刚登陆，会根据hash来设置初始页面
        var hashData = G_DATA.getLastSceneHash();
        if(hashData){
            var netData = {
                roomId : hashData.roomId,
                gameModel : G_DATA.getEnterHomeModel(hashData.enterType)
            }
            self._loadingNum += 1;
            GG.s_requestMgr.send_inHome(netData, function (recvData) {
                // GG.changeScene(G_DATA.getSceneName(hashData.enterType));
                if(recvData){
                    //记录hash信息
                    G_DATA.setCurSceneHash(hashData.enterType,hashData.roomId);
                    self._addReloadScene(G_DATA.getSceneName(hashData.enterType));
                }else{
                    //进房请求失败,回到主场景
                    G_DATA.setCurSceneHash('',0);
                    self._addReloadScene(G_TYPE.sceneName.platform);
                }
                self._oneCompleted();
            })
        }else{
            //默认的场景
            G_DATA.setCurSceneHash('',0);
            this._addReloadScene(G_TYPE.sceneName.platform);
        }
    },
    _addReloadScene : function (sceneName) {
        this._toSceneName = sceneName;
        this._loadingNum += 1;
        var self = this;
        cc.director.preloadScene(this._toSceneName, function () {
            self._oneCompleted();
        });
        if(sceneName != G_TYPE.sceneName.platform){
            //加载游戏房间内声音配置
            this._step_loadAudioConfig();
        }
    },

    //加载配置文件结束
    _addConfigCallFunc : function () {
        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.grabConfig);
        if(table) {
            var dataObj = table.getFirstData();
            this._addConfig(G_Config_grab, dataObj);
        }

        var table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonConfig);
        if(table) {
            var dataObj = table.getFirstData();
            this._addConfig(G_Config_common, dataObj);
        }
    },
    //增加配置信息
    _addConfig : function (targetObject, addObject) {
        if(addObject){
            for(var attrName in addObject){
                targetObject[attrName] = addObject[attrName];
            }
        }
    },

    //================================进度条的表现

    //开始进度条的显示，直接播放到90%等待
    _showSliderEffect : function () {
        this._loadingNum += 1;
        this._curProgress = 0;
        this._setSliderValue(this._curProgress);
        this._isStartEffect = true;
    },
    //设置显示进度 0-1
    _setSliderValue : function (rate) {
        this._setRateInfo(rate);
        var len = rate * this.node.height;
        var needH = this.node.height - len;
        this.node.y = -needH;
        this._node_sliderImg.y = needH;
    },
    //设置提示信息
    _setTipInfo : function (str) {
        if(this.label_tip){
            this.label_tip.string = str;
        }
    },
    //设置百分比信息
    _setRateInfo : function (rate) {
        var str = Math.floor(rate * 100) + '%';
        if(this.label_rate){
            this.label_rate.string = str;
        }
    },

    // called every frame, uncomment this function to activate update callback
    update: function (dt) {
        if(this._isStartEffect){
            this._playerSliderEffect(dt);
        }
    },
    //播放进度动画到90%
    _playerSliderEffect : function (dt) {
        if(this._curProgress >= this._firstMaxProgress) {
            //达到第一阶段，如90%
            this._isStartEffect = false;
            this._oneCompleted();
            return;
        }
        this._curProgress += dt * this._progressInSec;
        this._setSliderValue(this._curProgress);
        if(this._curProgress >= 1){
            this._curProgress = 1;
            this._setSliderValue(this._curProgress);
            this._isStartEffect = false;

            this._whenReloadEnd();
        }
    },

    //==========================显示提示文本

    //界面下方的提示文本
    _addTip : function () {
        this._table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonLoading);
        if (!this._table) {
            this._setLoadingTable();
        } else {
            this._showTableTime();
        }
    },

    //设置文本表格
    _setLoadingTable: function () {
        GG.tableMgr.reloadTables(G_RES_URL.dict_tablesName.commonLoading, this._getLoadingTable.bind(this));
    },
    //获取文本表格
    _getLoadingTable: function () {
        this._table = GG.tableMgr.getTable(G_RES_URL.dict_tablesName.commonLoading);
        if(this._table) {
            var loadMessage = this._table.getFirstData().content;
            this._setTipInfo(loadMessage);
            this._showTableTime();
        }
    },
    _getRandomTable: function () {
        var index = G_TOOL.getRandomArea(1,7);
        var loadMessage = this._table.getDataByID(index).content;
        return loadMessage;
    },
    //定时显示
    _showTableTime: function () {
        var self = this;
        this.inervalId = setInterval(function () {
            self._setTipInfo(self._getRandomTable());
        }, 2000);
    },

    onDestroy : function () {

    },
});
