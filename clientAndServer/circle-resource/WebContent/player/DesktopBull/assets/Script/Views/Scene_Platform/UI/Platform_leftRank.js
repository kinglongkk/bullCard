//游戏大厅玩家排行榜
var rankType = {
    type1 : 1,
    type2 : 2
}
cc.Class({
    extends: require('BaseUI'),

    properties: {
        _list_idleFriends : null,                                                                                   //没有显示出来的好友
        _curRankType : null,                                                                                         //当前选中的类型
        _gainsRankData : null,                                                                                       //盈利排行榜的信息
        _firstShowNum : null,                                                                                        //一开始显示的条目数量
        _list_richData : null,                                                                                       //富豪榜信息
        _list_gainsData : null,                                                                                      //盈利榜信息

        _isRichDataFull : null,                                                                                      //是否已经请求到所有的数据
        _isGainsDataFull : null,                                                                                     //是否已经请求到所有的数据
        sprite_listGainOn : {
            default : null,
            type : cc.Sprite,
            displayName : '盈利榜图标'
        },
        sprite_listRichOn : {
            default : null,
            type : cc.Sprite,
            displayName : '富豪榜图标'
        },
        sprite_listRankingBG : {
            default : null,
            type : cc.Sprite,
            displayName : '高亮显示条'
        },
        node_listGainBtn : {
            default : null,
            type : cc.Node,
            displayName : '盈利榜按钮'
        },
        node_listRichBtn : {
            default : null,
            type : cc.Node,
            displayName : '富豪榜按钮'
        },
        comp_itemContainer : {
            default : null,
            type : require('Obj_dealScroll'),
            displayName : '好友排行榜容器'
        },
        prefab_friendItem : {
            default : null,
            type : cc.Prefab,
            displayName : '好友条目'
        },
        label_null : {
            default : null,
            type : cc.Label,
            displayName : '无数据时显示'
        },
        comp_scroll : {
            default : null,
            type : cc.ScrollView,
            displayName : '滚动的容器'
        },
    },

    // use this for initialization
    onLoad: function () {
        this._itemCompName = 'Obj_rankItem_friend';
        this._firstShowNum = 7;
        this._isRichDataFull = false;
        this._isGainsDataFull = false;
        this.registerButton(this.node_listGainBtn, this.onClick_tabBtn, this, rankType.type1, true);
        this.registerButton(this.node_listRichBtn, this.onClick_tabBtn, this, rankType.type2, true);
        this.comp_scroll.node.on('scroll-to-bottom', this._scrollToBottom, this);
        //初始化滚动层1
        var dataObj = {
            itemPrefab : this.prefab_friendItem,
        }
        this.comp_itemContainer.setData(dataObj);
    },

    start : function () {
        //默认按钮的显示
        // this._curRankType = rankType.type2;
        // this._setRankTitle(this._curRankType);
    },

    //点击切换富豪榜
    onClick_tabBtn : function (event, type) {
        if(this._curRankType != type){

            switch (type){
                case rankType.type1:
                    this.setGainData(this._gainsRankData);
                    break;
                case rankType.type2:
                    this.setRichData(this._richRankData);
                    break;
                default:
                    break;
            }
        }
    },
    //设置按钮显示
    _setRankTitle : function (type) {
        this._curRankType = type;
        var isLeft = false, posX;
        switch (type){
            case rankType.type1://盈利榜
                isLeft = true;
                posX = this.node_listGainBtn.x;
                break;
            case rankType.type2://富豪榜
                posX = this.node_listRichBtn.x;
                break;
        };
        this.sprite_listRankingBG.node.x = posX;
        this.sprite_listGainOn.node.active = isLeft;
        this.sprite_listRichOn.node.active = !isLeft;
    },

    //设置盈利榜数据
    setGainData : function(dataObj){
        if(dataObj){
            this._setRankTitle(rankType.type1);
            this._setNull('');
            this._list_gainsData = dataObj.data.concat([]);

            var infoList = this.comp_itemContainer.getNextPageList(this._list_gainsData, this._firstShowNum);
            this._updateBottom(true, infoList, true);
        }else{
            dataObj = G_DATA.getGainsData();
            if(dataObj){
                this._gainsRankData  = dataObj;
                this.setGainData(this._gainsRankData);
            }else{
                this._setGainsPageData(1);
            }
        }
    },

    //设置富豪榜数据
    setRichData: function(dataObj) {
        if(dataObj){
            this._setRankTitle(rankType.type2);
            this._setNull('');
            this._list_richData = dataObj.data.concat([]);

            var infoList = this.comp_itemContainer.getNextPageList(this._list_richData, this._firstShowNum);
            this._updateBottom(false, infoList, true);
        }else{
            dataObj = G_DATA.getRichData();
            if(dataObj){
                this._richRankData = dataObj;
                this.setRichData(this._richRankData);
            }else{
                this._setRichPageData(1);
            }
        }
    },

    _setGainsPageData : function (pageNo, showNum) {
        if(showNum === undefined) showNum = this._firstShowNum;
        var sendData = 'gameId='+GG.getPlayer().getGameID()+"&pageSize="+showNum+"&pageNo="+pageNo;
        GG.httpMgr.sendHttpRequest(G_DIALOG_URL.playerProfitUrl, sendData, function (netData) {
            this._whenRequestDone(pageNo, rankType.type1, netData);
        }.bind(this));
        GG.httpMgr.addLongRequestWait(G_DIALOG_URL.playerProfitUrl, sendData, function (netData) {
            this._whenRequestDone(pageNo, rankType.type1, netData);
        }.bind(this));
    },
    _setRichPageData : function (pageNo, showNum) {
        if(showNum === undefined) showNum = this._firstShowNum;
        var sendData = "pageSize="+showNum+"&pageNo="+pageNo;
        GG.httpMgr.sendHttpRequest(G_DIALOG_URL.playerTreasuresUrl, sendData, function (netData) {
            this._whenRequestDone(pageNo, rankType.type2, netData);
        }.bind(this));
        GG.httpMgr.addLongRequestWait(G_DIALOG_URL.playerTreasuresUrl, sendData, function (netData) {
            this._whenRequestDone(pageNo, rankType.type2, netData);
        }.bind(this));
    },

    _whenRequestDone : function (pageNo, targetType, netData) {
        if(pageNo == 1){
            //是请求第一页
            if(netData){
                if(!netData.data || netData.data.length < 1 || netData.code == '0'){
                    this.comp_itemContainer.clearItems(-1);
                    this._setNull(netData.msg);
                    this._setRankTitle(targetType);
                }else{
                    if(targetType == rankType.type2){
                        //富豪排行榜
                        this._addRichNetData(pageNo, netData);
                        this.setRichData(this._richRankData);
                    }else{
                        //盈利排行榜
                        this._addGainsNetData(pageNo, netData);
                        this.setGainData(this._gainsRankData);
                    }
                }
            }else {
                var showItemNum = this.comp_itemContainer.getShowItemNum();
                if(showItemNum < 1)this._setNull();
            }
        }else{
            //不是第一页
            if(!netData) return;

            if(!netData.data || netData.data.length < 1 || netData.code == '0'){
                switch (targetType){
                    case rankType.type1:
                        this._isGainsDataFull = true;
                        break
                    case rankType.type2:
                        this._isRichDataFull = true;
                        break
                    default:
                        break
                }
            }else{
                if(targetType == rankType.type2){
                    //富豪排行榜
                    this._addRichNetData(pageNo, netData);
                    if(this._curRankType == rankType.type2){
                        var infoList = this.comp_itemContainer.getNextPageList(this._list_richData, this._firstShowNum);
                        this._updateBottom(false, infoList, false);
                    }
                }else{
                    //盈利排行榜
                    this._addGainsNetData(pageNo, netData);
                    if(this._curRankType == rankType.type1){
                        var infoList = this.comp_itemContainer.getNextPageList(this._list_gainsData, this._firstShowNum);
                        this._updateBottom(false, infoList, false);
                    }
                }
            }
        }
    },
    _addGainsNetData : function (pageNo, netData) {
        if(!this._gainsRankData) this._gainsRankData = netData;
        else{
            var curItemNum = this._gainsRankData.data.length;
            var curPageNo = this.comp_itemContainer.getPageNoByNum(curItemNum, this._firstShowNum);
            if(pageNo < curPageNo) return;
            //列表数值叠加
            this._gainsRankData.data = this._gainsRankData.data.concat(netData.data);
            this._list_gainsData = netData.data;
            if(netData.data.length < this._firstShowNum){
                //已经请求完所有的数据
                this._isGainsDataFull = true;
            }
        }
        G_DATA.setGainsData(this._gainsRankData);
    },
    _addRichNetData : function (pageNo, netData) {
        if(!this._richRankData) this._richRankData = netData;
        else{
            var curItemNum = this._richRankData.data.length;
            var curPageNo = this.comp_itemContainer.getPageNoByNum(curItemNum, this._firstShowNum);
            if(pageNo < curPageNo) return;
            //列表数值叠加
            this._richRankData.data = this._richRankData.data.concat(netData.data);
            this._list_richData = netData.data;
            if(netData.data.length < this._firstShowNum){
                //已经请求完所有的数据
                this._isRichDataFull = true;
            }
        }
        G_DATA.setRichData(this._richRankData);

    },

    //当scroll滚动到底边  isRestart : 是否从第一个开始
    _updateBottom : function (isGain, infoList, isRestart) {
        var item, comp, index = 0, playerInfo, itemNum = this.comp_itemContainer.getShowItemNum();
        if(isRestart) itemNum = 0;
        for (var i = itemNum; i < (itemNum + this._firstShowNum); i++) {
            playerInfo = infoList[index];
            if(!playerInfo){
                //如果没有玩家数据则终止，不要出现断层
                break;
            }
            item = this.comp_itemContainer.getItemByIndex(i);
            if(item){
                comp = item.getComponent(this._itemCompName);
                comp.init(isGain, i, playerInfo);
            }
            index += 1;
        }
        //如果是第一页，需要将多余的条目清理
        if(isRestart) {
            this.comp_itemContainer.clearItems(infoList.length - 1);
            this._scrollToUp();
        }
    },

    //当玩家滚动到底部
    _scrollToBottom : function (event) {
        var infoList, isGain, itemNum;
        switch (this._curRankType) {
            case rankType.type1:
                isGain = true;
                infoList = this.comp_itemContainer.getNextPageList(this._list_gainsData, this._firstShowNum);
                itemNum = this._gainsRankData.data.length;
                break
            case rankType.type2:
                isGain = false;
                infoList = this.comp_itemContainer.getNextPageList(this._list_richData, this._firstShowNum);
                itemNum = this._richRankData.data.length;
                break
            default:
                break
        }
        if(infoList && infoList.length > 0){
            this._updateBottom(isGain, infoList);
        }else if(itemNum < 50){
            //请求接下去的排行榜数据
            var pageNo = this.comp_itemContainer.getPageNoByNum(itemNum+1, this._firstShowNum);
            switch (this._curRankType) {
                case rankType.type1:
                    if(!this._isGainsDataFull)this._setGainsPageData(pageNo, 50-itemNum);
                    break
                case rankType.type2:
                    if(!this._isRichDataFull)this._setRichPageData(pageNo, 50-itemNum);
                    break
                default:
                    break
            }
        }
    },
    //视图内容将在规定时间内滚动到视图顶部。
    _scrollToUp : function () {
        this.comp_scroll.scrollToTop(0.2);
    },

    _setNull : function (msg) {
        if(Object.prototype.toString.call(msg) !== '[object String]'){
            msg = 'request timeout!'
        }
        this.label_null.string = msg;
    },

    //==============================================

    //移出屏幕外
    moveOutScene : function (isOut, isNoEffect) {
        if(!this._firstPosX) this._firstPosX = -this.node.x;
        var outX = cc.visibleRect.width * 0.3 + this._firstPosX;
        if(isNoEffect){
            var rightW = this.node.width*this.node.scale/2;
            this.node.x = isOut ? -outX : this._firstPosX;
            this.setFrameHide(isOut);
        }else{
            this.node.active = true;
            var time = 0.5;
            this.node.stopAllActions();
            var targetPos = cc.p(this._firstPosX, this.node.y);
            if(isOut) targetPos.x = -outX;
            var self = this;
            this.node.runAction(cc.sequence(cc.moveTo(time, targetPos), cc.callFunc(function () {
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
