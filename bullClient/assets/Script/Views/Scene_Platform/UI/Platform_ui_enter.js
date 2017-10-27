//显示所有入口动画的UI

cc.Class({
    extends: require('BaseUI'),
    properties: {
        _roomPrefabName: null,                                                                                          //房间节点名称
        _isTouchLimit : null,                                                                                          //是否有点击限制

        node_bull100 : {
            default : null,
            type : cc.Node,
            displayName : '百人大战'
        },
        node_grab : {
            default : null,
            type : cc.Node,
            displayName : '押宝大战'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._registerButton(this.node_bull100, this._btn_clickEnter, this, G_TYPE.gameModule.bull100);
        this._registerButton(this.node_grab, this._btn_clickEnter, this, G_TYPE.gameModule.grab);
        this._roomPrefabName = 'Prefab_roomList';
    },

    _btn_clickEnter : function (target, enterType) {
        if(this._isTouchLimit) return;
        this.showHomeList(enterType);
    },
    // _showTip : function (content) {
    //     if(!this._tip){
    //         var cNode = new cc.Node();
    //         this._tip = cNode.addComponent(cc.Label);
    //         cNode.parent = this.node;
    //         cNode.color = cc.Color.RED;
    //     }
    //     this._tip.string = content;
    // },

    showHomeList : function (enterType, isNoEffect) {
        var self = this;
        GG.topTouchLayer.showNetRequest();
        GG.platformMgr.showRoomUI(self._roomPrefabName, function (roomComp) {
            if (roomComp) {
                if(G_Config_common.isLocal){
                    GG.topTouchLayer.closeNetRequest();
                    var netData = self._getLocalData(enterType);
                    netData.enterType = enterType;
                    GG.platformMgr.showHomeList(true, isNoEffect);
                    GG.platformMgr.setHomeListInfo(netData);
                }else{
                    self._sendRoomListRequest(enterType, isNoEffect);
                }
            }
        });
    },

    //请求房间列表参数
    _sendRoomListRequest : function (enterType, isNoEffect) {
        var gameModel;
        switch (enterType) {
            case G_TYPE.gameModule.bull100:
                gameModel = G_TYPE.net_gameModule2.bull100;
                break;
            case G_TYPE.gameModule.grab:
                gameModel = G_TYPE.net_gameModule2.grab;
                break;
            case G_TYPE.gameModule.classic:
                gameModel = G_TYPE.net_gameModule2.classic;
            default:
                break;
        }
        GG.httpMgr.sendHttpRequest(G_DIALOG_URL.gameRoomListUrl, 'gameModel=' +  gameModel, function (data) {
            if(data){
                data.enterType = enterType;
                GG.platformMgr.showHomeList(true, isNoEffect);
                GG.platformMgr.setHomeListInfo(data);
            }
        }.bind(this));
    },

    _getLocalData : function (enterType) {
        var netData = {};
        netData.enterType = enterType;
        netData.data = [];
        var interval = 2;
        for(var i = 0; i < 6; i ++){
            var roomInfo = {
                id : i+1,   //房间ID
                minLimitPlayerBlance : 1000,   //最大人数上限
                currentPlayerCount : i * 50, //当前玩家数量
            }
            var rowNum = Math.ceil(roomInfo.id/interval);
            roomInfo.minLimitPlayerBlance += rowNum * 1000;
            netData.data.push(roomInfo);
        }
        return netData
    },

    //==================================

    //从游戏房间返回大厅，需要先显示房间列表
    showHomeListFirst : function (enterType, isNoEffect) {
        // GG.platformMgr.showHomeList(true, isNoEffect);
        this.showHomeList(enterType, isNoEffect);
    },
    _registerButton : function (node, callBack, target, userData, isNoScale) {
        //if(!this._list_registerBtn) this._list_registerBtn = [];
        var self = this;
        node._isTouchEnabledEx = true;
        //this._list_registerBtn.push([node, callBack, target]);
        node.on(cc.Node.EventType.TOUCH_START, function (event) {
            //if(event.target._isTouchEnabledEx) event.target.scale = G_Config_classic.scale_buttonTouch;
            var aniNode = event.target.children[0];
            if(event.target._isTouchEnabledEx && !isNoScale) {
                self._setAniPause(aniNode, true);
                event.target.scale = 1.1;
                GG.audioMgr.playSound(17);
            }
        }, target);
        node.on(cc.Node.EventType.TOUCH_END, function (event) {
            var aniNode = event.target.children[0];
            event.target.scale = 1;
            self._setAniPause(aniNode, false);
            if(event.target._isTouchEnabledEx) callBack.call(target, event, userData);
        }, target);
        node.on(cc.Node.EventType.TOUCH_CANCEL, function (event) {
            var aniNode = event.target.children[0];
            self._setAniPause(aniNode, false);
            event.target.scale = 1;
        }, target);
    },

    _setAniPause : function (node, isPause) {
        var comp = node.getComponent(dragonBones.ArmatureDisplay);
        if(isPause){
            comp._lastTimeScale = comp.timeScale;
            comp.timeScale = 0.01;
        }else{
            if(comp._lastTimeScale) comp.timeScale = comp._lastTimeScale;
        }
    },
    _resumeAni : function (node) {

    },

    //移出屏幕外
    moveOutScene : function (isOut, isNoEffect) {
        if(!this._firstPosX) this._firstPosX = this.node.x;
        var designSize = cc.sys.isMobile ? cc.view.getDesignResolutionSize() : cc.visibleRect;
        if (isNoEffect) {
            this.node.x = isOut ? this._firstPosX : designSize.width*2-this._firstPosX;
        }else{
            this._isTouchLimit = true;
            this.node.active = true;
            var time = 0.5;
            this.node.stopAllActions();
            var targetPos = cc.p(designSize.width*2-this._firstPosX, this.node.y);
            if(isOut) targetPos.x = this._firstPosX
            var self = this;
            this.node.runAction(cc.sequence(cc.moveTo(time, targetPos), cc.callFunc(function () {
                self._isTouchLimit = false;
                self.setFrameHide(isOut);
            })));
        }
    },

    setFrameHide : function (isOut) {
        this.node.active = !isOut;
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
