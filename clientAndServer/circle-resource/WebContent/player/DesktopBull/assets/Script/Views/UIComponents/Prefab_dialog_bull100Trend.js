//百人走势图


cc.Class({
    extends: require('BaseDialog'),

    properties: {

    },

    // use this for initialization
    onLoad: function () {
        this._super('UI_trend');
        this._setScrollData();
    },

    _setScrollData : function () {
        this._itemCompName = 'Obj_cell_trend';
        var dataObj = {
            itemPrefab : this.prefab_item,
            scrollType : G_TYPE.scrollType.vertical,
        }
        this.comp_itemContainer.setData(dataObj);
    },

    setData : function (homeID) {
        if(G_Config_common.isLocal) {
            var netData = this._getLocalData();
            console.log(netData)
            this._setNetData(netData);
        } else{
            var moduleType;
            switch (GG.getGameType()){
                case G_TYPE.gameModule.bull100:
                    moduleType = G_TYPE.http_gameModule.bull100;
                    break
                case G_TYPE.gameModule.grab:
                    moduleType = G_TYPE.http_gameModule.grab;
                    break
                case G_TYPE.gameModule.classic:
                    moduleType = G_TYPE.http_gameModule.classic;
                    break
                default:
                    moduleType = 1;
                    break
            }
            var sendData = 'gameModelId='+moduleType+'&gameRoomId='+homeID;
            GG.httpMgr.sendHttpRequest(G_DIALOG_URL.trendUrl, sendData, function (data) {
                if(data){
                    this._setNetData(data);
                }
            }.bind(this));
        }
    },

    _setNetData : function (data) {
        this.showLayer();
        var needList = [];
        var mathList;
        var dataList = data.data;
        if(!dataList) {
            //没有数据
            // data.msg
        }else{
            for(var i = 0; i < dataList.length; i ++){
                if(!needList[i]) needList[i] = [];
                mathList = dataList[i].matchResult;
                if(mathList){
                    for(var j = 0; j < mathList.length; j ++){
                        needList[i].push([mathList[j]['itemType'], mathList[j]['outcome']]);
                    }
                }
            }
            this._showList(needList);
        }
    },

    _showList : function (dataList) {
        var data, item, comp;//走势图显示条目为5
        for(var i = 0; i < G_Config_common.showTrendItems; i ++){
            data = dataList[i]
            if(data){
                item = this.comp_itemContainer.getItemByIndex(i);
                if(item){
                    comp = item.getComponent(this._itemCompName);
                    comp.setData(data);
                }
            }
        }
        this.comp_itemContainer.clearItems(i);
    },

    hideLayer : function () {
        this._super();
    },

    _getLocalData : function () {
        var netData = {};
        netData.data = G_TOOL.getRandomBool() ? null : [];
        if(netData.data){
            var showNum = cc.random0To1() * 10;
            for(var i = 0; i < showNum; i ++){
                var info = {
                    matchResult : this._getMatchResult()
            }
                netData.data.push(info);
            }
            netData.msg = '';
        }else netData.msg = 'request error'
        return netData
    },

    _getMatchResult : function () {
        var needList = [];
        var typeList = ['SPADE','DIAMOND','CLUB','HEART']
        for(var i = 0; i < G_TOOL.getRandomArea(0, 4); i ++){
            var info = {
                'itemType':typeList[G_TOOL.getRandomArea(0,3)],
                'outcome':G_TOOL.getRandomBool()?1:0,
            }
            needList.push(info)
        }
        return needList
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
