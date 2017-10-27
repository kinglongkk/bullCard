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
        var dataList;
        if(G_Config_common.isLocal) {
            dataList = [];
            for(var i = 0; i < 5; i ++){
                var curList = [['SPADE',1], ['DIAMOND',0], ['CLUB',1], ['HEART',0]];
                dataList.push(curList);
            }
            this._showList(dataList);
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
                    this.showLayer();
                    var needList = [];
                    var mathList;
                    var dataList = data.data;
                    if(!dataList) {
                        data.msg
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
                }
            }.bind(this));
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

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
