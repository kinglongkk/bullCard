//大厅场景管理

cc.Class({
    extends: require('BaseManager'),
    properties: {
        _dict_ui : null,                                                                                                //UI字典
        _richRankData : null,                                                                                          //财富排行榜的信息
        _ui_baseUrl: null,                                                                                               //ui整理的字典
        _roomComp: null,                                                                                                //房间脚本
        _enterRoomCallFunc : null,                                                                                     //进入游戏房间后的回调
        _isFirstEnter : null,                                                                                          //是否第一次进入游戏
        _reConnectNum : null,                                                                                          //重连次数

        comp_uiContainer : {
            'default' : null,
            type : require('Obj_uiContainer'),
            displayName : 'UI容器'
        },
        comp_enterBtn : {
            'default' : null,
            type : require('Platform_ui_enter'),
            displayName : '房间入口'
        },
        comp_leftRank : {
            'default' : null,
            type : require('Platform_leftRank'),
            displayName : '左边列表容器'
        },
        comp_bottomBtns : {
            'default' : null,
            type : require('Platform_bottomBtns'),
            displayName : '底部按钮容器'
        },
        comp_topInfo : {
            'default' : null,
            type : require('Platform_topInfo'),
            displayName : '大厅顶部信息'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super();
        this._ui_baseUrl = 'UI_prefabs/';
        GG.platformMgr = this;
        this._roomNode = false;
        this._isFirstEnter = false;
        this._reConnectNum = 0;
    },

    start : function () {
        this._initMainScene();

        if(G_Config_common.isLocal){
            var self = this;
            var id = setInterval(function () {
                if(GG.tipsMgr && !self.getAdIsLimit()) GG.tipsMgr.addNotice('这是一个很长的公告------------------');
            }, 3000)
        }
    },

    _initMainScene : function () {
        if(G_Config_common.isLocal){
            this.comp_leftRank.setRichData();
            var self = this;
            setTimeout(function () {
                self.showHomeList(false, true);
                // GG.audioMgr.playMusic();
            }, 100)
        }else{
            var self = this;
            this.comp_leftRank.setRichData();
            //设置玩家信息
            this.comp_topInfo.initShow();
            //是否直接跳转到房间列表
            setTimeout(function () {
                var dataObj = GG.getPlayer().getOutHomeData()
                if(dataObj && dataObj.gameType != G_TYPE.gameModule.platform) self.comp_enterBtn.showHomeListFirst(dataObj.gameType, true);
                else self.showHomeList(false, true);
            }, 100);
        }
    },

    //显示某个UI，传入G_RES_URL里面的UI名字
    openUI : function (uiName, callBack) {
        this.comp_uiContainer.addUI(uiName, callBack);
    },

    //控制房间列表的显隐
    showHomeList : function (isOut, isNoEffect) {
        this.comp_enterBtn.moveOutScene(isOut, isNoEffect);
        this.comp_leftRank.moveOutScene(isOut, isNoEffect);
        this.comp_bottomBtns.moveOutScene(isOut, isNoEffect);
        this.comp_topInfo.setHomeListShow(isOut, isNoEffect);
        if(this._roomComp){
            if(isOut) {
                this._roomComp.showLayer();
                GG.platformMgr.setAdLimit(true);
            } else {
                this._roomComp.hideLayer();
                GG.platformMgr.setAdLimit(false);
            }
        }
    },
    //显示房间列表信息
    setHomeListInfo : function (data) {
        this._roomComp.setDataList(data);
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

    showRoomUI : function (prefabName, callBack) {
        if (!this._roomComp) {
            var self = this,
                uiComp;
            G_DATA.getRoomListJson(function () {
                cc.loader.loadRes(self._ui_baseUrl + prefabName, function (err, prefab) {
                    if(err){

                    }else{
                        var uiNode = cc.instantiate(prefab);
                        uiNode.parent = self.comp_bottomBtns.node;
                        uiComp = uiNode.getComponent(prefabName);
                        self._roomComp = uiComp;
                    }
                    if(callBack){
                        callBack(uiComp);
                        callBack = null;
                    }
                });
            })
        } else {
            if(callBack){
                callBack(this._roomComp);
                callBack = null;
            }
        }
    },

    //释放房间列表资源
    releaseRoomUI : function () {
        var deps = cc.loader.getDependsRecursively(this._ui_baseUrl + 'Prefab_roomList');
        cc.loader.release(deps);
    },

    //进入游戏房间的请求
    requestEnterRoom : function (netData, callFunc) {
        this._enterRoomCallFunc = callFunc;
        GG.socketMgr.SendMsg(NetType.s_enterHouse, netData);
        GG.socketMgr.registerLong(NetType.r_enterHouseReturn, this.net_setHomeData.bind(this));
    },

    //接收到进房信息
    net_setHomeData : function (recvData) {
        if(!recvData) return;
        var tip = recvData.tip;
        if(tip.code != G_TYPE.serverCodeType.success) {
            // console.log(tip.tip);
            //进入房间失败-金币不足
            if (tip.code == G_TYPE.serverCodeType.goldNotEnough) {
                GG.tipsMgr.showConfirmTip_ONE(tip.tip, function () {
                    //显示房间列表
                    //是否直接跳转到房间列表
                    // this._isFirstEnter = false;
                    // this._oneRequestEnd();
                }.bind(this));
                return;
            }else{
                GG.tipsMgr.showConfirmTip_ONE(tip.tip, function () {
                    //进入房间失败
                    if(tip.code == G_TYPE.serverCodeType.matchNoEnd){
                        //已经开始的比赛未结束

                        //清理hash信息
                        // G_DATA.setCurSceneHash('',0);
                        // GG.exitHome();
                    }
                });
            }
            return;
        }
        var inHomeData = G_OBJ.data_inHomeData();
        inHomeData.net_inHomeData = recvData;
        GG.getPlayer().setInHomeData(inHomeData);
        if(this._enterRoomCallFunc){
            this._enterRoomCallFunc();
            this._enterRoomCallFunc = null;
        }
    },

    onDestroy : function () {
        this._super();
        GG.platformMgr = null;
        // this.releaseRoomUI();
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
