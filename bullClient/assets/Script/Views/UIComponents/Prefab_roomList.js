//存放所有的房间列表
cc.Class({
    extends: require('BaseUI'),

    properties: {
        _dict_loadScene : null,                                                                                         //是否已经预加载需要的场景
        _loadingScene : null,

        prefab_item : {
            default : null,
            type : cc.Prefab,
            displayName : '房间预制体'
        },
        comp_itemContainer : {
            default : null,
            type : require('Obj_dealScroll'),
            displayName : '房间列表容器'
        },
        node_roomName : {
            default : null,
            type : cc.Node,
            displayName : '房间列表名称'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._dealerItemCompName = 'Obj_roomListCell';
        this._dict_loadScene = {};
        //设置滚动层信息
        var dataObj = {
            itemPrefab : this.prefab_item,
            lineNum : 3,
            scrollType : G_TYPE.scrollType.bag,
        }
        this.comp_itemContainer.setData(

        );
    },

    setDataList : function (dataObj) {
        if(!dataObj) return;
        // this.showLayer();
        G_DATA.getRoomListJson(function (jsonConfig) {
            if(jsonConfig){
                this.setRoomList(dataObj.data, dataObj.enterType, jsonConfig);
            }else{
                if(G_Config_common.isLocal){
                    jsonConfig = this._getLocalJson();
                    this.setRoomList(dataObj.data, dataObj.enterType, jsonConfig);
                }else{
                    //无法正常加载房间配置
                }
            }
        }.bind(this))
        this._reloadScene();
    },

    setRoomList : function (dataList, enterType, jsonConfig) {
        this._showRoomName(enterType);
        var i = 0, configPageNum = 6, rowNum;
        if(dataList) {
            dataList = this._resortDataList(dataList);
            var item, comp, dataObj;
            for(i = 0; i < dataList.length; i ++){
                item = this.comp_itemContainer.getItemByIndex(i);
                if(item){
                    comp = item.getComponent(this._dealerItemCompName);
                    dataObj = dataList[i];
                    dataObj.enterType = enterType;
                    // comp.setData(dataObj, jsonConfig[dataObj.id]);
                    rowNum =(i+1)%configPageNum;
                    comp.setData(dataObj, jsonConfig[rowNum == 0 ? configPageNum : rowNum]);
                }
            }
        }
        this.comp_itemContainer.clearItems(i -1);
        this.comp_itemContainer.scrollToUp();
    },

    // _resortDataList : function (dataList) {
    //     var newList = [];
    //     while (dataList.length > 0){
    //         newList = newList.concat(this._getOneTypeList(dataList));
    //     }
    //     return newList;
    // },
    //
    // _getOneTypeList : function (dataList) {
    //     var copyValue = null, dataObj, newList = [];
    //     for(var i = dataList.length-1; i > -1; i --){
    //         dataObj = dataList[i];
    //         if(copyValue !== dataObj.minLimitPlayerBlance){
    //             copyValue = dataObj.minLimitPlayerBlance
    //             var targetNum = dataList.splice(i, 1)[0];
    //             newList.splice(0,0, targetNum);
    //         }
    //     }
    //     return newList
    // },

    _resortDataList : function (dataList) {
        var dict = {};
        var dataObj;
        for(var i = dataList.length-1; i > -1; i --){
            dataObj = dataList[i];
            if(!dict[dataObj.minLimitPlayerBlance]){
                dict[dataObj.minLimitPlayerBlance] = []
            }
            this._doBalancePush(dict[dataObj.minLimitPlayerBlance], dataObj);
        }

        var newList = [];
        var keyList = Object.keys(dict);
        keyList.sort (function (first, second){
            var f = parseInt (first, 10), s = parseInt (second, 10);
            if (f < s) {
                return -1;
            } else if (f > s) {
                return 1;
            } else {
                return 0;
            }
        });
        var roomDataList;
        while (this._isHaveChild(dict)){
            for(var i = 0; i < keyList.length; i ++){
                roomDataList = dict[keyList[i]];
                if(!roomDataList) continue;
                if(roomDataList.length < 1){
                    delete dict[keyList[i]]
                }else{
                    var target = roomDataList.splice(0,1)[0];
                    if(target) newList.push(target)
                }
            }
        }

        return newList
    },

    _isHaveChild : function (jsonObj) {
        for(var key in jsonObj){
            if(jsonObj[key]){
                return true
            }
        }
        return false
    },

    _doBalancePush : function (list, dataObj) {
        var firstLen = list.length;
        for(var i = 0; i < firstLen; i ++){
            if(dataObj.id < list[i].id){
                list.splice(i,0,dataObj);
                break;
            }
        }
        if(firstLen == list.length){
            //没有增加
            list.push(dataObj)
        }
    },

    //预加载场景
    _reloadScene : function () {
        if(!this._loadingScene){
            var self = this;
            var sceneName = G_TYPE.sceneName.loadingScene;
            cc.director.preloadScene(sceneName, function () {
                self._loadingScene = true;
            });
        }
    },

    //=====================================显示房间名

    _showRoomName : function (sceneType) {
        var grabNode = this.node_roomName.getChildByName('grab');
        var bullNode = this.node_roomName.getChildByName('bull100');
        if(!grabNode || !bullNode) return;
        switch (parseInt(sceneType)){
            case G_TYPE.gameModule.bull100:
                bullNode.active = true;
                grabNode.active = false;
                break
            case G_TYPE.gameModule.classic:

                break
            case G_TYPE.gameModule.grab:
                var grabNode = this.node_roomName.getChildByName('grab');
                grabNode.active = true;
                bullNode.active = false;
                break
            default:
                break
        }
    },

    _getLocalJson : function () {
        return {
            "1": {
                "txtImgUrl": "images/room/name/platform_room_n_7.png",
                "bgImgUrl": "images/room/bg/platform_room_b_1000.png"
            },
            "2": {
                "txtImgUrl": "images/room/name/platform_room_n_8.png",
                "bgImgUrl": "images/room/bg/platform_room_b_2000.png"
            },
            "3": {
                "txtImgUrl": "images/room/name/platform_room_n_11.png",
                "bgImgUrl": "images/room/bg/platform_room_b_20000.png"
            },
            "4": {
                "txtImgUrl": "images/room/name/platform_room_n_9.png",
                "bgImgUrl": "images/room/bg/platform_room_b_5000.png"
            },
            "5": {
                "txtImgUrl": "images/room/name/platform_room_n_12.png",
                "bgImgUrl": "images/room/bg/platform_room_b_50000.png"
            },
            "6": {
                "txtImgUrl": "images/room/name/platform_room_n_10.png",
                "bgImgUrl": "images/room/bg/platform_room_b_10000.png"
            },
            "7": {
                "txtImgUrl": "images/room/name/platform_room_n_7.png",
                "bgImgUrl": "images/room/bg/platform_room_b_1000.png"
            },
        }
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
