//玩家列表的界面


cc.Class({
    extends: require('BaseDialog'),

    properties: {
        label_null : {
            default : null,
            type : cc.Label,
            displayName : '无数据时显示'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._super('UI_playerList');

        this._dealerItemCompName = 'Obj_dialogPlayerListCell';
        var dataObj = {
            itemPrefab : this.prefab_item,
            lineNum : 3,
            scrollType : G_TYPE.scrollType.bag,
        }
        this.comp_itemContainer.setData(dataObj);

        this.comp_scroll = this.comp_itemContainer.getScrollView();
        this.comp_scroll.node.on('scroll-to-bottom', this._scrollToBottom, this);
    },

    setData: function() {
        if(G_Config_common.isLocal){
            var netData = this._getLocalData();
            this._setNetData(netData);
        }else {

            this.comp_itemContainer.clearItems(- 1);
            GG.httpMgr.sendHttpRequest(G_DIALOG_URL.playerListUrl, null, function (data) {
                if(data){
                    this._setNetData(data);
                }
            }.bind(this));
        }
    },

    _setNetData : function (data) {
        this.showLayer();
        var dataList = data.data,
            str;
        if (data.code == '0') {
            str = data.msg;
        } else {
            str = '';
            this._netDataList = dataList;
            this._showListByTouch();
        }
        this._setNull(str);
    },

    _showListByTouch : function () {
        var onceNum = 15;
        var onceList = this._netDataList.splice(0, onceNum);
        if(!onceList || onceList.length < 1) return;
        var oneData, item, comp, showNum = this.comp_itemContainer.getShowItemNum();
        for(var i = 0; i < onceNum; i ++){
            oneData = onceList[i];
            if(oneData){
                item = this.comp_itemContainer.getItemByIndex(showNum+i);
                if(item){
                    comp = item.getComponent(this._dealerItemCompName);
                    comp.setData(oneData);
                }
            }
        }
        this.comp_itemContainer.clearItems(showNum + i - 1);
    },

    _scrollToBottom : function () {
        this._showListByTouch();
    },

    _setNull : function (msg) {
        this.label_null.string = msg;
    },
    hideLayer : function () {
        this._super();
    },

    _getLocalData : function () {
        var netData = {};
        netData.data = [];
        netData.code = G_TOOL.getRandomBool() ? 0 : 1;
        if(netData.code == 1){
            // var showNum = Math.floor(cc.random0To1() * 10);
            var showNum = 47;
            for(var i = 0; i < showNum; i ++){
                var playerInfo = {
                    avatarUrl : '',
                    nickname : 'name_'+i,
                    coin : -3 + Math.floor(cc.random0To1()*10),
                }
                netData.data.push(playerInfo);
            }
            console.log(netData.data)
        }
        netData.msg = 'request error'
        return netData
    },

    // called every frame, uncomment this function to activate update callback
    // update: function (dt) {

    // },
});
