//房间列表条目
cc.Class({
    extends: require('BaseUI'),

    properties: {
        _enterType : null,                                                                                                 //进入房间的类型
        _homeID : null,                                                                                                     //房间ID

        node_backgroundBtn : {
            default : null,
            type : cc.Node,
            displayName : '进入房间'
        },
        label_intoLimit : {
            default : null,
            type : cc.Label,
            displayName : '进房限制'
        },
        label_onNumber : {
            default : null,
            type : cc.Label,
            displayName : '在房人数'
        },
        node_roomBG: {
            default : null,
            type : cc.Node,
            displayName : '房间背景'
        },
        node_roomName: {
            default : null,
            type : cc.Node,
            displayName : '房间名称'
        },
        frame_roomTypeUI : {
            default : null,
            type :cc.SpriteAtlas,
            displayName : '房间背景基础显示图片集'
        },
    },

    // use this for initialization
    onLoad: function () {
        this.registerButton(this.node_roomBG, this.onClick_into, this);
    },

    setData : function (data, jsonData) {
        if(!data) return;
        this._enterType = data.enterType;
        this._homeID = data.id;

        this._setEnterIcon(this.node_roomName, jsonData.txtImgUrl);
        this._setEnterIcon(this.node_roomBG, jsonData.bgImgUrl);
        this.label_intoLimit.string = G_CHINESE.inToRoomLimit + G_TOOL.changeMoney(data.minLimitPlayerBlance);
        this.label_onNumber.string = data.currentPlayerCount;
    },
    //点击进入游戏
    onClick_into : function (event) {
        if(G_Config_common.isLocal){
            this._doEnter();
        }else{
            var netData = {
                roomId : this._homeID,
                gameModel : G_DATA.getEnterHomeModel(this._enterType)
            }
            GG.platformMgr.requestEnterRoom(netData, this._doEnter.bind(this));
        }
    },

    _doEnter : function () {
        //记录hash信息
        G_DATA.setCurSceneHash(this._enterType,this._homeID);
        GG.changeScene(G_DATA.getSceneName(this._enterType));
    },

    //设置图片（入房区域背景，房间名）
    _setEnterIcon : function (curNode, imgUrl) {
        var imgName = this._getIconName(imgUrl);
        if(imgName){
            var frame = this.frame_roomTypeUI.getSpriteFrame(imgName);
            if(frame){
                //已经缓存
                curNode.getComponent(cc.Sprite).spriteFrame = frame;
            }else{
                //没有则根据url去请求
                G_TOOL.setHeadImg(curNode, imgUrl);
            }
        }else{
            //该url异常

        }
    },
    //获取图片名
    _getIconName: function (url) {
        if (!url) return null;
        var urlList = url.split('/');
        var iconName = urlList[urlList.length - 1].split('.')[0];
        return iconName;
    },
    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
